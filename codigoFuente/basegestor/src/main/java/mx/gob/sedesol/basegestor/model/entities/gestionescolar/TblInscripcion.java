package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;


/**
 * The persistent class for the tbl_inscripciones database table.
 * 
 */
@Entity
@Table(name="tbl_inscripciones")
@NamedQuery(name="TblInscripcion.findAll", query="SELECT t FROM TblInscripcion t")
public class TblInscripcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private Integer alta;

	private String asignatura;

	@Column(name="division_sige")
	private String divisionSige;

	private String groupbase;

	//private Integer idevento;
	//bi-directional many-to-one association to TblEvento
	@ManyToOne
	@JoinColumn(name="idevento",insertable=false, updatable=false)
	private TblEvento eventoCapacitacion;
	 
	//bi-directional many-to-one association to TblEvento
	@ManyToOne
	@JoinColumn(name="IdpersonaSIGIE",insertable=false, updatable=false)
	private TblPersona personaSige;

	// bi-directional many-to-one association to TblPlanes
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idplan")
	private TblPlan plan;

	// bi-directional one-to-one association to TblFichaDescriptivaPrograma
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "idprograma", insertable = false, updatable = false)
	private TblFichaDescriptivaPrograma fichaDescriptivaPrograma;
	
	@Column(name="nivel_sige")
	private String nivelSige;

	private Integer nuevoingreso;

	@Column(name="profile_field_perfil_unadm")
	private String profileFieldPerfilUnadm;

	private String programa;

	private Integer recursamiento;

	private Integer semestre;

	public TblInscripcion() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAlta() {
		return this.alta;
	}

	public void setAlta(Integer alta) {
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

	public TblEvento getEventoCapacitacion() {
		return eventoCapacitacion;
	}

	public void setEventoCapacitacion(TblEvento eventoCapacitacion) {
		this.eventoCapacitacion = eventoCapacitacion;
	}

	public TblPersona getPersonaSige() {
		return personaSige;
	}

	public void setPersonaSige(TblPersona personaSige) {
		this.personaSige = personaSige;
	}

	public TblPlan getPlan() {
		return plan;
	}

	public void setPlan(TblPlan plan) {
		this.plan = plan;
	}
	
	public TblFichaDescriptivaPrograma getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}

	public void setFichaDescriptivaPrograma(TblFichaDescriptivaPrograma fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
	}

	public String getNivelSige() {
		return this.nivelSige;
	}

	public void setNivelSige(String nivelSige) {
		this.nivelSige = nivelSige;
	}

	public Integer getNuevoingreso() {
		return this.nuevoingreso;
	}

	public void setNuevoingreso(Integer nuevoingreso) {
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

	public Integer getRecursamiento() {
		return this.recursamiento;
	}

	public void setRecursamiento(Integer recursamiento) {
		this.recursamiento = recursamiento;
	}

	public Integer getSemestre() {
		return this.semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

}