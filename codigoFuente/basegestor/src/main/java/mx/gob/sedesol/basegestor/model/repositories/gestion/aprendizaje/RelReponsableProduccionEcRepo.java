package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelReponsableProduccionEc;
@Repository
public interface  RelReponsableProduccionEcRepo extends JpaRepository<RelReponsableProduccionEc, Integer>, JpaSpecificationExecutor<RelReponsableProduccionEc> {

	
	@Query("SELECT r FROM RelReponsableProduccionEc r "
			+ " JOIN FETCH r.personaResponsabilidad pr "  
			+ " JOIN FETCH pr.tblPersona p"
			+ " JOIN FETCH pr.catTipoResponsabilidadEc res"
			+ " WHERE r.idEvtCapacitacion = :idEvento"
			+ "      AND pr.catTipoResponsabilidadEc.id = :idTipoResponsabilidad")
	

	public List<RelReponsableProduccionEc> getResponsableDelEvento(@Param("idEvento")int idEvento
	    														 , @Param("idTipoResponsabilidad")int idTipoResponsabilidad);
	
	
	@Query("SELECT r FROM RelReponsableProduccionEc r "  
			+ " WHERE r.idEvtCapacitacion = :idEvento")
	public List<RelReponsableProduccionEc> getResponsableDelEvento(@Param("idEvento")Integer idEvento);
}
