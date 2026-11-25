package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoControlAvanceAnualV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** F5A-U-01 a F5A-U-25: CTR-01 y base RN-6.1.1, sin avance condicionado. */
public class EvaluacionCurricularV2Fase5ATest {
    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f5AU01AntesDeVigenciaNoAplica() { ContextoEvaluacionCurricularV2DTO z = contexto(); z.setPeriodoNormativo("2026-2"); assertNoAplica(e(z)); }
    @Test public void f5AU02DesdeVigenciaEnQuintoAplica() { EvaluacionCargaAcademicaV2DTO x = e(contexto(baseAcreditada())).getEvaluacionCargaAcademica(); assertTrue(x.getAplicaCtr01()); assertTrue(x.getAplicaRn61()); }
    @Test public void f5AU03TerceroNoAplica() { ContextoEvaluacionCurricularV2DTO z=contexto(baseAcreditada()); z.setSemestreObjetivo(Integer.valueOf(3)); assertNoAplica(e(z)); }
    @Test public void f5AU04SeptimoNoAplica() { ContextoEvaluacionCurricularV2DTO z=contexto(baseAcreditada()); z.setSemestreObjetivo(Integer.valueOf(7)); assertNoAplica(e(z)); }
    @Test public void f5AU05CuartoNoAplica() { ContextoEvaluacionCurricularV2DTO z=contexto(baseAcreditada()); z.setSemestreObjetivo(Integer.valueOf(4)); assertNoAplica(e(z)); }
    @Test public void f5AU06SextoNoAplica() { ContextoEvaluacionCurricularV2DTO z=contexto(baseAcreditada()); z.setSemestreObjetivo(Integer.valueOf(6)); assertNoAplica(e(z)); }
    @Test public void f5AU07SinObjetivoNiCandidataNoAplica() { ContextoEvaluacionCurricularV2DTO z=contexto(baseAcreditada()); z.setSemestreObjetivo(null); assertEquals(e(z).getEvaluacionCargaAcademica().getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.NO_APLICA); }
    @Test public void f5AU08BaseCompletaDaAccesoOrdinarioSombra() { EvaluacionCargaAcademicaV2DTO x=e(contexto(baseAcreditada())).getEvaluacionCargaAcademica(); assertEquals(x.getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.ACCESO_ORDINARIO_PROCEDE_EN_SOMBRA); assertTrue(x.getAccesoOrdinarioQuinto()); assertEquals(x.getTotalObligatoriasSemestres1A4(),Integer.valueOf(4)); }
    @Test public void f5AU09ReprobadaEsPendienteNoAcceso() { EvaluacionCargaAcademicaV2DTO x=e(contexto(baseCon(reprobada(2L,2)))).getEvaluacionCargaAcademica(); assertCondicionada(x); assertTrue(x.getDetallePendientesRn611().toString().contains("REPROBADA")); }
    @Test public void f5AU10BajaParcialEsPendienteNoReprobada() { ResultadoEvaluacionCurricularV2DTO r=e(contexto(baseCon(pendiente(3L,3,EstadoUnidadCurricularV2.BAJA_PARCIAL,CausaPendienteUnidadV2.BAJA_PARCIAL)))); assertEquals(r.getCantidadReprobadasVigentes(),Integer.valueOf(0)); assertCondicionada(r.getEvaluacionCargaAcademica()); }
    @Test public void f5AU11NoCursadaEsPendienteNoReprobada() { ResultadoEvaluacionCurricularV2DTO r=e(contexto(baseCon(pendiente(3L,3,EstadoUnidadCurricularV2.NO_CURSADA,CausaPendienteUnidadV2.NO_CURSAMIENTO)))); assertEquals(r.getCantidadReprobadasVigentes(),Integer.valueOf(0)); assertCondicionada(r.getEvaluacionCargaAcademica()); }
    @Test public void f5AU12EnCursoNoEsAcreditada() { assertCondicionada(e(contexto(baseCon(pendiente(4L,4,EstadoUnidadCurricularV2.EN_CURSO,null)))).getEvaluacionCargaAcademica()); }
    @Test public void f5AU13EstadoNoDeterminadoRequiereRevision() { EvaluacionCargaAcademicaV2DTO x=e(contexto(baseCon(pendiente(1L,1,EstadoUnidadCurricularV2.NO_DETERMINADO,null)))).getEvaluacionCargaAcademica(); assertEquals(x.getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.REQUIERE_REVISION); }
    @Test public void f5AU14AcreditacionPosteriorSatisfaceBase() { UnidadEvaluacionCurricularV2DTO u=acreditada(1L,1); u.getReferenciasHistoricas().add("INTENTO_1:REPROBADA"); u.getReferenciasHistoricas().add("INTENTO_2:ACREDITADA"); assertEquals(e(contexto(baseCon(u))).getEvaluacionCargaAcademica().getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.ACCESO_ORDINARIO_PROCEDE_EN_SOMBRA); }
    @Test public void f5AU15OptativaReprobadaNoEntraBase() { ContextoEvaluacionCurricularV2DTO z=contexto(baseAcreditada()); z.getUnidades().add(reprobadaTipo(20L,2,"Optativa")); EvaluacionCargaAcademicaV2DTO x=e(z).getEvaluacionCargaAcademica(); assertEquals(x.getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.ACCESO_ORDINARIO_PROCEDE_EN_SOMBRA); assertEquals(x.getTotalObligatoriasSemestres1A4(),Integer.valueOf(4)); }
    @Test public void f5AU16ElectivaPendienteNoEntraBase() { ContextoEvaluacionCurricularV2DTO z=contexto(baseAcreditada()); z.getUnidades().add(pendiente(21L,4,EstadoUnidadCurricularV2.NO_CURSADA,CausaPendienteUnidadV2.NO_CURSAMIENTO,"Electiva")); assertEquals(e(z).getEvaluacionCargaAcademica().getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.ACCESO_ORDINARIO_PROCEDE_EN_SOMBRA); }
    @Test public void f5AU17DuplicadoTecnicoCuentaUnaVez() { ContextoEvaluacionCurricularV2DTO z=contexto(baseAcreditada()); UnidadEvaluacionCurricularV2DTO d=acreditada(1L,1);d.setBloque("B2");z.getUnidades().add(d);assertEquals(e(z).getEvaluacionCargaAcademica().getTotalObligatoriasSemestres1A4(),Integer.valueOf(4)); }
    @Test public void f5AU18Rn64PrevaleceConCuatroReprobadas() { ContextoEvaluacionCurricularV2DTO z=contexto(reprobada(1L,1),reprobada(2L,2),reprobada(3L,3),reprobada(4L,4)); EvaluacionCargaAcademicaV2DTO x=e(z).getEvaluacionCargaAcademica();assertTrue(x.getAplicaCtr01());assertTrue(x.getAplicaRn64());assertEquals(x.getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.BLOQUEADO_POR_RESTRICCION_CONCURRENTE);assertTrue(x.getRequiereEvaluacionAvanceCondicionado()); }
    @Test public void f5AU19IrregularConUnaReprobadaRequiereFasePosterior() { assertCondicionada(e(contexto(baseCon(reprobada(3L,3)))).getEvaluacionCargaAcademica()); }
    @Test public void f5AU20RegularConBajaParcialMantieneSituacionSeparada() { ResultadoEvaluacionCurricularV2DTO r=e(contexto(baseCon(pendiente(1L,1,EstadoUnidadCurricularV2.BAJA_PARCIAL,CausaPendienteUnidadV2.BAJA_PARCIAL))));assertEquals(r.getSituacionAcademicaCalculada(),"REGULAR");assertCondicionada(r.getEvaluacionCargaAcademica()); }
    @Test public void f5AU21PlazoMaximoSeIdentificaSinBloqueoAutomatico() { ContextoEvaluacionCurricularV2DTO z=contexto(baseAcreditada());z.setLimitePermanenciaExcedido(Boolean.TRUE);assertEquals(e(z).getEvaluacionCargaAcademica().getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.ACCESO_ORDINARIO_PROCEDE_EN_SOMBRA); }
    @Test public void f5AU22PlanNoDeterminadoRequiereRevision() { ContextoEvaluacionCurricularV2DTO z=contexto(baseAcreditada());z.setIdPlan(null);assertEquals(e(z).getEvaluacionCargaAcademica().getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.REQUIERE_REVISION); }
    @Test public void f5AU23TipoUdNoDeterminadoRequiereRevision() { ContextoEvaluacionCurricularV2DTO z=contexto(baseAcreditada());z.getUnidades().add(pendiente(30L,2,EstadoUnidadCurricularV2.ACREDITADA,null,null));assertEquals(e(z).getEvaluacionCargaAcademica().getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.REQUIERE_REVISION); }
    @Test public void f5AU24MismoContextoEsDeterminista() { ContextoEvaluacionCurricularV2DTO z=contexto(baseCon(reprobada(2L,2)));EvaluacionCargaAcademicaV2DTO a=e(z).getEvaluacionCargaAcademica(),b=e(z).getEvaluacionCargaAcademica();assertEquals(a.getEstadoControlAvanceAnual(),b.getEstadoControlAvanceAnual());assertEquals(a.getDetallePendientesRn611(),b.getDetallePendientesRn611()); }
    @Test public void f5AU25NoHayEfectoProductivoYExisteTraza() { ResultadoEvaluacionCurricularV2DTO r=e(contexto(baseCon(reprobada(2L,2))));assertFalse(r.getEfectosProductivosAplicados());assertTrue(r.getReglasEvaluadas().toString().contains("CTR_01"));assertTrue(r.getReglasEvaluadas().toString().contains("RN_6_1_1")); }

