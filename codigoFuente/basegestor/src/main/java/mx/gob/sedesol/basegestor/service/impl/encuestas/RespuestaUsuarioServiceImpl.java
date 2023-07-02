package mx.gob.sedesol.basegestor.service.impl.encuestas;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.encuestas.RelRespuestaUsuario;
import mx.gob.sedesol.basegestor.model.entities.encuestas.TblRespuesta;
import mx.gob.sedesol.basegestor.model.repositories.encuestas.RespuestasUsuarioRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.encuestas.RespuestasUsuarioService;

/**
 * Created by jhcortes on 15/12/16. Clase que contiene la implementacion de los
 * servicios que corresponden al registro de respuetas de encuesta por usuario.
 */
@Service("respuestaEncuestaService")
public class RespuestaUsuarioServiceImpl extends ComunValidacionService<RespuestaUsuarioDTO>
		implements RespuestasUsuarioService {

	private static final Logger logger = Logger.getLogger(RespuestaUsuarioServiceImpl.class);

	@Autowired
	private RespuestasUsuarioRepo respuestaUsuarioRepo;

	private ModelMapper mapper = new ModelMapper();
	
	Type RespuestaUsuarioDTO = new TypeToken<List<RespuestaUsuarioDTO>>() {
	}.getType();

	@Override
	public List<RespuestaUsuarioDTO> findAll() {
		List<RelRespuestaUsuario> respuestasUsuario = respuestaUsuarioRepo.findAll();
		Type respuestasUsuarioDTO = new TypeToken<List<RespuestaUsuarioDTO>>() {
		}.getType();
		return mapper.map(respuestasUsuario, respuestasUsuarioDTO);
	}

	@Override
	public RespuestaUsuarioDTO buscarPorId(Integer id) {
		RelRespuestaUsuario respuestaUsuario = respuestaUsuarioRepo.findOne(id);
		RespuestaUsuarioDTO dto = new RespuestaUsuarioDTO();
		if (ObjectUtils.isNotNull(respuestaUsuario)) {
			mapper.map(respuestaUsuario, dto);
		}
		return dto;
	}

	@Override
	public ResultadoDTO<RespuestaUsuarioDTO> guardar(RespuestaUsuarioDTO dto) {
		ResultadoDTO<RespuestaUsuarioDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (ObjectUtils.isNotNull(resultado)) {
			try {
				resultado = new ResultadoDTO<>();
				RelRespuestaUsuario entidad = mapper.map(dto, RelRespuestaUsuario.class);
				respuestaUsuarioRepo.save(entidad);
				resultado.setDto(mapper.map(entidad, RespuestaUsuarioDTO.class));
			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS);
				logger.error(e.getMessage(), e);
				throw e;
			}
		}

		return resultado;
	}

	@Override
	public ResultadoDTO<RespuestaUsuarioDTO> actualizar(RespuestaUsuarioDTO dto) {
		ResultadoDTO<RespuestaUsuarioDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (ObjectUtils.isNotNull(resultado)) {
			try {

				resultado = new ResultadoDTO<>();

				List<TblRespuesta> respuestas = new ArrayList<>();
				if (ObjectUtils.isNotNull(dto.getTblRespuestas())) {
					List<RespuestaDTO> lstAux = dto.getTblRespuestas();
					for (RespuestaDTO respuestaDTO : lstAux) {
						TblRespuesta respuesta = new TblRespuesta();
						mapper.map(respuestaDTO, respuesta);
						respuestas.add(respuesta);
					}
				}

				RelRespuestaUsuario entidad = mapper.map(dto, RelRespuestaUsuario.class);

				if (ObjectUtils.isNotNull(respuestas)) {
					entidad.setTblRespuestas(respuestas);
				}

				respuestaUsuarioRepo.saveAndFlush(entidad);
				resultado.setDto(mapper.map(entidad, RespuestaUsuarioDTO.class));

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesErrorEnum.ERROR_ACTUALIZACION_DATOS);
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<RespuestaUsuarioDTO> eliminar(RespuestaUsuarioDTO dto) {
		logger.info("No implementado...");
		return null;
	}

	/**
	 * No se requiere implementacion de eliminacion de respuestas de encuesta
	 * por que se requiere que se guarde el historico.
	 *
	 * @param accion
	 * @param dto
	 * @return
	 */
	@Override
	public ResultadoDTO<RespuestaUsuarioDTO> sonDatosRequeridosValidos(TipoAccion accion, RespuestaUsuarioDTO dto) {
		ResultadoDTO<RespuestaUsuarioDTO> res = new ResultadoDTO<>();

		if (ObjectUtils.isNotNull(dto)) {

			switch (accion) {
			case PERSISTENCIA:
				res = validaPersistencia(dto);
				break;

			case ACTUALIZACION:
				res = validaActualizacion(dto);
				break;

			case ELIMINACION:

				res = validaEliminacion(dto);
				break;
			}
		}
		return res;
	}

	private ResultadoDTO<RespuestaUsuarioDTO> validaEliminacion(RespuestaUsuarioDTO dto) {
		ResultadoDTO<RespuestaUsuarioDTO> res = new ResultadoDTO<>();
		if (ObjectUtils.isNullOrEmpty(dto.getIdRespuestaUsuario())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
		}
		return res;
	}

	/**
	 *
	 * @param dto
	 * @return
	 */
	private ResultadoDTO<RespuestaUsuarioDTO> validaPersistencia(RespuestaUsuarioDTO dto) {
		ResultadoDTO<RespuestaUsuarioDTO> res = new ResultadoDTO<>();
		if (ObjectUtils.isNull(dto.getFechaRegistro())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
		}
		return res;
	}

	/**
	 *
	 * @param dto
	 * @return
	 */
	private ResultadoDTO<RespuestaUsuarioDTO> validaActualizacion(RespuestaUsuarioDTO dto) {
		ResultadoDTO<RespuestaUsuarioDTO> res = new ResultadoDTO<>();
		if (ObjectUtils.isNullOrEmpty(dto.getIdRespuestaUsuario())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
		}
		return res;
	}

	@Override
	public List<RespuestaUsuarioDTO> buscarRespuestasEncuestaUsuario(Integer idUsuario, Integer idEncuesta) {
		List<RespuestaUsuarioDTO> respuestaUsuarioDTO = new ArrayList<>();
		List<RelRespuestaUsuario> respuesta = respuestaUsuarioRepo.buscarRespuestasEncuestaUsuario(idUsuario,
				idEncuesta);
		if (!respuesta.isEmpty()) {
			respuestaUsuarioDTO = mapper.map(respuesta, RespuestaUsuarioDTO);
		}
		return respuestaUsuarioDTO;
	}

	@Override
	public void validarPersistencia(RespuestaUsuarioDTO dto, ResultadoDTO<RespuestaUsuarioDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void validarActualizacion(RespuestaUsuarioDTO dto, ResultadoDTO<RespuestaUsuarioDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void validarEliminacion(RespuestaUsuarioDTO dto, ResultadoDTO<RespuestaUsuarioDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}
}
