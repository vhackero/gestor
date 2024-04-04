package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

/**
 * 
 */

public class EncabezadoActaImplDTO implements Serializable{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 6908080688797354787L;
	private String matricula="";
	private String docente="";
	private String programa="";
	private String cveprograma="";
	private String asignatura="";
	private String cveAsignatura="";
	private String periodo="";
	private String grupo="";
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getDocente() {
		return docente;
	}
	public void setDocente(String docente) {
		this.docente = docente;
	}
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	public String getCveprograma() {
		return cveprograma;
	}
	public void setCveprograma(String cveprograma) {
		this.cveprograma = cveprograma;
	}
	public String getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}
	public String getCveAsignatura() {
		return cveAsignatura;
	}
	public void setCveAsignatura(String cveAsignatura) {
		this.cveAsignatura = cveAsignatura;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	
}
