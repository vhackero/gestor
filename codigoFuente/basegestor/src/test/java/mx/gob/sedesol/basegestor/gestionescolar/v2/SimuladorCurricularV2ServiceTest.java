package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaMaximaCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
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
  assertSame(r.getEvaluacionCurricularV2(),v2); assertFalse(r.getValida()); assertTrue(r.getMotivos().size() >= 1);
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
 @Test public void f12A1B01RegularRespetaMaximoOchoDelResultadoV2() throws Exception {
  assertTrue(servicio(fuenteConUnidades(8),resultadoConCarga(true,8,"REGULAR")).simularSombra(9L,seleccion(8)).getValida());
 }
 @Test public void f12A1B02RegularSobreMaximoSeRestringePorRn42() throws Exception {
  mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO r=servicio(fuenteConUnidades(9),resultadoConCarga(true,8,"REGULAR")).simularSombra(9L,seleccion(9));
  assertFalse(r.getValida()); assertEquals(r.getMotivos().get(0).getCodigo(),"RN_4_2");
 }
 @Test public void f12A1B03IrregularSeisPermitidasSieteYOchoRestringidas() throws Exception {
  assertTrue(servicio(fuenteConUnidades(8),resultadoConCarga(true,6,"IRREGULAR")).simularSombra(9L,seleccion(6)).getValida());
  assertFalse(servicio(fuenteConUnidades(8),resultadoConCarga(true,6,"IRREGULAR")).simularSombra(9L,seleccion(7)).getValida());
  assertFalse(servicio(fuenteConUnidades(8),resultadoConCarga(true,6,"IRREGULAR")).simularSombra(9L,seleccion(8)).getValida());
 }
 @Test public void f12A1B04RestriccionV2PorSeriacionConservaCodigoDelMotor() throws Exception {
  ResultadoEvaluacionCurricularV2DTO v2=resultadoConCarga(false,8,"REGULAR"); v2.getDecisiones().get(6).getMotivos().add(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2.SERIACION_DIRECTA);
  mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO r=servicio(fuente(),v2).simularSombra(9L,Arrays.asList(7L));
  assertFalse(r.getValida()); assertEquals(r.getMotivos().get(0).getCodigo(),"SERIACION_DIRECTA");
 }
 @Test public void f12A1B05CargaNoDeterminadaNoInfiereMaximo() throws Exception {
  ResultadoEvaluacionCurricularV2DTO v2=resultado(true); v2.setSituacionAcademicaCalculada("NO_DETERMINADA");
  mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO r=servicio(fuente(),v2).simularSombra(9L,Arrays.asList(7L));
  assertFalse(r.getValida()); assertEquals(r.getMotivos().get(0).getCodigo(),"RN_4_2");
 }
 @Test public void f12A1B07CargaMinimaV2ImpideSeleccionVacia() throws Exception {
  ResultadoEvaluacionCurricularV2DTO v2=resultadoConCarga(true,8,"REGULAR");EvaluacionCargaAcademicaV2DTO carga=new EvaluacionCargaAcademicaV2DTO();carga.setCargaMinimaNormativa(Integer.valueOf(3));v2.setEvaluacionCargaAcademica(carga);
  mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO r=servicio(fuente(),v2).simularSombra(9L,java.util.Collections.<Long>emptyList());
  assertFalse(r.getValida());assertEquals(r.getMotivos().get(0).getCodigo(),"RN_4_1");
 }
 @Test public void f12A1B08ComplementosEvitanOptativaRepetidaYDosEnMismoBloque() throws Exception {
  AsistenteInscripcionContextoDTO f=fuenteConUnidades(3);UnidadDecisionInscripcionDTO a=f.getUnidades().get(0);a.setTipoUd("OPTATIVA");a.setClave("OPT-A");a.setSemestre(1);a.setBloque(1);
  UnidadDecisionInscripcionDTO b=f.getUnidades().get(1);b.setTipoUd("OPTATIVA");b.setClave("OPT-A");b.setSemestre(1);b.setBloque(2);
  UnidadDecisionInscripcionDTO c=f.getUnidades().get(2);c.setTipoUd("OPTATIVA");c.setClave("OPT-C");c.setSemestre(1);c.setBloque(1);
  mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO r=servicio(f,resultadoConCarga(true,8,"REGULAR")).simularSombra(9L,seleccion(3));
  assertFalse(r.getValida());assertTrue(contieneMotivo(r,"OPTATIVA_MISMA_CLAVE"));assertTrue(contieneMotivo(r,"OPTATIVA_DUPLICADA_EN_BLOQUE"));
 }
 @Test public void f12A1B09ComplementosConservanMaximoElectivasYPrioridad() throws Exception {
  AsistenteInscripcionContextoDTO f=fuenteConUnidades(2);f.getUnidades().get(0).setTipoUd("ELECTIVA");f.getUnidades().get(1).setTipoUd("ELECTIVA");f.getUnidades().get(1).setPrioritaria(Boolean.TRUE);
  EstadoAcademicoDTO estado=new EstadoAcademicoDTO();estado.setCantidadMaximaMateriasElectivas(Long.valueOf(1));f.getContextoBase().setEstadoAcademico(estado);
  mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO r=servicio(f,resultadoConCarga(true,8,"REGULAR")).simularSombra(9L,Arrays.asList(1L));
  assertTrue(r.getValida());assertEquals(r.getAdvertencias().size(),1);r=servicio(f,resultadoConCarga(true,8,"REGULAR")).simularSombra(9L,seleccion(2));
  assertFalse(r.getValida());assertTrue(contieneMotivo(r,"ELECTIVA_MAXIMA_EXCEDIDA"));
 }
 @DataProvider(name="motivosV2") public Object[][] motivosV2(){return new Object[][]{{CodigoMotivoCurricularV2.REPROBACION_ACUMULADA},{CodigoMotivoCurricularV2.SERIACION_INDIRECTA},{CodigoMotivoCurricularV2.PENDIENTE_NO_CURSADA},{CodigoMotivoCurricularV2.BAJA_PARCIAL},{CodigoMotivoCurricularV2.SIN_OFERTA},{CodigoMotivoCurricularV2.REQUISITO_CURRICULAR_PENDIENTE}};}
 @Test(dataProvider="motivosV2") public void f12A1B06PropagaRestriccionesDelMotorSinReinterpretarlas(CodigoMotivoCurricularV2 motivo) throws Exception {
  ResultadoEvaluacionCurricularV2DTO v2=resultadoConCarga(false,8,"REGULAR");v2.getDecisiones().get(6).getMotivos().add(motivo);
  mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO r=servicio(fuente(),v2).simularSombra(9L,Arrays.asList(7L));
  assertFalse(r.getValida());assertEquals(r.getMotivos().get(0).getCodigo(),motivo.name());
 }
 private SimuladorCurricularV2ServiceImpl servicio(AsistenteInscripcionContextoDTO f,ResultadoEvaluacionCurricularV2DTO r)throws Exception{
  AsistenteInscripcionService a=Mockito.mock(AsistenteInscripcionService.class); Mockito.when(a.obtenerContextoAsistido(9L)).thenReturn(f);
  ContextoEvaluacionCurricularV2Adapter ad=Mockito.mock(ContextoEvaluacionCurricularV2Adapter.class); Mockito.when(ad.construirDesdeAsistente(f)).thenReturn(new ContextoEvaluacionCurricularV2DTO());
  EvaluacionCurricularV2Service e=Mockito.mock(EvaluacionCurricularV2Service.class); Mockito.when(e.evaluarSombra(Mockito.any(ContextoEvaluacionCurricularV2DTO.class))).thenReturn(r);
  SimuladorCurricularV2ServiceImpl s=new SimuladorCurricularV2ServiceImpl(); inyectar(s,"asistenteInscripcionService",a);inyectar(s,"adaptador",ad);inyectar(s,"evaluador",e);return s;
 }
 private AsistenteInscripcionContextoDTO fuente(){InscripcionPersonaDTO p=new InscripcionPersonaDTO();p.setIdPersona(9L);InscripcionContextoDTO b=new InscripcionContextoDTO();b.setInscripcionPersona(p);UnidadDecisionInscripcionDTO u=new UnidadDecisionInscripcionDTO();u.setUdId(7L);u.setOfertada(true);u.setSeleccionable(true);u.setCreditos(5);AsistenteInscripcionContextoDTO a=new AsistenteInscripcionContextoDTO();a.setContextoBase(b);a.getUnidades().add(u);return a;}
 private AsistenteInscripcionContextoDTO fuenteConUnidades(int cantidad){AsistenteInscripcionContextoDTO a=fuente();a.getUnidades().clear();for(long id=1;id<=cantidad;id++){UnidadDecisionInscripcionDTO u=new UnidadDecisionInscripcionDTO();u.setUdId(Long.valueOf(id));u.setOfertada(true);u.setSeleccionable(true);u.setCreditos(5);a.getUnidades().add(u);}return a;}
 private ResultadoEvaluacionCurricularV2DTO resultado(boolean habilitada){ResultadoEvaluacionCurricularV2DTO r=new ResultadoEvaluacionCurricularV2DTO();DecisionUnidadCurricularV2DTO d=new DecisionUnidadCurricularV2DTO();d.setIdPrograma(7L);d.setHabilitada(habilitada);r.getDecisiones().add(d);return r;}
 private ResultadoEvaluacionCurricularV2DTO resultadoConCarga(boolean habilitada,int maximo,String situacion){ResultadoEvaluacionCurricularV2DTO r=resultado(habilitada);r.getDecisiones().clear();for(long id=1;id<=9;id++){DecisionUnidadCurricularV2DTO d=new DecisionUnidadCurricularV2DTO();d.setIdPrograma(Long.valueOf(id));d.setHabilitada(habilitada);r.getDecisiones().add(d);}r.setSituacionAcademicaCalculada(situacion);EvaluacionCargaMaximaCurricularV2DTO carga=new EvaluacionCargaMaximaCurricularV2DTO();carga.setCargaMaximaNormativa(Integer.valueOf(maximo));r.setEvaluacionCargaMaxima(carga);return r;}
 private List<Long> seleccion(int cantidad){List<Long> ids=new java.util.ArrayList<Long>();for(long id=1;id<=cantidad;id++)ids.add(Long.valueOf(id));return ids;}
 private boolean contieneMotivo(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO r,String codigo){for(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MotivoDecisionDTO m:r.getMotivos())if(codigo.equals(m.getCodigo()))return true;return false;}
 private void inyectar(Object o,String n,Object v)throws Exception{Field f=o.getClass().getDeclaredField(n);f.setAccessible(true);f.set(o,v);}
}
