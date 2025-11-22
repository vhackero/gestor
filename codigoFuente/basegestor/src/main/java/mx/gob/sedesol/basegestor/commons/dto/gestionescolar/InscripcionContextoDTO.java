package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.Date;
import java.util.List;

public class InscripcionContextoDTO {
	 
    private InscripcionPersonaDTO inscripcionPersona;
    private EstadoAcademicoDTO estadoAcademico;  
    private CreditosTotalesPlanDTO creditosTotalesPlan;
    private LimitesCargaAcademicaDTO limitesCargaAcademica;
    private TerminosCondicionesDTO terminosCondiciones;
    private Boolean mostrarMensajeSeriacion;
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
	public Boolean getMostrarMensajeSeriacion() {
		return mostrarMensajeSeriacion;
	}
	public void setMostrarMensajeSeriacion(Boolean mostrarMensajeSeriacion) {
		this.mostrarMensajeSeriacion = mostrarMensajeSeriacion;
	}
}
