package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
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

/** F2C-U-01 a F2C-U-16: RN-1.3 reutiliza RN-1.2 y no muta estado vigente. */
public class EvaluacionCurricularV2Fase2CTest {
    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f2CU01UnaReprobadaVigenteNoRecupera() { ResultadoEvaluacionCurricularV2DTO r=evaluar(reprobada(1L)); assertFalse(r.getRecuperacionRegularidadProcede()); assertTrue(r.getUdsReprobadasImpedimentoRecuperacion().contains(1L)); }
    @Test public void f2CU02TresReprobadasNoRecupera() { ResultadoEvaluacionCurricularV2DTO r=evaluar(reprobada(2L),reprobada(3L),reprobada(4L)); assertEquals(r.getResultadoRecuperacionRegularidad(),"NO_PROCEDE"); }
    @Test public void f2CU03UltimaReprobadaAcreditadaEsCandidata() { UnidadEvaluacionCurricularV2DTO r=reprobada(5L), a=unidad(5L,EstadoUnidadCurricularV2.ACREDITADA); r.setIntentos(Integer.valueOf(2)); ResultadoEvaluacionCurricularV2DTO x=evaluar(r,a); assertTrue(x.getRecuperacionRegularidadProcede()); assertEquals(x.getCantidadReprobadasVigentes(),Integer.valueOf(0)); }
    @Test public void f2CU04BajaParcialNoImpidePorSiSola() { ResultadoEvaluacionCurricularV2DTO r=evaluar(unidad(6L,EstadoUnidadCurricularV2.BAJA_PARCIAL)); assertTrue(r.getRecuperacionRegularidadProcede()); assertTrue(r.getPendientesNoReprobatoriosRecuperacion().contains(6L)); }
    @Test public void f2CU05NoCursadaNoImpidePorSiSola() { ResultadoEvaluacionCurricularV2DTO r=evaluar(unidad(7L,EstadoUnidadCurricularV2.NO_CURSADA)); assertTrue(r.getRecuperacionRegularidadProcede()); }
    @Test public void f2CU06EnCursoNoEsReprobada() { ResultadoEvaluacionCurricularV2DTO r=evaluar(unidad(8L,EstadoUnidadCurricularV2.EN_CURSO)); assertTrue(r.getRecuperacionRegularidadProcede()); assertEquals(r.getCantidadReprobadasVigentes(),Integer.valueOf(0)); }
    @Test public void f2CU07AcreditacionPosteriorConservaIntentos() { UnidadEvaluacionCurricularV2DTO r=reprobada(9L), a=unidad(9L,EstadoUnidadCurricularV2.ACREDITADA); r.setIntentos(Integer.valueOf(2)); ResultadoEvaluacionCurricularV2DTO x=evaluar(r,a); assertTrue(x.getRecuperacionRegularidadProcede()); assertTrue(x.getDecisiones().get(0).getReferenciasFuente().contains("DUPLICADO_CURRICULAR:ID:9")); }
    @Test public void f2CU08DuplicadosNoImpidenDoble() { UnidadEvaluacionCurricularV2DTO a=reprobada(10L), b=reprobada(10L); ResultadoEvaluacionCurricularV2DTO r=evaluar(a,b); assertFalse(r.getRecuperacionRegularidadProcede()); assertEquals(r.getUdsReprobadasImpedimentoRecuperacion().size(),1); }
    @Test public void f2CU09SeriacionPendienteImpide() { UnidadEvaluacionCurricularV2DTO u=unidad(11L,EstadoUnidadCurricularV2.NO_CURSADA); u.setSeriacionConocida(Boolean.TRUE); ResultadoEvaluacionCurricularV2DTO r=evaluar(u); assertFalse(r.getRecuperacionRegularidadProcede()); assertTrue(r.getCondicionesRecuperacionNoCumplidas().contains("SERIACION_PENDIENTE")); }
    @Test public void f2CU10CuatroReprobadasObservadasNoRecuperaSinActivarRn64() { ResultadoEvaluacionCurricularV2DTO r=evaluar(reprobada(12L),reprobada(13L),reprobada(14L),reprobada(15L)); assertFalse(r.getRecuperacionRegularidadProcede()); assertFalse(r.getEfectosProductivosAplicados()); }
    @Test public void f2CU11AvanceCondicionadoConReprobadaNoRecupera() { ContextoEvaluacionCurricularV2DTO c=valido(reprobada(16L)); c.setAvanceCondicionado(Boolean.TRUE); assertFalse(service.evaluarSombra(c).getRecuperacionRegularidadProcede()); }
    @Test public void f2CU12AvanceCondicionadoSinReprobadasNoCausaRegularidad() { ContextoEvaluacionCurricularV2DTO c=valido(); c.setAvanceCondicionado(Boolean.TRUE); ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(c); assertTrue(r.getRecuperacionRegularidadProcede()); assertTrue(r.getAvanceCondicionado()); }
    @Test public void f2CU13BajaTemporalMantieneActividadSeparada() { ContextoEvaluacionCurricularV2DTO c=valido(); c.setBajaTemporalTotalRegistrada(Boolean.TRUE); c.setPeriodosBajaTemporalAutorizados(Integer.valueOf(2)); c.setPeriodosBajaTemporalTranscurridos(Integer.valueOf(1)); ResultadoEvaluacionCurricularV2DTO r=service.evaluarSombra(c); assertTrue(r.getRecuperacionRegularidadProcede()); assertEquals(r.getEstadoActividadAcademica(),EstadoActividadAcademicaV2.BAJA_TEMPORAL_VIGENTE); }
    @Test public void f2CU14DatosInsuficientesNoInfierenRecuperacion() { ResultadoEvaluacionCurricularV2DTO r=evaluar(unidad(17L,EstadoUnidadCurricularV2.NO_DETERMINADO)); assertNull(r.getRecuperacionRegularidadProcede()); assertEquals(r.getResultadoRecuperacionRegularidad(),"REQUIERE_REVISION"); }
    @Test public void f2CU15MismoContextoMismoResultado() { ContextoEvaluacionCurricularV2DTO c=valido(unidad(18L,EstadoUnidadCurricularV2.BAJA_PARCIAL)); ResultadoEvaluacionCurricularV2DTO a=service.evaluarSombra(c),b=service.evaluarSombra(c); assertEquals(a.getResultadoRecuperacionRegularidad(),b.getResultadoRecuperacionRegularidad()); }
    @Test public void f2CU16NoHayCambioProductivo() { ResultadoEvaluacionCurricularV2DTO r=evaluar(); assertFalse(r.getEfectosProductivosAplicados()); assertTrue(r.getReglasEvaluadas().contains(CodigoReglaCurricularV2.RN_1_3)); }

