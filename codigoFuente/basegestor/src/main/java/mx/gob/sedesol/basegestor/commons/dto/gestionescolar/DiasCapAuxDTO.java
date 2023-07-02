package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.Date;

public class DiasCapAuxDTO {
	
	private int  id;
	private Date fechaEventoCapacitacion;
	private String fechaEventoFormat;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFechaEventoCapacitacion() {
		return fechaEventoCapacitacion;
	}
	public void setFechaEventoCapacitacion(Date fechaEventoCapacitacion) {
		this.fechaEventoCapacitacion = fechaEventoCapacitacion;
	}
	public String getFechaEventoFormat() {
		return fechaEventoFormat;
	}
	public void setFechaEventoFormat(String fechaEventoFormat) {
		this.fechaEventoFormat = fechaEventoFormat;
	}
	
	
	
}
