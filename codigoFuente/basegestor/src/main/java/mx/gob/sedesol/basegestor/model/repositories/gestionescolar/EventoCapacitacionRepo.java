package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EncabezadoActaDTO;
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
/*	@Query("SELECT evt FROM TblEvento evt "
			+ " JOIN  evt.inscripciones ins "
			+ " JOIN  ins.fichaDescriptivaPrograma fdp "
			+ " WHERE fdp.nombreTentativo like %:programa%"
			+ " AND ins.plan.idPlan =:idPlan ")
		//	+ " GROUP BY evt.idEvento")
			//+ " AND ( fdp.tipo = 'Optativa' AND "*/
	@Query(value = "SELECT * FROM tbl_eventos e " + 
			" inner join tbl_inscripciones i on e.id_programa = i.idprograma " + 
			" inner join tbl_ficha_descriptiva_programa fdp on fdp.id_programa = e.id_programa " + 
			" WHERE i.idplan =:idPlan and fdp.nombre_tentativo like %:programa% and e.id_estatus_ec = 2" + 		
			" GROUP by e.id_evento " + 
			" ORDER by i.idprograma, e.id_evento", nativeQuery = true)
	public List<TblEvento> obtenerEventosPorIdProgramaIdPlan(@Param("programa") String programa, @Param("idPlan") Integer idPlan);
	
	@Query("SELECT evt FROM TblEvento evt "
			+ " JOIN evt.inscripciones ins "
			+ " WHERE evt.idPrograma = ins.fichaDescriptivaPrograma.idPrograma"
			+ " AND ins.plan.idPlan =:idPlan"
		//	+ " GROUP BY evt.idEvento "
			+ " ORDER BY evt.idPrograma")
	public List<TblEvento> obtenerEventosPorIdProgramaIdPlan( @Param("idPlan") Integer idPlan);
	
	
	@Query(value = "select	rpm.id_persona as idpersona, rpm.id_persona_moodle as personamoodle "
			+ " from rel_personas_plataformas_moodle rpm "
			+ " inner join tbl_persona p on p.id_persona = rpm.id_persona "
			+ " inner join rel_persona_bajas rpb on rpb.id_persona = p.id_persona "
			+ " inner join tbl_eventos e on	e.id_evento = rpb.id_evento "
			+ " where e.id_evento = :idEvento	or e.id_curso_lms_borrador = :idCurso and rpm.id_persona_moodle in :idsMoodle", nativeQuery = true)
	public List<BajasDTO> obtenerBajas(@Param("idEvento")Integer idEvento, @Param("idCurso")Integer idCurso, @Param("idsMoodle")List<Integer> idsMoodle);
	
	@Query(value = "SELECT (SELECT p.sso_idUsuario FROM rel_grupo_participante rgp "
			+ "         INNER JOIN rel_persona_roles rpr ON rpr.id_persona = rgp.id_persona_participante "
			+ "         INNER JOIN tbl_persona p ON rpr.id_persona = p.id_persona AND (p.sso_idUsuario NOT LIKE '%.%' AND p.sso_idUsuario NOT LIKE 'ES%' AND (p.sso_idUsuario LIKE 'DL%' OR p.sso_idUsuario LIKE 'FA%'))        WHERE rgp.id_grupo = tg.id AND rpr.id_rol = 3) as 'matricula' , "
			+ "       (SELECT CONCAT(tp.sso_nombre,' ', tp.sso_apellidoPaterno, ' ',tp.sso_apellidoMaterno ) FROM rel_grupo_participante rgp "
			+ "        INNER JOIN rel_persona_roles rpr ON rpr.id_persona = rgp.id_persona_participante "
			+ "        INNER JOIN tbl_persona tp ON tp.id_persona = rpr.id_persona AND (tp.sso_idUsuario NOT LIKE '%.%' AND tp.sso_idUsuario NOT LIKE 'ES%' AND (tp.sso_idUsuario LIKE 'DL%' OR tp.sso_idUsuario LIKE 'FA%')) "
			+ "        WHERE rgp.id_grupo = tg.id AND rpr.id_rol = 3)as 'docente', "
			+ "       tp.nombre as 'programa', "
			+ "       tp.identificador as 'cveprograma', "
			+ "       tfd.nombre_tentativo as 'asignatura', "
			+ "       cpp.clave_asig as 'cveasignatura', "
			+ "       SUBSTRING_INDEX(tp.identificador, \"-\", -2)  as 'periodo', "
			+ "       CONCAT(te.nombre_ec,' - ',tg.nombre) as 'grupo' "
			+ " FROM tbl_eventos te "
			+ "         INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_programa = te.id_programa "
			+ "         INNER JOIN tbl_planes tp ON tp.id_plan = tfd.id_plan "
			+ "         INNER JOIN tbl_grupos tg ON tg.id_evento = te.id_evento "
			+ "         INNER JOIN cat_nombres_planesyprogramas cpp ON cpp.programa_educativo = tp.nombre AND tfd.nombre_tentativo = cpp.asignatura AND cpp.bloque NOT LIKE 'NA' "
			+ "        WHERE te.id_evento = :idEvento AND tg.id = :idGrupo", nativeQuery = true)
	public List<EncabezadoActaDTO> obtenerEncabezadoActa(@Param("idEvento")Integer idEvento, @Param("idGrupo")Integer idGrupo);

}
