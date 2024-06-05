package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoEvaluacion2;

@Repository
public interface RelGrupoEvaluacion2Repo extends JpaRepository<RelGrupoEvaluacion2, Integer> {

}
