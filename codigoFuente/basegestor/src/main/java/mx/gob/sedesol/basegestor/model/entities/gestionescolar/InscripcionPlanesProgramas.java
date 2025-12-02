package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;

public class InscripcionPlanesProgramas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPlan;
	private String nombreivelEnsenanza;
	private String nombrePlan;
	private Integer idPrograma;
	private String nombreBloque;
	private String nombreSemestre;
	
	 @Override
	    public String toString() {
	        return "PlanesProgramas [ nombreNivelEnsenanza=" +  nombreivelEnsenanza 
	                + ", idPlan=" + idPlan 
	                + ", nombrePlan=" + nombrePlan 
	                + ", nombreBloque=" + nombreBloque
	                + ", nombreSemestre=" + nombreSemestre
	                + ", idPrograma=" + idPrograma + "]";
	    }
	
	
	public Integer getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}
	public String getNombreivelEnsenanza() {
		return nombreivelEnsenanza;
	}
	public void setNombreivelEnsenanza(String nombreivelEnsenanza) {
		this.nombreivelEnsenanza = nombreivelEnsenanza;
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
	public String getNombreBloque() {
		return nombreBloque;
	}


	public void setNombreBloque(String nombreBloque) {
		this.nombreBloque = nombreBloque;
	}
	public String getNombreSemestre() {
		return nombreSemestre;
	}


	public void setNombreSemestre(String nombreSemestre) {
		this.nombreSemestre = nombreSemestre;
	}
	private String nombrePrograma;

}
