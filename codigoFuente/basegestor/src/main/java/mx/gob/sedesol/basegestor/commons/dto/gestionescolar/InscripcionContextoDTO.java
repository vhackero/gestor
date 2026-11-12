package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.List;
import java.util.stream.Collectors;

public class InscripcionContextoDTO {
    private ConfiguracionOportunidadesDTO configuracionOportunidades;
    private List<OportunidadesUnidadDTO> oportunidades = new java.util.ArrayList<>();
    private boolean aceptaUltimaOportunidad;
    private String avisoUltimaAceptado;
    public ConfiguracionOportunidadesDTO getConfiguracionOportunidades() { return configuracionOportunidades; }
    public void setConfiguracionOportunidades(ConfiguracionOportunidadesDTO v) { configuracionOportunidades=v; }
    public List<OportunidadesUnidadDTO> getOportunidades() { return oportunidades; }
    public void setOportunidades(List<OportunidadesUnidadDTO> v) { oportunidades=v; }
    public boolean isAceptaUltimaOportunidad() { return aceptaUltimaOportunidad && java.util.Objects.equals(avisoUltimaAceptado,getAvisoUltimaOportunidad()); }
    public void setAceptaUltimaOportunidad(boolean v) { aceptaUltimaOportunidad=v;avisoUltimaAceptado=v?getAvisoUltimaOportunidad():null; }
    public String getAvisoUltimaOportunidad() {
        if(configuracionOportunidades==null || !configuracionOportunidades.isAplicarLimite() || estadoAcademico==null || estadoAcademico.getMateriasDisponibles()==null) return "";
        java.util.Set<String> avisos=new java.util.LinkedHashSet<>();
        for(InscripcionMateriasDTO m:estadoAcademico.getMateriasDisponibles()) {
            if(!Boolean.TRUE.equals(m.getCheck()))continue;
            if(m.getOportunidades()!=null && m.getOportunidades().getDisponibles()==1)avisos.add(configuracionOportunidades.mensaje(configuracionOportunidades.getMensajeUltima(),m.getOportunidades()));
            AdeudoOptativaDTO d=obtenerAdeudoSeleccionado(m);
            if(d!=null && !d.isEnCurso() && d.isLimiteOportunidadesActivo() && d.getOportunidadesDisponibles()==1)avisos.add(configuracionOportunidades.mensajeAdeudo(configuracionOportunidades.getMensajeUltimaAcumulada(),d,m.getNombreTentativoPrograma()));
        }
        return String.join(" ",avisos);
    }
    public AdeudoOptativaDTO obtenerAdeudoSeleccionado(InscripcionMateriasDTO m) {
        if(m.getIdAdeudoOptativa()==null || estadoAcademico==null || estadoAcademico.getAdeudosOptativasAdicionales()==null)return null;
        return estadoAcademico.getAdeudosOptativasAdicionales().stream().filter(d->java.util.Objects.equals(d.getIdOrigen(),m.getIdAdeudoOptativa())).findFirst().orElse(null);
    }
    public String mensajeAdeudo(AdeudoOptativaDTO d) {
        if(d==null || configuracionOportunidades==null)return "";
        if(d.isEnCurso())return "La inscripción que conserva este adeudo está en curso.";
        if(!d.isLimiteOportunidadesActivo())return "";
        if(d.getOportunidadesDisponibles()==0)return configuracionOportunidades.mensajeAdeudo(configuracionOportunidades.getMensajeAgotadaAcumulada(),d,null);
        if(d.getOportunidadesDisponibles()==1)return configuracionOportunidades.mensajeAdeudo(configuracionOportunidades.getMensajeUltimaAcumulada(),d,null);
        return "";
    }
    public String mensajeOportunidad(InscripcionMateriasDTO m) {
        if(m==null || configuracionOportunidades==null)return "";
        java.util.List<String> avisos=new java.util.ArrayList<>();
        OportunidadesUnidadDTO o=m.getOportunidades();
        if(o!=null && o.isLimiteActivo()) {
            if(o.isAgotada())avisos.add(configuracionOportunidades.mensaje(configuracionOportunidades.getMensajeAgotada(),o));
            else if(o.getDisponibles()==1)avisos.add(configuracionOportunidades.mensaje(configuracionOportunidades.getMensajeUltima(),o));
        }
        String acumulado=mensajeAdeudo(obtenerAdeudoSeleccionado(m));if(!acumulado.isEmpty())avisos.add(acumulado);
        return String.join(" ",avisos);
    }
    private ConclusionEstudiosDTO conclusionEstudios;
    public ConclusionEstudiosDTO getConclusionEstudios() { return conclusionEstudios; }
    public void setConclusionEstudios(ConclusionEstudiosDTO v) { conclusionEstudios=v; }
    private String evidenciaEvaluacion;
    private Integer semestreReferencia;
    private String mensajeReferencia;
    private java.util.Date fechaEvaluacion;
    public String getEvidenciaEvaluacion() { return evidenciaEvaluacion; }
    public void setEvidenciaEvaluacion(String v) { evidenciaEvaluacion=v; }
    public Integer getSemestreReferencia() { return semestreReferencia; }
    public void setSemestreReferencia(Integer v) { semestreReferencia=v; }
    public String getMensajeReferencia() { return mensajeReferencia; }
    public void setMensajeReferencia(String v) { mensajeReferencia=v; }
    public java.util.Date getFechaEvaluacion() { return fechaEvaluacion; }
    public void setFechaEvaluacion(java.util.Date v) { fechaEvaluacion=v; }
    public String getCargaMaximaActual() {
        if(limitesCargaAcademica==null || estadoAcademico==null) return "";
        return Boolean.TRUE.equals(estadoAcademico.getEsRegular()) ? limitesCargaAcademica.getMaxProgramasRegulares() : limitesCargaAcademica.getMaxProgramasIrregulares();
    }


	private InscripcionPersonaDTO inscripcionPersona;
	private EstadoAcademicoDTO estadoAcademico;
	private CreditosTotalesPlanDTO creditosTotalesPlan;
	private LimitesCargaAcademicaDTO limitesCargaAcademica;
	private TerminosCondicionesDTO terminosCondiciones;
	private String mensajeSeriacion;
	private String mensajeLimiteReprobacionesAlcanzado;
	private Boolean permiteInscripcionLimiteReprobadas;
	private String mensajeOmisionReinscripcion;
	private Boolean permiteInscripcionOmision = Boolean.TRUE;
	private List<String> periodosSinReinscripcion = new java.util.ArrayList<>();

	public String getMensajeOmisionReinscripcion() { return mensajeOmisionReinscripcion; }
	public void setMensajeOmisionReinscripcion(String valor) { mensajeOmisionReinscripcion = valor; }
	public Boolean getPermiteInscripcionOmision() { return permiteInscripcionOmision; }
	public void setPermiteInscripcionOmision(Boolean valor) { permiteInscripcionOmision = valor; }
	public List<String> getPeriodosSinReinscripcion() { return periodosSinReinscripcion; }
	public void setPeriodosSinReinscripcion(List<String> valor) { periodosSinReinscripcion = valor; }

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

	public Boolean getPermiteInscripcionLimiteReprobadas() {
		return permiteInscripcionLimiteReprobadas;
	}

	public void setPermiteInscripcionLimiteReprobadas(Boolean permiteInscripcionLimiteReprobadas) {
		this.permiteInscripcionLimiteReprobadas = permiteInscripcionLimiteReprobadas;
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
