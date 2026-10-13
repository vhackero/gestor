package mx.gob.sedesol.basegestor.admin;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.commons.utils.ArchivoAsignacionPlan;
import mx.gob.sedesol.basegestor.service.admin.AsignacionPlanUsuarioService;
import mx.gob.sedesol.basegestor.service.impl.admin.AsignacionPlanMasivaService;

public class AsignacionPlanMasivaTest {
    @Test public void finalizaConAsignadosYOmitidosYProtegeEstado()throws Exception{verificar(false);}
    @Test public void falloSegundoBloqueConservaContadorConfirmado()throws Exception{verificar(true);}
    private void verificar(boolean fallo)throws Exception{
        AsignacionPlanMasivaService masivo=new AsignacionPlanMasivaService();
        try{
            AsignacionPlanUsuarioService service=mock(AsignacionPlanUsuarioService.class);Field f=masivo.getClass().getDeclaredField("asignacionService");f.setAccessible(true);f.set(masivo,service);
            byte[] contenido;SXSSFWorkbook libro=ArchivoAsignacionPlan.libro(Collections.emptyList());
            try{for(int n=1;n<=251;n++)ArchivoAsignacionPlan.fila(libro.getSheetAt(0),n,new Object[]{"es"+n,"Ana",n,"3","17"});ByteArrayOutputStream out=new ByteArrayOutputStream();libro.write(out);contenido=out.toByteArray();}
            finally{libro.close();libro.dispose();}
            CountDownLatch esperando=new CountDownLatch(1),continuar=new CountDownLatch(1);AtomicInteger bloque=new AtomicInteger();
            when(service.asignarBloque(anyList(),eq(7L))).thenAnswer(inv->{
                if(bloque.incrementAndGet()==1){assertEquals(250,((List<?>)inv.getArguments()[0]).size());esperando.countDown();assertTrue(continuar.await(5,TimeUnit.SECONDS));return new int[]{249,1};}
                if(fallo)throw new ErrorAsignacionPlan("gw.planUsuario.masivo.destinoFila",252,"es251");
                return new int[]{0,1};
            });
            String id=masivo.iniciar("datos.xlsx",contenido,7L);assertTrue(esperando.await(5,TimeUnit.SECONDS));
            assertFalse(masivo.consultar(id,7L).isTerminado());assertNull(masivo.consultar(id,8L));
            try{masivo.iniciar("datos.xlsx",contenido,7L);fail();}catch(ErrorAsignacionPlan e){assertTrue(e.getMessage().endsWith("enCurso"));}
            continuar.countDown();long limite=System.currentTimeMillis()+5000;
            while(!masivo.consultar(id,7L).isTerminado() && System.currentTimeMillis()<limite)Thread.sleep(10);
            EstadoCargaPlanDTO estado=masivo.consultar(id,7L);assertTrue(estado.isTerminado());assertEquals(249,estado.getAsignados());
            assertEquals(fallo?1:2,estado.getOmitidos());assertEquals(fallo?250:251,estado.getProcesados());
            if(fallo)assertEquals("gw.planUsuario.masivo.destinoFila",estado.getError());else assertNull(estado.getError());
        }finally{masivo.cerrar();}
    }
}
