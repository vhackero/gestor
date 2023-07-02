package mx.gob.sedesol.basegestor.service.gestion.aprendizaje;

import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;

public interface RelUnidadOaAvaService {
	public ResultadoDTO<UnidadOaAvaDTO> guardar(UnidadOaAvaDTO dto) ;
	public List<UnidadOaAvaDTO> findAll();
	public List<UnidadOaAvaDTO> findByIdAva(Integer idAva);
	public ResultadoDTO<UnidadOaAvaDTO> actualizar(UnidadOaAvaDTO dto);
	public ResultadoDTO<UnidadOaAvaDTO> actualizarUnidadYAmbientevirtualAprendizaje(UnidadOaAvaDTO unidadOaAvaDTO,
			AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO);
}
