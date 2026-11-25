package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.PeriodoTrayectoriaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.SemestreReferenciaV2DTO;

public interface SemestreReferenciaV2Service {
    SemestreReferenciaV2DTO calcular(String periodoEvaluado, Integer ultimoSemestreMapa,
            List<PeriodoTrayectoriaV2DTO> periodos, String corte);
}
