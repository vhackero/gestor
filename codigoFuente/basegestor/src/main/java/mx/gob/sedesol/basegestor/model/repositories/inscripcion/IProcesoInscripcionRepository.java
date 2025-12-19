package mx.gob.sedesol.basegestor.model.repositories.inscripcion;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ProcesoInscripcionDTO;

public interface IProcesoInscripcionRepository {
	List<ProcesoInscripcionDTO> obtenerPorConvocatoriaYTipoProceso(Long idConvocatoria, Long idTipoProceso);
}
