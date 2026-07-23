package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.RelPatronCriterioOperativoV2;

@Repository
public interface RelPatronCriterioOperativoV2Repo extends JpaRepository<RelPatronCriterioOperativoV2, Long> {

    List<RelPatronCriterioOperativoV2> findByIdPatronCaso(Long idPatronCaso);
}
