package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodleEventoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodlePlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodleProgramaDTO;

public interface IActualizacionCursoMoodleRepository {

	List<ActualizacionCursoMoodlePlanDTO> obtenerPlanes();

	List<ActualizacionCursoMoodleProgramaDTO> obtenerProgramasPorPlan(Long idPlanSeleccionado);

	List<ActualizacionCursoMoodleEventoDTO> buscarEventos(Long idPlanSeleccionado, Long idProgramaSeleccionado,
			String periodo);
}
