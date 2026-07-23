package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoBitacoraV2;

@Repository
public interface TblCasoBitacoraV2Repo extends JpaRepository<TblCasoBitacoraV2, Long> {

    List<TblCasoBitacoraV2> findByIdCasoOrderByFechaAsc(Long idCaso);

    TblCasoBitacoraV2 findByIdAndIdCaso(Long id, Long idCaso);
}
