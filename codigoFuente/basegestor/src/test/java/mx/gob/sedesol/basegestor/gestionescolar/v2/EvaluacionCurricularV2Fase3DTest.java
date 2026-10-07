package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CondicionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCargaAcademicaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/**
 * F3D-U-01 a F3D-U-24. Cierre integrado de RN-4.1, RN-4.2 y RN-4.3.
 * Cada caso usa el motor en sombra y comprueba que no se aplique un efecto
 * sobre la representación origen.
 */
public class EvaluacionCurricularV2Fase3DTest {

    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f3DU01RegularTresObligatoriasEsValida() {
        assertEstado(evaluar(contexto(obligatorias(1L, 3))), EstadoCargaAcademicaV2.VALIDA);
    }

    @Test public void f3DU02RegularOchoUnidadesAlcanzaMaximo() {
        EvaluacionCargaAcademicaV2DTO e = evaluar(contexto(obligatorias(10L, 8))).getEvaluacionCargaAcademica();
        assertEquals(e.getCargaMaximaNormativa(), Integer.valueOf(8)); assertTrue(e.getMaximoCumplido());
    }

    @Test public void f3DU03RegularNueveUnidadesNoSuperaMaximo() {
        assertEstado(evaluar(contexto(obligatorias(20L, 9))), EstadoCargaAcademicaV2.REQUIERE_REVISION);
    }

