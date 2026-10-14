package mx.gob.sedesol.basegestor.admin;
import java.lang.reflect.Field;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaRolesRepo;
import mx.gob.sedesol.basegestor.service.impl.admin.PersonaRolesServiceImpl;

public class EdicionRolesUsuarioTest {
    private PersonaRolesServiceImpl servicio(PersonaRolesRepo repo)throws Exception{
        PersonaRolesServiceImpl s=new PersonaRolesServiceImpl();Field f=s.getClass().getDeclaredField("personaRolesRepo");f.setAccessible(true);f.set(s,repo);return s;
    }
    private PersonaDTO persona(){PersonaDTO p=new PersonaDTO();p.setIdPersona(10L);p.setUsuario("es1");return p;}
    private RolDTO rol(int id){RolDTO r=new RolDTO();r.setIdRol(id);return r;}
    private RelPersonaRol relacion(long id,int rol){CatRol r=new CatRol();r.setIdRol(rol);TblPersona p=new TblPersona();p.setIdPersona(10L);p.setUsuario("es1");RelPersonaRol relacion=new RelPersonaRol(r,p,1L);relacion.setIdPersonaRol(id);return relacion;}
    @Test public void quitarEstudianteConservaSoloDocente()throws Exception{
        PersonaRolesRepo repo=mock(PersonaRolesRepo.class);when(repo.obtieneRelPersonaRoles("es1")).thenReturn(Arrays.asList(relacion(11,2),relacion(12,3)));
        assertEquals(ResultadoTransaccionEnum.EXITOSO,servicio(repo).almacenarRolesUsuario(persona(),Arrays.asList(rol(3)),1L).getResultado());
        verify(repo).eliminaRelPersonaRolById(11L);verify(repo,never()).eliminaRelPersonaRolById(12L);verify(repo,never()).saveAndFlush(any(RelPersonaRol.class));
    }
    @Test public void sustituirEstudiantePorDocenteGuardaSoloRolElegido()throws Exception{
        PersonaRolesRepo repo=mock(PersonaRolesRepo.class);when(repo.obtieneRelPersonaRoles("es1")).thenReturn(Arrays.asList(relacion(11,2)));
        assertEquals(ResultadoTransaccionEnum.EXITOSO,servicio(repo).almacenarRolesUsuario(persona(),Arrays.asList(rol(3)),1L).getResultado());
        ArgumentCaptor<RelPersonaRol> captor=ArgumentCaptor.forClass(RelPersonaRol.class);verify(repo).saveAndFlush(captor.capture());assertEquals(Integer.valueOf(3),captor.getValue().getRol().getIdRol());verify(repo).eliminaRelPersonaRolById(11L);
    }
    @Test public void seleccionVaciaONulaNoEliminaRoles()throws Exception{
        PersonaRolesRepo repo=mock(PersonaRolesRepo.class);PersonaRolesServiceImpl s=servicio(repo);
        for(List<RolDTO> roles:Arrays.<List<RolDTO>>asList(Collections.emptyList(),null)){
            ResultadoDTO<PersonaRolDTO> resultado=s.almacenarRolesUsuario(persona(),roles,1L);assertEquals(ResultadoTransaccionEnum.FALLIDO,resultado.getResultado());assertTrue(resultado.getMensajes().contains("gw.adminPersona.roles.minimo"));
        }
        verifyZeroInteractions(repo);
    }
    @Test public void conservarEstudianteComoUnicoRolEsValido()throws Exception{
        PersonaRolesRepo repo=mock(PersonaRolesRepo.class);when(repo.obtieneRelPersonaRoles("es1")).thenReturn(Arrays.asList(relacion(11,2),relacion(12,3)));
        assertEquals(ResultadoTransaccionEnum.EXITOSO,servicio(repo).almacenarRolesUsuario(persona(),Arrays.asList(rol(2)),1L).getResultado());
        verify(repo).eliminaRelPersonaRolById(12L);verify(repo,never()).eliminaRelPersonaRolById(11L);
    }
    @Test public void falloAlGuardarNuevoRolRevierteLaEliminacionAnterior()throws Exception{
        PersonaRolesRepo repo=mock(PersonaRolesRepo.class);when(repo.obtieneRelPersonaRoles("es1")).thenReturn(Arrays.asList(relacion(11,2)));
        when(repo.saveAndFlush(any(RelPersonaRol.class))).thenThrow(new IllegalStateException("Fallo simulado"));
        final boolean[] rollback={false};
        org.springframework.transaction.support.AbstractPlatformTransactionManager tx=new org.springframework.transaction.support.AbstractPlatformTransactionManager(){
            protected Object doGetTransaction(){return new Object();}
            protected void doBegin(Object transaccion,org.springframework.transaction.TransactionDefinition definicion){}
            protected void doCommit(org.springframework.transaction.support.DefaultTransactionStatus estado){fail("No debe confirmar el cambio fallido");}
            protected void doRollback(org.springframework.transaction.support.DefaultTransactionStatus estado){rollback[0]=true;}
        };
        org.springframework.transaction.interceptor.TransactionInterceptor advice=new org.springframework.transaction.interceptor.TransactionInterceptor(tx,new org.springframework.transaction.annotation.AnnotationTransactionAttributeSource());
        org.springframework.aop.framework.ProxyFactory proxy=new org.springframework.aop.framework.ProxyFactory(servicio(repo));proxy.addAdvice(advice);
        mx.gob.sedesol.basegestor.service.admin.PersonaRolesService s=(mx.gob.sedesol.basegestor.service.admin.PersonaRolesService)proxy.getProxy();
        assertEquals(ResultadoTransaccionEnum.FALLIDO,s.almacenarRolesUsuario(persona(),Arrays.asList(rol(3)),1L).getResultado());
        assertTrue(rollback[0]);verify(repo).eliminaRelPersonaRolById(11L);
    }
}
