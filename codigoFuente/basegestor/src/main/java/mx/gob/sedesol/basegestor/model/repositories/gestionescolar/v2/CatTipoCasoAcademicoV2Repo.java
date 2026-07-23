package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatTipoCasoAcademicoV2;

@Repository
public interface CatTipoCasoAcademicoV2Repo extends JpaRepository<CatTipoCasoAcademicoV2, Long> {

    CatTipoCasoAcademicoV2 findByClave(String clave);
}
