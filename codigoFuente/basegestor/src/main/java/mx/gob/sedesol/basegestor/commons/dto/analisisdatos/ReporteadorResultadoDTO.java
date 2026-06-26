package mx.gob.sedesol.basegestor.commons.dto.analisisdatos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReporteadorResultadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> columnas = new ArrayList<>();
	private List<List<Object>> filas = new ArrayList<>();

	public List<String> getColumnas() {
		return columnas;
	}

	public void setColumnas(List<String> columnas) {
		this.columnas = columnas;
	}

	public List<List<Object>> getFilas() {
		return filas;
	}

	public void setFilas(List<List<Object>> filas) {
		this.filas = filas;
	}
}
