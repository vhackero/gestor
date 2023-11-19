package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_evaluacion_calificacion database table.
 * 
 */
@Entity
@Table(name="rel_evaluacion_calificacion")
@NamedQuery(name="RelEvaluacionCalificacion.findAll", query="SELECT r FROM RelEvaluacionCalificacion r")
public class RelEvaluacionCalificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_eval_calificacion")
	private Integer idEvalCalificacion;

	private Double calificacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to RelGrupoEvaluacion
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="id_gpo_evaluacion")
	private RelGrupoEvaluacion relGrupoEvaluacion;

	//bi-directional many-to-one association to RelGrupoParticipante
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_gpo_participante")
	private RelGrupoParticipante relGrupoParticipante;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_dictamen")
	private CatDictamen dictamen;

	public RelEvaluacionCalificacion() {
	}

	public Integer getIdEvalCalificacion() {
		return this.idEvalCalificacion;
	}

	public void setIdEvalCalificacion(Integer idEvalCalificacion) {
		this.idEvalCalificacion = idEvalCalificacion;
	}

	public Double getCalificacion() {
		return this.calificacion;
	}

	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
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

	public RelGrupoEvaluacion getRelGrupoEvaluacion() {
		return this.relGrupoEvaluacion;
	}

	public void setRelGrupoEvaluacion(RelGrupoEvaluacion relGrupoEvaluacion) {
		this.relGrupoEvaluacion = relGrupoEvaluacion;
	}

	public RelGrupoParticipante getRelGrupoParticipante() {
		return this.relGrupoParticipante;
	}

	public void setRelGrupoParticipante(RelGrupoParticipante relGrupoParticipante) {
		this.relGrupoParticipante = relGrupoParticipante;
	}

	/**
	 * @return the dictamen
	 */
	public CatDictamen getDictamen() {
		return dictamen;
	}

	/**
	 * @param dictamen the dictamen to set
	 */
	public void setDictamen(CatDictamen dictamen) {
		this.dictamen = dictamen;
	}

}