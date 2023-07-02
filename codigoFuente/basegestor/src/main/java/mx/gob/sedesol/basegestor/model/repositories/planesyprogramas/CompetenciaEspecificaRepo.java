package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatCompetenciaEspecifica;

@Repository
public interface CompetenciaEspecificaRepo extends JpaRepository<CatCompetenciaEspecifica, Integer> {
	/**
	 * 
	 * @param id
	 * @param nombre
	 * @return
	 */
	@Query("SELECT c FROM CatCompetenciaEspecifica c WHERE c.nombre= :nombre and c.id<> :id")
	public List<CatCompetenciaEspecifica> estaVacio(@Param("id") Integer id,@Param("nombre")String nombre);
}