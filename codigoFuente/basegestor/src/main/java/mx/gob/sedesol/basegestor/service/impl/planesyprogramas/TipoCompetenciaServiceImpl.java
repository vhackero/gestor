package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.TipoCompetenciaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoCompetencia;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.TipoCompetenciaRepo;
import mx.gob.sedesol.basegestor.service.planesyprogramas.TipoCompetenciaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service("tipoCompetenciaService")
public class TipoCompetenciaServiceImpl implements TipoCompetenciaService {

	@Autowired
	private TipoCompetenciaRepo tipoCompetenciaRepo;

	private ModelMapper modelMapper = new ModelMapper();

	private static Type tpoCompetenciaDTO = new TypeToken<List<TipoCompetenciaDTO>>() {}.getType();

	@Override
	public List<TipoCompetenciaDTO> findAll() {
		return modelMapper.map(tipoCompetenciaRepo.findAll(), tpoCompetenciaDTO);
	}

	@Override
	public TipoCompetenciaDTO buscarPorId(Integer id) {
		TipoCompetenciaDTO tipoCompetenciaDTO;
		CatTipoCompetencia entidad = tipoCompetenciaRepo.findOne(id);
		if (ObjectUtils.isNull(entidad)) {
			tipoCompetenciaDTO = null;
		} else {
			tipoCompetenciaDTO = modelMapper.map(entidad, TipoCompetenciaDTO.class);
		}
		return tipoCompetenciaDTO;
	}
}
