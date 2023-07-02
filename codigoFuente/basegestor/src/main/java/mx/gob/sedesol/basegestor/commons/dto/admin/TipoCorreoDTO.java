package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class TipoCorreoDTO extends ComunDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer idTipoCorreo;
	private String descripcion;
	private Integer activo;
	
	/**
	 * @return the idTipoCorreo
	 */
	public Integer getIdTipoCorreo() {
		return idTipoCorreo;
	}
	/**
	 * @param idTipoCorreo the idTipoCorreo to set
	 */
	public void setIdTipoCorreo(Integer idTipoCorreo) {
		this.idTipoCorreo = idTipoCorreo;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
}
