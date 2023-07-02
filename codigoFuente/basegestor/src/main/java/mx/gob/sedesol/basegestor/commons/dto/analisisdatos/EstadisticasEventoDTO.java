package mx.gob.sedesol.basegestor.commons.dto.analisisdatos;

import java.util.Map;

public class EstadisticasEventoDTO {
	
	private Integer totalEventos;
	private Map<String,Integer> nombreCompetenciaCantidad;
	private Map<String,Integer> nombreEjeCapCantidad;
	private Map<String,Integer> nombreProgCantidad;
	private Map<String,Integer> nombreModalidadCantidad;
	private Map<String,Integer> nombreClasificacionCantidad;
	private Map<String,Integer> nombreTipoCantidad;
	private Map<String,Integer> nombreDirigidoCantidad;
	private Map<String,Integer> nombreEntidadCantidad;
	
	public Integer getTotalEventos() {
		return totalEventos;
	}
	public void setTotalEventos(Integer totalEventos) {
		this.totalEventos = totalEventos;
	}
	public Map<String,Integer> getNombreCompetenciaCantidad() {
		return nombreCompetenciaCantidad;
	}
	public void setNombreCompetenciaCantidad(Map<String,Integer> nombreCompetenciaCantidad) {
		this.nombreCompetenciaCantidad = nombreCompetenciaCantidad;
	}
	public Map<String,Integer> getNombreEjeCapCantidad() {
		return nombreEjeCapCantidad;
	}
	public void setNombreEjeCapCantidad(Map<String,Integer> nombreEjeCapCantidad) {
		this.nombreEjeCapCantidad = nombreEjeCapCantidad;
	}
	public Map<String,Integer> getNombreProgCantidad() {
		return nombreProgCantidad;
	}
	public void setNombreProgCantidad(Map<String,Integer> nombreProgCantidad) {
		this.nombreProgCantidad = nombreProgCantidad;
	}
	public Map<String,Integer> getNombreModalidadCantidad() {
		return nombreModalidadCantidad;
	}
	public void setNombreModalidadCantidad(Map<String,Integer> nombreModalidadCantidad) {
		this.nombreModalidadCantidad = nombreModalidadCantidad;
	}
	public Map<String,Integer> getNombreClasificacionCantidad() {
		return nombreClasificacionCantidad;
	}
	public void setNombreClasificacionCantidad(Map<String,Integer> nombreClasificacionCantidad) {
		this.nombreClasificacionCantidad = nombreClasificacionCantidad;
	}
	public Map<String,Integer> getNombreTipoCantidad() {
		return nombreTipoCantidad;
	}
	public void setNombreTipoCantidad(Map<String,Integer> nombreTipoCantidad) {
		this.nombreTipoCantidad = nombreTipoCantidad;
	}
	public Map<String,Integer> getNombreDirigidoCantidad() {
		return nombreDirigidoCantidad;
	}
	public void setNombreDirigidoCantidad(Map<String,Integer> nombreDirigidoCantidad) {
		this.nombreDirigidoCantidad = nombreDirigidoCantidad;
	}
	public Map<String,Integer> getNombreEntidadCantidad() {
		return nombreEntidadCantidad;
	}
	public void setNombreEntidadCantidad(Map<String,Integer> nombreEntidadCantidad) {
		this.nombreEntidadCantidad = nombreEntidadCantidad;
	}
	

	
	
	
}
