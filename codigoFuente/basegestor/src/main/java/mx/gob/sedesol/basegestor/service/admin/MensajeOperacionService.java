package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.model.entities.admin.RelMensajeOperacion;

public interface MensajeOperacionService extends CommonService<MensajeOperacionDTO, Long> {
	
	List<MensajeOperacionDTO> obtenerMensajesPorOperacion(long idFuncionalidad);
	
	ResultadoDTO<MensajeOperacionDTO> establecerPlantillaPredeterminada(MensajeOperacionDTO plantilla);
	
	MensajeOperacionDTO obtenerMensajeActivoPorClaveFuncionalidad(String clave);

}
