package mx.gob.sedesol.basegestor.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ArchivoAdjunto {
	
	private String nombre;
	private String ruta;
	private String tipo;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
