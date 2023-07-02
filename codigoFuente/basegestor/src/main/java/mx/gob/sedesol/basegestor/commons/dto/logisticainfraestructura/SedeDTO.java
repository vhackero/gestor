package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;


public class SedeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idSede;
	private boolean activo;
	private String codigoPostal;
	private String colonia;
	private String direccion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String nombre;
	private Integer orden;
	private Long usuarioModifico;
	private List<AreaSedeDTO> catAreasSedes;
	private CatalogoComunDTO catUbicacionTerritorial; //ubicacion
	private OrgGubernamentalDTO organismoGubernamental; //dependencia
	/**
	 * @return the idSede
	 */
	public Integer getIdSede() {
		return idSede;
	}
	/**
	 * @param idSede the idSede to set
	 */
	public void setIdSede(Integer idSede) {
		this.idSede = idSede;
	}
	/**
	 * @return the activo
	 */
	public boolean getActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}
	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	/**
	 * @return the colonia
	 */
	public String getColonia() {
		return colonia;
	}
	/**
	 * @param colonia the colonia to set
	 */
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
	 * @return the catAreasSedes
	 */
	public List<AreaSedeDTO> getCatAreasSedes() {
		return catAreasSedes;
	}
	/**
	 * @param catAreasSedes the catAreasSedes to set
	 */
	public void setCatAreasSedes(List<AreaSedeDTO> catAreasSedes) {
		this.catAreasSedes = catAreasSedes;
	}
	/**
	 * @return the catUbicacionTerritorial
	 */
	public CatalogoComunDTO getCatUbicacionTerritorial() {
		return catUbicacionTerritorial;
	}
	/**
	 * @param catUbicacionTerritorial the catUbicacionTerritorial to set
	 */
	public void setCatUbicacionTerritorial(CatalogoComunDTO catUbicacionTerritorial) {
		this.catUbicacionTerritorial = catUbicacionTerritorial;
	}
	/**
	 * @return the organismoGubernamental
	 */
	public OrgGubernamentalDTO getOrganismoGubernamental() {
		return organismoGubernamental;
	}
	/**
	 * @param organismoGubernamental the organismoGubernamental to set
	 */
	public void setOrganismoGubernamental(OrgGubernamentalDTO organismoGubernamental) {
		this.organismoGubernamental = organismoGubernamental;
	}	
}
