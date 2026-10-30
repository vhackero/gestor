package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIntentoAcreditacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoMejoraPromedioV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionMejoraPromedioUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.IntentoAcreditacionUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** F2E-U-01 a F2E-U-21: RN-1.5 conserva historia, pero prohíbe recursar una UD acreditada sólo por promedio. */
public class EvaluacionCurricularV2Fase2ETest {
    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f2EU01NoAcreditadaNoEsElegible() { UnidadEvaluacionCurricularV2DTO u=ud(1L,false);assertEstado(evaluar(contexto(0,u)),1L,EstadoMejoraPromedioV2.NO_ELEGIBLE); }
    @Test public void f2EU02AcreditadaNoSeReinscribeSoloParaMejorarPromedio() { assertEstado(evaluar(contexto(0,ud(2L,true))),2L,EstadoMejoraPromedioV2.NO_ELEGIBLE); }
    @Test public void f2EU03CalificacionInferiorConservaAnterior() { UnidadEvaluacionCurricularV2DTO u=ud(3L,true);u.getIntentosAcreditacion().add(intento("A",8.7,false));u.getIntentosAcreditacion().add(intento("M",7.9,true));assertEquals(buscar(evaluar(contexto(0,u)),3L).getCalificacionFavorable(),Double.valueOf(8.7)); }
    @Test public void f2EU04CalificacionSuperiorUsaNueva() { UnidadEvaluacionCurricularV2DTO u=ud(4L,true);u.getIntentosAcreditacion().add(intento("A",8.7,false));u.getIntentosAcreditacion().add(intento("M",9.5,true));assertEquals(buscar(evaluar(contexto(0,u)),4L).getCalificacionFavorable(),Double.valueOf(9.5)); }
    @Test public void f2EU05AmbasCalificacionesSeConservanComoEvidencia() { UnidadEvaluacionCurricularV2DTO u=ud(5L,true);u.getIntentosAcreditacion().add(intento("A",8.0,false));u.getIntentosAcreditacion().add(intento("M",9.0,true));assertEquals(buscar(evaluar(contexto(0,u)),5L).getEvidencias().size(),2); }
    @Test public void f2EU06PrimeraOportunidadDisponible() { ResultadoEvaluacionCurricularV2DTO r=evaluar(contexto(0,ud(6L,true)));assertEquals(r.getOportunidadesMejoraDisponibles(),Integer.valueOf(3)); }
    @Test public void f2EU07SegundaOportunidadGlobalDisponible() { ResultadoEvaluacionCurricularV2DTO r=evaluar(contexto(1,ud(7L,true)));assertEquals(r.getOportunidadesMejoraDisponibles(),Integer.valueOf(2)); }
    @Test public void f2EU08OportunidadesHistoricasNoHabilitanMejora() { ResultadoEvaluacionCurricularV2DTO r=evaluar(contexto(2,ud(8L,true)));assertEquals(buscar(r,8L).getEstado(),EstadoMejoraPromedioV2.NO_ELEGIBLE);assertEquals(r.getOportunidadesMejoraDisponibles(),Integer.valueOf(1)); }
    @Test public void f2EU09TresMejorasHistoricasNoCambianProhibicion() { assertEstado(evaluar(contexto(3,ud(9L,true))),9L,EstadoMejoraPromedioV2.NO_ELEGIBLE); }
    @Test public void f2EU10HistorialGlobalNoHabilitaUdAcreditada() { assertEstado(evaluar(contexto(3,ud(10L,true),ud(11L,true),ud(12L,true))),10L,EstadoMejoraPromedioV2.NO_ELEGIBLE); }
    @Test public void f2EU11IntentosRn14NoAutorizanMejoraRn15() { UnidadEvaluacionCurricularV2DTO u=ud(13L,true);u.getIntentosAcreditacion().add(intento("I1",null,false));u.getIntentosAcreditacion().add(intento("I2",null,false));ResultadoEvaluacionCurricularV2DTO r=evaluar(contexto(0,u));assertEquals(r.getOportunidadesMejoraUtilizadas(),Integer.valueOf(0));assertEquals(buscar(r,13L).getEstado(),EstadoMejoraPromedioV2.NO_ELEGIBLE); }
    @Test public void f2EU12RecursamientoPorReprobacionNoEsMejora() { UnidadEvaluacionCurricularV2DTO u=ud(14L,false);u.getIntentosAcreditacion().add(intento("R",null,false));assertEstado(evaluar(contexto(0,u)),14L,EstadoMejoraPromedioV2.NO_ELEGIBLE); }
    @Test public void f2EU13InactividadNoHabilitaMejora() { ContextoEvaluacionCurricularV2DTO c=contexto(0,ud(15L,true));c.setBajaTemporalTotalRegistrada(Boolean.TRUE);c.setPeriodosBajaTemporalAutorizados(Integer.valueOf(1));c.setPeriodosBajaTemporalTranscurridos(Integer.valueOf(1));assertEstado(evaluar(c),15L,EstadoMejoraPromedioV2.NO_ELEGIBLE); }
    @Test public void f2EU14EgresoIniciadoNoHabilitaMejora() { ContextoEvaluacionCurricularV2DTO c=contexto(0,ud(16L,true));c.setDictaminacionEgresoIniciada(Boolean.TRUE);assertEstado(evaluar(c),16L,EstadoMejoraPromedioV2.NO_ELEGIBLE); }
    @Test public void f2EU15EgresoNoDeterminadoNoAnulaProhibicionExpresa() { ContextoEvaluacionCurricularV2DTO c=contexto(0,ud(17L,true));c.setDictaminacionEgresoConocida(Boolean.FALSE);assertEstado(evaluar(c),17L,EstadoMejoraPromedioV2.NO_ELEGIBLE); }
    @Test public void f2EU16AcreditadaNoOfertadaNoMaterializable() { UnidadEvaluacionCurricularV2DTO u=ud(18L,true);u.setOfertada(Boolean.FALSE);assertEstado(evaluar(contexto(0,u)),18L,EstadoMejoraPromedioV2.NO_ELEGIBLE); }
    @Test public void f2EU17SinCupoNoConvierteProhibicionEnElegibilidad() { UnidadEvaluacionCurricularV2DTO u=ud(19L,true);u.setCupoDisponibleConocido(Boolean.FALSE);assertEstado(evaluar(contexto(0,u)),19L,EstadoMejoraPromedioV2.NO_ELEGIBLE); }
    @Test public void f2EU18MotivoExplicitaProhibicionDeMejora() { EvaluacionMejoraPromedioUnidadV2DTO e=buscar(evaluar(contexto(0,ud(20L,true))),20L);assertTrue(e.getMotivos().contains("RN15_UD_ACREDITADA_NO_REINSCRIBIBLE_POR_MEJORA_PROMEDIO")); }
    @Test public void f2EU19DuplicadosHistoricosNoDuplicanMejoras() { UnidadEvaluacionCurricularV2DTO u=ud(21L,true);u.getIntentosAcreditacion().add(intento("M",8.5,true));u.getIntentosAcreditacion().add(intento("M",8.5,true));ContextoEvaluacionCurricularV2DTO c=contexto(null,u);ResultadoEvaluacionCurricularV2DTO r=evaluar(c);assertEquals(r.getOportunidadesMejoraUtilizadas(),Integer.valueOf(1)); }
    @Test public void f2EU20MismoContextoMismoResultado() { ContextoEvaluacionCurricularV2DTO c=contexto(1,ud(22L,true));assertEquals(buscar(evaluar(c),22L).getEstado(),buscar(evaluar(c),22L).getEstado()); }
    @Test public void f2EU21NoHayCambioProductivo() { assertFalse(evaluar(contexto(0,ud(23L,true))).getEfectosProductivosAplicados()); }

