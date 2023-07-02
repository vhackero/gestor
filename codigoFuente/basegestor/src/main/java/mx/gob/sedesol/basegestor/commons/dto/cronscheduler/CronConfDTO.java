package mx.gob.sedesol.basegestor.commons.dto.cronscheduler;

import java.io.Serializable;
import java.util.Date;

public class CronConfDTO implements Serializable {

	private static final long serialVersionUID = 8342322960557848227L;

	private Integer idCfgCron;
	private Boolean activo;
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String nombreTarea;
	private String patronEjecucion;
	private Long usuarioModifico;
	private String clave;

	public CronConfDTO() {
	}

	public Integer getIdCfgCron() {
		return this.idCfgCron;
	}

	public void setIdCfgCron(Integer idCfgCron) {
		this.idCfgCron = idCfgCron;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
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

	public String getNombreTarea() {
		return this.nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public String getPatronEjecucion() {
		return this.patronEjecucion;
	}

	public void setPatronEjecucion(String patronEjecucion) {
		this.patronEjecucion = patronEjecucion;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
