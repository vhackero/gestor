package mx.gob.sedesol.basegestor.model.repositories.badges;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.badges.CatClasificacionBadge;

/**
 * Interfaz repo para CatClasificacionBadge
 * @author nnm_eolf
 *
 */
@Repository
public interface ClasificacionBadgeRepo extends JpaRepository<CatClasificacionBadge, Integer> {

}
