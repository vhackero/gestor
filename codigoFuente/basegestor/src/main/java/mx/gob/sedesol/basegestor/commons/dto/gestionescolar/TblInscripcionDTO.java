package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;

public class TblInscripcionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;


	private Integer id;
	private Integer alta;
	private String asignatura;
	private String divisionSige;
	private String groupbase;
	private EventoCapacitacionDTO evento;
	private PersonaDTO personaSige;
	private PlanDTO plan;
	private FichaDescProgramaDTO fichaDescriptivaPrograma;
	private String nivelSige;
	private Integer nuevoingreso;
	private String profileFieldPerfilUnadm;
	private String programa;
	private Integer recursamiento;
	private Integer semestre;

 
	
	public TblInscripcionDTO() {
	}

	@Override
	public String toString() {
		return "TblInscripcionDTO [id=" + id + ", alta=" + alta + ", asignatura=" + asignatura + ", divisionSige="
				+ divisionSige + ", groupbase=" + groupbase + ", idevento=" + evento + ", idpersonaSIGIE="
				+ personaSige + ", idplan=" + plan + ", idprograma=" + fichaDescriptivaPrograma + ", nivelSige=" + nivelSige
				+ ", nuevoingreso=" + nuevoingreso + ", profileFieldPerfilUnadm=" + profileFieldPerfilUnadm
				+ ", programa=" + programa + ", recursamiento=" + recursamiento + ", semestre=" + semestre + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAlta() {
		return alta;
	}

	public void setAlta(Integer alta) {
		this.alta = alta;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getDivisionSige() {
		return divisionSige;
	}

	public void setDivisionSige(String divisionSige) {
		this.divisionSige = divisionSige;
	}

	public String getGroupbase() {
		return groupbase;
	}

	public void setGroupbase(String groupbase) {
		this.groupbase = groupbase;
	}

	public EventoCapacitacionDTO getEvento() {
		return evento;
	}

	public void setEvento(EventoCapacitacionDTO evento) {
		this.evento = evento;
	}
	
	public PersonaDTO getPersonaSige() {
		return personaSige;
	}

	public void setPersonaSige(PersonaDTO personaSige) {
		this.personaSige = personaSige;
	}

	public PlanDTO getPlan() {
		return plan;
	}

	public void setPlan(PlanDTO plan) {
		this.plan = plan;
	}

	public FichaDescProgramaDTO getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}

	public void setFichaDescriptivaPrograma(FichaDescProgramaDTO fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
	}

	public String getNivelSige() {
		return nivelSige;
	}

	public void setNivelSige(String nivelSige) {
		this.nivelSige = nivelSige;
	}

	public Integer getNuevoingreso() {
		return nuevoingreso;
	}

	public void setNuevoingreso(Integer nuevoingreso) {
		this.nuevoingreso = nuevoingreso;
	}

	public String getProfileFieldPerfilUnadm() {
		return profileFieldPerfilUnadm;
	}

	public void setProfileFieldPerfilUnadm(String profileFieldPerfilUnadm) {
		this.profileFieldPerfilUnadm = profileFieldPerfilUnadm;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public Integer getRecursamiento() {
		return recursamiento;
	}

	public void setRecursamiento(Integer recursamiento) {
		this.recursamiento = recursamiento;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}
}