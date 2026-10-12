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
    /** RN-6.7 opera sobre bajas parciales por UD, sin equipararlas a reprobacion. */
    private Boolean aplicaRn67;
    private EstadoReincorporacionBajaParcialV2 estadoReincorporacionRn67 = EstadoReincorporacionBajaParcialV2.NO_APLICA;
    private String condicionTrayectoriaRn67;
    private List<Long> udsBajaParcialRn67 = new ArrayList<Long>();
    private List<Long> udsBajaParcialPrioritariasRn67 = new ArrayList<Long>();
    private List<Long> udsBajaParcialSinOfertaRn67 = new ArrayList<Long>();
    private List<Long> udsBajaParcialSinCupoRn67 = new ArrayList<Long>();
    private List<Long> udsBajaParcialRevisionRn67 = new ArrayList<Long>();
    private List<Long> udsBajaParcialSancionRn67 = new ArrayList<Long>();
    private List<Long> udsPosterioresCompatiblesRn67 = new ArrayList<Long>();
    private List<Long> udsPosterioresBloqueadasSeriacionRn67 = new ArrayList<Long>();
    private List<Long> udsRutaReincorporacionRn67 = new ArrayList<Long>();
    private List<String> decisionesPorUdRn67 = new ArrayList<String>();
    private Boolean rn61ConsumidaRn67 = Boolean.FALSE;
    /** RN-6.8 conserva el delta de cierre y una ruta non->par sólo en sombra. */
    private Boolean aplicaRn68;
    private EstadoActualizacionTrayectoriaRn68V2 estadoActualizacionTrayectoriaRn68 = EstadoActualizacionTrayectoriaRn68V2.NO_DETERMINADO;
    private Integer semestreCurricularCierreRn68;
    private Integer semestreObjetivoParRn68;
    private String situacionAcademicaAntesCierreRn68;
    private String situacionAcademicaVigenteRn68;
    private Integer totalReprobadasAntesCierreRn68;
    private Integer totalReprobadasVigentesRn68;
    private Boolean rn64AntesCierreRn68;
    private Boolean rn64VigenteRn68;
    private Boolean regularizacionAlcanzadaRn68;
    private Boolean requiereRevisionRn68 = Boolean.FALSE;
    private List<Long> udsAcreditadasDuranteCierreRn68 = new ArrayList<Long>();
    private List<Long> udsBloqueosResueltosRn68 = new ArrayList<Long>();
    private List<Long> udsBloqueosPendientesRn68 = new ArrayList<Long>();
    private List<Long> udsReprobadasParAnteriorPrioritariasRn68 = new ArrayList<Long>();
    private List<Long> udsReprobadasParAnteriorSinOfertaRn68 = new ArrayList<Long>();
    private List<Long> udsReprobadasParAnteriorSinCupoRn68 = new ArrayList<Long>();
    private List<Long> udsParCompatiblesRn68 = new ArrayList<Long>();
    private List<Long> udsParBloqueadasSeriacionRn68 = new ArrayList<Long>();
    private List<Long> udsRutaCierreRn68 = new ArrayList<Long>();
    private List<String> decisionesPorUdRn68 = new ArrayList<String>();
    /** F6 consolida causas mutuamente excluyentes y condiciones concurrentes. */
    private CausaPrincipalTrayectoriaV2 causaPrincipalTrayectoriaFase6 = CausaPrincipalTrayectoriaV2.NO_DETERMINADO;
    private EstadoConsolidacionFase6V2 estadoConsolidacionFase6 = EstadoConsolidacionFase6V2.NO_DETERMINADO;
    private Boolean requiereRevisionFase6 = Boolean.FALSE;
    private List<String> condicionesConcurrentesFase6 = new ArrayList<String>();
    private List<Long> udsRutaAcademicaConsolidadaFase6 = new ArrayList<Long>();
    private List<String> decisionesConsolidacionFase6 = new ArrayList<String>();
    private String trayectoriaAntesFase6;
    private String trayectoriaDespuesFase6;
    /** RN-7.1 y RN-7.1.1 consumen estados comunes sin crear otra ruta académica. */
    private Boolean aplicaRn71;
    private Boolean aplicaRn711;
    private EstadoRequerimientoOptativoV2 estadoRequerimientoOptativo = EstadoRequerimientoOptativoV2.NO_DETERMINADO;
    private Integer cantidadOptativasRequeridasRn711 = Integer.valueOf(8);
    private Integer cantidadOptativasAcreditadasRn711 = Integer.valueOf(0);
    private Integer cantidadOptativasFaltantesRn711 = Integer.valueOf(8);
    private Integer cantidadOptativasAdicionalesRn711 = Integer.valueOf(0);
    private Boolean requiereRevisionRn71 = Boolean.FALSE;
    private List<Long> udsOptativasIdentificadasRn71 = new ArrayList<Long>();
    private List<Long> udsOptativasAcreditadasRn711 = new ArrayList<Long>();
    private List<Long> udsOptativasAdicionalesRn711 = new ArrayList<Long>();
    private List<Long> udsOptativasReprobadasRn71 = new ArrayList<Long>();
    private List<Long> udsOptativasReprobadasPrioritariasRn71 = new ArrayList<Long>();
    private List<Long> udsOptativasReprobadasSinOfertaRn71 = new ArrayList<Long>();
    private List<Long> udsOptativasReprobadasSinCupoRn71 = new ArrayList<Long>();
    private List<Long> udsOptativasNuevasRn71 = new ArrayList<Long>();
    private List<String> decisionesPorUdOptativaRn71 = new ArrayList<String>();
    /** RN-7.2 conserva elegibilidad electiva sin seleccionar ni inscribir UD. */
    private Boolean aplicaRn72;
    private EstadoElegibilidadElectivaV2 estadoElegibilidadElectiva = EstadoElegibilidadElectivaV2.NO_DETERMINADO;
    private Boolean requiereRevisionRn72 = Boolean.FALSE;
    private List<Long> udsElectivasIdentificadasRn72 = new ArrayList<Long>();
    private List<Long> udsElectivasElegiblesRn72 = new ArrayList<Long>();
    private List<Long> udsElectivasMismoPeRn72 = new ArrayList<Long>();
    private List<Long> udsElectivasSinOfertaRn72 = new ArrayList<Long>();
    private List<Long> udsElectivasSinCupoRn72 = new ArrayList<Long>();
    private List<Long> udsElectivasBloqueadasSeriacionRn72 = new ArrayList<Long>();
    private List<Long> udsElectivasReprobadasRn72 = new ArrayList<Long>();
    private List<Long> udsElectivasConRestriccionPrioridadRn72 = new ArrayList<Long>();
    private List<String> decisionesPorUdElectivaRn72 = new ArrayList<String>();
    /** RN-7.3 consolida optativas/electivas sin reemplazar motores comunes. */
    private Boolean aplicaRn73;
    private EstadoPrincipioOptativaElectivaV2 estadoPrincipioOptativaElectiva = EstadoPrincipioOptativaElectivaV2.NO_DETERMINADO;
    private Boolean requiereRevisionRn73 = Boolean.FALSE;
    private Integer cantidadOptativasReprobadasRn73 = Integer.valueOf(0);
    private Integer cantidadElectivasReprobadasRn73 = Integer.valueOf(0);
    private Integer cantidadObligatoriasReprobadasRn73 = Integer.valueOf(0);
    private Integer totalReprobadasComunRn73 = Integer.valueOf(0);
    private Boolean bloqueoAutonomoContinuidadPorTipoRn73 = Boolean.FALSE;
    private Boolean bloqueoAutonomoQuintoPorTipoRn73 = Boolean.FALSE;
    private Boolean continuidadPotencialSujetaReglasRn73 = Boolean.FALSE;
    private List<Long> udsOptativasReprobadasRn73 = new ArrayList<Long>();
    private List<Long> udsElectivasReprobadasRn73 = new ArrayList<Long>();
    private List<Long> udsObligatoriasReprobadasRn73 = new ArrayList<Long>();
    private List<Long> udsObligatoriasPrioritariasRn73 = new ArrayList<Long>();
    private List<String> decisionesPorUdRn73 = new ArrayList<String>();
    /** F7D audita consistencia entre tipos sin introducir reglas académicas nuevas. */
    private EstadoCierreFase7V2 estadoCierreFase7 = EstadoCierreFase7V2.NO_DETERMINADO;
    private Boolean requiereRevisionFase7 = Boolean.FALSE;
    private List<Long> udsTipoAmbiguoFase7 = new ArrayList<Long>();
    private List<String> decisionesCierreFase7 = new ArrayList<String>();
    /** RN-8/RN-8.1: relaciones curriculares en sombra; no modifica seleccionabilidad origen. */
    private Boolean aplicaRn81 = Boolean.FALSE;
    private Boolean requiereRevisionRn81 = Boolean.FALSE;
    private List<RelacionSeriacionCurricularV2DTO> relacionesSeriacionV2 = new ArrayList<RelacionSeriacionCurricularV2DTO>();
    private List<Long> udsInterbloqueCumplidasRn81 = new ArrayList<Long>();
    private List<Long> udsInterbloqueCondicionadasRn81 = new ArrayList<Long>();
    private List<Long> udsInterbloqueNoCumplidasRn81 = new ArrayList<Long>();
    private List<Long> udsInterbloqueRevisionRn81 = new ArrayList<Long>();
    private List<String> decisionesPorUdSeriacionRn81 = new ArrayList<String>();
    /** RN-8.2 consume el mismo modelo de relación y no modifica oferta o selección. */
    private Boolean aplicaRn82 = Boolean.FALSE;
    private Boolean requiereRevisionRn82 = Boolean.FALSE;
    private List<Long> udsIntersemestralesCumplidasRn82 = new ArrayList<Long>();
    private List<Long> udsIntersemestralesNoCumplidasRn82 = new ArrayList<Long>();
    private List<Long> udsIntersemestralesRevisionRn82 = new ArrayList<Long>();
    private List<String> decisionesPorUdSeriacionRn82 = new ArrayList<String>();
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
    public Boolean getAplicaRn67() { return aplicaRn67; }
    public void setAplicaRn67(Boolean valor) { aplicaRn67 = valor; }
    public EstadoReincorporacionBajaParcialV2 getEstadoReincorporacionRn67() { return estadoReincorporacionRn67; }
    public void setEstadoReincorporacionRn67(EstadoReincorporacionBajaParcialV2 valor) { estadoReincorporacionRn67 = valor; }
    public String getCondicionTrayectoriaRn67() { return condicionTrayectoriaRn67; }
    public void setCondicionTrayectoriaRn67(String valor) { condicionTrayectoriaRn67 = valor; }
    public List<Long> getUdsBajaParcialRn67() { return udsBajaParcialRn67; }
    public List<Long> getUdsBajaParcialPrioritariasRn67() { return udsBajaParcialPrioritariasRn67; }
    public List<Long> getUdsBajaParcialSinOfertaRn67() { return udsBajaParcialSinOfertaRn67; }
    public List<Long> getUdsBajaParcialSinCupoRn67() { return udsBajaParcialSinCupoRn67; }
    public List<Long> getUdsBajaParcialRevisionRn67() { return udsBajaParcialRevisionRn67; }
    public List<Long> getUdsBajaParcialSancionRn67() { return udsBajaParcialSancionRn67; }
    public List<Long> getUdsPosterioresCompatiblesRn67() { return udsPosterioresCompatiblesRn67; }
    public List<Long> getUdsPosterioresBloqueadasSeriacionRn67() { return udsPosterioresBloqueadasSeriacionRn67; }
    public List<Long> getUdsRutaReincorporacionRn67() { return udsRutaReincorporacionRn67; }
    public List<String> getDecisionesPorUdRn67() { return decisionesPorUdRn67; }
    public Boolean getRn61ConsumidaRn67() { return rn61ConsumidaRn67; }
    public void setRn61ConsumidaRn67(Boolean valor) { rn61ConsumidaRn67 = valor; }
    public Boolean getAplicaRn68() { return aplicaRn68; }
    public void setAplicaRn68(Boolean valor) { aplicaRn68 = valor; }
    public EstadoActualizacionTrayectoriaRn68V2 getEstadoActualizacionTrayectoriaRn68() { return estadoActualizacionTrayectoriaRn68; }
    public void setEstadoActualizacionTrayectoriaRn68(EstadoActualizacionTrayectoriaRn68V2 valor) { estadoActualizacionTrayectoriaRn68 = valor; }
    public Integer getSemestreCurricularCierreRn68() { return semestreCurricularCierreRn68; }
    public void setSemestreCurricularCierreRn68(Integer valor) { semestreCurricularCierreRn68 = valor; }
    public Integer getSemestreObjetivoParRn68() { return semestreObjetivoParRn68; }
    public void setSemestreObjetivoParRn68(Integer valor) { semestreObjetivoParRn68 = valor; }
    public String getSituacionAcademicaAntesCierreRn68() { return situacionAcademicaAntesCierreRn68; }
    public void setSituacionAcademicaAntesCierreRn68(String valor) { situacionAcademicaAntesCierreRn68 = valor; }
    public String getSituacionAcademicaVigenteRn68() { return situacionAcademicaVigenteRn68; }
    public void setSituacionAcademicaVigenteRn68(String valor) { situacionAcademicaVigenteRn68 = valor; }
    public Integer getTotalReprobadasAntesCierreRn68() { return totalReprobadasAntesCierreRn68; }
    public void setTotalReprobadasAntesCierreRn68(Integer valor) { totalReprobadasAntesCierreRn68 = valor; }
    public Integer getTotalReprobadasVigentesRn68() { return totalReprobadasVigentesRn68; }
    public void setTotalReprobadasVigentesRn68(Integer valor) { totalReprobadasVigentesRn68 = valor; }
    public Boolean getRn64AntesCierreRn68() { return rn64AntesCierreRn68; }
    public void setRn64AntesCierreRn68(Boolean valor) { rn64AntesCierreRn68 = valor; }
    public Boolean getRn64VigenteRn68() { return rn64VigenteRn68; }
    public void setRn64VigenteRn68(Boolean valor) { rn64VigenteRn68 = valor; }
    public Boolean getRegularizacionAlcanzadaRn68() { return regularizacionAlcanzadaRn68; }
    public void setRegularizacionAlcanzadaRn68(Boolean valor) { regularizacionAlcanzadaRn68 = valor; }
    public Boolean getRequiereRevisionRn68() { return requiereRevisionRn68; }
    public void setRequiereRevisionRn68(Boolean valor) { requiereRevisionRn68 = valor; }
    public List<Long> getUdsAcreditadasDuranteCierreRn68() { return udsAcreditadasDuranteCierreRn68; }
    public List<Long> getUdsBloqueosResueltosRn68() { return udsBloqueosResueltosRn68; }
    public List<Long> getUdsBloqueosPendientesRn68() { return udsBloqueosPendientesRn68; }
    public List<Long> getUdsReprobadasParAnteriorPrioritariasRn68() { return udsReprobadasParAnteriorPrioritariasRn68; }
    public List<Long> getUdsReprobadasParAnteriorSinOfertaRn68() { return udsReprobadasParAnteriorSinOfertaRn68; }
    public List<Long> getUdsReprobadasParAnteriorSinCupoRn68() { return udsReprobadasParAnteriorSinCupoRn68; }
    public List<Long> getUdsParCompatiblesRn68() { return udsParCompatiblesRn68; }
    public List<Long> getUdsParBloqueadasSeriacionRn68() { return udsParBloqueadasSeriacionRn68; }
    public List<Long> getUdsRutaCierreRn68() { return udsRutaCierreRn68; }
    public List<String> getDecisionesPorUdRn68() { return decisionesPorUdRn68; }
    public CausaPrincipalTrayectoriaV2 getCausaPrincipalTrayectoriaFase6() { return causaPrincipalTrayectoriaFase6; }
    public void setCausaPrincipalTrayectoriaFase6(CausaPrincipalTrayectoriaV2 valor) { causaPrincipalTrayectoriaFase6 = valor; }
    public EstadoConsolidacionFase6V2 getEstadoConsolidacionFase6() { return estadoConsolidacionFase6; }
    public void setEstadoConsolidacionFase6(EstadoConsolidacionFase6V2 valor) { estadoConsolidacionFase6 = valor; }
    public Boolean getRequiereRevisionFase6() { return requiereRevisionFase6; }
    public void setRequiereRevisionFase6(Boolean valor) { requiereRevisionFase6 = valor; }
    public List<String> getCondicionesConcurrentesFase6() { return condicionesConcurrentesFase6; }
    public List<Long> getUdsRutaAcademicaConsolidadaFase6() { return udsRutaAcademicaConsolidadaFase6; }
    public List<String> getDecisionesConsolidacionFase6() { return decisionesConsolidacionFase6; }
    public String getTrayectoriaAntesFase6() { return trayectoriaAntesFase6; }
    public void setTrayectoriaAntesFase6(String valor) { trayectoriaAntesFase6 = valor; }
    public String getTrayectoriaDespuesFase6() { return trayectoriaDespuesFase6; }
    public void setTrayectoriaDespuesFase6(String valor) { trayectoriaDespuesFase6 = valor; }
    public Boolean getAplicaRn71() { return aplicaRn71; } public void setAplicaRn71(Boolean valor) { aplicaRn71 = valor; }
    public Boolean getAplicaRn711() { return aplicaRn711; } public void setAplicaRn711(Boolean valor) { aplicaRn711 = valor; }
    public EstadoRequerimientoOptativoV2 getEstadoRequerimientoOptativo() { return estadoRequerimientoOptativo; }
    public void setEstadoRequerimientoOptativo(EstadoRequerimientoOptativoV2 valor) { estadoRequerimientoOptativo = valor; }
    public Integer getCantidadOptativasRequeridasRn711() { return cantidadOptativasRequeridasRn711; } public void setCantidadOptativasRequeridasRn711(Integer valor) { cantidadOptativasRequeridasRn711 = valor; }
    public Integer getCantidadOptativasAcreditadasRn711() { return cantidadOptativasAcreditadasRn711; } public void setCantidadOptativasAcreditadasRn711(Integer valor) { cantidadOptativasAcreditadasRn711 = valor; }
    public Integer getCantidadOptativasFaltantesRn711() { return cantidadOptativasFaltantesRn711; } public void setCantidadOptativasFaltantesRn711(Integer valor) { cantidadOptativasFaltantesRn711 = valor; }
    public Integer getCantidadOptativasAdicionalesRn711() { return cantidadOptativasAdicionalesRn711; } public void setCantidadOptativasAdicionalesRn711(Integer valor) { cantidadOptativasAdicionalesRn711 = valor; }
    public Boolean getRequiereRevisionRn71() { return requiereRevisionRn71; } public void setRequiereRevisionRn71(Boolean valor) { requiereRevisionRn71 = valor; }
    public List<Long> getUdsOptativasIdentificadasRn71() { return udsOptativasIdentificadasRn71; }
    public List<Long> getUdsOptativasAcreditadasRn711() { return udsOptativasAcreditadasRn711; }
    public List<Long> getUdsOptativasAdicionalesRn711() { return udsOptativasAdicionalesRn711; }
    public List<Long> getUdsOptativasReprobadasRn71() { return udsOptativasReprobadasRn71; }
    public List<Long> getUdsOptativasReprobadasPrioritariasRn71() { return udsOptativasReprobadasPrioritariasRn71; }
    public List<Long> getUdsOptativasReprobadasSinOfertaRn71() { return udsOptativasReprobadasSinOfertaRn71; }
    public List<Long> getUdsOptativasReprobadasSinCupoRn71() { return udsOptativasReprobadasSinCupoRn71; }
    public List<Long> getUdsOptativasNuevasRn71() { return udsOptativasNuevasRn71; }
    public List<String> getDecisionesPorUdOptativaRn71() { return decisionesPorUdOptativaRn71; }
    public Boolean getAplicaRn72() { return aplicaRn72; } public void setAplicaRn72(Boolean valor) { aplicaRn72 = valor; }
    public EstadoElegibilidadElectivaV2 getEstadoElegibilidadElectiva() { return estadoElegibilidadElectiva; }
    public void setEstadoElegibilidadElectiva(EstadoElegibilidadElectivaV2 valor) { estadoElegibilidadElectiva = valor; }
    public Boolean getRequiereRevisionRn72() { return requiereRevisionRn72; } public void setRequiereRevisionRn72(Boolean valor) { requiereRevisionRn72 = valor; }
    public List<Long> getUdsElectivasIdentificadasRn72() { return udsElectivasIdentificadasRn72; }
    public List<Long> getUdsElectivasElegiblesRn72() { return udsElectivasElegiblesRn72; }
    public List<Long> getUdsElectivasMismoPeRn72() { return udsElectivasMismoPeRn72; }
    public List<Long> getUdsElectivasSinOfertaRn72() { return udsElectivasSinOfertaRn72; }
    public List<Long> getUdsElectivasSinCupoRn72() { return udsElectivasSinCupoRn72; }
    public List<Long> getUdsElectivasBloqueadasSeriacionRn72() { return udsElectivasBloqueadasSeriacionRn72; }
    public List<Long> getUdsElectivasReprobadasRn72() { return udsElectivasReprobadasRn72; }
    public List<Long> getUdsElectivasConRestriccionPrioridadRn72() { return udsElectivasConRestriccionPrioridadRn72; }
    public List<String> getDecisionesPorUdElectivaRn72() { return decisionesPorUdElectivaRn72; }
    public Boolean getAplicaRn73() { return aplicaRn73; } public void setAplicaRn73(Boolean valor) { aplicaRn73 = valor; }
    public EstadoPrincipioOptativaElectivaV2 getEstadoPrincipioOptativaElectiva() { return estadoPrincipioOptativaElectiva; }
    public void setEstadoPrincipioOptativaElectiva(EstadoPrincipioOptativaElectivaV2 valor) { estadoPrincipioOptativaElectiva = valor; }
    public Boolean getRequiereRevisionRn73() { return requiereRevisionRn73; } public void setRequiereRevisionRn73(Boolean valor) { requiereRevisionRn73 = valor; }
    public Integer getCantidadOptativasReprobadasRn73() { return cantidadOptativasReprobadasRn73; } public void setCantidadOptativasReprobadasRn73(Integer valor) { cantidadOptativasReprobadasRn73 = valor; }
    public Integer getCantidadElectivasReprobadasRn73() { return cantidadElectivasReprobadasRn73; } public void setCantidadElectivasReprobadasRn73(Integer valor) { cantidadElectivasReprobadasRn73 = valor; }
    public Integer getCantidadObligatoriasReprobadasRn73() { return cantidadObligatoriasReprobadasRn73; } public void setCantidadObligatoriasReprobadasRn73(Integer valor) { cantidadObligatoriasReprobadasRn73 = valor; }
    public Integer getTotalReprobadasComunRn73() { return totalReprobadasComunRn73; } public void setTotalReprobadasComunRn73(Integer valor) { totalReprobadasComunRn73 = valor; }
    public Boolean getBloqueoAutonomoContinuidadPorTipoRn73() { return bloqueoAutonomoContinuidadPorTipoRn73; } public void setBloqueoAutonomoContinuidadPorTipoRn73(Boolean valor) { bloqueoAutonomoContinuidadPorTipoRn73 = valor; }
    public Boolean getBloqueoAutonomoQuintoPorTipoRn73() { return bloqueoAutonomoQuintoPorTipoRn73; } public void setBloqueoAutonomoQuintoPorTipoRn73(Boolean valor) { bloqueoAutonomoQuintoPorTipoRn73 = valor; }
    public Boolean getContinuidadPotencialSujetaReglasRn73() { return continuidadPotencialSujetaReglasRn73; } public void setContinuidadPotencialSujetaReglasRn73(Boolean valor) { continuidadPotencialSujetaReglasRn73 = valor; }
    public List<Long> getUdsOptativasReprobadasRn73() { return udsOptativasReprobadasRn73; }
    public List<Long> getUdsElectivasReprobadasRn73() { return udsElectivasReprobadasRn73; }
    public List<Long> getUdsObligatoriasReprobadasRn73() { return udsObligatoriasReprobadasRn73; }
    public List<Long> getUdsObligatoriasPrioritariasRn73() { return udsObligatoriasPrioritariasRn73; }
    public List<String> getDecisionesPorUdRn73() { return decisionesPorUdRn73; }
    public EstadoCierreFase7V2 getEstadoCierreFase7() { return estadoCierreFase7; } public void setEstadoCierreFase7(EstadoCierreFase7V2 valor) { estadoCierreFase7 = valor; }
    public Boolean getRequiereRevisionFase7() { return requiereRevisionFase7; } public void setRequiereRevisionFase7(Boolean valor) { requiereRevisionFase7 = valor; }
    public List<Long> getUdsTipoAmbiguoFase7() { return udsTipoAmbiguoFase7; }
    public List<String> getDecisionesCierreFase7() { return decisionesCierreFase7; }
    public Boolean getAplicaRn81() { return aplicaRn81; } public void setAplicaRn81(Boolean valor) { aplicaRn81 = valor; }
    public Boolean getRequiereRevisionRn81() { return requiereRevisionRn81; } public void setRequiereRevisionRn81(Boolean valor) { requiereRevisionRn81 = valor; }
    public List<RelacionSeriacionCurricularV2DTO> getRelacionesSeriacionV2() { return relacionesSeriacionV2; }
    public List<Long> getUdsInterbloqueCumplidasRn81() { return udsInterbloqueCumplidasRn81; }
    public List<Long> getUdsInterbloqueCondicionadasRn81() { return udsInterbloqueCondicionadasRn81; }
    public List<Long> getUdsInterbloqueNoCumplidasRn81() { return udsInterbloqueNoCumplidasRn81; }
    public List<Long> getUdsInterbloqueRevisionRn81() { return udsInterbloqueRevisionRn81; }
    public List<String> getDecisionesPorUdSeriacionRn81() { return decisionesPorUdSeriacionRn81; }
    public Boolean getAplicaRn82() { return aplicaRn82; } public void setAplicaRn82(Boolean valor) { aplicaRn82 = valor; }
    public Boolean getRequiereRevisionRn82() { return requiereRevisionRn82; } public void setRequiereRevisionRn82(Boolean valor) { requiereRevisionRn82 = valor; }
    public List<Long> getUdsIntersemestralesCumplidasRn82() { return udsIntersemestralesCumplidasRn82; }
    public List<Long> getUdsIntersemestralesNoCumplidasRn82() { return udsIntersemestralesNoCumplidasRn82; }
    public List<Long> getUdsIntersemestralesRevisionRn82() { return udsIntersemestralesRevisionRn82; }
    public List<String> getDecisionesPorUdSeriacionRn82() { return decisionesPorUdSeriacionRn82; }

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
