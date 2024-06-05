package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoParticipante2;

@Repository
public interface RelGrupoParticipante2Repo extends JpaRepository<RelGrupoParticipante2, Integer>{

}
