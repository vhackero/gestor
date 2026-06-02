package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodleEventoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodlePlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodleProgramaDTO;

@Repository
public class ActualizacionCursoMoodleRepository implements IActualizacionCursoMoodleRepository {

	@Autowired
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ActualizacionCursoMoodlePlanDTO> obtenerPlanes() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT tp.id_plan, tp.nombre ");
		sql.append("FROM tbl_planes tp ");
		sql.append("JOIN tbl_malla_curricular tmc ");
		sql.append("  ON tp.id_plan = tmc.id_plan ");
		sql.append(" AND tmc.activo = 1 ");
		sql.append("WHERE tp.id_estatus_plan = 3 ");
		sql.append("  AND EXISTS ( ");
		sql.append("      SELECT 1 ");
		sql.append("      FROM tbl_eventos te ");
		sql.append("      JOIN tbl_ficha_descriptiva_programa tfd ");
		sql.append("        ON te.id_programa = tfd.id_programa ");
		sql.append("      WHERE tfd.id_plan = tp.id_plan ");
		sql.append("  ) ");
		sql.append("ORDER BY tp.nombre ");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString()).getResultList();
		return resultados.stream().map(this::mapearPlan).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActualizacionCursoMoodleProgramaDTO> obtenerProgramasPorPlan(Long idPlanSeleccionado) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT tfdp.id_programa, tfdp.cve_programa, tfdp.nombre_tentativo, tfdp.tipo ");
		sql.append("FROM tbl_ficha_descriptiva_programa tfdp ");
		sql.append("JOIN tbl_planes tp ON tfdp.id_plan = tp.id_plan ");
		sql.append("JOIN tbl_malla_curricular tmc ");
		sql.append("  ON tp.id_plan = tmc.id_plan ");
		sql.append(" AND tmc.activo = 1 ");
		sql.append("WHERE tp.id_estatus_plan = 3 ");
		sql.append("  AND tfdp.id_plan = :idPlanSeleccionado ");
		sql.append("  AND EXISTS ( ");
		sql.append("      SELECT 1 ");
		sql.append("      FROM tbl_eventos te ");
		sql.append("      JOIN tbl_ficha_descriptiva_programa tfd ");
		sql.append("        ON te.id_programa = tfd.id_programa ");
		sql.append("      WHERE tfd.id_plan = tp.id_plan ");
		sql.append("  ) ");
		sql.append("ORDER BY tfdp.nombre_tentativo ");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString())
				.setParameter("idPlanSeleccionado", idPlanSeleccionado).getResultList();
		return resultados.stream().map(this::mapearPrograma).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActualizacionCursoMoodleEventoDTO> buscarEventos(Long idPlanSeleccionado, Long idProgramaSeleccionado,
			String periodo) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT te.id_evento, te.id_curso_lms_borrador, tfdp.id_programa, te.nombre_ec, ");
		sql.append("       tfdp.tipo, COALESCE(tmc2.nombre, '-'), tmc.nombre ");
		sql.append("FROM tbl_eventos te ");
		sql.append("JOIN tbl_ficha_descriptiva_programa tfdp ON tfdp.id_programa = te.id_programa ");
		sql.append("JOIN tbl_malla_curricular tmc ON tmc.id = tfdp.id_eje_capacitacion ");
		sql.append("LEFT JOIN tbl_malla_curricular tmc2 ON tmc2.id = tmc.id_padre ");
		sql.append("WHERE tfdp.id_plan = :idPlanSeleccionado ");
		sql.append("  AND te.id_programa = :idProgramaSeleccionado ");
		sql.append("  AND te.cve_evento_cap LIKE CONCAT('%', :periodo, '%') ");
		sql.append("ORDER BY te.nombre_ec ");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString())
				.setParameter("idPlanSeleccionado", idPlanSeleccionado)
				.setParameter("idProgramaSeleccionado", idProgramaSeleccionado)
				.setParameter("periodo", periodo).getResultList();
		return resultados.stream().map(this::mapearEvento).collect(Collectors.toList());
	}

	private ActualizacionCursoMoodlePlanDTO mapearPlan(Object[] row) {
		ActualizacionCursoMoodlePlanDTO dto = new ActualizacionCursoMoodlePlanDTO();
		dto.setIdPlan(getLongValue(row[0]));
		dto.setNombre(getStringValue(row[1]));
		return dto;
	}

	private ActualizacionCursoMoodleProgramaDTO mapearPrograma(Object[] row) {
		ActualizacionCursoMoodleProgramaDTO dto = new ActualizacionCursoMoodleProgramaDTO();
		dto.setIdPrograma(getLongValue(row[0]));
		dto.setClavePrograma(getStringValue(row[1]));
		dto.setNombreTentativo(getStringValue(row[2]));
		dto.setTipo(getStringValue(row[3]));
		return dto;
	}

	private ActualizacionCursoMoodleEventoDTO mapearEvento(Object[] row) {
		ActualizacionCursoMoodleEventoDTO dto = new ActualizacionCursoMoodleEventoDTO();
		dto.setIdEvento(getIntegerValue(row[0]));
		dto.setIdCursoMoodle(getIntegerValue(row[1]));
		dto.setIdPrograma(getLongValue(row[2]));
		dto.setEventoCurso(getStringValue(row[3]));
		dto.setTipoAsignatura(getStringValue(row[4]));
		dto.setSemestre(getStringValue(row[5]));
		dto.setBloque(getStringValue(row[6]));
		return dto;
	}

	private Long getLongValue(Object value) {
		if (value instanceof Number) {
			return ((Number) value).longValue();
		}
		return null;
	}

	private Integer getIntegerValue(Object value) {
		if (value instanceof Number) {
			return ((Number) value).intValue();
		}
		return null;
	}

	private String getStringValue(Object value) {
		return value != null ? value.toString() : null;
	}
}
