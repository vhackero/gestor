package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.RelMensajeOperacion;
import mx.gob.sedesol.basegestor.model.repositories.admin.MensajeOperacionRepo;
import mx.gob.sedesol.basegestor.mongo.service.impl.NotificacionServiceImpl;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.MensajeOperacionService;

@Service("mensajeOperacionService")
public class MensajeOperacionServiceImpl extends ComunValidacionService<MensajeOperacionDTO>
		implements MensajeOperacionService {
	
    private static final Logger logger = Logger.getLogger(MensajeOperacionServiceImpl.class);

	@Autowired
	private MensajeOperacionRepo mensajeOperacionRepo;

	private ModelMapper modelMapper = new ModelMapper();

	private static Type tipoListaMensajeOperacion = new TypeToken<List<MensajeOperacionDTO>>() {
	}.getType();

	@Override
	public List<MensajeOperacionDTO> findAll() {
		return modelMapper.map(mensajeOperacionRepo.findAll(), tipoListaMensajeOperacion);
	}

	@Override
	public MensajeOperacionDTO buscarPorId(Long id) {
		MensajeOperacionDTO mensajeOperacion = new MensajeOperacionDTO();
		RelMensajeOperacion relMensajeOperacion = mensajeOperacionRepo.findOne(id);
		if (ObjectUtils.isNull(relMensajeOperacion)) {
			mensajeOperacion = null;
		} else {
			modelMapper.map(relMensajeOperacion, mensajeOperacion);
		}
		return mensajeOperacion;
	}

	@Override
	public ResultadoDTO<MensajeOperacionDTO> guardar(MensajeOperacionDTO dto) {
		ResultadoDTO<MensajeOperacionDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (resultado.getResultado().getValor()) {

			RelMensajeOperacion entidad = modelMapper.map(dto, RelMensajeOperacion.class);

			mensajeOperacionRepo.save(entidad);

			// GUSTAVO --guardarBitacoraMensaje(dto,
			// String.valueOf(entidad.getIdMensajeOperacion()));
			resultado.agregaMensaje(MensajesSistemaEnum.PLNT_MSG_GUARDAR_EXITO.getId());
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<MensajeOperacionDTO> actualizar(MensajeOperacionDTO dto) {
		ResultadoDTO<MensajeOperacionDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (resultado.getResultado().getValor()) {

			RelMensajeOperacion entidad = modelMapper.map(dto, RelMensajeOperacion.class);

			mensajeOperacionRepo.save(entidad);

			// GUSTAVO --guardarBitacoraMensaje(dto,
			// String.valueOf(entidad.getIdMensajeOperacion()));

			resultado.agregaMensaje(MensajesSistemaEnum.PLNT_MSG_EDITAR_EXITO.getId());
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<MensajeOperacionDTO> eliminar(MensajeOperacionDTO dto) {
		ResultadoDTO<MensajeOperacionDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (resultado.getResultado().getValor()) {

			RelMensajeOperacion entidad = modelMapper.map(dto, RelMensajeOperacion.class);

			mensajeOperacionRepo.delete(entidad);

			// GUSTAVO --guardarBitacoraMensaje(dto,
			// String.valueOf(entidad.getIdMensajeOperacion()));

			resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
		}
		return resultado;
	}

	@Override
	public void validarPersistencia(MensajeOperacionDTO dto, ResultadoDTO<MensajeOperacionDTO> resultado) {
		if (ObjectUtils.isNull(dto.getFuncionalidad())
				|| ObjectUtils.isNullOrCero(dto.getFuncionalidad().getIdFuncionalidad())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_OPERACION_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getTitulo())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_TITULO_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getMensaje())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_MENSAJE_REQ);
		}
		if (ObjectUtils.isNullOrCero(dto.getTipo())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_TIPO_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaRegistro())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_FECHA_REG_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_USUARIO_REQ);
		}
	}

	@Override
	public void validarActualizacion(MensajeOperacionDTO dto, ResultadoDTO<MensajeOperacionDTO> resultado) {
		if (ObjectUtils.isNullOrCero(dto.getIdMensajeOperacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_ID_REQ);
		}
		if (ObjectUtils.isNull(dto.getFuncionalidad())
				|| ObjectUtils.isNullOrCero(dto.getFuncionalidad().getIdFuncionalidad())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_OPERACION_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getTitulo())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_TITULO_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getMensaje())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_MENSAJE_REQ);
		}
		if (ObjectUtils.isNullOrCero(dto.getTipo())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_TIPO_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_FECHA_EDI_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_USUARIO_REQ);
		}
	}

	@Override
	public void validarEliminacion(MensajeOperacionDTO dto, ResultadoDTO<MensajeOperacionDTO> resultado) {
		if (ObjectUtils.isNullOrCero(dto.getIdMensajeOperacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_ID_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_FECHA_EDI_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.PLNT_MSG_USUARIO_REQ);
		}
	}

	@Override
	public List<MensajeOperacionDTO> obtenerMensajesPorOperacion(long idFuncionalidad) {
		return modelMapper.map(mensajeOperacionRepo.obtenerMensajesPorOperacion(idFuncionalidad),
				tipoListaMensajeOperacion);
	}

	@Override
	public ResultadoDTO<MensajeOperacionDTO> establecerPlantillaPredeterminada(MensajeOperacionDTO plantilla) {
		ResultadoDTO<MensajeOperacionDTO> resultado = new ResultadoDTO<>();
		for (RelMensajeOperacion mensajeOperacion : mensajeOperacionRepo
				.obtenerMensajesPorOperacion(plantilla.getFuncionalidad().getIdFuncionalidad())) {
			if (mensajeOperacion.isActivo()) {
				mensajeOperacion.setActivo(false);
				mensajeOperacion.setUsuarioModifico(plantilla.getUsuarioModifico());
				mensajeOperacion.setFechaActualizacion(new Date());
				mensajeOperacionRepo.save(mensajeOperacion);
			} else {
				if (mensajeOperacion.getIdMensajeOperacion() == plantilla.getIdMensajeOperacion()) {
					mensajeOperacion.setActivo(true);
					mensajeOperacion.setUsuarioModifico(plantilla.getUsuarioModifico());
					mensajeOperacion.setFechaActualizacion(new Date());
					mensajeOperacion.setTitulo(plantilla.getTitulo());
					mensajeOperacion.setMensaje(plantilla.getMensaje());
					mensajeOperacion.setTipo(plantilla.getTipo());
					mensajeOperacionRepo.save(mensajeOperacion);
				}
			}

		}
		resultado.agregaMensaje(MensajesSistemaEnum.PLNT_MSG_PREDETERMINADO.getId());
		return resultado;
	}


	@Override
	public MensajeOperacionDTO obtenerMensajeActivoPorClaveFuncionalidad(String clave) {
		RelMensajeOperacion relMensajeOperacionEntidad = mensajeOperacionRepo.obtenerMensajeActivoPorClaveFuncionalidad(clave);
		if(ObjectUtils.isNotNull(relMensajeOperacionEntidad)){
			return modelMapper.map(relMensajeOperacionEntidad, MensajeOperacionDTO.class);
		}
		return null;
	}
}
