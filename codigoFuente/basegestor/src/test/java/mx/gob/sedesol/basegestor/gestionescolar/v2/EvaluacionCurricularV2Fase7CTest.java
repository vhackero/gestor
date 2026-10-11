package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoPrincipioOptativaElectivaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cobertura F7C: principio transversal RN-7.3 sin duplicar reglas comunes. */
public class EvaluacionCurricularV2Fase7CTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f7CU01SinOptativasNiElectivasReprobadasNoAplica() {
        assertEquals(carga(base(4)).getEstadoPrincipioOptativaElectiva(), EstadoPrincipioOptativaElectivaV2.NO_APLICA);
    }

    @Test public void f7CU02OptativaYElectivaReprobadasAlimentanIrregularidadComun() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Optativa")); contexto.getUnidades().add(reprobada(2L, "Electiva"));
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(resultado.getSituacionAcademica(), "IRREGULAR");
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(2));
        assertEquals(resultado.getEvaluacionCargaAcademica().getCantidadOptativasReprobadasRn73(), Integer.valueOf(1));
        assertEquals(resultado.getEvaluacionCargaAcademica().getCantidadElectivasReprobadasRn73(), Integer.valueOf(1));
    }

    @Test public void f7CU03TresCombinadasNoActivanRn64NiBloqueoAutonomo() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Optativa")); contexto.getUnidades().add(reprobada(2L, "Optativa")); contexto.getUnidades().add(reprobada(3L, "Electiva"));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertFalse(Boolean.TRUE.equals(carga.getAplicaRn64()));
        assertFalse(Boolean.TRUE.equals(carga.getBloqueoAutonomoContinuidadPorTipoRn73()));
        assertEquals(carga.getEstadoPrincipioOptativaElectiva(), EstadoPrincipioOptativaElectivaV2.IRREGULAR_SIN_BLOQUEO_AUTONOMO);
    }

    @Test public void f7CU04DosOptativasYDosElectivasActivanRn64() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Optativa")); contexto.getUnidades().add(reprobada(2L, "Optativa")); contexto.getUnidades().add(reprobada(3L, "Electiva")); contexto.getUnidades().add(reprobada(4L, "Electiva"));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn64()));
        assertEquals(carga.getEstadoPrincipioOptativaElectiva(), EstadoPrincipioOptativaElectivaV2.SUJETO_RN64);
    }

    @Test public void f7CU05ConteoMixtoIncluyeObligatoriaSinContadorParalelo() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Obligatoria")); contexto.getUnidades().add(reprobada(2L, "Optativa")); contexto.getUnidades().add(reprobada(3L, "Electiva")); contexto.getUnidades().add(reprobada(4L, "Electiva"));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getTotalReprobadasComunRn73(), Integer.valueOf(4));
        assertEquals(carga.getCantidadObligatoriasReprobadasRn73(), Integer.valueOf(1));
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn64()));
    }

    @Test public void f7CU06EstadosNoReprobatoriosYAcreditadoVigenteNoCuentan() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(unidad(1L, "Optativa", EstadoUnidadCurricularV2.BAJA_PARCIAL)); contexto.getUnidades().add(unidad(2L, "Electiva", EstadoUnidadCurricularV2.NO_CURSADA)); contexto.getUnidades().add(unidad(3L, "Optativa", EstadoUnidadCurricularV2.ACREDITADA));
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        assertFalse(Boolean.TRUE.equals(resultado.getEvaluacionCargaAcademica().getAplicaRn73()));
    }

    @Test public void f7CU07DuplicadoTecnicoCuentaUnaSolaVez() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Optativa")); contexto.getUnidades().add(reprobada(1L, "Optativa"));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getTotalReprobadasComunRn73(), Integer.valueOf(1));
        assertEquals(carga.getUdsOptativasReprobadasRn73().size(), 1);
    }

    @Test public void f7CU08OptativaNoEntraABaseObligatoriaDeQuinto() {
        ContextoEvaluacionCurricularV2DTO contexto = base(5); contexto.setIdPlan(1L);
        for (long id = 1; id <= 4; id++) { UnidadEvaluacionCurricularV2DTO obligatoria = unidad(id, "Obligatoria", EstadoUnidadCurricularV2.ACREDITADA); obligatoria.setSemestre(Integer.valueOf((int) id)); contexto.getUnidades().add(obligatoria); }
        contexto.getUnidades().add(reprobada(10L, "Optativa"));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(Boolean.TRUE.equals(carga.getAccesoOrdinarioQuinto()));
        assertFalse(carga.getBaseRn611().contains(10L));
        assertFalse(Boolean.TRUE.equals(carga.getBloqueoAutonomoQuintoPorTipoRn73()));
    }

    @Test public void f7CU09ObligatoriaPrioritariaPrevalece() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Obligatoria")); contexto.getUnidades().add(reprobada(2L, "Optativa"));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsObligatoriasPrioritariasRn73().contains(1L));
        assertEquals(carga.getEstadoPrincipioOptativaElectiva(), EstadoPrincipioOptativaElectivaV2.PRIORIDAD_OBLIGATORIA_PREVALECE);
    }

    @Test public void f7CU10ConservaPrioridadRn71YNoTransformaElectivaEnRepeticionObligatoria() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); contexto.getUnidades().add(reprobada(1L, "Optativa")); contexto.getUnidades().add(reprobada(2L, "Electiva"));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsOptativasReprobadasRn71().contains(1L));
        assertTrue(carga.getUdsElectivasReprobadasRn72().contains(2L));
        assertTrue(carga.getDecisionesPorUdRn73().toString().contains("REPROBADA_ELECTIVA_VIGENTE"));
    }

    @Test public void f7CU11S7NoReactivaRn61YSinOfertaNoEsSinCupo() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); UnidadEvaluacionCurricularV2DTO optativa = reprobada(1L, "Optativa"); optativa.setOfertada(Boolean.FALSE); contexto.getUnidades().add(optativa);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertFalse(Boolean.TRUE.equals(carga.getAplicaCtr01()));
        assertTrue(carga.getUdsOptativasReprobadasSinOfertaRn71().contains(1L));
        assertFalse(carga.getUdsOptativasReprobadasSinCupoRn71().contains(1L));
    }

    @Test public void f7CU12TransicionCuatroATresLiberaRn64() {
        ContextoEvaluacionCurricularV2DTO cuatro = base(4); for (long id = 1; id <= 4; id++) cuatro.getUnidades().add(reprobada(id, id <= 2 ? "Optativa" : "Electiva"));
        assertTrue(Boolean.TRUE.equals(carga(cuatro).getAplicaRn64()));
        ContextoEvaluacionCurricularV2DTO tres = base(4); for (long id = 1; id <= 3; id++) tres.getUnidades().add(reprobada(id, id <= 2 ? "Optativa" : "Electiva"));
        assertFalse(Boolean.TRUE.equals(carga(tres).getAplicaRn64()));
    }

    @Test public void f7CU13MismoContextoEsDeterministaYSinEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Electiva"));
        assertEquals(carga(contexto).getDecisionesPorUdRn73(), carga(contexto).getDecisionesPorUdRn73());
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(contexto).getEfectosProductivosAplicados()));
    }

    @Test public void f7CU14ConflictoMaterialOptativaElectivaConservaRevisionSinJerarquiaInventada() {
        ContextoEvaluacionCurricularV2DTO contexto = base(4); contexto.getUnidades().add(reprobada(1L, "Optativa")); contexto.getUnidades().add(reprobada(2L, "Electiva"));
        for (long id = 10; id < 15; id++) contexto.getUnidades().add(unidad(id, "Obligatoria", EstadoUnidadCurricularV2.NO_INSCRITA));
        assertEquals(carga(contexto).getEstadoPrincipioOptativaElectiva(), EstadoPrincipioOptativaElectivaV2.REQUIERE_REVISION);
    }

    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) { return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica(); }
    private ContextoEvaluacionCurricularV2DTO base(Integer semestre) { ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO(); contexto.setSemestreObjetivo(semestre); contexto.setPeriodoNormativo("2027-1"); contexto.setSeriacionComprobada(Boolean.TRUE); return contexto; }
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id, String tipo) { UnidadEvaluacionCurricularV2DTO unidad = unidad(id, tipo, EstadoUnidadCurricularV2.REPROBADA); unidad.setCausa(CausaPendienteUnidadV2.REPROBACION); unidad.setReprobacionVigenteConocida(Boolean.TRUE); unidad.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); return unidad; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, String tipo, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO(); unidad.setIdPrograma(id); unidad.setClave("UD" + id); unidad.setTipoUd(tipo); unidad.setSemestre(Integer.valueOf(2)); unidad.setEstado(estado); unidad.setOfertada(Boolean.TRUE); unidad.setGrupoActivoConocido(Boolean.TRUE); unidad.setCupoDisponibleConocido(Boolean.TRUE); return unidad; }
}
