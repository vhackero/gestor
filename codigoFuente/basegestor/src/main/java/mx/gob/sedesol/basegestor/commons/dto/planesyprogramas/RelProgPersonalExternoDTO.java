package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;


public class RelProgPersonalExternoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPrograma;
	private Integer idEstPersonalExt;
	private Long usuarioModifico;
	private Date fechaRegistro;
	private FichaDescProgramaDTO tblFichaDescriptivaPrograma;
	private EstPersonalExternoDTO estPersonalExterno;
	
	/**
	 * @return the idPrograma
	 */
	public Integer getIdPrograma() {
		return idPrograma;
	}
	/**
	 * @param idPrograma the idPrograma to set
	 */
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	/**
	 * @return the idEstPersonalExt
	 */
	public Integer getIdEstPersonalExt() {
		return idEstPersonalExt;
	}
	/**
	 * @param idEstPersonalExt the idEstPersonalExt to set
	 */
	public void setIdEstPersonalExt(Integer idEstPersonalExt) {
		this.idEstPersonalExt = idEstPersonalExt;
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
		this.idPrograma = this.tblFichaDescriptivaPrograma.getIdPrograma();
	}
	/**
	 * @return the estPersonalExterno
	 */
	public EstPersonalExternoDTO getEstPersonalExterno() {
		return estPersonalExterno;
	}
	/**
	 * @param estPersonalExterno the estPersonalExterno to set
	 */
	public void setEstPersonalExterno(EstPersonalExternoDTO estPersonalExterno) {
		this.estPersonalExterno = estPersonalExterno;
		this.idEstPersonalExt = this.estPersonalExterno.getId();
	}
	
	
}
