package mx.gob.sedesol.basegestor.model.repositories.inscripcion;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.inscripcion.CatProcesoInscripcionDTO;

@Repository
public class CatProcesoInscripcionRepository implements ICatProcesoInscripcionRepository {

	@Autowired
	public EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<CatProcesoInscripcionDTO> obtenerTiposProcesoInscripcion() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    cp.id_proceso, ");
		sql.append("    cp.nombre ");
		sql.append("FROM cat_procesos_inscripcion cp ");
		sql.append("WHERE cp.activo = 1");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString()).getResultList();

		return resultados.stream().map(this::mapearCatProcesoInscripcion).collect(Collectors.toList());
	}

	private CatProcesoInscripcionDTO mapearCatProcesoInscripcion(Object[] row) {
		CatProcesoInscripcionDTO dto = new CatProcesoInscripcionDTO();
		dto.setId(getLongValue(row[0]));
		dto.setNombre((String) row[1]);
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
