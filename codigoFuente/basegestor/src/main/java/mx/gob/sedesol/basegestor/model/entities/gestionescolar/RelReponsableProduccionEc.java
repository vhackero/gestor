package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the rel_reponsable_produccion_ec database table.
 * 
 */
@Entity
@Table(name="rel_responsable_produccion_ec")
@IdClass(RelReponsableProduccionEcPK.class)
public class RelReponsableProduccionEc implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_evento_capacitacion")
	private Integer idEvtCapacitacion;

	@Id
	@Column(name="id_responsable_produccion")
	private Integer idReponsableProduccion;;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	@Column(name="es_responsable_principal")
	private Boolean esResponsablePrincipal;
	
	//bi-directional many-to-one association to RelPersonaResponsabilidade
	@ManyToOne
	@JoinColumn(name="id_responsable_produccion", insertable=false, updatable=false)
	private RelPersonaResponsabilidades personaResponsabilidad;

	//bi-directional many-to-one association to TblEvento
	@ManyToOne
	@JoinColumn(name="id_evento_capacitacion",insertable=false, updatable=false)
	private TblEvento eventoCapacitacion;

	public RelReponsableProduccionEc() {
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


	public RelPersonaResponsabilidades getPersonaResponsabilidad() {
		return personaResponsabilidad;
	}


	public void setPersonaResponsabilidad(RelPersonaResponsabilidades personaResponsabilidad) {
		this.personaResponsabilidad = personaResponsabilidad;
	}


	public TblEvento getEventoCapacitacion() {
		return eventoCapacitacion;
	}


	public void setEventoCapacitacion(TblEvento eventoCapacitacion) {
		this.eventoCapacitacion = eventoCapacitacion;
	}


	


	public Integer getIdEvtCapacitacion() {
		return idEvtCapacitacion;
	}


	public void setIdEvtCapacitacion(Integer idEvtCapacitacion) {
		this.idEvtCapacitacion = idEvtCapacitacion;
	}


	public Integer getIdReponsableProduccion() {
		return idReponsableProduccion;
	}


	public void setIdReponsableProduccion(Integer idReponsableProduccion) {
		this.idReponsableProduccion = idReponsableProduccion;
	}


	public Boolean getEsResponsablePrincipal() {
		return esResponsablePrincipal;
	}


	public void setEsResponsablePrincipal(Boolean esResponsablePrincipal) {
		this.esResponsablePrincipal = esResponsablePrincipal;
	}


	
}