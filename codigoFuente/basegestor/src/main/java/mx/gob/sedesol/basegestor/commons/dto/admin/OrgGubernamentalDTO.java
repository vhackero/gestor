package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class OrgGubernamentalDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Integer id;
	private Integer activo;
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private OrgGubernamentalDTO orgGubPadre;
	private Integer idTipoOrganismo;
	
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombre;
	private Integer orden;
	private Long usuarioModifico;
	private List<OrgGubernamentalDTO> lstHijosOrgGub;
        
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
	 * @return the orgGubPadre
	 */
	public OrgGubernamentalDTO getOrgGubPadre() {
		return orgGubPadre;
	}
	/**
	 * @param orgGubPadre the orgGubPadre to set
	 */
	public void setOrgGubPadre(OrgGubernamentalDTO orgGubPadre) {
		this.orgGubPadre = orgGubPadre;
	}
	/**
	 * @return the idTipoOrganismo
	 */
	public Integer getIdTipoOrganismo() {
		return idTipoOrganismo;
	}
	/**
	 * @param idTipoOrganismo the idTipoOrganismo to set
	 */
	public void setIdTipoOrganismo(Integer idTipoOrganismo) {
		this.idTipoOrganismo = idTipoOrganismo;
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
	 * @return the lstHijosOrgGub
	 */
	public List<OrgGubernamentalDTO> getLstHijosOrgGub() {
		return lstHijosOrgGub;
	}
	/**
	 * @param lstHijosOrgGub the lstHijosOrgGub to set
	 */
	public void setLstHijosOrgGub(List<OrgGubernamentalDTO> lstHijosOrgGub) {
		this.lstHijosOrgGub = lstHijosOrgGub;
	}

}
