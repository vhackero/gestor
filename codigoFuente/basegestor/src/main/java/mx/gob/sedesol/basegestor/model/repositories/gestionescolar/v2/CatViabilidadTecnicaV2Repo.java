package mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatViabilidadTecnicaV2;

@Repository
public interface CatViabilidadTecnicaV2Repo extends JpaRepository<CatViabilidadTecnicaV2, Long> {

    CatViabilidadTecnicaV2 findByClave(String clave);
}
