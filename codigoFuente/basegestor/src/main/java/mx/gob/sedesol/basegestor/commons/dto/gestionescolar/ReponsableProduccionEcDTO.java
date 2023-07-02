package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class ReponsableProduccionEcDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idEvtCapacitacion;

	private Integer idReponsableProduccion;

	private Date fechaActualizacion;

	private Date fechaRegistro;

	private BigInteger usuarioModifico;

	private Boolean esResponsablePrincipal;

	private PersonaResponsabilidadesDTO personaResponsabilidad;

	private EventoCapacitacionDTO eventoCapacitacion;

	public ReponsableProduccionEcDTO() {
	}

	public Boolean getEsResponsablePrincipal() {
		return esResponsablePrincipal;
	}

	public void setEsResponsablePrincipal(Boolean esResponsablePrincipal) {
		this.esResponsablePrincipal = esResponsablePrincipal;
	}

	public Integer getIdEvtCapacitacion() {
		return idEvtCapacitacion;
	}

	public void setIdEvtCapacitacion(Integer idEvtCapacitacion) {
		this.idEvtCapacitacion = idEvtCapacitacion;
	}

	public Integer getIdReponsableProduccion() {
		return idReponsableProduccion;
	}

	public void setIdReponsableProduccion(Integer idReponsableProduccion) {
		this.idReponsableProduccion = idReponsableProduccion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigInteger getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public PersonaResponsabilidadesDTO getPersonaResponsabilidad() {
		return personaResponsabilidad;
	}

	public void setPersonaResponsabilidad(PersonaResponsabilidadesDTO personaResponsabilidad) {
		this.personaResponsabilidad = personaResponsabilidad;
		this.idReponsableProduccion = this.personaResponsabilidad.getId();
	}

	public EventoCapacitacionDTO getEventoCapacitacion() {
		return eventoCapacitacion;
	}

	public void setEventoCapacitacion(EventoCapacitacionDTO eventoCapacitacion) {
		this.eventoCapacitacion = eventoCapacitacion;
		this.idEvtCapacitacion = this.eventoCapacitacion.getIdEvento();
	}


}
