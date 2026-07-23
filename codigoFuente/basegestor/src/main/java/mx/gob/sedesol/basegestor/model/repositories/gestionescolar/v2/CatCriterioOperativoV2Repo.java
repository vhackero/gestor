package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatCriterioOperativoV2;

@Repository
public interface CatCriterioOperativoV2Repo extends JpaRepository<CatCriterioOperativoV2, Long> {

    CatCriterioOperativoV2 findByClave(String clave);
}
