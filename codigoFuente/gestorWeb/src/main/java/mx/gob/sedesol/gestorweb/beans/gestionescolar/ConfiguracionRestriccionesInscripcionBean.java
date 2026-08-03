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

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReglaInscripcionDTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class ConfiguracionRestriccionesInscripcionBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConfiguracionRestriccionesInscripcionBean.class);

	@ManagedProperty(value = "#{inscripcionService}")
	private InscripcionService inscripcionService;

	private List<ReglaInscripcionDTO> reglas;

	@PostConstruct
	public void init() {
		consultarReglas();
	}

	private void consultarReglas() {
		try {
			reglas = inscripcionService.obtenerReglasInscripcion();
		} catch (Exception e) {
			logger.error("No fue posible consultar las reglas de inscripción", e);
			reglas = new ArrayList<>();
			mensaje(FacesMessage.SEVERITY_ERROR, "Error", "No fue posible consultar las reglas de inscripción.");
		}
	}

	public void guardar(ReglaInscripcionDTO regla) {
		try {
			inscripcionService.guardarReglaInscripcion(regla, getUsuarioEnSession().getIdPersona());
			consultarReglas();
			mensaje(FacesMessage.SEVERITY_INFO, "Configuración guardada",
					"La regla de inscripción fue actualizada correctamente.");
		} catch (Exception e) {
			logger.error("No fue posible guardar la regla de inscripción " + regla.getClave(), e);
			mensaje(FacesMessage.SEVERITY_ERROR, "Error", "No fue posible guardar la regla de inscripción.");
		}
	}

	private void mensaje(FacesMessage.Severity severity, String titulo, String detalle) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, detalle));
	}

	public InscripcionService getInscripcionService() { return inscripcionService; }
	public void setInscripcionService(InscripcionService inscripcionService) { this.inscripcionService = inscripcionService; }
	public List<ReglaInscripcionDTO> getReglas() { return reglas; }
	public void setReglas(List<ReglaInscripcionDTO> reglas) { this.reglas = reglas; }
}
