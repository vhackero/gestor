package mx.gob.sedesol.basegestor.service.eventos;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.eventos.EventoPresencialDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface EventoPresencialService extends CommonService<EventoPresencialDTO, Long> {
	
	List<EventoPresencialDTO> buscarPorCriterios(EventoPresencialDTO dto);

}
