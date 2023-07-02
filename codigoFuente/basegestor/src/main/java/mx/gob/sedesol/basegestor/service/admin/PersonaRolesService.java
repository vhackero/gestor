package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;
import java.util.Map;

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaRolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;

public interface PersonaRolesService extends CommonService<PersonaRolDTO, Long> {
	
	List<PersonaDTO> obtenerPersonasPorRol(Integer idRol);
	
	List<PersonaRolDTO> obtieneRelPersonaRolesPorUsuario(String usuario);
	
	List<FuncionalidadDTO> obtenerFuncionalidadesUsuario(String usuario);
	
	Map<String, String> obtenerFuncionalidadesRol(Integer idRol);
	
	public ResultadoDTO<PersonaRolDTO> almacenarRolesUsuario(PersonaDTO persona, List<RolDTO> roles,
			long usuarioModifico);
	
	public void reemplazarRoles(List<RolDTO> rolesDTO, TblPersona persona);

}
