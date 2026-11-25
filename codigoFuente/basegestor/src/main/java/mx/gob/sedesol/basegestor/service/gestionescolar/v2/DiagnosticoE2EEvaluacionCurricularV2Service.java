package mx.gob.sedesol.basegestor.service.gestionescolar.v2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DiagnosticoE2EEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
/** Mecanismo interno, sólo DEV/QA y deshabilitado por defecto. */
public interface DiagnosticoE2EEvaluacionCurricularV2Service {
    DiagnosticoE2EEvaluacionCurricularV2DTO ejecutar(Long idPersona) throws InscripcionException;
}
