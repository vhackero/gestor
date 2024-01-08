package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;


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
	private Integer id;

	private String asignatura;

	private String bloque;

	@Column(name="clave_asignatura")
	private String claveAsignatura;

	@Column(name="estudiantes_resto")
	private Integer estudiantesResto;

	@Column(name="estudiantes_x_grupo")
	private Integer estudiantesXGrupo;

	private String grupo;

	@Column(name="grupo_resto")
	private Integer grupoResto;

	@Column(name="no_estudiantes")
	private Integer noEstudiantes;

	@Column(name="no_grupos")
	private Integer noGrupos;

	@Column(name="programa_educativo")
	private String programaEducativo;

	private Integer semestre;

	public TblInscripcionResumen() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAsignatura() {
		return this.asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getBloque() {
		return this.bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getClaveAsignatura() {
		return this.claveAsignatura;
	}

	public void setClaveAsignatura(String claveAsignatura) {
		this.claveAsignatura = claveAsignatura;
	}

	public Integer getEstudiantesResto() {
		return this.estudiantesResto;
	}

	public void setEstudiantesResto(Integer estudiantesResto) {
		this.estudiantesResto = estudiantesResto;
	}

	public Integer getEstudiantesXGrupo() {
		return this.estudiantesXGrupo;
	}

	public void setEstudiantesXGrupo(Integer estudiantesXGrupo) {
		this.estudiantesXGrupo = estudiantesXGrupo;
	}

	public String getGrupo() {
		return this.grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Integer getGrupoResto() {
		return this.grupoResto;
	}

	public void setGrupoResto(Integer grupoResto) {
		this.grupoResto = grupoResto;
	}

	public Integer getNoEstudiantes() {
		return this.noEstudiantes;
	}

	public void setNoEstudiantes(Integer noEstudiantes) {
		this.noEstudiantes = noEstudiantes;
	}

	public Integer getNoGrupos() {
		return this.noGrupos;
	}

	public void setNoGrupos(Integer noGrupos) {
		this.noGrupos = noGrupos;
	}

	public String getProgramaEducativo() {
		return this.programaEducativo;
	}

	public void setProgramaEducativo(String programaEducativo) {
		this.programaEducativo = programaEducativo;
	}

	public Integer getSemestre() {
		return this.semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

}