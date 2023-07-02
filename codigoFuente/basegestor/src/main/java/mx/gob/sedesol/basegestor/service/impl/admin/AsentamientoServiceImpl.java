package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.CatAsentamiento;
import mx.gob.sedesol.basegestor.model.repositories.admin.AsentamientoRepo;
import mx.gob.sedesol.basegestor.service.admin.AsentamientoService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;

@Service("asentamientoService")
@EnableAsync
public class AsentamientoServiceImpl extends ComunValidacionService<AsentamientoDTO> implements AsentamientoService {

	private static final Logger logger = Logger.getLogger(AsentamientoServiceImpl.class);

	@Autowired
	private AsentamientoRepo asentamientoRepo;

	private ModelMapper modelMapper = new ModelMapper();

	private static Type tipoListaAsentamiento = new TypeToken<List<AsentamientoDTO>>() {
	}.getType();

	@Override
	public List<AsentamientoDTO> findAll() {
		return modelMapper.map(asentamientoRepo.findAll(), tipoListaAsentamiento);
	}

	@Override
	public List<AsentamientoDTO> buscarPorMunicipio(String idMunicipio) {
		return modelMapper.map(asentamientoRepo.buscarPorMunicipio(idMunicipio), tipoListaAsentamiento);
	}

	@Override
	public AsentamientoDTO buscarPorCodigoPostal(String codigoPostal) {
		List<CatAsentamiento> asentamientos = asentamientoRepo.buscarPorCodigoPostal(codigoPostal);
		if (asentamientos.isEmpty()) {
			return null;
		} else {
			return modelMapper.map(asentamientos.get(0), AsentamientoDTO.class);
		}
	}

	@Override
	public AsentamientoDTO buscarPorId(String id) {
		CatAsentamiento asentamiento = asentamientoRepo.findOne(id);
		if (ObjectUtils.isNull(asentamiento)) {
			return null;
		} else {
			return modelMapper.map(asentamiento, AsentamientoDTO.class);
		}
	}

	@Override
	public ResultadoDTO<AsentamientoDTO> guardar(AsentamientoDTO dto) {
		logger.info("guardar asentamiento");
		ResultadoDTO<AsentamientoDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (resultado.getResultado().getValor()) {
			try {
				CatAsentamiento entidad = modelMapper.map(dto, CatAsentamiento.class);
				CatAsentamiento entidadRespuesta = asentamientoRepo.save(entidad);
				resultado.setDto(modelMapper.map(entidadRespuesta, AsentamientoDTO.class));
				resultado.agregaMensaje(MensajesSistemaEnum.ASENTAMIENTOS_GUARDAR_EXITO.getId());
			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.agregaMensaje("Ocurrió un error al guardar el asentamiento");
			}
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<AsentamientoDTO> actualizar(AsentamientoDTO dto) {
		ResultadoDTO<AsentamientoDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (resultado.getResultado().getValor()) {
			try {
				CatAsentamiento entidad = modelMapper.map(dto, CatAsentamiento.class);
				CatAsentamiento entidadRespuesta = asentamientoRepo.save(entidad);
				resultado.setDto(modelMapper.map(entidadRespuesta, AsentamientoDTO.class));
				resultado.agregaMensaje(MensajesSistemaEnum.ASENTAMIENTOS_EDITAR_EXITO.getId());
			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.agregaMensaje("Ocurrió un error al actualizar el asentamiento");
			}
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<AsentamientoDTO> eliminar(AsentamientoDTO dto) {
		ResultadoDTO<AsentamientoDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (resultado.getResultado().getValor()) {

			CatAsentamiento entidad = modelMapper.map(dto, CatAsentamiento.class);

			asentamientoRepo.delete(entidad);

			// GUSTAVO --guardarBitacora(dto.getBitacoraDTO(),
			// entidad.getIdAsentamiento());
			resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
		}
		return resultado;
	}

	@Override
	public void validarPersistencia(AsentamientoDTO dto, ResultadoDTO<AsentamientoDTO> resultado) {
		validacionComun(dto, resultado);
		if (ObjectUtils.isNull(dto.getFechaRegistro())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_FECHA_REG_REQ);
		}

	}

	@Override
	public void validarActualizacion(AsentamientoDTO dto, ResultadoDTO<AsentamientoDTO> resultado) {
		validacionComun(dto, resultado);
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_FECHA_EDI_REQ);
		}

	}

	private void validacionComun(AsentamientoDTO dto, ResultadoDTO<AsentamientoDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getIdAsentamiento())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_ID_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_NOMBRE_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getCodigoPostal())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_CODIGO_POSTAL_REQ);
		}
		if (ObjectUtils.isNull(dto.getTipoAsentamiento())
				|| ObjectUtils.isNullOrCero(dto.getTipoAsentamiento().getIdTipoAsentamiento())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_TIPO_REQ);
		}
		if (ObjectUtils.isNull(dto.getMunicipio()) || ObjectUtils.isNullOrEmpty(dto.getMunicipio().getIdMunicipio())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_MUNICIPIO_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_USUARIO_REQ);
		}
	}

	@Override
	public void validarEliminacion(AsentamientoDTO dto, ResultadoDTO<AsentamientoDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getIdAsentamiento())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_ID_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_FECHA_EDI_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_USUARIO_REQ);
		}
	}
}
