package mx.gob.sedesol.basegestor.service;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

/**
 * Interface para las distintas funciones para ParametroWSMoodle
 * @author eolf
 *
 */
public interface ParametroWSMoodleService extends CommonService<ParametroWSMoodleDTO, Integer> {

	/**
	 * Metodo para obtener el parametro que se encuentra activo
	 * @author eolf
	 * @return
	 */
	ParametroWSMoodleDTO obtenerMoodleActivo();
	
}
