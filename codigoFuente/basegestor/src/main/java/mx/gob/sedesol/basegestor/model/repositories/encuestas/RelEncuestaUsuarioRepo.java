package mx.gob.sedesol.basegestor.model.repositories.encuestas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.encuestas.RelEncuestaUsuario;

@Repository
public interface RelEncuestaUsuarioRepo extends JpaRepository<RelEncuestaUsuario, Integer>, JpaSpecificationExecutor<RelEncuestaUsuario>
{
	@Query("SELECT eu from RelEncuestaUsuario eu  "+
			   " JOIN FETCH eu.relGrupoParticipante relGpoParticipante "+
			   " JOIN FETCH relGpoParticipante.persona per"+
			   " JOIN FETCH eu.tblEncuesta e"+			   
			   " JOIN FETCH e.encuestaTipo et" +
			   " JOIN FETCH relGpoParticipante.grupo gpo" +			   
			   " JOIN FETCH gpo.evento ev" +
			   " WHERE " +
			   " eu.activo =:esActivo"+
			   " AND ev.id =:clEvento "+
			   " AND per.idPersona =:clPersona")
	List<RelEncuestaUsuario> consultarEncuestasAsignadas(
			@Param("clEvento") Integer clEvento,
			@Param("clPersona") Long clPersona,
			@Param("esActivo")Boolean esActivo);

	@Query("SELECT eu from RelEncuestaUsuario eu  "+
			   " JOIN FETCH eu.relGrupoParticipante relGpoParticipante "+
			   " JOIN FETCH relGpoParticipante.persona per"+
			   " JOIN FETCH eu.tblEncuesta e"+			   
			   " JOIN FETCH e.encuestaTipo et" +
			   " JOIN FETCH relGpoParticipante.grupo gpo" +			   
			   " JOIN FETCH gpo.evento ev" +
			   " WHERE " +
			   " eu.activo =:esActivo"+
			   " AND per.idPersona =:clPersona")
	List<RelEncuestaUsuario> consultarEncuestasAsignadas(
			@Param("clPersona") Long clPersona,
			@Param("esActivo")Boolean esActivo);
	
	
	
	@Query("SELECT eu from RelEncuestaUsuario eu  "+
			   " JOIN FETCH eu.relGrupoParticipante relGpoParticipante "+
			   " JOIN FETCH relGpoParticipante.persona per"+
			   " JOIN FETCH eu.tblEncuesta e"+			   
			   " JOIN FETCH e.encuestaTipo et" +
			   " JOIN FETCH relGpoParticipante.grupo gpo" +			   
			   " JOIN FETCH gpo.evento ev" +
			   " WHERE " +
			   " eu.activo =:esActivo"+
			   " AND ev.id =:clEvento "+
			   " AND per.idPersona =:clPersona"+
			   " AND et.id =:idEncuestaTipo")
	List<RelEncuestaUsuario> consultarEncuestasAsignadas(
			@Param("clEvento") Integer clEvento,
			@Param("clPersona") Long clPersona,
			@Param("idEncuestaTipo")Integer idEncuestaTipo,
			@Param("esActivo")Boolean esActivo);
	
	
	
	@Query("SELECT eu from RelEncuestaUsuario eu  "+
			   " JOIN FETCH eu.relGrupoParticipante relGpoParticipante "+
			   " JOIN FETCH relGpoParticipante.persona per"+
			   " JOIN FETCH eu.tblEncuesta e"+			   
			   " JOIN FETCH e.encuestaTipo et" +
			   " JOIN FETCH relGpoParticipante.grupo gpo" +			   
			   " JOIN FETCH gpo.evento ev" +
			   " WHERE " +
			   " eu.activo =:esActivo"+
			   " AND ev.id IN(:clEventoList) "+
			   " AND per.idPersona =:clPersona"+
			   " AND et.id =:idEncuestaTipo")
	List<RelEncuestaUsuario> consultarEncuestasAsignadas(
			@Param("clEventoList") 	List<Integer> clEventoList,
			@Param("clPersona") Long clPersona,
			@Param("idEncuestaTipo")Integer idEncuestaTipo,
			@Param("esActivo")Boolean esActivo);
	
	@Query("SELECT eu from RelEncuestaUsuario eu  "+
			   " JOIN FETCH eu.relGrupoParticipante relGpoParticipante "+
			   " JOIN FETCH relGpoParticipante.persona per"+
			   " JOIN FETCH eu.tblEncuesta e"+			   
			   " JOIN FETCH e.encuestaTipo et" +
			   " JOIN FETCH relGpoParticipante.grupo gpo" +			   
			   " JOIN FETCH gpo.evento ev" +
			   " WHERE" +
			   " ev.id IN(:clEventoList) "+
			   " AND per.idPersona =:clPersona"+
			   " AND et.id =:idEncuestaTipo")
	List<RelEncuestaUsuario> consultarEncuestasAsignadasTodas(
			@Param("clEventoList") 	List<Integer> clEventoList,
			@Param("clPersona") Long clPersona,
			@Param("idEncuestaTipo")Integer idEncuestaTipo);
	
	
	
	@Query("SELECT eu from RelEncuestaUsuario eu  "+
			   " JOIN FETCH eu.relGrupoParticipante relGpoParticipante "+
			   " JOIN FETCH relGpoParticipante.persona per"+
			   " JOIN FETCH eu.tblEncuesta e"+			   
			   " JOIN FETCH e.encuestaTipo et" +
			   " JOIN FETCH relGpoParticipante.grupo gpo" +			   
			   " JOIN FETCH gpo.evento ev" +
			   " WHERE " +
			   " eu.activo =:esActivo"+
			   " AND ev.id IN(:clEventoList) "+
			   " AND per.idPersona =:clPersona")
	List<RelEncuestaUsuario> consultarEncuestasAsignadas(
			@Param("clEventoList") 	List<Integer> clEventoList,
			@Param("clPersona") Long clPersona,			
			@Param("esActivo")Boolean esActivo);
	
	@Query("SELECT eu from RelEncuestaUsuario eu" +
		   " JOIN FETCH eu.relGrupoParticipante relGpoParticipante"+
		   " JOIN FETCH relGpoParticipante.grupo grupo"+
		   " JOIN FETCH grupo.evento eve"+
		   " JOIN FETCH eve.catEstadoEventoCapacitacion cec"+
		   " JOIN FETCH eu.tblEncuesta enc"+
		   " JOIN FETCH enc.encuestaTipo et"+
		   " WHERE "+
		   " cec.id=3"+
		   " AND et.id =1"+
		   " AND eve.idPrograma =:idPrograma")
	List<RelEncuestaUsuario> obtenerParticipantesEncuestasPorPrograma(@Param("idPrograma") Integer idPrograma);
}
