package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;

public interface PersonaSigeService extends CommonService<PersonaSigeDTO, Long>{
	
	List<PersonaSigeDTO> buscarNoRegistrados();
}
