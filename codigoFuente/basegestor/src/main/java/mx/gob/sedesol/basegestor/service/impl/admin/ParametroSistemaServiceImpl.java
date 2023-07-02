package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.CatParametroSistema;
import mx.gob.sedesol.basegestor.model.repositories.admin.ParametroSistemaRepo;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;

@Service("parametroSistemaService")
@EnableAsync
public class ParametroSistemaServiceImpl extends ComunValidacionService<ParametroSistemaDTO>
		implements ParametroSistemaService {

	private static final long serialVersionUID = -818248326671790873L;

	private static final Logger LOG = Logger.getLogger(ParametroSistemaServiceImpl.class);

	@Autowired
	private ParametroSistemaRepo parametroSistemaRepo;

	private final ModelMapper modelMapper = new ModelMapper();

	private static final Type TIPO_LISTA_PARAMETRO = new TypeToken<List<ParametroSistemaDTO>>() {
	}.getType();

	@Override
	public List<ParametroSistemaDTO> findAll() {
		return modelMapper.map(parametroSistemaRepo.findAll(), TIPO_LISTA_PARAMETRO);
	}

	@Override
	public ParametroSistemaDTO buscarPorId(String id) {
		ParametroSistemaDTO dto;
		CatParametroSistema entidad = parametroSistemaRepo.findOne(id);
		if (ObjectUtils.isNull(entidad)) {
			dto = null;
		} else {
			dto = modelMapper.map(entidad, ParametroSistemaDTO.class);
		}
		return dto;
	}

	@Override
	public ResultadoDTO<ParametroSistemaDTO> guardar(ParametroSistemaDTO dto) {
		ResultadoDTO<ParametroSistemaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (resultado.getResultado().getValor()) {

			CatParametroSistema entidad = modelMapper.map(dto, CatParametroSistema.class);

			CatParametroSistema entidadRespuesta = parametroSistemaRepo.save(entidad);

			resultado.setDto(modelMapper.map(entidadRespuesta, ParametroSistemaDTO.class));
			resultado.agregaMensaje(MensajesSistemaEnum.PARAMETROS_SISTEMA_GUARDAR_EXITO.getId());
		} else {
			resultado.agregaMensaje("Error al crear parámetro de sistema.");
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<ParametroSistemaDTO> actualizar(ParametroSistemaDTO dto) {
		ResultadoDTO<ParametroSistemaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (resultado.getResultado().getValor()) {

			CatParametroSistema entidad = modelMapper.map(dto, CatParametroSistema.class);

			CatParametroSistema entidadRespuesta = parametroSistemaRepo.save(entidad);
			resultado.setDto(modelMapper.map(entidadRespuesta, ParametroSistemaDTO.class));
			resultado.agregaMensaje(MensajesSistemaEnum.PARAMETROS_SISTEMA_EDITAR_EXITO.getId());
		} else {
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			resultado.agregaMensaje("Error al editar parámetro de sistema.");
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<ParametroSistemaDTO> eliminar(ParametroSistemaDTO dto) {
		ResultadoDTO<ParametroSistemaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (resultado.getResultado().getValor()) {

			CatParametroSistema entidad = modelMapper.map(dto, CatParametroSistema.class);

			parametroSistemaRepo.delete(entidad);

			resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
		}
		return resultado;
	}

	@Override
	public void validarPersistencia(ParametroSistemaDTO dto, ResultadoDTO<ParametroSistemaDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getClave())) {
			resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_SISTEMA_CLAVE_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getValor())) {
			resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_SISTEMA_VALOR_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaRegistro())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
		}
	}

	@Override
	public void validarActualizacion(ParametroSistemaDTO dto, ResultadoDTO<ParametroSistemaDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getClave())) {
			resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_SISTEMA_CLAVE_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getValor())) {
			resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_SISTEMA_VALOR_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
		}
	}

	@Override
	public void validarEliminacion(ParametroSistemaDTO dto, ResultadoDTO<ParametroSistemaDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getClave())) {
			resultado.setMensajeError(MensajesSistemaEnum.PARAMETROS_SISTEMA_CLAVE_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
		}
	}

	@Override
	public String obtenerParametro(String clave) {
		CatParametroSistema parametro = parametroSistemaRepo.findOne(clave);
		if (ObjectUtils.isNull(parametro)) {
			return null;
		} else {
			return parametro.getValor();
		}
	}

	@Override
	public String obtenerParametroRuta(String clave) {
		CatParametroSistema parametro = parametroSistemaRepo.findOne(clave);
		if (ObjectUtils.isNull(parametro)) {
			return null;
		} else {
			return validarEstructuraRuta(parametro.getValor());
		}
	}

	@Override
	public String obtenerParametroConRutaCompleta(String clave) {
		CatParametroSistema parametro = parametroSistemaRepo.findOne(ConstantesGestor.PARAMETRO_RUTA_PRINCIPAL);
		if (ObjectUtils.isNull(parametro)) {
			return null;
		} else {
			String rutaPrincipal = validarEstructuraRuta(parametro.getValor());
			parametro = parametroSistemaRepo.findOne(clave);
			if (ObjectUtils.isNull(parametro)) {
				return null;
			} else {
				return rutaPrincipal + validarEstructuraRuta(parametro.getValor());
			}
		}
	}

	private String validarEstructuraRuta(String ruta) {
		if (ruta.charAt(ruta.length() - 1) == '/') {
			return ruta;
		} else {
			return ruta + "/";
		}
	}

	public ParametroSistemaDTO obtieneParametroPorClave(String clave) {

		CatParametroSistema res = parametroSistemaRepo.findOne(clave);
		if (ObjectUtils.isNotNull(res)) {
			return modelMapper.map(res, ParametroSistemaDTO.class);
		}
		return null;
	}
}
