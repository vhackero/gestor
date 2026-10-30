package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MotivoDecisionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaMaximaCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionUtils;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ContextoEvaluacionCurricularV2Adapter;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.EvaluacionCurricularV2Service;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.SimuladorCurricularV2Service;

@Service("simuladorCurricularV2Service")
public class SimuladorCurricularV2ServiceImpl implements SimuladorCurricularV2Service {
    private static final Logger LOGGER = Logger.getLogger(SimuladorCurricularV2ServiceImpl.class);

    @Autowired private AsistenteInscripcionService asistenteInscripcionService;
    @Autowired private ContextoEvaluacionCurricularV2Adapter adaptador;
    @Autowired private EvaluacionCurricularV2Service evaluador;

    @Override @Transactional(readOnly = true)
    public ResultadoSimulacionDTO simularSombra(Long idPersona, List<Long> idsSeleccionados) throws InscripcionException {
        LOGGER.info("[SIMULADOR_V2] Inicio de simulacion curricular");
        AsistenteInscripcionContextoDTO fuente = asistenteInscripcionService.obtenerContextoAsistido(idPersona);
        if (fuente == null) throw new InscripcionException("No existe contexto SIGIE para simular.");
        ContextoEvaluacionCurricularV2DTO contexto = adaptador.construirDesdeAsistente(fuente);
        List<Long> seleccionados = idsSeleccionados == null ? Collections.<Long>emptyList()
                : new ArrayList<Long>(new LinkedHashSet<Long>(idsSeleccionados));
        // La selección sólo viaja como candidata para la evaluación sombra.
        contexto.setIdsUnidadesPretendidas(seleccionados);
        LOGGER.info("[SIMULADOR_V2] Evaluando reglas curriculares");
        ResultadoEvaluacionCurricularV2DTO evaluacion = evaluador.evaluarSombra(contexto);
        if (evaluacion == null) {
            LOGGER.warn("[SIMULADOR_V2] No fue posible obtener el resultado de la evaluacion curricular");
        } else {
            LOGGER.info("[SIMULADOR_V2] Resultado de evaluacion curricular obtenido correctamente");
        }
        ResultadoSimulacionDTO resultado = new ResultadoSimulacionDTO();
        resultado.setUnidadesSeleccionadas(seleccionados); resultado.setEvaluacionCurricularV2(evaluacion);
        List<UnidadDecisionInscripcionDTO> seleccion = obtenerSeleccion(fuente, seleccionados);
        for (UnidadDecisionInscripcionDTO unidad : seleccion) {
            resultado.setTotalMaterias(Integer.valueOf(resultado.getTotalMaterias() == null ? 1 : resultado.getTotalMaterias().intValue() + 1));
            resultado.setTotalCreditos(Integer.valueOf((resultado.getTotalCreditos() == null ? 0 : resultado.getTotalCreditos().intValue()) + (unidad.getCreditos() == null ? 0 : unidad.getCreditos().intValue())));
            agregarRestricciones(evaluacion, unidad.getUdId(), resultado);
        }
        aplicarCargaV2(evaluacion, seleccionados.size(), resultado);
        aplicarValidacionesComplementarias(fuente, seleccion, seleccionados, resultado);
        resultado.setValida(Boolean.valueOf(resultado.getMotivos().isEmpty()));
        resultado.setMensajeGeneral(Boolean.TRUE.equals(resultado.getValida())
                ? "Escenario academico valido."
                : "Escenario restringido por reglas academicas y validaciones complementarias del simulador.");
        resultado.setImpactoSiguientePeriodo("Resultado académico hipotético; no modifica inscripción, oferta ni selección.");
        registrarResumenAplicacion(evaluacion, seleccionados.size(), resultado);
        LOGGER.info("[SIMULADOR_V2] Simulacion finalizada");
        return resultado;
    }

