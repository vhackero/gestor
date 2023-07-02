package mx.gob.sedesol.basegestor.service.planesyprogramas;

import java.util.List;


import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface PlanService extends CommonService<PlanDTO, Integer> {

	public List<PlanDTO> buscaPlanesPorCriterios(PlanDTO filtro);
	
	public void eliminaHabilidadesPorIdPlan(Integer idPlan);
	
	public void eliminaConocimientosPorIdPlan(Integer idPlan);
	
	public void eliminAptitudesPorIdPlan(Integer idPlan);
	
	public Integer obtieneUltimoIdPlanes();
	
}
