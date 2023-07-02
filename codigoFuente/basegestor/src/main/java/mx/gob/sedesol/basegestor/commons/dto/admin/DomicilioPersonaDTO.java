package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;


public class DomicilioPersonaDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idDomicilioPersona;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String calle;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String numeroExterior;
	
	private String numeroInterior;

	private AsentamientoDTO asentamiento;
	
	private PersonaDTO persona;
	
	private Integer activo;
	
	private String idPais;
	private int idEntidadFederativa;
	private String idMunicipio;
	private String codigoPostal;
	
	public DomicilioPersonaDTO() {
		setFechaRegistro(new Date());
	}
	
	public DomicilioPersonaDTO(long usuarioModifico, String idPais) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.activo = 1;
		this.asentamiento = new AsentamientoDTO();
		this.idPais = idPais;
	}
	/**
	 * @return the idDomicilioPersona
	 */
	public Long getIdDomicilioPersona() {
		return idDomicilioPersona;
	}
	/**
	 * @param idDomicilioPersona the idDomicilioPersona to set
	 */
	public void setIdDomicilioPersona(Long idDomicilioPersona) {
		this.idDomicilioPersona = idDomicilioPersona;
	}
	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}
	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
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
	 * @return the asentamiento
	 */
	public AsentamientoDTO getAsentamiento() {
		return asentamiento;
	}
	/**
	 * @param asentamiento the asentamiento to set
	 */
	public void setAsentamiento(AsentamientoDTO asentamiento) {
		this.asentamiento = asentamiento;
	}
	public String getIdPais() {
		return idPais;
	}
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public int getIdEntidadFederativa() {
		return idEntidadFederativa;
	}
	public void setIdEntidadFederativa(int idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}
	public String getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getNumeroExterior() {
		return numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}
}
