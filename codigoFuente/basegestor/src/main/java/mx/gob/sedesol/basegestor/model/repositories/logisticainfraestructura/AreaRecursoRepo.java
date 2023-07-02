package mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaRecursoDTO;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelAreaRecurso;

@Repository
public interface AreaRecursoRepo extends JpaRepository<RelAreaRecurso, Integer> {

	/**
	 * 
	 * @param idArea
	 * @return
	 */
	@Query("SELECT r FROM RelAreaRecurso r "
			+ "join fetch r.catRecursosInfraestructura "
			+ "WHERE r.tblConfiguracionArea.idConfigArea= :idArea")
	public List<RelAreaRecurso> obtieneRecursosPorIdArea(@Param("idArea")Integer idArea);
	
}
