package mx.gob.sedesol.gestorweb.beans.encuestas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.encuestas.RespuestasUsuarioService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.CorreoNotificacionBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;

@ViewScoped
@ManagedBean(name = "detallarEncuestadosPendientesBean")
public class DetallarEncuestadosPendientesBean extends BaseBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 3575527446041441839L;

	@ManagedProperty(value = "#{encuestaService}")
	private EncuestaService encuestaService;

	@ManagedProperty(value = "#{eventoCapacitacionServiceFacade}")
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	@ManagedProperty(value = "#{mallaCurricularService}")
	private MallaCurricularService mallaCurricularService;

	@ManagedProperty(value = "#{respuestaEncuestaService}")
	private RespuestasUsuarioService respuestaUsuarioService;

	private EventoCapacitacionDTO evento;
	private EncuestaDTO encuesta;

	private NodoeHijosDTO estPlanSedesol;
	private List<CatalogoComunDTO> listaCompetencias;
	private List<CatalogoComunDTO> listaEjesCapacitacion;

	private List<RelGrupoParticipanteDTO> listaParticipantes;
	private List<RelGrupoParticipanteDTO> listaParticipantesPendientes;
	private String competencia;
	private String ejeCapacitacion;

	@ManagedProperty("#{correoNotificacionBean}")
	private CorreoNotificacionBean correoNotificacionBean;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		evento = (EventoCapacitacionDTO) getSession().getAttribute(ConstantesGestorWeb.OBJ_EVENTO_SELEC);
		encuesta = (EncuestaDTO) getSession().getAttribute(ConstantesGestorWeb.OBJ_ENCUESTA_SELEC);
		listaParticipantesPendientes = (List<RelGrupoParticipanteDTO>) getSession()
				.getAttribute(ConstantesGestorWeb.OBJ_PARTICIPANTES_PENDIENTES_ENCUESTA);
		competencias();
		ejesCapacitacion(evento.getFichaDescriptivaPrograma().getTipoCompetencia());
	}

	public void enviarNotificacion(RelGrupoParticipanteDTO rgp) {
		try {
			String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_ENCUESTA_PENDIENTE;
			String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_ENCUESTA_PENDIENTE;
			correoNotificacionBean.notificarUsuarioEncuestaPendiente(claveNotificacion, claveCorreo, rgp.getPersona(),
					encuesta, evento);
			agregarMsgInfo("Se notificó al participante.", null);
		} catch (Exception e) {
			agregarMsgInfo("No se pudo notificar al participante.", null);
		}

	}

	private void competencias() {
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

		setListaCompetencias(new ArrayList<>());

		// Genera el Catalogo Tipo de Competencia
		setEstPlanSedesol(planes.get(ConstantesGestorWeb.INDICE_INICIAL));
		for (NodoeHijosDTO nh : getEstPlanSedesol().getNodosHijos()) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			getListaCompetencias().add(cc);
		}
	}

	public void ejesCapacitacion(Integer idTpoCompSel) {
		if (ObjectUtils.isNotNull(idTpoCompSel)) {
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

	public String navegaSeguimientoEncuesta() {
		return ConstantesGestorWeb.NAVEGA_SEGUIMIENTO_ENCUESTAS;
	}

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
	 * @return the mallaCurricularService
	 */
	public MallaCurricularService getMallaCurricularService() {
		return mallaCurricularService;
	}

	/**
	 * @param mallaCurricularService
	 *            the mallaCurricularService to set
	 */
	public void setMallaCurricularService(MallaCurricularService mallaCurricularService) {
		this.mallaCurricularService = mallaCurricularService;
	}

	/**
	 * @return the estPlanSedesol
	 */
	public NodoeHijosDTO getEstPlanSedesol() {
		return estPlanSedesol;
	}

	/**
	 * @param estPlanSedesol
	 *            the estPlanSedesol to set
	 */
	public void setEstPlanSedesol(NodoeHijosDTO estPlanSedesol) {
		this.estPlanSedesol = estPlanSedesol;
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
	 * @return the competencia
	 */
	public String getCompetencia() {
		return competencia;
	}

	/**
	 * @param competencia
	 *            the competencia to set
	 */
	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	/**
	 * @return the ejeCapacitacion
	 */
	public String getEjeCapacitacion() {
		return ejeCapacitacion;
	}

	/**
	 * @param ejeCapacitacion
	 *            the ejeCapacitacion to set
	 */
	public void setEjeCapacitacion(String ejeCapacitacion) {
		this.ejeCapacitacion = ejeCapacitacion;
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

	public CorreoNotificacionBean getCorreoNotificacionBean() {
		return correoNotificacionBean;
	}

	public void setCorreoNotificacionBean(CorreoNotificacionBean correoNotificacionBean) {
		this.correoNotificacionBean = correoNotificacionBean;
	}

}
