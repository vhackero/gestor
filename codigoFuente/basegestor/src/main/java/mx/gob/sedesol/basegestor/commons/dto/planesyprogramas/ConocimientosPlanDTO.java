package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

public class ConocimientosPlanDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idConocimientoPlan;
	private String nombre;
	private String descripcion;
	private Integer activo;
	private Integer orden;
	private Date fechaActualizacion;
	private Date fechaRegistro;	
	private Long usuarioModifico;
	
	
	public Integer getIdConocimientoPlan() {
		return idConocimientoPlan;
	}
	public void setIdConocimientoPlan(Integer idConocimientoPlan) {
		this.idConocimientoPlan = idConocimientoPlan;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getActivo() {
		return activo;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
			
}
