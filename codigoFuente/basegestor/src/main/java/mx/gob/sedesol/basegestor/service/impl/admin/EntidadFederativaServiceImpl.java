package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.CatEntidadFederativa;
import mx.gob.sedesol.basegestor.model.repositories.admin.EntidadFederativaRepo;
import mx.gob.sedesol.basegestor.service.admin.EntidadFederativaService;

@Service("entidadFederativaService")
public class EntidadFederativaServiceImpl implements EntidadFederativaService {
	
	@Autowired
	private EntidadFederativaRepo entidadFederativaRepo;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	private static Type tipoListaEntidadFederativa = new TypeToken<List<EntidadFederativaDTO>>() {}.getType();
	
	@Override
	public List<EntidadFederativaDTO> obtenerEntidadesPorPais(String idPais) {
		return modelMapper.map(entidadFederativaRepo.obtenerEntidadesPorPais(idPais), tipoListaEntidadFederativa);
	}

	@Override
	public List<EntidadFederativaDTO> findAll() {
		return modelMapper.map(entidadFederativaRepo.findAll(), tipoListaEntidadFederativa);
	}
	
	@Override
	public EntidadFederativaDTO buscarPorId(Integer id) {
		EntidadFederativaDTO dto;
		CatEntidadFederativa entidadFederativa = entidadFederativaRepo.findOne(id);
		if (ObjectUtils.isNull(entidadFederativa)) {
			dto = null;
		} else {
			dto = modelMapper.map(entidadFederativa, EntidadFederativaDTO.class);
		}
		return dto;
	}

}
