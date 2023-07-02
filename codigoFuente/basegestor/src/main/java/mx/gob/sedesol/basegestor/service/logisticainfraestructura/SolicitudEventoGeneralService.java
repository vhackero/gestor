package mx.gob.sedesol.basegestor.service.logisticainfraestructura;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.CritBusquedaReporteReservDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoGeneralDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface SolicitudEventoGeneralService extends CommonService<RelSolicitudEventoGeneralDTO, Integer> {

	public List<RelSolicitudEventoGeneralDTO> consultaSolicitudesPorIdReservacion(Integer idReservacion);
	
	public List<RelSolicitudEventoGeneralDTO> consultaReservacionesPorIdSolicitud(Integer idSolicitud);
	
	public List<RelSolicitudEventoGeneralDTO> consultaPorCriterios(CritBusquedaReporteReservDTO criterios);
	
}
