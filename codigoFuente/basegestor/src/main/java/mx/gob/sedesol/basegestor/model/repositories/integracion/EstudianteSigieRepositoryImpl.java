package mx.gob.sedesol.basegestor.model.repositories.integracion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.dto.integracion.EstudianteSigieConsultaDTO;

@Repository
@Transactional(readOnly = true)
public class EstudianteSigieRepositoryImpl implements EstudianteSigieRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<EstudianteSigieConsultaDTO> consultarPorMatricula(String matricula) {
        String sql = "SELECT ps.matricula_sige AS matricula, p.sso_nombre AS nombre, "
                + "p.sso_apellidoPaterno AS primer_apellido, "
                + "p.sso_apellidoMaterno AS segundo_apellido, p.sso_curp AS curp, "
                + "plan.identificador AS clave_programa, "
                + "ps.programa_educativo_sige AS nombre_programa, nivel.nombre AS nivel, "
                + "plan.identificador AS identificador_plan, "
                + "creditos.total_creditos AS creditos_totales, "
                + "COALESCE(aprobados.creditos_cubiertos, 0) AS creditos_cubiertos, "
                + "(SELECT rpc.sso_correoElectronico FROM rel_persona_correo rpc "
                + " WHERE rpc.id_persona = p.id_persona AND rpc.activo = 1 "
                + " ORDER BY COALESCE(rpc.nivel_prioridad, 999), rpc.id_persona_correo LIMIT 1) "
                + "AS correo_institucional, p.activo AS activo, "
                + "COALESCE(bajas.prioridad_baja, 0) AS prioridad_baja, "
                + "COALESCE(irregulares.tiene_reprobada, 0) AS tiene_reprobada "
                + "FROM tbl_persona p "
                + "INNER JOIN tbl_persona_sige ps ON ps.matricula_sige = p.sso_idUsuario "
                + "INNER JOIN tbl_persona_aspirante aspirante ON aspirante.id_persona = p.id_persona "
                + " AND aspirante.id_persona_aspirante = (SELECT MAX(a2.id_persona_aspirante) "
                + " FROM tbl_persona_aspirante a2 WHERE a2.id_persona = p.id_persona) "
                + "INNER JOIN tbl_planes plan ON plan.id_plan = aspirante.id_plan "
                + "LEFT JOIN cat_nivel_ensenanza_programa nivel ON nivel.id = plan.id_nivel_ensenanza "
                + "LEFT JOIN tbl_ficha_descriptiva_programa prog ON prog.id_plan = plan.id_plan "
                + " AND prog.cve_programa IS NOT NULL AND prog.cve_programa <> '' "
                + "LEFT JOIN rel_creditos_totales_por_plan creditos ON creditos.id_plan = plan.id_plan "
                + "LEFT JOIN (SELECT aprobadas.id_persona, aprobadas.id_plan, SUM(aprobadas.creditos) creditos_cubiertos "
                + " FROM (SELECT rgp.id_persona_participante id_persona, fd.id_plan, fd.id_programa, "
                + " MAX(fd.creditos) creditos "
                + " FROM rel_grupo_participante rgp "
                + " INNER JOIN tbl_grupos g ON g.id = rgp.id_grupo "
                + " INNER JOIN tbl_eventos e ON e.id_evento = g.id_evento "
                + " INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa "
                + " WHERE rgp.calificacion_final IS NOT NULL "
                + " GROUP BY rgp.id_persona_participante, fd.id_plan, fd.id_programa, fd.calificacion_min_aprobatoria "
                + " HAVING MAX(rgp.calificacion_final) >= CAST(fd.calificacion_min_aprobatoria AS DECIMAL(10,2))) aprobadas "
                + " GROUP BY aprobadas.id_persona, aprobadas.id_plan) aprobados "
                + " ON aprobados.id_persona = p.id_persona AND aprobados.id_plan = plan.id_plan "
                + "LEFT JOIN (SELECT rpb.id_persona, rpb.id_plan, "
                + " MAX(CASE WHEN rmb.tipo_baja_id IN (3,6) THEN 2 "
                + " WHEN rmb.tipo_baja_id IN (2,7,8) THEN 1 ELSE 0 END) prioridad_baja "
                + " FROM rel_persona_bajas rpb "
                + " INNER JOIN rel_motivo_baja rmb ON rmb.id_motivo_baja = rpb.motivo_baja_id "
                + " WHERE rpb.contabilizar = 1 "
                + " AND (rmb.tipo_baja_id IN (3,6) OR (rmb.tipo_baja_id IN (2,7,8) "
                + " AND rpb.id_periodo = (SELECT per.id_periodo FROM tbl_inscripciones ti "
                + " INNER JOIN tbl_periodos_inscripcion per "
                + " ON ti.fecha_registro BETWEEN per.fecha_inicio AND per.fecha_finalizacion "
                + " WHERE ti.idpersona = rpb.id_persona AND ti.idplan = rpb.id_plan "
                + " ORDER BY per.fecha_inicio DESC, per.id_periodo DESC LIMIT 1))) "
                + " GROUP BY rpb.id_persona, rpb.id_plan) bajas "
                + " ON bajas.id_persona = p.id_persona AND bajas.id_plan = plan.id_plan "
                + "LEFT JOIN (SELECT reprobadas.id_persona, reprobadas.id_plan, 1 tiene_reprobada "
                + " FROM (SELECT rgp.id_persona_participante id_persona, fd.id_plan, fd.id_programa "
                + " FROM rel_grupo_participante rgp "
                + " INNER JOIN tbl_grupos g ON g.id = rgp.id_grupo "
                + " INNER JOIN tbl_eventos e ON e.id_evento = g.id_evento "
                + " INNER JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa "
                + " WHERE rgp.calificacion_final IS NOT NULL "
                + " AND NOT EXISTS (SELECT 1 FROM rel_persona_bajas rpb "
                + " WHERE rpb.id_persona = rgp.id_persona_participante "
                + " AND rpb.id_plan = fd.id_plan AND rpb.id_evento = e.id_evento "
                + " AND rpb.contabilizar = 1) "
                + " GROUP BY rgp.id_persona_participante, fd.id_plan, fd.id_programa, "
                + " fd.calificacion_min_aprobatoria "
                + " HAVING MAX(rgp.calificacion_final) "
                + " < CAST(fd.calificacion_min_aprobatoria AS DECIMAL(10,2))) reprobadas "
                + " GROUP BY reprobadas.id_persona, reprobadas.id_plan) irregulares "
                + " ON irregulares.id_persona = p.id_persona AND irregulares.id_plan = plan.id_plan "
                + "WHERE p.sso_idUsuario = :matricula ORDER BY prog.id_programa DESC LIMIT 1";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("matricula", matricula);
        query.setHint("javax.persistence.query.timeout", obtenerTimeoutMilisegundos());
        List<Object[]> rows = query.getResultList();
        if (rows.isEmpty()) {
            return Optional.empty();
        }
        Object[] row = rows.get(0);
        EstudianteSigieConsultaDTO dto = new EstudianteSigieConsultaDTO();
        dto.setMatricula(texto(row[0]));
        dto.setNombre(texto(row[1]));
        dto.setPrimerApellido(texto(row[2]));
        dto.setSegundoApellido(texto(row[3]));
        dto.setCurp(texto(row[4]));
        dto.setClavePrograma(texto(row[5]));
        dto.setNombrePrograma(texto(row[6]));
        dto.setNivel(texto(row[7]));
        dto.setIdentificadorPlan(texto(row[8]));
        dto.setCreditosTotales(decimal(row[9]));
        dto.setCreditosCubiertos(decimal(row[10]));
        dto.setCorreoInstitucional(texto(row[11]));
        dto.setActivo(row[12] == null ? null : Boolean.valueOf("1".equals(row[12].toString()) || Boolean.TRUE.equals(row[12])));
        dto.setPrioridadBaja(row[13] == null ? Integer.valueOf(0) : Integer.valueOf(row[13].toString()));
        dto.setIrregular(Boolean.valueOf(row[14] != null
                && ("1".equals(row[14].toString()) || Boolean.TRUE.equals(row[14]))));
        return Optional.of(dto);
    }

    private int obtenerTimeoutMilisegundos() {
        String value = System.getenv("SIGIE_API_QUERY_TIMEOUT_MS");
        if (value == null || value.trim().isEmpty()) { return 5000; }
        try { return Integer.parseInt(value); } catch (NumberFormatException e) { return 5000; }
    }

    private String texto(Object value) { return value == null ? null : value.toString(); }
    private BigDecimal decimal(Object value) { return value == null ? null : new BigDecimal(value.toString()); }
}
