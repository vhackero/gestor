package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

public class PersonaDatosDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String institucion;
	private String areaAdscripcion;
	private PersonaDTO persona;
	private boolean seleccionado;
	
	public String getInstitucion() {
		return institucion;
	}
	public String getAreaAdscripcion() {
		return areaAdscripcion;
	}
	public PersonaDTO getPersona() {
		return persona;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	public void setAreaAdscripcion(String areaAdscripcion) {
		this.areaAdscripcion = areaAdscripcion;
	}
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}
	public boolean isSeleccionado() {
		return seleccionado;
	}
	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

}
