package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.LoteCargaUsuarioDTO;

@FunctionalInterface
public interface LoteCargaUsuarioService {
	
	List<LoteCargaUsuarioDTO> buscarPorCriterios(LoteCargaUsuarioDTO dto);

}
