package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoActualizacionTrayectoriaRn68V2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cobertura F6E: RN-6.8 en sombra, cierre curricular y transicion non-par. */
public class EvaluacionCurricularV2Fase6ETest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f6EU01SinCierreNoAplicaActualizacion() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.setCierrePeriodoAcademicoComprobado(Boolean.FALSE);
        assertEquals(carga(contexto).getEstadoActualizacionTrayectoriaRn68(), EstadoActualizacionTrayectoriaRn68V2.NO_APLICA_ESPECIFICO_NON_PAR);
    }

    @Test public void f6EU02CierreSinSemestreRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.setSemestreCurricularCierre(null);
        assertEquals(carga(contexto).getEstadoActualizacionTrayectoriaRn68(), EstadoActualizacionTrayectoriaRn68V2.REQUIERE_REVISION);
    }

    @Test public void f6EU03SemestreParSoloConservaRecalculoGeneral() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.setSemestreCurricularCierre(Integer.valueOf(4)); contexto.setSemestreObjetivo(Integer.valueOf(5));
        assertEquals(carga(contexto).getEstadoActualizacionTrayectoriaRn68(), EstadoActualizacionTrayectoriaRn68V2.NO_APLICA_ESPECIFICO_NON_PAR);
    }

    @Test public void f6EU04AcreditacionVigenteResuelveBloqueoYReconoceRegularizacion() {
        EvaluacionCargaAcademicaV2DTO carga = carga(base());
        assertTrue(carga.getUdsAcreditadasDuranteCierreRn68().contains(10L));
        assertTrue(carga.getUdsBloqueosResueltosRn68().contains(10L));
        assertTrue(Boolean.TRUE.equals(carga.getRegularizacionAlcanzadaRn68()));
        assertEquals(carga.getSituacionAcademicaVigenteRn68(), "REGULAR");
    }

    @Test public void f6EU05EnCursoNoDeclaraRegularizacion() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().get(0).setEstado(EstadoUnidadCurricularV2.EN_CURSO);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertFalse(Boolean.TRUE.equals(carga.getRegularizacionAlcanzadaRn68()));
        assertTrue(carga.getUdsBloqueosPendientesRn68().contains(10L));
    }

    @Test public void f6EU06ReprobadaHistoricaAcreditadaNoCuentaVigente() {
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(base());
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        assertFalse(resultado.getEvaluacionCargaAcademica().getUdsReprobadasParAnteriorPrioritariasRn68().contains(10L));
    }

    @Test public void f6EU07UnaATresReprobadasParOfertadasSePriorizan() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().add(reprobada(20L, 2));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsReprobadasParAnteriorPrioritariasRn68().contains(20L));
        assertTrue(carga.getUdsRutaCierreRn68().contains(20L));
        assertEquals(carga.getEstadoActualizacionTrayectoriaRn68(), EstadoActualizacionTrayectoriaRn68V2.CONTINUIDAD_CON_PRIORIDAD_REGULARIZACION);
    }

    @Test public void f6EU08SinOfertaYSinCupoNoSeConfunden() {
        ContextoEvaluacionCurricularV2DTO sinOferta = base(); UnidadEvaluacionCurricularV2DTO a = reprobada(20L, 2); a.setOfertada(Boolean.FALSE); sinOferta.getUnidades().add(a);
        assertTrue(carga(sinOferta).getUdsReprobadasParAnteriorSinOfertaRn68().contains(20L));
        ContextoEvaluacionCurricularV2DTO sinCupo = base(); UnidadEvaluacionCurricularV2DTO b = reprobada(20L, 2); b.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO); b.setCupoDisponibleConocido(Boolean.FALSE); sinCupo.getUnidades().add(b);
        assertTrue(carga(sinCupo).getUdsReprobadasParAnteriorSinCupoRn68().contains(20L));
    }

    @Test public void f6EU09CuatroATresReprobadasLiberaRn64() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.setTotalReprobadasAntesCierre(Integer.valueOf(4)); contexto.setRn64AntesCierre(Boolean.TRUE);
        for (long id = 20; id <= 22; id++) contexto.getUnidades().add(reprobada(id, 2));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertFalse(Boolean.TRUE.equals(carga.getRn64VigenteRn68()));
        assertEquals(carga.getTotalReprobadasVigentesRn68(), Integer.valueOf(3));
        assertEquals(carga.getEstadoActualizacionTrayectoriaRn68(), EstadoActualizacionTrayectoriaRn68V2.CONTINUIDAD_CON_PRIORIDAD_REGULARIZACION);
    }

    @Test public void f6EU10CuatroReprobadasVigentesMantieneRn64() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        for (long id = 20; id <= 23; id++) contexto.getUnidades().add(reprobada(id, 2));
        assertEquals(carga(contexto).getEstadoActualizacionTrayectoriaRn68(), EstadoActualizacionTrayectoriaRn68V2.RESTRINGIDA_POR_RN64);
    }

    @Test public void f6EU11TresACeroEliminaPrioridad() {
        EvaluacionCargaAcademicaV2DTO carga = carga(base());
        assertEquals(carga.getTotalReprobadasVigentesRn68(), Integer.valueOf(0));
        assertTrue(carga.getUdsReprobadasParAnteriorPrioritariasRn68().isEmpty());
        assertTrue(Boolean.TRUE.equals(carga.getRegularizacionAlcanzadaRn68()));
    }

    @Test public void f6EU12SeriacionPorUdNoBloqueaCompatibles() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); UnidadEvaluacionCurricularV2DTO bloqueada = noInscrita(20L, 4); bloqueada.setSeriacionDirectaConocida(Boolean.TRUE);
        contexto.getUnidades().add(bloqueada); contexto.getUnidades().add(noInscrita(21L, 4));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsParBloqueadasSeriacionRn68().contains(20L));
        assertTrue(carga.getUdsParCompatiblesRn68().contains(21L));
    }

    @Test public void f6EU13CargaMaximaEvitaAgregarPosteriores() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().clear();
        for (long id = 1; id <= 12; id++) contexto.getUnidades().add(noInscrita(id, 4));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsRutaCierreRn68().size() <= carga.getCargaMaximaNormativa().intValue());
    }

    @Test public void f6EU14NonAParNoReactivaRn61() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.setSemestreCurricularCierre(Integer.valueOf(5)); contexto.setSemestreObjetivo(Integer.valueOf(6));
        assertFalse(Boolean.TRUE.equals(carga(contexto).getAplicaCtr01()));
    }

    @Test public void f6EU15TipoOptativoNoAnticipaRn7() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); UnidadEvaluacionCurricularV2DTO optativa = noInscrita(20L, 4); optativa.setTipoUd("Optativa"); contexto.getUnidades().add(optativa);
        assertEquals(carga(contexto).getEstadoActualizacionTrayectoriaRn68(), EstadoActualizacionTrayectoriaRn68V2.REQUIERE_REVISION);
    }

    @Test public void f6EU16MismoCierreEsDeterministaSinEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().add(noInscrita(20L, 4));
        EvaluacionCargaAcademicaV2DTO primera = carga(contexto); EvaluacionCargaAcademicaV2DTO segunda = carga(contexto);
        assertEquals(primera.getDecisionesPorUdRn68(), segunda.getDecisionesPorUdRn68());
        assertEquals(primera.getUdsRutaCierreRn68(), segunda.getUdsRutaCierreRn68());
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(contexto).getEfectosProductivosAplicados()));
    }

    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) { return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica(); }

    private ContextoEvaluacionCurricularV2DTO base() {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setCierrePeriodoAcademicoComprobado(Boolean.TRUE); contexto.setSemestreCurricularCierre(Integer.valueOf(3));
        contexto.setSemestreObjetivo(Integer.valueOf(4)); contexto.setPeriodoNormativo("2027-1"); contexto.setSeriacionComprobada(Boolean.TRUE);
        contexto.setBloqueosContinuidadAntesComprobados(Boolean.TRUE); contexto.setSituacionAcademicaAntesCierre("IRREGULAR");
        contexto.setTotalReprobadasAntesCierre(Integer.valueOf(1)); contexto.setRn64AntesCierre(Boolean.FALSE);
        UnidadEvaluacionCurricularV2DTO unidad = unidad(10L, 3, "Obligatoria", EstadoUnidadCurricularV2.ACREDITADA);
        unidad.setEstadoAnteriorCierre(EstadoUnidadCurricularV2.REPROBADA); unidad.setAcreditadaDurantePeriodo(Boolean.TRUE);
        unidad.setImpediaContinuidadAntesCierre(Boolean.TRUE); unidad.setEvidenciaContinuidadAntesCierre("SERIACION_DIRECTA_PREVIA");
        contexto.getUnidades().add(unidad); return contexto;
    }

    private UnidadEvaluacionCurricularV2DTO reprobada(Long id, int semestre) {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(id, semestre, "Obligatoria", EstadoUnidadCurricularV2.REPROBADA);
        unidad.setCausa(CausaPendienteUnidadV2.REPROBACION); unidad.setReprobacionVigenteConocida(Boolean.TRUE);
        unidad.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); unidad.setCorrespondeSemestreParInmediatoAnterior(Boolean.TRUE); return unidad;
    }

    private UnidadEvaluacionCurricularV2DTO noInscrita(Long id, int semestre) { return unidad(id, semestre, "Obligatoria", EstadoUnidadCurricularV2.NO_INSCRITA); }

    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, String tipo, EstadoUnidadCurricularV2 estado) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(id); unidad.setClave("UD" + id); unidad.setTipoUd(tipo); unidad.setSemestre(Integer.valueOf(semestre));
        unidad.setEstado(estado); unidad.setOfertada(Boolean.TRUE); unidad.setGrupoActivoConocido(Boolean.TRUE);
        unidad.setCupoDisponibleConocido(Boolean.TRUE); return unidad;
    }
}
