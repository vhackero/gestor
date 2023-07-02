package mx.gob.sedesol.gestorweb.commons.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ReporteConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Ruta del archivo jasper 
	 */
	private String pathJasper;
	/**
	 * Mapa de parametros para el reporte
	 */
	private Map<String, Object> parametros;
	/**
	 * Lista de beans que representan los datos del reporte
	 */
	private List<? extends Object> datos;
	/**
	 * Nombre del reporte
	 */
	private String nombreReporte;
	/**
	 * Tipo de reporte
	 */
	private Integer tipoReporte;

	public String getPathJasper() {
		return pathJasper;
	}

	public void setPathJasper(String pathJasper) {
		this.pathJasper = pathJasper;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public List<? extends Object> getDatos() {
		return datos;
	}

	public void setDatos(List<? extends Object> datos) {
		this.datos = datos;
	}

	public String getNombreReporte() {
		return nombreReporte;
	}

	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}

	public Integer getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(Integer tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

}
