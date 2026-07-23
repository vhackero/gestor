package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.FichaIntegralCasoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.AsistenteCurricularV2Facade;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ExpedienteCurricularV2Facade;

@Service("expedienteCurricularV2Facade")
public class ExpedienteCurricularV2FacadeImpl implements ExpedienteCurricularV2Facade {

    @Autowired
    private AsistenteCurricularV2Facade asistenteFacade;

    @Override
    public FichaIntegralCasoDTO obtenerContextoExpediente(ContextoAsistenteCurricularV2DTO contexto)
            throws InscripcionException {
        return asistenteFacade.obtenerFichaIntegral(contexto);
    }
}
