package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class PruebaBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(PruebaBean.class);

	@ManagedProperty("#{personaService}")
	private PersonaService personaService;

	private String titulo;

	@NotEmpty(message = "no perro")
	private String codigoPostal;

	private List<PersonaDTO> personas;

	private PersonaDTO personaSeleccionada;

	public PruebaBean() {
		titulo = "Mi ejemplo";
	}

	public void obtenerPersonasPorCodigoPostal() {
		personas = personaService.obtenerPersonaPorCodigoPostal(codigoPostal);
		if (!personas.isEmpty()) {
			logger.info(personas.get(0).getDomiciliosPersona().get(0).getAsentamiento().getCodigoPostal());
		} else {
			agregarFlashMessage(getUsuarioEnSession().getUsuario(), " no se encontraron resultados", FacesMessage.SEVERITY_INFO);
			
		}

	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public List<PersonaDTO> getPersonas() {
		return personas;
	}

	public void setPersonas(List<PersonaDTO> personas) {
		this.personas = personas;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public PersonaDTO getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(PersonaDTO personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

}
