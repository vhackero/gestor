package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoCargaAcademicaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvaluacionCargaAcademicaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;

/** F3C-U-01 a F3C-U-24: RN-4.3 orquesta resultados previos sin recalcularlos. */
public class EvaluacionCurricularV2Fase3CTest {

    private final EvaluacionCurricularV2ServiceImpl service = new EvaluacionCurricularV2ServiceImpl();

    @Test public void f3CU01RegularTresObligatoriasCargaValida() { assertEstado(evaluar(contexto(obligatorias(1L, 3))), EstadoCargaAcademicaV2.VALIDA); }
    @Test public void f3CU02RegularOchoCompatiblesCargaValida() { assertEstado(evaluar(contexto(obligatorias(10L, 8))), EstadoCargaAcademicaV2.VALIDA); }
    @Test public void f3CU03RegularNueveExplicaExclusion() { ContextoEvaluacionCurricularV2DTO c=contexto(obligatorias(20L,8));c.getUnidades().add(optativa(28L));EvaluacionCargaAcademicaV2DTO e=evaluar(c).getEvaluacionCargaAcademica();assertEquals(e.getEstado(),EstadoCargaAcademicaV2.RESTRINGIDA);assertTrue(e.getUdsExcluidas().toString().contains("FUERA_POR_MAXIMO")); }
    @Test public void f3CU04IrregularConTresCargaValida() { assertEstado(evaluar(contexto(reprobada(30L),reprobada(31L),reprobada(32L))),EstadoCargaAcademicaV2.VALIDA); }
    @Test public void f3CU05IrregularConSeisAlcanzaMaximo() { ContextoEvaluacionCurricularV2DTO c=contexto(reprobada(40L));c.getUnidades().addAll(obligatorias(41L,5));EvaluacionCargaAcademicaV2DTO e=evaluar(c).getEvaluacionCargaAcademica();assertEquals(e.getCargaMaximaNormativa(),Integer.valueOf(6));assertTrue(e.getMaximoCumplido()); }
    @Test public void f3CU06IrregularConSieteSeRestringeASeis() { ContextoEvaluacionCurricularV2DTO c=contexto(reprobada(50L));c.getUnidades().addAll(obligatorias(51L,5));c.getUnidades().add(optativa(56L));EvaluacionCargaAcademicaV2DTO e=evaluar(c).getEvaluacionCargaAcademica();assertEquals(e.getEstado(),EstadoCargaAcademicaV2.RESTRINGIDA);assertFalse(e.getMaximoCumplido()); }
    @Test public void f3CU07DosObligatoriasYOptativaCumplenMinimo() { ContextoEvaluacionCurricularV2DTO c=contexto(obligatoria(60L),obligatoria(61L),optativa(62L));EvaluacionCargaAcademicaV2DTO e=evaluar(c).getEvaluacionCargaAcademica();assertTrue(e.getMinimoCumplido());assertEquals(e.getEstado(),EstadoCargaAcademicaV2.VALIDA); }
    @Test public void f3CU08DosObligatoriasSinAlternativasUsanExcepcion() { EvaluacionCargaAcademicaV2DTO e=evaluar(contexto(obligatoria(70L),obligatoria(71L))).getEvaluacionCargaAcademica();assertTrue(e.getExcepcionMinimoAplicable());assertEquals(e.getEstado(),EstadoCargaAcademicaV2.VALIDA_CON_EXCEPCION_MINIMO); }
    @Test public void f3CU09UnicaObligatoriaPendienteUsaExcepcion() { EvaluacionCargaAcademicaV2DTO e=evaluar(contexto(obligatoria(80L))).getEvaluacionCargaAcademica();assertTrue(e.getExcepcionMinimoAplicable());assertEquals(e.getCargaEvaluada(),Integer.valueOf(1)); }
    @Test public void f3CU10OptativaSeriadaNoCompletaMinimo() { UnidadEvaluacionCurricularV2DTO opt=optativa(90L);opt.setSeriacionDirectaConocida(Boolean.TRUE);EvaluacionCargaAcademicaV2DTO e=evaluar(contexto(obligatoria(91L),obligatoria(92L),opt)).getEvaluacionCargaAcademica();assertTrue(e.getUdsExcluidas().toString().contains("SERIACION"));assertTrue(e.getExcepcionMinimoAplicable()); }
    @Test public void f3CU11ObligatoriaReprobadaConservaPrioridad() { ContextoEvaluacionCurricularV2DTO c=contexto(reprobada(100L));c.getUnidades().addAll(optativas(101L,3));EvaluacionCargaAcademicaV2DTO e=evaluar(c).getEvaluacionCargaAcademica();assertTrue(e.getUdsPrioritarias().contains(Long.valueOf(100L))); }
    @Test public void f3CU12FaltaOfertaSeConservaDistinta() { UnidadEvaluacionCurricularV2DTO u=obligatoria(110L);u.setOfertada(Boolean.FALSE);u.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.UD_NO_OFERTADA_PERIODO);EvaluacionCargaAcademicaV2DTO e=evaluar(contexto(u)).getEvaluacionCargaAcademica();assertTrue(e.getUdsExcluidas().toString().contains("SIN_OFERTA")); }
    @Test public void f3CU13SinCupoNoSeConvierteEnSinOferta() { UnidadEvaluacionCurricularV2DTO u=obligatoria(120L);u.setCupoDisponibleConocido(Boolean.FALSE);u.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.SIN_CUPO);EvaluacionCargaAcademicaV2DTO e=evaluar(contexto(u)).getEvaluacionCargaAcademica();assertTrue(e.getUdsExcluidas().toString().contains("SIN_CUPO"));assertFalse(e.getUdsExcluidas().toString().contains("UD120:SIN_OFERTA")); }
    @Test public void f3CU14CuatroReprobadasRegistraRn64Futuro() { EvaluacionCargaAcademicaV2DTO e=evaluar(contexto(reprobada(130L),reprobada(131L),reprobada(132L),reprobada(133L))).getEvaluacionCargaAcademica();assertTrue(e.getRn64AplicableEnFuturaFase());assertTrue(e.getReglasRelacionadas().contains(CodigoReglaCurricularV2.RN_6_4)); }
    @Test public void f3CU15QuintoDesde2027DetectaCtr01SinAplicarlo() { ContextoEvaluacionCurricularV2DTO c=contexto(obligatorias(140L,3));c.setSemestreObjetivo(Integer.valueOf(5));c.setPeriodoNormativo("2027-1");EvaluacionCargaAcademicaV2DTO e=evaluar(c).getEvaluacionCargaAcademica();assertTrue(e.getCtr01Rn61Procedente());assertTrue(e.getReglasRelacionadas().contains(CodigoReglaCurricularV2.RN_6_1)); }
    @Test public void f3CU16TerceroNoActivaCtr01() { ContextoEvaluacionCurricularV2DTO c=contexto(obligatorias(150L,3));c.setSemestreObjetivo(Integer.valueOf(3));c.setPeriodoNormativo("2027-2");assertFalse(evaluar(c).getEvaluacionCargaAcademica().getCtr01Rn61Procedente()); }
    @Test public void f3CU17SeptimoNoActivaCtr01() { ContextoEvaluacionCurricularV2DTO c=contexto(obligatorias(160L,3));c.setSemestreObjetivo(Integer.valueOf(7));c.setPeriodoNormativo("2028-1");assertFalse(evaluar(c).getEvaluacionCargaAcademica().getCtr01Rn61Procedente()); }
    @Test public void f3CU18ExcepcionMinimoNuncaExtiendeMaximoIrregular() { EvaluacionCargaAcademicaV2DTO e=evaluar(contexto(reprobada(170L))).getEvaluacionCargaAcademica();assertTrue(e.getExcepcionMinimoAplicable());assertEquals(e.getCargaMaximaNormativa(),Integer.valueOf(6));assertTrue(e.getCargaMinimaNormativa().intValue()<=e.getCargaMaximaNormativa().intValue()); }
    @Test public void f3CU19SituacionNoDeterminadaRequiereRevision() { EvaluacionCargaAcademicaV2DTO e=evaluar(contexto(indeterminada(180L))).getEvaluacionCargaAcademica();assertEquals(e.getEstado(),EstadoCargaAcademicaV2.REQUIERE_REVISION);assertNull(e.getCargaMaximaNormativa()); }
    @Test public void f3CU20SeriacionNoComprobadaRequiereRevision() { ContextoEvaluacionCurricularV2DTO c=contexto(obligatorias(190L,3));c.setSeriacionComprobada(Boolean.FALSE);assertEstado(evaluar(c),EstadoCargaAcademicaV2.REQUIERE_REVISION); }
    @Test public void f3CU21EmpateSinCriterioRequiereRevision() { ContextoEvaluacionCurricularV2DTO c=contexto();c.getUnidades().addAll(optativas(200L,9));assertEstado(evaluar(c),EstadoCargaAcademicaV2.REQUIERE_REVISION); }
    @Test public void f3CU22DuplicadosNoDobleConteo() { UnidadEvaluacionCurricularV2DTO a=obligatoria(210L),b=obligatoria(210L);a.setBloque("B1");b.setBloque("B2");assertEquals(evaluar(contexto(a,b)).getEvaluacionCargaAcademica().getCargaEvaluada(),Integer.valueOf(1)); }
    @Test public void f3CU23MismoContextoMismoResultado() { ContextoEvaluacionCurricularV2DTO c=contexto(obligatorias(220L,3));assertEquals(evaluar(c).getEvaluacionCargaAcademica().getEstado(),evaluar(c).getEvaluacionCargaAcademica().getEstado()); }
    @Test public void f3CU24NoHayEfectoProductivo() { UnidadEvaluacionCurricularV2DTO u=obligatoria(230L);ResultadoEvaluacionCurricularV2DTO r=evaluar(contexto(u));assertFalse(r.getEfectosProductivosAplicados());assertTrue(u.getOfertada());assertTrue(u.getSeleccionableOrigen()); }

    private ResultadoEvaluacionCurricularV2DTO evaluar(ContextoEvaluacionCurricularV2DTO c){return service.evaluarSombra(c);} private void assertEstado(ResultadoEvaluacionCurricularV2DTO r,EstadoCargaAcademicaV2 e){assertEquals(r.getEvaluacionCargaAcademica().getEstado(),e);}
    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO... unidades){ContextoEvaluacionCurricularV2DTO c=new ContextoEvaluacionCurricularV2DTO();c.setVersionReglas("PE2027-V1");c.setFechaEvaluacion(new Date(0L));c.setSemestreObjetivo(Integer.valueOf(3));c.setActividadAcademicaComprobada(Boolean.TRUE);c.setPermanenciaDentroLimiteComprobada(Boolean.TRUE);c.setDictaminacionEgresoConocida(Boolean.TRUE);c.setOportunidadesMejoraUtilizadas(Integer.valueOf(0));for(UnidadEvaluacionCurricularV2DTO u:unidades)c.getUnidades().add(u);return c;}
    private ContextoEvaluacionCurricularV2DTO contexto(List<UnidadEvaluacionCurricularV2DTO> unidades){return contexto(unidades.toArray(new UnidadEvaluacionCurricularV2DTO[unidades.size()]));}
    private List<UnidadEvaluacionCurricularV2DTO> obligatorias(Long i,int n){return unidades(i,n,"Obligatoria");} private List<UnidadEvaluacionCurricularV2DTO> optativas(Long i,int n){return unidades(i,n,"Optativa");} private List<UnidadEvaluacionCurricularV2DTO> unidades(Long i,int n,String t){List<UnidadEvaluacionCurricularV2DTO> r=new ArrayList<UnidadEvaluacionCurricularV2DTO>();for(int x=0;x<n;x++)r.add(pendiente(Long.valueOf(i.longValue()+x),t));return r;}
    private UnidadEvaluacionCurricularV2DTO obligatoria(Long i){return pendiente(i,"Obligatoria");} private UnidadEvaluacionCurricularV2DTO optativa(Long i){return pendiente(i,"Optativa");}
    private UnidadEvaluacionCurricularV2DTO reprobada(Long i){UnidadEvaluacionCurricularV2DTO u=pendiente(i,"Obligatoria");u.setEstado(EstadoUnidadCurricularV2.REPROBADA);u.setCausa(CausaPendienteUnidadV2.REPROBACION);u.setReprobacionVigenteConocida(Boolean.TRUE);u.setReprobacionPeriodoPrevioConocida(Boolean.TRUE);return u;}
    private UnidadEvaluacionCurricularV2DTO indeterminada(Long i){UnidadEvaluacionCurricularV2DTO u=pendiente(i,"Obligatoria");u.setEstado(EstadoUnidadCurricularV2.NO_DETERMINADO);u.setCausa(null);return u;}
    private UnidadEvaluacionCurricularV2DTO pendiente(Long i,String t){UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO();u.setIdPrograma(i);u.setClave("UD"+i);u.setTipoUd(t);u.setEstado(EstadoUnidadCurricularV2.NO_CURSADA);u.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO);u.setOfertada(Boolean.TRUE);u.setGrupoActivoConocido(Boolean.TRUE);u.setCupoDisponibleConocido(Boolean.TRUE);u.setSeleccionableOrigen(Boolean.TRUE);return u;}
}
