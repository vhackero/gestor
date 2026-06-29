package mx.gob.sedesol.basegestor.commons.dto.analisisdatos;

import java.io.Serializable;

public class ReporteadorOpcionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String valor;
	private String nombre;

	public ReporteadorOpcionDTO() {
	}

	public ReporteadorOpcionDTO(String valor, String nombre) {
		this.valor = valor;
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
