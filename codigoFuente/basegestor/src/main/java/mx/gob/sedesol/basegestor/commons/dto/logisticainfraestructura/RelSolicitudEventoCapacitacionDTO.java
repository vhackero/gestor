package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

public class RelSolicitudEventoCapacitacionDTO implements Serializable {

	private static final long serialVersionUID = 8525893138078253463L;
	
	private Integer idSolicitud;
	private Integer idReservacionEC;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private SolicitudReservacionDTO tblSolicitudReservacion;
	private ReservacionEventoCapacitacionDTO tblReservacionEventoCapacitacion;
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
	 * @return the idReservacionEC
	 */
	public Integer getIdReservacionEC() {
		return idReservacionEC;
	}
	/**
	 * @param idReservacionEC the idReservacionEC to set
	 */
	public void setIdReservacionEC(Integer idReservacionEC) {
		this.idReservacionEC = idReservacionEC;
	}
	/**
	 * @return the tblReservacionEventoCapacitacion
	 */
	public ReservacionEventoCapacitacionDTO getTblReservacionEventoCapacitacion() {
		return tblReservacionEventoCapacitacion;
	}
	/**
	 * @param tblReservacionEventoCapacitacion the tblReservacionEventoCapacitacion to set
	 */
	public void setTblReservacionEventoCapacitacion(ReservacionEventoCapacitacionDTO tblReservacionEventoCapacitacion) {
		this.tblReservacionEventoCapacitacion = tblReservacionEventoCapacitacion;
	}
	public boolean isAprobado() {
		return aprobado;
	}
	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}


}
