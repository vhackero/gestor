package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReenvioCorreoMateriasDTO;

public interface IEnvioCorreoRepository {
	Long registrarResultadoEnvioCorreoInscripcion(Long idPersona, Long idProcesoInscripcion, Integer envioCorreo);
	void actualizarResultadoEnvioCorreoInscripcion(Long idPersona, Long idProcesoInscripcion, Integer envioCorreo);
	List<ReenvioCorreoMateriasDTO> obtenerMateriasParaReenvioCorreo(Long idPersona, Long idProcesoInscripcion);
}
