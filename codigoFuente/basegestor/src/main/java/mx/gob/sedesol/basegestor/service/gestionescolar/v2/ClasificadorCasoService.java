package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ClasificacionCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface ClasificadorCasoService {

    ClasificacionCasoDTO clasificarCaso(ContextoAsistenteCurricularV2DTO contexto) throws InscripcionException;
}
