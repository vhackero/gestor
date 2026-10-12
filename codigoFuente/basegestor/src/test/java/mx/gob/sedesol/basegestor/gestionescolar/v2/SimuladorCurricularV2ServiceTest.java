package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ContextoEvaluacionCurricularV2Adapter;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.EvaluacionCurricularV2Service;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.SimuladorCurricularV2ServiceImpl;

public class SimuladorCurricularV2ServiceTest {
 @Test public void f12A1U01ConsumeResultadoV2YNoMutaFuente() throws Exception {
  AsistenteInscripcionContextoDTO fuente=fuente(); ResultadoEvaluacionCurricularV2DTO v2=resultado(false);
  SimuladorCurricularV2ServiceImpl s=servicio(fuente,v2);
  mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO r=s.simularSombra(9L,Arrays.asList(7L));
  assertSame(r.getEvaluacionCurricularV2(),v2); assertFalse(r.getValida()); assertEquals(r.getMotivos().size(),1);
  assertTrue(fuente.getUnidades().get(0).getOfertada()); assertTrue(fuente.getUnidades().get(0).getSeleccionable());
 }
 @Test public void f12A1U02MismoContextoEsDeterministaYSinPersistencia() throws Exception {
  AsistenteInscripcionContextoDTO fuente=fuente(); ResultadoEvaluacionCurricularV2DTO v2=resultado(true);
  SimuladorCurricularV2ServiceImpl s=servicio(fuente,v2);
  assertEquals(s.simularSombra(9L,Arrays.asList(7L)).getMensajeGeneral(),s.simularSombra(9L,Arrays.asList(7L)).getMensajeGeneral());
  assertFalse(v2.getEfectosProductivosAplicados());
 }
 @Test public void f12A1U03NoCambiaEstadoNoReprobatorioDelResultadoV2() throws Exception {
  ResultadoEvaluacionCurricularV2DTO v2=resultado(true); v2.setCantidadReprobadasVigentes(0);
  assertEquals(servicio(fuente(),v2).simularSombra(9L,Arrays.asList(7L)).getEvaluacionCurricularV2().getCantidadReprobadasVigentes(),Integer.valueOf(0));
 }
 private SimuladorCurricularV2ServiceImpl servicio(AsistenteInscripcionContextoDTO f,ResultadoEvaluacionCurricularV2DTO r)throws Exception{
  AsistenteInscripcionService a=Mockito.mock(AsistenteInscripcionService.class); Mockito.when(a.obtenerContextoAsistido(9L)).thenReturn(f);
  ContextoEvaluacionCurricularV2Adapter ad=Mockito.mock(ContextoEvaluacionCurricularV2Adapter.class); Mockito.when(ad.construirDesdeAsistente(f)).thenReturn(new ContextoEvaluacionCurricularV2DTO());
  EvaluacionCurricularV2Service e=Mockito.mock(EvaluacionCurricularV2Service.class); Mockito.when(e.evaluarSombra(Mockito.any(ContextoEvaluacionCurricularV2DTO.class))).thenReturn(r);
  SimuladorCurricularV2ServiceImpl s=new SimuladorCurricularV2ServiceImpl(); inyectar(s,"asistenteInscripcionService",a);inyectar(s,"adaptador",ad);inyectar(s,"evaluador",e);return s;
 }
 private AsistenteInscripcionContextoDTO fuente(){InscripcionPersonaDTO p=new InscripcionPersonaDTO();p.setIdPersona(9L);InscripcionContextoDTO b=new InscripcionContextoDTO();b.setInscripcionPersona(p);UnidadDecisionInscripcionDTO u=new UnidadDecisionInscripcionDTO();u.setUdId(7L);u.setOfertada(true);u.setSeleccionable(true);u.setCreditos(5);AsistenteInscripcionContextoDTO a=new AsistenteInscripcionContextoDTO();a.setContextoBase(b);a.getUnidades().add(u);return a;}
 private ResultadoEvaluacionCurricularV2DTO resultado(boolean habilitada){ResultadoEvaluacionCurricularV2DTO r=new ResultadoEvaluacionCurricularV2DTO();DecisionUnidadCurricularV2DTO d=new DecisionUnidadCurricularV2DTO();d.setIdPrograma(7L);d.setHabilitada(habilitada);r.getDecisiones().add(d);return r;}
 private void inyectar(Object o,String n,Object v)throws Exception{Field f=o.getClass().getDeclaredField(n);f.setAccessible(true);f.set(o,v);}
}
