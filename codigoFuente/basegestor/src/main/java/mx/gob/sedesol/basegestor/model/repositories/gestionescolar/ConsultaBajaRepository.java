package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

@Repository
public class ConsultaBajaRepository implements IConsultaBajaRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ConsultaBajaDTO> buscarBajas(String matricula, String periodo, Integer estatus) {

        String consulta = "SELECT rpb.id_baja AS id_baja, "
                + " tp.sso_idUsuario AS matricula, "
                + " CONCAT(tp.sso_nombre, ' ', tp.sso_apellidoPaterno, IF(tp.sso_apellidoMaterno != '', CONCAT(' ', tp.sso_apellidoMaterno),'')) AS nombre_estudiante, "
                + " tpl.nombre AS plan, "
                + " COALESCE(tfdp.nombre_tentativo,'-') AS programa, "
                + " COALESCE(te.nombre_ec,'-') AS evento, "
                + " ctb.nombre AS tipo_baja, "
                + " COALESCE(CONCAT((SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre),' ', tmc.nombre),'-') AS estructura, "
                + " COALESCE(tpi.nombre_periodo,'-') AS periodo, "
                + " rpb.contabilizar AS estatus, "
                + " rpb.id_plan AS id_plan, "
                + " rpb.id_programa AS id_programa, "
                + " rpb.id_evento AS id_evento, "
                + " COALESCE(tmc.id, 0) AS id_bloque, "
                + " COALESCE(tmc.id_padre, 0) AS id_semestre, "
                + " tp.id_persona AS id_persona, "
                + " rpb.id_user_enrolments_lms AS id_user_enrolments_lms, "
                + " ctb.id_tipo_baja AS id_tipo_baja, "
                + " rmb.id_motivo_baja AS id_motivo_baja, "
                + " rmb.descripcion AS motivo, "
                + " rpb.solicitud AS numero_solicitud, "
                + " rpb.usuario_modifico AS quien_aplica, "
                + " rpb.id_grupo AS id_grupo, "
                + " rpb.proceso_id AS id_proceso "
                + "FROM rel_persona_bajas rpb "
                + " JOIN tbl_persona tp ON tp.id_persona = rpb.id_persona "
                + " JOIN tbl_planes tpl ON tpl.id_plan = rpb.id_plan "
                + " LEFT JOIN tbl_ficha_descriptiva_programa tfdp ON tfdp.id_programa = rpb.id_programa "
                + " JOIN rel_motivo_baja rmb ON rmb.id_motivo_baja = rpb.motivo_baja_id "
                + " JOIN cat_tipo_bajas ctb ON ctb.id_tipo_baja = rmb.tipo_baja_id "
                + " LEFT JOIN tbl_malla_curricular tmc ON tmc.id = tfdp.id_eje_capacitacion "
                + " LEFT JOIN tbl_eventos te ON te.id_evento = rpb.id_evento "
                + " LEFT JOIN tbl_periodos_inscripcion tpi ON te.cve_evento_cap LIKE CONCAT('%', tpi.nombre_periodo, '%') "
                + "WHERE rpb.contabilizar = :estatusSeleccionado "
                + " AND tp.sso_idUsuario = :matriculaSeleccionada "
                + " AND (te.id_evento IS NULL OR tpi.nombre_periodo = :periodoSeleccionado)";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("matriculaSeleccionada", matricula);
        query.setParameter("periodoSeleccionado", periodo);
        query.setParameter("estatusSeleccionado", estatus);

        List<Object[]> resultados = query.getResultList();
        List<ConsultaBajaDTO> bajas = new ArrayList<>();

        if (!ObjectUtils.isNullOrEmpty(resultados)) {
            for (Object[] registro : resultados) {
                ConsultaBajaDTO dto = new ConsultaBajaDTO();
                dto.setIdBaja(registro[0] != null ? Integer.valueOf(registro[0].toString()) : null);
                dto.setMatricula(registro[1] != null ? registro[1].toString() : "");
                dto.setNombreEstudiante(registro[2] != null ? registro[2].toString() : "");
                dto.setPlan(registro[3] != null ? registro[3].toString() : "");
                dto.setPrograma(registro[4] != null ? registro[4].toString() : "");
                dto.setEvento(registro[5] != null ? registro[5].toString() : "");
                dto.setTipoBaja(registro[6] != null ? registro[6].toString() : "");
                dto.setEstructura(registro[7] != null ? registro[7].toString() : "");
                dto.setPeriodo(registro[8] != null ? registro[8].toString() : "");
                dto.setEstatus(registro[9] != null ? Integer.valueOf(registro[9].toString()) : null);
                dto.setIdPlan(registro[10] != null ? Long.valueOf(registro[10].toString()) : null);
                dto.setIdPrograma(registro[11] != null ? Long.valueOf(registro[11].toString()) : null);
                dto.setIdEvento(registro[12] != null ? Long.valueOf(registro[12].toString()) : null);
                dto.setIdBloque(registro[13] != null ? Long.valueOf(registro[13].toString()) : null);
                dto.setIdSemestre(registro[14] != null ? Long.valueOf(registro[14].toString()) : null);
                dto.setIdPersona(registro[15] != null ? Long.valueOf(registro[15].toString()) : null);
                dto.setIdUserEnrolmentsLms(registro[16] != null ? Integer.valueOf(registro[16].toString()) : null);
                dto.setIdTipoBaja(registro[17] != null ? Integer.valueOf(registro[17].toString()) : null);
                dto.setIdMotivoBaja(registro[18] != null ? Long.valueOf(registro[18].toString()) : null);
                dto.setMotivo(registro[19] != null ? registro[19].toString() : "");
                dto.setNumeroSolicitud(registro[20] != null ? registro[20].toString() : "");
                dto.setQuienAplica(registro[21] != null ? registro[21].toString() : "");
                dto.setIdGrupo(registro[22] != null ? Long.valueOf(registro[22].toString()) : null);
                dto.setIdProceso(registro[23] != null ? Long.valueOf(registro[23].toString()) : null);
                bajas.add(dto);
            }
        }

        return bajas;
    }

    @Override
    public List<CatalogoComunDTO> obtenerPeriodos() {
        String consulta = "SELECT tpi.nombre_periodo FROM tbl_periodos_inscripcion tpi";

        Query query = entityManager.createNativeQuery(consulta);
        List<String> registros = query.getResultList();
        List<CatalogoComunDTO> periodos = new ArrayList<>();

        if (!ObjectUtils.isNullOrEmpty(registros)) {
            for (int i = 0; i < registros.size(); i++) {
                CatalogoComunDTO periodo = new CatalogoComunDTO();
                periodo.setId(i + 1);
                periodo.setNombre(registros.get(i));
                periodos.add(periodo);
            }
        }

        return periodos;
    }
}
