package mx.gob.sedesol.basegestor.service.logisticainfraestructura;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SedeDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface SedeService extends CommonService<SedeDTO, Integer> {

	public List<SedeDTO> consultaSedesPorUbicacionOrgGub(Integer idUbicacion, Integer idOrgGub);
	
	public List<SedeDTO> consultaSedesPorOrgGubDepedencia(Integer idOrgGub);
}
