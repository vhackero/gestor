package mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.RelPersonalizacionRecurso;

@Repository
public interface PersonalizacionRecursoRepo extends JpaRepository<RelPersonalizacionRecurso,Integer> {

	@Query("SELECT rpa FROM RelPersonalizacionRecurso rpa WHERE rpa.idPersonalizacionArea = :idPersonalizacion " )
	public List<RelPersonalizacionRecurso> consultaRelPersonalizacionArea(@Param("idPersonalizacion") Integer idPersonalizacion);
}

