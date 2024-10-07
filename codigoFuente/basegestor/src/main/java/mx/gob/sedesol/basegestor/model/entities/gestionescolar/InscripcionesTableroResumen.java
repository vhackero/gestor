package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.math.BigInteger;

public class InscripcionesTableroResumen implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String plan;
	private String idPrograma;
	private String programa;
	private String clave;
	private String semestre;
	private String bloque;
	private String noEstudiantesInscritos;
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(String idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getSemestre() {
		return semestre;
	}
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	public String getBloque() {
		return bloque;
	}
	public void setBloque(String bloque) {
		this.bloque = bloque;
	}
	public String getNoEstudiantesInscritos() {
		return noEstudiantesInscritos;
	}
	public void setNoEstudiantesInscritos(String noEstudiantesInscritos) {
		this.noEstudiantesInscritos = noEstudiantesInscritos;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
	
	
	
	

}
