package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.Optional;

public interface IPersonaRepository {
	Optional<Long> obtenerIdPersonaPorMatricula(String matricula);
}
