package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class PersonaCargaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idPersona;
	
	private String tipoUsuario;
	private String usuario;
	private String contrasenia;
	private String programa;
	private String curp;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String fechaNacimiento;
	private String institucion;
	private String correo;
	private String telefono;
	private String celular;
	private String sede;
	private String municipio;
	private String orden;
	private String puesto;
	private Date fechaNacimientoDate;
	private Integer idEntidadFederativa;
	private String idMunicipio;
	private String contraseniaEncriptada;
	private String mensajeResultado;
	
	private boolean correcto;
	
	private boolean tipoUsuarioCorrecto;
	private boolean usuarioCorrecto;
	private boolean contraseniaCorrecto;
	private boolean programaCorrecto;
	private boolean curpCorrecto;
	private boolean nombreCorrecto;
	private boolean apellidoPaternoCorrecto;
	private boolean apellidoMaternoCorrecto;
	private boolean fechaNacimientoCorrecto;
	private boolean institucionCorrecto;
	private boolean correoCorrecto;
	private boolean telefonoCorrecto;
	private boolean celularCorrecto;
	private boolean sedeCorrecto;
	private boolean municipioCorrecto;
	private boolean ordenCorrecto;
	private boolean puestoCorrecto;
	
	public PersonaCargaDTO() {
		this.correcto = true;
		
		this.tipoUsuarioCorrecto = true;
		this.usuarioCorrecto = true;
		this.contraseniaCorrecto = true;
		this.programaCorrecto = true;
		this.curpCorrecto = true;
		this.nombreCorrecto = true;
		this.apellidoPaternoCorrecto = true;
		this.apellidoMaternoCorrecto = true;
		this.fechaNacimientoCorrecto = true;
		this.institucionCorrecto = true;
		this.correoCorrecto = true;
		this.telefonoCorrecto = true;
		this.celularCorrecto = true;
		this.sedeCorrecto = true;
		this.municipioCorrecto = true;
		this.ordenCorrecto = true;
		this.puestoCorrecto = true;
	}
	
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		if (ObjectUtils.isNullOrEmpty(programa)) {
			this.programa = null;
		} else {
			this.programa = programa.toUpperCase();
		}
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
	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		if (ObjectUtils.isNullOrEmpty(sede)) {
			this.sede = null;
		} else {
			this.sede = sede.toUpperCase();
		}
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		if (ObjectUtils.isNullOrEmpty(municipio)) {
			this.municipio = null;
		} else {
			this.municipio = municipio.toUpperCase();
		}
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		if (ObjectUtils.isNullOrEmpty(orden)) {
			this.orden = null;
		} else {
			this.orden = orden.toUpperCase();
		}
	}
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public boolean isCorrecto() {
		return correcto;
	}
	public void setCorrecto(boolean correcto) {
		this.correcto = correcto;
	}
	public boolean isProgramaCorrecto() {
		return programaCorrecto;
	}
	public void setProgramaCorrecto(boolean programaCorrecto) {
		this.programaCorrecto = programaCorrecto;
	}
	public boolean isCurpCorrecto() {
		return curpCorrecto;
	}
	public void setCurpCorrecto(boolean curpCorrecto) {
		this.curpCorrecto = curpCorrecto;
	}
	public boolean isNombreCorrecto() {
		return nombreCorrecto;
	}
	public void setNombreCorrecto(boolean nombreCorrecto) {
		this.nombreCorrecto = nombreCorrecto;
	}
	public boolean isApellidoPaternoCorrecto() {
		return apellidoPaternoCorrecto;
	}
	public void setApellidoPaternoCorrecto(boolean apellidoPaternoCorrecto) {
		this.apellidoPaternoCorrecto = apellidoPaternoCorrecto;
	}
	public boolean isApellidoMaternoCorrecto() {
		return apellidoMaternoCorrecto;
	}
	public void setApellidoMaternoCorrecto(boolean apellidoMaternoCorrecto) {
		this.apellidoMaternoCorrecto = apellidoMaternoCorrecto;
	}
	public boolean isInstitucionCorrecto() {
		return institucionCorrecto;
	}
	public void setInstitucionCorrecto(boolean institucionCorrecto) {
		this.institucionCorrecto = institucionCorrecto;
	}
	public boolean isCorreoCorrecto() {
		return correoCorrecto;
	}
	public void setCorreoCorrecto(boolean correoCorrecto) {
		this.correoCorrecto = correoCorrecto;
	}
	public boolean isTelefonoCorrecto() {
		return telefonoCorrecto;
	}
	public void setTelefonoCorrecto(boolean telefonoCorrecto) {
		this.telefonoCorrecto = telefonoCorrecto;
	}
	public boolean isCelularCorrecto() {
		return celularCorrecto;
	}
	public void setCelularCorrecto(boolean celularCorrecto) {
		this.celularCorrecto = celularCorrecto;
	}
	public boolean isSedeCorrecto() {
		return sedeCorrecto;
	}
	public void setSedeCorrecto(boolean sedeCorrecto) {
		this.sedeCorrecto = sedeCorrecto;
	}
	public boolean isMunicipioCorrecto() {
		return municipioCorrecto;
	}
	public void setMunicipioCorrecto(boolean municipioCorrecto) {
		this.municipioCorrecto = municipioCorrecto;
	}
	public boolean isOrdenCorrecto() {
		return ordenCorrecto;
	}
	public void setOrdenCorrecto(boolean ordenCorrecto) {
		this.ordenCorrecto = ordenCorrecto;
	}
	public boolean isPuestoCorrecto() {
		return puestoCorrecto;
	}
	public void setPuestoCorrecto(boolean puestoCorrecto) {
		this.puestoCorrecto = puestoCorrecto;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public boolean isUsuarioCorrecto() {
		return usuarioCorrecto;
	}
	public void setUsuarioCorrecto(boolean usuarioCorrecto) {
		this.usuarioCorrecto = usuarioCorrecto;
	}
	public boolean isContraseniaCorrecto() {
		return contraseniaCorrecto;
	}
	public void setContraseniaCorrecto(boolean contraseniaCorrecto) {
		this.contraseniaCorrecto = contraseniaCorrecto;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		if (ObjectUtils.isNullOrEmpty(tipoUsuario)) {
			this.tipoUsuario = null;
		} else {
			this.tipoUsuario = tipoUsuario.toUpperCase();
		}
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public boolean isTipoUsuarioCorrecto() {
		return tipoUsuarioCorrecto;
	}
	public void setTipoUsuarioCorrecto(boolean tipoUsuarioCorrecto) {
		this.tipoUsuarioCorrecto = tipoUsuarioCorrecto;
	}
	public boolean isFechaNacimientoCorrecto() {
		return fechaNacimientoCorrecto;
	}
	public void setFechaNacimientoCorrecto(boolean fechaNacimientoCorrecto) {
		this.fechaNacimientoCorrecto = fechaNacimientoCorrecto;
	}
	public Date getFechaNacimientoDate() {
		return fechaNacimientoDate;
	}
	public void setFechaNacimientoDate(Date fechaNacimientoDate) {
		this.fechaNacimientoDate = fechaNacimientoDate;
	}
	public Integer getIdEntidadFederativa() {
		return idEntidadFederativa;
	}
	public void setIdEntidadFederativa(Integer idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}
	public String getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public String getContraseniaEncriptada() {
		return contraseniaEncriptada;
	}
	public void setContraseniaEncriptada(String contraseniaEncriptada) {
		this.contraseniaEncriptada = contraseniaEncriptada;
	}
	public String getMensajeResultado() {
		return mensajeResultado;
	}
	public void setMensajeResultado(String mensajeResultado) {
		this.mensajeResultado = mensajeResultado;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

}
