package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class RolDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idRol;
	@Length(max=50)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombre;
	private Long usuarioModifica;
	private List<PersonaRolDTO> personaRolesDTO;
	private List<RolFuncionalidadDTO> rolFuncionalidadesDTO;
	private boolean activo;
	@Length(max=100)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String clave;
	
	public RolDTO() {
		setFechaRegistro(new Date());
		this.activo = true;
	}
	
	public RolDTO(Long usuarioModifico) {
		this.usuarioModifica = usuarioModifico;
		setFechaRegistro(new Date());
		this.activo = true;
	}
	
	/**
	 * @return  idRol
	 */
	public Integer getIdRol() {
		return idRol;
	}
	/**
	 * @param idRol set idRol 
	 */
	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}
	/**
	 * @return  nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre set nombre 
	 */
	public void setNombre(String nombre) {
		if (ObjectUtils.isNull(nombre)) {
			this.nombre = null;
		} else {
			this.nombre = nombre.trim();
		}
	}
	/**
	 * @return  usuarioModifica
	 */
	public Long getUsuarioModifica() {
		return usuarioModifica;
	}
	/**
	 * @param usuarioModifica set usuarioModifica 
	 */
	public void setUsuarioModifica(Long usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}
	/**
	 * @return  personaRolesDTO
	 */
	public List<PersonaRolDTO> getPersonaRolesDTO() {
		return personaRolesDTO;
	}
	/**
	 * @param personaRolesDTO set personaRolesDTO 
	 */
	public void setPersonaRolesDTO(List<PersonaRolDTO> personaRolesDTO) {
		this.personaRolesDTO = personaRolesDTO;
	}
	/**
	 * @return  rolFuncionalidadesDTO
	 */
	public List<RolFuncionalidadDTO> getRolFuncionalidadesDTO() {
		return rolFuncionalidadesDTO;
	}
	/**
	 * @param rolFuncionalidadesDTO set rolFuncionalidadesDTO 
	 */
	public void setRolFuncionalidadesDTO(List<RolFuncionalidadDTO> rolFuncionalidadesDTO) {
		this.rolFuncionalidadesDTO = rolFuncionalidadesDTO;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}
	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		if (ObjectUtils.isNull(clave)) {
			this.clave = null;
		} else {
			this.clave = clave.trim();
		}
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RolDTO [idRol=");
		builder.append(idRol);
		builder.append(", fechaActualizacion=");
		builder.append(getFechaActualizacion());
		builder.append(", fechaRegistro=");
		builder.append(getFechaRegistro());
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", clave=");
		builder.append(clave);
		builder.append(", usuarioModifica=");
		builder.append(usuarioModifica);
		builder.append(", personaRolesDTO=");
		builder.append(personaRolesDTO);
		builder.append(", rolFuncionalidadesDTO=");
		builder.append(rolFuncionalidadesDTO);
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
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		result = prime * result + ((idRol == null) ? 0 : idRol.hashCode());
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
		if (!(obj instanceof RolDTO))
			return false;
		RolDTO other = (RolDTO) obj;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		if (idRol == null) {
			if (other.idRol != null)
				return false;
		} else if (!idRol.equals(other.idRol))
			return false;
		return true;
	}
}
