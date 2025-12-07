package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

public class EstatusEnvioCorreoInscripcionDTO {
	private Integer idEstatusEnvio;
	private String nombreEstatusEnvio;

	public EstatusEnvioCorreoInscripcionDTO() {
	}

	public EstatusEnvioCorreoInscripcionDTO(Integer idEstatusEnvio, String nombreEstatusEnvio) {
		this.idEstatusEnvio = idEstatusEnvio;
		this.nombreEstatusEnvio = nombreEstatusEnvio;
	}

	public Integer getIdEstatusEnvio() {
		return idEstatusEnvio;
	}

	public void setIdEstatusEnvio(Integer idEstatusEnvio) {
		this.idEstatusEnvio = idEstatusEnvio;
	}

	public String getNombreEstatusEnvio() {
		return nombreEstatusEnvio;
	}

	public void setNombreEstatusEnvio(String nombreEstatusEnvio) {
		this.nombreEstatusEnvio = nombreEstatusEnvio;
	}

}
