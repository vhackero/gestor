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
    public List<ConsultaBajaDTO> buscarBajas(String matricula, Integer idPeriodo, Integer estatus) {

        String consulta = "SELECT p.sso_idUsuario AS matricula, "
                + " pl.nombre AS plan, "
                + " fdp.nombre_tentativo AS programa, "
                + " e.nombre_ec AS evento, "
                + " ctb.nombre AS tipo_baja, "
                + " rmp.nombre_estructuras AS estructura, "
                + " cp.nombre AS periodo, "
                + " rpb.contabilizar AS estatus "
                + "FROM rel_persona_bajas rpb "
                + " INNER JOIN tbl_persona p ON p.id_persona = rpb.id_persona "
                + " INNER JOIN tbl_planes pl ON pl.id_plan = rpb.id_plan "
                + " INNER JOIN tbl_ficha_descriptiva_programa fdp ON fdp.id_programa = rpb.id_programa "
                + " INNER JOIN tbl_eventos e ON e.id_evento = rpb.id_evento "
                + " INNER JOIN rel_motivo_baja rmb ON rmb.id_motivo_baja = rpb.motivo_baja_id "
                + " INNER JOIN cat_tipo_bajas ctb ON ctb.id_tipo_baja = rmb.tipo_baja_id "
                + " LEFT JOIN rel_malla_plan rmp ON rmp.id_plan = pl.id_plan "
                + " LEFT JOIN cat_periodos cp ON cp.id_periodo = pl.id_periodo "
                + "WHERE p.sso_idUsuario LIKE :matricula "
                + " AND pl.id_periodo = :idPeriodo "
                + " AND rpb.contabilizar = :estatus";

        Query query = entityManager.createNativeQuery(consulta);
        query.setParameter("matricula", '%' + matricula + '%');
        query.setParameter("idPeriodo", idPeriodo);
        query.setParameter("estatus", estatus);

        List<Object[]> resultados = query.getResultList();
        List<ConsultaBajaDTO> bajas = new ArrayList<>();

        if (!ObjectUtils.isNullOrEmpty(resultados)) {
            for (Object[] registro : resultados) {
                ConsultaBajaDTO dto = new ConsultaBajaDTO();
                dto.setMatricula(registro[0] != null ? registro[0].toString() : "");
                dto.setPlan(registro[1] != null ? registro[1].toString() : "");
                dto.setPrograma(registro[2] != null ? registro[2].toString() : "");
                dto.setEvento(registro[3] != null ? registro[3].toString() : "");
                dto.setTipoBaja(registro[4] != null ? registro[4].toString() : "");
                dto.setEstructura(registro[5] != null ? registro[5].toString() : "");
                dto.setPeriodo(registro[6] != null ? registro[6].toString() : "");
                dto.setEstatus(registro[7] != null ? Integer.valueOf(registro[7].toString()) : null);
                bajas.add(dto);
            }
        }

        return bajas;
    }
}
