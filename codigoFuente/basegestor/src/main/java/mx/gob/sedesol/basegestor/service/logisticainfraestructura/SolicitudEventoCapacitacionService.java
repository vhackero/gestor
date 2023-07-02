package mx.gob.sedesol.basegestor.service.logisticainfraestructura;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.CritBusquedaReporteReservDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface SolicitudEventoCapacitacionService extends CommonService<RelSolicitudEventoCapacitacionDTO, Integer>{

	public List<RelSolicitudEventoCapacitacionDTO> consultaSolicitudesPorIdReservacion(Integer idReservacion);
	
	public List<RelSolicitudEventoCapacitacionDTO> consultaReservacionesPorIdSolicitud(Integer idSolicitud);
	
	public List<RelSolicitudEventoCapacitacionDTO> consultaPorCriterios(CritBusquedaReporteReservDTO criterios);
	
}
