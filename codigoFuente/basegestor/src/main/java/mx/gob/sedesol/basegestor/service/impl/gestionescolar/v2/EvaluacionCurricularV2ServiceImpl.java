package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoDecisionUnidadCurricularV2;
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
        List<UnidadEvaluacionCurricularV2DTO> unidadesNormalizadas = normalizarUnidades(contexto.getUnidades());
        for (UnidadEvaluacionCurricularV2DTO unidad : unidadesNormalizadas) {
            DecisionUnidadCurricularV2DTO decision = crearDecisionSombra(unidad);
            decision.setPrioritaria(Boolean.valueOf(unidad.getEstado() == EstadoUnidadCurricularV2.REPROBADA));

            if (unidad.getEstado() == EstadoUnidadCurricularV2.REPROBADA) {
                reprobadas++;
                registrarCondicion(resultado, decision, contexto, CondicionCurricularV2.REPROBACION,
                        CodigoReglaCurricularV2.RN_1_2, CodigoMotivoCurricularV2.REPROBACION_ACUMULADA,
                        80, "REPROBACION_NORMALIZADA", "La UD conserva una condición de reprobación en modo sombra.");
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

        resultado.setSituacionAcademica(reprobadas > 0 ? "IRREGULAR" : "REGULAR");
        resultado.setTotalReprobadas(Integer.valueOf(reprobadas));
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

    private void asociarExcepcion(ResultadoEvaluacionCurricularV2DTO resultado, Long idPrograma,
            CodigoExcepcionCurricularV2 codigo) {
        if (idPrograma == null) return;
        for (DecisionUnidadCurricularV2DTO decision : resultado.getDecisiones()) {
            if (idPrograma.equals(decision.getIdPrograma())) agregarUnico(decision.getExcepcionesRelacionadas(), codigo);
        }
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