    private ResultadoEvaluacionCurricularV2DTO evaluar(UnidadEvaluacionCurricularV2DTO... unidades) { return service.evaluarSombra(valido(unidades)); }
    private ContextoEvaluacionCurricularV2DTO valido(UnidadEvaluacionCurricularV2DTO... unidades) { ContextoEvaluacionCurricularV2DTO c=new ContextoEvaluacionCurricularV2DTO(); c.setVersionReglas("PE2027-V1"); c.setFechaEvaluacion(new java.util.Date(0L)); c.setActividadAcademicaComprobada(Boolean.TRUE); c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE); for(UnidadEvaluacionCurricularV2DTO u:unidades)c.getUnidades().add(u); return c; }
    private UnidadEvaluacionCurricularV2DTO reprobada(Long id) { UnidadEvaluacionCurricularV2DTO u=unidad(id,EstadoUnidadCurricularV2.REPROBADA); u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setReprobacionVigenteConocida(Boolean.TRUE); u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE); return u; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id,EstadoUnidadCurricularV2 estado) { UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id);u.setClave("UD"+id);u.setTipoUd("Obligatoria");u.setEstado(estado);u.setOfertada(Boolean.FALSE);if(estado==EstadoUnidadCurricularV2.BAJA_PARCIAL)u.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL);if(estado==EstadoUnidadCurricularV2.NO_CURSADA)u.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO);return u; }
}
