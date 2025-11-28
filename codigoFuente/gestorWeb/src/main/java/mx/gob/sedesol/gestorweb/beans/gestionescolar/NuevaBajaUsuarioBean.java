package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaSolicitudDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PlanBajaDTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.NuevaBajaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class NuevaBajaUsuarioBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = -7638621743956327370L;

    private static final Logger LOGGER = Logger.getLogger(NuevaBajaUsuarioBean.class);

    private String matriculaUsuario;
    private Long idTipoBaja;
    private Long idPlan;
    private Long idSemestre;
    private Long idBloque;
    private Long idPrograma;
    private String idPeriodo;
    private Long idEvento;
    private String motivo;
    private String quienAplica;
    private String numeroSolicitud;

    private List<NodoDTO> catalogoTiposBaja;
    private List<SelectItem> tiposBaja;
    private List<SelectItem> planes;
    private List<SelectItem> semestres;
    private List<SelectItem> bloques;
    private List<SelectItem> programas;
    private List<SelectItem> periodos;
    private List<SelectItem> eventos;

    private boolean mostrarSemestre;
    private boolean mostrarBloque;
    private boolean mostrarPrograma;
    private boolean mostrarPeriodo;
    private boolean mostrarEvento;
    private String mensajeErrorDialogo;
    private String mensajeExitoDialogo;
    private boolean esTipoDefinitiva;
    private boolean esTipoTemporalOParcial;
    private boolean esSinAsignaturas;

    @javax.faces.bean.ManagedProperty(value = "#{nuevaBajaService}")
    private NuevaBajaService nuevaBajaService;

    @PostConstruct
    public void init() {
        LOGGER.info("Inicializando formulario de nueva baja de usuario");
        cargarCatalogos();
        mostrarSemestre = true;
        mostrarBloque = true;
        mostrarPrograma = true;
        mostrarPeriodo = true;
        mostrarEvento = true;
        limpiarFormulario();
    }

    public void onTipoBajaChange() {
        actualizarVisibilidadCampos();
    }

    public void onPlanChange() {
        semestres = idPlan != null ? obtenerSemestres(idPlan) : Collections.emptyList();
        idSemestre = null;
        onSemestreChange();
    }

    public void onMatriculaChange() {
        if (matriculaUsuario == null || matriculaUsuario.trim().isEmpty()) {
            limpiarDatosDependientes();
            return;
        }

        try {
            BajaMatriculaDetalleDTO datos = nuevaBajaService.obtenerDatosPorMatricula(matriculaUsuario.trim());

            if (datos == null) {
                limpiarDatosDependientes();
                agregarMsgWarn("La matrícula no tiene datos válidos", null);
                return;
            }

            idPlan = datos.getIdPlan();
            semestres = idPlan != null ? obtenerSemestres(idPlan) : Collections.emptyList();
            idSemestre = datos.getIdSemestre();
            bloques = idSemestre != null ? obtenerBloques(idSemestre) : Collections.emptyList();
            idBloque = datos.getIdBloque();
            Long idEjeCapacitacion = idBloque != null ? idBloque : idSemestre;
            programas = idEjeCapacitacion != null
                    ? convertirANodosSelectItem(nuevaBajaService.obtenerProgramasPorEje(idEjeCapacitacion))
                    : Collections.emptyList();
            idPrograma = datos.getIdPrograma();
            idPeriodo = datos.getPeriodo();
            eventos = idPeriodo != null && idPrograma != null
                    ? convertirANodosSelectItem(nuevaBajaService.obtenerEventosPorPeriodoYPrograma(idPeriodo, idPrograma))
                    : Collections.emptyList();
            idEvento = datos.getIdEvento();
            actualizarVisibilidadCampos();
        } catch (Exception ex) {
            LOGGER.error("Error al consultar datos por matrícula", ex);
            agregarMsgError("La matrícula no tiene datos válidos", null);
        }
    }

    public void onSemestreChange() {
        bloques = idSemestre != null ? obtenerBloques(idSemestre) : Collections.emptyList();
        idBloque = null;
        actualizarProgramas();
    }

    public void onBloqueChange() {
        actualizarProgramas();
    }

    public void onPeriodoChange() {
        actualizarEventos();
    }

    public void onProgramaChange() {
        actualizarEventos();
    }

    public void registrarBaja() {
        List<String> errores = new ArrayList<>();

        if (matriculaUsuario == null || matriculaUsuario.trim().isEmpty()) {
            errores.add("La matrícula o usuario es obligatoria");
        }
        if (idTipoBaja == null) {
            errores.add("Seleccione un tipo de baja");
        }
        if (idPlan == null) {
            errores.add("Seleccione un plan");
        }

        if (semestreRequerido() && idSemestre == null) {
            errores.add("Seleccione un semestre");
        }

        if (mostrarPrograma && idPrograma == null) {
            errores.add("Seleccione un programa");
        }

        if (mostrarBloque && esTipoTemporalOParcial && idBloque == null) {
            errores.add("Seleccione un bloque");
        }

        if (mostrarPeriodo && (idPeriodo == null || idPeriodo.trim().isEmpty())) {
            errores.add("Seleccione un periodo");
        }

        if (!errores.isEmpty()) {
            mensajeErrorDialogo = "Ingrese los datos marcados como obligatorios.";
            mostrarDialogo("dlgNuevaBajaValidacion");
            return;
        }

        try {
            BajaSolicitudDTO solicitud = construirSolicitud();
            nuevaBajaService.aplicarBaja(solicitud);
            mensajeExitoDialogo = "Baja aplicada correctamente";
            limpiarFormulario();
            mostrarDialogo("dlgNuevaBajaExito");
        } catch (IllegalArgumentException ex) {
            mensajeErrorDialogo = ex.getMessage();
            mostrarDialogo("dlgNuevaBajaError");
        } catch (Exception ex) {
            LOGGER.error("Error al registrar la baja", ex);
            mensajeErrorDialogo = "Ocurrió un error al registrar la baja";
            mostrarDialogo("dlgNuevaBajaError");
        }
    }

    private void mostrarDialogo(String widgetVar) {
        RequestContext.getCurrentInstance().execute("PF('" + widgetVar + "').show()");
    }

    public void limpiarFormulario() {
        matriculaUsuario = null;
        idTipoBaja = null;
        limpiarDatosDependientes();

        motivo = null;
        quienAplica = null;
        numeroSolicitud = null;

        esTipoDefinitiva = false;
        esTipoTemporalOParcial = false;
        esSinAsignaturas = false;
        mostrarSemestre = true;
        mostrarBloque = true;
        mostrarPrograma = true;
        mostrarPeriodo = true;
        mostrarEvento = true;
    }

    private void limpiarDatosDependientes() {
        idPlan = null;
        idSemestre = null;
        idBloque = null;
        idPrograma = null;
        idPeriodo = null;
        idEvento = null;

        semestres = Collections.emptyList();
        bloques = Collections.emptyList();
        programas = Collections.emptyList();
        eventos = Collections.emptyList();
    }

    private void cargarCatalogos() {
        catalogoTiposBaja = nuevaBajaService.obtenerTiposBaja();
        tiposBaja = convertirANodosSelectItem(catalogoTiposBaja);
        planes = convertirAPlanesSelectItem(nuevaBajaService.obtenerPlanes());
        periodos = convertirAPeriodosSelectItem(nuevaBajaService.obtenerPeriodos());
        eventos = Collections.emptyList();
    }

    private void actualizarProgramas() {
        Long idEjeCapacitacion = idBloque != null ? idBloque : idSemestre;
        if (idEjeCapacitacion != null) {
            programas = convertirANodosSelectItem(nuevaBajaService.obtenerProgramasPorEje(idEjeCapacitacion));
        } else {
            programas = Collections.emptyList();
        }

        idPrograma = null;
        actualizarEventos();
    }

    private List<SelectItem> obtenerSemestres(Long idPlanSeleccionado) {
        return convertirANodosSelectItem(nuevaBajaService.obtenerSemestresPorPlan(idPlanSeleccionado));
    }

    private List<SelectItem> obtenerBloques(Long idSemestreSeleccionado) {
        return convertirANodosSelectItem(nuevaBajaService.obtenerBloquesPorSemestre(idSemestreSeleccionado));
    }

    private void actualizarEventos() {
        if (idPrograma != null && idPeriodo != null && !idPeriodo.trim().isEmpty()) {
            eventos = convertirANodosSelectItem(
                    nuevaBajaService.obtenerEventosPorPeriodoYPrograma(idPeriodo, idPrograma));
        } else {
            eventos = Collections.emptyList();
        }
        idEvento = null;
    }

    private void actualizarVisibilidadCampos() {
        NodoDTO tipoSeleccionado = obtenerTipoSeleccionado();
        String nombreTipo = tipoSeleccionado != null ? tipoSeleccionado.getNombre() : null;

        if (idTipoBaja == null) {
            esTipoDefinitiva = false;
            esTipoTemporalOParcial = false;
            esSinAsignaturas = false;
            mostrarSemestre = true;
            mostrarBloque = true;
            mostrarPrograma = true;
            mostrarPeriodo = true;
            mostrarEvento = true;
            return;
        }

        esTipoDefinitiva = contieneTexto(nombreTipo, "definitiva");
        esTipoTemporalOParcial = contieneTexto(nombreTipo, "temporal") || contieneTexto(nombreTipo, "parcial");
        esSinAsignaturas = contieneTexto(nombreTipo, "sin asignaturas");

        mostrarPrograma = true;
        mostrarPeriodo = true;
        mostrarEvento = true;

        if (esTipoDefinitiva) {
            prepararValoresParaBajaDefinitiva();
            mostrarPrograma = false;
            mostrarEvento = false;
        } else if (esTipoTemporalOParcial) {
            if (semestres.isEmpty() && idPlan != null) {
                semestres = obtenerSemestres(idPlan);
            }
        } else {
            restaurarValoresCamposOcultos();
            if (semestres.isEmpty() && idPlan != null) {
                semestres = obtenerSemestres(idPlan);
            }
        }

        mostrarSemestre = idTipoBaja == null || idSemestre != null;
        mostrarBloque = idTipoBaja == null || idBloque != null;
    }

    private void prepararValoresParaBajaDefinitiva() {
        idPrograma = 0L;
        idEvento = 0L;
    }

    private void restaurarValoresCamposOcultos() {
        if (Long.valueOf(0L).equals(idPrograma)) {
            idPrograma = null;
        }
        if (Long.valueOf(0L).equals(idEvento)) {
            idEvento = null;
        }
    }

    private BajaSolicitudDTO construirSolicitud() {
        BajaSolicitudDTO solicitud = new BajaSolicitudDTO();
        solicitud.setMatriculaUsuario(matriculaUsuario != null ? matriculaUsuario.trim() : null);
        solicitud.setIdTipoBaja(idTipoBaja);
        solicitud.setNombreTipoBaja(obtenerNombreTipoBaja());
        solicitud.setIdPlan(idPlan);
        solicitud.setIdSemestre(idSemestre);
        solicitud.setIdBloque(idBloque);
        solicitud.setIdPrograma(idPrograma);
        solicitud.setIdPeriodo(idPeriodo);
        solicitud.setIdEvento(idEvento);
        solicitud.setMotivo(motivo);
        solicitud.setQuienAplica(quienAplica);
        solicitud.setNumeroSolicitud(numeroSolicitud);
        return solicitud;
    }

    private NodoDTO obtenerTipoSeleccionado() {
        if (catalogoTiposBaja == null || idTipoBaja == null) {
            return null;
        }
        for (NodoDTO tipo : catalogoTiposBaja) {
            if (tipo.getId() != null && idTipoBaja.equals(tipo.getId().longValue())) {
                return tipo;
            }
        }
        return null;
    }

    private String obtenerNombreTipoBaja() {
        NodoDTO tipo = obtenerTipoSeleccionado();
        return tipo != null ? tipo.getNombre() : null;
    }

    private boolean contieneTexto(String origen, String texto) {
        return origen != null && texto != null && origen.toLowerCase().contains(texto.toLowerCase());
    }

    public boolean semestreRequerido() {
        return mostrarSemestre && esTipoTemporalOParcial;
    }

    public boolean bloqueRequerido() {
        return mostrarBloque && esTipoTemporalOParcial;
    }

    private List<SelectItem> convertirANodosSelectItem(List<NodoDTO> nodos) {
        List<SelectItem> items = new ArrayList<>();
        if (nodos != null) {
            for (NodoDTO nodo : nodos) {
                items.add(new SelectItem(nodo.getId() != null ? nodo.getId().longValue() : null, nodo.getNombre()));
            }
        }
        return items;
    }

    private List<SelectItem> convertirAPlanesSelectItem(List<PlanBajaDTO> planesDisponibles) {
        List<SelectItem> items = new ArrayList<>();
        if (planesDisponibles != null) {
            for (PlanBajaDTO plan : planesDisponibles) {
                items.add(new SelectItem(plan.getIdPlan() != null ? plan.getIdPlan().longValue() : null, plan.getNombre()));
            }
        }
        return items;
    }

    private List<SelectItem> convertirAPeriodosSelectItem(List<String> periodosDisponibles) {
        List<SelectItem> items = new ArrayList<>();
        if (periodosDisponibles != null) {
            for (String periodo : periodosDisponibles) {
                items.add(new SelectItem(periodo, periodo));
            }
        }
        return items;
    }

    public String getMatriculaUsuario() {
        return matriculaUsuario;
    }

    public void setMatriculaUsuario(String matriculaUsuario) {
        this.matriculaUsuario = matriculaUsuario;
    }

    public Long getIdTipoBaja() {
        return idTipoBaja;
    }

    public void setIdTipoBaja(Long idTipoBaja) {
        this.idTipoBaja = idTipoBaja;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public Long getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Long idSemestre) {
        this.idSemestre = idSemestre;
    }

    public Long getIdBloque() {
        return idBloque;
    }

    public void setIdBloque(Long idBloque) {
        this.idBloque = idBloque;
    }

    public Long getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(String idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getQuienAplica() {
        return quienAplica;
    }

    public void setQuienAplica(String quienAplica) {
        this.quienAplica = quienAplica;
    }

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public List<SelectItem> getTiposBaja() {
        return tiposBaja;
    }

    public void setTiposBaja(List<SelectItem> tiposBaja) {
        this.tiposBaja = tiposBaja;
    }

    public List<SelectItem> getPlanes() {
        return planes;
    }

    public void setPlanes(List<SelectItem> planes) {
        this.planes = planes;
    }

    public List<SelectItem> getSemestres() {
        return semestres;
    }

    public void setSemestres(List<SelectItem> semestres) {
        this.semestres = semestres;
    }

    public List<SelectItem> getBloques() {
        return bloques;
    }

    public void setBloques(List<SelectItem> bloques) {
        this.bloques = bloques;
    }

    public List<SelectItem> getProgramas() {
        return programas;
    }

    public void setProgramas(List<SelectItem> programas) {
        this.programas = programas;
    }

    public List<SelectItem> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<SelectItem> periodos) {
        this.periodos = periodos;
    }

    public List<SelectItem> getEventos() {
        return eventos;
    }

    public void setEventos(List<SelectItem> eventos) {
        this.eventos = eventos;
    }

    public String getMensajeErrorDialogo() {
        return mensajeErrorDialogo;
    }

    public void setMensajeErrorDialogo(String mensajeErrorDialogo) {
        this.mensajeErrorDialogo = mensajeErrorDialogo;
    }

    public String getMensajeExitoDialogo() {
        return mensajeExitoDialogo;
    }

    public void setMensajeExitoDialogo(String mensajeExitoDialogo) {
        this.mensajeExitoDialogo = mensajeExitoDialogo;
    }

    public boolean isMostrarSemestre() {
        return mostrarSemestre;
    }

    public void setMostrarSemestre(boolean mostrarSemestre) {
        this.mostrarSemestre = mostrarSemestre;
    }

    public boolean isMostrarBloque() {
        return mostrarBloque;
    }

    public void setMostrarBloque(boolean mostrarBloque) {
        this.mostrarBloque = mostrarBloque;
    }

    public boolean isMostrarPrograma() {
        return mostrarPrograma;
    }

    public void setMostrarPrograma(boolean mostrarPrograma) {
        this.mostrarPrograma = mostrarPrograma;
    }

    public boolean isMostrarPeriodo() {
        return mostrarPeriodo;
    }

    public void setMostrarPeriodo(boolean mostrarPeriodo) {
        this.mostrarPeriodo = mostrarPeriodo;
    }

    public boolean isMostrarEvento() {
        return mostrarEvento;
    }

    public void setMostrarEvento(boolean mostrarEvento) {
        this.mostrarEvento = mostrarEvento;
    }

    public NuevaBajaService getNuevaBajaService() {
        return nuevaBajaService;
    }

    public void setNuevaBajaService(NuevaBajaService nuevaBajaService) {
        this.nuevaBajaService = nuevaBajaService;
    }
}
