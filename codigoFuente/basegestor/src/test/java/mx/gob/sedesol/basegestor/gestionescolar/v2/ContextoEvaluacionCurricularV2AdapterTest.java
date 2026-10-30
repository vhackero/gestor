package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaMaximaCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.ContextoEvaluacionCurricularV2AdapterImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Pruebas F0-I-01 a F0-I-06: adaptación de lectura, sin infraestructura externa. */
public class ContextoEvaluacionCurricularV2AdapterTest {

    private final ContextoEvaluacionCurricularV2AdapterImpl adapter = new ContextoEvaluacionCurricularV2AdapterImpl();
    private final EvaluacionCurricularV2ServiceImpl evaluator = new EvaluacionCurricularV2ServiceImpl();

    @Test
    public void f0I01AsistenteYAdaptadorConservanClaveYEstado() {
        AsistenteInscripcionContextoDTO asistente = asistente(unidad("ABC", "Aprobada", null));

        ContextoEvaluacionCurricularV2DTO contexto = adapter.construirDesdeAsistente(asistente);

        assertEquals(1, contexto.getUnidades().size());
        assertEquals("ABC", contexto.getUnidades().get(0).getClave());
        assertEquals(EstadoUnidadCurricularV2.ACREDITADA, contexto.getUnidades().get(0).getEstado());
        assertEquals(Long.valueOf(91L), contexto.getIdPersona());
        assertEquals(Long.valueOf(12L), contexto.getIdPlan());
    }

    @Test
    public void f0I02EvaluacionSombraNoModificaSeleccionOOfertaOrigen() {
        UnidadDecisionInscripcionDTO unidad = unidad("DEF", "No inscrita", "Disponible");
        unidad.setSeleccionable(Boolean.TRUE);
        unidad.setOfertada(Boolean.TRUE);
        AsistenteInscripcionContextoDTO asistente = asistente(unidad);

        ContextoEvaluacionCurricularV2DTO contexto = adapter.construirDesdeAsistente(asistente);
        ResultadoEvaluacionCurricularV2DTO resultado = evaluator.evaluarSombra(contexto);

        assertTrue(unidad.getSeleccionable());
        assertTrue(unidad.getOfertada());
        assertEquals(1, resultado.getDecisiones().size());
        assertFalse(resultado.getMensajes().isEmpty());
    }

    @Test
    public void f0I03SinProcesoActivoConstruyeContexto() {
        AsistenteInscripcionContextoDTO asistente = asistente(unidad("GHI", "No inscrita", null));
        asistente.setIdProcesoInscripcion(null);

        ContextoEvaluacionCurricularV2DTO contexto = adapter.construirDesdeAsistente(asistente);

        assertNull(contexto.getIdProcesoInscripcion());
        assertNotNull(contexto.getHuellaOferta());
        assertEquals(1, contexto.getUnidades().size());
    }

    @Test
    public void f0I04NuevoIngresoNoRecibeDecisionDeElegibilidadNueva() {
        AsistenteInscripcionContextoDTO asistente = asistente(unidad("JKL", "No inscrita", "Recomendada"));
        ContextoEvaluacionCurricularV2DTO contexto = adapter.construirDesdeAsistente(asistente);

        ResultadoEvaluacionCurricularV2DTO resultado = evaluator.evaluarSombra(contexto);

        assertTrue(resultado.getDecisiones().get(0).getHabilitada());
        assertTrue(resultado.getMensajes().get(0).contains("modo sombra"));
    }

    @Test
    public void f0I05SeriacionSeRegistraSinAlterarOrigen() {
        UnidadDecisionInscripcionDTO unidad = unidad("MNO", "No inscrita", null);
        unidad.setBloqueada(Boolean.TRUE);
        AsistenteInscripcionContextoDTO asistente = asistente(unidad);

        ContextoEvaluacionCurricularV2DTO contexto = adapter.construirDesdeAsistente(asistente);
        ResultadoEvaluacionCurricularV2DTO resultado = evaluator.evaluarSombra(contexto);

        assertTrue(contexto.getUnidades().get(0).getSeriacionConocida());
        assertTrue(unidad.getBloqueada());
        assertNotNull(resultado.getDecisiones().get(0));
    }

