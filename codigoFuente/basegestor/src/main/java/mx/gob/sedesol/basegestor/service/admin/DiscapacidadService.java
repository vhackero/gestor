package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.DiscapacidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoDiscapacidadDTO;

public interface DiscapacidadService {

	List<DiscapacidadDTO> findAll();

	DiscapacidadDTO buscarPorId(String id);
	
	List<TipoDiscapacidadDTO> buscarTipoDiscapacidadPorDiscapacidad(int id);
}
