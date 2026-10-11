package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCierreFase7V2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoRequerimientoOptativoV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cierre F7D: integración RN-7.1, RN-7.1.1, RN-7.2 y RN-7.3 exclusivamente en sombra. */
public class EvaluacionCurricularV2Fase7DTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f7DU01ModeloRegularSinReprobadasCierraEnSombra() {
        assertEquals(carga(base(4)).getEstadoCierreFase7(), EstadoCierreFase7V2.CONSOLIDADA_EN_SOMBRA);
    }

    @Test public void f7DU02OptativaYElectivaConservanTipoYContribuyenAIrregular() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Optativa")); contexto.getUnidades().add(reprobada(2L, "Electiva"));
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(resultado.getSituacionAcademica(), "IRREGULAR");
        assertTrue(resultado.getEvaluacionCargaAcademica().getUdsOptativasReprobadasRn73().contains(1L));
        assertTrue(resultado.getEvaluacionCargaAcademica().getUdsElectivasReprobadasRn73().contains(2L));
    }

    @Test public void f7DU03TotalTresNoActivaRn64YCuatroSi() {
        ContextoEvaluacionCurricularV2DTO tres = base(4); tres.getUnidades().add(reprobada(1L, "Obligatoria")); tres.getUnidades().add(reprobada(2L, "Optativa")); tres.getUnidades().add(reprobada(3L, "Electiva"));
        assertFalse(Boolean.TRUE.equals(carga(tres).getAplicaRn64()));
        ContextoEvaluacionCurricularV2DTO cuatro = base(4); cuatro.getUnidades().add(reprobada(1L, "Obligatoria")); cuatro.getUnidades().add(reprobada(2L, "Optativa")); cuatro.getUnidades().add(reprobada(3L, "Electiva")); cuatro.getUnidades().add(reprobada(4L, "Electiva"));
        assertTrue(Boolean.TRUE.equals(carga(cuatro).getAplicaRn64()));
    }

    @Test public void f7DU04BajaParcialYNoCursadaNoGeneranReprobacionesArtificiales() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(unidad(1L, "Optativa", EstadoUnidadCurricularV2.BAJA_PARCIAL)); contexto.getUnidades().add(unidad(2L, "Electiva", EstadoUnidadCurricularV2.NO_CURSADA));
        assertEquals(servicio.evaluarSombra(contexto).getCantidadReprobadasVigentes(), Integer.valueOf(0));
    }

    @Test public void f7DU05TipoAmbiguoRequiereRevisionYSaleDeAmbasSemanticas() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Optativa Electiva"));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getEstadoCierreFase7(), EstadoCierreFase7V2.REQUIERE_REVISION);
        assertTrue(carga.getUdsTipoAmbiguoFase7().contains(1L));
        assertFalse(carga.getUdsOptativasReprobadasRn73().contains(1L));
        assertFalse(carga.getUdsElectivasReprobadasRn73().contains(1L));
    }

    @Test public void f7DU06RequisitoOptativoSieteOchoYNueveEsEstable() {
        ContextoEvaluacionCurricularV2DTO siete = base(4); agregarAcreditadas(siete, 7); assertEquals(carga(siete).getEstadoRequerimientoOptativo(), EstadoRequerimientoOptativoV2.REQUERIMIENTO_OPTATIVO_PENDIENTE);
        ContextoEvaluacionCurricularV2DTO ocho = base(4); agregarAcreditadas(ocho, 8); assertEquals(carga(ocho).getEstadoRequerimientoOptativo(), EstadoRequerimientoOptativoV2.REQUERIMIENTO_OPTATIVO_CUMPLIDO);
        ContextoEvaluacionCurricularV2DTO nueve = base(4); agregarAcreditadas(nueve, 9); assertEquals(carga(nueve).getCantidadOptativasAdicionalesRn711(), Integer.valueOf(1));
    }

    @Test public void f7DU07DuplicadoAcreditadoNoInflaRequisitoYAdicionalReprobadaCuenta() {
        ContextoEvaluacionCurricularV2DTO duplicado = base(4); duplicado.getUnidades().add(acreditada(1L, 1)); duplicado.getUnidades().add(acreditada(1L, 1));
        assertEquals(carga(duplicado).getCantidadOptativasAcreditadasRn711(), Integer.valueOf(1));
        ContextoEvaluacionCurricularV2DTO adicional = base(4); agregarAcreditadas(adicional, 8); adicional.getUnidades().add(reprobada(20L, "Optativa"));
        assertEquals(servicio.evaluarSombra(adicional).getCantidadReprobadasVigentes(), Integer.valueOf(1));
    }

    @Test public void f7DU08ElectivaExigeS7OS8YPeDistinto() {
        ContextoEvaluacionCurricularV2DTO s6 = base(6); s6.setIdPlan(1L); s6.getUnidades().add(electivaNueva(1L, 2L)); assertFalse(carga(s6).getUdsElectivasElegiblesRn72().contains(1L));
        ContextoEvaluacionCurricularV2DTO s7 = base(7); s7.setIdPlan(1L); s7.getUnidades().add(electivaNueva(1L, 2L)); assertTrue(carga(s7).getUdsElectivasElegiblesRn72().contains(1L));
        ContextoEvaluacionCurricularV2DTO mismo = base(8); mismo.setIdPlan(1L); mismo.getUnidades().add(electivaNueva(1L, 1L)); assertTrue(carga(mismo).getUdsElectivasMismoPeRn72().contains(1L));
    }

    @Test public void f7DU09OtroPeNoConvierteUnaUdNoElectiva() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); contexto.setIdPlan(1L); UnidadEvaluacionCurricularV2DTO ud = unidad(1L, "Optativa", EstadoUnidadCurricularV2.NO_INSCRITA); ud.setIdPlanOrigenElectiva(2L); contexto.getUnidades().add(ud);
        assertFalse(Boolean.TRUE.equals(carga(contexto).getAplicaRn72()));
    }

    @Test public void f7DU10ReprobadaElectivaNoCreaRepeticionFicticiaNiBorraEstado() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); contexto.setIdPlan(1L); contexto.getUnidades().add(reprobada(1L, "Electiva")); contexto.getUnidades().add(electivaNueva(2L, 2L));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsElectivasReprobadasRn72().contains(1L));
        assertFalse(carga.getUdsElectivasMismoPeRn72().contains(2L));
    }

    @Test public void f7DU11ObligatoriaPrioritariaNoEsDesplazada() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Obligatoria")); contexto.getUnidades().add(reprobada(2L, "Optativa")); contexto.getUnidades().add(reprobada(3L, "Electiva"));
        assertTrue(carga(contexto).getUdsObligatoriasPrioritariasRn73().contains(1L));
    }

    @Test public void f7DU12OfertaCupoYSeriacionSeConservanPorUd() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); contexto.setIdPlan(1L); UnidadEvaluacionCurricularV2DTO sinCupo = electivaNueva(1L, 2L); sinCupo.setCupoDisponibleConocido(Boolean.FALSE); sinCupo.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO);
        UnidadEvaluacionCurricularV2DTO seriada = electivaNueva(2L, 3L); seriada.setSeriacionDirectaConocida(Boolean.TRUE); contexto.getUnidades().add(sinCupo); contexto.getUnidades().add(seriada);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto); assertTrue(carga.getUdsElectivasSinCupoRn72().contains(1L)); assertTrue(carga.getUdsElectivasBloqueadasSeriacionRn72().contains(2L));
    }

    @Test public void f7DU13ObjetivoS5NoIncluyeTiposEnBaseRn61YS7NoLaReactiva() {
        ContextoEvaluacionCurricularV2DTO s5 = base(5); s5.setIdPlan(1L); for (long id = 1; id <= 4; id++) { UnidadEvaluacionCurricularV2DTO obligatoria = unidad(id, "Obligatoria", EstadoUnidadCurricularV2.ACREDITADA); obligatoria.setSemestre(Integer.valueOf((int) id)); s5.getUnidades().add(obligatoria); } s5.getUnidades().add(reprobada(10L, "Optativa"));
        assertFalse(carga(s5).getBaseRn611().contains(10L));
        ContextoEvaluacionCurricularV2DTO s7 = base(7); s7.getUnidades().add(reprobada(11L, "Electiva")); assertFalse(Boolean.TRUE.equals(carga(s7).getAplicaCtr01()));
    }

    @Test public void f7DU14TransicionCuatroATresYReprobadaAAcreditadaActualizaEstadoVigente() {
        ContextoEvaluacionCurricularV2DTO cuatro = base(4); for (long id = 1; id <= 4; id++) cuatro.getUnidades().add(reprobada(id, id <= 2 ? "Optativa" : "Electiva")); assertTrue(Boolean.TRUE.equals(carga(cuatro).getAplicaRn64()));
        ContextoEvaluacionCurricularV2DTO tres = base(4); for (long id = 1; id <= 3; id++) tres.getUnidades().add(reprobada(id, id <= 2 ? "Optativa" : "Electiva")); assertFalse(Boolean.TRUE.equals(carga(tres).getAplicaRn64()));
        ContextoEvaluacionCurricularV2DTO acreditada = base(4); acreditada.getUnidades().add(acreditada(1L, 1)); assertEquals(servicio.evaluarSombra(acreditada).getCantidadReprobadasVigentes(), Integer.valueOf(0));
    }

    @Test public void f7DU15ResultadoEsDeterministaEIdempotente() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Optativa")); contexto.getUnidades().add(reprobada(2L, "Electiva"));
        EvaluacionCargaAcademicaV2DTO primera = carga(contexto); EvaluacionCargaAcademicaV2DTO segunda = carga(contexto);
        assertEquals(primera.getEstadoCierreFase7(), segunda.getEstadoCierreFase7()); assertEquals(primera.getUdsOptativasReprobadasRn73(), segunda.getUdsOptativasReprobadasRn73());
    }

    @Test public void f7DU16NoExisteEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); contexto.setIdPlan(1L); contexto.getUnidades().add(electivaNueva(1L, 2L));
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(contexto).getEfectosProductivosAplicados()));
    }

    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) { return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica(); }
    private ContextoEvaluacionCurricularV2DTO base(Integer semestre) { ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO(); contexto.setSemestreObjetivo(semestre); contexto.setPeriodoNormativo("2027-1"); contexto.setSeriacionComprobada(Boolean.TRUE); return contexto; }
    private void agregarAcreditadas(ContextoEvaluacionCurricularV2DTO contexto, int cantidad) { for (long id = 1; id <= cantidad; id++) contexto.getUnidades().add(acreditada(id, (int) id)); }
    private UnidadEvaluacionCurricularV2DTO acreditada(Long id, int orden) { UnidadEvaluacionCurricularV2DTO unidad = unidad(id, "Optativa", EstadoUnidadCurricularV2.ACREDITADA); unidad.setOrdenAcreditacionOptativa(Integer.valueOf(orden)); return unidad; }
    private UnidadEvaluacionCurricularV2DTO electivaNueva(Long id, Long planOrigen) { UnidadEvaluacionCurricularV2DTO unidad = unidad(id, "Electiva", EstadoUnidadCurricularV2.NO_INSCRITA); unidad.setSemestre(Integer.valueOf(5)); unidad.setIdPlanOrigenElectiva(planOrigen); return unidad; }
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id, String tipo) { UnidadEvaluacionCurricularV2DTO unidad = unidad(id, tipo, EstadoUnidadCurricularV2.REPROBADA); unidad.setCausa(CausaPendienteUnidadV2.REPROBACION); unidad.setReprobacionVigenteConocida(Boolean.TRUE); unidad.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); return unidad; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, String tipo, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO(); unidad.setIdPrograma(id); unidad.setClave("UD" + id); unidad.setTipoUd(tipo); unidad.setSemestre(Integer.valueOf(2)); unidad.setEstado(estado); unidad.setOfertada(Boolean.TRUE); unidad.setGrupoActivoConocido(Boolean.TRUE); unidad.setCupoDisponibleConocido(Boolean.TRUE); return unidad; }
}
