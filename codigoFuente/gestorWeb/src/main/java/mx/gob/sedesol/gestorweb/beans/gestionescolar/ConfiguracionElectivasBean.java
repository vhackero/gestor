package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionElectivaDTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class ConfiguracionElectivasBean extends BaseBean {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ConfiguracionElectivasBean.class);

    @ManagedProperty(value = "#{inscripcionService}")
    private InscripcionService inscripcionService;

    private Long idProcesoInscripcion;
    private List<ConfiguracionElectivaDTO> procesos;
    private List<ConfiguracionElectivaDTO> asignaturas;

    @PostConstruct
    public void init() {
        asignaturas = new ArrayList<>();
        try {
            procesos = inscripcionService.obtenerProcesosActivosConfiguracionElectivas();
        } catch (Exception e) {
            logger.error("No fue posible consultar los procesos para configurar electivas", e);
            procesos = new ArrayList<>();
            mensaje(FacesMessage.SEVERITY_ERROR, "Error",
                    "No fue posible consultar los procesos de inscripción activos.");
        }
    }

    public void consultarAsignaturas() {
        if (idProcesoInscripcion == null) {
            asignaturas = new ArrayList<>();
            return;
        }
        asignaturas = inscripcionService.obtenerAsignaturasConfiguracionElectivas(idProcesoInscripcion);
    }

    public void guardar(ConfiguracionElectivaDTO configuracion) {
        try {
            validar(configuracion);
            inscripcionService.guardarConfiguracionElectiva(configuracion);
            consultarAsignaturas();
            mensaje(FacesMessage.SEVERITY_INFO, "Configuración guardada",
                    "La asignatura electiva fue actualizada correctamente.");
        } catch (IllegalArgumentException e) {
            mensaje(FacesMessage.SEVERITY_WARN, "Revisa la configuración", e.getMessage());
        } catch (Exception e) {
            logger.error("No fue posible guardar la configuración de la electiva", e);
            mensaje(FacesMessage.SEVERITY_ERROR, "Error", "No fue posible guardar la configuración.");
        }
    }

    private void validar(ConfiguracionElectivaDTO configuracion) {
        if (!Boolean.TRUE.equals(configuracion.getSinLimite())) {
            if (configuracion.getCupoMaximo() == null || configuracion.getCupoMaximo() <= 0) {
                throw new IllegalArgumentException("El cupo máximo debe ser mayor que cero.");
            }
            if (configuracion.getCupoOcupado() != null
                    && configuracion.getCupoMaximo().longValue() < configuracion.getCupoOcupado()) {
                throw new IllegalArgumentException("El cupo no puede ser menor que los "
                        + configuracion.getCupoOcupado() + " lugares actualmente ocupados.");
            }
        }
    }

    private void mensaje(FacesMessage.Severity severity, String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, detalle));
    }

    public InscripcionService getInscripcionService() { return inscripcionService; }
    public void setInscripcionService(InscripcionService value) { this.inscripcionService = value; }
    public Long getIdProcesoInscripcion() { return idProcesoInscripcion; }
    public void setIdProcesoInscripcion(Long value) { this.idProcesoInscripcion = value; }
    public List<ConfiguracionElectivaDTO> getProcesos() { return procesos; }
    public void setProcesos(List<ConfiguracionElectivaDTO> value) { this.procesos = value; }
    public List<ConfiguracionElectivaDTO> getAsignaturas() { return asignaturas; }
    public void setAsignaturas(List<ConfiguracionElectivaDTO> value) { this.asignaturas = value; }
}
