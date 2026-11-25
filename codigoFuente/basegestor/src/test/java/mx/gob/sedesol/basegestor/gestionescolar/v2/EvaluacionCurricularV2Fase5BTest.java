package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.util.Date;
import org.testng.annotations.Test;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.*;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** F5B-U-01 a F5B-U-20: condiciones comunes RN-6.1.2, sin tipificar RN-6.1.3. */
public class EvaluacionCurricularV2Fase5BTest {
 private final EvaluacionCurricularV2ServiceImpl s=new EvaluacionCurricularV2ServiceImpl();
 @Test public void f5BU01CtrNoAplica(){ContextoEvaluacionCurricularV2DTO z=base();z.setSemestreObjetivo(3);assertEquals(x(z).getEstadoCondicionesComunesAvance(),EstadoCondicionesComunesAvanceV2.NO_APLICA);}
 @Test public void f5BU02AccesoOrdinarioNoAplica(){ContextoEvaluacionCurricularV2DTO z=base();z.getUnidades().clear();z.getUnidades().add(a(1,1));z.getUnidades().add(a(2,2));z.getUnidades().add(a(3,3));z.getUnidades().add(a(4,4));z.getUnidades().add(u5(50));assertEquals(x(z).getEstadoCondicionesComunesAvance(),EstadoCondicionesComunesAvanceV2.NO_APLICA);}
 @Test public void f5BU03Rn64NoCumpleA(){ContextoEvaluacionCurricularV2DTO z=base();z.getUnidades().add(r(2,2));z.getUnidades().add(r(3,3));z.getUnidades().add(r(4,4));z.getUnidades().add(u5(50));assertEquals(c(x(z),"A_RN64").getEstado(),EstadoCondicionAvanceV2.NO_CUMPLE);}
 @Test public void f5BU04UnaReprobadaCumpleA(){assertEquals(c(x(conCompatible()),"A_RN64").getEstado(),EstadoCondicionAvanceV2.CUMPLE);}
 @Test public void f5BU05DirectaBloqueaQuinto(){ContextoEvaluacionCurricularV2DTO z=conCompatible();z.getUnidades().get(1).setSeriacionDirectaConocida(Boolean.TRUE);EvaluacionCargaAcademicaV2DTO q=x(z);assertTrue(q.getUdsQuintoBloqueadasSeriacionDirectaRn612().contains(50L));assertEquals(c(q,"B_SERIACION_QUINTO").getEstado(),EstadoCondicionAvanceV2.NO_CUMPLE);}
 @Test public void f5BU06IndirectaBloqueaQuinto(){ContextoEvaluacionCurricularV2DTO z=conCompatible();z.getUnidades().get(1).setSeriacionIndirectaConocida(Boolean.TRUE);assertEquals(c(x(z),"B_SERIACION_QUINTO").getEstado(),EstadoCondicionAvanceV2.NO_CUMPLE);}
 @Test public void f5BU07CompatibleQuintoSeConserva(){assertTrue(x(conCompatible()).getUdsQuintoCompatiblesRn612().contains(50L));}
 @Test public void f5BU08MixtoNoColapsaUds(){ContextoEvaluacionCurricularV2DTO z=conCompatible();UnidadEvaluacionCurricularV2DTO b=u5(51);b.setSeriacionDirectaConocida(Boolean.TRUE);z.getUnidades().add(b);EvaluacionCargaAcademicaV2DTO q=x(z);assertTrue(q.getUdsQuintoCompatiblesRn612().contains(50L));assertTrue(q.getUdsQuintoBloqueadasSeriacionDirectaRn612().contains(51L));}
 @Test public void f5BU09CargaDentroMaximoCumpleC(){assertEquals(c(x(conCompatible()),"C_CARGA").getEstado(),EstadoCondicionAvanceV2.CUMPLE);}
 @Test public void f5BU10CargaExcedidaNoCumpleC(){ContextoEvaluacionCurricularV2DTO z=conCompatible();for(int i=51;i<58;i++)z.getUnidades().add(u5(i));assertEquals(c(x(z),"C_CARGA").getEstado(),EstadoCondicionAvanceV2.NO_CUMPLE);}
 @Test public void f5BU11RutaRn63CumpleD(){assertEquals(c(x(conCompatible()),"D_PRIORIDAD_PENDIENTES").getEstado(),EstadoCondicionAvanceV2.CUMPLE);}
 @Test public void f5BU12SinOfertaConservaPendiente(){ContextoEvaluacionCurricularV2DTO z=conCompatible();z.getUnidades().get(0).setOfertada(Boolean.FALSE);EvaluacionCargaAcademicaV2DTO q=x(z);assertTrue(q.getDetallePendientesRn611().toString().contains("REPROBADA"));assertTrue(q.getCausasIndisponibilidadRegularizacion().toString().contains("SIN_OFERTA"));}
 @Test public void f5BU13EstructuraCompatibleCumpleE(){assertEquals(c(x(conCompatible()),"E_ESTRUCTURA_PE").getEstado(),EstadoCondicionAvanceV2.CUMPLE);}
 @Test public void f5BU14EstructuraSinFuenteRevisionE(){ContextoEvaluacionCurricularV2DTO z=conCompatible();z.setEstructuraProgramaEducativoComprobada(null);assertEquals(c(x(z),"E_ESTRUCTURA_PE").getEstado(),EstadoCondicionAvanceV2.REQUIERE_REVISION);}
 @Test public void f5BU15CausaIdentificadaPendienteTipificacion(){assertEquals(c(x(conCompatible()),"F_CAUSA_Y_EXCEPCION").getEstado(),EstadoCondicionAvanceV2.PENDIENTE_TIPIFICACION);}
 @Test public void f5BU16SinSancionVerificableCumpleG(){assertEquals(c(x(conCompatible()),"G_SANCION").getEstado(),EstadoCondicionAvanceV2.CUMPLE);}
 @Test public void f5BU17SancionImpide(){ContextoEvaluacionCurricularV2DTO z=conCompatible();z.setSancionImpedimentoRegistrada(Boolean.TRUE);assertEquals(c(x(z),"G_SANCION").getEstado(),EstadoCondicionAvanceV2.NO_CUMPLE);}
 @Test public void f5BU18SancionDesconocidaRevision(){ContextoEvaluacionCurricularV2DTO z=conCompatible();z.setSancionImpedimentoConocida(null);assertEquals(c(x(z),"G_SANCION").getEstado(),EstadoCondicionAvanceV2.REQUIERE_REVISION);}
 @Test public void f5BU19PrecondicionesPendientesNoConcedenAvance(){ResultadoEvaluacionCurricularV2DTO r=s.evaluarSombra(conCompatible());assertEquals(r.getEvaluacionCargaAcademica().getEstadoCondicionesComunesAvance(),EstadoCondicionesComunesAvanceV2.PENDIENTE_TIPIFICACION);assertFalse(r.getAvanceCondicionado());}
 @Test public void f5BU20DeterministaSinEfectoProductivo(){ContextoEvaluacionCurricularV2DTO z=conCompatible();EvaluacionCargaAcademicaV2DTO a=x(z),b=x(z);assertEquals(a.getCondicionesComunesAvance().size(),b.getCondicionesComunesAvance().size());assertFalse(s.evaluarSombra(z).getEfectosProductivosAplicados());}
 private EvaluacionCargaAcademicaV2DTO x(ContextoEvaluacionCurricularV2DTO z){return s.evaluarSombra(z).getEvaluacionCargaAcademica();}
 private CondicionComunAvanceV2DTO c(EvaluacionCargaAcademicaV2DTO x,String k){for(CondicionComunAvanceV2DTO v:x.getCondicionesComunesAvance())if(k.equals(v.getCodigo()))return v;throw new AssertionError(k);}
 private ContextoEvaluacionCurricularV2DTO base(){ContextoEvaluacionCurricularV2DTO z=new ContextoEvaluacionCurricularV2DTO();z.setIdPlan(1L);z.setPeriodoNormativo("2027-1");z.setSemestreObjetivo(5);z.setVersionReglas("PE2027-V1");z.setFechaEvaluacion(new Date(0));z.setActividadAcademicaComprobada(Boolean.TRUE);z.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);z.setSeriacionComprobada(Boolean.TRUE);z.setEstructuraProgramaEducativoComprobada(Boolean.TRUE);z.setSancionImpedimentoConocida(Boolean.TRUE);z.setSancionImpedimentoRegistrada(Boolean.FALSE);z.getUnidades().add(r(1,1));return z;}
 private ContextoEvaluacionCurricularV2DTO conCompatible(){ContextoEvaluacionCurricularV2DTO z=base();z.getUnidades().add(u5(50));return z;}
 private UnidadEvaluacionCurricularV2DTO u5(long id){return u(id,5,EstadoUnidadCurricularV2.NO_CURSADA,CausaPendienteUnidadV2.NO_CURSAMIENTO);}
 private UnidadEvaluacionCurricularV2DTO a(long id,int sem){return u(id,sem,EstadoUnidadCurricularV2.ACREDITADA,null);}
 private UnidadEvaluacionCurricularV2DTO r(long id,int sem){UnidadEvaluacionCurricularV2DTO u=u(id,sem,EstadoUnidadCurricularV2.REPROBADA,CausaPendienteUnidadV2.REPROBACION);u.setReprobacionVigenteConocida(Boolean.TRUE);u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE);return u;}
 private UnidadEvaluacionCurricularV2DTO u(long id,int sem,EstadoUnidadCurricularV2 e,CausaPendienteUnidadV2 c){UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO();u.setIdPrograma(id);u.setClave("UD"+id);u.setTipoUd("Obligatoria");u.setSemestre(sem);u.setEstado(e);u.setCausa(c);u.setOfertada(Boolean.TRUE);u.setGrupoActivoConocido(Boolean.TRUE);u.setCupoDisponibleConocido(Boolean.TRUE);return u;}
}
