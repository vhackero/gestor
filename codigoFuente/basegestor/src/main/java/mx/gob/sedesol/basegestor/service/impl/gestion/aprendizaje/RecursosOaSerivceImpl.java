package mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RecursosOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.RecursosOaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelUnidadOaAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblRecursosOa;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RecursosOaRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RecursosOaSerivce;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.ActividadesRecursosWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

@Service("recursosOaSerivce")
public class RecursosOaSerivceImpl extends ComunValidacionService<RecursosOaDTO> implements RecursosOaSerivce {

	private static final Logger logger = Logger.getLogger(RecursosOaSerivceImpl.class);

	@Autowired
	private RecursosOaRepo recursosOaRepo;

	@Autowired
	private AdministradorBitacora administradorBitacora;

	private ModelMapper recursosOaMapper = new ModelMapper();

	@Override
	public List<RecursosOaDTO> findAll() {
		List<TblRecursosOa> tblRecursosOas = recursosOaRepo.findAll();
		Type RecursosOaDTO = new TypeToken<List<RecursosOaDTO>>() {
		}.getType();

		for (TblRecursosOa tblRecursosOa : tblRecursosOas) {
			tblRecursosOa.setUnidadOaAva(null);
		}

		return recursosOaMapper.map(tblRecursosOas, RecursosOaDTO);
	}

	@Override
	public RecursosOaDTO buscarPorId(Integer id) {
		TblRecursosOa entidad = recursosOaRepo.findOne(id);
		RecursosOaDTO dto = new RecursosOaDTO();

		if (ObjectUtils.isNotNull(entidad)) {
			recursosOaMapper.map(entidad, dto);
		}
		return dto;
	}

	@Override
	public List<RecursosOaDTO> buscarRecursosOasPorUnidadOaAva(UnidadOaAvaDTO unidadOaAvaDTO) {

		RelUnidadOaAva entidad = recursosOaMapper.map(unidadOaAvaDTO, RelUnidadOaAva.class);

		List<TblRecursosOa> tblRecursosOas = recursosOaRepo.findTblRecursosOasByUnidadOaAva(entidad);

		for (TblRecursosOa tblRecursosOa : tblRecursosOas) {
			tblRecursosOa.setUnidadOaAva(null);
		}

		Type RecursosOaDTO = new TypeToken<List<RecursosOaDTO>>() {
		}.getType();

		return recursosOaMapper.map(tblRecursosOas, RecursosOaDTO);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResultadoDTO<RecursosOaDTO> guardar(RecursosOaDTO dto) {

		ResultadoDTO<RecursosOaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				resultado = new ResultadoDTO<RecursosOaDTO>();

				TblRecursosOa entidad = recursosOaMapper.map(dto, TblRecursosOa.class);
				recursosOaRepo.save(entidad);
				resultado.setDto(recursosOaMapper.map(entidad, RecursosOaDTO.class));

				// GUSTAVO --guardarBitacora(dto.getBitacoraDTO(),
				// String.valueOf(entidad.getId()));

				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
				logger.error(e.getMessage(), e);

			}
		}

