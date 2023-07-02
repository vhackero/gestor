package mx.gob.sedesol.basegestor.service.badges;

import mx.gob.sedesol.basegestor.commons.dto.badges.BadgeDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

/**
 * Interface para las distintas funciones para BadgeService
 * @author nnm_eolf
 *
 */
public interface BadgeService extends CommonService<BadgeDTO, Integer>{
	
	BadgeDTO obtenerBadgePorRangoPuntos(Integer puntos);

}
