package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Orquestación de carga V2: referencia resultados previos, no los recalcula. */
public class EvaluacionCargaAcademicaV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String situacionAcademica;
    private Integer cargaMinimaNormativa;
    private Integer cargaMaximaNormativa;
    private Integer cargaEvaluada;
    private Boolean minimoCumplido;
    private Boolean excepcionMinimoAplicable;
    private Boolean maximoCumplido;
    private Boolean requiereRevision = Boolean.FALSE;
    private Boolean rn64AplicableEnFuturaFase = Boolean.FALSE;
    private Boolean ctr01Rn61Procedente;
    /** RN-6.3: ruta descriptiva de regularización; nunca ejecuta reinscripción. */
    private Boolean aplicaRn63;
    /** RN-5.1: clasificación concurrente; no sustituye situación académica. */
    private Boolean aplicaRn51;
    /** RN-5.2 compone el resultado severo existente, sin duplicar RN-6.4. */
    private Boolean aplicaRn52;
    private String condicionRegularizacion;
    /** RN-6.4: restricción efectiva sólo dentro del resultado sombra V2. */
    private Boolean aplicaRn64;
    private Boolean avanceCondicionadoBloqueadoPorRn64 = Boolean.FALSE;
    /** Fase 5A: aplicabilidad formal de CTR-01/RN-6.1 para acceso a quinto. */
    private Boolean aplicaCtr01;
    private Boolean aplicaRn61;
    private Integer totalObligatoriasSemestres1A4;
    private Boolean todasObligatoriasPreviasAcreditadas;
    private Boolean accesoOrdinarioQuinto;
    private Boolean requiereEvaluacionAvanceCondicionado;
    private EstadoControlAvanceAnualV2 estadoControlAvanceAnual = EstadoControlAvanceAnualV2.NO_APLICA;
    private List<Long> baseRn611 = new ArrayList<Long>();
    private List<Long> obligatoriasAcreditadasRn611 = new ArrayList<Long>();
    private List<Long> obligatoriasPendientesRn611 = new ArrayList<Long>();
    private List<String> detallePendientesRn611 = new ArrayList<String>();
    private EstadoCondicionesComunesAvanceV2 estadoCondicionesComunesAvance = EstadoCondicionesComunesAvanceV2.NO_APLICA;
    private List<CondicionComunAvanceV2DTO> condicionesComunesAvance = new ArrayList<CondicionComunAvanceV2DTO>();
    private List<Long> udsQuintoCompatiblesRn612 = new ArrayList<Long>();
    private List<Long> udsQuintoBloqueadasSeriacionDirectaRn612 = new ArrayList<Long>();
    private List<Long> udsQuintoBloqueadasSeriacionIndirectaRn612 = new ArrayList<Long>();
    private List<Long> udsQuintoRevisionSeriacionRn612 = new ArrayList<Long>();
    private EstadoSupuestosAvanceV2 estadoSupuestosAvance = EstadoSupuestosAvanceV2.NO_APLICA;
    private List<SupuestoAvanceCondicionadoV2DTO> supuestosAvanceTipificados = new ArrayList<SupuestoAvanceCondicionadoV2DTO>();
    private List<Long> udsQuintoSinDependenciaSeriacionRn613 = new ArrayList<Long>();
    private List<EvaluacionOfertaPendienteRn614V2DTO> ofertasPendientesRn614 = new ArrayList<EvaluacionOfertaPendienteRn614V2DTO>();
    private EstadoResolucionAvanceV2 estadoResolucionAvance = EstadoResolucionAvanceV2.NO_APLICA;
    private List<String> impedimentosAvanceRn615 = new ArrayList<String>();
    private List<Long> udsQuintoNoSusceptiblesRn615 = new ArrayList<Long>();
    private List<String> rutaInstitucionalExtraordinariaRn616 = new ArrayList<String>();
    private EstadoDecisionControlAvanceRn61V2 decisionGlobalRn61 = EstadoDecisionControlAvanceRn61V2.NO_APLICA;
    private List<String> decisionesPorUdQuintoRn61 = new ArrayList<String>();
    private Boolean aplicaRn62;
    private EstadoIncorporacionDiferidaV2 estadoIncorporacionDiferida = EstadoIncorporacionDiferidaV2.NO_APLICA;
    /** Trayectoria descriptiva RN-6.2; no representa una inscripción productiva. */
    private String condicionTrayectoriaRn62;
    private String periodoAdmisionRn62;
    private String periodoIncorporacionRn62;
    private Integer semestreObjetivoRn62;
    private List<Long> udsSemestre2CompatiblesRn62 = new ArrayList<Long>();
    private List<Long> udsSemestre2BloqueadasRn62 = new ArrayList<Long>();
    /** RN-6.5 reutiliza carga, seriacion, RN-6.4 y RN-6.1 sin efectos productivos. */
    private Boolean aplicaRn65;
    private EstadoReincorporacionPeriodoNoCursadoV2 estadoReincorporacionRn65 = EstadoReincorporacionPeriodoNoCursadoV2.NO_APLICA;
    private String condicionTrayectoriaRn65;
    private String ultimoPeriodoCursadoRn65;
    private String primerPeriodoOmitidoRn65;
    private List<String> periodosOmitidosRn65 = new ArrayList<String>();
    private String periodoReincorporacionRn65;
    private Integer cantidadPeriodosInterrupcionRn65;
    private String causaInterrupcionRn65;
    private TipoProcesoReincorporacionV2 tipoProcesoReincorporacionRn65 = TipoProcesoReincorporacionV2.NO_DETERMINADO;
    private List<Long> udsPendientesPrioritariasRn65 = new ArrayList<Long>();
    private List<Long> udsPendientesSinOfertaRn65 = new ArrayList<Long>();
    private List<Long> udsPendientesSinCupoRn65 = new ArrayList<Long>();
    private List<Long> udsPosterioresCompatiblesRn65 = new ArrayList<Long>();
    private List<Long> udsPosterioresBloqueadasSeriacionRn65 = new ArrayList<Long>();
    private List<Long> udsPosterioresRevisionSeriacionRn65 = new ArrayList<Long>();
    private List<Long> udsRutaReincorporacionRn65 = new ArrayList<Long>();
    private List<String> decisionesPorUdRn65 = new ArrayList<String>();
    private Boolean rn61ConsumidaRn65 = Boolean.FALSE;
    /** RN-6.6 mantiene una ruta sombra separada de RN-6.5. */
    private Boolean aplicaRn66;
    private EstadoReincorporacionBajaTemporalV2 estadoReincorporacionRn66 = EstadoReincorporacionBajaTemporalV2.NO_APLICA;
    private String condicionTrayectoriaRn66;
    private String periodoInicioBajaTemporalRn66;
    private String periodoFinBajaTemporalRn66;
    private String ultimoPeriodoEfectivamenteCursadoRn66;
    private Integer semestreEfectivoPrevioBajaRn66;
    private Integer semestreObjetivoRn66;
    private Boolean dentroCalendarioReincorporacionRn66;
    private List<Long> udsPendientesPrioritariasRn66 = new ArrayList<Long>();
    private List<Long> udsPendientesSinOfertaRn66 = new ArrayList<Long>();
    private List<Long> udsPendientesSinCupoRn66 = new ArrayList<Long>();
    private List<Long> udsPosterioresCompatiblesRn66 = new ArrayList<Long>();
    private List<Long> udsPosterioresBloqueadasSeriacionRn66 = new ArrayList<Long>();
    private List<Long> udsPosterioresRevisionSeriacionRn66 = new ArrayList<Long>();
    private List<Long> udsRutaReincorporacionRn66 = new ArrayList<Long>();
    private List<String> decisionesPorUdRn66 = new ArrayList<String>();
    private Boolean rn61ConsumidaRn66 = Boolean.FALSE;
    private Boolean requiereRevisionRegularizacion = Boolean.FALSE;
    private Integer cargaDisponibleRegularizacion;
    private EstadoCargaAcademicaV2 estado = EstadoCargaAcademicaV2.NO_DETERMINADA;
    private List<Long> udsPrioritarias = new ArrayList<Long>();
    private List<Long> udsIncluidasSombra = new ArrayList<Long>();
    private List<String> udsExcluidas = new ArrayList<String>();
    private List<Long> udsReprobadasPrioritarias = new ArrayList<Long>();
    private List<Long> udsReprobadasOfertadas = new ArrayList<Long>();
    private List<Long> udsReprobadasNoDisponibles = new ArrayList<Long>();
    private List<Long> udsObligatoriasCompatibles = new ArrayList<Long>();
    private List<Long> udsOptativasCompatibles = new ArrayList<Long>();
    private List<Long> udsEmpatadasRegularizacion = new ArrayList<Long>();
    private List<String> causasIndisponibilidadRegularizacion = new ArrayList<String>();
    private List<Long> udsObligatoriasNuevasBloqueadasRn64 = new ArrayList<Long>();
    private List<Long> udsOptativasAdicionalesElegiblesRn64 = new ArrayList<Long>();
    private List<CodigoMotivoCurricularV2> causasExclusion = new ArrayList<CodigoMotivoCurricularV2>();
    private List<CodigoReglaCurricularV2> reglasRelacionadas = new ArrayList<CodigoReglaCurricularV2>();
    private List<String> evidencias = new ArrayList<String>();

    public Boolean getAplicaRn65() { return aplicaRn65; }
    public void setAplicaRn65(Boolean valor) { aplicaRn65 = valor; }
    public EstadoReincorporacionPeriodoNoCursadoV2 getEstadoReincorporacionRn65() { return estadoReincorporacionRn65; }
    public void setEstadoReincorporacionRn65(EstadoReincorporacionPeriodoNoCursadoV2 valor) { estadoReincorporacionRn65 = valor; }
    public String getCondicionTrayectoriaRn65() { return condicionTrayectoriaRn65; }
    public void setCondicionTrayectoriaRn65(String valor) { condicionTrayectoriaRn65 = valor; }
    public String getUltimoPeriodoCursadoRn65() { return ultimoPeriodoCursadoRn65; }
    public void setUltimoPeriodoCursadoRn65(String valor) { ultimoPeriodoCursadoRn65 = valor; }
    public String getPrimerPeriodoOmitidoRn65() { return primerPeriodoOmitidoRn65; }
    public void setPrimerPeriodoOmitidoRn65(String valor) { primerPeriodoOmitidoRn65 = valor; }
    public List<String> getPeriodosOmitidosRn65() { return periodosOmitidosRn65; }
    public void setPeriodosOmitidosRn65(List<String> valores) { periodosOmitidosRn65 = valores == null ? new ArrayList<String>() : valores; }
    public String getPeriodoReincorporacionRn65() { return periodoReincorporacionRn65; }
    public void setPeriodoReincorporacionRn65(String valor) { periodoReincorporacionRn65 = valor; }
    public Integer getCantidadPeriodosInterrupcionRn65() { return cantidadPeriodosInterrupcionRn65; }
    public void setCantidadPeriodosInterrupcionRn65(Integer valor) { cantidadPeriodosInterrupcionRn65 = valor; }
    public String getCausaInterrupcionRn65() { return causaInterrupcionRn65; }
    public void setCausaInterrupcionRn65(String valor) { causaInterrupcionRn65 = valor; }
    public TipoProcesoReincorporacionV2 getTipoProcesoReincorporacionRn65() { return tipoProcesoReincorporacionRn65; }
    public void setTipoProcesoReincorporacionRn65(TipoProcesoReincorporacionV2 valor) { tipoProcesoReincorporacionRn65 = valor == null ? TipoProcesoReincorporacionV2.NO_DETERMINADO : valor; }
    public List<Long> getUdsPendientesPrioritariasRn65() { return udsPendientesPrioritariasRn65; }
    public List<Long> getUdsPendientesSinOfertaRn65() { return udsPendientesSinOfertaRn65; }
    public List<Long> getUdsPendientesSinCupoRn65() { return udsPendientesSinCupoRn65; }
    public List<Long> getUdsPosterioresCompatiblesRn65() { return udsPosterioresCompatiblesRn65; }
    public List<Long> getUdsPosterioresBloqueadasSeriacionRn65() { return udsPosterioresBloqueadasSeriacionRn65; }
    public List<Long> getUdsPosterioresRevisionSeriacionRn65() { return udsPosterioresRevisionSeriacionRn65; }
    public List<Long> getUdsRutaReincorporacionRn65() { return udsRutaReincorporacionRn65; }
    public List<String> getDecisionesPorUdRn65() { return decisionesPorUdRn65; }
    public Boolean getRn61ConsumidaRn65() { return rn61ConsumidaRn65; }
    public void setRn61ConsumidaRn65(Boolean valor) { rn61ConsumidaRn65 = valor; }
    public Boolean getAplicaRn66() { return aplicaRn66; }
    public void setAplicaRn66(Boolean valor) { aplicaRn66 = valor; }
    public EstadoReincorporacionBajaTemporalV2 getEstadoReincorporacionRn66() { return estadoReincorporacionRn66; }
    public void setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2 valor) { estadoReincorporacionRn66 = valor; }
    public String getCondicionTrayectoriaRn66() { return condicionTrayectoriaRn66; }
    public void setCondicionTrayectoriaRn66(String valor) { condicionTrayectoriaRn66 = valor; }
    public String getPeriodoInicioBajaTemporalRn66() { return periodoInicioBajaTemporalRn66; }
    public void setPeriodoInicioBajaTemporalRn66(String valor) { periodoInicioBajaTemporalRn66 = valor; }
    public String getPeriodoFinBajaTemporalRn66() { return periodoFinBajaTemporalRn66; }
    public void setPeriodoFinBajaTemporalRn66(String valor) { periodoFinBajaTemporalRn66 = valor; }
    public String getUltimoPeriodoEfectivamenteCursadoRn66() { return ultimoPeriodoEfectivamenteCursadoRn66; }
    public void setUltimoPeriodoEfectivamenteCursadoRn66(String valor) { ultimoPeriodoEfectivamenteCursadoRn66 = valor; }
    public Integer getSemestreEfectivoPrevioBajaRn66() { return semestreEfectivoPrevioBajaRn66; }
    public void setSemestreEfectivoPrevioBajaRn66(Integer valor) { semestreEfectivoPrevioBajaRn66 = valor; }
    public Integer getSemestreObjetivoRn66() { return semestreObjetivoRn66; }
    public void setSemestreObjetivoRn66(Integer valor) { semestreObjetivoRn66 = valor; }
    public Boolean getDentroCalendarioReincorporacionRn66() { return dentroCalendarioReincorporacionRn66; }
    public void setDentroCalendarioReincorporacionRn66(Boolean valor) { dentroCalendarioReincorporacionRn66 = valor; }
    public List<Long> getUdsPendientesPrioritariasRn66() { return udsPendientesPrioritariasRn66; }
    public List<Long> getUdsPendientesSinOfertaRn66() { return udsPendientesSinOfertaRn66; }
    public List<Long> getUdsPendientesSinCupoRn66() { return udsPendientesSinCupoRn66; }
    public List<Long> getUdsPosterioresCompatiblesRn66() { return udsPosterioresCompatiblesRn66; }
    public List<Long> getUdsPosterioresBloqueadasSeriacionRn66() { return udsPosterioresBloqueadasSeriacionRn66; }
    public List<Long> getUdsPosterioresRevisionSeriacionRn66() { return udsPosterioresRevisionSeriacionRn66; }
    public List<Long> getUdsRutaReincorporacionRn66() { return udsRutaReincorporacionRn66; }
    public List<String> getDecisionesPorUdRn66() { return decisionesPorUdRn66; }
    public Boolean getRn61ConsumidaRn66() { return rn61ConsumidaRn66; }
    public void setRn61ConsumidaRn66(Boolean valor) { rn61ConsumidaRn66 = valor; }

    public String getSituacionAcademica() { return situacionAcademica; }
    public void setSituacionAcademica(String valor) { situacionAcademica = valor; }
    public Integer getCargaMinimaNormativa() { return cargaMinimaNormativa; }
    public void setCargaMinimaNormativa(Integer valor) { cargaMinimaNormativa = valor; }
    public Integer getCargaMaximaNormativa() { return cargaMaximaNormativa; }
    public void setCargaMaximaNormativa(Integer valor) { cargaMaximaNormativa = valor; }
    public Integer getCargaEvaluada() { return cargaEvaluada; }
    public void setCargaEvaluada(Integer valor) { cargaEvaluada = valor; }
    public Boolean getMinimoCumplido() { return minimoCumplido; }
    public void setMinimoCumplido(Boolean valor) { minimoCumplido = valor; }
    public Boolean getExcepcionMinimoAplicable() { return excepcionMinimoAplicable; }
    public void setExcepcionMinimoAplicable(Boolean valor) { excepcionMinimoAplicable = valor; }
    public Boolean getMaximoCumplido() { return maximoCumplido; }
    public void setMaximoCumplido(Boolean valor) { maximoCumplido = valor; }
    public Boolean getRequiereRevision() { return requiereRevision; }
    public void setRequiereRevision(Boolean valor) { requiereRevision = valor; }
    public Boolean getRn64AplicableEnFuturaFase() { return rn64AplicableEnFuturaFase; }
    public void setRn64AplicableEnFuturaFase(Boolean valor) { rn64AplicableEnFuturaFase = valor; }
    public Boolean getCtr01Rn61Procedente() { return ctr01Rn61Procedente; }
    public void setCtr01Rn61Procedente(Boolean valor) { ctr01Rn61Procedente = valor; }
    public Boolean getAplicaRn63() { return aplicaRn63; }
    public void setAplicaRn63(Boolean valor) { aplicaRn63 = valor; }
    public Boolean getAplicaRn51() { return aplicaRn51; }
    public void setAplicaRn51(Boolean valor) { aplicaRn51 = valor; }
    public Boolean getAplicaRn52() { return aplicaRn52; }
    public void setAplicaRn52(Boolean valor) { aplicaRn52 = valor; }
    public String getCondicionRegularizacion() { return condicionRegularizacion; }
    public void setCondicionRegularizacion(String valor) { condicionRegularizacion = valor; }
    public Boolean getAplicaRn64() { return aplicaRn64; }
    public void setAplicaRn64(Boolean valor) { aplicaRn64 = valor; }
    public Boolean getAvanceCondicionadoBloqueadoPorRn64() { return avanceCondicionadoBloqueadoPorRn64; }
    public void setAvanceCondicionadoBloqueadoPorRn64(Boolean valor) { avanceCondicionadoBloqueadoPorRn64 = valor; }
    public Boolean getAplicaCtr01() { return aplicaCtr01; }
    public void setAplicaCtr01(Boolean valor) { aplicaCtr01 = valor; }
    public Boolean getAplicaRn61() { return aplicaRn61; }
    public void setAplicaRn61(Boolean valor) { aplicaRn61 = valor; }
    public Integer getTotalObligatoriasSemestres1A4() { return totalObligatoriasSemestres1A4; }
    public void setTotalObligatoriasSemestres1A4(Integer valor) { totalObligatoriasSemestres1A4 = valor; }
    public Boolean getTodasObligatoriasPreviasAcreditadas() { return todasObligatoriasPreviasAcreditadas; }
    public void setTodasObligatoriasPreviasAcreditadas(Boolean valor) { todasObligatoriasPreviasAcreditadas = valor; }
    public Boolean getAccesoOrdinarioQuinto() { return accesoOrdinarioQuinto; }
    public void setAccesoOrdinarioQuinto(Boolean valor) { accesoOrdinarioQuinto = valor; }
    public Boolean getRequiereEvaluacionAvanceCondicionado() { return requiereEvaluacionAvanceCondicionado; }
    public void setRequiereEvaluacionAvanceCondicionado(Boolean valor) { requiereEvaluacionAvanceCondicionado = valor; }
    public EstadoControlAvanceAnualV2 getEstadoControlAvanceAnual() { return estadoControlAvanceAnual; }
    public void setEstadoControlAvanceAnual(EstadoControlAvanceAnualV2 valor) { estadoControlAvanceAnual = valor; }
    public Boolean getRequiereRevisionRegularizacion() { return requiereRevisionRegularizacion; }
    public void setRequiereRevisionRegularizacion(Boolean valor) { requiereRevisionRegularizacion = valor; }
    public Integer getCargaDisponibleRegularizacion() { return cargaDisponibleRegularizacion; }
    public void setCargaDisponibleRegularizacion(Integer valor) { cargaDisponibleRegularizacion = valor; }
    public EstadoCargaAcademicaV2 getEstado() { return estado; }
    public void setEstado(EstadoCargaAcademicaV2 valor) { estado = valor; }
    public List<Long> getUdsPrioritarias() { return udsPrioritarias; }
    public List<Long> getUdsIncluidasSombra() { return udsIncluidasSombra; }
    public List<String> getUdsExcluidas() { return udsExcluidas; }
    public List<Long> getUdsReprobadasPrioritarias() { return udsReprobadasPrioritarias; }
    public List<Long> getUdsReprobadasOfertadas() { return udsReprobadasOfertadas; }
    public List<Long> getUdsReprobadasNoDisponibles() { return udsReprobadasNoDisponibles; }
    public List<Long> getUdsObligatoriasCompatibles() { return udsObligatoriasCompatibles; }
    public List<Long> getUdsOptativasCompatibles() { return udsOptativasCompatibles; }
    public List<Long> getUdsEmpatadasRegularizacion() { return udsEmpatadasRegularizacion; }
    public List<String> getCausasIndisponibilidadRegularizacion() { return causasIndisponibilidadRegularizacion; }
    public List<Long> getUdsObligatoriasNuevasBloqueadasRn64() { return udsObligatoriasNuevasBloqueadasRn64; }
    public List<Long> getUdsOptativasAdicionalesElegiblesRn64() { return udsOptativasAdicionalesElegiblesRn64; }
    public List<Long> getBaseRn611() { return baseRn611; }
    public List<Long> getObligatoriasAcreditadasRn611() { return obligatoriasAcreditadasRn611; }
    public List<Long> getObligatoriasPendientesRn611() { return obligatoriasPendientesRn611; }
    public List<String> getDetallePendientesRn611() { return detallePendientesRn611; }
    public EstadoCondicionesComunesAvanceV2 getEstadoCondicionesComunesAvance() { return estadoCondicionesComunesAvance; }
    public void setEstadoCondicionesComunesAvance(EstadoCondicionesComunesAvanceV2 valor) { estadoCondicionesComunesAvance = valor; }
    public List<CondicionComunAvanceV2DTO> getCondicionesComunesAvance() { return condicionesComunesAvance; }
    public List<Long> getUdsQuintoCompatiblesRn612() { return udsQuintoCompatiblesRn612; }
    public List<Long> getUdsQuintoBloqueadasSeriacionDirectaRn612() { return udsQuintoBloqueadasSeriacionDirectaRn612; }
    public List<Long> getUdsQuintoBloqueadasSeriacionIndirectaRn612() { return udsQuintoBloqueadasSeriacionIndirectaRn612; }
    public List<Long> getUdsQuintoRevisionSeriacionRn612() { return udsQuintoRevisionSeriacionRn612; }
    public EstadoSupuestosAvanceV2 getEstadoSupuestosAvance() { return estadoSupuestosAvance; }
    public void setEstadoSupuestosAvance(EstadoSupuestosAvanceV2 valor) { estadoSupuestosAvance = valor; }
    public List<SupuestoAvanceCondicionadoV2DTO> getSupuestosAvanceTipificados() { return supuestosAvanceTipificados; }
    public List<Long> getUdsQuintoSinDependenciaSeriacionRn613() { return udsQuintoSinDependenciaSeriacionRn613; }
    public List<EvaluacionOfertaPendienteRn614V2DTO> getOfertasPendientesRn614() { return ofertasPendientesRn614; }
    public EstadoResolucionAvanceV2 getEstadoResolucionAvance() { return estadoResolucionAvance; }
    public void setEstadoResolucionAvance(EstadoResolucionAvanceV2 valor) { estadoResolucionAvance = valor; }
    public List<String> getImpedimentosAvanceRn615() { return impedimentosAvanceRn615; }
    public List<Long> getUdsQuintoNoSusceptiblesRn615() { return udsQuintoNoSusceptiblesRn615; }
    public List<String> getRutaInstitucionalExtraordinariaRn616() { return rutaInstitucionalExtraordinariaRn616; }
    public EstadoDecisionControlAvanceRn61V2 getDecisionGlobalRn61() { return decisionGlobalRn61; }
    public void setDecisionGlobalRn61(EstadoDecisionControlAvanceRn61V2 valor) { decisionGlobalRn61 = valor; }
    public List<String> getDecisionesPorUdQuintoRn61() { return decisionesPorUdQuintoRn61; }
    public Boolean getAplicaRn62() { return aplicaRn62; } public void setAplicaRn62(Boolean valor) { aplicaRn62 = valor; }
    public EstadoIncorporacionDiferidaV2 getEstadoIncorporacionDiferida() { return estadoIncorporacionDiferida; } public void setEstadoIncorporacionDiferida(EstadoIncorporacionDiferidaV2 valor) { estadoIncorporacionDiferida = valor; }
    public String getCondicionTrayectoriaRn62() { return condicionTrayectoriaRn62; } public void setCondicionTrayectoriaRn62(String valor) { condicionTrayectoriaRn62 = valor; }
    public String getPeriodoAdmisionRn62() { return periodoAdmisionRn62; } public void setPeriodoAdmisionRn62(String valor) { periodoAdmisionRn62 = valor; }
    public String getPeriodoIncorporacionRn62() { return periodoIncorporacionRn62; } public void setPeriodoIncorporacionRn62(String valor) { periodoIncorporacionRn62 = valor; }
    public Integer getSemestreObjetivoRn62() { return semestreObjetivoRn62; } public void setSemestreObjetivoRn62(Integer valor) { semestreObjetivoRn62 = valor; }
    public List<Long> getUdsSemestre2CompatiblesRn62() { return udsSemestre2CompatiblesRn62; } public List<Long> getUdsSemestre2BloqueadasRn62() { return udsSemestre2BloqueadasRn62; }
    public List<CodigoMotivoCurricularV2> getCausasExclusion() { return causasExclusion; }
    public List<CodigoReglaCurricularV2> getReglasRelacionadas() { return reglasRelacionadas; }
    public List<String> getEvidencias() { return evidencias; }
}
