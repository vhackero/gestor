package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contexto de sólo lectura para comparar el motor vigente con la evaluación V2.
 * No representa una solicitud de inscripción. Puede transportar UD candidatas
 * exclusivamente para evaluar una propuesta en sombra.
 */
public class ContextoEvaluacionCurricularV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPersona;
    private Long idPlan;
    private String clavePlan;
    private String cohorte;
    private Long idPeriodo;
    private String periodoNormativo;
    private Long idProcesoInscripcion;
    /** Total crediticio del PE aportado por estructura curricular verificable. */
    private BigDecimal creditosTotalesPrograma;
    /** Momento administrativo informado por fuente canónica, independiente de la trayectoria. */
    private TipoProcesoAdministrativoV2 tipoProcesoAdministrativo = TipoProcesoAdministrativoV2.NO_DETERMINADO;
    private String evidenciaProcesoAdministrativo;
    /** Metadatos no PII de una resolución extraordinaria ya emitida, si existe. */
    private String instanciaResolutoraExtraordinaria;
    private String fundamentacionExtraordinaria;
    private String folioInstitucionalExtraordinario;
    /** @deprecated Dato de compatibilidad: no debe gobernar reglas académicas. */
    private Integer semestreObjetivo;
    private Integer semestreReferencia;
    private Integer semestreMaximoCandidatas;
    /** Compatibilidad de contratos de prueba/cliente previos; el adaptador productivo no lo llena. */
    private Boolean candidataQuintoDeclaradaCompatibilidad = Boolean.FALSE;
    private SemestreReferenciaV2DTO detalleSemestreReferencia;
    private ParametrosCurricularesV2DTO parametrosCurriculares;
    private String ambitoRnPe2024_2027 = "REQUIERE_REVISION";
    private Boolean universoMapaCompleto;
    private Boolean historialCompleto;
    private List<PeriodoTrayectoriaV2DTO> periodosTrayectoria = new ArrayList<PeriodoTrayectoriaV2DTO>();
    private List<Long> idsUnidadesCandidatas = new ArrayList<Long>();
    private String bloqueObjetivo;
    private String perfilConsulta;
    private String origenConsulta;
    private String versionReglas;
    private String huellaOferta;
    private String situacionAcademicaOrigen;
    private Integer totalReprobadasOrigen;
    private Integer cargaMinimaOrigen;
    private Integer cargaMaximaOrigen;
    private Boolean ctr01ProcedenciaFutura = Boolean.FALSE;
    private Boolean bajaTemporalRegistrada = Boolean.FALSE;
    /* Datos de actividad sólo se llenan cuando la fuente canónica los acredita. */
    private Boolean bajaTemporalTotalRegistrada = Boolean.FALSE;
    private Boolean bajaParcialRegistrada = Boolean.FALSE;
    private Boolean reincorporacionRegistrada = Boolean.FALSE;
    private Boolean bajaDefinitivaRegistrada = Boolean.FALSE;
    private Boolean actividadAcademicaComprobada = Boolean.FALSE;
    private Boolean seriacionComprobada = Boolean.TRUE;
    private Boolean limiteInactividadAlcanzado = Boolean.FALSE;
    private Boolean datosActividadContradictorios = Boolean.FALSE;
    private Boolean permanenciaDentroLimiteComprobada = Boolean.FALSE;
    private Boolean limitePermanenciaExcedido = Boolean.FALSE;
    private Boolean dictaminacionEgresoConocida = Boolean.FALSE;
    private Boolean dictaminacionEgresoIniciada = Boolean.FALSE;
    private Integer oportunidadesMejoraUtilizadas;
    private Integer periodosBajaTemporalAutorizados;
    private Integer periodosBajaTemporalTranscurridos;
    private List<String> periodosActividadConsiderados = new ArrayList<String>();
    private List<String> evidenciasActividad = new ArrayList<String>();
    private Boolean avanceCondicionado;
    /** Sólo se llena cuando una fuente canónica acredita el dato disciplinario. */
    private Boolean sancionImpedimentoConocida;
    private Boolean sancionImpedimentoRegistrada;
    /** Compatibilidad curricular del plan acreditada por fuente canónica; null preserva incertidumbre. */
    private Boolean estructuraProgramaEducativoComprobada;
    private Boolean restriccionPeNormativaConocida;
    private Boolean restriccionPeNormativaImpeditiva;
    private String codigoRestriccionPeNormativa;
    private String fuenteRestriccionPeNormativa;
    private Boolean admitido;
    private Boolean inscripcionInicialRealizada;
    private String periodoAdmision;
    private String periodoIncorporacion;
    /** Referencia verificable de la convocatoria o resolución requerida por RN-6.2. */
    private String referenciaConvocatoriaOResolucionRn62;
    /** Medida administrativa externa: no se infiere su alcance ni su vigencia. */
    private Boolean medidaAdministrativaRegistrada;
    private String alcanceMedidaAdministrativa;
    private String inicioVigenciaMedidaAdministrativa;
    private String finVigenciaMedidaAdministrativa;
    private String condicionVigenciaMedidaAdministrativa;
    /** Evidencia de trayectoria requerida por RN-6.5; null conserva incertidumbre. */
    private Boolean haCursadoUnoOMasPeriodos;
    private Boolean noCursoPeriodoInmediatoSiguiente;
    private Boolean causaInterrupcionConocida;
    private Boolean limiteInactividadComprobado;
    private String causaInterrupcion;
    private String ultimoPeriodoCursado;
    private String primerPeriodoOmitido;
    private List<String> periodosOmitidos = new ArrayList<String>();
    private String periodoReincorporacion;
    private TipoProcesoReincorporacionV2 tipoProcesoReincorporacion = TipoProcesoReincorporacionV2.NO_DETERMINADO;
    /** Evidencia de RN-6.6; null preserva incertidumbre y evita inferir una baja formal. */
    private Boolean bajaTemporalFormalComprobada;
    private Boolean bajaTemporalConcluida;
    private String periodoInicioBajaTemporal;
    private String periodoFinBajaTemporal;
    private Integer semestreEfectivoPrevioBaja;
    private Boolean calendarioReincorporacionComprobado;
    private Boolean dentroCalendarioReincorporacion;
    private Boolean limitePermanenciaComprobado;
    /** Evidencia de corte para RN-6.8; null evita inferir un cierre académico. */
    private Boolean cierrePeriodoAcademicoComprobado;
    private Integer semestreCurricularCierre;
    private Boolean bloqueosContinuidadAntesComprobados;
    private String situacionAcademicaAntesCierre;
    private Integer totalReprobadasAntesCierre;
    private Boolean rn64AntesCierre;
    /** Configuracion de PE opcional para detectar discrepancia frente al umbral normativo de ocho. */
    private Integer optativasRequeridasPrograma;
    /** Universos explícitos del PE para RN-9.4; nunca se infieren de la trayectoria. */
    private List<Long> idsObligatoriasRequeridasEgreso = new ArrayList<Long>();
    private List<Long> idsSeriadasPrimerosSeisSemestres = new ArrayList<Long>();
    private List<Long> componentesFinalesSolicitados = new ArrayList<Long>();
    /** UD que se pretenden habilitar o evaluar; no genera una inscripción. */
    private List<Long> idsUnidadesPretendidas = new ArrayList<Long>();
    private Date fechaEvaluacion;
    private List<UnidadEvaluacionCurricularV2DTO> unidades = new ArrayList<UnidadEvaluacionCurricularV2DTO>();

    public Long getIdPersona() { return idPersona; }
    public void setIdPersona(Long idPersona) { this.idPersona = idPersona; }
    public Long getIdPlan() { return idPlan; }
    public void setIdPlan(Long idPlan) { this.idPlan = idPlan; }
    public String getClavePlan() { return clavePlan; }
    public void setClavePlan(String valor) { clavePlan = valor; }
    public String getCohorte() { return cohorte; }
    public void setCohorte(String valor) { cohorte = valor; }
    public Long getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(Long idPeriodo) { this.idPeriodo = idPeriodo; }
    public String getPeriodoNormativo() { return periodoNormativo; }
    public void setPeriodoNormativo(String valor) { periodoNormativo = valor; }
    public Long getIdProcesoInscripcion() { return idProcesoInscripcion; }
    public void setIdProcesoInscripcion(Long idProcesoInscripcion) { this.idProcesoInscripcion = idProcesoInscripcion; }
    public BigDecimal getCreditosTotalesPrograma() { return creditosTotalesPrograma; }
    public void setCreditosTotalesPrograma(BigDecimal valor) { creditosTotalesPrograma = valor; }
    public TipoProcesoAdministrativoV2 getTipoProcesoAdministrativo() { return tipoProcesoAdministrativo; }
    public void setTipoProcesoAdministrativo(TipoProcesoAdministrativoV2 valor) { tipoProcesoAdministrativo = valor == null ? TipoProcesoAdministrativoV2.NO_DETERMINADO : valor; }
    public String getEvidenciaProcesoAdministrativo() { return evidenciaProcesoAdministrativo; }
    public void setEvidenciaProcesoAdministrativo(String valor) { evidenciaProcesoAdministrativo = valor; }
    public String getInstanciaResolutoraExtraordinaria() { return instanciaResolutoraExtraordinaria; }
    public void setInstanciaResolutoraExtraordinaria(String valor) { instanciaResolutoraExtraordinaria = valor; }
    public String getFundamentacionExtraordinaria() { return fundamentacionExtraordinaria; }
    public void setFundamentacionExtraordinaria(String valor) { fundamentacionExtraordinaria = valor; }
    public String getFolioInstitucionalExtraordinario() { return folioInstitucionalExtraordinario; }
    public void setFolioInstitucionalExtraordinario(String valor) { folioInstitucionalExtraordinario = valor; }
    /** @deprecated Use getSemestreReferencia(); conservado para contratos binarios previos. */
    public Integer getSemestreObjetivo() { return semestreReferencia; }
    /** Compatibilidad de contratos previos; los evaluadores usan semestreReferencia. */
    public void setSemestreObjetivo(Integer valor) {
        semestreObjetivo = valor;
        semestreReferencia = valor;
        semestreMaximoCandidatas = valor;
        candidataQuintoDeclaradaCompatibilidad = Boolean.valueOf(Integer.valueOf(5).equals(valor));
    }
    public Integer getSemestreReferencia() { return semestreReferencia; }
    public void setSemestreReferencia(Integer valor) { semestreReferencia = valor; }
    public Integer getSemestreMaximoCandidatas() { return semestreMaximoCandidatas; }
    public void setSemestreMaximoCandidatas(Integer valor) { semestreMaximoCandidatas = valor; }
    public Boolean getCandidataQuintoDeclaradaCompatibilidad() { return candidataQuintoDeclaradaCompatibilidad; }
    public void setCandidataQuintoDeclaradaCompatibilidad(Boolean valor) { candidataQuintoDeclaradaCompatibilidad = valor; }
    public SemestreReferenciaV2DTO getDetalleSemestreReferencia() { return detalleSemestreReferencia; }
    public void setDetalleSemestreReferencia(SemestreReferenciaV2DTO valor) { detalleSemestreReferencia = valor; semestreReferencia = valor == null ? semestreReferencia : valor.getValor(); }
    public ParametrosCurricularesV2DTO getParametrosCurriculares() { return parametrosCurriculares; }
    public void setParametrosCurriculares(ParametrosCurricularesV2DTO valor) { parametrosCurriculares = valor; }
    public String getAmbitoRnPe2024_2027() { return ambitoRnPe2024_2027; }
    public void setAmbitoRnPe2024_2027(String valor) { ambitoRnPe2024_2027 = valor; }
    public Boolean getUniversoMapaCompleto() { return universoMapaCompleto; }
    public void setUniversoMapaCompleto(Boolean valor) { universoMapaCompleto = valor; }
    public Boolean getHistorialCompleto() { return historialCompleto; }
    public void setHistorialCompleto(Boolean valor) { historialCompleto = valor; }
    public List<PeriodoTrayectoriaV2DTO> getPeriodosTrayectoria() { return periodosTrayectoria; }
    public void setPeriodosTrayectoria(List<PeriodoTrayectoriaV2DTO> valores) { periodosTrayectoria = valores == null ? new ArrayList<PeriodoTrayectoriaV2DTO>() : valores; }
    public String getBloqueObjetivo() { return bloqueObjetivo; }
    public void setBloqueObjetivo(String bloqueObjetivo) { this.bloqueObjetivo = bloqueObjetivo; }
    public String getPerfilConsulta() { return perfilConsulta; }
    public void setPerfilConsulta(String perfilConsulta) { this.perfilConsulta = perfilConsulta; }
    public String getOrigenConsulta() { return origenConsulta; }
    public void setOrigenConsulta(String origenConsulta) { this.origenConsulta = origenConsulta; }
    public String getVersionReglas() { return versionReglas; }
    public void setVersionReglas(String versionReglas) { this.versionReglas = versionReglas; }
    public String getHuellaOferta() { return huellaOferta; }
    public void setHuellaOferta(String huellaOferta) { this.huellaOferta = huellaOferta; }
    public String getSituacionAcademicaOrigen() { return situacionAcademicaOrigen; }
    public void setSituacionAcademicaOrigen(String situacionAcademicaOrigen) { this.situacionAcademicaOrigen = situacionAcademicaOrigen; }
    public Integer getTotalReprobadasOrigen() { return totalReprobadasOrigen; }
    public void setTotalReprobadasOrigen(Integer totalReprobadasOrigen) { this.totalReprobadasOrigen = totalReprobadasOrigen; }
    public Integer getCargaMinimaOrigen() { return cargaMinimaOrigen; }
    public void setCargaMinimaOrigen(Integer cargaMinimaOrigen) { this.cargaMinimaOrigen = cargaMinimaOrigen; }
    public Integer getCargaMaximaOrigen() { return cargaMaximaOrigen; }
    public void setCargaMaximaOrigen(Integer cargaMaximaOrigen) { this.cargaMaximaOrigen = cargaMaximaOrigen; }
    public Boolean getCtr01ProcedenciaFutura() { return ctr01ProcedenciaFutura; }
    public void setCtr01ProcedenciaFutura(Boolean ctr01ProcedenciaFutura) { this.ctr01ProcedenciaFutura = ctr01ProcedenciaFutura; }
    public Boolean getBajaTemporalRegistrada() { return bajaTemporalRegistrada; }
    public void setBajaTemporalRegistrada(Boolean bajaTemporalRegistrada) { this.bajaTemporalRegistrada = bajaTemporalRegistrada; }
    public Boolean getBajaTemporalTotalRegistrada() { return bajaTemporalTotalRegistrada; }
    public void setBajaTemporalTotalRegistrada(Boolean valor) { bajaTemporalTotalRegistrada = valor; }
    public Boolean getBajaParcialRegistrada() { return bajaParcialRegistrada; }
    public void setBajaParcialRegistrada(Boolean valor) { bajaParcialRegistrada = valor; }
    public Boolean getReincorporacionRegistrada() { return reincorporacionRegistrada; }
    public void setReincorporacionRegistrada(Boolean valor) { reincorporacionRegistrada = valor; }
    public Boolean getBajaDefinitivaRegistrada() { return bajaDefinitivaRegistrada; }
    public void setBajaDefinitivaRegistrada(Boolean valor) { bajaDefinitivaRegistrada = valor; }
    public Boolean getActividadAcademicaComprobada() { return actividadAcademicaComprobada; }
    public void setActividadAcademicaComprobada(Boolean valor) { actividadAcademicaComprobada = valor; }
    public Boolean getSeriacionComprobada() { return seriacionComprobada; }
    public void setSeriacionComprobada(Boolean valor) { seriacionComprobada = valor; }
    public Boolean getLimiteInactividadAlcanzado() { return limiteInactividadAlcanzado; }
    public void setLimiteInactividadAlcanzado(Boolean valor) { limiteInactividadAlcanzado = valor; }
    public Boolean getDatosActividadContradictorios() { return datosActividadContradictorios; }
    public void setDatosActividadContradictorios(Boolean valor) { datosActividadContradictorios = valor; }
    public Boolean getPermanenciaDentroLimiteComprobada() { return permanenciaDentroLimiteComprobada; }
    public void setPermanenciaDentroLimiteComprobada(Boolean valor) { permanenciaDentroLimiteComprobada = valor; }
    public Boolean getLimitePermanenciaExcedido() { return limitePermanenciaExcedido; }
    public void setLimitePermanenciaExcedido(Boolean valor) { limitePermanenciaExcedido = valor; }
    public Boolean getDictaminacionEgresoConocida() { return dictaminacionEgresoConocida; }
    public void setDictaminacionEgresoConocida(Boolean valor) { dictaminacionEgresoConocida = valor; }
    public Boolean getDictaminacionEgresoIniciada() { return dictaminacionEgresoIniciada; }
    public void setDictaminacionEgresoIniciada(Boolean valor) { dictaminacionEgresoIniciada = valor; }
    public Integer getOportunidadesMejoraUtilizadas() { return oportunidadesMejoraUtilizadas; }
    public void setOportunidadesMejoraUtilizadas(Integer valor) { oportunidadesMejoraUtilizadas = valor; }
    public Integer getPeriodosBajaTemporalAutorizados() { return periodosBajaTemporalAutorizados; }
    public void setPeriodosBajaTemporalAutorizados(Integer valor) { periodosBajaTemporalAutorizados = valor; }
    public Integer getPeriodosBajaTemporalTranscurridos() { return periodosBajaTemporalTranscurridos; }
    public void setPeriodosBajaTemporalTranscurridos(Integer valor) { periodosBajaTemporalTranscurridos = valor; }
    public List<String> getPeriodosActividadConsiderados() { return periodosActividadConsiderados; }
    public void setPeriodosActividadConsiderados(List<String> valores) { periodosActividadConsiderados = valores == null ? new ArrayList<String>() : valores; }
    public List<String> getEvidenciasActividad() { return evidenciasActividad; }
    public void setEvidenciasActividad(List<String> valores) { evidenciasActividad = valores == null ? new ArrayList<String>() : valores; }
    public Boolean getAvanceCondicionado() { return avanceCondicionado; }
    public void setAvanceCondicionado(Boolean avanceCondicionado) { this.avanceCondicionado = avanceCondicionado; }
    public Boolean getSancionImpedimentoConocida() { return sancionImpedimentoConocida; }
    public void setSancionImpedimentoConocida(Boolean valor) { sancionImpedimentoConocida = valor; }
    public Boolean getSancionImpedimentoRegistrada() { return sancionImpedimentoRegistrada; }
    public void setSancionImpedimentoRegistrada(Boolean valor) { sancionImpedimentoRegistrada = valor; }
    public Boolean getEstructuraProgramaEducativoComprobada() { return estructuraProgramaEducativoComprobada; }
    public void setEstructuraProgramaEducativoComprobada(Boolean valor) { estructuraProgramaEducativoComprobada = valor; }
    public Boolean getRestriccionPeNormativaConocida() { return restriccionPeNormativaConocida; }
    public void setRestriccionPeNormativaConocida(Boolean valor) { restriccionPeNormativaConocida = valor; }
    public Boolean getRestriccionPeNormativaImpeditiva() { return restriccionPeNormativaImpeditiva; }
    public void setRestriccionPeNormativaImpeditiva(Boolean valor) { restriccionPeNormativaImpeditiva = valor; }
    public String getCodigoRestriccionPeNormativa() { return codigoRestriccionPeNormativa; }
    public void setCodigoRestriccionPeNormativa(String valor) { codigoRestriccionPeNormativa = valor; }
    public String getFuenteRestriccionPeNormativa() { return fuenteRestriccionPeNormativa; }
    public void setFuenteRestriccionPeNormativa(String valor) { fuenteRestriccionPeNormativa = valor; }
    public Boolean getAdmitido() { return admitido; } public void setAdmitido(Boolean valor) { admitido = valor; }
    public Boolean getInscripcionInicialRealizada() { return inscripcionInicialRealizada; } public void setInscripcionInicialRealizada(Boolean valor) { inscripcionInicialRealizada = valor; }
    public String getPeriodoAdmision() { return periodoAdmision; } public void setPeriodoAdmision(String valor) { periodoAdmision = valor; }
    public String getPeriodoIncorporacion() { return periodoIncorporacion; } public void setPeriodoIncorporacion(String valor) { periodoIncorporacion = valor; }
    public String getReferenciaConvocatoriaOResolucionRn62() { return referenciaConvocatoriaOResolucionRn62; } public void setReferenciaConvocatoriaOResolucionRn62(String valor) { referenciaConvocatoriaOResolucionRn62 = valor; }
    public Boolean getMedidaAdministrativaRegistrada() { return medidaAdministrativaRegistrada; } public void setMedidaAdministrativaRegistrada(Boolean valor) { medidaAdministrativaRegistrada = valor; }
    public String getAlcanceMedidaAdministrativa() { return alcanceMedidaAdministrativa; } public void setAlcanceMedidaAdministrativa(String valor) { alcanceMedidaAdministrativa = valor; }
    public String getInicioVigenciaMedidaAdministrativa() { return inicioVigenciaMedidaAdministrativa; } public void setInicioVigenciaMedidaAdministrativa(String valor) { inicioVigenciaMedidaAdministrativa = valor; }
    public String getFinVigenciaMedidaAdministrativa() { return finVigenciaMedidaAdministrativa; } public void setFinVigenciaMedidaAdministrativa(String valor) { finVigenciaMedidaAdministrativa = valor; }
    public String getCondicionVigenciaMedidaAdministrativa() { return condicionVigenciaMedidaAdministrativa; } public void setCondicionVigenciaMedidaAdministrativa(String valor) { condicionVigenciaMedidaAdministrativa = valor; }
    public Boolean getHaCursadoUnoOMasPeriodos() { return haCursadoUnoOMasPeriodos; }
    public void setHaCursadoUnoOMasPeriodos(Boolean valor) { haCursadoUnoOMasPeriodos = valor; }
    public Boolean getNoCursoPeriodoInmediatoSiguiente() { return noCursoPeriodoInmediatoSiguiente; }
    public void setNoCursoPeriodoInmediatoSiguiente(Boolean valor) { noCursoPeriodoInmediatoSiguiente = valor; }
    public Boolean getCausaInterrupcionConocida() { return causaInterrupcionConocida; }
    public void setCausaInterrupcionConocida(Boolean valor) { causaInterrupcionConocida = valor; }
    public Boolean getLimiteInactividadComprobado() { return limiteInactividadComprobado; }
    public void setLimiteInactividadComprobado(Boolean valor) { limiteInactividadComprobado = valor; }
    public String getCausaInterrupcion() { return causaInterrupcion; }
    public void setCausaInterrupcion(String valor) { causaInterrupcion = valor; }
    public String getUltimoPeriodoCursado() { return ultimoPeriodoCursado; }
    public void setUltimoPeriodoCursado(String valor) { ultimoPeriodoCursado = valor; }
    public String getPrimerPeriodoOmitido() { return primerPeriodoOmitido; }
    public void setPrimerPeriodoOmitido(String valor) { primerPeriodoOmitido = valor; }
    public List<String> getPeriodosOmitidos() { return periodosOmitidos; }
    public void setPeriodosOmitidos(List<String> valores) { periodosOmitidos = valores == null ? new ArrayList<String>() : valores; }
    public String getPeriodoReincorporacion() { return periodoReincorporacion; }
    public void setPeriodoReincorporacion(String valor) { periodoReincorporacion = valor; }
    public TipoProcesoReincorporacionV2 getTipoProcesoReincorporacion() { return tipoProcesoReincorporacion; }
    public void setTipoProcesoReincorporacion(TipoProcesoReincorporacionV2 valor) { tipoProcesoReincorporacion = valor == null ? TipoProcesoReincorporacionV2.NO_DETERMINADO : valor; }
    public Boolean getBajaTemporalFormalComprobada() { return bajaTemporalFormalComprobada; }
    public void setBajaTemporalFormalComprobada(Boolean valor) { bajaTemporalFormalComprobada = valor; }
    public Boolean getBajaTemporalConcluida() { return bajaTemporalConcluida; }
    public void setBajaTemporalConcluida(Boolean valor) { bajaTemporalConcluida = valor; }
    public String getPeriodoInicioBajaTemporal() { return periodoInicioBajaTemporal; }
    public void setPeriodoInicioBajaTemporal(String valor) { periodoInicioBajaTemporal = valor; }
    public String getPeriodoFinBajaTemporal() { return periodoFinBajaTemporal; }
    public void setPeriodoFinBajaTemporal(String valor) { periodoFinBajaTemporal = valor; }
    public Integer getSemestreEfectivoPrevioBaja() { return semestreEfectivoPrevioBaja; }
    public void setSemestreEfectivoPrevioBaja(Integer valor) { semestreEfectivoPrevioBaja = valor; }
    public Boolean getCalendarioReincorporacionComprobado() { return calendarioReincorporacionComprobado; }
    public void setCalendarioReincorporacionComprobado(Boolean valor) { calendarioReincorporacionComprobado = valor; }
    public Boolean getDentroCalendarioReincorporacion() { return dentroCalendarioReincorporacion; }
    public void setDentroCalendarioReincorporacion(Boolean valor) { dentroCalendarioReincorporacion = valor; }
    public Boolean getLimitePermanenciaComprobado() { return limitePermanenciaComprobado; }
    public void setLimitePermanenciaComprobado(Boolean valor) { limitePermanenciaComprobado = valor; }
    public Boolean getCierrePeriodoAcademicoComprobado() { return cierrePeriodoAcademicoComprobado; }
    public void setCierrePeriodoAcademicoComprobado(Boolean valor) { cierrePeriodoAcademicoComprobado = valor; }
    public Integer getSemestreCurricularCierre() { return semestreCurricularCierre; }
    public void setSemestreCurricularCierre(Integer valor) { semestreCurricularCierre = valor; }
    public Boolean getBloqueosContinuidadAntesComprobados() { return bloqueosContinuidadAntesComprobados; }
    public void setBloqueosContinuidadAntesComprobados(Boolean valor) { bloqueosContinuidadAntesComprobados = valor; }
    public String getSituacionAcademicaAntesCierre() { return situacionAcademicaAntesCierre; }
    public void setSituacionAcademicaAntesCierre(String valor) { situacionAcademicaAntesCierre = valor; }
    public Integer getTotalReprobadasAntesCierre() { return totalReprobadasAntesCierre; }
    public void setTotalReprobadasAntesCierre(Integer valor) { totalReprobadasAntesCierre = valor; }
    public Boolean getRn64AntesCierre() { return rn64AntesCierre; }
    public void setRn64AntesCierre(Boolean valor) { rn64AntesCierre = valor; }
    public Integer getOptativasRequeridasPrograma() { return optativasRequeridasPrograma; }
    public void setOptativasRequeridasPrograma(Integer valor) { optativasRequeridasPrograma = valor; }
    public List<Long> getIdsObligatoriasRequeridasEgreso() { return idsObligatoriasRequeridasEgreso; }
    public void setIdsObligatoriasRequeridasEgreso(List<Long> valores) { idsObligatoriasRequeridasEgreso = valores == null ? new ArrayList<Long>() : valores; }
    public List<Long> getIdsSeriadasPrimerosSeisSemestres() { return idsSeriadasPrimerosSeisSemestres; }
    public void setIdsSeriadasPrimerosSeisSemestres(List<Long> valores) { idsSeriadasPrimerosSeisSemestres = valores == null ? new ArrayList<Long>() : valores; }
    public List<Long> getComponentesFinalesSolicitados() { return componentesFinalesSolicitados; }
    public void setComponentesFinalesSolicitados(List<Long> valores) { componentesFinalesSolicitados = valores == null ? new ArrayList<Long>() : valores; }
    public List<Long> getIdsUnidadesPretendidas() { return idsUnidadesPretendidas; }
    public void setIdsUnidadesPretendidas(List<Long> valores) { idsUnidadesPretendidas = valores == null ? new ArrayList<Long>() : valores; }
    public List<Long> getIdsUnidadesCandidatas() { return idsUnidadesCandidatas; }
    public void setIdsUnidadesCandidatas(List<Long> valores) { idsUnidadesCandidatas = valores == null ? new ArrayList<Long>() : valores; }
    public Date getFechaEvaluacion() { return fechaEvaluacion; }
    public void setFechaEvaluacion(Date fechaEvaluacion) { this.fechaEvaluacion = fechaEvaluacion; }
    public List<UnidadEvaluacionCurricularV2DTO> getUnidades() { return unidades; }
    public void setUnidades(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        this.unidades = unidades == null ? new ArrayList<UnidadEvaluacionCurricularV2DTO>() : unidades;
    }
}
