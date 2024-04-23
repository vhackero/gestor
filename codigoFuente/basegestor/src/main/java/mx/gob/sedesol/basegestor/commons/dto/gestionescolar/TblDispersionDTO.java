package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class TblDispersionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private TblInscripcionResumenDTO inscripcionResumen;
	private TblProcesoInscripcionDTO procesoInscripcion;
	private Integer noTotalEstudiantes;
	private String noGrupos;
	private Integer estudiantesXGrupo;
	private Integer grupoResto;
	private Integer estudiantesResto;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public TblInscripcionResumenDTO getInscripcionResumen() {
		return inscripcionResumen;
	}
	public void setInscripcionResumen(TblInscripcionResumenDTO inscripcionResumen) {
		this.inscripcionResumen = inscripcionResumen;
	}
	public TblProcesoInscripcionDTO getProcesoInscripcion() {
		return procesoInscripcion;
	}
	public void setProcesoInscripcion(TblProcesoInscripcionDTO procesoInscripcion) {
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
	
	@Override
	public String toString() {
		return "TblDispersionDTO [id=" + id + ", inscripcionResumen=" + inscripcionResumen + ", procesoInscripcion="
				+ procesoInscripcion + ", noTotalEstudiantes=" + noTotalEstudiantes + ", noGrupos=" + noGrupos
				+ ", estudiantesXGrupo=" + estudiantesXGrupo + ", grupoResto=" + grupoResto + ", estudiantesResto="
				+ estudiantesResto + "]";
	}
}