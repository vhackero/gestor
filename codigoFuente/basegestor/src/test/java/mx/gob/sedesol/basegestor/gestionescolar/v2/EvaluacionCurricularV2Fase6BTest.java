package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import java.util.Arrays;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoReincorporacionPeriodoNoCursadoV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoProcesoReincorporacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cobertura F6B: RN-6.5 en sombra y sin efectos sobre reinscripcion real. */
public class EvaluacionCurricularV2Fase6BTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f6BU01NuncaCursoNoAplicaYNoSustituyeRn62() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.setHaCursadoUnoOMasPeriodos(Boolean.FALSE);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertFalse(Boolean.TRUE.equals(carga.getAplicaRn65()));
        assertEquals(carga.getEstadoReincorporacionRn65(), EstadoReincorporacionPeriodoNoCursadoV2.NO_APLICA);
    }

    @Test public void f6BU02TrayectoriaSinInterrupcionNoAplica() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.setNoCursoPeriodoInmediatoSiguiente(Boolean.FALSE);
        assertEquals(carga(contexto).getEstadoReincorporacionRn65(), EstadoReincorporacionPeriodoNoCursadoV2.NO_APLICA);
    }

    @Test public void f6BU03BajasFormalesQuedanFueraDeRn65() {
        ContextoEvaluacionCurricularV2DTO temporal = base(); temporal.setBajaTemporalRegistrada(Boolean.TRUE);
        assertEquals(carga(temporal).getEstadoReincorporacionRn65(), EstadoReincorporacionPeriodoNoCursadoV2.NO_APLICA);
        ContextoEvaluacionCurricularV2DTO parcial = base(); parcial.setBajaParcialRegistrada(Boolean.TRUE);
        assertEquals(carga(parcial).getEstadoReincorporacionRn65(), EstadoReincorporacionPeriodoNoCursadoV2.NO_APLICA);
    }

    @Test public void f6BU04DatosTrayectoriaOCausaIncompletosRequierenRevision() {
        ContextoEvaluacionCurricularV2DTO causa = base(); causa.setCausaInterrupcionConocida(Boolean.FALSE);
        assertEquals(carga(causa).getEstadoReincorporacionRn65(), EstadoReincorporacionPeriodoNoCursadoV2.REQUIERE_REVISION);
        ContextoEvaluacionCurricularV2DTO periodos = base(); periodos.setPeriodosOmitidos(null);
        assertEquals(carga(periodos).getEstadoReincorporacionRn65(), EstadoReincorporacionPeriodoNoCursadoV2.REQUIERE_REVISION);
    }

    @Test public void f6BU05ConservaPeriodosOmitidosYTipoProcesoSinInferir() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.setPeriodosOmitidos(Arrays.asList("2026-1", "2026-2"));
        contexto.setPrimerPeriodoOmitido("2026-1");
        contexto.setTipoProcesoReincorporacion(TipoProcesoReincorporacionV2.EXTRAORDINARIO);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getCantidadPeriodosInterrupcionRn65(), Integer.valueOf(2));
        assertEquals(carga.getPeriodosOmitidosRn65(), Arrays.asList("2026-1", "2026-2"));
        assertEquals(carga.getTipoProcesoReincorporacionRn65(), TipoProcesoReincorporacionV2.EXTRAORDINARIO);
    }

    @Test public void f6BU06NoCursadaYNoInscritaNoSeConviertenEnReprobadas() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.getUnidades().add(noInscrita(30L, 5));
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        assertFalse(Boolean.TRUE.equals(resultado.getEvaluacionCargaAcademica().getAplicaRn64()));
        assertTrue(resultado.getEvaluacionCargaAcademica().getUdsPendientesPrioritariasRn65().contains(10L));
    }

    @Test public void f6BU07PendienteObligatoriaOfertadaConCupoTienePrioridad() {
        EvaluacionCargaAcademicaV2DTO carga = carga(base());
        assertTrue(carga.getUdsPendientesPrioritariasRn65().contains(10L));
        assertTrue(carga.getUdsRutaReincorporacionRn65().contains(10L));
        assertEquals(carga.getEstadoReincorporacionRn65(), EstadoReincorporacionPeriodoNoCursadoV2.REINCORPORACION_PROCEDE_CON_RUTA_REGULARIZACION);
    }

    @Test public void f6BU08SinOfertaYSinCupoSeDistinguen() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO sinOferta = noCursada(11L, 3); sinOferta.setOfertada(Boolean.FALSE);
        UnidadEvaluacionCurricularV2DTO sinCupo = noCursada(12L, 4); sinCupo.setCupoDisponibleConocido(Boolean.FALSE);
        sinCupo.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO);
        contexto.getUnidades().add(sinOferta); contexto.getUnidades().add(sinCupo);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsPendientesSinOfertaRn65().contains(11L));
        assertTrue(carga.getUdsPendientesSinCupoRn65().contains(12L));
    }

    @Test public void f6BU09PosterioresSeEvalúanIndividualmentePorSeriacion() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO directa = noInscrita(20L, 5); directa.setSeriacionDirectaConocida(Boolean.TRUE);
        UnidadEvaluacionCurricularV2DTO compatible = noInscrita(21L, 5);
        contexto.getUnidades().add(directa); contexto.getUnidades().add(compatible);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsPosterioresBloqueadasSeriacionRn65().contains(20L));
        assertTrue(carga.getUdsPosterioresCompatiblesRn65().contains(21L));
    }

    @Test public void f6BU10SeriacionNoDeterminableRequiereRevisionSinBloquearCompatibles() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO incierta = noInscrita(20L, 5); incierta.setSeriacionConocida(Boolean.TRUE);
        UnidadEvaluacionCurricularV2DTO compatible = noInscrita(21L, 5);
        contexto.getUnidades().add(incierta); contexto.getUnidades().add(compatible);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsPosterioresRevisionSeriacionRn65().contains(20L));
        assertTrue(carga.getUdsPosterioresCompatiblesRn65().contains(21L));
        assertTrue(Boolean.TRUE.equals(carga.getRequiereRevision()));
    }

    @Test public void f6BU11Rn64MantienePrecedenciaYSoloCuentaReprobadasReales() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.getUnidades().clear();
        for (long id = 1; id <= 4; id++) contexto.getUnidades().add(reprobada(id, 2));
        contexto.getUnidades().add(noCursada(10L, 3));
        contexto.getUnidades().add(noInscrita(20L, 5));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn64()));
        assertEquals(carga.getEstadoReincorporacionRn65(), EstadoReincorporacionPeriodoNoCursadoV2.REINCORPORACION_RESTRINGIDA_POR_RN64);
        assertFalse(carga.getUdsPosterioresCompatiblesRn65().contains(20L));
    }

    @Test public void f6BU12Rn61SoloSeConsumeParaQuintoDesdeVigencia() {
        ContextoEvaluacionCurricularV2DTO tercero = base(); tercero.setSemestreObjetivo(3); tercero.setPeriodoNormativo("2027-1");
        assertFalse(Boolean.TRUE.equals(carga(tercero).getRn61ConsumidaRn65()));
        ContextoEvaluacionCurricularV2DTO quinto = base(); quinto.setSemestreObjetivo(5); quinto.setPeriodoNormativo("2027-1");
        EvaluacionCargaAcademicaV2DTO cargaQuinto = carga(quinto);
        assertTrue(Boolean.TRUE.equals(cargaQuinto.getRn61ConsumidaRn65()));
        assertEquals(cargaQuinto.getEstadoReincorporacionRn65(), EstadoReincorporacionPeriodoNoCursadoV2.REINCORPORACION_SUJETA_RN61);
    }

    @Test public void f6BU13InactividadExcedidaONoComprobadaNoProcedeORequiereRevision() {
        ContextoEvaluacionCurricularV2DTO excedida = base(); excedida.setLimiteInactividadAlcanzado(Boolean.TRUE);
        assertEquals(carga(excedida).getEstadoReincorporacionRn65(), EstadoReincorporacionPeriodoNoCursadoV2.NO_PROCEDE_POR_INACTIVIDAD);
        ContextoEvaluacionCurricularV2DTO incierta = base(); incierta.setLimiteInactividadComprobado(Boolean.FALSE);
        assertEquals(carga(incierta).getEstadoReincorporacionRn65(), EstadoReincorporacionPeriodoNoCursadoV2.REQUIERE_REVISION);
    }

    @Test public void f6BU14LimiteCargaNoIncluyePosterioresAdicionales() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        for (long id = 20; id <= 30; id++) contexto.getUnidades().add(noInscrita(id, 5));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsRutaReincorporacionRn65().size() <= carga.getCargaMaximaNormativa().intValue());
        assertFalse(carga.getUdsRutaReincorporacionRn65().contains(30L));
    }

    @Test public void f6BU15DuplicadosYMismoContextoSonDeterministasSinEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().add(noCursada(10L, 3));
        EvaluacionCargaAcademicaV2DTO primera = carga(contexto); EvaluacionCargaAcademicaV2DTO segunda = carga(contexto);
        assertEquals(primera.getUdsPendientesPrioritariasRn65().size(), 1);
        assertEquals(primera.getDecisionesPorUdRn65(), segunda.getDecisionesPorUdRn65());
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(contexto).getEfectosProductivosAplicados()));
    }

    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) {
        return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica();
    }

    private ContextoEvaluacionCurricularV2DTO base() {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setHaCursadoUnoOMasPeriodos(Boolean.TRUE); contexto.setNoCursoPeriodoInmediatoSiguiente(Boolean.TRUE);
        contexto.setCausaInterrupcionConocida(Boolean.TRUE); contexto.setCausaInterrupcion("SIN_REINSCRIPCION_SIN_BAJA_FORMAL");
        contexto.setLimiteInactividadComprobado(Boolean.TRUE); contexto.setLimiteInactividadAlcanzado(Boolean.FALSE);
        contexto.setUltimoPeriodoCursado("2025-2"); contexto.setPrimerPeriodoOmitido("2026-1");
        contexto.setPeriodosOmitidos(Arrays.asList("2026-1")); contexto.setPeriodoReincorporacion("2026-2");
        contexto.setTipoProcesoReincorporacion(TipoProcesoReincorporacionV2.ORDINARIO);
        contexto.setSemestreObjetivo(Integer.valueOf(4)); contexto.setPeriodoNormativo("2027-1");
        contexto.getUnidades().add(noCursada(10L, 3));
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
