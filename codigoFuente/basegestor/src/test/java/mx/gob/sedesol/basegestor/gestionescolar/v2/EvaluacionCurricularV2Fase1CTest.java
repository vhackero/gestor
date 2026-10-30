package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoExcepcionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoExcepcionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ExcepcionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoExcepcionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Pruebas F1C: CTR-02 en modo sombra, sin autorizaciones productivas. */
public class EvaluacionCurricularV2Fase1CTest {

    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test
    public void f1CU01NingunaExcepcionAplicable() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(3,
                unidad(1L, "Obligatoria", EstadoUnidadCurricularV2.ACREDITADA, true)));
        assertTrue(resultado.getExcepcionesEvaluadas().isEmpty());
    }

    @Test
    public void f1CU02ExcepcionesCargaMinimaTipificadas() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(3,
                unidad(2L, "Obligatoria", EstadoUnidadCurricularV2.NO_CURSADA, true));
        contexto.setCargaMinimaOrigen(Integer.valueOf(3));
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto);
        assertEquals(excepcion(resultado, CodigoExcepcionCurricularV2.CARGA_MINIMA_OBLIGATORIAS_INSUFICIENTES).getTipo(),
                TipoExcepcionCurricularV2.TIPIFICADA);
        assertNotNull(excepcion(resultado, CodigoExcepcionCurricularV2.CARGA_MINIMA_OPTATIVAS_INSUFICIENTES));
        assertNotNull(excepcion(resultado, CodigoExcepcionCurricularV2.CARGA_MINIMA_UNICA_OBLIGATORIA_PENDIENTE));
    }

    @Test
    public void f1CU03DosExcepcionesTipificadasConcurrentes() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(5,
                unidad(3L, "Obligatoria", EstadoUnidadCurricularV2.REPROBADA, true),
                unidad(4L, "Optativa", EstadoUnidadCurricularV2.BAJA_PARCIAL, false));
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto);
        assertNotNull(excepcion(resultado, CodigoExcepcionCurricularV2.AVANCE_UNA_OBLIGATORIA_PENDIENTE));
        assertNotNull(excepcion(resultado, CodigoExcepcionCurricularV2.AVANCE_REZAGO_LEVE));
        assertNotNull(excepcion(resultado, CodigoExcepcionCurricularV2.AVANCE_BAJA_PARCIAL));
    }

    @Test
    public void f1CU04UnaObligatoriaPendienteEsCandidataEnQuinto() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(5,
                unidad(5L, "Obligatoria", EstadoUnidadCurricularV2.NO_CURSADA, true)));
        assertEquals(excepcion(resultado, CodigoExcepcionCurricularV2.AVANCE_UNA_OBLIGATORIA_PENDIENTE).getEstado(),
                EstadoExcepcionCurricularV2.CANDIDATA);
    }

    @Test
    public void f1CU05RezagoLeveUnoATresReprobadas() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(5,
                unidad(6L, "Obligatoria", EstadoUnidadCurricularV2.REPROBADA, true),
                unidad(7L, "Optativa", EstadoUnidadCurricularV2.REPROBADA, true)));
        assertNotNull(excepcion(resultado, CodigoExcepcionCurricularV2.AVANCE_REZAGO_LEVE));
    }

    @Test
    public void f1CU06BajaTemporalSeDistingueDeReprobacion() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(5,
                unidad(8L, "Obligatoria", EstadoUnidadCurricularV2.ACREDITADA, true));
        contexto.setBajaTemporalRegistrada(Boolean.TRUE);
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto);
        assertNotNull(excepcion(resultado, CodigoExcepcionCurricularV2.AVANCE_BAJA_TEMPORAL));
        assertEquals(resultado.getTotalReprobadas(), Integer.valueOf(0));
    }

    @Test
    public void f1CU07BajaParcialEsSupuestoTipificado() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(5,
                unidad(9L, "Obligatoria", EstadoUnidadCurricularV2.BAJA_PARCIAL, true)));
        assertNotNull(excepcion(resultado, CodigoExcepcionCurricularV2.AVANCE_BAJA_PARCIAL));
    }

    @Test
    public void f1CU08FaltaOfertaEsDiferenteDeFaltaCupo() {
        UnidadEvaluacionCurricularV2DTO sinOferta = unidad(10L, "Obligatoria", EstadoUnidadCurricularV2.NO_CURSADA, false);
        sinOferta.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.UD_NO_OFERTADA_PERIODO);
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(5, sinOferta));
        assertNotNull(excepcion(resultado, CodigoExcepcionCurricularV2.AVANCE_FALTA_OFERTA));

        UnidadEvaluacionCurricularV2DTO sinCupo = unidad(11L, "Obligatoria", EstadoUnidadCurricularV2.NO_CURSADA, false);
        sinCupo.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO);
        assertNull(excepcion(service.evaluarSombra(contexto(5, sinCupo)), CodigoExcepcionCurricularV2.AVANCE_FALTA_OFERTA));
    }

    @Test
    public void f1CU09AusenciaSeriacionNoAutorizaPorSiSola() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(5,
                unidad(12L, "Obligatoria", EstadoUnidadCurricularV2.NO_CURSADA, true)));
        assertNull(excepcion(resultado, CodigoExcepcionCurricularV2.AVANCE_AUSENCIA_SERIACION));
    }

    @Test
    public void f1CU10CasoNoTipificadoRequiereRevisionExtraordinaria() {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(13L, "Obligatoria", EstadoUnidadCurricularV2.NO_CURSADA, false);
        unidad.setCausa(CausaPendienteUnidadV2.OTRA_INSTITUCIONAL);
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(3, unidad));
        ExcepcionCurricularV2DTO excepcion = excepcion(resultado, CodigoExcepcionCurricularV2.EXTRAORDINARIA_NO_TIPIFICADA);
        assertEquals(excepcion.getTipo(), TipoExcepcionCurricularV2.EXTRAORDINARIA);
        assertEquals(excepcion.getEstado(), EstadoExcepcionCurricularV2.REQUIERE_REVISION);
    }

    @Test
    public void f1CU11DatosInsuficientesNoInfierenExcepcionAutomatica() {
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(3,
                unidad(14L, "Obligatoria", EstadoUnidadCurricularV2.NO_DETERMINADO, false)));
        assertNull(excepcion(resultado, CodigoExcepcionCurricularV2.EXTRAORDINARIA_NO_TIPIFICADA));
        assertTrue(resultado.getExcepcionesEvaluadas().isEmpty());
    }

    @Test
    public void f1CU12MismoContextoMismoConjuntoExcepciones() {
        ContextoEvaluacionCurricularV2DTO contexto = contexto(5,
                unidad(15L, "Obligatoria", EstadoUnidadCurricularV2.REPROBADA, true));
        ResultadoEvaluacionCurricularV2DTO primero = service.evaluarSombra(contexto);
        ResultadoEvaluacionCurricularV2DTO segundo = service.evaluarSombra(contexto);
        assertEquals(codigos(primero), codigos(segundo));
    }

    @Test
    public void f1CU13ExcepcionTipificadaNoModificaOfertaOrigen() {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(16L, "Obligatoria", EstadoUnidadCurricularV2.NO_CURSADA, true);
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(5, unidad));
        DecisionUnidadCurricularV2DTO decision = resultado.getDecisiones().get(0);
        assertTrue(decision.getOfertaOrigen());
        assertTrue(decision.getOfertaEvaluadaV2());
        assertTrue(decision.getExcepcionesRelacionadas().contains(CodigoExcepcionCurricularV2.AVANCE_UNA_OBLIGATORIA_PENDIENTE));
    }

    @Test
    public void f1CU14ExtraordinariaNoHabilitaUdPorSiMisma() {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(17L, "Obligatoria", EstadoUnidadCurricularV2.NO_CURSADA, false);
        unidad.setCausa(CausaPendienteUnidadV2.LIMITE_INTENTOS);
        ResultadoEvaluacionCurricularV2DTO resultado = service.evaluarSombra(contexto(3, unidad));
        assertFalse(resultado.getDecisiones().get(0).getOfertaEvaluadaV2());
        assertNotNull(excepcion(resultado, CodigoExcepcionCurricularV2.EXTRAORDINARIA_NO_TIPIFICADA));
    }

    private ExcepcionCurricularV2DTO excepcion(ResultadoEvaluacionCurricularV2DTO resultado,
            CodigoExcepcionCurricularV2 codigo) {
        for (ExcepcionCurricularV2DTO excepcion : resultado.getExcepcionesEvaluadas()) {
            if (codigo == excepcion.getCodigo()) return excepcion;
        }
        return null;
    }

    private String codigos(ResultadoEvaluacionCurricularV2DTO resultado) {
        StringBuilder resultadoCodigos = new StringBuilder();
        for (ExcepcionCurricularV2DTO excepcion : resultado.getExcepcionesEvaluadas()) {
            resultadoCodigos.append(excepcion.getCodigo()).append('|');
        }
        return resultadoCodigos.toString();
    }

    private ContextoEvaluacionCurricularV2DTO contexto(int semestre, UnidadEvaluacionCurricularV2DTO... unidades) {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setVersionReglas("PE2027-V1");
        contexto.setFechaEvaluacion(new Date(0L));
        contexto.setSemestreObjetivo(Integer.valueOf(semestre));
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) contexto.getUnidades().add(unidad);
        return contexto;
    }

    private UnidadEvaluacionCurricularV2DTO unidad(Long id, String tipo, EstadoUnidadCurricularV2 estado, boolean ofertada) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(id);
        unidad.setClave("UD" + id);
        unidad.setTipoUd(tipo);
        unidad.setEstado(estado);
        unidad.setOfertada(Boolean.valueOf(ofertada));
        unidad.setSeleccionableOrigen(Boolean.valueOf(ofertada));
        unidad.setBloqueadaOrigen(Boolean.FALSE);
        unidad.setGrupoActivoConocido(Boolean.valueOf(ofertada));
        unidad.setCupoDisponibleConocido(Boolean.valueOf(ofertada));
        return unidad;
    }
}
