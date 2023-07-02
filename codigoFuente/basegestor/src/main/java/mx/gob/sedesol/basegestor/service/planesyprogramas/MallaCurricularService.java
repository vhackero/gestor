package mx.gob.sedesol.basegestor.service.planesyprogramas;


import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface MallaCurricularService extends CommonService<MallaCurricularDTO,Integer> {

	public MallaCurricularDTO obtenerMallaCurricularPorId(Integer id);
	
	public MallaCurricularDTO obtenerMallaCurricularPorIdPlan(Integer idPlan);
	
	public List<MallaCurricularDTO> obtieneMallasCurricularesDisponibles();
	
	//public Integer getNuevoIdMallaCurricular();
}
