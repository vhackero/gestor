package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelEvaluacionCalificacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoEvaluacionDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface RelGpoEvaluacionService extends CommonService<RelGrupoEvaluacionDTO, Integer> {

	public List<RelGrupoEvaluacionDTO> obtieneEvaluacionesPorIdGrupo(Integer idGpo);
	
	public List<RelEvaluacionCalificacionDTO> obtieneEvaluacionesByIdGpoEval(Integer idGpoEval);
	
	public void eliminaEvaluacionesByIdGpoEvaluacion(Integer idGpoEval) throws Exception;
	
	void eliminaGrupoEvaluacionByIdGrupo(Integer idGrupo) throws Exception;
	
	void eliminaEvaluacionesByIdGrupo(Integer idGrupo) throws Exception;
}