    private ResultadoEvaluacionCurricularV2DTO e(ContextoEvaluacionCurricularV2DTO z) { return service.evaluarSombra(z); }
    private void assertNoAplica(ResultadoEvaluacionCurricularV2DTO r) { assertFalse(r.getEvaluacionCargaAcademica().getAplicaCtr01()); assertEquals(r.getEvaluacionCargaAcademica().getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.NO_APLICA); }
    private void assertCondicionada(EvaluacionCargaAcademicaV2DTO x) { assertEquals(x.getEstadoControlAvanceAnual(),EstadoControlAvanceAnualV2.REQUIERE_EVALUACION_AVANCE_CONDICIONADO);assertTrue(x.getRequiereEvaluacionAvanceCondicionado());assertFalse(x.getAccesoOrdinarioQuinto()); }
    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO... unidades) { ContextoEvaluacionCurricularV2DTO z=new ContextoEvaluacionCurricularV2DTO();z.setIdPlan(Long.valueOf(100L));z.setVersionReglas("PE2027-V1");z.setFechaEvaluacion(new Date(0L));z.setPeriodoNormativo("2027-1");z.setSemestreObjetivo(Integer.valueOf(5));z.setActividadAcademicaComprobada(Boolean.TRUE);z.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);for(UnidadEvaluacionCurricularV2DTO u:unidades)z.getUnidades().add(u);return z; }
    private UnidadEvaluacionCurricularV2DTO[] baseAcreditada(){return new UnidadEvaluacionCurricularV2DTO[]{acreditada(1L,1),acreditada(2L,2),acreditada(3L,3),acreditada(4L,4)};}
    private UnidadEvaluacionCurricularV2DTO[] baseCon(UnidadEvaluacionCurricularV2DTO cambio){return new UnidadEvaluacionCurricularV2DTO[]{cambio.getIdPrograma().equals(Long.valueOf(1L))?cambio:acreditada(1L,1),cambio.getIdPrograma().equals(Long.valueOf(2L))?cambio:acreditada(2L,2),cambio.getIdPrograma().equals(Long.valueOf(3L))?cambio:acreditada(3L,3),cambio.getIdPrograma().equals(Long.valueOf(4L))?cambio:acreditada(4L,4)};}
    private UnidadEvaluacionCurricularV2DTO acreditada(Long id,int semestre){return pendiente(id,semestre,EstadoUnidadCurricularV2.ACREDITADA,null,"Obligatoria");}
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id,int semestre){return reprobadaTipo(id,semestre,"Obligatoria");}
    private UnidadEvaluacionCurricularV2DTO reprobadaTipo(Long id,int semestre,String tipo){UnidadEvaluacionCurricularV2DTO u=pendiente(id,semestre,EstadoUnidadCurricularV2.REPROBADA,CausaPendienteUnidadV2.REPROBACION,tipo);u.setReprobacionVigenteConocida(Boolean.TRUE);u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE);return u;}
    private UnidadEvaluacionCurricularV2DTO pendiente(Long id,int semestre,EstadoUnidadCurricularV2 estado,CausaPendienteUnidadV2 causa){return pendiente(id,semestre,estado,causa,"Obligatoria");}
    private UnidadEvaluacionCurricularV2DTO pendiente(Long id,int semestre,EstadoUnidadCurricularV2 estado,CausaPendienteUnidadV2 causa,String tipo){UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO();u.setIdPrograma(id);u.setClave("UD"+id);u.setTipoUd(tipo);u.setSemestre(Integer.valueOf(semestre));u.setEstado(estado);u.setCausa(causa);u.setOfertada(Boolean.TRUE);u.setGrupoActivoConocido(Boolean.TRUE);u.setCupoDisponibleConocido(Boolean.TRUE);u.setSeleccionableOrigen(Boolean.TRUE);return u;}
}
