package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoPeriodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MatriculacionMasivaRegistroDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PeriodoInscripcionDTO;

public interface MatriculacionMasivaService {

	List<PeriodoInscripcionDTO> obtenerPeriodosInscripcion();

	List<EventoPeriodoDTO> obtenerEventosPorPeriodo(String nombrePeriodo);

	List<MatriculacionMasivaRegistroDTO> procesarMatriculacionMasiva(List<MatriculacionMasivaRegistroDTO> registros,
			Long idUsuario);
}
