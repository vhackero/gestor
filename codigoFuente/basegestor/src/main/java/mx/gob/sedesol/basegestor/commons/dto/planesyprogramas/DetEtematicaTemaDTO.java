package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class DetEtematicaTemaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Integer idDetTema;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String nombreTema;
	private Long usuarioModifico;
	private EstructuraTematicaDTO tblEstructuraTematica;
	private List<RelEstUnidadDidacticaDTO> relEstructuraUnidadDidacticas;
	
	/**
	 * @return the idDetTema
	 */
	public Integer getIdDetTema() {
		return idDetTema;
	}
	/**
	 * @param idDetTema the idDetTema to set
	 */
	public void setIdDetTema(Integer idDetTema) {
		this.idDetTema = idDetTema;
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
	 * @return the nombreTema
	 */
	public String getNombreTema() {
		return nombreTema;
	}
	/**
	 * @param nombreTema the nombreTema to set
	 */
	public void setNombreTema(String nombreTema) {
		this.nombreTema = nombreTema;
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
	 * @return the tblEstructuraTematica
	 */
	public EstructuraTematicaDTO getTblEstructuraTematica() {
		return tblEstructuraTematica;
	}
	/**
	 * @param tblEstructuraTematica the tblEstructuraTematica to set
	 */
	public void setTblEstructuraTematica(EstructuraTematicaDTO tblEstructuraTematica) {
		this.tblEstructuraTematica = tblEstructuraTematica;
	}
	
	
	
	/**
	 * @return the relEstructuraUnidadDidactica
	 */
	
	public List<RelEstUnidadDidacticaDTO> getRelEstructuraUnidadDidacticas() {
		return relEstructuraUnidadDidacticas;
	}
	
	/**
	 * @param relEstructuraUnidadDidactica the relEstructuraUnidadDidactica to set
	 */
	
	public void setRelEstructuraUnidadDidacticas(List<RelEstUnidadDidacticaDTO> relEstructuraUnidadDidacticas) {
		this.relEstructuraUnidadDidacticas = relEstructuraUnidadDidacticas;
	}
	

	
	
}
