package mx.gob.sedesol.basegestor.service.planesyprogramas;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.TipoCompetenciaDTO;

import java.util.List;

public interface TipoCompetenciaService {

	List<TipoCompetenciaDTO> findAll();

	TipoCompetenciaDTO buscarPorId(Integer id);

}
