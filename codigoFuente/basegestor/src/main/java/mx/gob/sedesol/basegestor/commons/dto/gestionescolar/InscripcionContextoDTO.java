package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.List;
import java.util.stream.Collectors;

public class InscripcionContextoDTO {

	private InscripcionPersonaDTO inscripcionPersona;
	private EstadoAcademicoDTO estadoAcademico;
	private CreditosTotalesPlanDTO creditosTotalesPlan;
	private LimitesCargaAcademicaDTO limitesCargaAcademica;
	private TerminosCondicionesDTO terminosCondiciones;
	private String mensajeSeriacion;
	private String mensajeLimiteReprobacionesAlcanzado;

	public InscripcionPersonaDTO getInscripcionPersona() {
		return inscripcionPersona;
	}

	public void setInscripcionPersona(InscripcionPersonaDTO inscripcionPersona) {
		this.inscripcionPersona = inscripcionPersona;
	}

	public EstadoAcademicoDTO getEstadoAcademico() {
		return estadoAcademico;
	}

	public void setEstadoAcademico(EstadoAcademicoDTO estadoAcademico) {
		this.estadoAcademico = estadoAcademico;
	}

	public CreditosTotalesPlanDTO getCreditosTotalesPlan() {
		return creditosTotalesPlan;
	}

	public void setCreditosTotalesPlan(CreditosTotalesPlanDTO creditosTotalesPlan) {
		this.creditosTotalesPlan = creditosTotalesPlan;
	}

	public LimitesCargaAcademicaDTO getLimitesCargaAcademica() {
		return limitesCargaAcademica;
	}

	public void setLimitesCargaAcademica(LimitesCargaAcademicaDTO limitesCargaAcademica) {
		this.limitesCargaAcademica = limitesCargaAcademica;
	}

	public TerminosCondicionesDTO getTerminosCondiciones() {
		return terminosCondiciones;
	}

	public void setTerminosCondiciones(TerminosCondicionesDTO terminosCondiciones) {
		this.terminosCondiciones = terminosCondiciones;
	}

	public String getMensajeSeriacion() {
		return mensajeSeriacion;
	}

	public void setMensajeSeriacion(String mensajeSeriacion) {
		this.mensajeSeriacion = mensajeSeriacion;
	}

	public String getMensajeLimiteReprobacionesAlcanzado() {
		return mensajeLimiteReprobacionesAlcanzado;
	}

	public void setMensajeLimiteReprobacionesAlcanzado(String mensajeLimiteReprobacionesAlcanzado) {
		this.mensajeLimiteReprobacionesAlcanzado = mensajeLimiteReprobacionesAlcanzado;
	}

	public Long obtenerIdPersona() {
		return this.inscripcionPersona.getIdPersona();
	}

	public Long obtenerIdProcesoInscripcionDesdeMateriasDisponibles() {
		if (estadoAcademico == null || estadoAcademico.getMateriasDisponibles() == null
				|| estadoAcademico.getMateriasDisponibles().isEmpty()) {
			throw new IllegalStateException("No hay materias disponibles para determinar el proceso de inscripción.");
		}

		Long idProceso = estadoAcademico.getMateriasDisponibles().get(0).getIdProcesoInscripcion();

		if (idProceso == null) {
			throw new IllegalStateException("La materia disponible no contiene idProcesoInscripcion.");
		}

		return idProceso;
	}

	public List<InscripcionMateriasDTO> obtenerMateriasSeleccionadas() {
		if (estadoAcademico == null || estadoAcademico.getMateriasDisponibles() == null) {
			throw new IllegalStateException("No hay materias disponibles.");
		}
		return estadoAcademico.getMateriasDisponibles().stream().filter(materia -> materia.getCheck())
				.collect(Collectors.toList());
	}
}
