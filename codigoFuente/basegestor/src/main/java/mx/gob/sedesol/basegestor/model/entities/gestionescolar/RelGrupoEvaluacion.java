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
@NamedQuery(name="RelGrupoEvaluacion.findAll", query="SELECT r FROM RelGrupoEvaluacion r")
public class RelGrupoEvaluacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_gpo_evaluacion")
	private Integer idGpoEvaluacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="nombre_evaluacion")
	private String nombreEvaluacion;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to RelEvaluacionCalificacion
	@OneToMany(mappedBy="relGrupoEvaluacion", cascade= CascadeType.ALL, fetch=FetchType.LAZY)
	private List<RelEvaluacionCalificacion> relEvaluacionCalificaciones;

	//bi-directional many-to-one association to TblGrupo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_grupo")
	private TblGrupo tblGrupo;

	//bi-directional many-to-one association to CatTipoCalificacionEc
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_calificacion")
	private CatTipoCalificacionEc catTipoCalificacionEc;
	
//	@Column(name="acta_cerrada", nullable = false, columnDefinition = "TINYINT(1)")
//	private boolean actaCerrada;
	
	@Column(name="ponderacion")
	private Integer ponderacion;

	public RelGrupoEvaluacion() {
	}

	public Integer getIdGpoEvaluacion() {
		return this.idGpoEvaluacion;
	}

	public void setIdGpoEvaluacion(Integer idGpoEvaluacion) {
		this.idGpoEvaluacion = idGpoEvaluacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombreEvaluacion() {
		return this.nombreEvaluacion;
	}

	public void setNombreEvaluacion(String nombreEvaluacion) {
		this.nombreEvaluacion = nombreEvaluacion;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public TblGrupo getTblGrupo() {
		return this.tblGrupo;
	}

	public void setTblGrupo(TblGrupo tblGrupo) {
		this.tblGrupo = tblGrupo;
	}

	public CatTipoCalificacionEc getCatTipoCalificacionEc() {
		return this.catTipoCalificacionEc;
	}

	public void setCatTipoCalificacionEc(CatTipoCalificacionEc catTipoCalificacionEc) {
		this.catTipoCalificacionEc = catTipoCalificacionEc;
	}

	/**
	 * @return the relEvaluacionCalificaciones
	 */
	public List<RelEvaluacionCalificacion> getRelEvaluacionCalificaciones() {
		return relEvaluacionCalificaciones;
	}

	/**
	 * @param relEvaluacionCalificaciones the relEvaluacionCalificaciones to set
	 */
	public void setRelEvaluacionCalificaciones(List<RelEvaluacionCalificacion> relEvaluacionCalificaciones) {
		this.relEvaluacionCalificaciones = relEvaluacionCalificaciones;
	}

	/**
	 * @return the ponderacion
	 */
	public Integer getPonderacion() {
		return ponderacion;
	}

	/**
	 * @param ponderacion the ponderacion to set
	 */
	public void setPonderacion(Integer ponderacion) {
		this.ponderacion = ponderacion;
	}

}