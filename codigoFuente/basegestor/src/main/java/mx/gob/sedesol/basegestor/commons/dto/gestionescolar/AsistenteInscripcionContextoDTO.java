package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AsistenteInscripcionContextoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private InscripcionContextoDTO contextoBase;

    private String periodoActivo;
    private String situacionAcademicaPeriodo;
    private String subtipoSituacionAcademica;

    private Integer cargaMinima;
    private Integer cargaMaxima;

    private Integer creditosAcumulados;
    private Integer creditosTotalesPlan;
    private Double porcentajeCreditosCompletados;
    private Double promedioPronosticado;
    private Integer totalReprobadasAcumuladas;
    private Boolean restriccionCuatroOMasReprobadas;
    private Boolean cierreAnualCumplido;
    private Integer anioObligatorioPendiente;
    private Boolean reglaCargaFijaNuevoIngreso;
    private Boolean inscripcionVigente;
    private String modoProyeccion;

    private String mensajeResumenPeriodo;
    private String recomendacionInicial;
    private DiagnosticoAcademicoDTO diagnosticoActual;
    private PendientesPlanDTO pendientesPlan;
    private RiesgoAcademicoDTO riesgos;
    private ProyeccionSiguientePeriodoDTO proyeccionSiguientePeriodo;
    private OportunidadAcademicaDTO oportunidades;

    private List<String> reglasActivas;
    private List<String> pendientesCriticos;
    private List<UnidadDecisionInscripcionDTO> unidades;
    private List<EscenarioCargaDTO> escenarios;

    private String auditoriaDecisionId;

    public AsistenteInscripcionContextoDTO() {
        this.reglasActivas = new ArrayList<String>();
        this.pendientesCriticos = new ArrayList<String>();
        this.unidades = new ArrayList<UnidadDecisionInscripcionDTO>();
        this.escenarios = new ArrayList<EscenarioCargaDTO>();
    }

    public InscripcionContextoDTO getContextoBase() {
        return contextoBase;
    }

    public void setContextoBase(InscripcionContextoDTO contextoBase) {
        this.contextoBase = contextoBase;
    }

    public String getPeriodoActivo() {
        return periodoActivo;
    }

    public void setPeriodoActivo(String periodoActivo) {
        this.periodoActivo = periodoActivo;
    }

    public String getSituacionAcademicaPeriodo() {
        return situacionAcademicaPeriodo;
    }

    public void setSituacionAcademicaPeriodo(String situacionAcademicaPeriodo) {
        this.situacionAcademicaPeriodo = situacionAcademicaPeriodo;
    }

    public String getSubtipoSituacionAcademica() {
        return subtipoSituacionAcademica;
    }

    public void setSubtipoSituacionAcademica(String subtipoSituacionAcademica) {
        this.subtipoSituacionAcademica = subtipoSituacionAcademica;
    }

    public Integer getCargaMinima() {
        return cargaMinima;
    }

    public void setCargaMinima(Integer cargaMinima) {
        this.cargaMinima = cargaMinima;
    }

    public Integer getCargaMaxima() {
        return cargaMaxima;
    }

    public void setCargaMaxima(Integer cargaMaxima) {
        this.cargaMaxima = cargaMaxima;
    }

    public Integer getCreditosAcumulados() {
        return creditosAcumulados;
    }

    public void setCreditosAcumulados(Integer creditosAcumulados) {
        this.creditosAcumulados = creditosAcumulados;
    }

    public Integer getCreditosTotalesPlan() {
        return creditosTotalesPlan;
    }

    public void setCreditosTotalesPlan(Integer creditosTotalesPlan) {
        this.creditosTotalesPlan = creditosTotalesPlan;
    }

    public Double getPorcentajeCreditosCompletados() {
        return porcentajeCreditosCompletados;
    }

    public void setPorcentajeCreditosCompletados(Double porcentajeCreditosCompletados) {
        this.porcentajeCreditosCompletados = porcentajeCreditosCompletados;
    }

    public Double getPromedioPronosticado() {
        return promedioPronosticado;
    }

    public void setPromedioPronosticado(Double promedioPronosticado) {
        this.promedioPronosticado = promedioPronosticado;
    }

    public Integer getTotalReprobadasAcumuladas() {
        return totalReprobadasAcumuladas;
    }

    public void setTotalReprobadasAcumuladas(Integer totalReprobadasAcumuladas) {
        this.totalReprobadasAcumuladas = totalReprobadasAcumuladas;
    }

    public Boolean getRestriccionCuatroOMasReprobadas() {
        return restriccionCuatroOMasReprobadas;
    }

    public void setRestriccionCuatroOMasReprobadas(Boolean restriccionCuatroOMasReprobadas) {
        this.restriccionCuatroOMasReprobadas = restriccionCuatroOMasReprobadas;
    }

    public Boolean getCierreAnualCumplido() {
        return cierreAnualCumplido;
    }

    public void setCierreAnualCumplido(Boolean cierreAnualCumplido) {
        this.cierreAnualCumplido = cierreAnualCumplido;
    }

    public Integer getAnioObligatorioPendiente() {
        return anioObligatorioPendiente;
    }

    public void setAnioObligatorioPendiente(Integer anioObligatorioPendiente) {
        this.anioObligatorioPendiente = anioObligatorioPendiente;
    }

    public Boolean getReglaCargaFijaNuevoIngreso() {
        return reglaCargaFijaNuevoIngreso;
    }

    public void setReglaCargaFijaNuevoIngreso(Boolean reglaCargaFijaNuevoIngreso) {
        this.reglaCargaFijaNuevoIngreso = reglaCargaFijaNuevoIngreso;
    }

    public Boolean getInscripcionVigente() {
        return inscripcionVigente;
    }

    public void setInscripcionVigente(Boolean inscripcionVigente) {
        this.inscripcionVigente = inscripcionVigente;
    }

    public String getModoProyeccion() {
        return modoProyeccion;
    }

    public void setModoProyeccion(String modoProyeccion) {
        this.modoProyeccion = modoProyeccion;
    }

    public String getMensajeResumenPeriodo() {
        return mensajeResumenPeriodo;
    }

    public void setMensajeResumenPeriodo(String mensajeResumenPeriodo) {
        this.mensajeResumenPeriodo = mensajeResumenPeriodo;
    }

    public String getRecomendacionInicial() {
        return recomendacionInicial;
    }

    public void setRecomendacionInicial(String recomendacionInicial) {
        this.recomendacionInicial = recomendacionInicial;
    }

    public DiagnosticoAcademicoDTO getDiagnosticoActual() {
        return diagnosticoActual;
    }

    public void setDiagnosticoActual(DiagnosticoAcademicoDTO diagnosticoActual) {
        this.diagnosticoActual = diagnosticoActual;
    }

    public PendientesPlanDTO getPendientesPlan() {
        return pendientesPlan;
    }

    public void setPendientesPlan(PendientesPlanDTO pendientesPlan) {
        this.pendientesPlan = pendientesPlan;
    }

    public RiesgoAcademicoDTO getRiesgos() {
        return riesgos;
    }

    public void setRiesgos(RiesgoAcademicoDTO riesgos) {
        this.riesgos = riesgos;
    }

    public ProyeccionSiguientePeriodoDTO getProyeccionSiguientePeriodo() {
        return proyeccionSiguientePeriodo;
    }

    public void setProyeccionSiguientePeriodo(ProyeccionSiguientePeriodoDTO proyeccionSiguientePeriodo) {
        this.proyeccionSiguientePeriodo = proyeccionSiguientePeriodo;
    }

    public OportunidadAcademicaDTO getOportunidades() {
        return oportunidades;
    }

    public void setOportunidades(OportunidadAcademicaDTO oportunidades) {
        this.oportunidades = oportunidades;
    }

    public List<String> getReglasActivas() {
        return reglasActivas;
    }

    public void setReglasActivas(List<String> reglasActivas) {
        this.reglasActivas = reglasActivas;
    }

    public List<String> getPendientesCriticos() {
        return pendientesCriticos;
    }

    public void setPendientesCriticos(List<String> pendientesCriticos) {
        this.pendientesCriticos = pendientesCriticos;
    }

    public List<UnidadDecisionInscripcionDTO> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadDecisionInscripcionDTO> unidades) {
        this.unidades = unidades;
    }

    public List<EscenarioCargaDTO> getEscenarios() {
        return escenarios;
    }

    public void setEscenarios(List<EscenarioCargaDTO> escenarios) {
        this.escenarios = escenarios;
    }

    public String getAuditoriaDecisionId() {
        return auditoriaDecisionId;
    }

    public void setAuditoriaDecisionId(String auditoriaDecisionId) {
        this.auditoriaDecisionId = auditoriaDecisionId;
    }
}
