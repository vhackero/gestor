package mx.gob.sedesol.basegestor.commons.dto.eventos;

import java.util.Date;
import java.util.List;

public class EventoPresencialDTO {
	
	private long idEventoPresencial;
	private String nombre;
	private String tipoAsistentes;
	private boolean visible;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private long usuarioModifico;
	private List<FechaEventoPresencialDTO> fechasEventoPresencial;
	
	public long getIdEventoPresencial() {
		return idEventoPresencial;
	}
	public void setIdEventoPresencial(long idEventoPresencial) {
		this.idEventoPresencial = idEventoPresencial;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipoAsistentes() {
		return tipoAsistentes;
	}
	public void setTipoAsistentes(String tipoAsistentes) {
		this.tipoAsistentes = tipoAsistentes;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
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
	public long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	public List<FechaEventoPresencialDTO> getFechasEventoPresencial() {
		return fechasEventoPresencial;
	}
	public void setFechasEventoPresencial(List<FechaEventoPresencialDTO> fechasEventoPresencial) {
		this.fechasEventoPresencial = fechasEventoPresencial;
	}


}
