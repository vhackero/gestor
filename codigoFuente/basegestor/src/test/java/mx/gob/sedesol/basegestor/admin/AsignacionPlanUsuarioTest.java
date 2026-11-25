package mx.gob.sedesol.basegestor.admin;
import java.lang.reflect.Field;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.AsignacionPlanUsuarioRepository;
import mx.gob.sedesol.basegestor.service.impl.admin.AsignacionPlanUsuarioServiceImpl;

public class AsignacionPlanUsuarioTest {
    @Test public void usuariosNormalizadosYDuplicadosDetectados(){
        assertEquals(new LinkedHashSet<>(Arrays.asList("es1","es2","es3")),AsignacionPlanUsuarioServiceImpl.normalizarUsuarios("ES1; es2\nES3"));
        try{AsignacionPlanUsuarioServiceImpl.normalizarUsuarios("ES1,es1");fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("repetido"));}
    }
    private AsignacionPlanUsuarioDTO persona(long id,String usuario){AsignacionPlanUsuarioDTO p=new AsignacionPlanUsuarioDTO();p.setIdPersona(id);p.setUsuario(usuario);return p;}
    private AsignacionPlanUsuarioServiceImpl servicio(AsignacionPlanUsuarioRepository repo)throws Exception{
        AsignacionPlanUsuarioServiceImpl servicio=new AsignacionPlanUsuarioServiceImpl();Field campo=servicio.getClass().getDeclaredField("repository");campo.setAccessible(true);campo.set(servicio,repo);return servicio;
    }
    @Test public void loteDuplicadoNoInsertaParcialmente()throws Exception{
        AsignacionPlanUsuarioRepository repo=mock(AsignacionPlanUsuarioRepository.class);
        when(repo.destinoValido(3,17)).thenReturn(true);
        when(repo.personas(anyCollection())).thenReturn(Arrays.asList(persona(10,"es1"),persona(20,"es2")));
        AsignacionPlanUsuarioDTO existente=persona(20,"es2");existente.setIdPlan(3);existente.setIdConvocatoria(17);
        when(repo.asignacionesBloqueadas(anyCollection())).thenReturn(Arrays.asList(existente));
        try{servicio(repo).asignar("es1,es2",3,17,1L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("asignacionExistente"));}
        verify(repo,never()).insertar(anyLong(),anyInt(),anyInt());
    }
    @Test public void personaNoEstudianteImpideTodoElLote()throws Exception{
        AsignacionPlanUsuarioRepository repo=mock(AsignacionPlanUsuarioRepository.class);
        when(repo.destinoValido(3,17)).thenReturn(true);when(repo.personas(anyCollection())).thenReturn(Arrays.asList(persona(10,"es1")));
        try{servicio(repo).asignar("es1,es2",3,17,1L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("noEstudiante"));}
        verify(repo,never()).insertar(anyLong(),anyInt(),anyInt());
    }
    @Test public void destinoNoVinculadoEsRechazado()throws Exception{
        AsignacionPlanUsuarioRepository repo=mock(AsignacionPlanUsuarioRepository.class);
        try{servicio(repo).asignar("es1",3,17,1L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("destino"));}
        verify(repo,never()).personas(anyCollection());
    }
    @Test public void asignacionTienePermisoYTransaccion()throws Exception{
        java.lang.reflect.Method metodo=AsignacionPlanUsuarioServiceImpl.class.getMethod("asignar",String.class,Integer.class,Integer.class,Long.class);
        assertTrue(metodo.getAnnotation(org.springframework.security.access.prepost.PreAuthorize.class).value().contains("USU_PLAN_ASIGNAR"));
        assertArrayEquals(new Class[]{Exception.class},metodo.getAnnotation(org.springframework.transaction.annotation.Transactional.class).rollbackFor());
    }
    @Test public void modificacionRechazaDestinoDuplicadoAntesDeActualizar()throws Exception{
        AsignacionPlanUsuarioRepository repo=mock(AsignacionPlanUsuarioRepository.class);when(repo.destinoValido(4,18)).thenReturn(true);
        AsignacionPlanUsuarioDTO origen=persona(10,"es1");origen.setIdAsignacion(1L);origen.setIdPlan(3);origen.setIdConvocatoria(17);
        AsignacionPlanUsuarioDTO destino=persona(10,"es1");destino.setIdAsignacion(2L);destino.setIdPlan(4);destino.setIdConvocatoria(18);
        when(repo.asignacionesBloqueadas(anyCollection())).thenReturn(Arrays.asList(origen,destino));
        try{servicio(repo).modificar(Arrays.asList(origen),4,18,1L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("resolverDuplicados"));}
        verify(repo,never()).actualizar(any(AsignacionPlanUsuarioDTO.class),anyInt(),anyInt());
        verify(repo,never()).insertar(anyLong(),anyInt(),anyInt());
    }
    @Test public void modificacionSinCambiosNoActualiza()throws Exception{
        AsignacionPlanUsuarioRepository repo=mock(AsignacionPlanUsuarioRepository.class);when(repo.destinoValido(3,17)).thenReturn(true);
        AsignacionPlanUsuarioDTO origen=persona(10,"es1");origen.setIdAsignacion(1L);origen.setIdPlan(3);origen.setIdConvocatoria(17);
        when(repo.asignacionesBloqueadas(anyCollection())).thenReturn(Arrays.asList(origen));
        try{servicio(repo).modificar(Arrays.asList(origen),3,17,1L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("sinCambios"));}
        verify(repo,never()).actualizar(any(AsignacionPlanUsuarioDTO.class),anyInt(),anyInt());
    }
    @Test public void modificacionExigePermisoYTransaccion()throws Exception{
        java.lang.reflect.Method metodo=AsignacionPlanUsuarioServiceImpl.class.getMethod("modificar",List.class,Integer.class,Integer.class,Long.class);
        assertTrue(metodo.getAnnotation(org.springframework.security.access.prepost.PreAuthorize.class).value().contains("USU_PLAN_MODIFICAR"));
        assertArrayEquals(new Class[]{Exception.class},metodo.getAnnotation(org.springframework.transaction.annotation.Transactional.class).rollbackFor());
    }
    @Test public void eliminacionPermiteUltimaAsignacion()throws Exception{
        AsignacionPlanUsuarioRepository repo=mock(AsignacionPlanUsuarioRepository.class);
        AsignacionPlanUsuarioDTO unica=persona(10,"es1");unica.setIdAsignacion(1L);unica.setIdPlan(3);unica.setIdConvocatoria(17);
        when(repo.asignacionesBloqueadas(anyCollection())).thenReturn(Arrays.asList(unica));
        when(repo.personas(anyCollection())).thenReturn(Arrays.asList(unica));when(repo.eliminar(unica)).thenReturn(1);
        servicio(repo).eliminarAsignacion(unica,1L);
        verify(repo).eliminar(unica);
    }
    @Test public void eliminacionExigePermisoYTransaccion()throws Exception{
        java.lang.reflect.Method metodo=AsignacionPlanUsuarioServiceImpl.class.getMethod("eliminarAsignacion",AsignacionPlanUsuarioDTO.class,Long.class);
        assertTrue(metodo.getAnnotation(org.springframework.security.access.prepost.PreAuthorize.class).value().contains("USU_PLAN_ELIMINAR"));
        assertArrayEquals(new Class[]{Exception.class},metodo.getAnnotation(org.springframework.transaction.annotation.Transactional.class).rollbackFor());
    }
}
