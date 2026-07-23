package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoEvidenciaV2;

@Repository
public interface TblCasoEvidenciaV2Repo extends JpaRepository<TblCasoEvidenciaV2, Long> {

    List<TblCasoEvidenciaV2> findByIdCaso(Long idCaso);

    TblCasoEvidenciaV2 findByIdAndIdCaso(Long id, Long idCaso);
}
