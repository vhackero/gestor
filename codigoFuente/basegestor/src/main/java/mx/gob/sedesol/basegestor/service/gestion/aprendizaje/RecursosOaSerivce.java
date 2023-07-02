/**
 * 
 */
package mx.gob.sedesol.basegestor.service.gestion.aprendizaje;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RecursosOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;


public interface RecursosOaSerivce extends CommonService<RecursosOaDTO, Integer>{

	
	public List<RecursosOaDTO> findAll();
	
	public RecursosOaDTO buscarPorId(Integer id) ;
	
	public ResultadoDTO<RecursosOaDTO> guardar(RecursosOaDTO dto);
	
	public ResultadoDTO<RecursosOaDTO> actualizar(RecursosOaDTO dto);
	
	public ResultadoDTO<RecursosOaDTO> eliminar(RecursosOaDTO dto);
	
	public List<RecursosOaDTO> buscarRecursosOasPorUnidadOaAva(UnidadOaAvaDTO unidadOaAvaDTO);
	
	public ResultadoDTO<RecursosOaDTO> eliminarRecurso (RecursosOaDTO recursosOaDTO,AmbienteVirtualAprendizajeDTO ava);
	
	public ResultadoDTO<RecursosOaDTO> guardarRecurso(RecursosOaDTO recursosOaDTO, AmbienteVirtualAprendizajeDTO ava);
}
