package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;



import java.io.Serializable;
import javax.persistence.*;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;

import java.util.Date;
import java.util.List;


public class CatModalidadPlanProgramaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer activo;

	private String descripcion;

	private Date fechaActualizacion;

	private Date fechaRegistro;

	private String nombre;

	private Integer orden;

	private Long usuarioModifico;

	private List<EventoCapacitacionDTO> eventosCapacitacion;


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<EventoCapacitacionDTO> getEventosCapacitacion() {
		return eventosCapacitacion;
	}

	public void setEventosCapacitacion(List<EventoCapacitacionDTO> eventosCapacitacion) {
		this.eventosCapacitacion = eventosCapacitacion;
	}
	
	
}