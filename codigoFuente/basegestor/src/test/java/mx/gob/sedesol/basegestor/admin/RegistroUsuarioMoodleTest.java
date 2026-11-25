package mx.gob.sedesol.basegestor.admin;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.RegistroUsuariosMoodleRepository;
import mx.gob.sedesol.basegestor.service.impl.admin.*;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.UsuarioWSClient;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Usuario;

public class RegistroUsuarioMoodleTest {
    @Test public void relacionLocalSinCuentaRemotaPermiteRecrear()throws Exception{
        RegistroUsuarioMoodleService service=fixture();
        PersonaDTO persona=repo.personaPendiente(10L,1,null);when(repo.personaPendiente(10L,1,3,"es1")).thenReturn(persona);
        when(cliente.crearUsuario(any(Usuario.class))).thenReturn(601);
        assertEquals(1,service.registrar(10L,1,3,plataforma,7L,"es1"));
        verify(repo).guardarReconciliado(10L,1,601,7L);
    }
    @Test public void relacionLocalConCuentaRemotaNotificaRegistroExistente()throws Exception{
        RegistroUsuarioMoodleService service=fixture();
        PersonaDTO persona=repo.personaPendiente(10L,1,null);when(repo.personaPendiente(10L,1,3,"es1")).thenReturn(persona);
        when(cliente.existeNombreUsuario("es1")).thenReturn(501);
        when(repo.estadoUsuario("es1",1,3)).thenReturn(java.util.Collections.singletonList(new Object[]{1,1,1}));
        try{service.registrar(10L,1,3,plataforma,7L,"es1");fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("usuarioRegistrado"));}
        verify(cliente,never()).crearUsuario(any(Usuario.class));
    }
    private RegistroUsuariosMoodleRepository repo;
    private UsuarioWSClient cliente;
    private ParametroWSMoodleDTO plataforma;
    private RegistroUsuarioMoodleService fixture()throws Exception{
        RegistroUsuarioMoodleService service=new RegistroUsuarioMoodleService();repo=mock(RegistroUsuariosMoodleRepository.class);cliente=mock(UsuarioWSClient.class);
        RegistroUsuarioMoodleGateway gateway=mock(RegistroUsuarioMoodleGateway.class);when(gateway.cliente(any(ParametroWSMoodleDTO.class))).thenReturn(cliente);
        for(String campo:new String[]{"repository","gateway"}){Field f=service.getClass().getDeclaredField(campo);f.setAccessible(true);f.set(service,campo.equals("repository")?repo:gateway);}
        PersonaDTO persona=new PersonaDTO();persona.setIdPersona(10L);persona.setUsuario("es1");persona.setNombre("Ana");persona.setApellidoPaterno("Perez");persona.setApellidoMaterno("G");
        when(repo.personaPendiente(10L,1,null)).thenReturn(persona);when(repo.correo(10L)).thenReturn("es1@example.com");
        when(repo.credencialOriginal(10L)).thenReturn(new String[]{"Ejemplo2026!",new BCryptPasswordEncoder(4).encode("Ejemplo2026!")});
        plataforma=new ParametroWSMoodleDTO();plataforma.setIdParametroWSMoodle(1);return service;
    }
    @Test public void creaUsuarioYRelacionConIdValido()throws Exception{
        RegistroUsuarioMoodleService service=fixture();when(cliente.crearUsuario(any(Usuario.class))).thenReturn(501);
        assertEquals(1,service.registrar(10L,1,null,plataforma,7L));verify(repo).guardar(10L,1,501,7L);
        ArgumentCaptor<Usuario> usuario=ArgumentCaptor.forClass(Usuario.class);verify(cliente).crearUsuario(usuario.capture());assertEquals("Ejemplo2026!",usuario.getValue().getPassword());assertEquals("10",usuario.getValue().getIdnumber());
    }
    @Test public void cuentaExistenteSeVinculaSinCambiarCredenciales()throws Exception{
        RegistroUsuarioMoodleService service=fixture();when(cliente.existeNombreUsuario("es1")).thenReturn(501);
        assertEquals(2,service.registrar(10L,1,null,plataforma,7L));verify(repo).guardar(10L,1,501,7L);
        verify(cliente,never()).crearUsuario(any(Usuario.class));verify(repo,never()).credencialOriginal(anyLong());
    }
    @Test public void usuarioYaRelacionadoOInactivoSeOmite()throws Exception{
        RegistroUsuarioMoodleService service=fixture();when(repo.personaPendiente(10L,1,null)).thenReturn(null);
        assertEquals(0,service.registrar(10L,1,null,plataforma,7L));verifyZeroInteractions(cliente);verify(repo,never()).guardar(anyLong(),anyInt(),anyInt(),anyLong());
    }
    @Test public void noVinculaUnaCuentaAjenaPorCorreo()throws Exception{
        RegistroUsuarioMoodleService service=fixture();when(cliente.existeCorreo("es1@example.com")).thenReturn(900);
        try{service.registrar(10L,1,null,plataforma,7L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("correoExistente"));}
        verify(cliente,never()).crearUsuario(any(Usuario.class));verify(repo,never()).guardar(anyLong(),anyInt(),anyInt(),anyLong());
    }
    @Test public void idNuloOCeroNoSePersiste()throws Exception{
        RegistroUsuarioMoodleService service=fixture();when(cliente.crearUsuario(any(Usuario.class))).thenReturn(0);
        try{service.registrar(10L,1,null,plataforma,7L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("respuestaInvalida"));}
        verify(repo,never()).guardar(anyLong(),anyInt(),anyInt(),anyLong());
    }
    @Test public void contrasenaOriginalIncorrectaNoCreaCuenta()throws Exception{
        RegistroUsuarioMoodleService service=fixture();when(repo.credencialOriginal(10L)).thenReturn(new String[]{"Otra",new BCryptPasswordEncoder(4).encode("Ejemplo2026!")});
        try{service.registrar(10L,1,null,plataforma,7L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("contrasenaOriginal"));}
        verify(cliente,never()).crearUsuario(any(Usuario.class));
    }
    @Test public void recuperaCuentaCreadaAntesDeUnTimeout()throws Exception{
        RegistroUsuarioMoodleService service=fixture();when(cliente.existeNombreUsuario("es1")).thenReturn(0,501);
        when(cliente.crearUsuario(any(Usuario.class))).thenThrow(new IllegalStateException("Timeout simulado"));
        assertEquals(2,service.registrar(10L,1,null,plataforma,7L));verify(repo).guardar(10L,1,501,7L);
    }
}
