package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.PaisDTO;

public interface PaisService {

	List<PaisDTO> findAll();

	PaisDTO buscarPorId(String id);

}
