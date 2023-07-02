package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RelPlanHabilidadDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idHabilidad;
	private Integer idPlan;
	private Integer activo;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private CatalogoComunDTO catHabilidadesPlan;
	private PlanDTO tblPlan;
	
	/**
	 * @return the idHabilidad
	 */
	public Integer getIdHabilidad() {
		return idHabilidad;
	}
	/**
	 * @param idHabilidad the idHabilidad to set
	 */
	public void setIdHabilidad(Integer idHabilidad) {
		this.idHabilidad = idHabilidad;
	}
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
	 * @return the catHabilidadesPlan
	 */
	public CatalogoComunDTO getCatHabilidadesPlan() {
		return catHabilidadesPlan;
	}
	/**
	 * @param catHabilidadesPlan the catHabilidadesPlan to set
	 */
	public void setCatHabilidadesPlan(CatalogoComunDTO catHabilidadesPlan) {
		this.catHabilidadesPlan = catHabilidadesPlan;
		this.idHabilidad = this.catHabilidadesPlan.getId();
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
