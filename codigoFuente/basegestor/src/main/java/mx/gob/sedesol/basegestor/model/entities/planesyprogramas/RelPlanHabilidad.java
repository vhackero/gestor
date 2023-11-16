package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_plan_habilidades database table.
 * 
 */
@Entity
@Table(name="rel_plan_habilidades")
@IdClass(RelPlanHabilidadPK.class)
public class RelPlanHabilidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_habilidad")
	private Integer idHabilidad;

	@Id
	@Column(name="id_plan")
	private Integer idPlan;

	private Integer activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatHabilidadesPlan
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_habilidad", insertable=false, updatable=false)
	private CatHabilidadesPlan catHabilidadesPlan;

	//bi-directional many-to-one association to TblPlan
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_plan", insertable=false, updatable=false)
	private TblPlan tblPlan;

	public RelPlanHabilidad() {
	}

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

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public CatHabilidadesPlan getCatHabilidadesPlan() {
		return this.catHabilidadesPlan;
	}

	public void setCatHabilidadesPlan(CatHabilidadesPlan catHabilidadesPlan) {
		this.catHabilidadesPlan = catHabilidadesPlan;
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