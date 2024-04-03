package mx.gob.sedesol.basegestor.service.admin;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelPersonaPlataformaMoodleDTO;

public interface RelPersonaPlataformaMoodleService {

	Integer obtenerIdMoodle(PersonaDTO persona, Integer idPlataformaMoodle, Long usuarioModifico);

	Integer obtenerIdMoodle(PersonaDTO persona, ParametroWSMoodleDTO parametroWSMoodleDTO, Long usuarioModifico);
	
	public RelPersonaPlataformaMoodleDTO obtenerPersonaPlataformaMoodle(Integer idPersonaMoodle, Integer idPlataformaMoodle);

}
