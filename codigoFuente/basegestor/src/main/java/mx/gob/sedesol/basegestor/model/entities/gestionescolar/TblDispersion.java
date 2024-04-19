package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the tbl_dispersiones database table.
 * 
 */
@Entity
@Table(name="tbl_dispersiones")
@NamedQuery(name="TblDispersion.findAll", query="SELECT t FROM TblDispersion t")
public class TblDispersion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_dispersion")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_inscripcion_resumen")
	private TblInscripcionResumen inscripcionResumen;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proceso_inscripcion")
	private TblProcesoInscripcion procesoInscripcion;
	
	@Column(name="no_total_estudiantes")
	private Integer noTotalEstudiantes;
	
	@Column(name="no_grupos")
	private String noGrupos;
	
	@Column(name="estudiantes_x_grupo")
	private Integer estudiantesXGrupo;

	@Column(name="grupo_resto")
	private Integer grupoResto;
	
	@Column(name="estudiantes_resto")
	private Integer estudiantesResto;

	public TblDispersion() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TblInscripcionResumen getInscripcionResumen() {
		return inscripcionResumen;
	}

	public void setInscripcionResumen(TblInscripcionResumen inscripcionResumen) {
		this.inscripcionResumen = inscripcionResumen;
	}

	public TblProcesoInscripcion getProcesoInscripcion() {
		return procesoInscripcion;
	}

	public void setProcesoInscripcion(TblProcesoInscripcion procesoInscripcion) {
		this.procesoInscripcion = procesoInscripcion;
	}

	public Integer getNoTotalEstudiantes() {
		return noTotalEstudiantes;
	}

	public void setNoTotalEstudiantes(Integer noTotalEstudiantes) {
		this.noTotalEstudiantes = noTotalEstudiantes;
	}

	public String getNoGrupos() {
		return noGrupos;
	}

	public void setNoGrupos(String noGrupos) {
		this.noGrupos = noGrupos;
	}

	public Integer getEstudiantesXGrupo() {
		return estudiantesXGrupo;
	}

	public void setEstudiantesXGrupo(Integer estudiantesXGrupo) {
		this.estudiantesXGrupo = estudiantesXGrupo;
	}

	public Integer getGrupoResto() {
		return grupoResto;
	}

	public void setGrupoResto(Integer grupoResto) {
		this.grupoResto = grupoResto;
	}

	public Integer getEstudiantesResto() {
		return estudiantesResto;
	}

	public void setEstudiantesResto(Integer estudiantesResto) {
		this.estudiantesResto = estudiantesResto;
	}

}