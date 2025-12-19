package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class InscripcionMateriasReprobadasDTO {
	private Long intentosReprobados;
	private Long idPlan;
	private Long idPrograma;
	private String clavePrograma;
	private String estructura;
	private String subestructura;
	private Long idProgramaAntecedente;
	private String nombrePrograma;
	private String tipoPrograma;

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

	public String getClavePrograma() {
		return clavePrograma;
	}

	public void setClavePrograma(String clavePrograma) {
		this.clavePrograma = clavePrograma;
	}

	public String getEstructura() {
		return estructura;
	}

	public void setEstructura(String estructura) {
		this.estructura = estructura;
	}

	public String getSubestructura() {
		return subestructura;
	}

	public void setSubestructura(String subestructura) {
		this.subestructura = subestructura;
	}

	public Long getIdProgramaAntecedente() {
		return idProgramaAntecedente;
	}

	public void setIdProgramaAntecedente(Long idProgramaAntecedente) {
		this.idProgramaAntecedente = idProgramaAntecedente;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public String getTipoPrograma() {
		return tipoPrograma;
	}

	public void setTipoPrograma(String tipoPrograma) {
		this.tipoPrograma = tipoPrograma;
	}
}
