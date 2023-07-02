package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.ActividadDTO;

public interface ActividadService extends CommonService<ActividadDTO, String> {
	
	List<ActividadDTO> buscarPorUsuario(long idUsuario);

}
