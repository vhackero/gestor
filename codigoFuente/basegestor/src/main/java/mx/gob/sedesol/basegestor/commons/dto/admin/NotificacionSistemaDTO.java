package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

public class NotificacionSistemaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idNotificacion;
	private String titulo;
	private String mensaje;
	private boolean activo;
	private int tipo;
	private Date fechaEnvio;
	private Date fechaVisualizacion;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	public Date getFechaVisualizacion() {
		return fechaVisualizacion;
	}
	public void setFechaVisualizacion(Date fechaVisualizacion) {
		this.fechaVisualizacion = fechaVisualizacion;
	}
	public String getIdNotificacion() {
		return idNotificacion;
	}
	public void setIdNotificacion(String idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
	
	public boolean isNuevo() {
		return fechaVisualizacion == null;
	}

}
