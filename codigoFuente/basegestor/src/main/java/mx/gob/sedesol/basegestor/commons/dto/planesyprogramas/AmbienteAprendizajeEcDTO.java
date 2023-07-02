package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;


public class AmbienteAprendizajeEcDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer activo;
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String nombre;
	private Integer orden;
	private Integer idCatgTipoPlataforma;
	private Long usuarioModifico;
	
	public Integer getId() {
		return id;
	}
	public Integer getActivo() {
		return activo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public String getNombre() {
		return nombre;
	}
	public Integer getOrden() {
		return orden;
	}
	public Integer getIdCatgTipoPlataforma() {
		return idCatgTipoPlataforma;
	}
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	public void setIdCatgTipoPlataforma(Integer idCatgTipoPlataforma) {
		this.idCatgTipoPlataforma = idCatgTipoPlataforma;
	}
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}


}
