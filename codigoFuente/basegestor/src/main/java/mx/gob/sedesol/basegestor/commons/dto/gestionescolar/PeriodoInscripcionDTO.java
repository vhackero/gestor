package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class PeriodoInscripcionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idPeriodo;
	private String nombrePeriodo;

	public PeriodoInscripcionDTO() {
	}

	public PeriodoInscripcionDTO(Integer idPeriodo, String nombrePeriodo) {
		this.idPeriodo = idPeriodo;
		this.nombrePeriodo = nombrePeriodo;
	}

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getNombrePeriodo() {
		return nombrePeriodo;
	}

	public void setNombrePeriodo(String nombrePeriodo) {
		this.nombrePeriodo = nombrePeriodo;
	}
}
