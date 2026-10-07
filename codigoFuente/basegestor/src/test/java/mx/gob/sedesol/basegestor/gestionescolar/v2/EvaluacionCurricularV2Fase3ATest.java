package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoExcepcionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCargaMinimaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaMinimaCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** F3A-U-01 a F3A-U-16: RN-4.1 semestral, sólo descriptiva en modo sombra. */
public class EvaluacionCurricularV2Fase3ATest {

    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f3AU01TresObligatoriasCumplenMinimo() {
        assertEstado(evaluar(contexto(obligatoria(1L), obligatoria(2L), obligatoria(3L))),
                EstadoCargaMinimaCurricularV2.CUMPLE_MINIMO, 3, 0, false);
    }

    @Test public void f3AU02CuatroObligatoriasCumplenMinimo() {
        assertEstado(evaluar(contexto(obligatoria(4L), obligatoria(5L), obligatoria(6L), obligatoria(7L))),
                EstadoCargaMinimaCurricularV2.CUMPLE_MINIMO, 4, 0, false);
    }

    @Test public void f3AU03DosObligatoriasYUnaOptativaCumplen() {
        assertEstado(evaluar(contexto(obligatoria(8L), obligatoria(9L), optativa(10L))),
                EstadoCargaMinimaCurricularV2.CUMPLE_MEDIANTE_OPTATIVAS, 2, 1, false);
    }

    @Test public void f3AU04UnaObligatoriaYDosOptativasCumplen() {
        assertEstado(evaluar(contexto(obligatoria(11L), optativa(12L), optativa(13L))),
                EstadoCargaMinimaCurricularV2.CUMPLE_MEDIANTE_OPTATIVAS, 1, 2, false);
    }

