package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoUdRelacionadaV2;

@NoRepositoryBean
public interface TblCasoUdRelacionadaV2Repo extends JpaRepository<TblCasoUdRelacionadaV2, Long> {

    List<TblCasoUdRelacionadaV2> findByIdCaso(Long idCaso);
}
