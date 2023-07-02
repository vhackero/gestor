package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

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

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelPersonaResponsabilidades;

@Entity
@Table(name="rel_responsable_produccion_oa")

@NamedQuery(name="RelReponsableProduccionOa.findAll", query="SELECT r FROM RelReponsableProduccionOa r")
public class RelReponsableProduccionOa implements Serializable {
	private static final long serialVersionUID = 1L;
		

	@Id
	@Column(name="id_rel_responsable_produccion_oa")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idRelResponsableProduccionOa;
	
	@Column(name="id_responsable_produccion")
	private Integer idResponsableProduccion;
	
	@Column(name="id_unidad_oa")
	private Integer idUnidadOa;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to RelPersonaResponsabilidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_responsable_produccion", updatable = false, insertable = false)
	private RelPersonaResponsabilidades personaResponsabilidade;
	
	//bi-directional many-to-one association to RelUnidadOaAva
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_unidad_oa",updatable = false, insertable = false)
	private RelUnidadOaAva unidadOaAva;

	public RelReponsableProduccionOa() {
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

	


	public RelPersonaResponsabilidades getPersonaResponsabilidade() {
		return personaResponsabilidade;
	}


	public void setPersonaResponsabilidade(RelPersonaResponsabilidades personaResponsabilidade) {
		this.personaResponsabilidade = personaResponsabilidade;
	}


	public Integer getIdResponsableProduccion() {
		return idResponsableProduccion;
	}


	public void setIdResponsableProduccion(Integer idResponsableProduccion) {
		this.idResponsableProduccion = idResponsableProduccion;
	}


	public Integer getIdUnidadOa() {
		return idUnidadOa;
	}


	public void setIdUnidadOa(Integer idUnidadOa) {
		this.idUnidadOa = idUnidadOa;
	}


	public RelUnidadOaAva getUnidadOaAva() {
		return unidadOaAva;
	}


	public void setUnidadOaAva(RelUnidadOaAva unidadOaAva) {
		this.unidadOaAva = unidadOaAva;
	}


	public Integer getIdRelResponsableProduccionOa() {
		return idRelResponsableProduccionOa;
	}


	public void setIdRelResponsableProduccionOa(Integer idRelResponsableProduccionOa) {
		this.idRelResponsableProduccionOa = idRelResponsableProduccionOa;
	}

	
	
}