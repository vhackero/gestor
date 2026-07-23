package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatMotivoRestriccionV2;

@Repository
public interface CatMotivoRestriccionV2Repo extends JpaRepository<CatMotivoRestriccionV2, Long> {

    CatMotivoRestriccionV2 findByClave(String clave);
}
