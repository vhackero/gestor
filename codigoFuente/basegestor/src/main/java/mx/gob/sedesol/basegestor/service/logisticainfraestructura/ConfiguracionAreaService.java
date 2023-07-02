package mx.gob.sedesol.basegestor.service.logisticainfraestructura;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ConfiguracionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.CritBusquedaAreasConfigDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface ConfiguracionAreaService extends CommonService<ConfiguracionAreaDTO, Integer> {
	
	public List<ConfiguracionAreaDTO> busquedaAreasConfiguradasCriterios(CritBusquedaAreasConfigDTO criterios);
	
	public AreaSedeDTO consultaAreaSedePorIdSede(Integer idSede);

}
	