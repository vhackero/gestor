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
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaMinimaCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCargaMinimaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaMaximaCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCargaMaximaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCargaAcademicaV2;
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

        if (contexto.getSemestreObjetivo() == null) {
            evaluacion.setAplicaCargaMinimaNormativa(null);
            evaluacion.setEstado(EstadoCargaMinimaCurricularV2.NO_DETERMINADO);
            agregarUnico(evaluacion.getMotivos(), CodigoMotivoCurricularV2.DATOS_INSUFICIENTES);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_4_1,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 55,
                    "CARGA_MINIMA_SEMESTRE_OBJETIVO_NO_DETERMINADO",
                    "No se evalúa la carga mínima sin semestre objetivo verificable.");
            return;
        }
        if (contexto.getSemestreObjetivo().intValue() <= 1) {
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
        if (Integer.valueOf(5).equals(contexto.getSemestreObjetivo())) {
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
        Boolean ctr01 = procedenciaCtr01(contexto);
        integrada.setCtr01Rn61Procedente(ctr01);
        if (Boolean.TRUE.equals(ctr01)) {
            agregarUnico(integrada.getReglasRelacionadas(), CodigoReglaCurricularV2.RN_6_1);
            integrada.getEvidencias().add("CTR_01_RN_6_1_PROCEDENTE_SIN_ACTIVAR");
        } else if (Integer.valueOf(5).equals(contexto.getSemestreObjetivo()) && ctr01 == null) {
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

        Boolean aplica = procedenciaCtr01(contexto);
        control.setAplicaCtr01(aplica);
        control.setAplicaRn61(aplica);
        if (aplica == null) {
            control.setEstadoControlAvanceAnual(EstadoControlAvanceAnualV2.REQUIERE_REVISION);
            control.setRequiereRevision(Boolean.TRUE);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.CTR_01,
                    CodigoMotivoCurricularV2.DATOS_INSUFICIENTES, 100,
                    "CTR01_REQUIERE_REVISION",
                    "No es posible determinar semestre objetivo o periodo normativo para CTR-01.");
            return;
        }
        if (!aplica.booleanValue()) {
            control.setEstadoControlAvanceAnual(EstadoControlAvanceAnualV2.NO_APLICA);
            registrarTraza(resultado, contexto, CodigoReglaCurricularV2.CTR_01, null, 100,
                    "CTR01_NO_APLICA",
                    "CTR-01 sólo aplica desde 2027-1 cuando el semestre objetivo curricular es quinto.");
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
        boolean restriccionConcurrente = rn64Activa || Boolean.TRUE.equals(contexto.getLimitePermanenciaExcedido())
                || Boolean.TRUE.equals(contexto.getLimiteInactividadAlcanzado())
                || Boolean.TRUE.equals(contexto.getBajaDefinitivaRegistrada())
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

        EstadoCondicionAvanceV2 estadoSeriacion = evaluarSeriacionQuinto(control, unidades);
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
            List<UnidadEvaluacionCurricularV2DTO> unidades) {
        boolean revision = false;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad == null || !Integer.valueOf(5).equals(unidad.getSemestre())) continue;
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
        for (Long id : control.getUdsQuintoCompatiblesRn612()) {
            agregarIdUnico(control.getUdsQuintoSinDependenciaSeriacionRn613(), id);
            agregarSupuesto(control, CodigoSupuestoAvanceCondicionadoV2.SIN_DEPENDENCIA_SERIACION, listaId(id),
                    "SIN_DEPENDENCIA_DIRECTA_O_INDIRECTA_CONOCIDA", "RN-6.1.2 clasificó la UD de quinto como compatible.");
        }
        if (control.getSupuestosAvanceTipificados().isEmpty()) control.setEstadoSupuestosAvance(
                Boolean.TRUE.equals(control.getRequiereRevision()) ? EstadoSupuestosAvanceV2.REQUIERE_REVISION : EstadoSupuestosAvanceV2.SIN_SUPUESTO_TIPIFICADO);
        else control.setEstadoSupuestosAvance(control.getSupuestosAvanceTipificados().size() == 1
                ? EstadoSupuestosAvanceV2.SUPUESTO_TIPIFICADO_IDENTIFICADO : EstadoSupuestosAvanceV2.MULTIPLES_SUPUESTOS_TIPIFICADOS);
        registrarTraza(resultado, contexto, CodigoReglaCurricularV2.RN_6_1_3,
                Boolean.TRUE.equals(control.getAplicaRn64()) ? CodigoMotivoCurricularV2.REZAGO_SEVERO : null, 100,
                "RN613_" + control.getEstadoSupuestosAvance(), "Supuestos=" + control.getSupuestosAvanceTipificados().size()
                        + "; condicionesRN612=" + control.getEstadoCondicionesComunesAvance()
                        + "; RN64=" + control.getAplicaRn64() + "; no autoriza avance condicionado.");
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
        if(Boolean.TRUE.equals(contexto.getRestriccionPeNormativaConocida())&&Boolean.TRUE.equals(contexto.getRestriccionPeNormativaImpeditiva()))c.getImpedimentosAvanceRn615().add("RESTRICCION_PE_NORMATIVA:"+contexto.getCodigoRestriccionPeNormativa()+":"+contexto.getFuenteRestriccionPeNormativa());
        if(!Boolean.TRUE.equals(contexto.getSancionImpedimentoConocida())||!Boolean.TRUE.equals(contexto.getRestriccionPeNormativaConocida())||Boolean.TRUE.equals(c.getRequiereRevision())){c.setEstadoResolucionAvance(EstadoResolucionAvanceV2.REQUIERE_REVISION);}
        else if(!c.getImpedimentosAvanceRn615().isEmpty())c.setEstadoResolucionAvance(EstadoResolucionAvanceV2.NO_PROCEDE_AVANCE_CONDICIONADO);
        else if(c.getEstadoSupuestosAvance()==EstadoSupuestosAvanceV2.SIN_SUPUESTO_TIPIFICADO){c.setEstadoResolucionAvance(EstadoResolucionAvanceV2.REQUIERE_RESOLUCION_EXTRAORDINARIA);c.getRutaInstitucionalExtraordinariaRn616().add("DIRECCION_DIVISION:PENDIENTE_FUNDAMENTACION_ACADEMICA");c.getRutaInstitucionalExtraordinariaRn616().add("CAI:PENDIENTE_DEFINICION_ACADEMICA");c.getRutaInstitucionalExtraordinariaRn616().add("DAEAE:PENDIENTE_DETERMINACION_ESCOLAR_NORMATIVA");c.getRutaInstitucionalExtraordinariaRn616().add("CTIE:RESOLUCION_DOCUMENTADA_PENDIENTE_EJECUCION");}
        else c.setEstadoResolucionAvance(EstadoResolucionAvanceV2.CONTINUA_EVALUACION_ORDINARIA_RN61);
        registrarTraza(resultado,contexto,CodigoReglaCurricularV2.RN_6_1_5,null,100,"RN615_"+c.getEstadoResolucionAvance(),"Impedimentos="+c.getImpedimentosAvanceRn615());registrarTraza(resultado,contexto,CodigoReglaCurricularV2.RN_6_1_6,null,100,"RN616_"+c.getEstadoResolucionAvance(),"Ruta="+c.getRutaInstitucionalExtraordinariaRn616());
    }

    private void agregarIdUnico(List<Long> destino, Long id) {
        if (id != null && !destino.contains(id)) destino.add(id);
    }

    private Boolean procedenciaCtr01(ContextoEvaluacionCurricularV2DTO contexto) {
        if (contexto.getSemestreObjetivo() == null) return null;
        if (!Integer.valueOf(5).equals(contexto.getSemestreObjetivo())) return Boolean.FALSE;
        String periodo = contexto.getPeriodoNormativo();
        if (periodo == null || !periodo.matches("\\d{4}-[12]")) return null;
        return Boolean.valueOf(periodo.compareTo("2027-1") >= 0);
    }

    private boolean contieneCausa(List<String> valores, String causa) {
        for (String valor : valores) if (valor != null && valor.contains(causa)) return true;
        return false;
    }

    private void evaluarExcepcionesTipificadas(ResultadoEvaluacionCurricularV2DTO resultado,
            ContextoEvaluacionCurricularV2DTO contexto, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        List<UnidadEvaluacionCurricularV2DTO> obligatoriasPendientes = filtrarPendientes(unidades, true);

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
