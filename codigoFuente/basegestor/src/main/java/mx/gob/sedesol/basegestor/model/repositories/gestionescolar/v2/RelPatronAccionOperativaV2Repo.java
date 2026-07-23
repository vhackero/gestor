package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.RelPatronAccionOperativaV2;

@Repository
public interface RelPatronAccionOperativaV2Repo extends JpaRepository<RelPatronAccionOperativaV2, Long> {

    List<RelPatronAccionOperativaV2> findByIdPatronCaso(Long idPatronCaso);
}
