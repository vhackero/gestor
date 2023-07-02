package mx.gob.sedesol.basegestor.mongo.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Mensaje {
	
	private String contenido;
	private long remitente;
	private Date fecha;
	private ArchivoAdjunto archivo;
	
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public ArchivoAdjunto getArchivo() {
		return archivo;
	}
	public void setArchivo(ArchivoAdjunto archivo) {
		this.archivo = archivo;
	}

}
