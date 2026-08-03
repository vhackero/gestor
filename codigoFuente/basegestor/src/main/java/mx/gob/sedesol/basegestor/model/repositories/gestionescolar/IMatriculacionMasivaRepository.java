package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoPeriodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PeriodoInscripcionDTO;

public interface IMatriculacionMasivaRepository {

	List<PeriodoInscripcionDTO> obtenerPeriodosInscripcion();

	List<EventoPeriodoDTO> obtenerEventosPorPeriodo(String nombrePeriodo, String claveEvento);
	
	boolean existeRol(Integer idRol);
}
