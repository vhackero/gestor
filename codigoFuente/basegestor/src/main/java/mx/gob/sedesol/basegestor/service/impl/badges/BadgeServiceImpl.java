package mx.gob.sedesol.basegestor.service.impl.badges;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.badges.BadgeDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.badges.CatBadge;
import mx.gob.sedesol.basegestor.model.repositories.badges.BadgeRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.badges.BadgeService;

/**
 * Implementacion de la interface
 *
 * @author nnm_eolf
 *
 */
@Service("badgeService")
@EnableAsync
public class BadgeServiceImpl extends ComunValidacionService<BadgeDTO> implements BadgeService {

	@Autowired
	private BadgeRepo badgeRepo;

	private ModelMapper modelMapper = new ModelMapper();

	private static Type tipoListaParametro = new TypeToken<List<BadgeDTO>>() {
	}.getType();

	private static final Logger LOG = Logger.getLogger(BadgeServiceImpl.class);

	@Override
	public List<BadgeDTO> findAll() {
		LOG.debug("[findAll]");
		return modelMapper.map(badgeRepo.findAll(), tipoListaParametro);
	}

	@Override
	public BadgeDTO buscarPorId(Integer id) {
		LOG.debug("[buscarPorId]");
		BadgeDTO dto;
		CatBadge entidad = badgeRepo.findOne(id);
		if (ObjectUtils.isNull(entidad)) {
			dto = null;
		} else {
			dto = modelMapper.map(entidad, BadgeDTO.class);
		}
		return dto;
	}

	@Override
	public ResultadoDTO<BadgeDTO> guardar(BadgeDTO dto) {
		LOG.debug("[guardar]");
		ResultadoDTO<BadgeDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (resultado.getResultado().getValor()) {

			CatBadge entidad = modelMapper.map(dto, CatBadge.class);
			CatBadge entidadRespuesta = badgeRepo.save(entidad);
			resultado.setDto(modelMapper.map(entidadRespuesta, BadgeDTO.class));

			resultado.agregaMensaje(MensajesSistemaEnum.BADGE_GUARDAR_EXITO.getId());
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<BadgeDTO> actualizar(BadgeDTO dto) {
		LOG.debug("[actualizar]");
		ResultadoDTO<BadgeDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (resultado.getResultado().getValor()) {

			CatBadge entidad = modelMapper.map(dto, CatBadge.class);
			CatBadge entidadRespuesta = badgeRepo.save(entidad);
			resultado.setDto(modelMapper.map(entidadRespuesta, BadgeDTO.class));
			resultado.agregaMensaje(MensajesSistemaEnum.BADGE_EDITAR_EXITO.getId());
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<BadgeDTO> eliminar(BadgeDTO dto) {
		LOG.debug("[eliminar]");
		ResultadoDTO<BadgeDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (resultado.getResultado().getValor()) {

			CatBadge entidad = modelMapper.map(dto, CatBadge.class);

			badgeRepo.delete(entidad);

			// GUSTAVO --guardarBitacoraBadge(dto,
			// String.valueOf(entidad.getIdBadge()));

			resultado.agregaMensaje(MensajesSistemaEnum.BADGE_ELIMINAR_EXITO.getId());
		}
		return resultado;
	}

	/**
	 * Metodo que encapsula los campos comunes
	 *
	 * @author nnm_eolf
	 * @param dto
	 * @param resultado
	 */
	private void validarComunes(BadgeDTO dto, ResultadoDTO<BadgeDTO> resultado) {
		LOG.debug("[validarComunes]");
		if (ObjectUtils.isNull(dto.getIdClasificacionBadge())) {
			resultado.setMensajeError(MensajesSistemaEnum.CLASIFICACION_BADGE_ID_REQ);
		}
		if (ObjectUtils.isNull(dto.getCalificacionMaxima())) {
			resultado.setMensajeError(MensajesSistemaEnum.BADGE_CALIFMAXIMA_REQ);
		}
		if (ObjectUtils.isNull(dto.getCalificacionMinima())) {
			resultado.setMensajeError(MensajesSistemaEnum.BADGE_CALIFMINIMA_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
			resultado.setMensajeError(MensajesSistemaEnum.BADGE_NOMBRE_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getRutaImagen())) {
			resultado.setMensajeError(MensajesSistemaEnum.BADGE_RUTAIMAGEN_REQ);
		}
		if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
		}
		if (ObjectUtils.isNull(dto.getIdEstatus())) {
			resultado.setMensajeError(MensajesSistemaEnum.BADGE_ESTATUS_REQ);
		}
	}

	@Override
	public void validarPersistencia(BadgeDTO dto, ResultadoDTO<BadgeDTO> resultado) {
		LOG.debug("[validarPersistencia]");
		validarComunes(dto, resultado);
		if (ObjectUtils.isNull(dto.getFechaRegistro())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
		}
	}

	@Override
	public void validarActualizacion(BadgeDTO dto, ResultadoDTO<BadgeDTO> resultado) {
		LOG.debug("[validarActualizacion]");
		validarComunes(dto, resultado);
		if (ObjectUtils.isNull(dto.getIdBadge())) {
			resultado.setMensajeError(MensajesSistemaEnum.BADGE_ID_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
		}
	}

	@Override
	public void validarEliminacion(BadgeDTO dto, ResultadoDTO<BadgeDTO> resultado) {
		LOG.debug("[validarEliminacion]");
		if (ObjectUtils.isNull(dto.getIdBadge())) {
			resultado.setMensajeError(MensajesSistemaEnum.BADGE_ID_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
			resultado.setMensajeError(MensajesSistemaEnum.CLASIFICACION_BADGE_NOMBRE_REQ);
		}
		if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
		}
	}

	@Override
	public BadgeDTO obtenerBadgePorRangoPuntos(Integer puntos) {
		List<CatBadge> listaBadges = badgeRepo.findAll();
		if (puntos > listaBadges.get(listaBadges.size() - 1).getCalificacionMaxima()) {
			CatBadge badge = listaBadges.get(listaBadges.size() - 1);
			return modelMapper.map(badge, BadgeDTO.class);
		} else if (puntos < listaBadges.get(0).getCalificacionMinima()) {
			return null;
		} else {
			CatBadge catBadge = badgeRepo.obtenerBadgePorRangoPuntos(puntos);
			return modelMapper.map(catBadge, BadgeDTO.class);
		}
	}

}
