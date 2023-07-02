package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class NotificacionDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date fechaEnvio;
	private String id;
	private String titulo;
	private boolean enviado;
	private String rutaArchivos;
	
	private List<DestinatarioDTO> destinatarios;
	private List<MensajeDTO> mensajes;
	
	private boolean nuevo;
	
	public NotificacionDTO() {
		setFechaRegistro(new Date());
		this.enviado = false;
		this.destinatarios = new ArrayList<>();
		this.mensajes = new ArrayList<>();
		this.rutaArchivos = UUID.randomUUID().toString();
	}

	public NotificacionDTO(Long usuarioModifico) {
		setFechaRegistro(new Date());
		setUsuarioModifico(usuarioModifico);
		this.enviado = false;
		this.destinatarios = new ArrayList<>();
		this.mensajes = new ArrayList<>();
		this.rutaArchivos = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public boolean isEnviado() {
		return enviado;
	}
	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}

	public List<DestinatarioDTO> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(List<DestinatarioDTO> destinatarios) {
		this.destinatarios = destinatarios;
	}

	public List<MensajeDTO> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<MensajeDTO> mensajes) {
		this.mensajes = mensajes;
	}

	public String getRutaArchivos() {
		return rutaArchivos;
	}

	public void setRutaArchivos(String rutaArchivos) {
		this.rutaArchivos = rutaArchivos;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

}
