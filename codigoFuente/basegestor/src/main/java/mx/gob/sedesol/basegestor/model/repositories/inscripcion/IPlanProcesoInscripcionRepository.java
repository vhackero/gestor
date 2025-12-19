package mx.gob.sedesol.basegestor.model.repositories.inscripcion;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.PlanProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ProgramaProcesoInscripcionDTO;

public interface IPlanProcesoInscripcionRepository {
	List<PlanProcesoInscripcionDTO> obtenerPlanesPorIdProcesoInscripcion(Long idProcesoInscripcion);

	List<ProgramaProcesoInscripcionDTO> obtenerProgramasPorPlanYProcesoInscripcion(Long idPlan,
			Long idProcesoInscripcion);
}
