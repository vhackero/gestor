package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.TipoAsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.admin.CatTipoAsentamiento;
import mx.gob.sedesol.basegestor.model.repositories.admin.TipoAsentamientoRepo;
import mx.gob.sedesol.basegestor.service.admin.TipoAsentamientoService;

@Service("tipoAsentamientoService")
public class TipoAsentamientoServiceImpl implements TipoAsentamientoService {
	
	@Autowired
	private TipoAsentamientoRepo tipoAsentamientoRepo;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	private static Type tipoListaTipoAsentamiento = new TypeToken<List<TipoAsentamientoDTO>>() {}.getType();

	@Override
	public List<TipoAsentamientoDTO> findAll() {
		return modelMapper.map(tipoAsentamientoRepo.findAll(), tipoListaTipoAsentamiento);
	}

	@Override
	public TipoAsentamientoDTO buscarPorId(Integer id) {
		CatTipoAsentamiento tipoAsentamiento = tipoAsentamientoRepo.findOne(id);
		if (ObjectUtils.isNull(tipoAsentamiento)) {
			return null;
		} else {
			return modelMapper.map(tipoAsentamiento, TipoAsentamientoDTO.class);
		}
	}
}
