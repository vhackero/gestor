package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.*;

import java.math.BigDecimal;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoAvanceUltimosSemestresV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.RelacionSeriacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoRelacionSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** RN-9.3: S7/S8 reutiliza restricciones concurrentes, sin umbral porcentual de créditos. */
public class EvaluacionCurricularV2Fase9BTest {
    private final EvaluacionCurricularV2ServiceImpl servicio = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f9BU01S1AS6NoAplica() { assertFalse(Boolean.TRUE.equals(carga(contexto(6)).getAplicaRn93())); }
    @Test public void f9BU02S7YS8Aplican() { assertTrue(Boolean.TRUE.equals(carga(contexto(7)).getAplicaRn93())); assertTrue(Boolean.TRUE.equals(carga(contexto(8)).getAplicaRn93())); }
    @Test public void f9BU03CreditosNoSonPrerequisitoAutonomoRn93() { ContextoEvaluacionCurricularV2DTO c = contexto(7); c.setCreditosTotalesPrograma(null); assertEquals(carga(c).getEstadoAvanceUltimosSemestresRn93(), EstadoAvanceUltimosSemestresV2.AVANCE_ULTIMOS_SEMESTRES_CUMPLE); }

    @Test public void f9BU04Creditos49_50_51NoModificanRn93() {
        assertEquals(carga(conCreditos(7, "99")).getEstadoAvanceUltimosSemestresRn93(), EstadoAvanceUltimosSemestresV2.AVANCE_ULTIMOS_SEMESTRES_CUMPLE);
        assertEquals(carga(conCreditos(7, "100")).getEstadoAvanceUltimosSemestresRn93(), EstadoAvanceUltimosSemestresV2.AVANCE_ULTIMOS_SEMESTRES_CUMPLE);
        assertEquals(carga(conCreditos(7, "101")).getEstadoAvanceUltimosSemestresRn93(), EstadoAvanceUltimosSemestresV2.AVANCE_ULTIMOS_SEMESTRES_CUMPLE);
    }

    @Test public void f9BU05CreditosHistoricosNoSeUsanComoFiltroRn93() {
        ContextoEvaluacionCurricularV2DTO c = contexto(7); c.getUnidades().add(unidad(1L, 2, EstadoUnidadCurricularV2.ACREDITADA, "100"));
        UnidadEvaluacionCurricularV2DTO duplicada = unidad(1L, 2, EstadoUnidadCurricularV2.ACREDITADA, "100"); duplicada.getReferenciasHistoricas().add("HISTORICO_REPROBADA"); c.getUnidades().add(duplicada);
        c.getUnidades().add(unidad(2L, 3, EstadoUnidadCurricularV2.REPROBADA, "50")); c.getUnidades().add(unidad(3L, 3, EstadoUnidadCurricularV2.EN_CURSO, "50"));
        assertNull(carga(c).getCreditosAcreditadosValidosRn93());
    }

    @Test public void f9BU06CreditoSinEvidenciaNoBloqueaRn93PorSiSolo() { ContextoEvaluacionCurricularV2DTO c = contexto(7); UnidadEvaluacionCurricularV2DTO u = unidad(1L, 2, EstadoUnidadCurricularV2.ACREDITADA, "100"); u.setCreditoCurricularValido(Boolean.FALSE); c.getUnidades().add(u); assertFalse(Boolean.TRUE.equals(carga(c).getRequiereRevisionRn93())); }
    @Test public void f9BU07S7YS8NoReactivanRn61() { assertFalse(Boolean.TRUE.equals(carga(conCreditos(7, "100")).getAplicaCtr01())); assertFalse(Boolean.TRUE.equals(carga(conCreditos(8, "100")).getAplicaCtr01())); }

    @Test public void f9BU08SeriacionDirectaEIndirectaSeConservan() {
        ContextoEvaluacionCurricularV2DTO c = contextoConSeriacion(EstadoUnidadCurricularV2.NO_CURSADA); c.getUnidades().add(unidad(9L, 1, EstadoUnidadCurricularV2.ACREDITADA, "100"));
        EvaluacionCargaAcademicaV2DTO carga = carga(c); assertTrue(carga.getUdsInteranualesNoCumplidasRn83().contains(2L)); assertEquals(carga.getEstadoAvanceUltimosSemestresRn93(), EstadoAvanceUltimosSemestresV2.RESTRINGIDO_POR_SERIACION);
    }

