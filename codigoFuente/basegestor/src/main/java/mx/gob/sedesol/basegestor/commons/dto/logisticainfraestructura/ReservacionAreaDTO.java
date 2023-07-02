package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;

public class ReservacionAreaDTO {
	private Integer idReservacion;
	private boolean activo;
	private Date fechaFinalReservacion;
	private Date fechaInicialReservacion;
	private Date fechaRegistro;
	private Long usuarioRegistro;
	private AreaSedeDTO catAreasSede;
	private EventoCapacitacionDTO tblEvento;
	private EventoGeneralDTO tblEventoGeneral;
	private CatalogoComunDTO estatusReservacion;
	private PersonalizacionAreaDTO personalizacionArea;
	private String emailSolicitante;
	private Integer eventoPrivado;
	private String solicitante;
	
	/**
	 * @return the idReservacion
	 */
	public Integer getIdReservacion() {
		return idReservacion;
	}
	/**
	 * @param idReservacion the idReservacion to set
	 */
	public void setIdReservacion(Integer idReservacion) {
		this.idReservacion = idReservacion;
	}
	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	/**
	 * @return the fechaFinalReservacion
	 */
	public Date getFechaFinalReservacion() {
		return fechaFinalReservacion;
	}
	/**
	 * @param fechaFinalReservacion the fechaFinalReservacion to set
	 */
	public void setFechaFinalReservacion(Date fechaFinalReservacion) {
		this.fechaFinalReservacion = fechaFinalReservacion;
	}
	/**
	 * @return the fechaInicialReservacion
	 */
	public Date getFechaInicialReservacion() {
		return fechaInicialReservacion;
	}
	/**
	 * @param fechaInicialReservacion the fechaInicialReservacion to set
	 */
	public void setFechaInicialReservacion(Date fechaInicialReservacion) {
		this.fechaInicialReservacion = fechaInicialReservacion;
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
	 * @return the usuarioRegistro
	 */
	public Long getUsuarioRegistro() {
		return usuarioRegistro;
	}
	/**
	 * @param usuarioRegistro the usuarioRegistro to set
	 */
	public void setUsuarioRegistro(Long usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}
	/**
	 * @return the catAreasSede
	 */
	public AreaSedeDTO getCatAreasSede() {
		return catAreasSede;
	}
	/**
	 * @param catAreasSede the catAreasSede to set
	 */
	public void setCatAreasSede(AreaSedeDTO catAreasSede) {
		this.catAreasSede = catAreasSede;
	}
	/**
	 * @return the tblEvento
	 */
	public EventoCapacitacionDTO getTblEvento() {
		return tblEvento;
	}
	/**
	 * @param tblEvento the tblEvento to set
	 */
	public void setTblEvento(EventoCapacitacionDTO tblEvento) {
		this.tblEvento = tblEvento;
	}
	/**
	 * @return the tblEventoGeneral
	 */
	public EventoGeneralDTO getTblEventoGeneral() {
		return tblEventoGeneral;
	}
	/**
	 * @param tblEventoGeneral the tblEventoGeneral to set
	 */
	public void setTblEventoGeneral(EventoGeneralDTO tblEventoGeneral) {
		this.tblEventoGeneral = tblEventoGeneral;
	}
	/**
	 * @return the estatusReservacion
	 */
	public CatalogoComunDTO getEstatusReservacion() {
		return estatusReservacion;
	}
	/**
	 * @param estatusReservacion the estatusReservacion to set
	 */
	public void setEstatusReservacion(CatalogoComunDTO estatusReservacion) {
		this.estatusReservacion = estatusReservacion;
	}
	/**
	 * @return the personalizacionArea
	 */
	public PersonalizacionAreaDTO getPersonalizacionArea() {
		return personalizacionArea;
	}
	/**
	 * @param personalizacionArea the personalizacionArea to set
	 */
	public void setPersonalizacionArea(PersonalizacionAreaDTO personalizacionArea) {
		this.personalizacionArea = personalizacionArea;
	}
	/**
	 * @return the emailSolicitante
	 */
	public String getEmailSolicitante() {
		return emailSolicitante;
	}
	/**
	 * @param emailSolicitante the emailSolicitante to set
	 */
	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}
	/**
	 * @return the eventoPrivado
	 */
	public Integer getEventoPrivado() {
		return eventoPrivado;
	}
	/**
	 * @param eventoPrivado the eventoPrivado to set
	 */
	public void setEventoPrivado(Integer eventoPrivado) {
		this.eventoPrivado = eventoPrivado;
	}
	/**
	 * @return the solicitante
	 */
	public String getSolicitante() {
		return solicitante;
	}
	/**
	 * @param solicitante the solicitante to set
	 */
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	
}
