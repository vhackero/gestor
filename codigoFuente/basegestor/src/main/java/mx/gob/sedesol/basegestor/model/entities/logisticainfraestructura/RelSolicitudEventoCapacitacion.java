package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;


/**
 * The persistent class for the rel_solicitud_evento_capacitacion database table.
 * 
 */
@Entity
@Table(name="rel_solicitud_evento_capacitacion")
@IdClass(RelSolicitudEventoCapacitacionPK.class)
@NamedQuery(name="RelSolicitudEventoCapacitacion.findAll", query="SELECT r FROM RelSolicitudEventoCapacitacion r")
public class RelSolicitudEventoCapacitacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_solicitud")
	private Integer idSolicitud;

	@Id
	@Column(name="id_reservacion_ec")
	private Integer idReservacionEC;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblSolicitudReservacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_solicitud", insertable=false, updatable=false)
	private TblSolicitudReservacion tblSolicitudReservacion;

	//bi-directional many-to-one association to tblReservacionEventoCapacitacion
	@ManyToOne(fetch=FetchType.LAZY )
	@JoinColumn(name="id_reservacion_ec", insertable=false, updatable=false )
	private TblReservacionEventoCapacitacion tblReservacionEventoCapacitacion;

	public RelSolicitudEventoCapacitacion() {
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public TblSolicitudReservacion getTblSolicitudReservacion() {
		return this.tblSolicitudReservacion;
	}

	public void setTblSolicitudReservacion(TblSolicitudReservacion tblSolicitudReservacion) {
		this.tblSolicitudReservacion = tblSolicitudReservacion;
	}

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
	public TblReservacionEventoCapacitacion getTblReservacionEventoCapacitacion() {
		return tblReservacionEventoCapacitacion;
	}

	/**
	 * @param tblReservacionEventoCapacitacion the tblReservacionEventoCapacitacion to set
	 */
	public void setTblReservacionEventoCapacitacion(TblReservacionEventoCapacitacion tblReservacionEventoCapacitacion) {
		this.tblReservacionEventoCapacitacion = tblReservacionEventoCapacitacion;
	}

}