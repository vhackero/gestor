package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CondicionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoDecisionUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Pruebas F1B: evaluación previa de reinscripción exclusivamente en sombra. */
public class EvaluacionCurricularV2Fase1BTest {

    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test
    public void f1BU01RegularSinPendientesConservaOfertaYSeleccionabilidadOrigen() {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(1L, EstadoUnidadCurricularV2.ACREDITADA, true);
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(unidad));
        DecisionUnidadCurricularV2DTO decision = resultado.getDecisiones().get(0);

        assertEquals(resultado.getSituacionAcademica(), "REGULAR");
        assertTrue(decision.getOfertaOrigen());
        assertTrue(decision.getOfertaEvaluadaV2());
        assertTrue(decision.getSeleccionableOrigen());
        assertTrue(decision.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.HABILITADA));
        assertFalse(resultado.getEfectosProductivosAplicados());
    }

    @Test
    public void f1BU02UnaReprobadaGeneraPrioridadSinCambiarOfertaOrigen() {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(2L, EstadoUnidadCurricularV2.REPROBADA, true);
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(unidad));

        assertEquals(resultado.getTotalReprobadas(), Integer.valueOf(1));
        assertTrue(resultado.getDecisiones().get(0).getEstadosSombra()
                .contains(EstadoDecisionUnidadCurricularV2.PRIORITARIA));
        assertTrue(resultado.getDecisiones().get(0).getOfertaOrigen());
        assertTrue(resultado.getDecisiones().get(0).getOfertaEvaluadaV2());
    }

    @Test
    public void f1BU03TresReprobadasSeCuentanDeFormaDeterministica() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(
                unidad(3L, EstadoUnidadCurricularV2.REPROBADA, true),
                unidad(4L, EstadoUnidadCurricularV2.REPROBADA, false),
                unidad(5L, EstadoUnidadCurricularV2.REPROBADA, true));
        ResultadoEvaluacionCurricularV2DTO primero = service.evaluarSombra(contexto);
        ResultadoEvaluacionCurricularV2DTO segundo = service.evaluarSombra(contexto);

        assertEquals(primero.getTotalReprobadas(), Integer.valueOf(3));
        assertEquals(primero.getSituacionAcademica(), segundo.getSituacionAcademica());
        assertEquals(primero.getDecisiones().get(1).getOfertaOrigen(), segundo.getDecisiones().get(1).getOfertaOrigen());
        assertTrue(primero.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_2_2));
    }

    @Test
    public void f1BU04CuatroReprobadasSeObservanSinAplicarRn64() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(
                unidad(6L, EstadoUnidadCurricularV2.REPROBADA, true),
                unidad(7L, EstadoUnidadCurricularV2.REPROBADA, true),
                unidad(8L, EstadoUnidadCurricularV2.REPROBADA, true),
                unidad(9L, EstadoUnidadCurricularV2.REPROBADA, true)));

        assertEquals(resultado.getTotalReprobadas(), Integer.valueOf(4));
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.CUATRO_O_MAS_REPROBADAS));
        assertFalse(resultado.getEfectosProductivosAplicados());
    }

    @Test
    public void f1BU05BajaParcialSeExplicaComoCondicionSinReprobacion() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(
                unidad(10L, EstadoUnidadCurricularV2.BAJA_PARCIAL, true)));

        assertEquals(resultado.getTotalReprobadas(), Integer.valueOf(0));
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.BAJA_PARCIAL));
        assertTrue(resultado.getRestricciones().contains(CodigoMotivoCurricularV2.BAJA_PARCIAL));
    }

    @Test
    public void f1BU06NoCursadaSeConservaComoPendienteSinCambioProductivo() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(
                unidad(11L, EstadoUnidadCurricularV2.NO_CURSADA, false)));

        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.PENDIENTE_NO_CURSADA));
        assertTrue(resultado.getDecisiones().get(0).getMotivos().contains(CodigoMotivoCurricularV2.SIN_OFERTA));
        assertFalse(resultado.getDecisiones().get(0).getOfertaOrigen());
        assertFalse(resultado.getDecisiones().get(0).getOfertaEvaluadaV2());
    }

    @Test
    public void f1BU07SeriacionDirectaProduceBloqueoSoloEnResultadoSombra() {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(12L, EstadoUnidadCurricularV2.NO_INSCRITA, true);
        unidad.setSeriacionConocida(Boolean.TRUE);
        unidad.setSeriacionDirectaConocida(Boolean.TRUE);
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(unidad));
        DecisionUnidadCurricularV2DTO decision = resultado.getDecisiones().get(0);

        assertTrue(decision.getOfertaOrigen());
        assertFalse(decision.getOfertaEvaluadaV2());
        assertTrue(decision.getEstadosSombra().contains(EstadoDecisionUnidadCurricularV2.BLOQUEADA));
        assertFalse(resultado.getEfectosProductivosAplicados());
    }

    @Test
    public void f1BU08SeriacionIndirectaRequiereRevisionSinBloqueoProductivo() {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(13L, EstadoUnidadCurricularV2.NO_INSCRITA, true);
        unidad.setSeriacionConocida(Boolean.TRUE);
        unidad.setSeriacionIndirectaConocida(Boolean.TRUE);
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(unidad));

        assertTrue(resultado.getDecisiones().get(0).getEstadosSombra()
                .contains(EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION));
        assertTrue(resultado.getDecisiones().get(0).getMotivos().contains(CodigoMotivoCurricularV2.SERIACION_INDIRECTA));
        assertTrue(resultado.getDecisiones().get(0).getOfertaEvaluadaV2());
    }

    @Test
    public void f1BU09UnidadNoOfertadaMantieneFuenteYMarcaRevision() {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(14L, EstadoUnidadCurricularV2.ACREDITADA, false);
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(unidad));

        assertFalse(resultado.getDecisiones().get(0).getOfertaOrigen());
        assertFalse(resultado.getDecisiones().get(0).getOfertaEvaluadaV2());
        assertTrue(resultado.getDecisiones().get(0).getEstadosSombra()
                .contains(EstadoDecisionUnidadCurricularV2.REQUIERE_REVISION));
    }

    @Test
    public void f1BU10CondicionesConcurrentesIncluyenFuentesYExplicacion() {
        UnidadEvaluacionCurricularV2DTO reprobada = unidad(15L, EstadoUnidadCurricularV2.REPROBADA, true);
        reprobada.setSeriacionConocida(Boolean.TRUE);
        reprobada.setSeriacionIndirectaConocida(Boolean.TRUE);
        reprobada.getReferenciasHistoricas().add("ESTATUS_HISTORICO:REPROBADA");
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(reprobada,
                unidad(16L, EstadoUnidadCurricularV2.BAJA_PARCIAL, false)));
        DecisionUnidadCurricularV2DTO decision = resultado.getDecisiones().get(0);

        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.REPROBACION));
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.SERIACION));
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.BAJA_PARCIAL));
        assertTrue(decision.getReferenciasFuente().contains("ESTATUS_HISTORICO:REPROBADA"));
        assertTrue(decision.getExplicacionTecnica().contains("Seriación indirecta"));
    }

    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO... unidades) {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setVersionReglas("PE2027-V1");
        contexto.setFechaEvaluacion(new Date(0L));
        contexto.setSituacionAcademicaOrigen("Regular");
        contexto.setCargaMinimaOrigen(Integer.valueOf(4));
        contexto.setCargaMaximaOrigen(Integer.valueOf(8));
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) contexto.getUnidades().add(unidad);
        return contexto;
    }

    private UnidadEvaluacionCurricularV2DTO unidad(Long idPrograma, EstadoUnidadCurricularV2 estado, boolean ofertada) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(idPrograma);
        unidad.setClave("UD" + idPrograma);
        unidad.setEstado(estado);
        unidad.setOfertada(Boolean.valueOf(ofertada));
        unidad.setSeleccionableOrigen(Boolean.valueOf(ofertada));
        unidad.setBloqueadaOrigen(Boolean.FALSE);
        return unidad;
    }
}
