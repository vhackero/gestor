package mx.gob.sedesol.basegestor.service.planesyprogramas;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.ModuloMoodleDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface ModuloMoodleService extends CommonService<ModuloMoodleDTO, Integer>{

	/**
	 * Obtiene una lista de los modulos por tipoModulo
	 * @param tipoModulo: Recurso o Actividad
	 * @return
	 */
	public List<ModuloMoodleDTO> buscarPorTipoDeModulo(Integer tipoModulo);
}
