package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCargaMaximaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaMaximaCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** F3B-U-01 a F3B-U-18: RN-4.2 sin sustituir carga ni selección productivas. */
public class EvaluacionCurricularV2Fase3BTest {

    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f3BU01RegularConSieteEstaDentroDelMaximo() {
        EvaluacionCargaMaximaCurricularV2DTO e = evaluar(contexto(obligatorias(1L, 7))).getEvaluacionCargaMaxima();
        assertEstado(e, EstadoCargaMaximaCurricularV2.DENTRO_DE_MAXIMO, 8, 7);
    }

    @Test public void f3BU02RegularConOchoAlcanzaMaximo() {
        EvaluacionCargaMaximaCurricularV2DTO e = evaluar(contexto(obligatorias(10L, 8))).getEvaluacionCargaMaxima();
        assertEstado(e, EstadoCargaMaximaCurricularV2.MAXIMO_ALCANZADO, 8, 8);
    }

    @Test public void f3BU03RegularConNueveExcedeSinSeleccionArbitraria() {
        ContextoEvaluacionCurricularV2DTO c = contexto(obligatorias(20L, 8));
        c.getUnidades().add(optativa(28L));
        EvaluacionCargaMaximaCurricularV2DTO e = evaluar(c).getEvaluacionCargaMaxima();
        assertEquals(e.getEstado(), EstadoCargaMaximaCurricularV2.EXCEDE_MAXIMO);
        assertTrue(e.getUdsFueraPorMaximo().toString().contains("OPTATIVA_FUERA_POR_MAXIMO"));
    }

    @Test public void f3BU04IrregularConCincoEstaDentroDelMaximo() {
        ContextoEvaluacionCurricularV2DTO c = contexto(reprobada(30L));
        c.getUnidades().addAll(obligatorias(31L, 4));
        EvaluacionCargaMaximaCurricularV2DTO e = evaluar(c).getEvaluacionCargaMaxima();
        assertEstado(e, EstadoCargaMaximaCurricularV2.DENTRO_DE_MAXIMO, 6, 5);
    }

    @Test public void f3BU05IrregularConSeisAlcanzaMaximo() {
        ContextoEvaluacionCurricularV2DTO c = contexto(reprobada(40L));
        c.getUnidades().addAll(obligatorias(41L, 5));
        assertEstado(evaluar(c).getEvaluacionCargaMaxima(), EstadoCargaMaximaCurricularV2.MAXIMO_ALCANZADO, 6, 6);
    }

    @Test public void f3BU06IrregularConSieteExcedeMaximoSeis() {
        ContextoEvaluacionCurricularV2DTO c = contexto(reprobada(50L));
        c.getUnidades().addAll(obligatorias(51L, 5)); c.getUnidades().add(optativa(56L));
        EvaluacionCargaMaximaCurricularV2DTO e = evaluar(c).getEvaluacionCargaMaxima();
        assertEquals(e.getEstado(), EstadoCargaMaximaCurricularV2.EXCEDE_MAXIMO);
        assertEquals(e.getCargaMaximaNormativa(), Integer.valueOf(6));
    }

