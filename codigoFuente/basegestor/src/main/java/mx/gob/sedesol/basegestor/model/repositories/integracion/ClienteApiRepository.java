package mx.gob.sedesol.basegestor.model.repositories.integracion;

import java.util.Optional;
import mx.gob.sedesol.basegestor.commons.dto.integracion.ClienteApiDTO;

public interface ClienteApiRepository {
    Optional<ClienteApiDTO> buscarClienteVigente(String clientId);
}
