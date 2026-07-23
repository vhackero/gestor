package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DictamenCasoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface RecomendadorResolucionOperativaService {

    DictamenCasoDTO generarDictamen(CasoAcademicoOperativoDTO casoAcademico) throws InscripcionException;
}
