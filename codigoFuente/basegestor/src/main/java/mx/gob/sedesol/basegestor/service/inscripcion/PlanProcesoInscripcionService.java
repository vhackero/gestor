package mx.gob.sedesol.basegestor.service.inscripcion;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.PlanProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ProgramaProcesoInscripcionDTO;

public interface PlanProcesoInscripcionService {
	List<PlanProcesoInscripcionDTO> obtenerPlanesPorIdProcesoInscripcion(Long idProcesoInscripcion);

	List<ProgramaProcesoInscripcionDTO> obtenerProgramasPorPlanYProcesoInscripcion(Long idPlan,
			Long idProcesoInscripcion);
}
