package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;


import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class RelUDidacticaTposCompetenciaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long usuarioModifico;
	private Date fechaRegistro;
	private Integer idUnidadDidactica;
	private Integer idCompEspecifica;
	private CatalogoComunDTO catCompetenciaEspecifica;
	private DetEstUniDidacticaDTO detEstUnidadDidactica;
	
	
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
	 * @return the idCompEspecifica
	 */
	public Integer getIdCompEspecifica() {
		return idCompEspecifica;
	}
	/**
	 * @param idCompEspecifica the idCompEspecifica to set
	 */
	public void setIdCompEspecifica(Integer idCompEspecifica) {
		this.idCompEspecifica = idCompEspecifica;
	}
	/**
	 * @return the catCompetenciaEspecifica
	 */
	public CatalogoComunDTO getCatCompetenciaEspecifica() {
		return catCompetenciaEspecifica;
	}
	/**
	 * @param catCompetenciaEspecifica the catCompetenciaEspecifica to set
	 */
	public void setCatCompetenciaEspecifica(CatalogoComunDTO catCompetenciaEspecifica) {
		this.catCompetenciaEspecifica = catCompetenciaEspecifica;
		this.idCompEspecifica = this.catCompetenciaEspecifica.getId();
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

}
