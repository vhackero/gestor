package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaAplicacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PlanBajaDTO;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NuevaBajaRepository implements INuevaBajaRepository {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<NodoDTO> consultarTiposBajaActivos() {
        String consulta = "SELECT ctb.id_tipo_baja, ctb.nombre FROM cat_tipo_bajas ctb WHERE ctb.activo = 1";
        Query query = entityManager.createNativeQuery(consulta);
        List<Object[]> resultados = query.getResultList();
        List<NodoDTO> tiposBaja = new ArrayList<>();

        for (Object[] fila : resultados) {
            tiposBaja.add(new NodoDTO(obtenerEntero(fila[0]), obtenerCadena(fila[1])));
        }
        return tiposBaja;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlanBajaDTO> consultarPlanesActivos() {
        String consulta = "SELECT tmc.id, tmc.id_plan, tmc.nombre "
                + "FROM tbl_malla_curricular tmc "
                + "WHERE tmc.activo = 1 AND tmc.id_plan IS NOT NULL AND tmc.id_padre IS NULL";
        Query query = entityManager.createNativeQuery(consulta);
        List<Object[]> resultados = query.getResultList();
        List<PlanBajaDTO> planes = new ArrayList<>();

        for (Object[] fila : resultados) {
            planes.add(new PlanBajaDTO(obtenerEntero(fila[0]), obtenerEntero(fila[1]), obtenerCadena(fila[2])));
        }
        return planes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NodoDTO> consultarSemestresPorPlan(Long idPlan) {
        String consulta = "SELECT hijo.id, hijo.nombre "
                + "FROM tbl_malla_curricular hijo "
                + "WHERE hijo.id_padre = (";
        consulta += "    SELECT plan.id FROM tbl_malla_curricular plan "
                + "    WHERE plan.id_plan = :idPlan AND plan.id_padre IS NULL LIMIT 1";
        consulta += " ) AND hijo.activo = 1";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPlan", idPlan);
        List<Object[]> resultados = query.getResultList();
        List<NodoDTO> semestres = new ArrayList<>();

        for (Object[] fila : resultados) {
            semestres.add(new NodoDTO(obtenerEntero(fila[0]), obtenerCadena(fila[1])));
        }
        return semestres;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NodoDTO> consultarBloquesPorSemestre(Long idSemestre) {
        String consulta = "SELECT tmc.id, tmc.nombre FROM tbl_malla_curricular tmc "
                + "WHERE tmc.id_padre = :idSemestre AND tmc.activo = 1";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idSemestre", idSemestre);
        List<Object[]> resultados = query.getResultList();
        List<NodoDTO> bloques = new ArrayList<>();

        for (Object[] fila : resultados) {
            bloques.add(new NodoDTO(obtenerEntero(fila[0]), obtenerCadena(fila[1])));
        }
        return bloques;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NodoDTO> consultarProgramasPorEje(Long idEjeCapacitacion) {
        String consulta = "SELECT tfdp.id_programa, tfdp.nombre_tentativo FROM tbl_ficha_descriptiva_programa tfdp WHERE tfdp.id_eje_capacitacion = :idEjeCapacitacion";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idEjeCapacitacion", idEjeCapacitacion);
        List<Object[]> resultados = query.getResultList();
        List<NodoDTO> programas = new ArrayList<>();

        for (Object[] fila : resultados) {
            programas.add(new NodoDTO(obtenerEntero(fila[0]), obtenerCadena(fila[1])));
        }
        return programas;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> consultarPeriodosInscripcion() {
        String consulta = "SELECT tpi.nombre_periodo FROM tbl_periodos_inscripcion tpi";
        Query query = entityManager.createNativeQuery(consulta);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NodoDTO> consultarEventosPorPeriodoYPrograma(String nombrePeriodo, Long idPrograma, String matricula) {
        if (matricula == null) {
            return new ArrayList<>();
        }

        String consulta = "SELECT DISTINCT te.id_evento, te.nombre_ec FROM tbl_eventos te "
                + "JOIN tbl_grupos tg ON tg.id_evento = te.id_evento "
                + "JOIN rel_grupo_participante rgp ON rgp.id_grupo = tg.id "
                + "JOIN tbl_persona tp ON tp.id_persona = rgp.id_persona_participante "
                + "WHERE tp.sso_idUsuario = :matricula "
                + "AND te.cve_evento_cap LIKE CONCAT('%',:nombrePeriodo,'%') AND te.id_programa = :idPrograma";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("nombrePeriodo", nombrePeriodo);
        query.setParameter("idPrograma", idPrograma);
        query.setParameter("matricula", matricula);
        List<Object[]> resultados = query.getResultList();
        List<NodoDTO> eventos = new ArrayList<>();

        for (Object[] fila : resultados) {
            eventos.add(new NodoDTO(obtenerEntero(fila[0]), obtenerCadena(fila[1])));
        }
        return eventos;
    }

    @Override
    @Transactional
    public Long insertarMotivoBaja(Long idTipoBaja, String descripcion) {
        String consulta = "INSERT INTO rel_motivo_baja (tipo_baja_id, descripcion) VALUES (:idTipoBaja, :descripcion)";
        entityManager.createNativeQuery(consulta)
                .setParameter("idTipoBaja", idTipoBaja)
                .setParameter("descripcion", descripcion)
                .executeUpdate();

        Query query = entityManager.createNativeQuery("SELECT LAST_INSERT_ID()");
        return obtenerLong(query.getSingleResult());
    }

    @Override
    public Long obtenerIdProcesoBaja() {
        String consulta = "SELECT tpi.id_tipo_proceso FROM tbl_procesos_inscripcion tpi WHERE  tpi.nombre LIKE 'bajas' limit 1";
        Query query = entityManager.createNativeQuery(consulta);
        Object resultado = query.getSingleResult();
        return obtenerLong(resultado);
    }

    @Override
    public Long obtenerIdPersonaPorMatricula(String matricula) {
        String consulta = "SELECT tp.id_persona FROM tbl_persona tp WHERE tp.sso_idUsuario = :matricula LIMIT 1";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("matricula", matricula);
        List<?> resultados = query.getResultList();
        return resultados.isEmpty() ? null : obtenerLong(resultados.get(0));
    }

    @Override
    @Transactional
    public void actualizarPersonaInactiva(Long idPersona) {
        String consulta = "UPDATE tbl_persona SET activo = 0 WHERE id_persona = :idPersona";
        entityManager.createNativeQuery(consulta)
                .setParameter("idPersona", idPersona)
                .executeUpdate();
    }

    @Override
    public BajaMatriculacionDTO consultarMatriculacionPorEvento(String matricula, Long idEvento) {
        if (idEvento == null) {
            return null;
        }

        String consulta = "SELECT\n"
                + "    (SELECT tpl.id_plan FROM tbl_planes tpl\n"
                + "        JOIN tbl_ficha_descriptiva_programa tfdp ON tpl.id_plan = tfdp.id_plan\n"
                + "        JOIN tbl_eventos te ON te.id_programa = tfdp.id_programa AND te.id_evento = :idEvento) AS id_plan,\n"
                + "    (SELECT tfdp.id_programa FROM tbl_ficha_descriptiva_programa tfdp\n"
                + "        JOIN tbl_eventos te ON te.id_programa = tfdp.id_programa AND te.id_evento = :idEvento) AS id_programa,\n"
                + "    (SELECT te.id_evento FROM tbl_eventos te\n"
                + "        JOIN tbl_grupos tg ON tg.id_evento = te.id_evento\n"
                + "        JOIN rel_grupo_participante rgp ON tg.id = rgp.id_grupo AND rgp.id_persona_participante = tp.id_persona\n"
                + "     WHERE tg.id_evento = :idEvento) AS id_evento,\n"
                + "    (SELECT tg.id FROM tbl_grupos tg\n"
                + "              JOIN rel_grupo_participante rgp ON tg.id = rgp.id_grupo AND rgp.id_persona_participante = tp.id_persona\n"
                + "              WHERE tg.id_evento = :idEvento ) AS id_grupo\n"
                + "FROM tbl_persona tp WHERE tp.sso_idUsuario = :matricula";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("matricula", matricula);
        query.setParameter("idEvento", idEvento);
        List<Object[]> resultados = query.getResultList();

        if (resultados.isEmpty()) {
            return null;
        }

        Object[] fila = resultados.get(0);
        BajaMatriculacionDTO dto = new BajaMatriculacionDTO();
        dto.setIdPlan(obtenerLong(fila[0]));
        dto.setIdPrograma(obtenerLong(fila[1]));
        dto.setIdEvento(obtenerLong(fila[2]));
        dto.setIdGrupo(obtenerLong(fila[3]));
        return dto;
    }

    @Override
    public Integer obtenerIdUsuarioMoodle(Long idPersona, Long idEvento) {
        String consulta = "SELECT rppm.id_persona_moodle \n"
                + "FROM tbl_persona tp\n"
                + "JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona\n"
                + "JOIN tbl_grupos tg ON tg.id = rgp.id_grupo\n"
                + "JOIN tbl_eventos te ON te.id_evento = tg.id_evento\n"
                + "JOIN rel_personas_plataformas_moodle rppm ON rppm.id_persona = tp.id_persona \n"
                + "    AND rppm.id_plataforma_moodle = te.id_plataforma_lms_borrador\n"
                + "WHERE tp.id_persona = :idPersona\n"
                + "    AND te.id_evento = :idEvento";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPersona", idPersona);
        query.setParameter("idEvento", idEvento);
        List<?> resultados = query.getResultList();
        return resultados.isEmpty() ? null : obtenerEntero(resultados.get(0));
    }

    @Override
    public Integer obtenerIdUsuarioMoodlePorPersona(Long idPersona) {
        String consulta = "SELECT rppm.id_persona_moodle \n"
                + "FROM rel_personas_plataformas_moodle rppm \n"
                + "WHERE rppm.id_persona = :idPersona LIMIT 1";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPersona", idPersona);
        List<?> resultados = query.getResultList();
        return resultados.isEmpty() ? null : obtenerEntero(resultados.get(0));
    }

    @Override
    public Integer obtenerIdCursoMoodle(Long idEvento) {
        String consulta = "SELECT te.id_curso_lms_borrador FROM tbl_eventos te WHERE te.id_evento = :idEvento LIMIT 1";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idEvento", idEvento);
        List<?> resultados = query.getResultList();
        return resultados.isEmpty() ? null : obtenerEntero(resultados.get(0));
    }

    @Override
    public Integer obtenerIdPlataformaMoodle(Long idEvento) {
        String consulta = "SELECT te.id_plataforma_lms_borrador FROM tbl_eventos te WHERE te.id_evento = :idEvento LIMIT 1";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idEvento", idEvento);
        List<?> resultados = query.getResultList();
        return resultados.isEmpty() ? null : obtenerEntero(resultados.get(0));
    }

    @Override
    public Integer obtenerIdPlataformaMoodlePorPersona(Long idPersona) {
        String consulta = "SELECT rppm.id_plataforma_moodle "
                + "FROM rel_personas_plataformas_moodle rppm "
                + "WHERE rppm.id_persona = :idPersona "
                + "LIMIT 1";
        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPersona", idPersona);
        List<?> resultados = query.getResultList();
        return resultados.isEmpty() ? null : obtenerEntero(resultados.get(0));
    }

    @Override
    public Integer[] obtenerIdPersonaYPlataformaMoodle(Long idPersona) {
        String consulta = "SELECT rppm.id_persona_moodle, rppm.id_plataforma_moodle\n"
                + "      FROM tbl_persona tp\n"
                + "      JOIN rel_personas_plataformas_moodle rppm \n"
                + "          ON rppm.id_persona = tp.id_persona\n"
                + "      WHERE tp.id_persona = :idPersonaMatriculaIngresada";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPersonaMatriculaIngresada", idPersona);
        List<Object[]> resultados = query.getResultList();

        if (resultados.isEmpty()) {
            return null;
        }

        Object[] fila = resultados.get(0);
        return new Integer[]{obtenerEntero(fila[0]), obtenerEntero(fila[1])};
    }

    @Override
    @Transactional
    public void insertarBaja(BajaAplicacionDTO bajaAplicacionDTO) {
        String consulta = "INSERT INTO rel_persona_bajas (id_persona, motivo_baja_id, proceso_id, id_plan, id_programa, id_evento, id_grupo, id_user_enrolments_lms, usuario_modifico, contabilizar, solicitud)"
                + " VALUES (:idPersona, :motivoBajaId, :procesoId, :idPlan, :idPrograma, :idEvento, :idGrupo, :idUserEnrolmentsLms, :usuarioModifico, :contabilizar, :numeroSolicitud)";

        entityManager.createNativeQuery(consulta)
                .setParameter("idPersona", bajaAplicacionDTO.getIdPersona())
                .setParameter("motivoBajaId", bajaAplicacionDTO.getMotivoBajaId())
                .setParameter("procesoId", bajaAplicacionDTO.getProcesoId())
                .setParameter("idPlan", bajaAplicacionDTO.getIdPlan())
                .setParameter("idPrograma", bajaAplicacionDTO.getIdPrograma())
                .setParameter("idEvento", bajaAplicacionDTO.getIdEvento())
                .setParameter("idGrupo", bajaAplicacionDTO.getIdGrupo())
                .setParameter("idUserEnrolmentsLms", bajaAplicacionDTO.getIdUserEnrolmentsLms())
                .setParameter("usuarioModifico", bajaAplicacionDTO.getQuienAplicaBaja() != null ? bajaAplicacionDTO.getQuienAplicaBaja() : "-")
                .setParameter("contabilizar", bajaAplicacionDTO.getContabilizar())
                .setParameter("numeroSolicitud", bajaAplicacionDTO.getNumeroSolicitud())
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public BajaMatriculaDetalleDTO consultarDatosPorMatricula(String matricula) {
        String consulta = "SELECT DISTINCT "
                + " CONCAT(tp.sso_nombre, ' ', tp.sso_apellidoPaterno, IF(tp.sso_apellidoMaterno != '', CONCAT(' ', tp.sso_apellidoMaterno),'')) AS nombre_completo, "
                + " tpa.id_plan, "
                + " sem.id as id_semestre, "
                + " bloque.id as id_bloque, "
                + " tfdp.id_programa, "
                + " tpi.nombre_periodo, "
                + " te.id_evento "
                + "FROM tbl_persona tp "
                + "JOIN tbl_persona_aspirante tpa ON tpa.id_persona = tp.id_persona "
                + "LEFT JOIN rel_grupo_participante rgp ON rgp.id_persona_participante = tp.id_persona "
                + "LEFT JOIN tbl_grupos tg ON tg.id = rgp.id_grupo "
                + "LEFT JOIN tbl_eventos te ON te.id_evento = tg.id_evento "
                + "LEFT JOIN tbl_ficha_descriptiva_programa tfdp ON tfdp.id_programa = te.id_programa "
                + "LEFT JOIN tbl_malla_curricular bloque ON bloque.id = tfdp.id_eje_capacitacion "
                + "LEFT JOIN tbl_malla_curricular sem ON sem.id = bloque.id_padre "
                + "LEFT JOIN tbl_periodos_inscripcion tpi ON te.cve_evento_cap LIKE CONCAT('%', tpi.nombre_periodo, '%') "
                + "WHERE tp.sso_idUsuario = :matricula "
                + "ORDER BY te.id_evento DESC "
                + "LIMIT 1";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("matricula", matricula);
        List<Object[]> resultados = query.getResultList();

        if (resultados.isEmpty()) {
            return null;
        }

        Object[] fila = resultados.get(0);
        BajaMatriculaDetalleDTO dto = new BajaMatriculaDetalleDTO();
        dto.setNombreCompleto(obtenerCadena(fila[0]));
        dto.setIdPlan(obtenerLong(fila[1]));
        dto.setIdSemestre(obtenerLong(fila[2]));
        dto.setIdBloque(obtenerLong(fila[3]));
        dto.setIdPrograma(obtenerLong(fila[4]));
        dto.setPeriodo(obtenerCadena(fila[5]));
        dto.setIdEvento(obtenerLong(fila[6]));
        return dto;
    }

    @Override
    public boolean validarPlanYProgramaPorMatricula(String matricula, Long idPlan, Long idPrograma) {
        if (idPlan == null && idPrograma == null) {
            return true;
        }

        if (matricula == null) {
            return false;
        }

        String consulta = "SELECT COUNT(*) FROM tbl_persona tp "
                + "LEFT JOIN tbl_persona_aspirante tpa ON tpa.id_persona = tp.id_persona "
                + "WHERE tp.sso_idUsuario = :matricula "
                + "AND (:idPlanSeleccionado IS NULL OR tpa.id_plan = :idPlanSeleccionado) "
                + "AND EXISTS(SELECT 1 FROM rel_grupo_participante rgp2 "
                + "    INNER JOIN tbl_grupos tg2 ON tg2.id = rgp2.id_grupo "
                + "    INNER JOIN tbl_eventos te2 ON te2.id_evento = tg2.id_evento "
                + "    WHERE rgp2.id_persona_participante = tp.id_persona "
                + "      AND (:idProgramaSeleccionado IS NULL OR te2.id_programa = :idProgramaSeleccionado))";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("matricula", matricula);
        query.setParameter("idPlanSeleccionado", idPlan);
        query.setParameter("idProgramaSeleccionado", idPrograma);

        Object resultado = query.getSingleResult();
        return resultado != null && Long.valueOf(resultado.toString()) > 0;
    }

    private Integer obtenerEntero(Object valor) {
        return valor != null ? Integer.valueOf(valor.toString()) : null;
    }

    private String obtenerCadena(Object valor) {
        return valor != null ? valor.toString() : null;
    }

    private Long obtenerLong(Object valor) {
        return valor != null ? Long.valueOf(valor.toString()) : null;
    }
}
