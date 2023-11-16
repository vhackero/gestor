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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

/**
 * The persistent class for the tbl_persona database table.
 * 
 */
@Entity
@Table(name = "tbl_persona")
@NamedQuery(name = "TblPersona.findAll", query = "SELECT t FROM TblPersona t")
public class TblPersona implements Serializable {
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

	private String contrasenia;

	@Column(name = "sso_curp")
	private String curp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

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

	@Column(name = "rfc")
	private String rfc;

	@Column(name = "ruta_firma")
	private String rutaFirma;

	@Column(name = "ruta_foto")
	private String rutaFoto;

	@Column(name = "sso_idUsuario")
	private String usuario;

	@Column(name = "usuario_modifico")
	private Long usuarioModifico;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nacionalidad")
	private CatPais nacionalidad;

	@Column(name = "tipo_usuario")
	private Integer tipoUsuario;

	// bi-directional many-to-one association to RelPersonaCorreo
	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RelPersonaCorreo> personaCorreos;

	// bi-directional many-to-one association to RelPersonaRole
	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RelPersonaRol> relPersonaRoles;

	// bi-directional many-to-one association to RelPersonaTelefono
	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RelPersonaTelefono> relPersonaTelefonos;

	// bi-directional many-to-one association to TblDomiciliosPersona
	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TblDomiciliosPersona> domiciliosPersona;

	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
	private List<SsoElemento> relPersonaElementos;
	
	@OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
	private List<RelUsuarioDatosLaborales> datosLaborales;

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

	@Column(name = "sso_registradoPor")
	private String registradoPor;

	public TblPersona() {
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

	public String getRegistradoPor() {
		return registradoPor;
	}

	public void setRegistradoPor(String registradoPor) {
		this.registradoPor = registradoPor;
	}

	public void setDomiciliosPersona(List<TblDomiciliosPersona> domiciliosPersona) {
		this.domiciliosPersona = domiciliosPersona;
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

	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		if (ObjectUtils.isNullOrEmpty(curp)) {
			this.curp = null;
		} else {
			this.curp = curp.toUpperCase();
		}
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getRutaFirma() {
		return this.rutaFirma;
	}

	public void setRutaFirma(String rutaFirma) {
		this.rutaFirma = rutaFirma;
	}

	public String getRutaFoto() {
		return this.rutaFoto;
	}

	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<RelPersonaCorreo> getPersonaCorreos() {
		return this.personaCorreos;
	}

	public void setPersonaCorreos(List<RelPersonaCorreo> personaCorreos) {
		this.personaCorreos = personaCorreos;
	}

	public RelPersonaCorreo addPersonaCorreo(RelPersonaCorreo personaCorreos) {
		getPersonaCorreos().add(personaCorreos);
		personaCorreos.setPersona(this);

		return personaCorreos;
	}

	public RelPersonaCorreo removeRelPersonaCorreo(RelPersonaCorreo personaCorreos) {
		getPersonaCorreos().remove(personaCorreos);
		personaCorreos.setPersona(null);

		return personaCorreos;
	}

	public List<RelPersonaRol> getRelPersonaRoles() {
		return this.relPersonaRoles;
	}

	public void setRelPersonaRoles(List<RelPersonaRol> relPersonaRoles) {
		this.relPersonaRoles = relPersonaRoles;
	}

	public RelPersonaRol addRelPersonaRole(RelPersonaRol relPersonaRole) {
		getRelPersonaRoles().add(relPersonaRole);
		relPersonaRole.setPersona(this);

		return relPersonaRole;
	}

	public RelPersonaRol removeRelPersonaRole(RelPersonaRol relPersonaRole) {
		getRelPersonaRoles().remove(relPersonaRole);
		relPersonaRole.setPersona(null);

		return relPersonaRole;
	}

	public SsoElemento addRelPersonaElemento(SsoElemento relPersonaElemento) {
		relPersonaElementos.add(relPersonaElemento);
		relPersonaElemento.setPersona(this);

		return relPersonaElemento;
	}

	public SsoElemento removeRelPersonaElemento(SsoElemento relPersonaElemento) {
		relPersonaElementos.remove(relPersonaElemento);
		relPersonaElemento.setPersona(null);

		return relPersonaElemento;
	}

	public List<RelPersonaTelefono> getRelPersonaTelefonos() {
		return this.relPersonaTelefonos;
	}

	public void setRelPersonaTelefonos(List<RelPersonaTelefono> relPersonaTelefonos) {
		this.relPersonaTelefonos = relPersonaTelefonos;
	}

	public List<TblDomiciliosPersona> getDomiciliosPersona() {
		return this.domiciliosPersona;
	}

	public void setDomiciliosPersonas(List<TblDomiciliosPersona> domiciliosPersona) {
		this.domiciliosPersona = domiciliosPersona;
	}

	public CatPais getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(CatPais nacionalidad) {
		this.nacionalidad = nacionalidad;
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

	public List<SsoElemento> getRelPersonaElementos() {
		return relPersonaElementos;
	}

	public void setRelPersonaElementos(List<SsoElemento> relPersonaElementos) {
		this.relPersonaElementos = relPersonaElementos;
	}

	public List<RelUsuarioDatosLaborales> getDatosLaborales() {
		return datosLaborales;
	}

	public void setDatosLaborales(List<RelUsuarioDatosLaborales> datosLaborales) {
		this.datosLaborales = datosLaborales;
	}

}