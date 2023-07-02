package mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatRecursosInfraestructura;

@Repository
public interface RecursosInfraestructuraRepo extends JpaRepository<CatRecursosInfraestructura, Integer> {

	/**
	 * 
	 * @param idTipoRecurso
	 * @return
	 */
	@Query("SELECT c FROM CatRecursosInfraestructura c "
			+ "join fetch c.catTipoRecurso t "
			+ "WHERE c.catTipoRecurso.id= :idTipoRecurso")
	public List<CatRecursosInfraestructura> obtieneRecursosPorTipoDeRecurso(@Param("idTipoRecurso")Integer idTipoRecurso);
	
}
