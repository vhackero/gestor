package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;

public interface RelReponsableProduccionEcService {

	public List<ReponsableProduccionEcDTO> getResponsableDelEvento(int idEvento, int idTipoResponsabilidad);

	public List<ReponsableProduccionEcDTO> getResponsableDelEvento(int idEvento);
	
	public ResultadoDTO<ReponsableProduccionEcDTO> actualizar(ReponsableProduccionEcDTO dto);
		
	public ResultadoDTO<ReponsableProduccionEcDTO> guardar(ReponsableProduccionEcDTO dto);
		
	public ResultadoDTO<ReponsableProduccionEcDTO> eliminar(ReponsableProduccionEcDTO dto);
}
