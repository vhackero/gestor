package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblEvaluacionCurricularExcepcionV2;
@Repository
public interface TblEvaluacionCurricularExcepcionV2Repo extends JpaRepository<TblEvaluacionCurricularExcepcionV2, Long> { }
