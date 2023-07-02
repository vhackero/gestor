package mx.gob.sedesol.basegestor.service.planesyprogramas;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.CompetenciaEspecificaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;


public interface CompetenciaEspecificaService extends CommonService<CompetenciaEspecificaDTO, Integer> {
	public boolean estaVacio(Integer id, String nombre);
}