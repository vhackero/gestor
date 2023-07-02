package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelAsistencia;


@Repository
public interface RelAsistenciaRepo extends JpaRepository<RelAsistencia, Integer>, JpaSpecificationExecutor<RelAsistencia> { 
	
	@Query("SELECT asis FROM RelAsistencia asis WHERE asis.idGrupoParticipante IN (:listIdGrupoParticipante)")
	public List<RelAsistencia> getAsistenciaByGrupoParticipante(@Param("listIdGrupoParticipante")List<Integer> lisIdGrupoParticipante);


	@Query("SELECT new mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO(evt.id,COUNT(asistencia)) "
			+ " FROM RelAsistencia asistencia "
			+ " JOIN asistencia.catAsistencia tpoAsistencia"
			+ " JOIN asistencia.diasEventoCapacitacion diasEventoCap"
			+ " JOIN diasEventoCap.grupo   gpo"
			+ " JOIN gpo.evento evt"
			+ " WHERE "
			+ " tpoAsistencia.id =:idTipoAsistencia "
			+ " AND evt.id IN (:idEventoCapacitacionList)"
			+ " GROUP BY evt.id")
	public List<GroupByGestionEscolarDTO> obtenerAsistenciaAgrupadaPorEventoCapacitacion(@Param("idEventoCapacitacionList")List<Integer> 
		idEventoCapacitacionList,@Param("idTipoAsistencia")Integer idTipoAsistencia);
	
	
}