    @Test public void f3AU05DosObligatoriasSinOptativasAplicanExcepcion() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(obligatoria(14L), obligatoria(15L)));
        assertEstado(r, EstadoCargaMinimaCurricularV2.EXCEPCION_MINIMO_APLICABLE, 2, 0, true);
        assertTrue(tieneExcepcion(r, CodigoExcepcionCurricularV2.CARGA_MINIMA_OPTATIVAS_INSUFICIENTES));
    }

    @Test public void f3AU06UnicaObligatoriaPendientePermiteCargaDeUnaEnSombra() {
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(obligatoria(16L)));
        assertEstado(r, EstadoCargaMinimaCurricularV2.EXCEPCION_MINIMO_APLICABLE, 1, 0, true);
        assertEquals(r.getEvaluacionCargaMinima().getCargaMinimaEvaluada(), Integer.valueOf(1));
        assertTrue(tieneExcepcion(r, CodigoExcepcionCurricularV2.CARGA_MINIMA_UNICA_OBLIGATORIA_PENDIENTE));
    }

    @Test public void f3AU07SoloOptativasCompatiblesSonTrazables() {
        assertEstado(evaluar(contexto(optativa(17L), optativa(18L), optativa(19L))),
                EstadoCargaMinimaCurricularV2.CUMPLE_MEDIANTE_OPTATIVAS, 0, 3, false);
    }

    @Test public void f3AU08OptativaBloqueadaPorSeriacionNoCuenta() {
        UnidadEvaluacionCurricularV2DTO bloqueada = optativa(20L);
        bloqueada.setSeriacionDirectaConocida(Boolean.TRUE);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(obligatoria(21L), obligatoria(22L), bloqueada));
        assertEstado(r, EstadoCargaMinimaCurricularV2.EXCEPCION_MINIMO_APLICABLE, 2, 0, true);
        assertTrue(r.getEvaluacionCargaMinima().getUdsDescartadas().toString().contains("SERIACION"));
    }

    @Test public void f3AU09ObligatoriaNoOfertadaNoCuenta() {
        UnidadEvaluacionCurricularV2DTO noOfertada = obligatoria(23L);
        noOfertada.setOfertada(Boolean.FALSE);
        noOfertada.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.UD_NO_OFERTADA_PERIODO);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(obligatoria(24L), obligatoria(25L), noOfertada));
        assertEquals(r.getEvaluacionCargaMinima().getCantidadObligatoriasElegibles(), Integer.valueOf(2));
        assertTrue(r.getEvaluacionCargaMinima().getUdsDescartadas().toString().contains("SIN_OFERTA"));
    }

    @Test public void f3AU10SinCupoSeDistingueDeFaltaOferta() {
        UnidadEvaluacionCurricularV2DTO sinCupo = obligatoria(26L);
        sinCupo.setCupoDisponibleConocido(Boolean.FALSE);
        sinCupo.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(obligatoria(27L), obligatoria(28L), sinCupo));
        assertTrue(r.getEvaluacionCargaMinima().getUdsDescartadas().toString().contains("SIN_CUPO"));
        assertFalse(r.getEvaluacionCargaMinima().getUdsDescartadas().toString().contains("UD26:SIN_OFERTA"));
    }

    @Test public void f3AU11BajaYNoCursadaNoSeTransformanEnReprobacion() {
        UnidadEvaluacionCurricularV2DTO baja = obligatoria(29L);
        baja.setEstado(EstadoUnidadCurricularV2.BAJA_PARCIAL); baja.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL);
        UnidadEvaluacionCurricularV2DTO noCursada = optativa(30L);
        noCursada.setEstado(EstadoUnidadCurricularV2.NO_CURSADA); noCursada.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(baja, noCursada, obligatoria(31L)));
        assertEquals(r.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        assertEquals(r.getEvaluacionCargaMinima().getCantidadTotalElegible(), Integer.valueOf(3));
    }

    @Test public void f3AU12RegularEIrregularCompartenMinimoNormativo() {
        ResultadoEvaluacionCurricularV2DTO regular = evaluar(contexto(obligatoria(32L), obligatoria(33L), obligatoria(34L)));
        UnidadEvaluacionCurricularV2DTO reprobada = obligatoria(35L);
        reprobada.setEstado(EstadoUnidadCurricularV2.REPROBADA); reprobada.setCausa(CausaPendienteUnidadV2.REPROBACION);
        reprobada.setReprobacionVigenteConocida(Boolean.TRUE); reprobada.setReprobacionPeriodoPrevioConocida(Boolean.TRUE);
        ResultadoEvaluacionCurricularV2DTO irregular = evaluar(contexto(reprobada, obligatoria(36L), obligatoria(37L)));
        assertEquals(regular.getEvaluacionCargaMinima().getCargaMinimaNormativa(), Integer.valueOf(3));
        assertEquals(irregular.getEvaluacionCargaMinima().getCargaMinimaNormativa(), Integer.valueOf(3));
    }

    @Test public void f3AU13DatosInsuficientesNoInfierenElegibilidad() {
        UnidadEvaluacionCurricularV2DTO ambigua = obligatoria(38L);
        ambigua.setGrupoActivoConocido(null);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(obligatoria(39L), ambigua));
        assertEquals(r.getEvaluacionCargaMinima().getEstado(), EstadoCargaMinimaCurricularV2.REQUIERE_REVISION);
        assertNull(r.getEvaluacionCargaMinima().getCargaMinimaAlcanzable());
    }

    @Test public void f3AU14PrimerSemestreConservaTratamientoNuevoIngreso() {
        ContextoEvaluacionCurricularV2DTO c = contexto(obligatoria(40L), obligatoria(41L), obligatoria(42L));
        c.setSemestreObjetivo(Integer.valueOf(1));
        EvaluacionCargaMinimaCurricularV2DTO e = evaluar(c).getEvaluacionCargaMinima();
        assertFalse(e.getAplicaCargaMinimaNormativa());
        assertEquals(e.getEstado(), EstadoCargaMinimaCurricularV2.NO_DETERMINADO);
    }

    @Test public void f3AU15MismaEntradaProduceMismoResultado() {
        ContextoEvaluacionCurricularV2DTO c = contexto(obligatoria(43L), optativa(44L));
        assertEquals(evaluar(c).getEvaluacionCargaMinima().getEstado(), evaluar(c).getEvaluacionCargaMinima().getEstado());
    }

    @Test public void f3AU16CargaSombraNoCambiaResultadoProductivo() {
        UnidadEvaluacionCurricularV2DTO primera = obligatoria(45L);
        UnidadEvaluacionCurricularV2DTO segunda = obligatoria(46L);
        ResultadoEvaluacionCurricularV2DTO r = evaluar(contexto(primera, segunda));
        assertFalse(r.getEfectosProductivosAplicados());
        assertTrue(r.getReglasEvaluadas().contains(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2.RN_4_1));
        assertTrue(primera.getOfertada()); assertTrue(primera.getSeleccionableOrigen());
        assertTrue(segunda.getOfertada()); assertTrue(segunda.getSeleccionableOrigen());
    }

    private ResultadoEvaluacionCurricularV2DTO evaluar(ContextoEvaluacionCurricularV2DTO c) { return service.evaluarSombra(c); }
    private void assertEstado(ResultadoEvaluacionCurricularV2DTO r, EstadoCargaMinimaCurricularV2 estado,
            int obligatorias, int optativas, boolean excepcion) {
        EvaluacionCargaMinimaCurricularV2DTO e = r.getEvaluacionCargaMinima();
        assertEquals(e.getEstado(), estado); assertEquals(e.getCantidadObligatoriasElegibles(), Integer.valueOf(obligatorias));
        assertEquals(e.getCantidadOptativasElegibles(), Integer.valueOf(optativas));
        assertEquals(e.getExcepcionCargaMinimaAplicable(), Boolean.valueOf(excepcion));
    }
    private boolean tieneExcepcion(ResultadoEvaluacionCurricularV2DTO r, CodigoExcepcionCurricularV2 codigo) {
        for (mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ExcepcionCurricularV2DTO e : r.getExcepcionesEvaluadas()) if (e.getCodigo() == codigo) return true;
        return false;
    }
    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO... unidades) {
        ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO();
        c.setVersionReglas("PE2027-V1"); c.setFechaEvaluacion(new Date(0L)); c.setSemestreObjetivo(Integer.valueOf(3));
        c.setActividadAcademicaComprobada(Boolean.TRUE); c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);
        c.setDictaminacionEgresoConocida(Boolean.TRUE); c.setOportunidadesMejoraUtilizadas(Integer.valueOf(0));
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) c.getUnidades().add(unidad);
        return c;
    }
    private UnidadEvaluacionCurricularV2DTO obligatoria(Long id) { return pendiente(id, "Obligatoria"); }
    private UnidadEvaluacionCurricularV2DTO optativa(Long id) { return pendiente(id, "Optativa"); }
    private UnidadEvaluacionCurricularV2DTO pendiente(Long id, String tipo) {
        UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO();
        u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd(tipo); u.setEstado(EstadoUnidadCurricularV2.NO_CURSADA);
        u.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO); u.setOfertada(Boolean.TRUE); u.setGrupoActivoConocido(Boolean.TRUE);
        u.setCupoDisponibleConocido(Boolean.TRUE); u.setSeleccionableOrigen(Boolean.TRUE); return u;
    }
}
