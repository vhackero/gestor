package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class TipoAsentamientoDTO extends ComunDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idTipoAsentamiento;
	private String descripcion;
	private Boolean activo;
	
	public Integer getIdTipoAsentamiento() {
		return idTipoAsentamiento;
	}
	public void setIdTipoAsentamiento(Integer idTipoAsentamiento) {
		this.idTipoAsentamiento = idTipoAsentamiento;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
