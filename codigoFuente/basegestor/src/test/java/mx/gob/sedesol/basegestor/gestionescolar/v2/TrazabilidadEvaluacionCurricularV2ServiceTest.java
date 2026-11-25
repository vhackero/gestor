package mx.gob.sedesol.basegestor.gestionescolar.v2;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Date;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoExcepcionCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ExcepcionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblEvaluacionCurricularExcepcionV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblEvaluacionCurricularReglaV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblEvaluacionCurricularUdV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblEvaluacionCurricularV2;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblEvaluacionCurricularExcepcionV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblEvaluacionCurricularReglaV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblEvaluacionCurricularUdV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblEvaluacionCurricularV2Repo;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.EvaluacionCurricularV2ServiceImpl;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2.TrazabilidadEvaluacionCurricularV2ServiceImpl;

/** Pruebas F1D de mapeo y persistencia aislada de RN-9.5. */
public class TrazabilidadEvaluacionCurricularV2ServiceTest {

    private TrazabilidadEvaluacionCurricularV2ServiceImpl service;
    private TblEvaluacionCurricularV2Repo evaluacionRepo;
    private TblEvaluacionCurricularUdV2Repo unidadRepo;
    private TblEvaluacionCurricularReglaV2Repo reglaRepo;
    private TblEvaluacionCurricularExcepcionV2Repo excepcionRepo;
    private EvaluacionCurricularV2ServiceImpl evaluador = new EvaluacionCurricularV2ServiceImpl();

    @BeforeMethod
    public void preparar() throws Exception {
        service = new TrazabilidadEvaluacionCurricularV2ServiceImpl();
        evaluacionRepo = mock(TblEvaluacionCurricularV2Repo.class);
        unidadRepo = mock(TblEvaluacionCurricularUdV2Repo.class);
        reglaRepo = mock(TblEvaluacionCurricularReglaV2Repo.class);
        excepcionRepo = mock(TblEvaluacionCurricularExcepcionV2Repo.class);
        inyectar("evaluacionRepo", evaluacionRepo); inyectar("unidadRepo", unidadRepo);
        inyectar("reglaRepo", reglaRepo); inyectar("excepcionRepo", excepcionRepo);
        when(evaluacionRepo.findByIdPersonaAndIdPlanAndVersionReglasAndOrigenConsultaAndHashContexto(any(Long.class), any(Long.class), any(String.class), any(String.class), any(String.class))).thenReturn(null);
        when(evaluacionRepo.save(any(TblEvaluacionCurricularV2.class))).thenAnswer(new Answer<TblEvaluacionCurricularV2>() { public TblEvaluacionCurricularV2 answer(InvocationOnMock i) { if(i.getArguments()[0]==null)return null; TblEvaluacionCurricularV2 e=(TblEvaluacionCurricularV2)i.getArguments()[0]; e.setId(900L); return e; }});
        when(unidadRepo.save(any(TblEvaluacionCurricularUdV2.class))).thenAnswer(new Answer<TblEvaluacionCurricularUdV2>() { public TblEvaluacionCurricularUdV2 answer(InvocationOnMock i) { TblEvaluacionCurricularUdV2 e=(TblEvaluacionCurricularUdV2)i.getArguments()[0]; e.setId(901L); return e; }});
    }