    private ResultadoEvaluacionCurricularV2DTO evaluar(ContextoEvaluacionCurricularV2DTO c){return service.evaluarSombra(c);} private void assertEstado(ResultadoEvaluacionCurricularV2DTO r,Long id,EstadoMejoraPromedioV2 e){assertEquals(buscar(r,id).getEstado(),e);}
    private ContextoEvaluacionCurricularV2DTO contexto(Integer mejoras,UnidadEvaluacionCurricularV2DTO...u){ContextoEvaluacionCurricularV2DTO c=new ContextoEvaluacionCurricularV2DTO();c.setVersionReglas("PE2027-V1");c.setFechaEvaluacion(new java.util.Date(0L));c.setActividadAcademicaComprobada(Boolean.TRUE);c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);c.setDictaminacionEgresoConocida(Boolean.TRUE);c.setDictaminacionEgresoIniciada(Boolean.FALSE);c.setOportunidadesMejoraUtilizadas(mejoras);for(UnidadEvaluacionCurricularV2DTO x:u)c.getUnidades().add(x);return c;}
    private UnidadEvaluacionCurricularV2DTO ud(Long id,boolean acreditada){UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO();u.setIdPrograma(id);u.setClave("UD"+id);u.setEstado(acreditada?EstadoUnidadCurricularV2.ACREDITADA:EstadoUnidadCurricularV2.REPROBADA);u.setOfertada(Boolean.TRUE);u.setGrupoActivoConocido(Boolean.TRUE);u.setCupoDisponibleConocido(Boolean.TRUE);return u;}
    private IntentoAcreditacionUnidadV2DTO intento(String id,Double calificacion,boolean mejora){IntentoAcreditacionUnidadV2DTO i=new IntentoAcreditacionUnidadV2DTO();i.setIdEjecucionTecnico(id);i.setEstado(EstadoIntentoAcreditacionV2.ACREDITADO);i.setCalificacionFinal(calificacion);i.setRecursamientoMejora(Boolean.valueOf(mejora));return i;}
    private EvaluacionMejoraPromedioUnidadV2DTO buscar(ResultadoEvaluacionCurricularV2DTO r,Long id){for(EvaluacionMejoraPromedioUnidadV2DTO e:r.getEvaluacionesMejoraPromedio())if(id.equals(e.getIdPrograma()))return e;throw new AssertionError("Sin mejora "+id);}
}
