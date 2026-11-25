package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoRequerimientoOptativoV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cobertura F7A: RN-7.1/RN-7.1.1 para optativas, exclusivamente en sombra. */
public class EvaluacionCurricularV2Fase7ATest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f7AU01NoConfundeObligatoriaConOptativa() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().add(reprobada(1L, "Obligatoria"));
        assertFalse(Boolean.TRUE.equals(carga(contexto).getAplicaRn71()));
    }

    @Test public void f7AU02UnaOptativaReprobadaContribuyeAlEstadoComun() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().add(reprobada(1L, "Optativa"));
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(1));
        assertEquals(resultado.getSituacionAcademica(), "IRREGULAR");
    }

    @Test public void f7AU03TresOptativasNoActivanRn64PorSiSolas() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); agregarReprobadas(contexto, 3, "Optativa");
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertFalse(Boolean.TRUE.equals(carga.getAplicaRn64()));
        assertEquals(carga.getEstadoRequerimientoOptativo(), EstadoRequerimientoOptativoV2.OPTATIVAS_REPROBADAS_PRIORIZADAS);
    }

    @Test public void f7AU04CuatroOptativasReprobadasActivanRn64() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); agregarReprobadas(contexto, 4, "Optativa");
        assertTrue(Boolean.TRUE.equals(carga(contexto).getAplicaRn64()));
    }

    @Test public void f7AU05TresOptativasYUnaObligatoriaActivanRn64() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); agregarReprobadas(contexto, 3, "Optativa"); contexto.getUnidades().add(reprobada(10L, "Obligatoria"));
        assertTrue(Boolean.TRUE.equals(carga(contexto).getAplicaRn64()));
    }

    @Test public void f7AU06BajaParcialYNoCursadaNoSeCuentanComoReprobadas() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.getUnidades().add(unidad(1L, "Optativa", EstadoUnidadCurricularV2.BAJA_PARCIAL));
        contexto.getUnidades().add(unidad(2L, "Optativa", EstadoUnidadCurricularV2.NO_CURSADA));
        assertEquals(servicio.evaluarSombra(contexto).getCantidadReprobadasVigentes(), Integer.valueOf(0));
    }

    @Test public void f7AU07ReprobadaOfertadaPriorizaSobreNuevaOptativa() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().add(reprobada(1L, "Optativa"));
        contexto.getUnidades().add(unidad(2L, "Optativa", EstadoUnidadCurricularV2.NO_INSCRITA));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsOptativasReprobadasPrioritariasRn71().contains(1L));
        assertTrue(carga.getUdsOptativasNuevasRn71().contains(2L));
    }

    @Test public void f7AU08SinOfertaYSinCupoSeMantienenDiferenciadas() {
        ContextoEvaluacionCurricularV2DTO sinOferta = base(); UnidadEvaluacionCurricularV2DTO a = reprobada(1L, "Optativa"); a.setOfertada(Boolean.FALSE); sinOferta.getUnidades().add(a);
        assertTrue(carga(sinOferta).getUdsOptativasReprobadasSinOfertaRn71().contains(1L));
        ContextoEvaluacionCurricularV2DTO sinCupo = base(); UnidadEvaluacionCurricularV2DTO b = reprobada(2L, "Optativa"); b.setCupoDisponibleConocido(Boolean.FALSE); b.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO); sinCupo.getUnidades().add(b);
        assertTrue(carga(sinCupo).getUdsOptativasReprobadasSinCupoRn71().contains(2L));
    }

    @Test public void f7AU09CeroYF7AU10SieteAcreditadasMantienenRequisitoPendiente() {
        assertEquals(carga(base()).getEstadoRequerimientoOptativo(), EstadoRequerimientoOptativoV2.REQUERIMIENTO_OPTATIVO_PENDIENTE);
        ContextoEvaluacionCurricularV2DTO contexto = base(); agregarAcreditadas(contexto, 7);
        assertEquals(carga(contexto).getEstadoRequerimientoOptativo(), EstadoRequerimientoOptativoV2.REQUERIMIENTO_OPTATIVO_PENDIENTE);
    }

    @Test public void f7AU11OchoAcreditadasCumplenRequisito() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); agregarAcreditadas(contexto, 8);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getCantidadOptativasAcreditadasRn711(), Integer.valueOf(8));
        assertEquals(carga.getEstadoRequerimientoOptativo(), EstadoRequerimientoOptativoV2.REQUERIMIENTO_OPTATIVO_CUMPLIDO);
    }

    @Test public void f7AU12NovenaAcreditadaEsAdicionalCuandoExisteOrdenVerificable() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); agregarAcreditadas(contexto, 9);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getCantidadOptativasAdicionalesRn711(), Integer.valueOf(1));
        assertTrue(carga.getUdsOptativasAdicionalesRn711().contains(9L));
    }

    @Test public void f7AU13DuplicadoTecnicoNoDuplicaAcreditacion() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO primera = acreditada(1L, 1); UnidadEvaluacionCurricularV2DTO duplicada = acreditada(1L, 1);
        contexto.getUnidades().add(primera); contexto.getUnidades().add(duplicada);
        assertEquals(carga(contexto).getCantidadOptativasAcreditadasRn711(), Integer.valueOf(1));
    }

    @Test public void f7AU14EstadoVigenteAcreditadoNoPermaneceComoReprobado() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().add(acreditada(1L, 1));
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        assertTrue(resultado.getEvaluacionCargaAcademica().getUdsOptativasAcreditadasRn711().contains(1L));
    }

    @Test public void f7AU15ConfiguracionDePeContradictoriaRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.setOptativasRequeridasPrograma(Integer.valueOf(6));
        assertEquals(carga(contexto).getEstadoRequerimientoOptativo(), EstadoRequerimientoOptativoV2.REQUIERE_REVISION);
    }

    @Test public void f7AU16OptativaNoSeIntegraEnBaseObligatoriaDeQuinto() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.setSemestreObjetivo(Integer.valueOf(5)); contexto.setIdPlan(1L);
        for (long id = 1; id <= 4; id++) {
            UnidadEvaluacionCurricularV2DTO obligatoria = unidad(id, "Obligatoria", EstadoUnidadCurricularV2.ACREDITADA);
            obligatoria.setSemestre(Integer.valueOf((int) id)); contexto.getUnidades().add(obligatoria);
        }
        contexto.getUnidades().add(reprobada(10L, "Optativa"));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(Boolean.TRUE.equals(carga.getAccesoOrdinarioQuinto()));
        assertFalse(carga.getBaseRn611().contains(10L));
    }

    @Test public void f7AU17ResultadoEsDeterministaYSinEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().add(reprobada(1L, "Optativa"));
        EvaluacionCargaAcademicaV2DTO primera = carga(contexto); EvaluacionCargaAcademicaV2DTO segunda = carga(contexto);
        assertEquals(primera.getDecisionesPorUdOptativaRn71(), segunda.getDecisionesPorUdOptativaRn71());
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(contexto).getEfectosProductivosAplicados()));
    }

    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) { return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica(); }

    private ContextoEvaluacionCurricularV2DTO base() {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setPeriodoNormativo("2027-1"); contexto.setSemestreObjetivo(Integer.valueOf(4)); contexto.setSeriacionComprobada(Boolean.TRUE);
        return contexto;
    }

    private void agregarReprobadas(ContextoEvaluacionCurricularV2DTO contexto, int cantidad, String tipo) {
        for (long id = 1; id <= cantidad; id++) contexto.getUnidades().add(reprobada(id, tipo));
    }

    private void agregarAcreditadas(ContextoEvaluacionCurricularV2DTO contexto, int cantidad) {
        for (long id = 1; id <= cantidad; id++) contexto.getUnidades().add(acreditada(id, (int) id));
    }

    private UnidadEvaluacionCurricularV2DTO reprobada(Long id, String tipo) {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(id, tipo, EstadoUnidadCurricularV2.REPROBADA);
        unidad.setCausa(CausaPendienteUnidadV2.REPROBACION); unidad.setReprobacionVigenteConocida(Boolean.TRUE); unidad.setReprobacionPeriodoPrevioConocida(Boolean.TRUE);
        return unidad;
    }

    private UnidadEvaluacionCurricularV2DTO acreditada(Long id, int orden) {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(id, "Optativa", EstadoUnidadCurricularV2.ACREDITADA);
        unidad.setOrdenAcreditacionOptativa(Integer.valueOf(orden)); return unidad;
    }

    private UnidadEvaluacionCurricularV2DTO unidad(Long id, String tipo, EstadoUnidadCurricularV2 estado) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(id); unidad.setClave("UD" + id); unidad.setTipoUd(tipo); unidad.setSemestre(Integer.valueOf(2)); unidad.setEstado(estado);
        unidad.setOfertada(Boolean.TRUE); unidad.setGrupoActivoConocido(Boolean.TRUE); unidad.setCupoDisponibleConocido(Boolean.TRUE); return unidad;
    }
}
