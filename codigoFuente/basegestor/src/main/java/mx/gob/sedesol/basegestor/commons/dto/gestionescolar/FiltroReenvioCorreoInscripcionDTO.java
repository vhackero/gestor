package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class FiltroReenvioCorreoInscripcionDTO {
	private String matricula;
	private Long idConvocatoria;
	private Long idTipoProceso;
	private Long idProcesoInscripcion;
	private Long idPlan;
	private Long idPrograma;
	private Long estatusEnvio;

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

	public Long getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Long idPlan) {
		this.idPlan = idPlan;
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Long getEstatusEnvio() {
		return estatusEnvio;
	}

	public void setEstatusEnvio(Long estatusEnvio) {
		this.estatusEnvio = estatusEnvio;
	}

	@Override
	public String toString() {
		return "FiltroReenvioCorreoInscripcionDTO [matricula=" + matricula + ", idConvocatoria=" + idConvocatoria
				+ ", idTipoProceso=" + idTipoProceso + ", idProcesoInscripcion=" + idProcesoInscripcion + ", idPlan="
				+ idPlan + ", idPrograma=" + idPrograma + ", estatusEnvio=" + estatusEnvio + "]";
	}

}
