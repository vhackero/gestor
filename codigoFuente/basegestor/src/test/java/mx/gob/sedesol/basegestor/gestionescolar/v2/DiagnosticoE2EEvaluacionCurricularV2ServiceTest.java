package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import java.lang.reflect.Field;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DiagnosticoE2EEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2Service;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.TrazabilidadEvaluacionCurricularV2Service;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.ContextoEvaluacionCurricularV2AdapterImpl;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.DiagnosticoE2EEvaluacionCurricularV2ServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

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

    @Test
    public void f11AU02EjecutorUsaFuenteAdaptadorYMotorSinMutarOrigen() throws Exception {
        String habilitadoAnterior = System.getProperty(HABILITADO);
        String ambienteAnterior = System.getProperty(AMBIENTE);
        try {
            System.setProperty(HABILITADO, "true");
            System.setProperty(AMBIENTE, "QA");
            AsistenteInscripcionContextoDTO origen = asistente();
            AsistenteInscripcionService fuente = Mockito.mock(AsistenteInscripcionService.class);
            Mockito.when(fuente.obtenerContextoAsistido(Long.valueOf(91L))).thenReturn(origen);
            TrazabilidadEvaluacionCurricularV2Service trazabilidad = Mockito.mock(TrazabilidadEvaluacionCurricularV2Service.class);
            Mockito.when(trazabilidad.registrarSombra(Mockito.anyObject(), Mockito.anyObject())).thenReturn(Long.valueOf(701L));
            Mockito.when(trazabilidad.obtenerHashContexto(Long.valueOf(701L))).thenReturn("hash-e2e-qa");
            DiagnosticoE2EEvaluacionCurricularV2ServiceImpl servicio = new DiagnosticoE2EEvaluacionCurricularV2ServiceImpl();
            inyectar(servicio, "asistenteInscripcionService", fuente);
            inyectar(servicio, "configuracionService", configuracionSombra());
            inyectar(servicio, "adaptador", new ContextoEvaluacionCurricularV2AdapterImpl());
            inyectar(servicio, "evaluador", new EvaluacionCurricularV2ServiceImpl());
            inyectar(servicio, "trazabilidad", trazabilidad);

            DiagnosticoE2EEvaluacionCurricularV2DTO resultado = servicio.ejecutar(Long.valueOf(91L));

            assertNotNull(resultado.getEvaluacionSombra());
            assertFalse(resultado.getEvaluacionSombra().getEfectosProductivosAplicados());
            assertTrue(resultado.getInmutabilidadVerificada());
            assertTrue(resultado.getAuditoriaReconstruida());
            Mockito.verify(fuente).obtenerContextoAsistido(Long.valueOf(91L));
            Mockito.verify(trazabilidad).registrarSombra(Mockito.anyObject(), Mockito.anyObject());
            assertTrue(origen.getUnidades().get(0).getOfertada());
            assertTrue(origen.getUnidades().get(0).getSeleccionable());
        } finally {
            restaurar(HABILITADO, habilitadoAnterior);
            restaurar(AMBIENTE, ambienteAnterior);
        }
    }

    @Test
    public void f11AU03ExpedienteSinContextoSeRechazaControladamente() throws Exception {
        String habilitadoAnterior = System.getProperty(HABILITADO);
        String ambienteAnterior = System.getProperty(AMBIENTE);
        try {
            System.setProperty(HABILITADO, "true");
            System.setProperty(AMBIENTE, "DEV");
            AsistenteInscripcionService fuente = Mockito.mock(AsistenteInscripcionService.class);
            DiagnosticoE2EEvaluacionCurricularV2ServiceImpl servicio = new DiagnosticoE2EEvaluacionCurricularV2ServiceImpl();
            inyectar(servicio, "asistenteInscripcionService", fuente);
            inyectar(servicio, "configuracionService", configuracionSombra());
            try {
                servicio.ejecutar(Long.valueOf(404L));
                fail("Un expediente sin contexto real debe rechazarse de forma controlada.");
            } catch (InscripcionException esperado) {
                assertTrue(esperado.getMessage().contains("No existe contexto SIGIE"));
            }
        } finally {
            restaurar(HABILITADO, habilitadoAnterior);
            restaurar(AMBIENTE, ambienteAnterior);
        }
    }

    private ConfiguracionEvaluacionCurricularV2Service configuracionSombra() {
        ConfiguracionEvaluacionCurricularV2Service configuracion = Mockito.mock(ConfiguracionEvaluacionCurricularV2Service.class);
        ConfiguracionEvaluacionCurricularV2DTO valor = new ConfiguracionEvaluacionCurricularV2DTO();
        valor.setEvaluacionV2Habilitada(Boolean.TRUE); valor.setModoSombra(Boolean.TRUE); valor.setVersionReglas("PE2027-V1");
        Mockito.when(configuracion.obtenerConfiguracion()).thenReturn(valor);
        return configuracion;
    }

    private AsistenteInscripcionContextoDTO asistente() {
        InscripcionPersonaDTO persona = new InscripcionPersonaDTO(); persona.setIdPersona(Long.valueOf(91L)); persona.setIdPlan(Long.valueOf(12L));
        InscripcionContextoDTO base = new InscripcionContextoDTO(); base.setInscripcionPersona(persona);
        UnidadDecisionInscripcionDTO unidad = new UnidadDecisionInscripcionDTO();
        unidad.setUdId(Long.valueOf(700L)); unidad.setClave("E2E-700"); unidad.setNombre("Unidad técnica"); unidad.setSemestre(Integer.valueOf(5));
        unidad.setBloque(Integer.valueOf(1)); unidad.setEstatusHistorico("No inscrita"); unidad.setOfertada(Boolean.TRUE); unidad.setSeleccionable(Boolean.TRUE);
        AsistenteInscripcionContextoDTO asistente = new AsistenteInscripcionContextoDTO();
        asistente.setContextoBase(base); asistente.setPeriodoActivo("2027-1"); asistente.setIdProcesoInscripcion(Long.valueOf(88L)); asistente.getUnidades().add(unidad);
        return asistente;
    }

    private void inyectar(Object destino, String nombre, Object valor) throws Exception {
        Field campo = destino.getClass().getDeclaredField(nombre); campo.setAccessible(true); campo.set(destino, valor);
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
