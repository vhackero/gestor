package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.List;

public class EstadoAcademicoDTO {
	private Boolean esRegular;
	private Boolean esNuevoIngreso;

	private List<InscripcionMateriasCursadasDTO> materiasCursadas;
	private List<InscripcionMateriasReprobadasDTO> materiasReprobadas;
	private List<InscripcionBajasDTO> materiasBajas;
	private List<InscripcionMateriasDTO> materiasDisponibles;

	private Double porcentajeCreditosCompletados;

	private Long cantidadMaximaMateriasElectivas;

	public Double getPorcentajeCreditosCompletados() {
		return porcentajeCreditosCompletados;
	}

	public void setPorcentajeCreditosCompletados(Double porcentajeCreditosCompletados) {
		this.porcentajeCreditosCompletados = porcentajeCreditosCompletados;
	}

	public List<InscripcionMateriasCursadasDTO> getMateriasCursadas() {
		return materiasCursadas;
	}

	public void setMateriasCursadas(List<InscripcionMateriasCursadasDTO> materiasCursadas) {
		this.materiasCursadas = materiasCursadas;
	}

	public List<InscripcionMateriasReprobadasDTO> getMateriasReprobadas() {
		return materiasReprobadas;
	}

	public void setMateriasReprobadas(List<InscripcionMateriasReprobadasDTO> materiasReprobadas) {
		this.materiasReprobadas = materiasReprobadas;
	}

	public List<InscripcionBajasDTO> getMateriasBajas() {
		return materiasBajas;
	}

	public void setMateriasBajas(List<InscripcionBajasDTO> materiasBajas) {
		this.materiasBajas = materiasBajas;
	}

	public List<InscripcionMateriasDTO> getMateriasDisponibles() {
		return materiasDisponibles;
	}

	public void setMateriasDisponibles(List<InscripcionMateriasDTO> materiasDisponibles) {
		this.materiasDisponibles = materiasDisponibles;
	}

	public Long getCantidadMaximaMateriasElectivas() {
		return cantidadMaximaMateriasElectivas;
	}

	public void setCantidadMaximaMateriasElectivas(Long cantidadMaximaMateriasElectivas) {
		this.cantidadMaximaMateriasElectivas = cantidadMaximaMateriasElectivas;
	}

	public Boolean getEsRegular() {
		return esRegular;
	}

	public void setEsRegular(Boolean esRegular) {
		this.esRegular = esRegular;
	}

	public Boolean getEsNuevoIngreso() {
		return esNuevoIngreso;
	}

	public void setEsNuevoIngreso(Boolean esNuevoIngreso) {
		this.esNuevoIngreso = esNuevoIngreso;
	}

}
