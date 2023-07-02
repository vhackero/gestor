package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TextoSistemaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.TblTextoSistema;
import mx.gob.sedesol.basegestor.model.especificaciones.TextoSistemaEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.admin.TextoSistemaRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.TextoSistemaService;

@Service("textoSistemaService")
public class TextoSistemaServiceImpl extends ComunValidacionService<TextoSistemaDTO> implements TextoSistemaService {

	@Autowired
	private TextoSistemaRepo textoSistemaRepo;

	@Autowired
	private AdministradorBitacora administradorBitacora;

	private ModelMapper modelMapper = new ModelMapper();

	private static Type tipoListaTextoSistema = new TypeToken<List<TextoSistemaDTO>>() {
	}.getType();

	@Override
	public List<TextoSistemaDTO> obtenerTextosPorFuncionalidad(Long idFuncionalidad) {
		return modelMapper.map(textoSistemaRepo.obtenerTextosPorFuncionalidad(idFuncionalidad), tipoListaTextoSistema);
	}

	@Override
	public List<TextoSistemaDTO> findAll() {
		return modelMapper.map(textoSistemaRepo.findAll(), tipoListaTextoSistema);
	}

	@Override
	public TextoSistemaDTO buscarPorId(String id) {
		TextoSistemaDTO texto;
		TblTextoSistema tblTextoSistema = textoSistemaRepo.findOne(id);
		if (ObjectUtils.isNull(tblTextoSistema)) {
			texto = null;
		} else {
			texto = modelMapper.map(tblTextoSistema, TextoSistemaDTO.class);
		}
		return texto;
	}

	@Override
	public String obtenerTexto(String id) {
		String texto;
		TblTextoSistema tblTextoSistema = textoSistemaRepo.findOne(id);
		if (ObjectUtils.isNull(tblTextoSistema)) {
			texto = id;
		} else {
			texto = tblTextoSistema.getValor();
		}
		return texto;
	}

	@Override
	public ResultadoDTO<TextoSistemaDTO> guardar(TextoSistemaDTO dto) {
		ResultadoDTO<TextoSistemaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (resultado.getResultado().getValor()) {
			TblTextoSistema entidad = modelMapper.map(dto, TblTextoSistema.class);
			TblTextoSistema entidadRespuesta = textoSistemaRepo.save(entidad);
			resultado.setDto(modelMapper.map(entidadRespuesta, TextoSistemaDTO.class));
			resultado.agregaMensaje(MensajesSistemaEnum.TEXTOS_SISTEMA_GUARDAR_EXITO.getId());
		} else {
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			resultado.agregaMensaje("Ocurrió un error al crear el texto del sistema.");
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<TextoSistemaDTO> actualizar(TextoSistemaDTO dto) {
		ResultadoDTO<TextoSistemaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (resultado.getResultado().getValor()) {
			try {
				TblTextoSistema entidad = modelMapper.map(dto, TblTextoSistema.class);
				TblTextoSistema entidadRespuesta = textoSistemaRepo.save(entidad);
				resultado.setDto(modelMapper.map(entidadRespuesta, TextoSistemaDTO.class));
				resultado.agregaMensaje(MensajesSistemaEnum.TEXTOS_SISTEMA_EDITAR_EXITO.getId());
			} catch (Exception e) {
				resultado.agregaMensaje("Ocurrió un error al actualizar el texto");
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			}
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<TextoSistemaDTO> eliminar(TextoSistemaDTO dto) {
		ResultadoDTO<TextoSistemaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (resultado.getResultado().getValor()) {
			TblTextoSistema entidad = modelMapper.map(dto, TblTextoSistema.class);

			textoSistemaRepo.delete(entidad);

			// GUSTAVO --guardarBitacoraTexto(dto, entidad.getClave());

			resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
		}
		return resultado;
	}

	@Override
	public void validarPersistencia(TextoSistemaDTO dto, ResultadoDTO<TextoSistemaDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getClave())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEXTOS_SISTEMA_CLAVE_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getValor())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEXTOS_SISTEMA_VALOR_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaRegistro())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEXTOS_SISTEMA_FECHA_REG_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEXTOS_SISTEMA_USUARIO_REQ);
		}
	}

	@Override
	public void validarActualizacion(TextoSistemaDTO dto, ResultadoDTO<TextoSistemaDTO> resultado) {
		validarPersistencia(dto, resultado);
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEXTOS_SISTEMA_FECHA_EDI_REQ);
		}
	}

	@Override
	public void validarEliminacion(TextoSistemaDTO dto, ResultadoDTO<TextoSistemaDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getClave())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEXTOS_SISTEMA_CLAVE_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEXTOS_SISTEMA_FECHA_EDI_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.TEXTOS_SISTEMA_USUARIO_REQ);
		}
	}

	@Override
	public List<TextoSistemaDTO> buscarPorCriterios(TextoSistemaDTO dto) {
		List<TextoSistemaDTO> listaTextos;
		if (ObjectUtils.isNotNull(dto)) {
			Specification<TblTextoSistema> especificacion = new TextoSistemaEspecificacion(
					modelMapper.map(dto, TblTextoSistema.class));
			List<TblTextoSistema> textos = textoSistemaRepo.findAll(especificacion);

			listaTextos = modelMapper.map(textos, tipoListaTextoSistema);
		} else {
			listaTextos = new ArrayList<>();
		}
		return listaTextos;
	}

	/*
	 * private void guardarBitacoraTexto(TextoSistemaDTO dto, String clave) {
	 * //Usar la clave/id del registro guardado
	 * dto.getBitacoraDTO().setRegistro(clave); //Guarda la bitácora <<<<<<<
	 * Updated upstream //GUSTAVO --guardarBitacora(dto.getBitacoraDTO()); }
	 */

}
