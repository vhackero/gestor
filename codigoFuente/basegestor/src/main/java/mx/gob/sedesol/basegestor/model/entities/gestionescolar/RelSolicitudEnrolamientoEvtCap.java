package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the rel_solicitud_enrolamiento_evt_cap database table.
 * 
 */
@Entity
@Table(name="rel_solicitud_enrolamiento_evt_cap")
@IdClass(RelSolicitudEnrolamientoEvtCapPK.class)
@NamedQuery(name="RelSolicitudEnrolamientoEvtCap.findAll", query="SELECT r FROM RelSolicitudEnrolamientoEvtCap r")
public class RelSolicitudEnrolamientoEvtCap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_persona")
	private Long idPersona;

	@Id
	@Column(name="id_evento_capacitacion")
	private Integer idEventoCapacitacion;
	
	private Boolean activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblEvento
	@ManyToOne
	@JoinColumn(name="id_evento_capacitacion", insertable=false, updatable=false)
	private TblEvento tblEvento;

	//bi-directional many-to-one association to TblPersona
	@ManyToOne
	@JoinColumn(name="id_persona", insertable=false, updatable=false)
	private TblPersona tblPersona;

	public RelSolicitudEnrolamientoEvtCap() {
	}

	

	public Long getIdPersona() {
		return idPersona;
	}



	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}



	public Integer getIdEventoCapacitacion() {
		return idEventoCapacitacion;
	}



	public void setIdEventoCapacitacion(Integer idEventoCapacitacion) {
		this.idEventoCapacitacion = idEventoCapacitacion;
	}



	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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

	public TblEvento getTblEvento() {
		return this.tblEvento;
	}

	public void setTblEvento(TblEvento tblEvento) {
		this.tblEvento = tblEvento;
	}

	public TblPersona getTblPersona() {
		return this.tblPersona;
	}

	public void setTblPersona(TblPersona tblPersona) {
		this.tblPersona = tblPersona;
	}

}