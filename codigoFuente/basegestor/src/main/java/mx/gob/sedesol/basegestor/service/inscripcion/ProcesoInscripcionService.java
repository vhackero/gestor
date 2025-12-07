package mx.gob.sedesol.basegestor.service.inscripcion;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ProcesoInscripcionDTO;

public interface ProcesoInscripcionService {
	List<ProcesoInscripcionDTO> obtenerPorConvocatoriaYTipoProceso(Long idConvocatoria, Long idTipoProceso);
}
