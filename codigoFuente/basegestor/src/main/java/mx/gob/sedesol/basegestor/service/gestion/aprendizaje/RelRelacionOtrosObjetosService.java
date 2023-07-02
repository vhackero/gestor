package mx.gob.sedesol.basegestor.service.gestion.aprendizaje;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelRelacionOtrosObjetosFoaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface RelRelacionOtrosObjetosService extends CommonService<RelRelacionOtrosObjetosFoaDTO, Integer>
{
	List<RelRelacionOtrosObjetosFoaDTO> buscarRelacionOtrosObjetosFoa(Integer clFoa);
}
