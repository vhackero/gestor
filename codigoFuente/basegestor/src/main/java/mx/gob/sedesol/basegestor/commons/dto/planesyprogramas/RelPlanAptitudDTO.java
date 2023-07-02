package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RelPlanAptitudDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPlan;
	private Integer idAptitud;
	private Integer activo;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private CatalogoComunDTO catAptitudesPlan;
	private PlanDTO tblPlan;
	
	/**
	 * @return the idPlan
	 */
	public Integer getIdPlan() {
		return idPlan;
	}
	/**
	 * @param idPlan the idPlan to set
	 */
	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}
	/**
	 * @return the idAptitud
	 */
	public Integer getIdAptitud() {
		return idAptitud;
	}
	/**
	 * @param idAptitud the idAptitud to set
	 */
	public void setIdAptitud(Integer idAptitud) {
		this.idAptitud = idAptitud;
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
	 * @return the catAptitudesPlan
	 */
	public CatalogoComunDTO getCatAptitudesPlan() {
		return catAptitudesPlan;
	}
	/**
	 * @param catAptitudesPlan the catAptitudesPlan to set
	 */
	public void setCatAptitudesPlan(CatalogoComunDTO catAptitudesPlan) {
		this.catAptitudesPlan = catAptitudesPlan;
		this.idAptitud = this.catAptitudesPlan.getId();
	}
	/**
	 * @return the tblPlan
	 */
	public PlanDTO getTblPlan() {
		return tblPlan;
	}
	/**
	 * @param tblPlan the tblPlan to set
	 */
	public void setTblPlan(PlanDTO tblPlan) {
		this.tblPlan = tblPlan;
		this.idPlan = this.tblPlan.getIdPlan();
	}
}
