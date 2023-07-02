package mx.gob.sedesol.basegestor.service.gestion.aprendizaje;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.ElementosMultimediaFoaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface RelElementosMultimediaFoaService extends CommonService<ElementosMultimediaFoaDTO, Integer>
{
	List<ElementosMultimediaFoaDTO> buscarElementoMultimediaFoa(Integer clFoa);
}
