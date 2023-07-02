package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;
import java.util.Map;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolFuncionalidadDTO;

public interface RolFuncionalidadService extends CommonService<RolFuncionalidadDTO, Long> {
	
	List<RolFuncionalidadDTO> obtenerArbolFuncionalidadesRol(int idRol);
	
	ResultadoDTO<RolFuncionalidadDTO> guardarArbolFuncionalidadesRol(List<RolFuncionalidadDTO> arbol, int idRol, Long idUsuario);
	
	void procesarListaFuncionalidades(List<RolFuncionalidadDTO> arbol);
	
	/**
	 * obtiene la funcionalidades que tiene asignadas un rol
	 * las carga en un mapa en base a su clave
	 * @param idRol
	 * @return
	 */
	Map<String, String> obtenerMapaFuncionalidadesPorRol(int idRol);

}
