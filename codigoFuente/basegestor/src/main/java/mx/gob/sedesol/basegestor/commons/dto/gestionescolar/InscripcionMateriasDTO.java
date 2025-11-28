package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class InscripcionMateriasDTO {

    private String clavePlan;
	private String clavePrograma;
    private Long idPlan;
    private String nombrePlan;
    private Long idPrograma;
    private String nombreTentativoPrograma;
    private String subestructura;
    private String estructura;
    private String nivelEnsenanza;
    private String division;
    private String tipoPrograma;
    private Long idProgramaAntecedente;
    private Integer periodo;
    private String perfil;
    private Boolean check; 
    private Boolean disabled;
    private Boolean esMateriaSeriada;
    
	public Boolean getDisabled() {
		return disabled;
	}
	
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	public String getClavePlan() {
		return clavePlan;
	}
	public void setClavePlan(String clavePlan) {
		this.clavePlan = clavePlan;
	}
	public String getClavePrograma() {
		return clavePrograma;
	}
	public void setClavePrograma(String clavePrograma) {
		this.clavePrograma = clavePrograma;
	}
	public Long getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(Long idPlan) {
		this.idPlan = idPlan;
	}
	public String getNombrePlan() {
		return nombrePlan;
	}
	public void setNombrePlan(String nombrePlan) {
		this.nombrePlan = nombrePlan;
	}
	public Long getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getNombreTentativoPrograma() {
		return nombreTentativoPrograma;
	}
	public void setNombreTentativoPrograma(String nombreTentativoPrograma) {
		this.nombreTentativoPrograma = nombreTentativoPrograma;
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
	public String getNivelEnsenanza() {
		return nivelEnsenanza;
	}
	public void setNivelEnsenanza(String nivelEnsenanza) {
		this.nivelEnsenanza = nivelEnsenanza;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getTipoPrograma() {
		return tipoPrograma;
	}
	public void setTipoPrograma(String tipoPrograma) {
		this.tipoPrograma = tipoPrograma;
	}
	public Long getIdProgramaAntecedente() {
		return idProgramaAntecedente;
	}
	public void setIdProgramaAntecedente(Long idProgramaAntecedente) {
		this.idProgramaAntecedente = idProgramaAntecedente;
	}
	public Boolean getCheck() {
		return check;
	}
	public void setCheck(Boolean check) {
		this.check = check;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public Boolean getEsMateriaSeriada() {
		return esMateriaSeriada;
	}

	public void setEsMateriaSeriada(Boolean esMateriaSeriada) {
		this.esMateriaSeriada = esMateriaSeriada;
	}

	@Override
	public String toString() {
		return "InscripcionMateriasDTO [clavePlan=" + clavePlan + ", clavePrograma=" + clavePrograma + ", idPlan="
				+ idPlan + ", nombrePlan=" + nombrePlan + ", idPrograma=" + idPrograma + ", nombreTentativoPrograma="
				+ nombreTentativoPrograma + ", subestructura=" + subestructura + ", estructura=" + estructura
				+ ", nivelEnsenanza=" + nivelEnsenanza + ", division=" + division + ", tipoPrograma=" + tipoPrograma
				+ ", idProgramaAntecedente=" + idProgramaAntecedente + ", periodo=" + periodo + ", perfil=" + perfil
				+ ", check=" + check + ", disabled=" + disabled + "]";
	}
	
}
