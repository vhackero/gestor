package mx.gob.sedesol.basegestor.model.repositories.inscripcion;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.CatProcesoInscripcionDTO;

public interface ICatProcesoInscripcionRepository {
	List<CatProcesoInscripcionDTO> obtenerTiposProcesoInscripcion();
}
