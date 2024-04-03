package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CalificacionRecordDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CapturaEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EncabezadoActaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TablaCalificacionesDTO;

public interface EventoCapacitacionService {

	List<EventoCapacitacionDTO> consultaEventoPorEstatus(Integer idEstatus);
	List<EventoCapacitacionDTO>buscaEventosPorCriterios(EventoCapacitacionDTO filtro,String tipoDatoFechas);
	List<EventoCapacitacionDTO> obtenerEventosConcluidos(Integer idPrograma, Integer idPlataforma);
	EventoCapacitacionDTO getEvento(Integer idEvento);
	ResultadoDTO<EventoCapacitacionDTO> guardarEventoCapacitacion(CapturaEventoCapacitacionDTO evento, Boolean autonomo) throws Exception;
	ResultadoDTO<EventoCapacitacionDTO> guardarBorrador(CapturaEventoCapacitacionDTO datos, Boolean autonomo);
	CapturaEventoCapacitacionDTO obtenerEvento(EventoCapacitacionDTO evento);
	List<EventoCapacitacionDTO> obtenerEventosPublicosEnEjec();
	List<EventoCapacitacionDTO> consultaEventosPorTipoVisibilidad(boolean isPrivado);
	List<EventoCapacitacionDTO> obtenerEventosPorPrograma(Integer idPrograma);
	List<EventoCapacitacionDTO> obtenerEventosPorProgramaIdPlan(String idPrograma, Integer idPlan);
	List<EventoCapacitacionDTO> obtenerEventosPorIdPlan( Integer idPlan);
	List<EventoCapacitacionDTO> obtenerTodosLosEventos();
	boolean modificarEstatusEvento(Integer idEstatus, Integer idEvento);
	
	
	List<CalificacionRecordDTO> obtieneDetalleActa(Integer idEvento, Integer idCurso, List<TablaCalificacionesDTO> listaCalif);
	EncabezadoActaDTO obtenerEncabezadoActa(Integer idEvento, Integer idGrupo);


}
