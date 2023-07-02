/**
 *
 */
package mx.gob.sedesol.gestorweb.beans.administracion.encuestas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.CatEncuestaContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.PreguntaEncuestaDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatEncuestasYEvaluacionesEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaContexto;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaEstatus;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaObjetivo;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.encuestas.PreguntaEncuestaService;
import mx.gob.sedesol.basegestor.springinit.EncuestaServiceAdapter;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;

/**
 *
 * @author jhcortes
 */
@ViewScoped
@ManagedBean(name = "encuestaBean")
public class EncuestaBean extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EncuestaBean.class);

    @ManagedProperty(value = "#{encuestaService}")
    private EncuestaService encuestaService;

    @ManagedProperty(value = "#{encuestaServiceAdapter}")
    private EncuestaServiceAdapter encuestaServiceAdapter;

    @ManagedProperty(value = "#{preguntaEncuestaService}")
    private PreguntaEncuestaService preguntaEncuestaService;

    private List<CatalogoComunDTO> lstCatTipo;
    private List<CatalogoComunDTO> lstContexto;
    private List<CatalogoComunDTO> lstObjetivo;
    private List<CatalogoComunDTO> lstEstado;
    private List<EncuestaDTO> lstEncuesta;
    private EncuestaDTO encuestaSel;
    private EncuestaDTO criterioEncuesta;

    /* renderizar pantalla de edicion */
    private boolean renderEditar;
    /* renderizar campos para el caso de clonacion */
    private boolean renderDuplicar;
    /* renderizar la pantalla de ver encuesta */
    private boolean renderVista;
    /* renderizar la pantalla de responder encuesta */
    private boolean renderResponder;
    /* render dialog */
    private boolean renderDialog;
    /* boton de mensajes dialog */
    private boolean renderBtnSuccess;

    private String encuestaMSG;
    private EncuestaDTO encuesta;
    private Boolean renderCmpEncuestas;

    public EncuestaBean() {
        setLocalVariables();
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void preCargar() {

        lstEncuesta = getSession().getAttribute(ConstantesGestorWeb.OBJ_NUEVA_CONSULTA_ENCUESTA) == null
                ? new ArrayList<>()
                : (List<EncuestaDTO>) getSession().getAttribute(ConstantesGestorWeb.OBJ_NUEVA_CONSULTA_ENCUESTA);
        if (ObjectUtils.isNullOrEmpty(lstEncuesta)) {
            getSession().removeAttribute(ConstantesGestorWeb.OBJ_NUEVA_CONSULTA_ENCUESTA);
        }

        preparaConsulta();
        encuestasPorOrdenEstatus();
    }

    private void setLocalVariables() {
        lstEstado = new ArrayList<>();
        lstContexto = new ArrayList<>();
        lstCatTipo = new ArrayList<>();
        lstObjetivo = new ArrayList<>();

        encuesta = new EncuestaDTO();
        encuesta.setEncuestaEstatus(new CatalogoComunDTO());
        encuesta.setEncuestaObjetivo(new CatalogoComunDTO());
        encuesta.setEncuestaTipo(new CatalogoComunDTO());
        encuesta.getEncuestaTipo().setContexto(new CatEncuestaContextoDTO());

        encuestaSel = new EncuestaDTO();
        encuestaSel.setEncuestaEstatus(new CatalogoComunDTO());
        encuestaSel.setEncuestaObjetivo(new CatalogoComunDTO());
        encuestaSel.setEncuestaTipo(new CatalogoComunDTO());
        encuestaSel.getEncuestaTipo().setContexto(new CatEncuestaContextoDTO());

        criterioEncuesta = new EncuestaDTO();
        criterioEncuesta.setEncuestaEstatus(new CatalogoComunDTO());
        criterioEncuesta.setEncuestaObjetivo(new CatalogoComunDTO());
        criterioEncuesta.setEncuestaTipo(new CatalogoComunDTO());
        criterioEncuesta.getEncuestaTipo().setContexto(new CatEncuestaContextoDTO());

        encuestaMSG = "";
        setRenderDialog(Boolean.FALSE);
    }

    /**
     * Recupera la encuesta con su preguntas, por medio del idEncuesta.
     */
    public String navegaEditarEncuesta() {

        /* cargar a sesion la encuesta */
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC, encuestaSel);

        setRenderDuplicar(Boolean.FALSE);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_DUPLICAR, renderDuplicar);
        /* cargar a sesion variable de edicion */
        setRenderEditar(Boolean.TRUE);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_EDITAR, renderEditar);

        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_CONTEXTO, lstContexto);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_TIPO, lstCatTipo);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_ESTADO, lstEstado);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_OBJETIVO, lstObjetivo);

        return ConstantesGestorWeb.NAVEGA_REGISTRA_ENCUESTA;
    }

    /**
     * Obtiene la encuesta y limpia los datos que no podran ser duplicados
     */
    public String clonarEncuesta() {
        EncuestaDTO encuestaSeleccionada = encuestaSel;
        encuestaSeleccionada.setId(null);
        encuestaSeleccionada.setNombre("");
        encuestaSeleccionada.setClave("");
        encuestaSeleccionada.setComentarios("");
        encuestaSeleccionada.setNumeroRevision(null);


        /* cargar a sesion la encuesta */
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC, encuestaSeleccionada);

        /* cargar a sesion variable de edicion */
        setRenderEditar(Boolean.TRUE);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_EDITAR, renderEditar);
        /**/
        setRenderDuplicar(Boolean.TRUE);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_DUPLICAR, renderDuplicar);
        return ConstantesGestorWeb.NAVEGA_REGISTRA_ENCUESTA;
    }

    /**
     * Consulta del catalogo de estado de encuesta filtrado por kos que estan
     * activos.
     */
    private void obtenerCatEstado() {
        try {
            @SuppressWarnings("unchecked")
            List<CatalogoComunDTO> lstAux = getEncuestaServiceAdapter()
                    .getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_ESTATUS)
                    .findAll(CatEncuestaEstatus.class);
            lstEstado = filtraComponentesActivos(lstAux);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Elimiar encuesta, se elimina la encuesta por medio del idEncuesta.
     * Eliminacion fisica.
     */
    public void eliminarEncuesta() {
        if (ObjectUtils.isNotNull(encuestaSel)) {
            if (encuestaSel.getEncuestaEstatus().getId() <= ConstantesGestorWeb.ESTADO_ENCUESTA_DEFAULT) {

                encuestaSel.setFechaActualizacion(new Date());
                ResultadoDTO<EncuestaDTO> resultado = eliminar();

                if (resultado != null) {
                    if (resultado.getMensajeError() == null) {
                        encuestaMSG = resultado.getResultado().name();
                    } else {
                        encuestaMSG = resultado.getResultado().name();
                        agregarMsgError(encuestaMSG, null);
                    }
                }

            } else {
                encuestaMSG = "Únicamente se puede borrar une encuesta en el estado de creación por defecto.";
            }
            setRenderDialog(Boolean.TRUE);
            agregarMsgInfo(encuestaMSG, null);
        }
        encuestasPorOrdenEstatus();/* finaliza con nueva busqueda */
    }

    /**
     * @return
     */
    private ResultadoDTO<EncuestaDTO> eliminar() {

        ResultadoDTO<EncuestaDTO> res = new ResultadoDTO<EncuestaDTO>();
        try {

            List<PreguntaEncuestaDTO> lst = encuestaSel.getPreguntasEncuesta();
            if (!ObjectUtils.isNullOrEmpty(encuestaSel)) {
                for (PreguntaEncuestaDTO pregunta : lst) {
                    pregunta.setEncuesta(null);
                    //GUSTAVO --BitacoraUtil.llenarBitacora(pregunta, getUsuarioEnSession(), "PREGUNTA_ELIMINAR", request);
                    //TODO: Por qué se manda a eliminar la pregunta dos veces?
                    preguntaEncuestaService.eliminar(pregunta);
                    preguntaEncuestaService.eliminar(pregunta);
                }
                //GUSTAVO --BitacoraUtil.llenarBitacora(encuestaSel, getUsuarioEnSession(), "ENCUESTA_ELIMINAR", request);
                res = encuestaService.eliminar(encuestaSel);
            }

        } catch (Exception e) {
            res.setMensajeError(MensajesErrorEnum.ERROR_ELIMINAR_DATOS);
            logger.error(res.getMensajeError());
        }
        return res;
    }

    /**
     * Carga los catalogos de los itemSelect
     */
    private void preparaConsulta() {
        /* Obtener los catalogos */
        obtenerCatContexto();
        obtenerCatEstado();
        obtenerCatObjetivo();
        // getAllCatTipoEncuesta();
    }

    /**
     * Consulta de tipos de encuesta por contexto seleccionado
     *
     * @param event
     */
    public void cargarCatTipo(AjaxBehaviorEvent event) {
        logger.info("dato: " + criterioEncuesta.getEncuestaTipo().getContexto().getId());
        lstCatTipo = obtenerCatTipoEncuesta();
    }

    /**
     * Obtener listado de componentes de tipo de encuesta filtrados por los
     * activos
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<CatalogoComunDTO> obtenerCatTipoEncuesta() {
        List<CatalogoComunDTO> lista = new ArrayList<>();
        try {
            if (ObjectUtils.isNotNull(criterioEncuesta.getEncuestaTipo().getContexto().getId())) {
                lista = getEncuestaServiceAdapter()
                        .getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_TIPO)
                        .buscarPorContexto(criterioEncuesta.getEncuestaTipo().getContexto().getId().longValue(),
                                CatEncuestaTipo.class);

                lista = EncuestaBean.filtraComponentesActivos(lista);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Metodo de consulta de encuesta, mediante los atributos para considerarlos
     * como criterios de busqueda.
     */
    public void buscarPorCriterio(ActionEvent actionEvent) {
        encuestasPorCriterio();
    }

    /**
     * @param actionEvent
     */
    public void limpiarFormulario(ActionEvent actionEvent) {
        criterioEncuesta = new EncuestaDTO();
        criterioEncuesta.setEncuestaEstatus(new CatalogoComunDTO());
        criterioEncuesta.setEncuestaObjetivo(new CatalogoComunDTO());
        criterioEncuesta.setEncuestaTipo(new CatalogoComunDTO());
        criterioEncuesta.getEncuestaTipo().setContexto(new CatEncuestaContextoDTO());
    }

    /**
     *
     */
    public void encuestasPorCriterio() {
        try {
            lstEncuesta = new ArrayList<EncuestaDTO>();
            lstEncuesta = encuestaService.buscarPorCriterios(criterioEncuesta);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     *
     */
    public void encuestasPorOrdenEstatus() {
        try {
            lstEncuesta = new ArrayList<EncuestaDTO>();
            lstEncuesta = encuestaService.buscarTodosOrdenEstatus();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Filtra los componentes obtenidos de los catalogos comun y obtiene solo
     * los componentes activos (atributo activo != 0)
     *
     * @param lstAux
     * @return
     */
    public static List<CatalogoComunDTO> filtraComponentesActivos(List<CatalogoComunDTO> lstAux) {
        if (ObjectUtils.isNotNull(lstAux)) {
            List<CatalogoComunDTO> lstActivos = new ArrayList<CatalogoComunDTO>();
            for (int i = 0; i < lstAux.size(); i++) {
                if (lstAux.get(i).getActivo() > 0) {
                    lstActivos.add(lstAux.get(i));
                }
            }
            return lstActivos;
        }
        return null;
    }

    /**
     * Obtiene catalogo de contexto
     */
    @SuppressWarnings("unchecked")
    public List<CatalogoComunDTO> obtenerCatContexto() {
        List<CatalogoComunDTO> lista = null;
        try {
            List<CatalogoComunDTO> lstAux = getEncuestaServiceAdapter()
                    .getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_CONTEXTO)
                    .findAll(CatEncuestaContexto.class);

            lstContexto = filtraComponentesActivos(lstAux);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Obtener el contenido del catalago de objetivo . de aque grupo va dirigida
     * la encuesta
     */
    @SuppressWarnings("unchecked")
    private void obtenerCatObjetivo() {
        try {
            List<CatalogoComunDTO> lstAux = getEncuestaServiceAdapter()
                    .getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_OBJETIVO)
                    .findAll(CatEncuestaObjetivo.class);

            lstObjetivo = filtraComponentesActivos(lstAux);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Setea las variables para poder visualizar la encuesta
     *
     * @return
     */
    public String navegaVerEncuesta() {

        setRenderVista(Boolean.TRUE);
        setRenderResponder(Boolean.FALSE);

        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC, encuestaSel);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_VISTA, renderVista);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_RESPONDER, renderResponder);

        return ConstantesGestorWeb.NAVEGA_DETALLE_ENCUESTA;
    }

    /**
     * setea las variables para poder responder la encuesta
     */
    public String navegaRespondeEncuesta() {

        setRenderVista(Boolean.FALSE);
        setRenderResponder(Boolean.TRUE);

        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC, encuestaSel);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_VISTA, renderVista);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_RESPONDER, renderResponder);

        return ConstantesGestorWeb.NAVEGA_DETALLE_ENCUESTA;
    }

    /**
     * Registrar una nueva encuesta
     *
     * @return
     */
    public String navegaRegistraEncuesta() {
        setRenderEditar(Boolean.FALSE);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_EDITAR, renderEditar);
        setRenderDialog(Boolean.FALSE);
        getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_DUPLICAR, renderDuplicar);
        return ConstantesGestorWeb.NAVEGA_REGISTRA_ENCUESTA;
    }

    /**
     *
     */
    public void closeMSGDialog() {
        setRenderDialog(Boolean.FALSE);
    }

    /* comienzo de getters y setters */
    /**
     * @return the encuestaService
     */
    public EncuestaService getEncuestaService() {
        return encuestaService;
    }

    public PreguntaEncuestaService getPreguntaEncuestaService() {
        return preguntaEncuestaService;
    }

    public void setPreguntaEncuestaService(PreguntaEncuestaService preguntaEncuestaService) {
        this.preguntaEncuestaService = preguntaEncuestaService;
    }

    /**
     * @return the renderCmpEncuestas
     */
    public Boolean getRenderCmpEncuestas() {
        return renderCmpEncuestas;
    }

    /**
     * @param renderCmpEncuestas the renderCmpEncuestas to set
     */
    public void setRenderCmpEncuestas(Boolean renderCmpEncuestas) {
        this.renderCmpEncuestas = renderCmpEncuestas;
    }

    /**
     * @return the lstCatTipo
     */
    public List<CatalogoComunDTO> getLstCatTipo() {
        if (lstCatTipo == null) {
            lstCatTipo = new ArrayList<CatalogoComunDTO>();
        }
        return lstCatTipo;
    }

    /**
     * @param lstCatTipo the lstCatTipo to set
     */
    public void setLstCatTipo(List<CatalogoComunDTO> lstCatTipo) {
        this.lstCatTipo = lstCatTipo;
    }

    /**
     * @return the lstObjetivo
     */
    public List<CatalogoComunDTO> getLstObjetivo() {
        if (lstObjetivo == null) {
            lstObjetivo = new ArrayList<CatalogoComunDTO>();
        }
        return lstObjetivo;
    }

    /**
     * @param lstObjetivo the lstObjetivo to set
     */
    public void setLstObjetivo(List<CatalogoComunDTO> lstObjetivo) {
        this.lstObjetivo = lstObjetivo;
    }

    /**
     * @return the lstContexto
     */
    public List<CatalogoComunDTO> getLstContexto() {
        return lstContexto;
    }

    /**
     * @param lstContexto the lstContexto to set
     */
    public void setLstContexto(List<CatalogoComunDTO> lstContexto) {
        if (lstContexto == null) {
            lstContexto = new ArrayList<CatalogoComunDTO>();
        }
        this.lstContexto = lstContexto;
    }

    /**
     * @return the lstEncuesta
     */
    public List<EncuestaDTO> getLstEncuesta() {
        return lstEncuesta;
    }

    /**
     * @param lstEncuesta the lstEncuesta to set
     */
    public void setLstEncuesta(List<EncuestaDTO> lstEncuesta) {
        this.lstEncuesta = lstEncuesta;
    }

    /**
     * @param encuestaService the encuestaService to set
     */
    public void setEncuestaService(EncuestaService encuestaService) {
        this.encuestaService = encuestaService;
    }

    /**
     * @return the encuestaServiceAdapter
     */
    public EncuestaServiceAdapter getEncuestaServiceAdapter() {
        return encuestaServiceAdapter;
    }

    /**
     * @param encuestaServiceAdapter the encuestaServiceAdapter to set
     */
    public void setEncuestaServiceAdapter(EncuestaServiceAdapter encuestaServiceAdapter) {
        this.encuestaServiceAdapter = encuestaServiceAdapter;
    }

    /**
     * @return the encuesta
     */
    public EncuestaDTO getEncuesta() {

        return encuesta;
    }

    /**
     * @param encuesta the encuesta to set
     */
    public void setEncuesta(EncuestaDTO encuesta) {
        this.encuesta = encuesta;
    }

    /**
     * @return the encuestaSel
     */
    public EncuestaDTO getEncuestaSel() {
        if (ObjectUtils.isNull(encuesta)) {
            encuesta = new EncuestaDTO();
            encuesta.setEncuestaEstatus(new CatalogoComunDTO());
            encuesta.setEncuestaObjetivo(new CatalogoComunDTO());
            encuesta.setEncuestaTipo(new CatalogoComunDTO());
            encuesta.getEncuestaTipo().setContexto(new CatEncuestaContextoDTO());
        }
        return encuestaSel;
    }

    /**
     * @param encuestaSel the encuestaSel to set
     */
    public void setEncuestaSel(EncuestaDTO encuestaSel) {
        this.encuestaSel = encuestaSel;
    }

    /**
     * @return
     */
    public boolean isRenderEditar() {
        if (ObjectUtils.isNull(encuestaSel)) {
            encuestaSel = new EncuestaDTO();
            encuestaSel.setEncuestaEstatus(new CatalogoComunDTO());
            encuestaSel.setEncuestaObjetivo(new CatalogoComunDTO());
            encuestaSel.setEncuestaTipo(new CatalogoComunDTO());
            encuestaSel.getEncuestaTipo().setContexto(new CatEncuestaContextoDTO());
        }
        return renderEditar;
    }

    public void setRenderEditar(boolean renderEditar) {
        this.renderEditar = renderEditar;
    }

    /**
     * @return
     */
    public List<CatalogoComunDTO> getLstEstado() {
        return lstEstado;
    }

    public void setLstEstado(List<CatalogoComunDTO> lstEstado) {
        this.lstEstado = lstEstado;
    }

    public boolean isRenderDialog() {
        return renderDialog;
    }

    public void setRenderDialog(boolean renderDialog) {
        this.renderDialog = renderDialog;
    }

    public String getEncuestaMSG() {
        return encuestaMSG;
    }

    public void setEncuestaMSG(String encuestaMSG) {
        this.encuestaMSG = encuestaMSG;
    }

    /**
     *
     */
    public boolean isRenderVista() {
        return renderVista;
    }

    public void setRenderVista(boolean renderVista) {
        this.renderVista = renderVista;
    }

    public boolean isRenderResponder() {
        return renderResponder;
    }

    public void setRenderResponder(boolean renderResponder) {
        this.renderResponder = renderResponder;
    }

    public boolean isRenderDuplicar() {
        return renderDuplicar;
    }

    public void setRenderDuplicar(boolean renderDuplicar) {
        this.renderDuplicar = renderDuplicar;
    }

    public boolean isRenderBtnSuccess() {
        return renderBtnSuccess;
    }

    public void setRenderBtnSuccess(boolean renderBtnSuccess) {
        this.renderBtnSuccess = renderBtnSuccess;
    }

    /**
     * @return
     */
    public EncuestaDTO getCriterioEncuesta() {
        if (ObjectUtils.isNull(criterioEncuesta)) {
            criterioEncuesta = new EncuestaDTO();
            criterioEncuesta.setEncuestaEstatus(new CatalogoComunDTO());
            criterioEncuesta.setEncuestaObjetivo(new CatalogoComunDTO());
            criterioEncuesta.setEncuestaTipo(new CatalogoComunDTO());
            criterioEncuesta.getEncuestaTipo().setContexto(new CatEncuestaContextoDTO());

        }
        return criterioEncuesta;
    }

    public void setCriterioEncuesta(EncuestaDTO criterioEncuesta) {
        this.criterioEncuesta = criterioEncuesta;
    }

}
