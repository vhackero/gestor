package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.List;

public class ResultadoCargaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int totalRegistros;
	private int registrosValidos;
	private int registrosInvalidos;
	private List<PersonaCargaDTO> registros;
	private boolean correcto;
	private String rutaArchivo;
	private String nombreArchivo;
	private String rutaCompleta;
	public int getTotalRegistros() {
		return totalRegistros;
	}
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	public int getRegistrosValidos() {
		return registrosValidos;
	}
	public void setRegistrosValidos(int registrosValidos) {
		this.registrosValidos = registrosValidos;
	}
	public int getRegistrosInvalidos() {
		return registrosInvalidos;
	}
	public void setRegistrosInvalidos(int registrosInvalidos) {
		this.registrosInvalidos = registrosInvalidos;
	}
	public List<PersonaCargaDTO> getRegistros() {
		return registros;
	}
	public void setRegistros(List<PersonaCargaDTO> registros) {
		this.registros = registros;
	}
	public boolean isCorrecto() {
		return correcto;
	}
	public void setCorrecto(boolean correcto) {
		this.correcto = correcto;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getRutaCompleta() {
		return rutaCompleta;
	}
	public void setRutaCompleta(String rutaCompleta) {
		this.rutaCompleta = rutaCompleta;
	}
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

}
