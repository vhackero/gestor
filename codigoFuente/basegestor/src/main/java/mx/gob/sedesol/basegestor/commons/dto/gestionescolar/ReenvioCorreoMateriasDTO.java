package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class ReenvioCorreoMateriasDTO {
	private String programa;
	private String tipoPrograma;
	private String semestre;
	private String bloque;
	private Long periodo;

	public ReenvioCorreoMateriasDTO() {
	}

	public ReenvioCorreoMateriasDTO(String programa, String tipoPrograma, String semestre, String bloque,
			Long periodo) {
		this.programa = programa;
		this.tipoPrograma = tipoPrograma;
		this.semestre = semestre;
		this.bloque = bloque;
		this.periodo = periodo;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getTipoPrograma() {
		return tipoPrograma;
	}

	public void setTipoPrograma(String tipoPrograma) {
		this.tipoPrograma = tipoPrograma;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public Long getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}
}
