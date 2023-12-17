package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoCompetencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoCompetenciaRepo extends JpaRepository<CatTipoCompetencia, Integer>, JpaSpecificationExecutor<CatTipoCompetencia> {

	@Override
	@Query("SELECT c FROM CatTipoCompetencia c ORDER BY c.idTpoCompetencia")
	List<CatTipoCompetencia> findAll();
}
