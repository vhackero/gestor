package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
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
        LOGGER.info("[SIMULADOR_V2] Inicio simulacion utilizando Motor Curricular V2");
        AsistenteInscripcionContextoDTO fuente = asistenteInscripcionService.obtenerContextoAsistido(idPersona);
        if (fuente == null) throw new InscripcionException("No existe contexto SIGIE para simular.");
        ContextoEvaluacionCurricularV2DTO contexto = adaptador.construirDesdeAsistente(fuente);
        LOGGER.info("[SIMULADOR_V2] Invocando Motor Curricular V2 - version=PE2027-V1 - modo=SOMBRA");
        ResultadoEvaluacionCurricularV2DTO evaluacion = evaluador.evaluarSombra(contexto);
        if (evaluacion == null) {
            LOGGER.warn("[SIMULADOR_V2] No fue posible obtener ResultadoEvaluacionCurricularV2DTO");
        } else {
            LOGGER.info("[SIMULADOR_V2] ResultadoEvaluacionCurricularV2DTO obtenido correctamente");
        }
        ResultadoSimulacionDTO resultado = new ResultadoSimulacionDTO();
        List<Long> seleccionados = idsSeleccionados == null ? Collections.<Long>emptyList() : new ArrayList<Long>(idsSeleccionados);
        resultado.setUnidadesSeleccionadas(seleccionados); resultado.setEvaluacionCurricularV2(evaluacion);
        for (UnidadDecisionInscripcionDTO unidad : fuente.getUnidades()) if (unidad != null && unidad.getUdId() != null && seleccionados.contains(unidad.getUdId())) {
            resultado.setTotalMaterias(Integer.valueOf(resultado.getTotalMaterias() == null ? 1 : resultado.getTotalMaterias().intValue() + 1));
            resultado.setTotalCreditos(Integer.valueOf((resultado.getTotalCreditos() == null ? 0 : resultado.getTotalCreditos().intValue()) + (unidad.getCreditos() == null ? 0 : unidad.getCreditos().intValue())));
            agregarRestricciones(evaluacion, unidad.getUdId(), resultado);
        }
        resultado.setValida(Boolean.valueOf(resultado.getMotivos().isEmpty()));
        resultado.setMensajeGeneral(Boolean.TRUE.equals(resultado.getValida()) ? "Escenario evaluado por Motor Curricular V2 en modo sombra." : "Escenario restringido por Motor Curricular V2 en modo sombra.");
        resultado.setImpactoSiguientePeriodo("Resultado académico hipotético; no modifica inscripción, oferta ni selección.");
        LOGGER.info("[SIMULADOR_V2] Simulacion finalizada utilizando resultado del Motor Curricular V2");
        return resultado;
    }
    private void agregarRestricciones(ResultadoEvaluacionCurricularV2DTO evaluacion, Long id, ResultadoSimulacionDTO resultado) {
        for (DecisionUnidadCurricularV2DTO decision : evaluacion.getDecisiones()) if (id.equals(decision.getIdPrograma()) && !Boolean.TRUE.equals(decision.getHabilitada())) {
            resultado.getMotivos().add(new MotivoDecisionDTO("V2_RESTRICCION", "ALTA", "UD restringida por reglas curriculares V2", String.valueOf(decision.getMotivos()), "Revisa los motivos concurrentes del resultado V2.", Boolean.TRUE));
        }
    }
}
