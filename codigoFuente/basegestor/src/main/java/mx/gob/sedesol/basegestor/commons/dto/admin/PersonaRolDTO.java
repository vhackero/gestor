package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class PersonaRolDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idPersonaRol;
	private PersonaDTO persona;
	private RolDTO rol;
	private Integer activo;
	
	/**
	 * @return  idPersonaRol
	 */
	public Long getIdPersonaRol() {
		return idPersonaRol;
	}
	/**
	 * @param idPersonaRol set idPersonaRol 
	 */
	public void setIdPersonaRol(Long idPersonaRol) {
		this.idPersonaRol = idPersonaRol;
	}
	public Integer getActivo() {
		return activo;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	/**
	 * @return the persona
	 */
	public PersonaDTO getPersona() {
		return persona;
	}
	/**
	 * @param persona the persona to set
	 */
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}
	/**
	 * @return the rol
	 */
	public RolDTO getRol() {
		return rol;
	}
	/**
	 * @param rol the rol to set
	 */
	public void setRol(RolDTO rol) {
		this.rol = rol;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PersonaRolDTO [idPersonaRol=");
		builder.append(idPersonaRol);
		builder.append(", fechaActualizacion=");
		builder.append(getFechaActualizacion());
		builder.append(", fechaRegistro=");
		builder.append(getFechaRegistro());
		builder.append(", usuarioModifico=");
		builder.append(getUsuarioModifico());
		if (ObjectUtils.isNotNull(persona)) {
			builder.append(", personaDTO=");
			builder.append(persona.toString());
		}
		builder.append("]");
		return builder.toString();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PersonaRolDTO))
			return false;
		PersonaRolDTO other = (PersonaRolDTO) obj;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		return true;
	}
	
}
