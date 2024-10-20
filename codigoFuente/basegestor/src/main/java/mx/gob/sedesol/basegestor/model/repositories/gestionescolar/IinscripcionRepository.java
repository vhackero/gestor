package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasPasadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMaxMinDTO;


public interface IinscripcionRepository {
	
	List<InscripcionDTO> consultarTipoProceso(String idPersona);

	List<InscripcionMateriasDTO> consultarMaterias(String id_plan);

	InscripcionMaxMinDTO consultarMaxMin(String id_plan);

	List<InscripcionMateriasPasadasDTO> consultarMateriasCursadas(String id_persona);

	List<InscripcionMateriasInsDTO> consultarMateriasInscritas(String idpersona, String plan);
 
}
