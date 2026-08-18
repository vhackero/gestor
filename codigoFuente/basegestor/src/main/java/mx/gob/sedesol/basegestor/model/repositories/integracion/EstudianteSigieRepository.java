package mx.gob.sedesol.basegestor.model.repositories.integracion;

import java.util.Optional;
import mx.gob.sedesol.basegestor.commons.dto.integracion.EstudianteSigieConsultaDTO;

public interface EstudianteSigieRepository {
    Optional<EstudianteSigieConsultaDTO> consultarPorMatricula(String matricula);
}
