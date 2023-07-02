package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;

public interface FuncionalidadService extends CommonService<FuncionalidadDTO, Long> {
	
	List<FuncionalidadDTO> buscarPorCriterios(FuncionalidadDTO dto);
	
	List<FuncionalidadDTO> obtenerModulos();
	
	List<FuncionalidadDTO> obtenerComponentes();
	
	List<FuncionalidadDTO> obtenerComponentes(Long idFuncionalidad);
	
	List<FuncionalidadDTO> obtenerOperaciones(Long idFuncionalidad);
}
