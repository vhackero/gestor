package mx.gob.sedesol.basegestor.service.gestion.aprendizaje;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelRecursoPredominanteFoaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface RelRecursoPredominanteFoaService extends CommonService<RelRecursoPredominanteFoaDTO, Integer>
{
	List<RelRecursoPredominanteFoaDTO> buscarRecursoPredominanteFoa(Integer clFoa);
}
