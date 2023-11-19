package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;

import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the rel_encuesta_evento_capacitacion database table.
 * 
 */
@Entity
@Table(name="rel_encuesta_evento_capacitacion")
@IdClass(RelEncuestaEventoCapacitacionPK.class)
@NamedQuery(name="RelEncuestaEventoCapacitacion.findAll", query="SELECT r FROM RelEncuestaEventoCapacitacion r")
public class RelEncuestaEventoCapacitacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_evento_capacitacion")
	private Integer idEventoCapacitacion;

	@Id
	@Column(name="id_encuesta")
	private Integer idEncuesta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Column(name="activo")
	private Boolean activo;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblEncuesta
	@ManyToOne
	@JoinColumn(name="id_encuesta", insertable=false, updatable=false)
	private TblEncuesta tblEncuesta;

	//bi-directional many-to-one association to TblEvento
	@ManyToOne
	@JoinColumn(name="id_evento_capacitacion", insertable=false, updatable=false)
	private TblEvento tblEvento;

	public RelEncuestaEventoCapacitacion() {
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

	public TblEncuesta getTblEncuesta() {
		return this.tblEncuesta;
	}

	public void setTblEncuesta(TblEncuesta tblEncuesta) {
		this.tblEncuesta = tblEncuesta;
	}

	public TblEvento getTblEvento() {
		return this.tblEvento;
	}

	public void setTblEvento(TblEvento tblEvento) {
		this.tblEvento = tblEvento;
	}

	/**
	 * @return the idEventoCapacitacion
	 */
	public Integer getIdEventoCapacitacion() {
		return idEventoCapacitacion;
	}

	/**
	 * @param idEventoCapacitacion the idEventoCapacitacion to set
	 */
	public void setIdEventoCapacitacion(Integer idEventoCapacitacion) {
		this.idEventoCapacitacion = idEventoCapacitacion;
	}

	/**
	 * @return the idEncuesta
	 */
	public Integer getIdEncuesta() {
		return idEncuesta;
	}

	/**
	 * @param idEncuesta the idEncuesta to set
	 */
	public void setIdEncuesta(Integer idEncuesta) {
		this.idEncuesta = idEncuesta;
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
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

}