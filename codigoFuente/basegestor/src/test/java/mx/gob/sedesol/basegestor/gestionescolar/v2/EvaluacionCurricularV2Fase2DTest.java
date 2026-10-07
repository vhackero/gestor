package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIntentoAcreditacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoLimiteIntentosV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionIntentosUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.IntentoAcreditacionUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** F2D-U-01 a F2D-U-17: RN-1.4 usa oportunidades por UD, no reprobadas acumuladas. */
public class EvaluacionCurricularV2Fase2DTest {
    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f2DU01SinIntentosDisponible() { assertEstado(evaluar(ud(1L)),1L,EstadoLimiteIntentosV2.DISPONIBLE,0); }
    @Test public void f2DU02UnReprobadoDisponible() { assertEstado(evaluar(conIntentos(2L,EstadoIntentoAcreditacionV2.REPROBADO)),2L,EstadoLimiteIntentosV2.DISPONIBLE,1); }
    @Test public void f2DU03DosReprobadosUltimaOportunidad() { ResultadoEvaluacionCurricularV2DTO r=evaluar(conIntentos(3L,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO)); EvaluacionIntentosUnidadV2DTO e=buscar(r,3L); assertEquals(e.getEstadoLimite(),EstadoLimiteIntentosV2.ULTIMA_OPORTUNIDAD); assertTrue(e.getUltimaOportunidad()); }
    @Test public void f2DU04TresReprobadosLimiteAgotado() { ResultadoEvaluacionCurricularV2DTO r=evaluar(conIntentos(4L,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO)); EvaluacionIntentosUnidadV2DTO e=buscar(r,4L); assertEquals(e.getEstadoLimite(),EstadoLimiteIntentosV2.LIMITE_AGOTADO); assertTrue(e.getRequiereEscalamiento()); }
    @Test public void f2DU05CuartoRegistroRequiereRevision() { assertEstado(evaluar(conIntentos(5L,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO)),5L,EstadoLimiteIntentosV2.REQUIERE_REVISION,4); }
    @Test public void f2DU06ReprobadaYAcreditadaNoRequiereOtraOportunidad() { UnidadEvaluacionCurricularV2DTO u=conIntentos(6L,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.ACREDITADO);u.setEstado(EstadoUnidadCurricularV2.ACREDITADA);assertEstado(evaluar(u),6L,EstadoLimiteIntentosV2.ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA,1); }
    @Test public void f2DU07DosReprobadasYTerceraAcreditada() { UnidadEvaluacionCurricularV2DTO u=conIntentos(7L,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.ACREDITADO);u.setEstado(EstadoUnidadCurricularV2.ACREDITADA);assertEquals(buscar(evaluar(u),7L).getEstadoLimite(),EstadoLimiteIntentosV2.ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA); }
    @Test public void f2DU08BajaParcialNoCuentaFallo() { UnidadEvaluacionCurricularV2DTO u=conIntentos(8L,EstadoIntentoAcreditacionV2.BAJA_PARCIAL);u.setEstado(EstadoUnidadCurricularV2.BAJA_PARCIAL);assertEstado(evaluar(u),8L,EstadoLimiteIntentosV2.DISPONIBLE,0); }
    @Test public void f2DU09NoCursadaNoCuentaFallo() { assertEstado(evaluar(conIntentos(9L,EstadoIntentoAcreditacionV2.NO_CURSADA)),9L,EstadoLimiteIntentosV2.DISPONIBLE,0); }
    @Test public void f2DU10EnCursoNoCuentaFallo() { assertEstado(evaluar(conIntentos(10L,EstadoIntentoAcreditacionV2.EN_CURSO)),10L,EstadoLimiteIntentosV2.DISPONIBLE,0); }
    @Test public void f2DU11DuplicadoMismoIntentoCuentaUnaVez() { UnidadEvaluacionCurricularV2DTO u=conIntentos(11L,EstadoIntentoAcreditacionV2.REPROBADO);u.getIntentosAcreditacion().add(intento("E1",EstadoIntentoAcreditacionV2.REPROBADO));EvaluacionIntentosUnidadV2DTO e=buscar(evaluar(u),11L);assertEquals(e.getIntentosFallidos(),Integer.valueOf(1));assertEquals(e.getDuplicadosDescartados().size(),1); }
    @Test public void f2DU12MismaUdB1B2MismaEjecucionNoDuplica() { UnidadEvaluacionCurricularV2DTO a=conIntentos(12L,EstadoIntentoAcreditacionV2.REPROBADO),b=conIntentos(12L,EstadoIntentoAcreditacionV2.REPROBADO);b.getIntentosAcreditacion().get(0).setIdEjecucionTecnico("E1");assertEquals(buscar(evaluar(a,b),12L).getIntentosFallidos(),Integer.valueOf(1)); }
    @Test public void f2DU13DosUdTienenCalculoIndependiente() { ResultadoEvaluacionCurricularV2DTO r=evaluar(conIntentos(13L,EstadoIntentoAcreditacionV2.REPROBADO),conIntentos(14L,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO));assertEquals(buscar(r,13L).getEstadoLimite(),EstadoLimiteIntentosV2.DISPONIBLE);assertEquals(buscar(r,14L).getEstadoLimite(),EstadoLimiteIntentosV2.ULTIMA_OPORTUNIDAD); }
    @Test public void f2DU14TresIntentosNoInflanUdReprobadas() { ResultadoEvaluacionCurricularV2DTO r=evaluar(conIntentos(15L,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO));assertEquals(r.getCantidadReprobadasVigentes(),Integer.valueOf(1)); }
    @Test public void f2DU15ConteoLegadoAmbiguoRequiereRevision() { UnidadEvaluacionCurricularV2DTO u=ud(16L);u.setIntentos(Integer.valueOf(2));assertEquals(buscar(evaluar(u),16L).getEstadoLimite(),EstadoLimiteIntentosV2.REQUIERE_REVISION); }
    @Test public void f2DU16MismoContextoMismoResultado() { ContextoEvaluacionCurricularV2DTO c=contexto(conIntentos(17L,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO));assertEquals(buscar(service.evaluarSombra(c),17L).getEstadoLimite(),buscar(service.evaluarSombra(c),17L).getEstadoLimite()); }
    @Test public void f2DU17NoHayCambioProductivo() { ResultadoEvaluacionCurricularV2DTO r=evaluar(conIntentos(18L,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO,EstadoIntentoAcreditacionV2.REPROBADO));assertFalse(r.getEfectosProductivosAplicados()); }

