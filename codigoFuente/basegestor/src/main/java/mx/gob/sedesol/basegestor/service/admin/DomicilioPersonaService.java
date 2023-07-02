package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.DomicilioPersonaDTO;

public interface DomicilioPersonaService extends CommonService<DomicilioPersonaDTO, Long> {
	List<DomicilioPersonaDTO> busquedaPorCriterios(DomicilioPersonaDTO criterios, String tipoDatoFechas);
	List<DomicilioPersonaDTO> busquedaPorCriteriosReporteUsuario(DomicilioPersonaDTO criterios, String tipoDatoFechas);
}
