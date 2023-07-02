package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.TipoAsentamientoDTO;

public interface TipoAsentamientoService {
	
	List<TipoAsentamientoDTO> findAll();
	
	TipoAsentamientoDTO buscarPorId(Integer id);

}