		return resultado;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResultadoDTO<RecursosOaDTO> actualizar(RecursosOaDTO dto) {
		ResultadoDTO<RecursosOaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				resultado = new ResultadoDTO<RecursosOaDTO>();

				TblRecursosOa entidad = recursosOaMapper.map(dto, TblRecursosOa.class);
				recursosOaRepo.saveAndFlush(entidad);
				resultado.setDto(recursosOaMapper.map(entidad, RecursosOaDTO.class));

				// GUSTAVO --guardarBitacora(dto.getBitacoraDTO(),
				// String.valueOf(entidad.getId()));

				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
				logger.error(e.getMessage(), e);
				throw e;
			}
		}

		return resultado;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResultadoDTO<RecursosOaDTO> eliminar(RecursosOaDTO dto) {
		ResultadoDTO<RecursosOaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				resultado = new ResultadoDTO<RecursosOaDTO>();

				TblRecursosOa entidad = recursosOaMapper.map(dto, TblRecursosOa.class);
				recursosOaRepo.delete(entidad);
				resultado.setDto(recursosOaMapper.map(entidad, RecursosOaDTO.class));
				resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);

				// GUSTAVO --guardarBitacora(dto.getBitacoraDTO(),
				// String.valueOf(entidad.getId()));

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

	@Transactional
	@Override
	public ResultadoDTO<RecursosOaDTO> eliminarRecurso(RecursosOaDTO recursosOaDTO, AmbienteVirtualAprendizajeDTO ava) {

		TblRecursosOa entidad;
		ResultadoDTO<RecursosOaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, recursosOaDTO);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				resultado = new ResultadoDTO<RecursosOaDTO>();

				Boolean resultadoBorrarRecursoLms = this.borrarRecursoLms(ava, recursosOaDTO);

				if (!resultadoBorrarRecursoLms) {
					throw new NullPointerException("La respuesta al borrar el recurso del Ws es falsel");
				}

				entidad = recursosOaMapper.map(recursosOaDTO, TblRecursosOa.class);
				recursosOaRepo.delete(entidad);

				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());

				resultado.setDto(recursosOaMapper.map(entidad, RecursosOaDTO.class));
				resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);

				// GUSTAVO --guardarBitacora(recursosOaDTO.getBitacoraDTO(),
				// String.valueOf(entidad.getId()));

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
				logger.error(e.getMessage(), e);
				throw e;
			}

		}

		return resultado;
	}

	@Transactional
	@Override
	public ResultadoDTO<RecursosOaDTO> guardarRecurso(RecursosOaDTO recursosOaDTO, AmbienteVirtualAprendizajeDTO ava) {

		TblRecursosOa entidad;
		Integer idRecursoLms = null;
		ResultadoDTO<RecursosOaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, recursosOaDTO);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				resultado = new ResultadoDTO<RecursosOaDTO>();
				idRecursoLms = anadeRecursoLms(
						RecursosOaEnum.getRecursosOaEnum(recursosOaDTO.getCatTemaRecurso().getNombre()), ava,
						recursosOaDTO);
				recursosOaDTO.setIdRecursoLms(idRecursoLms);

				if (ObjectUtils.isNull(idRecursoLms) || idRecursoLms == 0) {
					throw new NullPointerException("La respuesta al crear el recurso del Ws es false");
				}

				entidad = recursosOaMapper.map(recursosOaDTO, TblRecursosOa.class);
				TblRecursosOa entidadRespuesta = recursosOaRepo.save(entidad);

				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());

				resultado.setDto(recursosOaMapper.map(entidadRespuesta, RecursosOaDTO.class));
				resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
				logger.error(e.getMessage(), e);
			}

		}

		return resultado;
	}

	private Integer anadeRecursoLms(RecursosOaEnum recursosOaEnum, AmbienteVirtualAprendizajeDTO ava,
			RecursosOaDTO recursosOaDTO) throws ErrorWS {

		ActividadesRecursosWS actividadesRecursosWs = new ActividadesRecursosWS(ava.getPlataformaMoodle());

		Integer idCurso = ava.getIdCursoLms();
		Integer seccion = 0;
		String nombre = recursosOaDTO.getNombre();
		String descripcionHTML = recursosOaDTO.getInstruccionesContenido();
		Integer idRecursoLms = 0;

		logger.info(recursosOaEnum.getValor());

		switch (recursosOaEnum) {
		case SCORM:
			idRecursoLms = actividadesRecursosWs.crearSCORM(idCurso, seccion, nombre, descripcionHTML);
			break;

		case TEMA_DISCUCION:

			idRecursoLms = actividadesRecursosWs.crearDiscucionForoNovedades(idCurso, nombre, descripcionHTML);
			break;

		case FORO:
			idRecursoLms = actividadesRecursosWs.crearForo(idCurso, seccion, nombre, descripcionHTML);
			break;

		default:
			logger.info("Recurso no encontrado");
			break;
		}

		return idRecursoLms;
	}

	private Boolean borrarRecursoLms(AmbienteVirtualAprendizajeDTO ava, RecursosOaDTO recursosOaDTO) {

		Boolean resultado = Boolean.FALSE;
		ActividadesRecursosWS actividadesRecursosWs = new ActividadesRecursosWS(ava.getPlataformaMoodle());

		try {
			if (RecursosOaEnum.TEMA_DISCUCION.getValor().equals(recursosOaDTO.getCatTemaRecurso().getNombre())) {
				resultado = actividadesRecursosWs.eliminarDiscucionForo(recursosOaDTO.getIdRecursoLms());
			} else {
				resultado = actividadesRecursosWs.eliminarRecurso(recursosOaDTO.getIdRecursoLms());

			}
		} catch (ErrorWS e) {
			e.printStackTrace();
			logger.error("Fallo al invocacion del WS borra recuso");
		}

		return resultado;
	}

	public ResultadoDTO<RecursosOaDTO> sonDatosRequeridosValidos(TipoAccion accion, RecursosOaDTO dto) {
		ResultadoDTO<RecursosOaDTO> res = null;

		if (ObjectUtils.isNotNull(dto)) {
			res = new ResultadoDTO<RecursosOaDTO>();

			switch (accion) {
			case PERSISTENCIA:

				if (ObjectUtils.isNull(dto.getIdUObjetoAprendizaje())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
				}
				if (ObjectUtils.isNull(dto.getIdCatTemaR())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
				}
				if (ObjectUtils.isNull(dto.getNombre())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_VALOR_REQ);
				}
				if (ObjectUtils.isNull(dto.getFechaRegistro())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
				}
				if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_PERSONA_REQ);
				}

				break;

			case ACTUALIZACION:

				if (ObjectUtils.isNullOrEmpty(dto.getIdCatTemaR())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
				}
				if (ObjectUtils.isNullOrEmpty(dto.getIdUObjetoAprendizaje())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
				}
				if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_VALOR_REQ);
				}
				if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_PERSONA_REQ);
				}
				if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
				}

				break;

			case ELIMINACION:

				if (ObjectUtils.isNullOrEmpty(dto.getIdCatTemaR())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
				}
				if (ObjectUtils.isNullOrEmpty(dto.getIdUObjetoAprendizaje())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
				}

				break;

			}
		}
		return res;
	}

	@Override
	public void validarPersistencia(RecursosOaDTO dto, ResultadoDTO<RecursosOaDTO> resultado) {
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
	public void validarActualizacion(RecursosOaDTO dto, ResultadoDTO<RecursosOaDTO> resultado) {
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
	public void validarEliminacion(RecursosOaDTO dto, ResultadoDTO<RecursosOaDTO> resultado) {
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
