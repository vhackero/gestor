package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasPasadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMaxMinDTO;

/**
 *  
 * @author ITTIVA
 * 
 */
public interface InscripcionService{
	
	InscripcionDTO consultaInformacionPersona(String id_persona);

	List<InscripcionMateriasDTO> consultarMaterias(String id_plan);

	InscripcionMaxMinDTO consultarMaxMin(String id_plan);

	List<InscripcionMateriasPasadasDTO> consultarMateriasCursadas(String id_plan);

	List<InscripcionMateriasInsDTO> consultarMateriasInscritas(String idpersona, String plan);



}
