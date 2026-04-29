package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class MallaAlumnoProgramaDTO {

	private Long idPrograma;
	private Double calificacionFinal;
	private Double calificacionMinAprobatoria;

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Double getCalificacionFinal() {
		return calificacionFinal;
	}

	public void setCalificacionFinal(Double calificacionFinal) {
		this.calificacionFinal = calificacionFinal;
	}

	public Double getCalificacionMinAprobatoria() {
		return calificacionMinAprobatoria;
	}

	public void setCalificacionMinAprobatoria(Double calificacionMinAprobatoria) {
		this.calificacionMinAprobatoria = calificacionMinAprobatoria;
	}
}
