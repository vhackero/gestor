package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblMallaCurricular;

@Repository
public interface MallaCurricularRepo extends JpaRepository<TblMallaCurricular,Integer> {
	
	@Query("select mc from TblMallaCurricular mc where mc.id = :id ")
	public List<TblMallaCurricular> buscarMallaCurricularPorId(@Param("id") Integer id);
	
	@Query("select max(mc.id) from TblMallaCurricular mc ")
	public Integer getMaxIdMallaCurricular();
	
	@Query("select mc from TblMallaCurricular mc where mc.idPlan = :idPlan ")
	public List<TblMallaCurricular> buscarMallaCurricularPorIdPlan(@Param("idPlan") Integer idPlan);
}
