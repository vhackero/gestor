package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;


public class RelProgActividadesAprendizajeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private CatalogoComunDTO catActividadesAprendizajePrograma;
	private FichaDescProgramaDTO tblFichaDescriptivaPrograma;
	private Integer idPrograma;
	private Integer idActividadAprendizaje;
	
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	public CatalogoComunDTO getCatActividadesAprendizajePrograma() {
		return catActividadesAprendizajePrograma;
	}
	
	public void setCatActividadesAprendizajePrograma(CatalogoComunDTO catActividadesAprendizajePrograma) {
		this.catActividadesAprendizajePrograma = catActividadesAprendizajePrograma;
		this.setIdActividadAprendizaje(this.catActividadesAprendizajePrograma.getId());
	}
	
	public FichaDescProgramaDTO getTblFichaDescriptivaPrograma() {
		return tblFichaDescriptivaPrograma;
	}
	
	public void setTblFichaDescriptivaPrograma(FichaDescProgramaDTO tblFichaDescriptivaPrograma) {
		this.tblFichaDescriptivaPrograma = tblFichaDescriptivaPrograma;
		this.setIdPrograma(this.tblFichaDescriptivaPrograma.getIdPrograma());
	}
	
	public Integer getIdPrograma() {
		return idPrograma;
	}
	
	public Integer getIdActividadAprendizaje() {
		return idActividadAprendizaje;
	}
	
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	
	public void setIdActividadAprendizaje(Integer idActividadAprendizaje) {
		this.idActividadAprendizaje = idActividadAprendizaje;
	}

}
