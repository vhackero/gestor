package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatPeriodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodoRepo extends JpaRepository<CatPeriodo, Integer>, JpaSpecificationExecutor<CatPeriodo> {

	@Override
	@Query("SELECT p FROM CatPeriodo p ORDER BY p.nombre")
	List<CatPeriodo> findAll();
}
