package mx.gob.sedesol.basegestor.service.logisticainfraestructura;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaRecursoDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface AreaRecursoService extends CommonService<RelAreaRecursoDTO, Integer> {
	public List<RelAreaRecursoDTO> obtieneRecursosPorIdArea(Integer idArea);
}
