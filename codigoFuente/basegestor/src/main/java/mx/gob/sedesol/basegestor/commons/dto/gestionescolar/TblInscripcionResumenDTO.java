package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;

public class TblInscripcionResumenDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String asignatura;
	private String bloque;
	private String claveAsignatura;
	private Integer estudiantesResto;
	private Integer estudiantesXGrupo;
	private String grupo;
	private Integer grupoResto;
	private Integer noEstudiantes;
//	private Integer noGrupos;
	private String programaEducativo;
	private Integer semestre;
	private PlanDTO plan;
	private FichaDescProgramaDTO fichaDescriptivaPrograma;

	@Override
	public String toString() {
		return "TblInscripcionResumenDTO [id=" + id + ", asignatura=" + asignatura + ", bloque=" + bloque
				+ ", claveAsignatura=" + claveAsignatura + ", estudiantesResto=" + estudiantesResto
				+ ", estudiantesXGrupo=" + estudiantesXGrupo + ", grupo=" + grupo + ", grupoResto=" + grupoResto
				+ ", noEstudiantes=" + noEstudiantes /*+ ", noGrupos=" + noGrupos */+ ", programaEducativo="
				+ programaEducativo + ", semestre=" + semestre + "]";
	}

	public TblInscripcionResumenDTO() {
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

//	public Integer getNoGrupos() {
//		return this.noGrupos;
//	}
//
//	public void setNoGrupos(Integer noGrupos) {
//		this.noGrupos = noGrupos;
//	}

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

	public PlanDTO getPlan() {
		return plan;
	}

	public void setPlan(PlanDTO plan) {
		this.plan = plan;
	}

	public FichaDescProgramaDTO getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}

	public void setFichaDescriptivaPrograma(FichaDescProgramaDTO fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
	}
	

}