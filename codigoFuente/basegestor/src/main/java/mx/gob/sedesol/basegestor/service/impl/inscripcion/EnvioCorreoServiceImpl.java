package mx.gob.sedesol.basegestor.service.impl.inscripcion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReenvioCorreoMateriasDTO;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IEnvioCorreoRepository;
import mx.gob.sedesol.basegestor.service.inscripcion.EnvioCorreoService;

@Service("envioCorreoService")
public class EnvioCorreoServiceImpl implements EnvioCorreoService {

	@Autowired
	private IEnvioCorreoRepository envioCorreoRepository;

	@Transactional
	@Override
	public Long registrarResultadoEnvioCorreoInscripcion(Long idPersona, Long idProcesoInscripcion,
			Integer envioCorreo) {
		return envioCorreoRepository.registrarResultadoEnvioCorreoInscripcion(idPersona, idProcesoInscripcion,
				envioCorreo);
	}

	@Transactional
	@Override
	public void actualizarResultadoEnvioCorreoInscripcion(Long idPersona, Long idProcesoInscripcion,
			Integer envioCorreo) {
		envioCorreoRepository.actualizarResultadoEnvioCorreoInscripcion(idPersona, idProcesoInscripcion, envioCorreo);
	}

	@Override
	public List<ReenvioCorreoMateriasDTO> obtenerMateriasParaReenvioCorreo(Long idPersona, Long idProcesoInscripcion) {
		return envioCorreoRepository.obtenerMateriasParaReenvioCorreo(idPersona, idProcesoInscripcion);
	}

}
