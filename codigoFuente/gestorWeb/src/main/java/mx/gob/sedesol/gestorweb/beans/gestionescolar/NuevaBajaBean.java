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
        cargarTiposBaja();
    }

    public void cargarTiposBaja() {
        tiposBaja = bajaUsuarioService.obtenerTiposBaja();
    }

    public void onTipoBajaChange() {
        if (!isBajaParcialOTemporal()) {
            nuevaBaja.setSemestre(null);
            nuevaBaja.setBloque(null);
            nuevaBaja.setIdPrograma(null);
            nuevaBaja.setIdEvento(null);
            nuevaBaja.setIdPeriodo(null);
            semestres.clear();
            bloques.clear();
            programas.clear();
            periodos.clear();
            eventos.clear();
        }
    }

    public void buscarPorMatricula() {
        limpiarListasDependientes();
        if (StringUtils.isBlank(nuevaBaja.getMatricula())) {
            agregarMsgError("Debe capturar la matrícula o usuario", null);
            usuarioValidado = false;
            return;
        }

        Optional<Long> persona = bajaUsuarioService.buscarPersonaPorMatricula(nuevaBaja.getMatricula());
        if (!persona.isPresent()) {
            agregarMsgError("No se encontró la matrícula ingresada", null);
            usuarioValidado = false;
            return;
        }

        nuevaBaja.setIdPersona(persona.get());
        planes = bajaUsuarioService.obtenerPlanes();
        usuarioValidado = true;

        if (planes.isEmpty()) {
            agregarMsgWarn("El usuario no cuenta con planes activos para aplicar baja", null);
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
            semestres = bajaUsuarioService.obtenerSemestres(nuevaBaja.getIdPlan());
            periodos = bajaUsuarioService.obtenerPeriodos();
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
            bloques = bajaUsuarioService.obtenerBloques(nuevaBaja.getSemestre());
            programas = bajaUsuarioService.obtenerProgramas(nuevaBaja.getSemestre());
        }
    }

    public void onBloqueChange() {
        programas.clear();
        eventos.clear();
        nuevaBaja.setIdPrograma(null);
        nuevaBaja.setIdEvento(null);
        if (nuevaBaja.getBloque() != null) {
            programas = bajaUsuarioService.obtenerProgramas(nuevaBaja.getBloque());
        }
    }

    public void onProgramaChange() {
        eventos.clear();
        nuevaBaja.setIdEvento(null);
        if (nuevaBaja.getIdPrograma() != null && nuevaBaja.getIdPeriodo() != null) {
            String nombrePeriodo = obtenerDescripcionPorId(periodos, nuevaBaja.getIdPeriodo().longValue());
            eventos = bajaUsuarioService.obtenerEventos(nombrePeriodo, nuevaBaja.getIdPrograma());
        }
    }

    public void onPeriodoChange() {
        eventos.clear();
        nuevaBaja.setIdEvento(null);

        if (nuevaBaja.getIdPrograma() != null && nuevaBaja.getIdPeriodo() != null) {
            String nombrePeriodo = obtenerDescripcionPorId(periodos, nuevaBaja.getIdPeriodo().longValue());
            eventos = bajaUsuarioService.obtenerEventos(nombrePeriodo, nuevaBaja.getIdPrograma());
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

    public BajaUsuarioService getBajaUsuarioService() {
        return bajaUsuarioService;
    }

    public void setBajaUsuarioService(BajaUsuarioService bajaUsuarioService) {
        this.bajaUsuarioService = bajaUsuarioService;
    }
}
