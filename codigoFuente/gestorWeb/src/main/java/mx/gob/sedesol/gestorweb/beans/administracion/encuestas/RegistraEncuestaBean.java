package mx.gob.sedesol.gestorweb.beans.administracion.encuestas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.CatEncuestaContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.PreguntaEncuestaDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatEncuestasYEvaluacionesEnum;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaContexto;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaEstatus;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaObjetivo;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.encuestas.PreguntaEncuestaService;
import mx.gob.sedesol.basegestor.springinit.EncuestaServiceAdapter;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;

/**
 * Created by jhcortes on 19/01/17.
 */
@ViewScoped
@ManagedBean
public class RegistraEncuestaBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RegistraEncuestaBean.class);

    @ManagedProperty(value = "#{encuestaService}")
    private EncuestaService encuestaService;

    @ManagedProperty(value = "#{encuestaServiceAdapter}")
    private EncuestaServiceAdapter encuestaServiceAdapter;

    @ManagedProperty(value = "#{preguntaEncuestaService}")
    private PreguntaEncuestaService preguntaEncuestaService;

    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;

    /* encuesta formulario */
    private EncuestaDTO encuesta;
    /* nueva pregunta */
    private PreguntaEncuestaDTO nuevaPregunta;
    /* pregunta seleccionada */
    private PreguntaEncuestaDTO preguntaSel;

    /* renderizar pantalla de alta */
    private boolean renderAlta;
    /* renderizar pantalla de edicion */
    private boolean renderEditar;
    /* renderizar dialogMSG */
    private boolean renderDialog;

    private boolean renderDuplicar;

    private boolean validaBean;

    /* renderizar botones de accion para flujo de navegacion */
    private boolean renderBtnSuccess;
    private boolean renderBtnError;

    private List<CatalogoComunDTO> lstCatTipo = new ArrayList<>();
    private List<CatalogoComunDTO> lstContexto = new ArrayList<>();
    private List<CatalogoComunDTO> lstObjetivo = new ArrayList<>();
    private List<CatalogoComunDTO> lstEstado = new ArrayList<>();
    private List<PreguntaEncuestaDTO> lstPreguntasEncuesta = new ArrayList<>();

    private String encuestaMSG;
    private String nombreModifico;
    /* Lista de comentarios */
    private List<String> listaComentarios = new ArrayList<>();

    /**
     *
     */
    public RegistraEncuestaBean() {
        /* Lista de comentarios*/
        String[] listaComentarios;

        renderEditar = (boolean) getSession().getAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_EDITAR);

        if (isRenderEditar()) {

            renderDuplicar = (boolean) getSession().getAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_DUPLICAR);

            if (isRenderDuplicar()) {
                setRenderAlta(Boolean.FALSE);
                setRenderEditar(Boolean.FALSE);

            }

            encuesta = (EncuestaDTO) getSession().getAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC);

            lstPreguntasEncuesta = recuperaPreguntas(encuesta);
            encuesta.setPreguntasEncuesta(lstPreguntasEncuesta);

            if (ObjectUtils.isNull(encuesta.getEncuestaEstatus())) {
                encuesta.setEncuestaEstatus(new CatalogoComunDTO());
            }

            if (ObjectUtils.isNull(encuesta.getEncuestaObjetivo())) {
                encuesta.setEncuestaObjetivo(new CatalogoComunDTO());
            }

            if (ObjectUtils.isNull(encuesta.getEncuestaTipo())) {
                encuesta.setEncuestaTipo(new CatalogoComunDTO());
                encuesta.getEncuestaTipo().setContexto(new CatEncuestaContextoDTO());
            }

            preguntaSel = new PreguntaEncuestaDTO();
            nuevaPregunta = new PreguntaEncuestaDTO();

            /*
			 * Se crea la lista de los comentarios 
             */
            String var = encuesta.getComentarios();
            try {
                listaComentarios = var.split("#");
                int i = 1;
                for (String comentario : listaComentarios) {
                    this.getListaComentarios().add(comentario);
                    i++;
                }
            } catch (Exception e) {

            }
        } else {

            encuesta = new EncuestaDTO();
            encuesta.setEncuestaEstatus(new CatalogoComunDTO());
            encuesta.setEncuestaObjetivo(new CatalogoComunDTO());
            encuesta.setEncuestaTipo(new CatalogoComunDTO());
            encuesta.getEncuestaTipo().setContexto(new CatEncuestaContextoDTO());

            setRenderAlta(Boolean.TRUE);
            setRenderEditar(Boolean.FALSE);
            setRenderDuplicar(Boolean.FALSE);

            nuevaPregunta = new PreguntaEncuestaDTO();
            preguntaSel = new PreguntaEncuestaDTO();
        }

        setRenderDialog(Boolean.FALSE);
        setRenderBtnError(Boolean.TRUE);
        setRenderBtnSuccess(Boolean.FALSE);
    }

    @PostConstruct
    public void init() {

        lstContexto = obtenerCatContexto();
        obtenerCatObjetivo();

        if (isRenderEditar() || isRenderDuplicar()) {
            lstEstado = obtenerCatEstado();
            getAllCatTipoEncuesta();

            if (!ObjectUtils.isNull(encuesta.getUsuarioModifico())) {
                PersonaDTO persona = personaService.buscarPorId(encuesta.getUsuarioModifico());
                setNombreModifico(
                        persona.getNombre() + " " + persona.getApellidoPaterno() + " " + persona.getApellidoMaterno());
            }
        }
    }

    public void finalizaMensaje() {
        if (isPreguntasValidas()) {
            encuestaMSG = "finalizar";
        } else {
            encuestaMSG = "validarPreguntas";
        }

    }

    public void borradorMensaje() {
        encuestaMSG = "borrador";
    }

    /**
     * Guardar encuesta en estado borrador.
     */
    public String guardarBorrador() {

        /* asignar estado de borrador */
        encuesta.getEncuestaEstatus().setId(ConstantesGestorWeb.ESTADO_ENCUESTA_DEFAULT);
        validaObjetosNull();

        // guardar la encuesta como borrador
        ResultadoDTO<EncuestaDTO> resultadoDTO = guardarEncuesta();

        if (hasResultado(resultadoDTO)) {

            encuesta = resultadoDTO.getDto();
            // obtener el id de la encuesta que se guardo
            // Si hay preguntas
            if (!ObjectUtils.isNullOrEmpty(lstPreguntasEncuesta)) {
                setPreguntasEncuesta(encuesta.getId());
                resultadoDTO = actualizarEncuesta();
            }

            encuestaMSG = "La encuesta está a punto de guardarse en estatus: Borrador. ¿Deseas continuar?";
            agregarMsgInfo(encuestaMSG, null);
            return navegaConsultaEncuesta();
//			return navegaConsultaEncuesta();
        } else {
            encuestaMSG = resultadoDTO.getResultado().name();
            agregarMsgError(encuestaMSG, null);
            return null;
        }

//		return null;
    }

    /**
     *
     * @return
     */
    private void validaObjetosNull() {

        if (ObjectUtils.isNullOrEmpty(encuesta.getEncuestaTipo().getId())) {
            encuesta.setEncuestaTipo(null);
        }

        if (ObjectUtils.isNullOrEmpty(encuesta.getEncuestaObjetivo().getId())) {
            encuesta.setEncuestaObjetivo(null);
        }
    }

    /**
     * Finalizar encuesta y enviar a estatus EN_REVISION, para que sea evaluada
     * y arpobada, o en su defecto enviada a correccion en estado
     * CON_COMENTARIOS.
     */
    public String finalizaEncuesta() {
        if (isPreguntasValidas()) {
            // validar minimo y maximo de preguntas
            encuesta.getEncuestaEstatus().setId(ConstantesGestorWeb.ESTADO_ENCUESTA_REVISION);
            crearEncuesta();
            return navegaConsultaEncuesta();

        } else {

            encuestaMSG = "Se sugiere que sean de 5 a 10 preguntas. La encuesta no se guardará";
            //agregarMsgError(encuestaMSG, null);
            return null;
        }
//		return null;
    }

    /**
     * Encuesta aprobada
     */
    public void publicarEncuesta() {

        encuesta.getEncuestaEstatus().setId(ConstantesGestorWeb.ESTADO_ENCUESTA_APROBADA);
        crearEncuesta();

    }

    /**
     *
     */
    private void crearEncuesta() {

        ResultadoDTO<EncuestaDTO> resultadoDTO = null;

        resultadoDTO = guardarEncuesta();

        if (hasResultado(resultadoDTO)) {

            encuesta = resultadoDTO.getDto();
            setPreguntasEncuesta(encuesta.getId());
            resultadoDTO = actualizarEncuesta();

            encuestaMSG = "La encuesta finalmente ha sido creada ¿Deseas continuar para guardar?";
            setRenderDialog(Boolean.TRUE);
            //agregarMsgInfo(encuestaMSG, null);

        }

    }

    /**
     *
     * @param resultadoDTO
     * @return
     */
    private boolean hasResultado(ResultadoDTO<EncuestaDTO> resultadoDTO) {
        /*if (ObjectUtils.isNotNull(resultadoDTO) && !ObjectUtils.isNullOrEmpty(resultadoDTO.getDto().getId()))*/
        if (ObjectUtils.isNull(resultadoDTO) || ObjectUtils.isNull(resultadoDTO.getDto()) || ObjectUtils.isNull(resultadoDTO.getDto().getId())) {
            return false;
        }
        return true;
    }

    /**
     * Guardar encuesta.
     *
     * @return
     */
    public ResultadoDTO<EncuestaDTO> guardarEncuesta() {
        if (ObjectUtils.isNullOrEmpty(encuesta.getFechaCreacion())) {
            encuesta.setFechaCreacion(new Date());
        }
        encuesta.setActivo(ConstantesGestorWeb.ACTIVO);
        encuesta.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        encuesta.setNombreUsuarioMod(getUsuarioEnSession().getUsuario());

        if (ObjectUtils.isNotNull(encuesta.getId())) {
            encuesta.setFechaActualizacion(new Date());
        }
        //GUSTAVO --BitacoraUtil.llenarBitacora(encuesta, getUsuarioEnSession(), ConstantesBitacora.ENCUESTA_AGREGAR, request);
        return encuestaService.guardar(encuesta);
    }

    /**
     * Actualiza la encuesta
     */
    private ResultadoDTO<EncuestaDTO> actualizarEncuesta() {
        encuesta.setFechaActualizacion(new Date());
        encuesta.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        /*
		 * Actualización del número de revisiones cuando el estatus es en revisión
         */
        CatalogoComunDTO cCDTO = encuesta.getEncuestaEstatus();
        if (cCDTO.getId() == 3) {
            if (encuesta.getNumeroRevision() == null) {
                encuesta.setNumeroRevision(0);
            } else {
                encuesta.setNumeroRevision(encuesta.getNumeroRevision() + 1);
            }
        }
        //GUSTAVO --BitacoraUtil.llenarBitacora(encuesta, getUsuarioEnSession(), ConstantesBitacora.ENCUESTA_ACTUALIZAR, request);
        //encuesta.setNombreUsuarioMod(getUsuarioEnSession().getUsuario());
        return encuestaService.actualizar(encuesta);
    }

    /**
     * Metodo para guardar las preguntas agregadas.
     *
     * @param idEncuesta
     */
    public void setPreguntasEncuesta(Integer idEncuesta) {
        if (lstPreguntasEncuesta != null) {
            for (PreguntaEncuestaDTO pregunta : lstPreguntasEncuesta) {
                pregunta.getEncuesta().setId(idEncuesta);
                pregunta.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
                pregunta.setFechaRegistro(new Date());
            }
            encuesta.setPreguntasEncuesta(lstPreguntasEncuesta);
        }
    }

    /**
     * Validar si contiene las preguntas minimas requeridas para poder guardar
     *
     * @return
     */
    private boolean isPreguntasValidas() {
        if (ObjectUtils.isNullOrEmpty(lstPreguntasEncuesta) || (lstPreguntasEncuesta.size() < ConstantesGestorWeb.NO_PREGUNTAS_MINIMO)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Obtiene catalogo de contexto
     *
     */
    @SuppressWarnings("unchecked")
    private List<CatalogoComunDTO> obtenerCatContexto() {
        List<CatalogoComunDTO> lista = null;
        try {
            List<CatalogoComunDTO> lstAux = getEncuestaServiceAdapter()
                    .getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_CONTEXTO)
                    .findAll(CatEncuestaContexto.class);

            lista = EncuestaBean.filtraComponentesActivos(lstAux);
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
            lstObjetivo = getEncuestaServiceAdapter()
                    .getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_OBJETIVO)
                    .findAll(CatEncuestaObjetivo.class);
            lstObjetivo = EncuestaBean.filtraComponentesActivos(lstObjetivo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Consulta de tipos de encuesta por contexto seleccionado
     *
     * @param event
     */
    public void cargarCatTipo(AjaxBehaviorEvent event) {
        lstCatTipo = obtenerCatTipoEncuesta();
        if (!ObjectUtils.isNullOrEmpty(lstCatTipo)) {
            encuesta.getEncuestaTipo().setId(lstCatTipo.get(0).getId());
        }
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
            if (ObjectUtils.isNotNull(encuesta.getEncuestaTipo().getContexto().getId())) {
                lista = getEncuestaServiceAdapter()
                        .getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_TIPO)
                        .buscarPorContexto(encuesta.getEncuestaTipo().getContexto().getId().longValue(),
                                CatEncuestaTipo.class);

                lista = EncuestaBean.filtraComponentesActivos(lista);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return lista;
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")
    private List<CatalogoComunDTO> obtenerCatEstado() {
        List<CatalogoComunDTO> lista = new ArrayList<>();
        try {
            lista = getEncuestaServiceAdapter()
                    .getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_ESTATUS)
                    .findAll(CatEncuestaEstatus.class);
            lista = EncuestaBean.filtraComponentesActivos(lista);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return lista;
    }

    /**
     *
     * @param event
     */
    public void onRowEditar(RowEditEvent event) {
        PreguntaEncuestaDTO preguntaAux = (PreguntaEncuestaDTO) event.getObject();
        for (int i = 0; i < lstPreguntasEncuesta.size(); i++) {
            if (preguntaAux.getIndex() == lstPreguntasEncuesta.get(i).getIndex()) {
                lstPreguntasEncuesta.get(i).setNombre(preguntaAux.getNombre());
            }
        }

        FacesMessage msg = new FacesMessage("Editar Pregunta", ((PreguntaEncuestaDTO) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Agregar un elemento al listado de preguntas.
     */
    public void agregarPregunta() {
        if (!nuevaPregunta.getNombre().equals("")) {
            nuevaPregunta.setFechaRegistro(new Date());
            nuevaPregunta.setUsuarioModifico(getUsuarioEnSession().getIdPersona());

            int index;

            if (!ObjectUtils.isNullOrEmpty(lstPreguntasEncuesta)) {
                index = lstPreguntasEncuesta.size();
            } else {
                index = 0;
            }

            if (ObjectUtils.isNull(lstPreguntasEncuesta)) {
                lstPreguntasEncuesta = new ArrayList<>();
            }

            nuevaPregunta.setIndex(index + 1);

            lstPreguntasEncuesta.add(nuevaPregunta);
            nuevaPregunta = new PreguntaEncuestaDTO();
        } else {
            logger.info("No se agrego pregunta............");
        }
    }

    /**
     * Eliminar una pregunta de la encuesta
     */
    public void eliminarPregunta() {
        List<PreguntaEncuestaDTO> lstAux = lstPreguntasEncuesta;
        lstPreguntasEncuesta = new ArrayList<>();
        int intAux = lstAux.size();

        ResultadoDTO<PreguntaEncuestaDTO> resultado = deletePregunta(preguntaSel);
        logger.info(resultado.getResultado().toString());

        for (PreguntaEncuestaDTO pregunta : lstAux) {
            if (preguntaSel.getIndex() != pregunta.getIndex()) {
                lstPreguntasEncuesta.add(pregunta);
            } else {
                lstPreguntasEncuesta.remove(pregunta);
            }
        }

        intAux = lstPreguntasEncuesta.size();
        for (int i = 0; i < intAux; i++) {
            lstPreguntasEncuesta.get(i).setIndex(i + 1);
        }

        preguntaSel = new PreguntaEncuestaDTO();
        logger.info(resultado.getResultado().toString());
    }

    private ResultadoDTO<PreguntaEncuestaDTO> deletePregunta(PreguntaEncuestaDTO preguntaSel) {
        preguntaSel.setEncuesta(null);
        //GUSTAVO --BitacoraUtil.llenarBitacora(preguntaSel, getUsuarioEnSession(), "PREGUNTA_ENCUESTA_ELIMINAR", request);
        ResultadoDTO<PreguntaEncuestaDTO> resultado = preguntaEncuestaService.eliminar(preguntaSel);
        resultado = preguntaEncuestaService.eliminar(preguntaSel);
        return resultado;
    }

    /**
     *
     * @param event
     */
    public void onRowCancelar(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición cancelada", ((PreguntaEncuestaDTO) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     */
    public String closeMSGDialog() {
        setRenderDialog(Boolean.FALSE);
        if (isRenderBtnSuccess()) {
            limpiaForm();
            return ConstantesGestorWeb.NAVEGA_ENCUESTAS;
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String navegaConsultaEncuesta() {
        limpiaForm();
        return ConstantesGestorWeb.NAVEGA_ENCUESTAS;
    }

    /**
     * Recupera las preguntas de la encuesta seleccionada para editar
     *
     * @return
     * @param encuestaSeleccionada
     */
    private List<PreguntaEncuestaDTO> recuperaPreguntas(EncuestaDTO encuestaSeleccionada) {

        if (!ObjectUtils.isNullOrEmpty(encuestaSeleccionada.getPreguntasEncuesta())) {

            List<PreguntaEncuestaDTO> lstAuxPreguntas = new ArrayList<>();

            for (int i = 0; i < encuestaSeleccionada.getPreguntasEncuesta().size(); i++) {

                encuestaSeleccionada.getPreguntasEncuesta().get(i).setIndex(i + 1);

                if (isRenderDuplicar()) {
                    encuestaSeleccionada.getPreguntasEncuesta().get(i).setId(null);
                    encuestaSeleccionada.getPreguntasEncuesta().get(i).getEncuesta().setId(null);
                }

                lstAuxPreguntas.add(encuestaSeleccionada.getPreguntasEncuesta().get(i));
            }
            return lstAuxPreguntas;

        }
        return null;
    }

    /**
     *
     */
    private void limpiaForm() {

        List<EncuestaDTO> lstEncuesta = consultaEncuestas();
        getSession().setAttribute(ConstantesGestorWeb.OBJ_NUEVA_CONSULTA_ENCUESTA, lstEncuesta);

        setRenderAlta(Boolean.FALSE);
        setRenderDuplicar(Boolean.FALSE);
        setRenderEditar(Boolean.FALSE);
        encuesta = new EncuestaDTO();
        encuestaMSG = "";
        preguntaSel = new PreguntaEncuestaDTO();
        lstPreguntasEncuesta = new ArrayList<>();
        nuevaPregunta = new PreguntaEncuestaDTO();
        getSession().removeAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_DUPLICAR);
        getSession().removeAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_EDITAR);
        getSession().removeAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC);

    }

    /**
     *
     * @return
     */
    private List<EncuestaDTO> consultaEncuestas() {
        try {
            return encuestaService.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Obtiene el catalogo completo de tipo de encuesta "nivel de enseñanza"
     */
    private void getAllCatTipoEncuesta() {
        try {

            @SuppressWarnings("unchecked")
            List<CatalogoComunDTO> lstTipoAux = getEncuestaServiceAdapter()
                    .getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_TIPO)
                    .buscarTipoEncuesta(CatEncuestaTipo.class);

            lstCatTipo = EncuestaBean.filtraComponentesActivos(lstTipoAux);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public EncuestaService getEncuestaService() {
        return encuestaService;
    }

    public void setEncuestaService(EncuestaService encuestaService) {
        this.encuestaService = encuestaService;
    }

    public EncuestaServiceAdapter getEncuestaServiceAdapter() {
        return encuestaServiceAdapter;
    }

    public void setEncuestaServiceAdapter(EncuestaServiceAdapter encuestaServiceAdapter) {
        this.encuestaServiceAdapter = encuestaServiceAdapter;
    }

    public PreguntaEncuestaService getPreguntaEncuestaService() {
        return preguntaEncuestaService;
    }

    public void setPreguntaEncuestaService(PreguntaEncuestaService preguntaEncuestaService) {
        this.preguntaEncuestaService = preguntaEncuestaService;
    }

    public EncuestaDTO getEncuesta() {
        if (ObjectUtils.isNull(encuesta)) {
            encuesta = new EncuestaDTO();
        }
        return encuesta;
    }

    public void setEncuesta(EncuestaDTO encuesta) {
        this.encuesta = encuesta;
    }

    public boolean isRenderAlta() {
        return renderAlta;
    }

    public void setRenderAlta(boolean renderAlta) {
        this.renderAlta = renderAlta;
    }

    public boolean isRenderEditar() {
        return renderEditar;
    }

    public void setRenderEditar(boolean renderEditar) {
        this.renderEditar = renderEditar;
    }

    public List<CatalogoComunDTO> getLstCatTipo() {
        return lstCatTipo;
    }

    public void setLstCatTipo(List<CatalogoComunDTO> lstCatTipo) {
        this.lstCatTipo = lstCatTipo;
    }

    public List<CatalogoComunDTO> getLstContexto() {
        return lstContexto;
    }

    public void setLstContexto(List<CatalogoComunDTO> lstContexto) {
        this.lstContexto = lstContexto;
    }

    public List<CatalogoComunDTO> getLstObjetivo() {
        return lstObjetivo;
    }

    public void setLstObjetivo(List<CatalogoComunDTO> lstObjetivo) {
        this.lstObjetivo = lstObjetivo;
    }

    public List<CatalogoComunDTO> getLstEstado() {
        return lstEstado;
    }

    public void setLstEstado(List<CatalogoComunDTO> lstEstado) {
        this.lstEstado = lstEstado;
    }

    public List<PreguntaEncuestaDTO> getLstPreguntasEncuesta() {
        return lstPreguntasEncuesta;
    }

    public void setLstPreguntasEncuesta(List<PreguntaEncuestaDTO> lstPreguntasEncuesta) {
        this.lstPreguntasEncuesta = lstPreguntasEncuesta;
    }

    public String getEncuestaMSG() {
        return encuestaMSG;
    }

    public void setEncuestaMSG(String encuestaMSG) {
        this.encuestaMSG = encuestaMSG;
    }

    public boolean isRenderBtnSuccess() {
        return renderBtnSuccess;
    }

    public void setRenderBtnSuccess(boolean renderBtnSuccess) {
        this.renderBtnSuccess = renderBtnSuccess;
    }

    public boolean isRenderBtnError() {
        return renderBtnError;
    }

    public void setRenderBtnError(boolean renderBtnError) {
        this.renderBtnError = renderBtnError;
    }

    public boolean isRenderDialog() {
        return renderDialog;
    }

    public void setRenderDialog(boolean renderDialog) {
        this.renderDialog = renderDialog;
    }

    public PreguntaEncuestaDTO getNuevaPregunta() {
        return nuevaPregunta;
    }

    public void setNuevaPregunta(PreguntaEncuestaDTO nuevaPregunta) {
        this.nuevaPregunta = nuevaPregunta;
    }

    public PreguntaEncuestaDTO getPreguntaSel() {
        return preguntaSel;
    }

    public void setPreguntaSel(PreguntaEncuestaDTO preguntaSel) {
        this.preguntaSel = preguntaSel;
    }

    public boolean isRenderDuplicar() {
        return renderDuplicar;
    }

    public void setRenderDuplicar(boolean renderDuplicar) {
        this.renderDuplicar = renderDuplicar;
    }

    public boolean isValidaBean() {
        return validaBean;
    }

    public void setValidaBean(boolean validaBean) {
        this.validaBean = validaBean;
    }

    public PersonaService getPersonaService() {
        return personaService;
    }

    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

    public String getNombreModifico() {
        return nombreModifico;
    }

    public void setNombreModifico(String nombreModifico) {
        this.nombreModifico = nombreModifico;
    }

    /**
     * @return the listaComentarios
     */
    public List<String> getListaComentarios() {
        return listaComentarios;
    }

    /**
     * @param listaComentarios the listaComentarios to set
     */
    public void setListaComentarios(List<String> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

}
