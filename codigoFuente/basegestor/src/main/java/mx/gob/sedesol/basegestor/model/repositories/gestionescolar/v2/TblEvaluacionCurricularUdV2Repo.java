package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblEvaluacionCurricularUdV2;
@Repository
public interface TblEvaluacionCurricularUdV2Repo extends JpaRepository<TblEvaluacionCurricularUdV2, Long> { }
