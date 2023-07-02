package mx.gob.sedesol.basegestor.service.impl.encuestas;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.encuestas.RelEncuestaEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.especificaciones.EventoCapEspecificacion;
import mx.gob.sedesol.basegestor.model.especificaciones.RelEncuestaEventoCapacitacionEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.encuestas.RelEncuestaEventoCapacitacionRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaEventoCapacitacionService;

@Service("relEncuestaEventoCapacitacionService")
public class RelEncuestaEventoCapacitacionServiceImpl extends ComunValidacionService<RelEncuestaEventoCapacitacionDTO>
		implements RelEncuestaEventoCapacitacionService {

	private static final Logger logger = Logger.getLogger(RelEncuestaEventoCapacitacionServiceImpl.class);

	@Autowired
	private RelEncuestaEventoCapacitacionRepo relEncuestaEventoCapacitacionRepo;

	@Autowired
	private EncuestaService encuestaService;

	private ModelMapper relEncuestaEventoCapacitacionMapper = new ModelMapper();

	private Type tipoListaRelEncuestaEvento = new TypeToken<List<RelEncuestaEventoCapacitacionDTO>>() {
	}.getType();

	@Override
	public List<RelEncuestaEventoCapacitacionDTO> busquedaPorCriterios(RelEncuestaEventoCapacitacionDTO filtro,
			String tipoDatoFechas) {
		try {
			RelEncuestaEventoCapacitacionEspecificacion especificacion = new RelEncuestaEventoCapacitacionEspecificacion(
					filtro, tipoDatoFechas);
			List<RelEncuestaEventoCapacitacion> res = relEncuestaEventoCapacitacionRepo.findAll(especificacion);

			return relEncuestaEventoCapacitacionMapper.map(res, tipoListaRelEncuestaEvento);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<RelEncuestaEventoCapacitacionDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelEncuestaEventoCapacitacionDTO buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<RelEncuestaEventoCapacitacionDTO> guardar(RelEncuestaEventoCapacitacionDTO dto) {
		ResultadoDTO<RelEncuestaEventoCapacitacionDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA,
				dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				RelEncuestaEventoCapacitacion entidad = new RelEncuestaEventoCapacitacion();
				relEncuestaEventoCapacitacionMapper.map(dto, entidad);
				relEncuestaEventoCapacitacionRepo.save(entidad);
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
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<RelEncuestaEventoCapacitacionDTO> actualizar(RelEncuestaEventoCapacitacionDTO dto) {
		ResultadoDTO<RelEncuestaEventoCapacitacionDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION,
				dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				RelEncuestaEventoCapacitacion entidad = new RelEncuestaEventoCapacitacion();
				relEncuestaEventoCapacitacionMapper.map(dto, entidad);
				relEncuestaEventoCapacitacionRepo.saveAndFlush(entidad);
				// GUSTAVO --guardarBitacora(dto.getBitacoraDTO(),
				// String.valueOf(entidad.getIdEventoCapacitacion()));
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
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<RelEncuestaEventoCapacitacionDTO> eliminar(RelEncuestaEventoCapacitacionDTO dto) {
		return new ResultadoDTO<>();
	}

	@Override
	public ResultadoDTO<RelEncuestaEventoCapacitacionDTO> sonDatosRequeridosValidos(TipoAccion accion,
			RelEncuestaEventoCapacitacionDTO dto) {

		ResultadoDTO<RelEncuestaEventoCapacitacionDTO> resultado = null;

		if (ObjectUtils.isNotNull(dto)) {
			resultado = new ResultadoDTO<RelEncuestaEventoCapacitacionDTO>();

			switch (accion) {
			case PERSISTENCIA:

				if (ObjectUtils.isNull(dto.getIdEncuesta())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_CLAVE_REQ);
				}

				if (ObjectUtils.isNull(dto.getIdEventoCapacitacion())) {
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

				if (ObjectUtils.isNull(dto.getIdEventoCapacitacion())) {
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
	public List<RelEncuestaEventoCapacitacionDTO> consultarEncuestasAsignadas(Integer clEventoCapacitacion) {
		List<RelEncuestaEventoCapacitacion> encuestas = relEncuestaEventoCapacitacionRepo
				.consultarEncuestasAsignadas(clEventoCapacitacion);
		Type relEncuestaEventoCapacitacionDTO = new TypeToken<List<RelEncuestaEventoCapacitacionDTO>>() {

		}.getType();
		return relEncuestaEventoCapacitacionMapper.map(encuestas, relEncuestaEventoCapacitacionDTO);
	}

	public void asignarEncuestas(Integer idEvento, Integer idContexto, Long usuarioModifico) {
		List<EncuestaDTO> listaEncuestas = new ArrayList<>();
		listaEncuestas = buscarEncuestasContexto(idContexto);
		asignarEncuestaAEvento(idEvento, listaEncuestas, usuarioModifico);

	}

	public void asignarEncuestaEvento(Integer idEvento, Integer idEncuesta, Long usuarioModifico) {
		RelEncuestaEventoCapacitacionDTO relEncuestaEventoCapacitacionDTO = new RelEncuestaEventoCapacitacionDTO();
		relEncuestaEventoCapacitacionDTO.setIdEventoCapacitacion(idEvento);
		relEncuestaEventoCapacitacionDTO.setIdEncuesta(idEncuesta);
		relEncuestaEventoCapacitacionDTO.setFechaRegistro(new Date());
		relEncuestaEventoCapacitacionDTO.setActivo(true);
		relEncuestaEventoCapacitacionDTO.setUsuarioModifico(usuarioModifico);
		guardar(relEncuestaEventoCapacitacionDTO);
	}
	
	public void asignarEncuestaEvento(Integer idEvento, Integer idEncuesta, Long usuarioModifico, Boolean valor) {
		RelEncuestaEventoCapacitacionDTO relEncuestaEventoCapacitacionDTO = new RelEncuestaEventoCapacitacionDTO(valor);
		relEncuestaEventoCapacitacionDTO.setIdEventoCapacitacion(idEvento);
		relEncuestaEventoCapacitacionDTO.setIdEncuesta(idEncuesta);
		relEncuestaEventoCapacitacionDTO.setFechaRegistro(new Date());
		relEncuestaEventoCapacitacionDTO.setUsuarioModifico(usuarioModifico);
		guardar(relEncuestaEventoCapacitacionDTO);
	}

	private List<EncuestaDTO> buscarEncuestasContexto(Integer idContexto) {
		List<EncuestaDTO> listaEncuestas = new ArrayList<>();
		listaEncuestas = encuestaService.buscarEncuestaContexto(idContexto);
		return listaEncuestas;
	}

	private void asignarEncuestaAEvento(Integer idEvento, List<EncuestaDTO> listaEncuestas, Long usuarioModifico) {
		for (EncuestaDTO encuesta : listaEncuestas) {
			RelEncuestaEventoCapacitacionDTO relEncuestaEventoCapacitacionDTO = new RelEncuestaEventoCapacitacionDTO();

			relEncuestaEventoCapacitacionDTO.setIdEventoCapacitacion(idEvento);
			relEncuestaEventoCapacitacionDTO.setIdEncuesta(encuesta.getId());
			relEncuestaEventoCapacitacionDTO.setFechaRegistro(new Date());
			relEncuestaEventoCapacitacionDTO.setActivo(true);
			relEncuestaEventoCapacitacionDTO.setUsuarioModifico(usuarioModifico);
			guardar(relEncuestaEventoCapacitacionDTO);
		}
	}

	public void desasignarEncuestaEvento(Integer idEvento, Integer idContexto) {
		List<EncuestaDTO> listaEncuestas = new ArrayList<>();
		listaEncuestas = buscarEncuestasContexto(idContexto);
		for (EncuestaDTO encuesta : listaEncuestas) {
			RelEncuestaEventoCapacitacionDTO relEncuestaEventoCapacitacionDTO = new RelEncuestaEventoCapacitacionDTO();

			relEncuestaEventoCapacitacionDTO.setIdEventoCapacitacion(idEvento);
			relEncuestaEventoCapacitacionDTO.setIdEncuesta(encuesta.getId());
			relEncuestaEventoCapacitacionDTO.setFechaActualizacion(new Date());
			relEncuestaEventoCapacitacionDTO.setActivo(false);
			// relEncuestaEventoCapacitacionDTO.setUsuarioModifico();
			actualizar(relEncuestaEventoCapacitacionDTO);
		}
	}

	/**
	 * @return the relEncuestaEventoCapacitacionRepo
	 */
	public RelEncuestaEventoCapacitacionRepo getRelEncuestaEventoCapacitacionRepo() {
		return relEncuestaEventoCapacitacionRepo;
	}

	/**
	 * @param relEncuestaEventoCapacitacionRepo
	 *            the relEncuestaEventoCapacitacionRepo to set
	 */
	public void setRelEncuestaEventoCapacitacionRepo(
			RelEncuestaEventoCapacitacionRepo relEncuestaEventoCapacitacionRepo) {
		this.relEncuestaEventoCapacitacionRepo = relEncuestaEventoCapacitacionRepo;
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
	public void validarPersistencia(RelEncuestaEventoCapacitacionDTO dto,
			ResultadoDTO<RelEncuestaEventoCapacitacionDTO> resultado) {
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
	public void validarActualizacion(RelEncuestaEventoCapacitacionDTO dto,
			ResultadoDTO<RelEncuestaEventoCapacitacionDTO> resultado) {
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
	public void validarEliminacion(RelEncuestaEventoCapacitacionDTO dto,
			ResultadoDTO<RelEncuestaEventoCapacitacionDTO> resultado) {
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
