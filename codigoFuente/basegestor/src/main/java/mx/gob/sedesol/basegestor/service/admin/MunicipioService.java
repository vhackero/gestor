package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;

public interface MunicipioService extends CommonService<MunicipioDTO, String> {
	
	List<MunicipioDTO> buscarPorEntidadFederativa(int idEntidadFederativa);

	boolean findDuplicateMunicipio(MunicipioDTO municipioDTO);
}
