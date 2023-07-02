package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProgramaSocialDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadResponsableDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.gestionescolar.ProgramaSocialService;
import mx.gob.sedesol.basegestor.service.gestionescolar.UnidadResponsableService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "programasSocialesBean")
public class ProgramasSocialesBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ProgramasSocialesBean.class);

    @ManagedProperty(value = "#{unidadResponsableService}")
    private transient UnidadResponsableService unidadResponsableService;

    @ManagedProperty(value = "#{programaSocialService}")
    private transient ProgramaSocialService programaSocialService;

    @ManagedProperty(value = "#{sistema}")
    private SistemaBean sistema;

    private transient ModelMapper modelMapper = new ModelMapper();

    private List<UnidadResponsableDTO> unidades;
    private Integer idUnidadResponsable;

    private List<ProgramaSocialDTO> programas;
    private ProgramaSocialDTO programa = new ProgramaSocialDTO();
    private ProgramaSocialDTO programaModificar;

    private boolean nueva;

    @PostConstruct
    public void init() {
        unidades = unidadResponsableService.findAll();
    }

    public String cargarProgramas() {
        logger.info("cargar__modulo_programas");
        return ConstantesGestorWeb.NAVEGA_PROGRAMAS_SOCIALES;
    }

    public void onChangeUnidad(ValueChangeEvent e) {
        logger.info("cambio_unidad");
        if (ObjectUtils.isNull(e.getNewValue())) {
            programas = new ArrayList<>();
        } else {
            Integer valor = (Integer) e.getNewValue();
            programas = programaSocialService.obtenerProgramasPorUnidad(valor);
        }
    }

    public void nuevoPrograma() {
        logger.info("nuevo_programa");
        programa = new ProgramaSocialDTO(getUsuarioEnSession().getIdPersona());
        programa.setUnidadResponsable(new UnidadResponsableDTO());
        programa.getUnidadResponsable().setIdUnidadResponsable(idUnidadResponsable);
        nueva = true;
    }

    public void cargarPrograma() {
        logger.info("cargar_programa");
        programa = new ProgramaSocialDTO();
        modelMapper.map(programaModificar, programa);

        programa.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        programa.setFechaActualizacion(new Date());

        nueva = false;
    }

    public void guardarPrograma() {
        logger.info("guardar_programa");
        if (nueva) {
            almacenarPrograma();
        } else {
            actualizarPrograma();
        }
    }

    private void almacenarPrograma() {
        //GUSTAVO --BitacoraUtil.llenarBitacora(programa, getUsuarioEnSession(), ConstantesBitacora.PROGRAMA_SOCIAL_AGREGAR, request);
        ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.guardar(programa);
        if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
            programas.add(0, resultado.getDto());
            RequestContext.getCurrentInstance().execute("PF('dialogPrograma').hide();");
            if (ObjectUtils.isNotNull(resultado.getMensajes())) {
                agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
            }
        } else {
            if (ObjectUtils.isNotNull(resultado.getMensajes())) {
                agregarMsgError(resultado.getMensajes(), null, sistema);
            }
        }
    }

    private void actualizarPrograma() {
        //GUSTAVO --BitacoraUtil.llenarBitacora(programa, getUsuarioEnSession(), ConstantesBitacora.PROGRAMA_SOCIAL_EDITAR, request);
        ResultadoDTO<ProgramaSocialDTO> resultado = programaSocialService.actualizar(programa);
        if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
            programas.remove(programaModificar);
            programas.add(0, resultado.getDto());
            RequestContext.getCurrentInstance().execute("PF('dialogPrograma').hide();");
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

    public ProgramaSocialService getProgramaSocialService() {
        return programaSocialService;
    }

    public void setProgramaSocialService(ProgramaSocialService programaSocialService) {
        this.programaSocialService = programaSocialService;
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

    public List<ProgramaSocialDTO> getProgramas() {
        return programas;
    }

    public void setProgramas(List<ProgramaSocialDTO> programas) {
        this.programas = programas;
    }

    public ProgramaSocialDTO getPrograma() {
        return programa;
    }

    public void setPrograma(ProgramaSocialDTO programa) {
        this.programa = programa;
    }

    public ProgramaSocialDTO getProgramaModificar() {
        return programaModificar;
    }

    public void setProgramaModificar(ProgramaSocialDTO programaModificar) {
        this.programaModificar = programaModificar;
    }

    public Integer getIdUnidadResponsable() {
        return idUnidadResponsable;
    }

    public void setIdUnidadResponsable(Integer idUnidadResponsable) {
        this.idUnidadResponsable = idUnidadResponsable;
    }

}
