package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.*;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
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
        String consulta = "SELECT te.id_evento, te.nombre_ec FROM tbl_eventos te \n"
                + "JOIN tbl_grupos tg ON tg.id_evento = te.id_evento\n"
                + "JOIN rel_grupo_participante rgp ON rgp.id_grupo = tg.id\n"
                + "JOIN tbl_persona tp ON tp.id_persona = rgp.id_persona_participante\n"
                + "WHERE tp.sso_idUsuario = :matricula AND te.cve_evento_cap LIKE CONCAT('%',:nombrePeriodo,'%') AND te.id_programa = :idPrograma";
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
    public boolean validarPlanProgramaPorPersona(Long idPersona, Long idPlan, Long idPrograma) {
        String consulta = "SELECT tp.id_persona\n"
                + "FROM tbl_persona tp\n"
                + "JOIN tbl_persona_aspirante tpa ON tpa.id_persona = tp.id_persona\n"
                + "WHERE tpa.id_plan = :idPlanSeleccionado\n"
                + "AND tp.id_persona = :idPersona\n"
                + "AND EXISTS(SELECT rgp2.id_persona_participante FROM rel_grupo_participante rgp2\n"
                + "    INNER JOIN tbl_grupos tg2 ON tg2.id = rgp2.id_grupo\n"
                + "    INNER JOIN tbl_eventos te2 ON te2.id_evento = tg2.id_evento\n"
                + "                                               WHERE te2.id_programa = :idProgramaSeleccionado\n"
                + "                                               AND rgp2.id_persona_participante = tp.id_persona)";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPlanSeleccionado", idPlan);
        query.setParameter("idProgramaSeleccionado", idPrograma);
        query.setParameter("idPersona", idPersona);
        List<?> resultados = query.getResultList();
        return !resultados.isEmpty();
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
    public List<DatosMoodlePersonaDTO> obtenerDatosMoodlePorPersona(Long idPersona) {
        String consulta = "SELECT rppm.id_persona_moodle, rppm.id_plataforma_moodle FROM tbl_persona tp\n"
                + "                                       JOIN rel_personas_plataformas_moodle rppm ON rppm.id_persona = tp.id_persona\n"
                + "WHERE tp.id_persona = :idPersonaMatriculaIngresada";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idPersonaMatriculaIngresada", idPersona);
        List<Object[]> resultados = query.getResultList();
        List<DatosMoodlePersonaDTO> datos = new ArrayList<>();
        for (Object[] fila : resultados) {
            datos.add(new DatosMoodlePersonaDTO(obtenerEntero(fila[0]), obtenerEntero(fila[1])));
        }
        return datos;
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
    public ConsultaBajaDTO consultarBajaPorId(Long idBaja) {
        String consulta = "SELECT rpb.id_baja, rpb.id_plan, rpb.id_programa, rpb.id_evento, rpb.id_grupo, "
                + " rpb.id_user_enrolments_lms, rpb.contabilizar, rpb.solicitud, rpb.usuario_modifico, "
                + " rpb.id_persona, rmb.descripcion, rmb.id_motivo_baja, rmb.tipo_baja_id, rpb.proceso_id, ctb.nombre AS tipo_baja "
                + "FROM rel_persona_bajas rpb "
                + " JOIN rel_motivo_baja rmb ON rmb.id_motivo_baja = rpb.motivo_baja_id "
                + " JOIN cat_tipo_bajas ctb ON ctb.id_tipo_baja = rmb.tipo_baja_id "
                + "WHERE rpb.id_baja = :idBaja";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("idBaja", idBaja);
        List<?> resultados = query.getResultList();

        if (resultados.isEmpty()) {
            return null;
        }

        Object[] fila = (Object[]) resultados.get(0);
        ConsultaBajaDTO dto = new ConsultaBajaDTO();
        dto.setIdBaja(obtenerEntero(fila[0]));
        dto.setIdPlan(obtenerLong(fila[1]));
        dto.setIdPrograma(obtenerLong(fila[2]));
        dto.setIdEvento(obtenerLong(fila[3]));
        dto.setIdGrupo(obtenerLong(fila[4]));
        dto.setIdUserEnrolmentsLms(obtenerEntero(fila[5]));
        dto.setEstatus(obtenerEntero(fila[6]));
        dto.setNumeroSolicitud(obtenerCadena(fila[7]));
        dto.setQuienAplica(obtenerCadena(fila[8]));
        dto.setIdPersona(obtenerLong(fila[9]));
        dto.setMotivo(obtenerCadena(fila[10]));
        dto.setIdMotivoBaja(obtenerLong(fila[11]));
        dto.setIdTipoBaja(obtenerEntero(fila[12]));
        dto.setIdProceso(obtenerLong(fila[13]));
        dto.setTipoBaja(obtenerCadena(fila[14]));
        return dto;
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

    private Integer obtenerEntero(Object valor) {
        return valor != null ? Integer.valueOf(valor.toString()) : null;
    }

    private String obtenerCadena(Object valor) {
        return valor != null ? valor.toString() : null;
    }

    private Long obtenerLong(Object valor) {
        return valor != null ? Long.valueOf(valor.toString()) : null;
    }

    @Override
    @Transactional
    public void actualizarBaja(Long idBaja, BajaAplicacionDTO bajaAplicacionDTO) {
        String consulta = "UPDATE rel_persona_bajas "
                + "SET motivo_baja_id = :motivoBajaId, proceso_id = :procesoId, "
                + "id_plan = :idPlan, id_programa = :idPrograma, id_evento = :idEvento, id_grupo = :idGrupo, "
                + "id_user_enrolments_lms = :idUserEnrolmentsLms, usuario_modifico = :usuarioModifico, "
                + "contabilizar = :contabilizar, solicitud = :numeroSolicitud "
                + "WHERE id_baja = :idBaja";

        entityManager.createNativeQuery(consulta)
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
                .setParameter("idBaja", idBaja)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void eliminarBaja(Long idBaja) {
        String consulta = "DELETE FROM rel_persona_bajas WHERE id_baja = :idBaja";
        entityManager.createNativeQuery(consulta)
                .setParameter("idBaja", idBaja)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void actualizarMotivoBaja(Long idMotivoBaja, Long idTipoBaja, String descripcion) {
        String consulta = "UPDATE rel_motivo_baja SET tipo_baja_id = :idTipoBaja, descripcion = :descripcion WHERE id_motivo_baja = :idMotivoBaja";
        entityManager.createNativeQuery(consulta)
                .setParameter("idTipoBaja", idTipoBaja)
                .setParameter("descripcion", descripcion)
                .setParameter("idMotivoBaja", idMotivoBaja)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void reactivarPersona(Long idPersona) {
        String consulta = "UPDATE tbl_persona SET activo = 1 WHERE id_persona = :idPersona";
        entityManager.createNativeQuery(consulta)
                .setParameter("idPersona", idPersona)
                .executeUpdate();
    }
}
