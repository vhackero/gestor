package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;

@Repository
public interface EventoCapacitacionRepo  extends JpaRepository<TblEvento, Integer>, JpaSpecificationExecutor<TblEvento> { 
	
	
	@Query("SELECT evt FROM TblEvento evt WHERE evt.catEstadoEventoCapacitacion.id = :idEstatus")
	public List<TblEvento> consultaEventoPorEstatus(@Param("idEstatus")Integer idEstatus);
	
	
	@Query("SELECT eve FROM TblEvento eve "
			+ " WHERE "
			+ "     eve.id = :idEvento ")
	public TblEvento getById(@Param("idEvento")Integer idEvento);	
	
	@Query("SELECT e FROM TblEvento e, TblAmbienteVirtualAprendizaje ava "
			+ "WHERE e.id = ava.eventoCapacitacion.id "
			+ "AND e.idPrograma = :idPrograma "
			+ "AND ava.plataformaMoodle.id = :idPlataforma "
			+ "AND e.catEstadoEventoCapacitacion.id = 3") // estatus concluido
	public List<TblEvento> obtenerEventosConcluidos(@Param("idPrograma") Integer idPrograma,
			@Param("idPlataforma") Integer idPlataforma);


	@Query("SELECT e FROM TblEvento e"
			+ " JOIN e.catEstadoEventoCapacitacion   estadoEvtCap"
			+ " WHERE estadoEvtCap .id =:idEstatusEvtCap") // estatus concluido
	public List<TblEvento> obtenerEventosCapacitacionPorEstatus(@Param("idEstatusEvtCap") Integer idEstatusEvtCap);


	@Query("SELECT evt FROM TblEvento evt WHERE evt.privado = :isPrivado")
	public List<TblEvento> consultaEventoPorTipoVisibilidad(@Param("isPrivado")boolean isPrivado);


	@Query("SELECT evt FROM TblEvento evt WHERE evt.privado = 0 AND evt.catEstadoEventoCapacitacion.id = 3")
	public List<TblEvento> obtenerEventosPublicosEnEjec();
	
	@Query("SELECT evt FROM TblEvento evt WHERE evt.catEstadoEventoCapacitacion=2 AND evt.idPrograma = :idPrograma")
	public List<TblEvento> obtenerEventosPorPrograma(@Param("idPrograma") Integer idPrograma);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE TblEvento e SET e.catEstadoEventoCapacitacion.id = ?1 WHERE e.id = ?2 ")
	public void cambiarEstatusEvento(Integer idEstatus, Integer idEvento);
	

}
