package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoDiagnosticoV2;

@Repository
public interface TblCasoDiagnosticoV2Repo extends JpaRepository<TblCasoDiagnosticoV2, Long> {

    TblCasoDiagnosticoV2 findByIdCaso(Long idCaso);
}
