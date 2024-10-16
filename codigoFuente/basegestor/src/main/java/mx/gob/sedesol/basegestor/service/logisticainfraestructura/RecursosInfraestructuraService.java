package mx.gob.sedesol.basegestor.service.logisticainfraestructura;

import java.io.Serializable;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RecursosInfraestructuraDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface RecursosInfraestructuraService extends CommonService<RecursosInfraestructuraDTO, Integer>, Serializable {
	public List<RecursosInfraestructuraDTO> obtieneRecursosPorTipoDeRecurso(Integer idTipoRecurso);
}
