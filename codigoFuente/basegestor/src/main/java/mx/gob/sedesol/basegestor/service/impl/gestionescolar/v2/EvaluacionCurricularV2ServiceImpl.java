package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
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
        evaluarMejoraPromedio(resultado, contexto, unidadesNormalizadas);
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
        evaluarExcepcionesTipificadas(resultado, contexto, unidadesNormalizadas);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.CTR_04, null, 15,
                "EVALUACION_CONCURRENTE",
                "Las condiciones y motivos se acumulan; no se reduce el resultado a una categoría excluyente.");
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_2_2, null, 25,
                "EVALUACION_PREVIA_REINSCRIPCION_SOMBRA",
                "Se construyó una oferta individual V2 separada de la oferta y seleccionabilidad origen.");
        resultado.getMensajes().add("Resultado generado en modo sombra; no modifica la elegibilidad vigente.");
        return resultado;
    }

    private void determinarSituacionAcademica(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, int reprobadas, boolean datosInsuficientes) {
        String situacion = reprobadas > 0 ? "IRREGULAR" : (datosInsuficientes ? "NO_DETERMINADA" : "REGULAR");
        resultado.setSituacionAcademica(situacion);
        resultado.setSituacionAcademicaCalculada(situacion);
        resultado.setSituacionAcademicaActualCalculada(situacion);
        resultado.setCantidadReprobadasVigentes(Integer.valueOf(reprobadas));
        resultado.setCargaMaximaFuturaSombra(Integer.valueOf(reprobadas > 0 ? 6 : 8));
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
                || resultado.getEstadoActividadAcademica() == EstadoActividadAcademicaV2.LIMITE_INACTIVIDAD_ALCANZADO
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
            resultado.getCondicionesRecuperacionNoCumplidas().add("LIMITE_PERMANENCIA_EXCEDIDO");
        else resultado.getCondicionesRecuperacionNoCumplidas().add("PERMANENCIA_NO_DETERMINADA");
        if (!actividadIndeterminada && !actividadImpide) resultado.getCondicionesRecuperacionCumplidas().add("ACTIVIDAD_COMPATIBLE");
        else if (actividadIndeterminada) resultado.getCondicionesRecuperacionNoCumplidas().add("ACTIVIDAD_NO_DETERMINADA");
        else resultado.getCondicionesRecuperacionNoCumplidas().add("ACTIVIDAD_IMPIDE_CONTINUIDAD:" + resultado.getEstadoActividadAcademica());

        if (hayReprobadas || seriacionPendiente || severa || actividadImpide
                || Boolean.TRUE.equals(contexto.getLimitePermanenciaExcedido())) {
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
            EvaluacionIntentosUnidadV2DTO evaluacion = evaluarIntentosUnidad(unidad);
            resultado.getEvaluacionesIntentos().add(evaluacion);
            CodigoMotivoCurricularV2 motivo = evaluacion.getEstadoLimite() == EstadoLimiteIntentosV2.LIMITE_AGOTADO
                    ? CodigoMotivoCurricularV2.LIMITE_INTENTOS
                    : (evaluacion.getEstadoLimite() == EstadoLimiteIntentosV2.REQUIERE_REVISION
                            || evaluacion.getEstadoLimite() == EstadoLimiteIntentosV2.NO_DETERMINADO
                            ? CodigoMotivoCurricularV2.DATOS_INSUFICIENTES : null);
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
                        || evaluacion.getEstadoLimite() == EstadoLimiteIntentosV2.REQUIERE_REVISION)
                    agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION);
            }
        }
    }

    private EvaluacionIntentosUnidadV2DTO evaluarIntentosUnidad(UnidadEvaluacionCurricularV2DTO unidad) {
        EvaluacionIntentosUnidadV2DTO evaluacion = new EvaluacionIntentosUnidadV2DTO();
        evaluacion.setIdPrograma(unidad.getIdPrograma()); evaluacion.setClave(unidad.getClave());
        if (unidad.getEstado() == EstadoUnidadCurricularV2.ACREDITADA) {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA);
        }
        Set<String> ejecuciones = new HashSet<String>();
        boolean identidadInsuficiente = false;
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
        }
        if (unidad.getIntentosAcreditacion().isEmpty() && unidad.getIntentosFallidosHistoricosConfiables() != null) {
            evaluacion.setIntentosIdentificados(unidad.getIntentosFallidosHistoricosConfiables());
            evaluacion.setIntentosFallidos(unidad.getIntentosFallidosHistoricosConfiables());
            evaluacion.getEvidencias().add("RESUMEN_CANONICO_INTENTOS_FALLIDOS:" + unidad.getIntentosFallidosHistoricosConfiables());
        } else if (unidad.getIntentosAcreditacion().isEmpty() && unidad.getIntentos() != null) {
            identidadInsuficiente = true;
            evaluacion.getEvidencias().add("CONTEO_LEGADO_SIN_CALIDAD_DE_INTENTO:" + unidad.getIntentos());
        }
        if (evaluacion.getIntentosAcreditados().intValue() > 0 || unidad.getEstado() == EstadoUnidadCurricularV2.ACREDITADA) {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA);
        } else if (identidadInsuficiente) {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.REQUIERE_REVISION);
        } else if (evaluacion.getIntentosFallidos().intValue() > 3 || evaluacion.getIntentosIdentificados().intValue() > 3) {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.REQUIERE_REVISION);
            evaluacion.setRequiereEscalamiento(Boolean.TRUE);
        } else if (evaluacion.getIntentosFallidos().intValue() == 3) {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.LIMITE_AGOTADO);
            evaluacion.setLimiteAgotado(Boolean.TRUE); evaluacion.setRequiereEscalamiento(Boolean.TRUE);
        } else if (evaluacion.getIntentosFallidos().intValue() == 2) {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.ULTIMA_OPORTUNIDAD);
            evaluacion.setUltimaOportunidad(Boolean.TRUE);
        } else {
            evaluacion.setEstadoLimite(EstadoLimiteIntentosV2.DISPONIBLE);
        }
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
        } else if (resultado.getEstadoActividadAcademica() != EstadoActividadAcademicaV2.ACTIVO) {
            evaluacion.setEstado(resultado.getEstadoActividadAcademica() == EstadoActividadAcademicaV2.NO_DETERMINADO
                    ? EstadoMejoraPromedioV2.REQUIERE_REVISION : EstadoMejoraPromedioV2.ESTUDIANTE_NO_ACTIVO);
            evaluacion.getMotivos().add("ESTADO_ACTIVIDAD:" + resultado.getEstadoActividadAcademica());
        } else if (!Boolean.TRUE.equals(contexto.getDictaminacionEgresoConocida())) {
            evaluacion.setEstado(EstadoMejoraPromedioV2.REQUIERE_REVISION); evaluacion.getMotivos().add("EGRESO_NO_DETERMINADO");
        } else if (Boolean.TRUE.equals(contexto.getDictaminacionEgresoIniciada())) {
            evaluacion.setEstado(EstadoMejoraPromedioV2.EGRESO_INICIADO); evaluacion.getMotivos().add("DICTAMINACION_EGRESO_INICIADA");
        } else if (resultado.getOportunidadesMejoraUtilizadas() == null) {
            evaluacion.setEstado(EstadoMejoraPromedioV2.REQUIERE_REVISION); evaluacion.getMotivos().add("OPORTUNIDADES_MEJORA_NO_DETERMINADAS");
        } else if (resultado.getOportunidadesMejoraUtilizadas().intValue() >= 3) {
            evaluacion.setEstado(EstadoMejoraPromedioV2.LIMITE_MEJORA_AGOTADO); evaluacion.getMotivos().add("LIMITE_GLOBAL_MEJORA_AGOTADO");
        } else if (!Boolean.TRUE.equals(unidad.getOfertada())) {
            evaluacion.setEstado(EstadoMejoraPromedioV2.NO_ELEGIBLE); evaluacion.getMotivos().add("UD_NO_OFERTADA");
        } else if (unidad.getGrupoActivoConocido() == null || unidad.getCupoDisponibleConocido() == null) {
            evaluacion.setEstado(EstadoMejoraPromedioV2.REQUIERE_REVISION); evaluacion.getMotivos().add("GRUPO_O_CUPO_NO_DETERMINADO");
        } else if (!Boolean.TRUE.equals(unidad.getGrupoActivoConocido())) {
            evaluacion.setEstado(EstadoMejoraPromedioV2.NO_ELEGIBLE); evaluacion.getMotivos().add("SIN_GRUPO_ACTIVO");
        } else if (!Boolean.TRUE.equals(unidad.getCupoDisponibleConocido())) {
            evaluacion.setEstado(EstadoMejoraPromedioV2.SIN_CUPO); evaluacion.getMotivos().add("SIN_CUPO");
        } else {
            evaluacion.setEstado(EstadoMejoraPromedioV2.ELEGIBLE_EN_SOMBRA);
            evaluacion.getMotivos().add("PRIORIDAD_INFERIOR_A_PRIMERA_INSCRIPCION_Y_REPROBACION");
            evaluacion.getMotivos().add("OBJETIVO_REFERENCIA_PROMEDIO_8_5");
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

    private void evaluarExcepcionesTipificadas(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        List<UnidadEvaluacionCurricularV2DTO> obligatoriasPendientes = filtrarPendientes(unidades, true);
        List<UnidadEvaluacionCurricularV2DTO> optativasPendientes = filtrarPendientes(unidades, false);
        List<UnidadEvaluacionCurricularV2DTO> obligatoriasOfertadas = filtrarOfertadas(obligatoriasPendientes);
        List<UnidadEvaluacionCurricularV2DTO> optativasOfertadas = filtrarOfertadas(optativasPendientes);
        Integer cargaMinima = contexto.getCargaMinimaOrigen();

        if (cargaMinima != null && cargaMinima.intValue() > 0
                && obligatoriasOfertadas.size() < cargaMinima.intValue()) {
            registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.CARGA_MINIMA_OBLIGATORIAS_INSUFICIENTES,
                    TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_4_1,
                    EstadoExcepcionCurricularV2.APLICABLE, obligatoriasOfertadas,
                    "OBLIGATORIAS_ELEGIBLES_OFERTADAS<" + cargaMinima,
                    "Las UD obligatorias elegibles/ofertadas no alcanzan la carga mínima; sólo se registra la excepción en sombra.");
        }
        if (cargaMinima != null && cargaMinima.intValue() > 0
                && obligatoriasOfertadas.size() + optativasOfertadas.size() < cargaMinima.intValue()) {
            List<UnidadEvaluacionCurricularV2DTO> relacionadas = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
            relacionadas.addAll(obligatoriasOfertadas);
            relacionadas.addAll(optativasOfertadas);
            registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.CARGA_MINIMA_OPTATIVAS_INSUFICIENTES,
                    TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_4_1,
                    EstadoExcepcionCurricularV2.APLICABLE, relacionadas,
                    "OBLIGATORIAS_Y_OPTATIVAS_ELEGIBLES_OFERTADAS<" + cargaMinima,
                    "Incluso al considerar optativas elegibles no se alcanza la carga mínima; no se altera carga productiva.");
        }
        if (obligatoriasPendientes.size() == 1 && obligatoriasOfertadas.size() == 1) {
            registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.CARGA_MINIMA_UNICA_OBLIGATORIA_PENDIENTE,
                    TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_4_1,
                    EstadoExcepcionCurricularV2.APLICABLE, obligatoriasOfertadas,
                    "UNICA_OBLIGATORIA_PENDIENTE_DISPONIBLE",
                    "Existe una única obligatoria pendiente disponible; la excepción de carga mínima sólo queda trazada.");
        }

        if (Integer.valueOf(5).equals(contexto.getSemestreObjetivo())) {
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
            if (!obligatoriasPendientes.isEmpty() && !existeSeriacionConocida(unidades)) {
                registrarExcepcion(resultado, contexto, CodigoExcepcionCurricularV2.AVANCE_AUSENCIA_SERIACION,
                        TipoExcepcionCurricularV2.TIPIFICADA, CodigoReglaCurricularV2.RN_6_1,
                        EstadoExcepcionCurricularV2.CANDIDATA, obligatoriasPendientes,
                        "AUSENCIA_SERIACION_DIRECTA_E_INDIRECTA_CONOCIDA",
                        "La ausencia de seriación se documenta, pero no autoriza por sí sola una UD.");
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
            agregarUnico(decision.getEstadosSombra(), EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION);
            agregarUnico(decision.getMotivos(), CodigoMotivoCurricularV2.SIN_OFERTA);
            decision.setExplicacionTecnica("La UD no forma parte de la oferta origen y requiere revisión en V2.");
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