    private void assertEstado(ResultadoEvaluacionCurricularV2DTO r,Long id,EstadoLimiteIntentosV2 estado,int fallidos){EvaluacionIntentosUnidadV2DTO e=buscar(r,id);assertEquals(e.getEstadoLimite(),estado);assertEquals(e.getIntentosFallidos(),Integer.valueOf(fallidos));}
    private ResultadoEvaluacionCurricularV2DTO evaluar(UnidadEvaluacionCurricularV2DTO...u){return service.evaluarSombra(contexto(u));}
    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO...u){ContextoEvaluacionCurricularV2DTO c=new ContextoEvaluacionCurricularV2DTO();c.setVersionReglas("PE2027-V1");c.setFechaEvaluacion(new java.util.Date(0L));c.setActividadAcademicaComprobada(Boolean.TRUE);c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);for(UnidadEvaluacionCurricularV2DTO x:u)c.getUnidades().add(x);return c;}
    private UnidadEvaluacionCurricularV2DTO ud(Long id){UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO();u.setIdPrograma(id);u.setClave("UD"+id);u.setEstado(EstadoUnidadCurricularV2.REPROBADA);u.setCausa(CausaPendienteUnidadV2.REPROBACION);u.setReprobacionVigenteConocida(Boolean.TRUE);u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE);return u;}
    private UnidadEvaluacionCurricularV2DTO conIntentos(Long id,EstadoIntentoAcreditacionV2...estados){UnidadEvaluacionCurricularV2DTO u=ud(id);int n=1;for(EstadoIntentoAcreditacionV2 e:estados)u.getIntentosAcreditacion().add(intento("E"+n++,e));return u;}
    private IntentoAcreditacionUnidadV2DTO intento(String id,EstadoIntentoAcreditacionV2 estado){IntentoAcreditacionUnidadV2DTO i=new IntentoAcreditacionUnidadV2DTO();i.setIdEjecucionTecnico(id);i.setEstado(estado);i.setEvidenciaFuente("EVIDENCIA:"+id);return i;}
    private EvaluacionIntentosUnidadV2DTO buscar(ResultadoEvaluacionCurricularV2DTO r,Long id){for(EvaluacionIntentosUnidadV2DTO e:r.getEvaluacionesIntentos())if(id.equals(e.getIdPrograma()))return e;throw new AssertionError("Sin evaluación "+id);}
}
