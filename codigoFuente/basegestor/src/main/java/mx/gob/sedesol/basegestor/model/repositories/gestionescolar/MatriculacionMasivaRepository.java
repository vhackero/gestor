package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoPeriodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PeriodoInscripcionDTO;

@Repository
public class MatriculacionMasivaRepository implements IMatriculacionMasivaRepository {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<PeriodoInscripcionDTO> obtenerPeriodosInscripcion() {
		String sql = "SELECT tpi.id_periodo, tpi.nombre_periodo FROM tbl_periodos_inscripcion tpi";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> resultados = query.getResultList();
		List<PeriodoInscripcionDTO> periodos = new ArrayList<>();

		for (Object[] fila : resultados) {
			periodos.add(new PeriodoInscripcionDTO(obtenerEntero(fila[0]), obtenerCadena(fila[1])));
		}
		return periodos;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EventoPeriodoDTO> obtenerEventosPorPeriodo(String nombrePeriodo) {
		String sql = "SELECT tpl.nombre plan, tfdp.nombre_tentativo programa, te.nombre_ec evento,te.cve_evento_cap, te.id_evento, tg.nombre grupo, tg.id id_grupo "
				+ "FROM tbl_eventos te "
				+ "JOIN tbl_ficha_descriptiva_programa tfdp ON tfdp.id_programa = te.id_programa "
				+ "JOIN tbl_planes tpl ON tfdp.id_plan = tpl.id_plan "
				+ "JOIN tbl_grupos tg ON tg.id_evento = te.id_evento "
				+ "JOIN tbl_malla_curricular tmc ON tpl.id_plan = tmc.id_plan AND tmc.activo = 1 "
				+ "WHERE te.cve_evento_cap LIKE CONCAT('%',:nombrePeriodo,'%') "
				+ "ORDER BY tpl.nombre, tfdp.nombre_tentativo";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("nombrePeriodo", nombrePeriodo);
		List<Object[]> resultados = query.getResultList();

		List<EventoPeriodoDTO> eventos = new ArrayList<>();
		for (Object[] fila : resultados) {
			EventoPeriodoDTO dto = new EventoPeriodoDTO();
			dto.setPlan(obtenerCadena(fila[0]));
			dto.setPrograma(obtenerCadena(fila[1]));
			dto.setEvento(obtenerCadena(fila[2]));
			dto.setClaveEvento(obtenerCadena(fila[3]));
			dto.setIdEvento(obtenerLong(fila[4]));
			dto.setGrupo(obtenerCadena(fila[5]));
			dto.setIdGrupo(obtenerLong(fila[6]));
			eventos.add(dto);
		}
		return eventos;
	}
	
	@Override
	public boolean existeRol(Integer idRol) {
		String sql = "SELECT 1 FROM cat_roles WHERE id_rol = :idRol LIMIT 1";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("idRol", idRol);
		return !query.getResultList().isEmpty();
	}

	private String obtenerCadena(Object valor) {
		return valor != null ? valor.toString() : null;
	}

	private Integer obtenerEntero(Object valor) {
		return valor != null ? Integer.valueOf(valor.toString()) : null;
	}

	private Long obtenerLong(Object valor) {
		return valor != null ? Long.valueOf(valor.toString()) : null;
	}
}
