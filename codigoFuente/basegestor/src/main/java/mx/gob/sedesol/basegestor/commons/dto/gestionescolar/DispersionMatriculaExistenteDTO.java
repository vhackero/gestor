package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class DispersionMatriculaExistenteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idDispersion;
	private Integer idProcesoInscripcionMatricular;
	private Integer idPrograma;
	private Integer idPlan;
	private String plan;
	private String programa;
	private String clave;
	private String semestre;
	private String bloque;
	private Integer noEstudiantesInscritos;
	
	public Integer getIdDispersion() {
		return idDispersion;
	}
	public void setIdDispersion(Integer idDispersion) {
		this.idDispersion = idDispersion;
	}
	public Integer getIdProcesoInscripcionMatricular() {
		return idProcesoInscripcionMatricular;
	}
	public void setIdProcesoInscripcionMatricular(Integer idProcesoInscripcionMatricular) {
		this.idProcesoInscripcionMatricular = idProcesoInscripcionMatricular;
	}
	public Integer getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	public Integer getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
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
	public Integer getNoEstudiantesInscritos() {
		return noEstudiantesInscritos;
	}
	public void setNoEstudiantesInscritos(Integer noEstudiantesInscritos) {
		this.noEstudiantesInscritos = noEstudiantesInscritos;
	}
	
}
