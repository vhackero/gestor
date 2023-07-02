package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;


import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RelProgAreaConocimientoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private CatalogoComunDTO catAreasConocimiento;
	private Long usuarioModifico;
	private FichaDescProgramaDTO fichaDescriptivaPrograma;
	private Integer idPrograma;
	private Integer idAreaConocimiento;
	
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
	public FichaDescProgramaDTO getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}
	
	/**
	 * 
	 * @param fichaDescriptivaPrograma
	 */
	public void setFichaDescriptivaPrograma(FichaDescProgramaDTO fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
		this.setIdPrograma(this.fichaDescriptivaPrograma.getIdPrograma());
	}
	public CatalogoComunDTO getCatAreasConocimiento() {
		return catAreasConocimiento;
	}
	
	/**
	 * 
	 * @param catAreasConocimiento
	 */
	public void setCatAreasConocimiento(CatalogoComunDTO catAreasConocimiento) {
		this.catAreasConocimiento = catAreasConocimiento;
		this.setIdAreaConocimiento(this.catAreasConocimiento.getId());
	}
	
	public Integer getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	public Integer getIdAreaConocimiento() {
		return idAreaConocimiento;
	}
	public void setIdAreaConocimiento(Integer idAreaConocimiento) {
		this.idAreaConocimiento = idAreaConocimiento;
	}

}
