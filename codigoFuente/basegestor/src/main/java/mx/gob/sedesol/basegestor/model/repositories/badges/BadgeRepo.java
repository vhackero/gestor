package mx.gob.sedesol.basegestor.model.repositories.badges;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.badges.CatBadge;

/**
 * Interfaz repo para CatBadge
 * @author nnm_eolf
 *
 */
@Repository
public interface BadgeRepo extends JpaRepository<CatBadge, Integer> {
	
	@Query("select cb from CatBadge cb where :puntos >= cb.calificacionMinima and :puntos <= cb.calificacionMaxima")
	CatBadge obtenerBadgePorRangoPuntos(@Param("puntos")Integer puntos);

}
