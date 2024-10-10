package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;

public class ConvocatoriaNivelEducativoCompl implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idNivelEnsenanza;
	private String nombreivelEnsenanza;
	private Integer idPlan;
	private String nombrePlan;
	private Integer idPrograma;
	private String nombrePrograma;
	
	 @Override
	    public String toString() {
	        return "ConvocatoriaNivelEducativoCompl [idNivelEnsenanza=" + idNivelEnsenanza 
	                + ", nombreNivelEnsenanza=" + nombreivelEnsenanza 
	                + ", idPlan=" + idPlan 
	                + ", nombrePlan=" + nombrePlan 
	                + ", idPrograma=" + idPrograma 
	                + ", nombrePrograma=" + nombrePrograma + "]";
	    }
	 
	public Integer getIdNivelEnsenanza() {
		return idNivelEnsenanza;
	}
	public void setIdNivelEnsenanza(Integer idNivelEnsenanza) {
		this.idNivelEnsenanza = idNivelEnsenanza;
	}
	public String getNombreivelEnsenanza() {
		return nombreivelEnsenanza;
	}
	public void setNombreivelEnsenanza(String nombreivelEnsenanza) {
		this.nombreivelEnsenanza = nombreivelEnsenanza;
	}
	public Integer getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}
	public String getNombrePlan() {
		return nombrePlan;
	}
	public void setNombrePlan(String nombrePlan) {
		this.nombrePlan = nombrePlan;
	}
	public Integer getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getNombrePrograma() {
		return nombrePrograma;
	}
	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}
	
	

}
