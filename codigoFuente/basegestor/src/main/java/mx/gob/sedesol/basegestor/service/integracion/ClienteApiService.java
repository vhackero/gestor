package mx.gob.sedesol.basegestor.service.integracion;

import mx.gob.sedesol.basegestor.commons.dto.integracion.ClienteApiDTO;

public interface ClienteApiService {
    ClienteApiDTO autenticar(String clientId, String clientSecret);
}
