package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the rel_ejes_competencias database table.
 * 
 */
@Entity
@Table(name = "rel_ejes_competencias")
// @NamedQuery(name="RelEjesCompetencia.findAll", query="SELECT r FROM
// RelEjeCompetencia r")
@IdClass(RelEjeCompetenciaPK.class)
public class RelEjeCompetencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Id
	@Column(name = "id_competencia_especifica")
	private Integer idCompetenciaEspecifica;

	@Id
	@Column(name = "id_malla_curricular")
	private Integer idMallaCurricular;

	@Column(name = "usuario_modifico")
	private Long usuarioModifico;

	// bi-directional many-to-one association to CatAptitudesPlan
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_malla_curricular", insertable = false, updatable = false)
	private TblMallaCurricular tblMallaCurricular;

	// bi-directional many-to-one association to TblPlan
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_competencia_especifica", insertable = false, updatable = false)
	private CatCompetenciaEspecifica catCompetenciaEspecifica;

	public TblMallaCurricular getTblMallaCurricular() {
		return tblMallaCurricular;
	}

	public void setTblMallaCurricular(TblMallaCurricular tblMallaCurricular) {
		this.tblMallaCurricular = tblMallaCurricular;
	}

	public CatCompetenciaEspecifica getCatCompetenciaEspecifica() {
		return catCompetenciaEspecifica;
	}

	public void setCatCompetenciaEspecifica(CatCompetenciaEspecifica catCompetenciaEspecifica) {
		this.catCompetenciaEspecifica = catCompetenciaEspecifica;
	}

	public RelEjeCompetencia() {
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getIdCompetenciaEspecifica() {
		return this.idCompetenciaEspecifica;
	}

	public void setIdCompetenciaEspecifica(Integer idCompetenciaEspecifica) {
		this.idCompetenciaEspecifica = idCompetenciaEspecifica;
	}

	public Integer getIdMallaCurricular() {
		return this.idMallaCurricular;
	}

	public void setIdMallaCurricular(Integer idMallaCurricular) {
		this.idMallaCurricular = idMallaCurricular;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

}