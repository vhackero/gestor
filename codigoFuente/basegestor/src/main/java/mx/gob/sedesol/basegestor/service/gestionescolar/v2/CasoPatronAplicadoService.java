package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.PatronConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface CasoPatronAplicadoService {

    void registrarPatronesAplicados(CasoAcademicoOperativoDTO caso, ContextoAsistenteCurricularV2DTO contexto,
            List<PatronConocimientoDTO> patrones) throws InscripcionException;
}
