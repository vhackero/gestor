package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;

public class RelEstUnidadDidacticaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Integer idDetTema;
	private Integer idUnidadDidactica;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private DetEtematicaTemaDTO detEtematicaTema;
	private DetEstUniDidacticaDTO detEstUnidadDidactica;
	private BitacoraDTO bitacoraDTO;
	
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
	 * @return the idDetTema
	 */
	public Integer getIdDetTema() {
		return idDetTema;
	}
	/**
	 * @param idDetTema the idDetTema to set
	 */
	public void setIdDetTema(Integer idDetTema) {
		this.idDetTema = idDetTema;
	}
	/**
	 * @return the detEtematicaTema
	 */
	public DetEtematicaTemaDTO getDetEtematicaTema() {
		return detEtematicaTema;
	}
	/**
	 * @param detEtematicaTema the detEtematicaTema to set
	 */
	public void setDetEtematicaTema(DetEtematicaTemaDTO detEtematicaTema) {
		this.detEtematicaTema = detEtematicaTema;
		this.idDetTema = this.detEtematicaTema.getIdDetTema();
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
     * @return the bitacoraDTO
     */
    public BitacoraDTO getBitacoraDTO() {
        return bitacoraDTO;
    }

    /**
     * @param bitacoraDTO the bitacoraDTO to set
     */
    public void setBitacoraDTO(BitacoraDTO bitacoraDTO) {
        this.bitacoraDTO = bitacoraDTO;
    }

}
