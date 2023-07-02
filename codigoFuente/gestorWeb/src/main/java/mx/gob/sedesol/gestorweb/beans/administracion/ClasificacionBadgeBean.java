package mx.gob.sedesol.gestorweb.beans.administracion;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.badges.ClasificacionBadgeDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.badges.ClasificacionBadgeService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "clasBadgeBean")
public class ClasificacionBadgeBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ClasificacionBadgeBean.class);

    @ManagedProperty(value = "#{clasificacionBadgeService}")
    private transient ClasificacionBadgeService clasificacionBadgeService;

    @ManagedProperty(value = "#{sistema}")
    private SistemaBean sistema;

    private transient ModelMapper modelMapper = new ModelMapper();

    private List<ClasificacionBadgeDTO> clasificaciones;
    private ClasificacionBadgeDTO clasificacion = new ClasificacionBadgeDTO();
    private ClasificacionBadgeDTO classModificar;
    private boolean nuevo;

    public String init() {
        clasificacion = new ClasificacionBadgeDTO();
        clasificaciones = clasificacionBadgeService.findAll();
        return ConstantesGestorWeb.NAVEGA_CLASIFICACION_BADGES;
    }

    public void nuevaClass() {
        clasificacion = new ClasificacionBadgeDTO();
        clasificacion.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        nuevo = true;
    }

    public void cargarClass() {
        clasificacion = new ClasificacionBadgeDTO();
        modelMapper.map(getClassModificar(), clasificacion);
        clasificacion.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        clasificacion.setFechaActualizacion(new Date());
        nuevo = false;
    }

    public void guardarClass() {
        if (nuevo) {
            guardarClasificacion();
        } else {
            actualizarClass();
        }
    }

    public void guardarClasificacion() {
        clasificacion.setFechaRegistro(new Date());
        clasificacion.setIdEstatus(ConstantesGestorWeb.ID_ESTATUS_DEFAULT);
        //GUSTAVO --BitacoraUtil.llenarBitacora(clasificacion, getUsuarioEnSession(), ConstantesBitacora.PARAMETRO_SISTEMA_AGREGAR, request);
        ResultadoDTO<ClasificacionBadgeDTO> resultado = clasificacionBadgeService.guardar(clasificacion);
        if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
            clasificaciones.add(0, resultado.getDto());
            RequestContext.getCurrentInstance().execute("PF('mClass').hide();");
            if (ObjectUtils.isNotNull(resultado.getMensajes())) {
                agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
            }
        } else {
            if (ObjectUtils.isNotNull(resultado.getMensajes())) {
                agregarMsgError(resultado.getMensajes(), null, sistema);
            }
        }
    }

    public void actualizarClass() {
        //GUSTAVO --BitacoraUtil.llenarBitacora(clasificacion, getUsuarioEnSession(), ConstantesBitacora.PARAMETRO_SISTEMA_EDITAR, request);
        ResultadoDTO<ClasificacionBadgeDTO> resultado = clasificacionBadgeService.actualizar(clasificacion);
        if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
            clasificaciones.remove(classModificar);
            clasificaciones.add(0, resultado.getDto());
            RequestContext.getCurrentInstance().execute("PF('mClass').hide();");
            if (ObjectUtils.isNotNull(resultado.getMensajes())) {
                agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
            }
        } else {
            if (ObjectUtils.isNotNull(resultado.getMensajes())) {
                agregarMsgError(resultado.getMensajes(), null, sistema);
            }
        }
    }

    /* INICIO DE GETS Y SETS */
    public SistemaBean getSistema() {
        return sistema;
    }

    public void setSistema(SistemaBean sistema) {
        this.sistema = sistema;
    }

    public List<ClasificacionBadgeDTO> getClasificaciones() {
        return clasificaciones;
    }

    public void setClasificaciones(List<ClasificacionBadgeDTO> clasificaciones) {
        this.clasificaciones = clasificaciones;
    }

    public ClasificacionBadgeDTO getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(ClasificacionBadgeDTO clasificacion) {
        this.clasificacion = clasificacion;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public ClasificacionBadgeService getClasificacionBadgeService() {
        return clasificacionBadgeService;
    }

    public void setClasificacionBadgeService(ClasificacionBadgeService clasificacionBadgeService) {
        this.clasificacionBadgeService = clasificacionBadgeService;
    }

    public ClasificacionBadgeDTO getClassModificar() {
        return classModificar;
    }

    public void setClassModificar(ClasificacionBadgeDTO classModificar) {
        this.classModificar = classModificar;
    }

}
