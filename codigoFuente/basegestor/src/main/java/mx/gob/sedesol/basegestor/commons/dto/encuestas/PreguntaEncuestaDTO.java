package mx.gob.sedesol.basegestor.commons.dto.encuestas;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class PreguntaEncuestaDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private EncuestaDTO encuesta;
	@Size(max=255, message= MensajesSistemaEnum.Constantes.ADMIN_ENCUESTAS_PREGUNTAS_NOMBRE_SIZE)
	private String nombre;
	private String descripcion;
	private Long usuarioModifico;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	private int index;
	private Integer respuesta;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the encuesta
	 */
	public EncuestaDTO getEncuesta() {
		if(encuesta == null)
			encuesta = new EncuestaDTO();
		return encuesta;
	}
	/**
	 * @param encuesta the encuesta to set
	 */
	public void setEncuesta(EncuestaDTO encuesta) {
		this.encuesta = encuesta;
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
	 * @return the respuesta
	 */
	public Integer getRespuesta() {
		return respuesta;
	}
	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(Integer respuesta) {
		this.respuesta = respuesta;
	}

	/**
	 *
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
