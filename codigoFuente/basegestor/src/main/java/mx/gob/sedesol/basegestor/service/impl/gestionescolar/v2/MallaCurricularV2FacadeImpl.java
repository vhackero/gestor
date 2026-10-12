package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.FichaIntegralCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.AsistenteCurricularV2Facade;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ContextoEvaluacionCurricularV2Adapter;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.EvaluacionCurricularV2Service;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.MallaCurricularV2Facade;

@Service("mallaCurricularV2Facade")
public class MallaCurricularV2FacadeImpl implements MallaCurricularV2Facade {

    @Autowired
    private AsistenteCurricularV2Facade asistenteFacade;

    @Autowired
    private ContextoEvaluacionCurricularV2Adapter adaptador;

    @Autowired
    private EvaluacionCurricularV2Service evaluador;

    @Override
    public FichaIntegralCasoDTO obtenerContextoMalla(ContextoAsistenteCurricularV2DTO contexto) throws InscripcionException {
        return asistenteFacade.obtenerFichaIntegral(contexto);
    }

    @Override
    public ResultadoEvaluacionCurricularV2DTO evaluarSombra(AsistenteInscripcionContextoDTO contexto) {
        ContextoEvaluacionCurricularV2DTO contextoV2 = adaptador.construirDesdeAsistente(contexto);
        return evaluador.evaluarSombra(contextoV2);
    }
}
