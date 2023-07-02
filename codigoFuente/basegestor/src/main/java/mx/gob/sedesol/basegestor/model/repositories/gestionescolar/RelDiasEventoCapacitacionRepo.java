package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelDiasEventoCapacitacion;


@Repository
public interface RelDiasEventoCapacitacionRepo extends JpaRepository<RelDiasEventoCapacitacion, Integer>, JpaSpecificationExecutor<RelDiasEventoCapacitacion> { 
	
	@Query("SELECT dias FROM RelDiasEventoCapacitacion dias WHERE dias.grupo.id = :idGrupo ORDER BY dias.fechaEventoCapacitacion" )
	public List<RelDiasEventoCapacitacion> getDiasEventoByGrupo(@Param("idGrupo")Integer idGrupo);
	
	
			
	  @Query("SELECT new mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO( evt.id, COUNT(dias) )"
    			+ " FROM RelDiasEventoCapacitacion dias, RelGrupoParticipante gpoParticipante"
    			+ " JOIN dias.grupo gpo "			    			    		
    			+ " JOIN gpo.evento evt "
    			+ " WHERE gpo.idGrupo = gpoParticipante.grupo.idGrupo "
    			+ "	and evt.id IN (:idEventoCapacitacionList)"
    			+ " GROUP BY evt.id")
	public List<GroupByGestionEscolarDTO> obtenerDiasEventoAgrupadoPorEvento(
			@Param("idEventoCapacitacionList")List<Integer> idEventoCapacitacionList);

	
	

}
