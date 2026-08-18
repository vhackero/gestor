package mx.gob.sedesol.basegestor.model.repositories.integracion;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.integracion.ClienteApiDTO;

@Repository
@Transactional(readOnly = true)
public class ClienteApiRepositoryImpl implements ClienteApiRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<ClienteApiDTO> buscarClienteVigente(String clientId) {
        List<Object[]> rows = entityManager.createNativeQuery(
                "SELECT client_id, secret_hash, scope, ips_permitidas "
                + "FROM tbl_clientes_api_sigie WHERE client_id = :clientId AND activo = 1 "
                + "AND (vigencia_inicio IS NULL OR vigencia_inicio <= NOW()) "
                + "AND (vigencia_fin IS NULL OR vigencia_fin > NOW()) LIMIT 1")
                .setParameter("clientId", clientId).getResultList();
        if (rows.isEmpty()) { return Optional.empty(); }
        Object[] row = rows.get(0);
        ClienteApiDTO dto = new ClienteApiDTO();
        dto.setClientId(row[0].toString());
        dto.setSecretHash(row[1].toString());
        dto.setScope(row[2].toString());
        dto.setIpsPermitidas(row[3] == null ? null : row[3].toString());
        return Optional.of(dto);
    }
}
