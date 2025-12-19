package mx.gob.sedesol.basegestor.model.repositories.inscripcion;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.PlanProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ProgramaProcesoInscripcionDTO;

@Repository
public class PlanProcesoInscripcionRepository implements IPlanProcesoInscripcionRepository {

	@Autowired
	public EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanProcesoInscripcionDTO> obtenerPlanesPorIdProcesoInscripcion(Long idProcesoInscripcion) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT ");
		sql.append("    rpip.id_plan, ");
		sql.append("    tp.nombre ");
		sql.append("FROM rel_proceso_inscipcion_planesyprogramas rpip ");
		sql.append("JOIN tbl_planes tp ON rpip.id_plan = tp.id_plan ");
		sql.append("WHERE rpip.id_proceso_inscripcion = :idProcesoInscripcion");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString())
				.setParameter("idProcesoInscripcion", idProcesoInscripcion).getResultList();

		return resultados.stream().map(this::mapearPlan).collect(Collectors.toList());
	}

	private PlanProcesoInscripcionDTO mapearPlan(Object[] row) {
		PlanProcesoInscripcionDTO dto = new PlanProcesoInscripcionDTO();
		dto.setIdPlan(getLongValue(row[0])); // id_plan
		dto.setNombrePlan((String) row[1]); // nombre del plan
		return dto;
	}

	private Long getLongValue(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return ((Number) value).longValue();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProgramaProcesoInscripcionDTO> obtenerProgramasPorPlanYProcesoInscripcion(Long idPlan,
			Long idProcesoInscripcion) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    rpip.id_programa, ");
		sql.append("    tfdp.nombre_tentativo AS nombre ");
		sql.append("FROM rel_proceso_inscipcion_planesyprogramas rpip ");
		sql.append("JOIN tbl_planes tp ON rpip.id_plan = tp.id_plan ");
		sql.append("JOIN tbl_ficha_descriptiva_programa tfdp ");
		sql.append("    ON tp.id_plan = tfdp.id_plan ");
		sql.append("   AND rpip.id_programa = tfdp.id_programa ");
		sql.append("WHERE rpip.id_proceso_inscripcion = :idProcesoInscripcion ");
		sql.append("  AND rpip.id_plan = :idPlan");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString())
				.setParameter("idProcesoInscripcion", idProcesoInscripcion).setParameter("idPlan", idPlan)
				.getResultList();

		return resultados.stream().map(this::mapearProgramaProcesoInscripcion).collect(Collectors.toList());
	}

	private ProgramaProcesoInscripcionDTO mapearProgramaProcesoInscripcion(Object[] row) {
		ProgramaProcesoInscripcionDTO dto = new ProgramaProcesoInscripcionDTO();
		dto.setIdPrograma(getLongValue(row[0])); // id_programa
		dto.setNombrePrograma((String) row[1]); // nombre_tentativo
		return dto;
	}

}
