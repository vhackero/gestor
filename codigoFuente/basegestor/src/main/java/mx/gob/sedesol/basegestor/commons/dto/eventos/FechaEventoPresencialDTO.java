package mx.gob.sedesol.basegestor.commons.dto.eventos;

import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.EspacioDTO;

public class FechaEventoPresencialDTO {
	
	private String idFechaEventoPresencial;
	private Date fechaFin;
	private Date fechaInicio;
	private EspacioDTO espacio;
	private EventoPresencialDTO eventoPresencial;
	
	public String getIdFechaEventoPresencial() {
		return idFechaEventoPresencial;
	}
	public void setIdFechaEventoPresencial(String idFechaEventoPresencial) {
		this.idFechaEventoPresencial = idFechaEventoPresencial;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public EspacioDTO getEspacio() {
		return espacio;
	}
	public void setEspacio(EspacioDTO espacio) {
		this.espacio = espacio;
	}
	public EventoPresencialDTO getEventoPresencial() {
		return eventoPresencial;
	}
	public void setEventoPresencial(EventoPresencialDTO eventoPresencial) {
		this.eventoPresencial = eventoPresencial;
	}

}
