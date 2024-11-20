package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasPasadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMaxMinDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.IntentosAsignaturasDTO;


public interface IinscripcionRepository {
	
	List<InscripcionDTO> consultarTipoProceso(String idPersona);

	List<InscripcionMateriasDTO> consultarMaterias(String id_plan);

	InscripcionMaxMinDTO consultarMaxMin(String id_plan);

	List<InscripcionMateriasPasadasDTO> consultarMateriasCursadas(String id_persona);

	List<InscripcionMateriasInsDTO> consultarMateriasInscritas(String idpersona, String plan);

	Long consultarNumeroEstructura(String id_plan_infopersona);

	List<IntentosAsignaturasDTO> consultarIntentosAsignaturas(String id_persona);

	Boolean consultarNuevoIngreso(String id_persona);

	List<InscripcionBajasDTO> consultarBajas(String id_persona);

	List<InscripcionMateriasDTO> consultarMateriasPorConvocatoria(String id_plan, String id_convocatoria,
			String id_convocatoria2);

	void insertarRegistro(InscripcionInsertDTO dto);
 
}
