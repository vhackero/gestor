package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.PaisDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.CatPais;
import mx.gob.sedesol.basegestor.model.repositories.admin.PaisRepo;
import mx.gob.sedesol.basegestor.service.admin.PaisService;

@Service("paisService")
public class PaisServiceImpl implements PaisService {
	
	@Autowired
	private PaisRepo paisRepo;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	private static Type tipoListaPais = new TypeToken<List<PaisDTO>>() {}.getType();

	@Override
	public List<PaisDTO> findAll() {
		return modelMapper.map(paisRepo.findAll(), tipoListaPais);
	}

	@Override
	public PaisDTO buscarPorId(String id) {
		PaisDTO paisDTO;
		CatPais entidad = paisRepo.findOne(id);
		if (ObjectUtils.isNull(entidad)) {
			paisDTO = null;
		} else {
			paisDTO = modelMapper.map(entidad, PaisDTO.class);
		}
		return paisDTO;
	}

}
