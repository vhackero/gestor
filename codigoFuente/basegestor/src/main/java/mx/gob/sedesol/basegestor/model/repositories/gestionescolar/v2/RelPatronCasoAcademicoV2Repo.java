package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.RelPatronCasoAcademicoV2;

@Repository
public interface RelPatronCasoAcademicoV2Repo extends JpaRepository<RelPatronCasoAcademicoV2, Long> {

    List<RelPatronCasoAcademicoV2> findByIdTipoCaso(Long idTipoCaso);
}
