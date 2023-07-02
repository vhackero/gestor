package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;

public class ReponsableProduccionOaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idRelResponsableProduccionOa;

	private Integer idResponsableProduccion;

	private Integer idUnidadOa;

	private Date fechaActualizacion;

	private Date fechaRegistro;

	private BigInteger usuarioModifico;

	private PersonaResponsabilidadesDTO personaResponsabilidade;

	private UnidadOaAvaDTO unidadOaAva;

	public ReponsableProduccionOaDTO() {
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Integer getIdResponsableProduccion() {
		return idResponsableProduccion;
	}

	public void setIdResponsableProduccion(Integer idResponsableProduccion) {
		this.idResponsableProduccion = idResponsableProduccion;
	}

	public Integer getIdUnidadOa() {
		return idUnidadOa;
	}

	public void setIdUnidadOa(Integer idUnidadOa) {
		this.idUnidadOa = idUnidadOa;
	}

	public PersonaResponsabilidadesDTO getPersonaResponsabilidade() {
		return personaResponsabilidade;
	}

	public void setPersonaResponsabilidade(PersonaResponsabilidadesDTO personaResponsabilidade) {
		this.personaResponsabilidade = personaResponsabilidade;
	}

	public UnidadOaAvaDTO getUnidadOaAva() {
		return unidadOaAva;
	}

	public void setUnidadOaAva(UnidadOaAvaDTO unidadOaAva) {

		this.unidadOaAva = unidadOaAva;
		this.idUnidadOa = this.unidadOaAva.getId();
	}

	public Integer getIdRelResponsableProduccionOa() {
		return idRelResponsableProduccionOa;
	}

	public void setIdRelResponsableProduccionOa(Integer idRelResponsableProduccionOa) {
		this.idRelResponsableProduccionOa = idRelResponsableProduccionOa;
	}

}
