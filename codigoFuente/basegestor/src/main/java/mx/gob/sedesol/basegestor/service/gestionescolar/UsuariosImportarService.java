package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.SelectImportarDTO;



public interface UsuariosImportarService {
	

	List<SelectImportarDTO> consultaConvocatorias();

	List<SelectImportarDTO> consultaFuenteExterna();
	
	List<PersonaSigeDTO> consultaPersonasImportar(String fuenteExterna, String convocatoria);


}



