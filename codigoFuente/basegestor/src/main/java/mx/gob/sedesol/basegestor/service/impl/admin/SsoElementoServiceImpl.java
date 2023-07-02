package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.SsoElementoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.SsoElemento;
import mx.gob.sedesol.basegestor.model.repositories.admin.SsoElementoRepo;
import mx.gob.sedesol.basegestor.service.admin.SsoElementoService;


@Service("ssoElementoServiceImpl")
public class SsoElementoServiceImpl implements SsoElementoService {

	private static final Logger logger = Logger.getLogger(SsoElementoServiceImpl.class);

	@Autowired
	private SsoElementoRepo ssoElementoRepo;

	private ModelMapper mapper = new ModelMapper();

	Type elementosDTO = new TypeToken<List<SsoElementoDTO>>() {
	}.getType();

	@Override
	public List<SsoElementoDTO> findAll() {
		List<SsoElemento> entidades = ssoElementoRepo.findAll();
		return mapper.map(entidades, elementosDTO);
	}

	@Override
	public SsoElementoDTO buscarPorId(Integer id) {
		SsoElemento entidad = ssoElementoRepo.findOne(id);
		return mapper.map(entidad, SsoElementoDTO.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<SsoElementoDTO> guardar(SsoElementoDTO dto) {
		ResultadoDTO<SsoElementoDTO> resultado = new ResultadoDTO<>();
		try {
			SsoElemento entidad = mapper.map(dto, SsoElemento.class);
			ssoElementoRepo.save(entidad);
			SsoElementoDTO dtoRespuesta = mapper.map(entidad, SsoElementoDTO.class);
			resultado.setDto(dtoRespuesta);
			return resultado;
		} catch (Exception e) {
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			logger.info("El error fue: " + e);
			logger.info("########################");
			logger.info("El error con e.getMessage fue: " + e.getMessage());
			logger.info("########################");
			e.printStackTrace();
			return resultado;
		}
	}

	@Override
	public ResultadoDTO<SsoElementoDTO> actualizar(SsoElementoDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<SsoElementoDTO> eliminar(SsoElementoDTO dto) {
		ResultadoDTO<SsoElementoDTO> resultado = new ResultadoDTO<>();
		try {
			SsoElemento entidad = mapper.map(dto, SsoElemento.class);
			ssoElementoRepo.delete(entidad);
			SsoElementoDTO dtoRespuesta = mapper.map(entidad, SsoElementoDTO.class);
			resultado.setDto(dtoRespuesta);
			return resultado;
		} catch (Exception e) {
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
			logger.info("El error fue: " + e);
			logger.info("########################");
			logger.info("El error con e.getMessage fue: " + e.getMessage());
			logger.info("########################");
			e.printStackTrace();
			return resultado;
		}
	}

	@Override
	public ResultadoDTO<SsoElementoDTO> sonDatosRequeridosValidos(TipoAccion accion, SsoElementoDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelMapper getMapper() {
		return mapper;
	}

	public void setMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

}
