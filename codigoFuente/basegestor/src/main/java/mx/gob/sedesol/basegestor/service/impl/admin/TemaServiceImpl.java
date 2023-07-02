package mx.gob.sedesol.basegestor.service.impl.admin;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TemaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.commons.utils.TipoTemaEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.TblTema;
import mx.gob.sedesol.basegestor.model.repositories.admin.TemaRepo;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.TemaService;

@Service("temaService")
@EnableAsync
public class TemaServiceImpl extends ComunValidacionService<TemaDTO> implements TemaService {

	private static final Logger logger = Logger.getLogger(TemaServiceImpl.class);

	@Autowired
	private TemaRepo temaRepo;

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	private ModelMapper modelMapper = new ModelMapper();

	private static Type tipoListaTema = new TypeToken<List<TemaDTO>>() {
	}.getType();

	@Override
	public List<TemaDTO> findAll() {
		return modelMapper.map(temaRepo.findAll(), tipoListaTema);
	}

	@Override
	public TemaDTO buscarPorId(Integer id) {
		TemaDTO temaDTO;
		TblTema tema = temaRepo.findOne(id);
		if (ObjectUtils.isNull(tema)) {
			temaDTO = null;
		} else {
			temaDTO = modelMapper.map(tema, TemaDTO.class);
		}
		return temaDTO;
	}

	@Override
	public ResultadoDTO<TemaDTO> guardar(TemaDTO dto) {
		ResultadoDTO<TemaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (resultado.getResultado().getValor()) {

			TblTema entidad = modelMapper.map(dto, TblTema.class);

			TblTema entidadRespuesta = temaRepo.save(entidad);

			resultado.setDto(modelMapper.map(entidadRespuesta, TemaDTO.class));
			resultado.agregaMensaje(MensajesSistemaEnum.TEMAS_GUARDAR_EXITO.getId());
		} else {
			resultado.agregaMensaje("Ocurrió un error al crear el tema");
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<TemaDTO> actualizar(TemaDTO dto) {
		ResultadoDTO<TemaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (resultado.getResultado().getValor()) {

			TblTema entidad = modelMapper.map(dto, TblTema.class);

			TblTema entidadRespuesta = temaRepo.saveAndFlush(entidad);

			resultado.setDto(modelMapper.map(entidadRespuesta, TemaDTO.class));

			resultado.agregaMensaje(MensajesSistemaEnum.TEMAS_EDITAR_EXITO.getId());
		} else {
			resultado.agregaMensaje("Ocurrió un error al crear el tema");
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			logger.info("Fallo la validacion en el servicio");
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<TemaDTO> eliminar(TemaDTO dto) {
		ResultadoDTO<TemaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (resultado.getResultado().getValor()) {

			TblTema entidad = modelMapper.map(dto, TblTema.class);

			temaRepo.delete(entidad);

			resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
		}
		return resultado;
	}

	@Override
	public void validarPersistencia(TemaDTO dto, ResultadoDTO<TemaDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_NOMBRE_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getRuta())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_RUTA_REQ);
		}
		if (ObjectUtils.isNullOrCero(dto.getTipoTema())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_TIPO_REQ);
		}
		if (ObjectUtils.isNull(dto.getActivo())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_ACTIVO_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaRegistro())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_FECHA_REG_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_USUARIO_REQ);
		}
	}

	@Override
	public void validarActualizacion(TemaDTO dto, ResultadoDTO<TemaDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getIdTema())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_ID_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_NOMBRE_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getRuta())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_RUTA_REQ);
		}
		if (ObjectUtils.isNullOrCero(dto.getTipoTema())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_TIPO_REQ);
		}
		if (ObjectUtils.isNull(dto.getActivo())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_ACTIVO_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_FECHA_EDI_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_USUARIO_REQ);
		}
	}

	@Override
	public void validarEliminacion(TemaDTO dto, ResultadoDTO<TemaDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getIdTema())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_ID_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_FECHA_EDI_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEMAS_USUARIO_REQ);
		}
	}

	@Override
	public List<TemaDTO> buscarPorTipo(Integer tipoTema) {
		return modelMapper.map(temaRepo.buscarPorTipo(tipoTema), tipoListaTema);
	}

	@Override
	public ResultadoDTO<TemaDTO> activarTema(TemaDTO tema) {
		ResultadoDTO<TemaDTO> resultado = new ResultadoDTO<>();

		for (TblTema entidad : temaRepo.buscarPorTipo(tema.getTipoTema())) {
			if (entidad.getIdTema() == tema.getIdTema()) {
				entidad.setActivo(true);
			} else {
				entidad.setActivo(false);
			}
			entidad.setUsuarioModifico(tema.getUsuarioModifico());
			entidad.setFechaActualizacion(new Date());
			temaRepo.save(entidad);
		}

		resultado.agregaMensaje(MensajesSistemaEnum.TEMAS_EDITAR_EXITO.getId());

		return resultado;
	}

	@Override
	public String obtenerTemaActivo(int tipo) {
		List<TblTema> temas = temaRepo.buscarPorTipoActivo(tipo, true);
		if (!temas.isEmpty() && validarTema(modelMapper.map(temas.get(0), TemaDTO.class))) {
			return temas.get(0).getRuta();
		}
		return ConstantesGestor.TEMA_BASE;
	}

	@Override
	public boolean validarTema(TemaDTO tema) {
		StringBuilder rutaAlmacenamiento = new StringBuilder(
				parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_RECURSOS));
		rutaAlmacenamiento.append(obtenerRutaTipo(tema.getTipoTema()));
		rutaAlmacenamiento.append("/");
		rutaAlmacenamiento.append(tema.getRuta());
		rutaAlmacenamiento.append("/");

		for (String nombreRecurso : obtenerRecursos(tema.getTipoTema())) {
			File archivo = new File(rutaAlmacenamiento.toString() + nombreRecurso);
			if (!archivo.exists()) {
				return false;
			}
		}

		return true;
	}

	private String obtenerRutaTipo(int tipoTema) {
		TipoTemaEnum tipo = TipoTemaEnum.getTipoTemaEnum(tipoTema);
		if (ObjectUtils.isNotNull(tipo)) {
			if (tipo.getValor() == TipoTemaEnum.PUBLICO.getValor()) {
				return ConstantesGestor.RUTA_RECURSOS_PUBLICO;
			}
			if (tipo.getValor() == TipoTemaEnum.PRIVADO.getValor()) {
				return ConstantesGestor.RUTA_RECURSOS_PRIVADO;
			}
		}
		return ConstantesGestor.RUTA_RECURSOS_PRIVADO;
	}

	private String[] obtenerRecursos(int tipoTema) {
		TipoTemaEnum tipo = TipoTemaEnum.getTipoTemaEnum(tipoTema);
		if (ObjectUtils.isNotNull(tipo)) {
			if (tipo.getValor() == TipoTemaEnum.PUBLICO.getValor()) {
				return ConstantesGestor.RECURSOS_PUBLICO;
			}
			if (tipo.getValor() == TipoTemaEnum.PRIVADO.getValor()) {
				return ConstantesGestor.RECURSOS_PRIVADOS;
			}
		}
		return ConstantesGestor.RECURSOS_PRIVADOS;
	}

}
