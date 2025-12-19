package mx.gob.sedesol.basegestor.service.impl.inscripcion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.PlanProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ProgramaProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.model.repositories.inscripcion.IPlanProcesoInscripcionRepository;
import mx.gob.sedesol.basegestor.service.inscripcion.PlanProcesoInscripcionService;

@Service("planProcesoInscripcionService")
public class PlanProcesoInscripcionServiceImpl implements PlanProcesoInscripcionService {

	@Autowired
	private IPlanProcesoInscripcionRepository planProcesoInscripcionRepository;

	@Transactional(readOnly = true)
	@Override
	public List<PlanProcesoInscripcionDTO> obtenerPlanesPorIdProcesoInscripcion(Long idProcesoInscripcion) {
		return planProcesoInscripcionRepository.obtenerPlanesPorIdProcesoInscripcion(idProcesoInscripcion);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProgramaProcesoInscripcionDTO> obtenerProgramasPorPlanYProcesoInscripcion(Long idPlan,
			Long idProcesoInscripcion) {
		return planProcesoInscripcionRepository.obtenerProgramasPorPlanYProcesoInscripcion(idPlan,
				idProcesoInscripcion);
	}

}
