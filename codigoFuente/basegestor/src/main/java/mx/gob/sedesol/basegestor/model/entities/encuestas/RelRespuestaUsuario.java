package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the rel_respuesta_usuario database table.
 * 
 */
@Entity
@Table(name = "rel_respuesta_usuario")
@NamedQuery(name = "RelRespuestaUsuario.findAll", query = "SELECT r FROM RelRespuestaUsuario r")
public class RelRespuestaUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_respuesta_usuario")
	private Integer idRespuestaUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name = "id_encuesta")
	private Integer idEncuesta;

	@Column(name = "id_usuario")
	private Integer idUsuario;

	// bi-directional many-to-one association to TblRespuesta
	@OneToMany(mappedBy = "relRespuestaUsuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TblRespuesta> tblRespuestas;

	public RelRespuestaUsuario() {
	}

	public Integer getIdRespuestaUsuario() {
		return idRespuestaUsuario;
	}

	public void setIdRespuestaUsuario(Integer idRespuestaUsuario) {
		this.idRespuestaUsuario = idRespuestaUsuario;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getIdEncuesta() {
		return idEncuesta;
	}

	public void setIdEncuesta(Integer idEncuesta) {
		this.idEncuesta = idEncuesta;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<TblRespuesta> getTblRespuestas() {
		return this.tblRespuestas;
	}

	public void setTblRespuestas(List<TblRespuesta> tblRespuestas) {
		this.tblRespuestas = tblRespuestas;
	}

}