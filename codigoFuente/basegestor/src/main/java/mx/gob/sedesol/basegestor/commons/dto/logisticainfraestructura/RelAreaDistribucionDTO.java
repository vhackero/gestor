package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;


public class RelAreaDistribucionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idAreaConfig;
	private Integer idDistribucion;
	private Integer activo;
	private Date fechaRegistro;
	private Long usuarioRegistro;
	private ConfiguracionAreaDTO tblConfiguracionArea;
	private CatalogoComunDTO catDistribucionArea;
	/**
	 * @return the idAreaConfig
	 */
	public Integer getIdAreaConfig() {
		return idAreaConfig;
	}
	/**
	 * @param idAreaConfig the idAreaConfig to set
	 */
	public void setIdAreaConfig(Integer idAreaConfig) {
		this.idAreaConfig = idAreaConfig;
	}
	/**
	 * @return the idDistribucion
	 */
	public Integer getIdDistribucion() {
		return idDistribucion;
	}
	/**
	 * @param idDistribucion the idDistribucion to set
	 */
	public void setIdDistribucion(Integer idDistribucion) {
		this.idDistribucion = idDistribucion;
	}
	/**
	 * @return the activo
	 */
	public Integer getActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Integer activo) {
		this.activo = activo;
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
	 * @return the usuarioRegistro
	 */
	public Long getUsuarioRegistro() {
		return usuarioRegistro;
	}
	/**
	 * @param usuarioRegistro the usuarioRegistro to set
	 */
	public void setUsuarioRegistro(Long usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}
	/**
	 * @return the tblConfiguracionArea
	 */
	public ConfiguracionAreaDTO getTblConfiguracionArea() {
		return tblConfiguracionArea;
	}
	/**
	 * @param tblConfiguracionArea the tblConfiguracionArea to set
	 */
	public void setTblConfiguracionArea(ConfiguracionAreaDTO tblConfiguracionArea) {
		this.tblConfiguracionArea = tblConfiguracionArea;
	}
	/**
	 * @return the catDistribucionArea
	 */
	public CatalogoComunDTO getCatDistribucionArea() {
		return catDistribucionArea;
	}
	/**
	 * @param catDistribucionArea the catDistribucionArea to set
	 */
	public void setCatDistribucionArea(CatalogoComunDTO catDistribucionArea) {
		this.catDistribucionArea = catDistribucionArea;
	}

}
