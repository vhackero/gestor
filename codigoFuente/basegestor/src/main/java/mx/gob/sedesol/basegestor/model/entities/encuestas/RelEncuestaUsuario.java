package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.io.Serializable;
import javax.persistence.*;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoParticipante;
import java.util.Date;

/**
 * The persistent class for the rel_encuesta_usuario database table.
 * 
 */
@Entity
@Table(name="rel_encuesta_usuario")
@IdClass(RelEncuestaUsuarioPK.class)
@NamedQuery(name="RelEncuestaUsuario.findAll", query="SELECT r FROM RelEncuestaUsuario r")
public class RelEncuestaUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_encuesta")
	private Integer idEncuesta;

	@Id
	@Column(name="id_grupo_participante")
	private Integer idGrupoParticipante;

	private Boolean activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_apertura")
	private Date fechaApertura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to RelGrupoParticipante
	@ManyToOne
	@JoinColumn(name="id_grupo_participante", insertable=false, updatable=false)
	private RelGrupoParticipante relGrupoParticipante;

	//bi-directional one-to-one association to TblEncuesta
	@OneToOne
	@JoinColumn(name="id_encuesta", insertable=false, updatable=false)
	private TblEncuesta tblEncuesta;

	public RelEncuestaUsuario() {
	}
	
	public Integer getIdEncuesta() {
		return idEncuesta;
	}

	public void setIdEncuesta(Integer idEncuesta) {
		this.idEncuesta = idEncuesta;
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

	public RelGrupoParticipante getRelGrupoParticipante() {
		return this.relGrupoParticipante;
	}

	public void setRelGrupoParticipante(RelGrupoParticipante relGrupoParticipante) {
		this.relGrupoParticipante = relGrupoParticipante;
	}

	public TblEncuesta getTblEncuesta() {
		return this.tblEncuesta;
	}

	public void setTblEncuesta(TblEncuesta tblEncuesta) {
		this.tblEncuesta = tblEncuesta;
	}

	/**
	 * @return the fechaApertura
	 */
	public Date getFechaApertura() {
		return fechaApertura;
	}

	/**
	 * @param fechaApertura the fechaApertura to set
	 */
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	/**
	 * @return the idGrupoParticipante
	 */
	public Integer getIdGrupoParticipante() {
		return idGrupoParticipante;
	}

	/**
	 * @param idGrupoParticipante the idGrupoParticipante to set
	 */
	public void setIdGrupoParticipante(Integer idGrupoParticipante) {
		this.idGrupoParticipante = idGrupoParticipante;
	}


}