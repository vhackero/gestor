package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CatalogoGeneralDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idCatGeneral;
	private Integer activo;
	private String claveCatalogo;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private List<DatoValorCatGeneralDTO> datosValorCatGeneral;
	
	/**
	 * @return the idCatGeneral
	 */
	public Integer getIdCatGeneral() {
		return idCatGeneral;
	}
	/**
	 * @param idCatGeneral the idCatGeneral to set
	 */
	public void setIdCatGeneral(Integer idCatGeneral) {
		this.idCatGeneral = idCatGeneral;
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
	 * @return the claveCatalogo
	 */
	public String getClaveCatalogo() {
		return claveCatalogo;
	}
	/**
	 * @param claveCatalogo the claveCatalogo to set
	 */
	public void setClaveCatalogo(String claveCatalogo) {
		this.claveCatalogo = claveCatalogo;
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
	 * @return the datosValorCatGeneral
	 */
	public List<DatoValorCatGeneralDTO> getDatosValorCatGeneral() {
		return datosValorCatGeneral;
	}
	/**
	 * @param datosValorCatGeneral the datosValorCatGeneral to set
	 */
	public void setDatosValorCatGeneral(List<DatoValorCatGeneralDTO> datosValorCatGeneral) {
		this.datosValorCatGeneral = datosValorCatGeneral;
	}


}
