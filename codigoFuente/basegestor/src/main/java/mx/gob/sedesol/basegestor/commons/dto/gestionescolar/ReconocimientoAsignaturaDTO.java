package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class ReconocimientoAsignaturaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long idEvento;
	private Long idPersonaParticipante;
	private Long idGrupo;
	private Long idUsuarioMoodle;
	private Long idGrupoMoodle;
	private Integer idCursoMoodle;
	private Integer idPlataformaMoodle;
	private Integer idModalidad;
	private Long idPlan;
	private String plan;
	private Long idPrograma;
	private String programa;
	private String clavePrograma;
	private String semestre;
	private String bloque;
	private String periodo;
	private String matricula;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public Long getIdPersonaParticipante() {
		return idPersonaParticipante;
	}

	public void setIdPersonaParticipante(Long idPersonaParticipante) {
		this.idPersonaParticipante = idPersonaParticipante;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Long getIdUsuarioMoodle() {
		return idUsuarioMoodle;
	}

	public void setIdUsuarioMoodle(Long idUsuarioMoodle) {
		this.idUsuarioMoodle = idUsuarioMoodle;
	}

	public Long getIdGrupoMoodle() {
		return idGrupoMoodle;
	}

	public void setIdGrupoMoodle(Long idGrupoMoodle) {
		this.idGrupoMoodle = idGrupoMoodle;
	}

	public Integer getIdCursoMoodle() {
		return idCursoMoodle;
	}

	public void setIdCursoMoodle(Integer idCursoMoodle) {
		this.idCursoMoodle = idCursoMoodle;
	}

	public Integer getIdPlataformaMoodle() {
		return idPlataformaMoodle;
	}

	public void setIdPlataformaMoodle(Integer idPlataformaMoodle) {
		this.idPlataformaMoodle = idPlataformaMoodle;
	}

	public Integer getIdModalidad() {
		return idModalidad;
	}

	public void setIdModalidad(Integer idModalidad) {
		this.idModalidad = idModalidad;
	}

	public Long getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Long idPlan) {
		this.idPlan = idPlan;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getClavePrograma() {
		return clavePrograma;
	}

	public void setClavePrograma(String clavePrograma) {
		this.clavePrograma = clavePrograma;
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

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
