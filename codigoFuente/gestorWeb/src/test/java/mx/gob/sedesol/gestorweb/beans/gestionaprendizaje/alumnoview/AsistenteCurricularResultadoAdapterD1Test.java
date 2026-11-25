package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;

public class AsistenteCurricularResultadoAdapterD1Test {
    @Test
    public void derivaEscenarioYReglaSoloDelResultadoPe2027() {
        ResultadoEvaluacionCurricularV2DTO resultado = new ResultadoEvaluacionCurricularV2DTO();
        resultado.setSituacionAcademicaCalculada("IRREGULAR");
        resultado.getRestricciones().add(CodigoMotivoCurricularV2.SERIACION_DIRECTA);
        TrazaReglaAplicadaV2DTO traza = new TrazaReglaAplicadaV2DTO();
        traza.setCodigoRegla(CodigoReglaCurricularV2.RN_6_1);
        traza.setAplicada(Boolean.TRUE);
        resultado.getTrazas().add(traza);

        AsistenteCurricularViewModelDTO vista = new AsistenteCurricularResultadoAdapter().adaptar(resultado);

        assertEquals(vista.getEscenario(), "IRREGULAR_POR_SERIACION");
        assertTrue(vista.getReglas().contains("RN-6.1"));
    }

    @Test
    public void conservaUdRestringidaFueraDeCualquierRecomendacion() {
        ResultadoEvaluacionCurricularV2DTO resultado = new ResultadoEvaluacionCurricularV2DTO();
        resultado.setSituacionAcademicaCalculada("IRREGULAR");
        DecisionUnidadCurricularV2DTO decision = new DecisionUnidadCurricularV2DTO();
        decision.setHabilitada(Boolean.FALSE);
        decision.getMotivos().add(CodigoMotivoCurricularV2.SERIACION_INDIRECTA);
        resultado.getDecisiones().add(decision);

        AsistenteCurricularViewModelDTO vista = new AsistenteCurricularResultadoAdapter().adaptar(resultado);

        assertTrue(vista.getAlertas().get(0).contains("SERIACION_INDIRECTA"));
    }
}
