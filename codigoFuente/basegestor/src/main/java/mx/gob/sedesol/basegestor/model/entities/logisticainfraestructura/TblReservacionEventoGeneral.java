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

/**
 * The persistent class for the tbl_reservacion_evento_general database table.
 * 
 */
@Entity
@Table(name = "tbl_reservacion_evento_general")
@NamedQuery(name = "TblReservacionEventoGeneral.findAll", query = "SELECT t FROM TblReservacionEventoGeneral t")
public class TblReservacionEventoGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reservacion_eg")
	private Integer idReservacionEg;

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
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name = "id_area_sede")
	private Integer idAreaSede;

	// @Column(name="id_estatus_reservacion")
	// private Integer idEstatusReservacion;

	// bi-directional many-to-one association to TblEventosGenerale
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus_reservacion")
	private CatEstatusReservacion catEstatusReservacion;

	@Column(name = "id_personalizacion_area")
	private Integer idPersonalizacionArea;

	@Column(name = "usuario_registro")
	private Long usuarioRegistro;

	// bi-directional many-to-one association to TblEventosGenerale
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_evento_general")
	private TblEventoGeneral tblEventoGeneral;

	public TblReservacionEventoGeneral() {
	}

	/**
	 * @return the idReservacionEg
	 */
	public Integer getIdReservacionEg() {
		return idReservacionEg;
	}

	/**
	 * @param idReservacionEg
	 *            the idReservacionEg to set
	 */
	public void setIdReservacionEg(Integer idReservacionEg) {
		this.idReservacionEg = idReservacionEg;
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
	 * @return the tblEventoGeneral
	 */
	public TblEventoGeneral getTblEventoGeneral() {
		return tblEventoGeneral;
	}

	/**
	 * @param tblEventoGeneral
	 *            the tblEventoGeneral to set
	 */
	public void setTblEventoGeneral(TblEventoGeneral tblEventoGeneral) {
		this.tblEventoGeneral = tblEventoGeneral;
	}

	public CatEstatusReservacion getCatEstatusReservacion() {
		return catEstatusReservacion;
	}

	public void setCatEstatusReservacion(CatEstatusReservacion catEstatusReservacion) {
		this.catEstatusReservacion = catEstatusReservacion;
	}

}