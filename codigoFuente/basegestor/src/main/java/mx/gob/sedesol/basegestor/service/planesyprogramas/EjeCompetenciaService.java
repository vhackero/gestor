package mx.gob.sedesol.basegestor.service.planesyprogramas;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEjeCompetenciaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface EjeCompetenciaService extends CommonService<RelEjeCompetenciaDTO,Integer> {
	
	public List<RelEjeCompetenciaDTO> obtenerCompetenciasEspecificasPorEje(Integer idEje);
	
	public List<RelEjeCompetenciaDTO> obtieneCompetenciasEspecificasNoAsignadas(Integer idEje);
	
}
