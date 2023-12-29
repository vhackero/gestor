package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;

public class PersonaDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPersona;

	private Integer tipoUsuario;

	private Boolean activo;
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String usuario;

	private String contrasenia;

	private String nuevaContrasenia;

	private String confirmacionContrasenia;

	private String contraseniaEncriptada;
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String curp;
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombre;
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String apellidoPaterno;

	private String apellidoMaterno;

	private String genero;
	@NotNull(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Date fechaNacimiento;

	private PaisDTO nacionalidad;
	private String rfc;
	private String rutaFirma;
	private String rutaFoto;

	private String correoElectronico;

	private String celular;

	private List<PersonaTelefonoDTO> relPersonaTelefonos;

	private List<PersonaRolDTO> relPersonaRoles;
	private String token;
	private List<PersonaCorreoDTO> personaCorreos;

	private List<DomicilioPersonaDTO> domiciliosPersona;
	
	private List<DatoSociodemograficoDTO> datosSociodeograficosPersona;

	private boolean seleccionado;
	private String rutaCompletaFoto;

	// rango de fechas de registro
	private Date fechaInicial;
	private Date fechaFinal;

	/* Campos para tablas de INSSOFT */
	private String idEntidadFederativa;

	private String entidadFederativa;

	private String idMunicipio;

	private String municipio;

	private String idDependencia;

	private String claveDependencia;

	private String dependencia;

	private String idUnidadAdministrativa;

	private String claveUnidadAdministrativa;

	private String unidadAdministrativa;

	private String registradoPor;
	
	private String sso_status;

	private List<SsoElementoDTO> relPersonaElementos;
	
	private List<UsuarioDatosLaboralesDTO> datosLaborales;

	public PersonaDTO() {
		setFechaRegistro(new Date());
		setFechaActualizacion(getFechaRegistro());
		this.setActivo(true);
		this.nacionalidad = new PaisDTO();
		// this.fechaNacimiento = new Date();
	}

	public PersonaDTO(Long usuarioModifico, String idPais) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		setFechaActualizacion(getFechaRegistro());
		this.setActivo(true);
		this.tipoUsuario = TipoUsuarioEnum.INTERNO.getValor();
		this.nacionalidad = new PaisDTO();
		this.nacionalidad.setIdPais(idPais);
		this.rutaFoto = UUID.randomUUID().toString();
		this.rutaFirma = UUID.randomUUID().toString();
		this.fechaNacimiento = new Date();
	}

	public Integer edad() {
		DateFormat y = new SimpleDateFormat("yyyy");
		DateFormat m = new SimpleDateFormat("MM");
		DateFormat d = new SimpleDateFormat("dd");

		LocalDate nacimiento = new LocalDate(Integer.valueOf(y.format(this.fechaNacimiento)),
				Integer.valueOf(m.format(this.fechaNacimiento)), Integer.valueOf(d.format(this.fechaNacimiento)));
		LocalDate ahora = new LocalDate();
		Years edad = Years.yearsBetween(nacimiento, ahora);
		return edad.getYears();
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		if (ObjectUtils.isNullOrEmpty(curp)) {
			this.curp = null;
		} else {
			this.curp = curp.toUpperCase();
		}
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getRutaFirma() {
		return rutaFirma;
	}

	public void setRutaFirma(String rutaFirma) {
		this.rutaFirma = rutaFirma;
	}

	public String getRutaFoto() {
		return rutaFoto;
	}

	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		if (ObjectUtils.isNull(usuario)) {
			this.usuario = null;
		} else {
			this.usuario = usuario.toLowerCase();
		}
	}

	public List<PersonaRolDTO> getRelPersonaRoles() {
		return relPersonaRoles;
	}

	public void setRelPersonaRoles(List<PersonaRolDTO> relPersonaRoles) {
		this.relPersonaRoles = relPersonaRoles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<PersonaCorreoDTO> getPersonaCorreos() {
		return personaCorreos;
	}

	public void setPersonaCorreos(List<PersonaCorreoDTO> personaCorreos) {
		this.personaCorreos = personaCorreos;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public void agregarPersonaCorreos(PersonaCorreoDTO personaCorreo) {

		if (ObjectUtils.isNull(this.personaCorreos)) {
			this.personaCorreos = new ArrayList<>();
		}

		for (PersonaCorreoDTO pc : this.personaCorreos) {
			if (ObjectUtils.isNotNull(personaCorreo.getIdPersonaCorreo())
					&& personaCorreo.getIdPersonaCorreo().equals(pc.getIdPersonaCorreo())) {
				this.personaCorreos.remove(pc);
				break;
			}
		}
		this.personaCorreos.add(personaCorreo);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PersonaDTO [idPersona=");
		builder.append(idPersona);
		builder.append(", activo=");
		builder.append(activo);
		builder.append(", apellidoMaterno=");
		builder.append(apellidoMaterno);
		builder.append(", apellidoPaterno=");
		builder.append(apellidoPaterno);
		builder.append(", contrasenia=");
		builder.append(contrasenia);
		builder.append(", curp=");
		builder.append(curp);
		builder.append(", fechaActualizacion=");
		builder.append(getFechaActualizacion());
		builder.append(", fechaNacimiento=");
		builder.append(fechaNacimiento);
		builder.append(", fechaRegistro=");
		builder.append(getFechaRegistro());
		builder.append(", genero=");
		builder.append(genero);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", rfc=");
		builder.append(rfc);
		builder.append(", rutaFirma=");
		builder.append(rutaFirma);
		builder.append(", rutaFoto=");
		builder.append(rutaFoto);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append(", usuarioModifico=");
		builder.append(getUsuarioModifico());
		builder.append(", relPersonaRoles=");
		builder.append(relPersonaRoles);
		builder.append(", token=");
		builder.append(token);
		builder.append("]");
		return builder.toString();
	}

	public String getNombreCompleto() {
		if (ObjectUtils.isNullOrEmpty(apellidoMaterno)) {
			return this.nombre + " " + this.apellidoPaterno;
		} else {
			return this.nombre + " " + this.apellidoPaterno + " " + this.apellidoMaterno;
		}

	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public PaisDTO getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(PaisDTO nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getConfirmacionContrasenia() {
		return confirmacionContrasenia;
	}

	public void setConfirmacionContrasenia(String confirmacionContrasenia) {
		this.confirmacionContrasenia = confirmacionContrasenia;
	}

	public String getNuevaContrasenia() {
		return nuevaContrasenia;
	}

	public void setNuevaContrasenia(String nuevaContrasenia) {
		this.nuevaContrasenia = nuevaContrasenia;
	}

	public String getContraseniaEncriptada() {
		return contraseniaEncriptada;
	}

	public void setContraseniaEncriptada(String contraseniaEncriptada) {
		this.contraseniaEncriptada = contraseniaEncriptada;
	}

	public String getRutaCompletaFoto() {
		return rutaCompletaFoto;
	}

	public void setRutaCompletaFoto(String rutaCompletaFoto) {
		this.rutaCompletaFoto = rutaCompletaFoto;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public String getCelular() {
		return celular;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public List<PersonaTelefonoDTO> getRelPersonaTelefonos() {
		return relPersonaTelefonos;
	}

	public void setRelPersonaTelefonos(List<PersonaTelefonoDTO> relPersonaTelefonos) {
		this.relPersonaTelefonos = relPersonaTelefonos;
	}

	public List<DomicilioPersonaDTO> getDomiciliosPersona() {
		return domiciliosPersona;
	}

	public void setDomiciliosPersona(List<DomicilioPersonaDTO> domiciliosPersona) {
		this.domiciliosPersona = domiciliosPersona;
	}

	/**
	 * @return the idEntidadFederativa
	 */
	public String getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

	/**
	 * @param idEntidadFederativa
	 *            the idEntidadFederativa to set
	 */
	public void setIdEntidadFederativa(String idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	/**
	 * @return the entidadFederativa
	 */
	public String getEntidadFederativa() {
		return entidadFederativa;
	}

	/**
	 * @param entidadFederativa
	 *            the entidadFederativa to set
	 */
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	/**
	 * @return the idMunicipio
	 */
	public String getIdMunicipio() {
		return idMunicipio;
	}

	/**
	 * @param idMunicipio
	 *            the idMunicipio to set
	 */
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	/**
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio
	 *            the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the idDependencia
	 */
	public String getIdDependencia() {
		return idDependencia;
	}

	/**
	 * @param idDependencia
	 *            the idDependencia to set
	 */
	public void setIdDependencia(String idDependencia) {
		this.idDependencia = idDependencia;
	}

	/**
	 * @return the claveDependencia
	 */
	public String getClaveDependencia() {
		return claveDependencia;
	}

	/**
	 * @param claveDependencia
	 *            the claveDependencia to set
	 */
	public void setClaveDependencia(String claveDependencia) {
		this.claveDependencia = claveDependencia;
	}

	/**
	 * @return the dependencia
	 */
	public String getDependencia() {
		return dependencia;
	}

	/**
	 * @param dependencia
	 *            the dependencia to set
	 */
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	/**
	 * @return the idUnidadAdministrativa
	 */
	public String getIdUnidadAdministrativa() {
		return idUnidadAdministrativa;
	}

	/**
	 * @param idUnidadAdministrativa
	 *            the idUnidadAdministrativa to set
	 */
	public void setIdUnidadAdministrativa(String idUnidadAdministrativa) {
		this.idUnidadAdministrativa = idUnidadAdministrativa;
	}

	/**
	 * @return the claveUnidadAdministrativa
	 */
	public String getClaveUnidadAdministrativa() {
		return claveUnidadAdministrativa;
	}

	/**
	 * @param claveUnidadAdministrativa
	 *            the claveUnidadAdministrativa to set
	 */
	public void setClaveUnidadAdministrativa(String claveUnidadAdministrativa) {
		this.claveUnidadAdministrativa = claveUnidadAdministrativa;
	}

	/**
	 * @return the unidadAdministrativa
	 */
	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	/**
	 * @param unidadAdministrativa
	 *            the unidadAdministrativa to set
	 */
	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	/**
	 * @return the registradoPor
	 */
	public String getRegistradoPor() {
		return registradoPor;
	}

	/**
	 * @param registradoPor
	 *            the registradoPor to set
	 */
	public void setRegistradoPor(String registradoPor) {
		this.registradoPor = registradoPor;
	}

	/**
	 * @return the relPersonaElementos
	 */
	public List<SsoElementoDTO> getRelPersonaElementos() {
		return relPersonaElementos;
	}

	/**
	 * @param relPersonaElementos
	 *            the relPersonaElementos to set
	 */
	public void setRelPersonaElementos(List<SsoElementoDTO> relPersonaElementos) {
		this.relPersonaElementos = relPersonaElementos;
	}

	public List<UsuarioDatosLaboralesDTO> getDatosLaborales() {
		return datosLaborales;
	}

	public void setDatosLaborales(List<UsuarioDatosLaboralesDTO> datosLaborales) {
		this.datosLaborales = datosLaborales;
	}

	public List<DatoSociodemograficoDTO> getDatosSociodeograficosPersona() {
		return datosSociodeograficosPersona;
	}

	public void setDatosSociodeograficosPersona(List<DatoSociodemograficoDTO> datosSociodeograficosPersona) {
		this.datosSociodeograficosPersona = datosSociodeograficosPersona;
	}
	
	public String getSso_status() {
		return sso_status;
	}

	public void setSso_status(String sso_status) {
		this.sso_status = sso_status;
	}

}
