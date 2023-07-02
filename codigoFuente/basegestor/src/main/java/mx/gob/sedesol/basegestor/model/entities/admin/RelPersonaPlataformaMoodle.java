package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="rel_personas_plataformas_moodle")
@IdClass(RelPersonaPlataformaMoodlePK.class)
public class RelPersonaPlataformaMoodle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_persona")
	private Long idPersona;

	@Id
	@Column(name="id_plataforma_moodle")
	private Integer idPlataformaMoodle;

	@Column(name="id_persona_moodle")
	private Integer idPersonaMoodle;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	public RelPersonaPlataformaMoodle() {
	}

	public int getIdPersonaMoodle() {
		return this.idPersonaMoodle;
	}

	public void setIdPersonaMoodle(int idPersonaMoodle) {
		this.idPersonaMoodle = idPersonaMoodle;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public int getIdPlataformaMoodle() {
		return idPlataformaMoodle;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public void setIdPlataformaMoodle(int idPlataformaMoodle) {
		this.idPlataformaMoodle = idPlataformaMoodle;
	}

}