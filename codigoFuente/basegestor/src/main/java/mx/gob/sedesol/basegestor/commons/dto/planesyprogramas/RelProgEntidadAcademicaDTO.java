package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;


public class RelProgEntidadAcademicaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Integer idEntidadAcademicaResp;
	private Long usuarioModifico;
	private FichaDescProgramaDTO fichaDescriptivaPrograma;
	private EntidadesDTO tblEntidad;
	private Integer idPrograma;
	
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
	public Integer getIdEntidadAcademicaResp() {
		return idEntidadAcademicaResp;
	}
	public void setIdEntidadAcademicaResp(Integer idEntidadAcademicaResp) {
		this.idEntidadAcademicaResp = idEntidadAcademicaResp;
	}
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	public FichaDescProgramaDTO getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}
	public void setFichaDescriptivaPrograma(FichaDescProgramaDTO fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
		this.setIdPrograma(this.fichaDescriptivaPrograma.getIdPrograma());
	}
	/**
	 * @return the tblEntidad
	 */
	public EntidadesDTO getTblEntidad() {
		return tblEntidad;
	}
	/**
	 * @param tblEntidad the tblEntidad to set
	 */
	public void setTblEntidad(EntidadesDTO tblEntidad) {
		this.tblEntidad = tblEntidad;
		this.setIdEntidadAcademicaResp(this.tblEntidad.getId());
	}
	/**
	 * @return the idPrograma
	 */
	public Integer getIdPrograma() {
		return idPrograma;
	}
	/**
	 * @param idPrograma the idPrograma to set
	 */
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
}
