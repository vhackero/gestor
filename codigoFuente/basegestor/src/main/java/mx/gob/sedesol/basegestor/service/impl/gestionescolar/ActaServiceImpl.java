package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Acta;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IActaRepository;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.ActaService;

/**
 * ACTAS
 * 
 * @author ITTIVA
 * 
 */
@Service("actaService")
public class ActaServiceImpl extends ComunValidacionService<ActaDTO> implements ActaService {

	private static final Logger logger = Logger.getLogger(ActaServiceImpl.class);

	@Autowired
	private IActaRepository iCargaActaRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public ResultadoDTO<ActaDTO> guardar(ActaDTO dto) {
		ResultadoDTO<ActaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				logger.info("EJECUTANDO QUERY");
				Acta entidad = new Acta();
				modelMapper.map(dto, entidad);
				iCargaActaRepository.save(entidad);

				logger.info("SAVE CORRECTO");
				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
		return resultado;
	}

	@Transactional
	@Override
	public ResultadoDTO<ActaDTO> eliminar(ActaDTO dto) {

		ResultadoDTO<ActaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				logger.info("EJECUTANDO QUERY");
				Acta entidad = new Acta();
				modelMapper.map(dto, entidad);
				iCargaActaRepository.borraPorIdActa(entidad.getIdActa());

				logger.info(">>> ADMIN_MSG_ELIMINACION_EXITOSA");
				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
		return resultado;
	}

	@Override
	public ActaDTO getActaByGrupoUser(int idGrupo, long idUser) {
		logger.info("EJECUTANDO QUERY descargaActa");
		List<Acta> optional = iCargaActaRepository.getActaByIdGrupoUser(idGrupo, idUser);

		if (optional.size() > 0) {
			return modelMapper.map(optional.get(0), ActaDTO.class);
		} else {
			return null;
		}

	}

	@Override
	public ActaDTO getActaByIdGrupo(int idGrupo) {
		List<Acta> optional = iCargaActaRepository.getActaByIdGrupo(idGrupo);

		if (optional.size() > 0) {
			return modelMapper.map(optional.get(0), ActaDTO.class);
		} else {
			return null;
		}

	}

	@Override
	public List<ActaDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActaDTO buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<ActaDTO> actualizar(ActaDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarPersistencia(ActaDTO dto, ResultadoDTO<ActaDTO> resultado) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validarActualizacion(ActaDTO dto, ResultadoDTO<ActaDTO> resultado) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validarEliminacion(ActaDTO dto, ResultadoDTO<ActaDTO> resultado) {
		// TODO Auto-generated method stub

	}

}
