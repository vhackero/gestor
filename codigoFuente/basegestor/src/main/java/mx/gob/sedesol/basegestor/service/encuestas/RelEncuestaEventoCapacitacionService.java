package mx.gob.sedesol.basegestor.service.encuestas;



import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface RelEncuestaEventoCapacitacionService extends CommonService<RelEncuestaEventoCapacitacionDTO, Integer>
{
	List<RelEncuestaEventoCapacitacionDTO> consultarEncuestasAsignadas(Integer clEventoCapacitacion);
	List<RelEncuestaEventoCapacitacionDTO> busquedaPorCriterios(RelEncuestaEventoCapacitacionDTO filtro, String tipoDatoFechas);
	void asignarEncuestas(Integer idEvento,Integer idContexto, Long usuarioModifico);
	void asignarEncuestaEvento(Integer idEvento, Integer idEncuesta, Long usuarioModifico);
	void asignarEncuestaEvento(Integer idEvento, Integer idEncuesta, Long usuarioModifico, Boolean valor);
}
