package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the tbl_persona database table.
 * 
 */
@Entity
@Table(name = "tbl_persona")
@NamedQuery(name = "TblPersonaReporteUsuario.findAll", query = "SELECT t FROM TblPersonaReporteUsuario t")
public class TblPersonaReporteUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_persona")
	private Long idPersona;

	@Column(name = "activo")
	private Boolean activo;

	@Column(name = "sso_apellidoMaterno")
	private String apellidoMaterno;

	@Column(name = "sso_apellidoPaterno")
	private String apellidoPaterno;

	@Column(name = "sso_curp")
	private String curp;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name = "genero")
	private String genero;

	@Column(name = "sso_nombre")
	private String nombre;

	@Column(name = "tipo_usuario")
	private Integer tipoUsuario;

	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RelPersonaRolReporteUsuario> relPersonaRoles;

	@OneToMany(mappedBy = "persona", fetch = FetchType.EAGER)
	private List<RelUsuarioDatosLaboralesReporteUsuario> datosLaborales;
	
	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TblDomiciliosPersonaReporteUsuario> domiciliosPersona;

	/* Campos para tablas de INSSOFT */
	@Column(name = "sso_idEntidadFederativa")
	private String idEntidadFederativa;

	@Column(name = "sso_entidadFederativa")
	private String entidadFederativa;

	@Column(name = "sso_idMunicipio")
	private String idMunicipio;

	@Column(name = "sso_municipio")
	private String municipio;

	@Column(name = "sso_idDependencia")
	private String idDependencia;

	@Column(name = "sso_claveDependencia")
	private String claveDependencia;

	@Column(name = "sso_dependencia")
	private String dependencia;

	@Column(name = "sso_idUnidadAdministrativa")
	private String idUnidadAdministrativa;

	@Column(name = "sso_claveUnidadAdministrativa")
	private String claveUnidadAdministrativa;

	@Column(name = "sso_unidadAdministrativa")
	private String unidadAdministrativa;

	public TblPersonaReporteUsuario() {
	}

	public String getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

	public void setIdEntidadFederativa(String idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public String getEntidadFederativa() {
		return entidadFederativa;
	}

	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getIdDependencia() {
		return idDependencia;
	}

	public void setIdDependencia(String idDependencia) {
		this.idDependencia = idDependencia;
	}

	public String getClaveDependencia() {
		return claveDependencia;
	}

	public void setClaveDependencia(String claveDependencia) {
		this.claveDependencia = claveDependencia;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getIdUnidadAdministrativa() {
		return idUnidadAdministrativa;
	}

	public void setIdUnidadAdministrativa(String idUnidadAdministrativa) {
		this.idUnidadAdministrativa = idUnidadAdministrativa;
	}

	public String getClaveUnidadAdministrativa() {
		return claveUnidadAdministrativa;
	}

	public void setClaveUnidadAdministrativa(String claveUnidadAdministrativa) {
		this.claveUnidadAdministrativa = claveUnidadAdministrativa;
	}

	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	public Long getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getApellidoMaterno() {
		return this.apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getApellidoPaterno() {
		return this.apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<RelPersonaRolReporteUsuario> getRelPersonaRoles() {
		return this.relPersonaRoles;
	}

	public void setRelPersonaRoles(List<RelPersonaRolReporteUsuario> relPersonaRoles) {
		this.relPersonaRoles = relPersonaRoles;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<RelUsuarioDatosLaboralesReporteUsuario> getDatosLaborales() {
		return datosLaborales;
	}

	public void setDatosLaborales(List<RelUsuarioDatosLaboralesReporteUsuario> datosLaborales) {
		this.datosLaborales = datosLaborales;
	}

	public List<TblDomiciliosPersonaReporteUsuario> getDomiciliosPersona() {
		return domiciliosPersona;
	}

	public void setDomiciliosPersona(List<TblDomiciliosPersonaReporteUsuario> domiciliosPersona) {
		this.domiciliosPersona = domiciliosPersona;
	}

}