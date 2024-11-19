package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.time.LocalDateTime;
import java.util.Date;

public class InscripcionInsertDTO {

	private Long idPersona;
	private String programa;
	private String asignatura;
	private String groupBase;
	private Long idPlan;
	private Long idPrograma;
	private Long idEvento;
	private String nivel;
	private String division;
	private String profileFieldPerfil;
	private Integer bloque;
	private String claveAsig;
	private Integer nuevoIngreso;
	private Integer recursamiento;
	private Integer alta;
	private Integer semestre;
	private Date fechaRegistro;

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getGroupBase() {
		return groupBase;
	}

	public void setGroupBase(String groupBase) {
		this.groupBase = groupBase;
	}

	public Long getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Long idPlan) {
		this.idPlan = idPlan;
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getProfileFieldPerfil() {
		return profileFieldPerfil;
	}

	public void setProfileFieldPerfil(String profileFieldPerfil) {
		this.profileFieldPerfil = profileFieldPerfil;
	}

	public Integer getBloque() {
		return bloque;
	}

	public void setBloque(Integer bloque) {
		this.bloque = bloque;
	}

	public String getClaveAsig() {
		return claveAsig;
	}

	public void setClaveAsig(String claveAsig) {
		this.claveAsig = claveAsig;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date date) {
		this.fechaRegistro = date;
	}
	
	public Integer getNuevoIngreso() {
		return nuevoIngreso;
	}

	public void setNuevoIngreso(Integer nuevoIngreso) {
		this.nuevoIngreso = nuevoIngreso;
	}

	public Integer getRecursamiento() {
		return recursamiento;
	}

	public void setRecursamiento(Integer recursamiento) {
		this.recursamiento = recursamiento;
	}

	public Integer getAlta() {
		return alta;
	}

	public void setAlta(Integer alta) {
		this.alta = alta;
	}

}
