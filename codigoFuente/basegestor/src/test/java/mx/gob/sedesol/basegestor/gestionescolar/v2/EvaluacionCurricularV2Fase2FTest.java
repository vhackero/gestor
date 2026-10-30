package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoActividadAcademicaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIntentoAcreditacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoLimiteIntentosV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoMejoraPromedioV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionIntentosUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionMejoraPromedioUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.IntentoAcreditacionUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/**
 * F2F-U-01 a F2F-U-14: matriz integrada de coherencia RN-1.1 a RN-1.5.
 * La suite sólo evalúa el resultado sombra y no conecta V2 al motor productivo.
 */
public class EvaluacionCurricularV2Fase2FTest {

    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f2FU01ActivoSinReprobadasEsRegular() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto());
        assertEquals(r.getEstadoActividadAcademica(), EstadoActividadAcademicaV2.ACTIVO);
        assertEquals(r.getSituacionAcademicaCalculada(), "REGULAR");
        assertTrue(r.getRecuperacionRegularidadProcede());
    }

    @Test public void f2FU02UnaReprobadaMantieneIrregularidadYIntentoDisponible() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(reprobada(2L, 1)));
        assertEquals(r.getSituacionAcademicaCalculada(), "IRREGULAR");
        assertEquals(r.getCantidadReprobadasVigentes(), Integer.valueOf(1));
        assertFalse(r.getRecuperacionRegularidadProcede());
        assertEquals(intentos(r, 2L).getEstadoLimite(), EstadoLimiteIntentosV2.DISPONIBLE);
    }

    @Test public void f2FU03TresUdReprobadasDistintasNoSonTresIntentosDeUna() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(reprobada(3L, 1), reprobada(4L, 1), reprobada(5L, 1)));
        assertEquals(r.getCantidadReprobadasVigentes(), Integer.valueOf(3));
        assertEquals(intentos(r, 3L).getIntentosFallidos(), Integer.valueOf(1));
        assertEquals(intentos(r, 4L).getIntentosFallidos(), Integer.valueOf(1));
        assertEquals(intentos(r, 5L).getIntentosFallidos(), Integer.valueOf(1));
    }

    @Test public void f2FU04TresIntentosFallidosDeUnaUdSonUnaReprobadaYLímiteAgotado() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(reprobada(6L, 3)));
        assertEquals(r.getCantidadReprobadasVigentes(), Integer.valueOf(1));
        assertEquals(intentos(r, 6L).getIntentosFallidos(), Integer.valueOf(3));
        assertEquals(intentos(r, 6L).getEstadoLimite(), EstadoLimiteIntentosV2.LIMITE_AGOTADO);
    }

    @Test public void f2FU05AcreditacionPosteriorEliminaReprobadaVigenteYConservaHistorial() {
        UnidadEvaluacionCurricularV2DTO reprobada = reprobada(7L, 2);
        UnidadEvaluacionCurricularV2DTO acreditada = acreditada(7L);
        acreditada.getIntentosAcreditacion().add(intento("UD7-A3", EstadoIntentoAcreditacionV2.ACREDITADO, 9.0, false));
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(reprobada, acreditada));
        assertEquals(r.getSituacionAcademicaCalculada(), "REGULAR");
        assertTrue(r.getRecuperacionRegularidadProcede());
        assertEquals(intentos(r, 7L).getIntentosFallidos(), Integer.valueOf(2));
        assertEquals(intentos(r, 7L).getIntentosAcreditados(), Integer.valueOf(1));
        assertEquals(intentos(r, 7L).getEstadoLimite(), EstadoLimiteIntentosV2.ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA);
    }

    @Test public void f2FU06BajaTemporalYRegularidadSonDimensionesIndependientes() {
        ContextoEvaluacionCurricularV2DTO c = contexto();
        c.setBajaTemporalTotalRegistrada(Boolean.TRUE);
        c.setPeriodosBajaTemporalAutorizados(Integer.valueOf(2));
        c.setPeriodosBajaTemporalTranscurridos(Integer.valueOf(1));
        ResultadoEvaluacionCurricularV2DTO r = evaluar(c);
        assertEquals(r.getEstadoActividadAcademica(), EstadoActividadAcademicaV2.BAJA_TEMPORAL_VIGENTE);
        assertEquals(r.getSituacionAcademicaCalculada(), "REGULAR");
    }

    @Test public void f2FU07BajaTemporalNoOcultaDosReprobadas() {
        ContextoEvaluacionCurricularV2DTO c = contexto(reprobada(8L, 1), reprobada(9L, 1));
        c.setBajaTemporalTotalRegistrada(Boolean.TRUE);
        c.setPeriodosBajaTemporalAutorizados(Integer.valueOf(2));
        c.setPeriodosBajaTemporalTranscurridos(Integer.valueOf(1));
        ResultadoEvaluacionCurricularV2DTO r = evaluar(c);
        assertEquals(r.getEstadoActividadAcademica(), EstadoActividadAcademicaV2.BAJA_TEMPORAL_VIGENTE);
        assertEquals(r.getSituacionAcademicaCalculada(), "IRREGULAR");
        assertEquals(r.getCantidadReprobadasVigentes(), Integer.valueOf(2));
    }

    @Test public void f2FU08BajaParcialYNoCursadaNoGeneranIrregularidad() {
        UnidadEvaluacionCurricularV2DTO baja = unidad(10L, EstadoUnidadCurricularV2.BAJA_PARCIAL);
        baja.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL);
        UnidadEvaluacionCurricularV2DTO noCursada = unidad(11L, EstadoUnidadCurricularV2.NO_CURSADA);
        noCursada.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(baja, noCursada));
        assertEquals(r.getSituacionAcademicaCalculada(), "REGULAR");
        assertEquals(r.getCantidadReprobadasVigentes(), Integer.valueOf(0));
    }

    @Test public void f2FU09AvanceCondicionadoNoConvierteIrregularEnRegular() {
        ContextoEvaluacionCurricularV2DTO c = contexto(reprobada(12L, 1));
        c.setAvanceCondicionado(Boolean.TRUE);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(c);
        assertTrue(r.getAvanceCondicionado());
        assertEquals(r.getSituacionAcademicaCalculada(), "IRREGULAR");
    }

    @Test public void f2FU10MejoraPromedioNoAlteraRegularidadNiIntentos() {
        UnidadEvaluacionCurricularV2DTO u = acreditada(13L);
        u.getIntentosAcreditacion().add(intento("UD13-A", EstadoIntentoAcreditacionV2.ACREDITADO, 8.7, false));
        u.getIntentosAcreditacion().add(intento("UD13-M", EstadoIntentoAcreditacionV2.ACREDITADO, 9.2, true));
        ContextoEvaluacionCurricularV2DTO c = contexto(u);
        c.setOportunidadesMejoraUtilizadas(null);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(c);
        assertEquals(r.getSituacionAcademicaCalculada(), "REGULAR");
        assertEquals(r.getOportunidadesMejoraUtilizadas(), Integer.valueOf(1));
        assertEquals(mejora(r, 13L).getEstado(), EstadoMejoraPromedioV2.NO_ELEGIBLE);
        assertEquals(mejora(r, 13L).getCalificacionFavorable(), Double.valueOf(9.2));
        assertEquals(intentos(r, 13L).getEstadoLimite(), EstadoLimiteIntentosV2.ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA);
    }

    @Test public void f2FU11TresMejorasGlobalesNoAfectanReprobadasNiIntentos() {
        ContextoEvaluacionCurricularV2DTO c = contexto(acreditada(14L));
        c.setOportunidadesMejoraUtilizadas(Integer.valueOf(3));
        ResultadoEvaluacionCurricularV2DTO r = evaluar(c);
        assertEquals(r.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        assertEquals(mejora(r, 14L).getEstado(), EstadoMejoraPromedioV2.NO_ELEGIBLE);
        assertEquals(intentos(r, 14L).getIntentosFallidos(), Integer.valueOf(0));
    }

    @Test public void f2FU12HistorialContradictorioPreservaIncertidumbre() {
        UnidadEvaluacionCurricularV2DTO u = unidad(15L, EstadoUnidadCurricularV2.NO_DETERMINADO);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(u));
        assertEquals(r.getSituacionAcademicaCalculada(), "NO_DETERMINADA");
        assertNull(r.getRecuperacionRegularidadProcede());
        assertFalse(r.getEfectosProductivosAplicados());
    }

    @Test public void f2FU13DuplicadosPorBloqueNoDuplicanReprobacionNiIntento() {
        UnidadEvaluacionCurricularV2DTO b1 = reprobada(16L, 1);
        UnidadEvaluacionCurricularV2DTO b2 = reprobada(16L, 1);
        b1.setBloque("B1"); b2.setBloque("B2");
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(b1, b2));
        assertEquals(r.getCantidadReprobadasVigentes(), Integer.valueOf(1));
        assertEquals(intentos(r, 16L).getIntentosFallidos(), Integer.valueOf(1));
    }

    @Test public void f2FU14ContratoCompletoConservaDimensionesYTraza() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(reprobada(17L, 1), acreditada(18L)));
        assertTrue(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_1_1));
        assertTrue(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_1_2));
        assertTrue(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_1_3));
        assertTrue(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_1_4));
        assertTrue(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_1_5));
        assertEquals(Integer.valueOf(r.getDecisiones().size()), Integer.valueOf(2));
        assertEquals(Integer.valueOf(r.getEvaluacionesIntentos().size()), Integer.valueOf(2));
        assertEquals(Integer.valueOf(r.getEvaluacionesMejoraPromedio().size()), Integer.valueOf(2));
        assertTrue(r.getTrazas().size() >= 8);
        assertFalse(r.getEfectosProductivosAplicados());
    }

    private ResultadoEvaluacionCurricularV2DTO evaluar(ContextoEvaluacionCurricularV2DTO contexto) {
        return service.evaluarSombra(contexto);
    }

    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO... unidades) {
        ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO();
        c.setVersionReglas("PE2027-V1"); c.setFechaEvaluacion(new java.util.Date(0L));
        c.setActividadAcademicaComprobada(Boolean.TRUE);
        c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);
        c.setDictaminacionEgresoConocida(Boolean.TRUE);
        c.setDictaminacionEgresoIniciada(Boolean.FALSE);
        c.setOportunidadesMejoraUtilizadas(Integer.valueOf(0));
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) c.getUnidades().add(unidad);
        return c;
    }

    private UnidadEvaluacionCurricularV2DTO reprobada(Long id, int intentosFallidos) {
        UnidadEvaluacionCurricularV2DTO u = unidad(id, EstadoUnidadCurricularV2.REPROBADA);
        u.setCausa(CausaPendienteUnidadV2.REPROBACION);
        u.setReprobacionVigenteConocida(Boolean.TRUE);
        u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE);
        for (int i = 1; i <= intentosFallidos; i++)
            u.getIntentosAcreditacion().add(intento("UD" + id + "-R" + i, EstadoIntentoAcreditacionV2.REPROBADO, null, false));
        return u;
    }

    private UnidadEvaluacionCurricularV2DTO acreditada(Long id) {
        UnidadEvaluacionCurricularV2DTO u = unidad(id, EstadoUnidadCurricularV2.ACREDITADA);
        u.setOfertada(Boolean.TRUE); u.setGrupoActivoConocido(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE);
        return u;
    }

    private UnidadEvaluacionCurricularV2DTO unidad(Long id, EstadoUnidadCurricularV2 estado) {
        UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO();
        u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd("Obligatoria"); u.setEstado(estado);
        return u;
    }

    private IntentoAcreditacionUnidadV2DTO intento(String id, EstadoIntentoAcreditacionV2 estado,
            Double calificacion, boolean mejora) {
        IntentoAcreditacionUnidadV2DTO intento = new IntentoAcreditacionUnidadV2DTO();
        intento.setIdEjecucionTecnico(id); intento.setEstado(estado); intento.setCalificacionFinal(calificacion);
        intento.setRecursamientoMejora(Boolean.valueOf(mejora)); intento.setEvidenciaFuente("EVIDENCIA:" + id);
        return intento;
    }

    private EvaluacionIntentosUnidadV2DTO intentos(ResultadoEvaluacionCurricularV2DTO resultado, Long id) {
        for (EvaluacionIntentosUnidadV2DTO evaluacion : resultado.getEvaluacionesIntentos())
            if (id.equals(evaluacion.getIdPrograma())) return evaluacion;
        throw new AssertionError("Sin evaluación de intentos para " + id);
    }

    private EvaluacionMejoraPromedioUnidadV2DTO mejora(ResultadoEvaluacionCurricularV2DTO resultado, Long id) {
        for (EvaluacionMejoraPromedioUnidadV2DTO evaluacion : resultado.getEvaluacionesMejoraPromedio())
            if (id.equals(evaluacion.getIdPrograma())) return evaluacion;
        throw new AssertionError("Sin evaluación de mejora para " + id);
    }
}
