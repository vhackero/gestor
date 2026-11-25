package mx.gob.sedesol.basegestor.service.impl.admin;
import java.util.*;
import java.util.concurrent.*;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.apache.log4j.Logger;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.RegistroUsuariosMoodleRepository;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;

@Service("registroUsuariosMoodleService")
public class RegistroUsuariosMoodleService {
    @Autowired private RegistroUsuariosMoodleRepository repository;
    @Autowired private RegistroUsuarioMoodleService registro;
    @Autowired private ParametroWSMoodleService parametrosService;
    private static final Logger LOG=Logger.getLogger(RegistroUsuariosMoodleService.class);
    private static final String PREFIJO="gw.registroMoodle.";
    private final Map<String,EstadoRegistroMoodleDTO> trabajos=new ConcurrentHashMap<>();
    private final Set<Integer> plataformasEnCurso=ConcurrentHashMap.newKeySet();
    private final ThreadPoolExecutor executor=new ThreadPoolExecutor(1,1,0,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(2),r->{Thread t=new Thread(r,"gestor-registro-moodle");t.setDaemon(true);return t;});
    @PreAuthorize("hasPermission(null,'USU_MOODLE_REG')") @Transactional(readOnly=true)
    public List<Object[]> plataformas(){return repository.plataformas();}
    @PreAuthorize("hasPermission(null,'USU_MOODLE_REG')") @Transactional(readOnly=true)
    public List<Object[]> planes(){return repository.planes();}
    @PreAuthorize("hasPermission(null,'USU_MOODLE_REG')") @Transactional(readOnly=true)
    public synchronized String iniciar(Integer plataforma,Integer plan,Long actor){
        return iniciar(plataforma,plan,null,actor);
    }
    @PreAuthorize("hasPermission(null,'USU_MOODLE_REG')") @Transactional(readOnly=true)
    public synchronized String iniciar(Integer plataforma,Integer plan,String nombreUsuario,Long actor){
        if(plataforma==null || actor==null)throw new ErrorAsignacionPlan(PREFIJO+"plataformaRequerida");
        if(plataformasEnCurso.contains(plataforma))throw new ErrorAsignacionPlan(PREFIJO+"enCurso");
        ParametroWSMoodleDTO parametros=parametrosService.buscarPorId(plataforma);
        if(parametros==null || !Boolean.TRUE.equals(parametros.getActivo()))throw new ErrorAsignacionPlan(PREFIJO+"plataformaInvalida");
        if(plan!=null && !repository.existePlan(plan))throw new ErrorAsignacionPlan(PREFIJO+"planInvalido");
        final String usuario=nombreUsuario==null || nombreUsuario.trim().isEmpty()?null:nombreUsuario.trim().toLowerCase(Locale.ROOT);
        if(usuario!=null){
            if(!usuario.matches("[a-z0-9_.@-]{1,100}"))throw new ErrorAsignacionPlan(PREFIJO+"usuarioInvalido");
            if(plan==null)throw new ErrorAsignacionPlan(PREFIJO+"planRequeridoUsuario");
            List<Object[]> datos=repository.estadoUsuario(usuario,plataforma,plan);
            if(datos.isEmpty())throw new ErrorAsignacionPlan(PREFIJO+"usuarioNoExiste");
            if(datos.size()!=1)throw new ErrorAsignacionPlan(PREFIJO+"usuarioAmbiguo");
            Object[] datosUsuario=datos.get(0);

            if(((Number)datosUsuario[0]).intValue()!=1)throw new ErrorAsignacionPlan(PREFIJO+"usuarioInactivo");
            if(((Number)datosUsuario[1]).intValue()!=1)throw new ErrorAsignacionPlan(PREFIJO+"usuarioPlan");
        }
        long[] resumen=usuario==null?repository.resumen(plataforma,plan):repository.resumen(plataforma,plan,usuario);String id=UUID.randomUUID().toString();EstadoRegistroMoodleDTO estado=new EstadoRegistroMoodleDTO(id,actor,resumen[1]);
        trabajos.entrySet().removeIf(e->e.getValue().isTerminado() && System.currentTimeMillis()-e.getValue().getFin()>3600000);trabajos.put(id,estado);
        if(resumen[1]==0){estado.finalizar(null);return id;}
        plataformasEnCurso.add(plataforma);
        try{executor.execute(new DelegatingSecurityContextRunnable(()->ejecutar(estado,plataforma,plan,usuario,actor,parametros,resumen[0])));}
        catch(RejectedExecutionException e){plataformasEnCurso.remove(plataforma);trabajos.remove(id);throw new ErrorAsignacionPlan(PREFIJO+"ocupado");}
        return id;
    }
    private void ejecutar(EstadoRegistroMoodleDTO estado,Integer plataforma,Integer plan,String usuario,Long actor,ParametroWSMoodleDTO parametros,long corte){
        try{long ultimo=0;
            while(!Thread.currentThread().isInterrupted()){
                List<Long> ids=usuario==null?repository.pendientes(plataforma,plan,ultimo,corte):repository.pendientes(plataforma,plan,usuario,ultimo,corte);if(ids.isEmpty())break;
                for(Long persona:ids){if(Thread.currentThread().isInterrupted())throw new IllegalStateException();
                    try{estado.avanzar(usuario==null?registro.registrar(persona,plataforma,plan,parametros,actor):registro.registrar(persona,plataforma,plan,parametros,actor,usuario));}
                    catch(ErrorAsignacionPlan e){estado.fallar(persona,e.getMessage());}
                    catch(Exception e){LOG.error("Registro Moodle "+estado.getId()+" persona="+persona+" error="+e.getClass().getSimpleName());estado.fallar(persona,PREFIJO+"errorServicio");}
                    ultimo=persona;
                }
            }
            if(Thread.currentThread().isInterrupted())throw new IllegalStateException();
            estado.finalizar(null);
        }catch(Exception e){LOG.error("Registro Moodle "+estado.getId()+" detenido: "+e.getClass().getSimpleName());estado.finalizar(PREFIJO+"errorServicio");}
        finally{if(!estado.isTerminado())estado.finalizar(PREFIJO+"errorServicio");plataformasEnCurso.remove(plataforma);}
    }
    public EstadoRegistroMoodleDTO consultar(String id,Long actor){EstadoRegistroMoodleDTO e=id==null?null:trabajos.get(id);return e!=null && e.getUsuario().equals(actor)?e:null;}
    @PreDestroy public void cerrar(){executor.shutdownNow();}
}
