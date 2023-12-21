package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelMallaPlan;

public interface MallaPlanRepo extends JpaRepository<RelMallaPlan,Integer> {
	
	/**
	 * 
	 * @param idMallaCurricular
	 * @return
	 */
	@Query("SELECT r FROM RelMallaPlan r "
			+ "WHERE r.idPlan= :idPlanBuscar")
	public RelMallaPlan findByIdPlan(@Param("idPlanBuscar") Integer idPlan);
	
}
