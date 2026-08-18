package mx.gob.sedesol.basegestor.service.impl.integracion;

import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.integracion.ClienteApiDTO;
import mx.gob.sedesol.basegestor.model.repositories.integracion.ClienteApiRepository;
import mx.gob.sedesol.basegestor.service.integracion.ClienteApiService;

@Service
@Transactional(readOnly = true)
public class ClienteApiServiceImpl implements ClienteApiService {
    @Autowired
    private ClienteApiRepository repository;

    @Override
    public ClienteApiDTO autenticar(String clientId, String clientSecret) {
        Optional<ClienteApiDTO> candidate = repository.buscarClienteVigente(clientId);
        if (!candidate.isPresent() || clientSecret == null
                || !BCrypt.checkpw(clientSecret, candidate.get().getSecretHash())) {
            return null;
        }
        return candidate.get();
    }
}
