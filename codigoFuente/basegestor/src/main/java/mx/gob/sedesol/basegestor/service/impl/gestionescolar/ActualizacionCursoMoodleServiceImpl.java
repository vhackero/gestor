package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodleEventoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodlePlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodleProgramaDTO;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IActualizacionCursoMoodleRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.ActualizacionCursoMoodleService;

@Service("actualizacionCursoMoodleService")
public class ActualizacionCursoMoodleServiceImpl implements ActualizacionCursoMoodleService {

	@Autowired
	private IActualizacionCursoMoodleRepository actualizacionCursoMoodleRepository;

	@Override
	public List<ActualizacionCursoMoodlePlanDTO> obtenerPlanes() {
		return actualizacionCursoMoodleRepository.obtenerPlanes();
	}

	@Override
	public List<ActualizacionCursoMoodleProgramaDTO> obtenerProgramasPorPlan(Long idPlanSeleccionado) {
		if (idPlanSeleccionado == null) {
			return Collections.emptyList();
		}
		return actualizacionCursoMoodleRepository.obtenerProgramasPorPlan(idPlanSeleccionado);
	}

	@Override
	public List<ActualizacionCursoMoodleEventoDTO> buscarEventos(Long idPlanSeleccionado, Long idProgramaSeleccionado,
			String periodo) {
		if (idPlanSeleccionado == null || idProgramaSeleccionado == null || periodo == null) {
			return Collections.emptyList();
		}
		return actualizacionCursoMoodleRepository.buscarEventos(idPlanSeleccionado, idProgramaSeleccionado, periodo);
	}
}
