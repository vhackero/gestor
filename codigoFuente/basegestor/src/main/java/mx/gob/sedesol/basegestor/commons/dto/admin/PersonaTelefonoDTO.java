package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class PersonaTelefonoDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idPersonaTelefono;
	private String telefono;
	private String extension;
	private TipoTelefonoDTO tipoTelefono;
	private Integer activo;
	
	private PersonaDTO persona;
	
	public PersonaTelefonoDTO() {
		setFechaRegistro(new Date());
		this.activo = 1;
	}
	
	public PersonaTelefonoDTO(long usuarioModifico, int idTipoTelefono) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.activo = 1;
		this.tipoTelefono = new TipoTelefonoDTO();
		this.tipoTelefono.setIdTipoTelefono(idTipoTelefono);
	}
	
	/**
	 * @return the idPersonaTelefono
	 */
	public Integer getIdPersonaTelefono() {
		return idPersonaTelefono;
	}
	/**
	 * @param idPersonaTelefono the idPersonaTelefono to set
	 */
	public void setIdPersonaTelefono(Integer idPersonaTelefono) {
		this.idPersonaTelefono = idPersonaTelefono;
	}
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @return the activo
	 */
	public Integer getActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}

	public TipoTelefonoDTO getTipoTelefono() {
		return tipoTelefono;
	}

	public void setTipoTelefono(TipoTelefonoDTO tipoTelefono) {
		this.tipoTelefono = tipoTelefono;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

}
