/**
 * 
 */
package mx.gob.sedesol.basegestor.model.repositories.encuestas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import mx.gob.sedesol.basegestor.model.entities.encuestas.TblPreguntasEncuesta;

/**
 * @author jhcortes
 *
 */
public interface PreguntaEncuestaRepo extends JpaRepository<TblPreguntasEncuesta, Long>, JpaSpecificationExecutor<TblPreguntasEncuesta>{

}
