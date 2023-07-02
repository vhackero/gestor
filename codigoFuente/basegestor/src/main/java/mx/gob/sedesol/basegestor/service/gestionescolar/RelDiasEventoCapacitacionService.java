package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.Date;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelDiasEventoCapacitacionDTO;

public interface RelDiasEventoCapacitacionService {
	
	public boolean existeDiaEvento(Date date, int idGrupo);
	
	 public List<RelDiasEventoCapacitacionDTO> getDiasEventoByGrupo (Integer idGrupo);
	 
	 public void save(RelDiasEventoCapacitacionDTO RelDiasEventoCapacitacion);
	 
	 public List<GroupByGestionEscolarDTO> obtenerDiasEventoAgrupadoPorEvento(List<Integer> idEventoCapacitacionList);
}
