package mx.gob.sedesol.basegestor.model.repositories.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.admin.CatParametroWSMoodle;

/**
 * Interfaz repo para CatParametroWSMoodle
 * @author eolf
 *
 */
@Repository
public interface ParametroWSMoodleRepo extends JpaRepository<CatParametroWSMoodle, Integer> {

}
