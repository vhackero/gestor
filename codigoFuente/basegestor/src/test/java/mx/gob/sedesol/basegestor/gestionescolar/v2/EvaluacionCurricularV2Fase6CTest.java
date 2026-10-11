package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CondicionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoReincorporacionBajaTemporalV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cobertura F6C: RN-6.6 en sombra y sin efectos sobre reinscripcion real. */
public class EvaluacionCurricularV2Fase6CTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f6CU01SinBajaFormalNoAplica() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.setBajaTemporalFormalComprobada(Boolean.FALSE);
        assertEquals(carga(contexto).getEstadoReincorporacionRn66(), EstadoReincorporacionBajaTemporalV2.NO_APLICA);
    }

    @Test public void f6CU02FormalidadNoDeterminableRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.setBajaTemporalFormalComprobada(null);
        assertEquals(carga(contexto).getEstadoReincorporacionRn66(), EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION);
    }

    @Test public void f6CU03SinTrayectoriaPreviaNoSeResuelveComoRn66() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.setHaCursadoUnoOMasPeriodos(Boolean.FALSE);
        assertEquals(carga(contexto).getEstadoReincorporacionRn66(), EstadoReincorporacionBajaTemporalV2.NO_APLICA);
    }

    @Test public void f6CU04BajaVigenteNoConstruyeReincorporacion() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.setBajaTemporalConcluida(Boolean.FALSE);
        assertEquals(carga(contexto).getEstadoReincorporacionRn66(), EstadoReincorporacionBajaTemporalV2.BAJA_TEMPORAL_AUN_VIGENTE);
    }

    @Test public void f6CU05TrayectoriaPreviaIncompletaRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.setUltimoPeriodoCursado(null);
        assertEquals(carga(contexto).getEstadoReincorporacionRn66(), EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION);
    }

    @Test public void f6CU06BajaSuspendeAvanceYConservaSemestreEfectivo() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.setSemestreEfectivoPrevioBaja(Integer.valueOf(3));
        contexto.setSemestreObjetivo(Integer.valueOf(4));
        contexto.setPeriodoInicioBajaTemporal("2026-1");
        contexto.setPeriodoFinBajaTemporal("2026-2");
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getSemestreEfectivoPrevioBajaRn66(), Integer.valueOf(3));
        assertEquals(carga.getSemestreObjetivoRn66(), Integer.valueOf(4));
        assertFalse(Integer.valueOf(6).equals(carga.getSemestreObjetivoRn66()));
    }

    @Test public void f6CU07BajaTemporalNoGeneraReprobacionNiRn64() {
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(base());
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        assertFalse(Boolean.TRUE.equals(resultado.getEvaluacionCargaAcademica().getAplicaRn64()));
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.REINCORPORACION_TRAS_BAJA_TEMPORAL));
    }

    @Test public void f6CU08TresReprobadasMasBajaNoActivaRn64() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.getUnidades().clear();
        for (long id = 1; id <= 3; id++) contexto.getUnidades().add(reprobada(id, 2));
        assertFalse(Boolean.TRUE.equals(carga(contexto).getAplicaRn64()));
    }

    @Test public void f6CU09CuatroReprobadasRealesMantienenPrecedenciaRn64() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.getUnidades().clear();
        for (long id = 1; id <= 4; id++) contexto.getUnidades().add(reprobada(id, 2));
        contexto.getUnidades().add(noInscrita(20L, 5));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn64()));
        assertEquals(carga.getEstadoReincorporacionRn66(), EstadoReincorporacionBajaTemporalV2.REINCORPORACION_RESTRINGIDA_RN64);
        assertFalse(carga.getUdsPosterioresCompatiblesRn66().contains(20L));
    }

    @Test public void f6CU10PendientesConservanCausaYEnCursoSinCierreRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO bajaParcial = unidad(11L, 3, EstadoUnidadCurricularV2.BAJA_PARCIAL);
        bajaParcial.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL);
        UnidadEvaluacionCurricularV2DTO enCurso = unidad(12L, 3, EstadoUnidadCurricularV2.EN_CURSO);
        contexto.getUnidades().add(bajaParcial); contexto.getUnidades().add(enCurso);
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(bajaParcial.getCausa(), CausaPendienteUnidadV2.BAJA_PARCIAL);
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        assertTrue(Boolean.TRUE.equals(resultado.getEvaluacionCargaAcademica().getRequiereRevision()));
    }

    @Test public void f6CU11OfertaYCupoSeDistinguenSinModificarPendiente() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO sinOferta = noCursada(11L, 3); sinOferta.setOfertada(Boolean.FALSE);
        UnidadEvaluacionCurricularV2DTO sinCupo = noCursada(12L, 3); sinCupo.setCupoDisponibleConocido(Boolean.FALSE);
        sinCupo.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO);
        contexto.getUnidades().add(sinOferta); contexto.getUnidades().add(sinCupo);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsPendientesSinOfertaRn66().contains(11L));
        assertTrue(carga.getUdsPendientesSinCupoRn66().contains(12L));
        assertEquals(sinOferta.getEstado(), EstadoUnidadCurricularV2.NO_CURSADA);
    }

    @Test public void f6CU12SeriacionBloqueaSoloPosteriorDependiente() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO bloqueada = noInscrita(20L, 5); bloqueada.setSeriacionDirectaConocida(Boolean.TRUE);
        UnidadEvaluacionCurricularV2DTO compatible = noInscrita(21L, 5);
        contexto.getUnidades().add(bloqueada); contexto.getUnidades().add(compatible);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsPosterioresBloqueadasSeriacionRn66().contains(20L));
        assertTrue(carga.getUdsPosterioresCompatiblesRn66().contains(21L));
    }

    @Test public void f6CU13Rn61SoloSeConsumeEnQuintoDesde2027() {
        ContextoEvaluacionCurricularV2DTO cuarto = base(); cuarto.setSemestreObjetivo(Integer.valueOf(4));
        assertFalse(Boolean.TRUE.equals(carga(cuarto).getRn61ConsumidaRn66()));
        ContextoEvaluacionCurricularV2DTO quinto = base(); quinto.setSemestreObjetivo(Integer.valueOf(5)); quinto.setPeriodoNormativo("2027-1");
        EvaluacionCargaAcademicaV2DTO cargaQuinto = carga(quinto);
        assertTrue(Boolean.TRUE.equals(cargaQuinto.getRn61ConsumidaRn66()));
        assertEquals(cargaQuinto.getEstadoReincorporacionRn66(), EstadoReincorporacionBajaTemporalV2.REINCORPORACION_SUJETA_RN61);
    }

    @Test public void f6CU14PermanenciaYCalendarioNoSeInfieren() {
        ContextoEvaluacionCurricularV2DTO excedida = base(); excedida.setLimitePermanenciaExcedido(Boolean.TRUE);
        assertEquals(carga(excedida).getEstadoReincorporacionRn66(), EstadoReincorporacionBajaTemporalV2.NO_PROCEDE_POR_PERMANENCIA);
        ContextoEvaluacionCurricularV2DTO calendario = base(); calendario.setCalendarioReincorporacionComprobado(Boolean.FALSE);
        assertEquals(carga(calendario).getEstadoReincorporacionRn66(), EstadoReincorporacionBajaTemporalV2.REQUIERE_REVISION);
    }

    @Test public void f6CU15CargaMaximaYDuplicadosSonDeterministasSinEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        for (long id = 20; id <= 30; id++) contexto.getUnidades().add(noInscrita(id, 5));
        contexto.getUnidades().add(noCursada(10L, 3));
        EvaluacionCargaAcademicaV2DTO primera = carga(contexto); EvaluacionCargaAcademicaV2DTO segunda = carga(contexto);
        assertTrue(primera.getUdsRutaReincorporacionRn66().size() <= primera.getCargaMaximaNormativa().intValue());
        assertEquals(primera.getDecisionesPorUdRn66(), segunda.getDecisionesPorUdRn66());
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(contexto).getEfectosProductivosAplicados()));
    }

    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) {
        return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica();
    }

    private ContextoEvaluacionCurricularV2DTO base() {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setBajaTemporalFormalComprobada(Boolean.TRUE); contexto.setBajaTemporalConcluida(Boolean.TRUE);
        contexto.setHaCursadoUnoOMasPeriodos(Boolean.TRUE); contexto.setUltimoPeriodoCursado("2025-2");
        contexto.setPeriodoInicioBajaTemporal("2026-1"); contexto.setPeriodoFinBajaTemporal("2026-2");
        contexto.setSemestreEfectivoPrevioBaja(Integer.valueOf(3)); contexto.setSemestreObjetivo(Integer.valueOf(4));
        contexto.setLimitePermanenciaComprobado(Boolean.TRUE); contexto.setLimitePermanenciaExcedido(Boolean.FALSE);
        contexto.setCalendarioReincorporacionComprobado(Boolean.TRUE); contexto.setDentroCalendarioReincorporacion(Boolean.TRUE);
        contexto.setPeriodoNormativo("2027-1"); contexto.getUnidades().add(noCursada(10L, 3));
        return contexto;
    }

    private UnidadEvaluacionCurricularV2DTO noCursada(Long id, int semestre) {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(id, semestre, EstadoUnidadCurricularV2.NO_CURSADA);
        unidad.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO); return unidad;
    }

    private UnidadEvaluacionCurricularV2DTO noInscrita(Long id, int semestre) {
        return unidad(id, semestre, EstadoUnidadCurricularV2.NO_INSCRITA);
    }

    private UnidadEvaluacionCurricularV2DTO reprobada(Long id, int semestre) {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(id, semestre, EstadoUnidadCurricularV2.REPROBADA);
        unidad.setCausa(CausaPendienteUnidadV2.REPROBACION);
        unidad.setReprobacionVigenteConocida(Boolean.TRUE); unidad.setReprobacionPeriodoPrevioConocida(Boolean.TRUE);
        return unidad;
    }

    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, EstadoUnidadCurricularV2 estado) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(id); unidad.setClave("UD" + id); unidad.setTipoUd("Obligatoria");
        unidad.setSemestre(Integer.valueOf(semestre)); unidad.setEstado(estado);
        unidad.setOfertada(Boolean.TRUE); unidad.setCupoDisponibleConocido(Boolean.TRUE);
        return unidad;
    }
}
