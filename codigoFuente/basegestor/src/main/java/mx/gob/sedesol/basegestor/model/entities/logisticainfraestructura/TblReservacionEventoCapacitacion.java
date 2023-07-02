package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;

/**
 * The persistent class for the tbl_reservacion_evento_capacitacion database
 * table.
 * 
 */
@Entity
@Table(name = "tbl_reservacion_evento_capacitacion")
@NamedQuery(name = "TblReservacionEventoCapacitacion.findAll", query = "SELECT t FROM TblReservacionEventoCapacitacion t")
public class TblReservacionEventoCapacitacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reservacion_ec")
	private Integer idReservacionEc;

	private Byte activo;

	@Column(name = "evento_privado")
	private Byte eventoPrivado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_final_reservacion")
	private Date fechaFinalReservacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_inicial_reservacion")
	private Date fechaInicialReservacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro")
	private Date fechaRegistro;

	@Column(name = "id_area_sede")
	private Integer idAreaSede;

	// @Column(name="id_estatus_reservacion")
	// private Integer idEstatusReservacion;

	// bi-directional many-to-one association to TblEvento
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus_reservacion")
	private CatEstatusReservacion catEstatusReservacion;

	@Column(name = "id_personalizacion_area")
	private Integer idPersonalizacionArea;

	@Column(name = "usuario_registro")
	private Long usuarioRegistro;

	// bi-directional many-to-one association to TblEvento
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_evento_capacitacion")
	private TblEvento tblEvento;

	public TblReservacionEventoCapacitacion() {
	}

	/**
	 * @return the idReservacionEc
	 */
	public Integer getIdReservacionEc() {
		return idReservacionEc;
	}

	/**
	 * @param idReservacionEc
	 *            the idReservacionEc to set
	 */
	public void setIdReservacionEc(Integer idReservacionEc) {
		this.idReservacionEc = idReservacionEc;
	}

	/**
	 * @return the activo
	 */
	public Byte getActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setActivo(Byte activo) {
		this.activo = activo;
	}

	/**
	 * @return the eventoPrivado
	 */
	public Byte getEventoPrivado() {
		return eventoPrivado;
	}

	/**
	 * @param eventoPrivado
	 *            the eventoPrivado to set
	 */
	public void setEventoPrivado(Byte eventoPrivado) {
		this.eventoPrivado = eventoPrivado;
	}

	/**
	 * @return the fechaFinalReservacion
	 */
	public Date getFechaFinalReservacion() {
		return fechaFinalReservacion;
	}

	/**
	 * @param fechaFinalReservacion
	 *            the fechaFinalReservacion to set
	 */
	public void setFechaFinalReservacion(Date fechaFinalReservacion) {
		this.fechaFinalReservacion = fechaFinalReservacion;
	}

	/**
	 * @return the fechaInicialReservacion
	 */
	public Date getFechaInicialReservacion() {
		return fechaInicialReservacion;
	}

	/**
	 * @param fechaInicialReservacion
	 *            the fechaInicialReservacion to set
	 */
	public void setFechaInicialReservacion(Date fechaInicialReservacion) {
		this.fechaInicialReservacion = fechaInicialReservacion;
	}

	/**
	 * @return the fechaModificacion
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	/**
	 * @param fechaModificacion
	 *            the fechaModificacion to set
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the idAreaSede
	 */
	public Integer getIdAreaSede() {
		return idAreaSede;
	}

	/**
	 * @param idAreaSede
	 *            the idAreaSede to set
	 */
	public void setIdAreaSede(Integer idAreaSede) {
		this.idAreaSede = idAreaSede;
	}

	/**
	 * @return the idPersonalizacionArea
	 */
	public Integer getIdPersonalizacionArea() {
		return idPersonalizacionArea;
	}

	/**
	 * @param idPersonalizacionArea
	 *            the idPersonalizacionArea to set
	 */
	public void setIdPersonalizacionArea(Integer idPersonalizacionArea) {
		this.idPersonalizacionArea = idPersonalizacionArea;
	}

	/**
	 * @return the usuarioRegistro
	 */
	public Long getUsuarioRegistro() {
		return usuarioRegistro;
	}

	/**
	 * @param usuarioRegistro
	 *            the usuarioRegistro to set
	 */
	public void setUsuarioRegistro(Long usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	/**
	 * @return the tblEvento
	 */
	public TblEvento getTblEvento() {
		return tblEvento;
	}

	/**
	 * @param tblEvento
	 *            the tblEvento to set
	 */
	public void setTblEvento(TblEvento tblEvento) {
		this.tblEvento = tblEvento;
	}

	public CatEstatusReservacion getCatEstatusReservacion() {
		return catEstatusReservacion;
	}

	public void setCatEstatusReservacion(CatEstatusReservacion catEstatusReservacion) {
		this.catEstatusReservacion = catEstatusReservacion;
	}

}