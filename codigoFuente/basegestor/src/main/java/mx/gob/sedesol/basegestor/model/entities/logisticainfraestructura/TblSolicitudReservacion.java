package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the tbl_solicitud_reservacion database table.
 * 
 */
@Entity
@Table(name="tbl_solicitud_reservacion")
@NamedQuery(name="TblSolicitudReservacion.findAll", query="SELECT t FROM TblSolicitudReservacion t")
public class TblSolicitudReservacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_solicitud")
	private Integer idSolicitud;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="nombre_solicitud")
	private String nombreSolicitud;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	@Column(name="privado")
	private Byte privado;
	
	@Column(name="solicitante")
	private String solicitante;
	
	@Column(name="email_solicitante")
	private String emailSolicitante;

	public TblSolicitudReservacion() {
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