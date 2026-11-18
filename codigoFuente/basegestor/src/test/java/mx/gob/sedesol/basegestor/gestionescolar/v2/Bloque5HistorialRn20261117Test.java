package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIntentoAcreditacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoLimiteIntentosV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionIntentosUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.IntentoAcreditacionUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Casos dorados GC-17 de RN vigente 2026-11-17. Datos exclusivamente sintéticos. */
public class Bloque5HistorialRn20261117Test {

    @Test public void gc17CuentaAdeudosPorUdYNoDuplicaLaRecursadaEnCurso() {
        ResultadoEvaluacionCurricularV2DTO resultado = evaluar(
                ud(1L, "A", EstadoUnidadCurricularV2.EN_CURSO, intento("A1", EstadoIntentoAcreditacionV2.REPROBADO), intento("A2", EstadoIntentoAcreditacionV2.REPROBADO), intento("A3", EstadoIntentoAcreditacionV2.EN_CURSO)),
                ud(2L, "B", EstadoUnidadCurricularV2.REPROBADA, intento("B1", EstadoIntentoAcreditacionV2.BAJA_PARCIAL), intento("B2", EstadoIntentoAcreditacionV2.REPROBADO)),
                ud(3L, "C", EstadoUnidadCurricularV2.ACREDITADA, intento("C1", EstadoIntentoAcreditacionV2.REPROBADO), intento("C2", EstadoIntentoAcreditacionV2.ACREDITADO)),
                ud(4L, "D", EstadoUnidadCurricularV2.REPROBADA, intento("D1", EstadoIntentoAcreditacionV2.REPROBADO), intento("D2", EstadoIntentoAcreditacionV2.REPROBADO)),
                ud(5L, "G", EstadoUnidadCurricularV2.BAJA_PARCIAL, intento("G1", EstadoIntentoAcreditacionV2.BAJA_PARCIAL)),
                ud(6L, "E", EstadoUnidadCurricularV2.NO_CURSADA));

        EvaluacionIntentosUnidadV2DTO a = intentos(resultado, 1L);
        EvaluacionIntentosUnidadV2DTO b = intentos(resultado, 2L);
        EvaluacionIntentosUnidadV2DTO c = intentos(resultado, 3L);
        EvaluacionIntentosUnidadV2DTO d = intentos(resultado, 4L);
        assertEquals(a.getEstadoLimite(), EstadoLimiteIntentosV2.ULTIMA_EN_CURSO);
        // Confirmados incluye dos resultados concluidos y el cursamiento vigente.
        assertEquals(a.getIntentosConfirmados(), Integer.valueOf(3));
        assertEquals(a.getIntentosFallidos(), Integer.valueOf(2));
        assertTrue(a.getIntentoEnCurso());
        assertEquals(b.getOportunidadesConsumidas(), Integer.valueOf(1));
        assertEquals(c.getEstadoLimite(), EstadoLimiteIntentosV2.ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA);
        assertEquals(c.getOportunidadesDisponibles(), Integer.valueOf(0));
        assertEquals(d.getEstadoLimite(), EstadoLimiteIntentosV2.ULTIMA_DISPONIBLE);
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(3));
        assertFalse(resultado.getCondicionesDetectadas().toString().contains("CUATRO_O_MAS_REPROBADAS"));
    }

    @Test public void gc17bReportaAgotadaYLimiteHistoricoSinProponerCuarta() {
        ResultadoEvaluacionCurricularV2DTO resultado = evaluar(
                ud(7L, "F", EstadoUnidadCurricularV2.REPROBADA, intento("F1", "2026-1", EstadoIntentoAcreditacionV2.REPROBADO), intento("F2", "2026-1", EstadoIntentoAcreditacionV2.REPROBADO), intento("F3", "2026-1", EstadoIntentoAcreditacionV2.REPROBADO)),
                ud(8L, "FP", EstadoUnidadCurricularV2.REPROBADA, intento("P1", "2026-1", EstadoIntentoAcreditacionV2.REPROBADO), intento("P2", "2026-1", EstadoIntentoAcreditacionV2.REPROBADO), intento("P3", "2026-1", EstadoIntentoAcreditacionV2.REPROBADO), intento("P4", "2026-1", EstadoIntentoAcreditacionV2.REPROBADO)));
        EvaluacionIntentosUnidadV2DTO agotada = intentos(resultado, 7L);
        assertEquals(agotada.getEstadoLimite(), EstadoLimiteIntentosV2.AGOTADA);
        assertTrue(agotada.getAviso().contains("PENDIENTE_DISPOSICION_TRANSITORIA"));
        EvaluacionIntentosUnidadV2DTO excedida = intentos(resultado, 8L);
        assertEquals(excedida.getEstadoLimite(), EstadoLimiteIntentosV2.AGOTADA);
        assertTrue(excedida.getAviso().contains("LIMITE_SUPERADO_HISTORIAL_PREVIO"));
    }

    @Test public void gc17cMantieneEnRevisionElUmbralCuandoPuedeCambiar() {
        ResultadoEvaluacionCurricularV2DTO resultado = evaluar(
                ud(11L, "A", EstadoUnidadCurricularV2.REPROBADA, intento("A1", EstadoIntentoAcreditacionV2.REPROBADO)),
                ud(12L, "B", EstadoUnidadCurricularV2.REPROBADA, intento("B1", EstadoIntentoAcreditacionV2.REPROBADO)),
                ud(13L, "C", EstadoUnidadCurricularV2.REPROBADA, intento("C1", EstadoIntentoAcreditacionV2.REPROBADO)),
                ud(14L, "D", EstadoUnidadCurricularV2.NO_DETERMINADO, intento("D1", EstadoIntentoAcreditacionV2.NO_DETERMINADO)));
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(3));
        assertTrue(resultado.getRestricciones().contains(CodigoMotivoCurricularV2.UMBRAL_64_EN_REVISION));
        assertEquals(intentos(resultado, 14L).getEstadoLimite(), EstadoLimiteIntentosV2.REQUIERE_REVISION);
        assertEquals(intentos(resultado, 14L).getOportunidadesDisponiblesMinimas(), Integer.valueOf(2));
        assertEquals(intentos(resultado, 14L).getOportunidadesDisponiblesMaximas(), Integer.valueOf(3));
    }

    @Test public void gc17dNoInfiereUnaSustitucionAdicionalSinFuenteVerificable() {
        ResultadoEvaluacionCurricularV2DTO resultado = evaluar(
                ud(21L, "OPT-AD", EstadoUnidadCurricularV2.NO_DETERMINADO, intento("SUST", EstadoIntentoAcreditacionV2.NO_DETERMINADO)));
        EvaluacionIntentosUnidadV2DTO evaluacion = intentos(resultado, 21L);
        assertEquals(evaluacion.getEstadoLimite(), EstadoLimiteIntentosV2.REQUIERE_REVISION);
        assertTrue(evaluacion.getAviso().contains("OPORTUNIDADES_EN_REVISION"));
        assertEquals(evaluacion.getOportunidadesConsumidas(), Integer.valueOf(0));
    }

    @Test public void gc17dUbicaUnSoloAdeudoEnLaSustitutaCuandoLaRelacionEsVerificable() {
        UnidadEvaluacionCurricularV2DTO original = ud(31L, "OPT-ORIG", EstadoUnidadCurricularV2.REPROBADA,
                intento("ORIG-1", "2026-1", EstadoIntentoAcreditacionV2.REPROBADO));
        UnidadEvaluacionCurricularV2DTO sustituta = ud(32L, "OPT-SUST", EstadoUnidadCurricularV2.REPROBADA,
                intento("SUST-1", "2027-1", EstadoIntentoAcreditacionV2.REPROBADO));
        sustituta.setIdAdeudoOptativaOriginal(31L);
        sustituta.getReferenciasHistoricas().add("SUSTITUCION_VERIFICABLE:OPT-ORIG->OPT-SUST");
        ResultadoEvaluacionCurricularV2DTO resultado = evaluar(original, sustituta);
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(1));
        assertFalse(resultado.getUnidadesOrigenIrregularidad().contains(31L));
        assertTrue(resultado.getUnidadesOrigenIrregularidad().contains(32L));
        assertEquals(intentos(resultado, 31L).getOportunidadesConsumidas(), Integer.valueOf(1));
        assertEquals(intentos(resultado, 32L).getOportunidadesConsumidas(), Integer.valueOf(1));
    }

    private ResultadoEvaluacionCurricularV2DTO evaluar(UnidadEvaluacionCurricularV2DTO... unidades) {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setAmbitoRnPe2024_2027("APLICA");
        contexto.setPeriodoNormativo("2027-1");
        contexto.setFechaEvaluacion(new Date(0L));
        contexto.setActividadAcademicaComprobada(Boolean.TRUE);
        contexto.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);
        contexto.setSeriacionComprobada(Boolean.TRUE);
        contexto.setEstructuraProgramaEducativoComprobada(Boolean.TRUE);
        contexto.setSancionImpedimentoConocida(Boolean.TRUE);
        contexto.setSancionImpedimentoRegistrada(Boolean.FALSE);
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) contexto.getUnidades().add(unidad);
        return new EvaluacionCurricularV2ServiceImpl().evaluarSombra(contexto);
    }

    private UnidadEvaluacionCurricularV2DTO ud(Long id, String clave, EstadoUnidadCurricularV2 estado,
            IntentoAcreditacionUnidadV2DTO... intentos) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(id); unidad.setClave(clave); unidad.setTipoUd("Obligatoria");
        unidad.setSemestre(Integer.valueOf(2)); unidad.setEstado(estado); unidad.setOfertada(Boolean.TRUE);
        unidad.setCupoDisponibleConocido(Boolean.TRUE);
        for (IntentoAcreditacionUnidadV2DTO intento : intentos) unidad.getIntentosAcreditacion().add(intento);
        return unidad;
    }

    private IntentoAcreditacionUnidadV2DTO intento(String id, EstadoIntentoAcreditacionV2 estado) {
        return intento(id, null, estado);
    }
    private IntentoAcreditacionUnidadV2DTO intento(String id, String periodo, EstadoIntentoAcreditacionV2 estado) {
        IntentoAcreditacionUnidadV2DTO intento = new IntentoAcreditacionUnidadV2DTO();
        intento.setIdEjecucionTecnico(id); intento.setPeriodo(periodo); intento.setEstado(estado); return intento;
    }

    private EvaluacionIntentosUnidadV2DTO intentos(ResultadoEvaluacionCurricularV2DTO resultado, Long id) {
        for (EvaluacionIntentosUnidadV2DTO evaluacion : resultado.getEvaluacionesIntentos())
            if (id.equals(evaluacion.getIdPrograma())) return evaluacion;
        throw new AssertionError("No existe evaluación de la UD " + id);
    }
}
