package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

public class DestinatarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idPersona;
	private String nombreCompleto;
	private boolean nuevo;
	private boolean activo;
	
	public DestinatarioDTO() {
		this.activo = true;
	}
	
	public DestinatarioDTO(PersonaDTO persona) {
		this.idPersona = persona.getIdPersona();
		this.nombreCompleto = persona.getNombreCompleto();
		this.activo = true;
		this.nuevo = true;
	}
	
	public DestinatarioDTO(long idPersona) {
		this.idPersona = idPersona;
		this.activo = true;
		this.nuevo = true;
	}
	
	public DestinatarioDTO(long idPersona, boolean nuevo) {
		this.idPersona = idPersona;
		this.nuevo = nuevo;
		this.activo = true;
	}
	
	public long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	public boolean isNuevo() {
		return nuevo;
	}
	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
