package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the rel_evaluacion_calificacion database table.
 * 
 */
@Entity
@Table(name="rel_evaluacion_calificacion")
public class RelEvaluacionCalificacion2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_eval_calificacion")
	private Integer idEvalCalificacion;

	@Column(name="calificacion")
	private Double calificacion;

	@Column(name="id_dictamen")
	private Integer idDictamen;
	
	@Column(name="id_gpo_evaluacion")
	private Integer idGpoEvaluacion;
	
	@Column(name="id_gpo_participante")
	private Integer idGpoParticipante;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	public RelEvaluacionCalificacion2() {
	}

	public Integer getIdEvalCalificacion() {
		return idEvalCalificacion;
	}

	public void setIdEvalCalificacion(Integer idEvalCalificacion) {
		this.idEvalCalificacion = idEvalCalificacion;
	}

	public Double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}

	public Integer getIdDictamen() {
		return idDictamen;
	}

	public void setIdDictamen(Integer idDictamen) {
		this.idDictamen = idDictamen;
	}

	public Integer getIdGpoEvaluacion() {
		return idGpoEvaluacion;
	}

	public void setIdGpoEvaluacion(Integer idGpoEvaluacion) {
		this.idGpoEvaluacion = idGpoEvaluacion;
	}

	public Integer getIdGpoParticipante() {
		return idGpoParticipante;
	}

	public void setIdGpoParticipante(Integer idGpoParticipante) {
		this.idGpoParticipante = idGpoParticipante;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	
	

}
