package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;

public interface RoleService extends CommonService<RolDTO, Integer> {
	
	RolDTO buscarPorClave(String  claveRol);

	List<RolDTO> busquedaPorCriterios(RolDTO criterios);
}
