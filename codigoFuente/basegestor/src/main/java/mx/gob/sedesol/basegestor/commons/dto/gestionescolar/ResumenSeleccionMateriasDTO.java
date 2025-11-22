package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class ResumenSeleccionMateriasDTO {
	private Long totalSeleccionadas;
	private Long obligatoriasSeleccionadas;
	private Long optativasSeleccionadas;
	private Long obligatoriasDisponibles;

	public ResumenSeleccionMateriasDTO() {

	}

	public ResumenSeleccionMateriasDTO(Long totalSeleccionadas, Long obligatoriasSeleccionadas,
			Long optativasSeleccionadas, Long obligatoriasDisponibles) {
		this.totalSeleccionadas = totalSeleccionadas;
		this.obligatoriasSeleccionadas = obligatoriasSeleccionadas;
		this.optativasSeleccionadas = optativasSeleccionadas;
		this.obligatoriasDisponibles = obligatoriasDisponibles;
	}

	public Long getTotalSeleccionadas() {
		return totalSeleccionadas;
	}

	public void setTotalSeleccionadas(Long totalSeleccionadas) {
		this.totalSeleccionadas = totalSeleccionadas;
	}

	public Long getObligatoriasSeleccionadas() {
		return obligatoriasSeleccionadas;
	}

	public void setObligatoriasSeleccionadas(Long obligatoriasSeleccionadas) {
		this.obligatoriasSeleccionadas = obligatoriasSeleccionadas;
	}

	public Long getOptativasSeleccionadas() {
		return optativasSeleccionadas;
	}

	public void setOptativasSeleccionadas(Long optativasSeleccionadas) {
		this.optativasSeleccionadas = optativasSeleccionadas;
	}

	public Long getObligatoriasDisponibles() {
		return obligatoriasDisponibles;
	}

	public void setObligatoriasDisponibles(Long obligatoriasDisponibles) {
		this.obligatoriasDisponibles = obligatoriasDisponibles;
	}

}
