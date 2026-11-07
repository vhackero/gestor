package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPrincipalTrayectoriaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoExcepcionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CondicionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIntentoAcreditacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoLimiteIntentosV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionIntentosUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaMinimaCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCargaMinimaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaMaximaCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCargaMaximaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCargaAcademicaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCierreFase7V2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCierreFase8V2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoReinscripcionExtemporaneaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoAvanceUltimosSemestresV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoRequisitosEgresoAcademicoV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoRequisitoComponenteFinalV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoComponenteCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCumplimientoSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoConsolidacionFase6V2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoElegibilidadElectivaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoPrincipioOptativaElectivaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoRequerimientoOptativoV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoControlAvanceAnualV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCondicionAvanceV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCondicionesComunesAvanceV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CondicionComunAvanceV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoSupuestoAvanceCondicionadoV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoSupuestosAvanceV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.SupuestoAvanceCondicionadoV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionOfertaPendienteRn614V2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoOfertaPendienteRn614V2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoResolucionAvanceV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoDecisionControlAvanceRn61V2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIncorporacionDiferidaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoReincorporacionPeriodoNoCursadoV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoReincorporacionBajaTemporalV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoReincorporacionBajaParcialV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoActualizacionTrayectoriaRn68V2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoProcesoReincorporacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoProcesoAdministrativoV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.IntentoAcreditacionUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoMejoraPromedioV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionMejoraPromedioUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoDecisionUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoActividadAcademicaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoExcepcionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ExcepcionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoExcepcionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.RelacionSeriacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoRelacionSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoSeriacionV2;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.EvaluacionCurricularV2Service;

@Service("evaluacionCurricularV2Service")
public class EvaluacionCurricularV2ServiceImpl implements EvaluacionCurricularV2Service {

    @Override
    public UnidadEvaluacionCurricularV2DTO normalizarUnidad(UnidadEvaluacionCurricularV2DTO unidad) {
        if (unidad == null) return null;
        if (unidad.getEstado() == null) unidad.setEstado(EstadoUnidadCurricularV2.NO_DETERMINADO);
        if (unidad.getEstado() == EstadoUnidadCurricularV2.REPROBADA && unidad.getCausa() == null)
            unidad.setCausa(CausaPendienteUnidadV2.REPROBACION);
        if (unidad.getEstado() == EstadoUnidadCurricularV2.NO_CURSADA && unidad.getCausa() == null)
            unidad.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO);
        if (unidad.getEstado() == EstadoUnidadCurricularV2.BAJA_PARCIAL && unidad.getCausa() == null)
            unidad.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL);
        if (unidad.getEstado() == EstadoUnidadCurricularV2.NO_DETERMINADO && unidad.getCausa() == null)
            unidad.setCausa(CausaPendienteUnidadV2.NO_DETERMINADA);
        return unidad;
    }

    @Override
    public List<UnidadEvaluacionCurricularV2DTO> normalizarUnidades(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        Map<String, UnidadEvaluacionCurricularV2DTO> porUnidad = new LinkedHashMap<String, UnidadEvaluacionCurricularV2DTO>();
        if (unidades == null) return new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad == null) continue;
            UnidadEvaluacionCurricularV2DTO normalizada = normalizarUnidad(unidad);
            String llave = obtenerLlaveUnidad(normalizada);
            UnidadEvaluacionCurricularV2DTO existente = porUnidad.get(llave);
            if (existente == null) {
                porUnidad.put(llave, normalizada);
            } else {
                existente.getReferenciasHistoricas().addAll(normalizada.getReferenciasHistoricas());
                existente.getReferenciasHistoricas().add("DUPLICADO_CURRICULAR:" + llave);
                consolidarEstadoVigente(existente, normalizada);
            }
        }
        return new ArrayList<UnidadEvaluacionCurricularV2DTO>(porUnidad.values());
    }

    @Override
    @Transactional(readOnly = true)
    public ResultadoEvaluacionCurricularV2DTO evaluarSombra(ContextoEvaluacionCurricularV2DTO contexto) {
        ResultadoEvaluacionCurricularV2DTO resultado = new ResultadoEvaluacionCurricularV2DTO();
        resultado.setEfectosProductivosAplicados(Boolean.FALSE);
        if (contexto == null) {
            resultado.getMensajes().add("No fue posible construir el contexto de evaluación sombra.");
            return resultado;
        }
        if (!"APLICA".equals(contexto.getAmbitoRnPe2024_2027()) && !"REQUIERE_REVISION".equals(contexto.getAmbitoRnPe2024_2027())) {
            resultado.setSituacionAcademicaCalculada("REQUIERE_REVISION");
            resultado.getMensajes().add("El expediente no tiene ámbito validado para las RN PE2024-2027.");
            resultado.getRestricciones().add(CodigoMotivoCurricularV2.ESTRUCTURA_PE_NO_DETERMINADA);
            return resultado;
        }

        int reprobadas = 0;
        boolean datosInsuficientesSituacion = false;
        List<UnidadEvaluacionCurricularV2DTO> unidadesNormalizadas = normalizarUnidades(contexto.getUnidades());
        for (UnidadEvaluacionCurricularV2DTO unidad : unidadesNormalizadas) {
            DecisionUnidadCurricularV2DTO decision = crearDecisionSombra(unidad);
            decision.setPrioritaria(Boolean.valueOf(unidad.getEstado() == EstadoUnidadCurricularV2.REPROBADA));

            if (esReprobacionVigentePrevia(unidad)) {
                reprobadas++;
                if (unidad.getIdPrograma() != null) resultado.getUnidadesOrigenIrregularidad().add(unidad.getIdPrograma());
                resultado.getEvidenciasSituacionAcademica().add("REPROBACION_VIGENTE_PREVIA:" + obtenerLlaveUnidad(unidad));
                registrarCondicion(resultado, decision, contexto, CondicionCurricularV2.REPROBACION,
                        CodigoReglaCurricularV2.RN_1_2, CodigoMotivoCurricularV2.REPROBACION_ACUMULADA,
                        80, "REPROBACION_NORMALIZADA", "La UD conserva una condición de reprobación en modo sombra.");
            } else if (unidad.getEstado() == EstadoUnidadCurricularV2.REPROBADA) {
                resultado.getDatosDescartadosSituacionAcademica().add("REPROBACION_NO_PREVIA_O_NO_VIGENTE:" + obtenerLlaveUnidad(unidad));
            }
            if (unidad.getCausa() == CausaPendienteUnidadV2.NO_CURSAMIENTO) {
                registrarCondicion(resultado, decision, contexto, CondicionCurricularV2.PENDIENTE_NO_CURSADA,
                        CodigoReglaCurricularV2.RN_1_3, CodigoMotivoCurricularV2.PENDIENTE_NO_CURSADA,
                        50, "NO_CURSAMIENTO_NORMALIZADO", "La UD conserva una pendiente de no cursamiento en modo sombra.");
            }
            if (unidad.getCausa() == CausaPendienteUnidadV2.BAJA_PARCIAL) {
                registrarCondicion(resultado, decision, contexto, CondicionCurricularV2.BAJA_PARCIAL,
                        CodigoReglaCurricularV2.RN_1_3, CodigoMotivoCurricularV2.BAJA_PARCIAL,
                        60, "BAJA_PARCIAL_NORMALIZADA", "La UD conserva una baja parcial en modo sombra.");
            }
            if (unidad.getCausa() == CausaPendienteUnidadV2.NO_DETERMINADA) {
                datosInsuficientesSituacion = true;
                registrarCondicion(resultado, decision, contexto, CondicionCurricularV2.DATOS_INSUFICIENTES,
                        CodigoReglaCurricularV2.CTR_04, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                        10, "DATOS_INSUFICIENTES", "No existe causa suficiente para clasificar la UD.");
            }
            if (Boolean.TRUE.equals(unidad.getSeriacionDirectaConocida())
                    || (Boolean.TRUE.equals(unidad.getSeriacionConocida())
                            && !Boolean.TRUE.equals(unidad.getSeriacionIndirectaConocida()))) {
                registrarCondicion(resultado, decision, contexto, CondicionCurricularV2.SERIACION,
                        CodigoReglaCurricularV2.RN_8, CodigoMotivoCurricularV2.SERIACION_DIRECTA,
                        90, "SERIACION_CONOCIDA", "La seriación se registra sin modificar bloqueos vigentes.");
                agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.BLOQUEADA);
                // Es una proyección V2, no una modificación a la oferta origen ni al bloqueo productivo.
                decision.setOfertaEvaluadaV2(Boolean.FALSE);
                agregarExplicacion(decision, "Seriación directa detectada en la representación sombra.");
            } else if (Boolean.TRUE.equals(unidad.getSeriacionIndirectaConocida())
                    || Boolean.TRUE.equals(unidad.getSeriacionConocida())) {
                registrarCondicion(resultado, decision, contexto, CondicionCurricularV2.SERIACION,
                        CodigoReglaCurricularV2.RN_8, CodigoMotivoCurricularV2.SERIACION_INDIRECTA,
                        70, "SERIACION_INDIRECTA_CONOCIDA",
                        "Existe relación de seriación que requiere revisión, sin alterar el motor vigente.");
                agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION);
                agregarExplicacion(decision, "Seriación indirecta conocida; requiere revisión académica.");
            }
            resultado.getDecisiones().add(decision);
        }

        determinarSituacionAcademica(resultado, contexto, reprobadas, datosInsuficientesSituacion);
        resultado.setTotalReprobadas(Integer.valueOf(reprobadas));
        evaluarActividadAcademica(resultado, contexto);
        evaluarRecuperacionRegularidad(resultado, contexto, unidadesNormalizadas);
        evaluarLimiteIntentos(resultado, contexto, unidadesNormalizadas);
        // RN-1.5 vigente impide reinscribir una UD acreditada para mejorar promedio.
        // El resultado PE2024/2027 no expone contadores ni la "mejor" calificacion.
        resultado.setAvanceCondicionado(Boolean.valueOf(Boolean.TRUE.equals(contexto.getAvanceCondicionado())));
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.CTR_03, null, 20,
                "SITUACION_SEPARADA_DE_AVANCE",
                "La situación académica se determina de forma independiente a la autorización de avance.");
        if (Boolean.TRUE.equals(contexto.getAvanceCondicionado())) {
            agregarUnico(resultado.getCondicionesDetectadas(), CondicionCurricularV2.AVANCE_CONDICIONADO);
            agregarUnico(resultado.getExcepciones(), "AVANCE_CONDICIONADO_SIN_EFECTO_PRODUCTIVO");
        }
        if (reprobadas >= 4) {
            agregarUnico(resultado.getCondicionesDetectadas(), CondicionCurricularV2.CUATRO_O_MAS_REPROBADAS);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_4,
                    CodigoMotivoCurricularV2.REPROBACION_ACUMULADA, 100,
                    "CONDICION_REGISTRADA_SIN_EFECTO_PRODUCTIVO",
                    "Cuatro o más reprobadas se registran como condición concurrente; la RN-6.4 no se activa en Fase 1A.");
        }
        evaluarCargaMinima(resultado, contexto, unidadesNormalizadas);
        evaluarCargaMaxima(resultado, contexto, unidadesNormalizadas);
        evaluarExcepcionesTipificadas(resultado, contexto, unidadesNormalizadas);
        evaluarCargaAcademicaIntegrada(resultado, contexto);
        evaluarPrioridadRegularizacion(resultado, contexto, unidadesNormalizadas);
        evaluarRezagoLeve(resultado, contexto);
        evaluarRezagoSevero(resultado, contexto, unidadesNormalizadas);
        evaluarCargaRezagoSevero(resultado, contexto);
        evaluarControlAvanceAnualBase(resultado, contexto, unidadesNormalizadas);
        evaluarCondicionesComunesAvance(resultado, contexto, unidadesNormalizadas);
        evaluarFaltaOfertaControlAvance(resultado, contexto, unidadesNormalizadas);
        evaluarSupuestosTipificadosAvance(resultado, contexto, unidadesNormalizadas);
        evaluarImpedimentosYExtraordinario(resultado, contexto);
        consolidarDecisionControlAvance(resultado, contexto);
        evaluarInscripcionInicialDiferida(resultado, contexto, unidadesNormalizadas);
        evaluarReincorporacionTrasPeriodoNoCursado(resultado, contexto, unidadesNormalizadas);
        evaluarReincorporacionTrasBajaTemporal(resultado, contexto, unidadesNormalizadas);
        evaluarReincorporacionTrasBajaParcial(resultado, contexto, unidadesNormalizadas);
        evaluarActualizacionTrayectoriaTrasRegularizacion(resultado, contexto, unidadesNormalizadas);
        consolidarFase6ReincorporacionYTrayectoria(resultado, contexto);
        evaluarOptativasRn71(resultado, contexto, unidadesNormalizadas);
        evaluarPrincipioGeneralOptativasReprobadasRn72(resultado, contexto, unidadesNormalizadas);
        // RN-7.3 no forma parte de las RN vigentes; RN-7.2 concentra las optativas reprobadas.
        // El cierre histórico de RN-7.3/electivas queda fuera de PE2024/2027.
        evaluarSeriacionInterbloqueRn81(resultado, contexto, unidadesNormalizadas);
        evaluarSeriacionIntersemestralRn82(resultado, contexto, unidadesNormalizadas);
        evaluarSeriacionInteranualRn83(resultado, contexto, unidadesNormalizadas);
        consolidarCierreSeriacionFase8(resultado, contexto, unidadesNormalizadas);
        aplicarOfertaYPreseleccionBloque3(resultado, contexto, unidadesNormalizadas);
        evaluarAvanceUltimosSemestresRn93(resultado, contexto, unidadesNormalizadas);
        evaluarRequisitosAcreditacionEgresoRn94(resultado, contexto, unidadesNormalizadas);
        evaluarReinscripcionExtemporaneaRn91(resultado, contexto);
        auditarCoberturaTrazabilidadRn95(resultado, contexto, unidadesNormalizadas);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.CTR_04, null, 15,
                "EVALUACION_CONCURRENTE",
                "Las condiciones y motivos se acumulan; no se reduce el resultado a una categoría excluyente.");
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_2_2, null, 25,
                "EVALUACION_PREVIA_REINSCRIPCION_SOMBRA",
                "Se construyó una oferta individual V2 separada de la oferta y seleccionabilidad origen.");
        resultado.getMensajes().add("Resultado generado en modo sombra; no modifica la elegibilidad vigente.");
        if (Boolean.FALSE.equals(contexto.getUniversoMapaCompleto()) || Boolean.FALSE.equals(contexto.getHistorialCompleto()))
            resultado.getMensajes().add("Evaluación parcial: falta integrar el mapa completo del plan o el historial completo; no equivale a ausencia de pendientes.");
        return resultado;
    }

    /**
     * RN-8/RN-8.1 consume exclusivamente relaciones del PE ya presentes en el
     * contexto. Su resultado es descriptivo: no modifica oferta, cupo ni la
     * seleccionabilidad productiva de la UD consecuente.
     */
    private void evaluarSeriacionInterbloqueRn81(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_8);
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_8);
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_8_1);

        Map<Long, UnidadEvaluacionCurricularV2DTO> unidadesPorId = new LinkedHashMap<Long, UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad != null && unidad.getIdPrograma() != null) unidadesPorId.put(unidad.getIdPrograma(), unidad);
        }
        Set<String> relacionesProcesadas = new HashSet<String>();
        boolean hayRelacionInterbloque = false;
        for (UnidadEvaluacionCurricularV2DTO consecuente : unidades) {
            if (consecuente == null || consecuente.getRelacionesSeriacion() == null) continue;
            for (RelacionSeriacionCurricularV2DTO relacion : consecuente.getRelacionesSeriacion()) {
                if (relacion == null) continue;
                if (relacion.getIdConsecuente() == null) relacion.setIdConsecuente(consecuente.getIdPrograma());
                String llaveRelacion = llaveRelacionSeriacion(relacion);
                if (!relacionesProcesadas.add(llaveRelacion)) continue;
                if (relacion.getTipoSeriacion() == null || relacion.getTipoSeriacion() == TipoSeriacionV2.NO_DETERMINADA) {
                    registrarRelacionTipoNoDeterminado(resultado, contexto, carga, consecuente, relacion);
                    carga.getRelacionesSeriacionV2().add(relacion);
                    continue;
                }
                if (relacion.getTipoSeriacion() != TipoSeriacionV2.INTERBLOQUE) continue;
                hayRelacionInterbloque = true;
                evaluarRelacionInterbloque(resultado, contexto, carga, unidadesPorId, consecuente, relacion);
                carga.getRelacionesSeriacionV2().add(relacion);
            }
        }
        carga.setAplicaRn81(Boolean.valueOf(hayRelacionInterbloque));
        if (!hayRelacionInterbloque) {
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_8_1, null, 65,
                    "RN81_NO_APLICA", "No existe una relación INTERBLOQUE verificable en el contexto curricular.");
            return;
        }
        String evidencia = "cumplidas=" + carga.getUdsInterbloqueCumplidasRn81()
                + "; condicionadas=" + carga.getUdsInterbloqueCondicionadasRn81()
                + "; noCumplidas=" + carga.getUdsInterbloqueNoCumplidasRn81()
                + "; revision=" + carga.getUdsInterbloqueRevisionRn81();
        agregarUnico(carga.getEvidencias(), "RN81:" + evidencia);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_8_1,
                Boolean.TRUE.equals(carga.getRequiereRevisionRn81()) ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                65, "RN81_EVALUADA_EN_SOMBRA", evidencia);
    }

    private void registrarRelacionTipoNoDeterminado(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO consecuente, RelacionSeriacionCurricularV2DTO relacion) {
        relacion.setDecision(EstadoCumplimientoSeriacionV2.REQUIERE_REVISION);
        relacion.setMotivo("TIPO_SERIACION_NO_DETERMINADO");
        if (relacion.getEvidencia() == null) relacion.setEvidencia("PE_RELACION:" + llaveRelacionSeriacion(relacion));
        carga.setRequiereRevision(Boolean.TRUE);
        DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, consecuente.getIdPrograma());
        if (decision != null) {
            agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
            agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION);
            agregarExplicacion(decision, "RN-8: el tipo de seriación de la relación curricular requiere revisión.");
            TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_8,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 65, "RN8_REQUIERE_REVISION",
                    relacion.getEvidencia() + "; TIPO_SERIACION_NO_DETERMINADO");
            decision.getTrazas().add(traza);
            resultado.getTrazas().add(traza);
        }
    }

    private void evaluarRelacionInterbloque(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            Map<Long, UnidadEvaluacionCurricularV2DTO> unidadesPorId,
            UnidadEvaluacionCurricularV2DTO consecuente, RelacionSeriacionCurricularV2DTO relacion) {
        DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, consecuente.getIdPrograma());
        if (!esRelacionInterbloqueValida(relacion, consecuente)) {
            registrarResultadoInterbloque(resultado, contexto, carga, decision, consecuente, relacion,
                    EstadoCumplimientoSeriacionV2.REQUIERE_REVISION, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    "RELACION_INTERBLOQUE_INCOMPLETA_O_FUERA_DEL_MISMO_PERIODO");
            return;
        }
        UnidadEvaluacionCurricularV2DTO antecedente = unidadesPorId.get(relacion.getIdAntecedente());
        if (antecedente == null) {
            registrarResultadoInterbloque(resultado, contexto, carga, decision, consecuente, relacion,
                    EstadoCumplimientoSeriacionV2.REQUIERE_REVISION, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    "ANTECEDENTE_NO_LOCALIZADO_EN_CONTEXTO_V2");
            return;
        }
        relacion.setEstadoAntecedente(antecedente.getEstado());
        EstadoUnidadCurricularV2 estado = antecedente.getEstado();
        if (estado == EstadoUnidadCurricularV2.ACREDITADA) {
            registrarResultadoInterbloque(resultado, contexto, carga, decision, consecuente, relacion,
                    EstadoCumplimientoSeriacionV2.CUMPLIDA, null, "ANTECEDENTE_ACREDITADO");
        } else if (estado == EstadoUnidadCurricularV2.EN_CURSO) {
            registrarResultadoInterbloque(resultado, contexto, carga, decision, consecuente, relacion,
                    EstadoCumplimientoSeriacionV2.CONDICIONADA, CodigoMotivoCurricularV2.SERIACION_DIRECTA,
                    "CONDICIONADA_A_ACREDITACION_ANTECEDENTE_B1");
        } else if (estado == EstadoUnidadCurricularV2.NO_DETERMINADO || estado == null) {
            registrarResultadoInterbloque(resultado, contexto, carga, decision, consecuente, relacion,
                    EstadoCumplimientoSeriacionV2.REQUIERE_REVISION, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    "ESTADO_ANTECEDENTE_NO_DETERMINADO");
        } else {
            registrarResultadoInterbloque(resultado, contexto, carga, decision, consecuente, relacion,
                    EstadoCumplimientoSeriacionV2.NO_CUMPLIDA, CodigoMotivoCurricularV2.SERIACION_DIRECTA,
                    "ANTECEDENTE_NO_ACREDITADO:" + estado);
        }
    }

    private boolean esRelacionInterbloqueValida(RelacionSeriacionCurricularV2DTO relacion,
            UnidadEvaluacionCurricularV2DTO consecuente) {
        return relacion.getIdAntecedente() != null && consecuente.getIdPrograma() != null
                && relacion.getIdConsecuente() != null && consecuente.getIdPrograma().equals(relacion.getIdConsecuente())
                && relacion.getTipoRelacion() == TipoRelacionSeriacionV2.DIRECTA
                && relacion.getSemestreAntecedente() != null && relacion.getSemestreConsecuente() != null
                && relacion.getSemestreAntecedente().equals(relacion.getSemestreConsecuente())
                && "1".equals(relacion.getBloqueAntecedente()) && "2".equals(relacion.getBloqueConsecuente());
    }

    private void registrarResultadoInterbloque(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            DecisionUnidadCurricularV2DTO decision, UnidadEvaluacionCurricularV2DTO consecuente,
            RelacionSeriacionCurricularV2DTO relacion, EstadoCumplimientoSeriacionV2 estado,
            CodigoMotivoCurricularV2 motivo, String detalle) {
        relacion.setDecision(estado);
        relacion.setMotivo(detalle);
        if (relacion.getEvidencia() == null) relacion.setEvidencia("PE_RELACION:" + llaveRelacionSeriacion(relacion));
        agregarIdUnico(estado == EstadoCumplimientoSeriacionV2.CUMPLIDA ? carga.getUdsInterbloqueCumplidasRn81()
                : estado == EstadoCumplimientoSeriacionV2.CONDICIONADA ? carga.getUdsInterbloqueCondicionadasRn81()
                : estado == EstadoCumplimientoSeriacionV2.NO_CUMPLIDA ? carga.getUdsInterbloqueNoCumplidasRn81()
                : carga.getUdsInterbloqueRevisionRn81(), consecuente.getIdPrograma());
        carga.getDecisionesPorUdSeriacionRn81().add(obtenerLlaveUnidad(consecuente) + ":" + estado + ":" + detalle);
        if (estado == EstadoCumplimientoSeriacionV2.REQUIERE_REVISION) {
            carga.setRequiereRevisionRn81(Boolean.TRUE);
            carga.setRequiereRevision(Boolean.TRUE);
        }
        if (decision != null) {
            if (motivo != null) agregarUnico(decision.getMotivos(), motivo);
            if (estado == EstadoCumplimientoSeriacionV2.NO_CUMPLIDA) agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.BLOQUEADA);
            if (estado == EstadoCumplimientoSeriacionV2.REQUIERE_REVISION) agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION);
            agregarExplicacion(decision, "RN-8.1: " + detalle + "; antecedente=" + relacion.getIdAntecedente() + ".");
            TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_8_1, motivo, 65,
                    "RN81_" + estado, relacion.getEvidencia() + "; " + detalle);
            decision.getTrazas().add(traza);
            resultado.getTrazas().add(traza);
        }
    }

    private String llaveRelacionSeriacion(RelacionSeriacionCurricularV2DTO relacion) {
        return String.valueOf(relacion.getIdAntecedente()) + "->" + String.valueOf(relacion.getIdConsecuente())
                + ":" + relacion.getTipoSeriacion() + ":" + relacion.getTipoRelacion();
    }

    /** RN-8.2 reutiliza la relación formal del PE; EN_CURSO no satisface acreditación previa. */
    private void evaluarSeriacionIntersemestralRn82(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_8_2);
        Map<Long, UnidadEvaluacionCurricularV2DTO> unidadesPorId = new LinkedHashMap<Long, UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) if (unidad != null && unidad.getIdPrograma() != null) unidadesPorId.put(unidad.getIdPrograma(), unidad);
        Set<String> relacionesProcesadas = new HashSet<String>();
        boolean aplica = false;
        for (UnidadEvaluacionCurricularV2DTO consecuente : unidades) {
            if (consecuente == null || consecuente.getRelacionesSeriacion() == null) continue;
            for (RelacionSeriacionCurricularV2DTO relacion : consecuente.getRelacionesSeriacion()) {
                if (relacion == null || relacion.getTipoSeriacion() != TipoSeriacionV2.INTERSEMESTRAL) continue;
                if (relacion.getIdConsecuente() == null) relacion.setIdConsecuente(consecuente.getIdPrograma());
                if (!relacionesProcesadas.add(llaveRelacionSeriacion(relacion))) continue;
                aplica = true;
                evaluarRelacionIntersemestral(resultado, contexto, carga, unidadesPorId, consecuente, relacion);
                agregarRelacionSeriacionUnica(carga, relacion);
            }
        }
        carga.setAplicaRn82(Boolean.valueOf(aplica));
        if (!aplica) {
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_8_2, null, 65,
                    "RN82_NO_APLICA", "No existe relación INTERSEMESTRAL formal y verificable en el PE.");
            return;
        }
        String evidencia = "cumplidas=" + carga.getUdsIntersemestralesCumplidasRn82()
                + "; noCumplidas=" + carga.getUdsIntersemestralesNoCumplidasRn82()
                + "; revision=" + carga.getUdsIntersemestralesRevisionRn82();
        agregarUnico(carga.getEvidencias(), "RN82:" + evidencia);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_8_2,
                Boolean.TRUE.equals(carga.getRequiereRevisionRn82()) ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                65, "RN82_EVALUADA_EN_SOMBRA", evidencia);
    }

    private void evaluarRelacionIntersemestral(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            Map<Long, UnidadEvaluacionCurricularV2DTO> unidadesPorId,
            UnidadEvaluacionCurricularV2DTO consecuente, RelacionSeriacionCurricularV2DTO relacion) {
        DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, consecuente.getIdPrograma());
        if (!esRelacionIntersemestralValida(relacion, consecuente)) {
            registrarResultadoIntersemestral(resultado, contexto, carga, decision, consecuente, relacion,
                    EstadoCumplimientoSeriacionV2.REQUIERE_REVISION, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    "RELACION_INTERSEMESTRAL_INCOMPLETA_O_SEMESTRES_NO_CONSECUTIVOS");
            return;
        }
        if (relacion.getTipoRelacion() != TipoRelacionSeriacionV2.DIRECTA) {
            registrarResultadoIntersemestral(resultado, contexto, carga, decision, consecuente, relacion,
                    EstadoCumplimientoSeriacionV2.REQUIERE_REVISION, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    "RELACION_INDIRECTA_REQUIERE_EVALUACION_ESPECIFICA_POSTERIOR");
            return;
        }
        UnidadEvaluacionCurricularV2DTO antecedente = unidadesPorId.get(relacion.getIdAntecedente());
        if (antecedente == null || antecedente.getEstado() == null || antecedente.getEstado() == EstadoUnidadCurricularV2.NO_DETERMINADO) {
            registrarResultadoIntersemestral(resultado, contexto, carga, decision, consecuente, relacion,
                    EstadoCumplimientoSeriacionV2.REQUIERE_REVISION, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    antecedente == null ? "ANTECEDENTE_NO_LOCALIZADO_EN_CONTEXTO_V2" : "ESTADO_ANTECEDENTE_NO_DETERMINADO");
            return;
        }
        relacion.setEstadoAntecedente(antecedente.getEstado());
        if (antecedente.getEstado() == EstadoUnidadCurricularV2.ACREDITADA) {
            registrarResultadoIntersemestral(resultado, contexto, carga, decision, consecuente, relacion,
                    EstadoCumplimientoSeriacionV2.CUMPLIDA, null, "ANTECEDENTE_ACREDITADO_PREVIAMENTE");
        } else {
            registrarResultadoIntersemestral(resultado, contexto, carga, decision, consecuente, relacion,
                    EstadoCumplimientoSeriacionV2.NO_CUMPLIDA, CodigoMotivoCurricularV2.SERIACION_DIRECTA,
                    "ANTECEDENTE_NO_ACREDITADO:" + antecedente.getEstado());
        }
    }

    private boolean esRelacionIntersemestralValida(RelacionSeriacionCurricularV2DTO relacion,
            UnidadEvaluacionCurricularV2DTO consecuente) {
        return relacion.getIdAntecedente() != null && consecuente.getIdPrograma() != null
                && consecuente.getIdPrograma().equals(relacion.getIdConsecuente())
                && relacion.getSemestreAntecedente() != null && relacion.getSemestreConsecuente() != null
                && relacion.getSemestreConsecuente().intValue() == relacion.getSemestreAntecedente().intValue() + 1;
    }

    private void registrarResultadoIntersemestral(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            DecisionUnidadCurricularV2DTO decision, UnidadEvaluacionCurricularV2DTO consecuente,
            RelacionSeriacionCurricularV2DTO relacion, EstadoCumplimientoSeriacionV2 estado,
            CodigoMotivoCurricularV2 motivo, String detalle) {
        relacion.setDecision(estado); relacion.setMotivo(detalle);
        if (relacion.getEvidencia() == null) relacion.setEvidencia("PE_RELACION:" + llaveRelacionSeriacion(relacion));
        agregarIdUnico(estado == EstadoCumplimientoSeriacionV2.CUMPLIDA ? carga.getUdsIntersemestralesCumplidasRn82()
                : estado == EstadoCumplimientoSeriacionV2.NO_CUMPLIDA ? carga.getUdsIntersemestralesNoCumplidasRn82()
                : carga.getUdsIntersemestralesRevisionRn82(), consecuente.getIdPrograma());
        carga.getDecisionesPorUdSeriacionRn82().add(obtenerLlaveUnidad(consecuente) + ":" + estado + ":" + detalle);
        if (estado == EstadoCumplimientoSeriacionV2.REQUIERE_REVISION) { carga.setRequiereRevisionRn82(Boolean.TRUE); carga.setRequiereRevision(Boolean.TRUE); }
        if (decision != null) {
            if (motivo != null) agregarUnico(decision.getMotivos(), motivo);
            if (estado == EstadoCumplimientoSeriacionV2.NO_CUMPLIDA) agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.BLOQUEADA);
            if (estado == EstadoCumplimientoSeriacionV2.REQUIERE_REVISION) agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION);
            agregarExplicacion(decision, "RN-8.2: " + detalle + "; antecedente=" + relacion.getIdAntecedente() + ".");
            TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_8_2, motivo, 65,
                    "RN82_" + estado, relacion.getEvidencia() + "; " + detalle);
            decision.getTrazas().add(traza); resultado.getTrazas().add(traza);
        }
    }

    private void agregarRelacionSeriacionUnica(EvaluacionCargaAcademicaV2DTO carga,
            RelacionSeriacionCurricularV2DTO relacion) {
        for (RelacionSeriacionCurricularV2DTO existente : carga.getRelacionesSeriacionV2()) {
            if (llaveRelacionSeriacion(existente).equals(llaveRelacionSeriacion(relacion))) return;
        }
        carga.getRelacionesSeriacionV2().add(relacion);
    }

    /**
     * RN-8.3 PE2027 recorre únicamente prerrequisitos formales del PE. No usa
     * cierre anual ni el resultado RN-6.1 para decidir la seriación.
     */
    private void evaluarSeriacionInteranualRn83(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_8_3);
        Map<Long, UnidadEvaluacionCurricularV2DTO> unidadesPorId = new LinkedHashMap<Long, UnidadEvaluacionCurricularV2DTO>();
        Map<Long, List<RelacionSeriacionCurricularV2DTO>> relacionesPorConsecuente = new LinkedHashMap<Long, List<RelacionSeriacionCurricularV2DTO>>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad == null || unidad.getIdPrograma() == null) continue;
            unidadesPorId.put(unidad.getIdPrograma(), unidad);
            if (unidad.getRelacionesSeriacion() == null) continue;
            for (RelacionSeriacionCurricularV2DTO relacion : unidad.getRelacionesSeriacion()) {
                if (relacion == null) continue;
                if (relacion.getIdConsecuente() == null) relacion.setIdConsecuente(unidad.getIdPrograma());
                List<RelacionSeriacionCurricularV2DTO> relaciones = relacionesPorConsecuente.get(relacion.getIdConsecuente());
                if (relaciones == null) { relaciones = new ArrayList<RelacionSeriacionCurricularV2DTO>(); relacionesPorConsecuente.put(relacion.getIdConsecuente(), relaciones); }
                relaciones.add(relacion);
            }
        }

        boolean aplica = false;
        for (UnidadEvaluacionCurricularV2DTO consecuente : unidades) {
            if (consecuente == null || consecuente.getIdPrograma() == null) continue;
            List<RelacionSeriacionCurricularV2DTO> directas = relacionesPorConsecuente.get(consecuente.getIdPrograma());
            if (!tieneRelacionInteranual(directas)) continue;
            aplica = true;
            ResultadoCadenaInteranual resultadoCadena = new ResultadoCadenaInteranual();
            List<Long> camino = new ArrayList<Long>(); camino.add(consecuente.getIdPrograma());
            recorrerAntecedentesInteranuales(resultado, contexto, carga, consecuente, consecuente.getIdPrograma(),
                    relacionesPorConsecuente, unidadesPorId, camino, new HashSet<Long>(), resultadoCadena, 0);
            registrarResultadoInteranualPorUd(resultado, contexto, carga, consecuente, resultadoCadena);
        }
        carga.setAplicaRn83(Boolean.valueOf(aplica));
        if (!aplica) {
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_8_3, null, 65,
                    "RN83_NO_APLICA", "No existe relación INTERANUAL formal clasificada por el PE.");
            return;
        }
        String evidencia = "cumplidas=" + carga.getUdsInteranualesCumplidasRn83()
                + "; noCumplidas=" + carga.getUdsInteranualesNoCumplidasRn83()
                + "; revision=" + carga.getUdsInteranualesRevisionRn83()
                + "; antecedentesNoAcreditados=" + carga.getAntecedentesNoAcreditadosRn83()
                + "; ciclos=" + carga.getCiclosSeriacionRn83()
                + "; rn61Concurrente=" + Boolean.TRUE.equals(carga.getAplicaRn61());
        agregarUnico(carga.getEvidencias(), "RN83:" + evidencia);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_8_3,
                Boolean.TRUE.equals(carga.getRequiereRevisionRn83()) ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                65, "RN83_EVALUADA_EN_SOMBRA", evidencia);
    }

    private boolean tieneRelacionInteranual(List<RelacionSeriacionCurricularV2DTO> relaciones) {
        if (relaciones == null) return false;
        for (RelacionSeriacionCurricularV2DTO relacion : relaciones)
            if (relacion != null && relacion.getTipoSeriacion() == TipoSeriacionV2.INTERANUAL) return true;
        return false;
    }

    private void recorrerAntecedentesInteranuales(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO consecuenteRaiz, Long actual,
            Map<Long, List<RelacionSeriacionCurricularV2DTO>> relacionesPorConsecuente,
            Map<Long, UnidadEvaluacionCurricularV2DTO> unidadesPorId, List<Long> camino,
            Set<Long> visitadosEnCamino, ResultadoCadenaInteranual acumulado, int profundidad) {
        if (actual == null) { acumulado.requiereRevision = true; return; }
        Set<Long> rutaActual = new HashSet<Long>(visitadosEnCamino); rutaActual.add(actual);
        List<RelacionSeriacionCurricularV2DTO> relaciones = relacionesPorConsecuente.get(actual);
        if (relaciones == null) return;
        Set<String> relacionesLocales = new HashSet<String>();
        for (RelacionSeriacionCurricularV2DTO origen : relaciones) {
            if (origen == null || !relacionesLocales.add(llaveRelacionSeriacion(origen))) continue;
            RelacionSeriacionCurricularV2DTO evaluada = copiarRelacionSeriacion(origen);
            evaluada.setTipoRelacion(profundidad == 0 ? TipoRelacionSeriacionV2.DIRECTA : TipoRelacionSeriacionV2.INDIRECTA);
            evaluada.setProfundidadCadena(Integer.valueOf(profundidad + 1));
            evaluada.setCaminoCurricular(construirCaminoAntecedente(origen.getIdAntecedente(), camino));
            if (origen.getTipoSeriacion() == null || origen.getTipoSeriacion() == TipoSeriacionV2.NO_DETERMINADA
                    || origen.getIdAntecedente() == null || !Boolean.TRUE.equals(origen.getAntecedenteObligatorio())) {
                registrarRelacionInteranual(resultado, contexto, carga, consecuenteRaiz, evaluada,
                        EstadoCumplimientoSeriacionV2.REQUIERE_REVISION, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                        "RELACION_O_OBLIGATORIEDAD_NO_DETERMINADA", acumulado);
                continue;
            }
            if (rutaActual.contains(origen.getIdAntecedente())) {
                evaluada.setCicloDetectado(Boolean.TRUE);
                registrarRelacionInteranual(resultado, contexto, carga, consecuenteRaiz, evaluada,
                        EstadoCumplimientoSeriacionV2.REQUIERE_REVISION, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                        "CICLO_DETECTADO_EN_GRAFO_SERIACION", acumulado);
                acumulado.cicloDetectado = true;
                agregarIdUnico(carga.getCiclosSeriacionRn83(), origen.getIdAntecedente());
                continue;
            }
            UnidadEvaluacionCurricularV2DTO antecedente = unidadesPorId.get(origen.getIdAntecedente());
            if (antecedente == null || antecedente.getEstado() == null || antecedente.getEstado() == EstadoUnidadCurricularV2.NO_DETERMINADO) {
                registrarRelacionInteranual(resultado, contexto, carga, consecuenteRaiz, evaluada,
                        EstadoCumplimientoSeriacionV2.REQUIERE_REVISION, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                        antecedente == null ? "ANTECEDENTE_NO_LOCALIZADO_EN_CONTEXTO_V2" : "ESTADO_ANTECEDENTE_NO_DETERMINADO", acumulado);
                continue;
            }
            evaluada.setEstadoAntecedente(antecedente.getEstado());
            if (antecedente.getEstado() == EstadoUnidadCurricularV2.ACREDITADA) {
                registrarRelacionInteranual(resultado, contexto, carga, consecuenteRaiz, evaluada,
                        EstadoCumplimientoSeriacionV2.CUMPLIDA, null, "ANTECEDENTE_ACREDITADO", acumulado);
            } else {
                registrarRelacionInteranual(resultado, contexto, carga, consecuenteRaiz, evaluada,
                        EstadoCumplimientoSeriacionV2.NO_CUMPLIDA, CodigoMotivoCurricularV2.SERIACION_DIRECTA,
                        "ANTECEDENTE_NO_ACREDITADO:" + antecedente.getEstado(), acumulado);
                agregarIdUnico(carga.getAntecedentesNoAcreditadosRn83(), antecedente.getIdPrograma());
            }
            List<Long> siguienteCamino = new ArrayList<Long>(camino); siguienteCamino.add(origen.getIdAntecedente());
            recorrerAntecedentesInteranuales(resultado, contexto, carga, consecuenteRaiz, origen.getIdAntecedente(),
                    relacionesPorConsecuente, unidadesPorId, siguienteCamino, rutaActual, acumulado, profundidad + 1);
        }
    }

    private void registrarRelacionInteranual(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO consecuente, RelacionSeriacionCurricularV2DTO relacion,
            EstadoCumplimientoSeriacionV2 estado, CodigoMotivoCurricularV2 motivo, String detalle,
            ResultadoCadenaInteranual acumulado) {
        relacion.setDecision(estado); relacion.setMotivo(detalle);
        if (relacion.getEvidencia() == null) relacion.setEvidencia("PE_RELACION:" + llaveRelacionSeriacion(relacion));
        agregarRelacionInteranualUnica(carga, relacion);
        if (estado == EstadoCumplimientoSeriacionV2.NO_CUMPLIDA) acumulado.noCumplida = true;
        if (estado == EstadoCumplimientoSeriacionV2.REQUIERE_REVISION) acumulado.requiereRevision = true;
        DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, consecuente.getIdPrograma());
        if (decision != null) {
            if (motivo != null) agregarUnico(decision.getMotivos(), motivo);
            if (estado == EstadoCumplimientoSeriacionV2.NO_CUMPLIDA) agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.BLOQUEADA);
            if (estado == EstadoCumplimientoSeriacionV2.REQUIERE_REVISION) agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION);
            agregarExplicacion(decision, "RN-8.3: " + detalle + "; camino=" + relacion.getCaminoCurricular() + ".");
            TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_8_3, motivo, 65,
                    "RN83_" + estado, relacion.getEvidencia() + "; camino=" + relacion.getCaminoCurricular() + "; " + detalle);
            decision.getTrazas().add(traza); resultado.getTrazas().add(traza);
        }
    }

    private void registrarResultadoInteranualPorUd(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO consecuente, ResultadoCadenaInteranual cadena) {
        EstadoCumplimientoSeriacionV2 estado = cadena.requiereRevision ? EstadoCumplimientoSeriacionV2.REQUIERE_REVISION
                : cadena.noCumplida ? EstadoCumplimientoSeriacionV2.NO_CUMPLIDA : EstadoCumplimientoSeriacionV2.CUMPLIDA;
        agregarIdUnico(estado == EstadoCumplimientoSeriacionV2.CUMPLIDA ? carga.getUdsInteranualesCumplidasRn83()
                : estado == EstadoCumplimientoSeriacionV2.NO_CUMPLIDA ? carga.getUdsInteranualesNoCumplidasRn83()
                : carga.getUdsInteranualesRevisionRn83(), consecuente.getIdPrograma());
        if (estado == EstadoCumplimientoSeriacionV2.REQUIERE_REVISION) { carga.setRequiereRevisionRn83(Boolean.TRUE); carga.setRequiereRevision(Boolean.TRUE); }
        carga.getDecisionesPorUdSeriacionRn83().add(obtenerLlaveUnidad(consecuente) + ":" + estado
                + ":directosEIndirectosEvaluados; ciclo=" + cadena.cicloDetectado);
    }

    private RelacionSeriacionCurricularV2DTO copiarRelacionSeriacion(RelacionSeriacionCurricularV2DTO origen) {
        RelacionSeriacionCurricularV2DTO copia = new RelacionSeriacionCurricularV2DTO();
        copia.setIdAntecedente(origen.getIdAntecedente()); copia.setIdConsecuente(origen.getIdConsecuente());
        copia.setSemestreAntecedente(origen.getSemestreAntecedente()); copia.setSemestreConsecuente(origen.getSemestreConsecuente());
        copia.setBloqueAntecedente(origen.getBloqueAntecedente()); copia.setBloqueConsecuente(origen.getBloqueConsecuente());
        copia.setTipoSeriacion(origen.getTipoSeriacion()); copia.setTipoRelacion(origen.getTipoRelacion());
        copia.setAntecedenteObligatorio(origen.getAntecedenteObligatorio()); copia.setEvidencia(origen.getEvidencia());
        return copia;
    }

    private List<Long> construirCaminoAntecedente(Long antecedente, List<Long> caminoRaiz) {
        List<Long> resultado = new ArrayList<Long>(); resultado.add(antecedente);
        for (int indice = caminoRaiz.size() - 1; indice >= 0; indice--) resultado.add(caminoRaiz.get(indice));
        return resultado;
    }

    private void agregarRelacionInteranualUnica(EvaluacionCargaAcademicaV2DTO carga,
            RelacionSeriacionCurricularV2DTO relacion) {
        String llave = llaveRelacionSeriacion(relacion) + ":" + relacion.getTipoRelacion() + ":" + relacion.getCaminoCurricular();
        for (RelacionSeriacionCurricularV2DTO existente : carga.getRelacionesSeriacionV2()) {
            String llaveExistente = llaveRelacionSeriacion(existente) + ":" + existente.getTipoRelacion() + ":" + existente.getCaminoCurricular();
            if (llave.equals(llaveExistente)) return;
        }
        carga.getRelacionesSeriacionV2().add(relacion);
    }

    private static class ResultadoCadenaInteranual {
        private boolean noCumplida;
        private boolean requiereRevision;
        private boolean cicloDetectado;
    }

    /**
     * F8D verifica que una dependencia formal del PE tenga una sola
     * clasificacion de seriacion. No recalcula RN-8.1, RN-8.2 ni RN-8.3.
     */
    private void consolidarCierreSeriacionFase8(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_8);
        Map<String, TipoSeriacionV2> tiposPorRelacion = new LinkedHashMap<String, TipoSeriacionV2>();
        for (UnidadEvaluacionCurricularV2DTO consecuente : unidades) {
            if (consecuente == null || consecuente.getRelacionesSeriacion() == null) continue;
            for (RelacionSeriacionCurricularV2DTO relacion : consecuente.getRelacionesSeriacion()) {
                if (relacion == null) continue;
                Long idConsecuente = relacion.getIdConsecuente() == null ? consecuente.getIdPrograma() : relacion.getIdConsecuente();
                String llave = String.valueOf(relacion.getIdAntecedente()) + "->" + String.valueOf(idConsecuente);
                TipoSeriacionV2 tipo = relacion.getTipoSeriacion();
                if (tipo == null || tipo == TipoSeriacionV2.NO_DETERMINADA) {
                    registrarDecisionCierreFase8(carga, "TIPO_SERIACION_NO_DETERMINADO:" + llave);
                    carga.setRequiereRevisionFase8(Boolean.TRUE);
                    continue;
                }
                TipoSeriacionV2 existente = tiposPorRelacion.get(llave);
                if (existente == null) {
                    tiposPorRelacion.put(llave, tipo);
                } else if (existente != tipo) {
                    String inconsistencia = "TIPO_SERIACION_INCONSISTENTE:" + llave + ":" + existente + ":" + tipo;
                    agregarUnico(carga.getRelacionesTipoInconsistenteFase8(), inconsistencia);
                    registrarDecisionCierreFase8(carga, inconsistencia);
                    carga.setRequiereRevisionFase8(Boolean.TRUE);
                }
            }
        }
        if (Boolean.TRUE.equals(carga.getRequiereRevisionRn81())
                || Boolean.TRUE.equals(carga.getRequiereRevisionRn82())
                || Boolean.TRUE.equals(carga.getRequiereRevisionRn83())) {
            carga.setRequiereRevisionFase8(Boolean.TRUE);
        }
        if (Boolean.TRUE.equals(carga.getRequiereRevisionFase8())) {
            carga.setEstadoCierreFase8(EstadoCierreFase8V2.REQUIERE_REVISION);
        } else {
            carga.setEstadoCierreFase8(EstadoCierreFase8V2.CONSOLIDADA_EN_SOMBRA);
        }
        String evidencia = "modeloComun={relaciones=" + carga.getRelacionesSeriacionV2().size()
                + ",rn81=" + carga.getAplicaRn81() + ",rn82=" + carga.getAplicaRn82()
                + ",rn83=" + carga.getAplicaRn83() + "}; inconsistencias="
                + carga.getRelacionesTipoInconsistenteFase8() + "; estadoCierre="
                + carga.getEstadoCierreFase8() + "; rn61Concurrente=" + carga.getAplicaRn61();
        agregarUnico(carga.getEvidencias(), "F8D:" + evidencia);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_8,
                Boolean.TRUE.equals(carga.getRequiereRevisionFase8()) ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                80, "F8D_" + carga.getEstadoCierreFase8(), evidencia);
    }

    private void registrarDecisionCierreFase8(EvaluacionCargaAcademicaV2DTO carga, String decision) {
        agregarUnico(carga.getDecisionesCierreFase8(), decision);
    }

    /**
     * RN-9.1 sólo incorpora el momento administrativo. Consume las decisiones
     * ya calculadas y no altera situación, carga, seriación ni excepciones.
     */
    private void evaluarReinscripcionExtemporaneaRn91(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_9_1);
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_9_1);
        TipoProcesoAdministrativoV2 proceso = contexto.getTipoProcesoAdministrativo();
        carga.setTipoProcesoAdministrativoRn91(proceso);
        if (proceso == null || proceso == TipoProcesoAdministrativoV2.NO_DETERMINADO) {
            carga.setEstadoReinscripcionExtemporaneaRn91(EstadoReinscripcionExtemporaneaV2.REQUIERE_REVISION);
            registrarDecisionRn91(carga, "TIPO_PROCESO_ADMINISTRATIVO_NO_DETERMINADO");
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_9_1,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 80, "RN91_REQUIERE_REVISION",
                    "No se determina si el proceso es ordinario o extemporáneo; no se infiere una excepción.");
            return;
        }
        if (proceso == TipoProcesoAdministrativoV2.ORDINARIO) {
            carga.setEstadoReinscripcionExtemporaneaRn91(EstadoReinscripcionExtemporaneaV2.NO_APLICA);
            registrarDecisionRn91(carga, "PROCESO_ORDINARIO_RN91_NO_APLICA");
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_9_1, null, 80, "RN91_NO_APLICA",
                    "Proceso ordinario: RN-9.1 no añade una condición administrativa.");
            return;
        }
        carga.setAplicaRn91(Boolean.TRUE);
        boolean tipificada = false;
        boolean extraordinaria = false;
        for (ExcepcionCurricularV2DTO excepcion : resultado.getExcepcionesEvaluadas()) {
            if (excepcion == null) continue;
            if (excepcion.getTipo() == TipoExcepcionCurricularV2.TIPIFICADA) tipificada = true;
            if (excepcion.getTipo() == TipoExcepcionCurricularV2.EXTRAORDINARIA
                    || excepcion.getEstado() == EstadoExcepcionCurricularV2.REQUIERE_REVISION) extraordinaria = true;
        }
        carga.setExcepcionTipificadaRn91(Boolean.valueOf(tipificada));
        if (extraordinaria) {
            carga.setRequiereValoracionIndividualRn91(Boolean.TRUE);
            carga.setRequiereResolucionDocumentadaRn91(Boolean.TRUE);
            carga.setEstadoReinscripcionExtemporaneaRn91(EstadoReinscripcionExtemporaneaV2.REQUIERE_VALORACION_INDIVIDUAL);
            registrarDecisionRn91(carga, "EXCEPCION_EXTRAORDINARIA_NO_TIPIFICADA_REQUIERE_RESOLUCION_DOCUMENTADA");
        } else if (tipificada) {
            carga.setEstadoReinscripcionExtemporaneaRn91(EstadoReinscripcionExtemporaneaV2.EXTEMPORANEO_CON_EXCEPCION_TIPIFICADA);
            registrarDecisionRn91(carga, "EXCEPCION_TIPIFICADA_CTR02_REUTILIZADA");
        } else if (tieneRestriccionAcademicaRn91(resultado, carga)) {
            carga.setEstadoReinscripcionExtemporaneaRn91(EstadoReinscripcionExtemporaneaV2.EXTEMPORANEO_RESTRINGIDO_POR_REGLAS);
            registrarDecisionRn91(carga, "RESTRICCIONES_ACADEMICAS_EXISTENTES_SE_CONSERVAN");
        } else {
            carga.setEstadoReinscripcionExtemporaneaRn91(EstadoReinscripcionExtemporaneaV2.EXTEMPORANEO_PROCEDE_SEGUN_REGLAS);
            registrarDecisionRn91(carga, "MISMAS_REGLAS_ACADEMICAS_QUE_PROCESO_ORDINARIO");
        }
        String evidencia = "proceso=EXTEMPORANEO; evidenciaProceso=" + contexto.getEvidenciaProcesoAdministrativo()
                + "; situacion=" + resultado.getSituacionAcademicaCalculada() + "; cargaMaxima=" + carga.getCargaMaximaNormativa()
                + "; rn64=" + carga.getAplicaRn64() + "; rn61=" + carga.getAplicaRn61()
                + "; seriacion={rn81=" + carga.getAplicaRn81() + ",rn82=" + carga.getAplicaRn82() + ",rn83=" + carga.getAplicaRn83()
                + "}; resolucionExtraordinaria=" + evidenciaResolucionExtraordinariaRn95(contexto, extraordinaria)
                + "; estado=" + carga.getEstadoReinscripcionExtemporaneaRn91();
        agregarUnico(carga.getEvidencias(), "RN91:" + evidencia);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_9_1,
                extraordinaria ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null, 80,
                "RN91_" + carga.getEstadoReinscripcionExtemporaneaRn91(), evidencia);
    }

    private boolean tieneRestriccionAcademicaRn91(ResultadoEvaluacionCurricularV2DTO resultado,
            EvaluacionCargaAcademicaV2DTO carga) {
        if (Boolean.TRUE.equals(carga.getAplicaRn64()) || Boolean.FALSE.equals(carga.getMaximoCumplido())) return true;
        for (DecisionUnidadCurricularV2DTO decision : resultado.getDecisiones()) {
            if (decision != null && decision.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.BLOQUEADA)) return true;
        }
        return false;
    }

    private void registrarDecisionRn91(EvaluacionCargaAcademicaV2DTO carga, String decision) {
        agregarUnico(carga.getDecisionesRn91(), decision);
    }

    private String evidenciaResolucionExtraordinariaRn95(ContextoEvaluacionCurricularV2DTO contexto, boolean extraordinaria) {
        if (!extraordinaria) return "NO_APLICA";
        if (esTexto(contexto.getInstanciaResolutoraExtraordinaria()) && esTexto(contexto.getFundamentacionExtraordinaria())
                && esTexto(contexto.getFolioInstitucionalExtraordinario()))
            return "DOCUMENTADA{instancia=" + contexto.getInstanciaResolutoraExtraordinaria()
                    + ",fundamentacion=" + contexto.getFundamentacionExtraordinaria()
                    + ",folio=" + contexto.getFolioInstitucionalExtraordinario() + "}";
        return "PENDIENTE_DE_RESOLUCION";
    }

    private boolean esTexto(String valor) { return valor != null && !valor.trim().isEmpty(); }

    /** RN-9.5 consolida evidencia ya calculada; no reevalúa reglas ni altera decisiones. */
    private void auditarCoberturaTrazabilidadRn95(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_9_5);
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_9_5);
        String resumen = "situacion=" + resultado.getSituacionAcademicaCalculada() + "; semestreReferencia=" + contexto.getSemestreReferencia()
                + "; totalReprobadas=" + resultado.getTotalReprobadas() + "; rn61="
                + (Boolean.TRUE.equals(carga.getAplicaRn61()) ? "APLICA:" + carga.getDecisionGlobalRn61() : "NO_APLICA")
                + "; rn64=" + carga.getAplicaRn64() + "; excepciones=" + resultado.getExcepcionesEvaluadas().size()
                + "; fecha=" + contexto.getFechaEvaluacion() + "; version=" + contexto.getVersionReglas();
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_9_5, null, 90, "RN95_COBERTURA_CONTEXTO", resumen);
        Set<Long> auditadas = new HashSet<Long>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad == null || unidad.getIdPrograma() == null || !auditadas.add(unidad.getIdPrograma())) continue;
            DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, unidad.getIdPrograma());
            String oferta = Boolean.TRUE.equals(unidad.getOfertada()) ? "OFERTADA"
                    : (unidad.getCausaIndisponibilidadOferta() == CausaIndisponibilidadOfertaV2.SIN_CUPO ? "SIN_CUPO" : "SIN_OFERTA");
            String detalle = "ud=" + unidad.getIdPrograma() + "; estado=" + unidad.getEstado() + "; causa=" + unidad.getCausa()
                    + "; disponibilidad=" + oferta + "; cupoConocido=" + unidad.getCupoDisponibleConocido()
                    + "; decision=" + (decision == null ? "NO_DETERMINADA" : decision.getEstadosSombra())
                    + "; motivos=" + (decision == null ? "[]" : decision.getMotivos());
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_9_5, null, 90, "RN95_COBERTURA_UD", detalle);
        }
    }

    /** RN-9.3 para S7/S8 reutiliza F6-F8; no establece un umbral porcentual de créditos. */
    private void evaluarAvanceUltimosSemestresRn93(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_9_3);
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_9_3);
        Integer semestre = contexto.getSemestreReferencia();
        if (semestre == null) { finalizarRevisionCreditosRn93(resultado, contexto, carga, "SEMESTRE_OBJETIVO_NO_DETERMINADO"); return; }
        if (!esSemestreElectivas(semestre)) {
            carga.setEstadoAvanceUltimosSemestresRn93(EstadoAvanceUltimosSemestresV2.NO_APLICA);
            registrarDecisionRn93(carga, "RN93_NO_APLICA_SEMESTRE=" + semestre);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_9_3, null, 80, "RN93_NO_APLICA",
                    "RN-9.3 aplica específicamente a S7/S8; RN-6.1 conserva su propia aplicabilidad.");
            return;
        }
        carga.setAplicaRn93(Boolean.TRUE);
        Map<Long, UnidadEvaluacionCurricularV2DTO> unidadesPorId = new LinkedHashMap<Long, UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad == null || unidad.getIdPrograma() == null || unidadesPorId.containsKey(unidad.getIdPrograma())) continue;
            unidadesPorId.put(unidad.getIdPrograma(), unidad);
        }
        boolean seriacionNoCumplida = false, requisitoPendiente = false, requisitoRevision = false;
        for (UnidadEvaluacionCurricularV2DTO componente : unidades) {
            if (componente == null || componente.getIdPrograma() == null) continue;
            if (esCandidataFinalRn93(componente, semestre) && estaRestringidaSeriacionRn93(carga, componente.getIdPrograma())) {
                seriacionNoCumplida = true;
                registrarRestriccionRn93(resultado, contexto, componente, CodigoMotivoCurricularV2.SERIACION_INDIRECTA, "RESTRINGIDA_POR_SERIACION_RN8");
            }
            if (!esComponenteConRequisitoPeRn93(componente)) continue;
            if (!Boolean.TRUE.equals(componente.getRequisitosEspecificosPeComprobados())) {
                requisitoRevision = true; agregarIdUnico(carga.getComponentesRevisionRequisitoPeRn93(), componente.getIdPrograma());
                registrarRevisionComponenteRn93(resultado, contexto, componente, "REQUISITOS_ESPECIFICOS_PE_NO_COMPROBADOS"); continue;
            }
            for (Long idRequisito : componente.getRequisitosCurricularesPe()) {
                UnidadEvaluacionCurricularV2DTO requisito = unidadesPorId.get(idRequisito);
                if (requisito == null || requisito.getEstado() == null) {
                    requisitoRevision = true; agregarIdUnico(carga.getComponentesRevisionRequisitoPeRn93(), componente.getIdPrograma());
                    registrarRevisionComponenteRn93(resultado, contexto, componente, "REQUISITO_PE_NO_LOCALIZADO:" + idRequisito);
                } else if (requisito.getEstado() != EstadoUnidadCurricularV2.ACREDITADA) {
                    requisitoPendiente = true; agregarIdUnico(carga.getComponentesRestringidosRequisitoPeRn93(), componente.getIdPrograma());
                    registrarRestriccionRn93(resultado, contexto, componente, CodigoMotivoCurricularV2.REQUISITO_CURRICULAR_PENDIENTE,
                            "REQUISITO_CURRICULAR_PENDIENTE:" + idRequisito + ":" + requisito.getEstado());
                }
            }
        }
        if (requisitoRevision) { carga.setRequiereRevisionRn93(Boolean.TRUE); carga.setEstadoAvanceUltimosSemestresRn93(EstadoAvanceUltimosSemestresV2.REQUIERE_REVISION); }
        else if (Boolean.TRUE.equals(carga.getAplicaRn64())) carga.setEstadoAvanceUltimosSemestresRn93(EstadoAvanceUltimosSemestresV2.RESTRINGIDO_POR_RN64);
        else if (seriacionNoCumplida) carga.setEstadoAvanceUltimosSemestresRn93(EstadoAvanceUltimosSemestresV2.RESTRINGIDO_POR_SERIACION);
        else if (requisitoPendiente) carga.setEstadoAvanceUltimosSemestresRn93(EstadoAvanceUltimosSemestresV2.RESTRINGIDO_POR_REQUISITO_PE);
        else carga.setEstadoAvanceUltimosSemestresRn93(EstadoAvanceUltimosSemestresV2.AVANCE_ULTIMOS_SEMESTRES_CUMPLE);
        String evidencia = "sinUmbralPorcentualCreditos=true; rn64=" + carga.getAplicaRn64()
                + "; rn61NoAplicaS7S8=" + !Boolean.TRUE.equals(carga.getAplicaRn61()) + "; estado=" + carga.getEstadoAvanceUltimosSemestresRn93();
        agregarUnico(carga.getEvidencias(), "RN93:" + evidencia);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_9_3,
                Boolean.TRUE.equals(carga.getRequiereRevisionRn93()) ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                80, "RN93_" + carga.getEstadoAvanceUltimosSemestresRn93(), evidencia);
    }

    private void finalizarRevisionCreditosRn93(ResultadoEvaluacionCurricularV2DTO resultado, ContextoEvaluacionCurricularV2DTO contexto,
            EvaluacionCargaAcademicaV2DTO carga, String detalle) {
        carga.setRequiereRevisionRn93(Boolean.TRUE); carga.setEstadoAvanceUltimosSemestresRn93(EstadoAvanceUltimosSemestresV2.REQUIERE_REVISION);
        registrarDecisionRn93(carga, detalle); registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_9_3,
                CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 80, "RN93_REQUIERE_REVISION", detalle);
    }

    private boolean esCandidataFinalRn93(UnidadEvaluacionCurricularV2DTO unidad, Integer semestreObjetivo) { return semestreObjetivo.equals(unidad.getSemestre()) || esComponenteConRequisitoPeRn93(unidad); }
    private boolean esComponenteConRequisitoPeRn93(UnidadEvaluacionCurricularV2DTO unidad) {
        return Boolean.TRUE.equals(unidad.getComponenteSeminarioTitulacion());
    }
    private boolean estaRestringidaSeriacionRn93(EvaluacionCargaAcademicaV2DTO carga, Long id) { return carga.getUdsInterbloqueNoCumplidasRn81().contains(id) || carga.getUdsIntersemestralesNoCumplidasRn82().contains(id) || carga.getUdsInteranualesNoCumplidasRn83().contains(id); }

    private void registrarRestriccionRn93(ResultadoEvaluacionCurricularV2DTO resultado, ContextoEvaluacionCurricularV2DTO contexto,
            UnidadEvaluacionCurricularV2DTO unidad, CodigoMotivoCurricularV2 motivo, String detalle) {
        DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, unidad.getIdPrograma()); if (decision == null) return;
        agregarUnico(decision.getMotivos(), motivo); agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.BLOQUEADA); agregarExplicacion(decision, "RN-9.3: " + detalle + ".");
        TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_9_3, motivo, 80, "RN93_RESTRINGIDA", obtenerLlaveUnidad(unidad) + "; " + detalle);
        decision.getTrazas().add(traza); resultado.getTrazas().add(traza);
    }

    private void registrarRevisionComponenteRn93(ResultadoEvaluacionCurricularV2DTO resultado, ContextoEvaluacionCurricularV2DTO contexto,
            UnidadEvaluacionCurricularV2DTO unidad, String detalle) {
        DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, unidad.getIdPrograma()); if (decision == null) return;
        agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.ESTRUCTURA_PE_NO_DETERMINADA); agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION); agregarExplicacion(decision, "RN-9.3: " + detalle + ".");
        TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_9_3, CodigoMotivoCurricularV2.ESTRUCTURA_PE_NO_DETERMINADA, 80, "RN93_REQUIERE_REVISION", obtenerLlaveUnidad(unidad) + "; " + detalle);
        decision.getTrazas().add(traza); resultado.getTrazas().add(traza);
    }

    private void registrarDecisionRn93(EvaluacionCargaAcademicaV2DTO carga, String decision) { agregarUnico(carga.getDecisionesRn93(), decision); }

    /** RN-9.4 compone catálogos verificados y motores previos; no autoriza egreso ni inscripción. */
    private void evaluarRequisitosAcreditacionEgresoRn94(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        carga.setAplicaRn94(Boolean.TRUE);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_9_4);
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_9_4);
        Map<Long, UnidadEvaluacionCurricularV2DTO> porId = new LinkedHashMap<Long, UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades)
            if (unidad != null && unidad.getIdPrograma() != null && !porId.containsKey(unidad.getIdPrograma())) porId.put(unidad.getIdPrograma(), unidad);

        boolean revision = !Boolean.TRUE.equals(contexto.getEstructuraProgramaEducativoComprobada());
        Set<Long> obligatorias = new HashSet<Long>(contexto.getIdsObligatoriasRequeridasEgreso());
        carga.setObligatoriasRequeridasRn94(Integer.valueOf(obligatorias.size()));
        int obligatoriasAcreditadas = 0;
        for (Long id : obligatorias) {
            UnidadEvaluacionCurricularV2DTO unidad = porId.get(id);
            if (unidad == null || !esObligatoria(unidad) || unidad.getEstado() == null) { revision = true; registrarDecisionRn94(carga, "OBLIGATORIA_PE_NO_VERIFICABLE:" + id); continue; }
            if (unidad.getEstado() == EstadoUnidadCurricularV2.ACREDITADA) obligatoriasAcreditadas++;
            else agregarIdUnico(carga.getObligatoriasPendientesRn94(), id);
        }
        carga.setObligatoriasAcreditadasRn94(Integer.valueOf(obligatoriasAcreditadas));

        // El reglamento definitivo no exige una electiva mínima para RN-9.4.
        carga.setElectivasAcreditadasRn94(Integer.valueOf(0));
        boolean optativasCumplidas = carga.getEstadoRequerimientoOptativo() == EstadoRequerimientoOptativoV2.REQUERIMIENTO_OPTATIVO_CUMPLIDO
                || carga.getCantidadOptativasAcreditadasRn711().intValue() >= 8;
        // Un adeudo adicional puede requerir prioridad o sustitución, pero no
        // deshace las ocho optativas ya acreditadas ni bloquea RN-9.4 por sí solo.
        if (carga.getEstadoRequerimientoOptativo() == EstadoRequerimientoOptativoV2.REQUIERE_REVISION
                && !optativasCumplidas) revision = true;

        evaluarComponentesFinalesRn94(resultado, contexto, carga, porId);
        if (revision) carga.setEstadoRequisitosEgresoAcademicoRn94(EstadoRequisitosEgresoAcademicoV2.REQUIERE_REVISION);
        else if (obligatoriasAcreditadas == obligatorias.size() && optativasCumplidas)
            carga.setEstadoRequisitosEgresoAcademicoRn94(EstadoRequisitosEgresoAcademicoV2.REQUISITOS_ACADEMICOS_EGRESO_CUMPLIDOS);
        else carga.setEstadoRequisitosEgresoAcademicoRn94(EstadoRequisitosEgresoAcademicoV2.REQUISITOS_ACADEMICOS_EGRESO_PENDIENTES);
        carga.setRequiereRevisionRn94(Boolean.valueOf(revision));
        String evidencia = "obligatorias={requeridas=" + obligatorias.size() + ",acreditadas=" + obligatoriasAcreditadas
                + ",pendientes=" + carga.getObligatoriasPendientesRn94() + "}; optativas=" + carga.getCantidadOptativasAcreditadasRn711()
                + "/8; electivas=NO_REQUERIDAS_POR_RN94; componentes=" + carga.getEstadoComponentesFinalesRn94()
                + "; referenciaRN15=REUTILIZADA_SIN_REQUISITO_GENERAL_85; estado=" + carga.getEstadoRequisitosEgresoAcademicoRn94();
        agregarUnico(carga.getEvidencias(), "RN94:" + evidencia);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_9_4,
                revision ? CodigoMotivoCurricularV2.ESTRUCTURA_PE_NO_DETERMINADA : null, 80,
                "RN94_" + carga.getEstadoRequisitosEgresoAcademicoRn94(), evidencia);
    }

    private void evaluarComponentesFinalesRn94(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            Map<Long, UnidadEvaluacionCurricularV2DTO> porId) {
        Set<Long> seriadas = new HashSet<Long>(contexto.getIdsSeriadasPrimerosSeisSemestres());
        boolean revision = false;
        for (Long id : seriadas) {
            UnidadEvaluacionCurricularV2DTO unidad = porId.get(id);
            if (unidad == null || unidad.getSemestre() == null || unidad.getSemestre().intValue() < 1 || unidad.getSemestre().intValue() > 6
                    || unidad.getEstado() == null || !esSeriadaFase8(unidad)) { revision = true; continue; }
            agregarIdUnico(carga.getSeriadasS1S6RequeridasRn94(), id);
            if (unidad.getEstado() != EstadoUnidadCurricularV2.ACREDITADA) agregarIdUnico(carga.getSeriadasS1S6PendientesRn94(), id);
        }
        Set<Long> componentes = new HashSet<Long>(contexto.getComponentesFinalesSolicitados());
        for (UnidadEvaluacionCurricularV2DTO unidad : porId.values()) if (esComponenteFinalRn94(unidad)) componentes.add(unidad.getIdPrograma());
        if (componentes.isEmpty()) { carga.setEstadoComponentesFinalesRn94(EstadoRequisitoComponenteFinalV2.NO_APLICA); return; }
        for (Long id : componentes) {
            UnidadEvaluacionCurricularV2DTO componente = porId.get(id);
            if (componente == null || !esComponenteFinalRn94(componente)) { revision = true; agregarIdUnico(carga.getComponentesFinalesRevisionRn94(), id); continue; }
            if (!carga.getSeriadasS1S6PendientesRn94().isEmpty()) {
                agregarIdUnico(carga.getComponentesFinalesPendientesRn94(), id);
                DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, id);
                if (decision != null) {
                    agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.REQUISITO_CURRICULAR_PENDIENTE);
                    agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.BLOQUEADA);
                    agregarExplicacion(decision, "RN-9.4: existen UD seriadas S1-S6 pendientes.");
                }
            } else agregarIdUnico(carga.getComponentesFinalesCumplidosRn94(), id);
        }
        if (revision) carga.setEstadoComponentesFinalesRn94(EstadoRequisitoComponenteFinalV2.REQUIERE_REVISION);
        else if (!carga.getComponentesFinalesPendientesRn94().isEmpty()) carga.setEstadoComponentesFinalesRn94(EstadoRequisitoComponenteFinalV2.REQUISITOS_SERIADAS_S1_S6_PENDIENTES);
        else carga.setEstadoComponentesFinalesRn94(EstadoRequisitoComponenteFinalV2.REQUISITOS_SERIADAS_S1_S6_CUMPLIDOS);
    }

    private boolean esComponenteFinalRn94(UnidadEvaluacionCurricularV2DTO unidad) {
        return unidad.getTipoComponenteCurricular() == TipoComponenteCurricularV2.PROYECTO_TERMINAL
                || unidad.getTipoComponenteCurricular() == TipoComponenteCurricularV2.MODULO_INTEGRADOR;
    }

    private boolean esSeriadaFase8(UnidadEvaluacionCurricularV2DTO unidad) {
        return Boolean.TRUE.equals(unidad.getSeriacionConocida()) || Boolean.TRUE.equals(unidad.getSeriacionDirectaConocida())
                || Boolean.TRUE.equals(unidad.getSeriacionIndirectaConocida()) || (unidad.getRelacionesSeriacion() != null && !unidad.getRelacionesSeriacion().isEmpty());
    }

    private void registrarDecisionRn94(EvaluacionCargaAcademicaV2DTO carga, String decision) { agregarUnico(carga.getDecisionesRn94(), decision); }

    private void determinarSituacionAcademica(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, int reprobadas, boolean datosInsuficientes) {
        String situacion = reprobadas > 0 ? "IRREGULAR" : (datosInsuficientes ? "NO_DETERMINADA" : "REGULAR");
        resultado.setSituacionAcademica(situacion);
        resultado.setSituacionAcademicaCalculada(situacion);
        resultado.setSituacionAcademicaActualCalculada(situacion);
        resultado.setCantidadReprobadasVigentes(Integer.valueOf(reprobadas));
        resultado.setCargaMaximaFuturaSombra("NO_DETERMINADA".equals(situacion)
                ? null : Integer.valueOf(reprobadas > 0 ? 6 : 8));
        String detalle = "Situación=" + situacion + "; reprobadasVigentes=" + reprobadas
                + "; UD=" + resultado.getUnidadesOrigenIrregularidad()
                + "; descartadas=" + resultado.getDatosDescartadosSituacionAcademica()
                + "; evidencia=" + resultado.getEvidenciasSituacionAcademica()
                + "; cargaFuturaSombra=" + resultado.getCargaMaximaFuturaSombra();
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_1_2,
                reprobadas > 0 ? CodigoMotivoCurricularV2.REPROBACION_ACUMULADA
                        : (datosInsuficientes ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null),
                80, "SITUACION_ACADEMICA_" + situacion, detalle);
    }

    /** RN-1.3 reutiliza RN-1.2; no recalcula ni modifica la situación productiva. */
    private void evaluarRecuperacionRegularidad(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        resultado.setRecuperacionRegularidadEvaluada(Boolean.TRUE);
        resultado.getUdsReprobadasImpedimentoRecuperacion().addAll(resultado.getUnidadesOrigenIrregularidad());
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if ((unidad.getEstado() == EstadoUnidadCurricularV2.BAJA_PARCIAL
                    || unidad.getEstado() == EstadoUnidadCurricularV2.NO_CURSADA)
                    && unidad.getIdPrograma() != null)
                resultado.getPendientesNoReprobatoriosRecuperacion().add(unidad.getIdPrograma());
        }

        boolean hayReprobadas = resultado.getCantidadReprobadasVigentes() != null
                && resultado.getCantidadReprobadasVigentes().intValue() > 0;
        boolean seriacionPendiente = existeSeriacionPendiente(unidades);
        boolean severa = resultado.getCantidadReprobadasVigentes() != null
                && resultado.getCantidadReprobadasVigentes().intValue() >= 4;
        boolean actividadIndeterminada = resultado.getEstadoActividadAcademica() == EstadoActividadAcademicaV2.NO_DETERMINADO;
        boolean actividadImpide = resultado.getEstadoActividadAcademica() == EstadoActividadAcademicaV2.INACTIVO
                || resultado.getEstadoActividadAcademica() == EstadoActividadAcademicaV2.REINCORPORACION_PENDIENTE;
        boolean permanenciaIndeterminada = !Boolean.TRUE.equals(contexto.getPermanenciaDentroLimiteComprobada())
                && !Boolean.TRUE.equals(contexto.getLimitePermanenciaExcedido());

        if (!hayReprobadas) resultado.getCondicionesRecuperacionCumplidas().add("SIN_REPROBACIONES_VIGENTES");
        else resultado.getCondicionesRecuperacionNoCumplidas().add("REPROBACIONES_VIGENTES:" + resultado.getUnidadesOrigenIrregularidad());
        if (!seriacionPendiente) resultado.getCondicionesRecuperacionCumplidas().add("SERIACION_SIN_PENDIENTES_CONOCIDAS");
        else resultado.getCondicionesRecuperacionNoCumplidas().add("SERIACION_PENDIENTE");
        if (!severa) resultado.getCondicionesRecuperacionCumplidas().add("SIN_RESTRICCION_SEVERA_6_4_OBSERVADA");
        else resultado.getCondicionesRecuperacionNoCumplidas().add("RESTRICCION_SEVERA_6_4_OBSERVADA");
        if (Boolean.TRUE.equals(contexto.getPermanenciaDentroLimiteComprobada()))
            resultado.getCondicionesRecuperacionCumplidas().add("PERMANENCIA_DENTRO_LIMITE_COMPROBADA");
        else if (Boolean.TRUE.equals(contexto.getLimitePermanenciaExcedido()))
            resultado.getCondicionesRecuperacionCumplidas().add("PLAZO_MAXIMO_IDENTIFICADO_REQUIERE_RESOLUCION_INSTITUCIONAL");
        else resultado.getCondicionesRecuperacionNoCumplidas().add("PERMANENCIA_NO_DETERMINADA");
        if (!actividadIndeterminada && !actividadImpide) resultado.getCondicionesRecuperacionCumplidas().add("ACTIVIDAD_COMPATIBLE");
        else if (actividadIndeterminada) resultado.getCondicionesRecuperacionNoCumplidas().add("ACTIVIDAD_NO_DETERMINADA");
        else resultado.getCondicionesRecuperacionNoCumplidas().add("ACTIVIDAD_IMPIDE_CONTINUIDAD:" + resultado.getEstadoActividadAcademica());

        if (hayReprobadas || seriacionPendiente || severa || actividadImpide) {
            resultado.setRecuperacionRegularidadProcede(Boolean.FALSE);
            resultado.setResultadoRecuperacionRegularidad("NO_PROCEDE");
        } else if ("NO_DETERMINADA".equals(resultado.getSituacionAcademicaCalculada())
                || actividadIndeterminada || permanenciaIndeterminada) {
            resultado.setRecuperacionRegularidadProcede(null);
            resultado.setResultadoRecuperacionRegularidad("REQUIERE_REVISION");
        } else {
            resultado.setRecuperacionRegularidadProcede(Boolean.TRUE);
            resultado.setResultadoRecuperacionRegularidad("PROCEDE_EN_SOMBRA");
        }
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_1_3,
                Boolean.TRUE.equals(resultado.getRecuperacionRegularidadProcede()) ? null
                        : (hayReprobadas ? CodigoMotivoCurricularV2.REPROBACION_ACUMULADA
                                : CodigoMotivoCurricularV2.DATOS_INSUFICIENTES),
                75, "RECUPERACION_REGULARIDAD_" + resultado.getResultadoRecuperacionRegularidad(),
                "SituacionRN12=" + resultado.getSituacionAcademicaActualCalculada()
                        + "; cumplidas=" + resultado.getCondicionesRecuperacionCumplidas()
                        + "; noCumplidas=" + resultado.getCondicionesRecuperacionNoCumplidas()
                        + "; reprobadas=" + resultado.getUdsReprobadasImpedimentoRecuperacion()
                        + "; pendientesNoReprobatorios=" + resultado.getPendientesNoReprobatoriosRecuperacion());
    }

    private boolean esReprobacionVigentePrevia(UnidadEvaluacionCurricularV2DTO unidad) {
        if (unidad.getEstado() != EstadoUnidadCurricularV2.REPROBADA
                || unidad.getCausa() != CausaPendienteUnidadV2.REPROBACION) return false;
        if (Boolean.FALSE.equals(unidad.getReprobacionVigenteConocida())
                || Boolean.FALSE.equals(unidad.getReprobacionPeriodoPrevioConocida())) return false;
        return true;
    }

    /** Acreditada prevalece sobre una representación histórica duplicada reprobada. */
    private void consolidarEstadoVigente(UnidadEvaluacionCurricularV2DTO existente,
            UnidadEvaluacionCurricularV2DTO candidata) {
        if (candidata.getEstado() == EstadoUnidadCurricularV2.ACREDITADA
                && existente.getEstado() != EstadoUnidadCurricularV2.ACREDITADA) {
            existente.setEstado(EstadoUnidadCurricularV2.ACREDITADA);
            existente.setCausa(null);
            existente.setReprobacionVigenteConocida(Boolean.FALSE);
        }
        if (candidata.getIntentos() != null && (existente.getIntentos() == null
                || candidata.getIntentos().intValue() > existente.getIntentos().intValue()))
            existente.setIntentos(candidata.getIntentos());
        if (Boolean.FALSE.equals(candidata.getReprobacionPeriodoPrevioConocida()))
            existente.setReprobacionPeriodoPrevioConocida(Boolean.FALSE);
        if (candidata.getIntentosFallidosHistoricosConfiables() != null && (existente.getIntentosFallidosHistoricosConfiables() == null
                || candidata.getIntentosFallidosHistoricosConfiables().intValue() > existente.getIntentosFallidosHistoricosConfiables().intValue()))
            existente.setIntentosFallidosHistoricosConfiables(candidata.getIntentosFallidosHistoricosConfiables());
        existente.getIntentosAcreditacion().addAll(candidata.getIntentosAcreditacion());
    }

    /** RN-1.4 cuenta intentos por UD; nunca los usa como conteo de reprobadas. */
    private void evaluarLimiteIntentos(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            EvaluacionIntentosUnidadV2DTO evaluacion = evaluarIntentosUnidad(unidad, contexto);
            resultado.getEvaluacionesIntentos().add(evaluacion);
            CodigoMotivoCurricularV2 motivo = (evaluacion.getEstadoLimite() == EstadoLimiteIntentosV2.LIMITE_AGOTADO
                    || evaluacion.getEstadoLimite() == EstadoLimiteIntentosV2.AGOTADA)
                    ? CodigoMotivoCurricularV2.LIMITE_INTENTOS
                    : motivoRevisionOportunidad(evaluacion);
            String detalle = "UD=" + evaluacion.getIdPrograma() + "; fallidos=" + evaluacion.getIntentosFallidos()
                    + "; acreditados=" + evaluacion.getIntentosAcreditados() + "; estado=" + evaluacion.getEstadoLimite()
                    + "; evidencia=" + evaluacion.getEvidencias() + "; duplicados=" + evaluacion.getDuplicadosDescartados();
            TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_1_4, motivo, 85,
                    "LIMITE_INTENTOS_" + evaluacion.getEstadoLimite(), detalle);
            resultado.getTrazas().add(traza);
            agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_1_4);
            actualizarPrioridad(resultado, 85);
            DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, unidad.getIdPrograma());
            if (decision != null) {
                decision.getTrazas().add(traza);
                if (motivo != null) agregarUnico(decision.getMotivos(), motivo);
                if (evaluacion.getEstadoLimite() == EstadoLimiteIntentosV2.LIMITE_AGOTADO
                        || evaluacion.getEstadoLimite() == EstadoLimiteIntentosV2.AGOTADA) {
                    agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.BLOQUEADA);
                    decision.setHabilitada(Boolean.FALSE);
                    agregarExplicacion(decision, "La UD agotó las oportunidades computables; no procede una inscripción ordinaria adicional.");
                } else if (evaluacion.getEstadoLimite() == EstadoLimiteIntentosV2.REQUIERE_REVISION) {
                    agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION);
                }
            }
        }
    }

    private CodigoMotivoCurricularV2 motivoRevisionOportunidad(EvaluacionIntentosUnidadV2DTO evaluacion) {
        if (evaluacion.getAviso() == null) return evaluacion.getEstadoLimite() == EstadoLimiteIntentosV2.REQUIERE_REVISION
                ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null;
        if (evaluacion.getAviso().contains(CodigoMotivoCurricularV2.BAJA_ADMINISTRATIVA_SEGUN_RESOLUCION.name()))
            return CodigoMotivoCurricularV2.BAJA_ADMINISTRATIVA_SEGUN_RESOLUCION;
        if (evaluacion.getAviso().contains(CodigoMotivoCurricularV2.EXTRAORDINARIO_HISTORICO_REQUIERE_REVISION.name()))
            return CodigoMotivoCurricularV2.EXTRAORDINARIO_HISTORICO_REQUIERE_REVISION;
        if (evaluacion.getAviso().contains(CodigoMotivoCurricularV2.NP_SIN_BAJA_REQUIERE_REVISION.name()))
            return CodigoMotivoCurricularV2.NP_SIN_BAJA_REQUIERE_REVISION;
        return CodigoMotivoCurricularV2.DATOS_INSUFICIENTES;
    }

    private EvaluacionIntentosUnidadV2DTO evaluarIntentosUnidad(UnidadEvaluacionCurricularV2DTO unidad,
            ContextoEvaluacionCurricularV2DTO contexto) {
        EvaluacionIntentosUnidadV2DTO evaluacion = new EvaluacionIntentosUnidadV2DTO();
        evaluacion.setIdPrograma(unidad.getIdPrograma()); evaluacion.setClave(unidad.getClave());
        if (unidad.getEstado() == EstadoUnidadCurricularV2.ACREDITADA) {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA);
        }
        Set<String> ejecuciones = new HashSet<String>();
        boolean identidadInsuficiente = false;
        boolean enCurso = unidad.getEstado() == EstadoUnidadCurricularV2.EN_CURSO;
        CodigoMotivoCurricularV2 motivoRevisionHistorica = null;
        for (IntentoAcreditacionUnidadV2DTO intento : unidad.getIntentosAcreditacion()) {
            if (intento == null) { identidadInsuficiente = true; continue; }
            String llave = llaveIntento(intento);
            if (llave == null) { identidadInsuficiente = true; continue; }
            if (!ejecuciones.add(llave)) { evaluacion.getDuplicadosDescartados().add("DUPLICADO_INTENTO:" + llave); continue; }
            evaluacion.setIntentosIdentificados(Integer.valueOf(evaluacion.getIntentosIdentificados().intValue() + 1));
            evaluacion.getEvidencias().add(intento.getEvidenciaFuente() == null ? "INTENTO:" + llave : intento.getEvidenciaFuente());
            if (intento.getEstado() == EstadoIntentoAcreditacionV2.REPROBADO)
                evaluacion.setIntentosFallidos(Integer.valueOf(evaluacion.getIntentosFallidos().intValue() + 1));
            if (intento.getEstado() == EstadoIntentoAcreditacionV2.ACREDITADO)
                evaluacion.setIntentosAcreditados(Integer.valueOf(evaluacion.getIntentosAcreditados().intValue() + 1));
            if (intento.getEstado() == EstadoIntentoAcreditacionV2.EN_CURSO) enCurso = true;
            if (intento.getEstado() == EstadoIntentoAcreditacionV2.NO_DETERMINADO) {
                identidadInsuficiente = true;
                motivoRevisionHistorica = intento.getEvidenciaFuente() != null && intento.getEvidenciaFuente().contains("BAJA_ADMINISTRATIVA_SEGUN_RESOLUCION")
                        ? CodigoMotivoCurricularV2.BAJA_ADMINISTRATIVA_SEGUN_RESOLUCION : CodigoMotivoCurricularV2.NP_SIN_BAJA_REQUIERE_REVISION;
            }
            if (intento.getEstado() == EstadoIntentoAcreditacionV2.EXTRAORDINARIO_HISTORICO) {
                identidadInsuficiente = true;
                motivoRevisionHistorica = CodigoMotivoCurricularV2.EXTRAORDINARIO_HISTORICO_REQUIERE_REVISION;
            }
        }
        if (unidad.getIntentosAcreditacion().isEmpty() && unidad.getIntentosFallidosHistoricosConfiables() != null) {
            evaluacion.setIntentosIdentificados(unidad.getIntentosFallidosHistoricosConfiables());
            evaluacion.setIntentosFallidos(unidad.getIntentosFallidosHistoricosConfiables());
            evaluacion.getEvidencias().add("RESUMEN_CANONICO_INTENTOS_FALLIDOS:" + unidad.getIntentosFallidosHistoricosConfiables());
        } else if (unidad.getIntentosAcreditacion().isEmpty() && unidad.getIntentos() != null) {
            identidadInsuficiente = true;
            evaluacion.getEvidencias().add("CONTEO_LEGADO_SIN_CALIDAD_DE_INTENTO:" + unidad.getIntentos());
        }
        evaluacion.setOportunidadesConsumidas(Integer.valueOf(evaluacion.getIntentosFallidos().intValue()
                + (enCurso ? 1 : 0)));
        int limiteOportunidades = contexto.getParametrosCurriculares() == null
                || contexto.getParametrosCurriculares().getOportunidadesPorUd() == null ? 3
                : contexto.getParametrosCurriculares().getOportunidadesPorUd().intValue();
        evaluacion.setOportunidadesDisponibles(Integer.valueOf(Math.max(0, limiteOportunidades - evaluacion.getOportunidadesConsumidas().intValue())));
        unidad.setOportunidadesConsumidas(evaluacion.getOportunidadesConsumidas());
        unidad.setOportunidadesDisponibles(evaluacion.getOportunidadesDisponibles());
        if (evaluacion.getIntentosAcreditados().intValue() > 0 || unidad.getEstado() == EstadoUnidadCurricularV2.ACREDITADA) {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA);
        } else if (identidadInsuficiente) {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.REQUIERE_REVISION);
            evaluacion.setAviso(motivoRevisionHistorica == null ? "Historial insuficiente para determinar oportunidades."
                    : motivoRevisionHistorica.name());
        } else if (evaluacion.getIntentosFallidos().intValue() > limiteOportunidades || evaluacion.getIntentosIdentificados().intValue() > limiteOportunidades) {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.REQUIERE_REVISION);
            evaluacion.setRequiereEscalamiento(Boolean.TRUE);
        } else if (evaluacion.getIntentosFallidos().intValue() == limiteOportunidades) {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.AGOTADA);
            evaluacion.setLimiteAgotado(Boolean.TRUE); evaluacion.setRequiereEscalamiento(Boolean.TRUE);
        } else if (evaluacion.getIntentosFallidos().intValue() == limiteOportunidades - 1) {
            evaluacion.setEstadoLimite(enCurso ? EstadoLimiteIntentosV2.ULTIMA_EN_CURSO : EstadoLimiteIntentosV2.ULTIMA_DISPONIBLE);
            evaluacion.setUltimaOportunidad(Boolean.TRUE);
            evaluacion.setAviso(enCurso ? "Estas cursando la ultima oportunidad aplicable."
                    : "Queda una ultima oportunidad aplicable, sujeta a oferta y condiciones.");
        } else {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.DISPONIBLE);
        }
        unidad.setEstadoOportunidad(evaluacion.getEstadoLimite().name());
        unidad.setAvisoUltimaOportunidad(evaluacion.getUltimaOportunidad());
        return evaluacion;
    }

    private String llaveIntento(IntentoAcreditacionUnidadV2DTO intento) {
        if (intento.getIdEjecucionTecnico() != null && !intento.getIdEjecucionTecnico().trim().isEmpty()) return intento.getIdEjecucionTecnico();
        if (intento.getIdParticipacion() != null) return "PARTICIPACION:" + intento.getIdParticipacion();
        return null;
    }

    /** RN-1.5 usa un límite global de mejoras, independiente de RN-1.4. */
    private void evaluarMejoraPromedio(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        Integer utilizadas = obtenerOportunidadesMejora(contexto, unidades);
        resultado.setOportunidadesMejoraUtilizadas(utilizadas);
        resultado.setOportunidadesMejoraDisponibles(utilizadas == null ? null : Integer.valueOf(Math.max(0, 3 - utilizadas.intValue())));
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            EvaluacionMejoraPromedioUnidadV2DTO evaluacion = evaluarMejoraUnidad(unidad, contexto, resultado);
            resultado.getEvaluacionesMejoraPromedio().add(evaluacion);
            CodigoMotivoCurricularV2 motivo = motivoMejora(evaluacion.getEstado());
            TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_1_5, motivo, 40,
                    "MEJORA_PROMEDIO_" + evaluacion.getEstado(),
                    "UD=" + evaluacion.getIdPrograma() + "; oportunidadesUsadas=" + utilizadas
                            + "; disponibles=" + resultado.getOportunidadesMejoraDisponibles()
                            + "; calificacionFavorable=" + evaluacion.getCalificacionFavorable()
                            + "; prioridad=" + evaluacion.getPrioridad() + "; motivos=" + evaluacion.getMotivos()
                            + "; evidencia=" + evaluacion.getEvidencias());
            resultado.getTrazas().add(traza);
            agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_1_5);
            DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, unidad.getIdPrograma());
            if (decision != null) decision.getTrazas().add(traza);
        }
    }

    private Integer obtenerOportunidadesMejora(ContextoEvaluacionCurricularV2DTO contexto,
            List<UnidadEvaluacionCurricularV2DTO> unidades) {
        if (contexto.getOportunidadesMejoraUtilizadas() != null) return contexto.getOportunidadesMejoraUtilizadas();
        Set<String> mejoras = new HashSet<String>();
        boolean identidadIncompleta = false;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) for (IntentoAcreditacionUnidadV2DTO intento : unidad.getIntentosAcreditacion()) {
            if (!Boolean.TRUE.equals(intento.getRecursamientoMejora())) continue;
            String llave = llaveIntento(intento);
            if (llave == null) identidadIncompleta = true; else mejoras.add(llave);
        }
        return identidadIncompleta ? null : Integer.valueOf(mejoras.size());
    }

    private EvaluacionMejoraPromedioUnidadV2DTO evaluarMejoraUnidad(UnidadEvaluacionCurricularV2DTO unidad,
            ContextoEvaluacionCurricularV2DTO contexto, ResultadoEvaluacionCurricularV2DTO resultado) {
        EvaluacionMejoraPromedioUnidadV2DTO evaluacion = new EvaluacionMejoraPromedioUnidadV2DTO();
        evaluacion.setIdPrograma(unidad.getIdPrograma()); evaluacion.setClave(unidad.getClave());
        evaluacion.setOportunidadesMejoraUtilizadas(resultado.getOportunidadesMejoraUtilizadas());
        evaluacion.setOportunidadesMejoraDisponibles(resultado.getOportunidadesMejoraDisponibles());
        evaluacion.setOfertada(unidad.getOfertada()); evaluacion.setCupoDisponible(unidad.getCupoDisponibleConocido());
        for (IntentoAcreditacionUnidadV2DTO intento : unidad.getIntentosAcreditacion()) {
            if (intento.getCalificacionFinal() == null) continue;
            if (evaluacion.getCalificacionFavorable() == null || intento.getCalificacionFinal().doubleValue() > evaluacion.getCalificacionFavorable().doubleValue())
                evaluacion.setCalificacionFavorable(intento.getCalificacionFinal());
            evaluacion.getEvidencias().add("CALIFICACION_HISTORICA:" + intento.getCalificacionFinal());
        }
        if (unidad.getEstado() != EstadoUnidadCurricularV2.ACREDITADA) {
            evaluacion.setEstado(EstadoMejoraPromedioV2.NO_ELEGIBLE); evaluacion.getMotivos().add("UD_NO_ACREDITADA");
        } else {
            // RN-1.5 definitivo: una UD acreditada no se reinscribe sólo para mejorar promedio.
            evaluacion.setEstado(EstadoMejoraPromedioV2.NO_ELEGIBLE);
            evaluacion.getMotivos().add("RN15_UD_ACREDITADA_NO_REINSCRIBIBLE_POR_MEJORA_PROMEDIO");
        }
        return evaluacion;
    }

    private CodigoMotivoCurricularV2 motivoMejora(EstadoMejoraPromedioV2 estado) {
        if (estado == EstadoMejoraPromedioV2.SIN_CUPO) return CodigoMotivoCurricularV2.SIN_CUPO;
        if (estado == EstadoMejoraPromedioV2.EGRESO_INICIADO) return CodigoMotivoCurricularV2.EGRESO_INICIADO;
        if (estado == EstadoMejoraPromedioV2.ESTUDIANTE_NO_ACTIVO) return CodigoMotivoCurricularV2.ESTUDIANTE_NO_ACTIVO;
        if (estado == EstadoMejoraPromedioV2.LIMITE_MEJORA_AGOTADO) return CodigoMotivoCurricularV2.LIMITE_MEJORA_PROMEDIO;
        return estado == EstadoMejoraPromedioV2.REQUIERE_REVISION ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null;
    }

    /**
     * RN-1.1: esta clasificación es independiente de la situación académica.
     * Sólo utiliza evidencia explícita del contexto; una ausencia de inscripción
     * o una baja parcial no se convierten en inactividad por inferencia.
     */
    private void evaluarActividadAcademica(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto) {
        EstadoActividadAcademicaV2 estado;
        String codigoResultado;
        String detalle;
        CodigoMotivoCurricularV2 motivo = null;

        if (Boolean.TRUE.equals(contexto.getDatosActividadContradictorios())) {
            estado = EstadoActividadAcademicaV2.NO_DETERMINADO;
            codigoResultado = "DATOS_ACTIVIDAD_CONTRADICTORIOS";
            detalle = "Las evidencias de actividad son contradictorias; V2 no infiere una baja, reincorporación ni inactividad.";
            motivo = CodigoMotivoCurricularV2.DATOS_INSUFICIENTES;
        } else if (Boolean.TRUE.equals(contexto.getBajaDefinitivaRegistrada())) {
            estado = EstadoActividadAcademicaV2.INACTIVO;
            codigoResultado = "BAJA_DEFINITIVA_REGISTRADA";
            detalle = "Existe evidencia explícita de baja definitiva; la evaluación sólo la registra sin modificar estado productivo.";
            motivo = CodigoMotivoCurricularV2.INACTIVIDAD;
        } else if (Boolean.TRUE.equals(contexto.getBajaTemporalTotalRegistrada())) {
            estado = resolverBajaTemporal(contexto);
            codigoResultado = "BAJA_TEMPORAL_" + estado.name();
            detalle = detalleActividad(contexto, estado);
            motivo = estado == EstadoActividadAcademicaV2.NO_DETERMINADO
                    ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : CodigoMotivoCurricularV2.INACTIVIDAD;
        } else if (Boolean.TRUE.equals(contexto.getReincorporacionRegistrada())
                || Boolean.TRUE.equals(contexto.getActividadAcademicaComprobada())) {
            estado = EstadoActividadAcademicaV2.ACTIVO;
            codigoResultado = Boolean.TRUE.equals(contexto.getReincorporacionRegistrada())
                    ? "REINCORPORACION_REGISTRADA" : "ACTIVIDAD_ACADEMICA_COMPROBADA";
            detalle = "La actividad académica se respalda con evidencia explícita del contexto.";
        } else {
            estado = EstadoActividadAcademicaV2.NO_DETERMINADO;
            codigoResultado = Boolean.TRUE.equals(contexto.getBajaParcialRegistrada())
                    ? "BAJA_PARCIAL_NO_EQUIVALE_A_INACTIVIDAD" : "EVIDENCIA_ACTIVIDAD_INSUFICIENTE";
            detalle = Boolean.TRUE.equals(contexto.getBajaParcialRegistrada())
                    ? "Una baja parcial afecta UD específicas y no acredita inactividad total."
                    : "No existe evidencia suficiente para clasificar actividad, baja temporal total o reincorporación.";
            motivo = CodigoMotivoCurricularV2.DATOS_INSUFICIENTES;
        }

        resultado.setEstadoActividadAcademica(estado);
        if (estado != EstadoActividadAcademicaV2.ACTIVO && estado != EstadoActividadAcademicaV2.NO_DETERMINADO) {
            agregarUnico(resultado.getCondicionesDetectadas(), CondicionCurricularV2.INACTIVIDAD_ACADEMICA);
            agregarUnico(resultado.getRestricciones(), CodigoMotivoCurricularV2.INACTIVIDAD);
        }
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_1_1, motivo, 65,
                codigoResultado, detalle + " Evidencia=" + contexto.getEvidenciasActividad()
                        + "; periodos=" + contexto.getPeriodosActividadConsiderados());
    }

    private EstadoActividadAcademicaV2 resolverBajaTemporal(ContextoEvaluacionCurricularV2DTO contexto) {
        if (Boolean.TRUE.equals(contexto.getLimiteInactividadAlcanzado()))
            return EstadoActividadAcademicaV2.LIMITE_INACTIVIDAD_ALCANZADO;
        if (Boolean.TRUE.equals(contexto.getReincorporacionRegistrada())) return EstadoActividadAcademicaV2.ACTIVO;
        Integer autorizados = contexto.getPeriodosBajaTemporalAutorizados();
        Integer transcurridos = contexto.getPeriodosBajaTemporalTranscurridos();
        if (autorizados == null || transcurridos == null || autorizados.intValue() < 0 || transcurridos.intValue() < 0)
            return EstadoActividadAcademicaV2.NO_DETERMINADO;
        return transcurridos.intValue() < autorizados.intValue()
                ? EstadoActividadAcademicaV2.BAJA_TEMPORAL_VIGENTE
                : EstadoActividadAcademicaV2.REINCORPORACION_PENDIENTE;
    }

    private String detalleActividad(ContextoEvaluacionCurricularV2DTO contexto, EstadoActividadAcademicaV2 estado) {
        if (estado == EstadoActividadAcademicaV2.BAJA_TEMPORAL_VIGENTE)
            return "La baja temporal total está dentro de los periodos autorizados.";
        if (estado == EstadoActividadAcademicaV2.REINCORPORACION_PENDIENTE)
            return "La duración autorizada de baja temporal concluyó y no existe reincorporación registrada.";
        if (estado == EstadoActividadAcademicaV2.LIMITE_INACTIVIDAD_ALCANZADO)
            return "La fuente canónica reporta que se alcanzó el límite institucional de inactividad.";
        return "La baja temporal total carece de duración autorizada o transcurrida verificable.";
    }

    /**
     * RN-4.1: cálculo semestral y descriptivo de carga mínima. La oferta origen
     * y las decisiones productivas nunca se sustituyen; se reutilizan las
     * decisiones sombra ya construidas por RN-2.2 y seriación.
     */
    private void evaluarCargaMinima(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaMinimaCurricularV2DTO evaluacion = new EvaluacionCargaMinimaCurricularV2DTO();
        evaluacion.setCargaMinimaNormativa(Integer.valueOf(3));
        agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_4_1);
        agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_2_2);
        agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.CTR_02);
        resultado.setEvaluacionCargaMinima(evaluacion);

        if (contexto.getSemestreReferencia() == null) {
            evaluacion.setAplicaCargaMinimaNormativa(null);
            evaluacion.setEstado(EstadoCargaMinimaCurricularV2.NO_DETERMINADO);
            agregarUnico(evaluacion.getMotivos(), CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_4_1,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 55,
                    "CARGA_MINIMA_SEMESTRE_OBJETIVO_NO_DETERMINADO",
                    "No se evalúa la carga mínima sin semestre objetivo verificable.");
            return;
        }
        if (contexto.getSemestreReferencia().intValue() <= 1) {
            evaluacion.setAplicaCargaMinimaNormativa(Boolean.FALSE);
            evaluacion.setEstado(EstadoCargaMinimaCurricularV2.NO_DETERMINADO);
            evaluacion.getEvidencias().add("PRIMER_SEMESTRE_REQUIERE_REGLA_ESPECIFICA_NUEVO_INGRESO");
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_4_1, null, 55,
                    "CARGA_MINIMA_NUEVO_INGRESO_SEPARADA",
                    "RN-4.1 no aplica automáticamente el mínimo general al primer semestre; se conserva la regla específica de nuevo ingreso.");
            return;
        }

        evaluacion.setAplicaCargaMinimaNormativa(Boolean.TRUE);
        List<UnidadEvaluacionCurricularV2DTO> obligatorias = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        List<UnidadEvaluacionCurricularV2DTO> optativas = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        boolean datosInsuficientes = false;
        boolean hayPendientes = false;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (esPendiente(unidad)) hayPendientes = true;
            String descarte = motivoDescarteCargaMinima(unidad, buscarDecision(resultado, unidad.getIdPrograma()));
            if (descarte != null) {
                evaluacion.getUdsDescartadas().add(obtenerLlaveUnidad(unidad) + ":" + descarte);
                if ("DATOS_INSUFICIENTES".equals(descarte)) datosInsuficientes = true;
                if ("SERIACION".equals(descarte)) agregarUnico(evaluacion.getMotivos(), CodigoMotivoCurricularV2.SERIACION_DIRECTA);
                if ("SIN_OFERTA".equals(descarte)) agregarUnico(evaluacion.getMotivos(), CodigoMotivoCurricularV2.SIN_OFERTA);
                if ("SIN_CUPO".equals(descarte)) agregarUnico(evaluacion.getMotivos(), CodigoMotivoCurricularV2.SIN_CUPO);
                continue;
            }
            if (esObligatoria(unidad)) obligatorias.add(unidad); else optativas.add(unidad);
        }
        registrarUnidadesCarga(evaluacion, obligatorias, true);
        registrarUnidadesCarga(evaluacion, optativas, false);
        int total = obligatorias.size() + optativas.size();
        evaluacion.setCantidadObligatoriasElegibles(Integer.valueOf(obligatorias.size()));
        evaluacion.setCantidadOptativasElegibles(Integer.valueOf(optativas.size()));
        evaluacion.setCantidadTotalElegible(Integer.valueOf(total));
        evaluacion.setCargaMinimaEvaluada(Integer.valueOf(Math.min(3, total)));
        evaluacion.getEvidencias().add("EVALUACION_SEMESTRAL;OBLIGATORIAS=" + obligatorias.size()
                + ";OPTATIVAS=" + optativas.size() + ";TOTAL=" + total);

        if (!hayPendientes) {
            evaluacion.setCargaMinimaAlcanzable(null);
            evaluacion.setEstado(EstadoCargaMinimaCurricularV2.NO_DETERMINADO);
            evaluacion.getEvidencias().add("SIN_UD_PENDIENTES_PARA_CONSTRUIR_CARGA_SOMBRA");
        } else if (datosInsuficientes && total < 3) {
            evaluacion.setCargaMinimaAlcanzable(null);
            evaluacion.setEstado(EstadoCargaMinimaCurricularV2.REQUIERE_REVISION);
            agregarUnico(evaluacion.getMotivos(), CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
        } else if (total >= 3) {
            evaluacion.setCargaMinimaAlcanzable(Boolean.TRUE);
            evaluacion.setEstado(optativas.isEmpty() ? EstadoCargaMinimaCurricularV2.CUMPLE_MINIMO
                    : EstadoCargaMinimaCurricularV2.CUMPLE_MEDIANTE_OPTATIVAS);
        } else {
            evaluacion.setCargaMinimaAlcanzable(Boolean.FALSE);
            evaluacion.setExcepcionCargaMinimaAplicable(Boolean.TRUE);
            evaluacion.setEstado(EstadoCargaMinimaCurricularV2.EXCEPCION_MINIMO_APLICABLE);
            registrarExcepcionCargaMinima(resultado, contexto, obligatorias, optativas, evaluacion);
        }
        CodigoMotivoCurricularV2 motivo = evaluacion.getExcepcionCargaMinimaAplicable()
                ? CodigoMotivoCurricularV2.CARGA_MINIMA_EXCEPCION
                : (datosInsuficientes ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_4_1, motivo, 55,
                "CARGA_MINIMA_" + evaluacion.getEstado(),
                "minimo=3; obligatorias=" + obligatorias.size() + "; optativas=" + optativas.size()
                        + "; total=" + total + "; descartadas=" + evaluacion.getUdsDescartadas()
                        + "; excepcion=" + evaluacion.getExcepcionCargaMinimaAplicable());
    }

    private String motivoDescarteCargaMinima(UnidadEvaluacionCurricularV2DTO unidad,
            DecisionUnidadCurricularV2DTO decision) {
        if (unidad.getEstado() == EstadoUnidadCurricularV2.NO_DETERMINADO) return "DATOS_INSUFICIENTES";
        if (!esPendiente(unidad)) return "NO_PENDIENTE";
        if (unidad.getOfertada() == null || unidad.getGrupoActivoConocido() == null
                || unidad.getCupoDisponibleConocido() == null) return "DATOS_INSUFICIENTES";
        if (!Boolean.TRUE.equals(unidad.getOfertada()) || !Boolean.TRUE.equals(unidad.getGrupoActivoConocido())) return "SIN_OFERTA";
        if (unidad.getCausaIndisponibilidadOferta() == CausaIndisponibilidadOfertaV2.SIN_CUPO
                || !Boolean.TRUE.equals(unidad.getCupoDisponibleConocido())) return "SIN_CUPO";
        if (Boolean.FALSE.equals(unidad.getSeleccionableOrigen())) return "RESTRICCION_ORIGEN";
        if (Boolean.TRUE.equals(unidad.getSeriacionDirectaConocida())
                || Boolean.TRUE.equals(unidad.getSeriacionIndirectaConocida())
                || Boolean.TRUE.equals(unidad.getSeriacionConocida())
                || (decision != null && (decision.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.BLOQUEADA)
                        || decision.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION)))) return "SERIACION";
        if (decision == null || !Boolean.TRUE.equals(decision.getOfertaEvaluadaV2())) return "RESTRICCION_V2";
        return null;
    }

    private void registrarUnidadesCarga(EvaluacionCargaMinimaCurricularV2DTO evaluacion,
            List<UnidadEvaluacionCurricularV2DTO> unidades, boolean obligatorias) {
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad.getIdPrograma() != null) {
                if (obligatorias) evaluacion.getUdsObligatoriasConsideradas().add(unidad.getIdPrograma());
                else evaluacion.getUdsOptativasConsideradas().add(unidad.getIdPrograma());
            }
            evaluacion.getEvidencias().addAll(unidad.getReferenciasHistoricas());
        }
    }

    private void registrarExcepcionCargaMinima(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> obligatorias,
            List<UnidadEvaluacionCurricularV2DTO> optativas, EvaluacionCargaMinimaCurricularV2DTO evaluacion) {
        registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.CARGA_MINIMA_OBLIGATORIAS_INSUFICIENTES,
                TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_4_1,
                EstadoExcepcionCurricularV2.APLICABLE, obligatorias,
                "OBLIGATORIAS_ELEGIBLES_OFERTADAS<3",
                "Las obligatorias elegibles no alcanzan la carga mínima; se consideran optativas compatibles en modo sombra.");
        List<UnidadEvaluacionCurricularV2DTO> relacionadas = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        relacionadas.addAll(obligatorias); relacionadas.addAll(optativas);
        registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.CARGA_MINIMA_OPTATIVAS_INSUFICIENTES,
                TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_4_1,
                EstadoExcepcionCurricularV2.APLICABLE, relacionadas,
                "OBLIGATORIAS_Y_OPTATIVAS_ELEGIBLES_OFERTADAS<3",
                "Incluso considerando optativas elegibles no se alcanza el mínimo; se permite una carga evaluada inferior sólo en sombra.");
        if (obligatorias.size() == 1 && relacionadas.size() == 1) {
            registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.CARGA_MINIMA_UNICA_OBLIGATORIA_PENDIENTE,
                    TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_4_1,
                    EstadoExcepcionCurricularV2.APLICABLE, obligatorias,
                    "UNICA_OBLIGATORIA_PENDIENTE_DISPONIBLE",
                    "Existe una única UD obligatoria pendiente disponible; RN-4.1 admite carga evaluada de una UD sólo en sombra.");
        }
        agregarUnico(evaluacion.getMotivos(), CodigoMotivoCurricularV2.CARGA_MINIMA_EXCEPCION);
    }

    /** RN-4.2 reutiliza RN-1.2, RN-2.2 y RN-4.1; la propuesta no altera selección ni carga. */
    private void evaluarCargaMaxima(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaMaximaCurricularV2DTO evaluacion = new EvaluacionCargaMaximaCurricularV2DTO();
        evaluacion.setSituacionAcademicaUtilizada(resultado.getSituacionAcademicaCalculada());
        agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_1_2);
        agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_2_2);
        agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_4_1);
        agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_4_2);
        resultado.setEvaluacionCargaMaxima(evaluacion);

        String situacion = resultado.getSituacionAcademicaCalculada();
        if (!"REGULAR".equals(situacion) && !"IRREGULAR".equals(situacion)) {
            evaluacion.setEstado("NO_DETERMINADA".equals(situacion)
                    ? EstadoCargaMaximaCurricularV2.REQUIERE_REVISION : EstadoCargaMaximaCurricularV2.NO_DETERMINADO);
            agregarUnico(evaluacion.getMotivos(), CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_4_2,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 55,
                    "CARGA_MAXIMA_SITUACION_NO_DETERMINADA",
                    "RN-4.2 reutiliza RN-1.2 y no infiere máximo de 8 o 6 sin situación académica determinada.");
            return;
        }

        int maximo = "REGULAR".equals(situacion) ? 8 : 6;
        evaluacion.setCargaMaximaNormativa(Integer.valueOf(maximo));
        List<UnidadEvaluacionCurricularV2DTO> prioritarias = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        List<UnidadEvaluacionCurricularV2DTO> compatibles = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        List<UnidadEvaluacionCurricularV2DTO> optativas = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            String descarte = motivoDescarteCargaMinima(unidad, buscarDecision(resultado, unidad.getIdPrograma()));
            if (descarte != null) {
                evaluacion.getUdsDescartadas().add(obtenerLlaveUnidad(unidad) + ":" + descarte);
                continue;
            }
            if (esObligatoria(unidad) && unidad.getEstado() == EstadoUnidadCurricularV2.REPROBADA) prioritarias.add(unidad);
            else if (esObligatoria(unidad)) compatibles.add(unidad);
            else optativas.add(unidad);
        }
        registrarUnidadesMaximas(evaluacion, prioritarias, "PRIORITARIA");
        evaluacion.setCantidadPrioritarias(Integer.valueOf(prioritarias.size()));
        evaluacion.setCantidadCompatibles(Integer.valueOf(compatibles.size()));
        evaluacion.setCantidadOptativas(Integer.valueOf(optativas.size()));
        int total = prioritarias.size() + compatibles.size() + optativas.size();
        evaluacion.setCargaActualEvaluada(Integer.valueOf(total));
        evaluacion.setExcedeMaximo(Boolean.valueOf(total > maximo));
        evaluacion.setCargaDisponible(Integer.valueOf(Math.max(0, maximo - total)));
        evaluacion.getEvidencias().add("SITUACION_RN_1_2=" + situacion + ";MAXIMO=" + maximo
                + ";PRIORITARIAS=" + prioritarias.size() + ";COMPATIBLES=" + compatibles.size()
                + ";OPTATIVAS=" + optativas.size());
        if (resultado.getEvaluacionCargaMinima() != null)
            evaluacion.getEvidencias().add("RN_4_1=" + resultado.getEvaluacionCargaMinima().getEstado());
        if (resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.CUATRO_O_MAS_REPROBADAS)) {
            agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_4);
            evaluacion.getEvidencias().add("RN_6_4_OBSERVACIONAL_SIN_EFECTO_PRODUCTIVO");
        }
        if (Integer.valueOf(5).equals(contexto.getSemestreReferencia())) {
            agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_1);
            evaluacion.getEvidencias().add("CTR_01_RN_6_1_PROCEDENCIA_FUTURA_SIN_ACTIVAR");
        }

        int espacios = maximo;
        espacios = incluirGrupoCargaMaxima(evaluacion, prioritarias, espacios, "PRIORITARIA");
        if (espacios >= 0) espacios = incluirGrupoCargaMaxima(evaluacion, compatibles, espacios, "COMPATIBLE");
        if (espacios >= 0) espacios = incluirGrupoCargaMaxima(evaluacion, optativas, espacios, "OPTATIVA");
        if (total < maximo) evaluacion.setEstado(EstadoCargaMaximaCurricularV2.DENTRO_DE_MAXIMO);
        else if (total == maximo) evaluacion.setEstado(EstadoCargaMaximaCurricularV2.MAXIMO_ALCANZADO);
        else if (espacios < 0) {
            evaluacion.setEstado(EstadoCargaMaximaCurricularV2.REQUIERE_REVISION);
            evaluacion.setRequiereRevisionPorEmpate(Boolean.TRUE);
            agregarUnico(evaluacion.getMotivos(), CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
        } else evaluacion.setEstado(EstadoCargaMaximaCurricularV2.EXCEDE_MAXIMO);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_4_2,
                evaluacion.getEstado() == EstadoCargaMaximaCurricularV2.REQUIERE_REVISION
                        ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                55, "CARGA_MAXIMA_" + evaluacion.getEstado(),
                "situacion=" + situacion + "; maximo=" + maximo + "; evaluada=" + total
                        + "; incluidas=" + evaluacion.getUdsIncluidasSombra()
                        + "; fuera=" + evaluacion.getUdsFueraPorMaximo()
                        + "; descartadas=" + evaluacion.getUdsDescartadas());
    }

    private void registrarUnidadesMaximas(EvaluacionCargaMaximaCurricularV2DTO evaluacion,
            List<UnidadEvaluacionCurricularV2DTO> unidades, String categoria) {
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad.getIdPrograma() != null) evaluacion.getUdsPrioritarias().add(unidad.getIdPrograma());
            evaluacion.getEvidencias().add(categoria + ":" + obtenerLlaveUnidad(unidad));
        }
    }

    /** Devuelve -1 si una frontera contiene UD de igual prioridad sin desempate aprobado. */
    private int incluirGrupoCargaMaxima(EvaluacionCargaMaximaCurricularV2DTO evaluacion,
            List<UnidadEvaluacionCurricularV2DTO> grupo, int espacios, String categoria) {
        if (grupo.isEmpty()) return espacios;
        if (espacios == 0) {
            registrarFueraPorMaximo(evaluacion, grupo, categoria + "_FUERA_POR_MAXIMO");
            return 0;
        }
        if (grupo.size() <= espacios) {
            for (UnidadEvaluacionCurricularV2DTO unidad : grupo)
                if (unidad.getIdPrograma() != null) evaluacion.getUdsIncluidasSombra().add(unidad.getIdPrograma());
            return espacios - grupo.size();
        }
        registrarFueraPorMaximo(evaluacion, grupo, categoria + "_EMPATE_SIN_CRITERIO_APROBADO");
        return -1;
    }

    private void registrarFueraPorMaximo(EvaluacionCargaMaximaCurricularV2DTO evaluacion,
            List<UnidadEvaluacionCurricularV2DTO> unidades, String motivo) {
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades)
            evaluacion.getUdsFueraPorMaximo().add(obtenerLlaveUnidad(unidad) + ":" + motivo);
    }

    /** RN-4.3 orquesta resultados ya calculados; no repite cálculos de mínimo, máximo o situación. */
    private void evaluarCargaAcademicaIntegrada(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto) {
        EvaluacionCargaAcademicaV2DTO integrada = new EvaluacionCargaAcademicaV2DTO();
        copiarResultadoCargaMinima(integrada, resultado.getEvaluacionCargaMinima());
        copiarResultadoCargaMaxima(integrada, resultado.getEvaluacionCargaMaxima());
        integrada.setSituacionAcademica(resultado.getSituacionAcademicaCalculada());
        agregarUnico(integrada.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_1_2);
        agregarUnico(integrada.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_2_2);
        agregarUnico(integrada.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_4_1);
        agregarUnico(integrada.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_4_2);
        agregarUnico(integrada.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_4_3);
        resultado.setEvaluacionCargaAcademica(integrada);

        boolean seriacionIndeterminada = !Boolean.TRUE.equals(contexto.getSeriacionComprobada());
        if (seriacionIndeterminada) {
            integrada.setRequiereRevision(Boolean.TRUE);
            agregarUnico(integrada.getCausasExclusion(), CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
            integrada.getEvidencias().add("SERIACION_NO_COMPROBADA");
        }
        Boolean ctr01 = procedenciaCtr01(contexto, normalizarUnidades(contexto.getUnidades()));
        integrada.setCtr01Rn61Procedente(ctr01);
        if (Boolean.TRUE.equals(ctr01)) {
            agregarUnico(integrada.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_1);
            integrada.getEvidencias().add("CTR_01_RN_6_1_PROCEDENTE_SIN_ACTIVAR");
        } else if (tieneCandidataQuinto(contexto, normalizarUnidades(contexto.getUnidades())) && ctr01 == null) {
            integrada.getEvidencias().add("CTR_01_NO_DETERMINADO_POR_PERIODO_NORMATIVO");
        }
        boolean rn64 = resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.CUATRO_O_MAS_REPROBADAS);
        integrada.setRn64AplicableEnFuturaFase(Boolean.valueOf(rn64));
        if (rn64) {
            agregarUnico(integrada.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_4);
            integrada.getEvidencias().add("RN_6_4_APLICABLE_EN_FUTURA_FASE_SIN_EFECTO_ACTUAL");
        }
        for (ExcepcionCurricularV2DTO excepcion : resultado.getExcepcionesEvaluadas()) {
            integrada.getEvidencias().add("EXCEPCION:" + excepcion.getCodigo() + ":" + excepcion.getEstado());
            if (excepcion.getTipo() == TipoExcepcionCurricularV2.EXTRAORDINARIA
                    || excepcion.getEstado() == EstadoExcepcionCurricularV2.REQUIERE_REVISION) {
                integrada.setRequiereRevision(Boolean.TRUE);
            }
        }

        if (integrada.getCargaMinimaNormativa() != null && integrada.getCargaMaximaNormativa() != null
                && integrada.getCargaMinimaNormativa().intValue() > integrada.getCargaMaximaNormativa().intValue()) {
            integrada.setRequiereRevision(Boolean.TRUE);
            integrada.getEvidencias().add("INVARIANTE_MINIMO_MAYOR_QUE_MAXIMO");
        }
        if (integrada.getCargaMinimaNormativa() == null || integrada.getCargaMaximaNormativa() == null) {
            integrada.setEstado("NO_DETERMINADA".equals(integrada.getSituacionAcademica())
                    ? EstadoCargaAcademicaV2.REQUIERE_REVISION : EstadoCargaAcademicaV2.NO_DETERMINADA);
            integrada.setRequiereRevision(Boolean.TRUE);
        } else if (Boolean.TRUE.equals(integrada.getRequiereRevision())) {
            integrada.setEstado(EstadoCargaAcademicaV2.REQUIERE_REVISION);
        } else if (Boolean.FALSE.equals(integrada.getMaximoCumplido())) {
            integrada.setEstado(EstadoCargaAcademicaV2.RESTRINGIDA);
        } else if (Boolean.TRUE.equals(integrada.getExcepcionMinimoAplicable())) {
            integrada.setEstado(EstadoCargaAcademicaV2.VALIDA_CON_EXCEPCION_MINIMO);
        } else if (Boolean.TRUE.equals(integrada.getMinimoCumplido())) {
            integrada.setEstado(EstadoCargaAcademicaV2.VALIDA);
        } else if (contieneCausa(integrada.getUdsExcluidas(), "SIN_CUPO")) {
            integrada.setEstado(EstadoCargaAcademicaV2.INCOMPLETA_POR_CUPO);
        } else if (contieneCausa(integrada.getUdsExcluidas(), "SIN_OFERTA")) {
            integrada.setEstado(EstadoCargaAcademicaV2.INCOMPLETA_POR_OFERTA);
        } else {
            integrada.setEstado(EstadoCargaAcademicaV2.RESTRINGIDA);
        }
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_4_3,
                integrada.getEstado() == EstadoCargaAcademicaV2.REQUIERE_REVISION
                        ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                55, "CARGA_ACADEMICA_" + integrada.getEstado(),
                "situacion=" + integrada.getSituacionAcademica() + "; minimo=" + integrada.getCargaMinimaNormativa()
                        + "; maximo=" + integrada.getCargaMaximaNormativa() + "; carga=" + integrada.getCargaEvaluada()
                        + "; excepcionMinimo=" + integrada.getExcepcionMinimoAplicable()
                        + "; rn64=" + rn64 + "; ctr01=" + ctr01
                        + "; excluidas=" + integrada.getUdsExcluidas());
    }

    private void copiarResultadoCargaMinima(EvaluacionCargaAcademicaV2DTO integrada,
            EvaluacionCargaMinimaCurricularV2DTO minima) {
        if (minima == null) return;
        integrada.setCargaMinimaNormativa(minima.getCargaMinimaNormativa());
        integrada.setMinimoCumplido(Boolean.valueOf(minima.getEstado() == EstadoCargaMinimaCurricularV2.CUMPLE_MINIMO
                || minima.getEstado() == EstadoCargaMinimaCurricularV2.CUMPLE_MEDIANTE_OPTATIVAS));
        integrada.setExcepcionMinimoAplicable(minima.getExcepcionCargaMinimaAplicable());
        integrada.getUdsExcluidas().addAll(minima.getUdsDescartadas());
        integrada.getEvidencias().addAll(minima.getEvidencias());
        integrada.getReglasRelacionadas().addAll(minima.getReglasRelacionadas());
        for (CodigoMotivoCurricularV2 motivo : minima.getMotivos()) agregarUnico(integrada.getCausasExclusion(), motivo);
    }

    private void copiarResultadoCargaMaxima(EvaluacionCargaAcademicaV2DTO integrada,
            EvaluacionCargaMaximaCurricularV2DTO maxima) {
        if (maxima == null) return;
        integrada.setCargaMaximaNormativa(maxima.getCargaMaximaNormativa());
        integrada.setCargaEvaluada(maxima.getCargaActualEvaluada());
        integrada.setMaximoCumplido(maxima.getExcedeMaximo() == null ? null : Boolean.valueOf(!maxima.getExcedeMaximo().booleanValue()));
        integrada.getUdsPrioritarias().addAll(maxima.getUdsPrioritarias());
        integrada.getUdsIncluidasSombra().addAll(maxima.getUdsIncluidasSombra());
        integrada.getUdsExcluidas().addAll(maxima.getUdsFueraPorMaximo());
        integrada.getUdsExcluidas().addAll(maxima.getUdsDescartadas());
        integrada.getEvidencias().addAll(maxima.getEvidencias());
        integrada.getReglasRelacionadas().addAll(maxima.getReglasRelacionadas());
        for (CodigoMotivoCurricularV2 motivo : maxima.getMotivos()) agregarUnico(integrada.getCausasExclusion(), motivo);
        if (maxima.getEstado() == EstadoCargaMaximaCurricularV2.REQUIERE_REVISION
                || Boolean.TRUE.equals(maxima.getRequiereRevisionPorEmpate())) integrada.setRequiereRevision(Boolean.TRUE);
    }

    /**
     * RN-6.3 en el alcance V2: describe la prioridad académica de regularización.
     * No realiza reinscripción, no reserva cupo y no altera selección, oferta ni carga origen.
     */
    private void evaluarPrioridadRegularizacion(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO ruta = resultado.getEvaluacionCargaAcademica();
        if (ruta == null) return;
        agregarUnico(ruta.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_3);
        String situacion = resultado.getSituacionAcademicaCalculada();
        if (!"IRREGULAR".equals(situacion)) {
            ruta.setAplicaRn63(Boolean.FALSE);
            if ("NO_DETERMINADA".equals(situacion)) {
                ruta.setRequiereRevisionRegularizacion(Boolean.TRUE);
                ruta.setRequiereRevision(Boolean.TRUE);
                agregarUnico(ruta.getCausasExclusion(), CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
                registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_3,
                        CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 75,
                        "REGULARIZACION_REQUIERE_REVISION",
                        "RN-6.3 no infiere ruta sin situación académica determinada por RN-1.2.");
            } else {
                registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_3, null, 75,
                        "REGULARIZACION_NO_APLICA",
                        "RN-6.3 no construye ruta de regularización para situación académica REGULAR.");
            }
            return;
        }

        ruta.setAplicaRn63(Boolean.TRUE);
        List<UnidadEvaluacionCurricularV2DTO> prioritariasOfertadas = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        List<UnidadEvaluacionCurricularV2DTO> obligatoriasCompatibles = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        List<UnidadEvaluacionCurricularV2DTO> optativasCompatibles = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, unidad.getIdPrograma());
            String indisponibilidad = motivoDescarteCargaMinima(unidad, decision);
            if (esReprobacionVigentePrevia(unidad)) {
                agregarIdUnico(ruta.getUdsReprobadasPrioritarias(), unidad.getIdPrograma());
                marcarPrioridadRegularizacion(resultado, contexto, decision, unidad);
                if (indisponibilidad == null) {
                    prioritariasOfertadas.add(unidad);
                    agregarIdUnico(ruta.getUdsReprobadasOfertadas(), unidad.getIdPrograma());
                } else {
                    agregarIdUnico(ruta.getUdsReprobadasNoDisponibles(), unidad.getIdPrograma());
                    ruta.getCausasIndisponibilidadRegularizacion().add(obtenerLlaveUnidad(unidad) + ":" + indisponibilidad);
                    ruta.getUdsExcluidas().add(obtenerLlaveUnidad(unidad) + ":" + indisponibilidad);
                    marcarNoDisponibleRegularizacion(resultado, contexto, decision, unidad, indisponibilidad);
                }
            } else if (indisponibilidad == null && esObligatoria(unidad)) {
                obligatoriasCompatibles.add(unidad);
                agregarIdUnico(ruta.getUdsObligatoriasCompatibles(), unidad.getIdPrograma());
                registrarCompatibilidadRegularizacion(resultado, contexto, decision, unidad, "OBLIGATORIA_COMPATIBLE");
            } else if (indisponibilidad == null) {
                optativasCompatibles.add(unidad);
                agregarIdUnico(ruta.getUdsOptativasCompatibles(), unidad.getIdPrograma());
                registrarCompatibilidadRegularizacion(resultado, contexto, decision, unidad, "OPTATIVA_COMPLEMENTARIA");
            }
        }

        Integer maximo = ruta.getCargaMaximaNormativa();
        if (maximo == null) {
            ruta.setRequiereRevisionRegularizacion(Boolean.TRUE);
            ruta.setRequiereRevision(Boolean.TRUE);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_3,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 75,
                    "REGULARIZACION_SIN_MAXIMO_DETERMINABLE",
                    "RN-4.2 no determinó carga máxima; no se propone orden de inclusión para RN-6.3.");
            return;
        }
        int espacios = maximo.intValue();
        espacios = evaluarNivelRegularizacion(ruta, prioritariasOfertadas, espacios, "REPROBADA_PRIORITARIA");
        if (espacios >= 0) espacios = evaluarNivelRegularizacion(ruta, obligatoriasCompatibles, espacios, "OBLIGATORIA_COMPATIBLE");
        if (espacios >= 0) espacios = evaluarNivelRegularizacion(ruta, optativasCompatibles, espacios, "OPTATIVA_COMPLEMENTARIA");
        ruta.setCargaDisponibleRegularizacion(Integer.valueOf(Math.max(0, espacios)));
        if (espacios < 0) {
            ruta.setRequiereRevisionRegularizacion(Boolean.TRUE);
            ruta.setRequiereRevision(Boolean.TRUE);
        }
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_3,
                ruta.getRequiereRevisionRegularizacion() ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                75, ruta.getRequiereRevisionRegularizacion()
                        ? "REGULARIZACION_REQUIERE_REVISION" : "REGULARIZACION_PRIORIZADA_SOMBRA",
                "situacion=" + situacion + "; reprobadasPrioritarias=" + ruta.getUdsReprobadasPrioritarias()
                        + "; ofertadas=" + ruta.getUdsReprobadasOfertadas()
                        + "; noDisponibles=" + ruta.getCausasIndisponibilidadRegularizacion()
                        + "; obligatoriasCompatibles=" + ruta.getUdsObligatoriasCompatibles()
                        + "; optativasCompatibles=" + ruta.getUdsOptativasCompatibles()
                        + "; cargaDisponible=" + ruta.getCargaDisponibleRegularizacion());
    }

    /** RN-5.1 consume la situación, ruta y carga ya evaluadas; no las recalcula. */
    private void evaluarRezagoLeve(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto) {
        EvaluacionCargaAcademicaV2DTO evaluacion = resultado.getEvaluacionCargaAcademica();
        if (evaluacion == null) return;
        agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_5_1);
        Integer reprobadas = resultado.getCantidadReprobadasVigentes();
        String situacion = resultado.getSituacionAcademicaCalculada();
        if (reprobadas == null || "NO_DETERMINADA".equals(situacion)) {
            evaluacion.setAplicaRn51(Boolean.FALSE);
            evaluacion.setCondicionRegularizacion("NO_DETERMINADA");
            evaluacion.setRequiereRevision(Boolean.TRUE);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_5_1,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 70,
                    "REZAGO_LEVE_REQUIERE_REVISION",
                    "RN-5.1 no infiere rezago leve sin situación o cantidad de reprobadas determinada por RN-1.2.");
            return;
        }
        boolean aplica = "IRREGULAR".equals(situacion) && reprobadas.intValue() >= 1 && reprobadas.intValue() <= 3;
        evaluacion.setAplicaRn51(Boolean.valueOf(aplica));
        if (!aplica) {
            evaluacion.setCondicionRegularizacion("NO_APLICA");
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_5_1, null, 70,
                    "REZAGO_LEVE_NO_APLICA",
                    "RN-5.1 requiere situación IRREGULAR y de una a tres reprobadas vigentes; situación="
                            + situacion + "; reprobadas=" + reprobadas + ".");
            return;
        }

        evaluacion.setCondicionRegularizacion("REZAGO_LEVE");
        agregarUnico(resultado.getCondicionesDetectadas(), CondicionCurricularV2.REZAGO_LEVE);
        boolean maximoConsistente = Integer.valueOf(6).equals(evaluacion.getCargaMaximaNormativa());
        boolean rutaConsistente = Boolean.TRUE.equals(evaluacion.getAplicaRn63());
        if (!maximoConsistente || !rutaConsistente) evaluacion.setRequiereRevision(Boolean.TRUE);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_5_1,
                (!maximoConsistente || !rutaConsistente) ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                70, (!maximoConsistente || !rutaConsistente)
                        ? "REZAGO_LEVE_REQUIERE_REVISION" : "REZAGO_LEVE_APLICA_SOMBRA",
                "situacionRN12=" + situacion + "; reprobadas=" + reprobadas
                        + "; maximoRN42=" + evaluacion.getCargaMaximaNormativa()
                        + "; rutaRN63=" + evaluacion.getAplicaRn63()
                        + "; prioritarias=" + evaluacion.getUdsReprobadasPrioritarias()
                        + "; noDisponibles=" + evaluacion.getCausasIndisponibilidadRegularizacion()
                        + "; rn64Observacional=" + evaluacion.getRn64AplicableEnFuturaFase()
                        + "; ctr01Observacional=" + evaluacion.getCtr01Rn61Procedente());
    }

    /**
     * RN-6.4 aplica una restricción sólo sobre la propuesta V2: no modifica
     * oferta, selección, carga o avance del proceso productivo vigente.
     */
    private void evaluarRezagoSevero(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO evaluacion = resultado.getEvaluacionCargaAcademica();
        if (evaluacion == null) return;
        agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_4);
        Integer reprobadas = resultado.getCantidadReprobadasVigentes();
        String situacion = resultado.getSituacionAcademicaCalculada();
        if (reprobadas == null || "NO_DETERMINADA".equals(situacion)) {
            evaluacion.setAplicaRn64(Boolean.FALSE);
            evaluacion.setRequiereRevision(Boolean.TRUE);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_4,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 100,
                    "REZAGO_SEVERO_REQUIERE_REVISION",
                    "RN-6.4 no se activa ni descarta sin total confiable de reprobadas vigentes.");
            return;
        }
        if (reprobadas.intValue() < 4) {
            evaluacion.setAplicaRn64(Boolean.FALSE);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_4, null, 100,
                    "REZAGO_SEVERO_NO_APLICA",
                    "RN-6.4 requiere cuatro o más reprobadas vigentes; total=" + reprobadas + ".");
            return;
        }

        evaluacion.setAplicaRn64(Boolean.TRUE);
        evaluacion.setRn64AplicableEnFuturaFase(Boolean.TRUE);
        evaluacion.setCondicionRegularizacion("REZAGO_SEVERO");
        evaluacion.setAplicaRn51(Boolean.FALSE);
        agregarUnico(resultado.getCondicionesDetectadas(), CondicionCurricularV2.REZAGO_SEVERO);
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (esReprobacionVigentePrevia(unidad) || !esObligatoria(unidad) || !esPendiente(unidad)) continue;
            agregarIdUnico(evaluacion.getUdsObligatoriasNuevasBloqueadasRn64(), unidad.getIdPrograma());
            evaluacion.getUdsObligatoriasCompatibles().remove(unidad.getIdPrograma());
            evaluacion.getUdsIncluidasSombra().remove(unidad.getIdPrograma());
            evaluacion.getUdsExcluidas().add(obtenerLlaveUnidad(unidad) + ":RN_6_4_REZAGO_SEVERO");
            bloquearObligatoriaNuevaRn64(resultado, contexto, buscarDecision(resultado, unidad.getIdPrograma()), unidad);
        }

        registrarOptativasAdicionalesRn64(resultado, evaluacion, unidades);
        if (Boolean.TRUE.equals(evaluacion.getCtr01Rn61Procedente()) && Boolean.TRUE.equals(contexto.getAvanceCondicionado())) {
            resultado.setAvanceCondicionado(Boolean.FALSE);
            evaluacion.setAvanceCondicionadoBloqueadoPorRn64(Boolean.TRUE);
        }
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_4,
                CodigoMotivoCurricularV2.REZAGO_SEVERO, 100,
                "REZAGO_SEVERO_APLICA_SOMBRA",
                "reprobadas=" + reprobadas + "; prioritarias=" + evaluacion.getUdsReprobadasPrioritarias()
                        + "; noDisponibles=" + evaluacion.getCausasIndisponibilidadRegularizacion()
                        + "; obligatoriasBloqueadas=" + evaluacion.getUdsObligatoriasNuevasBloqueadasRn64()
                        + "; optativasAdicionales=" + evaluacion.getUdsOptativasAdicionalesElegiblesRn64()
                        + "; maximoRN42=" + evaluacion.getCargaMaximaNormativa()
                        + "; excepcionMinimo=" + evaluacion.getExcepcionMinimoAplicable()
                        + "; ctr01=" + evaluacion.getCtr01Rn61Procedente()
                        + "; avanceBloqueado=" + evaluacion.getAvanceCondicionadoBloqueadoPorRn64());
    }

    private void registrarOptativasAdicionalesRn64(ResultadoEvaluacionCurricularV2DTO resultado,
            EvaluacionCargaAcademicaV2DTO evaluacion, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        Integer maximo = evaluacion.getCargaMaximaNormativa();
        if (maximo == null) {
            evaluacion.setRequiereRevision(Boolean.TRUE);
            return;
        }
        int espacios = maximo.intValue() - evaluacion.getUdsReprobadasOfertadas().size();
        List<UnidadEvaluacionCurricularV2DTO> optativas = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (esObligatoria(unidad) || esReprobacionVigentePrevia(unidad)) continue;
            if (motivoDescarteCargaMinima(unidad, buscarDecision(resultado, unidad.getIdPrograma())) == null) optativas.add(unidad);
        }
        if (optativas.size() > espacios) {
            evaluacion.setRequiereRevision(Boolean.TRUE);
            for (UnidadEvaluacionCurricularV2DTO unidad : optativas)
                agregarIdUnico(evaluacion.getUdsEmpatadasRegularizacion(), unidad.getIdPrograma());
            return;
        }
        for (UnidadEvaluacionCurricularV2DTO unidad : optativas)
            agregarIdUnico(evaluacion.getUdsOptativasAdicionalesElegiblesRn64(), unidad.getIdPrograma());
    }

    private void bloquearObligatoriaNuevaRn64(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, DecisionUnidadCurricularV2DTO decision,
            UnidadEvaluacionCurricularV2DTO unidad) {
        if (decision == null) return;
        agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.BLOQUEADA);
        agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.REZAGO_SEVERO);
        decision.setOfertaEvaluadaV2(Boolean.FALSE);
        agregarExplicacion(decision, "RN-6.4 bloquea la obligatoria nueva sólo dentro de la propuesta sombra.");
        TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_6_4,
                CodigoMotivoCurricularV2.REZAGO_SEVERO, 100, "OBLIGATORIA_NUEVA_BLOQUEADA_RN64",
                "La UD " + obtenerLlaveUnidad(unidad) + " no es reprobada vigente y queda fuera de la propuesta RN-6.4.");
        decision.getTrazas().add(traza);
        resultado.getTrazas().add(traza);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_6_4);
        actualizarPrioridad(resultado, 100);
    }

    /**
     * RN-5.2 clasifica y valida la composición severa ya resultante de RN-6.4,
     * RN-6.3 y RN-4.3. No recalcula bloqueo, prioridad, carga ni umbral.
     */
    private void evaluarCargaRezagoSevero(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto) {
        EvaluacionCargaAcademicaV2DTO evaluacion = resultado.getEvaluacionCargaAcademica();
        if (evaluacion == null) return;
        agregarUnico(evaluacion.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_5_2);
        Integer reprobadas = resultado.getCantidadReprobadasVigentes();
        String situacion = resultado.getSituacionAcademicaCalculada();
        if (reprobadas == null || "NO_DETERMINADA".equals(situacion)) {
            evaluacion.setAplicaRn52(Boolean.FALSE);
            evaluacion.setRequiereRevision(Boolean.TRUE);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_5_2,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 95,
                    "CARGA_REZAGO_SEVERO_REQUIERE_REVISION",
                    "RN-5.2 no infiere rezago severo sin situación y total de reprobadas confiables.");
            return;
        }
        boolean aplica = "IRREGULAR".equals(situacion) && reprobadas.intValue() >= 4;
        evaluacion.setAplicaRn52(Boolean.valueOf(aplica));
        if (!aplica) {
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_5_2, null, 95,
                    "CARGA_REZAGO_SEVERO_NO_APLICA",
                    "RN-5.2 requiere situación IRREGULAR y cuatro o más reprobadas; situación="
                            + situacion + "; reprobadas=" + reprobadas + ".");
            return;
        }

        boolean rn64Activo = Boolean.TRUE.equals(evaluacion.getAplicaRn64());
        boolean rutaConsistente = Boolean.TRUE.equals(evaluacion.getAplicaRn63());
        boolean maximoConsistente = Integer.valueOf(6).equals(evaluacion.getCargaMaximaNormativa());
        boolean condicionConsistente = "REZAGO_SEVERO".equals(evaluacion.getCondicionRegularizacion());
        boolean consistente = rn64Activo && rutaConsistente && maximoConsistente && condicionConsistente;
        if (!consistente) evaluacion.setRequiereRevision(Boolean.TRUE);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_5_2,
                consistente ? null : CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 95,
                consistente ? "CARGA_REZAGO_SEVERO_APLICA_SOMBRA" : "CARGA_REZAGO_SEVERO_INCONSISTENTE",
                "situacionRN12=" + situacion + "; reprobadas=" + reprobadas
                        + "; rn64=" + evaluacion.getAplicaRn64()
                        + "; rutaRN63=" + evaluacion.getAplicaRn63()
                        + "; maximoRN42=" + evaluacion.getCargaMaximaNormativa()
                        + "; cargaRN43=" + evaluacion.getCargaEvaluada()
                        + "; prioritarias=" + evaluacion.getUdsReprobadasPrioritarias()
                        + "; noDisponibles=" + evaluacion.getCausasIndisponibilidadRegularizacion()
                        + "; bloqueadas=" + evaluacion.getUdsObligatoriasNuevasBloqueadasRn64()
                        + "; optativas=" + evaluacion.getUdsOptativasAdicionalesElegiblesRn64()
                        + "; excepcionMinimo=" + evaluacion.getExcepcionMinimoAplicable()
                        + "; ctr01=" + evaluacion.getCtr01Rn61Procedente()
                        + "; prevalenciaRn64=" + evaluacion.getAvanceCondicionadoBloqueadoPorRn64());
    }

    /** Devuelve -1 sólo cuando el límite corta un conjunto empatado sin criterio normativo adicional. */
    private int evaluarNivelRegularizacion(EvaluacionCargaAcademicaV2DTO ruta,
            List<UnidadEvaluacionCurricularV2DTO> candidatas, int espacios, String nivel) {
        if (candidatas.isEmpty()) return espacios;
        if (candidatas.size() > espacios) {
            ruta.setRequiereRevisionRegularizacion(Boolean.TRUE);
            for (UnidadEvaluacionCurricularV2DTO unidad : candidatas) {
                agregarIdUnico(ruta.getUdsEmpatadasRegularizacion(), unidad.getIdPrograma());
                ruta.getUdsExcluidas().add(obtenerLlaveUnidad(unidad) + ":" + nivel + "_EMPATE_SIN_CRITERIO_APROBADO");
            }
            return -1;
        }
        return espacios - candidatas.size();
    }

    private void marcarPrioridadRegularizacion(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, DecisionUnidadCurricularV2DTO decision,
            UnidadEvaluacionCurricularV2DTO unidad) {
        if (decision == null) return;
        decision.setPrioritaria(Boolean.TRUE);
        agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.PRIORITARIA);
        agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.REPROBACION_ACUMULADA);
        decision.getReferenciasFuente().addAll(unidad.getReferenciasHistoricas());
        agregarExplicacion(decision, "RN-6.3 registra prioridad académica de regularización en modo sombra.");
        registrarTrazaDecisionRegularizacion(resultado, contexto, decision, CodigoMotivoCurricularV2.REPROBACION_ACUMULADA,
                "UD_REPROBADA_PRIORITARIA", "La UD reprobada vigente se ubica en nivel 1 de regularización.");
    }

    private void marcarNoDisponibleRegularizacion(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, DecisionUnidadCurricularV2DTO decision,
            UnidadEvaluacionCurricularV2DTO unidad, String causa) {
        if (decision == null) return;
        agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION);
        decision.setHabilitada(Boolean.FALSE);
        if ("SIN_CUPO".equals(causa)) agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.SIN_CUPO);
        else if ("SERIACION".equals(causa)) agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.SERIACION_DIRECTA);
        else if ("DATOS_INSUFICIENTES".equals(causa)) agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
        else agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.SIN_OFERTA);
        agregarExplicacion(decision, "RN-6.3 conserva prioridad, pero registra indisponibilidad: " + causa + ".");
        CodigoMotivoCurricularV2 motivo = "SIN_CUPO".equals(causa) ? CodigoMotivoCurricularV2.SIN_CUPO
                : ("SERIACION".equals(causa) ? CodigoMotivoCurricularV2.SERIACION_DIRECTA
                : ("DATOS_INSUFICIENTES".equals(causa) ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES
                : CodigoMotivoCurricularV2.SIN_OFERTA));
        registrarTrazaDecisionRegularizacion(resultado, contexto, decision, motivo,
                "UD_REPROBADA_NO_DISPONIBLE_" + causa,
                "La UD " + obtenerLlaveUnidad(unidad) + " sigue prioritaria, pero no está disponible: " + causa + ".");
    }

    private void registrarCompatibilidadRegularizacion(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, DecisionUnidadCurricularV2DTO decision,
            UnidadEvaluacionCurricularV2DTO unidad, String clasificacion) {
        if (decision == null) return;
        agregarExplicacion(decision, "RN-6.3 clasifica la UD como " + clasificacion + " en modo sombra.");
        registrarTrazaDecisionRegularizacion(resultado, contexto, decision, null,
                "UD_" + clasificacion, "La UD " + obtenerLlaveUnidad(unidad) + " es compatible después de regularización.");
    }

    private void registrarTrazaDecisionRegularizacion(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, DecisionUnidadCurricularV2DTO decision,
            CodigoMotivoCurricularV2 motivo, String resultadoTraza, String detalle) {
        TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_6_3, motivo, 75,
                resultadoTraza, detalle);
        decision.getTrazas().add(traza);
        resultado.getTrazas().add(traza);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_6_3);
        actualizarPrioridad(resultado, 75);
    }

    /**
     * Fase 5A. Formaliza la procedencia del Control de Avance Anual y su
     * base RN-6.1.1; no evalúa ni concede avance condicionado.
     */
    private void evaluarControlAvanceAnualBase(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO control = resultado.getEvaluacionCargaAcademica();
        if (control == null) return;
        agregarUnico(control.getReglasRelacionadas(), CodigoReglaCurricularV2.CTR_01);
        agregarUnico(control.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_1);
        agregarUnico(control.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_1_1);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.CTR_01);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_6_1);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_6_1_1);

        Boolean aplica = procedenciaCtr01(contexto, unidades);
        control.setAplicaCtr01(aplica);
        control.setAplicaRn61(aplica);
        if (aplica == null) {
            control.setEstadoControlAvanceAnual(EstadoControlAvanceAnualV2.REQUIERE_REVISION);
            control.setRequiereRevision(Boolean.TRUE);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.CTR_01,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 100,
                    "CTR01_REQUIERE_REVISION",
                    "No es posible determinar la candidata de quinto o el periodo normativo para CTR-01.");
            return;
        }
        if (!aplica.booleanValue()) {
            control.setEstadoControlAvanceAnual(EstadoControlAvanceAnualV2.NO_APLICA);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.CTR_01, null, 100,
                    "CTR01_NO_APLICA",
                    "CTR-01 sólo aplica desde 2027-1 cuando se pretende habilitar una UD de quinto semestre.");
            return;
        }
        if (contexto.getIdPlan() == null) {
            control.setEstadoControlAvanceAnual(EstadoControlAvanceAnualV2.REQUIERE_REVISION);
            control.setRequiereRevision(Boolean.TRUE);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_1,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 100,
                    "BASE_RN611_PLAN_NO_DETERMINADO",
                    "No se construye la base obligatoria 1-4 sin Programa Educativo/plan técnico.");
            return;
        }

        boolean datosInsuficientes = false;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad == null) continue;
            Integer semestre = unidad.getSemestre();
            if (semestre == null) {
                if (unidad.getTipoUd() == null || esObligatoria(unidad)) datosInsuficientes = true;
                continue;
            }
            if (semestre.intValue() < 1 || semestre.intValue() > 4) continue;
            if (unidad.getTipoUd() == null) {
                datosInsuficientes = true;
                continue;
            }
            if (!esObligatoria(unidad)) continue;
            if (unidad.getIdPrograma() == null) {
                datosInsuficientes = true;
                continue;
            }
            agregarIdUnico(control.getBaseRn611(), unidad.getIdPrograma());
            if (unidad.getEstado() == EstadoUnidadCurricularV2.ACREDITADA) {
                agregarIdUnico(control.getObligatoriasAcreditadasRn611(), unidad.getIdPrograma());
            } else {
                agregarIdUnico(control.getObligatoriasPendientesRn611(), unidad.getIdPrograma());
                control.getDetallePendientesRn611().add(detallePendienteControlAvance(unidad));
                if (unidad.getEstado() == EstadoUnidadCurricularV2.NO_DETERMINADO
                        || unidad.getCausa() == CausaPendienteUnidadV2.NO_DETERMINADA) datosInsuficientes = true;
            }
        }
        control.setTotalObligatoriasSemestres1A4(Integer.valueOf(control.getBaseRn611().size()));
        control.setTodasObligatoriasPreviasAcreditadas(Boolean.valueOf(!control.getBaseRn611().isEmpty()
                && control.getObligatoriasPendientesRn611().isEmpty() && !datosInsuficientes));
        if (control.getBaseRn611().isEmpty() || datosInsuficientes) {
            control.setEstadoControlAvanceAnual(EstadoControlAvanceAnualV2.REQUIERE_REVISION);
            control.setRequiereRevision(Boolean.TRUE);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_1,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 100,
                    "BASE_RN611_REQUIERE_REVISION",
                    "Base obligatoria 1-4 incompleta o indeterminada; base=" + control.getBaseRn611()
                            + "; pendientes=" + control.getDetallePendientesRn611());
            return;
        }

        boolean rn64Activa = Boolean.TRUE.equals(control.getAplicaRn64());
        boolean restriccionConcurrente = rn64Activa || Boolean.TRUE.equals(contexto.getBajaDefinitivaRegistrada())
                || resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.SERIACION);
        if (rn64Activa || restriccionConcurrente) {
            control.setAccesoOrdinarioQuinto(Boolean.FALSE);
            // La restricción impide conceder avance, pero conserva la delegación para
            // que RN-6.1.2 pueda trazar concurrentemente por qué no cumple.
            control.setRequiereEvaluacionAvanceCondicionado(Boolean.valueOf(!control.getObligatoriasPendientesRn611().isEmpty()));
            control.setEstadoControlAvanceAnual(EstadoControlAvanceAnualV2.BLOQUEADO_POR_RESTRICCION_CONCURRENTE);
            if (rn64Activa) control.setAvanceCondicionadoBloqueadoPorRn64(Boolean.TRUE);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_1,
                    rn64Activa ? CodigoMotivoCurricularV2.REZAGO_SEVERO : CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    100, "CTR01_BLOQUEADO_RESTRICCION_CONCURRENTE",
                    "CTR-01 aplica, pero una restricción concurrente impide declarar acceso ordinario o avance condicionado."
                            + " RN64=" + rn64Activa + "; pendientes=" + control.getDetallePendientesRn611());
            return;
        }
        if (Boolean.TRUE.equals(control.getTodasObligatoriasPreviasAcreditadas())) {
            control.setAccesoOrdinarioQuinto(Boolean.TRUE);
            control.setRequiereEvaluacionAvanceCondicionado(Boolean.FALSE);
            control.setEstadoControlAvanceAnual(EstadoControlAvanceAnualV2.ACCESO_ORDINARIO_PROCEDE_EN_SOMBRA);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_1, null, 100,
                    "ACCESO_ORDINARIO_QUINTO_PROCEDE_EN_SOMBRA",
                    "Todas las UD obligatorias de semestres 1-4 están acreditadas en la base RN-6.1.1.");
        } else {
            control.setAccesoOrdinarioQuinto(Boolean.FALSE);
            control.setRequiereEvaluacionAvanceCondicionado(Boolean.TRUE);
            control.setEstadoControlAvanceAnual(EstadoControlAvanceAnualV2.REQUIERE_EVALUACION_AVANCE_CONDICIONADO);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_1, null, 100,
                    "REQUIERE_EVALUACION_AVANCE_CONDICIONADO",
                    "Existen obligatorias 1-4 pendientes; Fase 5A no decide la procedencia del avance condicionado."
                            + " pendientes=" + control.getDetallePendientesRn611());
        }
    }

    private String detallePendienteControlAvance(UnidadEvaluacionCurricularV2DTO unidad) {
        return "UD=" + obtenerLlaveUnidad(unidad) + ";semestre=" + unidad.getSemestre()
                + ";estado=" + unidad.getEstado() + ";causa=" + unidad.getCausa()
                + ";evidencia=" + unidad.getReferenciasHistoricas();
    }

    /** Fase 5B: precondiciones comunes; no tipifica ni concede avance condicionado. */
    private void evaluarCondicionesComunesAvance(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO control = resultado.getEvaluacionCargaAcademica();
        if (control == null) return;
        agregarUnico(control.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_1_2);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_6_1_2);
        if (!Boolean.TRUE.equals(control.getAplicaCtr01())
                || !Boolean.TRUE.equals(control.getRequiereEvaluacionAvanceCondicionado())) {
            control.setEstadoCondicionesComunesAvance(EstadoCondicionesComunesAvanceV2.NO_APLICA);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_2, null, 100,
                    "RN612_NO_APLICA", "RN-6.1.2 requiere CTR-01 aplicable y pendientes delegadas por RN-6.1.1.");
            return;
        }
        List<EstadoCondicionAvanceV2> estados = new ArrayList<EstadoCondicionAvanceV2>();
        boolean rn64 = Boolean.TRUE.equals(control.getAplicaRn64());
        agregarCondicionAvance(control, "A_RN64", rn64 ? EstadoCondicionAvanceV2.NO_CUMPLE : EstadoCondicionAvanceV2.CUMPLE,
                "RN-6.4=" + rn64 + "; reprobadas=" + resultado.getCantidadReprobadasVigentes(),
                control.getObligatoriasPendientesRn611());
        estados.add(rn64 ? EstadoCondicionAvanceV2.NO_CUMPLE : EstadoCondicionAvanceV2.CUMPLE);

        EstadoCondicionAvanceV2 estadoSeriacion = evaluarSeriacionQuinto(control, contexto, unidades);
        estados.add(estadoSeriacion);

        EstadoCondicionAvanceV2 estadoCarga;
        if (control.getCargaMaximaNormativa() == null || control.getCargaEvaluada() == null) estadoCarga = EstadoCondicionAvanceV2.REQUIERE_REVISION;
        else estadoCarga = control.getCargaEvaluada().intValue() <= control.getCargaMaximaNormativa().intValue()
                ? EstadoCondicionAvanceV2.CUMPLE : EstadoCondicionAvanceV2.NO_CUMPLE;
        agregarCondicionAvance(control, "C_CARGA", estadoCarga,
                "carga=" + control.getCargaEvaluada() + ";maximoRN42=" + control.getCargaMaximaNormativa(), null);
        estados.add(estadoCarga);

        EstadoCondicionAvanceV2 estadoPrioridad;
        if (Boolean.TRUE.equals(control.getAplicaRn63())) estadoPrioridad = EstadoCondicionAvanceV2.CUMPLE;
        else if ("NO_DETERMINADA".equals(resultado.getSituacionAcademicaCalculada())) estadoPrioridad = EstadoCondicionAvanceV2.REQUIERE_REVISION;
        else estadoPrioridad = EstadoCondicionAvanceV2.REQUIERE_REVISION;
        agregarCondicionAvance(control, "D_PRIORIDAD_PENDIENTES", estadoPrioridad,
                "rutaRN63=" + control.getAplicaRn63() + ";pendientes=" + control.getObligatoriasPendientesRn611()
                        + ";indisponibilidades=" + control.getCausasIndisponibilidadRegularizacion(),
                control.getObligatoriasPendientesRn611());
        estados.add(estadoPrioridad);

        EstadoCondicionAvanceV2 estadoEstructura = Boolean.TRUE.equals(contexto.getEstructuraProgramaEducativoComprobada())
                ? EstadoCondicionAvanceV2.CUMPLE : EstadoCondicionAvanceV2.REQUIERE_REVISION;
        agregarCondicionAvance(control, "E_ESTRUCTURA_PE", estadoEstructura,
                "estructuraProgramaEducativoComprobada=" + contexto.getEstructuraProgramaEducativoComprobada(), null);
        estados.add(estadoEstructura);

        boolean causaIdentificada = !control.getDetallePendientesRn611().isEmpty();
        EstadoCondicionAvanceV2 estadoExcepcion = causaIdentificada
                ? EstadoCondicionAvanceV2.PENDIENTE_TIPIFICACION : EstadoCondicionAvanceV2.REQUIERE_REVISION;
        agregarCondicionAvance(control, "F_CAUSA_Y_EXCEPCION", estadoExcepcion,
                causaIdentificada ? "CAUSA_REGISTRADA=true;EXCEPCION_APLICADA=PENDIENTE_TIPIFICACION_RN_6_1_3"
                        : "No existe causa verificable para la pendiente.", control.getObligatoriasPendientesRn611());
        estados.add(estadoExcepcion);

        EstadoCondicionAvanceV2 estadoSancion;
        if (!Boolean.TRUE.equals(contexto.getSancionImpedimentoConocida())) estadoSancion = EstadoCondicionAvanceV2.REQUIERE_REVISION;
        else estadoSancion = Boolean.TRUE.equals(contexto.getSancionImpedimentoRegistrada())
                ? EstadoCondicionAvanceV2.NO_CUMPLE : EstadoCondicionAvanceV2.CUMPLE;
        agregarCondicionAvance(control, "G_SANCION", estadoSancion,
                "sancionImpedimentoConocida=" + contexto.getSancionImpedimentoConocida()
                        + ";sancionImpedimentoRegistrada=" + contexto.getSancionImpedimentoRegistrada(), null);
        estados.add(estadoSancion);

        EstadoCondicionesComunesAvanceV2 global;
        if (estados.contains(EstadoCondicionAvanceV2.NO_CUMPLE)) global = EstadoCondicionesComunesAvanceV2.NO_CUMPLE_CONDICIONES_COMUNES;
        else if (estados.contains(EstadoCondicionAvanceV2.REQUIERE_REVISION)) global = EstadoCondicionesComunesAvanceV2.REQUIERE_REVISION;
        else if (estados.contains(EstadoCondicionAvanceV2.PENDIENTE_TIPIFICACION)) global = EstadoCondicionesComunesAvanceV2.PENDIENTE_TIPIFICACION;
        else global = EstadoCondicionesComunesAvanceV2.CUMPLE_CONDICIONES_COMUNES;
        control.setEstadoCondicionesComunesAvance(global);
        if (global == EstadoCondicionesComunesAvanceV2.REQUIERE_REVISION) control.setRequiereRevision(Boolean.TRUE);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_2,
                global == EstadoCondicionesComunesAvanceV2.NO_CUMPLE_CONDICIONES_COMUNES ? CodigoMotivoCurricularV2.REZAGO_SEVERO
                        : (global == EstadoCondicionesComunesAvanceV2.REQUIERE_REVISION ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null),
                100, "RN612_" + global,
                "Condiciones A-G concurrentes=" + estados + "; no concede avance condicionado ni tipifica RN-6.1.3.");
    }

    private EstadoCondicionAvanceV2 evaluarSeriacionQuinto(EvaluacionCargaAcademicaV2DTO control,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        boolean revision = false;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad == null || !esCandidataQuinto(contexto, unidad)) continue;
            if (unidad.getIdPrograma() == null) { revision = true; continue; }
            if (Boolean.TRUE.equals(unidad.getSeriacionDirectaConocida())) {
                agregarIdUnico(control.getUdsQuintoBloqueadasSeriacionDirectaRn612(), unidad.getIdPrograma());
            } else if (Boolean.TRUE.equals(unidad.getSeriacionIndirectaConocida())) {
                agregarIdUnico(control.getUdsQuintoBloqueadasSeriacionIndirectaRn612(), unidad.getIdPrograma());
            } else if (Boolean.TRUE.equals(unidad.getSeriacionConocida())) {
                agregarIdUnico(control.getUdsQuintoRevisionSeriacionRn612(), unidad.getIdPrograma()); revision = true;
            } else agregarIdUnico(control.getUdsQuintoCompatiblesRn612(), unidad.getIdPrograma());
        }
        EstadoCondicionAvanceV2 estado = !control.getUdsQuintoBloqueadasSeriacionDirectaRn612().isEmpty()
                || !control.getUdsQuintoBloqueadasSeriacionIndirectaRn612().isEmpty() ? EstadoCondicionAvanceV2.NO_CUMPLE
                : (revision || control.getUdsQuintoCompatiblesRn612().isEmpty() ? EstadoCondicionAvanceV2.REQUIERE_REVISION : EstadoCondicionAvanceV2.CUMPLE);
        agregarCondicionAvance(control, "B_SERIACION_QUINTO", estado,
                "compatibles=" + control.getUdsQuintoCompatiblesRn612() + ";directa=" + control.getUdsQuintoBloqueadasSeriacionDirectaRn612()
                        + ";indirecta=" + control.getUdsQuintoBloqueadasSeriacionIndirectaRn612() + ";revision=" + control.getUdsQuintoRevisionSeriacionRn612(),
                control.getObligatoriasPendientesRn611());
        return estado;
    }

    private void agregarCondicionAvance(EvaluacionCargaAcademicaV2DTO control, String codigo,
            EstadoCondicionAvanceV2 estado, String detalle, List<Long> unidades) {
        CondicionComunAvanceV2DTO condicion = new CondicionComunAvanceV2DTO();
        condicion.setCodigo(codigo); condicion.setEstado(estado); condicion.setDetalle(detalle);
        if (unidades != null) condicion.getUnidadesRelacionadas().addAll(unidades);
        control.getCondicionesComunesAvance().add(condicion);
    }

    /** Fase 5C: identifica supuestos concurrentes; no autoriza avance ni implementa RN-6.1.4+. */
    private void evaluarSupuestosTipificadosAvance(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO control = resultado.getEvaluacionCargaAcademica();
        if (control == null) return;
        agregarUnico(control.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_1_3);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_6_1_3);
        if (!Boolean.TRUE.equals(control.getAplicaCtr01()) || !Boolean.TRUE.equals(control.getRequiereEvaluacionAvanceCondicionado())) {
            control.setEstadoSupuestosAvance(EstadoSupuestosAvanceV2.NO_APLICA);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_3, null, 100,
                    "RN613_NO_APLICA", "RN-6.1.3 requiere CTR-01 y una base pendiente delegada por RN-6.1.1.");
            return;
        }
        if (control.getEstadoCondicionesComunesAvance() == EstadoCondicionesComunesAvanceV2.NO_APLICA) {
            control.setEstadoSupuestosAvance(EstadoSupuestosAvanceV2.NO_APLICA); return;
        }
        if (control.getObligatoriasPendientesRn611().size() == 1) agregarSupuesto(control,
                CodigoSupuestoAvanceCondicionadoV2.UNA_OBLIGATORIA_PENDIENTE,
                control.getObligatoriasPendientesRn611(), "PENDIENTE_BASE_RN611", "Causa preservada: " + control.getDetallePendientesRn611());
        if (Boolean.TRUE.equals(control.getAplicaRn51()) && "REZAGO_LEVE".equals(control.getCondicionRegularizacion())) agregarSupuesto(control,
                CodigoSupuestoAvanceCondicionadoV2.REZAGO_LEVE, control.getUdsReprobadasPrioritarias(),
                "RN_5_1_REUTILIZADO", "Reprobadas vigentes=" + resultado.getCantidadReprobadasVigentes());
        if (Boolean.TRUE.equals(contexto.getBajaTemporalRegistrada()) && Boolean.TRUE.equals(contexto.getReincorporacionRegistrada())) agregarSupuesto(control,
                CodigoSupuestoAvanceCondicionadoV2.BAJA_TEMPORAL, control.getObligatoriasPendientesRn611(),
                "BAJA_TEMPORAL_Y_REINCORPORACION", "RN-1.1/contexto acredita ambos antecedentes.");
        else if (Boolean.TRUE.equals(contexto.getBajaTemporalRegistrada()) && !Boolean.TRUE.equals(contexto.getReincorporacionRegistrada()))
            control.setRequiereRevision(Boolean.TRUE);
        List<Long> bajas = idsPendientesPorCausa(unidades, CausaPendienteUnidadV2.BAJA_PARCIAL);
        if (!bajas.isEmpty()) agregarSupuesto(control, CodigoSupuestoAvanceCondicionadoV2.BAJA_PARCIAL, bajas,
                "BAJA_PARCIAL", "La baja parcial se conserva como causa, no como reprobación.");
        for (EvaluacionOfertaPendienteRn614V2DTO oferta : control.getOfertasPendientesRn614()) {
            if (Boolean.TRUE.equals(oferta.getFaltaOferta())) agregarSupuesto(control,
                    CodigoSupuestoAvanceCondicionadoV2.FALTA_OFERTA_SUFICIENTE, listaId(oferta.getIdUnidad()),
                    oferta.getEstado().name(), oferta.getEvidencia());
            else if (oferta.getEstado() == EstadoOfertaPendienteRn614V2.REQUIERE_REVISION) control.setRequiereRevision(Boolean.TRUE);
        }
        if (control.getSupuestosAvanceTipificados().isEmpty()) control.setEstadoSupuestosAvance(
                Boolean.TRUE.equals(control.getRequiereRevision()) ? EstadoSupuestosAvanceV2.REQUIERE_REVISION : EstadoSupuestosAvanceV2.SIN_SUPUESTO_TIPIFICADO);
        else control.setEstadoSupuestosAvance(control.getSupuestosAvanceTipificados().size() == 1
                ? EstadoSupuestosAvanceV2.SUPUESTO_TIPIFICADO_IDENTIFICADO : EstadoSupuestosAvanceV2.MULTIPLES_SUPUESTOS_TIPIFICADOS);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_3,
                Boolean.TRUE.equals(control.getAplicaRn64()) ? CodigoMotivoCurricularV2.REZAGO_SEVERO : null, 100,
                "RN613_" + control.getEstadoSupuestosAvance(), "Supuestos=" + control.getSupuestosAvanceTipificados().size()
                        + "; condicionesRN612=" + control.getEstadoCondicionesComunesAvance()
                        + "; RN64=" + control.getAplicaRn64() + "; supuestos tipificados se resuelven en sombra cuando cumplen condiciones.");
    }

    private void agregarSupuesto(EvaluacionCargaAcademicaV2DTO control, CodigoSupuestoAvanceCondicionadoV2 codigo,
            List<Long> unidades, String causa, String evidencia) {
        for (SupuestoAvanceCondicionadoV2DTO existente : control.getSupuestosAvanceTipificados())
            if (existente.getCodigo() == codigo && existente.getUnidadesRelacionadas().equals(unidades)) return;
        SupuestoAvanceCondicionadoV2DTO supuesto = new SupuestoAvanceCondicionadoV2DTO();
        supuesto.setCodigo(codigo); supuesto.setCausa(causa); supuesto.setEvidencia(evidencia);
        if (unidades != null) supuesto.getUnidadesRelacionadas().addAll(unidades);
        control.getSupuestosAvanceTipificados().add(supuesto);
    }

    private List<Long> idsPendientesPorCausa(List<UnidadEvaluacionCurricularV2DTO> unidades, CausaPendienteUnidadV2 causa) {
        List<Long> ids = new ArrayList<Long>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades)
            if (unidad.getCausa() == causa) agregarIdUnico(ids, unidad.getIdPrograma());
        return ids;
    }

    private List<Long> listaId(Long id) { List<Long> ids = new ArrayList<Long>(); agregarIdUnico(ids, id); return ids; }

    /** Fase 5D: fuente única de clasificación oferta/cupo para pendientes CTR-01. */
    private void evaluarFaltaOfertaControlAvance(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO control = resultado.getEvaluacionCargaAcademica();
        if (control == null) return;
        agregarUnico(control.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_1_4);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_6_1_4);
        if (!Boolean.TRUE.equals(control.getAplicaCtr01()) || control.getObligatoriasPendientesRn611().isEmpty()) {
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_4, null, 100, "RN614_NO_APLICA", "CTR-01 no aplica o no hay pendientes base."); return;
        }
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (!control.getObligatoriasPendientesRn611().contains(unidad.getIdPrograma())) continue;
            EvaluacionOfertaPendienteRn614V2DTO oferta = new EvaluacionOfertaPendienteRn614V2DTO();
            oferta.setIdUnidad(unidad.getIdPrograma()); oferta.setEstadoCurricular(unidad.getEstado()); oferta.setCausaPendiente(unidad.getCausa()); oferta.setCausaOferta(unidad.getCausaIndisponibilidadOferta());
            CausaIndisponibilidadOfertaV2 causa = unidad.getCausaIndisponibilidadOferta();
            if (causa == CausaIndisponibilidadOfertaV2.UD_NO_OFERTADA_PERIODO) clasificarOferta(oferta, EstadoOfertaPendienteRn614V2.NO_OFERTADA_EN_PERIODO, true, false, "OFERTA:UD_NO_OFERTADA_PERIODO");
            else if (causa == CausaIndisponibilidadOfertaV2.FUERA_BLOQUE_VIGENTE) clasificarOferta(oferta, EstadoOfertaPendienteRn614V2.FUERA_DE_BLOQUE_VIGENTE, true, false, "OFERTA:FUERA_BLOQUE_VIGENTE");
            else if (causa == CausaIndisponibilidadOfertaV2.SIN_GRUPO_ACTIVO_INSTITUCIONAL) clasificarOferta(oferta, EstadoOfertaPendienteRn614V2.SIN_GRUPO_ACTIVO_DECISION_INSTITUCIONAL, true, false, "OFERTA:SIN_GRUPO_ACTIVO_INSTITUCIONAL");
            else if (causa == CausaIndisponibilidadOfertaV2.OTRA_INDISPONIBILIDAD_INSTITUCIONAL) clasificarOferta(oferta, EstadoOfertaPendienteRn614V2.OTRA_CONDICION_INSTITUCIONAL_OBJETIVA, true, false, "OFERTA:OTRA_INSTITUCIONAL");
            else if (causa == CausaIndisponibilidadOfertaV2.SIN_CUPO || Boolean.FALSE.equals(unidad.getCupoDisponibleConocido())) clasificarOferta(oferta, EstadoOfertaPendienteRn614V2.SIN_CUPO_CAPACIDAD, false, true, "OFERTA:SIN_CUPO_CAPACIDAD");
            else if (Boolean.TRUE.equals(unidad.getOfertada()) && Boolean.TRUE.equals(unidad.getGrupoActivoConocido()) && Boolean.TRUE.equals(unidad.getCupoDisponibleConocido())) clasificarOferta(oferta, EstadoOfertaPendienteRn614V2.DISPONIBLE, false, false, "OFERTA:DISPONIBLE");
            else clasificarOferta(oferta, EstadoOfertaPendienteRn614V2.REQUIERE_REVISION, false, false, "OFERTA:EVIDENCIA_INSUFICIENTE");
            control.getOfertasPendientesRn614().add(oferta);
        }
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_4, null, 100, "RN614_EVALUADA", "UD pendientes clasificadas=" + control.getOfertasPendientesRn614().size() + "; RN-6.1.3 consume esta fuente única.");
    }

    private void clasificarOferta(EvaluacionOfertaPendienteRn614V2DTO oferta, EstadoOfertaPendienteRn614V2 estado, boolean falta, boolean cupo, String evidencia) {
        oferta.setEstado(estado); oferta.setFaltaOferta(Boolean.valueOf(falta)); oferta.setSinCupo(Boolean.valueOf(cupo)); oferta.setEvidencia(evidencia);
    }

    /** Fase 5E: impedimentos prevalentes y ruta extraordinaria sólo descriptiva. */
    private void evaluarImpedimentosYExtraordinario(ResultadoEvaluacionCurricularV2DTO resultado, ContextoEvaluacionCurricularV2DTO contexto) {
        EvaluacionCargaAcademicaV2DTO c=resultado.getEvaluacionCargaAcademica(); if(c==null)return;
        agregarUnico(c.getReglasRelacionadas(),CodigoReglaCurricularV2.RN_6_1_5);agregarUnico(c.getReglasRelacionadas(),CodigoReglaCurricularV2.RN_6_1_6);
        if(!Boolean.TRUE.equals(c.getAplicaCtr01())||!Boolean.TRUE.equals(c.getRequiereEvaluacionAvanceCondicionado())){c.setEstadoResolucionAvance(EstadoResolucionAvanceV2.NO_APLICA);return;}
        if(Boolean.TRUE.equals(c.getAplicaRn64()))c.getImpedimentosAvanceRn615().add("RN_6_4_REZAGO_SEVERO");
        c.getUdsQuintoNoSusceptiblesRn615().addAll(c.getUdsQuintoBloqueadasSeriacionDirectaRn612());c.getUdsQuintoNoSusceptiblesRn615().addAll(c.getUdsQuintoBloqueadasSeriacionIndirectaRn612());
        if(!c.getUdsQuintoNoSusceptiblesRn615().isEmpty())c.getImpedimentosAvanceRn615().add("SERIACION_DIRECTA_O_INDIRECTA");
        if(Boolean.TRUE.equals(contexto.getSancionImpedimentoConocida())&&Boolean.TRUE.equals(contexto.getSancionImpedimentoRegistrada()))c.getImpedimentosAvanceRn615().add("SANCION_IMPEDITIVA");
        boolean medidaSinVigencia = Boolean.TRUE.equals(contexto.getMedidaAdministrativaRegistrada())
                && (!textoPresente(contexto.getAlcanceMedidaAdministrativa())
                || !textoPresente(contexto.getInicioVigenciaMedidaAdministrativa())
                || (!textoPresente(contexto.getFinVigenciaMedidaAdministrativa())
                && !textoPresente(contexto.getCondicionVigenciaMedidaAdministrativa())));
        if (medidaSinVigencia) {
            c.getImpedimentosAvanceRn615().add("MEDIDA_ADMINISTRATIVA_SIN_VIGENCIA");
            c.setRequiereRevision(Boolean.TRUE);
            agregarUnico(resultado.getRestricciones(), CodigoMotivoCurricularV2.MEDIDA_ADMINISTRATIVA_SIN_VIGENCIA);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_5,
                    CodigoMotivoCurricularV2.MEDIDA_ADMINISTRATIVA_SIN_VIGENCIA, 100,
                    "RN615_REQUIERE_REVISION_MEDIDA_ADMINISTRATIVA",
                    "La medida administrativa registrada no cuenta con alcance e inicio y fin o condición de vigencia verificables.");
        }
        if(Boolean.TRUE.equals(contexto.getRestriccionPeNormativaConocida())&&Boolean.TRUE.equals(contexto.getRestriccionPeNormativaImpeditiva()))c.getImpedimentosAvanceRn615().add("RESTRICCION_PE_NORMATIVA:"+contexto.getCodigoRestriccionPeNormativa()+":"+contexto.getFuenteRestriccionPeNormativa());
        if(!Boolean.TRUE.equals(contexto.getSancionImpedimentoConocida())||!Boolean.TRUE.equals(contexto.getRestriccionPeNormativaConocida())||Boolean.TRUE.equals(c.getRequiereRevision())){c.setEstadoResolucionAvance(EstadoResolucionAvanceV2.REQUIERE_REVISION);}
        else if(Boolean.TRUE.equals(c.getAplicaRn64()) || Boolean.TRUE.equals(contexto.getSancionImpedimentoRegistrada()) || Boolean.TRUE.equals(contexto.getRestriccionPeNormativaImpeditiva()))c.setEstadoResolucionAvance(EstadoResolucionAvanceV2.NO_PROCEDE_AVANCE_CONDICIONADO);
        else if(c.getEstadoSupuestosAvance()==EstadoSupuestosAvanceV2.SIN_SUPUESTO_TIPIFICADO){c.setEstadoResolucionAvance(EstadoResolucionAvanceV2.REQUIERE_RESOLUCION_EXTRAORDINARIA);c.getRutaInstitucionalExtraordinariaRn616().add("DIRECCION_DIVISION:PENDIENTE_FUNDAMENTACION_ACADEMICA");c.getRutaInstitucionalExtraordinariaRn616().add("CAI:PENDIENTE_DEFINICION_ACADEMICA");c.getRutaInstitucionalExtraordinariaRn616().add("DAEAE:PENDIENTE_DETERMINACION_ESCOLAR_NORMATIVA");c.getRutaInstitucionalExtraordinariaRn616().add("CTIE:RESOLUCION_DOCUMENTADA_PENDIENTE_EJECUCION");}
        else c.setEstadoResolucionAvance(EstadoResolucionAvanceV2.CONTINUA_EVALUACION_ORDINARIA_RN61);
        registrarTraza(resultado,contexto,CodigoReglaCurricularV2.RN_6_1_5,null,100,"RN615_"+c.getEstadoResolucionAvance(),"Impedimentos="+c.getImpedimentosAvanceRn615());registrarTraza(resultado,contexto,CodigoReglaCurricularV2.RN_6_1_6,null,100,"RN616_"+c.getEstadoResolucionAvance(),"Ruta="+c.getRutaInstitucionalExtraordinariaRn616());
    }

    /** Fase 5F: compone resultados RN-6.1.1..6 sin recalcularlos ni activar efectos. */
    private void consolidarDecisionControlAvance(ResultadoEvaluacionCurricularV2DTO resultado, ContextoEvaluacionCurricularV2DTO contexto) {
        EvaluacionCargaAcademicaV2DTO c=resultado.getEvaluacionCargaAcademica(); if(c==null)return;
        EstadoDecisionControlAvanceRn61V2 d;
        if(!Boolean.TRUE.equals(c.getAplicaCtr01())) d=EstadoDecisionControlAvanceRn61V2.NO_APLICA;
        else if(c.getEstadoControlAvanceAnual()==EstadoControlAvanceAnualV2.ACCESO_ORDINARIO_PROCEDE_EN_SOMBRA) d=EstadoDecisionControlAvanceRn61V2.ACCESO_ORDINARIO_PROCEDE_EN_SOMBRA;
        else if(c.getEstadoResolucionAvance()==EstadoResolucionAvanceV2.NO_PROCEDE_AVANCE_CONDICIONADO) d=EstadoDecisionControlAvanceRn61V2.AVANCE_CONDICIONADO_NO_PROCEDE;
        else if(c.getEstadoResolucionAvance()==EstadoResolucionAvanceV2.REQUIERE_RESOLUCION_EXTRAORDINARIA) d=EstadoDecisionControlAvanceRn61V2.REQUIERE_RESOLUCION_EXTRAORDINARIA;
        else if(c.getEstadoResolucionAvance()==EstadoResolucionAvanceV2.REQUIERE_REVISION || Boolean.TRUE.equals(c.getRequiereRevision())) d=EstadoDecisionControlAvanceRn61V2.REQUIERE_REVISION;
        else if(!c.getSupuestosAvanceTipificados().isEmpty()) d=EstadoDecisionControlAvanceRn61V2.AVANCE_CONDICIONADO_PROCEDE_PARA_UD_COMPATIBLES;
        else d=EstadoDecisionControlAvanceRn61V2.NO_DETERMINADO;
        c.setDecisionGlobalRn61(d);
        for(Long id:c.getUdsQuintoBloqueadasSeriacionDirectaRn612())c.getDecisionesPorUdQuintoRn61().add(id+":NO_PROCEDE_POR_SERIACION_DIRECTA");
        for(Long id:c.getUdsQuintoBloqueadasSeriacionIndirectaRn612())c.getDecisionesPorUdQuintoRn61().add(id+":NO_PROCEDE_POR_SERIACION_INDIRECTA");
        for(Long id:c.getUdsQuintoRevisionSeriacionRn612())c.getDecisionesPorUdQuintoRn61().add(id+":REQUIERE_REVISION_SERIACION");
        for(Long id:c.getUdsQuintoCompatiblesRn612())c.getDecisionesPorUdQuintoRn61().add(id+":"+(d==EstadoDecisionControlAvanceRn61V2.AVANCE_CONDICIONADO_PROCEDE_PARA_UD_COMPATIBLES?"AVANCE_CONDICIONADO_PROCEDE_EN_SOMBRA":d.name()));
        registrarTraza(resultado,contexto,CodigoReglaCurricularV2.RN_6_1,null,100,"DECISION_RN61_"+d,"DecisionesUD5="+c.getDecisionesPorUdQuintoRn61()+"; sin efecto productivo.");
    }

    /** Fase 6A: trayectoria admitida sin inscripción inicial; evaluación descriptiva, no inscripción. */
    private void evaluarInscripcionInicialDiferida(ResultadoEvaluacionCurricularV2DTO resultado, ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO c=resultado.getEvaluacionCargaAcademica(); if(c==null)return;
        agregarUnico(c.getReglasRelacionadas(),CodigoReglaCurricularV2.RN_6_2);agregarUnico(resultado.getReglasEvaluadas(),CodigoReglaCurricularV2.RN_6_2);
        if(contexto.getAdmitido()==null||contexto.getInscripcionInicialRealizada()==null){c.setAplicaRn62(null);c.setEstadoIncorporacionDiferida(EstadoIncorporacionDiferidaV2.REQUIERE_REVISION);registrarTraza(resultado,contexto,CodigoReglaCurricularV2.RN_6_2,CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,70,"RN62_REQUIERE_REVISION","Admisión o inscripción inicial no determinable.");return;}
        boolean aplica=Boolean.TRUE.equals(contexto.getAdmitido())&&!Boolean.TRUE.equals(contexto.getInscripcionInicialRealizada());c.setAplicaRn62(aplica?Boolean.TRUE:Boolean.FALSE);if(!aplica){c.setEstadoIncorporacionDiferida(EstadoIncorporacionDiferidaV2.NO_APLICA);registrarTrazaInscripcionInicialDiferida(resultado,contexto,c);return;}c.setCondicionTrayectoriaRn62("INSCRIPCION_INICIAL_DIFERIDA");c.setPeriodoAdmisionRn62(contexto.getPeriodoAdmision());c.setPeriodoIncorporacionRn62(contexto.getPeriodoIncorporacion());c.setSemestreObjetivoRn62(contexto.getSemestreReferencia());
        if (!textoPresente(contexto.getReferenciaConvocatoriaOResolucionRn62())) {
            c.setEstadoIncorporacionDiferida(EstadoIncorporacionDiferidaV2.REQUIERE_REVISION); c.setRequiereRevision(Boolean.TRUE);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_2, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    70, "RN62_REQUIERE_REVISION_REFERENCIA", "Falta referencia verificable de convocatoria o resolución para la incorporación diferida."); return;
        }
        if(Boolean.TRUE.equals(contexto.getLimiteInactividadAlcanzado())){
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_2, CodigoMotivoCurricularV2.INACTIVIDAD,
                    70, "RN62_IDENTIFICA_INACTIVIDAD", "El límite de inactividad se identifica; no sustituye la resolución institucional.");
        }
        if(!Boolean.TRUE.equals(contexto.getActividadAcademicaComprobada())){c.setEstadoIncorporacionDiferida(EstadoIncorporacionDiferidaV2.REQUIERE_REVISION);registrarTrazaInscripcionInicialDiferida(resultado,contexto,c);return;}
        boolean bloqueo=false,revision=false; for(UnidadEvaluacionCurricularV2DTO u:unidades)if(Integer.valueOf(2).equals(u.getSemestre())){if(Boolean.TRUE.equals(u.getSeriacionDirectaConocida())||Boolean.TRUE.equals(u.getSeriacionIndirectaConocida())){agregarIdUnico(c.getUdsSemestre2BloqueadasRn62(),u.getIdPrograma());bloqueo=true;}else if(Boolean.TRUE.equals(u.getSeriacionConocida())){revision=true;}else agregarIdUnico(c.getUdsSemestre2CompatiblesRn62(),u.getIdPrograma());}
        if(revision)c.setEstadoIncorporacionDiferida(EstadoIncorporacionDiferidaV2.REQUIERE_REVISION);else if(bloqueo)c.setEstadoIncorporacionDiferida(EstadoIncorporacionDiferidaV2.INCORPORACION_SEMESTRE_2_NO_PROCEDE_POR_SERIACION);else if(!c.getUdsSemestre2CompatiblesRn62().isEmpty())c.setEstadoIncorporacionDiferida(EstadoIncorporacionDiferidaV2.INCORPORACION_SEMESTRE_2_PROCEDE_EN_SOMBRA);else c.setEstadoIncorporacionDiferida(EstadoIncorporacionDiferidaV2.INCORPORACION_DIFERIDA_SEMESTRE_1_PROCEDE_EN_SOMBRA);
        registrarTrazaInscripcionInicialDiferida(resultado,contexto,c);
    }

    private void registrarTrazaInscripcionInicialDiferida(ResultadoEvaluacionCurricularV2DTO resultado, ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga) {
        String evidencia="Trayectoria="+carga.getCondicionTrayectoriaRn62()+"; periodoAdmision="+contexto.getPeriodoAdmision()+"; periodoIncorporacion="+contexto.getPeriodoIncorporacion()+"; semestreReferencia="+contexto.getSemestreReferencia()+"; S2 compatibles="+carga.getUdsSemestre2CompatiblesRn62()+"; bloqueadas="+carga.getUdsSemestre2BloqueadasRn62()+"; CTR01="+carga.getAplicaCtr01();agregarUnico(carga.getEvidencias(),evidencia);registrarTraza(resultado,contexto,CodigoReglaCurricularV2.RN_6_2,null,70,"RN62_"+carga.getEstadoIncorporacionDiferida(),evidencia);
    }

    /**
     * Fase 6B: RN-6.5 describe la reincorporacion posterior a un periodo no
     * cursado. Reutiliza resultados ya calculados de carga, seriacion, RN-6.4
     * y RN-6.1; no altera oferta, cupo, seleccion ni reinscripcion real.
     */
    private void evaluarReincorporacionTrasPeriodoNoCursado(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;

        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_5);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_6_5);

        if (contexto.getHaCursadoUnoOMasPeriodos() == null
                || contexto.getNoCursoPeriodoInmediatoSiguiente() == null) {
            carga.setAplicaRn65(null);
            carga.setEstadoReincorporacionRn65(EstadoReincorporacionPeriodoNoCursadoV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn65(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "RN65_REQUIERE_REVISION_APLICABILIDAD",
                    "No es posible acreditar si la persona curso previamente o si omitio el periodo inmediato siguiente.");
            return;
        }

        boolean aplica = Boolean.TRUE.equals(contexto.getHaCursadoUnoOMasPeriodos())
                && Boolean.TRUE.equals(contexto.getNoCursoPeriodoInmediatoSiguiente());
        carga.setAplicaRn65(Boolean.valueOf(aplica));
        if (!aplica) {
            carga.setEstadoReincorporacionRn65(EstadoReincorporacionPeriodoNoCursadoV2.NO_APLICA);
            registrarTrazaReincorporacionRn65(resultado, contexto, carga, null, "RN65_NO_APLICA",
                    "RN-6.5 requiere trayectoria cursada y omision comprobada del periodo inmediato siguiente.");
            return;
        }

        // Las bajas formales se conservan para RN-6.6/RN-6.7, no se reinterpretan aqui.
        boolean existeBajaParcialPorUd = false;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad.getEstado() == EstadoUnidadCurricularV2.BAJA_PARCIAL
                    || unidad.getCausa() == CausaPendienteUnidadV2.BAJA_PARCIAL) {
                existeBajaParcialPorUd = true;
                break;
            }
        }
        if (Boolean.TRUE.equals(contexto.getBajaTemporalRegistrada())
                || Boolean.TRUE.equals(contexto.getBajaTemporalTotalRegistrada())
                || (Boolean.TRUE.equals(contexto.getBajaParcialRegistrada()) && !existeBajaParcialPorUd)) {
            carga.setAplicaRn65(Boolean.FALSE);
            carga.setEstadoReincorporacionRn65(EstadoReincorporacionPeriodoNoCursadoV2.NO_APLICA);
            registrarTrazaReincorporacionRn65(resultado, contexto, carga, null, "RN65_NO_APLICA_BAJA_FORMAL",
                    "La interrupcion tiene una baja formal y queda fuera de RN-6.5 para futuras RN-6.6 o RN-6.7.");
            return;
        }

        copiarTrayectoriaReincorporacionRn65(carga, contexto);
        agregarUnico(resultado.getCondicionesDetectadas(), CondicionCurricularV2.REINCORPORACION_TRAS_PERIODO_NO_CURSADO);
        if (!Boolean.TRUE.equals(contexto.getCausaInterrupcionConocida())
                || !textoPresente(contexto.getCausaInterrupcion())
                || !trayectoriaReincorporacionCompleta(contexto)
                || contexto.getSemestreReferencia() == null) {
            carga.setEstadoReincorporacionRn65(EstadoReincorporacionPeriodoNoCursadoV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn65(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "RN65_REQUIERE_REVISION_TRAYECTORIA",
                    "La causa, los periodos omitidos, la reincorporacion o el semestre objetivo no son determinables.");
            return;
        }

        if (!Boolean.TRUE.equals(contexto.getLimiteInactividadComprobado())) {
            carga.setEstadoReincorporacionRn65(EstadoReincorporacionPeriodoNoCursadoV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn65(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "RN65_REQUIERE_REVISION_INACTIVIDAD",
                    "No existe evidencia suficiente para determinar el limite de inactividad aplicable.");
            return;
        }
        if (Boolean.TRUE.equals(contexto.getLimiteInactividadAlcanzado())) {
            registrarTrazaReincorporacionRn65(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.INACTIVIDAD, "RN65_IDENTIFICA_INACTIVIDAD",
                    "El límite de inactividad se identifica; no produce una baja ni una denegación automática.");
        }

        if (carga.getCargaMaximaNormativa() == null) {
            carga.setEstadoReincorporacionRn65(EstadoReincorporacionPeriodoNoCursadoV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn65(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "RN65_REQUIERE_REVISION_CARGA",
                    "RN-4.2 no determino carga maxima; RN-6.5 no infiere una ruta de reincorporacion.");
            return;
        }

        int espacios = carga.getCargaMaximaNormativa().intValue();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (!esPendienteObligatoriaRn65(unidad, contexto.getSemestreReferencia())) continue;
            if (unidad.getEstado() == EstadoUnidadCurricularV2.NO_DETERMINADO
                    || unidad.getCausa() == CausaPendienteUnidadV2.NO_DETERMINADA) {
                marcarRevisionRn65(resultado, contexto, carga, unidad, "PENDIENTE_NO_DETERMINADA");
                continue;
            }
            String disponibilidad = disponibilidadRn65(unidad);
            if ("SIN_OFERTA".equals(disponibilidad)) {
                agregarIdUnico(carga.getUdsPendientesSinOfertaRn65(), unidad.getIdPrograma());
                registrarDecisionRn65(resultado, contexto, unidad, CodigoMotivoCurricularV2.SIN_OFERTA,
                        "PENDIENTE_OBLIGATORIA_SIN_OFERTA", disponibilidad);
                continue;
            }
            if ("SIN_CUPO".equals(disponibilidad)) {
                agregarIdUnico(carga.getUdsPendientesSinCupoRn65(), unidad.getIdPrograma());
                registrarDecisionRn65(resultado, contexto, unidad, CodigoMotivoCurricularV2.SIN_CUPO,
                        "PENDIENTE_OBLIGATORIA_SIN_CUPO", disponibilidad);
                continue;
            }
            if ("REQUIERE_REVISION".equals(disponibilidad)) {
                marcarRevisionRn65(resultado, contexto, carga, unidad, "OFERTA_O_CAPACIDAD_NO_DETERMINADA");
                continue;
            }
            agregarIdUnico(carga.getUdsPendientesPrioritariasRn65(), unidad.getIdPrograma());
            if (espacios > 0) {
                agregarIdUnico(carga.getUdsRutaReincorporacionRn65(), unidad.getIdPrograma());
                espacios--;
            } else {
                marcarRevisionRn65(resultado, contexto, carga, unidad, "CARGA_MAXIMA_SIN_DESEMPATE_APROBADO");
            }
            registrarDecisionRn65(resultado, contexto, unidad, motivoPendienteRn65(unidad),
                    "PENDIENTE_OBLIGATORIA_PRIORITARIA", disponibilidad);
        }

        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (!esPosteriorCandidataRn65(unidad, contexto.getSemestreReferencia())) continue;
            if (Boolean.TRUE.equals(carga.getAplicaRn64()) && esObligatoria(unidad)) {
                agregarUnico(carga.getUdsExcluidas(), obtenerLlaveUnidad(unidad) + ":RN_6_4");
                registrarDecisionRn65(resultado, contexto, unidad, CodigoMotivoCurricularV2.REZAGO_SEVERO,
                        "POSTERIOR_EXCLUIDA_RN64", "RN_6_4");
                continue;
            }
            if (Boolean.TRUE.equals(unidad.getSeriacionDirectaConocida())
                    || Boolean.TRUE.equals(unidad.getSeriacionIndirectaConocida())) {
                agregarIdUnico(carga.getUdsPosterioresBloqueadasSeriacionRn65(), unidad.getIdPrograma());
                registrarDecisionRn65(resultado, contexto, unidad, CodigoMotivoCurricularV2.SERIACION_DIRECTA,
                        "POSTERIOR_NO_COMPATIBLE_SERIACION", "SERIACION");
                continue;
            }
            if (Boolean.TRUE.equals(unidad.getSeriacionConocida())
                    || !Boolean.TRUE.equals(contexto.getSeriacionComprobada())) {
                agregarIdUnico(carga.getUdsPosterioresRevisionSeriacionRn65(), unidad.getIdPrograma());
                marcarRevisionRn65(resultado, contexto, carga, unidad, "SERIACION_NO_DETERMINABLE");
                continue;
            }
            String disponibilidad = disponibilidadRn65(unidad);
            if (!"DISPONIBLE".equals(disponibilidad)) {
                registrarDecisionRn65(resultado, contexto, unidad,
                        "SIN_CUPO".equals(disponibilidad) ? CodigoMotivoCurricularV2.SIN_CUPO
                                : ("SIN_OFERTA".equals(disponibilidad) ? CodigoMotivoCurricularV2.SIN_OFERTA
                                : CodigoMotivoCurricularV2.DATOS_INSUFICIENTES),
                        "POSTERIOR_NO_DISPONIBLE", disponibilidad);
                if ("REQUIERE_REVISION".equals(disponibilidad)) marcarRevisionRn65(resultado, contexto, carga, unidad, disponibilidad);
                continue;
            }
            if (espacios <= 0) {
                registrarDecisionRn65(resultado, contexto, unidad, null, "POSTERIOR_FUERA_POR_CARGA", "CARGA_MAXIMA");
                continue;
            }
            agregarIdUnico(carga.getUdsPosterioresCompatiblesRn65(), unidad.getIdPrograma());
            agregarIdUnico(carga.getUdsRutaReincorporacionRn65(), unidad.getIdPrograma());
            espacios--;
            registrarDecisionRn65(resultado, contexto, unidad, null, "POSTERIOR_COMPATIBLE", "DISPONIBLE");
        }

        carga.setCargaDisponibleRegularizacion(Integer.valueOf(Math.max(0, espacios)));
        if (Boolean.TRUE.equals(carga.getAplicaCtr01())) carga.setRn61ConsumidaRn65(Boolean.TRUE);
        if (Boolean.TRUE.equals(carga.getAplicaRn64())) {
            carga.setEstadoReincorporacionRn65(EstadoReincorporacionPeriodoNoCursadoV2.REINCORPORACION_RESTRINGIDA_POR_RN64);
        } else if (Boolean.TRUE.equals(carga.getAplicaCtr01())) {
            carga.setEstadoReincorporacionRn65(EstadoReincorporacionPeriodoNoCursadoV2.REINCORPORACION_SUJETA_RN61);
        } else if (!carga.getUdsRutaReincorporacionRn65().isEmpty()) {
            carga.setEstadoReincorporacionRn65(EstadoReincorporacionPeriodoNoCursadoV2.REINCORPORACION_PROCEDE_CON_RUTA_REGULARIZACION);
        } else {
            carga.setEstadoReincorporacionRn65(EstadoReincorporacionPeriodoNoCursadoV2.REINCORPORACION_PROCEDE_EN_SOMBRA);
        }
        registrarTrazaReincorporacionRn65(resultado, contexto, carga,
                Boolean.TRUE.equals(carga.getRequiereRevision()) ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                "RN65_" + carga.getEstadoReincorporacionRn65(), construirEvidenciaRn65(carga));
    }

    private void copiarTrayectoriaReincorporacionRn65(EvaluacionCargaAcademicaV2DTO carga,
            ContextoEvaluacionCurricularV2DTO contexto) {
        carga.setCondicionTrayectoriaRn65("REINCORPORACION_TRAS_PERIODO_NO_CURSADO");
        carga.setUltimoPeriodoCursadoRn65(contexto.getUltimoPeriodoCursado());
        carga.setPrimerPeriodoOmitidoRn65(contexto.getPrimerPeriodoOmitido());
        carga.setPeriodosOmitidosRn65(contexto.getPeriodosOmitidos());
        carga.setPeriodoReincorporacionRn65(contexto.getPeriodoReincorporacion());
        carga.setCantidadPeriodosInterrupcionRn65(Integer.valueOf(contexto.getPeriodosOmitidos().size()));
        carga.setCausaInterrupcionRn65(contexto.getCausaInterrupcion());
        carga.setTipoProcesoReincorporacionRn65(contexto.getTipoProcesoReincorporacion());
    }

    private boolean trayectoriaReincorporacionCompleta(ContextoEvaluacionCurricularV2DTO contexto) {
        return textoPresente(contexto.getUltimoPeriodoCursado())
                && textoPresente(contexto.getPrimerPeriodoOmitido())
                && contexto.getPeriodosOmitidos() != null && !contexto.getPeriodosOmitidos().isEmpty()
                && textoPresente(contexto.getPeriodoReincorporacion());
    }

    private boolean textoPresente(String valor) { return valor != null && !valor.trim().isEmpty(); }

    private boolean esPendienteObligatoriaRn65(UnidadEvaluacionCurricularV2DTO unidad, Integer semestreObjetivo) {
        return esObligatoria(unidad) && esPendiente(unidad)
                && unidad.getEstado() != EstadoUnidadCurricularV2.BAJA_PARCIAL
                && unidad.getSemestre() != null && unidad.getSemestre().intValue() <= semestreObjetivo.intValue();
    }

    private boolean esPosteriorCandidataRn65(UnidadEvaluacionCurricularV2DTO unidad, Integer semestreObjetivo) {
        return unidad.getEstado() == EstadoUnidadCurricularV2.NO_INSCRITA
                && unidad.getSemestre() != null && unidad.getSemestre().intValue() > semestreObjetivo.intValue();
    }

    private String disponibilidadRn65(UnidadEvaluacionCurricularV2DTO unidad) {
        if (!Boolean.TRUE.equals(unidad.getOfertada())) return "SIN_OFERTA";
        if (Boolean.FALSE.equals(unidad.getCupoDisponibleConocido())
                || unidad.getCausaIndisponibilidadOferta() == CausaIndisponibilidadOfertaV2.SIN_CUPO) return "SIN_CUPO";
        if (!Boolean.TRUE.equals(unidad.getCupoDisponibleConocido())) return "REQUIERE_REVISION";
        return "DISPONIBLE";
    }

    private CodigoMotivoCurricularV2 motivoPendienteRn65(UnidadEvaluacionCurricularV2DTO unidad) {
        return unidad.getCausa() == CausaPendienteUnidadV2.REPROBACION
                ? CodigoMotivoCurricularV2.REPROBACION_ACUMULADA : CodigoMotivoCurricularV2.PENDIENTE_NO_CURSADA;
    }

    private void marcarRevisionRn65(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO unidad, String causa) {
        carga.setRequiereRevision(Boolean.TRUE);
        if (unidad != null) registrarDecisionRn65(resultado, contexto, unidad,
                CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "REQUIERE_REVISION", causa);
    }

    private void registrarDecisionRn65(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, UnidadEvaluacionCurricularV2DTO unidad,
            CodigoMotivoCurricularV2 motivo, String decision, String detalle) {
        if (unidad == null) return;
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga != null) carga.getDecisionesPorUdRn65().add(obtenerLlaveUnidad(unidad) + ":" + decision + ":" + detalle);
        DecisionUnidadCurricularV2DTO unidadDecision = buscarDecision(resultado, unidad.getIdPrograma());
        if (unidadDecision != null) {
            if ("PENDIENTE_OBLIGATORIA_PRIORITARIA".equals(decision)) {
                unidadDecision.setPrioritaria(Boolean.TRUE);
                agregarUnico(unidadDecision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.PRIORITARIA);
            }
            if (motivo != null) agregarUnico(unidadDecision.getMotivos(), motivo);
            agregarExplicacion(unidadDecision, "RN-6.5: " + decision + " (" + detalle + ").");
            TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_6_5, motivo, 85,
                    "RN65_" + decision, obtenerLlaveUnidad(unidad) + "; " + detalle);
            unidadDecision.getTrazas().add(traza);
            resultado.getTrazas().add(traza);
        }
    }

    private String construirEvidenciaRn65(EvaluacionCargaAcademicaV2DTO carga) {
        return "Trayectoria=" + carga.getCondicionTrayectoriaRn65()
                + "; ultimo=" + carga.getUltimoPeriodoCursadoRn65()
                + "; omitidos=" + carga.getPeriodosOmitidosRn65()
                + "; reincorporacion=" + carga.getPeriodoReincorporacionRn65()
                + "; pendientes=" + carga.getUdsPendientesPrioritariasRn65()
                + "; sinOferta=" + carga.getUdsPendientesSinOfertaRn65()
                + "; sinCupo=" + carga.getUdsPendientesSinCupoRn65()
                + "; compatibles=" + carga.getUdsPosterioresCompatiblesRn65()
                + "; rn64=" + carga.getAplicaRn64()
                + "; rn61=" + carga.getRn61ConsumidaRn65();
    }

    private void registrarTrazaReincorporacionRn65(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            CodigoMotivoCurricularV2 motivo, String estado, String detalle) {
        agregarUnico(carga.getEvidencias(), detalle);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_5, motivo, 85, estado, detalle);
    }

    /**
     * Fase 6C: RN-6.6 conserva la baja temporal como condicion de trayectoria.
     * Los periodos calendario cubiertos por la baja no generan reprobacion ni
     * avance curricular. La ruta solo es descriptiva y no ejecuta operaciones.
     */
    private void evaluarReincorporacionTrasBajaTemporal(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;

        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_6);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_6_6);

        if (contexto.getBajaTemporalFormalComprobada() == null
                || contexto.getHaCursadoUnoOMasPeriodos() == null) {
            carga.setAplicaRn66(null);
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn66(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "RN66_REQUIERE_REVISION_APLICABILIDAD",
                    "No es posible acreditar la formalidad de la baja temporal o la trayectoria previa.");
            return;
        }

        boolean aplica = Boolean.TRUE.equals(contexto.getBajaTemporalFormalComprobada())
                && Boolean.TRUE.equals(contexto.getHaCursadoUnoOMasPeriodos());
        carga.setAplicaRn66(Boolean.valueOf(aplica));
        if (!aplica) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.NO_APLICA);
            registrarTrazaReincorporacionRn66(resultado, contexto, carga, null, "RN66_NO_APLICA",
                    "RN-6.6 requiere baja temporal formal y trayectoria académica previa.");
            return;
        }

        copiarTrayectoriaReincorporacionRn66(carga, contexto);
        agregarUnico(resultado.getCondicionesDetectadas(), CondicionCurricularV2.REINCORPORACION_TRAS_BAJA_TEMPORAL);
        if (contexto.getBajaTemporalConcluida() == null) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn66(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "RN66_REQUIERE_REVISION_VIGENCIA",
                    "No existe evidencia suficiente para determinar si la baja temporal concluyó.");
            return;
        }
        if (!Boolean.TRUE.equals(contexto.getBajaTemporalConcluida())) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.BAJA_TEMPORAL_AUN_VIGENTE);
            registrarTrazaReincorporacionRn66(resultado, contexto, carga, null, "RN66_BAJA_TEMPORAL_AUN_VIGENTE",
                    "La baja temporal sigue vigente; RN-6.6 no construye una reincorporación ordinaria.");
            return;
        }

        Integer maximoBajas = contexto.getParametrosCurriculares() == null
                ? Integer.valueOf(3) : contexto.getParametrosCurriculares().getMaxSemestresBajaTemporal();
        Integer bajasAcumuladas = contexto.getPeriodosBajaTemporalTranscurridos();
        if (maximoBajas == null || bajasAcumuladas == null || maximoBajas.intValue() < 0 || bajasAcumuladas.intValue() < 0) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn66(resultado, contexto, carga, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    "RN66_REQUIERE_REVISION_ACUMULADO", "No existe un conteo verificable de bajas temporales acumuladas en el programa.");
            return;
        }
        if (bajasAcumuladas.intValue() > maximoBajas.intValue()) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.NO_PROCEDE_POR_MAXIMO_BAJAS_TEMPORALES);
            agregarUnico(resultado.getRestricciones(), CodigoMotivoCurricularV2.INACTIVIDAD);
            registrarTrazaReincorporacionRn66(resultado, contexto, carga, CodigoMotivoCurricularV2.INACTIVIDAD,
                    "RN66_MAXIMO_BAJAS_TEMPORALES_EXCEDIDO", "Bajas temporales acumuladas=" + bajasAcumuladas + "; máximo aplicable=" + maximoBajas + ".");
            return;
        }

        if (!trayectoriaPreviaBajaCompleta(contexto)) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn66(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "RN66_REQUIERE_REVISION_TRAYECTORIA",
                    "No se puede determinar el último periodo efectivo, el semestre previo, el periodo de baja o el semestre objetivo.");
            return;
        }

        if (!Boolean.TRUE.equals(contexto.getLimitePermanenciaComprobado())) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn66(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "RN66_REQUIERE_REVISION_PERMANENCIA",
                    "No existe evidencia suficiente para determinar el límite de permanencia.");
            return;
        }
        if (Boolean.TRUE.equals(contexto.getLimitePermanenciaExcedido())) {
            registrarTrazaReincorporacionRn66(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.INACTIVIDAD, "RN66_IDENTIFICA_PLAZO_MAXIMO",
                    "El plazo máximo, incluidas las bajas, se identifica sin inferir la improcedencia de la reincorporación.");
        }

        if (!Boolean.TRUE.equals(contexto.getCalendarioReincorporacionComprobado())
                || contexto.getDentroCalendarioReincorporacion() == null) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn66(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "RN66_REQUIERE_REVISION_CALENDARIO",
                    "No existe evidencia verificable del calendario institucional de reincorporación.");
            return;
        }
        if (!Boolean.TRUE.equals(contexto.getDentroCalendarioReincorporacion())) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION);
            registrarTrazaReincorporacionRn66(resultado, contexto, carga, null, "RN66_FUERA_CALENDARIO",
                    "El contexto reporta reincorporación fuera de calendario; RN-6.6 no infiere un proceso administrativo.");
            return;
        }

        if (carga.getCargaMaximaNormativa() == null) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn66(resultado, contexto, carga,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "RN66_REQUIERE_REVISION_CARGA",
                    "RN-4.2 no determinó carga máxima; RN-6.6 no infiere una ruta de reincorporación.");
            return;
        }

        int espacios = carga.getCargaMaximaNormativa().intValue();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad.getEstado() == EstadoUnidadCurricularV2.EN_CURSO) {
                marcarRevisionRn66(resultado, contexto, carga, unidad, "EN_CURSO_AL_INICIO_BAJA_SIN_CIERRE_INSTITUCIONAL");
                continue;
            }
            if (!esPendienteObligatoriaRn66(unidad, contexto.getSemestreReferencia())) continue;
            if (unidad.getEstado() == EstadoUnidadCurricularV2.NO_DETERMINADO
                    || unidad.getCausa() == CausaPendienteUnidadV2.NO_DETERMINADA) {
                marcarRevisionRn66(resultado, contexto, carga, unidad, "PENDIENTE_NO_DETERMINADA");
                continue;
            }
            String disponibilidad = disponibilidadRn65(unidad);
            if ("SIN_OFERTA".equals(disponibilidad)) {
                agregarIdUnico(carga.getUdsPendientesSinOfertaRn66(), unidad.getIdPrograma());
                registrarDecisionRn66(resultado, contexto, unidad, CodigoMotivoCurricularV2.SIN_OFERTA,
                        "PENDIENTE_OBLIGATORIA_SIN_OFERTA", disponibilidad);
                continue;
            }
            if ("SIN_CUPO".equals(disponibilidad)) {
                agregarIdUnico(carga.getUdsPendientesSinCupoRn66(), unidad.getIdPrograma());
                registrarDecisionRn66(resultado, contexto, unidad, CodigoMotivoCurricularV2.SIN_CUPO,
                        "PENDIENTE_OBLIGATORIA_SIN_CUPO", disponibilidad);
                continue;
            }
            if ("REQUIERE_REVISION".equals(disponibilidad)) {
                marcarRevisionRn66(resultado, contexto, carga, unidad, "OFERTA_O_CAPACIDAD_NO_DETERMINADA");
                continue;
            }
            agregarIdUnico(carga.getUdsPendientesPrioritariasRn66(), unidad.getIdPrograma());
            if (espacios > 0) {
                agregarIdUnico(carga.getUdsRutaReincorporacionRn66(), unidad.getIdPrograma());
                espacios--;
            } else {
                marcarRevisionRn66(resultado, contexto, carga, unidad, "CARGA_MAXIMA_SIN_DESEMPATE_APROBADO");
            }
            registrarDecisionRn66(resultado, contexto, unidad, motivoPendienteRn65(unidad),
                    "PENDIENTE_OBLIGATORIA_PRIORITARIA", disponibilidad);
        }

        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (!esPosteriorCandidataRn66(unidad, contexto.getSemestreReferencia())) continue;
            if (Boolean.TRUE.equals(carga.getAplicaRn64()) && esObligatoria(unidad)) {
                agregarUnico(carga.getUdsExcluidas(), obtenerLlaveUnidad(unidad) + ":RN_6_4");
                registrarDecisionRn66(resultado, contexto, unidad, CodigoMotivoCurricularV2.REZAGO_SEVERO,
                        "POSTERIOR_EXCLUIDA_RN64", "RN_6_4");
                continue;
            }
            if (Boolean.TRUE.equals(unidad.getSeriacionDirectaConocida())
                    || Boolean.TRUE.equals(unidad.getSeriacionIndirectaConocida())) {
                agregarIdUnico(carga.getUdsPosterioresBloqueadasSeriacionRn66(), unidad.getIdPrograma());
                registrarDecisionRn66(resultado, contexto, unidad, CodigoMotivoCurricularV2.SERIACION_DIRECTA,
                        "POSTERIOR_NO_COMPATIBLE_SERIACION", "SERIACION");
                continue;
            }
            if (Boolean.TRUE.equals(unidad.getSeriacionConocida())
                    || !Boolean.TRUE.equals(contexto.getSeriacionComprobada())) {
                agregarIdUnico(carga.getUdsPosterioresRevisionSeriacionRn66(), unidad.getIdPrograma());
                marcarRevisionRn66(resultado, contexto, carga, unidad, "SERIACION_NO_DETERMINABLE");
                continue;
            }
            String disponibilidad = disponibilidadRn65(unidad);
            if (!"DISPONIBLE".equals(disponibilidad)) {
                registrarDecisionRn66(resultado, contexto, unidad,
                        "SIN_CUPO".equals(disponibilidad) ? CodigoMotivoCurricularV2.SIN_CUPO
                                : ("SIN_OFERTA".equals(disponibilidad) ? CodigoMotivoCurricularV2.SIN_OFERTA
                                : CodigoMotivoCurricularV2.DATOS_INSUFICIENTES),
                        "POSTERIOR_NO_DISPONIBLE", disponibilidad);
                if ("REQUIERE_REVISION".equals(disponibilidad)) marcarRevisionRn66(resultado, contexto, carga, unidad, disponibilidad);
                continue;
            }
            if (espacios <= 0) {
                registrarDecisionRn66(resultado, contexto, unidad, null, "POSTERIOR_FUERA_POR_CARGA", "CARGA_MAXIMA");
                continue;
            }
            agregarIdUnico(carga.getUdsPosterioresCompatiblesRn66(), unidad.getIdPrograma());
            agregarIdUnico(carga.getUdsRutaReincorporacionRn66(), unidad.getIdPrograma());
            espacios--;
            registrarDecisionRn66(resultado, contexto, unidad, null, "POSTERIOR_COMPATIBLE", "DISPONIBLE");
        }

        carga.setCargaDisponibleRegularizacion(Integer.valueOf(Math.max(0, espacios)));
        if (Boolean.TRUE.equals(carga.getAplicaCtr01())) carga.setRn61ConsumidaRn66(Boolean.TRUE);
        if (Boolean.TRUE.equals(carga.getAplicaRn64())) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REINCORPORACION_RESTRINGIDA_RN64);
        } else if (Boolean.TRUE.equals(carga.getAplicaCtr01())) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REINCORPORACION_SUJETA_RN61);
        } else if (!carga.getUdsRutaReincorporacionRn66().isEmpty()) {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REINCORPORACION_PROCEDE_CON_RUTA_REGULARIZACION);
        } else {
            carga.setEstadoReincorporacionRn66(EstadoReincorporacionBajaTemporalV2.REINCORPORACION_PROCEDE_EN_SOMBRA);
        }
        registrarTrazaReincorporacionRn66(resultado, contexto, carga,
                Boolean.TRUE.equals(carga.getRequiereRevision()) ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                "RN66_" + carga.getEstadoReincorporacionRn66(), construirEvidenciaRn66(carga));
    }

    private void copiarTrayectoriaReincorporacionRn66(EvaluacionCargaAcademicaV2DTO carga,
            ContextoEvaluacionCurricularV2DTO contexto) {
        carga.setCondicionTrayectoriaRn66("REINCORPORACION_TRAS_BAJA_TEMPORAL");
        carga.setPeriodoInicioBajaTemporalRn66(contexto.getPeriodoInicioBajaTemporal());
        carga.setPeriodoFinBajaTemporalRn66(contexto.getPeriodoFinBajaTemporal());
        carga.setUltimoPeriodoEfectivamenteCursadoRn66(contexto.getUltimoPeriodoCursado());
        carga.setSemestreEfectivoPrevioBajaRn66(contexto.getSemestreEfectivoPrevioBaja());
        carga.setSemestreObjetivoRn66(contexto.getSemestreReferencia());
        carga.setDentroCalendarioReincorporacionRn66(contexto.getDentroCalendarioReincorporacion());
    }

    private boolean trayectoriaPreviaBajaCompleta(ContextoEvaluacionCurricularV2DTO contexto) {
        return textoPresente(contexto.getUltimoPeriodoCursado())
                && textoPresente(contexto.getPeriodoInicioBajaTemporal())
                && textoPresente(contexto.getPeriodoFinBajaTemporal())
                && contexto.getSemestreEfectivoPrevioBaja() != null
                && contexto.getSemestreReferencia() != null;
    }

    private boolean esPendienteObligatoriaRn66(UnidadEvaluacionCurricularV2DTO unidad, Integer semestreObjetivo) {
        return esObligatoria(unidad) && esPendiente(unidad)
                && unidad.getEstado() != EstadoUnidadCurricularV2.BAJA_PARCIAL
                && unidad.getSemestre() != null && unidad.getSemestre().intValue() <= semestreObjetivo.intValue();
    }

    private boolean esPosteriorCandidataRn66(UnidadEvaluacionCurricularV2DTO unidad, Integer semestreObjetivo) {
        return unidad.getEstado() == EstadoUnidadCurricularV2.NO_INSCRITA
                && unidad.getSemestre() != null && unidad.getSemestre().intValue() > semestreObjetivo.intValue();
    }

    private void marcarRevisionRn66(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO unidad, String causa) {
        carga.setRequiereRevision(Boolean.TRUE);
        if (unidad != null) registrarDecisionRn66(resultado, contexto, unidad,
                CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "REQUIERE_REVISION", causa);
    }

    private void registrarDecisionRn66(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, UnidadEvaluacionCurricularV2DTO unidad,
            CodigoMotivoCurricularV2 motivo, String decision, String detalle) {
        if (unidad == null) return;
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga != null) carga.getDecisionesPorUdRn66().add(obtenerLlaveUnidad(unidad) + ":" + decision + ":" + detalle);
        DecisionUnidadCurricularV2DTO unidadDecision = buscarDecision(resultado, unidad.getIdPrograma());
        if (unidadDecision != null) {
            if ("PENDIENTE_OBLIGATORIA_PRIORITARIA".equals(decision)) {
                unidadDecision.setPrioritaria(Boolean.TRUE);
                agregarUnico(unidadDecision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.PRIORITARIA);
            }
            if (motivo != null) agregarUnico(unidadDecision.getMotivos(), motivo);
            agregarExplicacion(unidadDecision, "RN-6.6: " + decision + " (" + detalle + ").");
            TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_6_6, motivo, 85,
                    "RN66_" + decision, obtenerLlaveUnidad(unidad) + "; " + detalle);
            unidadDecision.getTrazas().add(traza);
            resultado.getTrazas().add(traza);
        }
    }

    private String construirEvidenciaRn66(EvaluacionCargaAcademicaV2DTO carga) {
        return "Trayectoria=" + carga.getCondicionTrayectoriaRn66()
                + "; inicioBaja=" + carga.getPeriodoInicioBajaTemporalRn66()
                + "; finBaja=" + carga.getPeriodoFinBajaTemporalRn66()
                + "; ultimoEfectivo=" + carga.getUltimoPeriodoEfectivamenteCursadoRn66()
                + "; semestreEfectivo=" + carga.getSemestreEfectivoPrevioBajaRn66()
                + "; semestreObjetivo=" + carga.getSemestreObjetivoRn66()
                + "; calendario=" + carga.getDentroCalendarioReincorporacionRn66()
                + "; pendientes=" + carga.getUdsPendientesPrioritariasRn66()
                + "; compatibles=" + carga.getUdsPosterioresCompatiblesRn66()
                + "; rn64=" + carga.getAplicaRn64()
                + "; rn61=" + carga.getRn61ConsumidaRn66();
    }

    private void registrarTrazaReincorporacionRn66(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            CodigoMotivoCurricularV2 motivo, String estado, String detalle) {
        agregarUnico(carga.getEvidencias(), detalle);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_6, motivo, 85, estado, detalle);
    }

    /**
     * Fase 6D: RN-6.7 evalua bajas parciales por UD. Conserva su causa y no
     * altera la situacion academica, la oferta ni la reinscripcion productiva.
     */
    private void evaluarReincorporacionTrasBajaParcial(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_7);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_6_7);

        List<UnidadEvaluacionCurricularV2DTO> bajasParciales = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad.getEstado() == EstadoUnidadCurricularV2.BAJA_PARCIAL
                    || unidad.getCausa() == CausaPendienteUnidadV2.BAJA_PARCIAL) bajasParciales.add(unidad);
        }
        if (bajasParciales.isEmpty()) {
            carga.setAplicaRn67(Boolean.FALSE);
            carga.setEstadoReincorporacionRn67(EstadoReincorporacionBajaParcialV2.NO_APLICA);
            registrarTrazaReincorporacionRn67(resultado, contexto, carga, null, "RN67_NO_APLICA",
                    "No existen UD con baja parcial en el contexto normalizado.");
            return;
        }

        carga.setAplicaRn67(Boolean.TRUE);
        carga.setCondicionTrayectoriaRn67("REINCORPORACION_TRAS_BAJA_PARCIAL");
        agregarUnico(resultado.getCondicionesDetectadas(), CondicionCurricularV2.REINCORPORACION_TRAS_BAJA_PARCIAL);
        if (contexto.getSemestreReferencia() == null || carga.getCargaMaximaNormativa() == null) {
            carga.setEstadoReincorporacionRn67(EstadoReincorporacionBajaParcialV2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn67(resultado, contexto, carga, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    "RN67_REQUIERE_REVISION_CONTEXTO", "No existe semestre objetivo o carga máxima verificable.");
            return;
        }

        int espacios = carga.getCargaMaximaNormativa().intValue();
        boolean prioridadRn67 = false;
        boolean rutaCompatible = false;
        boolean sancionImpeditiva = false;
        for (UnidadEvaluacionCurricularV2DTO unidad : bajasParciales) {
            agregarIdUnico(carga.getUdsBajaParcialRn67(), unidad.getIdPrograma());
            if (!Boolean.TRUE.equals(unidad.getBajaParcialFormalComprobada())) {
                marcarRevisionRn67(resultado, contexto, carga, unidad, "BAJA_PARCIAL_FORMAL_NO_COMPROBADA");
                continue;
            }
            if (Boolean.TRUE.equals(unidad.getBajaParcialDerivadaSancion())) {
                agregarIdUnico(carga.getUdsBajaParcialSancionRn67(), unidad.getIdPrograma());
                if (!Boolean.TRUE.equals(unidad.getResolucionSancionConocida())) {
                    marcarRevisionRn67(resultado, contexto, carga, unidad, "RESOLUCION_SANCION_NO_DETERMINADA");
                    continue;
                }
                if (!Boolean.TRUE.equals(unidad.getResolucionSancionPermiteFlexibilizacion())) {
                    sancionImpeditiva = true;
                    registrarDecisionRn67(resultado, contexto, unidad, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                            "RESTRINGIDA_POR_SANCION", "SIN_FLEXIBILIZACION_AUTOMATICA");
                    continue;
                }
            } else if (unidad.getBajaParcialDerivadaSancion() == null) {
                marcarRevisionRn67(resultado, contexto, carga, unidad, "SANCION_NO_DETERMINADA");
                continue;
            }

            if (esElectiva(unidad)) {
                marcarRevisionRn67(resultado, contexto, carga, unidad, "ELECTIVA_SIN_REGLA_RN67_EXPRESA");
                continue;
            }
            if (!esObligatoria(unidad) && !esOptativa(unidad)) {
                marcarRevisionRn67(resultado, contexto, carga, unidad, "TIPO_UD_NO_DETERMINADO");
                continue;
            }

            String disponibilidad = disponibilidadRn65(unidad);
            if ("SIN_OFERTA".equals(disponibilidad)) {
                agregarIdUnico(carga.getUdsBajaParcialSinOfertaRn67(), unidad.getIdPrograma());
                registrarDecisionRn67(resultado, contexto, unidad, CodigoMotivoCurricularV2.SIN_OFERTA,
                        esObligatoria(unidad) ? "OBLIGATORIA_BAJA_PARCIAL_SIN_OFERTA" : "OPTATIVA_BAJA_PARCIAL_SIN_OFERTA",
                        disponibilidad);
                continue;
            }
            if ("SIN_CUPO".equals(disponibilidad)) {
                agregarIdUnico(carga.getUdsBajaParcialSinCupoRn67(), unidad.getIdPrograma());
                registrarDecisionRn67(resultado, contexto, unidad, CodigoMotivoCurricularV2.SIN_CUPO,
                        "BAJA_PARCIAL_SIN_CUPO", disponibilidad);
                continue;
            }
            if ("REQUIERE_REVISION".equals(disponibilidad)) {
                marcarRevisionRn67(resultado, contexto, carga, unidad, "OFERTA_O_CAPACIDAD_NO_DETERMINADA");
                continue;
            }

            boolean prioritaria = esObligatoria(unidad);
            if (esOptativa(unidad)) {
                if (unidad.getNecesariaParaRequerimientoCurricular() == null) {
                    marcarRevisionRn67(resultado, contexto, carga, unidad, "REQUERIMIENTO_OPTATIVO_NO_DETERMINADO");
                    continue;
                }
                prioritaria = Boolean.TRUE.equals(unidad.getNecesariaParaRequerimientoCurricular());
            }
            if (!prioritaria) {
                registrarDecisionRn67(resultado, contexto, unidad, null, "OPTATIVA_NO_REQUIERE_PRIORIDAD", "OFERTADA");
                continue;
            }

            agregarIdUnico(carga.getUdsBajaParcialPrioritariasRn67(), unidad.getIdPrograma());
            prioridadRn67 = true;
            if (espacios > 0) {
                agregarIdUnico(carga.getUdsRutaReincorporacionRn67(), unidad.getIdPrograma());
                espacios--;
            } else {
                marcarRevisionRn67(resultado, contexto, carga, unidad, "CARGA_MAXIMA_SIN_DESEMPATE_APROBADO");
            }
            registrarDecisionRn67(resultado, contexto, unidad, CodigoMotivoCurricularV2.PENDIENTE_NO_CURSADA,
                    "PRIORITARIA_POR_BAJA_PARCIAL", "OFERTADA");
        }

        boolean conflictoConRn63 = prioridadRn67 && Boolean.TRUE.equals(carga.getAplicaRn63())
                && !carga.getUdsReprobadasPrioritarias().isEmpty();
        if (conflictoConRn63) {
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaReincorporacionRn67(resultado, contexto, carga, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    "RN67_REQUIERE_REVISION_ORDEN_PRIORIDADES",
                    "Concurren prioridades RN-6.3 y RN-6.7 sin desempate normativo explícito.");
        }

        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (!esPosteriorCandidataRn67(unidad, contexto.getSemestreReferencia())) continue;
            if (Boolean.TRUE.equals(carga.getAplicaRn64()) && esObligatoria(unidad)) {
                registrarDecisionRn67(resultado, contexto, unidad, CodigoMotivoCurricularV2.REZAGO_SEVERO,
                        "POSTERIOR_EXCLUIDA_RN64", "RN_6_4");
                continue;
            }
            if (Boolean.TRUE.equals(unidad.getSeriacionDirectaConocida())
                    || Boolean.TRUE.equals(unidad.getSeriacionIndirectaConocida())) {
                agregarIdUnico(carga.getUdsPosterioresBloqueadasSeriacionRn67(), unidad.getIdPrograma());
                registrarDecisionRn67(resultado, contexto, unidad, CodigoMotivoCurricularV2.SERIACION_DIRECTA,
                        "POSTERIOR_NO_COMPATIBLE_SERIACION", "SERIACION");
                continue;
            }
            if (Boolean.TRUE.equals(unidad.getSeriacionConocida())
                    || !Boolean.TRUE.equals(contexto.getSeriacionComprobada())) {
                marcarRevisionRn67(resultado, contexto, carga, unidad, "SERIACION_NO_DETERMINABLE");
                continue;
            }
            String disponibilidad = disponibilidadRn65(unidad);
            if (!"DISPONIBLE".equals(disponibilidad)) {
                registrarDecisionRn67(resultado, contexto, unidad,
                        "SIN_CUPO".equals(disponibilidad) ? CodigoMotivoCurricularV2.SIN_CUPO
                                : ("SIN_OFERTA".equals(disponibilidad) ? CodigoMotivoCurricularV2.SIN_OFERTA
                                : CodigoMotivoCurricularV2.DATOS_INSUFICIENTES),
                        "POSTERIOR_NO_DISPONIBLE", disponibilidad);
                if ("REQUIERE_REVISION".equals(disponibilidad)) marcarRevisionRn67(resultado, contexto, carga, unidad, disponibilidad);
                continue;
            }
            if (espacios <= 0) {
                registrarDecisionRn67(resultado, contexto, unidad, null, "POSTERIOR_FUERA_POR_CARGA", "CARGA_MAXIMA");
                continue;
            }
            agregarIdUnico(carga.getUdsPosterioresCompatiblesRn67(), unidad.getIdPrograma());
            agregarIdUnico(carga.getUdsRutaReincorporacionRn67(), unidad.getIdPrograma());
            espacios--;
            rutaCompatible = true;
            registrarDecisionRn67(resultado, contexto, unidad, null, "POSTERIOR_COMPATIBLE", "DISPONIBLE");
        }

        if (Boolean.TRUE.equals(carga.getAplicaCtr01())) carga.setRn61ConsumidaRn67(Boolean.TRUE);
        if (Boolean.TRUE.equals(carga.getAplicaRn64())) {
            carga.setEstadoReincorporacionRn67(EstadoReincorporacionBajaParcialV2.REINCORPORACION_RESTRINGIDA_RN64);
        } else if (sancionImpeditiva) {
            carga.setEstadoReincorporacionRn67(EstadoReincorporacionBajaParcialV2.RESTRINGIDA_POR_SANCION);
        } else if (conflictoConRn63) {
            carga.setEstadoReincorporacionRn67(EstadoReincorporacionBajaParcialV2.REQUIERE_REVISION);
        } else if (Boolean.TRUE.equals(carga.getAplicaCtr01())) {
            carga.setEstadoReincorporacionRn67(EstadoReincorporacionBajaParcialV2.REINCORPORACION_SUJETA_RN61);
        } else if (prioridadRn67) {
            carga.setEstadoReincorporacionRn67(EstadoReincorporacionBajaParcialV2.REINCORPORACION_CON_PRIORIDAD_RECUPERACION);
        } else if (rutaCompatible) {
            carga.setEstadoReincorporacionRn67(EstadoReincorporacionBajaParcialV2.REINCORPORACION_CON_RUTA_COMPATIBLE);
        } else if (Boolean.TRUE.equals(carga.getRequiereRevision())) {
            carga.setEstadoReincorporacionRn67(EstadoReincorporacionBajaParcialV2.REQUIERE_REVISION);
        } else {
            carga.setEstadoReincorporacionRn67(EstadoReincorporacionBajaParcialV2.REINCORPORACION_BAJA_PARCIAL_PROCEDE_EN_SOMBRA);
        }
        registrarTrazaReincorporacionRn67(resultado, contexto, carga,
                Boolean.TRUE.equals(carga.getRequiereRevision()) ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                "RN67_" + carga.getEstadoReincorporacionRn67(), construirEvidenciaRn67(carga));
    }

    private boolean esPosteriorCandidataRn67(UnidadEvaluacionCurricularV2DTO unidad, Integer semestreObjetivo) {
        return unidad.getEstado() == EstadoUnidadCurricularV2.NO_INSCRITA
                && unidad.getSemestre() != null && unidad.getSemestre().intValue() > semestreObjetivo.intValue();
    }

    private boolean esOptativa(UnidadEvaluacionCurricularV2DTO unidad) {
        return unidad.getTipoUd() != null && unidad.getTipoUd().toUpperCase().contains("OPTATIVA")
                && !esTipoAmbiguoFase7(unidad);
    }

    private boolean esElectiva(UnidadEvaluacionCurricularV2DTO unidad) {
        return unidad.getTipoUd() != null && unidad.getTipoUd().toUpperCase().contains("ELECTIVA")
                && !esTipoAmbiguoFase7(unidad);
    }

    private boolean esTipoAmbiguoFase7(UnidadEvaluacionCurricularV2DTO unidad) {
        if (unidad == null || unidad.getTipoUd() == null) return false;
        String tipo = unidad.getTipoUd().toUpperCase();
        return tipo.contains("OPTATIVA") && tipo.contains("ELECTIVA");
    }

    private void marcarRevisionRn67(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO unidad, String causa) {
        carga.setRequiereRevision(Boolean.TRUE);
        agregarIdUnico(carga.getUdsBajaParcialRevisionRn67(), unidad == null ? null : unidad.getIdPrograma());
        if (unidad != null) registrarDecisionRn67(resultado, contexto, unidad,
                CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "REQUIERE_REVISION", causa);
    }

    private void registrarDecisionRn67(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, UnidadEvaluacionCurricularV2DTO unidad,
            CodigoMotivoCurricularV2 motivo, String decision, String detalle) {
        if (unidad == null) return;
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga != null) carga.getDecisionesPorUdRn67().add(obtenerLlaveUnidad(unidad) + ":" + decision + ":" + detalle);
        DecisionUnidadCurricularV2DTO unidadDecision = buscarDecision(resultado, unidad.getIdPrograma());
        if (unidadDecision != null) {
            if ("PRIORITARIA_POR_BAJA_PARCIAL".equals(decision)) {
                unidadDecision.setPrioritaria(Boolean.TRUE);
                agregarUnico(unidadDecision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.PRIORITARIA);
            }
            if (motivo != null) agregarUnico(unidadDecision.getMotivos(), motivo);
            agregarExplicacion(unidadDecision, "RN-6.7: " + decision + " (" + detalle + ").");
            TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_6_7, motivo, 85,
                    "RN67_" + decision, obtenerLlaveUnidad(unidad) + "; " + detalle);
            unidadDecision.getTrazas().add(traza);
            resultado.getTrazas().add(traza);
        }
    }

    private String construirEvidenciaRn67(EvaluacionCargaAcademicaV2DTO carga) {
        return "Trayectoria=" + carga.getCondicionTrayectoriaRn67()
                + "; bajasParciales=" + carga.getUdsBajaParcialRn67()
                + "; prioritarias=" + carga.getUdsBajaParcialPrioritariasRn67()
                + "; sinOferta=" + carga.getUdsBajaParcialSinOfertaRn67()
                + "; sinCupo=" + carga.getUdsBajaParcialSinCupoRn67()
                + "; sancion=" + carga.getUdsBajaParcialSancionRn67()
                + "; posteriores=" + carga.getUdsPosterioresCompatiblesRn67()
                + "; rn64=" + carga.getAplicaRn64()
                + "; rn61=" + carga.getRn61ConsumidaRn67();
    }

    private void registrarTrazaReincorporacionRn67(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            CodigoMotivoCurricularV2 motivo, String estado, String detalle) {
        agregarUnico(carga.getEvidencias(), detalle);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_7, motivo, 85, estado, detalle);
    }

    private void agregarIdUnico(List<Long> destino, Long id) {
        if (id != null && !destino.contains(id)) destino.add(id);
    }

    /**
     * RN-6.8 observa el cierre en sombra. La situacion vigente ya fue calculada
     * por RN-1.2; aqui solo se conserva el delta y se compone la continuidad non-par.
     */
    private void evaluarActualizacionTrayectoriaTrasRegularizacion(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_8);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_6_8);

        if (contexto.getCierrePeriodoAcademicoComprobado() == null) {
            carga.setAplicaRn68(null);
            carga.setEstadoActualizacionTrayectoriaRn68(EstadoActualizacionTrayectoriaRn68V2.REQUIERE_REVISION);
            carga.setRequiereRevision(Boolean.TRUE);
            registrarTrazaRn68(resultado, contexto, carga, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    "RN68_REQUIERE_REVISION_CIERRE", "No existe evidencia verificable del cierre del periodo academico.");
            return;
        }
        if (!Boolean.TRUE.equals(contexto.getCierrePeriodoAcademicoComprobado())) {
            carga.setAplicaRn68(Boolean.FALSE);
            carga.setEstadoActualizacionTrayectoriaRn68(EstadoActualizacionTrayectoriaRn68V2.NO_APLICA_ESPECIFICO_NON_PAR);
            registrarTrazaRn68(resultado, contexto, carga, null, "RN68_NO_APLICA_SIN_CIERRE",
                    "RN-6.8 no genera una actualizacion de cierre sin evidencia de cierre academico.");
            return;
        }

        carga.setAplicaRn68(Boolean.TRUE);
        carga.setSemestreCurricularCierreRn68(contexto.getSemestreCurricularCierre());
        carga.setSituacionAcademicaAntesCierreRn68(contexto.getSituacionAcademicaAntesCierre());
        carga.setSituacionAcademicaVigenteRn68(resultado.getSituacionAcademicaCalculada());
        carga.setTotalReprobadasAntesCierreRn68(contexto.getTotalReprobadasAntesCierre());
        carga.setTotalReprobadasVigentesRn68(resultado.getCantidadReprobadasVigentes());
        carga.setRn64AntesCierreRn68(contexto.getRn64AntesCierre());
        carga.setRn64VigenteRn68(carga.getAplicaRn64());

        Integer semestreCierre = contexto.getSemestreCurricularCierre();
        if (semestreCierre == null) {
            marcarRevisionRn68(resultado, contexto, carga, null, "SEMESTRE_CURRICULAR_CIERRE_NO_DETERMINADO");
            finalizarRn68(resultado, contexto, carga, EstadoActualizacionTrayectoriaRn68V2.REQUIERE_REVISION);
            return;
        }
        if (semestreCierre.intValue() % 2 == 0) {
            finalizarRn68(resultado, contexto, carga, EstadoActualizacionTrayectoriaRn68V2.NO_APLICA_ESPECIFICO_NON_PAR);
            return;
        }
        if (contexto.getSemestreReferencia() == null
                || contexto.getSemestreReferencia().intValue() != semestreCierre.intValue() + 1
                || contexto.getSemestreReferencia().intValue() % 2 != 0) {
            marcarRevisionRn68(resultado, contexto, carga, null, "SEMESTRE_PAR_SIGUIENTE_NO_DETERMINABLE_POR_TRAYECTORIA");
            finalizarRn68(resultado, contexto, carga, EstadoActualizacionTrayectoriaRn68V2.REQUIERE_REVISION);
            return;
        }
        carga.setSemestreObjetivoParRn68(contexto.getSemestreReferencia());
        if (!Boolean.TRUE.equals(contexto.getBloqueosContinuidadAntesComprobados())) {
            marcarRevisionRn68(resultado, contexto, carga, null, "BLOQUEOS_PREVIOS_NO_COMPROBADOS");
            finalizarRn68(resultado, contexto, carga, EstadoActualizacionTrayectoriaRn68V2.REQUIERE_REVISION);
            return;
        }

        boolean existenBloqueosPrevios = false;
        boolean bloqueosPendientes = false;
        int reprobadasParAnterior = 0;
        int espacios = carga.getCargaMaximaNormativa() == null ? 0 : carga.getCargaMaximaNormativa().intValue();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (Boolean.TRUE.equals(unidad.getAcreditadaDurantePeriodo())) {
                if (unidad.getEstado() == EstadoUnidadCurricularV2.ACREDITADA) {
                    agregarIdUnico(carga.getUdsAcreditadasDuranteCierreRn68(), unidad.getIdPrograma());
                } else {
                    marcarRevisionRn68(resultado, contexto, carga, unidad, "ACREDITACION_CIERRE_NO_COINCIDE_CON_ESTADO_VIGENTE");
                }
            }
            if (Boolean.TRUE.equals(unidad.getImpediaContinuidadAntesCierre())) {
                existenBloqueosPrevios = true;
                if (unidad.getEstadoAnteriorCierre() == null || !textoPresente(unidad.getEvidenciaContinuidadAntesCierre())) {
                    bloqueosPendientes = true;
                    marcarRevisionRn68(resultado, contexto, carga, unidad, "EVIDENCIA_BLOQUEO_PREVIO_INSUFICIENTE");
                } else if (unidad.getEstado() == EstadoUnidadCurricularV2.ACREDITADA) {
                    agregarIdUnico(carga.getUdsBloqueosResueltosRn68(), unidad.getIdPrograma());
                    registrarDecisionRn68(resultado, contexto, unidad, null, "BLOQUEO_CONTINUIDAD_RESUELTO", "ACREDITADA_AL_CIERRE");
                } else {
                    bloqueosPendientes = true;
                    agregarIdUnico(carga.getUdsBloqueosPendientesRn68(), unidad.getIdPrograma());
                    registrarDecisionRn68(resultado, contexto, unidad,
                            unidad.getEstado() == EstadoUnidadCurricularV2.NO_DETERMINADO
                                    ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                            "BLOQUEO_CONTINUIDAD_PERSISTE", String.valueOf(unidad.getEstado()));
                }
            }
            if (esReprobacionVigentePrevia(unidad)) {
                if (unidad.getCorrespondeSemestreParInmediatoAnterior() == null) {
                    marcarRevisionRn68(resultado, contexto, carga, unidad, "SEMESTRE_PAR_INMEDIATO_ANTERIOR_NO_DETERMINABLE");
                } else if (Boolean.TRUE.equals(unidad.getCorrespondeSemestreParInmediatoAnterior())) {
                    reprobadasParAnterior++;
                    String disponibilidad = disponibilidadRn65(unidad);
                    if ("DISPONIBLE".equals(disponibilidad)
                            && carga.getUdsReprobadasOfertadas().contains(unidad.getIdPrograma())) {
                        // RN-6.8 delimita el subconjunto non-par; RN-6.3 conserva la prioridad.
                        agregarIdUnico(carga.getUdsReprobadasParAnteriorPrioritariasRn68(), unidad.getIdPrograma());
                    } else if ("DISPONIBLE".equals(disponibilidad)) {
                        marcarRevisionRn68(resultado, contexto, carga, unidad, "PRIORIDAD_RN63_NO_DETERMINABLE");
                    } else if ("SIN_OFERTA".equals(disponibilidad)) {
                        agregarIdUnico(carga.getUdsReprobadasParAnteriorSinOfertaRn68(), unidad.getIdPrograma());
                    } else if ("SIN_CUPO".equals(disponibilidad)) {
                        agregarIdUnico(carga.getUdsReprobadasParAnteriorSinCupoRn68(), unidad.getIdPrograma());
                    } else {
                        marcarRevisionRn68(resultado, contexto, carga, unidad, "OFERTA_O_CUPO_REPROBADA_NO_DETERMINADO");
                    }
                }
            }
        }

        carga.setRegularizacionAlcanzadaRn68(Boolean.valueOf(existenBloqueosPrevios && !bloqueosPendientes));
        if (Boolean.TRUE.equals(carga.getAplicaRn64()) || reprobadasParAnterior >= 4) {
            finalizarRn68(resultado, contexto, carga, EstadoActualizacionTrayectoriaRn68V2.RESTRINGIDA_POR_RN64);
            return;
        }
        if (reprobadasParAnterior > 3) {
            marcarRevisionRn68(resultado, contexto, carga, null, "CONTEO_REPROBADAS_PAR_ANTERIOR_INCONSISTENTE_CON_RN64");
            finalizarRn68(resultado, contexto, carga, EstadoActualizacionTrayectoriaRn68V2.REQUIERE_REVISION);
            return;
        }
        if (reprobadasParAnterior > 0) {
            for (Long id : carga.getUdsReprobadasParAnteriorPrioritariasRn68()) {
                if (espacios <= 0) break;
                agregarIdUnico(carga.getUdsRutaCierreRn68(), id);
                espacios--;
            }
        }

        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad.getEstado() != EstadoUnidadCurricularV2.NO_INSCRITA
                    || unidad.getSemestre() == null
                    || unidad.getSemestre().intValue() != contexto.getSemestreReferencia().intValue()) continue;
            if (esOptativa(unidad) || esElectiva(unidad)) {
                marcarRevisionRn68(resultado, contexto, carga, unidad, "TIPO_UD_REQUIERE_REGLA_RN7");
                continue;
            }
            if (!esObligatoria(unidad)) {
                marcarRevisionRn68(resultado, contexto, carga, unidad, "TIPO_UD_NO_DETERMINADO");
                continue;
            }
            if (Boolean.TRUE.equals(unidad.getSeriacionDirectaConocida())
                    || Boolean.TRUE.equals(unidad.getSeriacionIndirectaConocida())) {
                agregarIdUnico(carga.getUdsParBloqueadasSeriacionRn68(), unidad.getIdPrograma());
                registrarDecisionRn68(resultado, contexto, unidad, CodigoMotivoCurricularV2.SERIACION_DIRECTA,
                        "UD_PAR_NO_COMPATIBLE_SERIACION", "SERIACION");
                continue;
            }
            if (Boolean.TRUE.equals(unidad.getSeriacionConocida())
                    || !Boolean.TRUE.equals(contexto.getSeriacionComprobada())) {
                marcarRevisionRn68(resultado, contexto, carga, unidad, "SERIACION_NO_DETERMINABLE");
                continue;
            }
            String disponibilidad = disponibilidadRn65(unidad);
            if (!"DISPONIBLE".equals(disponibilidad)) {
                registrarDecisionRn68(resultado, contexto, unidad,
                        "SIN_OFERTA".equals(disponibilidad) ? CodigoMotivoCurricularV2.SIN_OFERTA
                                : ("SIN_CUPO".equals(disponibilidad) ? CodigoMotivoCurricularV2.SIN_CUPO
                                : CodigoMotivoCurricularV2.DATOS_INSUFICIENTES),
                        "UD_PAR_NO_DISPONIBLE", disponibilidad);
                if ("REQUIERE_REVISION".equals(disponibilidad)) marcarRevisionRn68(resultado, contexto, carga, unidad, disponibilidad);
                continue;
            }
            if (espacios <= 0) {
                registrarDecisionRn68(resultado, contexto, unidad, null, "UD_PAR_FUERA_POR_CARGA", "CARGA_MAXIMA");
                continue;
            }
            agregarIdUnico(carga.getUdsParCompatiblesRn68(), unidad.getIdPrograma());
            agregarIdUnico(carga.getUdsRutaCierreRn68(), unidad.getIdPrograma());
            espacios--;
            registrarDecisionRn68(resultado, contexto, unidad, null, "UD_PAR_COMPATIBLE", "DISPONIBLE");
        }

        if (Boolean.TRUE.equals(carga.getRequiereRevisionRn68())) {
            finalizarRn68(resultado, contexto, carga, EstadoActualizacionTrayectoriaRn68V2.REQUIERE_REVISION);
        } else if (!carga.getUdsReprobadasParAnteriorPrioritariasRn68().isEmpty()) {
            finalizarRn68(resultado, contexto, carga, EstadoActualizacionTrayectoriaRn68V2.CONTINUIDAD_CON_PRIORIDAD_REGULARIZACION);
        } else if (!carga.getUdsParCompatiblesRn68().isEmpty()) {
            finalizarRn68(resultado, contexto, carga, EstadoActualizacionTrayectoriaRn68V2.CONTINUIDAD_A_SEMESTRE_PAR_PROCEDE_EN_SOMBRA);
        } else if (Boolean.TRUE.equals(carga.getRegularizacionAlcanzadaRn68())) {
            finalizarRn68(resultado, contexto, carga, EstadoActualizacionTrayectoriaRn68V2.REGULARIZACION_RECONOCIDA_EN_SOMBRA);
        } else {
            finalizarRn68(resultado, contexto, carga, EstadoActualizacionTrayectoriaRn68V2.RECALCULO_REALIZADO_EN_SOMBRA);
        }
    }

    private void marcarRevisionRn68(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO unidad, String causa) {
        carga.setRequiereRevisionRn68(Boolean.TRUE);
        if (unidad != null) registrarDecisionRn68(resultado, contexto, unidad,
                CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "REQUIERE_REVISION", causa);
    }

    private void registrarDecisionRn68(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, UnidadEvaluacionCurricularV2DTO unidad,
            CodigoMotivoCurricularV2 motivo, String decision, String detalle) {
        if (unidad == null) return;
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga != null) carga.getDecisionesPorUdRn68().add(obtenerLlaveUnidad(unidad) + ":" + decision + ":" + detalle);
        DecisionUnidadCurricularV2DTO unidadDecision = buscarDecision(resultado, unidad.getIdPrograma());
        if (unidadDecision != null) {
            if ("BLOQUEO_CONTINUIDAD_RESUELTO".equals(decision) || "UD_PAR_COMPATIBLE".equals(decision)) {
                agregarUnico(unidadDecision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.HABILITADA);
            }
            if (motivo != null) agregarUnico(unidadDecision.getMotivos(), motivo);
            agregarExplicacion(unidadDecision, "RN-6.8: " + decision + " (" + detalle + ").");
            TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_6_8, motivo, 85,
                    "RN68_" + decision, obtenerLlaveUnidad(unidad) + "; " + detalle);
            unidadDecision.getTrazas().add(traza);
            resultado.getTrazas().add(traza);
        }
    }

    private void finalizarRn68(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            EstadoActualizacionTrayectoriaRn68V2 estado) {
        carga.setEstadoActualizacionTrayectoriaRn68(estado);
        String evidencia = "cierre=" + contexto.getCierrePeriodoAcademicoComprobado()
                + "; semestreCierre=" + carga.getSemestreCurricularCierreRn68()
                + "; semestrePar=" + carga.getSemestreObjetivoParRn68()
                + "; situacionAntes=" + carga.getSituacionAcademicaAntesCierreRn68()
                + "; situacionVigente=" + carga.getSituacionAcademicaVigenteRn68()
                + "; reprobadasAntes=" + carga.getTotalReprobadasAntesCierreRn68()
                + "; reprobadasVigentes=" + carga.getTotalReprobadasVigentesRn68()
                + "; rn64Antes=" + carga.getRn64AntesCierreRn68()
                + "; rn64Vigente=" + carga.getRn64VigenteRn68()
                + "; acreditadas=" + carga.getUdsAcreditadasDuranteCierreRn68()
                + "; bloqueosResueltos=" + carga.getUdsBloqueosResueltosRn68()
                + "; bloqueosPendientes=" + carga.getUdsBloqueosPendientesRn68()
                + "; prioritarias=" + carga.getUdsReprobadasParAnteriorPrioritariasRn68()
                + "; ruta=" + carga.getUdsRutaCierreRn68()
                + "; rn61=NO_REACTIVADA_NON_PAR";
        registrarTrazaRn68(resultado, contexto, carga,
                estado == EstadoActualizacionTrayectoriaRn68V2.REQUIERE_REVISION
                        ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                "RN68_" + estado, evidencia);
    }

    private void registrarTrazaRn68(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            CodigoMotivoCurricularV2 motivo, String estado, String detalle) {
        agregarUnico(carga.getEvidencias(), detalle);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_8, motivo, 85, estado, detalle);
    }

    /**
     * Fase 6F no evalua una regla nueva: ordena los resultados F6 ya calculados
     * en una causa principal, condiciones concurrentes y una sola ruta sombra.
     */
    private void consolidarFase6ReincorporacionYTrayectoria(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_9_5);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_9_5);

        boolean rn62 = Boolean.TRUE.equals(carga.getAplicaRn62());
        boolean rn65 = Boolean.TRUE.equals(carga.getAplicaRn65());
        boolean rn66 = Boolean.TRUE.equals(carga.getAplicaRn66());
        boolean conflictoPrincipal = (rn62 && (rn65 || rn66)) || (rn65 && rn66);
        boolean datosIncoherentes = Boolean.FALSE.equals(contexto.getInscripcionInicialRealizada())
                && (Boolean.TRUE.equals(contexto.getHaCursadoUnoOMasPeriodos())
                        || Boolean.TRUE.equals(contexto.getBajaTemporalFormalComprobada()));

        registrarCondicionesConcurrentesFase6(carga, resultado);
        carga.setTrayectoriaAntesFase6(construirTrayectoriaAntesFase6(carga));
        carga.setTrayectoriaDespuesFase6(construirTrayectoriaDespuesFase6(carga, resultado));

        if (conflictoPrincipal || datosIncoherentes) {
            carga.setCausaPrincipalTrayectoriaFase6(CausaPrincipalTrayectoriaV2.REQUIERE_REVISION);
            carga.setEstadoConsolidacionFase6(EstadoConsolidacionFase6V2.REQUIERE_REVISION);
            carga.setRequiereRevisionFase6(Boolean.TRUE);
            registrarDecisionConsolidacionFase6(carga, "CAUSA_PRINCIPAL_INCONSISTENTE");
            registrarTrazaFase6(resultado, contexto, carga, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
            return;
        }

        if (rn62) {
            carga.setCausaPrincipalTrayectoriaFase6(CausaPrincipalTrayectoriaV2.INSCRIPCION_INICIAL_DIFERIDA);
            agregarRutaFase6(carga, carga.getUdsSemestre2CompatiblesRn62());
        } else if (rn66) {
            carga.setCausaPrincipalTrayectoriaFase6(CausaPrincipalTrayectoriaV2.REINCORPORACION_TRAS_BAJA_TEMPORAL);
            agregarRutaFase6(carga, carga.getUdsRutaReincorporacionRn66());
        } else if (rn65) {
            carga.setCausaPrincipalTrayectoriaFase6(CausaPrincipalTrayectoriaV2.REINCORPORACION_TRAS_PERIODO_NO_CURSADO);
            agregarRutaFase6(carga, carga.getUdsRutaReincorporacionRn65());
        } else if (contexto.getInscripcionInicialRealizada() == null) {
            carga.setCausaPrincipalTrayectoriaFase6(CausaPrincipalTrayectoriaV2.NO_DETERMINADO);
            carga.setEstadoConsolidacionFase6(EstadoConsolidacionFase6V2.NO_DETERMINADO);
            registrarDecisionConsolidacionFase6(carga, "CAUSA_PRINCIPAL_NO_DETERMINADA");
            registrarTrazaFase6(resultado, contexto, carga, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
            return;
        } else {
            carga.setCausaPrincipalTrayectoriaFase6(CausaPrincipalTrayectoriaV2.NORMAL);
        }

        // RN-6.7 es una condicion por UD y puede coexistir con la causa principal.
        if (Boolean.TRUE.equals(carga.getAplicaRn67())) agregarRutaFase6(carga, carga.getUdsRutaReincorporacionRn67());
        // RN-6.8 es evento de cierre; no reemplaza la causa principal de trayectoria.
        if (Boolean.TRUE.equals(carga.getAplicaRn68())) agregarRutaFase6(carga, carga.getUdsRutaCierreRn68());

        carga.setEstadoConsolidacionFase6(determinarEstadoConsolidadoFase6(carga));
        registrarDecisionConsolidacionFase6(carga, "CAUSA=" + carga.getCausaPrincipalTrayectoriaFase6()
                + "; ESTADO=" + carga.getEstadoConsolidacionFase6()
                + "; RUTA=" + carga.getUdsRutaAcademicaConsolidadaFase6());
        registrarTrazaFase6(resultado, contexto, carga,
                carga.getEstadoConsolidacionFase6() == EstadoConsolidacionFase6V2.REQUIERE_REVISION
                        ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null);
    }

    private void registrarCondicionesConcurrentesFase6(EvaluacionCargaAcademicaV2DTO carga,
            ResultadoEvaluacionCurricularV2DTO resultado) {
        if (Boolean.TRUE.equals(carga.getAplicaRn67())) agregarUnico(carga.getCondicionesConcurrentesFase6(), "BAJA_PARCIAL");
        if (resultado.getCantidadReprobadasVigentes() != null && resultado.getCantidadReprobadasVigentes().intValue() > 0)
            agregarUnico(carga.getCondicionesConcurrentesFase6(), "REPROBADAS_VIGENTES");
        if (Boolean.TRUE.equals(carga.getAplicaRn64())) agregarUnico(carga.getCondicionesConcurrentesFase6(), "RN_6_4");
        if (Boolean.TRUE.equals(carga.getAplicaCtr01())) agregarUnico(carga.getCondicionesConcurrentesFase6(), "RN_6_1");
        if (Boolean.TRUE.equals(carga.getAplicaRn68())) agregarUnico(carga.getCondicionesConcurrentesFase6(), "CIERRE_PERIODO");
        if (Boolean.TRUE.equals(carga.getRegularizacionAlcanzadaRn68()))
            agregarUnico(carga.getCondicionesConcurrentesFase6(), "REGULARIZACION_RECONOCIDA");
    }

    private EstadoConsolidacionFase6V2 determinarEstadoConsolidadoFase6(EvaluacionCargaAcademicaV2DTO carga) {
        if (Boolean.TRUE.equals(carga.getAplicaRn64())) return EstadoConsolidacionFase6V2.REINCORPORACION_RESTRINGIDA_RN64;
        if (carga.getEstadoReincorporacionRn67() == EstadoReincorporacionBajaParcialV2.RESTRINGIDA_POR_SANCION)
            return EstadoConsolidacionFase6V2.RESTRINGIDA_POR_SANCION;
        if (esNoProcedeFase6(carga)) return EstadoConsolidacionFase6V2.NO_PROCEDE_POR_INACTIVIDAD_PERMANENCIA;
        if (requiereRevisionFase6(carga)) return EstadoConsolidacionFase6V2.REQUIERE_REVISION;
        if (carga.getEstadoActualizacionTrayectoriaRn68() == EstadoActualizacionTrayectoriaRn68V2.REGULARIZACION_RECONOCIDA_EN_SOMBRA
                || carga.getEstadoActualizacionTrayectoriaRn68() == EstadoActualizacionTrayectoriaRn68V2.CONTINUIDAD_A_SEMESTRE_PAR_PROCEDE_EN_SOMBRA
                || carga.getEstadoActualizacionTrayectoriaRn68() == EstadoActualizacionTrayectoriaRn68V2.CONTINUIDAD_CON_PRIORIDAD_REGULARIZACION)
            return EstadoConsolidacionFase6V2.CONTINUIDAD_RECONOCIDA_TRAS_REGULARIZACION;
        if (Boolean.TRUE.equals(carga.getAplicaCtr01()) && carga.getCausaPrincipalTrayectoriaFase6() != CausaPrincipalTrayectoriaV2.NORMAL)
            return EstadoConsolidacionFase6V2.REINCORPORACION_SUJETA_RN61;
        if (!carga.getUdsRutaAcademicaConsolidadaFase6().isEmpty())
            return EstadoConsolidacionFase6V2.REINCORPORACION_CON_RUTA_REGULARIZACION;
        if (carga.getCausaPrincipalTrayectoriaFase6() == CausaPrincipalTrayectoriaV2.INSCRIPCION_INICIAL_DIFERIDA)
            return EstadoConsolidacionFase6V2.INCORPORACION_DIFERIDA_PROCEDE_EN_SOMBRA;
        if (carga.getCausaPrincipalTrayectoriaFase6() == CausaPrincipalTrayectoriaV2.REINCORPORACION_TRAS_PERIODO_NO_CURSADO
                || carga.getCausaPrincipalTrayectoriaFase6() == CausaPrincipalTrayectoriaV2.REINCORPORACION_TRAS_BAJA_TEMPORAL)
            return EstadoConsolidacionFase6V2.REINCORPORACION_PROCEDE_EN_SOMBRA;
        return EstadoConsolidacionFase6V2.NO_APLICA;
    }

    private boolean esNoProcedeFase6(EvaluacionCargaAcademicaV2DTO carga) {
        return carga.getEstadoIncorporacionDiferida() == EstadoIncorporacionDiferidaV2.NO_PROCEDE_POR_INACTIVIDAD
                || carga.getEstadoReincorporacionRn65() == EstadoReincorporacionPeriodoNoCursadoV2.NO_PROCEDE_POR_INACTIVIDAD
                || carga.getEstadoReincorporacionRn66() == EstadoReincorporacionBajaTemporalV2.NO_PROCEDE_POR_PERMANENCIA;
    }

    private boolean requiereRevisionFase6(EvaluacionCargaAcademicaV2DTO carga) {
        return Boolean.TRUE.equals(carga.getRequiereRevisionFase6())
                || carga.getEstadoIncorporacionDiferida() == EstadoIncorporacionDiferidaV2.REQUIERE_REVISION
                || carga.getEstadoReincorporacionRn65() == EstadoReincorporacionPeriodoNoCursadoV2.REQUIERE_REVISION
                || carga.getEstadoReincorporacionRn66() == EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION
                || carga.getEstadoReincorporacionRn67() == EstadoReincorporacionBajaParcialV2.REQUIERE_REVISION
                || carga.getEstadoActualizacionTrayectoriaRn68() == EstadoActualizacionTrayectoriaRn68V2.REQUIERE_REVISION;
    }

    private void agregarRutaFase6(EvaluacionCargaAcademicaV2DTO carga, List<Long> ruta) {
        for (Long id : ruta) agregarIdUnico(carga.getUdsRutaAcademicaConsolidadaFase6(), id);
    }

    private String construirTrayectoriaAntesFase6(EvaluacionCargaAcademicaV2DTO carga) {
        if (carga.getUltimoPeriodoEfectivamenteCursadoRn66() != null)
            return "ULTIMO_PERIODO_CURSADO=" + carga.getUltimoPeriodoEfectivamenteCursadoRn66();
        if (carga.getUltimoPeriodoCursadoRn65() != null) return "ULTIMO_PERIODO_CURSADO=" + carga.getUltimoPeriodoCursadoRn65();
        if (carga.getSituacionAcademicaAntesCierreRn68() != null) return "SITUACION_ANTES_CIERRE=" + carga.getSituacionAcademicaAntesCierreRn68();
        return "SIN_EVIDENCIA_DE_TRAYECTORIA_PREVIA";
    }

    private String construirTrayectoriaDespuesFase6(EvaluacionCargaAcademicaV2DTO carga,
            ResultadoEvaluacionCurricularV2DTO resultado) {
        return "SITUACION_VIGENTE=" + resultado.getSituacionAcademicaCalculada()
                + "; SEMESTRE_OBJETIVO=" + (carga.getSemestreObjetivoParRn68() != null
                        ? carga.getSemestreObjetivoParRn68() : null);
    }

    private void registrarDecisionConsolidacionFase6(EvaluacionCargaAcademicaV2DTO carga, String decision) {
        agregarUnico(carga.getDecisionesConsolidacionFase6(), decision);
    }

    private void registrarTrazaFase6(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            CodigoMotivoCurricularV2 motivo) {
        String detalle = "causa=" + carga.getCausaPrincipalTrayectoriaFase6()
                + "; condiciones=" + carga.getCondicionesConcurrentesFase6()
                + "; antes=" + carga.getTrayectoriaAntesFase6()
                + "; despues=" + carga.getTrayectoriaDespuesFase6()
                + "; ruta=" + carga.getUdsRutaAcademicaConsolidadaFase6()
                + "; estados={rn62=" + carga.getEstadoIncorporacionDiferida()
                + ",rn65=" + carga.getEstadoReincorporacionRn65()
                + ",rn66=" + carga.getEstadoReincorporacionRn66()
                + ",rn67=" + carga.getEstadoReincorporacionRn67()
                + ",rn68=" + carga.getEstadoActualizacionTrayectoriaRn68() + "}";
        agregarUnico(carga.getEvidencias(), detalle);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_9_5, motivo, 90,
                "F6_" + carga.getEstadoConsolidacionFase6(), detalle);
    }

    /** RN-7.1/RN-7.1.1 compone optativas sobre los resultados comunes ya evaluados. */
    private void evaluarOptativasRn71(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_7_1);
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_7_1_1);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_7_1);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_7_1_1);
        carga.setAplicaRn711(Boolean.TRUE);

        if (contexto.getOptativasRequeridasPrograma() != null
                && contexto.getOptativasRequeridasPrograma().intValue() != 8) {
            carga.setAplicaRn71(null);
            carga.setRequiereRevisionRn71(Boolean.TRUE);
            carga.setEstadoRequerimientoOptativo(EstadoRequerimientoOptativoV2.REQUIERE_REVISION);
            registrarTrazaOptativasRn71(resultado, contexto, carga, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES,
                    "RN71_REQUIERE_REVISION_CONFIGURACION_PE", "El PE informa un requisito optativo distinto al umbral normativo vigente de ocho.");
            return;
        }

        List<UnidadEvaluacionCurricularV2DTO> optativasAcreditadas = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        boolean hayOptativas = false;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad.getTipoUd() == null || unidad.getTipoUd().trim().isEmpty()) {
                marcarRevisionOptativasRn71(resultado, contexto, carga, unidad, "TIPO_UD_NO_DETERMINADO");
                continue;
            }
            if (esTipoAmbiguoFase7(unidad)) {
                marcarRevisionOptativasRn71(resultado, contexto, carga, unidad, "TIPO_UD_AMBIGUO_OPTATIVA_ELECTIVA");
                continue;
            }
            if (!esOptativa(unidad)) continue;
            hayOptativas = true;
            agregarIdUnico(carga.getUdsOptativasIdentificadasRn71(), unidad.getIdPrograma());
            if (unidad.getEstado() == EstadoUnidadCurricularV2.ACREDITADA) {
                optativasAcreditadas.add(unidad);
                agregarIdUnico(carga.getUdsOptativasAcreditadasRn711(), unidad.getIdPrograma());
                registrarDecisionOptativaRn71(resultado, contexto, unidad, null, "OPTATIVA_ACREDITADA", "CUENTA_REQUERIMIENTO");
                continue;
            }
            if (esReprobacionVigentePrevia(unidad)) {
                evaluarOptativaReprobadaRn71(resultado, contexto, carga, unidad);
                continue;
            }
            if (unidad.getEstado() == EstadoUnidadCurricularV2.NO_INSCRITA) {
                agregarIdUnico(carga.getUdsOptativasNuevasRn71(), unidad.getIdPrograma());
                registrarDecisionOptativaRn71(resultado, contexto, unidad, null, "OPTATIVA_NUEVA",
                        "POSTERIOR_A_PRIORIDAD_REGULARIZACION");
            }
        }

        carga.setAplicaRn71(Boolean.valueOf(hayOptativas));
        int acreditadas = carga.getUdsOptativasAcreditadasRn711().size();
        carga.setCantidadOptativasAcreditadasRn711(Integer.valueOf(acreditadas));
        carga.setCantidadOptativasFaltantesRn711(Integer.valueOf(Math.max(0, 8 - acreditadas)));
        carga.setCantidadOptativasAdicionalesRn711(Integer.valueOf(Math.max(0, acreditadas - 8)));
        clasificarOptativasAdicionalesRn711(resultado, contexto, carga, optativasAcreditadas);

        if (Boolean.TRUE.equals(carga.getRequiereRevisionRn71())) {
            carga.setEstadoRequerimientoOptativo(EstadoRequerimientoOptativoV2.REQUIERE_REVISION);
        } else if (Boolean.TRUE.equals(carga.getAplicaRn64())) {
            carga.setEstadoRequerimientoOptativo(EstadoRequerimientoOptativoV2.SUJETO_RN64);
        } else if (!carga.getUdsOptativasReprobadasPrioritariasRn71().isEmpty()) {
            carga.setEstadoRequerimientoOptativo(EstadoRequerimientoOptativoV2.OPTATIVAS_REPROBADAS_PRIORIZADAS);
        } else if (!carga.getUdsOptativasReprobadasRn71().isEmpty()) {
            carga.setEstadoRequerimientoOptativo(EstadoRequerimientoOptativoV2.OPTATIVAS_REPROBADAS_PENDIENTES);
        } else if (acreditadas >= 8) {
            carga.setEstadoRequerimientoOptativo(EstadoRequerimientoOptativoV2.REQUERIMIENTO_OPTATIVO_CUMPLIDO);
        } else {
            carga.setEstadoRequerimientoOptativo(EstadoRequerimientoOptativoV2.REQUERIMIENTO_OPTATIVO_PENDIENTE);
        }
        registrarTrazaOptativasRn71(resultado, contexto, carga,
                carga.getEstadoRequerimientoOptativo() == EstadoRequerimientoOptativoV2.REQUIERE_REVISION
                        ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                "RN71_" + carga.getEstadoRequerimientoOptativo(), construirEvidenciaOptativasRn71(carga));
    }

    private void evaluarOptativaReprobadaRn71(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO unidad) {
        agregarIdUnico(carga.getUdsOptativasReprobadasRn71(), unidad.getIdPrograma());
        String disponibilidad = disponibilidadRn65(unidad);
        if ("SIN_OFERTA".equals(disponibilidad)) {
            agregarIdUnico(carga.getUdsOptativasReprobadasSinOfertaRn71(), unidad.getIdPrograma());
            registrarDecisionOptativaRn71(resultado, contexto, unidad, CodigoMotivoCurricularV2.SIN_OFERTA,
                    "OPTATIVA_REPROBADA_PENDIENTE", "SIN_OFERTA");
            return;
        }
        if ("SIN_CUPO".equals(disponibilidad)) {
            agregarIdUnico(carga.getUdsOptativasReprobadasSinCupoRn71(), unidad.getIdPrograma());
            registrarDecisionOptativaRn71(resultado, contexto, unidad, CodigoMotivoCurricularV2.SIN_CUPO,
                    "OPTATIVA_REPROBADA_PENDIENTE", "SIN_CUPO");
            return;
        }
        if (!"DISPONIBLE".equals(disponibilidad)) {
            marcarRevisionOptativasRn71(resultado, contexto, carga, unidad, "OFERTA_O_CUPO_NO_DETERMINADO");
            return;
        }
        // RN-6.3 conserva la prioridad de reprobacion general; RN-7.1 solo ordena frente a optativas nuevas.
        if (!carga.getUdsReprobadasOfertadas().contains(unidad.getIdPrograma())) {
            marcarRevisionOptativasRn71(resultado, contexto, carga, unidad, "PRIORIDAD_RN63_NO_DETERMINABLE");
            return;
        }
        agregarIdUnico(carga.getUdsOptativasReprobadasPrioritariasRn71(), unidad.getIdPrograma());
        registrarDecisionOptativaRn71(resultado, contexto, unidad, CodigoMotivoCurricularV2.REPROBACION_ACUMULADA,
                "OPTATIVA_REPROBADA_PRIORITARIA", "PRECEDE_A_OPTATIVA_NUEVA");
    }

    private void clasificarOptativasAdicionalesRn711(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            List<UnidadEvaluacionCurricularV2DTO> acreditadas) {
        if (acreditadas.size() <= 8) return;
        boolean ordenCompleto = true;
        for (UnidadEvaluacionCurricularV2DTO unidad : acreditadas) if (unidad.getOrdenAcreditacionOptativa() == null) ordenCompleto = false;
        if (!ordenCompleto) {
            marcarRevisionOptativasRn71(resultado, contexto, carga, null, "ORDEN_ACREDITACION_OPTATIVA_NO_DETERMINABLE");
            return;
        }
        for (UnidadEvaluacionCurricularV2DTO unidad : acreditadas) {
            if (unidad.getOrdenAcreditacionOptativa().intValue() > 8) {
                agregarIdUnico(carga.getUdsOptativasAdicionalesRn711(), unidad.getIdPrograma());
                registrarDecisionOptativaRn71(resultado, contexto, unidad, null,
                        "OPTATIVA_ADICIONAL_OPCIONAL", "REQUERIMIENTO_YA_CUMPLIDO");
            }
        }
    }

    private void marcarRevisionOptativasRn71(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO unidad, String causa) {
        carga.setRequiereRevisionRn71(Boolean.TRUE);
        if (unidad != null) registrarDecisionOptativaRn71(resultado, contexto, unidad,
                CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "REQUIERE_REVISION", causa);
    }

    private void registrarDecisionOptativaRn71(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, UnidadEvaluacionCurricularV2DTO unidad,
            CodigoMotivoCurricularV2 motivo, String decision, String detalle) {
        if (unidad == null) return;
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga != null) carga.getDecisionesPorUdOptativaRn71().add(obtenerLlaveUnidad(unidad) + ":" + decision + ":" + detalle);
        DecisionUnidadCurricularV2DTO unidadDecision = buscarDecision(resultado, unidad.getIdPrograma());
        if (unidadDecision != null) {
            if ("OPTATIVA_REPROBADA_PRIORITARIA".equals(decision)) {
                unidadDecision.setPrioritaria(Boolean.TRUE);
                agregarUnico(unidadDecision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.PRIORITARIA);
            }
            if (motivo != null) agregarUnico(unidadDecision.getMotivos(), motivo);
            agregarExplicacion(unidadDecision, "RN-7.1: " + decision + " (" + detalle + ").");
            TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_7_1, motivo, 70,
                    "RN71_" + decision, obtenerLlaveUnidad(unidad) + "; " + detalle);
            unidadDecision.getTrazas().add(traza);
            resultado.getTrazas().add(traza);
        }
    }

    private String construirEvidenciaOptativasRn71(EvaluacionCargaAcademicaV2DTO carga) {
        return "requeridas=" + carga.getCantidadOptativasRequeridasRn711()
                + "; acreditadas=" + carga.getCantidadOptativasAcreditadasRn711()
                + "; faltantes=" + carga.getCantidadOptativasFaltantesRn711()
                + "; adicionales=" + carga.getCantidadOptativasAdicionalesRn711()
                + "; reprobadas=" + carga.getUdsOptativasReprobadasRn71()
                + "; prioritarias=" + carga.getUdsOptativasReprobadasPrioritariasRn71()
                + "; sinOferta=" + carga.getUdsOptativasReprobadasSinOfertaRn71()
                + "; sinCupo=" + carga.getUdsOptativasReprobadasSinCupoRn71()
                + "; rn64=" + carga.getAplicaRn64()
                + "; rn61=" + carga.getAplicaCtr01();
    }

    private void registrarTrazaOptativasRn71(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            CodigoMotivoCurricularV2 motivo, String estado, String detalle) {
        agregarUnico(carga.getEvidencias(), detalle);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_7_1, motivo, 70, estado, detalle);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_7_1_1, motivo, 70, estado, detalle);
    }

    /** RN-7.2 definitivo trata optativas reprobadas; no crea una categoría ELECTIVA. */
    private void evaluarPrincipioGeneralOptativasReprobadasRn72(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_7_2);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_7_2);
        boolean hayOptativasReprobadas = false;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (!esOptativa(unidad) || !esReprobacionVigentePrevia(unidad)) continue;
            hayOptativasReprobadas = true;
            String tratamiento = carga.getCantidadOptativasAcreditadasRn711() != null
                    && carga.getCantidadOptativasAcreditadasRn711().intValue() >= 8
                    ? "OPTATIVA_REPROBADA_POSIBLE_ADEUDO_ADICIONAL_SUJETO_A_CATALOGO_Y_RN711"
                    : "OPTATIVA_REPROBADA_REQUERIDA_RECURSAMIENTO_MISMA_UD";
            registrarDecisionElectivaRn72(resultado, contexto, unidad, CodigoMotivoCurricularV2.REPROBACION_ACUMULADA,
                    tratamiento, "RN72_REUTILIZA_RN71_RN711_SIN_ELECTIVAS");
        }
        carga.setAplicaRn72(Boolean.valueOf(hayOptativasReprobadas));
        carga.setEstadoElegibilidadElectiva(EstadoElegibilidadElectivaV2.NO_APLICA);
        registrarTrazaElectivasRn72(resultado, contexto, carga, null,
                "RN72_OPTATIVAS_REPROBADAS", "sinElectivas=true; optativasReprobadas=" + hayOptativasReprobadas
                        + "; tratamiento=RN71_RN711");
    }

    private String evaluarElectivaNuevaRn72(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO unidad) {
        // Compatibilidad binaria: RN-7.2 ya no evalúa una categoría ELECTIVA.
        marcarRevisionElectivaRn72(resultado, contexto, carga, unidad, "CATEGORIA_ELECTIVA_RETIRADA_RN72");
        return "NO_APLICA";
    }

    private boolean esSemestreElectivas(Integer semestreObjetivo) {
        return Integer.valueOf(7).equals(semestreObjetivo) || Integer.valueOf(8).equals(semestreObjetivo);
    }

    private void marcarRevisionElectivaRn72(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            UnidadEvaluacionCurricularV2DTO unidad, String detalle) {
        carga.setRequiereRevisionRn72(Boolean.TRUE);
        if (unidad != null) registrarDecisionElectivaRn72(resultado, contexto, unidad,
                CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, "REQUIERE_REVISION", detalle);
    }

    private void registrarDecisionElectivaRn72(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, UnidadEvaluacionCurricularV2DTO unidad,
            CodigoMotivoCurricularV2 motivo, String decision, String detalle) {
        if (unidad == null) return;
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga != null) carga.getDecisionesPorUdElectivaRn72().add(obtenerLlaveUnidad(unidad) + ":" + decision + ":" + detalle);
        DecisionUnidadCurricularV2DTO unidadDecision = buscarDecision(resultado, unidad.getIdPrograma());
        if (unidadDecision == null) return;
        if (motivo != null) agregarUnico(unidadDecision.getMotivos(), motivo);
        agregarExplicacion(unidadDecision, "RN-7.2: " + decision + " (" + detalle + ").");
        TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_7_2, motivo, 70,
                "RN72_" + decision, obtenerLlaveUnidad(unidad) + "; " + detalle);
        unidadDecision.getTrazas().add(traza);
        resultado.getTrazas().add(traza);
    }

    private String construirEvidenciaElectivasRn72(EvaluacionCargaAcademicaV2DTO carga) {
        return "electivas=" + carga.getUdsElectivasIdentificadasRn72()
                + "; elegibles=" + carga.getUdsElectivasElegiblesRn72()
                + "; mismoPe=" + carga.getUdsElectivasMismoPeRn72()
                + "; reprobadas=" + carga.getUdsElectivasReprobadasRn72()
                + "; sinOferta=" + carga.getUdsElectivasSinOfertaRn72()
                + "; sinCupo=" + carga.getUdsElectivasSinCupoRn72()
                + "; prioridadRn63=" + carga.getUdsElectivasConRestriccionPrioridadRn72()
                + "; rn64=" + carga.getAplicaRn64();
    }

    private void registrarTrazaElectivasRn72(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            CodigoMotivoCurricularV2 motivo, String estado, String detalle) {
        agregarUnico(carga.getEvidencias(), detalle);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_7_2, motivo, 70, estado, detalle);
    }

    /** RN-7.3 consolida tipos y resultados existentes; no recalcula RN-1.2, RN-6.3 ni RN-6.4. */
    private void consolidarPrincipioOptativasElectivasRn73(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        agregarUnico(carga.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_7_3);
        agregarUnico(resultado.getReglasEvaluadas(), CodigoReglaCurricularV2.RN_7_3);

        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (!esReprobacionVigentePrevia(unidad)) continue;
            if (esTipoAmbiguoFase7(unidad)) {
                carga.setRequiereRevisionRn73(Boolean.TRUE);
            } else if (esOptativa(unidad)) {
                agregarIdUnico(carga.getUdsOptativasReprobadasRn73(), unidad.getIdPrograma());
                registrarDecisionRn73(resultado, contexto, unidad, "REPROBADA_OPTATIVA_VIGENTE", "NO_BLOQUEO_AUTONOMO_POR_TIPO");
            } else if (esObligatoria(unidad)) {
                agregarIdUnico(carga.getUdsObligatoriasReprobadasRn73(), unidad.getIdPrograma());
            } else if (unidad.getTipoUd() == null || unidad.getTipoUd().trim().isEmpty()) {
                carga.setRequiereRevisionRn73(Boolean.TRUE);
            }
        }
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (esObligatoria(unidad) && esObligatoriaPendientePrioritariaRn73(carga, unidad.getIdPrograma()))
                agregarIdUnico(carga.getUdsObligatoriasPrioritariasRn73(), unidad.getIdPrograma());
        }

        carga.setCantidadOptativasReprobadasRn73(Integer.valueOf(carga.getUdsOptativasReprobadasRn73().size()));
        carga.setCantidadElectivasReprobadasRn73(Integer.valueOf(carga.getUdsElectivasReprobadasRn73().size()));
        carga.setCantidadObligatoriasReprobadasRn73(Integer.valueOf(carga.getUdsObligatoriasReprobadasRn73().size()));
        carga.setTotalReprobadasComunRn73(resultado.getCantidadReprobadasVigentes());
        boolean hayTiposRn73 = !carga.getUdsOptativasReprobadasRn73().isEmpty()
                || !carga.getUdsElectivasReprobadasRn73().isEmpty();
        carga.setAplicaRn73(Boolean.valueOf(hayTiposRn73));

        if (!hayTiposRn73) {
            carga.setEstadoPrincipioOptativaElectiva(EstadoPrincipioOptativaElectivaV2.NO_APLICA);
        } else if (Boolean.TRUE.equals(carga.getRequiereRevisionRn73())) {
            carga.setEstadoPrincipioOptativaElectiva(EstadoPrincipioOptativaElectivaV2.REQUIERE_REVISION);
        } else if (Boolean.TRUE.equals(carga.getAplicaRn64())) {
            carga.setEstadoPrincipioOptativaElectiva(EstadoPrincipioOptativaElectivaV2.SUJETO_RN64);
        } else if (!"IRREGULAR".equals(resultado.getSituacionAcademicaCalculada())) {
            carga.setRequiereRevisionRn73(Boolean.TRUE);
            carga.setEstadoPrincipioOptativaElectiva(EstadoPrincipioOptativaElectivaV2.REQUIERE_REVISION);
        } else if (conflictoMaterialPrioridadRn73(carga)) {
            carga.setRequiereRevisionRn73(Boolean.TRUE);
            carga.setEstadoPrincipioOptativaElectiva(EstadoPrincipioOptativaElectivaV2.REQUIERE_REVISION);
        } else if (!carga.getUdsObligatoriasPrioritariasRn73().isEmpty()) {
            carga.setEstadoPrincipioOptativaElectiva(EstadoPrincipioOptativaElectivaV2.PRIORIDAD_OBLIGATORIA_PREVALECE);
        } else {
            carga.setContinuidadPotencialSujetaReglasRn73(Boolean.TRUE);
            carga.setEstadoPrincipioOptativaElectiva(EstadoPrincipioOptativaElectivaV2.IRREGULAR_SIN_BLOQUEO_AUTONOMO);
        }
        registrarTrazaRn73(resultado, contexto, carga,
                carga.getEstadoPrincipioOptativaElectiva() == EstadoPrincipioOptativaElectivaV2.REQUIERE_REVISION
                        ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                "RN73_" + carga.getEstadoPrincipioOptativaElectiva(), construirEvidenciaRn73(carga));
    }

    private boolean conflictoMaterialPrioridadRn73(EvaluacionCargaAcademicaV2DTO carga) {
        if (carga.getUdsOptativasReprobadasRn73().isEmpty() || carga.getUdsElectivasReprobadasRn73().isEmpty()) return false;
        Integer espacios = carga.getCargaDisponibleRegularizacion();
        if (espacios == null) return false;
        int candidatasOfertadas = 0;
        for (Long id : carga.getUdsOptativasReprobadasRn73()) if (carga.getUdsReprobadasOfertadas().contains(id)) candidatasOfertadas++;
        for (Long id : carga.getUdsElectivasReprobadasRn73()) if (carga.getUdsReprobadasOfertadas().contains(id)) candidatasOfertadas++;
        return candidatasOfertadas > espacios.intValue();
    }

    /** Consume prioridades ya construidas por RN-6.3 y las reincorporaciones de Fase 6. */
    private boolean esObligatoriaPendientePrioritariaRn73(EvaluacionCargaAcademicaV2DTO carga, Long idPrograma) {
        if (idPrograma == null) return false;
        return carga.getUdsReprobadasPrioritarias().contains(idPrograma)
                || carga.getUdsPrioritarias().contains(idPrograma)
                || carga.getUdsPendientesPrioritariasRn65().contains(idPrograma)
                || carga.getUdsPendientesPrioritariasRn66().contains(idPrograma)
                || carga.getUdsBajaParcialPrioritariasRn67().contains(idPrograma);
    }

    private void registrarDecisionRn73(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, UnidadEvaluacionCurricularV2DTO unidad,
            String decision, String detalle) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga != null) carga.getDecisionesPorUdRn73().add(obtenerLlaveUnidad(unidad) + ":" + decision + ":" + detalle);
        DecisionUnidadCurricularV2DTO unidadDecision = buscarDecision(resultado, unidad.getIdPrograma());
        if (unidadDecision == null) return;
        agregarExplicacion(unidadDecision, "RN-7.3: " + decision + " (" + detalle + ").");
        TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, CodigoReglaCurricularV2.RN_7_3, null, 70,
                "RN73_" + decision, obtenerLlaveUnidad(unidad) + "; " + detalle);
        unidadDecision.getTrazas().add(traza);
        resultado.getTrazas().add(traza);
    }

    private String construirEvidenciaRn73(EvaluacionCargaAcademicaV2DTO carga) {
        return "optativas=" + carga.getUdsOptativasReprobadasRn73()
                + "; electivas=" + carga.getUdsElectivasReprobadasRn73()
                + "; obligatorias=" + carga.getUdsObligatoriasReprobadasRn73()
                + "; totalComun=" + carga.getTotalReprobadasComunRn73()
                + "; rn64=" + carga.getAplicaRn64()
                + "; obligatoriasPrioritarias=" + carga.getUdsObligatoriasPrioritariasRn73()
                + "; noBloqueoAutonomo={continuidad=" + carga.getBloqueoAutonomoContinuidadPorTipoRn73()
                + ",quinto=" + carga.getBloqueoAutonomoQuintoPorTipoRn73() + "}";
    }

    private void registrarTrazaRn73(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, EvaluacionCargaAcademicaV2DTO carga,
            CodigoMotivoCurricularV2 motivo, String estado, String detalle) {
        agregarUnico(carga.getEvidencias(), detalle);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_7_3, motivo, 70, estado, detalle);
    }

    /** F7D audita que RN-7.1, RN-7.2 y RN-7.3 compartan un contrato y tipos no ambiguos. */
    private void consolidarCierreTiposUdFase7(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        EvaluacionCargaAcademicaV2DTO carga = resultado.getEvaluacionCargaAcademica();
        if (carga == null) return;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (esTipoAmbiguoFase7(unidad)) {
                agregarIdUnico(carga.getUdsTipoAmbiguoFase7(), unidad.getIdPrograma());
                registrarDecisionCierreFase7(carga, "TIPO_UD_AMBIGUO:" + obtenerLlaveUnidad(unidad));
            }
        }
        if (!carga.getUdsTipoAmbiguoFase7().isEmpty()) carga.setRequiereRevisionFase7(Boolean.TRUE);
        if (intersectan(carga.getUdsOptativasReprobadasRn73(), carga.getUdsElectivasReprobadasRn73())) {
            carga.setRequiereRevisionFase7(Boolean.TRUE);
            registrarDecisionCierreFase7(carga, "TIPO_UD_DUPLICADO_ENTRE_OPTATIVA_Y_ELECTIVA");
        }
        if (Boolean.TRUE.equals(carga.getRequiereRevisionRn71()) || Boolean.TRUE.equals(carga.getRequiereRevisionRn72())
                || Boolean.TRUE.equals(carga.getRequiereRevisionRn73())) carga.setRequiereRevisionFase7(Boolean.TRUE);
        if (Boolean.TRUE.equals(carga.getRequiereRevisionFase7())) {
            carga.setEstadoCierreFase7(EstadoCierreFase7V2.REQUIERE_REVISION);
        } else if (Boolean.TRUE.equals(carga.getAplicaRn64())) {
            carga.setEstadoCierreFase7(EstadoCierreFase7V2.RESTRINGIDA_POR_RN64);
        } else {
            carga.setEstadoCierreFase7(EstadoCierreFase7V2.CONSOLIDADA_EN_SOMBRA);
        }
        String evidencia = "tipos={optativas=" + carga.getUdsOptativasReprobadasRn73()
                + ",electivas=" + carga.getUdsElectivasReprobadasRn73()
                + ",obligatorias=" + carga.getUdsObligatoriasReprobadasRn73()
                + "}; totalComun=" + carga.getTotalReprobadasComunRn73()
                + "; estadoCierre=" + carga.getEstadoCierreFase7()
                + "; decisiones=" + carga.getDecisionesCierreFase7();
        agregarUnico(carga.getEvidencias(), evidencia);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_7_3,
                Boolean.TRUE.equals(carga.getRequiereRevisionFase7()) ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null,
                72, "F7D_" + carga.getEstadoCierreFase7(), evidencia);
    }

    private boolean intersectan(List<Long> izquierda, List<Long> derecha) {
        for (Long id : izquierda) if (derecha.contains(id)) return true;
        return false;
    }

    private void registrarDecisionCierreFase7(EvaluacionCargaAcademicaV2DTO carga, String decision) {
        agregarUnico(carga.getDecisionesCierreFase7(), decision);
    }

    private Boolean procedenciaCtr01(ContextoEvaluacionCurricularV2DTO contexto,
            List<UnidadEvaluacionCurricularV2DTO> unidades) {
        if (!tieneCandidataQuinto(contexto, unidades)) return Boolean.FALSE;
        String periodo = contexto.getPeriodoNormativo();
        if (periodo == null || !periodo.matches("\\d{4}-[12]")) return null;
        String inicio = contexto.getParametrosCurriculares() == null ? "2027-1"
                : contexto.getParametrosCurriculares().getCtr01PeriodoInicio();
        return Boolean.valueOf(periodo.compareTo(inicio == null ? "2027-1" : inicio) >= 0);
    }

    private boolean tieneCandidataQuinto(ContextoEvaluacionCurricularV2DTO contexto,
            List<UnidadEvaluacionCurricularV2DTO> unidades) {
        if (contexto.getIdsUnidadesPretendidas().isEmpty()
                && contexto.getIdsUnidadesCandidatas().isEmpty()) {
            if (Boolean.TRUE.equals(contexto.getCandidataQuintoDeclaradaCompatibilidad())) return true;
            for (UnidadEvaluacionCurricularV2DTO unidad : unidades)
                if (unidad != null && Boolean.TRUE.equals(unidad.getOfertada()) && Integer.valueOf(5).equals(unidad.getSemestre())) return true;
        }
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades)
            if (esCandidataQuinto(contexto, unidad)) return true;
        return false;
    }

    private boolean esCandidataQuinto(ContextoEvaluacionCurricularV2DTO contexto,
            UnidadEvaluacionCurricularV2DTO unidad) {
        if (unidad == null || !Integer.valueOf(5).equals(unidad.getSemestre())) return false;
        List<Long> pretendidas = contexto.getIdsUnidadesPretendidas();
        if (pretendidas == null || pretendidas.isEmpty()) pretendidas = contexto.getIdsUnidadesCandidatas();
        return pretendidas == null || pretendidas.isEmpty()
                || (unidad.getIdPrograma() != null && pretendidas.contains(unidad.getIdPrograma()));
    }

    private boolean contieneCausa(List<String> valores, String causa) {
        for (String valor : valores) if (valor != null && valor.contains(causa)) return true;
        return false;
    }

    private void evaluarExcepcionesTipificadas(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        List<UnidadEvaluacionCurricularV2DTO> obligatoriasPendientes = filtrarPendientes(unidades, true);

        if (tieneCandidataQuinto(contexto, unidades)) {
            if (obligatoriasPendientes.size() == 1) {
                registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.AVANCE_UNA_OBLIGATORIA_PENDIENTE,
                        TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_6_1,
                        EstadoExcepcionCurricularV2.CANDIDATA, obligatoriasPendientes,
                        "UNA_OBLIGATORIA_PENDIENTE", "Supuesto tipificado detectado para quinto; RN-6.1 no se aplica en Fase 1C.");
            }
            if (resultado.getTotalReprobadas() != null && resultado.getTotalReprobadas().intValue() >= 1
                    && resultado.getTotalReprobadas().intValue() <= 3) {
                registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.AVANCE_REZAGO_LEVE,
                        TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_6_1,
                        EstadoExcepcionCurricularV2.CANDIDATA, filtrarReprobadas(unidades),
                        "REZAGO_LEVE_1_A_3_REPROBADAS", "Rezago leve detectado para quinto; no se habilitan UD mediante RN-6.1.");
            }
            if (Boolean.TRUE.equals(contexto.getBajaTemporalRegistrada())) {
                registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.AVANCE_BAJA_TEMPORAL,
                        TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_6_1,
                        EstadoExcepcionCurricularV2.CANDIDATA, new ArrayList<UnidadEvaluacionCurricularV2DTO>(),
                        "BAJA_TEMPORAL_REGISTRADA", "Antecedente de baja temporal detectado; no se trata como reprobación.");
            }
            List<UnidadEvaluacionCurricularV2DTO> bajasParciales = filtrarPorEstado(unidades, EstadoUnidadCurricularV2.BAJA_PARCIAL);
            if (!bajasParciales.isEmpty()) {
                registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.AVANCE_BAJA_PARCIAL,
                        TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_6_1,
                        EstadoExcepcionCurricularV2.CANDIDATA, bajasParciales,
                        "BAJA_PARCIAL_AUTORIZADA_REGISTRADA", "Baja parcial detectada; no se equipara automáticamente con reprobación.");
            }
            List<UnidadEvaluacionCurricularV2DTO> sinOferta = filtrarSinOferta(unidades);
            if (!sinOferta.isEmpty()) {
                registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.AVANCE_FALTA_OFERTA,
                        TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_6_1,
                        EstadoExcepcionCurricularV2.CANDIDATA, sinOferta,
                        "FALTA_OFERTA_O_INDISPONIBILIDAD_INSTITUCIONAL", "La falta de oferta se registra separada de la falta de cupo.");
            }
        }

        List<UnidadEvaluacionCurricularV2DTO> extraordinarias = filtrarExtraordinarias(unidades);
        if (!extraordinarias.isEmpty()) {
            registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.EXTRAORDINARIA_NO_TIPIFICADA,
                    TipoExcepcionCurricularV2.EXTRAORDINARIA, CodigoReglaCurricularV2.CTR_02,
                    EstadoExcepcionCurricularV2.REQUIERE_REVISION, extraordinarias,
                    "CAUSA_NO_TIPIFICADA_REQUIERE_VALORACION_INSTITUCIONAL",
                    "No existe excepción tipificada que resuelva la causa registrada; se prepara escalamiento sin habilitar UD.");
        }
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.CTR_02, null, 30,
                "EXCEPCIONES_TIPIFICADAS_EVALUADAS_EN_SOMBRA",
                "Las excepciones se detectan y trazan sin alterar oferta, carga ni inscripción vigente.");
    }

    private void registrarExcepcion(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, CodigoExcepcionCurricularV2 codigo,
            TipoExcepcionCurricularV2 tipo, CodigoReglaCurricularV2 regla, EstadoExcepcionCurricularV2 estado,
            List<UnidadEvaluacionCurricularV2DTO> unidades, String condicionCumplida, String explicacion) {
        ExcepcionCurricularV2DTO excepcion = new ExcepcionCurricularV2DTO();
        excepcion.setCodigo(codigo);
        excepcion.setTipo(tipo);
        excepcion.setReglaOrigen(regla);
        excepcion.setEstado(estado);
        excepcion.setFechaEvaluacion(contexto.getFechaEvaluacion());
        excepcion.setVersionNormativa(contexto.getVersionReglas());
        excepcion.getCondicionesEvaluadas().add(condicionCumplida);
        excepcion.getCondicionesCumplidas().add(condicionCumplida);
        excepcion.setExplicacionTecnica(explicacion);
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad.getIdPrograma() != null) excepcion.getUnidadesRelacionadas().add(unidad.getIdPrograma());
            excepcion.getEvidencias().addAll(unidad.getReferenciasHistoricas());
            asociarExcepcion(resultado, unidad.getIdPrograma(), codigo);
        }
        if (codigo == CodigoExcepcionCurricularV2.AVANCE_FALTA_OFERTA) {
            excepcion.getMotivos().add(CodigoMotivoCurricularV2.SIN_OFERTA);
        }
        resultado.getExcepcionesEvaluadas().add(excepcion);
        registrarTraza(resultado, contexto, regla, null, 30, "EXCEPCION_" + codigo.name(), explicacion);
    }

    private List<UnidadEvaluacionCurricularV2DTO> filtrarPendientes(List<UnidadEvaluacionCurricularV2DTO> unidades,
            boolean obligatorias) {
        List<UnidadEvaluacionCurricularV2DTO> resultado = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (esObligatoria(unidad) == obligatorias && esPendiente(unidad)) resultado.add(unidad);
        }
        return resultado;
    }

    private List<UnidadEvaluacionCurricularV2DTO> filtrarOfertadas(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        List<UnidadEvaluacionCurricularV2DTO> resultado = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (Boolean.TRUE.equals(unidad.getOfertada())) resultado.add(unidad);
        }
        return resultado;
    }

    private List<UnidadEvaluacionCurricularV2DTO> filtrarReprobadas(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        return filtrarPorEstado(unidades, EstadoUnidadCurricularV2.REPROBADA);
    }

    private List<UnidadEvaluacionCurricularV2DTO> filtrarPorEstado(List<UnidadEvaluacionCurricularV2DTO> unidades,
            EstadoUnidadCurricularV2 estado) {
        List<UnidadEvaluacionCurricularV2DTO> resultado = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (estado == unidad.getEstado()) resultado.add(unidad);
        }
        return resultado;
    }

    private List<UnidadEvaluacionCurricularV2DTO> filtrarSinOferta(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        List<UnidadEvaluacionCurricularV2DTO> resultado = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (!Boolean.TRUE.equals(unidad.getOfertada()) && esPendiente(unidad)
                    && unidad.getCausaIndisponibilidadOferta() != CausaIndisponibilidadOfertaV2.SIN_CUPO) {
                resultado.add(unidad);
            }
        }
        return resultado;
    }

    private List<UnidadEvaluacionCurricularV2DTO> filtrarExtraordinarias(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        List<UnidadEvaluacionCurricularV2DTO> resultado = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad.getCausa() == CausaPendienteUnidadV2.OTRA_INSTITUCIONAL
                    || unidad.getCausa() == CausaPendienteUnidadV2.LIMITE_INTENTOS) resultado.add(unidad);
        }
        return resultado;
    }

    private boolean esObligatoria(UnidadEvaluacionCurricularV2DTO unidad) {
        return unidad.getTipoUd() != null && unidad.getTipoUd().toUpperCase().contains("OBLIGATORIA");
    }

    private boolean esPendiente(UnidadEvaluacionCurricularV2DTO unidad) {
        return unidad.getEstado() != EstadoUnidadCurricularV2.ACREDITADA
                && unidad.getEstado() != EstadoUnidadCurricularV2.EN_CURSO;
    }

    private boolean existeSeriacionConocida(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (Boolean.TRUE.equals(unidad.getSeriacionDirectaConocida())
                    || Boolean.TRUE.equals(unidad.getSeriacionIndirectaConocida())
                    || Boolean.TRUE.equals(unidad.getSeriacionConocida())) return true;
        }
        return false;
    }

    private boolean existeSeriacionPendiente(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (!esPendiente(unidad)) continue;
            if (Boolean.TRUE.equals(unidad.getSeriacionDirectaConocida())
                    || Boolean.TRUE.equals(unidad.getSeriacionIndirectaConocida())
                    || Boolean.TRUE.equals(unidad.getSeriacionConocida())) return true;
        }
        return false;
    }

    private void asociarExcepcion(ResultadoEvaluacionCurricularV2DTO resultado, Long idPrograma,
            CodigoExcepcionCurricularV2 codigo) {
        if (idPrograma == null) return;
        for (DecisionUnidadCurricularV2DTO decision : resultado.getDecisiones()) {
            if (idPrograma.equals(decision.getIdPrograma())) agregarUnico(decision.getExcepcionesRelacionadas(), codigo);
        }
    }

    /** Aplica C-08, C-09, C-10 y C-13 sobre decisiones ya evaluadas por el motor. */
    private void aplicarOfertaYPreseleccionBloque3(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        int umbral = contexto.getParametrosCurriculares() == null || contexto.getParametrosCurriculares().getUmbralRezagoSevero() == null
                ? 4 : contexto.getParametrosCurriculares().getUmbralRezagoSevero().intValue();
        boolean rezagoSevero = resultado.getCantidadReprobadasVigentes() != null
                && resultado.getCantidadReprobadasVigentes().intValue() >= umbral;
        List<UnidadEvaluacionCurricularV2DTO> prioritarias = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, unidad.getIdPrograma());
            if (decision == null) continue;
            if (tieneAntecedenteNoAcreditado(unidad, unidades)) {
                decision.setHabilitada(Boolean.FALSE);
                agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.BLOQUEADA);
                agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.SERIACION_DIRECTA);
            }
            boolean seriacion = decision.getMotivos().contains(CodigoMotivoCurricularV2.SERIACION_DIRECTA)
                    || decision.getMotivos().contains(CodigoMotivoCurricularV2.SERIACION_INDIRECTA);
            boolean reprobada = esReprobacionVigentePrevia(unidad);
            boolean rutaNuncaInscrita = rezagoSevero && !reprobada && esObligatoria(unidad) && esPendiente(unidad)
                    && unidad.getSemestre() != null && contexto.getSemestreReferencia() != null
                    && unidad.getSemestre().intValue() < contexto.getSemestreReferencia().intValue();
            if (reprobada) {
                decision.setPrioritaria(Boolean.TRUE);
                agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.PRIORITARIA);
                agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.REPROBACION_ACUMULADA);
                if (Boolean.TRUE.equals(unidad.getOfertada()) && !seriacion) prioritarias.add(unidad);
                else if (!Boolean.TRUE.equals(unidad.getOfertada())) decision.setHabilitada(Boolean.FALSE);
            }
            if (esOptativa(unidad) && reprobada) {
                agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.OPTATIVA_REQUERIDA_SIN_SUSTITUCION);
            }
            if (rutaNuncaInscrita && Boolean.TRUE.equals(unidad.getOfertada()) && !seriacion) {
                decision.setHabilitada(Boolean.TRUE);
                agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.HABILITADA);
                agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.RUTA_REGULARIZACION_NUNCA_INSCRITA);
            } else if (rezagoSevero && !reprobada && !rutaNuncaInscrita) {
                decision.setHabilitada(Boolean.FALSE);
                agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.BLOQUEADA);
                agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.REZAGO_SEVERO_SOLO_REGULARIZACION);
            }
        }
        java.util.Collections.sort(prioritarias, new java.util.Comparator<UnidadEvaluacionCurricularV2DTO>() {
            public int compare(UnidadEvaluacionCurricularV2DTO a, UnidadEvaluacionCurricularV2DTO b) {
                int diferencia = contarIntentosReprobados(b) - contarIntentosReprobados(a);
                String claveA = a.getClave() == null ? "" : a.getClave();
                String claveB = b.getClave() == null ? "" : b.getClave();
                return diferencia != 0 ? diferencia : claveA.compareTo(claveB);
            }
        });
        int orden = 1;
        for (UnidadEvaluacionCurricularV2DTO unidad : prioritarias) {
            DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, unidad.getIdPrograma());
            if (decision.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.BLOQUEADA)) continue;
            unidad.setPreseleccionada(Boolean.TRUE); unidad.setOrdenPreseleccion(Integer.valueOf(orden++));
            unidad.setDesmarcable(Boolean.valueOf(!rezagoSevero));
        }
        validarCargaQuintoSinOmitirPendiente(resultado, contexto, unidades);
        validarSeleccionOptativasBloque3(resultado, contexto, unidades);
        validarNuevoIngresoBloque3(resultado, contexto, unidades);
        if (Boolean.TRUE.equals(contexto.getAdmitido()) && Boolean.FALSE.equals(contexto.getInscripcionInicialRealizada())
                && unidades.isEmpty()) {
            agregarUnico(resultado.getRestricciones(), CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
        }
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (!Boolean.TRUE.equals(unidad.getCondicionInterbloque())) continue;
            DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, unidad.getIdPrograma());
            if (decision != null && !decision.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.BLOQUEADA)) {
                unidad.setPreseleccionada(Boolean.TRUE); unidad.setDesmarcable(Boolean.FALSE);
                agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.PRESELECCION_CONDICIONADA_INTERBLOQUE);
            }
        }
    }

    /** Tabla 2 RN-7.1: catálogo común de lectura para PE2024/2027. */
    private int semestreCatalogoOptativa(ContextoEvaluacionCurricularV2DTO contexto, String clave) {
        if (contexto.getParametrosCurriculares() != null && contexto.getParametrosCurriculares().getCatalogoOptativas() != null) {
            for (String entrada : contexto.getParametrosCurriculares().getCatalogoOptativas().split(",")) {
                String[] par = entrada.split(":");
                if (par.length == 2 && par[0].trim().equals(clave)) try { return Integer.parseInt(par[1].trim()); } catch (NumberFormatException e) { return -1; }
            }
            return -1;
        }
        if (clave == null) return -1;
        if ("USTAPREN".equals(clave) || "RECVIRTU".equals(clave) || "PRINVEST".equals(clave)) return 1;
        if ("PGHRATRS".equals(clave) || "INAVIRTU".equals(clave) || "ANDHUMAN".equals(clave)) return 2;
        if ("BAIDOCUM".equals(clave) || "BAICAMPO".equals(clave) || "REACADEM".equals(clave) || "INARTIFI".equals(clave)) return 3;
        if ("PECRITIC".equals(clave) || "HIMEXICO".equals(clave) || "MECMUNDI".equals(clave)) return 4;
        return -1;
    }

    private void validarSeleccionOptativasBloque3(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        List<UnidadEvaluacionCurricularV2DTO> seleccionadas = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO u : unidades)
            if (contexto.getIdsUnidadesPretendidas().contains(u.getIdPrograma()) && esOptativa(u)) seleccionadas.add(u);
        if (seleccionadas.isEmpty()) return;
        boolean irregular = "IRREGULAR".equals(resultado.getSituacionAcademicaCalculada());
        int nuevas = 0; boolean hayReferenciaDisponible = false; boolean seleccionaReferencia = false; Set<String> bloques = new HashSet<String>();
        for (UnidadEvaluacionCurricularV2DTO u : unidades)
            if (esOptativa(u) && Boolean.TRUE.equals(u.getOfertada()) && u.getSemestre() != null
                    && u.getSemestre().equals(contexto.getSemestreReferencia()) && u.getEstado() != EstadoUnidadCurricularV2.ACREDITADA) hayReferenciaDisponible = true;
        for (UnidadEvaluacionCurricularV2DTO u : seleccionadas)
            if (u.getSemestre() != null && u.getSemestre().equals(contexto.getSemestreReferencia())) seleccionaReferencia = true;
        for (UnidadEvaluacionCurricularV2DTO u : seleccionadas) {
            DecisionUnidadCurricularV2DTO d = buscarDecision(resultado, u.getIdPrograma());
            int semestreCatalogo = semestreCatalogoOptativa(contexto, u.getClave()); boolean recursa = esReprobacionVigentePrevia(u);
            if (semestreCatalogo < 0) { bloquearPorOptativa(resultado, d, CodigoMotivoCurricularV2.DATOS_INSUFICIENTES); continue; }
            if (!recursa) {
                nuevas++; if (bloques.contains(u.getBloque())) bloquearPorOptativa(resultado, d, CodigoMotivoCurricularV2.OPTATIVA_LIMITE_SELECCION); bloques.add(u.getBloque());
                if (u.getSemestre() != null && contexto.getSemestreReferencia() != null && u.getSemestre().intValue() != contexto.getSemestreReferencia().intValue()) {
                    if (irregular) bloquearPorOptativa(resultado, d, CodigoMotivoCurricularV2.OPTATIVA_OTRO_SEMESTRE_SOLO_REGULAR);
                    else if (hayReferenciaDisponible && !seleccionaReferencia) bloquearPorOptativa(resultado, d, CodigoMotivoCurricularV2.OPTATIVA_SEMESTRE_REFERENCIA_PRIMERO);
                }
            }
            if ("INARTIFI".equals(u.getClave()) && contarOptativasAcreditadas(unidades) >= 8 && !recursa) bloquearPorOptativa(resultado, d, CodigoMotivoCurricularV2.OPTATIVA_LIMITE_SELECCION);
        }
        if (nuevas > 2) for (UnidadEvaluacionCurricularV2DTO u : seleccionadas) if (!esReprobacionVigentePrevia(u)) bloquearPorOptativa(resultado, buscarDecision(resultado, u.getIdPrograma()), CodigoMotivoCurricularV2.OPTATIVA_LIMITE_SELECCION);
    }

    private int contarOptativasAcreditadas(List<UnidadEvaluacionCurricularV2DTO> unidades) { int n=0; for (UnidadEvaluacionCurricularV2DTO u:unidades) if(esOptativa(u)&&u.getEstado()==EstadoUnidadCurricularV2.ACREDITADA)n++; return n; }
    private void bloquearPorOptativa(ResultadoEvaluacionCurricularV2DTO r, DecisionUnidadCurricularV2DTO d, CodigoMotivoCurricularV2 m) { if(d==null)return;d.setHabilitada(Boolean.FALSE);agregarUnico(d.getEstadosSombra(),EstadoDecisionUnidadCurricularV2.BLOQUEADA);agregarUnico(d.getMotivos(),m);agregarUnico(r.getRestricciones(),m); }

    private void validarNuevoIngresoBloque3(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        if (!Boolean.TRUE.equals(contexto.getAdmitido()) || Boolean.TRUE.equals(contexto.getInscripcionInicialRealizada())) return;
        int obligatorias=0,optativas=0; Set<String> claves=new HashSet<String>(); Set<String> bloques=new HashSet<String>();
        for(UnidadEvaluacionCurricularV2DTO u:unidades) if(contexto.getIdsUnidadesPretendidas().contains(u.getIdPrograma())) { if(esObligatoria(u)) obligatorias++; if(esOptativa(u)){optativas++;claves.add(u.getClave());bloques.add(u.getBloque());} }
        if (obligatorias != 4 || optativas != 2 || claves.size()!=2 || bloques.size()!=2)
            agregarUnico(resultado.getRestricciones(), CodigoMotivoCurricularV2.OPTATIVA_LIMITE_SELECCION);
    }

    private boolean tieneAntecedenteNoAcreditado(UnidadEvaluacionCurricularV2DTO unidad,
            List<UnidadEvaluacionCurricularV2DTO> unidades) {
        for (RelacionSeriacionCurricularV2DTO relacion : unidad.getRelacionesSeriacion()) {
            for (UnidadEvaluacionCurricularV2DTO candidata : unidades) {
                if (relacion.getIdAntecedente() != null && relacion.getIdAntecedente().equals(candidata.getIdPrograma())
                        && candidata.getEstado() != EstadoUnidadCurricularV2.ACREDITADA) return true;
            }
        }
        return false;
    }

    private int contarIntentosReprobados(UnidadEvaluacionCurricularV2DTO unidad) {
        int total = 0;
        for (IntentoAcreditacionUnidadV2DTO intento : unidad.getIntentosAcreditacion())
            if (intento != null && intento.getEstado() == EstadoIntentoAcreditacionV2.REPROBADO) total++;
        return total;
    }

    private void validarCargaQuintoSinOmitirPendiente(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        boolean quinto = false;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades)
            if (contexto.getIdsUnidadesPretendidas().contains(unidad.getIdPrograma()) && Integer.valueOf(5).equals(unidad.getSemestre())) quinto = true;
        if (!quinto || !Boolean.TRUE.equals(resultado.getAvanceCondicionado())) return;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (!esPendienteAvanceCondicionado(unidad) || !Boolean.TRUE.equals(unidad.getOfertada())
                    || unidad.getSemestre() == null || unidad.getSemestre().intValue() > 4
                    || contexto.getIdsUnidadesPretendidas().contains(unidad.getIdPrograma())) continue;
            DecisionUnidadCurricularV2DTO decision = buscarDecision(resultado, unidad.getIdPrograma());
            if (decision != null) agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.PENDIENTE_OMITIDA_AVANCE_CONDICIONADO);
            agregarUnico(resultado.getRestricciones(), CodigoMotivoCurricularV2.PENDIENTE_OMITIDA_AVANCE_CONDICIONADO);
        }
    }

    private boolean esPendienteAvanceCondicionado(UnidadEvaluacionCurricularV2DTO unidad) {
        return esReprobacionVigentePrevia(unidad) || unidad.getEstado() == EstadoUnidadCurricularV2.NO_CURSADA
                || unidad.getEstado() == EstadoUnidadCurricularV2.BAJA_PARCIAL;
    }

    private DecisionUnidadCurricularV2DTO buscarDecision(ResultadoEvaluacionCurricularV2DTO resultado,
            Long idPrograma) {
        if (idPrograma == null) return null;
        for (DecisionUnidadCurricularV2DTO decision : resultado.getDecisiones()) {
            if (idPrograma.equals(decision.getIdPrograma())) return decision;
        }
        return null;
    }

    private DecisionUnidadCurricularV2DTO crearDecisionSombra(UnidadEvaluacionCurricularV2DTO unidad) {
        DecisionUnidadCurricularV2DTO decision = new DecisionUnidadCurricularV2DTO();
        decision.setIdPrograma(unidad.getIdPrograma());
        decision.setOfertaOrigen(unidad.getOfertada());
        decision.setOfertaEvaluadaV2(unidad.getOfertada());
        decision.setSeleccionableOrigen(unidad.getSeleccionableOrigen());
        decision.setBloqueadaOrigen(unidad.getBloqueadaOrigen());
        // Compatibilidad con Fase 0/1A: habilitada conserva la fotografía de la oferta origen.
        decision.setHabilitada(Boolean.valueOf(Boolean.TRUE.equals(unidad.getOfertada())));
        decision.getReferenciasFuente().addAll(unidad.getReferenciasHistoricas());
        if (Boolean.TRUE.equals(unidad.getOfertada())) {
            agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.HABILITADA);
            decision.setExplicacionTecnica("Oferta origen disponible; la evaluación V2 la replica para comparación.");
        } else {
            // La falta de oferta no borra la prioridad curricular ni vuelve seleccionable la UD.
            agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.PRIORITARIA);
            agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION);
            decision.setPrioritaria(Boolean.TRUE);
            decision.setHabilitada(Boolean.FALSE);
            agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.SIN_OFERTA);
            decision.setExplicacionTecnica("La UD permanece prioritaria, pero no está ofertada y no es seleccionable.");
        }
        return decision;
    }

    private void registrarCondicion(ResultadoEvaluacionCurricularV2DTO resultado,
            DecisionUnidadCurricularV2DTO decision, ContextoEvaluacionCurricularV2DTO contexto,
            CondicionCurricularV2 condicion, CodigoReglaCurricularV2 regla,
            CodigoMotivoCurricularV2 motivo, int prioridad, String resultadoTraza, String detalle) {
        agregarUnico(resultado.getCondicionesDetectadas(), condicion);
        agregarUnico(resultado.getRestricciones(), motivo);
        agregarUnico(decision.getMotivos(), motivo);
        if (motivo == CodigoMotivoCurricularV2.REPROBACION_ACUMULADA) {
            agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.PRIORITARIA);
        }
        TrazaReglaAplicadaV2DTO traza = crearTraza(contexto, regla, motivo, prioridad, resultadoTraza, detalle);
        decision.getTrazas().add(traza);
        resultado.getTrazas().add(traza);
        agregarUnico(resultado.getReglasEvaluadas(), regla);
        actualizarPrioridad(resultado, prioridad);
    }

    private void registrarTraza(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, CodigoReglaCurricularV2 regla,
            CodigoMotivoCurricularV2 motivo, int prioridad, String resultadoTraza, String detalle) {
        resultado.getTrazas().add(crearTraza(contexto, regla, motivo, prioridad, resultadoTraza, detalle));
        agregarUnico(resultado.getReglasEvaluadas(), regla);
        actualizarPrioridad(resultado, prioridad);
    }

    private TrazaReglaAplicadaV2DTO crearTraza(ContextoEvaluacionCurricularV2DTO contexto,
            CodigoReglaCurricularV2 regla, CodigoMotivoCurricularV2 motivo, int prioridad,
            String resultadoTraza, String detalle) {
        TrazaReglaAplicadaV2DTO traza = new TrazaReglaAplicadaV2DTO();
        traza.setCodigoRegla(regla);
        traza.setCodigoMotivo(motivo);
        traza.setVersionReglas(contexto.getVersionReglas());
        traza.setPrioridad(Integer.valueOf(prioridad));
        traza.setAplicada(Boolean.FALSE);
        traza.setResultado(resultadoTraza);
        traza.setDetalle(detalle);
        traza.setFechaEvaluacion(contexto.getFechaEvaluacion() == null ? new Date() : contexto.getFechaEvaluacion());
        return traza;
    }

    private void actualizarPrioridad(ResultadoEvaluacionCurricularV2DTO resultado, int prioridad) {
        if (resultado.getPrioridadTecnicaDominante() == null
                || prioridad > resultado.getPrioridadTecnicaDominante().intValue()) {
            resultado.setPrioridadTecnicaDominante(Integer.valueOf(prioridad));
        }
    }

    private <T> void agregarUnico(List<T> destino, T valor) {
        if (valor != null && !destino.contains(valor)) destino.add(valor);
    }

    private void agregarExplicacion(DecisionUnidadCurricularV2DTO decision, String texto) {
        if (decision.getExplicacionTecnica() == null || decision.getExplicacionTecnica().isEmpty()) {
            decision.setExplicacionTecnica(texto);
        } else {
            decision.setExplicacionTecnica(decision.getExplicacionTecnica() + " " + texto);
        }
    }

    private String obtenerLlaveUnidad(UnidadEvaluacionCurricularV2DTO unidad) {
        if (unidad.getIdPrograma() != null) return "ID:" + unidad.getIdPrograma();
        if (unidad.getClave() != null && !unidad.getClave().trim().isEmpty()) return "CLAVE:" + unidad.getClave().trim();
        return "SIN_IDENTIFICADOR:" + System.identityHashCode(unidad);
    }
}
