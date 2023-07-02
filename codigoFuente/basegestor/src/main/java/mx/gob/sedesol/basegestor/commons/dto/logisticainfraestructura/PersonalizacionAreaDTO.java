package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;


public class PersonalizacionAreaDTO implements Serializable {
	
	private static final long serialVersionUID = 3273823072298143932L;
	
	private Integer idPersonalizacion;
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	@Email(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_EMAIL)
	private String emailResponsable;
	private String emailCc;
	private Integer eventoPrivado;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private AreaSedeDTO catAreasSede;
	private CatalogoComunDTO distribucionArea;
	private String observacionesAcademicas;
	private Integer servicioCafeteria;
	private Long usuarioModifico;
	
	private List<RelAreaRecursoDTO> recTec;
	private List<RelAreaRecursoDTO> recMob;
        
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
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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
	 * @return the observacionesAcademicas
	 */
	public String getObservacionesAcademicas() {
		return observacionesAcademicas;
	}
	/**
	 * @param observacionesAcademicas the observacionesAcademicas to set
	 */
	public void setObservacionesAcademicas(String observacionesAcademicas) {
		this.observacionesAcademicas = observacionesAcademicas;
	}
	/**
	 * @return the servicioCafeteria
	 */
	public Integer getServicioCafeteria() {
		return servicioCafeteria;
	}
	/**
	 * @param servicioCafeteria the servicioCafeteria to set
	 */
	public void setServicioCafeteria(Integer servicioCafeteria) {
		this.servicioCafeteria = servicioCafeteria;
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
	public CatalogoComunDTO getDistribucionArea() {
		return distribucionArea;
	}
	public void setDistribucionArea(CatalogoComunDTO distribucionArea) {
		this.distribucionArea = distribucionArea;
	}
	public Integer getIdPersonalizacion() {
		return idPersonalizacion;
	}
	public void setIdPersonalizacion(Integer idPersonalizacion) {
		this.idPersonalizacion = idPersonalizacion;
	}
	/**
	 * @return the emailResponsable
	 */
	public String getEmailResponsable() {
		return emailResponsable;
	}
	/**
	 * @param emailResponsable the emailResponsable to set
	 */
	public void setEmailResponsable(String emailResponsable) {
		this.emailResponsable = emailResponsable;
	}
	/**
	 * @return the emailCc
	 */
	public String getEmailCc() {
		return emailCc;
	}
	/**
	 * @param emailCc the emailCc to set
	 */
	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}
	public List<RelAreaRecursoDTO> getRecTec() {
		return recTec;
	}
	public void setRecTec(List<RelAreaRecursoDTO> recTec) {
		this.recTec = recTec;
	}
	public List<RelAreaRecursoDTO> getRecMob() {
		return recMob;
	}
	public void setRecMob(List<RelAreaRecursoDTO> recMob) {
		this.recMob = recMob;
	}

}
