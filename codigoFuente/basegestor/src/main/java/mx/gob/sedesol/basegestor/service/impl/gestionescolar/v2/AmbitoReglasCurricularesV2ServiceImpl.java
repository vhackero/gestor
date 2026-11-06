package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import org.springframework.stereotype.Service;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.AmbitoReglasCurricularesV2Service;

/** Resuelve sólo aplicabilidad normativa; no intenta homologar claves por nombre. */
@Service("ambitoReglasCurricularesV2Service")
public class AmbitoReglasCurricularesV2ServiceImpl implements AmbitoReglasCurricularesV2Service {
    @Override public String resolver(String clavePlan, Boolean comprobada) {
        if (Boolean.TRUE.equals(comprobada)) return "APLICA";
        if (clavePlan == null || clavePlan.trim().isEmpty()) return "REQUIERE_REVISION";
        String normalizada = clavePlan.trim().toUpperCase();
        if (normalizada.contains("PE2024") || normalizada.contains("PE2027")) return "APLICA";
        return "NO_APLICA_RN_PE2024_2027";
    }
}
