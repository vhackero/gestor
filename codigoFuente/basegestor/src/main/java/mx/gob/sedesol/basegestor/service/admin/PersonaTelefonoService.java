package mx.gob.sedesol.basegestor.service.admin;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaTelefonoDTO;

public interface PersonaTelefonoService extends CommonService<PersonaTelefonoDTO, Integer> {
	
	PersonaTelefonoDTO obtenerTelefonosPersonaPorTipo(Long idPersona, Integer idTipoTelefono);
	
}
