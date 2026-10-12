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

/** RN-8/RN-8.1: relación del PE B1->B2 evaluada sólo en modo sombra. */
public class EvaluacionCurricularV2Fase8ATest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f8AU01SinRelacionInterbloqueRn81NoAplica() {
        assertFalse(Boolean.TRUE.equals(carga(base()).getAplicaRn81()));
    }

    @Test public void f8AU02AntecedenteAcreditadoEnPeriodoPrevioCumple() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().get(0).getReferenciasHistoricas().add("ACREDITADA_PERIODO_PREVIO");
        assertEquals(relacion(carga(contexto)).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
    }

    @Test public void f8AU03AntecedenteAcreditadoEnPeriodoActualCumple() {
        assertEquals(relacion(carga(contexto(EstadoUnidadCurricularV2.ACREDITADA))).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
    }

    @Test public void f8AU04AntecedenteEnCursoCondicionaSinHabilitarDefinitivamente() {
        assertEquals(relacion(carga(contexto(EstadoUnidadCurricularV2.EN_CURSO))).getDecision(), EstadoCumplimientoSeriacionV2.CONDICIONADA);
    }

    @Test public void f8AU05AntecedenteReprobadoNoCumple() {
        assertEquals(relacion(carga(contexto(EstadoUnidadCurricularV2.REPROBADA))).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
    }

    @Test public void f8AU06NoCursadaNoSeTransformaEnReprobada() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.NO_CURSADA);
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(relacion(resultado.getEvaluacionCargaAcademica()).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
    }

    @Test public void f8AU07BajaParcialNoCumpleSinConvertirseEnReprobada() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.BAJA_PARCIAL);
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(relacion(resultado.getEvaluacionCargaAcademica()).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
    }

    @Test public void f8AU08AntecedenteNoDeterminadoRequiereRevision() {
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto(EstadoUnidadCurricularV2.NO_DETERMINADO));
        assertEquals(relacion(carga).getDecision(), EstadoCumplimientoSeriacionV2.REQUIERE_REVISION);
        assertTrue(Boolean.TRUE.equals(carga.getRequiereRevisionRn81()));
    }

    @Test public void f8AU09EstadoVigenteAcreditadoPrevaleceSobreHistoriaReprobada() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().get(0).getReferenciasHistoricas().add("HISTORICO_REPROBADA");
        assertEquals(relacion(carga(contexto)).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
    }

    @Test public void f8AU10OfertaNoEquivaleASeriacion() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.REPROBADA);
        contexto.getUnidades().get(1).setOfertada(Boolean.TRUE);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(relacion(carga).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
    }

    @Test public void f8AU11SinOfertaNoEliminaSeriacionCumplida() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().get(1).setOfertada(Boolean.FALSE);
        assertEquals(relacion(carga(contexto)).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
    }

    @Test public void f8AU12SinCupoNoSeConvierteEnFallaDeSeriacion() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        UnidadEvaluacionCurricularV2DTO b2 = contexto.getUnidades().get(1);
        b2.setCupoDisponibleConocido(Boolean.FALSE); b2.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO);
        assertEquals(relacion(carga(contexto)).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
    }

    @Test public void f8AU13Rn64EsRestriccionConcurrenteNoFallaDeSeriacion() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        for (long id = 10; id < 14; id++) contexto.getUnidades().add(reprobada(id));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn64()));
        assertEquals(relacion(carga).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
    }

    @Test public void f8AU14DosAntecedentesAcreditadosCumplen() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        UnidadEvaluacionCurricularV2DTO segundo = b1(3L, EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().add(segundo); contexto.getUnidades().get(1).getRelacionesSeriacion().add(relacion(3L, 2L));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getUdsInterbloqueCumplidasRn81().size(), 1);
        assertEquals(carga.getRelacionesSeriacionV2().size(), 2);
    }

    @Test public void f8AU15UnoDeVariosAntecedentesNoAcreditadoNoCumple() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().add(b1(3L, EstadoUnidadCurricularV2.REPROBADA)); contexto.getUnidades().get(1).getRelacionesSeriacion().add(relacion(3L, 2L));
        assertTrue(carga(contexto).getUdsInterbloqueNoCumplidasRn81().contains(2L));
    }

    @Test public void f8AU16RelacionIncompletaRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().get(1).getRelacionesSeriacion().get(0).setBloqueAntecedente(null);
        assertTrue(Boolean.TRUE.equals(carga(contexto).getRequiereRevisionRn81()));
    }

    @Test public void f8AU17TipoSeriacionNoDeterminadoRequiereRevisionSinIniciarRn82() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().get(1).getRelacionesSeriacion().get(0).setTipoSeriacion(TipoSeriacionV2.NO_DETERMINADA);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertFalse(Boolean.TRUE.equals(carga.getAplicaRn81()));
        assertEquals(relacion(carga).getDecision(), EstadoCumplimientoSeriacionV2.REQUIERE_REVISION);
    }

    @Test public void f8AU18InterbloqueFueraDelMismoPeriodoNoSeClasificaAutomaticamente() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().get(1).getRelacionesSeriacion().get(0).setSemestreAntecedente(Integer.valueOf(1));
        assertTrue(Boolean.TRUE.equals(carga(contexto).getRequiereRevisionRn81()));
    }

    @Test public void f8AU19DuplicadoTecnicoDeRelacionSeEvaluaUnaSolaVez() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        contexto.getUnidades().get(1).getRelacionesSeriacion().add(relacion(1L, 2L));
        assertEquals(carga(contexto).getRelacionesSeriacionV2().size(), 1);
    }

    @Test public void f8AU20TransicionesEnCursoAcreditadaYReprobadaActualizanResultado() {
        assertEquals(relacion(carga(contexto(EstadoUnidadCurricularV2.EN_CURSO))).getDecision(), EstadoCumplimientoSeriacionV2.CONDICIONADA);
        assertEquals(relacion(carga(contexto(EstadoUnidadCurricularV2.ACREDITADA))).getDecision(), EstadoCumplimientoSeriacionV2.CUMPLIDA);
        assertEquals(relacion(carga(contexto(EstadoUnidadCurricularV2.REPROBADA))).getDecision(), EstadoCumplimientoSeriacionV2.NO_CUMPLIDA);
    }

    @Test public void f8AU21ResultadoEsDeterministaEIdempotente() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(EstadoUnidadCurricularV2.ACREDITADA);
        EvaluacionCargaAcademicaV2DTO primera = carga(contexto); EvaluacionCargaAcademicaV2DTO segunda = carga(contexto);
        assertEquals(relacion(primera).getDecision(), relacion(segunda).getDecision());
        assertEquals(primera.getRelacionesSeriacionV2().size(), segunda.getRelacionesSeriacionV2().size());
    }

    @Test public void f8AU22NoExisteEfectoProductivo() {
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(contexto(EstadoUnidadCurricularV2.ACREDITADA)).getEfectosProductivosAplicados()));
    }

    private ContextoEvaluacionCurricularV2DTO base() { ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO(); contexto.setSemestreObjetivo(Integer.valueOf(2)); contexto.setPeriodoNormativo("2027-1"); contexto.setSeriacionComprobada(Boolean.TRUE); return contexto; }
    private ContextoEvaluacionCurricularV2DTO contexto(EstadoUnidadCurricularV2 estadoAntecedente) { ContextoEvaluacionCurricularV2DTO contexto = base(); UnidadEvaluacionCurricularV2DTO b1 = b1(1L, estadoAntecedente); UnidadEvaluacionCurricularV2DTO b2 = b2(2L); b2.getRelacionesSeriacion().add(relacion(1L, 2L)); contexto.getUnidades().add(b1); contexto.getUnidades().add(b2); return contexto; }
    private UnidadEvaluacionCurricularV2DTO b1(Long id, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO unidad = unidad(id, estado, "1"); if (estado == EstadoUnidadCurricularV2.REPROBADA) { unidad.setCausa(CausaPendienteUnidadV2.REPROBACION); unidad.setReprobacionVigenteConocida(Boolean.TRUE); } return unidad; }
    private UnidadEvaluacionCurricularV2DTO b2(Long id) { return unidad(id, EstadoUnidadCurricularV2.NO_INSCRITA, "2"); }
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id) { UnidadEvaluacionCurricularV2DTO unidad = unidad(id, EstadoUnidadCurricularV2.REPROBADA, "1"); unidad.setCausa(CausaPendienteUnidadV2.REPROBACION); unidad.setReprobacionVigenteConocida(Boolean.TRUE); return unidad; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, EstadoUnidadCurricularV2 estado, String bloque) { UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO(); unidad.setIdPrograma(id); unidad.setClave("UD" + id); unidad.setTipoUd("Obligatoria"); unidad.setSemestre(Integer.valueOf(2)); unidad.setBloque(bloque); unidad.setEstado(estado); unidad.setOfertada(Boolean.TRUE); unidad.setGrupoActivoConocido(Boolean.TRUE); unidad.setCupoDisponibleConocido(Boolean.TRUE); return unidad; }
    private RelacionSeriacionCurricularV2DTO relacion(Long antecedente, Long consecuente) { RelacionSeriacionCurricularV2DTO relacion = new RelacionSeriacionCurricularV2DTO(); relacion.setIdAntecedente(antecedente); relacion.setIdConsecuente(consecuente); relacion.setSemestreAntecedente(Integer.valueOf(2)); relacion.setSemestreConsecuente(Integer.valueOf(2)); relacion.setBloqueAntecedente("1"); relacion.setBloqueConsecuente("2"); relacion.setTipoSeriacion(TipoSeriacionV2.INTERBLOQUE); relacion.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA); relacion.setEvidencia("PE_TEST:" + antecedente + "->" + consecuente); return relacion; }
    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) { return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica(); }
    private RelacionSeriacionCurricularV2DTO relacion(EvaluacionCargaAcademicaV2DTO carga) { return carga.getRelacionesSeriacionV2().get(0); }
}
