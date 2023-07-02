package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.TblFuncionalidad;
import mx.gob.sedesol.basegestor.model.especificaciones.FuncionalidadEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.admin.FuncionalidadRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.FuncionalidadService;

@Service("funcionalidadService")
public class FuncionalidadServiceImpl extends ComunValidacionService<FuncionalidadDTO> implements FuncionalidadService {

	@Autowired
	private FuncionalidadRepo funcionalidadRepo;

	private ModelMapper modelMapper = new ModelMapper();

	private static Type tipoListaFuncionalidad = new TypeToken<List<FuncionalidadDTO>>() {
	}.getType();

	public static final String COLUMNA_ORDENAMIENTO = "descripcion";

	@Override
	public List<FuncionalidadDTO> findAll() {
		return modelMapper.map(funcionalidadRepo.findAll(), tipoListaFuncionalidad);
	}

	@Override
	public List<FuncionalidadDTO> buscarPorCriterios(FuncionalidadDTO dto) {
		List<FuncionalidadDTO> listaFuncionalidades;
		if (ObjectUtils.isNull(dto)) {
			listaFuncionalidades = new ArrayList<>();
		} else {
			Specification<TblFuncionalidad> especificacion = new FuncionalidadEspecificacion(
					modelMapper.map(dto, TblFuncionalidad.class));
			List<TblFuncionalidad> funcionalidades = funcionalidadRepo.findAll(especificacion);
			listaFuncionalidades = modelMapper.map(funcionalidades, tipoListaFuncionalidad);
		}
		return listaFuncionalidades;
	}

	@Override
	public FuncionalidadDTO buscarPorId(Long id) {
		TblFuncionalidad funcionalidad = funcionalidadRepo.findOne(id);
		if (ObjectUtils.isNull(funcionalidad)) {
			return null;
		} else {
			funcionalidad.setFuncionalidadPadre(null);
			return modelMapper.map(funcionalidad, FuncionalidadDTO.class);
		}
	}

	@Override
	public ResultadoDTO<FuncionalidadDTO> guardar(FuncionalidadDTO dto) {
		ResultadoDTO<FuncionalidadDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (resultado.getResultado().getValor()) {

			TblFuncionalidad funcionalidad = modelMapper.map(dto, TblFuncionalidad.class);

			TblFuncionalidad entidadRespuesta = funcionalidadRepo.save(funcionalidad);

			resultado.setDto(modelMapper.map(entidadRespuesta, FuncionalidadDTO.class));
			resultado.agregaMensaje(MensajesSistemaEnum.FUNCIONALIDADES_GUARDAR_EXITO.getId());

		} else {
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			resultado.agregaMensaje("Ocurrio un error al crear la funcionalidad");
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<FuncionalidadDTO> actualizar(FuncionalidadDTO dto) {
		ResultadoDTO<FuncionalidadDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (resultado.getResultado().getValor()) {

			TblFuncionalidad funcionalidad = modelMapper.map(dto, TblFuncionalidad.class);

			TblFuncionalidad entidadRespuesta = funcionalidadRepo.save(funcionalidad);
			resultado.setDto(modelMapper.map(entidadRespuesta, FuncionalidadDTO.class));
			resultado.agregaMensaje(MensajesSistemaEnum.FUNCIONALIDADES_EDITAR_EXITO.getId());
		} else {
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			resultado.agregaMensaje("Ocurrio un error al actualizar la funcionalidad");
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<FuncionalidadDTO> eliminar(FuncionalidadDTO dto) {
		ResultadoDTO<FuncionalidadDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (resultado.getResultado().getValor()) {

			TblFuncionalidad funcionalidad = modelMapper.map(dto, TblFuncionalidad.class);

			funcionalidadRepo.save(funcionalidad);

			// GUSTAVO --guardarBitacoraFuncionalidad(dto,
			// String.valueOf(funcionalidad.getIdFuncionalidad()));

			resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
		}
		return resultado;
	}

	@Override
	public void validarPersistencia(FuncionalidadDTO dto, ResultadoDTO<FuncionalidadDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getDescripcion())) {
			resultado.setMensajeError(MensajesSistemaEnum.FUNCIONALIDADES_DESCRIPCION_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getClave())) {
			resultado.setMensajeError(MensajesSistemaEnum.FUNCIONALIDADES_CLAVE_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaRegistro())) {
			resultado.setMensajeError(MensajesSistemaEnum.FUNCIONALIDADES_FECHA_REG_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.FUNCIONALIDADES_USUARIO_REQ);
		}
	}

	@Override
	public void validarActualizacion(FuncionalidadDTO dto, ResultadoDTO<FuncionalidadDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getDescripcion())) {
			resultado.setMensajeError(MensajesSistemaEnum.FUNCIONALIDADES_DESCRIPCION_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getClave())) {
			resultado.setMensajeError(MensajesSistemaEnum.FUNCIONALIDADES_CLAVE_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.FUNCIONALIDADES_FECHA_EDI_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.FUNCIONALIDADES_USUARIO_REQ);
		}
	}

