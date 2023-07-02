package mx.gob.sedesol.basegestor.commons.dto.encuestas;

import java.util.Date;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoParticipante;

public class RelEncuestaUsuarioDTO {
	private Integer idEncuesta;

	private Integer idGrupoParticipante;

	private Boolean activo;

	private Date fechaActualizacion;

	private Date fechaApertura;

	private Date fechaRegistro;

	private Long usuarioModifico;

	private RelGrupoParticipante relGrupoParticipante;

	private EncuestaDTO tblEncuesta;

	/**
	 * @return the idEncuesta
	 */
	public Integer getIdEncuesta() {
		return idEncuesta;
	}

	/**
	 * @param idEncuesta
	 *            the idEncuesta to set
	 */
	public void setIdEncuesta(Integer idEncuesta) {
		this.idEncuesta = idEncuesta;
	}

	/**
	 * @return the IdGrupoParticipante
	 */
	public Integer getIdGrupoParticipante() {
		return idGrupoParticipante;
	}

	/**
	 * @param IdGrupoParticipante
	 *            the IdGrupoParticipante to set
	 */
	public void setIdGrupoParticipante(Integer idGrupoParticipante) {
		this.idGrupoParticipante = idGrupoParticipante;
	}

	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion
	 *            the fechaActualizacion to set
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
	 * @param fechaRegistro
	 *            the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	 * @return the relGrupoParticipante
	 */
	public RelGrupoParticipante getRelGrupoParticipante() {
		return relGrupoParticipante;
	}

	/**
	 * @param relGrupoParticipante
	 *            the relGrupoParticipante to set
	 */
	public void setRelGrupoParticipante(RelGrupoParticipante relGrupoParticipante) {
		this.relGrupoParticipante = relGrupoParticipante;
	}

	/**
	 * @return the tblEncuesta
	 */
	public EncuestaDTO getTblEncuesta() {
		return tblEncuesta;
	}

	/**
	 * @param tblEncuesta
	 *            the tblEncuesta to set
	 */
	public void setTblEncuesta(EncuestaDTO tblEncuesta) {
		this.tblEncuesta = tblEncuesta;
	}

	/**
	 * @return the fechaApertura
	 */
	public Date getFechaApertura() {
		return fechaApertura;
	}

	/**
	 * @param fechaApertura
	 *            the fechaApertura to set
	 */
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}


}
