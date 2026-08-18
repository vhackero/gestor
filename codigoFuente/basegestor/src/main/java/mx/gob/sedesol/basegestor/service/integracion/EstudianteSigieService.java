package mx.gob.sedesol.basegestor.service.integracion;

import java.util.Optional;
import mx.gob.sedesol.basegestor.commons.dto.integracion.EstudianteSigieConsultaDTO;

public interface EstudianteSigieService {
    Optional<EstudianteSigieConsultaDTO> consultarPorMatricula(String matricula);
}
