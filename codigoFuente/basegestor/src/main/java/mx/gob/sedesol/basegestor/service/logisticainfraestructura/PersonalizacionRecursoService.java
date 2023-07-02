package mx.gob.sedesol.basegestor.service.logisticainfraestructura;

import java.io.Serializable;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelPersonalizacionRecursoDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface PersonalizacionRecursoService extends CommonService<RelPersonalizacionRecursoDTO, Integer>, Serializable {

	public List<RelPersonalizacionRecursoDTO> consultaRelPersonalizacionArea(Integer idPersonalizacion);
	
}
