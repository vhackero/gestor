package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.List;

public class EstadoAcademicoDTO {
    private final java.util.List<String> optativasSinCupo = new java.util.ArrayList<>();
    private final java.util.List<String> optativasSinCupoConfigurado = new java.util.ArrayList<>();

    public java.util.List<String> getOptativasSinCupo() { return optativasSinCupo; }
    public java.util.List<String> getOptativasSinCupoConfigurado() { return optativasSinCupoConfigurado; }

    private String avisoCupoOptativas = "";
    public String getAvisoCupoOptativas() { return avisoCupoOptativas; }
    public void setAvisoCupoOptativas(String avisoCupoOptativas) { this.avisoCupoOptativas = avisoCupoOptativas; }

	private EstadoInscripcionEstudianteDTO clasificacionInscripcion;
	public EstadoInscripcionEstudianteDTO getClasificacionInscripcion() { return clasificacionInscripcion; }
	public void setClasificacionInscripcion(EstadoInscripcionEstudianteDTO valor) { clasificacionInscripcion = valor; }
    private Long idProcesoEvaluado;
    public Long getIdProcesoEvaluado() { return idProcesoEvaluado; }
    public void setIdProcesoEvaluado(Long v) { idProcesoEvaluado=v; }
    private List<AdeudoOptativaDTO> adeudosOptativasAdicionales = new java.util.ArrayList<>();
    private int optativasRequeridasAcreditadas;
    public List<AdeudoOptativaDTO> getAdeudosOptativasAdicionales() { return adeudosOptativasAdicionales; }
    public void setAdeudosOptativasAdicionales(List<AdeudoOptativaDTO> v) { adeudosOptativasAdicionales=v; }
    public int getOptativasRequeridasAcreditadas() { return optativasRequeridasAcreditadas; }
    public void setOptativasRequeridasAcreditadas(int v) { optativasRequeridasAcreditadas=v; }

	private Boolean esRegular;
	private Boolean esNuevoIngreso;
	private Boolean primerSemestrePendiente;
	private Integer semestrePrioritarioBajaTemporal;

	public Integer getSemestrePrioritarioBajaTemporal() {
		return semestrePrioritarioBajaTemporal;
	}

	public void setSemestrePrioritarioBajaTemporal(Integer semestre) {
		this.semestrePrioritarioBajaTemporal = semestre;
	}

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

	public Boolean getPrimerSemestrePendiente() {
		return primerSemestrePendiente;
	}

	public void setPrimerSemestrePendiente(Boolean primerSemestrePendiente) {
		this.primerSemestrePendiente = primerSemestrePendiente;
	}

}
