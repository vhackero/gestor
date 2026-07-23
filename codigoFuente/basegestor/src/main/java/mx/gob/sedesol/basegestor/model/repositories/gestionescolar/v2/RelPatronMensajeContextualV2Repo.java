package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.RelPatronMensajeContextualV2;

@Repository
public interface RelPatronMensajeContextualV2Repo extends JpaRepository<RelPatronMensajeContextualV2, Long> {

    List<RelPatronMensajeContextualV2> findByIdPatronCasoAndPerfil(Long idPatronCaso, String perfil);
}
