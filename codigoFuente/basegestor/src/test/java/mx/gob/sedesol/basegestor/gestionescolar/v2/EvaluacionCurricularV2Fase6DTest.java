package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CondicionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoReincorporacionBajaParcialV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cobertura F6D: RN-6.7 en sombra y sin efectos sobre reinscripcion real. */
public class EvaluacionCurricularV2Fase6DTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f6DU01SinBajaParcialNoAplica() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().clear();
        assertEquals(carga(contexto).getEstadoReincorporacionRn67(), EstadoReincorporacionBajaParcialV2.NO_APLICA);
    }

    @Test public void f6DU02BajaParcialFormalObligatoriaOfertadaEsPrioritaria() {
        EvaluacionCargaAcademicaV2DTO carga = carga(base());
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn67()));
        assertTrue(carga.getUdsBajaParcialPrioritariasRn67().contains(10L));
        assertTrue(carga.getUdsRutaReincorporacionRn67().contains(10L));
        assertEquals(carga.getEstadoReincorporacionRn67(), EstadoReincorporacionBajaParcialV2.REINCORPORACION_CON_PRIORIDAD_RECUPERACION);
    }

    @Test public void f6DU03FormalidadOCausaNoDeterminableRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.getUnidades().get(0).setBajaParcialFormalComprobada(null);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsBajaParcialRevisionRn67().contains(10L));
        assertEquals(carga.getEstadoReincorporacionRn67(), EstadoReincorporacionBajaParcialV2.REQUIERE_REVISION);
    }

    @Test public void f6DU04BajaParcialNoEsReprobacionNiIrregularidad() {
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(base());
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(0));
        assertEquals(resultado.getSituacionAcademicaCalculada(), "REGULAR");
        assertFalse(Boolean.TRUE.equals(resultado.getEvaluacionCargaAcademica().getAplicaRn64()));
        assertTrue(resultado.getCondicionesDetectadas().contains(CondicionCurricularV2.REINCORPORACION_TRAS_BAJA_PARCIAL));
    }

    @Test public void f6DU05EscenarioBObligatoriaSinOfertaNoBloqueaRutaRestante() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        contexto.getUnidades().get(0).setOfertada(Boolean.FALSE);
        UnidadEvaluacionCurricularV2DTO posterior = noInscrita(20L, 5, "Obligatoria"); contexto.getUnidades().add(posterior);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsBajaParcialSinOfertaRn67().contains(10L));
        assertTrue(carga.getUdsPosterioresCompatiblesRn67().contains(20L));
        assertEquals(contexto.getUnidades().get(0).getCausa(), CausaPendienteUnidadV2.BAJA_PARCIAL);
    }

    @Test public void f6DU06SinCupoNoSeConvierteEnSinOferta() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO baja = contexto.getUnidades().get(0);
        baja.setCupoDisponibleConocido(Boolean.FALSE); baja.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO);
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsBajaParcialSinCupoRn67().contains(10L));
        assertFalse(carga.getUdsBajaParcialSinOfertaRn67().contains(10L));
    }

    @Test public void f6DU07EscenarioCOptativaOfertadaNecesariaEsPrioritaria() {
        ContextoEvaluacionCurricularV2DTO contexto = baseOptativa(Boolean.TRUE); EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsBajaParcialPrioritariasRn67().contains(10L));
        assertEquals(carga.getEstadoReincorporacionRn67(), EstadoReincorporacionBajaParcialV2.REINCORPORACION_CON_PRIORIDAD_RECUPERACION);
    }

    @Test public void f6DU08OptativaNoNecesariaONoDeterminadaNoObtienePrioridad() {
        ContextoEvaluacionCurricularV2DTO noNecesaria = baseOptativa(Boolean.FALSE);
        assertFalse(carga(noNecesaria).getUdsBajaParcialPrioritariasRn67().contains(10L));
        ContextoEvaluacionCurricularV2DTO incierta = baseOptativa(null);
        assertEquals(carga(incierta).getEstadoReincorporacionRn67(), EstadoReincorporacionBajaParcialV2.REQUIERE_REVISION);
    }

    @Test public void f6DU09EscenarioDOptativaSinOfertaNoBloqueaObligatoriaElegible() {
        ContextoEvaluacionCurricularV2DTO contexto = baseOptativa(Boolean.TRUE);
        contexto.getUnidades().get(0).setOfertada(Boolean.FALSE);
        contexto.getUnidades().add(noInscrita(20L, 5, "Obligatoria"));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsBajaParcialSinOfertaRn67().contains(10L));
        assertTrue(carga.getUdsPosterioresCompatiblesRn67().contains(20L));
    }

    @Test public void f6DU10ElectivaNoAsumeSemanticaDeOptativa() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().get(0).setTipoUd("Electiva");
        assertEquals(carga(contexto).getEstadoReincorporacionRn67(), EstadoReincorporacionBajaParcialV2.REQUIERE_REVISION);
    }

    @Test public void f6DU11SancionImpeditivaONoResueltaNoFlexibiliza() {
        ContextoEvaluacionCurricularV2DTO impeditiva = base(); UnidadEvaluacionCurricularV2DTO baja = impeditiva.getUnidades().get(0);
        baja.setBajaParcialDerivadaSancion(Boolean.TRUE); baja.setResolucionSancionConocida(Boolean.TRUE);
        baja.setResolucionSancionPermiteFlexibilizacion(Boolean.FALSE);
        assertEquals(carga(impeditiva).getEstadoReincorporacionRn67(), EstadoReincorporacionBajaParcialV2.RESTRINGIDA_POR_SANCION);
        ContextoEvaluacionCurricularV2DTO incierta = base(); incierta.getUnidades().get(0).setBajaParcialDerivadaSancion(Boolean.TRUE);
        assertEquals(carga(incierta).getEstadoReincorporacionRn67(), EstadoReincorporacionBajaParcialV2.REQUIERE_REVISION);
    }

    @Test public void f6DU12Rn64CuentaSoloReprobadasReales() {
        ContextoEvaluacionCurricularV2DTO tres = base();
        for (long id = 1; id <= 3; id++) tres.getUnidades().add(reprobada(id, 2));
        assertFalse(Boolean.TRUE.equals(carga(tres).getAplicaRn64()));
        ContextoEvaluacionCurricularV2DTO cuatro = base();
        for (long id = 1; id <= 4; id++) cuatro.getUnidades().add(reprobada(id, 2));
        assertEquals(carga(cuatro).getEstadoReincorporacionRn67(), EstadoReincorporacionBajaParcialV2.REINCORPORACION_RESTRINGIDA_RN64);
    }

    @Test public void f6DU13SeriacionPorUdNoBloqueaCompatibles() {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO bloqueada = noInscrita(20L, 5, "Obligatoria"); bloqueada.setSeriacionDirectaConocida(Boolean.TRUE);
        contexto.getUnidades().add(bloqueada); contexto.getUnidades().add(noInscrita(21L, 5, "Obligatoria"));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsPosterioresBloqueadasSeriacionRn67().contains(20L));
        assertTrue(carga.getUdsPosterioresCompatiblesRn67().contains(21L));
    }

    @Test public void f6DU14Rn61SoloEnQuintoDesde2027() {
        ContextoEvaluacionCurricularV2DTO cuarto = base(); cuarto.setSemestreObjetivo(Integer.valueOf(4));
        assertFalse(Boolean.TRUE.equals(carga(cuarto).getRn61ConsumidaRn67()));
        ContextoEvaluacionCurricularV2DTO quinto = base(); quinto.setSemestreObjetivo(Integer.valueOf(5)); quinto.setPeriodoNormativo("2027-1");
        assertTrue(Boolean.TRUE.equals(carga(quinto).getRn61ConsumidaRn67()));
        ContextoEvaluacionCurricularV2DTO previo = base(); previo.setSemestreObjetivo(Integer.valueOf(5)); previo.setPeriodoNormativo("2026-2");
        assertFalse(Boolean.TRUE.equals(carga(previo).getRn61ConsumidaRn67()));
    }

    @Test public void f6DU15ConcurrenciaConReprobadaConservaAmbasYSolicitaRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().add(reprobada(1L, 2));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(Boolean.TRUE.equals(carga.getAplicaRn63()));
        assertTrue(carga.getUdsBajaParcialPrioritariasRn67().contains(10L));
        assertEquals(carga.getEstadoReincorporacionRn67(), EstadoReincorporacionBajaParcialV2.REQUIERE_REVISION);
    }

    @Test public void f6DU16DuplicadosYCargaSonDeterministasSinEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO contexto = base(); contexto.getUnidades().add(bajaParcial(10L, 3, "Obligatoria"));
        for (long id = 20; id <= 30; id++) contexto.getUnidades().add(noInscrita(id, 5, "Obligatoria"));
        EvaluacionCargaAcademicaV2DTO primera = carga(contexto); EvaluacionCargaAcademicaV2DTO segunda = carga(contexto);
        assertEquals(primera.getUdsBajaParcialPrioritariasRn67().size(), 1);
        assertTrue(primera.getUdsRutaReincorporacionRn67().size() <= primera.getCargaMaximaNormativa().intValue());
        assertEquals(primera.getDecisionesPorUdRn67(), segunda.getDecisionesPorUdRn67());
        assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(contexto).getEfectosProductivosAplicados()));
    }

    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) { return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica(); }

    private ContextoEvaluacionCurricularV2DTO base() {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setSemestreObjetivo(Integer.valueOf(4)); contexto.setPeriodoNormativo("2027-1");
        contexto.getUnidades().add(bajaParcial(10L, 3, "Obligatoria")); return contexto;
    }

    private ContextoEvaluacionCurricularV2DTO baseOptativa(Boolean necesaria) {
        ContextoEvaluacionCurricularV2DTO contexto = base();
        UnidadEvaluacionCurricularV2DTO unidad = contexto.getUnidades().get(0); unidad.setTipoUd("Optativa");
        unidad.setNecesariaParaRequerimientoCurricular(necesaria); return contexto;
    }

    private UnidadEvaluacionCurricularV2DTO bajaParcial(Long id, int semestre, String tipo) {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(id, semestre, tipo, EstadoUnidadCurricularV2.BAJA_PARCIAL);
        unidad.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL); unidad.setBajaParcialFormalComprobada(Boolean.TRUE);
        unidad.setBajaParcialDerivadaSancion(Boolean.FALSE); return unidad;
    }

    private UnidadEvaluacionCurricularV2DTO noInscrita(Long id, int semestre, String tipo) { return unidad(id, semestre, tipo, EstadoUnidadCurricularV2.NO_INSCRITA); }

    private UnidadEvaluacionCurricularV2DTO reprobada(Long id, int semestre) {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(id, semestre, "Obligatoria", EstadoUnidadCurricularV2.REPROBADA);
        unidad.setCausa(CausaPendienteUnidadV2.REPROBACION); unidad.setReprobacionVigenteConocida(Boolean.TRUE);
        unidad.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); return unidad;
    }

    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, String tipo, EstadoUnidadCurricularV2 estado) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(id); unidad.setClave("UD" + id); unidad.setTipoUd(tipo); unidad.setSemestre(Integer.valueOf(semestre));
        unidad.setEstado(estado); unidad.setOfertada(Boolean.TRUE); unidad.setCupoDisponibleConocido(Boolean.TRUE); return unidad;
    }
}
