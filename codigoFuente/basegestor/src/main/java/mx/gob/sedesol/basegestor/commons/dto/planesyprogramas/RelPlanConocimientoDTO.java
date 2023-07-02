package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;


import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RelPlanConocimientoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPlan;
	private Integer idAreaConocimiento;
	private Integer activo;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private CatalogoComunDTO catAreasConocimiento;
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
	 * @return the catAreasConocimiento
	 */
	public CatalogoComunDTO getCatAreasConocimiento() {
		return catAreasConocimiento;
	}
	/**
	 * @param catAreasConocimiento the catAreasConocimiento to set
	 */
	public void setCatAreasConocimiento(CatalogoComunDTO catAreasConocimiento) {
		this.catAreasConocimiento = catAreasConocimiento;
		this.idAreaConocimiento = this.catAreasConocimiento.getId();
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
