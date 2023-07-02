package mx.gob.sedesol.basegestor.commons.dto.analisisdatos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;

public class AnalisisDatosUsuarioDTO {

	private DomicilioPersonaDTO domicilioPersona;

	private UsuarioDatosLaboralesDTO datosLaborales;

	private List<PersonaRolDTO> listaRoles;

	public String roles() {
		StringBuilder roles = new StringBuilder();
		for (PersonaRolDTO rol : listaRoles) {
			roles.append(rol.getRol().getNombre() + " ");
		}
		return roles.toString();
	}

	public String fechaRegistro() {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(domicilioPersona.getPersona().getFechaRegistro());
	}

	public DomicilioPersonaDTO getDomicilioPersona() {
		return domicilioPersona;
	}

	public void setDomicilioPersona(DomicilioPersonaDTO domicilioPersona) {
		this.domicilioPersona = domicilioPersona;
	}

	public UsuarioDatosLaboralesDTO getDatosLaborales() {
		return datosLaborales;
	}

	public void setDatosLaborales(UsuarioDatosLaboralesDTO datosLaborales) {
		this.datosLaborales = datosLaborales;
	}

	public List<PersonaRolDTO> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<PersonaRolDTO> listaRoles) {
		this.listaRoles = listaRoles;
	}

}
