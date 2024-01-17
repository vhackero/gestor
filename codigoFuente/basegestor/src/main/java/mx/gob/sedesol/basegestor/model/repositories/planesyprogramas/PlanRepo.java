package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

@Repository
public interface PlanRepo extends JpaRepository<TblPlan, Integer>,JpaSpecificationExecutor<TblPlan> {
	
	@Modifying
	@Query("DELETE FROM RelPlanHabilidad rh WHERE rh.idPlan = :idPlan")
	public void eliminaHabilidadesPorIdPlan(@Param("idPlan") Integer idPlan);
	
	@Modifying
	@Query("DELETE FROM RelPlanConocimiento rh WHERE rh.idPlan = :idPlan")
	public void eliminaConocimientosPorIdPlan(@Param("idPlan") Integer idPlan);
	
	@Modifying
	@Query("DELETE FROM RelPlanAptitud rh WHERE rh.idPlan = :idPlan")
	public void eliminAptitudesPorIdPlan(@Param("idPlan") Integer idPlan);
	
	@Query("select max(p.idPlan) from TblPlan p")
	public Integer obtieneUltimoIdPlanes();
	
	@Query("select p from TblPlan p"
			+ " where p.idPlan in (idPlanes)")
	public List<TblPlan> obtenerPlanesPorIds(@Param("idPlan") List<Integer> idPlanes);
}
