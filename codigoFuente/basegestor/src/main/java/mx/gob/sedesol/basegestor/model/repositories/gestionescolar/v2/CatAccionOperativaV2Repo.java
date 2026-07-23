package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatAccionOperativaV2;

@Repository
public interface CatAccionOperativaV2Repo extends JpaRepository<CatAccionOperativaV2, Long> {

    CatAccionOperativaV2 findByClave(String clave);
}
