package mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje;

import java.lang.reflect.Type;
import java.util.List;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.ReponsableProduccionOaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelReponsableProduccionOa;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.ReponsableProduccionOaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.ReponsableProduccionOaService;

@Service("reponsableProduccionOaService")
public class ReponsableProduccionOaServiceImpl extends ComunValidacionService<ReponsableProduccionOaDTO>
		implements ReponsableProduccionOaService {

	private static final Logger logger = Logger.getLogger(ReponsableProduccionOaServiceImpl.class);

	@Autowired
	private ReponsableProduccionOaRepo reponsableProduccionOaRepo;

	private ModelMapper reponsableProduccionOaMapper = new ModelMapper();

	@Override
	public List<ReponsableProduccionOaDTO> findAll() {

		Type listAux = new TypeToken<List<ReponsableProduccionOaDTO>>() {
		}.getType();

		List<RelReponsableProduccionOa> relReponsableProduccionOas = reponsableProduccionOaRepo.findAll();

		this.limpiaRelaciones(relReponsableProduccionOas);

		return reponsableProduccionOaMapper.map(relReponsableProduccionOas, listAux);

	}

	@Override
	public List<ReponsableProduccionOaDTO> findByTipoResponsabilidades(List<Integer> idTipoResposabilidades) {

		Type listAux = new TypeToken<List<ReponsableProduccionOaDTO>>() {
		}.getType();

		List<RelReponsableProduccionOa> relReponsableProduccionOas = reponsableProduccionOaRepo
				.findByTipoResponsabilidades(idTipoResposabilidades);
		this.limpiaRelaciones(relReponsableProduccionOas);

		return reponsableProduccionOaMapper.map(relReponsableProduccionOas, listAux);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<ReponsableProduccionOaDTO> guardar(ReponsableProduccionOaDTO reponsableProduccionOaDTO) {
		ResultadoDTO<ReponsableProduccionOaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA,
				reponsableProduccionOaDTO);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				resultado = new ResultadoDTO<ReponsableProduccionOaDTO>();

				RelReponsableProduccionOa entidad = reponsableProduccionOaMapper.map(reponsableProduccionOaDTO,
						RelReponsableProduccionOa.class);
				RelReponsableProduccionOa respuestaEntidad = reponsableProduccionOaRepo.save(entidad);
				resultado.setDto(reponsableProduccionOaMapper.map(respuestaEntidad, ReponsableProduccionOaDTO.class));
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
	public ResultadoDTO<ReponsableProduccionOaDTO> actualizar(ReponsableProduccionOaDTO dto) {
		ResultadoDTO<ReponsableProduccionOaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				resultado = new ResultadoDTO<ReponsableProduccionOaDTO>();

				RelReponsableProduccionOa entidad = reponsableProduccionOaMapper.map(dto,
						RelReponsableProduccionOa.class);
				reponsableProduccionOaRepo.saveAndFlush(entidad);
				resultado.setDto(reponsableProduccionOaMapper.map(entidad, ReponsableProduccionOaDTO.class));

				// GUSTAVO --guardarBitacora(dto.getBitacoraDTO(),
				// String.valueOf(entidad.getIdRelResponsableProduccionOa()));

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

	@Override
	public ResultadoDTO<ReponsableProduccionOaDTO> sonDatosRequeridosValidos(TipoAccion accion,
			ReponsableProduccionOaDTO dto) {
		ResultadoDTO<ReponsableProduccionOaDTO> res = null;

		if (ObjectUtils.isNotNull(dto)) {
			res = new ResultadoDTO<ReponsableProduccionOaDTO>();

			switch (accion) {
			case PERSISTENCIA:

				if (ObjectUtils.isNull(dto.getFechaRegistro())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_VALOR_REQ);
				}
				if (ObjectUtils.isNull(dto.getIdResponsableProduccion())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_VALOR_REQ);
				}
				if (ObjectUtils.isNull(dto.getIdUnidadOa())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_VALOR_REQ);
				}
				if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_VALOR_REQ);
				}

				break;

			case ACTUALIZACION:

				if (ObjectUtils.isNull(dto.getIdUnidadOa())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
				}
				if (ObjectUtils.isNull(dto.getIdResponsableProduccion())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_VALOR_REQ);
				}
				if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
				}
				if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_VALOR_REQ);
				}

				break;

			case ELIMINACION:

				if (ObjectUtils.isNull(dto.getIdUnidadOa())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
				}
				if (ObjectUtils.isNull(dto.getIdResponsableProduccion())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
				}

				break;

			}
		}
		return res;
	}

	private void limpiaRelaciones(List<RelReponsableProduccionOa> relReponsableProduccionOas) {
		for (RelReponsableProduccionOa relReponsableProduccionOa : relReponsableProduccionOas) {

			if (ObjectUtils.isNotNull(relReponsableProduccionOa.getUnidadOaAva())) {
				relReponsableProduccionOa.getUnidadOaAva().getAmbienteVirtualAprendizaje().setRelUnidadOaAvas(null);
				relReponsableProduccionOa.getUnidadOaAva().getAmbienteVirtualAprendizaje().setEventoCapacitacion(null);
				relReponsableProduccionOa.getUnidadOaAva().setCargaArchivosOas(null);
				relReponsableProduccionOa.getUnidadOaAva().setDetEstUnidadDidactica(null);
				relReponsableProduccionOa.getUnidadOaAva().setFichaDescriptivaObjetoAprendizaje(null);
				relReponsableProduccionOa.getUnidadOaAva().setRecursosOas(null);
				relReponsableProduccionOa.getUnidadOaAva().setReponsableProduccionOas(null);
			}
			if (ObjectUtils.isNotNull(relReponsableProduccionOa.getUnidadOaAva())) {
				relReponsableProduccionOa.getPersonaResponsabilidade().setRelReponsableProduccionOas(null);
				relReponsableProduccionOa.getPersonaResponsabilidade().setRelReponsableProduccionEcs(null);
				relReponsableProduccionOa.getPersonaResponsabilidade().getTblPersona().setDomiciliosPersonas(null);
				;
				relReponsableProduccionOa.getPersonaResponsabilidade().getTblPersona().setPersonaCorreos(null);
				relReponsableProduccionOa.getPersonaResponsabilidade().getTblPersona().setNacionalidad(null);
				relReponsableProduccionOa.getPersonaResponsabilidade().getTblPersona().setRelPersonaRoles(null);
				relReponsableProduccionOa.getPersonaResponsabilidade().getTblPersona().setRelPersonaTelefonos(null);
			}
		}
	}

	@Override
	public void validarPersistencia(ReponsableProduccionOaDTO dto, ResultadoDTO<ReponsableProduccionOaDTO> resultado) {
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
	public void validarActualizacion(ReponsableProduccionOaDTO dto, ResultadoDTO<ReponsableProduccionOaDTO> resultado) {
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
	public void validarEliminacion(ReponsableProduccionOaDTO dto, ResultadoDTO<ReponsableProduccionOaDTO> resultado) {
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
