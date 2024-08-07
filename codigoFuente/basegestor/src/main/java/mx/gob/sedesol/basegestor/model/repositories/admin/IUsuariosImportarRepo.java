package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.SelectImportarDTO;

public interface IUsuariosImportarRepo { 

	List<SelectImportarDTO> consultaConvocatorias();

	List<SelectImportarDTO> consultaFuenteExterna();

	List<PersonaSigeDTO> consultaPersonasImportar(String fuenteExterna, String convocatoria);

	void insertAspirante(String idPersonaRegistrada, String programaEducativo, String idConvocatoria);

	
}
