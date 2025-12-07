package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReenvioCorreoMateriasDTO;

@Repository
public class EnvioCorreoRepository implements IEnvioCorreoRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long registrarResultadoEnvioCorreoInscripcion(Long idPersona, Long idProcesoInscripcion,
			Integer envioCorreo) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_envio_correo (");
		sql.append("    id_persona, ");
		sql.append("    id_proceso_inscripcion, ");
		sql.append("    envio_correo ");
		sql.append(") VALUES (");
		sql.append("    :idPersona, ");
		sql.append("    :idProcesoInscripcion, ");
		sql.append("    :envioCorreo ");
		sql.append(")");

		entityManager.createNativeQuery(sql.toString()).setParameter("idPersona", idPersona)
				.setParameter("idProcesoInscripcion", idProcesoInscripcion).setParameter("envioCorreo", envioCorreo)
				.executeUpdate();

		Object result = entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult();

		return getLongValue(result);
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

	@Override
	public void actualizarResultadoEnvioCorreoInscripcion(Long idPersona, Long idProcesoInscripcion,
			Integer envioCorreo) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_envio_correo ");
		sql.append("SET envio_correo = :envioCorreo ");
		sql.append("WHERE id_persona = :idPersona ");
		sql.append("  AND id_proceso_inscripcion = :idProcesoInscripcion ");

		entityManager.createNativeQuery(sql.toString()).setParameter("envioCorreo", envioCorreo)
				.setParameter("idPersona", idPersona).setParameter("idProcesoInscripcion", idProcesoInscripcion)
				.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReenvioCorreoMateriasDTO> obtenerMateriasParaReenvioCorreo(Long idPersona, Long idProcesoInscripcion) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    ti.asignatura AS programa, ");
		sql.append("    tfdp.tipo AS tipoPrograma, ");
		sql.append(
				"    (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre) AS semestre, ");
		sql.append("    tmc.nombre AS bloque, ");
		sql.append("    tpi.semestre AS periodo ");
		sql.append("FROM tbl_inscripciones ti ");
		sql.append("INNER JOIN tbl_procesos_inscripcion tpi ");
		sql.append("    ON ti.fecha_registro >= tpi.fecha_inicio ");
		sql.append("   AND ti.fecha_registro <= tpi.fecha_fin ");
		sql.append("INNER JOIN rel_proceso_inscipcion_planesyprogramas rgp ");
		sql.append("    ON rgp.id_programa = ti.idprograma ");
		sql.append("JOIN tbl_ficha_descriptiva_programa tfdp ");
		sql.append("    ON ti.idprograma = tfdp.id_programa ");
		sql.append("JOIN tbl_malla_curricular tmc ");
		sql.append("    ON tmc.id = tfdp.id_eje_capacitacion ");
		sql.append("   AND rgp.id_plan = ti.idplan ");
		sql.append("   AND tpi.proceso_inscripcion_id = rgp.id_proceso_inscripcion ");
		sql.append("WHERE tpi.proceso_inscripcion_id = :idProcesoInscripcion ");
		sql.append("  AND ti.idpersona = :idPersona ");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString())
				.setParameter("idProcesoInscripcion", idProcesoInscripcion).setParameter("idPersona", idPersona)
				.getResultList();

		return resultados.stream().map(this::mapearInscripcionProgramaEstructura).collect(Collectors.toList());
	}

	private ReenvioCorreoMateriasDTO mapearInscripcionProgramaEstructura(Object[] row) {
		return new ReenvioCorreoMateriasDTO((String) row[0], // programa
				(String) row[1], // tipoPrograma
				(String) row[2], // semestre
				(String) row[3], // bloque
				getLongValue(row[4]) // periodo
		);
	}

}
