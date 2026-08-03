package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.ArrayList;
import java.util.List;

public class TiraMateriaDTO {
	
	
	private int id_grupo;
	private String semestre;
	private String bloque;
	private String grupo;
	private String docente;
	private String asesor;
	private String clave;
	private List<String> estructurasCurriculares = new ArrayList<String>();

	
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
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getDocente() {
		return docente;
	}
	public void setDocente(String docente) {
		this.docente = docente;
	}
	public String getAsesor() {
		return asesor;
	}
	public void setAsesor(String asesor) {
		this.asesor = asesor;
	}
	public int getId_grupo() {
		return id_grupo;
	}
	public void setId_grupo(int id_grupo) {
		this.id_grupo = id_grupo;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public List<String> getEstructurasCurriculares() {
		return estructurasCurriculares;
	}
	public void setEstructurasCurriculares(List<String> estructurasCurriculares) {
		this.estructurasCurriculares = estructurasCurriculares;
	}

}
