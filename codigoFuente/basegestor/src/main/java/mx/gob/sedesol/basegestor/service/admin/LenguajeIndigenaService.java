package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.LenguajeIndigenaDTO;

public interface LenguajeIndigenaService {
	
	List<LenguajeIndigenaDTO> findAll();

	LenguajeIndigenaDTO buscarPorId(String id);

}
