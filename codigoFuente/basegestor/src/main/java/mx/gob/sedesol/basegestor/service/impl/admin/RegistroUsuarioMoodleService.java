package mx.gob.sedesol.basegestor.service.impl.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.RegistroUsuariosMoodleRepository;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.UsuarioWSClient;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Usuario;

@Service("registroUsuarioMoodleService")
public class RegistroUsuarioMoodleService {
    @Autowired private RegistroUsuariosMoodleRepository repository;
    @Autowired private RegistroUsuarioMoodleGateway gateway;
    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
    private static final String PREFIJO="gw.registroMoodle.";
    @PreAuthorize("hasPermission(null,'USU_MOODLE_REG')")
    @Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
    public int registrar(Long idPersona,Integer plataforma,Integer plan,ParametroWSMoodleDTO parametros,Long actor)throws Exception {
        return registrar(idPersona,plataforma,plan,parametros,actor,null);
    }
    @PreAuthorize("hasPermission(null,'USU_MOODLE_REG')")
    @Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
    public int registrar(Long idPersona,Integer plataforma,Integer plan,ParametroWSMoodleDTO parametros,Long actor,String usuarioFiltro)throws Exception {
        if(actor==null || plataforma==null || parametros==null || !plataforma.equals(parametros.getIdParametroWSMoodle()))throw new ErrorAsignacionPlan(PREFIJO+"plataformaInvalida");
        repository.bloquear(idPersona);
        PersonaDTO persona=usuarioFiltro==null?repository.personaPendiente(idPersona,plataforma,plan):repository.personaPendiente(idPersona,plataforma,plan,usuarioFiltro);
        if(persona==null)return 0;
        if(persona.getUsuario()==null || !persona.getUsuario().matches("[a-z0-9_.@-]{1,100}"))throw new ErrorAsignacionPlan(PREFIJO+"usuarioInvalido");
        UsuarioWSClient cliente=gateway.cliente(parametros);
        int idMoodle=cliente.existeNombreUsuario(persona.getUsuario());boolean reutilizado=idMoodle>0;
        if(usuarioFiltro!=null && reutilizado){
            if(((Number)repository.estadoUsuario(usuarioFiltro,plataforma,plan).get(0)[2]).intValue()==1)
                throw new ErrorAsignacionPlan(PREFIJO+"usuarioRegistrado");
            repository.guardarReconciliado(idPersona,plataforma,idMoodle,actor);
            return 2;
        }
        if(!reutilizado){
            String correo=repository.correo(idPersona);
            if(correo==null || !correo.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"))throw new ErrorAsignacionPlan(PREFIJO+"correoInvalido");
            if(cliente.existeCorreo(correo)>0)throw new ErrorAsignacionPlan(PREFIJO+"correoExistente");
            String[] credenciales=repository.credencialOriginal(idPersona);
            boolean coincide=false;
            try{coincide=credenciales[0]!=null && credenciales[1]!=null && encoder.matches(credenciales[0],credenciales[1]);}catch(RuntimeException e){coincide=false;}
            if(!coincide)throw new ErrorAsignacionPlan(PREFIJO+"contrasenaOriginal");
            String apellidos=((persona.getApellidoPaterno()==null?"":persona.getApellidoPaterno())+" "+(persona.getApellidoMaterno()==null?"":persona.getApellidoMaterno())).trim();
            if(persona.getNombre()==null || persona.getNombre().trim().isEmpty() || apellidos.isEmpty())throw new ErrorAsignacionPlan(PREFIJO+"nombreIncompleto");
            Usuario usuario=new Usuario();usuario.setUsername(persona.getUsuario());usuario.setFirstname(persona.getNombre());usuario.setLastname(apellidos);
            usuario.setEmail(correo);usuario.setIdnumber(idPersona.toString());usuario.setPassword(credenciales[0]);
            try{idMoodle=cliente.crearUsuario(usuario);}catch(Exception e){
                // Puede haberse creado antes de un timeout, o desde otro módulo.
                idMoodle=cliente.existeNombreUsuario(persona.getUsuario());
                if(idMoodle<=0)throw e;
                reutilizado=true;
            }
        }
        if(idMoodle<=0)throw new ErrorAsignacionPlan(PREFIJO+"respuestaInvalida");
        if(usuarioFiltro==null)repository.guardar(idPersona,plataforma,idMoodle,actor);
        else repository.guardarReconciliado(idPersona,plataforma,idMoodle,actor);
        return reutilizado?2:1;
    }
}
