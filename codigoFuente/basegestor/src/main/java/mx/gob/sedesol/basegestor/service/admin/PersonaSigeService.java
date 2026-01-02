package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersonaSige;

public interface PersonaSigeService extends CommonService<PersonaSigeDTO, Long>{
	
	List<PersonaSigeDTO> buscarNoRegistrados();

	TblPersonaSige importarDesdeFuenteExterna(String idFuenteExterna, String matricula) throws Exception;
}
