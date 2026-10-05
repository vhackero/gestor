package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2ServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Pruebas F0-U-01 a F0-U-08: contratos y evaluación V2 aislada. */
public class EvaluacionCurricularV2ServiceTest {

    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test
    public void f0U01NormalizaEstadosAcreditadaYReprobada() {
        UnidadEvaluacionCurricularV2DTO acreditada = unidad(1L, EstadoUnidadCurricularV2.ACREDITADA);
        UnidadEvaluacionCurricularV2DTO reprobada = unidad(2L, EstadoUnidadCurricularV2.REPROBADA);

        service.normalizarUnidad(acreditada);
        service.normalizarUnidad(reprobada);

        assertEquals(EstadoUnidadCurricularV2.ACREDITADA, acreditada.getEstado());
        assertNull(acreditada.getCausa());
        assertEquals(EstadoUnidadCurricularV2.REPROBADA, reprobada.getEstado());
        assertEquals(CausaPendienteUnidadV2.REPROBACION, reprobada.getCausa());
    }

    @Test
    public void f0U02EnCursoNoSeConvierteEnReprobada() {
        UnidadEvaluacionCurricularV2DTO enCurso = unidad(3L, EstadoUnidadCurricularV2.EN_CURSO);

        service.normalizarUnidad(enCurso);

        assertEquals(EstadoUnidadCurricularV2.EN_CURSO, enCurso.getEstado());
        assertNull(enCurso.getCausa());
    }

    @Test
    public void f0U03BajaYNoCursadaConservanCausaPropia() {
        UnidadEvaluacionCurricularV2DTO baja = unidad(4L, EstadoUnidadCurricularV2.BAJA_PARCIAL);
        UnidadEvaluacionCurricularV2DTO noCursada = unidad(5L, EstadoUnidadCurricularV2.NO_CURSADA);

        service.normalizarUnidad(baja);
        service.normalizarUnidad(noCursada);

        assertEquals(CausaPendienteUnidadV2.BAJA_PARCIAL, baja.getCausa());
        assertEquals(CausaPendienteUnidadV2.NO_CURSAMIENTO, noCursada.getCausa());
        assertFalse(baja.getEstado() == EstadoUnidadCurricularV2.REPROBADA);
        assertFalse(noCursada.getEstado() == EstadoUnidadCurricularV2.REPROBADA);
    }

    @Test
    public void f0U04DatosInsuficientesSeMarcanSinInferencia() {
        UnidadEvaluacionCurricularV2DTO ambigua = unidad(6L, null);

        service.normalizarUnidad(ambigua);

        assertEquals(EstadoUnidadCurricularV2.NO_DETERMINADO, ambigua.getEstado());
        assertEquals(CausaPendienteUnidadV2.NO_DETERMINADA, ambigua.getCausa());
    }

    @Test
    public void f0U05NoDuplicaLaMismaUnidadPorBloque() {
        UnidadEvaluacionCurricularV2DTO bloqueUno = unidad(7L, EstadoUnidadCurricularV2.NO_INSCRITA);
        bloqueUno.setBloque("1");
        bloqueUno.getReferenciasHistoricas().add("B1");
        UnidadEvaluacionCurricularV2DTO bloqueDos = unidad(7L, EstadoUnidadCurricularV2.NO_INSCRITA);
        bloqueDos.setBloque("2");
        bloqueDos.getReferenciasHistoricas().add("B2");
        List<UnidadEvaluacionCurricularV2DTO> unidades = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        unidades.add(bloqueUno);
        unidades.add(bloqueDos);

        List<UnidadEvaluacionCurricularV2DTO> resultado = service.normalizarUnidades(unidades);

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getReferenciasHistoricas().contains("B1"));
        assertTrue(resultado.get(0).getReferenciasHistoricas().contains("B2"));
    }

    @Test
    public void f0U06ConservaMotivosConcurrentesEnResultadoSombra() {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(8L, EstadoUnidadCurricularV2.REPROBADA);
        unidad.setSeriacionConocida(Boolean.TRUE);
        ContextoEvaluacionCurricularV2DTO contexto = contexto(unidad);

        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto);

        assertEquals(1, resultado.getDecisiones().size());
        assertTrue(resultado.getDecisiones().get(0).getMotivos()
                .contains(CodigoMotivoCurricularV2.REPROBACION_ACUMULADA));
        assertTrue(resultado.getDecisiones().get(0).getMotivos()
                .contains(CodigoMotivoCurricularV2.SERIACION_DIRECTA));
    }

    @Test
    public void f0U07ConfiguracionSeparaVersionVigenciaYModoSombra() {
        ConfiguracionEvaluacionCurricularV2ServiceImpl configuracionService = new ConfiguracionEvaluacionCurricularV2ServiceImpl();
        ConfiguracionEvaluacionCurricularV2DTO configuracion = configuracionService.obtenerConfiguracion();

        assertEquals("PE2027-V1", configuracion.getVersionReglas());
        assertEquals("2027-1", configuracion.getCtr01PeriodoInicio());
        assertEquals(Integer.valueOf(5), configuracion.getCtr01SemestreObjetivo());
        assertTrue(configuracion.getEvaluacionV2Habilitada());
        assertTrue(configuracion.getModoSombra());
    }

    @Test
    public void f0U08ReevaluacionEquivalenteMantieneDecisionesYHuella() {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(9L, EstadoUnidadCurricularV2.REPROBADA);
        ContextoEvaluacionCurricularV2DTO contexto = contexto(unidad);

        ResultadoEvaluacionCurricularV2DTO primera = service.evaluarSombra(contexto);
        ResultadoEvaluacionCurricularV2DTO segunda = service.evaluarSombra(contexto);

        assertNotNull(contexto.getFechaEvaluacion());
        assertEquals(primera.getDecisiones().get(0).getHabilitada(), segunda.getDecisiones().get(0).getHabilitada());
        assertEquals(primera.getDecisiones().get(0).getMotivos(), segunda.getDecisiones().get(0).getMotivos());
    }

    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO unidad) {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setVersionReglas("PE2027-V1");
        contexto.setFechaEvaluacion(new java.util.Date());
        contexto.getUnidades().add(unidad);
        return contexto;
    }

    private UnidadEvaluacionCurricularV2DTO unidad(Long idPrograma, EstadoUnidadCurricularV2 estado) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(idPrograma);
        unidad.setClave("UD" + idPrograma);
        unidad.setEstado(estado);
        unidad.setOfertada(Boolean.TRUE);
        return unidad;
    }
}
