package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoDictamenV2;

@Repository
public interface TblCasoDictamenV2Repo extends JpaRepository<TblCasoDictamenV2, Long> {

    TblCasoDictamenV2 findByIdCaso(Long idCaso);
}
