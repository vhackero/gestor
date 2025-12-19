package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class InscripcionMateriasCursadasDTO {

	private String plan;
	private Long idPlan;
	private String programa;
	private Long idPrograma;
	private String clavePrograma;
	private String subestructura;
	private String estructura;
	private Double calificacionFinal;
	private Integer estatusAprobacion;
	private Integer creditos;
	private String tipoPrograma;

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

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getSubestructura() {
		return subestructura;
	}

	public void setSubestructura(String subestructura) {
		this.subestructura = subestructura;
	}

	public String getEstructura() {
		return estructura;
	}

	public void setEstructura(String estructura) {
		this.estructura = estructura;
	}

	public Double getCalificacionFinal() {
		return calificacionFinal;
	}

	public void setCalificacionFinal(Double calificacionFinal) {
		this.calificacionFinal = calificacionFinal;
	}

	public Integer getEstatusAprobacion() {
		return estatusAprobacion;
	}

	public void setEstatusAprobacion(Integer estatusAprobacion) {
		this.estatusAprobacion = estatusAprobacion;
	}

	public String getClavePrograma() {
		return clavePrograma;
	}

	public void setClavePrograma(String clavePrograma) {
		this.clavePrograma = clavePrograma;
	}

	public Integer getCreditos() {
		return creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	public String getTipoPrograma() {
		return tipoPrograma;
	}

	public void setTipoPrograma(String tipoPrograma) {
		this.tipoPrograma = tipoPrograma;
	}
}
