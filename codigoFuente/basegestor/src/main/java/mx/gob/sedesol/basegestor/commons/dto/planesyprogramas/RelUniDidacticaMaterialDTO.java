package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RelUniDidacticaMaterialDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idUnidadDidactica;
	private Integer idMaterialDidactico;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private DetEstUniDidacticaDTO detEstUnidadDidactica;
	private CatalogoComunDTO catMaterialDidactico;
	
	
	/**
	 * @return the idUnidadDidactica
	 */
	public Integer getIdUnidadDidactica() {
		return idUnidadDidactica;
	}
	/**
	 * @param idUnidadDidactica the idUnidadDidactica to set
	 */
	public void setIdUnidadDidactica(Integer idUnidadDidactica) {
		this.idUnidadDidactica = idUnidadDidactica;
	}
	/**
	 * @return the idMaterialDidactico
	 */
	public Integer getIdMaterialDidactico() {
		return idMaterialDidactico;
	}
	/**
	 * @param idMaterialDidactico the idMaterialDidactico to set
	 */
	public void setIdMaterialDidactico(Integer idMaterialDidactico) {
		this.idMaterialDidactico = idMaterialDidactico;
	}
	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
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
	 * @return the detEstUnidadDidactica
	 */
	public DetEstUniDidacticaDTO getDetEstUnidadDidactica() {
		return detEstUnidadDidactica;
	}
	/**
	 * @param detEstUnidadDidactica the detEstUnidadDidactica to set
	 */
	public void setDetEstUnidadDidactica(DetEstUniDidacticaDTO detEstUnidadDidactica) {
		this.detEstUnidadDidactica = detEstUnidadDidactica;
		this.idUnidadDidactica = this.detEstUnidadDidactica.getIdUnidadDidactica();
	}
	/**
	 * @return the catMaterialDidactico
	 */
	public CatalogoComunDTO getCatMaterialDidactico() {
		return catMaterialDidactico;
	}
	/**
	 * @param catMaterialDidactico the catMaterialDidactico to set
	 */
	public void setCatMaterialDidactico(CatalogoComunDTO catMaterialDidactico) {
		this.catMaterialDidactico = catMaterialDidactico;
		this.idMaterialDidactico = this.catMaterialDidactico.getId();
	}
}
