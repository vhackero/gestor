package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class TipoDiscapacidadDTO extends ComunDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer idTipoDiscapacidad;
	private Integer idDiscapacidad;
	private String tipoDiscapacidad;
	private String descripcion;
	
	
	
	public Integer getIdTipoDiscapacidad() {
		return idTipoDiscapacidad;
	}
	public void setIdTipoDiscapacidad(Integer idTipoDiscapacidad) {
		this.idTipoDiscapacidad = idTipoDiscapacidad;
	}
	public Integer getIdDiscapacidad() {
		return idDiscapacidad;
	}
	public void setIdDiscapacidad(Integer idDiscapacidad) {
		this.idDiscapacidad = idDiscapacidad;
	}
	public String getTipoDiscapacidad() {
		return tipoDiscapacidad;
	}
	public void setTipoDiscapacidad(String tipoDiscapacidad) {
		this.tipoDiscapacidad = tipoDiscapacidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
