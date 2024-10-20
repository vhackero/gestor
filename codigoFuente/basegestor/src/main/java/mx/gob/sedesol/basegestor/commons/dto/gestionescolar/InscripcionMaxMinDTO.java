package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class InscripcionMaxMinDTO {

    private Long idMaxMin;
    private String plan;
    private Long idPlan;
    private String minimo;
    private String maximoRegular;
    
    
    public Long getIdMaxMin() {
		return idMaxMin;
	}
	public void setIdMaxMin(Long idMaxMin) {
		this.idMaxMin = idMaxMin;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public Long getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(Long idPlan) {
		this.idPlan = idPlan;
	}
	public String getMinimo() {
		return minimo;
	}
	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}
	public String getMaximoRegular() {
		return maximoRegular;
	}
	public void setMaximoRegular(String maximoRegular) {
		this.maximoRegular = maximoRegular;
	}
	public String getMaximoIrregular() {
		return maximoIrregular;
	}
	public void setMaximoIrregular(String maximoIrregular) {
		this.maximoIrregular = maximoIrregular;
	}
	private String maximoIrregular;

    
}
