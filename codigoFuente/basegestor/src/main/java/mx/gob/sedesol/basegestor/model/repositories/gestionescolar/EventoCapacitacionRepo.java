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
	
	
	/*@Query(value = "SELECT tp.id_plan, tp.nombre, tfdp.id_programa, tfdp.nombre_tentativo, te.id_evento, te.nombre_ec\r\n" + 
			"	FROM tbl_eventos te\r\n" + 
			"	         INNER JOIN tbl_ficha_descriptiva_programa  tfdp ON tfdp.id_programa = te.id_programa\r\n" + 
			"	                  INNER JOIN tbl_planes tp ON tp.id_plan = tfdp.id_plan\r\n" + 
			"	                  INNER JOIN tbl_inscripcion_resumen ir ON tp.nombre = ir.programa_educativo\r\n" + 
			"	WHERE  (\r\n" + 
			"	    (tfdp.tipo = 'Optativa' AND\r\n" + 
			"	     tfdp.nombre_tentativo = REPLACE(REPLACE(REPLACE(CONCAT(ir.asignatura, ' ', ir.bloque), '1', 'I'), '2', 'II'), '3', 'III'))\r\n" + 
			"	        OR (tfdp.tipo != 'Optativa' AND tfdp.nombre_tentativo = ir.asignatura)\r\n" + 
			"	    )\r\n" + 
			"	  and ir.programa_educativo  IN (:programa) AND\r\n" + 
			"	    ir.asignatura  IN (:asignatura)\r\n" + 
			"	  and ir.bloque = 2", nativeQuery = true)
	public List<TblEvento> obtenerEventosPorNombrePrograma(@Param("programa") String programa, @Param("asignatura") String asignatura);*/
	@Query("SELECT evt FROM TblEvento evt "
			+ " JOIN  evt.inscripciones ins "
			+ " JOIN  ins.fichaDescriptivaPrograma fdp "
			+ " WHERE fdp.nombreTentativo like %:programa%"
			+ " AND ins.plan.idPlan =:idPlan ")
		//	+ " GROUP BY evt.idEvento")
			//+ " AND ( fdp.tipo = 'Optativa' AND "
	public List<TblEvento> obtenerEventosPorIdProgramaIdPlan(@Param("programa") String programa, @Param("idPlan") Integer idPlan);
	
	@Query("SELECT evt FROM TblEvento evt "
			+ " JOIN evt.inscripciones ins "
			+ " WHERE evt.idPrograma = ins.fichaDescriptivaPrograma.idPrograma"
			+ " AND ins.plan.idPlan =:idPlan"
		//	+ " GROUP BY evt.idEvento "
			+ " ORDER BY evt.idPrograma")
	public List<TblEvento> obtenerEventosPorIdProgramaIdPlan( @Param("idPlan") Integer idPlan);

}
