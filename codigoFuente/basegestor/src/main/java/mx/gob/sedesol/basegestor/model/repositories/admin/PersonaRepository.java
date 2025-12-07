package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaRepository implements IPersonaRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long obtenerIdPersonaPorMatricula(String matricula) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    tp.id_persona ");
		sql.append("FROM tbl_persona tp ");
		sql.append("WHERE tp.sso_idUsuario = :matricula");

		List<?> resultados = entityManager.createNativeQuery(sql.toString()).setParameter("matricula", matricula)
				.getResultList();

		if (resultados.isEmpty()) {
			return null;
		}

		Object row = resultados.get(0);
		return getLongValue(row);
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
