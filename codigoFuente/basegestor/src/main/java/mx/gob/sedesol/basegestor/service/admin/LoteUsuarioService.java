package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.LoteUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;

public interface LoteUsuarioService {
	
	ResultadoDTO<LoteUsuarioDTO> guardar(LoteUsuarioDTO LoteUsuario);

	List<UsuarioDatosLaboralesDTO> obtenerUsuarioPorLote(Integer idLote);

}
