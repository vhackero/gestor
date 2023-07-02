package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

public class EventoGeneralDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idEventoGeneral;
	private String correoSolicitante;
	private Integer eventoPrivado;
	private Date fechaRegistro;
	private String nombre;
	private String solicitante;
	private Long usuarioModifico;
	private AreaSedeDTO catAreasSede;

	/**
	 * @return the idEventoGeneral
	 */
	public Integer getIdEventoGeneral() {
		return idEventoGeneral;
	}

	/**
	 * @param idEventoGeneral
	 *            the idEventoGeneral to set
	 */
	public void setIdEventoGeneral(Integer idEventoGeneral) {
		this.idEventoGeneral = idEventoGeneral;
	}

	/**
	 * @return the correoSolicitante
	 */
	public String getCorreoSolicitante() {
		return correoSolicitante;
	}

	/**
	 * @param correoSolicitante
	 *            the correoSolicitante to set
	 */
	public void setCorreoSolicitante(String correoSolicitante) {
		this.correoSolicitante = correoSolicitante;
	}

	/**
	 * @return the eventoPrivado
	 */
	public Integer isEventoPrivado() {
		return eventoPrivado;
	}

	/**
	 * @param eventoPrivado
	 *            the eventoPrivado to set
	 */
	public void setEventoPrivado(Integer eventoPrivado) {
		this.eventoPrivado = eventoPrivado;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the solicitante
	 */
	public String getSolicitante() {
		return solicitante;
	}

	/**
	 * @param solicitante
	 *            the solicitante to set
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
	 * @param usuarioModifico
	 *            the usuarioModifico to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the catAreasSede
	 */
	public AreaSedeDTO getCatAreasSede() {
		return catAreasSede;
	}

	/**
	 * @param catAreasSede
	 *            the catAreasSede to set
	 */
	public void setCatAreasSede(AreaSedeDTO catAreasSede) {
		this.catAreasSede = catAreasSede;
	}

}
