package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class AreaSedeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idAreaSede;
	private CatalogoComunDTO catArea;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Integer orden;
	private Integer piso;
	private Long usuarioModifico;
	private SedeDTO catSede;
	private CatalogoComunDTO catEstatusArea;

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion
	 *            the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	/**
	 * @return the piso
	 */
	public Integer getPiso() {
		return piso;
	}

	/**
	 * @param piso
	 *            the piso to set
	 */
	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	/**
	 * @return the usuarioModifico
	 */
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	/**
	 * @param usuarioModifico
	 *            the usuarioModifico to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the catSede
	 */
	public SedeDTO getCatSede() {
		return catSede;
	}

	/**
	 * @param catSede
	 *            the catSede to set
	 */
	public void setCatSede(SedeDTO catSede) {
		this.catSede = catSede;
	}

	/**
	 * @return the catEstatusArea
	 */
	public CatalogoComunDTO getCatEstatusArea() {
		return catEstatusArea;
	}

	/**
	 * @param catEstatusArea
	 *            the catEstatusArea to set
	 */
	public void setCatEstatusArea(CatalogoComunDTO catEstatusArea) {
		this.catEstatusArea = catEstatusArea;
	}
	/**
	 * @return the tblConfiguracionAreas
	 */
	// public List<ConfiguracionAreaDTO> getTblConfiguracionAreas() {
	// return tblConfiguracionAreas;
	// }
	/**
	 * @param tblConfiguracionAreas
	 *            the tblConfiguracionAreas to set
	 */

	// public void setTblConfiguracionAreas(List<ConfiguracionAreaDTO>
	// tblConfiguracionAreas) {
	// this.tblConfiguracionAreas = tblConfiguracionAreas;
	// }
	/**
	 * @return the idAreaSede
	 */
	public Integer getIdAreaSede() {
		return idAreaSede;
	}

	/**
	 * @param idAreaSede
	 *            the idAreaSede to set
	 */
	public void setIdAreaSede(Integer idAreaSede) {
		this.idAreaSede = idAreaSede;
	}

	/**
	 * @return the catArea
	 */
	public CatalogoComunDTO getCatArea() {
		return catArea;
	}

	/**
	 * @param catArea
	 *            the catArea to set
	 */
	public void setCatArea(CatalogoComunDTO catArea) {
		this.catArea = catArea;
	}

}
