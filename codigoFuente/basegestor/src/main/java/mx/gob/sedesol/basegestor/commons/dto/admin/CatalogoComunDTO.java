package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.CatEncuestaContextoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class CatalogoComunDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Integer id;
	private Integer activo;
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String nombre;
	private Integer orden;
	private Long usuarioModifico;
	private String nombreUsuarioMod;
	private CatEncuestaContextoDTO contexto;
	
	private Boolean activoBoolean;
        
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
	 * @return the fechaActualizacion	@Override
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
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
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
	 * Metodo que retorna bandera booleana de acuerdo al estatus del catalogo
	 * @return
	 */
	public Boolean getActivoBoolean(){
		
		if(ObjectUtils.isNull(activo))
		{
			activoBoolean = false;
		}
		else
		{
			if(activo.equals(ConstantesGestor.ACTIVO))
				activoBoolean = true;
			else
				activoBoolean = false;
		}
		return activoBoolean;
	}
	
	/**
	 * @param activoBoolean the activoBoolean to set
	 */
	public void setActivoBoolean(Boolean activoBoolean) {
		this.activoBoolean = activoBoolean;
	}
	/**
	 * @return the contexto
	 */
	public CatEncuestaContextoDTO getContexto() {
		return contexto;
	}
	/**
	 * @param contexto the contexto to set
	 */
	public void setContexto(CatEncuestaContextoDTO contexto) {
		this.contexto = contexto;
	}


}
