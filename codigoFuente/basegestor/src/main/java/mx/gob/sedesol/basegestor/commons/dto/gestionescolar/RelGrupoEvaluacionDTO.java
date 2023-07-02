package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RelGrupoEvaluacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idGpoEvaluacion;
	private Date fechaRegistro;
	private String nombreEvaluacion;
	private Long usuarioModifico;
	private List<RelEvaluacionCalificacionDTO> relEvaluacionCalificaciones;
	private GrupoDTO tblGrupo;
	private CatalogoComunDTO catTipoCalificacionEc;
	private Integer ponderacion;
	
	
	/**
	 * @return the idGpoEvaluacion
	 */
	public Integer getIdGpoEvaluacion() {
		return idGpoEvaluacion;
	}
	/**
	 * @param idGpoEvaluacion the idGpoEvaluacion to set
	 */
	public void setIdGpoEvaluacion(Integer idGpoEvaluacion) {
		this.idGpoEvaluacion = idGpoEvaluacion;
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
	 * @return the relEvaluacionCalificaciones
	 */
	public List<RelEvaluacionCalificacionDTO> getRelEvaluacionCalificaciones() {
		return relEvaluacionCalificaciones;
	}
	/**
	 * @param relEvaluacionCalificaciones the relEvaluacionCalificaciones to set
	 */
	public void setRelEvaluacionCalificaciones(List<RelEvaluacionCalificacionDTO> relEvaluacionCalificaciones) {
		this.relEvaluacionCalificaciones = relEvaluacionCalificaciones;
	}
	/**
	 * @return the tblGrupo
	 */
	public GrupoDTO getTblGrupo() {
		return tblGrupo;
	}
	/**
	 * @param tblGrupo the tblGrupo to set
	 */
	public void setTblGrupo(GrupoDTO tblGrupo) {
		this.tblGrupo = tblGrupo;
	}
	/**
	 * @return the catTipoCalificacionEc
	 */
	public CatalogoComunDTO getCatTipoCalificacionEc() {
		return catTipoCalificacionEc;
	}
	/**
	 * @param catTipoCalificacionEc the catTipoCalificacionEc to set
	 */
	public void setCatTipoCalificacionEc(CatalogoComunDTO catTipoCalificacionEc) {
		this.catTipoCalificacionEc = catTipoCalificacionEc;
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
