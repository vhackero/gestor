package mx.gob.sedesol.basegestor.model.repositories.planesyprogramas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatAmbientesAprendizajeEc;

@Repository
public interface AmbientesAprendizajeRepo extends JpaRepository<CatAmbientesAprendizajeEc, Integer> {

}
