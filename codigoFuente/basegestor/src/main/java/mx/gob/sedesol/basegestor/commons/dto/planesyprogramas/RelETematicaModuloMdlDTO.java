package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.util.Date;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;


public class RelETematicaModuloMdlDTO {

	private Integer idEstructuraTematica;
	private Integer idModuloMdl;
	private Integer activo;
	private Date fechaRegistro;
	private Long usuarioModifico;
//	private EstructuraTematicaDTO tblEstructuraTematica;
//	private ModuloMoodleDTO mdlModulo;
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
	 * @return the idModuloMdl
	 */
	public Integer getIdModuloMdl() {
		return idModuloMdl;
	}
	/**
	 * @param idModuloMdl the idModuloMdl to set
	 */
	public void setIdModuloMdl(Integer idModuloMdl) {
		this.idModuloMdl = idModuloMdl;
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
	/**
	 * @return the tblEstructuraTematica
	 */
//	public EstructuraTematicaDTO getTblEstructuraTematica() {
//		return tblEstructuraTematica;
//	}
	/**
	 * @param tblEstructuraTematica the tblEstructuraTematica to set
	 */
//	public void setTblEstructuraTematica(EstructuraTematicaDTO tblEstructuraTematica) {
//		this.tblEstructuraTematica = tblEstructuraTematica;
//		this.setIdEstructuraTematica(this.tblEstructuraTematica.getIdEstructuraTematica());
//	}
	/**
	 * @return the mdlModulo
	 */
//	public ModuloMoodleDTO getMdlModulo() {
//		return mdlModulo;
//	}
	/**
	 * @param mdlModulo the mdlModulo to set
	 */
//	public void setMdlModulo(ModuloMoodleDTO mdlModulo) {
//		this.mdlModulo = mdlModulo;
//		this.setIdModuloMdl(this.mdlModulo.getId());
//	}
	
}
