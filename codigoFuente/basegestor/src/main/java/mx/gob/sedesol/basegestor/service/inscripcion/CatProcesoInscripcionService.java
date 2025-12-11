package mx.gob.sedesol.basegestor.service.inscripcion;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.CatProcesoInscripcionDTO;

public interface CatProcesoInscripcionService {
	List<CatProcesoInscripcionDTO> obtenerTiposProcesoInscripcion();
}
