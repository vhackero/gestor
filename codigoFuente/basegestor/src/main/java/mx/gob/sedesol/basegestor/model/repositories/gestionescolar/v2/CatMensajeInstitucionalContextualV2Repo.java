package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatMensajeInstitucionalContextualV2;

@Repository
public interface CatMensajeInstitucionalContextualV2Repo extends JpaRepository<CatMensajeInstitucionalContextualV2, Long> {

    CatMensajeInstitucionalContextualV2 findByClave(String clave);

    List<CatMensajeInstitucionalContextualV2> findByPerfilAndPeriodoOperativo(String perfil, String periodoOperativo);
}