    @Test public void f9BU09CreditoNoSustituyeSeriacionNiViceversa() {
        ContextoEvaluacionCurricularV2DTO setenta = contextoConSeriacion(EstadoUnidadCurricularV2.NO_CURSADA); setenta.getUnidades().add(unidad(9L, 1, EstadoUnidadCurricularV2.ACREDITADA, "140")); assertEquals(carga(setenta).getEstadoAvanceUltimosSemestresRn93(), EstadoAvanceUltimosSemestresV2.RESTRINGIDO_POR_SERIACION);
        ContextoEvaluacionCurricularV2DTO cuarenta = contextoConSeriacion(EstadoUnidadCurricularV2.ACREDITADA); cuarenta.getUnidades().add(unidad(9L, 1, EstadoUnidadCurricularV2.ACREDITADA, "90")); assertEquals(carga(cuarenta).getEstadoAvanceUltimosSemestresRn93(), EstadoAvanceUltimosSemestresV2.AVANCE_ULTIMOS_SEMESTRES_CUMPLE);
    }

    @Test public void f9BU10Rn64PermaneceCon80PorCiento() { ContextoEvaluacionCurricularV2DTO c = conCreditos(7, "160"); for (long id = 10; id < 14; id++) c.getUnidades().add(unidad(id, 2, EstadoUnidadCurricularV2.REPROBADA, "0")); EvaluacionCargaAcademicaV2DTO carga = carga(c); assertTrue(Boolean.TRUE.equals(carga.getAplicaRn64())); assertEquals(carga.getEstadoAvanceUltimosSemestresRn93(), EstadoAvanceUltimosSemestresV2.RESTRINGIDO_POR_RN64); }

    @Test public void f9BU11ElectivaNoCreaUmbralNiRequisitoRn93() {
        ContextoEvaluacionCurricularV2DTO c = conCreditos(7, "80"); c.setIdPlan(1L); UnidadEvaluacionCurricularV2DTO e = unidad(2L, 5, EstadoUnidadCurricularV2.NO_INSCRITA, "0"); e.setTipoUd("Electiva"); e.setIdPlanOrigenElectiva(2L); e.setRequisitosEspecificosPeComprobados(Boolean.TRUE); c.getUnidades().add(e); assertEquals(carga(c).getEstadoAvanceUltimosSemestresRn93(), EstadoAvanceUltimosSemestresV2.AVANCE_ULTIMOS_SEMESTRES_CUMPLE); }

    @Test public void f9BU12RequisitosPeBloqueanSoloComponenteRelacionado() {
        ContextoEvaluacionCurricularV2DTO c = conCreditos(7, "100"); UnidadEvaluacionCurricularV2DTO pendiente = unidad(3L, 4, EstadoUnidadCurricularV2.NO_CURSADA, "0"); UnidadEvaluacionCurricularV2DTO seminario = unidad(2L, 7, EstadoUnidadCurricularV2.NO_INSCRITA, "0"); seminario.setComponenteSeminarioTitulacion(Boolean.TRUE); seminario.setRequisitosEspecificosPeComprobados(Boolean.TRUE); seminario.getRequisitosCurricularesPe().add(3L); c.getUnidades().add(pendiente); c.getUnidades().add(seminario); EvaluacionCargaAcademicaV2DTO carga = carga(c); assertTrue(carga.getComponentesRestringidosRequisitoPeRn93().contains(2L)); assertEquals(carga.getEstadoAvanceUltimosSemestresRn93(), EstadoAvanceUltimosSemestresV2.RESTRINGIDO_POR_REQUISITO_PE); }

