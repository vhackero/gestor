package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCierreFase8V2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCumplimientoSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.RelacionSeriacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoRelacionSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cierre F8D: integración del modelo único de seriación V2, sólo en sombra. */
public class EvaluacionCurricularV2Fase8DTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f8DU01DiferenciasEnCursoSeConservanPorTipo() {
        ContextoEvaluacionCurricularV2DTO c = base(5);
        agregarRelacion(c, 1L, 2L, 2, 2, "1", "2", TipoSeriacionV2.INTERBLOQUE, EstadoUnidadCurricularV2.EN_CURSO);
        agregarRelacion(c, 3L, 4L, 3, 4, null, null, TipoSeriacionV2.INTERSEMESTRAL, EstadoUnidadCurricularV2.EN_CURSO);
        agregarRelacion(c, 5L, 6L, 1, 5, null, null, TipoSeriacionV2.INTERANUAL, EstadoUnidadCurricularV2.EN_CURSO);
        EvaluacionCargaAcademicaV2DTO carga = carga(c);
        assertEquals(relacion(carga, 1L, 2L, TipoSeriacionV2.INTERBLOQUE).getDecision(), EstadoCumplimientoSeriacionV2.CONDICIONADA);
        assertEquals(relacion(carga, 3L, 4L, TipoSeriacionV2.INTERSEMESTRAL).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
        assertEquals(relacion(carga, 5L, 6L, TipoSeriacionV2.INTERANUAL).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
        assertEquals(carga.getEstadoCierreFase8(), EstadoCierreFase8V2.CONSOLIDADA_EN_SOMBRA);
    }

    @Test public void f8DU02AcreditacionVigenteCumpleLasTresVariantes() {
        ContextoEvaluacionCurricularV2DTO c = base(5);
        agregarRelacion(c, 1L, 2L, 2, 2, "1", "2", TipoSeriacionV2.INTERBLOQUE, EstadoUnidadCurricularV2.ACREDITADA);
        agregarRelacion(c, 3L, 4L, 3, 4, null, null, TipoSeriacionV2.INTERSEMESTRAL, EstadoUnidadCurricularV2.ACREDITADA);
        agregarRelacion(c, 5L, 6L, 1, 5, null, null, TipoSeriacionV2.INTERANUAL, EstadoUnidadCurricularV2.ACREDITADA);
        EvaluacionCargaAcademicaV2DTO carga = carga(c);
        assertEquals(relacion(carga, 1L, 2L, TipoSeriacionV2.INTERBLOQUE).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
        assertEquals(relacion(carga, 3L, 4L, TipoSeriacionV2.INTERSEMESTRAL).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
        assertEquals(relacion(carga, 5L, 6L, TipoSeriacionV2.INTERANUAL).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
    }

    @Test public void f8DU03NoCursadaYBajaParcialNoCreanReprobacion() {
        ContextoEvaluacionCurricularV2DTO c = base(5);
        agregarRelacion(c, 1L, 2L, 3, 4, null, null, TipoSeriacionV2.INTERSEMESTRAL, EstadoUnidadCurricularV2.NO_CURSADA);
        agregarRelacion(c, 3L, 4L, 1, 5, null, null, TipoSeriacionV2.INTERANUAL, EstadoUnidadCurricularV2.BAJA_PARCIAL);
        EvaluacionCargaAcademicaV2DTO carga = carga(c);
        assertTrue(carga.getUdsIntersemestralesNoCumplidasRn82().contains(2L));
        assertTrue(carga.getUdsInteranualesNoCumplidasRn83().contains(4L));
        assertEquals(servicio.evaluarSombra(c).getCantidadReprobadasVigentes(), Integer.valueOf(0));
    }

    @Test public void f8DU04EstadoVigenteAcreditadoSuperaHistoria() {
        ContextoEvaluacionCurricularV2DTO c = base(3);
        UnidadEvaluacionCurricularV2DTO antecedente = unidad(1L, 2, EstadoUnidadCurricularV2.ACREDITADA);
        antecedente.getReferenciasHistoricas().add("HISTORICO_REPROBADA");
        UnidadEvaluacionCurricularV2DTO consecuente = unidad(2L, 3, EstadoUnidadCurricularV2.NO_INSCRITA);
        consecuente.getRelacionesSeriacion().add(relacion(1L, 2L, 2, 3, null, null, TipoSeriacionV2.INTERANUAL));
        c.getUnidades().add(antecedente); c.getUnidades().add(consecuente);
        assertEquals(relacion(carga(c), 1L, 2L, TipoSeriacionV2.INTERANUAL).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
    }

    @Test public void f8DU05CadenaYMultiplesAntecedentesSeDeduplican() {
        ContextoEvaluacionCurricularV2DTO c = base(5);
        agregarRelacion(c, 1L, 2L, 1, 3, null, null, TipoSeriacionV2.INTERANUAL, EstadoUnidadCurricularV2.ACREDITADA);
        agregarRelacion(c, 2L, 3L, 3, 5, null, null, TipoSeriacionV2.INTERANUAL, EstadoUnidadCurricularV2.ACREDITADA);
        agregarRelacion(c, 1L, 3L, 1, 5, null, null, TipoSeriacionV2.INTERANUAL, EstadoUnidadCurricularV2.ACREDITADA);
        EvaluacionCargaAcademicaV2DTO carga = carga(c);
        assertTrue(carga.getUdsInteranualesCumplidasRn83().contains(3L));
        assertEquals(antecedentes(carga, 1L, TipoRelacionSeriacionV2.INDIRECTA), 1);
    }

    @Test public void f8DU06CicloYTipoInconsistenteRequierenRevision() {
        ContextoEvaluacionCurricularV2DTO ciclo = base(5);
        agregarRelacion(ciclo, 1L, 2L, 1, 3, null, null, TipoSeriacionV2.INTERANUAL, EstadoUnidadCurricularV2.ACREDITADA);
        agregarRelacion(ciclo, 2L, 1L, 3, 1, null, null, TipoSeriacionV2.INTERANUAL, EstadoUnidadCurricularV2.ACREDITADA);
        assertEquals(carga(ciclo).getEstadoCierreFase8(), EstadoCierreFase8V2.REQUIERE_REVISION);
        ContextoEvaluacionCurricularV2DTO inconsistente = base(4);
        agregarRelacion(inconsistente, 10L, 11L, 3, 4, null, null, TipoSeriacionV2.INTERSEMESTRAL, EstadoUnidadCurricularV2.ACREDITADA);
        UnidadEvaluacionCurricularV2DTO misma = inconsistente.getUnidades().get(1);
        misma.getRelacionesSeriacion().add(relacion(10L, 11L, 3, 4, null, null, TipoSeriacionV2.INTERANUAL));
        EvaluacionCargaAcademicaV2DTO carga = carga(inconsistente);
        assertEquals(carga.getEstadoCierreFase8(), EstadoCierreFase8V2.REQUIERE_REVISION);
        assertFalse(carga.getRelacionesTipoInconsistenteFase8().isEmpty());
    }

    @Test public void f8DU07AusenciaRelacionNoBloqueaPorCierreAnualLegacy() {
        ContextoEvaluacionCurricularV2DTO c = base(3);
        c.getUnidades().add(unidad(1L, 2, EstadoUnidadCurricularV2.REPROBADA));
        c.getUnidades().add(unidad(2L, 3, EstadoUnidadCurricularV2.NO_INSCRITA));
        EvaluacionCargaAcademicaV2DTO carga = carga(c);
        assertFalse(Boolean.TRUE.equals(carga.getAplicaRn83()));
        assertFalse(carga.getUdsInteranualesNoCumplidasRn83().contains(2L));
    }

    @Test public void f8DU08S2S3YS6S7NoReactivanRn61() {
        ContextoEvaluacionCurricularV2DTO s3 = directoInteranual(2, EstadoUnidadCurricularV2.REPROBADA);
        ContextoEvaluacionCurricularV2DTO s7 = directoInteranual(6, EstadoUnidadCurricularV2.REPROBADA);
        s7.setSemestreObjetivo(Integer.valueOf(7));
        assertFalse(Boolean.TRUE.equals(carga(s3).getAplicaCtr01()));
        assertFalse(Boolean.TRUE.equals(carga(s7).getAplicaCtr01()));
    }

    @Test public void f8DU09S5ConservaConcurrenciaIndependienteConRn61() {
        ContextoEvaluacionCurricularV2DTO c = directoInteranual(3, EstadoUnidadCurricularV2.ACREDITADA);
        c.setSemestreObjetivo(Integer.valueOf(5));
        EvaluacionCargaAcademicaV2DTO carga = carga(c);
        assertTrue(carga.getReglasRelacionadas().contains(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2.RN_6_1));
        assertTrue(carga.getUdsInteranualesCumplidasRn83().contains(2L));
    }

    @Test public void f8DU10OfertaCupoCargaYRn64NoAlteranSeriacion() {
        ContextoEvaluacionCurricularV2DTO c = directoInteranual(2, EstadoUnidadCurricularV2.ACREDITADA);
        c.getUnidades().get(1).setOfertada(Boolean.FALSE);
        c.getUnidades().get(1).setCupoDisponibleConocido(Boolean.FALSE);
        for (long id = 10; id < 14; id++) c.getUnidades().add(unidad(id, 1, EstadoUnidadCurricularV2.REPROBADA));
        EvaluacionCargaAcademicaV2DTO carga = carga(c);
        assertTrue(carga.getUdsInteranualesCumplidasRn83().contains(2L));
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn64()));
    }

    @Test public void f8DU11TipoUdYPrioridadNoSeMutanPorSeriacion() {
        ContextoEvaluacionCurricularV2DTO c = directoInteranual(2, EstadoUnidadCurricularV2.NO_CURSADA);
        c.getUnidades().get(1).setTipoUd("Optativa");
        c.getUnidades().get(1).setEstado(EstadoUnidadCurricularV2.REPROBADA);
        c.getUnidades().get(1).setCausa(CausaPendienteUnidadV2.REPROBACION);
        EvaluacionCargaAcademicaV2DTO carga = carga(c);
        assertEquals(c.getUnidades().get(1).getTipoUd(), "Optativa");
        assertTrue(carga.getUdsInteranualesNoCumplidasRn83().contains(2L));
    }

    @Test public void f8DU12DuplicadosSonIdempotentesYNoHayEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO c = directoInteranual(2, EstadoUnidadCurricularV2.ACREDITADA);
        c.getUnidades().get(1).getRelacionesSeriacion().add(relacion(1L, 2L, 2, 3, null, null, TipoSeriacionV2.INTERANUAL));
        EvaluacionCargaAcademicaV2DTO primera = carga(c); EvaluacionCargaAcademicaV2DTO segunda = carga(c);
        assertEquals(primera.getDecisionesCierreFase8(), segunda.getDecisionesCierreFase8());
        assertEquals(primera.getRelacionesSeriacionV2().size(), segunda.getRelacionesSeriacionV2().size());
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(c).getEfectosProductivosAplicados()));
    }

