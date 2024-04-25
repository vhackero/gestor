package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;


/**
 * The persistent class for the rel_grupo_participante database table.
 * 
 */
@Entity
@Table(name="rel_grupo_participante")
@NamedQuery(name="RelGrupoParticipante.findAll", query="SELECT r FROM RelGrupoParticipante r")
public class RelGrupoParticipante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;


	//bi-directional many-to-one association to TblGrupo
	@ManyToOne
	@JoinColumn(name="id_grupo")
	private TblGrupo grupo;

	//bi-directional many-to-one association to TblPersona
	@ManyToOne
	@JoinColumn(name="id_persona_participante")
	private TblPersona persona;
	
	@Column(name="id_persona_lms")
	private Integer idPersonaLms;
	
	@Column(name="calificacion_total")
	private Double califTotal;
	
	@Column(name="porcentaje_asistencia")
	private Integer	porcentajeAsist;
	
	@Column(name="calificacion_final")
	private Double califFinal;

	public RelGrupoParticipante() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public TblGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(TblGrupo grupo) {
		this.grupo = grupo;
	}

	public TblPersona getPersona() {
		return persona;
	}

	public void setPersona(TblPersona persona) {
		this.persona = persona;
	}

	public Integer getIdPersonaLms() {
		return idPersonaLms;
	}

	public void setIdPersonaLms(Integer idPersonaLms) {
		this.idPersonaLms = idPersonaLms;
	}
	/**
	 * @return the califTotal
	 */
	public Double getCalifTotal() {
		return califTotal;
	}
	/**
	 * @param califTotal the califTotal to set
	 */
	public void setCalifTotal(Double califTotal) {
		this.califTotal = califTotal;
	}
	/**
	 * @return the porcentajeAsist
	 */
	public Integer getPorcentajeAsist() {
		return porcentajeAsist;
	}
	/**
	 * @param porcentajeAsist the porcentajeAsist to set
	 */
	public void setPorcentajeAsist(Integer porcentajeAsist) {
		this.porcentajeAsist = porcentajeAsist;
	}
	/**
	 * @return the califFinal
	 */
	public Double getCalifFinal() {
		return califFinal;
	}
	/**
	 * @param califFinal the califFinal to set
	 */
	public void setCalifFinal(Double califFinal) {
		this.califFinal = califFinal;
	}

}