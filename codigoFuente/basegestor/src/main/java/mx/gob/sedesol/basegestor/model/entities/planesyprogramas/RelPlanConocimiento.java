package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_plan_conocimientos database table.
 * 
 */
@Entity
@Table(name="rel_plan_conocimientos")
@IdClass(RelPlanConocimientoPK.class)
public class RelPlanConocimiento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_plan")
	private Integer idPlan;

	@Id
	@Column(name="id_area_conocimiento")
	private Integer idAreaConocimiento;

	private Integer activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatAreasConocimiento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_area_conocimiento", insertable=false, updatable=false)
	private CatAreasConocimiento catAreasConocimiento;

	//bi-directional many-to-one association to TblPlan
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_plan", insertable=false, updatable=false)
	private TblPlan tblPlan;
	
	public RelPlanConocimiento() {
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
	public CatAreasConocimiento getCatAreasConocimiento() {
		return catAreasConocimiento;
	}

	/**
	 * @param catAreasConocimiento the catAreasConocimiento to set
	 */
	public void setCatAreasConocimiento(CatAreasConocimiento catAreasConocimiento) {
		this.catAreasConocimiento = catAreasConocimiento;
	}

	/**
	 * @return the tblPlan
	 */
	public TblPlan getTblPlan() {
		return tblPlan;
	}

	/**
	 * @param tblPlan the tblPlan to set
	 */
	public void setTblPlan(TblPlan tblPlan) {
		this.tblPlan = tblPlan;
	}

}