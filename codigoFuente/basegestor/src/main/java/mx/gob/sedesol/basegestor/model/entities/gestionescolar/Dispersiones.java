package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;

/**
 * ENTITY 
 * @author ITTIVA
 * 
 */
public class Dispersiones implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idDispersion;
	private Integer idInscripcionResumen;
	private Integer idProcesoInscripcion;
	private Integer noTotalEstudiantes;
	private Integer noGrupos;
	private Integer estudiantesGrupo;
	private Integer grupoResto;
	private Integer estudiantesResto;
	private Integer usuarioModifico;
	private Integer tipoMatriculacion;
	public Integer getIdDispersion() {
		return idDispersion;
	}
	public void setIdDispersion(Integer idDispersion) {
		this.idDispersion = idDispersion;
	}
	public Integer getIdInscripcionResumen() {
		return idInscripcionResumen;
	}
	public void setIdInscripcionResumen(Integer idInscripcionResumen) {
		this.idInscripcionResumen = idInscripcionResumen;
	}
	public Integer getIdProcesoInscripcion() {
		return idProcesoInscripcion;
	}
	public void setIdProcesoInscripcion(Integer idProcesoInscripcion) {
		this.idProcesoInscripcion = idProcesoInscripcion;
	}
	public Integer getNoTotalEstudiantes() {
		return noTotalEstudiantes;
	}
	public void setNoTotalEstudiantes(Integer noTotalEstudiantes) {
		this.noTotalEstudiantes = noTotalEstudiantes;
	}
	public Integer getNoGrupos() {
		return noGrupos;
	}
	public void setNoGrupos(Integer noGrupos) {
		this.noGrupos = noGrupos;
	}
	public Integer getEstudiantesGrupo() {
		return estudiantesGrupo;
	}
	public void setEstudiantesGrupo(Integer estudiantesGrupo) {
		this.estudiantesGrupo = estudiantesGrupo;
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
	public Integer getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(Integer usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	public Integer getTipoMatriculacion() {
		return tipoMatriculacion;
	}
	public void setTipoMatriculacion(Integer tipoMatriculacion) {
		this.tipoMatriculacion = tipoMatriculacion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
