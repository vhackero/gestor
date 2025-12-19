package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class EstadoInscripcionEstudianteDTO {
	private Boolean esInscripcionOrdinariaInicial;
	private Boolean esInscripcionExtraordinariaInicial;
	private Integer semestreInscripcionOrdinaria;

	// Constructores
	public EstadoInscripcionEstudianteDTO() {
	}

	public EstadoInscripcionEstudianteDTO(Boolean esInscripcionOrdinariaInicial,
			Boolean esInscripcionExtraordinariaInicial, Integer semestreInscripcionOrdinaria) {
		this.esInscripcionOrdinariaInicial = esInscripcionOrdinariaInicial;
		this.esInscripcionExtraordinariaInicial = esInscripcionExtraordinariaInicial;
		this.semestreInscripcionOrdinaria = semestreInscripcionOrdinaria;
	}

	// Getters y Setters
	public Boolean getEsInscripcionOrdinariaInicial() {
		return esInscripcionOrdinariaInicial;
	}

	public void setEsInscripcionOrdinariaInicial(Boolean esInscripcionOrdinariaInicial) {
		this.esInscripcionOrdinariaInicial = esInscripcionOrdinariaInicial;
	}

	public Boolean getEsInscripcionExtraordinariaInicial() {
		return esInscripcionExtraordinariaInicial;
	}

	public void setEsInscripcionExtraordinariaInicial(Boolean esInscripcionExtraordinariaInicial) {
		this.esInscripcionExtraordinariaInicial = esInscripcionExtraordinariaInicial;
	}

	public Integer getSemestreInscripcionOrdinaria() {
		return semestreInscripcionOrdinaria;
	}

	public void setSemestreInscripcionOrdinaria(Integer semestreInscripcionOrdinaria) {
		this.semestreInscripcionOrdinaria = semestreInscripcionOrdinaria;
	}

	@Override
	public String toString() {
		return "EstadoInscripcionEstudianteDTO{" + "esIncripcionOrdinariaInicial=" + esInscripcionOrdinariaInicial
				+ ", esInscripcionExtraordinariaInicial=" + esInscripcionExtraordinariaInicial
				+ ", semestreInscripcionOrdinaria=" + semestreInscripcionOrdinaria + '}';
	}
}
