package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.TextoSistemaDTO;

public interface TextoSistemaService extends CommonService<TextoSistemaDTO, String> {
	
	List<TextoSistemaDTO> obtenerTextosPorFuncionalidad(Long idFuncionalidad);
	
	List<TextoSistemaDTO> buscarPorCriterios(TextoSistemaDTO dto);

	String obtenerTexto(String id);

}
