package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaPlataformaMoodle;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaPlataformaMoodlePK;

@Repository
public interface RelPersonaPlataformaMoodleRepo extends JpaRepository<RelPersonaPlataformaMoodle, RelPersonaPlataformaMoodlePK> {
	
	@Query("SELECT ppm FROM RelPersonaPlataformaMoodle ppm "
			+ " WHERE ppm.idPersona = :idPersona "
			+ " AND ppm.idPlataformaMoodle = :idPlataforma")
	List<RelPersonaPlataformaMoodle> obtenerPorPersonaPlataforma(@Param("idPersona") Long idPersona,
			@Param("idPlataforma") Integer idPlataforma);
	
	
	@Query("SELECT ppm FROM RelPersonaPlataformaMoodle ppm "
			+ " WHERE ppm.idPersonaMoodle = :idPersonaMoodle "
			+ " AND ppm.idPlataformaMoodle = :idPlataforma")
	List<RelPersonaPlataformaMoodle> obtenerPersonaPlataformaByPersonaMoodle(@Param("idPersonaMoodle") Integer idPersonaMoodle,
			@Param("idPlataforma") Integer idPlataforma);

}
