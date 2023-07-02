package mx.gob.sedesol.gestorweb.commons.dto;

public class ReservacionAreasDTO {
	private String nombreEvento;
	private String fechasReservadas;
	private String fechaHoraRegistro;

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public String getFechasReservadas() {
		return fechasReservadas;
	}

	public void setFechasReservadas(String fechasReservadas) {
		this.fechasReservadas = fechasReservadas;
	}

	public String getFechaHoraRegistro() {
		return fechaHoraRegistro;
	}

	public void setFechaHoraRegistro(String fechaHoraRegistro) {
		this.fechaHoraRegistro = fechaHoraRegistro;
	}
}
