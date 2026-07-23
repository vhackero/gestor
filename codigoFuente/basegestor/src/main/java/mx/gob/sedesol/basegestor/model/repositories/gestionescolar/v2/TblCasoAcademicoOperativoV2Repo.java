package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblCasoAcademicoOperativoV2;

@Repository
public interface TblCasoAcademicoOperativoV2Repo extends JpaRepository<TblCasoAcademicoOperativoV2, Long> {

    TblCasoAcademicoOperativoV2 findByFolioExterno(String folioExterno);

    List<TblCasoAcademicoOperativoV2> findByIdPersona(Long idPersona);

    TblCasoAcademicoOperativoV2 findTopByIdPersonaOrderByIdDesc(Long idPersona);
}
