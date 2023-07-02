package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;


public class PersonaCorreoDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idPersonaCorreo;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	@Email(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_EMAIL)
	private String correoElectronico;
	private Integer nivelPrioridad;
	private TipoCorreoDTO tipoCorreo;
	private Integer activo;
	
	private PersonaDTO persona;
	
	public PersonaCorreoDTO() {
		setFechaRegistro(new Date());
		this.activo = 1;
	}
	
	public PersonaCorreoDTO(long usuarioModifico, int idTipoCorreo) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.activo = 1;
		this.tipoCorreo = new TipoCorreoDTO();
		this.tipoCorreo.setIdTipoCorreo(idTipoCorreo);
	}
	
	/**
	 * @return the idPersonaCorreo
	 */
	public Integer getIdPersonaCorreo() {
		return idPersonaCorreo;
	}
	/**
	 * @param idPersonaCorreo the idPersonaCorreo to set
	 */
	public void setIdPersonaCorreo(Integer idPersonaCorreo) {
		this.idPersonaCorreo = idPersonaCorreo;
	}
	/**
	 * @return the correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	/**
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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
	 * @return the tipoCorreo
	 */
	public TipoCorreoDTO getTipoCorreo() {
		return tipoCorreo;
	}
	/**
	 * @param tipoCorreo the tipoCorreo to set
	 */
	public void setTipoCorreo(TipoCorreoDTO tipoCorreo) {
		this.tipoCorreo = tipoCorreo;
	}
	/**
	 * @return the nivelPrioridad
	 */
	public Integer getNivelPrioridad() {
		return nivelPrioridad;
	}
	/**
	 * @param nivelPrioridad the nivelPrioridad to set
	 */
	public void setNivelPrioridad(Integer nivelPrioridad) {
		this.nivelPrioridad = nivelPrioridad;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

}
