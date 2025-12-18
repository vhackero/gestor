package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class DispersionGrupoEventoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idEvento;
	private Integer idGrupo;
	private Integer idCursoLms;
	private Integer idPlataformaLms;
	private Integer idModalidad;
	private Integer idGrupoMoodle;
	private Integer capacidadTotal;
	private Integer inscritosActuales;
	private String nombreGrupo;

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdCursoLms() {
		return idCursoLms;
	}

	public void setIdCursoLms(Integer idCursoLms) {
		this.idCursoLms = idCursoLms;
	}

	public Integer getIdPlataformaLms() {
		return idPlataformaLms;
	}

	public void setIdPlataformaLms(Integer idPlataformaLms) {
		this.idPlataformaLms = idPlataformaLms;
	}

	public Integer getIdModalidad() {
		return idModalidad;
	}

	public void setIdModalidad(Integer idModalidad) {
		this.idModalidad = idModalidad;
	}

	public Integer getIdGrupoMoodle() {
		return idGrupoMoodle;
	}

	public void setIdGrupoMoodle(Integer idGrupoMoodle) {
		this.idGrupoMoodle = idGrupoMoodle;
	}

	public Integer getCapacidadTotal() {
		return capacidadTotal;
	}

	public void setCapacidadTotal(Integer capacidadTotal) {
		this.capacidadTotal = capacidadTotal;
	}

	public Integer getInscritosActuales() {
		return inscritosActuales;
	}

	public void setInscritosActuales(Integer inscritosActuales) {
		this.inscritosActuales = inscritosActuales;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

}
