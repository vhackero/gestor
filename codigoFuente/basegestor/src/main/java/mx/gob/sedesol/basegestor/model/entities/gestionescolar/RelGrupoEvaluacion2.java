package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the rel_grupo_evaluacion database table.
 * 
 */
@Entity
@Table(name="rel_grupo_evaluacion")
public class RelGrupoEvaluacion2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_gpo_evaluacion")
	private Integer idGpoEvaluacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="nombre_evaluacion")
	private String nombreEvaluacion;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

//	//bi-directional many-to-one association to RelEvaluacionCalificacion
//	@OneToMany(mappedBy="relGrupoEvaluacion", cascade= CascadeType.ALL, fetch=FetchType.LAZY)
//	private List<RelEvaluacionCalificacion> relEvaluacionCalificaciones;

	@Column(name="id_grupo")
	private Integer idGrupo;

	@Column(name="id_tipo_calificacion")
	private Integer idTipoCalificacion;
	
//	@Column(name="acta_cerrada", nullable = false, columnDefinition = "TINYINT(1)")
//	private boolean actaCerrada;
	
	@Column(name="ponderacion")
	private Integer ponderacion;

	public RelGrupoEvaluacion2() {
	}

	public Integer getIdGpoEvaluacion() {
		return idGpoEvaluacion;
	}

	public void setIdGpoEvaluacion(Integer idGpoEvaluacion) {
		this.idGpoEvaluacion = idGpoEvaluacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombreEvaluacion() {
		return nombreEvaluacion;
	}

	public void setNombreEvaluacion(String nombreEvaluacion) {
		this.nombreEvaluacion = nombreEvaluacion;
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

	public Integer getIdTipoCalificacion() {
		return idTipoCalificacion;
	}

	public void setIdTipoCalificacion(Integer idTipoCalificacion) {
		this.idTipoCalificacion = idTipoCalificacion;
	}

	public Integer getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(Integer ponderacion) {
		this.ponderacion = ponderacion;
	}
	
	

}
