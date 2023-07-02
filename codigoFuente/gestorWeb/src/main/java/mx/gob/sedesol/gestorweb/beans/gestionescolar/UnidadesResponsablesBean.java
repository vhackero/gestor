package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadResponsableDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.gestionescolar.UnidadResponsableService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "unidadesResponsablesBean")
public class UnidadesResponsablesBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UnidadesResponsablesBean.class);

    @ManagedProperty(value = "#{unidadResponsableService}")
    private transient UnidadResponsableService unidadResponsableService;

    @ManagedProperty(value = "#{sistema}")
    private SistemaBean sistema;

    private transient ModelMapper modelMapper = new ModelMapper();

    private List<UnidadResponsableDTO> unidades;
    private UnidadResponsableDTO unidad = new UnidadResponsableDTO();
    private UnidadResponsableDTO unidadModificar;

    private boolean nueva;

    @PostConstruct
    public void init() {
        unidades = unidadResponsableService.findAll();
    }

    public String cargarUnidades() {
        logger.info("cargar_unidades");
        init();
        return ConstantesGestorWeb.NAVEGA_UNIDADES_RESPONSABLES;
    }

    public void nuevaUnidad() {
        logger.info("nueva unidad");
        unidad = new UnidadResponsableDTO(getUsuarioEnSession().getIdPersona());
        nueva = true;
    }

    public void cargarUnidad() {
        logger.info("cargar_unidad");
        unidad = new UnidadResponsableDTO();
        modelMapper.map(unidadModificar, unidad);

        unidad.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        unidad.setFechaActualizacion(new Date());

        nueva = false;
    }

    public void guardarUnidad() {
        logger.info("guardar_unidad");
        if (nueva) {
            almacenarUnidad();
        } else {
            actualizarUnidad();
        }
    }

    private void almacenarUnidad() {
        //GUSTAVO --BitacoraUtil.llenarBitacora(unidad, getUsuarioEnSession(), ConstantesBitacora.UNIDAD_RESPONSABLE_AGREGAR, request);
        ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.guardar(unidad);
        if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
            unidades.add(0, resultado.getDto());
            RequestContext.getCurrentInstance().execute("PF('dialogUnidad').hide();");
            if (ObjectUtils.isNotNull(resultado.getMensajes())) {
                agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
            }
        } else {
            if (ObjectUtils.isNotNull(resultado.getMensajes())) {
                agregarMsgError(resultado.getMensajes(), null, sistema);
            }
        }
    }

    private void actualizarUnidad() {
        //GUSTAVO --BitacoraUtil.llenarBitacora(unidad, getUsuarioEnSession(), ConstantesBitacora.UNIDAD_RESPONSABLE_EDITAR, request);
        ResultadoDTO<UnidadResponsableDTO> resultado = unidadResponsableService.actualizar(unidad);
        if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
            unidades.remove(unidadModificar);
            unidades.add(0, resultado.getDto());
            RequestContext.getCurrentInstance().execute("PF('dialogUnidad').hide();");
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
    public UnidadResponsableService getUnidadResponsableService() {
        return unidadResponsableService;
    }

    public void setUnidadResponsableService(UnidadResponsableService unidadResponsableService) {
        this.unidadResponsableService = unidadResponsableService;
    }

    public SistemaBean getSistema() {
        return sistema;
    }

    public void setSistema(SistemaBean sistema) {
        this.sistema = sistema;
    }

    public List<UnidadResponsableDTO> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadResponsableDTO> unidades) {
        this.unidades = unidades;
    }

    public UnidadResponsableDTO getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadResponsableDTO unidad) {
        this.unidad = unidad;
    }

    public UnidadResponsableDTO getUnidadModificar() {
        return unidadModificar;
    }

    public void setUnidadModificar(UnidadResponsableDTO unidadModificar) {
        this.unidadModificar = unidadModificar;
    }

}
