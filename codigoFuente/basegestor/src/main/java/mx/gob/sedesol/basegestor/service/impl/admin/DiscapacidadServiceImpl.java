package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.DiscapacidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoDiscapacidadDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.CatDiscapacidad;
import mx.gob.sedesol.basegestor.model.repositories.admin.DiscapacidadRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.TipoDiscapacidadRepo;
import mx.gob.sedesol.basegestor.service.admin.DiscapacidadService;


@Service("discapacidadService")
public class DiscapacidadServiceImpl implements DiscapacidadService{

	@Autowired
	private DiscapacidadRepo discapacidadRepo;
	
	@Autowired
	private TipoDiscapacidadRepo tipoDiscapacidadRepo;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	private static Type tipoListaDiscapacidad = new TypeToken<List<DiscapacidadDTO>>() {}.getType();
	private static Type tipoListaTipoDiscapacidad = new TypeToken<List<TipoDiscapacidadDTO>>() {}.getType();
	
	@Override
	public List<DiscapacidadDTO> findAll() {
		return modelMapper.map(discapacidadRepo.findAll(), tipoListaDiscapacidad);
	}

	@Override
	public DiscapacidadDTO buscarPorId(String id) {
		DiscapacidadDTO discapacidadDTO;
		CatDiscapacidad discapacidad = discapacidadRepo.findOne(id);
		if (ObjectUtils.isNull(discapacidad)) {
			discapacidadDTO = null;
		} else {
			discapacidadDTO = modelMapper.map(discapacidad, DiscapacidadDTO.class);
		}
		return discapacidadDTO;
	}
	
	@Override
	public List<TipoDiscapacidadDTO> buscarTipoDiscapacidadPorDiscapacidad(int idDiscapacidad) {
		return modelMapper.map(tipoDiscapacidadRepo.buscarPorDiscapacidad(idDiscapacidad), tipoListaTipoDiscapacidad);
	}

	
}
