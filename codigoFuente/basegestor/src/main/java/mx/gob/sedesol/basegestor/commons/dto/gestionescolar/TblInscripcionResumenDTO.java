package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;

public class TblInscripcionResumenDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String grupo;
	private FichaDescProgramaDTO fichaDescriptivaPrograma;
	private String programaEducativo;
	private PlanDTO plan;
	private String asignatura;
	private String claveAsignatura;
	private Integer semestre;
	private String bloque;
	private TblConvocatoriaDTO convocatoria;
	
	
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
	public FichaDescProgramaDTO getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}
	public void setFichaDescriptivaPrograma(FichaDescProgramaDTO fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
	}
	public String getProgramaEducativo() {
		return programaEducativo;
	}
	public void setProgramaEducativo(String programaEducativo) {
		this.programaEducativo = programaEducativo;
	}
	public PlanDTO getPlan() {
		return plan;
	}
	public void setPlan(PlanDTO plan) {
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
	public TblConvocatoriaDTO getConvocatoria() {
		return convocatoria;
	}
	public void setConvocatoria(TblConvocatoriaDTO convocatoria) {
		this.convocatoria = convocatoria;
	}
	
	@Override
	public String toString() {
		return "TblInscripcionResumenDTO [id=" + id + ", grupo=" + grupo + ", fichaDescriptivaPrograma="
				+ fichaDescriptivaPrograma + ", programaEducativo=" + programaEducativo + ", plan=" + plan
				+ ", asignatura=" + asignatura + ", claveAsignatura=" + claveAsignatura + ", semestre=" + semestre
				+ ", bloque=" + bloque + ", convocatoria=" + convocatoria + "]";
	}

}