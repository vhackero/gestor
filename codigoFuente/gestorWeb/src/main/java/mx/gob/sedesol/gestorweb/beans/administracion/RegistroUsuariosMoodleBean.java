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
import mx.gob.sedesol.basegestor.service.impl.admin.RegistroUsuariosMoodleService;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@ManagedBean @ViewScoped
public class RegistroUsuariosMoodleBean implements Serializable {
    private static final long serialVersionUID=1L;
    private static final Logger LOG=Logger.getLogger(RegistroUsuariosMoodleBean.class);
    @ManagedProperty("#{registroUsuariosMoodleService}") private transient RegistroUsuariosMoodleService servicio;
    @ManagedProperty("#{sistema}") private SistemaBean sistema;
    private Integer plataforma,plan;private String trabajo,nombreUsuario;
    private List<Object[]> plataformas=new ArrayList<>(),planes=new ArrayList<>();
    @PostConstruct public void iniciar(){try{plataformas=servicio.plataformas();planes=servicio.planes();}catch(Exception e){error(e);}}
    public void registrar(){try{trabajo=servicio.iniciar(plataforma,plan,nombreUsuario,actor());}catch(Exception e){error(e);}}
    private Long actor(){Object usuario=FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstantesGestorWeb.PARAM_USUARIO_SESSION);return usuario instanceof UsuarioSessionDTO?((UsuarioSessionDTO)usuario).getIdPersona():null;}
    public EstadoRegistroMoodleDTO getEstado(){return servicio.consultar(trabajo,actor());}
    public boolean isEnCurso(){EstadoRegistroMoodleDTO e=getEstado();return e!=null && !e.isTerminado();}
    public boolean isRegistrarDeshabilitado(){return plataforma==null || isEnCurso();}
    public String getResumen(){EstadoRegistroMoodleDTO e=getEstado();if(e==null)return "";
        String clave=e.getTotal()==0?"sinPendientes":(e.isTerminado()?(e.getError()==null?"finalizado":"detenido"):"progreso");
        return MessageFormat.format(sistema.obtenerTexto("gw.registroMoodle."+clave),e.getRegistrados(),e.getReutilizados(),e.getOmitidos(),e.getFallidos(),e.getProcesados(),e.getTotal());}
    public String getErrorGeneral(){EstadoRegistroMoodleDTO e=getEstado();return e==null || e.getError()==null?"":sistema.obtenerTexto(e.getError());}
    public List<String> getErrores(){List<String> resultado=new ArrayList<>();EstadoRegistroMoodleDTO e=getEstado();if(e!=null)for(String[] fila:e.getErrores())resultado.add(MessageFormat.format(sistema.obtenerTexto("gw.registroMoodle.detalleError"),fila[0],sistema.obtenerTexto(fila[1])));return resultado;}
    private void error(Exception e){String texto;
        if(e instanceof ErrorAsignacionPlan){ErrorAsignacionPlan error=(ErrorAsignacionPlan)e;texto=MessageFormat.format(sistema.obtenerTexto(error.getMessage()),error.getParametros());}
        else if(e instanceof org.springframework.security.access.AccessDeniedException)texto=sistema.obtenerTexto("gw.registroMoodle.permiso");
        else{LOG.error("Registro Moodle: "+e.getClass().getSimpleName());texto=sistema.obtenerTexto("gw.registroMoodle.errorServicio");}
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,texto,null));
    }
    public Integer getPlataforma(){return plataforma;}public void setPlataforma(Integer v){plataforma=v;}
    public Integer getPlan(){return plan;}public void setPlan(Integer v){plan=v;}
    public String getNombreUsuario(){return nombreUsuario;}public void setNombreUsuario(String v){nombreUsuario=v;}
    public List<Object[]> getPlataformas(){return plataformas;}public List<Object[]> getPlanes(){return planes;}
    public void setServicio(RegistroUsuariosMoodleService v){servicio=v;}public void setSistema(SistemaBean v){sistema=v;}
}
