/**
 *
 */
package mx.gob.sedesol.basegestor.service.impl.encuestas;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.PreguntaEncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaContexto;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo;
import mx.gob.sedesol.basegestor.model.entities.encuestas.TblEncuesta;
import mx.gob.sedesol.basegestor.model.entities.encuestas.TblPreguntasEncuesta;
import mx.gob.sedesol.basegestor.model.especificaciones.EncuestaEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.encuestas.CatalogoEncuestaRepo;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionSistemaService;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaUsuarioService;
import mx.gob.sedesol.basegestor.service.encuestas.RespuestasUsuarioService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;

/**
 * @author jhcortes
 *
 */
@Service("encuestaService")
public class EncuestaServiceImpl extends ComunValidacionService<EncuestaDTO> implements EncuestaService {

	private static final Logger logger = Logger.getLogger(EncuestaServiceImpl.class);

	@Autowired
	private CatalogoEncuestaRepo catalogoEncuestaRepo;

	@Autowired
	private NotificacionSistemaService notificacionSistemaService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	@Autowired
	private RelEncuestaUsuarioService relEncuestaUsuarioService;

	@Autowired
	private RespuestasUsuarioService respuestasUsuarioService;

	@Autowired
	private RelEncuestaEventoCapacitacionService relEncuestaEventoCapacitacionService;

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	private ModelMapper catComunMapper = new ModelMapper();

	@Override
	public List<EncuestaDTO> findAll() {
		List<TblEncuesta> encuestas = catalogoEncuestaRepo.findAll();
		Type encuestasDto = new TypeToken<List<EncuestaDTO>>() {
		}.getType();
		return catComunMapper.map(encuestas, encuestasDto);
	}

	/**
	 *
	 */
	@Override
	public EncuestaDTO buscarPorId(Integer id) {
		TblEncuesta entidad = catalogoEncuestaRepo.findOne(id);
		EncuestaDTO enc = new EncuestaDTO();

		if (ObjectUtils.isNotNull(entidad)) {
			catComunMapper.map(entidad, enc);
		}

		return enc;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<EncuestaDTO> guardar(EncuestaDTO catDto) {
		ResultadoDTO<EncuestaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, catDto);

		if (ObjectUtils.isNotNull(resultado)) {
			try {
				resultado = new ResultadoDTO<>();

				CatEncuestaTipo tipoEncuesta = new CatEncuestaTipo();

				if (!ObjectUtils.isNullOrEmpty(catDto.getEncuestaTipo())) {
					catComunMapper.map(catDto.getEncuestaTipo(), tipoEncuesta);
					CatEncuestaContexto contextoEncuesta = new CatEncuestaContexto();
					catComunMapper.map(catDto.getEncuestaTipo().getContexto(), contextoEncuesta);
					tipoEncuesta.setContexto(contextoEncuesta);
				}

				TblEncuesta entidad = catComunMapper.map(catDto, TblEncuesta.class);

				if (!ObjectUtils.isNullOrEmpty(catDto.getEncuestaTipo())) {
					entidad.setEncuestaTipo(tipoEncuesta);
				}

				catalogoEncuestaRepo.save(entidad);
				resultado.setDto(catComunMapper.map(entidad, EncuestaDTO.class));

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesErrorEnum.ERROR_PERSISTENCIA_DATOS);
				logger.error(e.getMessage(), e);
				throw e;
			}
		}

