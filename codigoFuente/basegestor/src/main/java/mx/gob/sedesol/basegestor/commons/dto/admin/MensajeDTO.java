package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import org.jsoup.Jsoup;

public class MensajeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String contenido;
	private long remitente;
	private String nombreCompleto;
	private Date fecha;
	private ArchivoAdjuntoDTO archivo;
	
	public MensajeDTO() {
		this.fecha = new Date();
	}
	
	public MensajeDTO(long remitente) {
		this.remitente = remitente;
		this.fecha = new Date();
	}
	
	public MensajeDTO(PersonaDTO remitente) {
		this.remitente = remitente.getIdPersona();
		this.nombreCompleto = remitente.getNombreCompleto();
		this.fecha = new Date();
	}
	
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public long getRemitente() {
		return remitente;
	}
	public void setRemitente(long remitente) {
		this.remitente = remitente;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ArchivoAdjuntoDTO getArchivo() {
		return archivo;
	}

	public void setArchivo(ArchivoAdjuntoDTO archivo) {
		this.archivo = archivo;
	}
	
	public String getContenidoResumen() {
		String resumen = Jsoup.parse(this.contenido).text();
		if (resumen.length() > 20) {
			return resumen.substring(0, 20) + "...";
		} else {
			return resumen;
		}
	}

}