    @Test
    public void f0I06DistingueReprobadaBajaYNoCursada() {
        List<UnidadDecisionInscripcionDTO> unidades = new ArrayList<UnidadDecisionInscripcionDTO>();
        unidades.add(unidad("PQR", "Reprobada", null));
        unidades.add(unidad("STU", "Baja", null));
        unidades.add(unidad("VWX", "No cursada", null));
        AsistenteInscripcionContextoDTO asistente = asistente(unidades);

        ContextoEvaluacionCurricularV2DTO contexto = adapter.construirDesdeAsistente(asistente);

        assertEquals(EstadoUnidadCurricularV2.REPROBADA, contexto.getUnidades().get(0).getEstado());
        assertEquals(EstadoUnidadCurricularV2.BAJA_PARCIAL, contexto.getUnidades().get(1).getEstado());
        assertEquals(EstadoUnidadCurricularV2.NO_CURSADA, contexto.getUnidades().get(2).getEstado());
    }

    @Test
    public void f12A1B07ReconoceEstadosLegacyConGuionBajoSinDegradarlos() {
        List<UnidadDecisionInscripcionDTO> unidades = new ArrayList<UnidadDecisionInscripcionDTO>();
        unidades.add(unidad("REP", "NO_ACREDITADA", null));
        unidades.add(unidad("NIN", "NO_INSCRITA", null));
        AsistenteInscripcionContextoDTO asistente = asistente(unidades);

        ContextoEvaluacionCurricularV2DTO contexto = adapter.construirDesdeAsistente(asistente);
        ResultadoEvaluacionCurricularV2DTO resultado = evaluator.evaluarSombra(contexto);

        assertEquals(EstadoUnidadCurricularV2.REPROBADA, contexto.getUnidades().get(0).getEstado());
        assertEquals(EstadoUnidadCurricularV2.NO_INSCRITA, contexto.getUnidades().get(1).getEstado());
        assertEquals("IRREGULAR", resultado.getSituacionAcademicaCalculada());
        EvaluacionCargaMaximaCurricularV2DTO carga = resultado.getEvaluacionCargaMaxima();
        assertEquals(Integer.valueOf(6), carga.getCargaMaximaNormativa());
    }

    @Test
    public void f0I08ConservaIdentidadDeUdSinDeduplicarPorNombreNiPlan() {
        UnidadDecisionInscripcionDTO primera = unidad("UD-A", "Aprobada", null);
        UnidadDecisionInscripcionDTO segunda = unidad("UD-B", "Aprobada", null);
        primera.setUdId(Long.valueOf(1001L));
        segunda.setUdId(Long.valueOf(1002L));
        primera.setNombre("Nombre coincidente");
        segunda.setNombre("Nombre coincidente");

        ContextoEvaluacionCurricularV2DTO contexto = adapter.construirDesdeAsistente(asistente(java.util.Arrays.asList(primera, segunda)));

        assertEquals(contexto.getIdPlan(), Long.valueOf(12L));
        assertEquals(contexto.getUnidades().size(), 2);
        assertEquals(contexto.getUnidades().get(0).getIdPrograma(), Long.valueOf(1001L));
        assertEquals(contexto.getUnidades().get(1).getIdPrograma(), Long.valueOf(1002L));
        assertFalse(contexto.getUnidades().get(0).getIdPrograma().equals(contexto.getIdPlan()));
    }

    private AsistenteInscripcionContextoDTO asistente(UnidadDecisionInscripcionDTO unidad) {
        List<UnidadDecisionInscripcionDTO> unidades = new ArrayList<UnidadDecisionInscripcionDTO>();
        unidades.add(unidad);
        return asistente(unidades);
    }

    private AsistenteInscripcionContextoDTO asistente(List<UnidadDecisionInscripcionDTO> unidades) {
        InscripcionPersonaDTO persona = new InscripcionPersonaDTO();
        persona.setIdPersona(91L);
        persona.setIdPlan(12L);
        InscripcionContextoDTO inscripcion = new InscripcionContextoDTO();
        inscripcion.setInscripcionPersona(persona);
        AsistenteInscripcionContextoDTO asistente = new AsistenteInscripcionContextoDTO();
        asistente.setContextoBase(inscripcion);
        asistente.setPeriodoActivo("2027-1");
        asistente.setIdProcesoInscripcion(77L);
        asistente.setUnidades(unidades);
        return asistente;
    }

    private UnidadDecisionInscripcionDTO unidad(String clave, String estatusHistorico, String estatusPeriodo) {
        UnidadDecisionInscripcionDTO unidad = new UnidadDecisionInscripcionDTO();
        unidad.setUdId(Long.valueOf(clave.hashCode() & 0x7fffffff));
        unidad.setClave(clave);
        unidad.setNombre("Unidad " + clave);
        unidad.setSemestre(Integer.valueOf(5));
        unidad.setBloque(Integer.valueOf(1));
        unidad.setEstatusHistorico(estatusHistorico);
        unidad.setEstatusPeriodo(estatusPeriodo);
        unidad.setOfertada(Boolean.TRUE);
        return unidad;
    }
}
