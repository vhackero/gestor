package mx.gob.sedesol.gestorweb.beans.encuestas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.PreguntaEncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaUsuarioService;
import mx.gob.sedesol.basegestor.service.encuestas.RespuestasUsuarioService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.gestionescolar.CapturaEventoCapacitacion;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@ViewScoped
@ManagedBean(name = "contestarEncuestaBean")
public class ContestarEncuestaBean extends BaseBean {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ContestarEncuestaBean.class);

	@ManagedProperty(value = "#{encuestaService}")
	private EncuestaService encuestaService;

	@ManagedProperty(value = "#{respuestaEncuestaService}")
	private RespuestasUsuarioService respuestaUsuarioService;

	@ManagedProperty(value = "#{relEncuestaUsuarioService}")
	private RelEncuestaUsuarioService relEncuestaUsuarioService;

	private EncuestaDTO encuestaDTO;
	private List<PreguntaEncuestaDTO> listaPreguntasEncuesta;
	private PreguntaEncuestaDTO pregunta;
	private RespuestaUsuarioDTO respuestaUsuarioDTO;
	private RespuestaDTO respuesta;
	private List<RespuestaDTO> lstRespuestas;
	private boolean renderFinDialog;
	private RelEncuestaUsuarioDTO relEncuestaUsuarioDTO;

	public ContestarEncuestaBean() {
		relEncuestaUsuarioDTO = (RelEncuestaUsuarioDTO) getSession()
				.getAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC);
		setEncuestaDTO(new EncuestaDTO());
	}

	@PostConstruct
	public void init() {
		encuestaDTO = encuestaService.buscarPorId(relEncuestaUsuarioDTO.getIdEncuesta());
		listaPreguntasEncuesta = encuestaDTO.getPreguntasEncuesta();
		presentarPregunta();
	}

	public void presentarPregunta() {
		respuestaUsuarioDTO = new RespuestaUsuarioDTO();
		respuesta = new RespuestaDTO();
		lstRespuestas = new ArrayList<>();

		int numPreguntas = listaPreguntasEncuesta.size();

		for (int i = 0; i < numPreguntas; i++) {
			listaPreguntasEncuesta.get(i).setIndex(i + 1);
		}
		if (numPreguntas > 0)
			pregunta = listaPreguntasEncuesta.get(0);

	}

	/**
	 * Proceso de guardar las respuestas del usuario por encuesta.
	 */
	public ResultadoDTO<RespuestaUsuarioDTO> guardaRespuestasEncuesta() {
		ResultadoDTO<RespuestaUsuarioDTO> resultado = new ResultadoDTO<RespuestaUsuarioDTO>();

		/* setear datos respuestaUsuarioDTO */
		respuestaUsuarioDTO.setIdUsuario(getUsuarioEnSession().getIdPersona().intValue());
		respuestaUsuarioDTO.setIdEncuesta(encuestaDTO.getId());
		respuestaUsuarioDTO.setFechaRegistro(new Date());

		resultado = respuestaUsuarioService.guardar(respuestaUsuarioDTO);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			respuestaUsuarioDTO = (RespuestaUsuarioDTO) resultado.getDto();
			setRespuestasEncuesta(respuestaUsuarioDTO.getIdRespuestaUsuario());
			resultado = respuestaUsuarioService.actualizar(respuestaUsuarioDTO);
		}

		// Se pone encuesta inactiva para indicar que dicha encuenta ya está
		// contestada
		relEncuestaUsuarioDTO.setActivo(false);
		getRelEncuestaUsuarioService().actualizar(relEncuestaUsuarioDTO);

		return resultado;
	}

	/**
	 * Agrega las respuestas por cada pregunta asignada, mientras existan
	 * preguntas que contestar
	 *
	 */
	private ResultadoDTO<?> agregaRespuesta() {
		ResultadoDTO<?> resultado = new ResultadoDTO<Object>();
		respuesta.setIdPregunta(pregunta.getId());

		/*
		 * Se crea nuevo objeto de respuesta para agregarlo a la lista con el
		 * valor de cada respuesta elegida por el usuario
		 */
		RespuestaDTO respuestaParaGuardar = new RespuestaDTO();
		respuestaParaGuardar.setIdPregunta(respuesta.getIdPregunta());
		respuestaParaGuardar.setPonderacion(respuesta.getPonderacion());

		int index = pregunta.getIndex();
		lstRespuestas.add(respuestaParaGuardar);

		if (index < listaPreguntasEncuesta.size()) {
			pregunta = listaPreguntasEncuesta.get(index);

		} else {

			setRenderFinDialog(Boolean.TRUE);

			try {
				resultado = guardaRespuestasEncuesta();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		respuesta = new RespuestaDTO();
		return resultado;
	}

	public void respondePregunta() {
		agregaRespuesta();
	}

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

	public String closeDialog() {
		setRenderFinDialog(Boolean.FALSE);

		return ConstantesGestorWeb.NAVEGA_ENCUESTAS_PENDIENTES;
	}
	

	public String navegaEncuestasPendientes() {
		return ConstantesGestorWeb.NAVEGA_ENCUESTAS_PENDIENTES;
	}

	/**********************************************************************************************************************************************/
	/**
	 * @return the encuestaService
	 */
	public EncuestaService getEncuestaService() {
		return encuestaService;
	}

	/**
	 * @param encuestaService
	 *            the encuestaService to set
	 */
	public void setEncuestaService(EncuestaService encuestaService) {
		this.encuestaService = encuestaService;
	}

	/**
	 * @return the encuestaDTO
	 */
	public EncuestaDTO getEncuestaDTO() {
		return encuestaDTO;
	}

	/**
	 * @param encuestaDTO
	 *            the encuestaDTO to set
	 */
	public void setEncuestaDTO(EncuestaDTO encuestaDTO) {
		this.encuestaDTO = encuestaDTO;
	}

	/**
	 * @return the pregunta
	 */
	public PreguntaEncuestaDTO getPregunta() {
		return pregunta;
	}

	/**
	 * @param pregunta
	 *            the pregunta to set
	 */
	public void setPregunta(PreguntaEncuestaDTO pregunta) {
		this.pregunta = pregunta;
	}

	/**
	 * @return the listaPreguntasEncuesta
	 */
	public List<PreguntaEncuestaDTO> getListaPreguntasEncuesta() {
		return listaPreguntasEncuesta;
	}

	/**
	 * @param listaPreguntasEncuesta
	 *            the listaPreguntasEncuesta to set
	 */
	public void setListaPreguntasEncuesta(List<PreguntaEncuestaDTO> listaPreguntasEncuesta) {
		this.listaPreguntasEncuesta = listaPreguntasEncuesta;
	}

	/**
	 * @return the respuestaUsuarioService
	 */
	public RespuestasUsuarioService getRespuestaUsuarioService() {
		return respuestaUsuarioService;
	}

	/**
	 * @param respuestaUsuarioService
	 *            the respuestaUsuarioService to set
	 */
	public void setRespuestaUsuarioService(RespuestasUsuarioService respuestaUsuarioService) {
		this.respuestaUsuarioService = respuestaUsuarioService;
	}

	/**
	 * @return the respuestaUsuarioDTO
	 */
	public RespuestaUsuarioDTO getRespuestaUsuarioDTO() {
		return respuestaUsuarioDTO;
	}

	/**
	 * @param respuestaUsuarioDTO
	 *            the respuestaUsuarioDTO to set
	 */
	public void setRespuestaUsuarioDTO(RespuestaUsuarioDTO respuestaUsuarioDTO) {
		this.respuestaUsuarioDTO = respuestaUsuarioDTO;
	}

	/**
	 * @return the respuesta
	 */
	public RespuestaDTO getRespuesta() {
		return respuesta;
	}

	/**
	 * @param respuesta
	 *            the respuesta to set
	 */
	public void setRespuesta(RespuestaDTO respuesta) {
		this.respuesta = respuesta;
	}

	/**
	 * @return the lstRespuestas
	 */
	public List<RespuestaDTO> getLstRespuestas() {
		return lstRespuestas;
	}

	/**
	 * @param lstRespuestas
	 *            the lstRespuestas to set
	 */
	public void setLstRespuestas(List<RespuestaDTO> lstRespuestas) {
		this.lstRespuestas = lstRespuestas;
	}

	/**
	 * @return the renderFinDialog
	 */
	public boolean isRenderFinDialog() {
		return renderFinDialog;
	}

	/**
	 * @param renderFinDialog
	 *            the renderFinDialog to set
	 */
	public void setRenderFinDialog(boolean renderFinDialog) {
		this.renderFinDialog = renderFinDialog;
	}

	/**
	 * @return the relEncuestaUsuarioDTO
	 */
	public RelEncuestaUsuarioDTO getRelEncuestaUsuarioDTO() {
		return relEncuestaUsuarioDTO;
	}

	/**
	 * @param relEncuestaUsuarioDTO
	 *            the relEncuestaUsuarioDTO to set
	 */
	public void setRelEncuestaUsuarioDTO(RelEncuestaUsuarioDTO relEncuestaUsuarioDTO) {
		this.relEncuestaUsuarioDTO = relEncuestaUsuarioDTO;
	}

	/**
	 * @return the relEncuestaUsuarioService
	 */
	public RelEncuestaUsuarioService getRelEncuestaUsuarioService() {
		return relEncuestaUsuarioService;
	}

	/**
	 * @param relEncuestaUsuarioService
	 *            the relEncuestaUsuarioService to set
	 */
	public void setRelEncuestaUsuarioService(RelEncuestaUsuarioService relEncuestaUsuarioService) {
		this.relEncuestaUsuarioService = relEncuestaUsuarioService;
	}
}
