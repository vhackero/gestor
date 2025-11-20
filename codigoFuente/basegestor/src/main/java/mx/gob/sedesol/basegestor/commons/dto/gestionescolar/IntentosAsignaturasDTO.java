package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class IntentosAsignaturasDTO {

    private Long intentosReprobados;
    private Long idPlan;
    private Long idPrograma;
    
	public Long getIntentosReprobados() {
		return intentosReprobados;
	}

	public void setIntentosReprobados(Long intentosReprobados) {
		this.intentosReprobados = intentosReprobados;
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
}
