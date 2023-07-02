package mx.gob.sedesol.basegestor.commons.dto.analisisdatos;

import java.io.Serializable;

public class UsuarioCalificacionDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombreCompleto;
	private Double calificacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}
}
