package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelEjeCompetencia;

@Repository
public interface EjeCompetenciaRepo extends JpaRepository<RelEjeCompetencia,Integer> {
	
	/**
	 * 
	 * @param idMallaCurricular
	 * @return
	 */
	@Query("SELECT r FROM RelEjeCompetencia r "
			+ "join fetch r.catCompetenciaEspecifica ce "
			+ "WHERE r.idMallaCurricular= :idMallaCurricular")
	public List<RelEjeCompetencia> obtieneCompetenciasEspecificasPorEje(@Param("idMallaCurricular")Integer idMallaCurricular);
	
	
	
	/**
	 * 
	 * @param idMallaCurricular
	 * @return
	 */
	@Query("SELECT r FROM RelEjeCompetencia r WHERE r.idMallaCurricular<> :idMallaCurricular")
	public List<RelEjeCompetencia> obtieneCompetenciasEspecificasNoAsignadas(@Param("idMallaCurricular")Integer idMallaCurricular);
	
}
