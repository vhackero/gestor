package mx.gob.sedesol.basegestor.service.logisticainfraestructura;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface AreaSedeService extends CommonService<AreaSedeDTO, Integer> {

	public List<AreaSedeDTO> consultaAreasSedePorIdSede(Integer idSede);
	
	public List<AreaSedeDTO> consultaAreasSedePorIdArea(Integer idArea);
	
	public void eliminaAreasSedePorIdSede(Integer idSede);
	
	public AreaSedeDTO consultaAreaSedePorIdSedeyIdArea(Integer idSede, Integer idArea);
}