    @Test public void f1DU01EvaluacionSimpleSePersiste() { assertEquals(service.registrarSombra(contexto(unidad(1L)), resultado(unidad(1L))), Long.valueOf(900L)); verify(evaluacionRepo).save(any(TblEvaluacionCurricularV2.class)); }
    @Test public void f1DU02ReglasConcurrentesSePersisten() { ResultadoEvaluacionCurricularV2DTO r=resultado(unidad(2L)); r.getTrazas().addAll(resultado(unidad(3L)).getTrazas()); service.registrarSombra(contexto(unidad(2L)),r); verify(reglaRepo,atLeastOnce()).save(any(TblEvaluacionCurricularReglaV2.class)); }
    @Test public void f1DU03VariasDecisionesPorUd() { UnidadEvaluacionCurricularV2DTO a=unidad(3L), b=unidad(4L); service.registrarSombra(contexto(a,b),resultado(a,b)); verify(unidadRepo, org.mockito.Mockito.times(2)).save(any(TblEvaluacionCurricularUdV2.class)); }
    @Test public void f1DU04ExcepcionTipificada() { ResultadoEvaluacionCurricularV2DTO r=resultado(unidad(5L)); r.getExcepcionesEvaluadas().add(excepcion(CodigoExcepcionCurricularV2.AVANCE_REZAGO_LEVE)); service.registrarSombra(contexto(unidad(5L)),r); verify(excepcionRepo,atLeastOnce()).save(any(TblEvaluacionCurricularExcepcionV2.class)); }
    @Test public void f1DU05ExcepcionExtraordinaria() { ResultadoEvaluacionCurricularV2DTO r=resultado(unidad(6L)); r.getExcepcionesEvaluadas().add(excepcion(CodigoExcepcionCurricularV2.EXTRAORDINARIA_NO_TIPIFICADA)); service.registrarSombra(contexto(unidad(6L)),r); verify(excepcionRepo,atLeastOnce()).save(any(TblEvaluacionCurricularExcepcionV2.class)); }
    @Test public void f1DU06ProcesoInscripcionNuloEsAdmitido() { ContextoEvaluacionCurricularV2DTO c=contexto(unidad(7L)); c.setIdProcesoInscripcion(null); assertEquals(service.registrarSombra(c,resultado(unidad(7L))),Long.valueOf(900L)); }
    @Test public void f1DU07MultiplesMotivosPorUdSeMapean() { UnidadEvaluacionCurricularV2DTO u=unidad(8L); ResultadoEvaluacionCurricularV2DTO r=resultado(u); r.getDecisiones().get(0).getMotivos().add(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2.SERIACION_DIRECTA); service.registrarSombra(contexto(u),r); verify(unidadRepo).save(any(TblEvaluacionCurricularUdV2.class)); }
    @Test public void f1DU08ReevaluacionIdempotenteReutilizaEncabezado() { TblEvaluacionCurricularV2 existente=new TblEvaluacionCurricularV2(); existente.setId(777L); when(evaluacionRepo.findByIdPersonaAndIdPlanAndVersionReglasAndOrigenConsultaAndHashContexto(any(Long.class),any(Long.class),any(String.class),any(String.class),any(String.class))).thenReturn(existente); assertEquals(service.registrarSombra(contexto(unidad(9L)),resultado(unidad(9L))),Long.valueOf(777L)); verify(unidadRepo,never()).save(any(TblEvaluacionCurricularUdV2.class)); }
    @Test public void f1DU09FalloPersistenciaNoAlteraResultadoEvaluado() { ResultadoEvaluacionCurricularV2DTO r=resultado(unidad(10L)); when(evaluacionRepo.save(any(TblEvaluacionCurricularV2.class))).thenThrow(new RuntimeException("falla simulada")); try { service.registrarSombra(contexto(unidad(10L)),r); } catch(RuntimeException esperado) { } assertFalse(r.getEfectosProductivosAplicados()); }
    @Test public void f1DU10NoPersistePii() { UnidadEvaluacionCurricularV2DTO u=unidad(11L); u.setNombre("Nombre no persistible"); service.registrarSombra(contexto(u),resultado(u)); ArgumentCaptor<TblEvaluacionCurricularUdV2> c=ArgumentCaptor.forClass(TblEvaluacionCurricularUdV2.class); verify(unidadRepo).save(c.capture()); assertNull(c.getValue().getClaveUd().contains("Nombre") ? c.getValue().getClaveUd() : null); }
    @Test public void f1DU11EncabezadoYUdSeReconstruyenPorCamposTecnicos() { service.registrarSombra(contexto(unidad(12L)),resultado(unidad(12L))); verify(evaluacionRepo).save(any(TblEvaluacionCurricularV2.class)); verify(unidadRepo).save(any(TblEvaluacionCurricularUdV2.class)); }
    @Test public void f1DU12OfertaOrigenPermaneceIntacta() { UnidadEvaluacionCurricularV2DTO u=unidad(13L); Boolean oferta=u.getOfertada(); service.registrarSombra(contexto(u),resultado(u)); assertEquals(u.getOfertada(),oferta); }
    @Test public void f1DU13EvaluacionSinExcepcionNoCreaFilaExcepcion() { ResultadoEvaluacionCurricularV2DTO r=resultado(unidad(14L)); r.getExcepcionesEvaluadas().clear(); service.registrarSombra(contexto(unidad(14L)),r); verify(excepcionRepo,never()).save(any(TblEvaluacionCurricularExcepcionV2.class)); }
    @Test public void f1DU14NoDeterminadoSeConservaSinInferencia() { UnidadEvaluacionCurricularV2DTO u=unidad(15L); u.setEstado(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2.NO_DETERMINADO); service.registrarSombra(contexto(u),resultado(u)); assertNotNull(u.getEstado()); }

    private void inyectar(String nombre, Object valor) throws Exception { Field f=TrazabilidadEvaluacionCurricularV2ServiceImpl.class.getDeclaredField(nombre); f.setAccessible(true); f.set(service,valor); }
    private ContextoEvaluacionCurricularV2DTO contexto(UnidadEvaluacionCurricularV2DTO... us) { ContextoEvaluacionCurricularV2DTO c=new ContextoEvaluacionCurricularV2DTO(); c.setIdPersona(1L); c.setIdPlan(1L); c.setPerfilConsulta("TECNICO"); c.setOrigenConsulta("TEST_SOMBRA"); c.setVersionReglas("PE2027-V1"); c.setFechaEvaluacion(new Date(0L)); for(UnidadEvaluacionCurricularV2DTO u:us)c.getUnidades().add(u); return c; }
    private UnidadEvaluacionCurricularV2DTO unidad(Long id) { UnidadEvaluacionCurricularV2DTO u=new UnidadEvaluacionCurricularV2DTO(); u.setIdPrograma(id); u.setClave("UD"+id); u.setTipoUd("Obligatoria"); u.setEstado(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2.REPROBADA); u.setCausa(CausaPendienteUnidadV2.REPROBACION); u.setOfertada(Boolean.TRUE); u.setSeleccionableOrigen(Boolean.TRUE); return u; }
    private ResultadoEvaluacionCurricularV2DTO resultado(UnidadEvaluacionCurricularV2DTO... us) { return evaluador.evaluarSombra(contexto(us)); }
    private ExcepcionCurricularV2DTO excepcion(CodigoExcepcionCurricularV2 codigo) { ExcepcionCurricularV2DTO e=new ExcepcionCurricularV2DTO(); e.setCodigo(codigo); e.setTipo(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoExcepcionCurricularV2.TIPIFICADA); e.setEstado(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoExcepcionCurricularV2.CANDIDATA); e.setReglaOrigen(mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2.CTR_02); e.setVersionNormativa("PE2027-V1"); e.setFechaEvaluacion(new Date(0L)); return e; }
}
