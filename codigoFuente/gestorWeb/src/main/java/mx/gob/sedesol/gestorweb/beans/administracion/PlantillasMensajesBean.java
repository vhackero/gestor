package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoNotificacionEnum;
import mx.gob.sedesol.basegestor.service.admin.FuncionalidadService;
import mx.gob.sedesol.basegestor.service.admin.MensajeOperacionService;
import mx.gob.sedesol.basegestor.service.admin.VariableMensajeOperacionService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "plantillasMensajesBean")
public class PlantillasMensajesBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(PlantillasMensajesBean.class);

    @ManagedProperty(value = "#{mensajeOperacionService}")
    private transient MensajeOperacionService mensajeOperacionService;

    @ManagedProperty(value = "#{variableMensajeOperacionService}")
    private transient VariableMensajeOperacionService variableMensajeOperacionService;

    @ManagedProperty(value = "#{funcionalidadService}")
    private transient FuncionalidadService funcionalidadService;

    @ManagedProperty(value = "#{sistema}")
    private SistemaBean sistema;

    private transient ModelMapper modelMapper = new ModelMapper();

    private List<MensajeOperacionDTO> mensajes;
    private MensajeOperacionDTO mensaje;
    private MensajeOperacionDTO mensajeModificar;

    private List<VariableMensajeOperacionDTO> variables;
    private VariableMensajeOperacionDTO variable = new VariableMensajeOperacionDTO();
    private VariableMensajeOperacionDTO variableModificar;

    private List<FuncionalidadDTO> funcionalidades;
    private FuncionalidadDTO funcionalidadSeleccionada = new FuncionalidadDTO();
    private FuncionalidadDTO funcionalidadFiltro = new FuncionalidadDTO();

    private boolean nuevo;

    private int indiceTabulacion;


    public void buscarFuncionalidades() {
        funcionalidades = funcionalidadService.buscarPorCriterios(funcionalidadFiltro);
    }

    public void seleccionarFuncionalidad() {
        mensajes = mensajeOperacionService.obtenerMensajesPorOperacion(funcionalidadSeleccionada.getIdFuncionalidad());
        variables = variableMensajeOperacionService
                .obtenerVariablesPorOperacion(funcionalidadSeleccionada.getIdFuncionalidad());
    }

    public void llenarTablaBusquedaDeOperaciones() {
        funcionalidadFiltro = new FuncionalidadDTO();
        funcionalidades = funcionalidadService.findAll();
    }

    public String agregarMensaje() {
        mensaje = new MensajeOperacionDTO(getUsuarioEnSession().getIdPersona(),
                funcionalidadSeleccionada);
        mensaje.setTipo(1);
        nuevo = true;
        return ConstantesGestorWeb.NAVEGA_EDICION_PLANTILLA_NOTIFICACION;
    }

    public String cargarMensaje() {
        mensaje = new MensajeOperacionDTO();
        modelMapper.map(mensajeModificar, mensaje);
        
        mensaje.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        mensaje.setFechaActualizacion(new Date());

        nuevo = false;
        return ConstantesGestorWeb.NAVEGA_EDICION_PLANTILLA_NOTIFICACION;
    }

    public String guardarMensaje() {
        ResultadoDTO<MensajeOperacionDTO> resultado;
        if (nuevo) {
            resultado = mensajeOperacionService.guardar(mensaje);
        } else {
            resultado = mensajeOperacionService.actualizar(mensaje);
        }
        if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
            seleccionarFuncionalidad();
            agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
        } else {
            agregarMsgError(resultado.getMensajes(), null, sistema);
            return ConstantesGestorWeb.NAVEGA_EDICION_PLANTILLA_NOTIFICACION;
        }
        return ConstantesGestorWeb.NAVEGA_PLANTILLAS_MENSAJES;
    }
    
    
	public String navegaPlantillasDeNotificaciones(){
		 return ConstantesGestorWeb.NAVEGA_PLANTILLAS_MENSAJES;
	}

    public String establecerPlantillaPersonalizada() {
        ResultadoDTO<MensajeOperacionDTO> resultado;
        resultado = mensajeOperacionService.establecerPlantillaPredeterminada(mensajeModificar);
        if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
            seleccionarFuncionalidad();
            agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
        } else {
            agregarMsgError(resultado.getMensajes(), null, sistema);
            return ConstantesGestorWeb.NAVEGA_EDICION_PLANTILLA_NOTIFICACION;
        }
        return ConstantesGestorWeb.NAVEGA_PLANTILLAS_MENSAJES;
    }

    public void agregarVariable() {
        variable = new VariableMensajeOperacionDTO(getUsuarioEnSession().getIdPersona(),
                funcionalidadSeleccionada);
        nuevo = true;
    }

    public void cargarVariable() {
        variable = new VariableMensajeOperacionDTO();
        modelMapper.map(variableModificar, variable);
        variable.setFuncionalidadDTO(funcionalidadSeleccionada);

        variable.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        variable.setFechaActualizacion(new Date());
        nuevo = false;
    }

    public void guardarVariable() {
        ResultadoDTO<VariableMensajeOperacionDTO> resultado;

        if (nuevo) {
            resultado = variableMensajeOperacionService.guardar(variable);
        } else {
            resultado = variableMensajeOperacionService.actualizar(variable);
        }
        if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
            seleccionarFuncionalidad();
            RequestContext.getCurrentInstance().execute("PF('amodal').hide();");
            agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
        } else {
            agregarMsgError(resultado.getMensajes(), null, sistema);
        }
    }

    /* INICIO DE GETS Y SETS */
    public TipoNotificacionEnum[] getTiposNotificacion() {
        return TipoNotificacionEnum.values();
    }

    public MensajeOperacionService getMensajeOperacionService() {
        return mensajeOperacionService;
    }

    public void setMensajeOperacionService(MensajeOperacionService mensajeOperacionService) {
        this.mensajeOperacionService = mensajeOperacionService;
    }

    public VariableMensajeOperacionService getVariableMensajeOperacionService() {
        return variableMensajeOperacionService;
    }

    public void setVariableMensajeOperacionService(VariableMensajeOperacionService variableMensajeOperacionService) {
        this.variableMensajeOperacionService = variableMensajeOperacionService;
    }

    public FuncionalidadService getFuncionalidadService() {
        return funcionalidadService;
    }

    public void setFuncionalidadService(FuncionalidadService funcionalidadService) {
        this.funcionalidadService = funcionalidadService;
    }

    public List<MensajeOperacionDTO> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<MensajeOperacionDTO> mensajes) {
        this.mensajes = mensajes;
    }

    public MensajeOperacionDTO getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeOperacionDTO mensaje) {
        this.mensaje = mensaje;
    }

    public MensajeOperacionDTO getMensajeModificar() {
        return mensajeModificar;
    }

    public void setMensajeModificar(MensajeOperacionDTO mensajeModificar) {
        this.mensajeModificar = mensajeModificar;
    }

    public List<VariableMensajeOperacionDTO> getVariables() {
        return variables;
    }

    public void setVariables(List<VariableMensajeOperacionDTO> variables) {
        this.variables = variables;
    }

    public VariableMensajeOperacionDTO getVariable() {
        return variable;
    }

    public void setVariable(VariableMensajeOperacionDTO variable) {
        this.variable = variable;
    }

    public VariableMensajeOperacionDTO getVariableModificar() {
        return variableModificar;
    }

    public void setVariableModificar(VariableMensajeOperacionDTO variableModificar) {
        this.variableModificar = variableModificar;
    }

    public List<FuncionalidadDTO> getFuncionalidades() {
        return funcionalidades;
    }

    public void setFuncionalidades(List<FuncionalidadDTO> funcionalidades) {
        this.funcionalidades = funcionalidades;
    }

    public FuncionalidadDTO getFuncionalidadSeleccionada() {
        return funcionalidadSeleccionada;
    }

    public void setFuncionalidadSeleccionada(FuncionalidadDTO funcionalidadSeleccionada) {
        this.funcionalidadSeleccionada = funcionalidadSeleccionada;
    }

    public FuncionalidadDTO getFuncionalidadFiltro() {
        return funcionalidadFiltro;
    }

    public void setFuncionalidadFiltro(FuncionalidadDTO funcionalidadFiltro) {
        this.funcionalidadFiltro = funcionalidadFiltro;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public SistemaBean getSistema() {
        return sistema;
    }

    public void setSistema(SistemaBean sistema) {
        this.sistema = sistema;
    }

    public int getIndiceTabulacion() {
        return indiceTabulacion;
    }

    public void setIndiceTabulacion(int indiceTabulacion) {
        this.indiceTabulacion = indiceTabulacion;
    }
}
