package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class TerminosCondicionesDTO {
	private Boolean mostrarTerminosCondiciones;
	private String enlaceTerminosCondiciones;

	public TerminosCondicionesDTO() {
	}

	public TerminosCondicionesDTO(Boolean mostrarTerminosCondiciones) {
		this.mostrarTerminosCondiciones = mostrarTerminosCondiciones;
	}

	public TerminosCondicionesDTO(Boolean mostrarTerminosCondiciones, String enlaceTerminosCondiciones) {
		this.mostrarTerminosCondiciones = mostrarTerminosCondiciones;
		this.enlaceTerminosCondiciones = enlaceTerminosCondiciones;
	}

	public Boolean getMostrarTerminosCondiciones() {
		return mostrarTerminosCondiciones;
	}

	public void setMostrarTerminosCondiciones(Boolean mostrarTerminosCondiciones) {
		this.mostrarTerminosCondiciones = mostrarTerminosCondiciones;
	}

	public String getEnlaceTerminosCondiciones() {
		return enlaceTerminosCondiciones;
	}

	public void setEnlaceTerminosCondiciones(String enlaceTerminosCondiciones) {
		this.enlaceTerminosCondiciones = enlaceTerminosCondiciones;
	}

}