    @Test public void f3BU07SituacionNoDeterminadaNoInfiereOchoOSeis() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(indeterminada(60L)));
        assertEquals(r.getEvaluacionCargaMaxima().getEstado(), EstadoCargaMaximaCurricularV2.REQUIERE_REVISION);
        assertNull(r.getEvaluacionCargaMaxima().getCargaMaximaNormativa());
        assertNull(r.getCargaMaximaFuturaSombra());
    }

    @Test public void f3BU08IrregularPriorizaReprobadasAntesDeCompatibles() {
        ContextoEvaluacionCurricularV2DTO c = contexto(reprobada(70L), reprobada(71L));
        c.getUnidades().addAll(obligatorias(72L, 4));
        EvaluacionCargaMaximaCurricularV2DTO e = evaluar(c).getEvaluacionCargaMaxima();
        assertEquals(e.getCantidadPrioritarias(), Integer.valueOf(2));
        assertTrue(e.getUdsIncluidasSombra().contains(Long.valueOf(70L)));
        assertTrue(e.getUdsIncluidasSombra().contains(Long.valueOf(71L)));
        assertEquals(e.getCargaMaximaNormativa(), Integer.valueOf(6));
    }

    @Test public void f3BU09PendientesPrioritariasNoSonDesplazadasPorOptativas() {
        ContextoEvaluacionCurricularV2DTO c = contexto(reprobada(80L), reprobada(81L));
        c.getUnidades().add(optativa(82L)); c.getUnidades().add(optativa(83L)); c.getUnidades().add(optativa(84L));
        c.getUnidades().add(optativa(85L)); c.getUnidades().add(optativa(86L));
        EvaluacionCargaMaximaCurricularV2DTO e = evaluar(c).getEvaluacionCargaMaxima();
        assertEquals(e.getEstado(), EstadoCargaMaximaCurricularV2.REQUIERE_REVISION);
        assertTrue(e.getUdsIncluidasSombra().contains(Long.valueOf(80L)));
        assertTrue(e.getUdsIncluidasSombra().contains(Long.valueOf(81L)));
        assertTrue(e.getRequiereRevisionPorEmpate());
    }

    @Test public void f3BU10RegularConPendientesYCompatiblesRespetaOcho() {
        ContextoEvaluacionCurricularV2DTO c = contexto(obligatoria(90L), obligatoria(91L), obligatoria(92L));
        c.getUnidades().addAll(obligatorias(93L, 5));
        assertEstado(evaluar(c).getEvaluacionCargaMaxima(), EstadoCargaMaximaCurricularV2.MAXIMO_ALCANZADO, 8, 8);
    }

    @Test public void f3BU11ExcepcionMinimaNoPermiteSuperarMaximo() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(obligatoria(100L), obligatoria(101L)));
        assertTrue(r.getEvaluacionCargaMinima().getExcepcionCargaMinimaAplicable());
        assertEquals(r.getEvaluacionCargaMaxima().getCargaMaximaNormativa(), Integer.valueOf(8));
        assertEquals(r.getEvaluacionCargaMaxima().getCargaActualEvaluada(), Integer.valueOf(2));
    }

    @Test public void f3BU12CuatroReprobadasConservaRn64Observacional() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(reprobada(110L), reprobada(111L), reprobada(112L), reprobada(113L)));
        assertTrue(r.getEvaluacionCargaMaxima().getReglasRelacionadas().contains(CodigoReglaCurricularV2.RN_6_4));
        assertFalse(r.getEfectosProductivosAplicados());
    }

    @Test public void f3BU13QuintoRegistraProcedenciaFuturaSinAplicarla() {
        ContextoEvaluacionCurricularV2DTO c = contexto(obligatorias(120L, 3)); c.setSemestreObjetivo(Integer.valueOf(5));
        EvaluacionCargaMaximaCurricularV2DTO e = evaluar(c).getEvaluacionCargaMaxima();
        assertTrue(e.getReglasRelacionadas().contains(CodigoReglaCurricularV2.RN_6_1));
        assertEquals(e.getCargaMaximaNormativa(), Integer.valueOf(8));
    }

    @Test public void f3BU14SeriacionBloqueaYNoCuentaComoCompatible() {
        UnidadEvaluacionCurricularV2DTO bloqueada = obligatoria(130L); bloqueada.setSeriacionDirectaConocida(Boolean.TRUE);
        ContextoEvaluacionCurricularV2DTO c = contexto(obligatorias(131L, 6)); c.getUnidades().add(bloqueada);
        EvaluacionCargaMaximaCurricularV2DTO e = evaluar(c).getEvaluacionCargaMaxima();
        assertEquals(e.getCargaActualEvaluada(), Integer.valueOf(6));
        assertTrue(e.getUdsDescartadas().toString().contains("SERIACION"));
    }

    @Test public void f3BU15DuplicadosNoDuplicanCarga() {
        UnidadEvaluacionCurricularV2DTO a = obligatoria(140L), b = obligatoria(140L); a.setBloque("B1"); b.setBloque("B2");
        EvaluacionCargaMaximaCurricularV2DTO e = evaluar(contexto(a, b)).getEvaluacionCargaMaxima();
        assertEquals(e.getCargaActualEvaluada(), Integer.valueOf(1));
    }

    @Test public void f3BU16EmpateSinCriterioAprobadoRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO c = contexto(); c.getUnidades().addAll(optativas(150L, 9));
        EvaluacionCargaMaximaCurricularV2DTO e = evaluar(c).getEvaluacionCargaMaxima();
        assertEquals(e.getEstado(), EstadoCargaMaximaCurricularV2.REQUIERE_REVISION);
        assertTrue(e.getRequiereRevisionPorEmpate());
    }

    @Test public void f3BU17MismoContextoMismoResultado() {
        ContextoEvaluacionCurricularV2DTO c = contexto(obligatorias(160L, 6));
        assertEquals(evaluar(c).getEvaluacionCargaMaxima().getEstado(), evaluar(c).getEvaluacionCargaMaxima().getEstado());
    }

    @Test public void f3BU18NoHayCambioProductivo() {
        UnidadEvaluacionCurricularV2DTO u = obligatoria(170L);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(u));
        assertFalse(r.getEfectosProductivosAplicados()); assertTrue(u.getOfertada()); assertTrue(u.getSeleccionableOrigen());
    }

    private ResultadoEvaluacionCurricularV2DTO evaluar(ContextoEvaluacionCurricularV2DTO c) { return service.evaluarSombra(c); }
    private void assertEstado(EvaluacionCargaMaximaCurricularV2DTO e, EstadoCargaMaximaCurricularV2 estado, int maximo, int actual) {
        assertEquals(e.getEstado(), estado); assertEquals(e.getCargaMaximaNormativa(), Integer.valueOf(maximo));
        assertEquals(e.getCargaActualEvaluada(), Integer.valueOf(actual));
    }
    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO... unidades) {
        ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setVersionReglas("PE2027-V1");
        c.setFechaEvaluacion(new Date(0L)); c.setSemestreObjetivo(Integer.valueOf(3)); c.setActividadAcademicaComprobada(Boolean.TRUE);
        c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE); c.setDictaminacionEgresoConocida(Boolean.TRUE);
        c.setOportunidadesMejoraUtilizadas(Integer.valueOf(0)); for (UnidadEvaluacionCurricularV2DTO u : unidades) c.getUnidades().add(u); return c;
    }
    private ContextoEvaluacionCurricularV2DTO contexto(java.util.List<UnidadEvaluacionCurricularV2DTO> unidades) {
        return contexto(unidades.toArray(new UnidadEvaluacionCurricularV2DTO[unidades.size()]));
    }
    private java.util.List<UnidadEvaluacionCurricularV2DTO> obligatorias(Long inicio, int cantidad) { return unidades(inicio, cantidad, "Obligatoria"); }
    private java.util.List<UnidadEvaluacionCurricularV2DTO> optativas(Long inicio, int cantidad) { return unidades(inicio, cantidad, "Optativa"); }
    private java.util.List<UnidadEvaluacionCurricularV2DTO> unidades(Long inicio, int cantidad, String tipo) {
        java.util.List<UnidadEvaluacionCurricularV2DTO> resultado = new java.util.ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (int i = 0; i < cantidad; i++) resultado.add(pendiente(Long.valueOf(inicio.longValue() + i), tipo)); return resultado;
    }
    private UnidadEvaluacionCurricularV2DTO obligatoria(Long id) { return pendiente(id, "Obligatoria"); }
    private UnidadEvaluacionCurricularV2DTO optativa(Long id) { return pendiente(id, "Optativa"); }
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id) {
        UnidadEvaluacionCurricularV2DTO u = pendiente(id, "Obligatoria"); u.setEstado(EstadoUnidadCurricularV2.REPROBADA);
        u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setReprobacionVigenteConocida(Boolean.TRUE);
        u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); return u;
    }
    private UnidadEvaluacionCurricularV2DTO indeterminada(Long id) { UnidadEvaluacionCurricularV2DTO u = pendiente(id, "Obligatoria"); u.setEstado(EstadoUnidadCurricularV2.NO_DETERMINADO); u.setCausa(null); return u; }
    private UnidadEvaluacionCurricularV2DTO pendiente(Long id, String tipo) {
        UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd(tipo);
        u.setEstado(EstadoUnidadCurricularV2.NO_CURSADA); u.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO); u.setOfertada(Boolean.TRUE);
        u.setGrupoActivoConocido(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE); u.setSeleccionableOrigen(Boolean.TRUE); return u;
    }
}
