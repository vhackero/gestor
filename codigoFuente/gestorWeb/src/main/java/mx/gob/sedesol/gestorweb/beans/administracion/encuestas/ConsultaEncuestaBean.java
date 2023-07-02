/**
 *
 */
package mx.gob.sedesol.gestorweb.beans.administracion.encuestas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.PreguntaEncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.encuestas.RespuestasUsuarioService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;

/**
 * @author jhcortes Esta clase es para poder ver el detalle de la encuesta, o
 * responder la encuesta, por parte de un usuario.
 */
@ViewScoped
@ManagedBean
public class ConsultaEncuestaBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ConsultaEncuestaBean.class);

    @ManagedProperty(value = "#{encuestaService}")
    private EncuestaService encuestaService;

    @ManagedProperty(value = "#{respuestaEncuestaService}")
    private RespuestasUsuarioService respuestaUsuarioService;

    private EncuestaDTO encuestaSelDetalle;
    private List<PreguntaEncuestaDTO> preguntasEncuesta;
    private PreguntaEncuestaDTO pregunta;
    private RespuestaUsuarioDTO respuestaUsuarioDTO;
    private RespuestaDTO respuesta;
    private List<RespuestaDTO> lstRespuestas;
    private String encuestaMSG;

    private boolean renderVista;
    private boolean renderResponder;
    private boolean renderFinDialog;

    /* Lista de comentarios */
    private List<String> listaComentarios = new ArrayList<>();
    private String nuevoComentario;

    public ConsultaEncuestaBean() {

        /* Lista de comentarios*/
        String[] listaComentarios;

        logger.info("inicializando consulta encuesta");
        encuestaSelDetalle = (EncuestaDTO) getSession().getAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC);
        renderVista = (boolean) getSession().getAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_VISTA);
        renderResponder = (boolean) getSession().getAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_RESPONDER);
        setRenderFinDialog(Boolean.FALSE);

        if (!ObjectUtils.isNullOrEmpty(encuestaSelDetalle)) {

            preguntasEncuesta = encuestaSelDetalle.getPreguntasEncuesta();

            if (isRenderVista()) {
                /* responder encuesta false */
                setRenderResponder(Boolean.FALSE);

            } else {
                respuestaUsuarioDTO = new RespuestaUsuarioDTO();
                respuesta = new RespuestaDTO();
                lstRespuestas = new ArrayList<>();

                int numPreguntas = preguntasEncuesta.size();

                for (int i = 0; i < numPreguntas; i++) {
                    preguntasEncuesta.get(i).setIndex(i + 1);
                }
                if (numPreguntas > 0) {
                    pregunta = preguntasEncuesta.get(0);
                }

            }
        }

        /*
		 * Se crea la lista de los comentarios 
         */
        String var = encuestaSelDetalle.getComentarios();

        try {
            listaComentarios = var.split("#");
            int i = 1;
            for (String comentario : listaComentarios) {
                this.listaComentarios.add(comentario);
                i++;
            }
        } catch (Exception e) {

        }
    }

    /**
     * Se guarda la encuesta con comentarios para que sea revisada la encuesta y
     * modificada
     */
    public String guardaConComentarios() {
        /*Para acumular comentarios*/
        String acumulaConetario = "";

        encuestaSelDetalle.getEncuestaEstatus().setId(ConstantesGestorWeb.ESTADO_ENCUESTA_COMENTARIOS);
        /*Para acumular comentarios*/

        if (!ObjectUtils.isNullOrEmpty(encuestaSelDetalle.getComentarios()) || nuevoComentario != "") {
            if (ObjectUtils.isNullOrEmpty(encuestaSelDetalle.getComentarios())) {
                acumulaConetario = nuevoComentario;
            } else {
                acumulaConetario = encuestaSelDetalle.getComentarios() + "#" + nuevoComentario;
            }

            encuestaSelDetalle.setComentarios(acumulaConetario);;
            encuestaSelDetalle.setIdUsrMensajeDestino(encuestaSelDetalle.getUsuarioModifico());

            /*ResultadoDTO<EncuestaDTO> resultadoDTO = null;
			resultadoDTO = actualizarEncuesta();*/
            actualizarEncuesta();
            return navegaConsultaEncuesta();

        } else {
            encuestaMSG = "Se deben de agregar comentarios";
            setRenderFinDialog(Boolean.TRUE);
            return navegaConsultaEncuesta();
        }
        //agregarMsgInfo(encuestaMSG, null);

    }

    /**
     * Encuesta aprobada
     */
    public String publicarEncuesta() {
        encuestaSelDetalle.getEncuestaEstatus().setId(ConstantesGestorWeb.ESTADO_ENCUESTA_APROBADA);
        ResultadoDTO<EncuestaDTO> resultadoDTO = null;
        //actualizarEncuesta();
        resultadoDTO = actualizarEncuesta();

        return navegaConsultaEncuesta();
    }

    /**
     * Actualiza la encuesta
     */
    private ResultadoDTO<EncuestaDTO> actualizarEncuesta() {
        //GUSTAVO --BitacoraUtil.llenarBitacora(encuestaSelDetalle, getUsuarioEnSession(), ConstantesBitacora.ENCUESTA_ACTUALIZAR, request);
        encuestaSelDetalle.setFechaActualizacion(new Date());
        encuestaSelDetalle.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
        encuestaSelDetalle.setNombreUsuarioMod(getUsuarioEnSession().getUsuario());
        return encuestaService.actualizar(encuestaSelDetalle);
    }

    /**
     * Proceso de guardar las respuestas del usuario por encuesta.
     */
    public ResultadoDTO<RespuestaUsuarioDTO> guardaRespuestasEncuesta() {

        ResultadoDTO<RespuestaUsuarioDTO> resultado = new ResultadoDTO<RespuestaUsuarioDTO>();

        /* setear datos respuestaUsuarioDTO */
        //respuestaUsuarioDTO.getBitacoraDTO().setIdUsuario(getUsuarioEnSession().getIdPersona());
        respuestaUsuarioDTO.setIdEncuesta(encuestaSelDetalle.getId());
        respuestaUsuarioDTO.setFechaRegistro(new Date());
        //GUSTAVO --BitacoraUtil.llenarBitacora(encuestaSelDetalle, getUsuarioEnSession(), ConstantesBitacora.ENCUESTA_AGREGAR, request);
        resultado = respuestaUsuarioService.guardar(respuestaUsuarioDTO);

        if (resultado.getResultado().name().equals("EXITOSO")) {
            respuestaUsuarioDTO = (RespuestaUsuarioDTO) resultado.getDto();
            setRespuestasEncuesta(respuestaUsuarioDTO.getIdRespuestaUsuario());
            //No hay una funcionalidad para guardar en bitácora, usando un texto estático
            //GUSTAVO --BitacoraUtil.llenarBitacora(respuestaUsuarioDTO, getUsuarioEnSession(), "RESPUESTA_USUARIO", request);
            resultado = respuestaUsuarioService.actualizar(respuestaUsuarioDTO);
        }

        return resultado;
    }

    /**
     *
     * @param idRespuestaUsuario
     */
    private void setRespuestasEncuesta(Integer idRespuestaUsuario) {

        List<RespuestaDTO> listaRespuestas = new ArrayList<>();

        for (RespuestaDTO respuestaAux : lstRespuestas) {

            RespuestaUsuarioDTO aux = new RespuestaUsuarioDTO();
            aux.setIdRespuestaUsuario(idRespuestaUsuario);
            aux.setTblRespuestas(null);
            respuestaAux.setRelRespuestaUsuario(aux);
            listaRespuestas.add(respuestaAux);
        }
        respuestaUsuarioDTO.setTblRespuestas(listaRespuestas);
    }

    /**
     * Agrega las respuestas por cada pregunta asignada, mientras existan
     * preguntas que contestar
     *
     */
    private ResultadoDTO<?> agregaRespuesta() {
        ResultadoDTO<?> resultado = new ResultadoDTO<Object>();

        respuesta.setIdPregunta(pregunta.getId());
        int index = pregunta.getIndex();
        lstRespuestas.add(respuesta);
        respuesta = new RespuestaDTO();

        if (index < preguntasEncuesta.size()) {
            pregunta = preguntasEncuesta.get(index);

        } else {

            setRenderFinDialog(Boolean.TRUE);

            try {
                resultado = guardaRespuestasEncuesta();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return resultado;
    }

    /**
     *
     * @param actionEvent
     */
    public void respondePregunta(ActionEvent actionEvent) {
        agregaRespuesta();
    }

    /**
     *
     * @return
     */
    public String closeDialog() {
        setRenderFinDialog(Boolean.FALSE);
        return ConstantesGestorWeb.NAVEGA_ENCUESTAS;
    }

    /**
     * Cuando se ha invocado la vista de la encuesta, el flujo de navegación
     * activara el boton de regresar a la pantalla de administración de la
     * encuesta.
     *
     * @return
     */
    public String navegaConsultaEncuesta() {
        getSession().removeAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC);
        getSession().removeAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_VISTA);
        getSession().removeAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_RESPONDER);
        return ConstantesGestorWeb.NAVEGA_ENCUESTAS;
    }

    public void comentariosMensaje() {
        encuestaMSG = "comentarios";
    }

    public void aprobarMensaje() {
        encuestaMSG = "aprobar";
    }

    /**
     * @return the encuestaSelDetalle
     */
    public EncuestaDTO getEncuestaSelDetalle() {
        return encuestaSelDetalle;
    }

    /**
     * @param encuestaSelDetalle the encuestaSelDetalle to set
     */
    public void setEncuestaSelDetalle(EncuestaDTO encuestaSelDetalle) {
        this.encuestaSelDetalle = encuestaSelDetalle;
    }

    /**
     * @return the preguntasEncuesta
     */
    public List<PreguntaEncuestaDTO> getPreguntasEncuesta() {
        return preguntasEncuesta;
    }

    /**
     *
     * @param preguntasEncuesrespuestaUsuariota
     * @param preguntasEncuesta
     */
    public void setPreguntasEncuesta(List<PreguntaEncuestaDTO> preguntasEncuesrespuestaUsuariota,
            List<PreguntaEncuestaDTO> preguntasEncuesta) {
        this.preguntasEncuesta = preguntasEncuesta;
    }

    /**
     * @return the encuestaService
     */
    public EncuestaService getEncuestaService() {
        return encuestaService;
    }

    /**
     * @param encuestaService the encuestaService to set
     */
    public void setEncuestaService(EncuestaService encuestaService) {
        this.encuestaService = encuestaService;
    }

    /**
     *
     * @return
     */
    public RespuestasUsuarioService getRespuestaUsuarioService() {
        return respuestaUsuarioService;
    }

    public void setRespuestaUsuarioService(RespuestasUsuarioService respuestaUsuarioService) {
        this.respuestaUsuarioService = respuestaUsuarioService;
    }

    /**
     *
     * @return
     */
    public boolean isRenderVista() {
        return renderVista;
    }

    public void setRenderVista(boolean renderVista) {
        this.renderVista = renderVista;
    }

    /**
     *
     * @return
     */
    public boolean isRenderResponder() {
        return renderResponder;
    }

    public void setRenderResponder(boolean renderResponder) {
        this.renderResponder = renderResponder;
    }

    /**
     *
     * @return
     */
    public RespuestaUsuarioDTO getRespuestaUsuarioDTO() {
        return respuestaUsuarioDTO;
    }

    public void setRespuestaUsuarioDTO(RespuestaUsuarioDTO respuestaUsuarioDTO) {
        this.respuestaUsuarioDTO = respuestaUsuarioDTO;
    }

    public PreguntaEncuestaDTO getPregunta() {
        return pregunta;
    }

    public void setPregunta(PreguntaEncuestaDTO pregunta) {
        this.pregunta = pregunta;
    }

    public RespuestaDTO getRespuesta() {
        if (ObjectUtils.isNull(respuesta)) {
            respuesta = new RespuestaDTO();
            respuesta.setRelRespuestaUsuario(new RespuestaUsuarioDTO());
        }
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    public List<RespuestaDTO> getLstRespuestas() {
        return lstRespuestas;
    }

    public void setLstRespuestas(List<RespuestaDTO> lstRespuestas) {
        this.lstRespuestas = lstRespuestas;
    }

    public boolean isRenderFinDialog() {
        return renderFinDialog;
    }

    public void setRenderFinDialog(boolean renderFinDialog) {
        this.renderFinDialog = renderFinDialog;
    }

    public String getEncuestaMSG() {
        return encuestaMSG;
    }

    public void setEncuestaMSG(String encuestaMSG) {
        this.encuestaMSG = encuestaMSG;
    }

    /**
     * @return the listaComentarioss
     */
    public List<String> getListaComentarioss() {
        return listaComentarios;
    }

    /**
     * @param listaComentarioss the listaComentarioss to set
     */
    public void setListaComentarioss(List<String> listaComentarioss) {
        this.listaComentarios = listaComentarioss;
    }

    /**
     * @return the nuevoComentario
     */
    public String getNuevoComentario() {
        return nuevoComentario;
    }

    /**
     * @param nuevoComentario the nuevoComentario to set
     */
    public void setNuevoComentario(String nuevoComentario) {
        this.nuevoComentario = nuevoComentario;
    }

}
