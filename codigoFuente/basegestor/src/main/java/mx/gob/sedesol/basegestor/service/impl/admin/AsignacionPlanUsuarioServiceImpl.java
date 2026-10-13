package mx.gob.sedesol.basegestor.service.impl.admin;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.AsignacionPlanUsuarioRepository;
import mx.gob.sedesol.basegestor.service.admin.AsignacionPlanUsuarioService;

@Service("asignacionPlanUsuarioService")
public class AsignacionPlanUsuarioServiceImpl implements AsignacionPlanUsuarioService {
    @Autowired private AsignacionPlanUsuarioRepository repository;
    private static final String PREFIJO="gw.planUsuario.mensaje.";
    @Override @PreAuthorize("hasPermission(null,'USU_PLAN_VER')")
    @Transactional(readOnly=true)
    public List<Object[]> planes(){return repository.planes();}
    @Override @PreAuthorize("hasPermission(null,'USU_PLAN_VER')")
    @Transactional(readOnly=true)
    public List<Object[]> convocatorias(Integer plan){return plan==null?Collections.emptyList():repository.convocatorias(plan);}
    @Override @PreAuthorize("hasPermission(null,'USU_PLAN_VER')")
    @Transactional(readOnly=true)
    public List<AsignacionPlanUsuarioDTO> buscar(String usuarios){
        List<AsignacionPlanUsuarioDTO> personas=validarPersonas(usuarios);
        List<AsignacionPlanUsuarioDTO> asignaciones=repository.asignaciones(ids(personas));
        Set<Long> existentes=asignaciones.stream().map(AsignacionPlanUsuarioDTO::getIdPersona).collect(Collectors.toSet());
        List<String> faltantes=personas.stream().filter(p->!existentes.contains(p.getIdPersona())).map(AsignacionPlanUsuarioDTO::getUsuario).collect(Collectors.toList());
        if(!faltantes.isEmpty()) throw new ErrorAsignacionPlan(PREFIJO+"sinAsignacion",String.join(", ",faltantes));
        return asignaciones;
    }
    @Override @PreAuthorize("hasPermission(null,'USU_PLAN_VER') and hasPermission(null,'USU_PLAN_ASIGNAR')")
    @Transactional(rollbackFor=Exception.class)
    public int asignar(String usuarios,Integer plan,Integer convocatoria,Long actor){
        validarDestino(plan,convocatoria,actor);
        List<AsignacionPlanUsuarioDTO> personas=validarPersonas(usuarios);
        List<Long> ids=ids(personas);repository.bloquearPersonas(ids);
        List<AsignacionPlanUsuarioDTO> existentes=repository.asignacionesBloqueadas(ids);
        List<String> duplicados=existentes.stream()
            .map(AsignacionPlanUsuarioDTO::getUsuario).distinct().collect(Collectors.toList());
        if(!duplicados.isEmpty())throw new ErrorAsignacionPlan(PREFIJO+"asignacionExistente",String.join(", ",duplicados));
        for(AsignacionPlanUsuarioDTO p:personas)repository.insertar(p.getIdPersona(),plan,convocatoria);
        return personas.size();
    }
    @Override @PreAuthorize("hasPermission(null,'USU_PLAN_VER') and hasPermission(null,'USU_PLAN_MODIFICAR')")
    @Transactional(rollbackFor=Exception.class)
    public int modificar(List<AsignacionPlanUsuarioDTO> seleccion,Integer plan,Integer convocatoria,Long actor){
        validarDestino(plan,convocatoria,actor);
        if(seleccion==null || seleccion.isEmpty())throw new ErrorAsignacionPlan(PREFIJO+"seleccion");
        if(seleccion.size()>500)throw new ErrorAsignacionPlan(PREFIJO+"limite");
        Set<Long> personas=new TreeSet<>();Set<Long> relaciones=new HashSet<>();
        for(AsignacionPlanUsuarioDTO s:seleccion){
            if(s==null || s.getIdPersona()==null || s.getIdAsignacion()==null)throw new ErrorAsignacionPlan(PREFIJO+"seleccion");
            if(!relaciones.add(s.getIdAsignacion()) || !personas.add(s.getIdPersona()))throw new ErrorAsignacionPlan(PREFIJO+"seleccionDuplicada");
        }
        repository.bloquearPersonas(personas);
        List<AsignacionPlanUsuarioDTO> actuales=repository.asignacionesBloqueadas(personas);
        Map<Long,Long> cantidades=actuales.stream().collect(Collectors.groupingBy(AsignacionPlanUsuarioDTO::getIdPersona,Collectors.counting()));
        if(cantidades.values().stream().anyMatch(n->n>1))throw new ErrorAsignacionPlan(PREFIJO+"resolverDuplicados");
        Map<Long,AsignacionPlanUsuarioDTO> porId=actuales.stream().collect(Collectors.toMap(AsignacionPlanUsuarioDTO::getIdAsignacion,a->a));
        List<AsignacionPlanUsuarioDTO> cambios=new ArrayList<>();
        for(AsignacionPlanUsuarioDTO s:seleccion){
            AsignacionPlanUsuarioDTO actual=porId.get(s.getIdAsignacion());
            if(actual==null || !Objects.equals(actual.getIdPersona(),s.getIdPersona()) || !Objects.equals(actual.getIdPlan(),s.getIdPlan()) || !Objects.equals(actual.getIdConvocatoria(),s.getIdConvocatoria()))
                throw new ErrorAsignacionPlan(PREFIJO+"desactualizado");
            if(actuales.stream().anyMatch(a->Objects.equals(a.getIdPersona(),actual.getIdPersona()) && !Objects.equals(a.getIdAsignacion(),actual.getIdAsignacion()) && Objects.equals(a.getIdPlan(),plan) && Objects.equals(a.getIdConvocatoria(),convocatoria)))
                throw new ErrorAsignacionPlan(PREFIJO+"duplicado",actual.getUsuario());
            if(!Objects.equals(actual.getIdPlan(),plan) || !Objects.equals(actual.getIdConvocatoria(),convocatoria))cambios.add(actual);
        }
        if(cambios.isEmpty())throw new ErrorAsignacionPlan(PREFIJO+"sinCambios");
        validarPersonas(cambios.stream().map(AsignacionPlanUsuarioDTO::getUsuario).collect(Collectors.joining(",")));
        for(AsignacionPlanUsuarioDTO actual:cambios)if(repository.actualizar(actual,plan,convocatoria)!=1)throw new ErrorAsignacionPlan(PREFIJO+"desactualizado");
        return cambios.size();
    }
    @Override @PreAuthorize("hasPermission(null,'USU_PLAN_VER') and hasPermission(null,'USU_PLAN_ELIMINAR')")
    @Transactional(rollbackFor=Exception.class)
    public void eliminarAsignacion(AsignacionPlanUsuarioDTO seleccion,Long actor){
        if(actor==null)throw new ErrorAsignacionPlan(PREFIJO+"sesion");
        if(seleccion==null || seleccion.getIdPersona()==null || seleccion.getIdAsignacion()==null)throw new ErrorAsignacionPlan(PREFIJO+"seleccion");
        repository.bloquearPersonas(Collections.singletonList(seleccion.getIdPersona()));
        List<AsignacionPlanUsuarioDTO> actuales=repository.asignacionesBloqueadas(Collections.singletonList(seleccion.getIdPersona()));
        AsignacionPlanUsuarioDTO actual=actuales.stream().filter(a->Objects.equals(a.getIdAsignacion(),seleccion.getIdAsignacion())).findFirst().orElse(null);
        if(actual==null || !Objects.equals(actual.getIdPlan(),seleccion.getIdPlan()) || !Objects.equals(actual.getIdConvocatoria(),seleccion.getIdConvocatoria()))
            throw new ErrorAsignacionPlan(PREFIJO+"desactualizado");
        validarPersonas(actual.getUsuario());
        if(repository.eliminar(actual)!=1)throw new ErrorAsignacionPlan(PREFIJO+"desactualizado");
    }
    private void validarDestino(Integer plan,Integer convocatoria,Long actor){
        if(actor==null)throw new ErrorAsignacionPlan(PREFIJO+"sesion");
        if(plan==null || convocatoria==null || !repository.destinoValido(plan,convocatoria))throw new ErrorAsignacionPlan(PREFIJO+"destino");
    }
    private List<AsignacionPlanUsuarioDTO> validarPersonas(String texto){
        Set<String> usuarios=normalizarUsuarios(texto);
        List<AsignacionPlanUsuarioDTO> personas=repository.personas(usuarios);
        Set<String> encontrados=personas.stream().map(p->p.getUsuario().toLowerCase(Locale.ROOT)).collect(Collectors.toSet());
        List<String> faltantes=usuarios.stream().filter(u->!encontrados.contains(u)).collect(Collectors.toList());
        if(!faltantes.isEmpty())throw new ErrorAsignacionPlan(PREFIJO+"noEstudiante",String.join(", ",faltantes));
        if(personas.size()!=usuarios.size())throw new ErrorAsignacionPlan(PREFIJO+"usuarioAmbiguo");
        return personas;
    }
    public static Set<String> normalizarUsuarios(String texto){
        if(texto==null || texto.trim().isEmpty())throw new ErrorAsignacionPlan(PREFIJO+"usuarios");
        if(texto.length()>51000)throw new ErrorAsignacionPlan(PREFIJO+"limite");
        Set<String> resultado=new LinkedHashSet<>();
        for(String token:texto.trim().split("[\\s,;]+")){
            String usuario=token.toLowerCase(Locale.ROOT);
            if(!usuario.matches("[a-z0-9_.@-]{1,100}"))throw new ErrorAsignacionPlan(PREFIJO+"formato");
            if(!resultado.add(usuario))throw new ErrorAsignacionPlan(PREFIJO+"repetido",usuario);
            if(resultado.size()>500)throw new ErrorAsignacionPlan(PREFIJO+"limite");
        }
        return resultado;
    }
    private static List<Long> ids(List<AsignacionPlanUsuarioDTO> personas){return personas.stream().map(AsignacionPlanUsuarioDTO::getIdPersona).sorted().collect(Collectors.toList());}
}
