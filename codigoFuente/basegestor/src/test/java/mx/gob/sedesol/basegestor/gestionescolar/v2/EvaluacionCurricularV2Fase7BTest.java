package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoElegibilidadElectivaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** Cobertura F7B: RN-7.2 de electivas sin inscripción ni sustitución productiva. */
public class EvaluacionCurricularV2Fase7BTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f7BU01NoAplicaSemanticaElectivaAUdNoElectiva() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); contexto.getUnidades().add(unidad(1L, "Optativa", EstadoUnidadCurricularV2.NO_INSCRITA));
        assertFalse(Boolean.TRUE.equals(carga(contexto).getAplicaRn72()));
    }

    @Test public void f7BU02S6NoEsElegibleYS7YS8SonCandidatos() {
        ContextoEvaluacionCurricularV2DTO s6 = base(6); s6.getUnidades().add(electivaNueva(1L, 2L));
        assertEquals(carga(s6).getEstadoElegibilidadElectiva(), EstadoElegibilidadElectivaV2.NO_ELEGIBLE_POR_SEMESTRE);
        ContextoEvaluacionCurricularV2DTO s7 = base(7); s7.getUnidades().add(electivaNueva(1L, 2L));
        assertTrue(carga(s7).getUdsElectivasElegiblesRn72().contains(1L));
        ContextoEvaluacionCurricularV2DTO s8 = base(8); s8.getUnidades().add(electivaNueva(1L, 2L));
        assertTrue(carga(s8).getUdsElectivasElegiblesRn72().contains(1L));
    }

    @Test public void f7BU03SemestreObjetivoNoDeterminadoRequiereRevision() {
        ContextoEvaluacionCurricularV2DTO contexto = base(null); contexto.getUnidades().add(electivaNueva(1L, 2L));
        assertEquals(carga(contexto).getEstadoElegibilidadElectiva(), EstadoElegibilidadElectivaV2.REQUIERE_REVISION);
    }

    @Test public void f7BU04MismoPeNoEsElegibleYOrigenFaltanteEsRevision() {
        ContextoEvaluacionCurricularV2DTO mismo = base(7); mismo.getUnidades().add(electivaNueva(1L, 1L));
        assertEquals(carga(mismo).getEstadoElegibilidadElectiva(), EstadoElegibilidadElectivaV2.NO_ELEGIBLE_POR_MISMO_PE);
        ContextoEvaluacionCurricularV2DTO faltante = base(7); faltante.getUnidades().add(electivaNueva(2L, null));
        assertEquals(carga(faltante).getEstadoElegibilidadElectiva(), EstadoElegibilidadElectivaV2.REQUIERE_REVISION);
    }

    @Test public void f7BU05ElectivaDebeEstarClasificadaYUbicadaEnS5OS6() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); UnidadEvaluacionCurricularV2DTO electiva = electivaNueva(1L, 2L); electiva.setSemestre(Integer.valueOf(7)); contexto.getUnidades().add(electiva);
        assertEquals(carga(contexto).getEstadoElegibilidadElectiva(), EstadoElegibilidadElectivaV2.REQUIERE_REVISION);
    }

    @Test public void f7BU06ElectivaReprobadaAfectaSituacionYConservaReprobacion() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); contexto.getUnidades().add(reprobada(1L, "Electiva"));
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(resultado.getSituacionAcademica(), "IRREGULAR");
        assertEquals(resultado.getCantidadReprobadasVigentes(), Integer.valueOf(1));
        assertTrue(resultado.getEvaluacionCargaAcademica().getUdsElectivasReprobadasRn72().contains(1L));
    }

    @Test public void f7BU07ReprobarAProhibeNoAutomaticamenteElectivaB() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); contexto.getUnidades().add(reprobada(1L, "Electiva")); contexto.getUnidades().add(electivaNueva(2L, 2L));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertFalse(carga.getUdsElectivasMismoPeRn72().contains(2L));
        assertFalse(carga.getUdsElectivasBloqueadasSeriacionRn72().contains(2L));
        assertTrue(carga.getUdsElectivasIdentificadasRn72().contains(2L));
    }

    @Test public void f7BU08ElectivasCuentanEnRn64YBajasNo() {
        ContextoEvaluacionCurricularV2DTO tres = base(7); agregarReprobadas(tres, 3, "Electiva");
        assertFalse(Boolean.TRUE.equals(carga(tres).getAplicaRn64()));
        ContextoEvaluacionCurricularV2DTO cuatro = base(7); agregarReprobadas(cuatro, 4, "Electiva");
        assertTrue(Boolean.TRUE.equals(carga(cuatro).getAplicaRn64()));
        ContextoEvaluacionCurricularV2DTO baja = base(7); baja.getUnidades().add(unidad(1L, "Electiva", EstadoUnidadCurricularV2.BAJA_PARCIAL));
        assertEquals(servicio.evaluarSombra(baja).getCantidadReprobadasVigentes(), Integer.valueOf(0));
    }

    @Test public void f7BU09NuevaElectivaNoDesplazaObligatoriaPrioritaria() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); contexto.getUnidades().add(reprobada(1L, "Obligatoria")); contexto.getUnidades().add(electivaNueva(2L, 2L));
        assertTrue(carga(contexto).getUdsElectivasConRestriccionPrioridadRn72().contains(2L));
    }

    @Test public void f7BU10DisponibilidadDistingueSinOfertaYSinCupo() {
        ContextoEvaluacionCurricularV2DTO sinOferta = base(7); UnidadEvaluacionCurricularV2DTO a = electivaNueva(1L, 2L); a.setOfertada(Boolean.FALSE); sinOferta.getUnidades().add(a);
        assertTrue(carga(sinOferta).getUdsElectivasSinOfertaRn72().contains(1L));
        ContextoEvaluacionCurricularV2DTO sinCupo = base(7); UnidadEvaluacionCurricularV2DTO b = electivaNueva(2L, 2L); b.setCupoDisponibleConocido(Boolean.FALSE); b.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO); sinCupo.getUnidades().add(b);
        assertTrue(carga(sinCupo).getUdsElectivasSinCupoRn72().contains(2L));
    }

    @Test public void f7BU11SeriacionFormalBloqueaSoloLaElectivaRelacionada() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); UnidadEvaluacionCurricularV2DTO bloqueada = electivaNueva(1L, 2L); bloqueada.setSeriacionDirectaConocida(Boolean.TRUE);
        contexto.getUnidades().add(bloqueada); contexto.getUnidades().add(electivaNueva(2L, 2L));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertTrue(carga.getUdsElectivasBloqueadasSeriacionRn72().contains(1L));
        assertTrue(carga.getUdsElectivasElegiblesRn72().contains(2L));
    }

    @Test public void f7BU12RegularPuedeTenerMasDeUnaElectivaYS7NoReactivaRn61() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); contexto.getUnidades().add(electivaNueva(1L, 2L)); contexto.getUnidades().add(electivaNueva(2L, 3L));
        EvaluacionCargaAcademicaV2DTO carga = carga(contexto);
        assertEquals(carga.getUdsElectivasElegiblesRn72().size(), 2);
        assertFalse(Boolean.TRUE.equals(carga.getAplicaCtr01()));
    }

    @Test public void f7BU13CargaMaximaRestringeSinCrearLimiteDeUnaElectiva() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7);
        for (long id = 1; id <= 8; id++) contexto.getUnidades().add(unidad(id, "Obligatoria", EstadoUnidadCurricularV2.NO_INSCRITA));
        contexto.getUnidades().add(electivaNueva(20L, 2L));
        assertTrue(carga(contexto).getDecisionesPorUdElectivaRn72().toString().contains("CARGA_MAXIMA_ALCANZADA"));
    }

    @Test public void f7BU14DuplicadosNoDuplicanDecisionYNoHayEfectoProductivo() {
        ContextoEvaluacionCurricularV2DTO contexto = base(7); contexto.getUnidades().add(electivaNueva(1L, 2L)); contexto.getUnidades().add(electivaNueva(1L, 2L));
        ResultadoEvaluacionCurricularV2DTO resultado = servicio.evaluarSombra(contexto);
        assertEquals(resultado.getEvaluacionCargaAcademica().getUdsElectivasElegiblesRn72().size(), 1);
        assertFalse(Boolean.TRUE.equals(resultado.getEfectosProductivosAplicados()));
    }

    @Test public void f7BU15MismoContextoEsDeterminista() {
        ContextoEvaluacionCurricularV2DTO contexto = base(8); contexto.getUnidades().add(electivaNueva(1L, 2L));
        assertEquals(carga(contexto).getDecisionesPorUdElectivaRn72(), carga(contexto).getDecisionesPorUdElectivaRn72());
    }

    @Test public void f7BU16ConteoMixtoYNoCursadaUsanSoloReprobacionesVigentes() {
        ContextoEvaluacionCurricularV2DTO mixto = base(7); agregarReprobadas(mixto, 2, "Electiva"); mixto.getUnidades().add(reprobada(10L, "Optativa")); mixto.getUnidades().add(reprobada(11L, "Obligatoria"));
        assertTrue(Boolean.TRUE.equals(carga(mixto).getAplicaRn64()));
        ContextoEvaluacionCurricularV2DTO noCursada = base(7); noCursada.getUnidades().add(unidad(20L, "Electiva", EstadoUnidadCurricularV2.NO_CURSADA));
        assertEquals(servicio.evaluarSombra(noCursada).getCantidadReprobadasVigentes(), Integer.valueOf(0));
    }

    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO contexto) { return servicio.evaluarSombra(contexto).getEvaluacionCargaAcademica(); }

    private ContextoEvaluacionCurricularV2DTO base(Integer semestre) {
        ContextoEvaluacionCurricularV2DTO contexto = new ContextoEvaluacionCurricularV2DTO();
        contexto.setIdPlan(1L); contexto.setSemestreObjetivo(semestre); contexto.setPeriodoNormativo("2027-1"); contexto.setSeriacionComprobada(Boolean.TRUE); return contexto;
    }

    private void agregarReprobadas(ContextoEvaluacionCurricularV2DTO contexto, int cantidad, String tipo) {
        for (long id = 1; id <= cantidad; id++) contexto.getUnidades().add(reprobada(id, tipo));
    }

    private UnidadEvaluacionCurricularV2DTO electivaNueva(Long id, Long planOrigen) {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(id, "Electiva", EstadoUnidadCurricularV2.NO_INSCRITA); unidad.setSemestre(Integer.valueOf(5)); unidad.setIdPlanOrigenElectiva(planOrigen); return unidad;
    }

    private UnidadEvaluacionCurricularV2DTO reprobada(Long id, String tipo) {
        UnidadEvaluacionCurricularV2DTO unidad = unidad(id, tipo, EstadoUnidadCurricularV2.REPROBADA); unidad.setCausa(CausaPendienteUnidadV2.REPROBACION);
        unidad.setReprobacionVigenteConocida(Boolean.TRUE); unidad.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); return unidad;
    }

    private UnidadEvaluacionCurricularV2DTO unidad(Long id, String tipo, EstadoUnidadCurricularV2 estado) {
        UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
        unidad.setIdPrograma(id); unidad.setClave("UD" + id); unidad.setTipoUd(tipo); unidad.setSemestre(Integer.valueOf(5)); unidad.setEstado(estado);
        unidad.setOfertada(Boolean.TRUE); unidad.setGrupoActivoConocido(Boolean.TRUE); unidad.setCupoDisponibleConocido(Boolean.TRUE); return unidad;
    }
}
