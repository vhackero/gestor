package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class LoteCargaUsuarioDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idLoteCargaUsuarios;
	private String nombre;
	private String rutaArchivo;
	private String nombreArchivo;
	
	private Date fechaInicio;
	private Date fechaFin;
	
	public LoteCargaUsuarioDTO() {
		setFechaRegistro(new Date());
		setFechaActualizacion(getFechaRegistro());
	}
	
	public LoteCargaUsuarioDTO(long usuarioModifico) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		setFechaActualizacion(getFechaRegistro());
	}
	
	public Integer getIdLoteCargaUsuarios() {
		return idLoteCargaUsuarios;
	}

	public void setIdLoteCargaUsuarios(Integer idLoteCargaUsuarios) {
		this.idLoteCargaUsuarios = idLoteCargaUsuarios;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
