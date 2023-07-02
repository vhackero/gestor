package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.CatMunicipio;
import mx.gob.sedesol.basegestor.model.repositories.admin.MunicipioRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.MunicipioService;

@Service("municipioService")
public class MunicipioServiceImpl extends ComunValidacionService<MunicipioDTO> implements MunicipioService {

	@Autowired
	private MunicipioRepo municipioRepo;

	private ModelMapper modelMapper = new ModelMapper();

	private static Type tipoListaMunicipio = new TypeToken<List<MunicipioDTO>>() {
	}.getType();

	@Override
	public List<MunicipioDTO> findAll() {
		return modelMapper.map(municipioRepo.findAll(), tipoListaMunicipio);
	}

	@Override
	public List<MunicipioDTO> buscarPorEntidadFederativa(int idEntidadFederativa) {
		return modelMapper.map(municipioRepo.buscarPorEntidadFederativa(idEntidadFederativa), tipoListaMunicipio);
	}

	@Override
	public MunicipioDTO buscarPorId(String id) {
		CatMunicipio municipio = municipioRepo.findOne(id);
		MunicipioDTO municipioDTO;
		if (ObjectUtils.isNull(municipio)) {
			municipioDTO = null;
		} else {
			municipioDTO = modelMapper.map(municipio, MunicipioDTO.class);
		}
		return municipioDTO;
	}

	@Override
	public ResultadoDTO<MunicipioDTO> guardar(MunicipioDTO dto) {
		ResultadoDTO<MunicipioDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (resultado.getResultado().getValor()) {
			try {
				CatMunicipio entidad = modelMapper.map(dto, CatMunicipio.class);
				CatMunicipio entidadRespuesta = municipioRepo.save(entidad);
				resultado.setDto(modelMapper.map(entidadRespuesta, MunicipioDTO.class));
				resultado.agregaMensaje(MensajesSistemaEnum.MUNICIPIOS_GUARDAR_EXITO.getId());
			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.agregaMensaje("Ocurrió un error al intentar guardar el municipio");
			}
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<MunicipioDTO> actualizar(MunicipioDTO dto) {
		ResultadoDTO<MunicipioDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (resultado.getResultado().getValor()) {
			try {
				CatMunicipio entidad = modelMapper.map(dto, CatMunicipio.class);
				CatMunicipio entidadRespuesta = municipioRepo.save(entidad);
				resultado.setDto(modelMapper.map(entidadRespuesta, MunicipioDTO.class));
				resultado.agregaMensaje(MensajesSistemaEnum.MUNICIPIOS_EDITAR_EXITO.getId());
			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.agregaMensaje("Ocurrió un error al intentar actualizar el municipio");
			}
		}
		return resultado;
	}

	@Override
	public ResultadoDTO<MunicipioDTO> eliminar(MunicipioDTO dto) {
		ResultadoDTO<MunicipioDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (resultado.getResultado().getValor()) {

			CatMunicipio entidad = modelMapper.map(dto, CatMunicipio.class);

			municipioRepo.delete(entidad);

			// GUSTAVO --guardarBitacoraMunicipio(dto,
			// entidad.getIdMunicipio());

			resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
		}
		return resultado;
	}

	@Override
	public void validarPersistencia(MunicipioDTO dto, ResultadoDTO<MunicipioDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getIdMunicipio())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_ID_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_NOMBRE_REQ);
		}
		if (ObjectUtils.isNull(dto.getEntidadFederativa())
				|| ObjectUtils.isNullOrCero(dto.getEntidadFederativa().getIdEntidadFederativa())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_ENTIDAD_FED_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaRegistro())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_FECHA_REG_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_USUARIO_REQ);
		}
	}

	@Override
	public void validarActualizacion(MunicipioDTO dto, ResultadoDTO<MunicipioDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getIdMunicipio())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_ID_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_NOMBRE_REQ);
		}
		if (ObjectUtils.isNull(dto.getEntidadFederativa())
				|| ObjectUtils.isNullOrCero(dto.getEntidadFederativa().getIdEntidadFederativa())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_ENTIDAD_FED_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_FECHA_EDI_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_USUARIO_REQ);
		}
	}

	@Override
	public void validarEliminacion(MunicipioDTO dto, ResultadoDTO<MunicipioDTO> resultado) {
		if (ObjectUtils.isNullOrEmpty(dto.getIdMunicipio())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_ID_REQ);
		}
		if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_FECHA_EDI_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_USUARIO_REQ);
		}
	}

}
