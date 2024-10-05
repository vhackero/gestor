package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class PersonaSigeDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPersonaSige;

	private String matricula;
	
	private String password;
	
	private String nombre;

	private String apellidoPaterno;

	private String apellidoMaterno;

	private String programaEducativo;

	private String division;

	private String correoInstitucional;

	private Date fechaNacimiento;

	private String curp;

	private String nivelSige;

	private int personaIdSige;

	private int perfilIdSige;
	
	//Objetos necesaris 

	public PersonaSigeDTO() {
		super();
	}

	public Long getIdPersonaSige() {
		return idPersonaSige;
	}

	public void setIdPersonaSige(Long idPersonaSige) {
		this.idPersonaSige = idPersonaSige;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getProgramaEducativo() {
		return programaEducativo;
	}

	public void setProgramaEducativo(String programaEducativo) {
		this.programaEducativo = programaEducativo;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getCorreoInstitucional() {
		return correoInstitucional;
	}

	public void setCorreoInstitucional(String correoInstitucional) {
		this.correoInstitucional = correoInstitucional;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date date) {
		this.fechaNacimiento =  date;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNivelSige() {
		return nivelSige;
	}

	public void setNivelSige(String nivelSige) {
		this.nivelSige = nivelSige;
	}

	public int getPersonaIdSige() {
		return personaIdSige;
	}

	public void setPersonaIdSige(int personaIdSige) {
		this.personaIdSige = personaIdSige;
	}

	public int getPerfilIdSige() {
		return perfilIdSige;
	}

	public void setPerfilIdSige(int perfilIdSige) {
		this.perfilIdSige = perfilIdSige;
	}
}
