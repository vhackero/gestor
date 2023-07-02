package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;

public interface VariableMensajeOperacionService extends CommonService<VariableMensajeOperacionDTO, Integer> {
	
	List<VariableMensajeOperacionDTO> obtenerVariablesPorOperacion(long idFuncionalidad);

}
