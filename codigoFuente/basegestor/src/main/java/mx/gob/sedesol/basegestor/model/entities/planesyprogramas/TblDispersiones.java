package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the tbl_ficha_descriptiva_programa database table.
 * 
 */
@Entity
@Table(name = "tbl_dispersiones")
@NamedQuery(name = "TblDisperciones.findAll", query = "SELECT t FROM TblDispersiones t")
public class TblDispersiones implements Serializable {

	public TblDispersiones() {
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dispercion")
	private Integer idDispercion;

	@Column(name = "id_inscripcion_resumen")
	private Integer idInscripcionResumen;
	
	@Column(name = "id_proceso_inscripcion")
	private Integer idProcesoInscripcion;
	
	@Column(name = "no_total_estudiantes")
	private Integer noTotalesEstudiantes;
	
	@Column(name = "no_grupos")
	private Integer noGrupos;
	
	@Column(name = "estudiantes_x_grupo")
	private Integer estudiantesGrupo;
	
	@Column(name = "grupo_resto")
	private Integer grupoResto;
	
	@Column(name = "estudiantes_resto")
	private Integer estudiantesResto;
	
	@Column(name = "usuario_modifico")
	private Integer usuarioModifico;
	
	@Column(name = "tipo_matriculacion")
	private Integer tipoMatriculacion;
	
	private Integer existente;

	public Integer getExistente() {
		return existente;
	}

	public void setExistente(Integer existente) {
		this.existente = existente;
	}

	public Integer getIdDispercion() {
		return idDispercion;
	}

	public void setIdDispercion(Integer idDispercion) {
		this.idDispercion = idDispercion;
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

	public Integer getNoTotalesEstudiantes() {
		return noTotalesEstudiantes;
	}

	public void setNoTotalesEstudiantes(Integer noTotalesEstudiantes) {
		this.noTotalesEstudiantes = noTotalesEstudiantes;
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
		
	
}
