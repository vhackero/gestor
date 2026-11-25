package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoActividadAcademicaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** F2B-U-01 a F2B-U-16: RN-1.2 sin activar consecuencias productivas. */
public class EvaluacionCurricularV2Fase2BTest {
    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f2BU01CeroReprobadasPreviasRegular() { assertSituacion(contexto(unidad(1L,"Obligatoria",EstadoUnidadCurricularV2.ACREDITADA)),"REGULAR",0); }
    @Test public void f2BU02ObligatoriaReprobadaPreviaIrregular() { assertSituacion(contexto(reprobada(2L,"Obligatoria")),"IRREGULAR",1); }
    @Test public void f2BU03OptativaReprobadaPreviaIrregular() { assertSituacion(contexto(reprobada(3L,"Optativa")),"IRREGULAR",1); }
    @Test public void f2BU04ElectivaReprobadaPreviaIrregular() { assertSituacion(contexto(reprobada(4L,"Electiva")),"IRREGULAR",1); }
    @Test public void f2BU05TresReprobadasIrregular() { assertSituacion(contexto(reprobada(5L,"Obligatoria"),reprobada(6L,"Optativa"),reprobada(7L,"Electiva")),"IRREGULAR",3); }
    @Test public void f2BU06CuatroReprobadasRegistranCondicionSinActivarRn64() { ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(contexto(reprobada(8L,"Obligatoria"),reprobada(9L,"Obligatoria"),reprobada(10L,"Optativa"),reprobada(11L,"Electiva"))); assertEquals(r.getSituacionAcademicaCalculada(),"IRREGULAR"); assertFalse(r.getEfectosProductivosAplicados()); }
    @Test public void f2BU07BajaParcialNoGeneraIrregularidad() { assertSituacion(contexto(unidad(12L,"Obligatoria",EstadoUnidadCurricularV2.BAJA_PARCIAL)),"REGULAR",0); }
    @Test public void f2BU08NoCursadaNoGeneraIrregularidad() { assertSituacion(contexto(unidad(13L,"Optativa",EstadoUnidadCurricularV2.NO_CURSADA)),"REGULAR",0); }
    @Test public void f2BU09EnCursoNoGeneraIrregularidad() { assertSituacion(contexto(unidad(14L,"Obligatoria",EstadoUnidadCurricularV2.EN_CURSO)),"REGULAR",0); }
    @Test public void f2BU10NoDeterminadoNoSeConvierteEnReprobada() { ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(contexto(unidad(15L,"Obligatoria",EstadoUnidadCurricularV2.NO_DETERMINADO))); assertEquals(r.getSituacionAcademicaCalculada(),"NO_DETERMINADA"); assertEquals(r.getCantidadReprobadasVigentes(),Integer.valueOf(0)); }
    @Test public void f2BU11ReprobadaPosteriormenteAcreditadaNoPermaneceVigente() { UnidadEvaluacionCurricularV2DTO r=reprobada(16L,"Obligatoria"), a=unidad(16L,"Obligatoria",EstadoUnidadCurricularV2.ACREDITADA); r.setIntentos(Integer.valueOf(2)); a.getReferenciasHistoricas().add("INTENTO_ACREDITADO_POSTERIOR"); ResultadoEvaluacionCurricularV2DTO x=service.evaluarSombra(contexto(r,a)); assertEquals(x.getSituacionAcademicaCalculada(),"REGULAR"); assertTrue(x.getDecisiones().get(0).getReferenciasFuente().contains("INTENTO_ACREDITADO_POSTERIOR")); }
    @Test public void f2BU12DuplicadaBloquesNoDuplicaConteo() { UnidadEvaluacionCurricularV2DTO a=reprobada(17L,"Optativa"), b=reprobada(17L,"Optativa"); a.setBloque("1"); b.setBloque("2"); assertSituacion(contexto(a,b),"IRREGULAR",1); }
    @Test public void f2BU13DosIntentosReprobadosMismaUdCuentanUna() { UnidadEvaluacionCurricularV2DTO a=reprobada(18L,"Obligatoria"), b=reprobada(18L,"Obligatoria"); a.setIntentos(Integer.valueOf(1)); b.setIntentos(Integer.valueOf(2)); ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(contexto(a,b)); assertEquals(r.getCantidadReprobadasVigentes(),Integer.valueOf(1)); assertTrue(r.getDecisiones().get(0).getReferenciasFuente().contains("DUPLICADO_CURRICULAR:ID:18")); }
    @Test public void f2BU14IrregularConAvanceCondicionadoSigueIrregular() { ContextoEvaluacionCurricularV2DTO c=contexto(reprobada(19L,"Obligatoria")); c.setAvanceCondicionado(Boolean.TRUE); ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(c); assertEquals(r.getSituacionAcademicaCalculada(),"IRREGULAR"); assertTrue(r.getAvanceCondicionado()); }
    @Test public void f2BU15BajaTemporalSinReprobadasConservaRegularidad() { ContextoEvaluacionCurricularV2DTO c=contexto(); c.setBajaTemporalTotalRegistrada(Boolean.TRUE); c.setPeriodosBajaTemporalAutorizados(Integer.valueOf(2)); c.setPeriodosBajaTemporalTranscurridos(Integer.valueOf(1)); ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(c); assertEquals(r.getSituacionAcademicaCalculada(),"REGULAR"); assertEquals(r.getEstadoActividadAcademica(),EstadoActividadAcademicaV2.BAJA_TEMPORAL_VIGENTE); }
    @Test public void f2BU16ReprobacionNoVigenteSeDescartaSinInferencia() { UnidadEvaluacionCurricularV2DTO u=reprobada(20L,"Obligatoria"); u.setReprobacionVigenteConocida(Boolean.FALSE); ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(contexto(u)); assertEquals(r.getSituacionAcademicaCalculada(),"REGULAR"); assertEquals(r.getDatosDescartadosSituacionAcademica().size(),1); assertTrue(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_1_2)); }

    private void assertSituacion(ContextoEvaluacionCurricularV2DTO c,String situacion,int cantidad) { ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(c); assertEquals(r.getSituacionAcademicaCalculada(),situacion); assertEquals(r.getCantidadReprobadasVigentes(),Integer.valueOf(cantidad)); assertEquals(r.getSituacionAcademica(),situacion); }
    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO... unidades) { ContextoEvaluacionCurricularV2DTO c=new ContextoEvaluacionCurricularV2DTO(); c.setVersionReglas("PE2027-V1"); c.setFechaEvaluacion(new java.util.Date(0L)); for(UnidadEvaluacionCurricularV2DTO u:unidades)c.getUnidades().add(u); return c; }
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id,String tipo) { UnidadEvaluacionCurricularV2DTO u=unidad(id,tipo,EstadoUnidadCurricularV2.REPROBADA); u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setReprobacionVigenteConocida(Boolean.TRUE); u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); return u; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id,String tipo,EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD"+id); u.setTipoUd(tipo); u.setEstado(estado); if(estado==EstadoUnidadCurricularV2.BAJA_PARCIAL)u.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL); if(estado==EstadoUnidadCurricularV2.NO_CURSADA)u.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO); u.setOfertada(Boolean.FALSE); return u; }
}
