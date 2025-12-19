package mx.gob.sedesol.basegestor.service.inscripcion;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReenvioCorreoMateriasDTO;

public interface EnvioCorreoService {
	
	boolean existeRegistroResultadoEnvioCorreoInscripcion(Long idPersona, Long idProcesoInscripcion);
	
	Long registrarResultadoEnvioCorreoInscripcion(Long idPersona, Long idProcesoInscripcion, Integer envioCorreo);

	void actualizarResultadoEnvioCorreoInscripcion(Long idPersona, Long idProcesoInscripcion, Integer envioCorreo);

	List<ReenvioCorreoMateriasDTO> obtenerMateriasParaReenvioCorreo(Long idPersona, Long idProcesoInscripcion);
}
