package mx.gob.sedesol.basegestor.service.logisticainfraestructura;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ReservacionEventoGeneralDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface ReservacionEventoGeneralService extends CommonService<ReservacionEventoGeneralDTO, Integer> {

	public List<ReservacionEventoGeneralDTO> buscaReservacionesPorIdAreaSede(Integer id);
	
}