		return resultado;
	}

	/**
	 * Enviar mensaje de notificacion al usuario ultimo que modifico la encuesta
	 *
	 * @param persona
	 */
	private void generarMensajes(Long idPersona, int accion) {
		if (idPersona != null) {
			List<VariableMensajeOperacionDTO> variables = new ArrayList<>();

			PersonaDTO persona = personaService.buscarPorId(idPersona);
			variables.add(new VariableMensajeOperacionDTO("${nombre_usuario}", persona.getNombreCompleto()));

			String strAccion = "";

			switch (accion) {

			case ConstantesGestor.ESTADO_ENCUESTA_COMENTARIOS:
				strAccion = ConstantesBitacora.ENCUESTA_CON_COMENTARIO;
				break;

			case ConstantesGestor.ESTADO_ENCUESTA_REVISION:
				strAccion = ConstantesBitacora.ENCUESTA_EN_REVISION;
				break;

			case ConstantesGestor.ESTADO_ENCUESTA_APROBADA:
				strAccion = ConstantesBitacora.ENCUESTA_APROBADA;
				break;

			default:
				logger.info("se genero una encuesta en estado borrador");
				break;
			}

			notificacionSistemaService.enviarNotificacion(strAccion, persona.getIdPersona(), variables);

		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<EncuestaDTO> actualizar(EncuestaDTO catDto) {

		ResultadoDTO<EncuestaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, catDto);
		int accion = catDto.getEncuestaEstatus().getId();

		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {

			try {

				resultado = new ResultadoDTO<>();

				TblEncuesta entidad = catComunMapper.map(catDto, TblEncuesta.class);

				if (ObjectUtils.isNotNull(catDto.getPreguntasEncuesta())) {
					List<TblPreguntasEncuesta> preguntas = mapearPreguntas(catDto.getPreguntasEncuesta());
					entidad.setPreguntasEncuestas(preguntas);
				}

				if (isAprobada(accion) && ObjectUtils.isNullOrEmpty(entidad.getClave())) {

					ActualizarEncuestaSync sync = new ActualizarEncuestaSync(entidad);
					sync.start();

					try {

						synchronized (entidad) {

							Integer secuencia = catalogoEncuestaRepo.countCombinacion(entidad.getEncuestaTipo().getId(),
									entidad.getEncuestaObjetivo().getId(),
									entidad.getEncuestaTipo().getContexto().getId(),
									entidad.getEncuestaEstatus().getId());

							secuencia++;

							entidad.setClave(generaClave(entidad.getEncuestaTipo().getId(),
									entidad.getEncuestaObjetivo().getId(),
									entidad.getEncuestaTipo().getContexto().getId(), secuencia));

							catalogoEncuestaRepo.saveAndFlush(entidad);

							Thread.sleep(1000);
							if (!ObjectUtils.isNullOrEmpty(entidad.getClave())) {
								entidad.notify();
							}
						}

					} catch (InterruptedException e) {
						logger.error(e.getMessage(), e);
					}

				} else {

					catalogoEncuestaRepo.saveAndFlush(entidad);
				}

				generarMensajes(catDto.getIdUsrMensajeDestino(), accion);

				resultado.setDto(catComunMapper.map(entidad, EncuestaDTO.class));

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesErrorEnum.ERROR_ACTUALIZACION_DATOS);
				logger.error(e.getMessage(), e);
			}
		}
		return resultado;
	}

	/**
	 * Mapea las preguntas de la encuesta.
	 *
	 * @param preguntasEncuesta
	 * @return
	 */
	private List<TblPreguntasEncuesta> mapearPreguntas(List<PreguntaEncuestaDTO> preguntasEncuesta) {
		List<TblPreguntasEncuesta> preguntas = null;
		if (ObjectUtils.isNotNull(preguntasEncuesta)) {
			Type lstAux = new TypeToken<List<TblPreguntasEncuesta>>() {
			}.getType();
			preguntas = catComunMapper.map(preguntasEncuesta, lstAux);
		}
		return preguntas;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<EncuestaDTO> eliminar(EncuestaDTO catDto) {

		ResultadoDTO<EncuestaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, catDto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				catDto.setActivo(ConstantesGestor.INACTIVO);
				TblEncuesta entidad = catComunMapper.map(catDto, TblEncuesta.class);
				catalogoEncuestaRepo.delete(entidad);
				resultado.setDto(catComunMapper.map(entidad, EncuestaDTO.class));
				resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesErrorEnum.ERROR_ELIMINAR_DATOS);
				throw e;
			}
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<EncuestaDTO> sonDatosRequeridosValidos(TipoAccion accion, EncuestaDTO catDto) {
		ResultadoDTO<EncuestaDTO> res = new ResultadoDTO<>();

		if (ObjectUtils.isNotNull(catDto)) {
			switch (accion) {
			case PERSISTENCIA:
				res = validaPersistencia(catDto);
				break;

			case ACTUALIZACION:
				res = validaActualizacion(catDto);
				break;

			case ELIMINACION:

				res = validaEliminacion(catDto);
				break;
			}
		}
		return res;
	}

	/**
	 *
	 * @param catDto
	 * @return
	 */
	private ResultadoDTO<EncuestaDTO> validaEliminacion(EncuestaDTO catDto) {
		ResultadoDTO<EncuestaDTO> res = new ResultadoDTO<>();

		if (ObjectUtils.isNullOrEmpty(catDto.getId())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
		}
		if (ObjectUtils.isNull(catDto.getFechaActualizacion())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
		}

		return res;
	}

	/**
	 *
	 * @param catDto
	 * @return
	 */
	private ResultadoDTO<EncuestaDTO> validaActualizacion(EncuestaDTO catDto) {
		ResultadoDTO<EncuestaDTO> res = new ResultadoDTO<>();

		if (ObjectUtils.isNullOrEmpty(catDto.getId())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(catDto.getUsuarioModifico())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_USUARIO_REQ);
		}
		if (ObjectUtils.isNull(catDto.getFechaActualizacion())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
		}

		return res;
	}

	/**
	 *
	 * @param catDto
	 * @return
	 */
	private ResultadoDTO<EncuestaDTO> validaPersistencia(EncuestaDTO catDto) {
		ResultadoDTO<EncuestaDTO> res = new ResultadoDTO<>();

		if (ObjectUtils.isNullOrEmpty(catDto.getNombre())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_NOMBRE_REQ);
		}
		if (ObjectUtils.isNull(catDto.getFechaCreacion())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(catDto.getUsuarioModifico())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_USUARIO_REQ);
		}

		return res;
	}

	@Override
	public EncuestaDTO buscarEncuestaNomClv(String nombre, String clave) {
		EncuestaDTO dto = new EncuestaDTO();
		TblEncuesta entidad = catalogoEncuestaRepo.buscarPorParametros(nombre, clave);
		if (ObjectUtils.isNotNull(entidad)) {
			dto = catComunMapper.map(entidad, EncuestaDTO.class);
		}
		return dto;
	}

	@Override
	public List<EncuestaDTO> buscarPorCriterios(EncuestaDTO dto) {
		List<EncuestaDTO> listas = new ArrayList<>();
		List<TblEncuesta> encuestas = null;

		try {
			Specification<TblEncuesta> especificacion = new EncuestaEspecificacion(
					catComunMapper.map(dto, TblEncuesta.class));
			encuestas = catalogoEncuestaRepo.findAll(especificacion, sortByEstatusFechaActualizacionDesc());

			if (!ObjectUtils.isNullOrEmpty(encuestas)) {
				for (TblEncuesta encuesta : encuestas) {
					EncuestaDTO enc = new EncuestaDTO();
					catComunMapper.map(encuesta, enc);
					listas.add(enc);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return listas;
	}

	private Sort sortByEstatusFechaActualizacionDesc() {
		return new Sort(Sort.Direction.ASC, "encuestaEstatus").and(new Sort(Sort.Direction.DESC, "fechaActualizacion"));
	}

	/**
	 *
	 * @param contextoID
	 * @param nivelID
	 * @param dirigidoAID
	 * @param secuencia
	 * @return
	 */
	public String generaClave(Integer nivelID, Integer dirigidoAID, Integer contextoID, Integer secuencia) {

		String contextAux = "";
		if (contextoID.toString().length() < 2) {
			contextAux = "0" + contextoID;
		} else {
			contextAux = "" + contextoID;
		}

		String nivelAux = "";
		if (nivelID.toString().length() < 2) {
			nivelAux = "0" + nivelID;
		} else {
			nivelAux = "" + nivelID;
		}

		String dirigidoAux = "";
		if (dirigidoAID.toString().length() < 2) {
			dirigidoAux = "0" + dirigidoAID;
		} else {
			dirigidoAux = "" + dirigidoAID;
		}

		String secuenciaAux = "";
		if (secuencia.toString().length() < 2) {
			secuenciaAux = "0" + secuencia;
		} else {
			secuenciaAux = "" + secuencia;
		}

		String clave = contextAux + nivelAux + dirigidoAux + secuenciaAux;

		return clave;
	}

	/**
	 *
	 * @param encuestaEstatus
	 * @return
	 */
	public boolean isAprobada(Integer encuestaEstatus) {
		if (ConstantesGestor.ESTADO_ENCUESTA_APROBADA == encuestaEstatus) {
			return true;
		}
		return false;
	}

	@Override
	public List<EncuestaDTO> buscarTodosOrdenEstatus() {

		List<TblEncuesta> encuestas = catalogoEncuestaRepo.buscarTodosOrdenEstatus();
		Type encuestasDto = new TypeToken<List<EncuestaDTO>>() {
		}.getType();
		return catComunMapper.map(encuestas, encuestasDto);
	}

	@Override
	public List<EncuestaDTO> buscarTipoEncuesta(Integer clTipoEncuesta) {

		if (ObjectUtils.isNull(clTipoEncuesta)) {
			clTipoEncuesta = 1;
		}

		List<TblEncuesta> encuestas = catalogoEncuestaRepo.buscarTipoEncuesta(clTipoEncuesta);
		Type encuestasDto = new TypeToken<List<EncuestaDTO>>() {
		}.getType();
		return catComunMapper.map(encuestas, encuestasDto);
	}

	@Override
	public List<EncuestaDTO> buscarEncuestaContexto(Integer clContexto) {
		List<TblEncuesta> encuestas = catalogoEncuestaRepo.buscarEncuestaContexto(clContexto);
		Type encuestasDto = new TypeToken<List<EncuestaDTO>>() {
		}.getType();
		return catComunMapper.map(encuestas, encuestasDto);
	}

	/**
	 * ***Obtienen la evaluación de un programa gracias al promedio de las
	 * encuetas asigandas a los eventos relacionados al programa*************
	 */
	@Override
	public Double calificacionPrograma(Integer idPrograma) {
		Double calificacionPrograma = 0.0;
		Double sumatoria = 0.0;
		List<RelEncuestaUsuarioDTO> listaParticipantes = new ArrayList<>();
		List<Double> listaEvaluacionPorParticipante = new ArrayList<>();

		listaParticipantes = relEncuestaUsuarioService.obtenerParticipantesEncuestasPorPrograma(idPrograma);
		if (!ObjectUtils.isNullOrEmpty(listaParticipantes)) {

			for (RelEncuestaUsuarioDTO participante : listaParticipantes) {
				listaEvaluacionPorParticipante.add(obtenerCalificacionPorUsuario(
						participante.getRelGrupoParticipante().getPersona().getIdPersona(),
						participante.getIdEncuesta()));
			}

			for (Double calificacion : listaEvaluacionPorParticipante) {
				sumatoria = sumatoria + calificacion;
			}
			calificacionPrograma = sumatoria / listaParticipantes.size();
		}

		return calificacionPrograma;
	}

	private Double obtenerCalificacionPorUsuario(Long idPersona, Integer idEncuesta) {
		List<RespuestaUsuarioDTO> respuestaUsuario = new ArrayList<>();
		List<RespuestaDTO> listaRespuestasPonderacion = new ArrayList<>();
		Integer sumatoria = 0;
		Integer numeroItems = 0;
		Double calificacion = 0.0;

		respuestaUsuario = respuestasUsuarioService.buscarRespuestasEncuestaUsuario(idPersona.intValue(), idEncuesta);

		if (!respuestaUsuario.isEmpty()) {

			if (ObjectUtils.isNotNull(respuestaUsuario.get(0).getTblRespuestas())) {
				listaRespuestasPonderacion = respuestaUsuario.get(0).getTblRespuestas();

				for (RespuestaDTO respuesta : listaRespuestasPonderacion) {
					sumatoria = sumatoria + respuesta.getPonderacion();
					numeroItems++;
				}
				calificacion = (double) (sumatoria / numeroItems);
			}
		}

		return calificacion;
	}

	/**
	 * *************************************************************************
	 * *****************************************************************
	 */

	/**
	 * @return the eventoCapacitacionServiceFacade
	 */
	public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
		return eventoCapacitacionServiceFacade;
	}

	/**
	 * @param eventoCapacitacionServiceFacade
	 *            the eventoCapacitacionServiceFacade to set
	 */
	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
	}

	/**
	 * @return the respuestasUsuarioService
	 */
	public RespuestasUsuarioService getRespuestasUsuarioService() {
		return respuestasUsuarioService;
	}

	/**
	 * @param respuestasUsuarioService
	 *            the respuestasUsuarioService to set
	 */
	public void setRespuestasUsuarioService(RespuestasUsuarioService respuestasUsuarioService) {
		this.respuestasUsuarioService = respuestasUsuarioService;
	}

	/**
	 * @return the relEncuestaEventoCapacitacionService
	 */
	public RelEncuestaEventoCapacitacionService getRelEncuestaEventoCapacitacionService() {
		return relEncuestaEventoCapacitacionService;
	}

	/**
	 * @param relEncuestaEventoCapacitacionService
	 *            the relEncuestaEventoCapacitacionService to set
	 */
	public void setRelEncuestaEventoCapacitacionService(
			RelEncuestaEventoCapacitacionService relEncuestaEventoCapacitacionService) {
		this.relEncuestaEventoCapacitacionService = relEncuestaEventoCapacitacionService;
	}

	/**
	 * @return the relEncuestaUsuarioService
	 */
	public RelEncuestaUsuarioService getRelEncuestaUsuarioService() {
		return relEncuestaUsuarioService;
	}

	/**
	 * @param relEncuestaUsuarioService
	 *            the relEncuestaUsuarioService to set
	 */
	public void setRelEncuestaUsuarioService(RelEncuestaUsuarioService relEncuestaUsuarioService) {
		this.relEncuestaUsuarioService = relEncuestaUsuarioService;
	}

	@Override
	public void validarPersistencia(EncuestaDTO dto, ResultadoDTO<EncuestaDTO> resultado) {
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
	public void validarActualizacion(EncuestaDTO dto, ResultadoDTO<EncuestaDTO> resultado) {
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
	public void validarEliminacion(EncuestaDTO dto, ResultadoDTO<EncuestaDTO> resultado) {
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

	/**
	 * Obtiene todas las encuestas que sean de contexto kirkpatrik, tipo
	 * reaccion, dirigido a alumnos, estatus a publicar.
	 * 
	 * @return
	 */
	@Override
	public List<EncuestaDTO> buscarEncsPorKirkReaccionAlumnoPublicar() {
		List<TblEncuesta> listaEncuestasEntities = catalogoEncuestaRepo.buscarEncPorKirkReaccionAlumnoPublicar();
		if (!listaEncuestasEntities.isEmpty()) {
			Type listaEncuestasDTO = new TypeToken<List<EncuestaDTO>>() {
			}.getType();
			return catComunMapper.map(listaEncuestasEntities, listaEncuestasDTO);
		}
		return new ArrayList<>();
	}

	@Override
	public List<EncuestaDTO> consultarEncuestasPorDefecto() {
		String idEncuestaFacilitador = parametroSistemaService.obtenerParametro(ConstantesGestor.ENCUESTA_FACILITADOR);
		String idEncuestaCurso = parametroSistemaService.obtenerParametro(ConstantesGestor.ENCUESTA_CURSO);
		if (!ObjectUtils.isNullOrEmpty(idEncuestaFacilitador) && !ObjectUtils.isNullOrEmpty(idEncuestaCurso)) {
			TblEncuesta encuestaFacilitador = catalogoEncuestaRepo
					.obtenerEncuestaPorId(Integer.valueOf(idEncuestaFacilitador));
			TblEncuesta encuestaCurso = catalogoEncuestaRepo.obtenerEncuestaPorId(Integer.valueOf(idEncuestaCurso));
			if (ObjectUtils.isNotNull(encuestaFacilitador) && ObjectUtils.isNotNull(encuestaCurso)) {
				List<TblEncuesta> listaEncuestasPorDefectoEntities = new ArrayList<>();
				listaEncuestasPorDefectoEntities.add(encuestaFacilitador);
				listaEncuestasPorDefectoEntities.add(encuestaCurso);
				Type listaEncuestasDTO = new TypeToken<List<EncuestaDTO>>() {
				}.getType();
				return catComunMapper.map(listaEncuestasPorDefectoEntities, listaEncuestasDTO);
			}
		}

		return new ArrayList<>();
	}
}
