package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelSolicitudEnrolamientoEvtCapDTO;

public interface RelSolicitudEnrolEvtCapService {

	
	List<EventoCapacitacionDTO> obtenerSolicitudesEnrolamientoPorIdPersona(Long idPersona,Boolean esActivo);

	List<EventoCapacitacionDTO> obtenerSolicitudesPorIdPersonaPrograma(Long idPersona, Boolean esActivo,
			Integer idPrograma);
	ResultadoDTO<RelSolicitudEnrolamientoEvtCapDTO> crearSolicitud(RelSolicitudEnrolamientoEvtCapDTO solicitud);
}
