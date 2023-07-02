package mx.gob.sedesol.basegestor.commons.dto.ws;

import java.io.Serializable;

public class Usuario implements Serializable {

	private static final long serialVersionUID = -6142202924917601162L;
	
	private String idUsuario;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String telefono;
	private String extensionDeTelefono;
	private String curp;
	private String correoElectronico;
	private String cargoPuesto;
	private String idOrdenDeGobierno;
	private String ordenDeGobierno;
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
	private String status;
	private ArrayOfPerfil perfiles;
	private ArrayOfElemento elementos;
	
	public Usuario() {
		this.perfiles = new ArrayOfPerfil();
		this.elementos = new ArrayOfElemento();
	}
	
	public String getIdUsuario() {
		return idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getExtensionDeTelefono() {
		return extensionDeTelefono;
	}
	public String getCurp() {
		return curp;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public String getCargoPuesto() {
		return cargoPuesto;
	}
	public String getIdOrdenDeGobierno() {
		return idOrdenDeGobierno;
	}
	public String getOrdenDeGobierno() {
		return ordenDeGobierno;
	}
	public String getIdEntidadFederativa() {
		return idEntidadFederativa;
	}
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public String getIdMunicipio() {
		return idMunicipio;
	}
	public String getMunicipio() {
		return municipio;
	}
	public String getIdDependencia() {
		return idDependencia;
	}
	public String getDependencia() {
		return dependencia;
	}
	public String getIdUnidadAdministrativa() {
		return idUnidadAdministrativa;
	}
	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setExtensionDeTelefono(String extensionDeTelefono) {
		this.extensionDeTelefono = extensionDeTelefono;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public void setCargoPuesto(String cargoPuesto) {
		this.cargoPuesto = cargoPuesto;
	}
	public void setIdOrdenDeGobierno(String idOrdenDeGobierno) {
		this.idOrdenDeGobierno = idOrdenDeGobierno;
	}
	public void setOrdenDeGobierno(String ordenDeGobierno) {
		this.ordenDeGobierno = ordenDeGobierno;
	}
	public void setIdEntidadFederativa(String idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public void setIdDependencia(String idDependencia) {
		this.idDependencia = idDependencia;
	}
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	public void setIdUnidadAdministrativa(String idUnidadAdministrativa) {
		this.idUnidadAdministrativa = idUnidadAdministrativa;
	}
	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	public ArrayOfPerfil getPerfiles() {
		return perfiles;
	}
	public void setPerfiles(ArrayOfPerfil perfiles) {
		this.perfiles = perfiles;
	}

	/**
	 * @return the claveDependencia
	 */
	public String getClaveDependencia() {
		return claveDependencia;
	}

	/**
	 * @param claveDependencia the claveDependencia to set
	 */
	public void setClaveDependencia(String claveDependencia) {
		this.claveDependencia = claveDependencia;
	}

	/**
	 * @return the claveUnidadAdministrativa
	 */
	public String getClaveUnidadAdministrativa() {
		return claveUnidadAdministrativa;
	}

	/**
	 * @param claveUnidadAdministrativa the claveUnidadAdministrativa to set
	 */
	public void setClaveUnidadAdministrativa(String claveUnidadAdministrativa) {
		this.claveUnidadAdministrativa = claveUnidadAdministrativa;
	}

	/**
	 * @return the registradoPor
	 */
	public String getRegistradoPor() {
		return registradoPor;
	}

	/**
	 * @param registradoPor the registradoPor to set
	 */
	public void setRegistradoPor(String registradoPor) {
		this.registradoPor = registradoPor;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the elementos
	 */
	public ArrayOfElemento getElementos() {
		return elementos;
	}

	/**
	 * @param elementos the elementos to set
	 */
	public void setElementos(ArrayOfElemento elementos) {
		this.elementos = elementos;
	}
	

}
