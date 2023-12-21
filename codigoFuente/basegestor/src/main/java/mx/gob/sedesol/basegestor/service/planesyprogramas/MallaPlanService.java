package mx.gob.sedesol.basegestor.service.planesyprogramas;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelMallaPlanDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface MallaPlanService extends CommonService<RelMallaPlanDTO, Integer> {
	public RelMallaPlanDTO findByIdPlan(Integer idEje);
}
