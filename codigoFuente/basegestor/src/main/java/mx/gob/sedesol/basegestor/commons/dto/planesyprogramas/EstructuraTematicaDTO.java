package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;



public class EstructuraTematicaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Integer idEstructuraTematica;
	private Integer activo;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private Integer numUnidadesTematicas;
	private FichaDescProgramaDTO tblFichaDescriptivaPrograma;
	private List<DetEtematicaTemaDTO> detEtematicaTemas;
	
	//private EstructuraTematicaDTO estTematicaPadre;
	//private List<EstructuraTematicaDTO> lstHijosEstTematica;
	//private Integer idFichaDescPrograma;
        private BitacoraDTO bitacoraDTO;
	
	/**
	 * @return the idEstructuraTematica
	 */
	public Integer getIdEstructuraTematica() {
		return idEstructuraTematica;
	}
	/**
	 * @param idEstructuraTematica the idEstructuraTematica to set
	 */
	public void setIdEstructuraTematica(Integer idEstructuraTematica) {
		this.idEstructuraTematica = idEstructuraTematica;
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
	 * @return the numUnidadesTematicas
	 */
	public Integer getNumUnidadesTematicas() {
		return numUnidadesTematicas;
	}
	/**
	 * @param numUnidadesTematicas the numUnidadesTematicas to set
	 */
	public void setNumUnidadesTematicas(Integer numUnidadesTematicas) {
		this.numUnidadesTematicas = numUnidadesTematicas;
	}
	/**
	 * @return the tblFichaDescriptivaPrograma
	 */
	public FichaDescProgramaDTO getTblFichaDescriptivaPrograma() {
		return tblFichaDescriptivaPrograma;
	}
	/**
	 * @param tblFichaDescriptivaPrograma the tblFichaDescriptivaPrograma to set
	 */
	public void setTblFichaDescriptivaPrograma(FichaDescProgramaDTO tblFichaDescriptivaPrograma) {
		this.tblFichaDescriptivaPrograma = tblFichaDescriptivaPrograma;
	}
	/**
	 * @return the detEtematicaTemas
	 */
	public List<DetEtematicaTemaDTO> getDetEtematicaTemas() {
		return detEtematicaTemas;
	}
	/**
	 * @param detEtematicaTemas the detEtematicaTemas to set
	 */
	public void setDetEtematicaTemas(List<DetEtematicaTemaDTO> detEtematicaTemas) {
		this.detEtematicaTemas = detEtematicaTemas;
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