    @Test public void f3DU04IrregularTresPrioritariasEsValida() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(reprobada(30L), reprobada(31L), reprobada(32L)));
        assertEquals(r.getSituacionAcademicaCalculada(), "IRREGULAR"); assertEstado(r, EstadoCargaAcademicaV2.VALIDA);
    }

    @Test public void f3DU05IrregularSeisUnidadesAlcanzaMaximo() {
        ContextoEvaluacionCurricularV2DTO c = contexto(reprobada(40L)); c.getUnidades().addAll(obligatorias(41L, 5));
        EvaluacionCargaAcademicaV2DTO e = evaluar(c).getEvaluacionCargaAcademica();
        assertEquals(e.getCargaMaximaNormativa(), Integer.valueOf(6)); assertTrue(e.getMaximoCumplido());
    }

    @Test public void f3DU06IrregularSieteCandidatasNoSuperaSeis() {
        ContextoEvaluacionCurricularV2DTO c = contexto(reprobada(50L)); c.getUnidades().addAll(obligatorias(51L, 6));
        EvaluacionCargaAcademicaV2DTO e = evaluar(c).getEvaluacionCargaAcademica();
        assertEquals(e.getCargaMaximaNormativa(), Integer.valueOf(6)); assertFalse(e.getMaximoCumplido());
    }

    @Test public void f3DU07DosObligatoriasMasOptativaCumpleMinimo() {
        EvaluacionCargaAcademicaV2DTO e = evaluar(contexto(obligatoria(60L), obligatoria(61L), optativa(62L))).getEvaluacionCargaAcademica();
        assertTrue(e.getMinimoCumplido()); assertFalse(e.getExcepcionMinimoAplicable());
    }

    @Test public void f3DU08DosObligatoriasSinAlternativaUsaExcepcion() {
        EvaluacionCargaAcademicaV2DTO e = evaluar(contexto(obligatoria(70L), obligatoria(71L))).getEvaluacionCargaAcademica();
        assertTrue(e.getExcepcionMinimoAplicable()); assertEstado(e, EstadoCargaAcademicaV2.VALIDA_CON_EXCEPCION_MINIMO);
    }

    @Test public void f3DU09UnaUnicaObligatoriaPendienteUsaExcepcion() {
        EvaluacionCargaAcademicaV2DTO e = evaluar(contexto(obligatoria(80L))).getEvaluacionCargaAcademica();
        assertTrue(e.getExcepcionMinimoAplicable()); assertEquals(e.getCargaEvaluada(), Integer.valueOf(1));
    }

    @Test public void f3DU10OptativasConSeriacionNoCompletanMinimo() {
        UnidadEvaluacionCurricularV2DTO opt = optativa(90L); opt.setSeriacionDirectaConocida(Boolean.TRUE);
        EvaluacionCargaAcademicaV2DTO e = evaluar(contexto(obligatoria(91L), opt)).getEvaluacionCargaAcademica();
        assertTrue(e.getExcepcionMinimoAplicable()); assertTrue(e.getUdsExcluidas().toString().contains("SERIACION"));
    }

    @Test public void f3DU11SinCupoSeDistingueDeSinOferta() {
        UnidadEvaluacionCurricularV2DTO sinCupo = obligatoria(100L); sinCupo.setCupoDisponibleConocido(Boolean.FALSE);
        sinCupo.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO);
        EvaluacionCargaAcademicaV2DTO e = evaluar(contexto(obligatoria(101L), obligatoria(102L), sinCupo)).getEvaluacionCargaAcademica();
        assertTrue(e.getUdsExcluidas().toString().contains("SIN_CUPO"));
        assertFalse(e.getUdsExcluidas().toString().contains("SIN_OFERTA"));
    }

    @Test public void f3DU12ObligatoriaPrioritariaNoEsDesplazadaPorOptativas() {
        ContextoEvaluacionCurricularV2DTO c = contexto(reprobada(110L)); c.getUnidades().addAll(optativas(111L, 7));
        EvaluacionCargaAcademicaV2DTO e = evaluar(c).getEvaluacionCargaAcademica();
        assertTrue(e.getUdsPrioritarias().contains(Long.valueOf(110L))); assertTrue(e.getUdsIncluidasSombra().contains(Long.valueOf(110L)));
    }

    @Test public void f3DU13EmpateSinCriterioNoSeleccionaArbitrariamente() {
        assertEstado(evaluar(contexto(optativas(120L, 9))), EstadoCargaAcademicaV2.REQUIERE_REVISION);
    }

    @Test public void f3DU14SituacionNoDeterminadaPropagaIncertidumbre() {
        EvaluacionCargaAcademicaV2DTO e = evaluar(contexto(indeterminada(130L))).getEvaluacionCargaAcademica();
        assertEquals(e.getEstado(), EstadoCargaAcademicaV2.REQUIERE_REVISION); assertNull(e.getCargaMaximaNormativa());
    }

    @Test public void f3DU15CuatroReprobadasDetectaRn64SoloObservacional() {
        EvaluacionCargaAcademicaV2DTO e = evaluar(contexto(reprobada(140L), reprobada(141L), reprobada(142L), reprobada(143L))).getEvaluacionCargaAcademica();
        assertTrue(e.getRn64AplicableEnFuturaFase());
    }

    @Test public void f3DU16TresReprobadasNoDetectaRn64() {
        EvaluacionCargaAcademicaV2DTO e = evaluar(contexto(reprobada(150L), reprobada(151L), reprobada(152L))).getEvaluacionCargaAcademica();
        assertFalse(e.getRn64AplicableEnFuturaFase());
    }

    @Test public void f3DU17QuintoDesde2027DetectaCtr01SinAplicarlo() {
        ContextoEvaluacionCurricularV2DTO c = contexto(obligatorias(160L, 3)); c.setSemestreObjetivo(Integer.valueOf(5)); c.setPeriodoNormativo("2027-1");
        assertTrue(evaluar(c).getEvaluacionCargaAcademica().getCtr01Rn61Procedente());
    }

    @Test public void f3DU18TerceroNoDetectaCtr01() {
        ContextoEvaluacionCurricularV2DTO c = contexto(obligatorias(170L, 3)); c.setSemestreObjetivo(Integer.valueOf(3)); c.setPeriodoNormativo("2027-1");
        assertFalse(evaluar(c).getEvaluacionCargaAcademica().getCtr01Rn61Procedente());
    }

    @Test public void f3DU19SeptimoNoDetectaCtr01() {
        ContextoEvaluacionCurricularV2DTO c = contexto(obligatorias(180L, 3)); c.setSemestreObjetivo(Integer.valueOf(7)); c.setPeriodoNormativo("2027-1");
        assertFalse(evaluar(c).getEvaluacionCargaAcademica().getCtr01Rn61Procedente());
    }

    @Test public void f3DU20ExcepcionMinimoIrregularNoAlteraMaximoSeis() {
        EvaluacionCargaAcademicaV2DTO e = evaluar(contexto(reprobada(190L))).getEvaluacionCargaAcademica();
        assertTrue(e.getExcepcionMinimoAplicable()); assertEquals(e.getCargaMaximaNormativa(), Integer.valueOf(6));
        assertTrue(e.getCargaMinimaNormativa().intValue() <= e.getCargaMaximaNormativa().intValue());
    }

    @Test public void f3DU21DuplicadoTecnicoCuentaUnaSolaUnidad() {
        UnidadEvaluacionCurricularV2DTO b1 = obligatoria(200L), b2 = obligatoria(200L); b1.setBloque("B1"); b2.setBloque("B2");
        assertEquals(evaluar(contexto(b1, b2)).getEvaluacionCargaAcademica().getCargaEvaluada(), Integer.valueOf(1));
    }

    @Test public void f3DU22BajaParcialYNoCursadaConservanCausasSinReprobacion() {
        UnidadEvaluacionCurricularV2DTO baja = obligatoria(210L); baja.setEstado(EstadoUnidadCurricularV2.BAJA_PARCIAL); baja.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(baja, obligatoria(211L)));
        assertEquals(r.getCantidadReprobadasVigentes(), Integer.valueOf(0)); assertEquals(r.getSituacionAcademicaCalculada(), "REGULAR");
        assertTrue(r.getCondicionesDetectadas().contains(CondicionCurricularV2.BAJA_PARCIAL));
        assertTrue(r.getCondicionesDetectadas().contains(CondicionCurricularV2.PENDIENTE_NO_CURSADA));
    }

    @Test public void f3DU23MismoContextoProduceResultadoDeterminista() {
        ContextoEvaluacionCurricularV2DTO c = contexto(obligatorias(220L, 3));
        EvaluacionCargaAcademicaV2DTO primero = evaluar(c).getEvaluacionCargaAcademica();
        EvaluacionCargaAcademicaV2DTO segundo = evaluar(c).getEvaluacionCargaAcademica();
        assertEquals(primero.getEstado(), segundo.getEstado()); assertEquals(primero.getUdsIncluidasSombra(), segundo.getUdsIncluidasSombra());
    }

    @Test public void f3DU24ContratoYTrazaConservanCadenaSinEfectoProductivo() {
        UnidadEvaluacionCurricularV2DTO u = obligatoria(230L);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(u));
        EvaluacionCargaAcademicaV2DTO e = r.getEvaluacionCargaAcademica();
        assertTrue(tieneRegla(e, CodigoReglaCurricularV2.RN_1_2)); assertTrue(tieneRegla(e, CodigoReglaCurricularV2.RN_2_2));
        assertTrue(tieneRegla(e, CodigoReglaCurricularV2.RN_4_1)); assertTrue(tieneRegla(e, CodigoReglaCurricularV2.RN_4_2));
        assertTrue(tieneRegla(e, CodigoReglaCurricularV2.RN_4_3)); assertTrue(tieneTraza(r, CodigoReglaCurricularV2.RN_4_3));
        assertFalse(r.getEfectosProductivosAplicados()); assertTrue(u.getOfertada()); assertTrue(u.getSeleccionableOrigen());
    }

    private ResultadoEvaluacionCurricularV2DTO evaluar(ContextoEvaluacionCurricularV2DTO contexto) { return service.evaluarSombra(contexto); }
    private void assertEstado(ResultadoEvaluacionCurricularV2DTO r, EstadoCargaAcademicaV2 estado) { assertEquals(r.getEvaluacionCargaAcademica().getEstado(), estado); }
    private void assertEstado(EvaluacionCargaAcademicaV2DTO e, EstadoCargaAcademicaV2 estado) { assertEquals(e.getEstado(), estado); }
    private boolean tieneRegla(EvaluacionCargaAcademicaV2DTO e, CodigoReglaCurricularV2 regla) { return e.getReglasRelacionadas().contains(regla); }
    private boolean tieneTraza(ResultadoEvaluacionCurricularV2DTO r, CodigoReglaCurricularV2 regla) { for (TrazaReglaAplicadaV2DTO t : r.getTrazas()) if (regla == t.getCodigoRegla()) return true; return false; }

    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO... unidades) {
        ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setVersionReglas("PE2027-V1"); c.setFechaEvaluacion(new Date(0L));
        c.setSemestreObjetivo(Integer.valueOf(3)); c.setActividadAcademicaComprobada(Boolean.TRUE); c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);
        c.setDictaminacionEgresoConocida(Boolean.TRUE); c.setOportunidadesMejoraUtilizadas(Integer.valueOf(0));
        for (UnidadEvaluacionCurricularV2DTO u : unidades) c.getUnidades().add(u); return c;
    }
    private ContextoEvaluacionCurricularV2DTO contexto(List<UnidadEvaluacionCurricularV2DTO> unidades) { return contexto(unidades.toArray(new UnidadEvaluacionCurricularV2DTO[unidades.size()])); }
    private List<UnidadEvaluacionCurricularV2DTO> obligatorias(Long inicio, int total) { return unidades(inicio, total, "Obligatoria"); }
    private List<UnidadEvaluacionCurricularV2DTO> optativas(Long inicio, int total) { return unidades(inicio, total, "Optativa"); }
    private List<UnidadEvaluacionCurricularV2DTO> unidades(Long inicio, int total, String tipo) { List<UnidadEvaluacionCurricularV2DTO> r = new ArrayList<UnidadEvaluacionCurricularV2DTO>(); for (int i = 0; i < total; i++) r.add(pendiente(Long.valueOf(inicio.longValue() + i), tipo)); return r; }
    private UnidadEvaluacionCurricularV2DTO obligatoria(Long id) { return pendiente(id, "Obligatoria"); }
    private UnidadEvaluacionCurricularV2DTO optativa(Long id) { return pendiente(id, "Optativa"); }
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id) { UnidadEvaluacionCurricularV2DTO u = pendiente(id, "Obligatoria"); u.setEstado(EstadoUnidadCurricularV2.REPROBADA); u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setReprobacionVigenteConocida(Boolean.TRUE); u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); return u; }
    private UnidadEvaluacionCurricularV2DTO indeterminada(Long id) { UnidadEvaluacionCurricularV2DTO u = pendiente(id, "Obligatoria"); u.setEstado(EstadoUnidadCurricularV2.NO_DETERMINADO); u.setCausa(null); return u; }
    private UnidadEvaluacionCurricularV2DTO pendiente(Long id, String tipo) { UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd(tipo); u.setEstado(EstadoUnidadCurricularV2.NO_CURSADA); u.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO); u.setOfertada(Boolean.TRUE); u.setGrupoActivoConocido(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE); u.setSeleccionableOrigen(Boolean.TRUE); return u; }
}
