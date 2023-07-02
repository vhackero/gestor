package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;

public class ReservacionEventoCapacitacionDTO implements Serializable {
	
	private static final long serialVersionUID = 6789402272875790791L;
	private Integer idReservacionEc;
	private Byte activo;
	private Byte eventoPrivado;
	private Date fechaFinalReservacion;
	private Date fechaInicialReservacion;
	private Date fechaModificacion;
	private Date fechaRegistro;
	private Integer idAreaSede;
	private CatalogoComunDTO catEstatusReservacion;
	private Integer idPersonalizacionArea;
	private Long usuarioRegistro;
	private EventoCapacitacionDTO tblEvento;
	private PersonalizacionAreaDTO personalizacionAreaDTO;

	public Integer getIdReservacionEc() {
		return idReservacionEc;
	}

	public void setIdReservacionEc(Integer idReservacionEc) {
		this.idReservacionEc = idReservacionEc;
	}

	public Byte getActivo() {
		return activo;
	}

	public void setActivo(Byte activo) {
		this.activo = activo;
	}

	public Byte getEventoPrivado() {
		return eventoPrivado;
	}

	public void setEventoPrivado(Byte eventoPrivado) {
		this.eventoPrivado = eventoPrivado;
	}

	public Date getFechaFinalReservacion() {
		return fechaFinalReservacion;
	}

	public void setFechaFinalReservacion(Date fechaFinalReservacion) {
		this.fechaFinalReservacion = fechaFinalReservacion;
	}

	public Date getFechaInicialReservacion() {
		return fechaInicialReservacion;
	}

	public void setFechaInicialReservacion(Date fechaInicialReservacion) {
		this.fechaInicialReservacion = fechaInicialReservacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getIdAreaSede() {
		return idAreaSede;
	}

	public void setIdAreaSede(Integer idAreaSede) {
		this.idAreaSede = idAreaSede;
	}

	public CatalogoComunDTO getCatEstatusReservacion() {
		return catEstatusReservacion;
	}

	public void setCatEstatusReservacion(CatalogoComunDTO catEstatusReservacion) {
		this.catEstatusReservacion = catEstatusReservacion;
	}

	public Integer getIdPersonalizacionArea() {
		return idPersonalizacionArea;
	}

	public void setIdPersonalizacionArea(Integer idPersonalizacionArea) {
		this.idPersonalizacionArea = idPersonalizacionArea;
	}

	public Long getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(Long usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public EventoCapacitacionDTO getTblEvento() {
		return tblEvento;
	}

	public void setTblEvento(EventoCapacitacionDTO tblEvento) {
		this.tblEvento = tblEvento;
	}

	public PersonalizacionAreaDTO getPersonalizacionAreaDTO() {
		return personalizacionAreaDTO;
	}

	public void setPersonalizacionAreaDTO(PersonalizacionAreaDTO personalizacionAreaDTO) {
		this.personalizacionAreaDTO = personalizacionAreaDTO;
	}
	
}
