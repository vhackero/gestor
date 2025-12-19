package mx.gob.sedesol.basegestor.model.repositories.inscripcion;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.InscripcionPreviaMateriasDTO;

public interface IInscripcionPreviaMateriasRepository {
	List<InscripcionPreviaMateriasDTO> obtenerInscripcionPrevia(Long idPersona);
}