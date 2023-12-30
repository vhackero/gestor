package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class DiscapacidadDTO extends ComunDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idDiscapacidad;
	private String discapacidad;
	private String descripcion;
	private TipoDiscapacidadDTO tipoDiscapacidad;
	
	public DiscapacidadDTO() {
		setFechaRegistro(new Date());
	}
	
	public DiscapacidadDTO(Long usuarioModifico) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.tipoDiscapacidad = new TipoDiscapacidadDTO();
	}
	
	public String getIdDiscapacidad() {
		return idDiscapacidad;
	}
	public void setIdDiscapacidad(String idDiscapacidad) {
		this.idDiscapacidad = idDiscapacidad;
	}
	public String getDiscapacidad() {
		return discapacidad;
	}
	public void setDiscapacidad(String discapacidad) {
		this.discapacidad = discapacidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoDiscapacidadDTO getTipoDiscapacidad() {
		return tipoDiscapacidad;
	}

	public void setTipoDiscapacidad(TipoDiscapacidadDTO tipoDiscapacidad) {
		this.tipoDiscapacidad = tipoDiscapacidad;
	}
	
	
}
