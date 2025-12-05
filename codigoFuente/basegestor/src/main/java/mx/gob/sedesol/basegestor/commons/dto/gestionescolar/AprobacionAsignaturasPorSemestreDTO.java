package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class AprobacionAsignaturasPorSemestreDTO {
	private Long idMallaCurricular;
	private String semestre;
	private Long asignaturasObligatoriasPorPrograma;
	private Long asignaturasAprobadas;

	public Long getIdMallaCurricular() {
		return idMallaCurricular;
	}

	public void setIdMallaCurricular(Long idMallaCurricular) {
		this.idMallaCurricular = idMallaCurricular;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public Long getAsignaturasObligatoriasPorPrograma() {
		return asignaturasObligatoriasPorPrograma;
	}

	public void setAsignaturasObligatoriasPorPrograma(Long asignaturasObligatoriasPorPrograma) {
		this.asignaturasObligatoriasPorPrograma = asignaturasObligatoriasPorPrograma;
	}

	public Long getAsignaturasAprobadas() {
		return asignaturasAprobadas;
	}

	public void setAsignaturasAprobadas(Long asignaturasAprobadas) {
		this.asignaturasAprobadas = asignaturasAprobadas;
	}

}
