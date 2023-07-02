package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class PlantillaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPlantilla;

	@Length(max = 60)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombre;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String parrafo1;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String parrafo2;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String parrafo3;

	private Boolean activo;
	private Integer tipoDocumento;

	private String imagenFondo;

	private Long usuarioCreo;
	private Long usuarioModifico;
	private Date fechaCreacion;
	private Date fechaActualizacion;

	public PlantillaDTO() {
		this.fechaCreacion = new Date();
		this.fechaActualizacion = new Date();
		this.activo = false;
	}

	public PlantillaDTO(Long usuario) {
		this.fechaCreacion = new Date();
		this.fechaActualizacion = new Date();
		this.usuarioCreo = usuario;
		this.usuarioModifico = usuario;
		this.activo = false;
	}

	/**
	 * @return the idPlantilla
	 */
	public Long getIdPlantilla() {
		return idPlantilla;
	}

	/**
	 * @param idPlantilla
	 *            the idPlantilla to set
	 */
	public void setIdPlantilla(Long idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion
	 *            the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion
	 *            the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getImagenFondo() {
		return imagenFondo;
	}

	public void setImagenFondo(String imagenFondo) {
		this.imagenFondo = imagenFondo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the usuarioCreo
	 */
	public Long getUsuarioCreo() {
		return usuarioCreo;
	}

	/**
	 * @param usuarioCreo
	 *            the usuarioCreo to set
	 */
	public void setUsuarioCreo(Long usuarioCreo) {
		this.usuarioCreo = usuarioCreo;
	}

	/**
	 * @return the usuarioModifico
	 */
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	/**
	 * @param usuarioModifico
	 *            the usuarioModifico to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Boolean getActivo() {
		return activo;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getParrafo1() {
		return parrafo1;
	}

	public String getParrafo2() {
		return parrafo2;
	}

	public String getParrafo3() {
		return parrafo3;
	}

	public void setParrafo1(String parrafo1) {
		this.parrafo1 = parrafo1;
	}

	public void setParrafo2(String parrafo2) {
		this.parrafo2 = parrafo2;
	}

	public void setParrafo3(String parrafo3) {
		this.parrafo3 = parrafo3;
	}

}
