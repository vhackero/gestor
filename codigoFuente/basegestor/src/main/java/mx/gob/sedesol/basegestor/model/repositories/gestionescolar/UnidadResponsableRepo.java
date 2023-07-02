package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatUnidadResponsable;

@Repository
public interface UnidadResponsableRepo extends JpaRepository<CatUnidadResponsable, Integer> {

}
