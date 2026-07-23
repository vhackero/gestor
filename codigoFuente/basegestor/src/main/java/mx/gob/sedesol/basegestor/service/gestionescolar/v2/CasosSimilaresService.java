package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoSimilarDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface CasosSimilaresService {

    List<CasoSimilarDTO> buscarCasosSimilares(ContextoAsistenteCurricularV2DTO contexto) throws InscripcionException;
}