	@Override
	public void validarEliminacion(FuncionalidadDTO dto, ResultadoDTO<FuncionalidadDTO> resultado) {
		if (ObjectUtils.isNullOrCero(dto.getIdFuncionalidad())) {
			resultado.setMensajeError(MensajesSistemaEnum.FUNCIONALIDADES_ID_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.FUNCIONALIDADES_FECHA_EDI_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.FUNCIONALIDADES_USUARIO_REQ);
		}
	}

	@Override
	public List<FuncionalidadDTO> obtenerModulos() {
		return modelMapper.map(funcionalidadRepo.buscarFuncionalidadesPadre(), tipoListaFuncionalidad);
	}

	@Override
	public List<FuncionalidadDTO> obtenerComponentes() {
		List<FuncionalidadDTO> listaFuente = new ArrayList<>();
		List<FuncionalidadDTO> listaFinal = new ArrayList<>();
		List<FuncionalidadDTO> modulos = obtenerModulos();
		for (FuncionalidadDTO modulo : modulos) {
			listaFuente.addAll(obtenerArbolFunciones(modulo.getIdFuncionalidad()));
		}

		procesarComponentes(listaFuente, listaFinal);

		BeanComparator<FuncionalidadDTO> comparadorCampos = new BeanComparator<>(COLUMNA_ORDENAMIENTO);
		Collections.sort(listaFinal, comparadorCampos);
		return listaFinal;
	}

	@Override
	public List<FuncionalidadDTO> obtenerComponentes(Long idFuncionalidad) {
		List<FuncionalidadDTO> listaFuente = obtenerArbolFunciones(idFuncionalidad);
		List<FuncionalidadDTO> listaFinal = new ArrayList<>();
		procesarComponentes(listaFuente, listaFinal);
		BeanComparator<FuncionalidadDTO> comparadorCampos = new BeanComparator<>(COLUMNA_ORDENAMIENTO);
		Collections.sort(listaFinal, comparadorCampos);
		return listaFinal;
	}

	@Override
	public List<FuncionalidadDTO> obtenerOperaciones(Long idFuncionalidad) {
		List<FuncionalidadDTO> listaFuente = obtenerArbolFunciones(idFuncionalidad);
		List<FuncionalidadDTO> listaFinal = new ArrayList<>();
		procesarOperaciones(listaFuente, listaFinal);
		return listaFinal;
	}

	private List<FuncionalidadDTO> obtenerArbolFunciones(Long idFuncionalidad) {
		List<FuncionalidadDTO> funcionalidades = findAll();

		Map<Long, FuncionalidadDTO> mapa = new HashMap<>();
		for (FuncionalidadDTO funcion : funcionalidades) {
			funcion.setFuncionalidadesHijo(new ArrayList<>());
			if (ObjectUtils.isNotNull(funcion.getFuncionalidadPadre())) {
				FuncionalidadDTO funionalidadPadre = mapa.get(funcion.getFuncionalidadPadre().getIdFuncionalidad());
				funionalidadPadre.getFuncionalidadesHijo().add(funcion);
			}
			mapa.put(funcion.getIdFuncionalidad(), funcion);
		}

		FuncionalidadDTO funcionalidad = mapa.get(idFuncionalidad);
		if (ObjectUtils.isNull(funcionalidad)) {
			return new ArrayList<>();
		} else {
			return funcionalidad.getFuncionalidadesHijo();
		}
	}

	private void procesarComponentes(List<FuncionalidadDTO> listaFuente, List<FuncionalidadDTO> listaFinal) {
		for (FuncionalidadDTO dto : listaFuente) {
			if (!dto.getFuncionalidadesHijo().isEmpty()) {
				listaFinal.add(dto);
				procesarComponentes(dto.getFuncionalidadesHijo(), listaFinal);
			}
		}
	}

	private void procesarOperaciones(List<FuncionalidadDTO> listaFuente, List<FuncionalidadDTO> listaFinal) {
		for (FuncionalidadDTO dto : listaFuente) {
			if (dto.getFuncionalidadesHijo().isEmpty()) {
				listaFinal.add(dto);
			} else {
				procesarOperaciones(dto.getFuncionalidadesHijo(), listaFinal);
			}
		}
	}

}
