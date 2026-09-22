package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoPatronAplicadoV2;

@Repository
public interface TblCasoPatronAplicadoV2Repo extends JpaRepository<TblCasoPatronAplicadoV2, Long> {

    TblCasoPatronAplicadoV2 findByIdCasoAndIdPatronCasoAndPerfilAndPeriodoOperativoAndHuellaEvaluacion(
            Long idCaso, Long idPatronCaso, String perfil, String periodoOperativo, String huellaEvaluacion);
}
