package mx.gob.sedesol.basegestor.commons.dto.inscripcion;

public class ModificacionInscripcionDTO {
	private Long idInscripciones;
	private String programa;
	private String tipoPrograma;
	private String semestre;
	private String bloque;
	private boolean checked;

	public Long getIdInscripciones() {
		return idInscripciones;
	}

	public void setIdInscripciones(Long idInscripciones) {
		this.idInscripciones = idInscripciones;
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
