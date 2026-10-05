package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;

/** Adaptador de sólo lectura entre los contextos vigentes de inscripción/asistente y V2. */
public interface ContextoEvaluacionCurricularV2Adapter {

    ContextoEvaluacionCurricularV2DTO construirDesdeInscripcion(InscripcionContextoDTO contextoInscripcion,
            List<UnidadDecisionInscripcionDTO> unidades, String periodoActivo, Long idProcesoInscripcion);

    ContextoEvaluacionCurricularV2DTO construirDesdeAsistente(AsistenteInscripcionContextoDTO contextoAsistente);
}
