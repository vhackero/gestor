package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasPasadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMaxMinDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.IntentosAsignaturasDTO;

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

	Long consultarNumeroEstructura(String id_plan_infopersona);

	List<IntentosAsignaturasDTO> consultarIntentosAsignaturas(String idpersona);

	Boolean consultarNuevoIngreso(String id_persona);

	List<InscripcionBajasDTO> consultarBajas(String idpersona);

	List<InscripcionMateriasDTO> consultarMateriasPorConvocatoria(String id_plan, String id_convocatoria,
			String id_estructura);

	void insertarInscripciones(List<InscripcionInsertDTO> inscripciones);




}