    private List<UnidadDecisionInscripcionDTO> obtenerSeleccion(AsistenteInscripcionContextoDTO fuente,
            List<Long> seleccionados) {
        List<UnidadDecisionInscripcionDTO> seleccion = new ArrayList<UnidadDecisionInscripcionDTO>();
        if (fuente == null || fuente.getUnidades() == null) return seleccion;
        for (UnidadDecisionInscripcionDTO unidad : fuente.getUnidades()) {
            if (unidad != null && unidad.getUdId() != null && seleccionados.contains(unidad.getUdId())) seleccion.add(unidad);
        }
        return seleccion;
    }

    private void aplicarCargaV2(ResultadoEvaluacionCurricularV2DTO evaluacion,
            int cargaSeleccionada, ResultadoSimulacionDTO resultado) {
        Integer minima = obtenerCargaMinimaV2(evaluacion);
        if (minima != null && cargaSeleccionada < minima.intValue()) {
            resultado.getMotivos().add(new MotivoDecisionDTO("RN_4_1", "ALTA",
                    "Carga mínima no cubierta", "La selección contiene " + cargaSeleccionada
                    + " UD y no alcanza la carga mínima " + minima + " determinada por RN-4.1.",
                    "Agrega UD habilitadas hasta cumplir la carga mínima aplicable.", Boolean.TRUE));
        }
        Integer maxima = obtenerCargaMaximaV2(evaluacion);
        if (maxima == null) {
            resultado.getMotivos().add(new MotivoDecisionDTO("RN_4_2", "ALTA",
                    "Carga máxima no determinada", "RN-4.2 requiere revisión para determinar la carga máxima aplicable.",
                    "Revisa la situación académica y la evidencia de RN-4.2.", Boolean.TRUE));
        } else if (cargaSeleccionada > maxima.intValue()) {
            resultado.getMotivos().add(new MotivoDecisionDTO("RN_4_2", "ALTA",
                    "Carga máxima excedida", "La selección contiene " + cargaSeleccionada
                    + " UD y excede la carga máxima " + maxima + " determinada por RN-4.2.",
                    "Reduce la selección conforme a RN-4.2.", Boolean.TRUE));
        }
    }

    private Integer obtenerCargaMinimaV2(ResultadoEvaluacionCurricularV2DTO evaluacion) {
        return evaluacion == null || evaluacion.getEvaluacionCargaAcademica() == null ? null
                : evaluacion.getEvaluacionCargaAcademica().getCargaMinimaNormativa();
    }

    /**
     * Conserva validaciones de composición de escenario que no forman parte del
     * contrato académico V2. Son complementarias y nunca sustituyen V2.
     */
    private void aplicarValidacionesComplementarias(AsistenteInscripcionContextoDTO fuente,
            List<UnidadDecisionInscripcionDTO> seleccion, List<Long> seleccionados,
            ResultadoSimulacionDTO resultado) {
        validarOptativasComplementarias(seleccion, resultado);
        advertirPrioritariasOmitidas(fuente, seleccionados, resultado);
    }

    private void validarOptativasComplementarias(List<UnidadDecisionInscripcionDTO> seleccion,
            ResultadoSimulacionDTO resultado) {
        Map<String, Integer> porBloque = new LinkedHashMap<String, Integer>();
        Map<String, Integer> porClave = new LinkedHashMap<String, Integer>();
        for (UnidadDecisionInscripcionDTO unidad : seleccion) {
            if (unidad == null || unidad.getTipoUd() == null
                    || !InscripcionUtils.esMateriaOptativa(unidad.getTipoUd())) continue;
            String bloque = String.valueOf(unidad.getSemestre()) + "-" + String.valueOf(unidad.getBloque());
            porBloque.put(bloque, Integer.valueOf((porBloque.containsKey(bloque) ? porBloque.get(bloque).intValue() : 0) + 1));
            if (unidad.getClave() != null) {
                String clave = unidad.getClave();
                porClave.put(clave, Integer.valueOf((porClave.containsKey(clave) ? porClave.get(clave).intValue() : 0) + 1));
            }
        }
        for (Map.Entry<String, Integer> entrada : porBloque.entrySet()) if (entrada.getValue().intValue() > 1) {
            resultado.getMotivos().add(new MotivoDecisionDTO("OPTATIVA_DUPLICADA_EN_BLOQUE", "ALTA",
                    "UD optativa duplicada en bloque", "Validación complementaria: sólo se permite una UD optativa por bloque/semestre; "
                    + entrada.getKey() + " contiene " + entrada.getValue() + ".",
                    "Deja una sola UD optativa en ese bloque.", Boolean.TRUE));
        }
        for (Map.Entry<String, Integer> entrada : porClave.entrySet()) if (entrada.getValue().intValue() > 1) {
            resultado.getMotivos().add(new MotivoDecisionDTO("OPTATIVA_MISMA_CLAVE", "ALTA",
                    "UD optativa repetida", "Validación complementaria: la UD optativa " + entrada.getKey()
                    + " fue seleccionada " + entrada.getValue() + " veces.",
                    "Sustituye las repeticiones por otra UD válida.", Boolean.TRUE));
        }
    }

