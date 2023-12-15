package mx.gob.sedesol.basegestor.service.planesyprogramas;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PeriodoDTO;

import java.util.List;

public interface PeriodoService {

	List<PeriodoDTO> findAll();

	PeriodoDTO buscarPorId(Integer id);

}
