package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PeriodoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatPeriodo;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.PeriodoRepo;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PeriodoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service("periodoService")
public class PeriodoServiceImpl implements PeriodoService {

	@Autowired
	private PeriodoRepo periodoRepository;

	private ModelMapper periodoMapper = new ModelMapper();

	private static Type periodoDto = new TypeToken<List<PeriodoDTO>>() {}.getType();

	@Override
	public List<PeriodoDTO> findAll() {
		return periodoMapper.map(periodoRepository.findAll(), periodoDto);
	}

	@Override
	public PeriodoDTO buscarPorId(Integer id) {
		PeriodoDTO periodoDTO;
		CatPeriodo entidad = periodoRepository.findOne(id);
		if (ObjectUtils.isNull(entidad)) {
			periodoDTO = null;
		} else {
			periodoDTO = periodoMapper.map(entidad, PeriodoDTO.class);
		}
		return periodoDTO;
	}
}
