package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AprobacionAsignaturasPorSemestreDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CreditosTotalesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoInscripcionEstudianteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionInsertDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasReprobadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasCursadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.LimitesCargaAcademicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.IntentosAsignaturasDTO;

@Repository
public class InscripcionRepository implements IinscripcionRepository {

	@Autowired
	public EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<InscripcionPersonaDTO> obtenerInscripcionPorPersona(String idPersona) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tp.id_persona, ");
		sql.append("       tp.sso_idUsuario, ");
		sql.append("       tp.sso_nombre, ");
		sql.append("       tp.sso_apellidoMaterno, ");
		sql.append("       tp.sso_apellidoPaterno, ");
		sql.append("       rpc.sso_correoElectronico, ");
		sql.append("       tpa.id_plan, ");
		sql.append("       tpl.nombre, ");
		sql.append("       tfd.nombre_tentativo, ");
		sql.append("       tpa.id_convocatoria ");
		sql.append("FROM tbl_persona tp ");
		sql.append("INNER JOIN rel_persona_correo rpc ON rpc.id_persona = tp.id_persona ");
		sql.append("INNER JOIN tbl_persona_aspirante tpa ON tpa.id_persona = rpc.id_persona ");
		sql.append("INNER JOIN tbl_planes tpl ON tpl.id_plan = tpa.id_plan ");
		sql.append("INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = tpl.id_plan ");
		sql.append("INNER JOIN tbl_convocatoria tc ON tc.convocatoria_id = tpa.id_convocatoria ");
		sql.append("WHERE tp.id_persona = :idPersona ");
		sql.append("  AND tc.activo = 1");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString()).setParameter("idPersona", idPersona)
				.getResultList();

		List<InscripcionPersonaDTO> listaDTO = new ArrayList<>();

		for (Object[] row : resultados) {
			listaDTO.add(mapearInscripcionPersona(row));
		}

		return listaDTO;

	}

	private InscripcionPersonaDTO mapearInscripcionPersona(Object[] row) {
		InscripcionPersonaDTO dto = new InscripcionPersonaDTO();
		dto.setIdPersona(getLongValue(row[0]));
		dto.setNombreUsuario((String) row[1]);
		dto.setNombre((String) row[2]);
		dto.setPrimerApellido((String) row[3]);
		dto.setSegundoApellido((String) row[4]);
		dto.setCorreo((String) row[5]);
		dto.setIdPlan(getLongValue(row[6]));
		dto.setPlan((String) row[7]);
		dto.setPrograma((String) row[8]);
		dto.setIdConvocatoria(getLongValue(row[9]));

		return dto;
	}

	@Override
	public List<InscripcionMateriasDTO> consultarMaterias(String id_plan) {

		List<InscripcionMateriasDTO> lista = new ArrayList<InscripcionMateriasDTO>();

		String consulta = "SELECT tp.identificador clave_plan, tfd.identificador_final clave_progrma, tp.id_plan id_plan, tp.nombre nombre_plan, tfd.id_programa id_programa ,tfd.nombre_tentativo programa,\r\n"
				+ "       tmc.nombre subestructura,\r\n"
				+ "       (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre ) estructura,\r\n"
				+ "       cne.nombre nivel_ensenanza,\r\n" + "       cdp.nombre division,\r\n"
				+ "       tfd.tipo tipo_programa,\r\n" + "       tfd.id_programa_antecedente seriada\r\n"
				+ "FROM tbl_planes tp\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = tp.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = tfd.id_eje_capacitacion\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = tp.id_nivel_ensenanza\r\n"
				+ "         INNER JOIN cat_divisiones_plan cdp ON cdp.id = tp.id_divisiones_plan\r\n"
				+ "WHERE tp.id_plan = :id_plan AND tfd.identificador_final is not null AND tfd.identificador_final != ''";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_plan", id_plan);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionMateriasDTO convocatoria = mapeoMaterias(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	@Override
	public List<InscripcionMateriasDTO> consultarMateriasPorConvocatoria(String id_plan, String id_convocatoria,
			String id_estructura) {

		List<InscripcionMateriasDTO> lista = new ArrayList<InscripcionMateriasDTO>();

		String consulta = "SELECT tp.identificador clave_plan, tfd.identificador_final clave_progrma, tp.id_plan id_plan, tp.nombre nombre_plan, tfd.id_programa id_programa ,tfd.nombre_tentativo programa,\r\n"
				+ "       tmc.nombre subestructura,\r\n"
				+ "       (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre ) estructura,\r\n"
				+ "       cne.nombre nivel_ensenanza,\r\n" + "       cdp.nombre division,\r\n"
				+ "       tfd.tipo tipo_programa,\r\n" + "       tfd.id_programa_antecedente seriada\r\n"
				+ "FROM tbl_planes tp\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = tp.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = tfd.id_eje_capacitacion\r\n"
				+ "         INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = tp.id_nivel_ensenanza\r\n"
				+ "         INNER JOIN cat_divisiones_plan cdp ON cdp.id = tp.id_divisiones_plan\r\n"
				+ "         INNER JOIN rel_convocatoria_planesyprogramas rcp ON tp.id_plan = rcp.id_plan AND tfd.id_programa = rcp.id_programa\r\n"
				+ "WHERE tp.id_plan = :id_plan AND rcp.id_convocatoria = :id_convocatoria AND EXISTS\r\n"
				+ "    (SELECT tmc3.nombre FROM tbl_malla_curricular tmc3 WHERE tmc3.id = tmc.id_padre AND SUBSTRING(tmc3.nombre, -1) IN (:id_estructura)) AND\r\n"
				+ "      tfd.identificador_final is not null AND tfd.identificador_final != ''";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_plan", id_plan);
		query.setParameter("id_convocatoria", id_convocatoria);
		List<String> estructuras = Arrays.asList(id_estructura.split(","));
		query.setParameter("id_estructura", estructuras);
		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionMateriasDTO convocatoria = mapeoMaterias(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	@Override
	public Long consultarNumeroEstructura(String id_plan_infopersona) {

		String consulta = "SELECT rmp.id_plan plan,\r\n" + "       rmp.elementos_estructuras num_estructuras\r\n"
				+ "    From rel_malla_plan rmp\r\n" + "WHERE rmp.id_plan=:id_plan_infopersona and rmp.activo=1;";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_plan_infopersona", id_plan_infopersona);

		List<Object[]> listaQuery = query.getResultList();

		Long numeroEstructura = 0l;

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				numeroEstructura = Long.valueOf(obj[1].toString());

			}
		}

		return numeroEstructura;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<LimitesCargaAcademicaDTO> obtenerLimitesCargaAcademicaPorPlan(Long idPlan) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    tm.id_max_min, ");
		sql.append("    tm.plan, ");
		sql.append("    tm.id_plan, ");
		sql.append("    tm.min_programas_por_periodo, ");
		sql.append("    tm.max_programas_regulares, ");
		sql.append("    tm.max_programas_irregulares, ");
		sql.append("    tm.programas_por_periodo ");
		sql.append("FROM tbl_max_min_planes tm ");
		sql.append("WHERE tm.id_plan = :idPlan");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString()).setParameter("idPlan", idPlan)
				.getResultList();

		if (resultados.isEmpty()) {
			return Optional.empty();
		}

		Object[] row = resultados.get(0);
		return Optional.of(mapearLimiteCargaAcademica(row));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Optional<EstadoInscripcionEstudianteDTO> obtenerEstadoInscripcionEstudiante(Long idPersona,
			Long idProcesoInscripcion) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    (NOT EXISTS( ");
		sql.append("        SELECT 1 ");
		sql.append("        FROM tbl_inscripciones ti ");
		sql.append("        WHERE ti.Idpersona = tp.id_persona ");
		sql.append("    )) AS esInscripcionOrdinariaInicial, ");
		sql.append("    ");
		sql.append("    (EXISTS( ");
		sql.append("        SELECT 1 ");
		sql.append("        FROM tbl_procesos_inscripcion tpi ");
		sql.append("        WHERE NOT EXISTS( ");
		sql.append("                SELECT 1 ");
		sql.append("                FROM tbl_inscripciones ti2 ");
		sql.append("                WHERE ti2.Idpersona = tp.id_persona ");
		sql.append("              ) ");
		sql.append("          AND tpi.proceso_inscripcion_id = :idProcesoInscripcion ");
		sql.append("          AND tpi.id_tipo_proceso = 2 ");
		sql.append("    )) AS esInscripcionExtraordinariaInicial, ");
		sql.append("    ");
		sql.append("    ( ");
		sql.append("        SELECT MIN(n) ");
		sql.append("        FROM ( ");
		sql.append("            SELECT ");
		sql.append("                a.n + b.n*10 + c.n*100 AS n ");
		sql.append("            FROM ");
		sql.append(
				"                (SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 ");
		sql.append(
				"                 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) a ");
		sql.append("            CROSS JOIN ");
		sql.append(
				"                (SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 ");
		sql.append(
				"                 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) b ");
		sql.append("            CROSS JOIN ");
		sql.append(
				"                (SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 ");
		sql.append(
				"                 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) c ");
		sql.append("        ) AS nums ");
		sql.append("        WHERE n > 0 ");
		sql.append("          AND n <= ( ");
		sql.append("                SELECT IFNULL(MAX(t.semestre), 1) + 1 ");
		sql.append("                FROM tbl_inscripciones t ");
		sql.append("                WHERE t.Idpersona = tp.id_persona ");
		sql.append("          ) ");
		sql.append("          AND n NOT IN ( ");
		sql.append("                SELECT t2.semestre ");
		sql.append("                FROM tbl_inscripciones t2 ");
		sql.append("                WHERE t2.Idpersona = tp.id_persona ");
		sql.append("          ) ");
		sql.append("    ) AS semestreInscripcionOrdinaria ");
		sql.append("FROM tbl_persona tp ");
		sql.append("WHERE tp.id_persona = :idPersona");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString()).setParameter("idPersona", idPersona)
				.setParameter("idProcesoInscripcion", idProcesoInscripcion).getResultList();

		if (resultados.isEmpty()) {
			return Optional.empty();
		}

		Object[] row = resultados.get(0);
		return Optional.of(mapearEstadoInscripcionEstudiante(row));
	}

	private EstadoInscripcionEstudianteDTO mapearEstadoInscripcionEstudiante(Object[] row) {
		EstadoInscripcionEstudianteDTO dto = new EstadoInscripcionEstudianteDTO();

		dto.setEsInscripcionOrdinariaInicial(getBooleanValue(row[0])); // esIncripcionOrdinariaInicial
		dto.setEsInscripcionExtraordinariaInicial(getBooleanValue(row[1])); // esInscripcionExtraordinariaInicial
		dto.setSemestreInscripcionOrdinaria(getIntegerValue(row[2])); // semestreInscripcionOrdinaria

		return dto;
	}

	private Boolean getBooleanValue(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		if (value instanceof Number) {
			return ((Number) value).intValue() != 0;
		}
		return Boolean.valueOf(value.toString());
	}

	@Override
	public List<InscripcionMateriasCursadasDTO> consultarMateriasCursadas(String id_persona) {

		List<InscripcionMateriasCursadasDTO> lista = new ArrayList<InscripcionMateriasCursadasDTO>();

		String consulta = "SELECT\r\n" + "    tpl.nombre plan,\r\n" + "    tpl.id_plan id_plan,\r\n"
				+ "    fd.nombre_tentativo programa,\r\n" + "    fd.id_programa id_programa,\r\n"
				+ "    tmc.nombre subestructura,\r\n"
				+ "    (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre ) estructura,\r\n"
				+ "    rgp.calificacion_final calificacion_final,\r\n"
				+ "    if(rgp.calificacion_final >= 60, 1,0) estatus_aprobacion\r\n" + "FROM tbl_persona tp\r\n"
				+ "         INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona AND calificacion_final IS NOT NULL\r\n"
				+ "         INNER JOIN tbl_grupos tg ON tg.id = rgp.id_grupo\r\n"
				+ "         INNER JOIN tbl_eventos te ON te.id_evento = tg.id_evento\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = te.id_programa\r\n"
				+ "         INNER JOIN tbl_planes tpl ON tpl.id_plan = fd.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion\r\n"
				+ "WHERE tp.id_persona = :id_persona";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionMateriasCursadasDTO convocatoria = mapeoMateriasPasadas(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	@Override
	public List<InscripcionMateriasInsDTO> consultarMateriasInscritas(String idpersona, String plan) {

		List<InscripcionMateriasInsDTO> lista = new ArrayList<InscripcionMateriasInsDTO>();

		String consulta = "SELECT ti.* FROM tbl_inscripciones ti\r\n"
				+ "             INNER JOIN tbl_procesos_inscripcion tpi ON ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_fin\r\n"
				+ "             INNER JOIN rel_proceso_inscipcion_planesyprogramas rgp ON rgp.id_programa = ti.idprograma ANd rgp.id_plan = ti.idplan AND tpi.proceso_inscripcion_id =  rgp.id_proceso_inscripcion\r\n"
				+ "             WHERE ti.Idpersona = :idpersona  AND ti.idplan = :plan AND ti.semestre = tpi.semestre";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("idpersona", idpersona);
		query.setParameter("plan", plan);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionMateriasInsDTO convocatoria = mapeoInscripcionMateria(obj);
				lista.add(convocatoria);

			}
		}

		return lista;
	}

	@Override
	public List<IntentosAsignaturasDTO> consultarIntentosAsignaturas(String id_persona) {

		List<IntentosAsignaturasDTO> lista = new ArrayList<IntentosAsignaturasDTO>();

		String consulta = "SELECT COUNT(if(rgp.calificacion_final < 60, 1,0)) as intentos_reprobados,\r\n"
				+ "    tpl.id_plan id_plan,\r\n" + "    fd.id_programa id_programa\r\n" + "FROM tbl_persona tp\r\n"
				+ "         INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona AND calificacion_final IS NOT NULL\r\n"
				+ "         INNER JOIN tbl_grupos tg ON tg.id = rgp.id_grupo\r\n"
				+ "         INNER JOIN tbl_eventos te ON te.id_evento = tg.id_evento\r\n"
				+ "         INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = te.id_programa\r\n"
				+ "         INNER JOIN tbl_planes tpl ON tpl.id_plan = fd.id_plan\r\n"
				+ "         INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion\r\n"
				+ "WHERE tp.id_persona = :id_persona and rgp.calificacion_final < 60 group by te.id_programa";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				IntentosAsignaturasDTO convocatoria = mapeoIntentosAsignaturas(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	@Override
	public Boolean consultarNuevoIngreso(String id_persona) {

		String consulta = "SELECT * from tbl_persona tp\r\n"
				+ "WHERE NOT EXISTS(SELECT * FROM tbl_inscripciones ti WHERE ti.semestre=1 AND ti.Idpersona=tp.id_persona) AND id_persona=:id_persona\r\n"
				+ "  AND NOT EXISTS(SELECT * from rel_persona_bajas rpb WHERE rpb.id_persona = tp.id_persona)";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			return true;
		}

		return false;

	}

	@Override
	public List<InscripcionBajasDTO> consultarBajas(String id_persona) {

		List<InscripcionBajasDTO> lista = new ArrayList<InscripcionBajasDTO>();

		String consulta = "SELECT rpb.id_plan, rpb.id_programa, ctb.id_tipo_baja, ctb.nombre tipo_baja, ctb.fecha_modificacion FROM rel_persona_bajas rpb\r\n"
				+ "         INNER JOIN rel_motivo_baja rmb ON rmb.id_motivo_baja = rpb.motivo_baja_id\r\n"
				+ "         INNER JOIN cat_tipo_bajas ctb ON ctb.id_tipo_baja = rmb.tipo_baja_id\r\n"
				+ "         WHERE rpb.id_persona = :id_persona";

		Query query = entityManager.createNativeQuery(consulta);
		query.setParameter("id_persona", id_persona);

		List<Object[]> listaQuery = query.getResultList();

		if (!listaQuery.isEmpty()) {
			for (Object[] obj : listaQuery) {

				InscripcionBajasDTO convocatoria = mapeoPersonaBaja(obj);
				lista.add(convocatoria);

			}
		}

		return lista;

	}

	@Override
	public void insertarRegistro(InscripcionInsertDTO dto) {
		String sql = "INSERT INTO tbl_inscripciones (Idpersona, programa, asignatura, groupbase, idplan, idprograma, "
				+ "idevento, nivel, division, profile_field_perfil, bloque, clave_asig, nuevoingreso, "
				+ "recursamiento, alta, semestre, fecha_registro) "
				+ "VALUES ( :Idpersona, :programa, :asignatura, :groupbase, :idplan, :idprograma, :idevento, "
				+ ":nivel, :division, :profileFieldPerfil, :bloque, :claveAsig, :nuevoIngreso, :recursamiento, "
				+ ":alta, :semestre, :fechaRegistro)";

		entityManager.createNativeQuery(sql).setParameter("Idpersona", dto.getIdPersona())
				.setParameter("programa", dto.getPrograma()).setParameter("asignatura", dto.getAsignatura())
				.setParameter("groupbase", dto.getGroupBase()).setParameter("idplan", dto.getIdPlan())
				.setParameter("idprograma", dto.getIdPrograma()).setParameter("idevento", dto.getIdEvento())
				.setParameter("nivel", dto.getNivel()).setParameter("division", dto.getDivision())
				.setParameter("profileFieldPerfil", dto.getProfileFieldPerfil()).setParameter("bloque", dto.getBloque())
				.setParameter("claveAsig", dto.getClaveAsig()).setParameter("nuevoIngreso", dto.getNuevoIngreso())
				.setParameter("recursamiento", dto.getRecursamiento()).setParameter("alta", dto.getAlta())
				.setParameter("semestre", dto.getSemestre()).setParameter("fechaRegistro", dto.getFechaRegistro())
				.executeUpdate();
	}

	private InscripcionMateriasInsDTO mapeoInscripcionMateria(Object[] obj) {
		InscripcionMateriasInsDTO materia = new InscripcionMateriasInsDTO();

		materia.setId(Integer.parseInt(obj[0].toString()));
		materia.setIdPersona(obj[1] != null ? Long.valueOf(obj[1].toString()) : null);
		materia.setPrograma(obj[2] != null ? obj[2].toString() : null);
		materia.setAsignatura(obj[3].toString());
		materia.setGroupBase(obj[4].toString());
		materia.setIdPlan(Integer.parseInt(obj[5].toString()));
		materia.setIdPrograma(Integer.parseInt(obj[6].toString()));
		materia.setIdEvento(Integer.parseInt(obj[7].toString()));
		materia.setNivel(obj[8].toString());
		materia.setDivision(obj[9].toString());
		materia.setPerfil(obj[10].toString());
		materia.setBloque(obj[11].toString());
		materia.setClaveAsignatura(obj[12].toString());
		materia.setNuevoIngreso(Integer.parseInt(obj[13].toString()));
		materia.setRecursamiento(Integer.parseInt(obj[14].toString()));
		materia.setAlta(Integer.parseInt(obj[15].toString()));
		materia.setSemestre(Integer.parseInt(obj[16].toString()));
		materia.setFechaRegistro(obj[17] != null ? obj[17].toString() : null);

		return materia;
	}

	private InscripcionMateriasCursadasDTO mapeoMateriasPasadas(Object[] obj) {
		InscripcionMateriasCursadasDTO programa = new InscripcionMateriasCursadasDTO();

		programa.setPlan(obj[0].toString());
		programa.setIdPlan(Long.valueOf(obj[1].toString()));
		programa.setPrograma(obj[2].toString());
		programa.setIdPrograma(Long.valueOf(obj[3].toString()));
		programa.setSubestructura(obj[4].toString());
		programa.setEstructura(obj[5].toString());
		programa.setCalificacionFinal(Double.valueOf(obj[6].toString()));
		programa.setEstatusAprobacion(Integer.valueOf(obj[7].toString()));

		return programa;
	}

	private LimitesCargaAcademicaDTO mapearLimiteCargaAcademica(Object[] row) {
		LimitesCargaAcademicaDTO dto = new LimitesCargaAcademicaDTO();

		dto.setIdMaxMin(getIntegerValue(row[0])); // id_max_min
		dto.setPlan((String) row[1]); // plan
		dto.setIdPlan(getIntegerValue(row[2])); // id_plan
		dto.setMinProgramasPorPeriodo((String) row[3]); // min_programas_por_periodo
		dto.setMaxProgramasRegulares((String) row[4]); // max_programas_regulares
		dto.setMaxProgramasIrregulares((String) row[5]); // max_programas_irregulares
		dto.setProgramasPorPeriodo(getIntegerValue(row[6])); // programas_por_periodo

		return dto;
	}

	private InscripcionPersonaDTO mapeo(Object[] obj) {

		InscripcionPersonaDTO regresa = new InscripcionPersonaDTO();

		regresa.setIdPersona(Long.valueOf(obj[0].toString()));
		regresa.setNombreUsuario(obj[1].toString());
		regresa.setNombre(obj[2].toString());
		regresa.setPrimerApellido(obj[3].toString());
		regresa.setSegundoApellido(obj[4].toString());
		regresa.setCorreo(obj[5].toString());
		regresa.setIdPlan(Long.valueOf(obj[6].toString()));
		regresa.setPlan(obj[7].toString());
		regresa.setPrograma(obj[8].toString());
		regresa.setIdConvocatoria(Long.valueOf(obj[10].toString()));

		return regresa;
	}

	private InscripcionMateriasDTO mapeoMaterias(Object[] obj) {

		InscripcionMateriasDTO programa = new InscripcionMateriasDTO();

		programa.setClavePlan(obj[0].toString());
		programa.setClavePrograma(obj[1].toString());
		programa.setIdPlan(Long.valueOf(obj[2].toString()));
		programa.setNombrePlan(obj[3].toString());
		programa.setIdPrograma(Long.valueOf(obj[4].toString()));
		programa.setNombreTentativoPrograma(obj[5].toString());
		programa.setSubestructura(obj[6].toString());
		programa.setEstructura(obj[7].toString());
		programa.setNivelEnsenanza(obj[8].toString());
		programa.setDivision(obj[9].toString());
		programa.setTipoPrograma(obj[10].toString());
		programa.setIdProgramaAntecedente(obj[11] != null ? Long.valueOf(obj[11].toString()) : null);
		programa.setCheck(false);

		return programa;
	}

	private IntentosAsignaturasDTO mapeoIntentosAsignaturas(Object[] obj) {

		IntentosAsignaturasDTO programa = new IntentosAsignaturasDTO();

		programa.setIntentosReprobados(Long.valueOf(obj[0].toString()));
		programa.setIdPlan(Long.valueOf(obj[1].toString()));
		programa.setIdPrograma(Long.valueOf(obj[2].toString()));

		return programa;
	}

	private InscripcionBajasDTO mapeoPersonaBaja(Object[] obj) {

		InscripcionBajasDTO personaBaja = new InscripcionBajasDTO();

		personaBaja.setIdPlan(Long.valueOf(obj[0].toString()));
		personaBaja.setIdPrograma(Long.valueOf(obj[1].toString()));
		personaBaja.setIdTipoBaja(Long.valueOf(obj[2].toString()));
		personaBaja.setTipoBaja(obj[3].toString());
		personaBaja.setFechaModificacion(LocalDateTime.parse(obj[4].toString()));

		return personaBaja;
	}

	@SuppressWarnings("unchecked")
	public List<IntentosAsignaturasDTO> obtenerIntentosReprobadosPorPersona(Long idPersona) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(IF(rgp.calificacion_final < 60, 1, 0)) AS intentos_reprobados, ");
		sql.append("       tpl.id_plan AS id_plan, ");
		sql.append("       fd.id_programa AS id_programa ");
		sql.append("FROM tbl_persona tp ");
		sql.append("INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona ");
		sql.append("    AND calificacion_final IS NOT NULL ");
		sql.append("INNER JOIN tbl_grupos tg ON tg.id = rgp.id_grupo ");
		sql.append("INNER JOIN tbl_eventos te ON te.id_evento = tg.id_evento ");
		sql.append("INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = te.id_programa ");
		sql.append("INNER JOIN tbl_planes tpl ON tpl.id_plan = fd.id_plan ");
		sql.append("INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion ");
		sql.append("WHERE tp.id_persona = :idPersona ");
		sql.append("AND rgp.calificacion_final < 60 ");
		sql.append("GROUP BY te.id_programa");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString()).setParameter("idPersona", idPersona)
				.getResultList();

		return mapearIntentosReprobados(resultados);
	}

	private List<IntentosAsignaturasDTO> mapearIntentosReprobados(List<Object[]> resultados) {
		return resultados.stream().map(this::mapearIntentosReprobadosDTO).collect(Collectors.toList());
	}

	private IntentosAsignaturasDTO mapearIntentosReprobadosDTO(Object[] row) {
		IntentosAsignaturasDTO dto = new IntentosAsignaturasDTO();

		dto.setIntentosReprobados(getLongValue(row[0])); // COUNT(IF(...))
		dto.setIdPlan(getLongValue(row[1])); // tpl.id_plan
		dto.setIdPrograma(getLongValue(row[2])); // fd.id_programa

		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InscripcionMateriasDTO> obtenerMateriasPorPeriodoInscripcion(Long idPlan, Date fechaActual,
			Long idConvocatoria) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    tp.identificador AS clave_plan, ");
		sql.append("    tfd.identificador_final AS clave_programa, ");
		sql.append("    tp.id_plan AS id_plan, ");
		sql.append("    tp.nombre AS nombre_plan, ");
		sql.append("    tfd.id_programa AS id_programa, ");
		sql.append("    tfd.nombre_tentativo AS programa, ");
		sql.append("    tmc.nombre AS subestructura, ");
		sql.append(
				"    (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre) AS estructura, ");
		sql.append("    cne.nombre AS nivel_ensenanza, ");
		sql.append("    cdp.nombre AS division, ");
		sql.append("    tfd.tipo AS tipo_programa, ");
		sql.append("    tfd.id_programa_antecedente AS seriada, ");
		sql.append("    tpi.semestre AS periodo, ");
		sql.append("    tpi.perfil AS perfil, ");
		sql.append("    tpi.proceso_inscripcion_id AS proceso_inscripcion_id ");
		sql.append("FROM tbl_procesos_inscripcion tpi ");
		sql.append("JOIN rel_proceso_inscipcion_planesyprogramas rpip ON rpip.id_plan = :idPlanInfopersona ");
		sql.append("    AND tpi.proceso_inscripcion_id = rpip.id_proceso_inscripcion ");
		sql.append("JOIN tbl_planes tp ON tp.id_plan = rpip.id_plan ");
		sql.append("JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = tp.id_plan ");
		sql.append("    AND rpip.id_programa = tfd.id_programa ");
		sql.append("JOIN tbl_malla_curricular tmc ON tmc.id = tfd.id_eje_capacitacion ");
		sql.append("JOIN cat_nivel_ensenanza_programa cne ON cne.id = tp.id_nivel_ensenanza ");
		sql.append("JOIN cat_divisiones_plan cdp ON cdp.id = tp.id_divisiones_plan ");
		sql.append(
				"WHERE :fechaActual > tpi.fecha_inicio AND :fechaActual < tpi.fecha_fin AND tpi.convocatoria_id = :idConvocatoria ");
		sql.append("ORDER BY estructura ASC, subestructura ASC, programa ASC");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString())
				.setParameter("idPlanInfopersona", idPlan).setParameter("fechaActual", fechaActual)
				.setParameter("idConvocatoria", idConvocatoria).getResultList();

		return mapearResultados(resultados);
	}

	/**
	 * Mapea los resultados de Object[] a InscripcionMateriasDTO
	 */
	private List<InscripcionMateriasDTO> mapearResultados(List<Object[]> resultados) {
		return resultados.stream().map(this::mapearArrayADTO).collect(Collectors.toList());
	}

	/**
	 * Convierte un Object[] en InscripcionMateriasDTO El orden debe coincidir
	 * exactamente con el SELECT de la consulta
	 */
	private InscripcionMateriasDTO mapearArrayADTO(Object[] row) {
		InscripcionMateriasDTO dto = new InscripcionMateriasDTO();

		dto.setClavePlan((String) row[0]); // tp.identificador
		dto.setClavePrograma((String) row[1]); // tfd.identificador_final
		dto.setIdPlan(getLongValue(row[2])); // tp.id_plan
		dto.setNombrePlan((String) row[3]); // tp.nombre
		dto.setIdPrograma(getLongValue(row[4])); // tfd.id_programa
		dto.setNombreTentativoPrograma((String) row[5]); // tfd.nombre_tentativo
		dto.setSubestructura((String) row[6]); // tmc.nombre
		dto.setEstructura((String) row[7]); // subconsulta tmc2.nombre
		dto.setNivelEnsenanza((String) row[8]); // cne.nombre
		dto.setDivision((String) row[9]); // cdp.nombre
		dto.setTipoPrograma((String) row[10]); // tfd.tipo
		dto.setIdProgramaAntecedente(getLongValue(row[11])); // tfd.id_programa_antecedente
		dto.setCheck(false); // Valor por defecto
		dto.setPeriodo(getIntegerValue(row[12])); // tpi.semestre
		dto.setPerfil((String) row[13]); // tpi.perfil
		dto.setIdProcesoInscripcion(getLongValue(row[14])); // tpi.proceso_inscripcion_id

		return dto;
	}

	/**
	 * Helper method para convertir Number a Long de forma segura
	 */
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
	public Boolean esEstudianteRegular(Long idPersona) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT IF(COUNT(IF(rgp.calificacion_final < fd.calificacion_min_aprobatoria, 1, 0)) > 0, 0, 1) ");
		sql.append("FROM rel_grupo_participante rgp ");
		sql.append("JOIN tbl_grupos tg ON tg.id = rgp.id_grupo ");
		sql.append("JOIN tbl_eventos te ON te.id_evento = tg.id_evento ");
		sql.append("JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = te.id_programa ");
		sql.append("WHERE rgp.id_persona_participante = :idPersona ");
		sql.append("AND rgp.calificacion_final IS NOT NULL ");
		sql.append("AND rgp.calificacion_final < fd.calificacion_min_aprobatoria ");
		sql.append("AND NOT EXISTS( ");
		sql.append("    SELECT 1 ");
		sql.append("    FROM rel_grupo_participante rgp2 ");
		sql.append("    JOIN tbl_grupos tg2 ON tg2.id = rgp2.id_grupo ");
		sql.append("    JOIN tbl_eventos te2 ON te2.id_evento = tg2.id_evento ");
		sql.append("    JOIN tbl_ficha_descriptiva_programa fd2 ON fd2.id_programa = te2.id_programa ");
		sql.append("    WHERE rgp2.id_persona_participante = rgp.id_persona_participante ");
		sql.append("    AND te2.id_programa = te.id_programa ");
		sql.append("    AND rgp2.calificacion_final > fd2.calificacion_min_aprobatoria ");
		sql.append(")");

		List<?> resultados = entityManager.createNativeQuery(sql.toString()).setParameter("idPersona", idPersona)
				.getResultList();

		if (resultados.isEmpty()) {
			return true;
		}

		// CAMBIO: Obtener el primer elemento directamente (no es un array)
		Object resultado = resultados.get(0);
		Integer estatus = getIntegerValue(resultado);

		// Retorna true si estatus es 1 (estudiante regular), false si es 0 (irregular)
		return estatus != null && estatus == 1;
	}

	private Integer getIntegerValue(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return ((Number) value).intValue();
		}
		return null;
	}

	@Override
	public Boolean esEstudianteNuevoIngreso(Long idPersona) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(tp.id_persona) ");
		sql.append("FROM tbl_persona tp ");
		sql.append("WHERE NOT EXISTS( ");
		sql.append("    SELECT 1 ");
		sql.append("    FROM tbl_inscripciones ti ");
		sql.append("    WHERE ti.semestre = 1 ");
		sql.append("    AND ti.Idpersona = tp.id_persona ");
		sql.append(") ");
		sql.append("AND tp.id_persona = :idPersona ");
		sql.append("AND NOT EXISTS( ");
		sql.append("    SELECT 1 ");
		sql.append("    FROM rel_persona_bajas rpb ");
		sql.append("    WHERE rpb.id_persona = tp.id_persona ");
		sql.append(")");

		List<?> resultados = entityManager.createNativeQuery(sql.toString()).setParameter("idPersona", idPersona)
				.getResultList();

		// Si no hay resultados, retorna false (no es nuevo ingreso)
		if (resultados.isEmpty()) {
			return false;
		}

		// Obtener el count directamente
		Object resultado = resultados.get(0);
		Long count = getLongValue(resultado);

		// Retorna true si count > 0 (es nuevo ingreso), false si count = 0
		return count != null && count > 0;
	}

	@Override
	public Boolean existeInscripcionPrevia(Long idPersona, Long idPlanPersona, Long idConvocatoriaPersona,
			Date fechaActual) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tpi.proceso_inscripcion_id ");
		sql.append("FROM tbl_inscripciones ti ");
		sql.append(
				"INNER JOIN tbl_procesos_inscripcion tpi ON ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_fin ");
		sql.append(
				"INNER JOIN rel_proceso_inscipcion_planesyprogramas rgp ON rgp.id_programa = ti.idprograma AND rgp.id_plan = ti.idplan AND tpi.proceso_inscripcion_id = rgp.id_proceso_inscripcion ");
		sql.append("WHERE :fechaActual > tpi.fecha_inicio AND :fechaActual < tpi.fecha_fin ");
		sql.append("AND tpi.convocatoria_id = :idConvocatoriaPersona ");
		sql.append("AND ti.Idpersona = :idPersona ");
		sql.append("AND ti.idplan = :idPlanPersona ");
		sql.append("AND tpi.estatus = 1");

		List<?> resultados = entityManager.createNativeQuery(sql.toString()).setParameter("idPersona", idPersona)
				.setParameter("idPlanPersona", idPlanPersona)
				.setParameter("idConvocatoriaPersona", idConvocatoriaPersona).setParameter("fechaActual", fechaActual)
				.getResultList();

		return !resultados.isEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InscripcionMateriasCursadasDTO> obtenerMateriasCursadas(Long idPersona) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    tpl.nombre AS plan, ");
		sql.append("    tpl.id_plan AS id_plan, ");
		sql.append("    fd.nombre_tentativo AS programa, ");
		sql.append("    fd.id_programa AS id_programa, ");
		sql.append("    fd.cve_programa AS clave_programa, ");
		sql.append("    tmc.nombre AS subestructura, ");
		sql.append(
				"    (SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre) AS estructura, ");
		sql.append("    rgp.calificacion_final AS calificacion_final, ");
		sql.append("    IF(rgp.calificacion_final >= fd.calificacion_min_aprobatoria, 1, 0) AS estatus_aprobacion, ");
		sql.append("    fd.creditos AS creditos, ");
		sql.append("    fd.tipo AS tipoPrgrama ");
		sql.append("FROM tbl_persona tp ");
		sql.append(
				"INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona AND calificacion_final IS NOT NULL ");
		sql.append("INNER JOIN tbl_grupos tg ON tg.id = rgp.id_grupo ");
		sql.append("INNER JOIN tbl_eventos te ON te.id_evento = tg.id_evento ");
		sql.append("INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = te.id_programa ");
		sql.append("INNER JOIN tbl_planes tpl ON tpl.id_plan = fd.id_plan ");
		sql.append("INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion ");
		sql.append("WHERE tp.id_persona = :idPersona");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString()).setParameter("idPersona", idPersona)
				.getResultList();

		List<InscripcionMateriasCursadasDTO> listaDTO = new ArrayList<>();

		for (Object[] row : resultados) {
			listaDTO.add(mapearInscripcionMateriasCursadas(row));
		}

		return listaDTO;
	}

	private InscripcionMateriasCursadasDTO mapearInscripcionMateriasCursadas(Object[] row) {
		InscripcionMateriasCursadasDTO dto = new InscripcionMateriasCursadasDTO();
		dto.setPlan((String) row[0]);
		dto.setIdPlan(getLongValue(row[1]));
		dto.setPrograma((String) row[2]);
		dto.setIdPrograma(getLongValue(row[3]));
		dto.setClavePrograma((String) row[4]);
		dto.setSubestructura((String) row[5]);
		dto.setEstructura((String) row[6]);
		dto.setCalificacionFinal(getDoubleValue(row[7]));
		dto.setEstatusAprobacion(getIntegerValue(row[8]));
		dto.setCreditos(getIntegerValue(row[9]));
		dto.setTipoPrograma((String)(row[10]));

		return dto;
	}

	private Double getDoubleValue(Object value) {
		if (value == null)
			return null;
		if (value instanceof Number) {
			return ((Number) value).doubleValue();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InscripcionMateriasReprobadasDTO> obtenerMateriasCursadasReprobadas(Long idPersona) {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT COUNT(IF(rgp.calificacion_final < fd.calificacion_min_aprobatoria, 1, 0)) AS intentos_reprobados, ");
		sql.append("       tpl.id_plan AS id_plan, ");
		sql.append("       fd.id_programa AS id_programa, ");
		sql.append("       fd.cve_programa AS clave_programa, ");
		sql.append("       tmc.nombre AS subestructura, ");
		sql.append("       ( ");
		sql.append("        	SELECT ");
		sql.append("        		tmc2.nombre ");
		sql.append("        	FROM ");
		sql.append("        		tbl_malla_curricular tmc2");
		sql.append("        	WHERE ");
		sql.append("        		tmc2.id = tmc.id_padre");
		sql.append("       ) AS estructura, ");
		sql.append("       fd.id_programa_antecedente AS id_programa_antecedente, ");
		sql.append("       fd.nombre_tentativo AS nombrePrograma, ");
		sql.append("       fd.tipo AS tipo ");
		sql.append("FROM tbl_persona tp ");
		sql.append(
				"INNER JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona AND rgp.calificacion_final IS NOT NULL ");
		sql.append("INNER JOIN tbl_grupos tg ON tg.id = rgp.id_grupo ");
		sql.append("INNER JOIN tbl_eventos te ON te.id_evento = tg.id_evento ");
		sql.append("INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = te.id_programa ");
		sql.append("INNER JOIN tbl_planes tpl ON tpl.id_plan = fd.id_plan ");
		sql.append("INNER JOIN tbl_malla_curricular tmc ON tmc.id = fd.id_eje_capacitacion ");
		sql.append("WHERE tp.id_persona = :id_persona ");
		sql.append("  AND rgp.calificacion_final < fd.calificacion_min_aprobatoria ");
		sql.append("  AND NOT EXISTS( ");
		sql.append("      SELECT 1 ");
		sql.append("      FROM rel_grupo_participante rgp2 ");
		sql.append("      INNER JOIN tbl_grupos tg2 ON tg2.id = rgp2.id_grupo ");
		sql.append("      INNER JOIN tbl_eventos te2 ON te2.id_evento = tg2.id_evento ");
		sql.append("      INNER JOIN tbl_ficha_descriptiva_programa fd2 ON fd2.id_programa = te2.id_programa ");
		sql.append("      WHERE fd2.cve_programa = fd.cve_programa ");
		sql.append("        AND te2.id_programa = fd.id_programa ");
		sql.append("        AND rgp2.calificacion_final > fd2.calificacion_min_aprobatoria ");
		sql.append("        AND rgp2.id_persona_participante = rgp.id_persona_participante ");
		sql.append("  ) ");
		sql.append("GROUP BY tpl.id_plan, fd.id_programa, fd.cve_programa");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString())
				.setParameter("id_persona", idPersona).getResultList();

		List<InscripcionMateriasReprobadasDTO> listaDTO = new ArrayList<>();

		for (Object[] row : resultados) {
			listaDTO.add(mapearInscripcionMateriasReprobadas(row));
		}

		return listaDTO;
	}

	private InscripcionMateriasReprobadasDTO mapearInscripcionMateriasReprobadas(Object[] row) {
		InscripcionMateriasReprobadasDTO dto = new InscripcionMateriasReprobadasDTO();
		dto.setIntentosReprobados(getLongValue(row[0]));
		dto.setIdPlan(getLongValue(row[1]));
		dto.setIdPrograma(getLongValue(row[2]));
		dto.setClavePrograma((String) row[3]);
		dto.setSubestructura((String) row[4]);
		dto.setEstructura((String) row[5]);
		dto.setIdProgramaAntecedente(getLongValue(row[6]));
		dto.setNombrePrograma((String) row[7]);
		dto.setTipoPrograma((String) row[8]);
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InscripcionBajasDTO> obtenerBajasDeMateriasSolicitadas(Long idPersona) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT rpb.id_plan, ");
		sql.append("       rpb.id_programa, ");
		sql.append("       tfdp.cve_programa, ");
		sql.append("       ctb.id_tipo_baja, ");
		sql.append("       ctb.nombre AS tipo_baja, ");
		sql.append("       ctb.fecha_modificacion ");
		sql.append("FROM rel_persona_bajas rpb ");
		sql.append("INNER JOIN rel_motivo_baja rmb ON rmb.id_motivo_baja = rpb.motivo_baja_id ");
		sql.append("INNER JOIN cat_tipo_bajas ctb ON ctb.id_tipo_baja = rmb.tipo_baja_id ");
		sql.append("INNER JOIN tbl_ficha_descriptiva_programa tfdp ON tfdp.id_programa = rpb.id_programa ");
		sql.append("WHERE rpb.id_persona = :id_persona");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString())
				.setParameter("id_persona", idPersona).getResultList();

		List<InscripcionBajasDTO> listaDTO = new ArrayList<>();

		for (Object[] row : resultados) {
			listaDTO.add(mapearInscripcionBajas(row));
		}

		return listaDTO;
	}

	private InscripcionBajasDTO mapearInscripcionBajas(Object[] row) {
		InscripcionBajasDTO dto = new InscripcionBajasDTO();
		dto.setIdPlan(getLongValue(row[0]));
		dto.setIdPrograma(getLongValue(row[1]));
		dto.setClavePrograma((String) row[2]);
		dto.setIdTipoBaja(getLongValue(row[3]));
		dto.setTipoBaja((String) row[4]);
		dto.setFechaModificacion(getLocalDateTimeValue(row[5]));

		return dto;
	}

	private LocalDateTime getLocalDateTimeValue(Object value) {
		if (value == null)
			return null;
		if (value instanceof java.sql.Timestamp) {
			return ((java.sql.Timestamp) value).toLocalDateTime();
		}
		if (value instanceof java.util.Date) {
			return new java.sql.Timestamp(((java.util.Date) value).getTime()).toLocalDateTime();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<CreditosTotalesPlanDTO> obtenerCreditosTotalesPorPlan(Long idPlan) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    rctp.id_creditos_totales, ");
		sql.append("    rctp.id_plan, ");
		sql.append("    rctp.total_creditos ");
		sql.append("FROM rel_creditos_totales_por_plan rctp ");
		sql.append("WHERE rctp.id_plan = :id_plan");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString()).setParameter("id_plan", idPlan)
				.getResultList();

		if (resultados.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(mapearCreditosPlan(resultados.get(0)));
	}

	private CreditosTotalesPlanDTO mapearCreditosPlan(Object[] row) {
		CreditosTotalesPlanDTO dto = new CreditosTotalesPlanDTO();
		dto.setIdCreditosTotales(getLongValue(row[0]));
		dto.setIdPlan(getLongValue(row[1]));
		dto.setTotalCreditos(getLongValue(row[2]));

		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InscripcionMateriasDTO> obtenerMateriasElectivasDeOtrosPlanes(Long idPlanPersona, Date fechaActual,
			Long idConvocatoria, String semestreCinco, String semestreSeis) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("    tp.identificador AS clave_plan, ");
		sql.append("    tfd.identificador_final AS clave_programa, ");
		sql.append("    tp.id_plan AS id_plan, ");
		sql.append("    tp.nombre AS nombre_plan, ");
		sql.append("    tfd.id_programa AS id_programa, ");
		sql.append("    tfd.nombre_tentativo AS programa, ");
		sql.append("    tmc.nombre AS subestructura, ");
		sql.append("    tmc2.nombre AS estructura, ");
		sql.append("    cne.nombre AS nivel_ensenanza, ");
		sql.append("    cdp.nombre AS division, ");
		sql.append("    'Electiva' AS tipo_programa, ");
		sql.append("    tfd.id_programa_antecedente AS seriada ");
		sql.append("FROM tbl_procesos_inscripcion tpi ");
		sql.append(
				"INNER JOIN rel_proceso_inscipcion_planesyprogramas rpip ON tpi.proceso_inscripcion_id = rpip.id_proceso_inscripcion ");
		sql.append("INNER JOIN tbl_planes tp ON tp.id_plan = rpip.id_plan ");
		sql.append(
				"INNER JOIN tbl_ficha_descriptiva_programa tfd ON tfd.id_plan = tp.id_plan AND rpip.id_programa = tfd.id_programa ");
		sql.append("INNER JOIN tbl_malla_curricular tmc ON tmc.id = tfd.id_eje_capacitacion ");
		sql.append("INNER JOIN tbl_malla_curricular tmc2 ON tmc2.id = tmc.id_padre ");
		sql.append(
				"    AND (tmc2.nombre LIKE CONCAT('%', :semestre_cinco, '%') OR tmc2.nombre LIKE CONCAT('%', :semestre_seis, '%')) ");
		sql.append("INNER JOIN cat_nivel_ensenanza_programa cne ON cne.id = tp.id_nivel_ensenanza ");
		sql.append("INNER JOIN cat_divisiones_plan cdp ON cdp.id = tp.id_divisiones_plan ");
		sql.append("WHERE tfd.tipo = 'Obligatoria' ");
		sql.append("  AND rpip.id_plan NOT IN (:id_plan_persona) ");
		sql.append("  AND :fecha_actual >= tpi.fecha_inicio ");
		sql.append("  AND :fecha_actual <= tpi.fecha_fin ");
		sql.append("  AND tpi.convocatoria_id = :id_convocatoria");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString())
				.setParameter("id_plan_persona", idPlanPersona).setParameter("semestre_cinco", semestreCinco)
				.setParameter("semestre_seis", semestreSeis).setParameter("fecha_actual", fechaActual)
				.setParameter("id_convocatoria", idConvocatoria).getResultList();

		List<InscripcionMateriasDTO> listaDTO = new ArrayList<>();

		for (Object[] row : resultados) {
			listaDTO.add(mapearInscripcionMaterias(row));
		}

		return listaDTO;
	}

	private InscripcionMateriasDTO mapearInscripcionMaterias(Object[] row) {
		InscripcionMateriasDTO dto = new InscripcionMateriasDTO();
		dto.setClavePlan((String) row[0]);
		dto.setClavePrograma((String) row[1]);
		dto.setIdPlan(getLongValue(row[2]));
		dto.setNombrePlan((String) row[3]);
		dto.setIdPrograma(getLongValue(row[4]));
		dto.setNombreTentativoPrograma((String) row[5]);
		dto.setSubestructura((String) row[6]);
		dto.setEstructura((String) row[7]);
		dto.setNivelEnsenanza((String) row[8]);
		dto.setDivision((String) row[9]);
		dto.setTipoPrograma((String) row[10]);
		dto.setIdProgramaAntecedente(getLongValue(row[11]));

		dto.setCheck(false);
		dto.setDisabled(false);

		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AprobacionAsignaturasPorSemestreDTO> obtenerAprobacionAsignaturasPorSemestre(Long idPlan,
			Long idPersona) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("       tmcu2.id, ");
		sql.append("       tmcu2.nombre, ");
		sql.append("       (SELECT COUNT(tmll2.nombre) ");
		sql.append("        FROM tbl_malla_curricular tmll ");
		sql.append("        JOIN tbl_malla_curricular tmll2 ");
		sql.append("          ON tmll2.id_padre = tmll.id ");
		sql.append("         AND tmll2.activo = 1 ");
		sql.append("        JOIN tbl_ficha_descriptiva_programa tlfdp ");
		sql.append("          ON tlfdp.id_categoria_mdl = tmll2.id_categoria_mdl ");
		sql.append("         AND tlfdp.tipo = 'Obligatoria' ");
		sql.append("        WHERE tmll.id_padre = tmcu2.id ");
		sql.append("       ) AS asignaturasObligatoriasPorPrograma, ");
		sql.append("       (SELECT COUNT(rgp2.id_persona_participante) ");
		sql.append("        FROM rel_grupo_participante rgp2 ");
		sql.append("        INNER JOIN tbl_grupos tg2 ");
		sql.append("          ON tg2.id = rgp2.id_grupo ");
		sql.append("        INNER JOIN tbl_eventos te2 ");
		sql.append("          ON te2.id_evento = tg2.id_evento ");
		sql.append("        INNER JOIN tbl_ficha_descriptiva_programa fd2 ");
		sql.append("          ON fd2.id_plan = :idPlan ");
		sql.append("         AND fd2.id_programa = te2.id_programa ");
		sql.append("         AND fd2.tipo = 'Obligatoria' ");
		sql.append("        INNER JOIN tbl_malla_curricular tmcc ");
		sql.append("          ON tmcc.id = fd2.id_eje_capacitacion ");
		sql.append("        INNER JOIN tbl_malla_curricular tmcc2 ");
		sql.append("          ON tmcc2.id = tmcc.id_padre ");
		sql.append("        WHERE rgp2.calificacion_final >= fd2.calificacion_min_aprobatoria ");
		sql.append("          AND tmcc2.nombre = tmcu2.nombre ");
		sql.append("          AND rgp2.id_persona_participante = :idPersona ");
		sql.append("       ) AS asignaturasAprobadas ");
		sql.append("FROM tbl_malla_curricular tmcu ");
		sql.append("INNER JOIN tbl_malla_curricular tmcu2 ");
		sql.append("        ON tmcu2.id_padre = tmcu.id ");
		sql.append("WHERE tmcu.id_plan = :idPlan");

		List<Object[]> resultados = entityManager.createNativeQuery(sql.toString()).setParameter("idPlan", idPlan)
				.setParameter("idPersona", idPersona).getResultList();

		return resultados.stream().map(this::mapearAprobacionAsignaturasPorSemestre).collect(Collectors.toList());
	}

	private AprobacionAsignaturasPorSemestreDTO mapearAprobacionAsignaturasPorSemestre(Object[] row) {
		AprobacionAsignaturasPorSemestreDTO dto = new AprobacionAsignaturasPorSemestreDTO();
		dto.setIdMallaCurricular(getLongValue(row[0])); // tmcu2.id
		dto.setSemestre((String) row[1]); // tmcu2.nombre
		dto.setAsignaturasObligatoriasPorPrograma(getLongValue(row[2])); // subquery COUNT(...)
		dto.setAsignaturasAprobadas(getLongValue(row[3])); // subquery COUNT(...)
		return dto;
	}

}
