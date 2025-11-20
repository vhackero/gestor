package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class InscripcionMaxMinDTO {
	private Integer idMaxMin;
	private String plan;
	private Integer idPlan;
	private String minProgramasPorPeriodo;
	private String maxProgramasRegulares;
	private String maxProgramasIrregulares;
	private Integer programasPorPeriodo;

	public InscripcionMaxMinDTO() {
	}

	public Integer getIdMaxMin() {
		return idMaxMin;
	}

	public void setIdMaxMin(Integer idMaxMin) {
		this.idMaxMin = idMaxMin;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Integer getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}

	public String getMinProgramasPorPeriodo() {
		return minProgramasPorPeriodo;
	}

	public void setMinProgramasPorPeriodo(String minProgramasPorPeriodo) {
		this.minProgramasPorPeriodo = minProgramasPorPeriodo;
	}

	public String getMaxProgramasRegulares() {
		return maxProgramasRegulares;
	}

	public void setMaxProgramasRegulares(String maxProgramasRegulares) {
		this.maxProgramasRegulares = maxProgramasRegulares;
	}

	public String getMaxProgramasIrregulares() {
		return maxProgramasIrregulares;
	}

	public void setMaxProgramasIrregulares(String maxProgramasIrregulares) {
		this.maxProgramasIrregulares = maxProgramasIrregulares;
	}

	public Integer getProgramasPorPeriodo() {
		return programasPorPeriodo;
	}

	public void setProgramasPorPeriodo(Integer programasPorPeriodo) {
		this.programasPorPeriodo = programasPorPeriodo;
	}
}
