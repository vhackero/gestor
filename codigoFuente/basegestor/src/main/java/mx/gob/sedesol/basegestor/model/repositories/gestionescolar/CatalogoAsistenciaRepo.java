package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.io.Serializable;

import mx.gob.sedesol.basegestor.model.entities.encuestas.TblEncuesta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatAsistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

/**
 * Created by jhcortes on 31/01/17.
 */
@Repository
public interface CatalogoAsistenciaRepo extends JpaRepository<CatAsistencia, Integer>, JpaSpecificationExecutor<CatAsistencia> {

}
