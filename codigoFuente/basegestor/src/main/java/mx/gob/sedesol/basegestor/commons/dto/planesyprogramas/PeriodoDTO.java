package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;

public class PeriodoDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPeriodo;
	private Date fechaActualizacion;
	private Date fechaRegistro;

	@Length(max=200)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombre;

	private Integer activo;
	private Long usuarioModifica;

	public Long getUsuarioModifica() {
		return usuarioModifica;
	}

	public void setUsuarioModifica(Long usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}

	/**
	 * @return the idPeriodo
	 */
	public Integer getIdPeriodo() {
		return idPeriodo;
	}
	/**
	 * @param idPeriodo the idPeriodo to set
	 */
	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
