package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;


public class RelOrgGubUbicacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idOrgGub;
	private Integer idUbicacion;
	private Integer activo;
	private Date fechaRegistro;
	private Long usuarioRegistro;
	private OrgGubernamentalDTO tblOrganismoGubernamental;
	private CatalogoComunDTO catUbicacionTerritorial;
	
	/**
	 * @return the idOrgGub
	 */
	public Integer getIdOrgGub() {
		return idOrgGub;
	}
	/**
	 * @param idOrgGub the idOrgGub to set
	 */
	public void setIdOrgGub(Integer idOrgGub) {
		this.idOrgGub = idOrgGub;
	}
	/**
	 * @return the idUbicacion
	 */
	public Integer getIdUbicacion() {
		return idUbicacion;
	}
	/**
	 * @param idUbicacion the idUbicacion to set
	 */
	public void setIdUbicacion(Integer idUbicacion) {
		this.idUbicacion = idUbicacion;
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
	 * @return the usuarioRegistro
	 */
	public Long getUsuarioRegistro() {
		return usuarioRegistro;
	}
	/**
	 * @param usuarioRegistro the usuarioRegistro to set
	 */
	public void setUsuarioRegistro(Long usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}
	/**
	 * @return the tblOrganismoGubernamental
	 */
	public OrgGubernamentalDTO getTblOrganismoGubernamental() {
		return tblOrganismoGubernamental;
	}
	/**
	 * @param tblOrganismoGubernamental the tblOrganismoGubernamental to set
	 */
	public void setTblOrganismoGubernamental(OrgGubernamentalDTO tblOrganismoGubernamental) {
		this.tblOrganismoGubernamental = tblOrganismoGubernamental;
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
}
