package mx.gob.sedesol.basegestor.service.planesyprogramas;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.EstPersonalExternoDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface EstPersonalExternoService extends CommonService<EstPersonalExternoDTO, Integer> {

	public EstPersonalExternoDTO obtenerEstPersonalExternoPorId(Integer id);
	public List<EstPersonalExternoDTO> obtenerEstPersonalExtPadres();
}