    private void advertirPrioritariasOmitidas(AsistenteInscripcionContextoDTO fuente,
            List<Long> seleccionados, ResultadoSimulacionDTO resultado) {
        if (fuente == null || fuente.getUnidades() == null) return;
        for (UnidadDecisionInscripcionDTO unidad : fuente.getUnidades()) if (unidad != null
                && Boolean.TRUE.equals(unidad.getPrioritaria()) && unidad.getUdId() != null
                && !seleccionados.contains(unidad.getUdId())) resultado.getAdvertencias().add(
                "No seleccionaste una UD prioritaria ofertada; revisa la recomendación de regularización.");
    }

    private Integer obtenerCargaMaximaV2(ResultadoEvaluacionCurricularV2DTO evaluacion) {
        EvaluacionCargaMaximaCurricularV2DTO carga = evaluacion == null ? null : evaluacion.getEvaluacionCargaMaxima();
        return carga != null && carga.getCargaMaximaNormativa() != null
                ? carga.getCargaMaximaNormativa() : evaluacion == null ? null : evaluacion.getCargaMaximaFuturaSombra();
    }

    private void registrarResumenAplicacion(ResultadoEvaluacionCurricularV2DTO evaluacion,
            int cargaSeleccionada, ResultadoSimulacionDTO resultado) {
        String situacion = evaluacion == null ? "NO_DETERMINADA" : evaluacion.getSituacionAcademicaCalculada();
        if (situacion == null && evaluacion != null) situacion = evaluacion.getSituacionAcademica();
        LOGGER.info("[SIMULADOR_V2] Resultado aplicado al simulador - situacion=" + situacion
                + " - cargaMaxima=" + obtenerCargaMaximaV2(evaluacion) + " - cargaSeleccionada="
                + cargaSeleccionada + " - seleccionValida=" + resultado.getValida());
        if (!Boolean.TRUE.equals(resultado.getValida())) {
            Set<String> codigos = new LinkedHashSet<String>();
            for (MotivoDecisionDTO motivo : resultado.getMotivos()) if (motivo != null && motivo.getCodigo() != null) codigos.add(motivo.getCodigo());
            LOGGER.info("[SIMULADOR_V2] Seleccion restringida - motivos=" + codigos);
        }
    }

    private void agregarRestricciones(ResultadoEvaluacionCurricularV2DTO evaluacion, Long id, ResultadoSimulacionDTO resultado) {
        for (DecisionUnidadCurricularV2DTO decision : evaluacion.getDecisiones()) if (id.equals(decision.getIdPrograma()) && !Boolean.TRUE.equals(decision.getHabilitada())) {
            if (decision.getMotivos().isEmpty()) {
                resultado.getMotivos().add(new MotivoDecisionDTO("V2_RESTRICCION", "ALTA",
                        "UD restringida por reglas curriculares V2", String.valueOf(decision.getMotivos()),
                        "Revisa los motivos concurrentes del resultado V2.", Boolean.TRUE));
            } else for (Object motivo : decision.getMotivos()) {
                resultado.getMotivos().add(new MotivoDecisionDTO(String.valueOf(motivo), "ALTA",
                        "UD restringida por reglas curriculares V2", String.valueOf(decision.getMotivos()),
                        "Revisa los motivos concurrentes del resultado V2.", Boolean.TRUE));
            }
        }
    }
}
