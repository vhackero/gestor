package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.UUID;

public class ArchivoAdjuntoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String ruta;
	private String tipo;
	
	public ArchivoAdjuntoDTO() {
		this.ruta = UUID.randomUUID().toString();
	}
	
	public ArchivoAdjuntoDTO(String nombre, String tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.ruta = UUID.randomUUID().toString();
	}
	
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
