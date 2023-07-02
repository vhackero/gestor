package mx.gob.sedesol.gestorweb.beans.planesprogramas;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.primefaces.component.dialog.Dialog;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.ModuloMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelETematicaModuloMdlDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.basegestor.ws.moodle.dto.ModuleDTO;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ViewScoped
@ManagedBean
public class AsignaRecursosMdlBean extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AsignaRecursosMdlBean.class);

    @ManagedProperty(value = "#{fecServiceFacade}")
    private FECServiceFacade fecServiceFacade;

    @ManagedProperty("#{sistema}")
    private SistemaBean textosSistema;

    //Recursos Y Actividades Moodle
    private Dialog dialogRecActMoodle;
    private List<ModuloMoodleDTO> actividadesMdl;
    private List<ModuloMoodleDTO> recursosMdl;
    private Integer actividadMdlSel;
    private Integer recursoMdlSel;
    private NodoDTO temarioProgSelect;
    private boolean showPnlRecurso;
    private boolean showPnlActividad;

    private ModuloMoodleDTO actividadMdl;
    private ModuloMoodleDTO recursoMdl;

    private ModuleDTO actModuleMdl;
    private ModuleDTO recursoModuleMdl;
    private String descActMdl;
    private String descRecMdl;

    public AsignaRecursosMdlBean() {
        temarioProgSelect = (NodoDTO) getSession().getAttribute(ConstantesGestorWeb.OBJ_TEMA_PROGRAMA_SELEC);
        if (ObjectUtils.isNotNull(temarioProgSelect)) {
            getSession().removeAttribute(ConstantesGestorWeb.OBJ_TEMA_PROGRAMA_SELEC);
            actModuleMdl = new ModuleDTO();
            recursoModuleMdl = new ModuleDTO();
        }
    }

    @PostConstruct
    public void muestraPanelRecursoAct() {
        recursosMdl = getFecServiceFacade().getModuloMoodleService().buscarPorTipoDeModulo(ConstantesGestorWeb.MDL_TIPO_MODULO_RECURSOS);
        actividadesMdl = getFecServiceFacade().getModuloMoodleService().buscarPorTipoDeModulo(ConstantesGestorWeb.MDL_TIPO_MODULO_ACTIVIDADES);
        //dialogRecActMoodle.setVisible(Boolean.TRUE);
    }

    /**
     *
     */
    public void asignaActRecATema() {
        if (ObjectUtils.isNotNull(temarioProgSelect)) {
            if (ObjectUtils.isNotNull(actividadMdlSel)) {
                actividadMdl = fecServiceFacade.getModuloMoodleService().buscarPorId(actividadMdlSel);
                setShowPnlActividad(Boolean.TRUE);
            }
            if (ObjectUtils.isNotNull(recursoMdlSel)) {
                recursoMdl = fecServiceFacade.getModuloMoodleService().buscarPorId(recursoMdlSel);
                setShowPnlRecurso(Boolean.TRUE);
            }
        }
    }

    /**
     * TODO: Por qué código repetitivo?
     */
    public void guardaRelActividadTema() {
        logger.debug("persistiendo relacion actividadMdl-tema");
        if (datosRequeridosCursoMdlValidos(actModuleMdl)) {
            RelETematicaModuloMdlDTO relActMdlTema = generaRelTemaActRecursoMdl(actividadMdlSel);
            //GUSTAVO --BitacoraUtil.llenarBitacora(relActMdlTema, getUsuarioEnSession(), ConstantesBitacora.MODULO_MDL_AGREGAR, request);
            ResultadoDTO<RelETematicaModuloMdlDTO> res = fecServiceFacade.geteTematicaModuloMdlService().guardar(relActMdlTema);
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
                agregarMsgInfo(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId(), null, textosSistema);
            } else {
                agregarMsgError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO.getId(), null, textosSistema);
            }
            //TODO: Integrar servicios de Moodle
        }
    }

    /**
     *
     */
    public void guardaRelRecursoTema() {
        logger.debug("persistiendo relacion recursoMdl-tema");
        if (datosRequeridosCursoMdlValidos(recursoModuleMdl)) {
            RelETematicaModuloMdlDTO relRecursoMdlTema = generaRelTemaActRecursoMdl(recursoMdlSel);
            //GUSTAVO --BitacoraUtil.llenarBitacora(relRecursoMdlTema, getUsuarioEnSession(), ConstantesBitacora.MODULO_MDL_AGREGAR, request);
            ResultadoDTO<RelETematicaModuloMdlDTO> res = fecServiceFacade.geteTematicaModuloMdlService().guardar(relRecursoMdlTema);
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
                agregarMsgInfo(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId(), null, textosSistema);
            } else {
                agregarMsgError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO.getId(), null, textosSistema);
            }
            //TODO: Integrar servicios de Moodle
        }
    }

    /**
     * Valida Datos requeridos
     *
     * @param cursoMdl
     * @return
     */
    private boolean datosRequeridosCursoMdlValidos(ModuleDTO moduleMdl) {

        if (ObjectUtils.isNull(moduleMdl)) {
            return false;
        }

        if (ObjectUtils.isNullOrEmpty(moduleMdl.getName())) {
            return false;
        }

        return true;
    }

    /**
     *
     * @param idActRecMdlSel
     * @return
     */
    private RelETematicaModuloMdlDTO generaRelTemaActRecursoMdl(Integer idActRecMdlSel) {

        RelETematicaModuloMdlDTO relTemaActRecMdl = new RelETematicaModuloMdlDTO();
        relTemaActRecMdl.setActivo(ConstantesGestorWeb.INACTIVO);
        relTemaActRecMdl.setFechaRegistro(new Date());
        relTemaActRecMdl.setIdEstructuraTematica(temarioProgSelect.getId());
        relTemaActRecMdl.setIdModuloMdl(idActRecMdlSel);
        relTemaActRecMdl.setUsuarioModifico(getUsuarioEnSession().getIdPersona());

        return relTemaActRecMdl;
    }

    /**
     *
     */
    public void onSelecActividadMdl() {
        if (ObjectUtils.isNotNull(actividadMdlSel)) {
            for (ModuloMoodleDTO actMdl : actividadesMdl) {
                if (actMdl.getId().equals(actividadMdlSel)) {
                    descActMdl = actMdl.getDescripcion();
                    break;
                }
            }
        }
    }

    /**
     *
     */
    public void onSelecRecursoMdl() {
        if (ObjectUtils.isNotNull(recursoMdlSel)) {
            for (ModuloMoodleDTO recMdl : recursosMdl) {
                if (recMdl.getId().equals(recursoMdlSel)) {
                    descRecMdl = recMdl.getDescripcion();
                    break;
                }
            }
        }
    }

    /**
     * @return the dialogRecActMoodle
     */
    public Dialog getDialogRecActMoodle() {
        return dialogRecActMoodle;
    }

    /**
     * @param dialogRecActMoodle the dialogRecActMoodle to set
     */
    public void setDialogRecActMoodle(Dialog dialogRecActMoodle) {
        this.dialogRecActMoodle = dialogRecActMoodle;
    }

    /**
     * @return the actividadesMdl
     */
    public List<ModuloMoodleDTO> getActividadesMdl() {
        return actividadesMdl;
    }

    /**
     * @param actividadesMdl the actividadesMdl to set
     */
    public void setActividadesMdl(List<ModuloMoodleDTO> actividadesMdl) {
        this.actividadesMdl = actividadesMdl;
    }

    /**
     * @return the recursosMdl
     */
    public List<ModuloMoodleDTO> getRecursosMdl() {
        return recursosMdl;
    }

    /**
     * @param recursosMdl the recursosMdl to set
     */
    public void setRecursosMdl(List<ModuloMoodleDTO> recursosMdl) {
        this.recursosMdl = recursosMdl;
    }

    /**
     * @return the actividadMdlSel
     */
    public Integer getActividadMdlSel() {
        return actividadMdlSel;
    }

    /**
     * @param actividadMdlSel the actividadMdlSel to set
     */
    public void setActividadMdlSel(Integer actividadMdlSel) {
        this.actividadMdlSel = actividadMdlSel;
    }

    /**
     * @return the recursoMdlSel
     */
    public Integer getRecursoMdlSel() {
        return recursoMdlSel;
    }

    /**
     * @param recursoMdlSel the recursoMdlSel to set
     */
    public void setRecursoMdlSel(Integer recursoMdlSel) {
        this.recursoMdlSel = recursoMdlSel;
    }

    /**
     * @return the fecServiceFacade
     */
    public FECServiceFacade getFecServiceFacade() {
        return fecServiceFacade;
    }

    /**
     * @param fecServiceFacade the fecServiceFacade to set
     */
    public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
        this.fecServiceFacade = fecServiceFacade;
    }

    /**
     * @return the temarioProgSelect
     */
    public NodoDTO getTemarioProgSelect() {
        return temarioProgSelect;
    }

    /**
     * @param temarioProgSelect the temarioProgSelect to set
     */
    public void setTemarioProgSelect(NodoDTO temarioProgSelect) {
        this.temarioProgSelect = temarioProgSelect;
    }

    /**
     * @return the actividadMdl
     */
    public ModuloMoodleDTO getActividadMdl() {
        return actividadMdl;
    }

    /**
     * @param actividadMdl the actividadMdl to set
     */
    public void setActividadMdl(ModuloMoodleDTO actividadMdl) {
        this.actividadMdl = actividadMdl;
    }

    /**
     * @return the recursoMdl
     */
    public ModuloMoodleDTO getRecursoMdl() {
        return recursoMdl;
    }

    /**
     * @param recursoMdl the recursoMdl to set
     */
    public void setRecursoMdl(ModuloMoodleDTO recursoMdl) {
        this.recursoMdl = recursoMdl;
    }

    /**
     * @return the showPnlRecurso
     */
    public boolean isShowPnlRecurso() {
        return showPnlRecurso;
    }

    /**
     * @param showPnlRecurso the showPnlRecurso to set
     */
    public void setShowPnlRecurso(boolean showPnlRecurso) {
        this.showPnlRecurso = showPnlRecurso;
    }

    /**
     * @return the showPnlActividad
     */
    public boolean isShowPnlActividad() {
        return showPnlActividad;
    }

    /**
     * @param showPnlActividad the showPnlActividad to set
     */
    public void setShowPnlActividad(boolean showPnlActividad) {
        this.showPnlActividad = showPnlActividad;
    }

    /**
     * @return the descActMdl
     */
    public String getDescActMdl() {
        return descActMdl;
    }

    /**
     * @param descActMdl the descActMdl to set
     */
    public void setDescActMdl(String descActMdl) {
        this.descActMdl = descActMdl;
    }

    /**
     * @return the descRecMdl
     */
    public String getDescRecMdl() {
        return descRecMdl;
    }

    /**
     * @param descRecMdl the descRecMdl to set
     */
    public void setDescRecMdl(String descRecMdl) {
        this.descRecMdl = descRecMdl;
    }

    /**
     * @return the actModuleMdl
     */
    public ModuleDTO getActModuleMdl() {
        return actModuleMdl;
    }

    /**
     * @param actModuleMdl the actModuleMdl to set
     */
    public void setActModuleMdl(ModuleDTO actModuleMdl) {
        this.actModuleMdl = actModuleMdl;
    }

    /**
     * @return the recursoModuleMdl
     */
    public ModuleDTO getRecursoModuleMdl() {
        return recursoModuleMdl;
    }

    /**
     * @param recursoModuleMdl the recursoModuleMdl to set
     */
    public void setRecursoModuleMdl(ModuleDTO recursoModuleMdl) {
        this.recursoModuleMdl = recursoModuleMdl;
    }

    /**
     * @return the textosSistema
     */
    public SistemaBean getTextosSistema() {
        return textosSistema;
    }

    /**
     * @param textosSistema the textosSistema to set
     */
    public void setTextosSistema(SistemaBean textosSistema) {
        this.textosSistema = textosSistema;
    }
}
