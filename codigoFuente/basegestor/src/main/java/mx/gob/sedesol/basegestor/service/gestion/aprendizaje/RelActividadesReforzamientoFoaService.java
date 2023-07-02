package mx.gob.sedesol.basegestor.service.gestion.aprendizaje;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelActividadesReforzamientoFoaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface RelActividadesReforzamientoFoaService extends CommonService<RelActividadesReforzamientoFoaDTO, Integer>
{
	List<RelActividadesReforzamientoFoaDTO> buscarActividadReforzamientoFoa(Integer clFoa);
}
