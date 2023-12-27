package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_persona_sige")
@NamedQuery(name = "TblPersonaSige.findAll", query = "SELECT t FROM TblPersonaSige t")
public class TblPersonaSige implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_persona_sige")
	private Long idPersonaSige;

	@Column(name = "matricula_sige")
	private String matricula;
	
	@Column(name = "password_sige")
	private String password;
	
	@Column(name = "nombre_sige")
	private String nombre;

	@Column(name = "apellidop_sige")
	private String apellidoPaterno;
	
	@Column(name = "apellidom_sige")
	private String apellidoMaterno;
	
	@Column(name = "programa_educativo_sige")
	private String programaEducativo;
	
	@Column(name = "division_sige")
	private String division;
	
	@Column(name = "correo_institucional_sige")
	private String correoInstitucional;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento_sige")
	private Date fechaNacimiento;
	
	@Column(name = "curp_sige")
	private String curp;
	
	@Column(name = "nivel_sige")
	private String nivelSige;
	
	@Column(name = "persona_id_sige")
	private int personaIdSige;
	
	@Column(name = "perfil_id_sige")
	private int perfilIdSige;

	public TblPersonaSige() {
	}

	public Long getIdPersonaSige() {
		return idPersonaSige;
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

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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

	public void setIdPersonaSige(Long idPersonaSige) {
		this.idPersonaSige = idPersonaSige;
	}
}
