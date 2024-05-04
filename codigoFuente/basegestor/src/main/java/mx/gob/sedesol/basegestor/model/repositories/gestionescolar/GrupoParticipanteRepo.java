package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoParticipante;



@Repository
public interface GrupoParticipanteRepo extends JpaRepository<RelGrupoParticipante, Integer>, JpaSpecificationExecutor<RelGrupoParticipante> { 
	  
	@Query("SELECT gp FROM RelGrupoParticipante gp "
			+ " JOIN FETCH gp.grupo g "
			+ " JOIN FETCH g.evento e "
			+ " WHERE gp.persona.idPersona = :idPersona "
			+ " AND g.actaCerrada = 1 ")
	List<RelGrupoParticipante> getParticipantePorIdActaCerrada(@Param("idPersona") Long idPersona);
	
	
	@Query("SELECT gp FROM RelGrupoParticipante gp "
			+ " JOIN FETCH gp.grupo g "
			+ " JOIN FETCH g.evento e "
			+ " WHERE gp.persona.idPersona = :idPersona "
			+ " AND g.actaCerrada = 1 "
			+ " AND e.constancia = 1 ")
	List<RelGrupoParticipante> getParticipanteByActaCerradaYconstancia(@Param("idPersona") Long idPersona);
	
	
	@Query("SELECT gp FROM RelGrupoParticipante gp "
			+ " JOIN FETCH gp.grupo g "
			+ " JOIN FETCH g.evento e "
			+ " WHERE g.idGrupo = :idGrupo "
			+ " AND g.actaCerrada = 1 "
			+ " AND e.constancia = 1 ")
	List<RelGrupoParticipante> getParticipantesByGrupoConActaCerradayConstancia(@Param("idGrupo") Integer idGrupo);
	
	@Query("SELECT gp FROM RelGrupoParticipante gp "
			+ " JOIN FETCH gp.persona p "
			+ "WHERE gp.grupo.idGrupo = :idGrupo")
	List<RelGrupoParticipante> getParticipantesByGrupo(@Param("idGrupo")Integer idGrupo);
	
	@Query("SELECT gp FROM RelGrupoParticipante gp "
			+ " JOIN FETCH gp.persona p "
			+ "WHERE gp.grupo.evento.idEvento = :idEvento")
	List<RelGrupoParticipante> getParticipantesByEvento(@Param("idEvento")Integer idEvento);
	
	
	@Query("SELECT gp FROM RelGrupoParticipante gp "
			+ "JOIN FETCH gp.persona p "
			+ "JOIN FETCH gp.grupo grupo "
			+ "JOIN FETCH grupo.evento evento "
			+ "WHERE evento.idEvento = :idEvento AND p.idPersona = :idPersona")	
	List<RelGrupoParticipante> buscarParticipanteEnEvento(@Param("idEvento")Integer idEvento, @Param("idPersona")Long idPersona);
	
	@Query("SELECT gp FROM RelGrupoParticipante gp "
			+ " JOIN FETCH gp.persona p "
			+ " WHERE  gp.grupo.idGrupo = :idGrupo"
			+ "   AND  CONCAT( UPPER(gp.persona.nombre), ' ',UPPER( gp.persona.apellidoPaterno), ' ',UPPER(gp.persona.apellidoMaterno)) "
			+ " LIKE  CONCAT('%',UPPER(:nombre), '%') ")
	List<RelGrupoParticipante> getParticipantesByNombre(@Param("idGrupo")Integer idGrupo, @Param("nombre")String nombre);
	
	@Query("SELECT new mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO( evt.id, COUNT(evt)) "
			+ " FROM  RelGrupoParticipante gpoParticipante "
			+ " JOIN gpoParticipante.grupo gpo"
			+ " JOIN gpo.evento  evt"
			+ " JOIN evt.fichaDescriptivaPrograma programa"
			+ " WHERE "			
			+ " evt.id IN (:idEventosList)"
			+ " GROUP BY evt.id")		
	public List<GroupByGestionEscolarDTO> obtenerParticipantesPorEventosAgrupados(@Param("idEventosList")List<Integer> idEventosList);
	

	@Query("SELECT new mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO(evt.id,persona.genero, COUNT(persona)) "
			+ " FROM  RelGrupoParticipante gpoParticipante "
			+ " JOIN gpoParticipante.grupo gpo"
			+ " JOIN gpo.evento  evt"
			+ " JOIN evt.fichaDescriptivaPrograma programa"
			+ " JOIN gpoParticipante.persona persona"
			+ " WHERE "			
			+ " evt.id IN (:idEventosList)"
			+ " GROUP BY persona.genero ,evt.id"
			+ " ORDER BY evt.id")	
	public List<GroupByGestionEscolarDTO> obtenerGeneroParticipantesPorIdEventoCapacitacion(@Param("idEventosList")List<Integer> idEventosList);
	
	@Query("SELECT gpoParticipante FROM RelGrupoParticipante gpoParticipante "
			+ " JOIN gpoParticipante.grupo gpo"
			+ " JOIN gpoParticipante.persona persona"
			+ " JOIN gpo.evento evt"
			+ " JOIN evt.catEstadoEventoCapacitacion edoEvtCap"
			+ " WHERE "
			+ " edoEvtCap.id =:idEstatusEc "
			+ " AND persona.idPersona =:idParticipante ")
	public List<RelGrupoParticipante> obtenerEventosCapacitacionPorIdParticipante(
					@Param("idParticipante")Long idParticipante,@Param("idEstatusEc")Integer idEstatusEc);
	
	
	@Query("SELECT gpoParticipante FROM RelGrupoParticipante gpoParticipante "
			+ " JOIN gpoParticipante.grupo gpo"
			+ " JOIN gpoParticipante.persona persona"
			+ " JOIN gpo.evento evt"
			+ " JOIN evt.catEstadoEventoCapacitacion edoEvtCap"
			+ " WHERE "
			+ " edoEvtCap.id =:idEstatusEc "
			+ " AND persona.idPersona =:idParticipante AND gpo.actaCerrada = 0")
	public List<RelGrupoParticipante> obtenerEventosCapacitacionPorIdParticipante2(
					@Param("idParticipante")Long idParticipante,@Param("idEstatusEc")Integer idEstatusEc);
		
	
	@Query("SELECT gpoParticipante FROM RelGrupoParticipante gpoParticipante "
			+ " JOIN gpoParticipante.grupo gpo"
			+ " JOIN gpoParticipante.persona persona"
			+ " JOIN gpo.evento evt"
			+ " JOIN evt.catEstadoEventoCapacitacion edoEvtCap"
			+ " JOIN evt.catModalidadPlanPrograma evtModalidad"
			+ " WHERE "
			+ " edoEvtCap.id =:idEstatusEc "
			+ " AND persona.idPersona =:idParticipante "
			+ " AND (evtModalidad.id = 1 OR evtModalidad.id = 3)")
	public List<RelGrupoParticipante> obtenEvtsEnLineayMixtosPorIdParticipante(
					@Param("idParticipante")Long idParticipante,@Param("idEstatusEc")Integer idEstatusEc);
	
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE RelGrupoParticipante rgp "
			+ " SET rgp.califTotal = null, rgp.porcentajeAsist = null, rgp.califFinal = null  " 
			+ " WHERE rgp.grupo.idGrupo = :idGrupo ")
	public void actualizarRelGrupoParticipanteByGrupo(@Param("idGrupo")Integer idGrupo);
	
}
