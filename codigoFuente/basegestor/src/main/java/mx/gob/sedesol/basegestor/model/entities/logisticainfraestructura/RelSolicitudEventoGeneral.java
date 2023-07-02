package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the rel_solicitud_evento_general database table.
 * 
 */
@Entity
@Table(name="rel_solicitud_evento_general")
@IdClass(RelSolicitudEventoGeneralPK.class)
@NamedQuery(name="RelSolicitudEventoGeneral.findAll", query="SELECT r FROM RelSolicitudEventoGeneral r")
public class RelSolicitudEventoGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_solicitud")
	private Integer idSolicitud;

	@Id
	@Column(name="id_reservacion_eg")
	private Integer idReservacionEG;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblSolicitudReservacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_solicitud", insertable=false, updatable=false)
	private TblSolicitudReservacion tblSolicitudReservacion;

	//bi-directional many-to-one association to tblReservacionEventoGeneral
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_reservacion_eg",insertable=false, updatable=false )
	private TblReservacionEventoGeneral tblReservacionEventoGeneral;

	public RelSolicitudEventoGeneral() {
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

	/**
	 * @return the tblReservacionEventoGeneral
	 */
	public TblReservacionEventoGeneral getTblReservacionEventoGeneral() {
		return tblReservacionEventoGeneral;
	}

	/**
	 * @param tblReservacionEventoGeneral the tblReservacionEventoGeneral to set
	 */
	public void setTblReservacionEventoGeneral(TblReservacionEventoGeneral tblReservacionEventoGeneral) {
		this.tblReservacionEventoGeneral = tblReservacionEventoGeneral;
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


}