package mx.gob.sedesol.basegestor.service.impl.encuestas;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.encuestas.RelEncuestaUsuario;
import mx.gob.sedesol.basegestor.model.especificaciones.ConsultarEncuestasUsuarioEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.encuestas.RelEncuestaUsuarioRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaUsuarioService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;

@Service("relEncuestaUsuarioService")
public class RelEncuestaUsuarioServiceImpl extends ComunValidacionService<RelEncuestaUsuarioDTO>
		implements RelEncuestaUsuarioService {

	private static final Logger logger = Logger.getLogger(RelEncuestaUsuarioServiceImpl.class);
	
	@Autowired
	private RelEncuestaUsuarioRepo relEncuestaUsuarioRepo;

	@Autowired
	private RelEncuestaEventoCapacitacionService encuestaEventoCapacitacionService;

	@Autowired
	private GrupoParticipanteService grupoParticipanteService;

	@Autowired
	private EncuestaService encuestaService;

	ModelMapper relEncuestaUsuarioMapper = new ModelMapper();
	
	@Override
	public List<RelEncuestaUsuarioDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelEncuestaUsuarioDTO buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return new RelEncuestaUsuarioDTO();
	}
	
	
	@Override
	public ResultadoDTO<RelEncuestaUsuarioDTO> guardar(RelEncuestaUsuarioDTO dto) {
		ResultadoDTO<RelEncuestaUsuarioDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				RelEncuestaUsuario entidad = new RelEncuestaUsuario();
				relEncuestaUsuarioMapper.map(dto, entidad);
				relEncuestaUsuarioRepo.save(entidad);
				// GUSTAVO --guardarBitacora(dto.getBitacoraDTO(),
				// String.valueOf(entidad.getIdEncuesta()));
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

	@Override
	public ResultadoDTO<RelEncuestaUsuarioDTO> actualizar(RelEncuestaUsuarioDTO dto) {
		ResultadoDTO<RelEncuestaUsuarioDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				RelEncuestaUsuario entidad = new RelEncuestaUsuario();
				relEncuestaUsuarioMapper.map(dto, entidad);
				relEncuestaUsuarioRepo.saveAndFlush(entidad);
				// GUSTAVO --guardarBitacora(dto.getBitacoraDTO(),
				// String.valueOf(entidad.getIdEncuesta()));
				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesErrorEnum.ERROR_ELIMINAR_DATOS);
				throw e;
			}
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<RelEncuestaUsuarioDTO> eliminar(RelEncuestaUsuarioDTO dto) {
		// TODO Auto-generated method stub
		return new ResultadoDTO<>();
	}

	@Override
	public ResultadoDTO<RelEncuestaUsuarioDTO> sonDatosRequeridosValidos(TipoAccion accion, RelEncuestaUsuarioDTO dto) {
		ResultadoDTO<RelEncuestaUsuarioDTO> resultado = null;

		if (ObjectUtils.isNotNull(dto)) {
			resultado = new ResultadoDTO<RelEncuestaUsuarioDTO>();

			switch (accion) {
			case PERSISTENCIA:

				if (ObjectUtils.isNull(dto.getIdEncuesta())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
				}

				if (ObjectUtils.isNull(dto.getIdGrupoParticipante())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
				}

				if (ObjectUtils.isNull(dto.getFechaRegistro())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
				}

				if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
				}

				break;

			case ELIMINACION:

				if (ObjectUtils.isNull(dto.getIdEncuesta())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
				}

				if (ObjectUtils.isNull(dto.getIdGrupoParticipante())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
				}

				break;
			default:
				break;
			}
		}
		return resultado;
	}

	@Override
	public List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(Integer clEvento, Long clPersona, Boolean esActivo) {
		List<RelEncuestaUsuario> encuestas = relEncuestaUsuarioRepo.consultarEncuestasAsignadas(clEvento, clPersona,
				esActivo);
		Type relEncuestaUsuarioDTO = new TypeToken<List<RelEncuestaUsuarioDTO>>() {

		}.getType();
		return relEncuestaUsuarioMapper.map(encuestas, relEncuestaUsuarioDTO);
	}

	@Override
	public List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(Long clPersona, Boolean esActivo) {
		List<RelEncuestaUsuario> encuestas = relEncuestaUsuarioRepo.consultarEncuestasAsignadas(clPersona, esActivo);
		Type relEncuestaUsuarioDTO = new TypeToken<List<RelEncuestaUsuarioDTO>>() {

		}.getType();
		return relEncuestaUsuarioMapper.map(encuestas, relEncuestaUsuarioDTO);
	}

	@Override
	public List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(Integer clEvento, Long clPersona,
			Integer idTipoEncuesta, Boolean esActivo) {
		List<RelEncuestaUsuario> encuestas = relEncuestaUsuarioRepo.consultarEncuestasAsignadas(clEvento, clPersona,
				idTipoEncuesta, esActivo);
		Type relEncuestaUsuarioDTO = new TypeToken<List<RelEncuestaUsuarioDTO>>() {

		}.getType();
		return relEncuestaUsuarioMapper.map(encuestas, relEncuestaUsuarioDTO);
	}

	@Override
	public List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(List<Integer> clEventoList, Long clPersona,
			Integer idTipoEncuesta, Boolean esActivo) {
		List<RelEncuestaUsuario> encuestas = relEncuestaUsuarioRepo.consultarEncuestasAsignadas(clEventoList, clPersona,
				idTipoEncuesta, esActivo);
		Type relEncuestaUsuarioDTO = new TypeToken<List<RelEncuestaUsuarioDTO>>() {

		}.getType();
		return relEncuestaUsuarioMapper.map(encuestas, relEncuestaUsuarioDTO);
	}
	
	@Override
	public List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(List<Integer> clEventoList, Long clPersona,
			Integer idTipoEncuesta) {
		List<RelEncuestaUsuario> encuestas = relEncuestaUsuarioRepo.consultarEncuestasAsignadasTodas(clEventoList, clPersona,
				idTipoEncuesta);
		Type relEncuestaUsuarioDTO = new TypeToken<List<RelEncuestaUsuarioDTO>>() {

		}.getType();
		return relEncuestaUsuarioMapper.map(encuestas, relEncuestaUsuarioDTO);
	}

	@Override
	public List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(List<Integer> clEventoList, Long clPersona,
			Boolean esActivo) {
		List<RelEncuestaUsuario> encuestas = relEncuestaUsuarioRepo.consultarEncuestasAsignadas(clEventoList, clPersona,
				esActivo);
		Type relEncuestaUsuarioDTO = new TypeToken<List<RelEncuestaUsuarioDTO>>() {

		}.getType();
		return relEncuestaUsuarioMapper.map(encuestas, relEncuestaUsuarioDTO);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void asignarEncuestaParticipantes(Integer idGrupo, Date fechaFinalEC, Long usuarioModifico) {
		List<RelGrupoParticipanteDTO> listaGrupoParticipantes = new ArrayList<>();
		Integer idEvento;
		Integer idTipoEncuesta = 0;
		List<RelEncuestaEventoCapacitacionDTO> listaEncuestasAsignadasEvento = new ArrayList<>();
		listaGrupoParticipantes = consultarParticipantesGrupo(idGrupo);
		idEvento = consultarEventoParticipantes(listaGrupoParticipantes);
		listaEncuestasAsignadasEvento = encuestaEventoCapacitacionService.consultarEncuestasAsignadas(idEvento);
		for (RelGrupoParticipanteDTO participante : listaGrupoParticipantes) {
			
			for (RelEncuestaEventoCapacitacionDTO encuestaAsignada : listaEncuestasAsignadasEvento) {
				if (encuestaAsignada.getTblEncuesta().getEncuestaObjetivo().getId() == 1) {
					idTipoEncuesta = encuestaAsignada.getTblEncuesta().getEncuestaTipo().getId();
					RelEncuestaUsuarioDTO relEncuestaUsuarioDTO = new RelEncuestaUsuarioDTO();
					relEncuestaUsuarioDTO.setIdEncuesta(encuestaAsignada.getIdEncuesta());
					relEncuestaUsuarioDTO.setIdGrupoParticipante(participante.getId());
					relEncuestaUsuarioDTO.setFechaRegistro(new Date());
					relEncuestaUsuarioDTO.setFechaApertura(asignarFechaApertura(idTipoEncuesta, fechaFinalEC));
					relEncuestaUsuarioDTO.setActivo(true);
					relEncuestaUsuarioDTO.setUsuarioModifico(usuarioModifico);
					guardar(relEncuestaUsuarioDTO);
				}
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void asignarEncuestasPorDefecto(Integer idGrupo, Date fechaFinalEC, Long usuarioModifico) {
		List<RelGrupoParticipanteDTO> listaGrupoParticipantes = consultarParticipantesGrupo(idGrupo);
		List<EncuestaDTO> listaEncuestasPorDefecto = encuestaService.consultarEncuestasPorDefecto();
		for (RelGrupoParticipanteDTO participante : listaGrupoParticipantes) {
			for (EncuestaDTO encuesta : listaEncuestasPorDefecto) {
				RelEncuestaUsuarioDTO relEncuestaUsuarioDTO = new RelEncuestaUsuarioDTO();
				relEncuestaUsuarioDTO.setIdEncuesta(encuesta.getId());
				relEncuestaUsuarioDTO.setIdGrupoParticipante(participante.getId());
				relEncuestaUsuarioDTO.setFechaRegistro(new Date());
				relEncuestaUsuarioDTO.setFechaApertura(asignarFechaApertura(encuesta.getEncuestaTipo().getId(), fechaFinalEC));
				relEncuestaUsuarioDTO.setActivo(true);
				relEncuestaUsuarioDTO.setUsuarioModifico(usuarioModifico);
				guardar(relEncuestaUsuarioDTO);
			}
		}
	}

	public void asigarEncuestaAlumno(Integer idEvento, Integer idGrupo, Long idPersona, Date fechaFinalEC,
			Long usuarioModifico) {
		Integer idRelGpoParticipante = 0;
		Integer idTipoEncuesta = 0;
		List<RelEncuestaEventoCapacitacionDTO> listaEncuestasAsignadasEvento = new ArrayList<>();
		listaEncuestasAsignadasEvento = encuestaEventoCapacitacionService.consultarEncuestasAsignadas(idEvento);
		idRelGpoParticipante = consultarIdRelGrupoParticipantes(idGrupo, idPersona);
		for (RelEncuestaEventoCapacitacionDTO encuestaAsignada : listaEncuestasAsignadasEvento) {
			if (encuestaAsignada.getTblEncuesta().getEncuestaObjetivo().getId() == 1) {
				idTipoEncuesta = encuestaAsignada.getTblEncuesta().getEncuestaTipo().getId();
				RelEncuestaUsuarioDTO relEncuestaUsuarioDTO = new RelEncuestaUsuarioDTO();
				relEncuestaUsuarioDTO.setIdEncuesta(encuestaAsignada.getIdEncuesta());
				relEncuestaUsuarioDTO.setIdGrupoParticipante(idRelGpoParticipante);
				relEncuestaUsuarioDTO.setFechaRegistro(new Date());
				relEncuestaUsuarioDTO.setFechaApertura(asignarFechaApertura(idTipoEncuesta, fechaFinalEC));
				relEncuestaUsuarioDTO.setActivo(true);
				relEncuestaUsuarioDTO.setUsuarioModifico(usuarioModifico);
				guardar(relEncuestaUsuarioDTO);
			}
		}
	}

	public List<EncuestaDTO> obtenerDetalleEncuestas(Integer clEvento, Long clPersona, Boolean esActivo) {
		List<EncuestaDTO> listaEncuestasUsuario = new ArrayList<>();
		List<RelEncuestaUsuarioDTO> listaIdEncuestaUsuarioAsignadas;
		listaIdEncuestaUsuarioAsignadas = consultarEncuestasAsignadas(clEvento, clPersona, esActivo);

		for (RelEncuestaUsuarioDTO encuestaAsignada : listaIdEncuestaUsuarioAsignadas) {
			EncuestaDTO encuesta = new EncuestaDTO();
			encuesta = encuestaService.buscarPorId(encuestaAsignada.getIdEncuesta());
			listaEncuestasUsuario.add(encuesta);
		}

		return listaEncuestasUsuario;
	}

	private List<RelGrupoParticipanteDTO> consultarParticipantesGrupo(Integer idGrupo) {
		List<RelGrupoParticipanteDTO> listaGrupoParticipantes = new ArrayList<>();
		listaGrupoParticipantes = grupoParticipanteService.getParticipantesByGrupo(idGrupo);
		return listaGrupoParticipantes;
	}

	private Integer consultarEventoParticipantes(List<RelGrupoParticipanteDTO> listaGrupoParticipantes) {
		Integer idEvento;
		RelGrupoParticipanteDTO participante = new RelGrupoParticipanteDTO();
		participante = listaGrupoParticipantes.get(0);
		idEvento = participante.getGrupo().getIdEventoTemp();
		return idEvento;
	}

	private Integer consultarIdRelGrupoParticipantes(Integer idGrupo, Long idPersona) {
		Integer idRelGpoParticipante = 0;
		List<RelGrupoParticipanteDTO> listaGrupoParticipantes = new ArrayList<>();
		listaGrupoParticipantes = grupoParticipanteService.getParticipantesByGrupo(idGrupo);
		for (RelGrupoParticipanteDTO participante : listaGrupoParticipantes) {
			if (participante.getPersona().getIdPersona().equals(idPersona)) {
				idRelGpoParticipante = participante.getId();
			}
		}

		return idRelGpoParticipante;
	}

	private Date asignarFechaApertura(Integer idTipoEncuesta, Date fechaFinalEC) {
		Date fecha;
		Integer dias = 0;
		switch (idTipoEncuesta) {
		case 1:
			dias = ConstantesGestor.FECHA_APERTURA_REACCION;
			break;
		case 2:
			dias = ConstantesGestor.FECHA_APERTURA_COMPORTAMIENTO;
			break;
		case 3:
			dias = ConstantesGestor.FECHA_APERTURA_RESULTADO;
			break;
		}
		try {
			fecha = new SimpleDateFormat("dd-MM-yyyy").parse(fecharLimite(fechaFinalEC, dias));
		} catch (Exception e) {
			fecha = new Date();
		}

		return fecha;
	}

	@Override
	public List<RelEncuestaUsuarioDTO> busquedaPorCriterios(RelEncuestaUsuarioDTO criterios) {
		try {
			if (ObjectUtils.isNotNull(criterios)) {
				Type listaEncuestasAsignadas = new TypeToken<List<RelEncuestaUsuarioDTO>>() {

				}.getType();

				Specification<RelEncuestaUsuario> spec = new ConsultarEncuestasUsuarioEspecificacion(criterios);
				List<RelEncuestaUsuario> resultadosCriterio = relEncuestaUsuarioRepo.findAll(spec);
				return relEncuestaUsuarioMapper.map(resultadosCriterio, listaEncuestasAsignadas);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<RelEncuestaUsuarioDTO> obtenerParticipantesEncuestasPorPrograma(Integer idPrograma) {
		List<RelEncuestaUsuario> listaParticipantes = relEncuestaUsuarioRepo
				.obtenerParticipantesEncuestasPorPrograma(idPrograma);
		Type relEncuestaUsuarioDTO = new TypeToken<List<RelEncuestaUsuarioDTO>>() {
		}.getType();
		return relEncuestaUsuarioMapper.map(listaParticipantes, relEncuestaUsuarioDTO);
	}

	public String fecharLimite(Date fecha, Integer dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);

		return new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
	}

	/**
	 * @return the relEncuestaUsuarioRepo
	 */
	public RelEncuestaUsuarioRepo getRelEncuestaUsuarioRepo() {
		return relEncuestaUsuarioRepo;
	}

	/**
	 * @param relEncuestaUsuarioRepo
	 *            the relEncuestaUsuarioRepo to set
	 */
	public void setRelEncuestaUsuarioRepo(RelEncuestaUsuarioRepo relEncuestaUsuarioRepo) {
		this.relEncuestaUsuarioRepo = relEncuestaUsuarioRepo;
	}

	/**
	 * @return the grupoParticipanteService
	 */
	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	/**
	 * @param grupoParticipanteService
	 *            the grupoParticipanteService to set
	 */
	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
	}

	/**
	 * @return the encuestaEventoCapacitacionService
	 */
	public RelEncuestaEventoCapacitacionService getEncuestaEventoCapacitacionService() {
		return encuestaEventoCapacitacionService;
	}

	/**
	 * @param encuestaEventoCapacitacionService
	 *            the encuestaEventoCapacitacionService to set
	 */
	public void setEncuestaEventoCapacitacionService(
			RelEncuestaEventoCapacitacionService encuestaEventoCapacitacionService) {
		this.encuestaEventoCapacitacionService = encuestaEventoCapacitacionService;
	}

	/**
	 * @return the encuestaService
	 */
	public EncuestaService getEncuestaService() {
		return encuestaService;
	}

	/**
	 * @param encuestaService
	 *            the encuestaService to set
	 */
	public void setEncuestaService(EncuestaService encuestaService) {
		this.encuestaService = encuestaService;
	}

	@Override
	public void validarPersistencia(RelEncuestaUsuarioDTO dto, ResultadoDTO<RelEncuestaUsuarioDTO> resultado) {
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
	public void validarActualizacion(RelEncuestaUsuarioDTO dto, ResultadoDTO<RelEncuestaUsuarioDTO> resultado) {
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
	public void validarEliminacion(RelEncuestaUsuarioDTO dto, ResultadoDTO<RelEncuestaUsuarioDTO> resultado) {
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
