package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;

public interface EntidadFederativaService {
	
	List<EntidadFederativaDTO> obtenerEntidadesPorPais(String idPais);
	
	List<EntidadFederativaDTO> findAll();
	
	EntidadFederativaDTO buscarPorId(Integer id);
	
}
