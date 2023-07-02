package mx.gob.sedesol.basegestor.service.logisticainfraestructura;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ReservacionEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface ReservacionEventoCapacitacionService extends  CommonService<ReservacionEventoCapacitacionDTO, Integer> {

	public List<ReservacionEventoCapacitacionDTO> buscaReservacionesPorIdAreaSede(Integer id);
	
}
