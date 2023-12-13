package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.AsentamientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProgramaSocialDTO;

public interface AsentamientoService extends CommonService<AsentamientoDTO, String> {
	
	List<AsentamientoDTO> buscarPorMunicipio(String idMunicipio);
	
	AsentamientoDTO buscarPorCodigoPostal(String codigoPostal);

	boolean findDuplicateAsentamiento(AsentamientoDTO asentamientoDTO);
}
