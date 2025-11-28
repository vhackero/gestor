package mx.gob.sedesol.basegestor.service.gestionescolar;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;

public interface InscripcionFacade {
	InscripcionContextoDTO obtenerContextoInscripcion(Long idPersona);
	void validarSeleccionMateria(InscripcionMateriasDTO materiaSeleccionada, InscripcionContextoDTO contexto);
	void finalizarInscripcion(Boolean aceptaTerminos, InscripcionContextoDTO contexto);
}
