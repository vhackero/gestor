package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

@Repository
public class ConsultaBajaRepository implements IConsultaBajaRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ConsultaBajaDTO> buscarBajas(String matricula, String nombrePeriodo, Integer estatus) {

        String consulta = "SELECT rpb.id_baja, "
                + " tp.sso_idUsuario AS matricula, "
                + " tpl.nombre AS plan, "
                + " tfdp.nombre_tentativo AS programa, "
                + " te.nombre_ec AS evento, "
                + " ctb.nombre AS tipo_baja, "
                + " CONCAT((SELECT tmc2.nombre FROM tbl_malla_curricular tmc2 WHERE tmc2.id = tmc.id_padre), ' ', tmc.nombre) AS estructura, "
                + " tpi.nombre_periodo AS periodo, "
                + " IF(rpb.contabilizar, 'Aplicada', 'Pendiente') AS estatus "
                + "FROM rel_persona_bajas rpb "
                + " JOIN tbl_persona tp ON tp.id_persona = rpb.id_persona "
                + " JOIN tbl_planes tpl ON tpl.id_plan = rpb.id_plan "
                + " JOIN tbl_ficha_descriptiva_programa tfdp ON tfdp.id_programa = rpb.id_programa "
                + " JOIN rel_motivo_baja rmb ON rmb.id_motivo_baja = rpb.motivo_baja_id "
                + " JOIN cat_tipo_bajas ctb ON ctb.id_tipo_baja = rmb.tipo_baja_id "
                + " JOIN tbl_malla_curricular tmc ON tmc.id = tfdp.id_eje_capacitacion "
                + " LEFT JOIN tbl_eventos te ON te.id_evento = rpb.id_evento "
                + " LEFT JOIN tbl_periodos_inscripcion tpi ON te.cve_evento_cap LIKE CONCAT('%', tpi.nombre_periodo, '%') "
                + "WHERE rpb.contabilizar = :estatusSeleccionado "
                + " AND tp.sso_idUsuario = :matriculaProporcionada "
                + " AND tpi.nombre_periodo = :nombrePeriodoProporcionado";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("estatusSeleccionado", estatus);
        query.setParameter("matriculaProporcionada", matricula);
        query.setParameter("nombrePeriodoProporcionado", nombrePeriodo);

        List<Object[]> resultados = query.getResultList();
        List<ConsultaBajaDTO> bajas = new ArrayList<>();

        if (!ObjectUtils.isNullOrEmpty(resultados)) {
            for (Object[] registro : resultados) {
                ConsultaBajaDTO dto = new ConsultaBajaDTO();
                dto.setIdBaja(registro[0] != null ? Long.valueOf(registro[0].toString()) : null);
                dto.setMatricula(registro[1] != null ? registro[1].toString() : "");
                dto.setPlan(registro[2] != null ? registro[2].toString() : "");
                dto.setPrograma(registro[3] != null ? registro[3].toString() : "");
                dto.setEvento(registro[4] != null ? registro[4].toString() : "");
                dto.setTipoBaja(registro[5] != null ? registro[5].toString() : "");
                dto.setEstructura(registro[6] != null ? registro[6].toString() : "");
                dto.setPeriodo(registro[7] != null ? registro[7].toString() : "");
                dto.setEstatus(registro[8] != null ? registro[8].toString() : "");
                bajas.add(dto);
            }
        }

        return bajas;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> obtenerPeriodosInscripcion() {
        String consulta = "SELECT tpi.nombre_periodo FROM tbl_periodos_inscripcion tpi";
        Query query = entityManager.createNativeQuery(consulta);
        return query.getResultList();
    }
}
