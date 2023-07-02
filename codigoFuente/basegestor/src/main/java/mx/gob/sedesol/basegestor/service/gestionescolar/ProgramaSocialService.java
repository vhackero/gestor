package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProgramaSocialDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface ProgramaSocialService extends CommonService<ProgramaSocialDTO, Integer> {
	
	List<ProgramaSocialDTO> obtenerProgramasPorUnidad(Integer idUnidadResponsable);;

}
