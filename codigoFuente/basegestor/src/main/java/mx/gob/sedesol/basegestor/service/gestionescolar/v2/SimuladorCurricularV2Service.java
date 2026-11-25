package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

/** Consumidor de presentación: no ejecuta inscripción ni reglas académicas propias. */
public interface SimuladorCurricularV2Service {
    ResultadoSimulacionDTO simularSombra(Long idPersona, List<Long> idsSeleccionados) throws InscripcionException;
}
