package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class TblInscripcionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;


	private int id;
	private int alta;
	private String asignatura;
	private String divisionSige;
	private String groupbase;
	private int idevento;
	private int idpersonaSIGIE;
	private int idplan;
	private int idprograma;
	private String nivelSige;
	private int nuevoingreso;
	private String profileFieldPerfilUnadm;
	private String programa;
	private int recursamiento;
	private int semestre;

	public TblInscripcionDTO() {
	}

	@Override
	public String toString() {
		return "TblInscripcionDTO [id=" + id + ", alta=" + alta + ", asignatura=" + asignatura + ", divisionSige="
				+ divisionSige + ", groupbase=" + groupbase + ", idevento=" + idevento + ", idpersonaSIGIE="
				+ idpersonaSIGIE + ", idplan=" + idplan + ", idprograma=" + idprograma + ", nivelSige=" + nivelSige
				+ ", nuevoingreso=" + nuevoingreso + ", profileFieldPerfilUnadm=" + profileFieldPerfilUnadm
				+ ", programa=" + programa + ", recursamiento=" + recursamiento + ", semestre=" + semestre + "]";
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAlta() {
		return this.alta;
	}

	public void setAlta(int alta) {
		this.alta = alta;
	}

	public String getAsignatura() {
		return this.asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getDivisionSige() {
		return this.divisionSige;
	}

	public void setDivisionSige(String divisionSige) {
		this.divisionSige = divisionSige;
	}

	public String getGroupbase() {
		return this.groupbase;
	}

	public void setGroupbase(String groupbase) {
		this.groupbase = groupbase;
	}

	public int getIdevento() {
		return this.idevento;
	}

	public void setIdevento(int idevento) {
		this.idevento = idevento;
	}

	public int getIdpersonaSIGIE() {
		return this.idpersonaSIGIE;
	}

	public void setIdpersonaSIGIE(int idpersonaSIGIE) {
		this.idpersonaSIGIE = idpersonaSIGIE;
	}

	public int getIdplan() {
		return this.idplan;
	}

	public void setIdplan(int idplan) {
		this.idplan = idplan;
	}

	public int getIdprograma() {
		return this.idprograma;
	}

	public void setIdprograma(int idprograma) {
		this.idprograma = idprograma;
	}

	public String getNivelSige() {
		return this.nivelSige;
	}

	public void setNivelSige(String nivelSige) {
		this.nivelSige = nivelSige;
	}

	public int getNuevoingreso() {
		return this.nuevoingreso;
	}

	public void setNuevoingreso(int nuevoingreso) {
		this.nuevoingreso = nuevoingreso;
	}

	public String getProfileFieldPerfilUnadm() {
		return this.profileFieldPerfilUnadm;
	}

	public void setProfileFieldPerfilUnadm(String profileFieldPerfilUnadm) {
		this.profileFieldPerfilUnadm = profileFieldPerfilUnadm;
	}

	public String getPrograma() {
		return this.programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public int getRecursamiento() {
		return this.recursamiento;
	}

	public void setRecursamiento(int recursamiento) {
		this.recursamiento = recursamiento;
	}

	public int getSemestre() {
		return this.semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

}