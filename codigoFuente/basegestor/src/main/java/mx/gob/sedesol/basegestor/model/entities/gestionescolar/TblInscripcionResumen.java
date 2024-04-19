package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;


/**
 * The persistent class for the tbl_inscripcion_resumen database table.
 * 
 */
@Entity
@Table(name="tbl_inscripcion_resumen")
@NamedQuery(name="TblInscripcionResumen.findAll", query="SELECT t FROM TblInscripcionResumen t")
public class TblInscripcionResumen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_proceso")
	private Integer id;
	
	private String grupo;
	
	// bi-directional many-to-one association to TblFichaDescriptivaPrograma
	@ManyToOne
	@JoinColumn(name = "id_programa", insertable = false, updatable = false)
	private TblFichaDescriptivaPrograma fichaDescriptivaPrograma;
	
	@Column(name="programa_educativo")
	private String programaEducativo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_plan")
	private TblPlan plan;

	private String asignatura;
	
	@Column(name="clave_asignatura")
	private String claveAsignatura;

	private Integer semestre;
	
	private String bloque;

	//bi-directional many-to-one association to RelGrupoEvaluacion
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="convocatoria_id")
	private TblConvocatoria convocatoria;
	
	public TblInscripcionResumen() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public TblFichaDescriptivaPrograma getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}

	public void setFichaDescriptivaPrograma(TblFichaDescriptivaPrograma fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
	}

	public String getProgramaEducativo() {
		return programaEducativo;
	}

	public void setProgramaEducativo(String programaEducativo) {
		this.programaEducativo = programaEducativo;
	}

	public TblPlan getPlan() {
		return plan;
	}

	public void setPlan(TblPlan plan) {
		this.plan = plan;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getClaveAsignatura() {
		return claveAsignatura;
	}

	public void setClaveAsignatura(String claveAsignatura) {
		this.claveAsignatura = claveAsignatura;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public TblConvocatoria getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(TblConvocatoria convocatoria) {
		this.convocatoria = convocatoria;
	}

}