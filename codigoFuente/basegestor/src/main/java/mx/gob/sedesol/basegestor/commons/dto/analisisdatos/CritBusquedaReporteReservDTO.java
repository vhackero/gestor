package mx.gob.sedesol.basegestor.commons.dto.analisisdatos;

import java.util.Date;

public class CritBusquedaReporteReservDTO {
	private Integer idSede;
	private Integer idArea;
	private Integer idAreaSede;
	private Integer tipoEvento;
	private Integer idEstatusReservacion;
	private Date fechaInicial;
	private Date fechaFinal;
	
	public Integer getIdSede() {
		return idSede;
	}
	public void setIdSede(Integer idSede) {
		this.idSede = idSede;
	}
	public Integer getIdArea() {
		return idArea;
	}
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	public Integer getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(Integer tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public Date getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public Integer getIdEstatusReservacion() {
		return idEstatusReservacion;
	}
	public void setIdEstatusReservacion(Integer idEstatusReservacion) {
		this.idEstatusReservacion = idEstatusReservacion;
	}
	public Integer getIdAreaSede() {
		return idAreaSede;
	}
	public void setIdAreaSede(Integer idAreaSede) {
		this.idAreaSede = idAreaSede;
	}
	
}
