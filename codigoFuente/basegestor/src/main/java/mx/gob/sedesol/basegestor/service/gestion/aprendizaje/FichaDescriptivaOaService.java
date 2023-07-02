package mx.gob.sedesol.basegestor.service.gestion.aprendizaje;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.FichaDescriptivaOaDTO;


public interface FichaDescriptivaOaService
{
	public List<FichaDescriptivaOaDTO> findAll();
	
	public FichaDescriptivaOaDTO buscarPorId(Integer id);
	
	public ResultadoDTO<FichaDescriptivaOaDTO> guardar(FichaDescriptivaOaDTO dto) ;
	
	public ResultadoDTO<FichaDescriptivaOaDTO> actualizar(FichaDescriptivaOaDTO dto) ;
	
	public ResultadoDTO<FichaDescriptivaOaDTO> eliminar(FichaDescriptivaOaDTO dto) ;
}