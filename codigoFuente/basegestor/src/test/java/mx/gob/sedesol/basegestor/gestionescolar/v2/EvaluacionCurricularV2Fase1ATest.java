package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CondicionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/**
 * Pruebas F1A-U-01 a F1A-U-07. Verifican representación concurrente en
 * modo sombra; ninguna prueba activa decisiones productivas.
 */
public class EvaluacionCurricularV2Fase1ATest {

    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test
    public void f1AU01RegularSinCondicionesAdicionales() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(
                unidad(1L, EstadoUnidadCurricularV2.ACREDITADA, null)));

        assertEquals(resultado.getSituacionAcademica(), "REGULAR");
        assertTrue(resultado.getCondicionesDetectadas().isEmpty());
        assertTrue(resultado.getReglasEvaluadas().contains(CodigoReglaCurricularV2.CTR_03));
        assertTrue(resultado.getReglasEvaluadas().contains(CodigoReglaCurricularV2.CTR_04));
        assertFalse(resultado.getEfectosProductivosAplicados());
    }

    @Test
    public void f1AU02IrregularConUnaReprobada() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(
                unidad(2L, EstadoUnidadCurricularV2.REPROBADA, null)));

        assertEquals(resultado.getSituacionAcademica(), "IRREGULAR");
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.REPROBACION));
        assertTrue(resultado.getRestricciones().contains(CodigoMotivoCurricularV2.REPROBACION_ACUMULADA));
        assertTrue(resultado.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_1_2));
    }

    @Test
    public void f1AU03ReprobadaYBajaParcialSeConservanConcurrentes() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(
                unidad(3L, EstadoUnidadCurricularV2.REPROBADA, null),
                unidad(4L, EstadoUnidadCurricularV2.BAJA_PARCIAL, null));

        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto);

        assertEquals(resultado.getSituacionAcademica(), "IRREGULAR");
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.REPROBACION));
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.BAJA_PARCIAL));
        assertTrue(resultado.getRestricciones().contains(CodigoMotivoCurricularV2.REPROBACION_ACUMULADA));
        assertTrue(resultado.getRestricciones().contains(CodigoMotivoCurricularV2.BAJA_PARCIAL));
    }

    @Test
    public void f1AU04MultiplesRestriccionesNoSeReducenAUnoSoloMotivo() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(
                unidad(5L, EstadoUnidadCurricularV2.REPROBADA, null),
                unidad(6L, EstadoUnidadCurricularV2.BAJA_PARCIAL, null),
                unidad(7L, EstadoUnidadCurricularV2.NO_CURSADA, null),
                unidad(8L, EstadoUnidadCurricularV2.NO_DETERMINADO, null)));

        assertEquals(resultado.getRestricciones().size(), 4);
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.DATOS_INSUFICIENTES));
        assertTrue(resultado.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_1_2));
        assertTrue(resultado.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_1_3));
        assertTrue(resultado.getReglasEvaluadas().contains(CodigoReglaCurricularV2.CTR_04));
    }

    @Test
    public void f1AU05SeriacionYPendienteSeRepresentanSinModificarOfertaFuente() {
        UnidadEvaluacionCurricularV2DTO pendiente = unidad(9L, EstadoUnidadCurricularV2.NO_CURSADA, null);
        pendiente.setSeriacionConocida(Boolean.TRUE);
        pendiente.setOfertada(Boolean.TRUE);

        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(pendiente));

        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.SERIACION));
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.PENDIENTE_NO_CURSADA));
        assertTrue(resultado.getDecisiones().get(0).getHabilitada());
        assertFalse(resultado.getEfectosProductivosAplicados());
    }

    @Test
    public void f1AU06AvanceCondicionadoNoCambiaSituacionAcademica() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(unidad(10L, EstadoUnidadCurricularV2.ACREDITADA, null));
        contexto.setAvanceCondicionado(Boolean.TRUE);

        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto);

        assertEquals(resultado.getSituacionAcademica(), "REGULAR");
        assertTrue(resultado.getAvanceCondicionado());
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.AVANCE_CONDICIONADO));
        assertTrue(resultado.getExcepciones().contains("AVANCE_CONDICIONADO_SIN_EFECTO_PRODUCTIVO"));
    }

    @Test
    public void f1AU07CuatroReprobadasSeRegistranSinActivarRn64() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(
                unidad(11L, EstadoUnidadCurricularV2.REPROBADA, null),
                unidad(12L, EstadoUnidadCurricularV2.REPROBADA, null),
                unidad(13L, EstadoUnidadCurricularV2.REPROBADA, null),
                unidad(14L, EstadoUnidadCurricularV2.REPROBADA, null)));

        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.CUATRO_O_MAS_REPROBADAS));
        assertTrue(resultado.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_6_4));
        assertFalse(resultado.getEfectosProductivosAplicados());
        assertFalse(traza(resultado, CodigoReglaCurricularV2.RN_6_4).getAplicada());
        assertEquals(Integer.valueOf(100), resultado.getPrioridadTecnicaDominante());
    }

    private TrazaReglaAplicadaV2DTO traza(ResultadoEvaluacionCurricularV2DTO resultado,
            CodigoReglaCurricularV2 regla) {
        for (TrazaReglaAplicadaV2DTO traza : resultado.getTrazas()) {
            if (regla == traza.getCodigoRegla()) return traza;
        }
        throw new AssertionError("No se localizó la traza " + regla);
    }

    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO... unidades) {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setVersionReglas("PE2027-V1");
        contexto.setFechaEvaluacion(new java.util.Date());
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) contexto.getUnidades().add(unidad);
        return contexto;
    }

    private UnidadEvaluacionCurricularV2DTO unidad(Long idPrograma, EstadoUnidadCurricularV2 estado,
            CausaPendienteUnidadV2 causa) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(idPrograma);
        unidad.setClave("UD" + idPrograma);
        unidad.setEstado(estado);
        unidad.setCausa(causa);
        unidad.setOfertada(Boolean.FALSE);
        return unidad;
    }
}
