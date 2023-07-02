package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.util.Date;

public class BitacoraDTO {

	private String idBitacora;

	private String idUsuario;
	private String usuario;
	private String nombreCompleto;
	private String claveModulo;
	private String modulo;
	private String claveComponente;
	private String componente;
	private String claveFuncionalidad;
	private String funcionalidad;
	private String idElementoAfectado;
	private Date fechaRegistro;
	private String ip;
	private String navegador;
	private String tipoServicio;
	
	private Date fechaInicio;
	private Date fechaFin;


	public String getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(String idBitacora) {
		this.idBitacora = idBitacora;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getFuncionalidad() {
		return funcionalidad;
	}

	public void setFuncionalidad(String funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	public String getIdElementoAfectado() {
		return idElementoAfectado;
	}

	public void setIdElementoAfectado(String idElementoAfectado) {
		this.idElementoAfectado = idElementoAfectado;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNavegador() {
		return navegador;
	}

	public void setNavegador(String navegador) {
		this.navegador = navegador;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getComponente() {
		return componente;
	}

	public void setComponente(String componente) {
		this.componente = componente;
	}



	public String getClaveModulo() {
		return claveModulo;
	}

	public void setClaveModulo(String claveModulo) {
		this.claveModulo = claveModulo;
	}

	public String getClaveComponente() {
		return claveComponente;
	}

	public void setClaveComponente(String claveComponente) {
		this.claveComponente = claveComponente;
	}

	public String getClaveFuncionalidad() {
		return claveFuncionalidad;
	}

	public void setClaveFuncionalidad(String claveFuncionalidad) {
		this.claveFuncionalidad = claveFuncionalidad;
	}
	

}
