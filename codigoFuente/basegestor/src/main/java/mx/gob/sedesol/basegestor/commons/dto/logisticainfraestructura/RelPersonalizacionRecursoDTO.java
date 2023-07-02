package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.util.Date;


public class RelPersonalizacionRecursoDTO {
	
	private Integer idPersonalizacionArea;

	private Integer idRecurso;
	
	private boolean activo;

	private Integer cantidad;
	
	private Date fechaRegistro;

	private Long usuarioRegistro;

	private PersonalizacionAreaDTO tblPersonalizacionArea;
	
	private RecursosInfraestructuraDTO recursoInfraestructura;
        

	/**
	 * @return the idPersonalizacionArea
	 */
	public Integer getIdPersonalizacionArea() {
		return idPersonalizacionArea;
	}

	/**
	 * @param idPersonalizacionArea the idPersonalizacionArea to set
	 */
	public void setIdPersonalizacionArea(Integer idPersonalizacionArea) {
		this.idPersonalizacionArea = idPersonalizacionArea;
	}

	/**
	 * @return the idRecurso
	 */
	public Integer getIdRecurso() {
		return idRecurso;
	}

	/**
	 * @param idRecurso the idRecurso to set
	 */
	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
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
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
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
	 * @return the tblPersonalizacionArea
	 */
	public PersonalizacionAreaDTO getTblPersonalizacionArea() {
		return tblPersonalizacionArea;
	}

	/**
	 * @param tblPersonalizacionArea the tblPersonalizacionArea to set
	 */
	public void setTblPersonalizacionArea(PersonalizacionAreaDTO tblPersonalizacionArea) {
		this.tblPersonalizacionArea = tblPersonalizacionArea;
	}

	/**
	 * @return the recursoInfraestructura
	 */
	public RecursosInfraestructuraDTO getRecursoInfraestructura() {
		return recursoInfraestructura;
	}

	/**
	 * @param recursoInfraestructura the recursoInfraestructura to set
	 */
	public void setRecursoInfraestructura(RecursosInfraestructuraDTO recursoInfraestructura) {
		this.recursoInfraestructura = recursoInfraestructura;
	}



}
