package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCumplimientoSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.RelacionSeriacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoRelacionSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** RN-8.3 PE2027: grafo de prerrequisitos interanuales exclusivamente en sombra. */
public class EvaluacionCurricularV2Fase8CTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f8CU01SinRelacionFormalNoAplicaRn83() { assertFalse(Boolean.TRUE.equals(carga(base(3)).getAplicaRn83())); }
    @Test public void f8CU02RelacionDirectaInteranualAcreditadaCumple() { assertEquals(decisionRaiz(carga(contextoDirecto(2, EstadoUnidadCurricularV2.ACREDITADA))).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA); }
    @Test public void f8CU03RelacionDirectaReprobadaNoCumple() { assertTrue(carga(contextoDirecto(2, EstadoUnidadCurricularV2.REPROBADA)).getUdsInteranualesNoCumplidasRn83().contains(2L)); }

    @Test public void f8CU04EstadosNoReprobatoriosNoSeTransforman() {
        for (EstadoUnidadCurricularV2 estado : new EstadoUnidadCurricularV2[] { EstadoUnidadCurricularV2.NO_CURSADA, EstadoUnidadCurricularV2.BAJA_PARCIAL, EstadoUnidadCurricularV2.EN_CURSO, EstadoUnidadCurricularV2.NO_INSCRITA }) {
            ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contextoDirecto(2, estado));
            assertTrue(resultado.getEvaluacionCargaAcademica().getUdsInteranualesNoCumplidasRn83().contains(2L));
            assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        }
    }

    @Test public void f8CU05EstadoNoDeterminadoRequiereRevision() { assertTrue(Boolean.TRUE.equals(carga(contextoDirecto(2, EstadoUnidadCurricularV2.NO_DETERMINADO)).getRequiereRevisionRn83())); }
    @Test public void f8CU06EstadoVigenteAcreditadoSuperaHistoriaReprobada() { ContextoEvaluacionCurricularV2DTO c = contextoDirecto(2, EstadoUnidadCurricularV2.ACREDITADA); c.getUnidades().get(0).getReferenciasHistoricas().add("HISTORICO_REPROBADA"); assertEquals(decisionRaiz(carga(c)).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA); }

    @Test public void f8CU07CadenaIndirectaCompletaCumple() {
        ContextoEvaluacionCurricularV2DTO contexto = cadena(EstadoUnidadCurricularV2.ACREDITADA, EstadoUnidadCurricularV2.ACREDITADA);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto); assertTrue(carga.getUdsInteranualesCumplidasRn83().contains(3L)); assertTrue(existeIndirecta(carga, 1L));
    }

    @Test public void f8CU08CadenaConAntecedenteIndirectoNoAcreditadoBloquea() {
        EvaluacionCargaAcademicaV2DTO carga = carga(cadena(EstadoUnidadCurricularV2.NO_CURSADA, EstadoUnidadCurricularV2.ACREDITADA));
        assertTrue(carga.getUdsInteranualesNoCumplidasRn83().contains(3L)); assertTrue(carga.getAntecedentesNoAcreditadosRn83().contains(1L));
    }

    @Test public void f8CU09CadenaConAntecedenteDirectoNoAcreditadoBloquea() {
        EvaluacionCargaAcademicaV2DTO carga = carga(cadena(EstadoUnidadCurricularV2.ACREDITADA, EstadoUnidadCurricularV2.REPROBADA));
        assertTrue(carga.getUdsInteranualesNoCumplidasRn83().contains(3L)); assertTrue(carga.getAntecedentesNoAcreditadosRn83().contains(2L));
    }

    @Test public void f8CU10MultiplesAntecedentesExigenTodosAcreditados() {
        ContextoEvaluacionCurricularV2DTO ok = contextoDirecto(2, EstadoUnidadCurricularV2.ACREDITADA); ok.getUnidades().add(unidad(3L, 1, EstadoUnidadCurricularV2.ACREDITADA)); ok.getUnidades().get(1).getRelacionesSeriacion().add(relacion(3L, 2L, 1, 3)); assertTrue(carga(ok).getUdsInteranualesCumplidasRn83().contains(2L));
        ContextoEvaluacionCurricularV2DTO no = contextoDirecto(2, EstadoUnidadCurricularV2.ACREDITADA); no.getUnidades().add(unidad(3L, 1, EstadoUnidadCurricularV2.REPROBADA)); no.getUnidades().get(1).getRelacionesSeriacion().add(relacion(3L, 2L, 1, 3)); assertTrue(carga(no).getUdsInteranualesNoCumplidasRn83().contains(2L));
    }

    @Test public void f8CU11MultiplesCaminosNoDuplicanAntecedenteLogicoNoAcreditado() {
        ContextoEvaluacionCurricularV2DTO c = base(5); UnidadEvaluacionCurricularV2DTO a = unidad(1L, 1, EstadoUnidadCurricularV2.NO_CURSADA); UnidadEvaluacionCurricularV2DTO b = unidad(2L, 3, EstadoUnidadCurricularV2.ACREDITADA); UnidadEvaluacionCurricularV2DTO d = unidad(3L, 3, EstadoUnidadCurricularV2.ACREDITADA); UnidadEvaluacionCurricularV2DTO e = unidad(4L, 5, EstadoUnidadCurricularV2.NO_INSCRITA); b.getRelacionesSeriacion().add(relacion(1L, 2L, 1, 3)); d.getRelacionesSeriacion().add(relacion(1L, 3L, 1, 3)); e.getRelacionesSeriacion().add(relacion(2L, 4L, 3, 5)); e.getRelacionesSeriacion().add(relacion(3L, 4L, 3, 5)); c.getUnidades().add(a); c.getUnidades().add(b); c.getUnidades().add(d); c.getUnidades().add(e);
        EvaluacionCargaAcademicaV2DTO carga = carga(c); assertEquals(carga.getAntecedentesNoAcreditadosRn83().size(), 1); assertTrue(carga.getAntecedentesNoAcreditadosRn83().contains(1L));
    }

    @Test public void f8CU12CicloDetectadoRequiereRevisionSinRecursionInfinita() {
        ContextoEvaluacionCurricularV2DTO c = base(5); UnidadEvaluacionCurricularV2DTO a = unidad(1L, 1, EstadoUnidadCurricularV2.ACREDITADA); UnidadEvaluacionCurricularV2DTO b = unidad(2L, 3, EstadoUnidadCurricularV2.ACREDITADA); UnidadEvaluacionCurricularV2DTO d = unidad(3L, 5, EstadoUnidadCurricularV2.NO_INSCRITA); a.getRelacionesSeriacion().add(relacion(3L, 1L, 5, 1)); b.getRelacionesSeriacion().add(relacion(1L, 2L, 1, 3)); d.getRelacionesSeriacion().add(relacion(2L, 3L, 3, 5)); c.getUnidades().add(a); c.getUnidades().add(b); c.getUnidades().add(d);
        EvaluacionCargaAcademicaV2DTO carga = carga(c); assertTrue(Boolean.TRUE.equals(carga.getRequiereRevisionRn83())); assertFalse(carga.getCiclosSeriacionRn83().isEmpty());
    }

    @Test public void f8CU13TipoNoDeterminadoRequiereRevisionSinInferirRn83() {
        ContextoEvaluacionCurricularV2DTO c = contextoDirecto(2, EstadoUnidadCurricularV2.ACREDITADA); c.getUnidades().get(1).getRelacionesSeriacion().get(0).setTipoSeriacion(TipoSeriacionV2.NO_DETERMINADA); EvaluacionCargaAcademicaV2DTO carga = carga(c); assertFalse(Boolean.TRUE.equals(carga.getAplicaRn83())); assertTrue(Boolean.TRUE.equals(carga.getRequiereRevision()));
    }

    @Test public void f8CU14S2AS3AplicaRn83SinReactivarRn61() { EvaluacionCargaAcademicaV2DTO carga = carga(contextoDirecto(2, EstadoUnidadCurricularV2.REPROBADA)); assertTrue(Boolean.TRUE.equals(carga.getAplicaRn83())); assertFalse(Boolean.TRUE.equals(carga.getAplicaCtr01())); }
    @Test public void f8CU15S6AS7AplicaRn83SinReactivarRn61() { ContextoEvaluacionCurricularV2DTO c = contextoDirecto(6, EstadoUnidadCurricularV2.REPROBADA); c.setSemestreObjetivo(Integer.valueOf(7)); EvaluacionCargaAcademicaV2DTO carga = carga(c); assertTrue(Boolean.TRUE.equals(carga.getAplicaRn83())); assertFalse(Boolean.TRUE.equals(carga.getAplicaCtr01())); }

    @Test public void f8CU16S5ConservaConcurrenciaRn61YSeriacion() { ContextoEvaluacionCurricularV2DTO c = contextoDirecto(4, EstadoUnidadCurricularV2.ACREDITADA); c.setSemestreObjetivo(Integer.valueOf(5)); EvaluacionCargaAcademicaV2DTO carga = carga(c); assertTrue(carga.getReglasRelacionadas().contains(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2.RN_6_1)); assertTrue(carga.getUdsInteranualesCumplidasRn83().contains(2L)); }

    @Test public void f8CU17PendienteSinRelacionNoBloqueaOtraUdPorCierreAnualLegacy() { ContextoEvaluacionCurricularV2DTO c = base(3); c.getUnidades().add(unidad(1L, 2, EstadoUnidadCurricularV2.REPROBADA)); c.getUnidades().add(unidad(2L, 3, EstadoUnidadCurricularV2.NO_INSCRITA)); EvaluacionCargaAcademicaV2DTO carga = carga(c); assertFalse(Boolean.TRUE.equals(carga.getAplicaRn83())); assertFalse(carga.getUdsInteranualesNoCumplidasRn83().contains(2L)); }

    @Test public void f8CU18S6PendienteSinRelacionNoBloqueaS7PorCierreAnualLegacy() { ContextoEvaluacionCurricularV2DTO c = base(7); c.getUnidades().add(unidad(1L, 6, EstadoUnidadCurricularV2.REPROBADA)); c.getUnidades().add(unidad(2L, 7, EstadoUnidadCurricularV2.NO_INSCRITA)); assertFalse(Boolean.TRUE.equals(carga(c).getAplicaRn83())); }

    @Test public void f8CU19OfertaCupoCargaYRn64SonConcurrentes() {
        ContextoEvaluacionCurricularV2DTO c = contextoDirecto(2, EstadoUnidadCurricularV2.ACREDITADA); c.getUnidades().get(1).setOfertada(Boolean.FALSE); c.getUnidades().get(1).setCupoDisponibleConocido(Boolean.FALSE); c.getUnidades().get(1).setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO); for (long id = 10; id < 14; id++) c.getUnidades().add(reprobada(id, 1)); EvaluacionCargaAcademicaV2DTO carga = carga(c); assertTrue(carga.getUdsInteranualesCumplidasRn83().contains(2L)); assertTrue(Boolean.TRUE.equals(carga.getAplicaRn64()));
    }

    @Test public void f8CU20BloqueoEsIndividualPorUd() { ContextoEvaluacionCurricularV2DTO c = contextoDirecto(2, EstadoUnidadCurricularV2.NO_CURSADA); c.getUnidades().add(unidad(3L, 3, EstadoUnidadCurricularV2.NO_INSCRITA)); EvaluacionCargaAcademicaV2DTO carga = carga(c); assertTrue(carga.getUdsInteranualesNoCumplidasRn83().contains(2L)); assertFalse(carga.getUdsInteranualesNoCumplidasRn83().contains(3L)); }

    @Test public void f8CU21OptativaYElectivaConRelacionConservanTipo() { ContextoEvaluacionCurricularV2DTO optativa = contextoDirecto(2, EstadoUnidadCurricularV2.ACREDITADA); optativa.getUnidades().get(1).setTipoUd("Optativa"); assertTrue(optativa.getUnidades().get(1).getTipoUd().contains("Optativa")); assertTrue(carga(optativa).getUdsInteranualesCumplidasRn83().contains(2L)); ContextoEvaluacionCurricularV2DTO electiva = contextoDirecto(2, EstadoUnidadCurricularV2.ACREDITADA); electiva.getUnidades().get(1).setTipoUd("Electiva"); assertTrue(electiva.getUnidades().get(1).getTipoUd().contains("Electiva")); assertTrue(carga(electiva).getUdsInteranualesCumplidasRn83().contains(2L)); }

    @Test public void f8CU22DuplicadosDeterminismoIdempotenciaYSinEfectoProductivo() { ContextoEvaluacionCurricularV2DTO c = contextoDirecto(2, EstadoUnidadCurricularV2.ACREDITADA); c.getUnidades().get(1).getRelacionesSeriacion().add(relacion(1L, 2L, 2, 3)); EvaluacionCargaAcademicaV2DTO primera = carga(c); EvaluacionCargaAcademicaV2DTO segunda = carga(c); assertEquals(primera.getUdsInteranualesCumplidasRn83(), segunda.getUdsInteranualesCumplidasRn83()); assertEquals(primera.getRelacionesSeriacionV2().size(), segunda.getRelacionesSeriacionV2().size()); assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(c).getEfectosProductivosAplicados())); }

    @Test public void f8CU23NoRegresionEnCursoDifierePorTipoDeSeriacion() {
        ContextoEvaluacionCurricularV2DTO interbloque = base(2); UnidadEvaluacionCurricularV2DTO b1 = unidad(11L, 2, EstadoUnidadCurricularV2.EN_CURSO); b1.setBloque("1"); UnidadEvaluacionCurricularV2DTO b2 = unidad(12L, 2, EstadoUnidadCurricularV2.NO_INSCRITA); b2.setBloque("2"); RelacionSeriacionCurricularV2DTO r81 = relacion(11L, 12L, 2, 2); r81.setTipoSeriacion(TipoSeriacionV2.INTERBLOQUE); r81.setBloqueAntecedente("1"); r81.setBloqueConsecuente("2"); b2.getRelacionesSeriacion().add(r81); interbloque.getUnidades().add(b1); interbloque.getUnidades().add(b2);
        assertEquals(decisionRaiz(carga(interbloque)).getDecision(), EstadoCumplimientoSeriacionV2.CONDICIONADA);
        ContextoEvaluacionCurricularV2DTO intersemestral = contextoDirecto(2, EstadoUnidadCurricularV2.EN_CURSO); intersemestral.getUnidades().get(1).getRelacionesSeriacion().get(0).setTipoSeriacion(TipoSeriacionV2.INTERSEMESTRAL); assertEquals(decisionRaiz(carga(intersemestral)).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
        assertEquals(decisionRaiz(carga(contextoDirecto(2, EstadoUnidadCurricularV2.EN_CURSO))).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
    }

    private ContextoEvaluacionCurricularV2DTO base(int objetivo) { ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setSemestreObjetivo(Integer.valueOf(objetivo)); c.setPeriodoNormativo("2027-1"); c.setSeriacionComprobada(Boolean.TRUE); return c; }
    private ContextoEvaluacionCurricularV2DTO contextoDirecto(int semestreAntecedente, EstadoUnidadCurricularV2 estado) { ContextoEvaluacionCurricularV2DTO c = base(semestreAntecedente + 1); UnidadEvaluacionCurricularV2DTO a = unidad(1L, semestreAntecedente, estado); UnidadEvaluacionCurricularV2DTO b = unidad(2L, semestreAntecedente + 1, EstadoUnidadCurricularV2.NO_INSCRITA); b.getRelacionesSeriacion().add(relacion(1L, 2L, semestreAntecedente, semestreAntecedente + 1)); c.getUnidades().add(a); c.getUnidades().add(b); return c; }
    private ContextoEvaluacionCurricularV2DTO cadena(EstadoUnidadCurricularV2 estadoA, EstadoUnidadCurricularV2 estadoB) { ContextoEvaluacionCurricularV2DTO c = base(5); UnidadEvaluacionCurricularV2DTO a = unidad(1L, 1, estadoA); UnidadEvaluacionCurricularV2DTO b = unidad(2L, 3, estadoB); UnidadEvaluacionCurricularV2DTO d = unidad(3L, 5, EstadoUnidadCurricularV2.NO_INSCRITA); b.getRelacionesSeriacion().add(relacion(1L, 2L, 1, 3)); d.getRelacionesSeriacion().add(relacion(2L, 3L, 3, 5)); c.getUnidades().add(a); c.getUnidades().add(b); c.getUnidades().add(d); return c; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd("Obligatoria"); u.setSemestre(Integer.valueOf(semestre)); u.setEstado(estado); u.setOfertada(Boolean.TRUE); u.setGrupoActivoConocido(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE); if (estado == EstadoUnidadCurricularV2.REPROBADA) { u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setReprobacionVigenteConocida(Boolean.TRUE); } return u; }
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id, int semestre) { return unidad(id, semestre, EstadoUnidadCurricularV2.REPROBADA); }
    private RelacionSeriacionCurricularV2DTO relacion(Long a, Long b, int sa, int sb) { RelacionSeriacionCurricularV2DTO r = new RelacionSeriacionCurricularV2DTO(); r.setIdAntecedente(a); r.setIdConsecuente(b); r.setSemestreAntecedente(Integer.valueOf(sa)); r.setSemestreConsecuente(Integer.valueOf(sb)); r.setTipoSeriacion(TipoSeriacionV2.INTERANUAL); r.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA); r.setEvidencia("PE_TEST:" + a + "->" + b); return r; }
    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO c) { return servicio.evaluarSombra(c).getEvaluacionCargaAcademica(); }
    private RelacionSeriacionCurricularV2DTO decisionRaiz(EvaluacionCargaAcademicaV2DTO carga) { for (RelacionSeriacionCurricularV2DTO r : carga.getRelacionesSeriacionV2()) if (Integer.valueOf(1).equals(r.getProfundidadCadena())) return r; return carga.getRelacionesSeriacionV2().get(0); }
    private boolean existeIndirecta(EvaluacionCargaAcademicaV2DTO carga, Long antecedente) { for (RelacionSeriacionCurricularV2DTO r : carga.getRelacionesSeriacionV2()) if (antecedente.equals(r.getIdAntecedente()) && r.getTipoRelacion() == TipoRelacionSeriacionV2.INDIRECTA) return true; return false; }
}
