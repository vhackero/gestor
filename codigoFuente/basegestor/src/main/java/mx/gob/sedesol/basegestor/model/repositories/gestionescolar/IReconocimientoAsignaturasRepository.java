package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReconocimientoAsignaturaDTO;

public interface IReconocimientoAsignaturasRepository {

	List<ReconocimientoAsignaturaDTO> buscarPorMatriculaYPeriodo(String matricula, String periodo);
}
