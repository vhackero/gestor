package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;

public class RelSolicitudEnrolamientoEvtCapDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1317121050291933972L;

	private Long idPersona;

	private Integer idEventoCapacitacion;

	private Boolean activo;

	private Date fechaActualizacion;

	private Date fechaRegistro;

	private Long usuarioModifico;

	private EventoCapacitacionDTO tblEvento;

	private PersonaDTO tblPersona;

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public Integer getIdEventoCapacitacion() {
		return idEventoCapacitacion;
	}

	public void setIdEventoCapacitacion(Integer idEventoCapacitacion) {
		this.idEventoCapacitacion = idEventoCapacitacion;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
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

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public EventoCapacitacionDTO getTblEvento() {
		return this.tblEvento;
	}

	public void setTblEvento(EventoCapacitacionDTO tblEvento) {
		this.tblEvento = tblEvento;
	}

	public PersonaDTO getTblPersona() {
		return this.tblPersona;
	}

	public void setTblPersona(PersonaDTO tblPersona) {
		this.tblPersona = tblPersona;
	}

}
