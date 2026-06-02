package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class ActualizacionCursoMoodleProgramaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPrograma;
	private String clavePrograma;
	private String nombreTentativo;
	private String tipo;

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getClavePrograma() {
		return clavePrograma;
	}

	public void setClavePrograma(String clavePrograma) {
		this.clavePrograma = clavePrograma;
	}

	public String getNombreTentativo() {
		return nombreTentativo;
	}

	public void setNombreTentativo(String nombreTentativo) {
		this.nombreTentativo = nombreTentativo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
