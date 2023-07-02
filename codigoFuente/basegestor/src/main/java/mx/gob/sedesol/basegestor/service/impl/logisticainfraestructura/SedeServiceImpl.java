package mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SedeDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatSede;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.SedeRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SedeService;

@Service("sedeService")
public class SedeServiceImpl extends ComunValidacionService<SedeDTO> implements SedeService {

	private static final Logger logger = Logger.getLogger(SedeServiceImpl.class);

	@Autowired
	private SedeRepo sedeRepo;
	private ModelMapper sedeMapper = new ModelMapper();

	@Override
	public List<SedeDTO> findAll() {

		List<SedeDTO> aux = null;
		List<CatSede> res = sedeRepo.findAll();
		if (ObjectUtils.isNotNull(res)) {
			aux = new ArrayList<>();
			for (CatSede s : res) {
				aux.add(sedeMapper.map(s, SedeDTO.class));
			}
		}

		return aux;
	}

	@Override
	public SedeDTO buscarPorId(Integer id) {
		return sedeMapper.map(sedeRepo.findOne(id), SedeDTO.class);
	}

	/**
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public ResultadoDTO<SedeDTO> guardar(SedeDTO dto) {

		ResultadoDTO<SedeDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		try {
			if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
				CatSede sede = sedeMapper.map(dto, CatSede.class);
				sede = sedeRepo.save(sede);
				res.setDto(sedeMapper.map(sede, SedeDTO.class));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
		}
		return res;
	}

	/**
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public ResultadoDTO<SedeDTO> actualizar(SedeDTO dto) {
		ResultadoDTO<SedeDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		try {
			if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
				CatSede sede = sedeMapper.map(dto, CatSede.class);
				sede = sedeRepo.saveAndFlush(sede);
				res.setDto(sedeMapper.map(sede, SedeDTO.class));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			res.setResultado(ResultadoTransaccionEnum.FALLIDO);
			res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
		}
		return res;
	}

	@Override
	public ResultadoDTO<SedeDTO> eliminar(SedeDTO dto) {
		return null;
	}

	/**
	 *
	 * @param idUbicacion
	 * @param idOrgGub
	 * @return
	 */
	@Override
	public List<SedeDTO> consultaSedesPorUbicacionOrgGub(Integer idUbicacion, Integer idOrgGub) {

		Type listAux = new TypeToken<List<SedeDTO>>() {
		}.getType();

		List<CatSede> resBusq = sedeRepo.consultaSedesPorUbicacionOrgGub(idUbicacion, idOrgGub);
		if (ObjectUtils.isNotNull(resBusq)) {
			return sedeMapper.map(resBusq, listAux);
		}

		return new ArrayList<>();
	}

	/**
	 *
	 */
	@Override
	public List<SedeDTO> consultaSedesPorOrgGubDepedencia(Integer idOrgGub) {

		List<CatSede> sedesRes = sedeRepo.consultaSedesPorOrgGubDepedencia(idOrgGub);

		Type listAux = new TypeToken<List<SedeDTO>>() {
		}.getType();
		if (ObjectUtils.isNotNull(sedesRes)) {
			return sedeMapper.map(sedesRes, listAux);
		}

		return new ArrayList<>();

	}

	@Override
	public ResultadoDTO<SedeDTO> sonDatosRequeridosValidos(TipoAccion accion, SedeDTO dto) {

		ResultadoDTO<SedeDTO> res = null;
		if (ObjectUtils.isNotNull(dto)) {
			res = new ResultadoDTO<>();

			switch (accion) {
			case PERSISTENCIA:

				validarPersistencia(dto, res);

				break;

			case ACTUALIZACION:

				validarActualizacion(dto, res);

				break;

			case ELIMINACION:

				if (ObjectUtils.isNullOrEmpty(dto.getIdSede())) {
					res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
				}

				break;
			}
		}

		return res;

	}

	@Override
	public void validarActualizacion(SedeDTO dto, ResultadoDTO<SedeDTO> res) {
		if (ObjectUtils.isNullOrEmpty(dto.getIdSede())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_ID_REQ);
		}

		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_UPDATE_REQ);
		}
	}

	@Override
	public void validarPersistencia(SedeDTO dto, ResultadoDTO<SedeDTO> res) {
		if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_NOMBRE_REQ);
		}

		if (ObjectUtils.isNull(dto.getFechaRegistro())) {
			res.setMensajeError(MensajesErrorEnum.ERROR_FECHA_REGISTRO_REQ);
		}
	}

	@Override
	public void validarEliminacion(SedeDTO dto, ResultadoDTO<SedeDTO> resultado) {
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
