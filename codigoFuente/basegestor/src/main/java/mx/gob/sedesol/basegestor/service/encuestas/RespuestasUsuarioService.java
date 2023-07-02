package mx.gob.sedesol.basegestor.service.encuestas;

import java.util.List;
import java.util.Set;

import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

/**
 * Created by jhcortes on 15/12/16.
 */
public interface RespuestasUsuarioService extends CommonService<RespuestaUsuarioDTO, Integer> {

    List<RespuestaUsuarioDTO> buscarRespuestasEncuestaUsuario(Integer idUsuario, Integer idEncuesta);
}
