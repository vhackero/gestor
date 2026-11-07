package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPrincipalTrayectoriaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoConsolidacionFase6V2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cierre F6: integracion de causas principales, condiciones por UD y eventos de cierre. */
public class EvaluacionCurricularV2Fase6FTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f6FU01NuncaInscritoClasificaSoloRn62() {
        EvaluacionCargaAcademicaV2DTO carga = carga(inicialDiferida());
        assertEquals(carga.getCausaPrincipalTrayectoriaFase6(), CausaPrincipalTrayectoriaV2.INSCRIPCION_INICIAL_DIFERIDA);
        assertFalse(Boolean.TRUE.equals(carga.getAplicaRn65())); assertFalse(Boolean.TRUE.equals(carga.getAplicaRn66()));
    }

    @Test public void f6FU02InscripcionDiferidaCompatibleConservaRutaS2() {
        EvaluacionCargaAcademicaV2DTO carga = carga(inicialDiferida());
        assertTrue(carga.getUdsRutaAcademicaConsolidadaFase6().contains(20L));
        assertEquals(carga.getEstadoConsolidacionFase6(), EstadoConsolidacionFase6V2.REINCORPORACION_CON_RUTA_REGULARIZACION);
    }

    @Test public void f6FU03InconsistenciaNuncaInscritoConTrayectoriaRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = inicialDiferida(); contexto.setHaCursadoUnoOMasPeriodos(Boolean.TRUE);
        contexto.setNoCursoPeriodoInmediatoSiguiente(Boolean.TRUE);
        assertEquals(carga(contexto).getCausaPrincipalTrayectoriaFase6(), CausaPrincipalTrayectoriaV2.REQUIERE_REVISION);
    }

    @Test public void f6FU04PeriodoNoCursadoClasificaRn65SinRn66() {
        EvaluacionCargaAcademicaV2DTO carga = carga(periodoNoCursado());
        assertEquals(carga.getCausaPrincipalTrayectoriaFase6(), CausaPrincipalTrayectoriaV2.REINCORPORACION_TRAS_PERIODO_NO_CURSADO);
        assertFalse(Boolean.TRUE.equals(carga.getAplicaRn66()));
    }

    @Test public void f6FU05BajaTemporalFormalTienePrecedenciaSobrePeriodoOmitido() {
        ContextoEvaluacionCurricularV2DTO contexto = bajaTemporal(); contexto.setNoCursoPeriodoInmediatoSiguiente(Boolean.TRUE);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getCausaPrincipalTrayectoriaFase6(), CausaPrincipalTrayectoriaV2.REINCORPORACION_TRAS_BAJA_TEMPORAL);
        assertFalse(Boolean.TRUE.equals(carga.getAplicaRn65())); assertTrue(Boolean.TRUE.equals(carga.getAplicaRn66()));
    }

    @Test public void f6FU06BajaTemporalNoAvanzaPorCalendario() {
        ContextoEvaluacionCurricularV2DTO contexto = bajaTemporal(); contexto.setSemestreEfectivoPrevioBaja(Integer.valueOf(3)); contexto.setSemestreObjetivo(Integer.valueOf(4));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getSemestreEfectivoPrevioBajaRn66(), Integer.valueOf(3));
        assertEquals(carga.getSemestreObjetivoRn66(), Integer.valueOf(4));
    }

    @Test public void f6FU07BajaParcialEsConcurrenteConRn65() {
        ContextoEvaluacionCurricularV2DTO contexto = periodoNoCursado(); contexto.getUnidades().add(bajaParcial(30L, 3));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getCausaPrincipalTrayectoriaFase6(), CausaPrincipalTrayectoriaV2.REINCORPORACION_TRAS_PERIODO_NO_CURSADO);
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn67())); assertTrue(carga.getCondicionesConcurrentesFase6().contains("BAJA_PARCIAL"));
    }

    @Test public void f6FU08BajaParcialEsConcurrenteConRn66() {
        ContextoEvaluacionCurricularV2DTO contexto = bajaTemporal(); contexto.getUnidades().add(bajaParcial(30L, 3));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getCausaPrincipalTrayectoriaFase6(), CausaPrincipalTrayectoriaV2.REINCORPORACION_TRAS_BAJA_TEMPORAL);
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn67()));
    }

    @Test public void f6FU09BajaParcialNoCuentaParaIrregularidadNiRn64() {
        ContextoEvaluacionCurricularV2DTO contexto = normal(); contexto.getUnidades().add(bajaParcial(30L, 3));
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(resultado.getSituacionAcademicaCalculada(), "REGULAR"); assertFalse(Boolean.TRUE.equals(resultado.getEvaluacionCargaAcademica().getAplicaRn64()));
    }

    @Test public void f6FU10Rn64CuentaSoloCuatroReprobadasReales() {
        ContextoEvaluacionCurricularV2DTO contexto = normal(); for (long id = 1; id <= 4; id++) contexto.getUnidades().add(reprobada(id, 2));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getEstadoConsolidacionFase6(), EstadoConsolidacionFase6V2.REINCORPORACION_RESTRINGIDA_RN64);
    }

    @Test public void f6FU11ObjetivoQuintoConsumeMismaDecisionRn61() {
        ContextoEvaluacionCurricularV2DTO contexto = bajaTemporal(); contexto.setSemestreObjetivo(Integer.valueOf(5)); contexto.setSemestreEfectivoPrevioBaja(Integer.valueOf(4));
        assertTrue(Boolean.TRUE.equals(carga(contexto).getAplicaCtr01()));
    }

    @Test public void f6FU12Rn68EsEventoNoCausaPrincipal() {
        ContextoEvaluacionCurricularV2DTO contexto = normal(); contexto.setCierrePeriodoAcademicoComprobado(Boolean.TRUE); contexto.setSemestreCurricularCierre(Integer.valueOf(3)); contexto.setSemestreObjetivo(Integer.valueOf(4));
        contexto.setBloqueosContinuidadAntesComprobados(Boolean.TRUE); UnidadEvaluacionCurricularV2DTO u = acreditada(10L, 3); u.setImpediaContinuidadAntesCierre(Boolean.TRUE); u.setEstadoAnteriorCierre(EstadoUnidadCurricularV2.REPROBADA); u.setEvidenciaContinuidadAntesCierre("SER"); u.setAcreditadaDurantePeriodo(Boolean.TRUE); contexto.getUnidades().add(u);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getCausaPrincipalTrayectoriaFase6(), CausaPrincipalTrayectoriaV2.NORMAL);
        assertTrue(carga.getCondicionesConcurrentesFase6().contains("CIERRE_PERIODO"));
    }

    @Test public void f6FU13EstadoVigenteAcreditadoNoConservaPendienteHistorica() {
        ContextoEvaluacionCurricularV2DTO contexto = normal(); contexto.getUnidades().add(acreditada(10L, 3));
        assertEquals(servicio.evaluarSombra(contexto).getCantidadReprobadasVigentes(), Integer.valueOf(0));
    }

    @Test public void f6FU14RutaConsolidadaNoDuplicaUd() {
        ContextoEvaluacionCurricularV2DTO contexto = periodoNoCursado(); UnidadEvaluacionCurricularV2DTO pendiente = reprobada(10L, 2); contexto.getUnidades().add(pendiente);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getUdsRutaAcademicaConsolidadaFase6().size(), new java.util.HashSet<Long>(carga.getUdsRutaAcademicaConsolidadaFase6()).size());
    }

    @Test public void f6FU15ContextoRepetidoEsDeterministaEIdempotente() {
        ContextoEvaluacionCurricularV2DTO contexto = periodoNoCursado(); EvaluacionCargaAcademicaV2DTO primera = carga(contexto); EvaluacionCargaAcademicaV2DTO segunda = carga(contexto);
        assertEquals(primera.getCausaPrincipalTrayectoriaFase6(), segunda.getCausaPrincipalTrayectoriaFase6());
        assertEquals(primera.getUdsRutaAcademicaConsolidadaFase6(), segunda.getUdsRutaAcademicaConsolidadaFase6());
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(contexto).getEfectosProductivosAplicados()));
    }

    @Test public void f6FU16TrazaConsolidadaNoContienePiiNiCambiaFlujoProductivo() {
        EvaluacionCargaAcademicaV2DTO carga = carga(periodoNoCursado());
        assertFalse(carga.getDecisionesConsolidacionFase6().isEmpty());
        assertTrue(carga.getTrayectoriaAntesFase6().contains("ULTIMO_PERIODO_CURSADO"));
    }

    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) { return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica(); }

    private ContextoEvaluacionCurricularV2DTO normal() {
        ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO();
        c.setAdmitido(Boolean.FALSE); c.setInscripcionInicialRealizada(Boolean.TRUE); c.setHaCursadoUnoOMasPeriodos(Boolean.TRUE);
        c.setNoCursoPeriodoInmediatoSiguiente(Boolean.FALSE); c.setBajaTemporalFormalComprobada(Boolean.FALSE); c.setBajaTemporalRegistrada(Boolean.FALSE); c.setBajaTemporalTotalRegistrada(Boolean.FALSE);
        c.setSemestreObjetivo(Integer.valueOf(4)); c.setPeriodoNormativo("2027-1"); c.setSeriacionComprobada(Boolean.TRUE); c.setCierrePeriodoAcademicoComprobado(Boolean.FALSE); return c;
    }

    private ContextoEvaluacionCurricularV2DTO inicialDiferida() {
        ContextoEvaluacionCurricularV2DTO c = normal(); c.setAdmitido(Boolean.TRUE); c.setInscripcionInicialRealizada(Boolean.FALSE); c.setHaCursadoUnoOMasPeriodos(Boolean.FALSE);
        c.setActividadAcademicaComprobada(Boolean.TRUE); c.setLimiteInactividadAlcanzado(Boolean.FALSE); c.setReferenciaConvocatoriaOResolucionRn62("CONVOCATORIA-PRUEBA"); c.getUnidades().add(noInscrita(20L, 2)); return c;
    }

    private ContextoEvaluacionCurricularV2DTO periodoNoCursado() {
        ContextoEvaluacionCurricularV2DTO c = normal(); c.setNoCursoPeriodoInmediatoSiguiente(Boolean.TRUE); c.setCausaInterrupcionConocida(Boolean.TRUE); c.setCausaInterrupcion("SIN_BAJA_FORMAL");
        c.setUltimoPeriodoCursado("2026-1"); c.setPrimerPeriodoOmitido("2026-2"); c.getPeriodosOmitidos().add("2026-2"); c.setPeriodoReincorporacion("2027-1");
        c.setLimiteInactividadComprobado(Boolean.TRUE); c.setLimiteInactividadAlcanzado(Boolean.FALSE); return c;
    }

    private ContextoEvaluacionCurricularV2DTO bajaTemporal() {
        ContextoEvaluacionCurricularV2DTO c = normal(); c.setBajaTemporalFormalComprobada(Boolean.TRUE); c.setBajaTemporalRegistrada(Boolean.TRUE); c.setBajaTemporalTotalRegistrada(Boolean.TRUE);
        c.setBajaTemporalConcluida(Boolean.TRUE); c.setUltimoPeriodoCursado("2026-1"); c.setPeriodoInicioBajaTemporal("2026-2"); c.setPeriodoFinBajaTemporal("2027-1");
        c.setSemestreEfectivoPrevioBaja(Integer.valueOf(3)); c.setLimitePermanenciaComprobado(Boolean.TRUE); c.setLimitePermanenciaExcedido(Boolean.FALSE); c.setCalendarioReincorporacionComprobado(Boolean.TRUE); c.setDentroCalendarioReincorporacion(Boolean.TRUE); c.setPeriodosBajaTemporalTranscurridos(Integer.valueOf(1)); return c;
    }

    private UnidadEvaluacionCurricularV2DTO bajaParcial(Long id, int semestre) { UnidadEvaluacionCurricularV2DTO u = unidad(id, semestre, "Obligatoria", EstadoUnidadCurricularV2.BAJA_PARCIAL); u.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL); u.setBajaParcialFormalComprobada(Boolean.TRUE); u.setBajaParcialDerivadaSancion(Boolean.FALSE); return u; }
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id, int semestre) { UnidadEvaluacionCurricularV2DTO u = unidad(id, semestre, "Obligatoria", EstadoUnidadCurricularV2.REPROBADA); u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setReprobacionVigenteConocida(Boolean.TRUE); u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); return u; }
    private UnidadEvaluacionCurricularV2DTO acreditada(Long id, int semestre) { return unidad(id, semestre, "Obligatoria", EstadoUnidadCurricularV2.ACREDITADA); }
    private UnidadEvaluacionCurricularV2DTO noInscrita(Long id, int semestre) { return unidad(id, semestre, "Obligatoria", EstadoUnidadCurricularV2.NO_INSCRITA); }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, String tipo, EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd(tipo); u.setSemestre(Integer.valueOf(semestre)); u.setEstado(estado); u.setOfertada(Boolean.TRUE); u.setGrupoActivoConocido(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE); return u; }
}
