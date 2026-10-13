package mx.gob.sedesol.gestorweb.beans.administracion;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import org.apache.log4j.Logger;
import mx.gob.sedesol.basegestor.commons.dto.admin.*;
import mx.gob.sedesol.basegestor.service.admin.AsignacionPlanUsuarioService;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@ManagedBean @ViewScoped
public class AsignacionPlanUsuarioBean implements Serializable {
    private static final long serialVersionUID=1L;
    private static final Logger LOG=Logger.getLogger(AsignacionPlanUsuarioBean.class);
    @ManagedProperty("#{asignacionPlanUsuarioService}") private transient AsignacionPlanUsuarioService servicio;
    @ManagedProperty("#{sistema}") private SistemaBean sistema;
    private String usuariosAsignar,usuariosBuscar;
    private Integer planAsignar,convocatoriaAsignar,planModificar,convocatoriaModificar;
    private List<Object[]> planes=new ArrayList<>(),convocatoriasAsignar=new ArrayList<>(),convocatoriasModificar=new ArrayList<>();
    private List<AsignacionPlanUsuarioDTO> asignaciones=new ArrayList<>(),seleccion=new ArrayList<>();
    @PostConstruct public void iniciar(){try{planes=servicio.planes();}catch(Exception e){error(e);}}
    public void cambiarPlanAsignar(){convocatoriaAsignar=null;try{convocatoriasAsignar=servicio.convocatorias(planAsignar);}catch(Exception e){error(e);}}
    public void cambiarPlanModificar(){Integer anterior=convocatoriaModificar;convocatoriaModificar=null;try{convocatoriasModificar=servicio.convocatorias(planModificar);
        if(anterior!=null && convocatoriasModificar.stream().anyMatch(c->((Number)c[0]).intValue()==anterior))convocatoriaModificar=anterior;
        }catch(Exception e){error(e);}}
    public void asignar(){
        try {int cantidad=servicio.asignar(usuariosAsignar,planAsignar,convocatoriaAsignar,actor());mensaje(FacesMessage.SEVERITY_INFO,"asignado",cantidad);usuariosAsignar=null;}
        catch(Exception e){error(e);}
    }
    public void buscar(){
        asignaciones.clear();seleccion.clear();planModificar=null;convocatoriaModificar=null;convocatoriasModificar.clear();
        try{asignaciones=servicio.buscar(usuariosBuscar);seleccion=new ArrayList<>(asignaciones);
            if(asignaciones.size()==1){planModificar=asignaciones.get(0).getIdPlan();convocatoriasModificar=servicio.convocatorias(planModificar);convocatoriaModificar=asignaciones.get(0).getIdConvocatoria();}}
        catch(Exception e){error(e);}
    }
    public void modificar(){
        try{int cantidad=servicio.modificar(seleccion,planModificar,convocatoriaModificar,actor());
            asignaciones=servicio.buscar(asignaciones.stream().map(AsignacionPlanUsuarioDTO::getUsuario).distinct().collect(java.util.stream.Collectors.joining(",")));
            seleccion=new ArrayList<>(asignaciones);mensaje(FacesMessage.SEVERITY_INFO,"modificado",cantidad);}
        catch(Exception e){error(e);}
    }
    public boolean puedeEliminar(AsignacionPlanUsuarioDTO fila){return fila!=null;}
    public String confirmacionEliminar(AsignacionPlanUsuarioDTO fila){String clave=asignaciones.stream().filter(a->Objects.equals(a.getIdPersona(),fila.getIdPersona())).count()==1?"gw.planUsuario.confirmar.eliminarUltima":"gw.planUsuario.confirmar.eliminar";return MessageFormat.format(sistema.obtenerTexto(clave),fila.getUsuario(),fila.getPlan(),fila.getConvocatoria());}
    public void eliminar(AsignacionPlanUsuarioDTO fila){
        try{servicio.eliminarAsignacion(fila,actor());
            asignaciones.removeIf(a->Objects.equals(a.getIdAsignacion(),fila.getIdAsignacion()));
            if(!asignaciones.isEmpty())asignaciones=servicio.buscar(asignaciones.stream().map(AsignacionPlanUsuarioDTO::getUsuario).distinct().collect(java.util.stream.Collectors.joining(",")));
            seleccion=new ArrayList<>(asignaciones);
            if(asignaciones.isEmpty()){planModificar=null;convocatoriaModificar=null;convocatoriasModificar.clear();}
            mensaje(FacesMessage.SEVERITY_INFO,"eliminado");}
        catch(Exception e){error(e);}
    }
    private Long actor(){Object usuario=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstantesGestorWeb.PARAM_USUARIO_SESSION);return usuario instanceof UsuarioSessionDTO?((UsuarioSessionDTO)usuario).getIdPersona():null;}
    private void mensaje(FacesMessage.Severity severity,String clave,Object... argumentos){FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(severity,MessageFormat.format(sistema.obtenerTexto("gw.planUsuario.mensaje."+clave),argumentos),null));}
    private void error(Exception e){
        if(e instanceof ErrorAsignacionPlan){ErrorAsignacionPlan error=(ErrorAsignacionPlan)e;FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,MessageFormat.format(sistema.obtenerTexto(error.getMessage()),error.getParametros()),null));}
        else if(e instanceof org.springframework.security.access.AccessDeniedException)mensaje(FacesMessage.SEVERITY_ERROR,"permiso");
        else{LOG.error("Asignacion de plan: "+e.getClass().getSimpleName());mensaje(FacesMessage.SEVERITY_ERROR,"error");}
    }
    public String getUsuariosAsignar(){return usuariosAsignar;} public void setUsuariosAsignar(String v){usuariosAsignar=v;}
    public String getUsuariosBuscar(){return usuariosBuscar;} public void setUsuariosBuscar(String v){usuariosBuscar=v;}
    public Integer getPlanAsignar(){return planAsignar;} public void setPlanAsignar(Integer v){planAsignar=v;}
    public Integer getConvocatoriaAsignar(){return convocatoriaAsignar;} public void setConvocatoriaAsignar(Integer v){convocatoriaAsignar=v;}
    public Integer getPlanModificar(){return planModificar;} public void setPlanModificar(Integer v){planModificar=v;}
    public Integer getConvocatoriaModificar(){return convocatoriaModificar;} public void setConvocatoriaModificar(Integer v){convocatoriaModificar=v;}
    public List<Object[]> getPlanes(){return planes;}
    public List<Object[]> getConvocatoriasAsignar(){return convocatoriasAsignar;}
    public List<Object[]> getConvocatoriasModificar(){return convocatoriasModificar;}
    public List<AsignacionPlanUsuarioDTO> getAsignaciones(){return asignaciones;}
    public List<AsignacionPlanUsuarioDTO> getSeleccion(){return seleccion;} public void setSeleccion(List<AsignacionPlanUsuarioDTO> v){seleccion=v;}
    public void setServicio(AsignacionPlanUsuarioService v){servicio=v;}
    public void setSistema(SistemaBean v){sistema=v;}
}
