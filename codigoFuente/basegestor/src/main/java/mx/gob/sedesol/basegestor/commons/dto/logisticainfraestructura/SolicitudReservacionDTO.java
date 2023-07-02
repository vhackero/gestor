package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class SolicitudReservacionDTO implements Serializable {

	private static final long serialVersionUID = -1791330024325677070L;
	
	private Integer idSolicitud;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String nombreSolicitud;
	private Long usuarioModifico;
	private Byte privado;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String solicitante;
	@Email(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_EMAIL)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String emailSolicitante;
	/**
	 * @return the idSolicitud
	 */
	public Integer getIdSolicitud() {
		return idSolicitud;
	}
	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return the nombreSolicitud
	 */
	public String getNombreSolicitud() {
		return nombreSolicitud;
	}
	/**
	 * @param nombreSolicitud the nombreSolicitud to set
	 */
	public void setNombreSolicitud(String nombreSolicitud) {
		this.nombreSolicitud = nombreSolicitud;
	}
	/**
	 * @return the usuarioModifico
	 */
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	/**
	 * @param usuarioModifico the usuarioModifico to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	/**
	 * @return the privado
	 */
	public Byte getPrivado() {
		return privado;
	}
	/**
	 * @param privado the privado to set
	 */
	public void setPrivado(Byte privado) {
		this.privado = privado;
	}
	/**
	 * @return the solicitante
	 */
	public String getSolicitante() {
		return solicitante;
	}
	/**
	 * @param solicitante the solicitante to set
	 */
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	/**
	 * @return the emailSolicitante
	 */
	public String getEmailSolicitante() {
		return emailSolicitante;
	}
	/**
	 * @param emailSolicitante the emailSolicitante to set
	 */
	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}


	
}
