package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelPersonaResponsabilidades;

@Repository
public interface PersonaResponsabilidadesRepo extends JpaRepository<RelPersonaResponsabilidades, Integer>{
	
	/**
	 * 
	 * @param idTipoResponsabilidad
	 * @return
	 */
	@Query("SELECT r FROM RelPersonaResponsabilidades r "
			+ "join fetch r.tblPersona p "
			+ "join fetch r.catTipoResponsabilidadEc res "
			+ "WHERE r.catTipoResponsabilidadEc.id= :idTipoResponsabilidad")
	public List<RelPersonaResponsabilidades> obtienePersonasPorResponsabilidad(@Param("idTipoResponsabilidad")Integer idTipoResponsabilidad);
	
	
	@Query("SELECT r FROM RelPersonaResponsabilidades r "
			+ "join fetch r.tblPersona p "
			+ "join fetch r.catTipoResponsabilidadEc res "
			+ "WHERE r.catTipoResponsabilidadEc.id IN (:idTipoResponsabilidades)")							 
	public List<RelPersonaResponsabilidades> obtienePersonasPorResponsabilidadList(@Param("idTipoResponsabilidades")List<Integer> idTipoResponsabilidades);
	
}
