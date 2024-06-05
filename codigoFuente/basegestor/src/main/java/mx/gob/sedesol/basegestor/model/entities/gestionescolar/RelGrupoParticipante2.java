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

/**
 * The persistent class for the rel_grupo_participante database table.
 * 
 */
@Entity
@Table(name="rel_grupo_participante")
public class RelGrupoParticipante2 implements Serializable {
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

	@Column(name="id_grupo")
	private Integer idGrupo;

	@Column(name="id_persona_participante")
	private Integer idPersonaParticipante;
	
	@Column(name="id_persona_lms")
	private Integer idPersonaLms;
	
	@Column(name="calificacion_total")
	private Double califTotal;
	
	@Column(name="porcentaje_asistencia")
	private Integer	porcentajeAsist;
	
	@Column(name="calificacion_final")
	private Double califFinal;

	public RelGrupoParticipante2() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdPersonaParticipante() {
		return idPersonaParticipante;
	}

	public void setIdPersonaParticipante(Integer idPersonaParticipante) {
		this.idPersonaParticipante = idPersonaParticipante;
	}

	public Integer getIdPersonaLms() {
		return idPersonaLms;
	}

	public void setIdPersonaLms(Integer idPersonaLms) {
		this.idPersonaLms = idPersonaLms;
	}

	public Double getCalifTotal() {
		return califTotal;
	}

	public void setCalifTotal(Double califTotal) {
		this.califTotal = califTotal;
	}

	public Integer getPorcentajeAsist() {
		return porcentajeAsist;
	}

	public void setPorcentajeAsist(Integer porcentajeAsist) {
		this.porcentajeAsist = porcentajeAsist;
	}

	public Double getCalifFinal() {
		return califFinal;
	}

	public void setCalifFinal(Double califFinal) {
		this.califFinal = califFinal;
	}
	
	

}
