package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIntentoAcreditacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.IntentoAcreditacionUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.AsistenteCurricularV2FacadeImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.ContextoEvaluacionCurricularV2AdapterImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.MallaCurricularV2FacadeImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.SimuladorCurricularV2ServiceImpl;

/** Contrato común Bloque 7: ningún consumidor reinterpreta la evaluación V2. */
public class ConsistenciaConsumidoresBloque7Test {

    @Test
    public void catalogoB7CasoRepresentativoEntregaElMismoContratoALosCuatroConsumidores() throws Exception {
        AsistenteInscripcionContextoDTO fuente = fuente();
        ContextoEvaluacionCurricularV2AdapterImpl adaptador = new ContextoEvaluacionCurricularV2AdapterImpl();
        EvaluacionCurricularV2ServiceImpl evaluador = new EvaluacionCurricularV2ServiceImpl();

        MallaCurricularV2FacadeImpl malla = new MallaCurricularV2FacadeImpl();
        inyectar(malla, "adaptador", adaptador); inyectar(malla, "evaluador", evaluador);
        AsistenteCurricularV2FacadeImpl asistente = new AsistenteCurricularV2FacadeImpl();
        inyectar(asistente, "adaptadorEvaluacion", adaptador); inyectar(asistente, "evaluadorCurricular", evaluador);

        SimuladorCurricularV2ServiceImpl simulador = new SimuladorCurricularV2ServiceImpl();
        AsistenteInscripcionService servicioFuente = Mockito.mock(AsistenteInscripcionService.class);
        Mockito.when(servicioFuente.obtenerContextoAsistido(Long.valueOf(7001L))).thenReturn(fuente);
        inyectar(simulador, "asistenteInscripcionService", servicioFuente);
        inyectar(simulador, "adaptador", adaptador); inyectar(simulador, "evaluador", evaluador);

        ResultadoEvaluacionCurricularV2DTO resultadoMalla = malla.evaluarSombra(fuente);
        ResultadoEvaluacionCurricularV2DTO resultadoAsistente = asistente.evaluarSombra(fuente);
        ResultadoEvaluacionCurricularV2DTO resultadoSimulador = simulador.simularSombra(Long.valueOf(7001L),
                Collections.singletonList(Long.valueOf(501L))).getEvaluacionCurricularV2();
        // El panel virtual se construye desde la misma fachada del Asistente y su resultado V2.
        ResultadoEvaluacionCurricularV2DTO resultadoVirtual = asistente.evaluarSombra(fuente);

        String esperado = huella(resultadoMalla);
        assertEquals(huella(resultadoAsistente), esperado);
        assertEquals(huella(resultadoSimulador), esperado);
        assertEquals(huella(resultadoVirtual), esperado);
    }

    private String huella(ResultadoEvaluacionCurricularV2DTO resultado) {
        return resultado.getSituacionAcademicaCalculada() + "|" + resultado.getCantidadReprobadasVigentes()
                + "|" + resultado.getUnidadesOrigenIrregularidad() + "|" + resultado.getDecisiones().get(0).getHabilitada()
                + "|" + resultado.getDecisiones().get(0).getMotivos() + "|"
                + resultado.getEvaluacionesIntentos().get(0).getOportunidadesConsumidas() + "|"
                + resultado.getEvaluacionesIntentos().get(0).getRecursamientos() + "|"
                + resultado.getDecisiones().get(0).getEstadoOperativoInterbloque() + "|" + resultado.getAvisos();
    }

    private AsistenteInscripcionContextoDTO fuente() {
        InscripcionPersonaDTO persona = new InscripcionPersonaDTO(); persona.setIdPersona(Long.valueOf(7001L)); persona.setIdPlan(Long.valueOf(202402L));
        InscripcionContextoDTO base = new InscripcionContextoDTO(); base.setInscripcionPersona(persona);
        UnidadDecisionInscripcionDTO unidad = new UnidadDecisionInscripcionDTO();
        unidad.setUdId(Long.valueOf(501L)); unidad.setClave("B7-REP"); unidad.setNombre("Unidad sintética");
        unidad.setTipoUd("Obligatoria"); unidad.setSemestre(Integer.valueOf(3)); unidad.setBloque(Integer.valueOf(1));
        unidad.setEstatusHistorico("Reprobada"); unidad.setOfertada(Boolean.TRUE); unidad.setSeleccionable(Boolean.TRUE);
        IntentoAcreditacionUnidadV2DTO intento = new IntentoAcreditacionUnidadV2DTO();
        intento.setIdPrograma(Long.valueOf(501L)); intento.setIdEjecucionTecnico("B7-I-1"); intento.setPeriodo("2026-2"); intento.setEstado(EstadoIntentoAcreditacionV2.REPROBADO);
        AsistenteInscripcionContextoDTO contexto = new AsistenteInscripcionContextoDTO();
        contexto.setContextoBase(base); contexto.setPeriodoActivo("2027-1"); contexto.setUnidades(Arrays.asList(unidad));
        contexto.setUnidadesMapaPlan(Arrays.asList(unidad)); contexto.setUnidadesCandidatasPeriodo(Arrays.asList(unidad));
        contexto.setHistorialIntentosV2(Arrays.asList(intento));
        return contexto;
    }

    private void inyectar(Object destino, String campo, Object valor) throws Exception {
        Field field = destino.getClass().getDeclaredField(campo); field.setAccessible(true); field.set(destino, valor);
    }
}
