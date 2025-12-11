package mx.gob.sedesol.basegestor.service.inscripcion;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.InscripcionPreviaMateriasDTO;

public interface InscripcionPreviaMateriasService {
	List<InscripcionPreviaMateriasDTO> obtenerInscripcionPrevia(Long idPersona);
}