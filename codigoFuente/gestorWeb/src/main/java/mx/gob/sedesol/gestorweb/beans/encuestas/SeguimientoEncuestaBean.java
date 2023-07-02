package mx.gob.sedesol.gestorweb.beans.encuestas;

/**
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ValueChangeEvent;
import org.apache.log4j.Logger;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RespuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaUsuarioService;
import mx.gob.sedesol.basegestor.service.encuestas.RespuestasUsuarioService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.basegestor.springinit.EncuestaServiceAdapter;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.CorreoNotificacionBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;

/**
 *
 * @author jhcortes
 */
@ViewScoped
@ManagedBean(name = "seguimientoEncuestaBean")
public class SeguimientoEncuestaBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SeguimientoEncuestaBean.class);

	@ManagedProperty(value = "#{eventoCapacitacionServiceFacade}")
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	@ManagedProperty(value = "#{catalogoComunService}")
	private CatalogoComunService<CatEncuestaTipo, Integer> catalogoEncuestaTipoService;

	@ManagedProperty(value = "#{encuestaServiceAdapter}")
	private EncuestaServiceAdapter encuestaServiceAdapter;

	@ManagedProperty(value = "#{relEncuestaEventoCapacitacionService}")
	private RelEncuestaEventoCapacitacionService relEncuestaEventoCapacitacionService;

	@ManagedProperty(value = "#{respuestaEncuestaService}")
	private RespuestasUsuarioService respuestaUsuarioService;

	@ManagedProperty(value = "#{relEncuestaUsuarioService}")
	private RelEncuestaUsuarioService relEncuestaUsuarioService;

	@ManagedProperty(value = "#{notificacionSistemaService}")
	private NotificacionSistemaService notificacionSistemaService;

	private NodoeHijosDTO estPlanSedesol;
	private List<CatalogoComunDTO> listaCompetencias;
	private List<CatalogoComunDTO> listaEjesCapacitacion;
	private List<EventoCapacitacionDTO> lstEventosCapacitacion;
	private List<EncuestaDTO> lstEncuesta;
	private RelEncuestaEventoCapacitacionDTO filtros = new RelEncuestaEventoCapacitacionDTO();
	private EncuestaDTO filtrosEncuesta;
	private EventoCapacitacionDTO evento;
	private EncuestaDTO encuesta;
	private List<CatalogoComunDTO> listaModalidadesPrograma;
	private List<CatalogoComunDTO> listaTipoRangoFecha;
	private List<CatalogoComunDTO> listaTipoEncuesta;
	private Integer clTipoEncuesta;
	private Integer idEvento;
	private List<RelEncuestaEventoCapacitacionDTO> listaEncuestasEventoAsignadas;
	private List<RelGrupoParticipanteDTO> listaParticipantes;
	private List<RelGrupoParticipanteDTO> listaParticipantesPendientes;
	private String tipoDatoFechas;
	private Boolean esFechaInicialYFinalHabilitada = Boolean.FALSE;

	@ManagedProperty("#{correoNotificacionBean}")
	private CorreoNotificacionBean correoNotificacionBean;

	@PostConstruct
	public void preCargar() {

		lstEncuesta = new ArrayList<>();
		listaParticipantes = new ArrayList<>();
		generarComboBoxCompetencias();
		generaCatEjesCapacitBusqueda();
		generarComboBoxEventosCapacitacion();
		generarComboBoxModalidadPrograma();
		generarComboBoxTipoEncuesta();

	}

	private void generarComboBoxCompetencias() {
		List<NodoeHijosDTO> planes = new ArrayList<>();
		List<MallaCurricularDTO> mallas = new ArrayList<>();
		MallaCurricularDTO mallaSedesol = eventoCapacitacionServiceFacade.obtenerMallaCurricular();
		mallas.add(mallaSedesol);
		for (MallaCurricularDTO m : mallas) {
			NodoeHijosDTO nodog = new NodoeHijosDTO();
			nodog.setNombre(m.getNombre());
			nodog.setIdNodo(m.getId());
			nodog.setIdPadre(m.getMallaCurricularPadre() != null ? m.getMallaCurricularPadre().getId() : null);
			nodog.setIdObjCurr(m.getObjetoCurricular().getId());
			nodog.setNivel(0);

			if (!m.getLstHijosMallaCurr().isEmpty()) {
				this.generaCatxNivel(m.getLstHijosMallaCurr(), nodog, nodog.getNivel());
			}
			planes.add(nodog);
		}
		listaCompetencias = new ArrayList<>();
		// Genera el Catalogo Tipo de Competencia
		estPlanSedesol = planes.get(ConstantesGestorWeb.INDICE_INICIAL);
		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			listaCompetencias.add(cc);
		}
	}

	public void generarComboBoxEjesCapacitacion(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idTpoCompSel = (Integer) e.getNewValue();
			filtros.getTblEvento().getFichaDescriptivaPrograma().setTipoCompetencia(idTpoCompSel);
			listaEjesCapacitacion = new ArrayList<>();
			for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
				if (nh.getIdNodo().equals(idTpoCompSel)) {
					for (NodoeHijosDTO nint : nh.getNodosHijos()) {
						CatalogoComunDTO cc = new CatalogoComunDTO();
						cc.setId(nint.getIdNodo());
						cc.setNombre(nint.getNombre());
						listaEjesCapacitacion.add(cc);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void generarComboBoxModalidadPrograma() {
		listaModalidadesPrograma = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG);
	}

	public void limpiarCampos() {
		filtros = new RelEncuestaEventoCapacitacionDTO();
		filtros.getTblEvento().getFichaDescriptivaPrograma().setTipoCompetencia(null);
		filtros.getTblEvento().getFichaDescriptivaPrograma().setEjeCapacitacion(null);
		filtros.getTblEvento().setIdEvento(null);
		filtros.getTblEvento().getFichaDescriptivaPrograma().setNombreTentativo(null);
		filtros.getTblEvento().getFichaDescriptivaPrograma().setCatModalidad(null);
		filtros.getTblEvento().setFechaInicial(null);
		filtros.getTblEvento().setFechaFinal(null);
		listaEncuestasEventoAsignadas = new ArrayList<>();
		tipoDatoFechas = null;
	}

	private void generarComboBoxTipoEncuesta() {
		listaTipoEncuesta = (List<CatalogoComunDTO>) catalogoEncuestaTipoService.findAll(CatEncuestaTipo.class);
	}

	private void generarComboBoxEventosCapacitacion() {
		lstEventosCapacitacion = eventoCapacitacionServiceFacade.getEventoCapacitacionService()
				.obtenerTodosLosEventos();
	}

	public void buscarEventosCapacitacion() {
		if (ObjectUtils.isNotNull(getFiltros())) {
			listaEncuestasEventoAsignadas = relEncuestaEventoCapacitacionService.busquedaPorCriterios(filtros,
					tipoDatoFechas);
		}
	}

	public void encuestasPorTipo(ValueChangeEvent evento) {
		listaEncuestasEventoAsignadas = relEncuestaEventoCapacitacionService.busquedaPorCriterios(filtros,
				tipoDatoFechas);
	}

	public String obtieneNombreTpoCompetencia(Integer idTpoComp) {
		if (ObjectUtils.isNotNull(idTpoComp)) {
			for (CatalogoComunDTO tpoCom : listaCompetencias) {
				if (tpoCom.getId().equals(idTpoComp)) {
					return tpoCom.getNombre();
				}
			}
		}
		return null;
	}

	private void generaCatEjesCapacitBusqueda() {
		listaEjesCapacitacion = new ArrayList<>();
		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			for (NodoeHijosDTO nint : nh.getNodosHijos()) {
				CatalogoComunDTO cc = new CatalogoComunDTO();
				cc.setId(nint.getIdNodo());
				cc.setNombre(nint.getNombre());
				listaEjesCapacitacion.add(cc);
			}
		}
	}

	public String obtieneNombreEjeCapacit(Integer idEjeCap) {
		if (ObjectUtils.isNotNull(idEjeCap)) {
			for (CatalogoComunDTO ejeCap : listaEjesCapacitacion) {
				if (ejeCap.getId().compareTo(idEjeCap) == 0) {
					return ejeCap.getNombre();
				}
			}
		}
		return null;
	}

	public String fecharLimite(Date fecha, Integer dias) {
		return relEncuestaUsuarioService.fecharLimite(fecha, ConstantesGestorWeb.FECHA_LIMITE_REACCION);
	}

	private Boolean verificarEncuesta(Integer idEncuesta, Long idPersona) {
		Boolean existe = false;
		List<RespuestaUsuarioDTO> respuestausuarioDTO = new ArrayList<>();
		respuestausuarioDTO = getRespuestaUsuarioService().buscarRespuestasEncuestaUsuario(idPersona.intValue(),
				idEncuesta);

		if (!respuestausuarioDTO.isEmpty()) {
			existe = true;
		}
		return existe;
	}

	/***
	 * Obtiene las personas pendientes por contestar la encuesta.
	 * 
	 * @return
	 */
	public String obtenerPendientesPorContestar(RelEncuestaEventoCapacitacionDTO relEncuestaEvento) {
		int totalParticipantes = 0;
		int participantesPendientes = 0;
		List<RelGrupoParticipanteDTO> listaRelGpoPart = eventoCapacitacionServiceFacade.getGrupoParticipanteService()
				.getParticipantesByEvento(relEncuestaEvento.getIdEventoCapacitacion());
		if (!listaRelGpoPart.isEmpty()) {
			totalParticipantes = listaRelGpoPart.size();
			for (RelGrupoParticipanteDTO participante : listaRelGpoPart) {
				if (verificarEncuesta(relEncuestaEvento.getIdEncuesta(), participante.getPersona().getIdPersona())) {
					participantesPendientes++;
				}
			}
		}
		return participantesPendientes + " de " + totalParticipantes;
	}

	public List<RelGrupoParticipanteDTO> obtenerListaParticipantesPendientes(
			RelEncuestaEventoCapacitacionDTO relEncuestaEvento) {
		List<RelGrupoParticipanteDTO> participantesPendientes = new ArrayList<>();
		List<RelGrupoParticipanteDTO> listaRelGpoPart = eventoCapacitacionServiceFacade.getGrupoParticipanteService()
				.getParticipantesByEvento(relEncuestaEvento.getIdEventoCapacitacion());
		for (RelGrupoParticipanteDTO participante : listaRelGpoPart) {
			if (!verificarEncuesta(relEncuestaEvento.getIdEncuesta(), participante.getPersona().getIdPersona())) {
				participantesPendientes.add(participante);
			}
		}

		return participantesPendientes;
	}

	public String navegaDetalleEncuestadosPendientes(RelEncuestaEventoCapacitacionDTO relEncuestaEvento) {

		getSession().setAttribute(ConstantesGestorWeb.OBJ_EVENTO_SELEC, relEncuestaEvento.getTblEvento());
		getSession().setAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC, relEncuestaEvento.getTblEncuesta());
		getSession().setAttribute(ConstantesGestorWeb.OBJ_PARTICIPANTES_PENDIENTES_ENCUESTA,
				obtenerListaParticipantesPendientes(relEncuestaEvento));

		return ConstantesGestorWeb.NAVEGA_DETALLE_ENCUESTADOS_PENDIENTES;
	}

	public void enviarNotificacionesParticipantes(RelEncuestaEventoCapacitacionDTO relEncuestaEvento) {
		try {
			List<RelGrupoParticipanteDTO> participantesPendientes = obtenerListaParticipantesPendientes(
					relEncuestaEvento);
			String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_ENCUESTA_PENDIENTE;
			String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_ENCUESTA_PENDIENTE;
			correoNotificacionBean.notificarUsuariosEncuestaPendiente(claveNotificacion, claveCorreo,
					participantesPendientes, relEncuestaEvento.getTblEncuesta(), relEncuestaEvento.getTblEvento());
			agregarMsgInfo("Se notificó a los participantes.", null);
		} catch (Exception e) {
			agregarMsgInfo("No se pudo notificar a los participantes.", null);
		}

	}

	public void enviarNotificacionUsuario(Long idPersona) {
		List<VariableMensajeOperacionDTO> variables = new ArrayList<>();
		variables.add(new VariableMensajeOperacionDTO("${evento}", evento.getNombreEc()));
		variables.add(new VariableMensajeOperacionDTO("${encuesta}", encuesta.getNombre()));
		variables.add(new VariableMensajeOperacionDTO("${usuario}", getPersona(idPersona)));
		notificacionSistemaService.enviarNotificacion(ConstantesGestorWeb.NOTIFICACION_ENVIO_RECORDATORIO, idPersona,
				variables);
	}

	private String getPersona(Long idPersona) {
		for (RelGrupoParticipanteDTO participante : listaParticipantes) {
			if (participante.getPersona().getIdPersona().equals(idPersona)) {
				return participante.getPersona().getNombreCompleto();
			}
		}
		return null;
	}

	public void onChangeActivaBusquedaFechas(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			String tipoDatoFechas = (String) e.getNewValue();
			esFechaInicialYFinalHabilitada = Boolean.FALSE;
			if (ObjectUtils.isNotNull(tipoDatoFechas) && (!"".equals(tipoDatoFechas))) {
				esFechaInicialYFinalHabilitada = Boolean.TRUE;
			}
		}
	}

	/**
	 * *************************************************************************
	 * ***********************
	 */
	/* INICIO DE LOS SETTERS Y GETTERS */

	/**
	 * @return the lstEncuesta
	 */
	public List<EncuestaDTO> getLstEncuesta() {
		return lstEncuesta;
	}

	/**
	 * @param lstEncuesta
	 *            the lstEncuesta to set
	 */
	public void setLstEncuesta(List<EncuestaDTO> lstEncuesta) {
		this.lstEncuesta = lstEncuesta;
	}

	/**
	 * @return the encuestaServiceAdapter
	 */
	public EncuestaServiceAdapter getEncuestaServiceAdapter() {
		return encuestaServiceAdapter;
	}

	/**
	 * @param encuestaServiceAdapter
	 *            the encuestaServiceAdapter to set
	 */
	public void setEncuestaServiceAdapter(EncuestaServiceAdapter encuestaServiceAdapter) {
		this.encuestaServiceAdapter = encuestaServiceAdapter;
	}

	/**
	 * @return the lstEventosCapacitacion
	 */
	public List<EventoCapacitacionDTO> getLstEventosCapacitacion() {
		return lstEventosCapacitacion;
	}

	/**
	 * @param lstEventosCapacitacion
	 *            the lstEventosCapacitacion to set
	 */
	public void setLstEventosCapacitacion(List<EventoCapacitacionDTO> lstEventosCapacitacion) {
		this.lstEventosCapacitacion = lstEventosCapacitacion;
	}

	/**
	 * @return the eventoCapacitacionServiceFacade
	 */
	public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
		return eventoCapacitacionServiceFacade;
	}

	/**
	 * @param eventoCapacitacionServiceFacade
	 *            the eventoCapacitacionServiceFacade to set
	 */
	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
	}

	/**
	 * @return the listaCompetencias
	 */
	public List<CatalogoComunDTO> getListaCompetencias() {
		return listaCompetencias;
	}

	/**
	 * @param listaCompetencias
	 *            the listaCompetencias to set
	 */
	public void setListaCompetencias(List<CatalogoComunDTO> listaCompetencias) {
		this.listaCompetencias = listaCompetencias;
	}

	/**
	 * @return the listaEjesCapacitacion
	 */
	public List<CatalogoComunDTO> getListaEjesCapacitacion() {
		return listaEjesCapacitacion;
	}

	/**
	 * @param listaEjesCapacitacion
	 *            the listaEjesCapacitacion to set
	 */
	public void setListaEjesCapacitacion(List<CatalogoComunDTO> listaEjesCapacitacion) {
		this.listaEjesCapacitacion = listaEjesCapacitacion;
	}

	/**
	 * @return the listaTipoRangoFecha
	 */
	public List<CatalogoComunDTO> getListaTipoRangoFecha() {
		return listaTipoRangoFecha;
	}

	/**
	 * @param listaTipoRangoFecha
	 *            the listaTipoRangoFecha to set
	 */
	public void setListaTipoRangoFecha(List<CatalogoComunDTO> listaTipoRangoFecha) {
		this.listaTipoRangoFecha = listaTipoRangoFecha;
	}

	/**
	 * @return the listaModalidadesPrograma
	 */
	public List<CatalogoComunDTO> getListaModalidadesPrograma() {
		return listaModalidadesPrograma;
	}

	/**
	 * @param listaModalidadesPrograma
	 *            the listaModalidadesPrograma to set
	 */
	public void setListaModalidadesPrograma(List<CatalogoComunDTO> listaModalidadesPrograma) {
		this.listaModalidadesPrograma = listaModalidadesPrograma;
	}

	/**
	 * @return the clTipoEncuesta
	 */
	public Integer getClTipoEncuesta() {
		return clTipoEncuesta;
	}

	/**
	 * @param clTipoEncuesta
	 *            the clTipoEncuesta to set
	 */
	public void setClTipoEncuesta(Integer clTipoEncuesta) {
		this.clTipoEncuesta = clTipoEncuesta;
	}

	/**
	 * @return the listaTipoEncuesta
	 */
	public List<CatalogoComunDTO> getListaTipoEncuesta() {
		return listaTipoEncuesta;
	}

	/**
	 * @param listaTipoEncuesta
	 *            the listaTipoEncuesta to set
	 */
	public void setListaTipoEncuesta(List<CatalogoComunDTO> listaTipoEncuesta) {
		this.listaTipoEncuesta = listaTipoEncuesta;
	}

	/**
	 * @return the catalogoEncuestaTipoService
	 */
	public CatalogoComunService<CatEncuestaTipo, Integer> getCatalogoEncuestaTipoService() {
		return catalogoEncuestaTipoService;
	}

	/**
	 * @param catalogoEncuestaTipoService
	 *            the catalogoEncuestaTipoService to set
	 */
	public void setCatalogoEncuestaTipoService(
			CatalogoComunService<CatEncuestaTipo, Integer> catalogoEncuestaTipoService) {
		this.catalogoEncuestaTipoService = catalogoEncuestaTipoService;
	}

	/**
	 * @return the idEvento
	 */
	public Integer getIdEvento() {
		return idEvento;
	}

	/**
	 * @param idEvento
	 *            the idEvento to set
	 */
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	/**
	 * @return the filtrosEncuesta
	 */
	public EncuestaDTO getFiltrosEncuesta() {
		return filtrosEncuesta;
	}

	/**
	 * @param filtrosEncuesta
	 *            the filtrosEncuesta to set
	 */
	public void setFiltrosEncuesta(EncuestaDTO filtrosEncuesta) {
		this.filtrosEncuesta = filtrosEncuesta;
	}

	/**
	 * @return the listaEncuestasEventoAsignadas
	 */
	public List<RelEncuestaEventoCapacitacionDTO> getListaEncuestasEventoAsignadas() {
		return listaEncuestasEventoAsignadas;
	}

	/**
	 * @param listaEncuestasEventoAsignadas
	 *            the listaEncuestasEventoAsignadas to set
	 */
	public void setListaEncuestasEventoAsignadas(List<RelEncuestaEventoCapacitacionDTO> listaEncuestasEventoAsignadas) {
		this.listaEncuestasEventoAsignadas = listaEncuestasEventoAsignadas;
	}

	/**
	 * @return the relEncuestaEventoCapacitacionService
	 */
	public RelEncuestaEventoCapacitacionService getRelEncuestaEventoCapacitacionService() {
		return relEncuestaEventoCapacitacionService;
	}

	/**
	 * @param relEncuestaEventoCapacitacionService
	 *            the relEncuestaEventoCapacitacionService to set
	 */
	public void setRelEncuestaEventoCapacitacionService(
			RelEncuestaEventoCapacitacionService relEncuestaEventoCapacitacionService) {
		this.relEncuestaEventoCapacitacionService = relEncuestaEventoCapacitacionService;
	}

	/**
	 * @return the listaParticipantes
	 */
	public List<RelGrupoParticipanteDTO> getListaParticipantes() {
		return listaParticipantes;
	}

	/**
	 * @param listaParticipantes
	 *            the listaParticipantes to set
	 */
	public void setListaParticipantes(List<RelGrupoParticipanteDTO> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
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
	 * @return the evento
	 */
	public EventoCapacitacionDTO getEvento() {
		return evento;
	}

	/**
	 * @param evento
	 *            the evento to set
	 */
	public void setEvento(EventoCapacitacionDTO evento) {
		this.evento = evento;
	}

	/**
	 * @return the encuesta
	 */
	public EncuestaDTO getEncuesta() {
		return encuesta;
	}

	/**
	 * @param encuesta
	 *            the encuesta to set
	 */
	public void setEncuesta(EncuestaDTO encuesta) {
		this.encuesta = encuesta;
	}

	/**
	 * @return the tiposCompetencias
	 */
	/**
	 * @return the listaParticipantesPendientes
	 */
	public List<RelGrupoParticipanteDTO> getListaParticipantesPendientes() {
		return listaParticipantesPendientes;
	}

	/**
	 * @param listaParticipantesPendientes
	 *            the listaParticipantesPendientes to set
	 */
	public void setListaParticipantesPendientes(List<RelGrupoParticipanteDTO> listaParticipantesPendientes) {
		this.listaParticipantesPendientes = listaParticipantesPendientes;
	}

	/**
	 * @return the notificacionSistemaService
	 */
	public NotificacionSistemaService getNotificacionSistemaService() {
		return notificacionSistemaService;
	}

	/**
	 * @param notificacionSistemaService
	 *            the notificacionSistemaService to set
	 */
	public void setNotificacionSistemaService(NotificacionSistemaService notificacionSistemaService) {
		this.notificacionSistemaService = notificacionSistemaService;
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

	/**
	 * @return the tipoDatoFechas
	 */
	public String getTipoDatoFechas() {
		return tipoDatoFechas;
	}

	/**
	 * @param tipoDatoFechas
	 *            the tipoDatoFechas to set
	 */
	public void setTipoDatoFechas(String tipoDatoFechas) {
		this.tipoDatoFechas = tipoDatoFechas;
	}

	/**
	 * @return the esFechaInicialYFinalHabilitada
	 */
	public Boolean getEsFechaInicialYFinalHabilitada() {
		return esFechaInicialYFinalHabilitada;
	}

	/**
	 * @param esFechaInicialYFinalHabilitada
	 *            the esFechaInicialYFinalHabilitada to set
	 */
	public void setEsFechaInicialYFinalHabilitada(Boolean esFechaInicialYFinalHabilitada) {
		this.esFechaInicialYFinalHabilitada = esFechaInicialYFinalHabilitada;
	}

	public RelEncuestaEventoCapacitacionDTO getFiltros() {
		return filtros;
	}

	public void setFiltros(RelEncuestaEventoCapacitacionDTO filtros) {
		this.filtros = filtros;
	}

	public CorreoNotificacionBean getCorreoNotificacionBean() {
		return correoNotificacionBean;
	}

	public void setCorreoNotificacionBean(CorreoNotificacionBean correoNotificacionBean) {
		this.correoNotificacionBean = correoNotificacionBean;
	}

}
