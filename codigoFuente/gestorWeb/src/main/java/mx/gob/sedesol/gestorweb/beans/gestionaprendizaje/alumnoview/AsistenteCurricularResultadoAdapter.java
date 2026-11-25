package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;

/** Construye narrativas deterministas sólo a partir de PE2027. */
public class AsistenteCurricularResultadoAdapter {
    public AsistenteCurricularViewModelDTO adaptar(ResultadoEvaluacionCurricularV2DTO resultado) {
        AsistenteCurricularViewModelDTO vista = new AsistenteCurricularViewModelDTO();
        if (resultado == null) {
            vista.setSituacionAcademica("REQUIERE_REVISION");
            vista.setEscenario("REQUIERE_REVISION");
            vista.setResumen("La evaluación PE2027 no está disponible; requiere revisión con la evidencia curricular.");
            return vista;
        }
        String situacion = seguro(resultado.getSituacionAcademicaCalculada(), "REQUIERE_REVISION");
        vista.setSituacionAcademica(situacion);
        vista.setEscenario(escenario(resultado));
        vista.setCarga(carga(resultado));
        vista.setResumen(resumen(resultado, vista.getEscenario()));
        for (CodigoMotivoCurricularV2 motivo : resultado.getRestricciones()) vista.getMotivos().add(motivo.name());
        for (TrazaReglaAplicadaV2DTO traza : resultado.getTrazas()) {
            if (traza != null && traza.getCodigoRegla() != null && Boolean.TRUE.equals(traza.getAplicada())) vista.getReglas().add(formatear(traza.getCodigoRegla()));
        }
        if (vista.getReglas().isEmpty()) for (CodigoReglaCurricularV2 regla : resultado.getReglasEvaluadas()) vista.getReglas().add(formatear(regla));
        for (DecisionUnidadCurricularV2DTO decision : resultado.getDecisiones()) {
            if (decision != null && Boolean.FALSE.equals(decision.getHabilitada())) vista.getAlertas().add("UD restringida: " + decision.getMotivos());
        }
        return vista;
    }

    private String escenario(ResultadoEvaluacionCurricularV2DTO r) {
        if (r.getRestricciones().contains(CodigoMotivoCurricularV2.DATOS_INSUFICIENTES)) return "REQUIERE_REVISION";
        if (r.getEvaluacionCargaAcademica() != null
                && Boolean.TRUE.equals(r.getEvaluacionCargaAcademica().getAplicaRn64())) return "RESTRINGIDO_RN64";
        if (r.getRestricciones().contains(CodigoMotivoCurricularV2.SERIACION_DIRECTA) || r.getRestricciones().contains(CodigoMotivoCurricularV2.SERIACION_INDIRECTA)) return "IRREGULAR_POR_SERIACION";
        if (r.getRestricciones().contains(CodigoMotivoCurricularV2.REPROBACION_ACUMULADA)) return "IRREGULAR_POR_REPROBACION";
        return "REGULAR".equals(r.getSituacionAcademicaCalculada()) ? "REGULAR" : "REQUIERE_REVISION";
    }

    private String resumen(ResultadoEvaluacionCurricularV2DTO r, String escenario) {
        if ("REGULAR".equals(escenario)) return "Situación académica regular conforme a la evaluación PE2027.";
        if ("REQUIERE_REVISION".equals(escenario)) return "La información disponible requiere revisión; se conservan los motivos y reglas PE2027.";
        return "Situación " + escenario.replace('_', ' ').toLowerCase() + " conforme a PE2027; revisa motivos, reglas y UD restringidas.";
    }

    private String carga(ResultadoEvaluacionCurricularV2DTO r) {
        if (r.getEvaluacionCargaAcademica() == null) return "Carga pendiente de determinación PE2027.";
        return "Carga normativa: " + r.getEvaluacionCargaAcademica().getCargaMinimaNormativa() + " a " + r.getEvaluacionCargaAcademica().getCargaMaximaNormativa() + ".";
    }
    private String seguro(String valor, String defecto) { return valor == null || valor.trim().isEmpty() ? defecto : valor; }
    private String formatear(CodigoReglaCurricularV2 regla) { return regla.name().replaceFirst("_", "-").replace('_', '.'); }
}
