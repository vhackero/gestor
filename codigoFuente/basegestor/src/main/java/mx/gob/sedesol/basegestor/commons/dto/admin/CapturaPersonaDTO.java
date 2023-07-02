package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CapturaPersonaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PersonaDTO persona;
	private UsuarioDatosLaboralesDTO datosLaborales;
	private PersonaTelefonoDTO telefonoFijo;
	private PersonaTelefonoDTO celular;
	private PersonaCorreoDTO personaCorreo;
	private PersonaCorreoDTO correoDePersonaEnBD;
	private DomicilioPersonaDTO domicilioPersona;
	private List<RolDTO> roles;
	private PersonaDatosAcademicoDTO datosAcademicos;
	private List<SsoElementoDTO> elementos;

	
	public CapturaPersonaDTO() {
		this.persona = new PersonaDTO();
		this.datosLaborales = new UsuarioDatosLaboralesDTO();
		this.telefonoFijo = new PersonaTelefonoDTO();
		this.celular = new PersonaTelefonoDTO();
		this.personaCorreo = new PersonaCorreoDTO();
		this.correoDePersonaEnBD = new PersonaCorreoDTO();
		this.domicilioPersona = new DomicilioPersonaDTO();
		this.roles = new ArrayList<>();
		this.elementos = new ArrayList<>();
		this.datosAcademicos = new PersonaDatosAcademicoDTO();
	}
	
	public CapturaPersonaDTO(PersonaDTO persona, UsuarioDatosLaboralesDTO datosLaborales, PersonaTelefonoDTO telefonoFijo,
			PersonaTelefonoDTO celular, PersonaCorreoDTO personaCorreo, DomicilioPersonaDTO domicilioPersona,
			List<RolDTO> roles) {
		this.persona = persona;
		this.datosLaborales = datosLaborales;
		this.telefonoFijo = telefonoFijo;
		this.celular = celular;
		this.personaCorreo = personaCorreo;
		this.domicilioPersona = domicilioPersona;
		this.roles = roles;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public UsuarioDatosLaboralesDTO getDatosLaborales() {
		return datosLaborales;
	}

	public void setDatosLaborales(UsuarioDatosLaboralesDTO datosLaborales) {
		this.datosLaborales = datosLaborales;
	}

	public PersonaTelefonoDTO getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(PersonaTelefonoDTO telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public PersonaTelefonoDTO getCelular() {
		return celular;
	}

	public void setCelular(PersonaTelefonoDTO celular) {
		this.celular = celular;
	}

	public PersonaCorreoDTO getPersonaCorreo() {
		return personaCorreo;
	}

	public void setPersonaCorreo(PersonaCorreoDTO personaCorreo) {
		this.personaCorreo = personaCorreo;
	}

	public DomicilioPersonaDTO getDomicilioPersona() {
		return domicilioPersona;
	}

	public void setDomicilioPersona(DomicilioPersonaDTO domicilioPersona) {
		this.domicilioPersona = domicilioPersona;
	}

	public List<RolDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RolDTO> roles) {
		this.roles = roles;
	}

	public PersonaDatosAcademicoDTO getDatosAcademicos() {
		return datosAcademicos;
	}

	public void setDatosAcademicos(PersonaDatosAcademicoDTO datosAcademicos) {
		this.datosAcademicos = datosAcademicos;
	}

	@Override
	public String toString() {
		return "CapturaPersonaDTO [persona=" + persona + ", datosLaborales=" + datosLaborales + ", telefonoFijo="
				+ telefonoFijo + ", celular=" + celular + ", personaCorreo=" + personaCorreo + ", domicilioPersona="
				+ domicilioPersona + ", roles=" + roles + ", datosAcademicos=" + datosAcademicos + "]";
	}

	/**
	 * @return the elementos
	 */
	public List<SsoElementoDTO> getElementos() {
		return elementos;
	}

	/**
	 * @param elementos the elementos to set
	 */
	public void setElementos(List<SsoElementoDTO> elementos) {
		this.elementos = elementos;
	}

	public PersonaCorreoDTO getCorreoDePersonaEnBD() {
		return correoDePersonaEnBD;
	}

	public void setCorreoDePersonaEnBD(PersonaCorreoDTO correoDePersonaEnBD) {
		this.correoDePersonaEnBD = correoDePersonaEnBD;
	}




}
