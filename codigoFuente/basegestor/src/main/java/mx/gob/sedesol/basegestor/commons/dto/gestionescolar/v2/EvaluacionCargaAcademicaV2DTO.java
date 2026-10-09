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
    private List<Long> udsSemestre2CompatiblesRn62 = new ArrayList<Long>();
    private List<Long> udsSemestre2BloqueadasRn62 = new ArrayList<Long>();
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
    public List<Long> getUdsSemestre2CompatiblesRn62() { return udsSemestre2CompatiblesRn62; } public List<Long> getUdsSemestre2BloqueadasRn62() { return udsSemestre2BloqueadasRn62; }
    public List<CodigoMotivoCurricularV2> getCausasExclusion() { return causasExclusion; }
    public List<CodigoReglaCurricularV2> getReglasRelacionadas() { return reglasRelacionadas; }
    public List<String> getEvidencias() { return evidencias; }
}
