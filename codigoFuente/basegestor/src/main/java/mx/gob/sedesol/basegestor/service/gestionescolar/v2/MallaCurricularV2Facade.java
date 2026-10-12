package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.FichaIntegralCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface MallaCurricularV2Facade {

    FichaIntegralCasoDTO obtenerContextoMalla(ContextoAsistenteCurricularV2DTO contexto) throws InscripcionException;

    ResultadoEvaluacionCurricularV2DTO evaluarSombra(AsistenteInscripcionContextoDTO contexto);
}
