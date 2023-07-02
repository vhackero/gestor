package mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelReponsableProduccionOa;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoResponsabilidadEc;


@Repository
public interface ReponsableProduccionOaRepo extends JpaRepository<RelReponsableProduccionOa,Integer>  {

	/**
	 * Query que busca reposnsables de produccion de los objetos de aprendizaje por una lista
	 * de id de unidad oas
	 * @param idsUnidadOa
	 * @return
	 */
	@Query("SELECT responsables FROM RelReponsableProduccionOa responsables "
			+ "WHERE responsables.idUnidadOa in (:idsUnidadOa)")
	public List<RelReponsableProduccionOa> findByIds(@Param("idsUnidadOa") List<Integer> idsUnidadOa);
	
	
	/**
	 * Query que busca reposnsables de produccion de los objetos de aprendizaje por 
	 * id de unidad oa
	 * @param idsUnidadOa
	 * @return
	 */
	public List<RelReponsableProduccionOa> findByIdUnidadOa(@Param("idUnidadOa") Integer idUnidadOa);
	
	
	
	@Query("SELECT responsables FROM  RelReponsableProduccionOa responsables "
	+ " JOIN FETCH responsables.personaResponsabilidade personaresponsable"
	+ " JOIN FETCH responsables.unidadOaAva ava"	
	+ "	JOIN FETCH personaresponsable.tblPersona persona"
	+ "	JOIN FETCH persona.nacionalidad"
	+ "	JOIN FETCH personaresponsable.catTipoResponsabilidadEc tporesp "	
	+ "  WHERE tporesp =:tipoResponsabilidad")
	public List<RelReponsableProduccionOa> findByTipoResponsabilidad(@Param("tipoResponsabilidad")
		CatTipoResponsabilidadEc catTipoResponsabilidadEc );
		
		

	@Query("SELECT responsables FROM  RelReponsableProduccionOa responsables "	
			+ " JOIN FETCH responsables.personaResponsabilidade personaresponsable"
			+ " JOIN FETCH responsables.unidadOaAva ava"	
			+ "	JOIN FETCH personaresponsable.tblPersona persona"
			+ "	JOIN FETCH persona.nacionalidad"
			+ "	JOIN FETCH personaresponsable.catTipoResponsabilidadEc tporesp "	
			+ "  WHERE tporesp.id IN (:idTipoResposabilidades)")
	public List<RelReponsableProduccionOa> findByTipoResponsabilidades(@Param("idTipoResposabilidades")
	List<Integer> idTipoResposabilidades);
	
	
}



	
	
