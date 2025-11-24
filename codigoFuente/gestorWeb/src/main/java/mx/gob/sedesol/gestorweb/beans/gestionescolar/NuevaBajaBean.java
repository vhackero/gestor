package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CatalogoOpcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.NuevaBajaDTO;
import mx.gob.sedesol.basegestor.service.ServiceException;
import mx.gob.sedesol.basegestor.service.gestionescolar.BajaUsuarioService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;

@ManagedBean
@ViewScoped
public class NuevaBajaBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(NuevaBajaBean.class);

    @ManagedProperty(value = "#{bajaUsuarioService}")
    private transient BajaUsuarioService bajaUsuarioService;

    private NuevaBajaDTO nuevaBaja;
    private List<CatalogoOpcionDTO> tiposBaja;
    private List<CatalogoOpcionDTO> planes;
    private List<CatalogoOpcionDTO> semestres;
    private List<CatalogoOpcionDTO> bloques;
    private List<CatalogoOpcionDTO> programas;
    private List<CatalogoOpcionDTO> periodos;
    private List<CatalogoOpcionDTO> eventos;
    private boolean usuarioValidado;
    private String mensajeUsuarioNoEncontrado;

    @PostConstruct
    public void init() {
        nuevaBaja = new NuevaBajaDTO();
        tiposBaja = new ArrayList<>();
        planes = new ArrayList<>();
        semestres = new ArrayList<>();
        bloques = new ArrayList<>();
        programas = new ArrayList<>();
        periodos = new ArrayList<>();
        eventos = new ArrayList<>();
        usuarioValidado = false;
        mensajeUsuarioNoEncontrado = "";
        cargarTiposBaja();
    }

    public void cargarTiposBaja() {
        tiposBaja = bajaUsuarioService.obtenerTiposBaja();
    }

    public void buscarPorMatricula() {
        limpiarListasDependientes();
        usuarioValidado = false;
        nuevaBaja.setIdPersona(null);
        String matriculaCapturada = nuevaBaja.getMatricula();
        LOGGER.info("buscarPorMatricula invocado con valor capturado: '" + matriculaCapturada + "'");
        String matriculaNormalizada = StringUtils.lowerCase(StringUtils.trimToEmpty(matriculaCapturada));
        nuevaBaja.setMatricula(matriculaNormalizada);
        LOGGER.info("Matrícula normalizada (minúsculas): '" + matriculaNormalizada + "'");

        if (StringUtils.isBlank(matriculaNormalizada)) {
            LOGGER.warn("Búsqueda cancelada: la matrícula está vacía o en blanco");
            agregarMsgError("Debe capturar la matrícula o usuario", null);
            usuarioValidado = false;
            return;
        }

        try {
            LOGGER.info("Iniciando búsqueda de usuario con matrícula normalizada: " + matriculaNormalizada);
            Optional<Long> persona = bajaUsuarioService.buscarPersonaPorMatricula(matriculaNormalizada);
            if (!persona.isPresent()) {
                mensajeUsuarioNoEncontrado = "El usuario con matrícula " + matriculaNormalizada
                        + " no existe en el sistema. Verifique la información.";
                LOGGER.warn("No se encontró información para la matrícula: " + matriculaNormalizada);
                usuarioValidado = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("frmNuevaBaja:dlgUsuarioNoEncontrado");
                context.execute("PF('dlgUsuarioNoEncontrado').show();");
                return;
            }

            nuevaBaja.setIdPersona(persona.get());
            LOGGER.info("Matrícula " + matriculaNormalizada + " encontrada con id de persona: " + persona.get());
            mensajeUsuarioNoEncontrado = "";
            planes = bajaUsuarioService.obtenerPlanes();
            LOGGER.info("Se recuperaron " + (planes != null ? planes.size() : 0)
                    + " planes activos para la matrícula " + matriculaNormalizada);
            usuarioValidado = true;

            if (planes.isEmpty()) {
                agregarMsgWarn("El usuario no cuenta con planes activos para aplicar baja", null);
            }
        } catch (Exception e) {
            LOGGER.error("Error inesperado al buscar la matrícula " + nuevaBaja.getMatricula(), e);
            agregarMsgError("No fue posible completar la búsqueda. Contacte al administrador del sistema.", null);
        }
    }

    public void onPlanChange() {
        semestres.clear();
        bloques.clear();
        programas.clear();
        periodos.clear();
        eventos.clear();
        nuevaBaja.setSemestre(null);
        nuevaBaja.setBloque(null);
        nuevaBaja.setIdPrograma(null);
        nuevaBaja.setIdEvento(null);
        nuevaBaja.setIdPeriodo(null);

        if (nuevaBaja.getIdPlan() != null) {
            LOGGER.info("Cargando semestres y periodos para el plan " + nuevaBaja.getIdPlan());
            semestres = bajaUsuarioService.obtenerSemestres(nuevaBaja.getIdPlan());
            periodos = bajaUsuarioService.obtenerPeriodos();
            LOGGER.info("Semestres obtenidos: " + (semestres != null ? semestres.size() : 0)
                    + ", periodos obtenidos: " + (periodos != null ? periodos.size() : 0));
        } else {
            LOGGER.info("Sin plan seleccionado, no se cargan semestres ni periodos");
        }
    }

    public void onSemestreChange() {
        bloques.clear();
        programas.clear();
        eventos.clear();
        nuevaBaja.setBloque(null);
        nuevaBaja.setIdPrograma(null);
        nuevaBaja.setIdEvento(null);

        if (nuevaBaja.getSemestre() != null) {
            LOGGER.info("Cargando bloques y programas para el semestre " + nuevaBaja.getSemestre());
            bloques = bajaUsuarioService.obtenerBloques(nuevaBaja.getSemestre());
            programas = bajaUsuarioService.obtenerProgramas(nuevaBaja.getSemestre());
            LOGGER.info("Bloques obtenidos: " + (bloques != null ? bloques.size() : 0)
                    + ", programas obtenidos: " + (programas != null ? programas.size() : 0));
        } else {
            LOGGER.info("Sin semestre seleccionado, no se cargan bloques ni programas");
        }
    }

    public void onBloqueChange() {
        programas.clear();
        eventos.clear();
        nuevaBaja.setIdPrograma(null);
        nuevaBaja.setIdEvento(null);
        if (nuevaBaja.getBloque() != null) {
            LOGGER.info("Cargando programas para el bloque " + nuevaBaja.getBloque());
            programas = bajaUsuarioService.obtenerProgramas(nuevaBaja.getBloque());
            LOGGER.info("Programas obtenidos: " + (programas != null ? programas.size() : 0));
        } else {
            LOGGER.info("Sin bloque seleccionado, no se cargan programas");
        }
    }

    public void onProgramaChange() {
        eventos.clear();
        nuevaBaja.setIdEvento(null);
        if (nuevaBaja.getIdPrograma() != null && nuevaBaja.getIdPeriodo() != null) {
            String nombrePeriodo = obtenerDescripcionPorId(periodos, nuevaBaja.getIdPeriodo().longValue());
            LOGGER.info("Cargando eventos para el programa " + nuevaBaja.getIdPrograma() + " y periodo " + nombrePeriodo);
            eventos = bajaUsuarioService.obtenerEventos(nombrePeriodo, nuevaBaja.getIdPrograma());
            LOGGER.info("Eventos obtenidos: " + (eventos != null ? eventos.size() : 0));
        } else {
            LOGGER.info("Sin programa o periodo seleccionado, no se cargan eventos");
        }
    }

    public void onPeriodoChange() {
        eventos.clear();
        nuevaBaja.setIdEvento(null);

        if (nuevaBaja.getIdPrograma() != null && nuevaBaja.getIdPeriodo() != null) {
            String nombrePeriodo = obtenerDescripcionPorId(periodos, nuevaBaja.getIdPeriodo().longValue());
            LOGGER.info("Actualizando eventos para el programa " + nuevaBaja.getIdPrograma() + " y periodo " + nombrePeriodo);
            eventos = bajaUsuarioService.obtenerEventos(nombrePeriodo, nuevaBaja.getIdPrograma());
            LOGGER.info("Eventos obtenidos tras cambio de periodo: " + (eventos != null ? eventos.size() : 0));
        } else {
            LOGGER.info("Sin programa o periodo seleccionado tras el cambio, no se actualizan eventos");
        }
    }

    public void aplicarBaja() {
        List<String> errores = new ArrayList<>();
        if (!usuarioValidado || nuevaBaja.getIdPersona() == null) {
            errores.add("Debe validar la matrícula antes de aplicar la baja");
        }
        if (nuevaBaja.getIdTipoBaja() == null) {
            errores.add("Seleccione el tipo de baja");
        }
        if (nuevaBaja.getIdPlan() == null) {
            errores.add("Seleccione el plan");
        }
        boolean bajaDetallada = isBajaParcialOTemporal();
        if (bajaDetallada) {
            if (nuevaBaja.getSemestre() == null) {
                errores.add("Seleccione el semestre");
            }
            if (nuevaBaja.getIdPrograma() == null) {
                errores.add("Seleccione la asignatura o programa");
            }
            if (nuevaBaja.getIdPeriodo() == null) {
                errores.add("Seleccione el periodo");
            }
        }
        if (StringUtils.isBlank(nuevaBaja.getMotivo())) {
            errores.add("Capture el motivo de la baja");
        }

        if (!errores.isEmpty()) {
            errores.forEach(error -> agregarMsgError(error, null));
            return;
        }

        try {
            UsuarioSessionDTO usuario = getUsuarioEnSession();
            bajaUsuarioService.aplicarBaja(nuevaBaja, usuario != null ? usuario.getIdPersona() : null);
            agregarMsgInfo("Baja aplicada correctamente", null);
            limpiarFormulario();
        } catch (ServiceException se) {
            LOGGER.error("Error de negocio al aplicar la baja", se);
            agregarMsgError(se.getMessage(), null);
        } catch (Exception e) {
            LOGGER.error("Error inesperado al aplicar la baja", e);
            agregarMsgError("No fue posible aplicar la baja. Intente nuevamente.", null);
        }
    }

    public boolean isBajaParcialOTemporal() {
        if (nuevaBaja.getIdTipoBaja() == null) {
            return false;
        }
        return tiposBaja.stream()
                .filter(tipo -> tipo.getId().equals(nuevaBaja.getIdTipoBaja()))
                .map(CatalogoOpcionDTO::getDescripcion)
                .anyMatch(nombre -> StringUtils.containsIgnoreCase(nombre, "parcial") || StringUtils.containsIgnoreCase(nombre, "temporal"));
    }

    private String obtenerDescripcionPorId(List<CatalogoOpcionDTO> opciones, Long idSeleccionado) {
        if (opciones == null || idSeleccionado == null) {
            return "";
        }
        return opciones.stream()
                .filter(op -> op.getId() != null && op.getId().equals(idSeleccionado))
                .map(CatalogoOpcionDTO::getDescripcion)
                .findFirst()
                .orElse("");
    }

    private void limpiarListasDependientes() {
        planes.clear();
        semestres.clear();
        bloques.clear();
        programas.clear();
        periodos.clear();
        eventos.clear();
    }

    public void limpiarFormulario() {
        nuevaBaja = new NuevaBajaDTO();
        limpiarListasDependientes();
        usuarioValidado = false;
        mensajeUsuarioNoEncontrado = "";
        cargarTiposBaja();
    }

    public NuevaBajaDTO getNuevaBaja() {
        return nuevaBaja;
    }

    public void setNuevaBaja(NuevaBajaDTO nuevaBaja) {
        this.nuevaBaja = nuevaBaja;
    }

    public List<CatalogoOpcionDTO> getTiposBaja() {
        return tiposBaja;
    }

    public void setTiposBaja(List<CatalogoOpcionDTO> tiposBaja) {
        this.tiposBaja = tiposBaja;
    }

    public List<CatalogoOpcionDTO> getPlanes() {
        return planes;
    }

    public void setPlanes(List<CatalogoOpcionDTO> planes) {
        this.planes = planes;
    }

    public List<CatalogoOpcionDTO> getSemestres() {
        return semestres;
    }

    public void setSemestres(List<CatalogoOpcionDTO> semestres) {
        this.semestres = semestres;
    }

    public List<CatalogoOpcionDTO> getBloques() {
        return bloques;
    }

    public void setBloques(List<CatalogoOpcionDTO> bloques) {
        this.bloques = bloques;
    }

    public List<CatalogoOpcionDTO> getProgramas() {
        return programas;
    }

    public void setProgramas(List<CatalogoOpcionDTO> programas) {
        this.programas = programas;
    }

    public List<CatalogoOpcionDTO> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<CatalogoOpcionDTO> periodos) {
        this.periodos = periodos;
    }

    public List<CatalogoOpcionDTO> getEventos() {
        return eventos;
    }

    public void setEventos(List<CatalogoOpcionDTO> eventos) {
        this.eventos = eventos;
    }

    public String getMensajeUsuarioNoEncontrado() {
        return mensajeUsuarioNoEncontrado;
    }

    public void setMensajeUsuarioNoEncontrado(String mensajeUsuarioNoEncontrado) {
        this.mensajeUsuarioNoEncontrado = mensajeUsuarioNoEncontrado;
    }

    public BajaUsuarioService getBajaUsuarioService() {
        return bajaUsuarioService;
    }

    public void setBajaUsuarioService(BajaUsuarioService bajaUsuarioService) {
        this.bajaUsuarioService = bajaUsuarioService;
    }
}