    private ContextoEvaluacionCurricularV2DTO base(int objetivo) { ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setSemestreObjetivo(Integer.valueOf(objetivo)); c.setPeriodoNormativo("2027-1"); c.setSeriacionComprobada(Boolean.TRUE); return c; }
    private ContextoEvaluacionCurricularV2DTO directoInteranual(int semestre, EstadoUnidadCurricularV2 estado) { ContextoEvaluacionCurricularV2DTO c = base(semestre + 1); agregarRelacion(c, 1L, 2L, semestre, semestre + 1, null, null, TipoSeriacionV2.INTERANUAL, estado); return c; }
    private void agregarRelacion(ContextoEvaluacionCurricularV2DTO c, Long antecedente, Long consecuente, int sa, int sc, String ba, String bc, TipoSeriacionV2 tipo, EstadoUnidadCurricularV2 estado) {
        UnidadEvaluacionCurricularV2DTO a = buscar(c, antecedente); if (a == null) { a = unidad(antecedente, sa, estado); c.getUnidades().add(a); } else a.setEstado(estado);
        UnidadEvaluacionCurricularV2DTO b = buscar(c, consecuente); if (b == null) { b = unidad(consecuente, sc, EstadoUnidadCurricularV2.NO_INSCRITA); c.getUnidades().add(b); }
        b.setBloque(bc); a.setBloque(ba); b.getRelacionesSeriacion().add(relacion(antecedente, consecuente, sa, sc, ba, bc, tipo));
    }
    private UnidadEvaluacionCurricularV2DTO buscar(ContextoEvaluacionCurricularV2DTO c, Long id) { for (UnidadEvaluacionCurricularV2DTO u : c.getUnidades()) if (id.equals(u.getIdPrograma())) return u; return null; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd("Obligatoria"); u.setSemestre(Integer.valueOf(semestre)); u.setEstado(estado); u.setOfertada(Boolean.TRUE); u.setGrupoActivoConocido(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE); if (estado == EstadoUnidadCurricularV2.REPROBADA) { u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setReprobacionVigenteConocida(Boolean.TRUE); } return u; }
    private RelacionSeriacionCurricularV2DTO relacion(Long a, Long b, int sa, int sb, String ba, String bb, TipoSeriacionV2 tipo) { RelacionSeriacionCurricularV2DTO r = new RelacionSeriacionCurricularV2DTO(); r.setIdAntecedente(a); r.setIdConsecuente(b); r.setSemestreAntecedente(Integer.valueOf(sa)); r.setSemestreConsecuente(Integer.valueOf(sb)); r.setBloqueAntecedente(ba); r.setBloqueConsecuente(bb); r.setTipoSeriacion(tipo); r.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA); r.setEvidencia("PE_F8D:" + a + "->" + b); return r; }
    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO c) { return servicio.evaluarSombra(c).getEvaluacionCargaAcademica(); }
    private RelacionSeriacionCurricularV2DTO relacion(EvaluacionCargaAcademicaV2DTO carga, Long a, Long b, TipoSeriacionV2 tipo) { for (RelacionSeriacionCurricularV2DTO r : carga.getRelacionesSeriacionV2()) if (a.equals(r.getIdAntecedente()) && b.equals(r.getIdConsecuente()) && tipo == r.getTipoSeriacion()) return r; fail("No se encontró relación " + a + "->" + b + ":" + tipo); return null; }
    private int antecedentes(EvaluacionCargaAcademicaV2DTO carga, Long a, TipoRelacionSeriacionV2 tipo) { int total = 0; for (RelacionSeriacionCurricularV2DTO r : carga.getRelacionesSeriacionV2()) if (a.equals(r.getIdAntecedente()) && tipo == r.getTipoRelacion()) total++; return total; }
}
