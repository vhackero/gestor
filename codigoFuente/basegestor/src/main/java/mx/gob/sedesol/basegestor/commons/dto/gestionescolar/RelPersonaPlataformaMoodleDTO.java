package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;


public class RelPersonaPlataformaMoodleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idPersona;
	private Integer idPlataformaMoodle;
	private Integer idPersonaMoodle;
	private Date fechaRegistro;
	private Long usuarioModifico;
	
	
	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}
	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	/**
	 * @return the idPlataformaMoodle
	 */
	public Integer getIdPlataformaMoodle() {
		return idPlataformaMoodle;
	}
	/**
	 * @param idPlataformaMoodle the idPlataformaMoodle to set
	 */
	public void setIdPlataformaMoodle(Integer idPlataformaMoodle) {
		this.idPlataformaMoodle = idPlataformaMoodle;
	}
	/**
	 * @return the idPersonaMoodle
	 */
	public Integer getIdPersonaMoodle() {
		return idPersonaMoodle;
	}
	/**
	 * @param idPersonaMoodle the idPersonaMoodle to set
	 */
	public void setIdPersonaMoodle(Integer idPersonaMoodle) {
		this.idPersonaMoodle = idPersonaMoodle;
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
}
