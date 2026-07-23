package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DiagnosticoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface DiagnosticoAcademicoOperativoService {

    DiagnosticoAcademicoOperativoDTO construirDiagnostico(ContextoAsistenteCurricularV2DTO contexto) throws InscripcionException;
}
