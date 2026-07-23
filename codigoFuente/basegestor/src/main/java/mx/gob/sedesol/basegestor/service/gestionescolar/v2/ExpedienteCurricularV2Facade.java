package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.FichaIntegralCasoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface ExpedienteCurricularV2Facade {

    FichaIntegralCasoDTO obtenerContextoExpediente(ContextoAsistenteCurricularV2DTO contexto) throws InscripcionException;
}
