package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoSimilarV2;

@NoRepositoryBean
public interface TblCasoSimilarV2Repo extends JpaRepository<TblCasoSimilarV2, Long> {

    List<TblCasoSimilarV2> findByIdCasoOrigen(Long idCasoOrigen);
}
