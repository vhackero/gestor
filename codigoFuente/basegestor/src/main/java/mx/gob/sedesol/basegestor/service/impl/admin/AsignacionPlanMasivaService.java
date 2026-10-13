package mx.gob.sedesol.basegestor.service.impl.admin;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.log4j.Logger;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.commons.utils.ArchivoAsignacionPlan;
import mx.gob.sedesol.basegestor.model.repositories.admin.AsignacionPlanUsuarioRepository;
import mx.gob.sedesol.basegestor.service.admin.AsignacionPlanUsuarioService;

@Service("asignacionPlanMasivaService")
public class AsignacionPlanMasivaService {
    @Autowired private AsignacionPlanUsuarioRepository repository;
    @Autowired private AsignacionPlanUsuarioService asignacionService;
    private static final Logger LOG=Logger.getLogger(AsignacionPlanMasivaService.class);
    private final Map<String,EstadoCargaPlanDTO> trabajos=new ConcurrentHashMap<>();
    private final ThreadPoolExecutor executor=new ThreadPoolExecutor(1,1,0,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(2),r->{Thread t=new Thread(r,"gestor-asignacion-plan-masiva");t.setDaemon(true);return t;});
    @PreAuthorize("hasPermission(null,'USU_PLAN_VER') and hasPermission(null,'USU_PLAN_MASIVO')")
    @Transactional(readOnly=true)
    public byte[] descargar()throws IOException {
        long[] resumen=repository.resumenSinPlan();
        if(resumen[1]>ArchivoAsignacionPlan.MAX_FILAS)throw new ErrorAsignacionPlan("gw.planUsuario.masivo.limite");
        SXSSFWorkbook libro=ArchivoAsignacionPlan.libro(repository.catalogosMasivos());
        try{Sheet datos=libro.getSheetAt(0);long ultimo=0;int fila=1;
            while(true){List<Object[]> pagina=repository.sinPlan(ultimo,resumen[0],500);if(pagina.isEmpty())break;
                for(Object[] registro:pagina)ArchivoAsignacionPlan.fila(datos,fila++,new Object[]{registro[0],registro[1],registro[2],"",""});
                ultimo=((Number)pagina.get(pagina.size()-1)[2]).longValue();}
            ByteArrayOutputStream salida=new ByteArrayOutputStream();libro.write(salida);return salida.toByteArray();
        }finally{try{libro.close();}finally{libro.dispose();}}
    }
    @PreAuthorize("hasPermission(null,'USU_PLAN_VER') and hasPermission(null,'USU_PLAN_MASIVO') and hasPermission(null,'USU_PLAN_ASIGNAR')")
    public synchronized String iniciar(String nombre,byte[] contenido,Long actor)throws IOException {
        if(actor==null)throw new ErrorAsignacionPlan("gw.planUsuario.mensaje.sesion");
        if(trabajos.values().stream().anyMatch(e->actor.equals(e.getUsuario()) && !e.isTerminado()))throw new ErrorAsignacionPlan("gw.planUsuario.masivo.enCurso");
        List<FilaAsignacionPlanDTO> filas=ArchivoAsignacionPlan.leer(nombre,contenido);
        trabajos.entrySet().removeIf(e->e.getValue().isTerminado() && System.currentTimeMillis()-e.getValue().getFin()>3600000);
        String id=UUID.randomUUID().toString();EstadoCargaPlanDTO estado=new EstadoCargaPlanDTO(id,actor,filas.size());trabajos.put(id,estado);
        try{executor.execute(new DelegatingSecurityContextRunnable(()->ejecutar(estado,filas,actor)));}
        catch(RejectedExecutionException e){trabajos.remove(id);throw new ErrorAsignacionPlan("gw.planUsuario.masivo.ocupado");}
        return id;
    }
    private void ejecutar(EstadoCargaPlanDTO estado,List<FilaAsignacionPlanDTO> filas,Long actor){
        try{for(int n=0;n<filas.size();n+=250){if(Thread.currentThread().isInterrupted())throw new IllegalStateException();
            int[] resultado=asignacionService.asignarBloque(new ArrayList<>(filas.subList(n,Math.min(n+250,filas.size()))),actor);estado.avanzar(resultado[0],resultado[1]);}
            estado.finalizar(null);
        }catch(ErrorAsignacionPlan e){estado.finalizar(e.getMessage(),e.getParametros());}
        catch(Exception e){LOG.error("Asignacion masiva "+estado.getId()+": "+e.getClass().getSimpleName());estado.finalizar("gw.planUsuario.mensaje.error");}
        finally{if(!estado.isTerminado())estado.finalizar("gw.planUsuario.mensaje.error");}
    }
    public EstadoCargaPlanDTO consultar(String id,Long actor){EstadoCargaPlanDTO estado=id==null?null:trabajos.get(id);return estado!=null && estado.getUsuario().equals(actor)?estado:null;}
    @PreDestroy public void cerrar(){executor.shutdownNow();}
}
