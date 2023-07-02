package mx.gob.sedesol.basegestor.service.admin;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;

public interface PersonaCorreoService extends CommonService<PersonaCorreoDTO, Integer> {

	PersonaCorreoDTO buscaPersonaCorreoElectronico(String correo);

	PersonaCorreoDTO obtenerCorreoInstitucional(Long idPersona);
}
