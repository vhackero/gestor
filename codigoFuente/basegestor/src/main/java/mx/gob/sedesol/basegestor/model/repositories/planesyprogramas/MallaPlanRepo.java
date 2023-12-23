package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelMallaPlan;

@Repository
public interface MallaPlanRepo extends JpaRepository<RelMallaPlan,Integer> {
	
	/**
	 * 
	 * @param idPlanBuscar
	 * @return
	 */
	@Query("SELECT r FROM RelMallaPlan r WHERE r.idPlan = :idPlanBuscar")
	public RelMallaPlan findByIdPlan(@Param("idPlanBuscar") Integer idPlanBuscar);
	
	/**
	 * 
	 * @param idBuscar
	 * @return
	 */
	@Query("SELECT r FROM RelMallaPlan r WHERE r.id = :idBuscar")
	public RelMallaPlan findById(@Param("idBuscar") Integer idBuscar);
}
