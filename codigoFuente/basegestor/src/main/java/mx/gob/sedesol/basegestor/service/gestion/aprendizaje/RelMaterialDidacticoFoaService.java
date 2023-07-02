package mx.gob.sedesol.basegestor.service.gestion.aprendizaje;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelMaterialDidacticoFoaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface RelMaterialDidacticoFoaService extends CommonService<RelMaterialDidacticoFoaDTO, Integer>
{
	List<RelMaterialDidacticoFoaDTO> buscarMaterialDidacticoFoa(Integer clFoa);
}
