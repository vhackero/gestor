package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

public class ArchivoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String tipo;
	private byte[] datos;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public byte[] getDatos() {
		return datos;
	}
	public void setDatos(byte[] datos) {
		this.datos = datos;
	}

}
