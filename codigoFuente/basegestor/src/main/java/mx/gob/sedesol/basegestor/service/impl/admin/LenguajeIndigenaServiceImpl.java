package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.LenguajeIndigenaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.CatLenguajeIndigena;
import mx.gob.sedesol.basegestor.model.repositories.admin.LenguajeIndigenaRepo;
import mx.gob.sedesol.basegestor.service.admin.LenguajeIndigenaService;

@Service("lenguajeIndigenaService")
public class LenguajeIndigenaServiceImpl implements LenguajeIndigenaService{

	@Autowired
	private LenguajeIndigenaRepo lenguajeIndigenaRepo;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	private static Type tipoListaLenguaje = new TypeToken<List<LenguajeIndigenaDTO>>() {}.getType();

	@Override
	public List<LenguajeIndigenaDTO> findAll() {
		return modelMapper.map(lenguajeIndigenaRepo.findAll(), tipoListaLenguaje);
	}

	@Override
	public LenguajeIndigenaDTO buscarPorId(String id) {
		LenguajeIndigenaDTO lenguajeDTO;
		CatLenguajeIndigena lenguaje = lenguajeIndigenaRepo.findOne(id);
		if (ObjectUtils.isNull(lenguaje)) {
			lenguajeDTO = null;
		} else {
			lenguajeDTO = modelMapper.map(lenguaje, LenguajeIndigenaDTO.class);
		}
		return lenguajeDTO;
	}
}
