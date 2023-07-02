package mx.gob.sedesol.basegestor.service.gestion.aprendizaje;

import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.ReponsableProduccionOaDTO;

public interface ReponsableProduccionOaService {
	public List<ReponsableProduccionOaDTO> findAll();
	public List<ReponsableProduccionOaDTO> findByTipoResponsabilidades (List<Integer> idTipoResposabilidades);
	public ResultadoDTO<ReponsableProduccionOaDTO> guardar (ReponsableProduccionOaDTO reponsableProduccionOaDTO) ;	
	public ResultadoDTO<ReponsableProduccionOaDTO> actualizar(ReponsableProduccionOaDTO dto);
}
