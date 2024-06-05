package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

public class RelEvaluacionCalificacion2DTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idEvalCalificacion;

	private Double calificacion;

	private Integer idDictamen;
	
	private Integer idGpoEvaluacion;
	
	private Integer idGpoParticipante;
	
	private Date fechaRegistro;

	private Long usuarioModifico;

	public RelEvaluacionCalificacion2DTO() {
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
