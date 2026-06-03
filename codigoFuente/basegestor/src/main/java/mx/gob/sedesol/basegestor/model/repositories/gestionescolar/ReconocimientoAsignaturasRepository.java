package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReconocimientoAsignaturaDTO;

@Repository
public class ReconocimientoAsignaturasRepository implements IReconocimientoAsignaturasRepository {

	private static final Logger logger = Logger.getLogger(ReconocimientoAsignaturasRepository.class);

	@Autowired
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<ReconocimientoAsignaturaDTO> buscarPorMatriculaYPeriodo(String matricula, String periodo) {
		String consulta = "SELECT rgp.id, te.id_evento, rgp.id_persona_participante, rgp.id_grupo, "
				+ "rgp.id_persona_lms idusuariomoodle, tg.id_moodle idgrupomoodle, "
				+ "te.id_curso_lms_borrador idCursoMoodle, te.id_plataforma_lms_borrador idPlataformaMoodle, "
				+ "te.modalidad, tp.sso_idUsuario matricula, tpl.id_plan, tpl.nombre plan, tfdp.id_programa, "
				+ "tfdp.nombre_tentativo programa, tfdp.cve_programa, "
				+ "COALESCE((SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre), '-') semestre, "
				+ "tmc.nombre bloque, SUBSTRING_INDEX(te.cve_evento_cap, '-', -2) periodo "
				+ "FROM rel_grupo_participante rgp "
				+ "JOIN tbl_persona tp ON rgp.id_persona_participante = tp.id_persona "
				+ "JOIN tbl_grupos tg ON rgp.id_grupo = tg.id "
				+ "JOIN tbl_eventos te ON tg.id_evento = te.id_evento "
				+ "JOIN tbl_ficha_descriptiva_programa tfdp ON te.id_programa = tfdp.id_programa "
				+ "JOIN tbl_planes tpl ON tfdp.id_plan = tpl.id_plan "
				+ "JOIN tbl_malla_curricular tmc ON tmc.id = tfdp.id_eje_capacitacion "
				+ "WHERE tp.sso_idUsuario = :matricula "
				+ "AND te.cve_evento_cap LIKE CONCAT('%', :periodo, '%')";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("matricula", matricula);
		query.setParameter("periodo", periodo);

		List<Object[]> rows = query.getResultList();
		logger.info("ReconocimientoAsignaturasRepository.buscarPorMatriculaYPeriodo - matricula=" + matricula
				+ ", periodo=" + periodo + ", filas=" + rows.size());
		List<ReconocimientoAsignaturaDTO> resultados = new ArrayList<ReconocimientoAsignaturaDTO>();
		for (Object[] row : rows) {
			ReconocimientoAsignaturaDTO dto = new ReconocimientoAsignaturaDTO();
			dto.setId(toLong(row[0]));
			dto.setIdEvento(toLong(row[1]));
			dto.setIdPersonaParticipante(toLong(row[2]));
			dto.setIdGrupo(toLong(row[3]));
			dto.setIdUsuarioMoodle(toLong(row[4]));
			dto.setIdGrupoMoodle(toLong(row[5]));
			dto.setIdCursoMoodle(toInteger(row[6]));
			dto.setIdPlataformaMoodle(toInteger(row[7]));
			dto.setIdModalidad(toInteger(row[8]));
			dto.setMatricula(toString(row[9]));
			dto.setIdPlan(toLong(row[10]));
			dto.setPlan(toString(row[11]));
			dto.setIdPrograma(toLong(row[12]));
			dto.setPrograma(toString(row[13]));
			dto.setClavePrograma(toString(row[14]));
			dto.setSemestre(toString(row[15]));
			dto.setBloque(toString(row[16]));
			dto.setPeriodo(toString(row[17]));
			resultados.add(dto);
		}
		return resultados;
	}

	private Long toLong(Object value) {
		return value != null ? ((Number) value).longValue() : null;
	}

	private Integer toInteger(Object value) {
		return value != null ? ((Number) value).intValue() : null;
	}

	private String toString(Object value) {
		return value != null ? String.valueOf(value) : null;
	}
}
