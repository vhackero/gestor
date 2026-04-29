package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReenvioCorreoMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ReenvioCorreoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface InscripcionFacade {
	InscripcionContextoDTO obtenerContextoInscripcion(Long idPersona) throws InscripcionException;
	InscripcionContextoDTO obtenerContextoInscripcionConsulta(Long idPersona) throws InscripcionException;
	void validarSeleccionMateria(InscripcionMateriasDTO materiaSeleccionada, InscripcionContextoDTO contexto) throws InscripcionException;
	void finalizarInscripcion(Boolean aceptaTerminos, InscripcionContextoDTO contexto) throws InscripcionException;
	boolean intentarReenviarCorreoInscripcion(ReenvioCorreoInscripcionDTO inscripcion);
	boolean intentarReenviarCorreoInscripcion(ReenvioCorreoInscripcionDTO inscripcion, List<ReenvioCorreoMateriasDTO> materias);
}
