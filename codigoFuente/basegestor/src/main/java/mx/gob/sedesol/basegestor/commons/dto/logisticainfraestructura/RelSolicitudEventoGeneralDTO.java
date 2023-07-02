package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

public class RelSolicitudEventoGeneralDTO implements Serializable {

	private static final long serialVersionUID = 7863958175623353751L;
	
	private Integer idReservacionEG;
	private Integer idSolicitud;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private SolicitudReservacionDTO tblSolicitudReservacion;
	private ReservacionEventoGeneralDTO tblReservacionEventoGeneral;
	private boolean aprobado;

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
	 * @return the tblSolicitudReservacion
	 */
	public SolicitudReservacionDTO getTblSolicitudReservacion() {
		return tblSolicitudReservacion;
	}
	/**
	 * @param tblSolicitudReservacion the tblSolicitudReservacion to set
	 */
	public void setTblSolicitudReservacion(SolicitudReservacionDTO tblSolicitudReservacion) {
		this.tblSolicitudReservacion = tblSolicitudReservacion;
	}
	/**
	 * @return the tblReservacionEventoGeneral
	 */
	public ReservacionEventoGeneralDTO getTblReservacionEventoGeneral() {
		return tblReservacionEventoGeneral;
	}
	/**
	 * @param tblReservacionEventoGeneral the tblReservacionEventoGeneral to set
	 */
	public void setTblReservacionEventoGeneral(ReservacionEventoGeneralDTO tblReservacionEventoGeneral) {
		this.tblReservacionEventoGeneral = tblReservacionEventoGeneral;
	}
	/**
	 * @return the idReservacionEG
	 */
	public Integer getIdReservacionEG() {
		return idReservacionEG;
	}
	/**
	 * @param idReservacionEG the idReservacionEG to set
	 */
	public void setIdReservacionEG(Integer idReservacionEG) {
		this.idReservacionEG = idReservacionEG;
	}
	public boolean isAprobado() {
		return aprobado;
	}
	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}

	
}
