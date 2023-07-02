package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

public class LogrosDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer trofeo;
	private String nombreCurso;
	private String modalidad;
	private Date fecha;
	private Integer puntos;


	public String getNombreCurso() {
		return nombreCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	public Integer getTrofeo() {
		return trofeo;
	}

	public void setTrofeo(Integer trofeo) {
		this.trofeo = trofeo;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getPuntos() {
		return puntos;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}

}
