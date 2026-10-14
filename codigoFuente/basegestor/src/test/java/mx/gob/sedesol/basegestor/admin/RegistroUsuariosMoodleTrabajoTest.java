package mx.gob.sedesol.basegestor.admin;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.RegistroUsuariosMoodleRepository;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.impl.admin.*;

public class RegistroUsuariosMoodleTrabajoTest {
    @Test public void usuarioEspecificoValidaPlanYNotificaRegistroExistente()throws Exception{
        RegistroUsuariosMoodleService service=new RegistroUsuariosMoodleService();
        try{
            RegistroUsuariosMoodleRepository repo=mock(RegistroUsuariosMoodleRepository.class);
            RegistroUsuarioMoodleService registro=mock(RegistroUsuarioMoodleService.class);
            ParametroWSMoodleService parametros=mock(ParametroWSMoodleService.class);
            Object[] valores={repo,registro,parametros};String[] nombres={"repository","registro","parametrosService"};
            for(int n=0;n<3;n++){Field f=service.getClass().getDeclaredField(nombres[n]);f.setAccessible(true);f.set(service,valores[n]);}
            ParametroWSMoodleDTO plataforma=new ParametroWSMoodleDTO();plataforma.setActivo(true);when(parametros.buscarPorId(1)).thenReturn(plataforma);when(repo.existePlan(3)).thenReturn(true);
            try{service.iniciar(1,null," ES1 ",7L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("planRequeridoUsuario"));}
            when(repo.estadoUsuario("es1",1,3)).thenReturn(Collections.singletonList(new Object[]{1,0,1}));
            try{service.iniciar(1,3,"es1",7L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("usuarioPlan"));}
            verifyZeroInteractions(registro);
        }finally{service.cerrar();}
    }
    @Test public void falloIndividualNoImpideRegistrarAlSiguienteYProtegeEstado()throws Exception{
        RegistroUsuariosMoodleService service=new RegistroUsuariosMoodleService();
        try{RegistroUsuariosMoodleRepository repo=mock(RegistroUsuariosMoodleRepository.class);RegistroUsuarioMoodleService registro=mock(RegistroUsuarioMoodleService.class);ParametroWSMoodleService parametros=mock(ParametroWSMoodleService.class);
            Object[] valores={repo,registro,parametros};String[] nombres={"repository","registro","parametrosService"};
            for(int n=0;n<3;n++){Field f=service.getClass().getDeclaredField(nombres[n]);f.setAccessible(true);f.set(service,valores[n]);}
            ParametroWSMoodleDTO plataforma=new ParametroWSMoodleDTO();plataforma.setActivo(true);plataforma.setIdParametroWSMoodle(1);when(parametros.buscarPorId(1)).thenReturn(plataforma);
            when(repo.resumen(1,null)).thenReturn(new long[]{20,2});when(repo.pendientes(1,null,0,20)).thenReturn(Arrays.asList(10L,20L));when(repo.pendientes(1,null,20,20)).thenReturn(Collections.emptyList());
            CountDownLatch esperando=new CountDownLatch(1),continuar=new CountDownLatch(1);
            when(registro.registrar(10L,1,null,plataforma,7L)).thenAnswer(inv->{esperando.countDown();assertTrue(continuar.await(5,TimeUnit.SECONDS));throw new ErrorAsignacionPlan("gw.registroMoodle.correoInvalido");});
            when(registro.registrar(20L,1,null,plataforma,7L)).thenReturn(1);
            String id=service.iniciar(1,null,7L);assertTrue(esperando.await(5,TimeUnit.SECONDS));assertFalse(service.consultar(id,7L).isTerminado());assertNull(service.consultar(id,8L));
            try{service.iniciar(1,null,8L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("enCurso"));}
            continuar.countDown();long fin=System.currentTimeMillis()+5000;while(!service.consultar(id,7L).isTerminado() && System.currentTimeMillis()<fin)Thread.sleep(10);
            EstadoRegistroMoodleDTO estado=service.consultar(id,7L);assertTrue(estado.isTerminado());assertEquals(1,estado.getRegistrados());assertEquals(1,estado.getFallidos());assertEquals(100,estado.getPorcentaje());assertEquals(1,estado.getErrores().size());
        }finally{service.cerrar();}
    }
}
