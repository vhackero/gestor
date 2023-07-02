package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;



public class RelEvaluacionCalificacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idEvalCalificacion;
	private Double calificacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private CatalogoComunDTO dictamen;
	private RelGrupoEvaluacionDTO relGrupoEvaluacion;
	private RelGrupoParticipanteDTO relGrupoParticipante;
	
	private String nombreEvaluacion;
	
	/**
	 * @return the idEvalCalificacion
	 */
	public Integer getIdEvalCalificacion() {
		return idEvalCalificacion;
	}
	/**
	 * @param idEvalCalificacion the idEvalCalificacion to set
	 */
	public void setIdEvalCalificacion(Integer idEvalCalificacion) {
		this.idEvalCalificacion = idEvalCalificacion;
	}
	/**
	 * @return the calificacion
	 */
	public Double getCalificacion() {
		return calificacion;
	}
	/**
	 * @param calificacion the calificacion to set
	 */
	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return the usuarioModifico
	 */
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	/**
	 * @param usuarioModifico the usuarioModifico to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	/**
	 * @return the relGrupoEvaluacion
	 */
	public RelGrupoEvaluacionDTO getRelGrupoEvaluacion() {
		return relGrupoEvaluacion;
	}
	/**
	 * @param relGrupoEvaluacion the relGrupoEvaluacion to set
	 */
	public void setRelGrupoEvaluacion(RelGrupoEvaluacionDTO relGrupoEvaluacion) {
		this.relGrupoEvaluacion = relGrupoEvaluacion;
	}
	/**
	 * @return the relGrupoParticipante
	 */
	public RelGrupoParticipanteDTO getRelGrupoParticipante() {
		return relGrupoParticipante;
	}
	/**
	 * @param relGrupoParticipante the relGrupoParticipante to set
	 */
	public void setRelGrupoParticipante(RelGrupoParticipanteDTO relGrupoParticipante) {
		this.relGrupoParticipante = relGrupoParticipante;
	}
	/**
	 * @return the dictamen
	 */
	public CatalogoComunDTO getDictamen() {
		return dictamen;
	}
	/**
	 * @param dictamen the dictamen to set
	 */
	public void setDictamen(CatalogoComunDTO dictamen) {
		this.dictamen = dictamen;
	}
	/**
	 * @return the nombreEvaluacion
	 */
	public String getNombreEvaluacion() {
		return nombreEvaluacion;
	}
	/**
	 * @param nombreEvaluacion the nombreEvaluacion to set
	 */
	public void setNombreEvaluacion(String nombreEvaluacion) {
		this.nombreEvaluacion = nombreEvaluacion;
	}
}
