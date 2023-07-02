package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_plan_aptitudes database table.
 * 
 */
@Entity
@Table(name="rel_plan_aptitudes")
@IdClass(RelPlanAptitudPK.class)
public class RelPlanAptitud implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="id_plan")
	private Integer idPlan;

	@Id
	@Column(name="id_aptitud")
	private Integer idAptitud;

	private Integer activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatAptitudesPlan
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_aptitud",insertable=false, updatable=false)
	private CatAptitudesPlan catAptitudesPlan;

	//bi-directional many-to-one association to TblPlan
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_plan",insertable=false, updatable=false)
	private TblPlan tblPlan;

	public RelPlanAptitud() {
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

	public CatAptitudesPlan getCatAptitudesPlan() {
		return this.catAptitudesPlan;
	}

	public void setCatAptitudesPlan(CatAptitudesPlan catAptitudesPlan) {
		this.catAptitudesPlan = catAptitudesPlan;
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