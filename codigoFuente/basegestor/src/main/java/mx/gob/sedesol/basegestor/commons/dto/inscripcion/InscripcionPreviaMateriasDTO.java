package mx.gob.sedesol.basegestor.commons.dto.inscripcion;

public class InscripcionPreviaMateriasDTO {

    private String programa;
	private String asignatura;
	private String tipoPrograma;
	private String bloque;
	private String semestre;
 
	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	
	public String getTipoPrograma() {
		return tipoPrograma;
	}

	public void setTipoPrograma(String tipoPrograma) {
		this.tipoPrograma = tipoPrograma;
	}
	
	@Override
	public String toString() {
		return "InscripcionPreviaMateriasDTO [programa=" + programa + ", asignatura=" + asignatura + ", bloque="
				+ bloque + ", semestre=" + semestre +", tipoPrograma=" + tipoPrograma+ "]";
	}

}
