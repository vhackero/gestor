package mx.gob.sedesol.basegestor.commons.dto.inscripcion;

public class ProgramaProcesoInscripcionDTO {
	private Long idPrograma;
	private String nombrePrograma;

	public ProgramaProcesoInscripcionDTO() {
	}

	public ProgramaProcesoInscripcionDTO(Long idPrograma, String nombrePrograma) {
		this.idPrograma = idPrograma;
		this.nombrePrograma = nombrePrograma;
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

}
