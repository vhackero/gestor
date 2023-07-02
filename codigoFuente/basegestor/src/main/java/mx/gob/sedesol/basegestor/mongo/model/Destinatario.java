package mx.gob.sedesol.basegestor.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Destinatario {
	
	private long idPersona;
	private boolean nuevo;
	private boolean activo;
	
	public long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}
	public boolean isNuevo() {
		return nuevo;
	}
	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
