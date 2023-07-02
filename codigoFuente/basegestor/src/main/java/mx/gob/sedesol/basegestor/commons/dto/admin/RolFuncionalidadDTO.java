/**
 * 
 */
package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;


/**
 * @author PlanetMedia
 *
 */
public class RolFuncionalidadDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idRolFuncionalidad;
	private FuncionalidadDTO funcionalidadDTO;
	private RolDTO rol;
	private Integer activo;
	
	private boolean seleccionado;
	private List<RolFuncionalidadDTO> funcionalidades;
	/**
	 * @return  idRolFuncionalidad
	 */
	public Long getIdRolFuncionalidad() {
		return idRolFuncionalidad;
	}
	/**
	 * @param idRolFuncionalidad set idRolFuncionalidad 
	 */
	public void setIdRolFuncionalidad(Long idRolFuncionalidad) {
		this.idRolFuncionalidad = idRolFuncionalidad;
	}
	public Integer getActivo() {
		return activo;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	/**
	 * @return the funcionalidadDTO
	 */
	public FuncionalidadDTO getFuncionalidadDTO() {
		return funcionalidadDTO;
	}
	/**
	 * @param funcionalidad the funcionalidadDTO to set
	 */
	public void setFuncionalidadDTO(FuncionalidadDTO funcionalidad) {
		this.funcionalidadDTO = funcionalidad;
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
	public boolean isSeleccionado() {
		return seleccionado;
	}
	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	public List<RolFuncionalidadDTO> getFuncionalidades() {
		return funcionalidades;
	}
	public void setFuncionalidades(List<RolFuncionalidadDTO> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RolFuncionalidadDTO [idRolFuncionalidad=");
		builder.append(idRolFuncionalidad);
		builder.append(", fechaActualizacion=");
		builder.append(getFechaActualizacion());
		builder.append(", fechaRegistro=");
		builder.append(getFechaRegistro());
		builder.append(", usuarioModifico=");
		builder.append(getUsuarioModifico());
		builder.append(", funcionalidad=");
		builder.append(funcionalidadDTO);
		builder.append(", rol=");
		builder.append(rol);
		builder.append(", activo=");
		builder.append(activo);
		builder.append("]");
		return builder.toString();
	}
}
