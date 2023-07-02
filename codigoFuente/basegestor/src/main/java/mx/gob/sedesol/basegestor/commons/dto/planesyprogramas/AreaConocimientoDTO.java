package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

public class AreaConocimientoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idAreaConocimiento;
	private Integer activo;
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String nombre;
	private Long usuarioModifico;
	private String nombreUsuarioMod;
	
	/**
	 * @return the idAreaConocimiento
	 */
	public Integer getIdAreaConocimiento() {
		return idAreaConocimiento;
	}
	/**
	 * @param idAreaConocimiento the idAreaConocimiento to set
	 */
	public void setIdAreaConocimiento(Integer idAreaConocimiento) {
		this.idAreaConocimiento = idAreaConocimiento;
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
	 * @return the usuarioModifico
	 */
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	/**
	 * @param usuarioModifico the usuarioModifico to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	/**
	 * @return the nombreUsuarioMod
	 */
	public String getNombreUsuarioMod() {
		return nombreUsuarioMod;
	}
	/**
	 * @param nombreUsuarioMod the nombreUsuarioMod to set
	 */
	public void setNombreUsuarioMod(String nombreUsuarioMod) {
		this.nombreUsuarioMod = nombreUsuarioMod;
	}


	
}
