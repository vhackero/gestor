package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblEvaluacionCurricularV2;

@Repository
public interface TblEvaluacionCurricularV2Repo extends JpaRepository<TblEvaluacionCurricularV2, Long> {
    TblEvaluacionCurricularV2 findByIdPersonaAndIdPlanAndVersionReglasAndOrigenConsultaAndHashContexto(
            Long idPersona, Long idPlan, String versionReglas, String origenConsulta, String hashContexto);
}
