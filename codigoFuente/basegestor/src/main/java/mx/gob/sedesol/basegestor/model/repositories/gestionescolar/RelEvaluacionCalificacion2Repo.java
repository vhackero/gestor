package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelEvaluacionCalificacion2;

@Repository
public interface RelEvaluacionCalificacion2Repo extends JpaRepository<RelEvaluacionCalificacion2, Integer>{

}
