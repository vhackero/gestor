package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.DiagnosticoE2EEvaluacionCurricularV2ServiceImpl;

/** F1E-U-01: el diagnóstico interno no puede ejecutarse por omisión ni fuera de DEV/QA. */
public class DiagnosticoE2EEvaluacionCurricularV2ServiceTest {
    private static final String HABILITADO = "sigie.v2.diagnostico.e2e.habilitado";
    private static final String AMBIENTE = "sigie.v2.diagnostico.e2e.ambiente";

    @Test
    public void f1EU01DiagnosticoEstaDeshabilitadoPorDefectoYFueraDeDevQa() throws Exception {
        String habilitadoAnterior = System.getProperty(HABILITADO);
        String ambienteAnterior = System.getProperty(AMBIENTE);
        try {
            System.clearProperty(HABILITADO);
            System.clearProperty(AMBIENTE);
            verificarRechazo();
            System.setProperty(HABILITADO, "true");
            System.setProperty(AMBIENTE, "PROD");
            verificarRechazo();
        } finally {
            restaurar(HABILITADO, habilitadoAnterior);
            restaurar(AMBIENTE, ambienteAnterior);
        }
    }

    private void verificarRechazo() throws Exception {
        try {
            new DiagnosticoE2EEvaluacionCurricularV2ServiceImpl().ejecutar(Long.valueOf(1L));
            fail("El diagnóstico E2E no debe ejecutarse sin habilitación DEV/QA explícita.");
        } catch (InscripcionException esperado) {
            assertTrue(esperado.getMessage().contains("deshabilitado"));
        }
    }

    private void restaurar(String propiedad, String valor) {
        if (valor == null) System.clearProperty(propiedad); else System.setProperty(propiedad, valor);
    }
}
