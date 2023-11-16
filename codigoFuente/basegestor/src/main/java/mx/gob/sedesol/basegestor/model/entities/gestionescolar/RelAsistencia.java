package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_asistencia database table.
 * 
 */
@Entity
@Table(name="rel_asistencia")
@NamedQuery(name="RelAsistencia.findAll", query="SELECT r FROM RelAsistencia r")
public class RelAsistencia implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="id_dias_evento_cap")
	private int idDiasEventoCap;

	@Column(name="id_grupo_participante")
	private int idGrupoParticipante;

	@Column(name="id_tpo_asistencia")
	private int idTpoAsistencia;

	@Column(name="usuario_modifico")
	private long usuarioModifico;
	
	@ManyToOne
	@JoinColumn(name="id_dias_evento_cap", insertable = false, updatable = false)		
	private RelDiasEventoCapacitacion diasEventoCapacitacion;
	
	@ManyToOne
	@JoinColumn(name="id_tpo_asistencia", insertable = false, updatable = false)	
	private CatAsistencia catAsistencia;
	


	public long getId() {
		return this.id;
	}

	public void setId(long id) {
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

	public int getIdDiasEventoCap() {
		return this.idDiasEventoCap;
	}

	public void setIdDiasEventoCap(int idDiasEventoCap) {
		this.idDiasEventoCap = idDiasEventoCap;
	}

	public int getIdGrupoParticipante() {
		return this.idGrupoParticipante;
	}

	public void setIdGrupoParticipante(int idGrupoParticipante) {
		this.idGrupoParticipante = idGrupoParticipante;
	}

	public int getIdTpoAsistencia() {
		return this.idTpoAsistencia;
	}

	public void setIdTpoAsistencia(int idTpoAsistencia) {
		this.idTpoAsistencia = idTpoAsistencia;
	}

	public long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public RelDiasEventoCapacitacion getDiasEventoCapacitacion() {
		return diasEventoCapacitacion;
	}

	public void setDiasEventoCapacitacion(RelDiasEventoCapacitacion diasEventoCapacitacion) {
		this.diasEventoCapacitacion = diasEventoCapacitacion;
	}

	public CatAsistencia getCatAsistencia() {
		return catAsistencia;
	}

	public void setCatAsistencia(CatAsistencia catAsistencia) {
		this.catAsistencia = catAsistencia;
	}

		
	
	

}