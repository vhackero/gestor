package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TiraMateriaDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatEncuestasYEvaluacionesEnum;
import mx.gob.sedesol.basegestor.commons.utils.EncuestaTipoEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.admin.RelPersonaPlataformaMoodleService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaUsuarioService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.AmbienteVirtualApService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelSolicitudEnrolEvtCapService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.basegestor.springinit.EncuestaServiceAdapter;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CursoWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.commons.utils.EstadoEventoCapEnum;

@ManagedBean
@ViewScoped
public class MisCursosBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5557051732307359696L;
	private static final Logger logger = Logger.getLogger(MisCursosBean.class);

	/**
	 * Inyeccion de GrupoParticipanteService
	 */
	@ManagedProperty(value = "#{grupoParticipanteService}")
	private GrupoParticipanteService grupoParticipanteService;

	/**
	 * Inyeccion de Malla curricular Service
	 */
	@ManagedProperty(value = "#{mallaCurricularService}")
	private MallaCurricularService mallaCurricularService;

	/**
	 * Inyeccion de Ambiente Virtual de aprendizaje
	 */
	@ManagedProperty(value = "#{ambienteVirtualApService}")
	private AmbienteVirtualApService ambienteVirtualApService;

	/**
	 * Inyeccion de RelEncuestaUsuarioService
	 */
	@ManagedProperty(value = "#{relEncuestaUsuarioService}")
	private RelEncuestaUsuarioService relEncuestaUsuarioService;

	/**
	 * Inyeccion de Cat encuesta tipo
	 */
	@ManagedProperty(value = "#{encuestaServiceAdapter}")
	private EncuestaServiceAdapter encuestaServiceAdapter;

	/**
	 * Inyeccion de RelSolicitudEnrolEvtCapService
	 */
	@ManagedProperty(value = "#{relSolicitudEnrolEvtCapService}")
	private RelSolicitudEnrolEvtCapService relSolicitudEnrolEvtCapService;

	/**
	 * Inyeccion de relPersonaPlataformaMoodleService
	 */
	@ManagedProperty(value = "#{relPersonaPlataformaMoodleService}")
	private RelPersonaPlataformaMoodleService relPersonaPlataformaMoodleService;

	/**
	 * Inyeccion de personaService
	 */
	@ManagedProperty(value = "#{personaService}")
	private PersonaService personaService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<CatalogoComunDTO> catEstadoEventoCapacitacionList;
	private UsuarioSessionDTO usuarioEnSesion;
	private List<RelGrupoParticipanteDTO> participanteEventosCapacitacionEnEjecucion;
	private List<RelGrupoParticipanteDTO> participanteEventosCapacitacionEnEjecucion2;

	private List<EventoCapacitacionDTO> eventoCapacitacionList;
	private CatalogoComunDTO estatusEcSeleccionado;
	private List<RelEncuestaUsuarioDTO> relEncuestaUsuarioEvtConcList;
	private List<RelEncuestaUsuarioDTO> relEncuestaUsuarioEvtEnEjecList;
	private List<CatalogoComunDTO> encuestaTipoList;
	private CatalogoComunDTO tipoEncuestaSeleccionado;
	private Integer idEstatusSeleccionado;
	private Boolean esColumnaCompetenciasVisible;
	private List<AmbienteVirtualAprendizajeDTO> avaList;
	private HistorialAcademicoDTO tiraMaterias;


	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		/**
		 * Obtiene el usuario en sesion
		 */
		usuarioEnSesion = this.getUsuarioEnSession();

		/**
		 * Obtiene el catalogo de los estados del evento de capacitacion
		 */
		/** Se carga el catalogo CAT_CLASIFICACION_ARCHIVO_OA **/
		catEstadoEventoCapacitacionList = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_ESTADO_EVENTO_CAPACITACION);

		/**
		 * Obtiene los eventos de capacitacion ligados a el usuario en session
		 * en estatus En ejecucion
		 */
		participanteEventosCapacitacionEnEjecucion = grupoParticipanteService
				.obtenerEventosCapacitacionPorIdParticipante(usuarioEnSesion.getIdPersona(),
						obtenerEstadoEventoCapacitacionPorNombre(EstadoEventoCapEnum.EN_EJECUCION.getId()).getId());
		
		participanteEventosCapacitacionEnEjecucion2 = grupoParticipanteService
				.obtenerEventosCapacitacionPorIdParticipante2(usuarioEnSesion.getIdPersona(),
						obtenerEstadoEventoCapacitacionPorNombre(EstadoEventoCapEnum.EN_EJECUCION.getId()).getId());
		
		tiraMaterias = grupoParticipanteService.consultaTiraMaterias(usuarioEnSesion.getIdPersona().toString());




		/**
		 * Elimina los estatus de evento de capacitacion que no sean en
		 * solicitud y concluidos
		 */
		catEstadoEventoCapacitacionList = this.eliminarEstatusEventoCapacitacion();

		/**
		 * 
		 * Obtiene Ava por id del Evento Cap
		 */

		avaList = obtenerAvasPorEventoCapacitacionas(
				this.obtenerIdEventoPorRelParticipantes(participanteEventosCapacitacionEnEjecucion));

		/**
		 * Obtiene los eventos de capacitacion ligados a el usuario en session
		 * en estatus Concluidos
		 */
		List<RelGrupoParticipanteDTO> participanteEventosCapacitacionEstatus = grupoParticipanteService
				.obtenerEventosCapacitacionPorIdParticipante(usuarioEnSesion.getIdPersona(),
						obtenerEstadoEventoCapacitacionPorNombre(EstadoEventoCapEnum.CONCLUIDOS.getId()).getId());

		idEstatusSeleccionado = obtenerEstadoEventoCapacitacionPorNombre(EstadoEventoCapEnum.CONCLUIDOS.getId())
				.getId();

		eventoCapacitacionList = obtenerEventosDelGrupoParticipante(participanteEventosCapacitacionEstatus);
		/**
		 * obtener los tipos de encuesta
		 */
		encuestaTipoList = encuestaServiceAdapter
				.getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_TIPO)
				.findAll(CatEncuestaTipo.class);
		tipoEncuestaSeleccionado = this.obtenerTipoEncuesta(EncuestaTipoEnum.REACCION, encuestaTipoList);

		/**
		 * Obtiene el id de los eventos de capacitacion en una lista
		 */
		List<Integer> idEventoCapacitacionEnEjecucion = obtenerIdEventoPorRelParticipantes(
				participanteEventosCapacitacionEnEjecucion);

		List<Integer> idEventoCapacitacionConcluidos = obtenerIdEventoPorRelParticipantes(
				participanteEventosCapacitacionEstatus);

		/**
		 * Obtiene las encuestas que estan ligadas a el usuario y al evento de
		 * capacitacion
		 */
		this.obtenerEncuestas(idEventoCapacitacionEnEjecucion, idEventoCapacitacionConcluidos);

		esColumnaCompetenciasVisible = Boolean.TRUE;
	}

	public Integer obtenerAvanceOa(Integer idEventoCapacitacion) {
		Integer avanceDeLosOas = 0;
		AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO;
		PersonaDTO persona;
		Integer idPersonaLMS;
		CursoWS cursoWS;

		ambienteVirtualAprendizajeDTO = this.obtenerAvaPorIdEvtCap(avaList, idEventoCapacitacion);

		persona = obtenerPersonaPorId(usuarioEnSesion.getIdPersona());

		idPersonaLMS = obtenerIdPersonaLmsPorIdPersonaGestor(persona,
				ambienteVirtualAprendizajeDTO.getPlataformaMoodle(), usuarioEnSesion.getIdPersona());

		cursoWS = new CursoWS(ambienteVirtualAprendizajeDTO.getPlataformaMoodle());
		try {
			avanceDeLosOas = cursoWS.obtenerAvanceOAS(ambienteVirtualAprendizajeDTO.getIdCursoLms(), idPersonaLMS);
		} catch (ErrorWS e) {
			e.printStackTrace();
			logger.info("Ocurrio un error");
		}

		return avanceDeLosOas;
	}

	private Integer obtenerIdPersonaLmsPorIdPersonaGestor(PersonaDTO persona, ParametroWSMoodleDTO parametroWSMoodleDTO,
			Long usuarioModifico) {

		Integer idPersonaLms = relPersonaPlataformaMoodleService.obtenerIdMoodle(persona, parametroWSMoodleDTO,
				usuarioModifico);

		return idPersonaLms;
	}

	private PersonaDTO obtenerPersonaPorId(Long idPersona) {
		return personaService.buscarPorId(idPersona);
	}

	private AmbienteVirtualAprendizajeDTO obtenerAvaPorIdEvtCap(List<AmbienteVirtualAprendizajeDTO> avaList,
			Integer idEventoCapacitacion) {
		AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO = null;

		for (AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO2 : avaList) {

			if (idEventoCapacitacion.equals(ambienteVirtualAprendizajeDTO2.getEventoCapacitacion().getIdEvento())) {
				ambienteVirtualAprendizajeDTO = ambienteVirtualAprendizajeDTO2;

			}
		}
		return ambienteVirtualAprendizajeDTO;
	}

	private List<EventoCapacitacionDTO> obtenerEventosDelGrupoParticipante(
			List<RelGrupoParticipanteDTO> grupoParticipante) {

		List<EventoCapacitacionDTO> eventoCapacitacionlist = new ArrayList<EventoCapacitacionDTO>();

		grupoParticipante
				.forEach(gpoParticipante -> eventoCapacitacionlist.add(gpoParticipante.getGrupo().getEvento()));

		return eventoCapacitacionlist;
	}

	private List<EventoCapacitacionDTO> obtenerSolicitudEnrolamiento(Long idPersona) {
		logger.info("Buscara las solicitudes de enrolamiento por el id Persona " + idPersona);

		return relSolicitudEnrolEvtCapService.obtenerSolicitudesEnrolamientoPorIdPersona(usuarioEnSesion.getIdPersona(),
				Boolean.TRUE);
	}

	private List<CatalogoComunDTO> eliminarEstatusEventoCapacitacion() {
		List<CatalogoComunDTO> catEstadoEventoCapacitacionList = new ArrayList<CatalogoComunDTO>();
		for (CatalogoComunDTO catalogoComunDTO : this.catEstadoEventoCapacitacionList) {
			if (EstadoEventoCapEnum.CONCLUIDOS.getId().equals(catalogoComunDTO.getId())) {
				catEstadoEventoCapacitacionList.add(catalogoComunDTO);
				catEstadoEventoCapacitacionList.add(obtenerEstatusEnSolicitud());
				continue;
			}
		}

		return catEstadoEventoCapacitacionList;
	}

	private CatalogoComunDTO obtenerEstatusEnSolicitud() {
		CatalogoComunDTO estatusEnSolicitud = new CatalogoComunDTO();
		estatusEnSolicitud.setNombre("En Solicitud");
		estatusEnSolicitud.setId(-1);
		return estatusEnSolicitud;
	}

	private void obtenerEncuestas(List<Integer> idEventoCapEnEjecucion,
			List<Integer> idEventoCapEnEjecucionConcluidos) {

		if (!ObjectUtils.isNullOrEmpty(idEventoCapEnEjecucion)) {
			this.obtenerEcuestasPorPersonaEventoCapEnEjecucion(idEventoCapEnEjecucion);
		}
		if (!ObjectUtils.isNullOrEmpty(idEventoCapEnEjecucionConcluidos)) {
			this.obtenerEncuestasPorPersonaEventoCapConcluidos(idEventoCapEnEjecucionConcluidos);
		}
	}

	private void obtenerEcuestasPorPersonaEventoCapEnEjecucion(List<Integer> idEventoCapEnEjecucion) {
		relEncuestaUsuarioEvtEnEjecList = this.obtenerEncuestaUsuarioEventoYTipoEncuesta(idEventoCapEnEjecucion,
				usuarioEnSesion.getIdPersona(), tipoEncuestaSeleccionado.getId());

	}

	private void obtenerEncuestasPorPersonaEventoCapConcluidos(List<Integer> idEventoCapEnEjecucionConcluidos) {
		relEncuestaUsuarioEvtConcList = this.obtenerEncuestaUsuarioEvento(idEventoCapEnEjecucionConcluidos,
				usuarioEnSesion.getIdPersona());

	}

	private List<RelEncuestaUsuarioDTO> obtenerEncuestaUsuarioEventoYTipoEncuesta(List<Integer> clEventoList,
			Long clPersona, Integer idTipoEncuesta) {
		List<RelEncuestaUsuarioDTO> relEncuestaUsuarioList = null;
		relEncuestaUsuarioList = relEncuestaUsuarioService.consultarEncuestasAsignadas(clEventoList, clPersona,
				idTipoEncuesta);

		return relEncuestaUsuarioList;
	}

	private List<RelEncuestaUsuarioDTO> obtenerEncuestaUsuarioEvento(List<Integer> clEventoList, Long clPersona) {
		List<RelEncuestaUsuarioDTO> relEncuestaUsuarioList = null;
		relEncuestaUsuarioList = relEncuestaUsuarioService.consultarEncuestasAsignadas(clEventoList, clPersona,
				Boolean.TRUE);

		return relEncuestaUsuarioList;
	}

	public Boolean esEncuestaVisibleEvtEnEje(Integer idEventoCapacitacion) {

		Boolean esEncuestaVisible = Boolean.FALSE;

		RelEncuestaUsuarioDTO relEncuestaUsuarioDTO = obtenerEcnuestaDeRelEncuestaUsuarioPorIdEvt(idEventoCapacitacion,
				this.relEncuestaUsuarioEvtEnEjecList);

		if (ObjectUtils.isNotNull(relEncuestaUsuarioDTO)) {
			esEncuestaVisible = Boolean.TRUE;
		}

		return !esEncuestaVisible;
	}

	public Boolean esEncuestaVisibleEvtConc(Integer idEventoCapacitacion) {

		Boolean esEncuestaVisible = Boolean.FALSE;

		RelEncuestaUsuarioDTO relEncuestaUsuarioDTO = obtenerEcnuestaDeRelEncuestaUsuarioPorIdEvt(idEventoCapacitacion,
				this.relEncuestaUsuarioEvtConcList);

		if (ObjectUtils.isNotNull(relEncuestaUsuarioDTO)) {
			esEncuestaVisible = Boolean.TRUE;
		}

		return !esEncuestaVisible;
	}

	public String obtenerEstatusEncuestaEvtEnEjec(EventoCapacitacionDTO evento) {

		if (evento.getAplicaEncuesta().booleanValue() == true) {
			RelEncuestaUsuarioDTO relEncuestaUsuarioDTO = obtenerEcnuestaDeRelEncuestaUsuarioPorIdEvt(
					evento.getIdEvento(), this.relEncuestaUsuarioEvtEnEjecList);

			if (ObjectUtils.isNull(relEncuestaUsuarioDTO)) {
				return "";
			}
			for (RelEncuestaUsuarioDTO reu : relEncuestaUsuarioEvtEnEjecList) {
				logger.info(reu.getRelGrupoParticipante().getPersona().getUsuario());
				logger.info("---");
				logger.info(reu.getTblEncuesta().getNombre());
				logger.info("---");
				logger.info(reu.getRelGrupoParticipante().getGrupo().getEvento().getNombreEc());
			}
			relEncuestaUsuarioDTO = obtenerEcnuestaActivaDeRelEncuestaUsuarioPorIdEvt(evento.getIdEvento(),
					this.relEncuestaUsuarioEvtEnEjecList);

			if (ObjectUtils.isNotNull(relEncuestaUsuarioDTO)) {
				return "Contestar";
			}

			return "Realizada";
		} else {
			return "No aplica";
		}

	}

	public String obtenerEstatusEncuestaEvtConc(Integer idEventoCapacitacion) {

		String encuestaEnestatus = "Realizada";

		RelEncuestaUsuarioDTO relEncuestaUsuarioDTO = obtenerEcnuestaDeRelEncuestaUsuarioPorIdEvt(idEventoCapacitacion,
				this.relEncuestaUsuarioEvtConcList);

		if (ObjectUtils.isNotNull(relEncuestaUsuarioDTO)) {
			encuestaEnestatus = "Contestar";
		}

		return encuestaEnestatus;
	}

	private RelEncuestaUsuarioDTO obtenerEcnuestaDeRelEncuestaUsuarioPorIdEvt(Integer idEvt,
			List<RelEncuestaUsuarioDTO> relEncuestaUsuarioList) {
		RelEncuestaUsuarioDTO encuestaUsuarioSeleccionada = null;
		for (RelEncuestaUsuarioDTO relEncuestaUsuarioDTO : relEncuestaUsuarioList) {
			if (idEvt.equals(relEncuestaUsuarioDTO.getRelGrupoParticipante().getGrupo().getEvento().getIdEvento())) {
				encuestaUsuarioSeleccionada = relEncuestaUsuarioDTO;
			}
		}
		return encuestaUsuarioSeleccionada;
	}

	private RelEncuestaUsuarioDTO obtenerEcnuestaActivaDeRelEncuestaUsuarioPorIdEvt(Integer idEvt,
			List<RelEncuestaUsuarioDTO> relEncuestaUsuarioList) {
		RelEncuestaUsuarioDTO encuestaUsuarioSeleccionada = null;
		for (RelEncuestaUsuarioDTO relEncuestaUsuarioDTO : relEncuestaUsuarioList) {
			if (idEvt.equals(relEncuestaUsuarioDTO.getRelGrupoParticipante().getGrupo().getEvento().getIdEvento())
					&& relEncuestaUsuarioDTO.getActivo().booleanValue() == true) {
				encuestaUsuarioSeleccionada = relEncuestaUsuarioDTO;
			}
		}
		return encuestaUsuarioSeleccionada;
	}

	public String obtenerFechaLimiteEncuesta(Integer idEventoCapacitacion) {
		try {

			Date fechaActual = new Date();

			Date fechaLimite = null;

			RelEncuestaUsuarioDTO relEncuestaComportamiento = obtenerEncuestaPorTipoYIdEvt(
					relEncuestaUsuarioEvtConcList, EncuestaTipoEnum.COMPORTAMIENTO, idEventoCapacitacion);

			RelEncuestaUsuarioDTO relEncuestaResultados = null;

			if (fechaActual.before(relEncuestaComportamiento.getFechaApertura())) {
				fechaLimite = relEncuestaComportamiento.getFechaApertura();
			} else {
				relEncuestaResultados = obtenerEncuestaPorTipoYIdEvt(relEncuestaUsuarioEvtConcList,
						EncuestaTipoEnum.RESULTADOS, idEventoCapacitacion);
				fechaLimite = relEncuestaResultados.getFechaApertura();
			}

			return relEncuestaUsuarioService.fecharLimite(fechaLimite,
					ConstantesGestor.NUMERO_DIAS_ANADIDOS_AL_LIMITE_DE_ENCUESTA);
		} catch (Exception e) {
			logger.info("Ha ocurrido un error al obtener la fecha limite.");
			return "";
		}

	}

	private RelEncuestaUsuarioDTO obtenerEncuestaPorTipoYIdEvt(
			List<RelEncuestaUsuarioDTO> relEncuestaUsuarioEvtConcList, EncuestaTipoEnum encuestaTipoEnum,
			Integer idEventoCapacitacion) {

		RelEncuestaUsuarioDTO relEncuestaUsuarioSeleccionada = new RelEncuestaUsuarioDTO();

		for (RelEncuestaUsuarioDTO relEncuestaUsuarioDTO : relEncuestaUsuarioEvtConcList) {

			if (idEventoCapacitacion
					.equals(relEncuestaUsuarioDTO.getRelGrupoParticipante().getGrupo().getEvento().getIdEvento())
					&& encuestaTipoEnum.getId()
							.equals(relEncuestaUsuarioDTO.getTblEncuesta().getEncuestaTipo().getId())) {

				relEncuestaUsuarioSeleccionada = relEncuestaUsuarioDTO;
			}

		}

		return relEncuestaUsuarioSeleccionada;
	}

	private CatalogoComunDTO obtenerTipoEncuesta(EncuestaTipoEnum encuestaTipoEnum,
			List<CatalogoComunDTO> encuestaTipoList) {
		CatalogoComunDTO tipoEncuestaSeleccionado = null;
		for (CatalogoComunDTO catalogoComunDTO : encuestaTipoList) {
			if (encuestaTipoEnum.getId().equals(catalogoComunDTO.getId())) {
				tipoEncuestaSeleccionado = catalogoComunDTO;
			}
		}
		return tipoEncuestaSeleccionado;
	}

	private List<Integer> obtenerIdEventoPorRelParticipantes(
			List<RelGrupoParticipanteDTO> participanteEventosCapacitacionEnEjecucion) {
		List<Integer> eventoCapacitacionList = new ArrayList<Integer>();

		participanteEventosCapacitacionEnEjecucion
				.forEach(relEvenCap -> eventoCapacitacionList.add(relEvenCap.getGrupo().getEvento().getIdEvento()));

		return eventoCapacitacionList;
	}

	private List<AmbienteVirtualAprendizajeDTO> obtenerAvasPorEventoCapacitacionas(
			List<Integer> idEventoCapacitacionList) {
		List<AmbienteVirtualAprendizajeDTO> ambienteVirtualAprendizajeDTOList = null;
		if (!ObjectUtils.isNullOrEmpty(idEventoCapacitacionList)) {
			ambienteVirtualAprendizajeDTOList = ambienteVirtualApService
					.obtenerAvasPorIdEventos(idEventoCapacitacionList);
		}

		return ambienteVirtualAprendizajeDTOList;
	}

	public void navegaUrlCursoLms(Integer idEventoCapacitacion) {

		logger.info("El id del evento de capacitacion es " + idEventoCapacitacion);

		AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO = this.obtenerAvaPorIdEvtCap(avaList,
				idEventoCapacitacion);

		logger.info("El id curso lms es " + ambienteVirtualAprendizajeDTO.getIdCursoLms());

		String urlLms = ambienteVirtualApService.obtenerUrlCursoLms(usuarioEnSesion.getIdPersona(),
				ambienteVirtualAprendizajeDTO.getIdCursoLms(), ambienteVirtualAprendizajeDTO.getPlataformaMoodle());

		logger.info("La url obtenida es :" + urlLms);

		ExternalContext externalContext = getFacesContext().getExternalContext();
		try {
			externalContext.redirect(urlLms);
		} catch (IOException e) {

			e.printStackTrace();
			logger.error("Ocurrio un error al intentar abrir la URL " + urlLms);
		}

	}

	public CatalogoComunDTO obtenerEstadoEventoCapacitacionPorNombre(Integer idEstadoEvento) {
		CatalogoComunDTO estadoEventoCapacitacion = null;
		for (CatalogoComunDTO catalogoComunDTO : catEstadoEventoCapacitacionList) {
			if (catalogoComunDTO.getId().equals(idEstadoEvento)) {
				estadoEventoCapacitacion = catalogoComunDTO;
			}
		}
		return estadoEventoCapacitacion;
	}

	public void onChangeEstatuEc(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			esColumnaCompetenciasVisible = Boolean.TRUE;
			Integer idEstatusSeleccionado = (Integer) e.getNewValue();
			if (new Integer(-1).equals(idEstatusSeleccionado)) {
				esColumnaCompetenciasVisible = Boolean.FALSE;
				eventoCapacitacionList = obtenerSolicitudEnrolamiento(usuarioEnSesion.getIdPersona());

			} else {
				logger.info("Busqueda por estatus :" + idEstatusSeleccionado);
				List<RelGrupoParticipanteDTO> participanteEventosCapacitacionEstatus = grupoParticipanteService
						.obtenerEventosCapacitacionPorIdParticipante(usuarioEnSesion.getIdPersona(),
								idEstatusSeleccionado);
				eventoCapacitacionList = obtenerEventosDelGrupoParticipante(participanteEventosCapacitacionEstatus);
				logger.info("Los eventos encontrados fueron " + eventoCapacitacionList.size());

			}
		} else {
			eventoCapacitacionList = null;
		}
	}

	/**
	 * Setter y getter
	 */

	public List<CatalogoComunDTO> getCatEstadoEventoCapacitacionList() {
		return catEstadoEventoCapacitacionList;
	}

	public void setCatEstadoEventoCapacitacionList(List<CatalogoComunDTO> catEstadoEventoCapacitacionList) {
		this.catEstadoEventoCapacitacionList = catEstadoEventoCapacitacionList;
	}

	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
	}

	public UsuarioSessionDTO getUsuarioEnSesion() {
		return usuarioEnSesion;
	}

	public void setUsuarioEnSesion(UsuarioSessionDTO usuarioEnSesion) {
		this.usuarioEnSesion = usuarioEnSesion;
	}

	public CatalogoComunDTO getEstatusEcSeleccionado() {
		return estatusEcSeleccionado;
	}

	public void setEstatusEcSeleccionado(CatalogoComunDTO estatusEcSeleccionado) {
		this.estatusEcSeleccionado = estatusEcSeleccionado;
	}

	public MallaCurricularService getMallaCurricularService() {
		return mallaCurricularService;
	}

	public void setMallaCurricularService(MallaCurricularService mallaCurricularService) {
		this.mallaCurricularService = mallaCurricularService;
	}

	public AmbienteVirtualApService getAmbienteVirtualApService() {
		return ambienteVirtualApService;
	}

	public void setAmbienteVirtualApService(AmbienteVirtualApService ambienteVirtualApService) {
		this.ambienteVirtualApService = ambienteVirtualApService;
	}

	public RelEncuestaUsuarioService getRelEncuestaUsuarioService() {
		return relEncuestaUsuarioService;
	}

	public void setRelEncuestaUsuarioService(RelEncuestaUsuarioService relEncuestaUsuarioService) {
		this.relEncuestaUsuarioService = relEncuestaUsuarioService;
	}

	public CatalogoComunDTO getTipoEncuestaSeleccionado() {
		return tipoEncuestaSeleccionado;
	}

	public void setTipoEncuestaSeleccionado(CatalogoComunDTO tipoEncuestaSeleccionado) {
		this.tipoEncuestaSeleccionado = tipoEncuestaSeleccionado;
	}

	public EncuestaServiceAdapter getEncuestaServiceAdapter() {
		return encuestaServiceAdapter;
	}

	public void setEncuestaServiceAdapter(EncuestaServiceAdapter encuestaServiceAdapter) {
		this.encuestaServiceAdapter = encuestaServiceAdapter;
	}

	public RelSolicitudEnrolEvtCapService getRelSolicitudEnrolEvtCapService() {
		return relSolicitudEnrolEvtCapService;
	}

	public void setRelSolicitudEnrolEvtCapService(RelSolicitudEnrolEvtCapService relSolicitudEnrolEvtCapService) {
		this.relSolicitudEnrolEvtCapService = relSolicitudEnrolEvtCapService;
	}

	public List<EventoCapacitacionDTO> getEventoCapacitacionList() {
		return eventoCapacitacionList;
	}

	public void setEventoCapacitacionList(List<EventoCapacitacionDTO> eventoCapacitacionList) {
		this.eventoCapacitacionList = eventoCapacitacionList;
	}

	public Integer getIdEstatusSeleccionado() {
		return idEstatusSeleccionado;
	}

	public void setIdEstatusSeleccionado(Integer idEstatusSeleccionado) {
		this.idEstatusSeleccionado = idEstatusSeleccionado;
	}

	public List<RelGrupoParticipanteDTO> getParticipanteEventosCapacitacionEnEjecucion() {
		return participanteEventosCapacitacionEnEjecucion;
	}

	public void setParticipanteEventosCapacitacionEnEjecucion(
			List<RelGrupoParticipanteDTO> participanteEventosCapacitacionEnEjecucion) {
		this.participanteEventosCapacitacionEnEjecucion = participanteEventosCapacitacionEnEjecucion;
	}
	
	public List<RelGrupoParticipanteDTO> getParticipanteEventosCapacitacionEnEjecucion2() {
		return participanteEventosCapacitacionEnEjecucion2;
	}

	public void setParticipanteEventosCapacitacionEnEjecucion2(
			List<RelGrupoParticipanteDTO> participanteEventosCapacitacionEnEjecucion2) {
		this.participanteEventosCapacitacionEnEjecucion2 = participanteEventosCapacitacionEnEjecucion2;
	}

	public Boolean getEsColumnaCompetenciasVisible() {
		return esColumnaCompetenciasVisible;
	}

	public void setEsColumnaCompetenciasVisible(Boolean esColumnaCompetenciasVisible) {
		this.esColumnaCompetenciasVisible = esColumnaCompetenciasVisible;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public RelPersonaPlataformaMoodleService getRelPersonaPlataformaMoodleService() {
		return relPersonaPlataformaMoodleService;
	}

	public void setRelPersonaPlataformaMoodleService(
			RelPersonaPlataformaMoodleService relPersonaPlataformaMoodleService) {
		this.relPersonaPlataformaMoodleService = relPersonaPlataformaMoodleService;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public HistorialAcademicoDTO getTiraMaterias() {
		return tiraMaterias;
	}

	public void setTiraMaterias(HistorialAcademicoDTO tiraMaterias) {
		this.tiraMaterias = tiraMaterias;
	}

}
