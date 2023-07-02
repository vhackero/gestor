package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenciaAuxDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelAsistenciaDTO;

public interface RelAsistenciaService {
	
	public List<RelAsistenciaDTO> getAsistenciaByIdGrupoParticipante (List<Integer> listIdGrupoPart) ;
	
	public void save(AsistenciaAuxDTO asistenciaAuxDTO);
	
	public List<GroupByGestionEscolarDTO> obtenerAsistenciaAgrupadaPorEventoCapacitacion(List<Integer> 
		idEventoCapacitacionList,Integer idTipoAsistencia);
	
}
