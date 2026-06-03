package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReconocimientoAsignaturaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;

public interface ReconocimientoAsignaturasService {

	List<ReconocimientoAsignaturaDTO> buscarPorMatriculaYPeriodo(String matricula, String periodo);

	ResultadoDTO<RelGrupoParticipanteDTO> eliminarRegistro(ReconocimientoAsignaturaDTO registro, Long usuarioModifico);

	boolean actualizarRegistro(Long idRegistro);
}
