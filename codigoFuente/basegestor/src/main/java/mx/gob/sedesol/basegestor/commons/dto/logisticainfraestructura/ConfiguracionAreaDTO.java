package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;


public class ConfiguracionAreaDTO implements Serializable{

	private static final long serialVersionUID = 5136508793301800027L;
	
	private Integer idConfigArea;
	private String emailCc;
	@Email(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_EMAIL)
	private String emailResponsable;
	//@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	@Email(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_EMAIL)
	private String emailSolicitante;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	@NotNull(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private CatalogoComunDTO catEstatusArea;
	private String rutaImgDos;
	private String rutaImgTres;
	private String rutaImgUno;
	private String solicitante;
	private Long usuarioModifico;
	private List<RelAreaDistribucionDTO> relAreaDistribucion;
	private List<RelAreaRecursoDTO> relAreaRecursos;
	private AreaSedeDTO catAreasSede;
	/**
	 * @return the idConfigArea
	 */
	public Integer getIdConfigArea() {
		return idConfigArea;
	}
	/**
	 * @param idConfigArea the idConfigArea to set
	 */
	public void setIdConfigArea(Integer idConfigArea) {
		this.idConfigArea = idConfigArea;
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
	 * @return the rutaImgDos
	 */
	public String getRutaImgDos() {
		return rutaImgDos;
	}
	/**
	 * @param rutaImgDos the rutaImgDos to set
	 */
	public void setRutaImgDos(String rutaImgDos) {
		this.rutaImgDos = rutaImgDos;
	}
	/**
	 * @return the rutaImgTres
	 */
	public String getRutaImgTres() {
		return rutaImgTres;
	}
	/**
	 * @param rutaImgTres the rutaImgTres to set
	 */
	public void setRutaImgTres(String rutaImgTres) {
		this.rutaImgTres = rutaImgTres;
	}
	/**
	 * @return the rutaImgUno
	 */
	public String getRutaImgUno() {
		return rutaImgUno;
	}
	/**
	 * @param rutaImgUno the rutaImgUno to set
	 */
	public void setRutaImgUno(String rutaImgUno) {
		this.rutaImgUno = rutaImgUno;
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
	 * @return the relAreaDistribucion
	 */
	public List<RelAreaDistribucionDTO> getRelAreaDistribucion() {
		return relAreaDistribucion;
	}
	/**
	 * @param relAreaDistribucion the relAreaDistribucion to set
	 */
	public void setRelAreaDistribucion(List<RelAreaDistribucionDTO> relAreaDistribucion) {
		this.relAreaDistribucion = relAreaDistribucion;
	}
	/**
	 * @return the relAreaRecursos
	 */
	public List<RelAreaRecursoDTO> getRelAreaRecursos() {
		return relAreaRecursos;
	}
	/**
	 * @param relAreaRecursos the relAreaRecursos to set
	 */
	public void setRelAreaRecursos(List<RelAreaRecursoDTO> relAreaRecursos) {
		this.relAreaRecursos = relAreaRecursos;
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
	public CatalogoComunDTO getCatEstatusArea() {
		return catEstatusArea;
	}
	public void setCatEstatusArea(CatalogoComunDTO catEstatusArea) {
		this.catEstatusArea = catEstatusArea;
	}


	
}
