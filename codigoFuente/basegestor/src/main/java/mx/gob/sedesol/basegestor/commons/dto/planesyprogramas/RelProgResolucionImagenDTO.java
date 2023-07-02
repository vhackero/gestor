package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

public class RelProgResolucionImagenDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String nombreImagen;
	private Long usuarioModifico;
	private FichaDescProgramaDTO fichaDescriptivaPrograma;
	
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
	public String getNombreImagen() {
		return nombreImagen;
	}
	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
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
	}


}
