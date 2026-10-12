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

/** RN-8.2: seriación entre semestres consecutivos en modo sombra. */
public class EvaluacionCurricularV2Fase8BTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f8BU01SinRelacionFormalNoAplica() { assertFalse(Boolean.TRUE.equals(carga(base(4)).getAplicaRn82())); }
    @Test public void f8BU02RelacionS3AS4FormalAplica() { assertTrue(Boolean.TRUE.equals(carga(contexto(3, EstadoUnidadCurricularV2.ACREDITADA)).getAplicaRn82())); }
    @Test public void f8BU03AntecedenteAcreditadoCumple() { assertEquals(relacion(carga(contexto(3, EstadoUnidadCurricularV2.ACREDITADA))).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA); }
    @Test public void f8BU04AntecedenteReprobadoNoCumple() { assertEquals(relacion(carga(contexto(3, EstadoUnidadCurricularV2.REPROBADA))).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA); }

    @Test public void f8BU05NoCursadaBajaParcialYNoInscritaNoSonReprobacion() {
        for (EstadoUnidadCurricularV2 estado : new EstadoUnidadCurricularV2[] { EstadoUnidadCurricularV2.NO_CURSADA, EstadoUnidadCurricularV2.BAJA_PARCIAL, EstadoUnidadCurricularV2.NO_INSCRITA }) {
            ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto(3, estado));
            assertEquals(relacion(resultado.getEvaluacionCargaAcademica()).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
            assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        }
    }

    @Test public void f8BU06EnCursoNoCumpleNiSeCondicionaComoRn81() {
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto(3, EstadoUnidadCurricularV2.EN_CURSO));
        assertEquals(relacion(carga).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
        assertFalse(carga.getUdsIntersemestralesCumplidasRn82().contains(2L));
    }

    @Test public void f8BU07EstadoNoDeterminadoRequiereRevision() {
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto(3, EstadoUnidadCurricularV2.NO_DETERMINADO));
        assertEquals(relacion(carga).getDecision(), EstadoCumplimientoSeriacionV2.REQUIERE_REVISION);
        assertTrue(Boolean.TRUE.equals(carga.getRequiereRevisionRn82()));
    }

    @Test public void f8BU08EstadoVigenteAcreditadoSuperaHistoriaReprobada() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(3, EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().get(0).getReferenciasHistoricas().add("HISTORICO_REPROBADA");
        assertEquals(relacion(carga(contexto)).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
    }

    @Test public void f8BU09RelacionNoConsecutivaRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(3, EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().get(1).getRelacionesSeriacion().get(0).setSemestreConsecuente(Integer.valueOf(5));
        assertTrue(Boolean.TRUE.equals(carga(contexto).getRequiereRevisionRn82()));
    }

    @Test public void f8BU10SemestresConsecutivosSinRelacionPeNoCreanSeriacion() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(unidad(1L, 3, EstadoUnidadCurricularV2.REPROBADA)); contexto.getUnidades().add(unidad(2L, 4, EstadoUnidadCurricularV2.NO_INSCRITA));
        assertFalse(Boolean.TRUE.equals(carga(contexto).getAplicaRn82()));
    }

    @Test public void f8BU11DosAntecedentesAcreditadosCumplen() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(3, EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().add(unidad(3L, 3, EstadoUnidadCurricularV2.ACREDITADA)); contexto.getUnidades().get(1).getRelacionesSeriacion().add(relacion(3L, 2L, 3, 4));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto); assertEquals(carga.getUdsIntersemestralesCumplidasRn82().size(), 1); assertEquals(carga.getRelacionesSeriacionV2().size(), 2);
    }

    @Test public void f8BU12UnoDeVariosAntecedentesNoAcreditadoBloqueaConsecuente() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(3, EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().add(unidad(3L, 3, EstadoUnidadCurricularV2.REPROBADA)); contexto.getUnidades().get(1).getRelacionesSeriacion().add(relacion(3L, 2L, 3, 4));
        assertTrue(carga(contexto).getUdsIntersemestralesNoCumplidasRn82().contains(2L));
    }

    @Test public void f8BU13OfertaYCupoSonIndependientesDeSeriacion() {
        ContextoEvaluacionCurricularV2DTO oferta = contexto(3, EstadoUnidadCurricularV2.REPROBADA); oferta.getUnidades().get(1).setOfertada(Boolean.TRUE); assertEquals(relacion(carga(oferta)).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
        ContextoEvaluacionCurricularV2DTO cupo = contexto(3, EstadoUnidadCurricularV2.ACREDITADA); cupo.getUnidades().get(1).setCupoDisponibleConocido(Boolean.FALSE); cupo.getUnidades().get(1).setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO); assertEquals(relacion(carga(cupo)).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
    }

    @Test public void f8BU14SinOfertaNoEliminaSeriacionCumplida() { ContextoEvaluacionCurricularV2DTO contexto = contexto(3, EstadoUnidadCurricularV2.ACREDITADA); contexto.getUnidades().get(1).setOfertada(Boolean.FALSE); assertEquals(relacion(carga(contexto)).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA); }

    @Test public void f8BU15Rn64ConcurrenteNoConvierteCumplidaEnNoCumplida() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(3, EstadoUnidadCurricularV2.ACREDITADA); for (long id = 10; id < 14; id++) contexto.getUnidades().add(reprobada(id, 2));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto); assertTrue(Boolean.TRUE.equals(carga.getAplicaRn64())); assertEquals(relacion(carga).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
    }

    @Test public void f8BU16S4AS5CoexisteConRn61SinSustituirla() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(4, EstadoUnidadCurricularV2.ACREDITADA); contexto.setSemestreObjetivo(Integer.valueOf(5));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto); assertEquals(relacion(carga).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA); assertTrue(carga.getReglasRelacionadas().contains(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2.RN_6_1));
    }

    @Test public void f8BU17S5AS6NoReactivaRn61YPermiteRn82() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(5, EstadoUnidadCurricularV2.ACREDITADA); contexto.setSemestreObjetivo(Integer.valueOf(6));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto); assertTrue(Boolean.TRUE.equals(carga.getAplicaRn82())); assertFalse(Boolean.TRUE.equals(carga.getAplicaCtr01()));
    }

    @Test public void f8BU18RelacionIndirectaSeConservaParaFasePosteriorSinInventarCadena() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(3, EstadoUnidadCurricularV2.ACREDITADA); contexto.getUnidades().get(1).getRelacionesSeriacion().get(0).setTipoRelacion(TipoRelacionSeriacionV2.INDIRECTA);
        assertEquals(relacion(carga(contexto)).getDecision(), EstadoCumplimientoSeriacionV2.REQUIERE_REVISION);
    }

    @Test public void f8BU19DuplicadoTecnicoSeConsolida() { ContextoEvaluacionCurricularV2DTO contexto = contexto(3, EstadoUnidadCurricularV2.ACREDITADA); contexto.getUnidades().get(1).getRelacionesSeriacion().add(relacion(1L, 2L, 3, 4)); assertEquals(carga(contexto).getRelacionesSeriacionV2().size(), 1); }

    @Test public void f8BU20TransicionesActualizanEstadoVigente() {
        assertEquals(relacion(carga(contexto(3, EstadoUnidadCurricularV2.EN_CURSO))).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
        assertEquals(relacion(carga(contexto(3, EstadoUnidadCurricularV2.ACREDITADA))).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
        assertEquals(relacion(carga(contexto(3, EstadoUnidadCurricularV2.REPROBADA))).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
    }

    @Test public void f8BU21Rn81EnCursoPermaneceCondicionada() {
        ContextoEvaluacionCurricularV2DTO contexto = base(2); UnidadEvaluacionCurricularV2DTO b1 = unidad(1L, 2, EstadoUnidadCurricularV2.EN_CURSO); b1.setBloque("1"); UnidadEvaluacionCurricularV2DTO b2 = unidad(2L, 2, EstadoUnidadCurricularV2.NO_INSCRITA); b2.setBloque("2"); RelacionSeriacionCurricularV2DTO relacion = relacion(1L, 2L, 2, 2); relacion.setTipoSeriacion(TipoSeriacionV2.INTERBLOQUE); relacion.setBloqueAntecedente("1"); relacion.setBloqueConsecuente("2"); b2.getRelacionesSeriacion().add(relacion); contexto.getUnidades().add(b1); contexto.getUnidades().add(b2);
        assertEquals(relacion(carga(contexto)).getDecision(), EstadoCumplimientoSeriacionV2.CONDICIONADA);
    }

    @Test public void f8BU22ResultadoDeterministaEIdempotenteSinEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(3, EstadoUnidadCurricularV2.ACREDITADA); ResultadoEvaluacionCurricularV2DTO primera = servicio.evaluarSombra(contexto); ResultadoEvaluacionCurricularV2DTO segunda = servicio.evaluarSombra(contexto);
        assertEquals(relacion(primera.getEvaluacionCargaAcademica()).getDecision(), relacion(segunda.getEvaluacionCargaAcademica()).getDecision()); assertFalse(Boolean.TRUE.equals(segunda.getEfectosProductivosAplicados()));
    }

    private ContextoEvaluacionCurricularV2DTO base(int objetivo) { ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO(); contexto.setSemestreObjetivo(Integer.valueOf(objetivo)); contexto.setPeriodoNormativo("2027-1"); contexto.setSeriacionComprobada(Boolean.TRUE); return contexto; }
    private ContextoEvaluacionCurricularV2DTO contexto(int semestreAntecedente, EstadoUnidadCurricularV2 estado) { ContextoEvaluacionCurricularV2DTO contexto = base(semestreAntecedente + 1); UnidadEvaluacionCurricularV2DTO antecedente = unidad(1L, semestreAntecedente, estado); UnidadEvaluacionCurricularV2DTO consecuente = unidad(2L, semestreAntecedente + 1, EstadoUnidadCurricularV2.NO_INSCRITA); consecuente.getRelacionesSeriacion().add(relacion(1L, 2L, semestreAntecedente, semestreAntecedente + 1)); contexto.getUnidades().add(antecedente); contexto.getUnidades().add(consecuente); return contexto; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO(); unidad.setIdPrograma(id); unidad.setClave("UD" + id); unidad.setTipoUd("Obligatoria"); unidad.setSemestre(Integer.valueOf(semestre)); unidad.setEstado(estado); unidad.setOfertada(Boolean.TRUE); unidad.setGrupoActivoConocido(Boolean.TRUE); unidad.setCupoDisponibleConocido(Boolean.TRUE); if (estado == EstadoUnidadCurricularV2.REPROBADA) { unidad.setCausa(CausaPendienteUnidadV2.REPROBACION); unidad.setReprobacionVigenteConocida(Boolean.TRUE); } return unidad; }
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id, int semestre) { return unidad(id, semestre, EstadoUnidadCurricularV2.REPROBADA); }
    private RelacionSeriacionCurricularV2DTO relacion(Long antecedente, Long consecuente, int semestreAntecedente, int semestreConsecuente) { RelacionSeriacionCurricularV2DTO relacion = new RelacionSeriacionCurricularV2DTO(); relacion.setIdAntecedente(antecedente); relacion.setIdConsecuente(consecuente); relacion.setSemestreAntecedente(Integer.valueOf(semestreAntecedente)); relacion.setSemestreConsecuente(Integer.valueOf(semestreConsecuente)); relacion.setTipoSeriacion(TipoSeriacionV2.INTERSEMESTRAL); relacion.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA); relacion.setEvidencia("PE_TEST:" + antecedente + "->" + consecuente); return relacion; }
    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) { return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica(); }
    private RelacionSeriacionCurricularV2DTO relacion(EvaluacionCargaAcademicaV2DTO carga) { return carga.getRelacionesSeriacionV2().get(0); }
}
