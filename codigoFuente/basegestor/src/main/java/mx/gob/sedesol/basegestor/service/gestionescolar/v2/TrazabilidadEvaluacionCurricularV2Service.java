package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;

/** Persistencia de auditoría V2 aislada del flujo productivo de reinscripción. */
public interface TrazabilidadEvaluacionCurricularV2Service {
    Long registrarSombra(ContextoEvaluacionCurricularV2DTO contexto, ResultadoEvaluacionCurricularV2DTO resultado);
}