    @Test public void f9BU13PendienteNoRelacionadoNoCreaBloqueoGlobalYSinConfiguracionHayRevision() {
        ContextoEvaluacionCurricularV2DTO sinRelacion = conCreditos(7, "100"); sinRelacion.getUnidades().add(unidad(1L, 4, EstadoUnidadCurricularV2.NO_CURSADA, "0")); UnidadEvaluacionCurricularV2DTO seminario = unidad(2L, 7, EstadoUnidadCurricularV2.NO_INSCRITA, "0"); seminario.setComponenteSeminarioTitulacion(Boolean.TRUE); seminario.setRequisitosEspecificosPeComprobados(Boolean.TRUE); sinRelacion.getUnidades().add(seminario); assertFalse(carga(sinRelacion).getComponentesRestringidosRequisitoPeRn93().contains(2L));
        ContextoEvaluacionCurricularV2DTO sinConfig = conCreditos(7, "100"); UnidadEvaluacionCurricularV2DTO electiva = unidad(3L, 5, EstadoUnidadCurricularV2.NO_INSCRITA, "0"); electiva.setTipoUd("Electiva"); sinConfig.getUnidades().add(electiva); assertFalse(Boolean.TRUE.equals(carga(sinConfig).getRequiereRevisionRn93()));
    }

    @Test public void f9BU14OfertaCupoYDeterminismoSinEfectoProductivo() { ContextoEvaluacionCurricularV2DTO c = conCreditos(7, "100"); UnidadEvaluacionCurricularV2DTO u = unidad(2L, 7, EstadoUnidadCurricularV2.NO_INSCRITA, "0"); u.setOfertada(Boolean.FALSE); u.setCupoDisponibleConocido(Boolean.FALSE); c.getUnidades().add(u); EvaluacionCargaAcademicaV2DTO primera = carga(c), segunda = carga(c); assertEquals(primera.getPorcentajeCreditosAcreditadosRn93(), segunda.getPorcentajeCreditosAcreditadosRn93()); assertFalse(Boolean.TRUE.equals(servicio.evaluarSombra(c).getEfectosProductivosAplicados())); }

    private ContextoEvaluacionCurricularV2DTO contexto(int semestre) { ContextoEvaluacionCurricularV2DTO c = new ContextoEvaluacionCurricularV2DTO(); c.setSemestreObjetivo(Integer.valueOf(semestre)); c.setPeriodoNormativo("2027-1"); c.setCreditosTotalesPrograma(new BigDecimal("200")); c.setSeriacionComprobada(Boolean.TRUE); return c; }
    private ContextoEvaluacionCurricularV2DTO conCreditos(int semestre, String creditos) { ContextoEvaluacionCurricularV2DTO c = contexto(semestre); c.getUnidades().add(unidad(1L, 2, EstadoUnidadCurricularV2.ACREDITADA, creditos)); return c; }
    private ContextoEvaluacionCurricularV2DTO contextoConSeriacion(EstadoUnidadCurricularV2 estado) { ContextoEvaluacionCurricularV2DTO c = contexto(7); UnidadEvaluacionCurricularV2DTO a = unidad(1L, 5, estado, "0"); UnidadEvaluacionCurricularV2DTO b = unidad(2L, 7, EstadoUnidadCurricularV2.NO_INSCRITA, "0"); RelacionSeriacionCurricularV2DTO r = new RelacionSeriacionCurricularV2DTO(); r.setIdAntecedente(1L); r.setIdConsecuente(2L); r.setSemestreAntecedente(Integer.valueOf(5)); r.setSemestreConsecuente(Integer.valueOf(7)); r.setTipoSeriacion(TipoSeriacionV2.INTERANUAL); r.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA); r.setEvidencia("PE_F9B:1->2"); b.getRelacionesSeriacion().add(r); c.getUnidades().add(a); c.getUnidades().add(b); return c; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id, int semestre, EstadoUnidadCurricularV2 estado, String creditos) { UnidadEvaluacionCurricularV2DTO u = new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD" + id); u.setTipoUd("Obligatoria"); u.setSemestre(Integer.valueOf(semestre)); u.setEstado(estado); u.setCreditosCurriculares(new BigDecimal(creditos)); u.setCreditoCurricularValido(Boolean.TRUE); u.setOfertada(Boolean.TRUE); u.setGrupoActivoConocido(Boolean.TRUE); u.setCupoDisponibleConocido(Boolean.TRUE); if (estado == EstadoUnidadCurricularV2.REPROBADA) { u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setReprobacionVigenteConocida(Boolean.TRUE); } return u; }
    private EvaluacionCargaAcademicaV2DTO carga(ContextoEvaluacionCurricularV2DTO c) { return servicio.evaluarSombra(c).getEvaluacionCargaAcademica(); }
}
