package mx.gob.sedesol.basegestor.model.repositories.inscripcion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ProcesoInscripcionDTO;

@Repository
public class ProcesoInscripcionRepository implements IProcesoInscripcionRepository {

	@Autowired
	public EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcesoInscripcionDTO> obtenerPorConvocatoriaYTipoProceso(Long idConvocatoria, Long idTipoProceso) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    t.proceso_inscripcion_id, ");
		sql.append("    t.nombre ");
		sql.append("FROM tbl_procesos_inscripcion t ");
		sql.append("WHERE t.id_categoria_proceso = 1 ");
		sql.append("  AND t.convocatoria_id = :idConvocatoria ");
		sql.append("  AND t.id_tipo_proceso = :idTipoProceso");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString())
				.setParameter("idConvocatoria", idConvocatoria).setParameter("idTipoProceso", idTipoProceso)
				.getResultList();

		return resultados.stream().map(this::mapearProcesoInscripcion).collect(Collectors.toList());
	}

	private ProcesoInscripcionDTO mapearProcesoInscripcion(Object[] row) {
		ProcesoInscripcionDTO dto = new ProcesoInscripcionDTO();
		dto.setId(getLongValue(row[0])); // proceso_inscripcion_id
		dto.setNombre((String) row[1]); // nombre
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

}
