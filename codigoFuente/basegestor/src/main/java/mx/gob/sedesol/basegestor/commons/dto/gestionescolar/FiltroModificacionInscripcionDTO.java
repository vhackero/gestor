package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class FiltroModificacionInscripcionDTO {
	private String matricula;
	private Long idConvocatoria;
	private Long idTipoProceso;
	private Long idProcesoInscripcion;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Long getIdConvocatoria() {
		return idConvocatoria;
	}

	public void setIdConvocatoria(Long idConvocatoria) {
		this.idConvocatoria = idConvocatoria;
	}

	public Long getIdTipoProceso() {
		return idTipoProceso;
	}

	public void setIdTipoProceso(Long idTipoProceso) {
		this.idTipoProceso = idTipoProceso;
	}

	public Long getIdProcesoInscripcion() {
		return idProcesoInscripcion;
	}

	public void setIdProcesoInscripcion(Long idProcesoInscripcion) {
		this.idProcesoInscripcion = idProcesoInscripcion;
	}

}
