package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class ActualizacionCursoMoodleEventoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idEvento;
	private Integer idCursoMoodle;
	private Long idPrograma;
	private String eventoCurso;
	private String tipoAsignatura;
	private String semestre;
	private String bloque;

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Integer getIdCursoMoodle() {
		return idCursoMoodle;
	}

	public void setIdCursoMoodle(Integer idCursoMoodle) {
		this.idCursoMoodle = idCursoMoodle;
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getEventoCurso() {
		return eventoCurso;
	}

	public void setEventoCurso(String eventoCurso) {
		this.eventoCurso = eventoCurso;
	}

	public String getTipoAsignatura() {
		return tipoAsignatura;
	}

	public void setTipoAsignatura(String tipoAsignatura) {
		this.tipoAsignatura = tipoAsignatura;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}
}
