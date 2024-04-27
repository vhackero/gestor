package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelEvaluacionCalificacionDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelGrupoEvaluacion;

@Repository
public interface RelGpoEvaluacionRepo extends JpaRepository<RelGrupoEvaluacion, Integer> {

	@Query("SELECT rge FROM RelGrupoEvaluacion rge WHERE  rge.tblGrupo.idGrupo = :idGpo")
	List<RelGrupoEvaluacion> obtieneEvaluacionesPorIdGrupo(@Param("idGpo") Integer idGpo);
	
	@Query("SELECT rec FROM RelEvaluacionCalificacion rec WHERE  rec.relGrupoEvaluacion.idGpoEvaluacion = :idGpoEval")
	public List<RelEvaluacionCalificacionDTO> obtieneEvaluacionesByIdGpoEval(@Param("idGpoEval") Integer idGpoEval);
	
	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM RelEvaluacionCalificacion rec WHERE rec.relGrupoEvaluacion.idGpoEvaluacion = :idGpoEval")
	public void eliminaEvaluacionesByIdGpoEvaluacion(@Param("idGpoEval") Integer idGpoEval);
	
	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM RelGrupoEvaluacion rge WHERE rge.tblGrupo.idGrupo = :idGrupo")
	public void eliminaGrupoEvaluacionByIdGrupo(@Param("idGrupo") Integer idGrupo);

	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM rel_evaluacion_calificacion rec"
			+ "		WHERE rec.id_eval_calificacion in ( SELECT id_eval_calificacion "
			+ "       FROM ( SELECT DISTINCT(ec.id_eval_calificacion) "
			+ "			FROM rel_evaluacion_calificacion ec "
			+ "			inner JOIN rel_grupo_evaluacion ge ON ec.id_gpo_evaluacion = ge.id_gpo_evaluacion"
			+ "			WHERE ge.id_grupo = :idGrupo)AS temporal )", nativeQuery = true)
	public void eliminaEvaluacionesByIdGrupo(@Param("idGrupo") Integer idGrupo);

}
