package mx.gob.sedesol.gestorweb.beans.analisisdatos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.Visibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.EstadisticasEventoDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.UsuarioCalificacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InfoEventoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProgramaSocialDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.service.admin.MunicipioService;
import mx.gob.sedesol.basegestor.service.impl.analisisdatos.AnalisisDatosGestionEscolarFacade;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;

@SessionScoped
@ManagedBean
public class ReporteGestionEscolarBean extends BaseBean {

	private static final Logger log = Logger.getLogger(ReporteGestionEscolarBean.class);

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{analisisDatosGestionEscolarFacade}")
	private AnalisisDatosGestionEscolarFacade analisisDatosGestionEscolarFacade;

	@ManagedProperty("#{eventoCapacitacionServiceFacade}")
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	@ManagedProperty("#{municipioService}")
	private MunicipioService municipioService;

	private EventoCapacitacionDTO filtros;
	private List<EntidadFederativaDTO> listaEntidades;
	private List<MunicipioDTO> listaMunicipios;
	private List<CatalogoComunDTO> listaTiposCompetencias;
	private List<CatalogoComunDTO> listaEjesCapacitacion;
	private List<CatalogoComunDTO> ejesCapacitacion;
	private List<CatalogoComunDTO> listaModalidadesPrograma;
	private List<CatalogoComunDTO> listaDestinatarios;
	private NodoeHijosDTO estPlanSedesol;
	private List<CatalogoComunDTO> catEventoCapacit;
	private String tipoDatoFechas;
	private List<EventoCapacitacionDTO> tblEventos;
	private List<MunicipioDTO> listaDeTodosLosMunicipios;
	private List<EventoCapacitacionDTO> eventosCapacitacion;
	private List<Boolean> visible;

	private EstadisticasEventoDTO estadisticas;
	private Boolean renderizarEstado;

	private ObjectMapper jsonMapper;

	private List<InfoEventoDTO> infoEventos;

	private InfoEventoDTO infoEvento;

	private static final String SI_CUENTA_CON_PROGRAMA_SOCIAL = "Si";

	private static final String NO_CUENTA_CON_PROGRAMA_SOCIAL = "No";

	private StreamedContent expedientePDF;

	public ReporteGestionEscolarBean() {

	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		filtros = new EventoCapacitacionDTO();

		listaEntidades = new ArrayList<>();
		listaMunicipios = new ArrayList<>();
		listaDeTodosLosMunicipios = new ArrayList<>();
		listaTiposCompetencias = new ArrayList<>();
		listaEjesCapacitacion = new ArrayList<>();
		catEventoCapacit = new ArrayList<>();
		tblEventos = new ArrayList<>();
		jsonMapper = new ObjectMapper();
		eventosCapacitacion = new ArrayList<>();
		infoEventos = new ArrayList<>();
		infoEvento = new InfoEventoDTO();
		renderizarEstado = Boolean.FALSE;
		limpiarCampos();
		listaModalidadesPrograma = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG);
		listaDestinatarios = eventoCapacitacionServiceFacade.obtenerDestinatariosEc();
		catEventoCapacit = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_EVENTO_CAPACITACION);
		listaEntidades = analisisDatosGestionEscolarFacade.obtenerEntidadesPorPais(ConstantesGestor.ID_PAIS_MEXICO);
		listaDeTodosLosMunicipios = municipioService.findAll();
		// generaEstructuraCatTpoCompetenciaPlan();
		// generaCatEjesCapacitBusqueda();
		visible = Arrays.asList(true, true, true, true, true, true, true, true, false, false, false, false, false,
				false, false, false, false, false);
	}

	public void cambioFechaInicial() {
		if (ObjectUtils.isNotNull(filtros.getFechaFinal())) {
			if (filtros.getFechaInicial().after(filtros.getFechaFinal())) {
				filtros.setFechaFinal(null);
			}
		}

	}

	public String tipoDatoFecha(String tipoFecha) {
		String rango = "";
		switch (tipoFecha) {
		case "1":
			rango = "INICIAN Y TERMINAN ENTRE:";
			break;
		case "2":
			rango = "INICIAN ENTRE:";
			break;
		case "3":
			rango = "TERMINAN ENTRE:";
			break;

		}

		return rango;
	}

	public void buscarEventosCapacitacion() {
		log.info("***********************Buscar Eventos Analisis Datos ***********************");
		if (ObjectUtils.isNotNull(filtros)) {
			log.info("Entidad federativa: " + filtros.getIdEntidadFederativa());
			log.info("Municipio: " + filtros.getIdMunicipio());
			log.info("Tipo de competencia: " + filtros.getFichaDescriptivaPrograma().getTipoCompetencia());
			log.info("Eje de capacitacion: " + filtros.getFichaDescriptivaPrograma().getEjeCapacitacion());
			log.info("Modalidad: " + filtros.getCatModalidadPlanPrograma());
			log.info("Nombre del evento de capacitacion: " + filtros.getNombreEc());
			log.info("Clasificacion de la informacion: " + filtros.getPrivado());
			log.info("Tipo de evento: " + filtros.getFichaDescriptivaPrograma().getCatTipoEventoEc());
			log.info("Dirigido a: " + filtros.getIdDirigido());
			log.info("Tipo de rango de fecha: " + tipoDatoFechas);
			log.info("Fecha inicial: " + filtros.getFechaInicial());
			log.info("Fecha final: " + filtros.getFechaFinal());

			eventosCapacitacion = eventoCapacitacionServiceFacade.busquedaPorCriterios(filtros, tipoDatoFechas);

			log.info("lista size: " + eventosCapacitacion.size());
			renderizadoEstado();
			validarFechaInicialFinal();

		}

	}

	public void generarExpediente() {

		String rutaFondoConstancia = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
				+ "/recursos/documentos/fondoConstancia.png";

		expedientePDF = null;

		List<InfoEventoDTO> infoEventoDTOs = new ArrayList<>();
		infoEventoDTOs.add(infoEvento);

		ReporteConfig reporteConfig = new ReporteConfig();
		reporteConfig.setDatos(infoEventoDTOs);
		reporteConfig.setNombreReporte("Plantilla_SISI");
		reporteConfig.setPathJasper("/resources/jasperReport/analisisDatos/expediente_evento.jasper");

		reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);

		HashMap<String, Object> params = new HashMap<>();

		params.put("IMAGEN_URL", rutaFondoConstancia);

		reporteConfig.setParametros(params);

		expedientePDF = ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig), "application/pdf",
				"plantillaPrototipo");
		// RequestContext.getCurrentInstance().update("frmExpediente:pnlExpediente");
		// RequestContext.getCurrentInstance().execute("PF('modalExpediente').show()");

	}

	public void generarEstadisticasEventosCap() {
		eventosCapacitacion = eventoCapacitacionServiceFacade.busquedaPorCriterios(filtros, tipoDatoFechas);
		infoEventos = obtenerEstadisticasEventos(eventosCapacitacion);

		/*
		 * for (InfoEventoDTO infoEvento : infoEventos) {
		 * 
		 * if (ObjectUtils.isNotNull(infoEvento.getUsuarios())) { if
		 * (!infoEvento.getUsuarios().isEmpty()) { for (UsuarioCalificacionDTO usuario :
		 * infoEvento.getUsuarios()) { log.info("id" + usuario.getId());
		 * log.info("nombre" + usuario.getNombreCompleto()); log.info("calificacion" +
		 * usuario.getCalificacion()); } } }
		 * 
		 * }
		 */
	}

	private List<InfoEventoDTO> obtenerEstadisticasEventos(List<EventoCapacitacionDTO> eventos) {
		List<InfoEventoDTO> infoEventos = new ArrayList<>();
		for (EventoCapacitacionDTO evento : eventos) {
			InfoEventoDTO infoEvento = new InfoEventoDTO();
			infoEvento.setIdEvento(evento.getIdEvento());
			infoEvento.setNombrePrograma(evento.getFichaDescriptivaPrograma().getNombreTentativo());
			infoEvento.setNombreEvento(evento.getNombreEc());
			infoEvento.setNombreModalidadEvento(evento.getCatModalidadPlanPrograma().getNombre());
			infoEvento.setFechaInicioEvento(evento.getFechaInicial());
			infoEvento.setNombreEjeCapacitacion(
					obtieneNombreEjeCapacit(evento.getFichaDescriptivaPrograma().getEjeCapacitacion()));
			infoEvento.setNombreTipoCompetencia(
					obtieneNombreTpoCompetencia(evento.getFichaDescriptivaPrograma().getTipoCompetencia()));
			infoEvento.setCuentaConProgramaSocial(verificarExistenciaProgramaSocial(evento));
			infoEvento.setNombreProgramaSocial(obtenerNombreProgramaSocial(evento));
			infoEvento.setNombreUnidadResponsable(obtenerNombreUnidadResponsable(evento));
			infoEvento.setNombreEntidadFederativa(obtenerNombreEntidadPorId(evento.getIdEntidadFederativa()));
			infoEvento.setNombreMunicipio(obtenerNombreMunicipioPorId(evento.getIdMunicipio()));
			infoEvento.setCantidadUsuariosRegistrados(obtenerCantidadUsuariosPorEvento(evento.getIdEvento()));
			infoEvento.setCantidadUsuariosFinalizaron(obtenerCantUsuariosFinalizaronEvento(evento.getIdEvento()));
			infoEvento.setCantidadUsuariosAprobados(obtenerCantidadUsuariosAprobados(evento));
			infoEvento.setCantidadUsuariosReprobados(obtenerCantidadUsuariosReprobados(evento));
			infoEvento.setPromedioEvento(obtenerPromedioEventoCapacitacion(evento));
			infoEvento.setUsuarios(obtenerUsuariosPorEvento(evento.getIdEvento()));
			infoEvento.setEstatusEvento(evento.getCatEstadoEventoCapacitacion().getNombre());

			infoEventos.add(infoEvento);
		}

		return infoEventos;
	}

	/**
	 * Metodo que obtiene los usuarios por evento, los usuarios no se repiten
	 */
	public List<UsuarioCalificacionDTO> obtenerUsuariosPorEvento(Integer idEvento) {

		List<RelGrupoParticipanteDTO> participantes = eventoCapacitacionServiceFacade
				.obtenerParticipantesPorEvento(idEvento);

		if (!participantes.isEmpty()) {
			Map<Long, Double> usuarioCalificacion = new HashMap<>();
			List<UsuarioCalificacionDTO> usuarios = new ArrayList<>();
			for (RelGrupoParticipanteDTO participante : participantes) {
				Long idUsuario = participante.getPersona().getIdPersona();
				Double calificacionFinal = participante.getCalifFinal();

				if (ObjectUtils.isNotNull(participante.getCalifFinal())) {

					UsuarioCalificacionDTO usuario = new UsuarioCalificacionDTO();

					if (usuarioCalificacion.containsKey(idUsuario)) {
						Double calificacion1 = calificacionFinal;
						Double calificacion2 = usuarioCalificacion.get(idUsuario);

						if (calificacion1.doubleValue() > calificacion2.doubleValue()) {
							usuarioCalificacion.put(idUsuario, calificacion1.doubleValue() * 10);

							usuario.setId(idUsuario);
							usuario.setNombreCompleto(participante.getPersona().getNombreCompleto());
							usuario.setCalificacion(calificacion1 * 10);
							usuarios.add(usuario);
						}

					} else {
						usuarioCalificacion.put(idUsuario, calificacionFinal.doubleValue() * 10);
						usuario.setId(idUsuario);
						usuario.setNombreCompleto(participante.getPersona().getNombreCompleto());
						usuario.setCalificacion(calificacionFinal.doubleValue() * 10);
						usuarios.add(usuario);
					}
				}

			}
			return usuarios.size() == 0 ? null : usuarios;
		} else {
			return null;
		}

	}

	/**
	 * Obtiene el promedio de calificacion del evento de capacitacion con base en la
	 * cantidad de alumnos aprobados y su calificacion final
	 */
	private Double obtenerPromedioEventoCapacitacion(EventoCapacitacionDTO evento) {
		Map<Long, Double> mapaUsuarioCalificacion = obtenerMapaIdUsuarioCalificacion(evento.getIdEvento());
		if (ObjectUtils.isNotNull(mapaUsuarioCalificacion)
				&& ObjectUtils.isNotNull(evento.getCalificacionMinAprobatoria())) {

			double calificacionAprobatoria = Double.valueOf(evento.getCalificacionMinAprobatoria());
			double cantidadUsuariosAprobados = 0.0;
			double sumaDeCalificacionFinal = 0.0;
			for (Double calificacion : mapaUsuarioCalificacion.values()) {

				if (calificacion.doubleValue() >= calificacionAprobatoria) {
					sumaDeCalificacionFinal += calificacion.doubleValue();
					cantidadUsuariosAprobados++;
				}
			}
			if(cantidadUsuariosAprobados != 0.0)
			return sumaDeCalificacionFinal / cantidadUsuariosAprobados;

		}
		return 0.0;
	}

	/**
	 * Obtiene la cantidad de usuarios aprobados
	 * 
	 */
	private Integer obtenerCantidadUsuariosAprobados(EventoCapacitacionDTO evento) {
		Map<Long, Double> mapaUsuarioCalificacion = obtenerMapaIdUsuarioCalificacion(evento.getIdEvento());
		if (ObjectUtils.isNotNull(mapaUsuarioCalificacion)
				&& ObjectUtils.isNotNull(evento.getCalificacionMinAprobatoria())) {

			double calificacionAprobatoria = Double.valueOf(evento.getCalificacionMinAprobatoria());
			int cantidadUsuariosAprobados = 0;
			for (Double calificacion : mapaUsuarioCalificacion.values()) {

				if (calificacion.doubleValue() >= calificacionAprobatoria) {
					cantidadUsuariosAprobados++;
				}
			}
			return cantidadUsuariosAprobados;

		}
		return 0;
	}

	/**
	 * Metodo que obtiene la cantidad de alumnos por evento, los alumnos no se
	 * repiten
	 */
	public String obtenerCantidadAlumnosPorEvento(Integer idEvento) {
		if (ObjectUtils.isNotNull(idEvento)) {
			List<RelGrupoParticipanteDTO> participantes = eventoCapacitacionServiceFacade
					.obtenerParticipantesPorEvento(idEvento);

			if (ObjectUtils.isNotNull(participantes)) {
				Set<Long> cantidadAlumnosPorEvento = new HashSet<>();
				for (RelGrupoParticipanteDTO relGrupoParticipanteDTO : participantes) {
					cantidadAlumnosPorEvento.add(relGrupoParticipanteDTO.getPersona().getIdPersona());
				}
				return String.valueOf(cantidadAlumnosPorEvento.size());
			} else {
				return "0";
			}
		}
		return "0";
	}

	/**
	 * Obtiene la cantidad de usuarios aprobados
	 * 
	 */
	private Integer obtenerCantidadUsuariosReprobados(EventoCapacitacionDTO evento) {
		Map<Long, Double> mapaUsuarioCalificacion = obtenerMapaIdUsuarioCalificacion(evento.getIdEvento());
		if (ObjectUtils.isNotNull(mapaUsuarioCalificacion)
				&& ObjectUtils.isNotNull(evento.getCalificacionMinAprobatoria())) {

			double calificacionAprobatoria = Double.valueOf(evento.getCalificacionMinAprobatoria());
			int cantidadUsuariosReprobados = 0;
			for (Double calificacion : mapaUsuarioCalificacion.values()) {

				if (calificacion.doubleValue() < calificacionAprobatoria) {
					cantidadUsuariosReprobados++;
				}
			}
			return cantidadUsuariosReprobados;

		}
		return 0;
	}

	/**
	 * Obtiene un mapa con el id de usuario y su calificacion
	 * 
	 * @param idEvento
	 * @return
	 */
	private Map<Long, Double> obtenerMapaIdUsuarioCalificacion(Integer idEvento) {

		List<RelGrupoParticipanteDTO> participantes = eventoCapacitacionServiceFacade
				.obtenerParticipantesPorEvento(idEvento);

		if (!participantes.isEmpty()) {
			Map<Long, Double> usuarioCalificacion = new HashMap<>();
			for (RelGrupoParticipanteDTO participante : participantes) {
				Long idUsuario = participante.getPersona().getIdPersona();
				Double calificacionFinal = participante.getCalifFinal();

				if (ObjectUtils.isNotNull(participante.getCalifFinal())) {

					if (usuarioCalificacion.containsKey(idUsuario)) {
						Double calificacion1 = calificacionFinal;
						Double calificacion2 = usuarioCalificacion.get(idUsuario);

						if (calificacion1.doubleValue() > calificacion2.doubleValue()) {
							usuarioCalificacion.put(idUsuario, calificacion1.doubleValue() * 10);
						}

					} else {
						usuarioCalificacion.put(idUsuario, calificacionFinal.doubleValue() * 10);
					}
				}

			}
			return usuarioCalificacion.size() == 0 ? null : usuarioCalificacion;
		} else {
			return null;
		}

	}

	private Integer obtenerCantUsuariosFinalizaronEvento(Integer idEvento) {
		Map<Long, Double> mapaUsuarioCalificacion = obtenerMapaIdUsuarioCalificacion(idEvento);
		if (ObjectUtils.isNotNull(mapaUsuarioCalificacion)) {
			return mapaUsuarioCalificacion.size();
		}
		return 0;
	}

	private String verificarExistenciaProgramaSocial(EventoCapacitacionDTO evento) {
		if (ObjectUtils.isNotNull(evento.getIdProgramaSocial())) {
			return SI_CUENTA_CON_PROGRAMA_SOCIAL;
		}
		return NO_CUENTA_CON_PROGRAMA_SOCIAL;
	}

	public void generarEstadisticas() {
		if (!eventosCapacitacion.isEmpty()) {
			estadisticas = generarEstadisticas(eventosCapacitacion);
			try {
				String estadisticasStr = jsonMapper.writeValueAsString(estadisticas);
				RequestContext.getCurrentInstance().addCallbackParam("estadisticas", estadisticasStr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

	}

	public void renderizadoEstado() {
		if (ObjectUtils.isNotNull(filtros.getIdEntidadFederativa())) {
			if (filtros.getIdEntidadFederativa() != 0) {
				renderizarEstado = Boolean.TRUE;
			} else {
				renderizarEstado = Boolean.FALSE;
			}
		} else {
			renderizarEstado = Boolean.FALSE;
		}

	}

	private void validarFechaInicialFinal() {
		if (!ObjectUtils.isNullOrEmpty(tipoDatoFechas)) {

			if (ObjectUtils.isNull(filtros.getFechaInicial()) || ObjectUtils.isNull(filtros.getFechaFinal())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Datos requeridos:", "La fecha inicial y final son requeridas"));

			}
		}

	}

	public void scrollArriba() {
		RequestContext.getCurrentInstance().scrollTo("form:modalInformeEventos");
	}

	private EstadisticasEventoDTO generarEstadisticas(List<EventoCapacitacionDTO> eventos) {
		EstadisticasEventoDTO estadisticas = new EstadisticasEventoDTO();

		estadisticas.setTotalEventos(eventos.size());
		estadisticas.setNombreCompetenciaCantidad(obtenerEventosPorCompetencia(eventos));
		estadisticas.setNombreEjeCapCantidad(obtenerEventosPorEjeCapacitacion(eventos));
		estadisticas.setNombreModalidadCantidad(obtenerEventosPorModalidad(eventos));
		estadisticas.setNombreProgCantidad(obtenerEventosPorPrograma(eventos));
		estadisticas.setNombreClasificacionCantidad(obtenerEventosPorClasificacion(eventos));
		estadisticas.setNombreTipoCantidad(obtenerEventosPorTipo(eventos));
		estadisticas.setNombreDirigidoCantidad(obtenerEventosPorDirigido(eventos));
		estadisticas.setNombreEntidadCantidad(obtenerEventosPorEntidad(eventos));

		return estadisticas;
	}

	private Map<String, Integer> obtenerEventosPorCompetencia(List<EventoCapacitacionDTO> eventos) {

		Map<String, Integer> nombreCantidadCompetencias = new TreeMap<>();

		for (CatalogoComunDTO elemento : listaTiposCompetencias) {
			nombreCantidadCompetencias.put(elemento.getNombre(), 0);
		}

		for (EventoCapacitacionDTO evento : eventos) {
			Integer idCompetencia = evento.getFichaDescriptivaPrograma().getTipoCompetencia();
			String nombreCompetencia = obtieneNombreTpoCompetencia(idCompetencia);

			if (nombreCantidadCompetencias.containsKey(nombreCompetencia)) {
				nombreCantidadCompetencias.put(nombreCompetencia,
						nombreCantidadCompetencias.get(nombreCompetencia) + 1);
			} else {
				nombreCantidadCompetencias.put(nombreCompetencia, 1);
			}
		}

		return nombreCantidadCompetencias;
	}

	private Map<String, Integer> obtenerEventosPorEjeCapacitacion(List<EventoCapacitacionDTO> eventos) {

		Map<String, Integer> nombreCantidadEjesCap = new TreeMap<>();

		for (CatalogoComunDTO elemento : ejesCapacitacion) {
			nombreCantidadEjesCap.put(elemento.getNombre(), 0);
		}

		for (EventoCapacitacionDTO evento : eventos) {

			Integer idEjeCap = evento.getFichaDescriptivaPrograma().getEjeCapacitacion();
			String nombreEjeCap = obtieneNombreEjeCapacit(idEjeCap);

			if (nombreCantidadEjesCap.containsKey(nombreEjeCap)) {
				nombreCantidadEjesCap.put(nombreEjeCap, nombreCantidadEjesCap.get(nombreEjeCap) + 1);
			} else {
				nombreCantidadEjesCap.put(nombreEjeCap, 1);
			}

		}

		return nombreCantidadEjesCap;
	}

	private Map<String, Integer> obtenerEventosPorModalidad(List<EventoCapacitacionDTO> eventos) {

		Map<String, Integer> nombreCantidadModalidad = new TreeMap<>();

		for (CatalogoComunDTO elemento : listaModalidadesPrograma) {
			nombreCantidadModalidad.put(elemento.getNombre(), 0);
		}

		for (EventoCapacitacionDTO evento : eventos) {

			String modalidad = evento.getCatModalidadPlanPrograma().getNombre();

			if (nombreCantidadModalidad.containsKey(modalidad)) {
				nombreCantidadModalidad.put(modalidad, nombreCantidadModalidad.get(modalidad) + 1);
			} else {
				nombreCantidadModalidad.put(modalidad, 1);
			}

		}

		return nombreCantidadModalidad;
	}

	private Map<String, Integer> obtenerEventosPorClasificacion(List<EventoCapacitacionDTO> eventos) {

		Map<String, Integer> nombreCantidadClasificacion = new TreeMap<>();

		nombreCantidadClasificacion.put("Público", 0);
		nombreCantidadClasificacion.put("Privado", 0);

		for (EventoCapacitacionDTO evento : eventos) {

			Boolean clasificacion = evento.getPrivado();
			String nombre;
			if (clasificacion.booleanValue() == true) {
				nombre = "Privado";
			} else {
				nombre = "Público";
			}
			if (nombreCantidadClasificacion.containsKey(nombre)) {
				nombreCantidadClasificacion.put(nombre, nombreCantidadClasificacion.get(nombre) + 1);
			} else {
				nombreCantidadClasificacion.put(nombre, 1);
			}

		}

		return nombreCantidadClasificacion;
	}

	private Map<String, Integer> obtenerEventosPorTipo(List<EventoCapacitacionDTO> eventos) {

		Map<String, Integer> nombreCantidadTipo = new TreeMap<>();

		for (CatalogoComunDTO elemento : catEventoCapacit) {
			nombreCantidadTipo.put(elemento.getNombre(), 0);
		}

		for (EventoCapacitacionDTO evento : eventos) {

			String nombre = evento.getFichaDescriptivaPrograma().getCatTipoEventoEc().getNombre();

			if (nombreCantidadTipo.containsKey(nombre)) {
				nombreCantidadTipo.put(nombre, nombreCantidadTipo.get(nombre) + 1);
			} else {
				nombreCantidadTipo.put(nombre, 1);
			}

		}

		return nombreCantidadTipo;
	}

	private Map<String, Integer> obtenerEventosPorDirigido(List<EventoCapacitacionDTO> eventos) {

		Map<String, Integer> nombreDirigidoCantidad = new TreeMap<>();

		for (CatalogoComunDTO elemento : listaDestinatarios) {
			nombreDirigidoCantidad.put(elemento.getDescripcion(), 0);
		}

		for (EventoCapacitacionDTO evento : eventos) {

			if (!ObjectUtils.isNullOrEmpty(evento.getIdDestinatario())) {
				String nombre = evento.getIdDestinatario().getNombre();
				if (!ObjectUtils.isNullOrEmpty(nombre)) {
					if (nombreDirigidoCantidad.containsKey(nombre)) {
						nombreDirigidoCantidad.put(nombre, nombreDirigidoCantidad.get(nombre) + 1);
					} else {
						nombreDirigidoCantidad.put(nombre, 1);
					}
				}
			}

		}

		return nombreDirigidoCantidad;
	}

	private Map<String, Integer> obtenerEventosPorPrograma(List<EventoCapacitacionDTO> eventos) {

		Map<String, Integer> nombreCantidadProgramas = new TreeMap<>();

		for (EventoCapacitacionDTO evento : eventos) {

			String nombrePrograma = evento.getFichaDescriptivaPrograma().getNombreTentativo();

			if (nombreCantidadProgramas.containsKey(nombrePrograma)) {
				nombreCantidadProgramas.put(nombrePrograma, nombreCantidadProgramas.get(nombrePrograma) + 1);
			} else {
				nombreCantidadProgramas.put(nombrePrograma, 1);
			}

		}

		return nombreCantidadProgramas;
	}

	private Map<String, Integer> obtenerEventosPorEntidad(List<EventoCapacitacionDTO> eventos) {

		Map<String, Integer> nombreCantidadEntidades = new TreeMap<>(Collections.reverseOrder());
		int contador = 0;
		for (EventoCapacitacionDTO evento : eventos) {

			Integer idEntidadFederativa = evento.getIdEntidadFederativa();
			if (ObjectUtils.isNotNull(idEntidadFederativa)) {
				String nombreEstado = obtenerNombreEntidadPorId(idEntidadFederativa);
				if (nombreCantidadEntidades.containsKey(nombreEstado)) {
					nombreCantidadEntidades.put(nombreEstado, nombreCantidadEntidades.get(nombreEstado) + 1);
				} else {
					nombreCantidadEntidades.put(nombreEstado, 1);
				}
				contador++;
			}

		}
		if (contador != 0) {
			return nombreCantidadEntidades;
		} else {
			return null;
		}

	}

	public void limpiarCampos() {

		filtros.setIdEntidadFederativa(null);
		filtros.setIdMunicipio(null);
		filtros.getFichaDescriptivaPrograma().setNombreTentativo(null);
		filtros.getFichaDescriptivaPrograma().setTipoCompetencia(null);
		filtros.getFichaDescriptivaPrograma().setEjeCapacitacion(null);
		filtros.setCatModalidadPlanPrograma(null);
		filtros.setNombreEc(null);
		filtros.setPrivado(null);
		filtros.getFichaDescriptivaPrograma().setCatTipoEventoEc(null);
		filtros.setIdDirigido(null);
		tipoDatoFechas = null;
		filtros.setFechaInicial(null);
		filtros.setFechaFinal(null);
		eventosCapacitacion = new ArrayList<>();
		infoEventos = new ArrayList<>();

	}

	/**
	 * Metodo que obtiene la cantidad de alumnos por evento, los alumnos no se
	 * repiten
	 */
	public Integer obtenerCantidadUsuariosPorEvento(Integer idEvento) {
		if (ObjectUtils.isNotNull(idEvento)) {
			List<RelGrupoParticipanteDTO> participantes = eventoCapacitacionServiceFacade
					.obtenerParticipantesPorEvento(idEvento);

			if (ObjectUtils.isNotNull(participantes)) {
				Set<Long> cantidadAlumnosPorEvento = new HashSet<>();
				for (RelGrupoParticipanteDTO relGrupoParticipanteDTO : participantes) {
					cantidadAlumnosPorEvento.add(relGrupoParticipanteDTO.getPersona().getIdPersona());
				}
				return cantidadAlumnosPorEvento.size();
			} else {
				return 0;
			}
		}
		return 0;
	}

	/**
	 * Metodo que obtiene la cantidad de alumnos aprobados por evento, los alumnos
	 * no se repiten
	 */
	public String obtenerCantidadAlumnosAprobados(Integer idEvento) {

		if (ObjectUtils.isNotNull(idEvento)) {
			List<RelGrupoParticipanteDTO> participantes = eventoCapacitacionServiceFacade
					.obtenerParticipantesPorEvento(idEvento);

			if (ObjectUtils.isNotNull(participantes)) {
				Set<Long> cantidadAlumnosPorEvento = new HashSet<>();
				for (RelGrupoParticipanteDTO relGrupoParticipanteDTO : participantes) {
					cantidadAlumnosPorEvento.add(relGrupoParticipanteDTO.getPersona().getIdPersona());
				}
				return String.valueOf(cantidadAlumnosPorEvento.size());
			} else {
				return "0";
			}
		}
		return "0";
	}

	/**
	 * Metodo que obtiene el nombre de un programa social a partir de un objeto de
	 * evento de capacitacion
	 */
	public String obtenerNombreProgramaSocial(EventoCapacitacionDTO evento) {

		if (ObjectUtils.isNotNull(evento.getIdProgramaSocial())) {
			ProgramaSocialDTO programaSocial = eventoCapacitacionServiceFacade.getProgramaSocialService()
					.buscarPorId(evento.getIdProgramaSocial());
			if (ObjectUtils.isNotNull(programaSocial)) {
				return programaSocial.getNombre();
			} else {
				return "--";
			}
		} else {
			return "--";
		}
	}

	/**
	 * Metodo que obtiene el nombre de la unidad responsable a partir de un objeto
	 * de evento de capacitacion
	 */
	public String obtenerNombreUnidadResponsable(EventoCapacitacionDTO evento) {

		if (ObjectUtils.isNotNull(evento.getIdProgramaSocial())) {
			ProgramaSocialDTO programaSocial = eventoCapacitacionServiceFacade.getProgramaSocialService()
					.buscarPorId(evento.getIdProgramaSocial());
			if (ObjectUtils.isNotNull(programaSocial)) {

				if (ObjectUtils.isNotNull(programaSocial.getUnidadResponsable())) {
					return programaSocial.getUnidadResponsable().getNombre();

				} else {
					return "--";
				}

			} else {
				return "--";
			}
		} else {
			return "--";
		}
	}

	/**
	 * Metodo que obtiene el nombre de la entidad
	 */

	public String obtenerNombreEntidadPorId(Integer idEntidad) {

		if (ObjectUtils.isNotNull(idEntidad)) {
			for (EntidadFederativaDTO entidad : listaEntidades) {
				if (idEntidad.equals(entidad.getIdEntidadFederativa())) {
					return entidad.getNombre();
				}
			}
		}

		return "";
	}

	/**
	 * Metodo que obtiene el nombre del nunicipio
	 */

	public String obtenerNombreMunicipioPorId(Integer idMunicipio) {
		if (ObjectUtils.isNotNull(idMunicipio)) {
			String idMunicipioString = String.valueOf(idMunicipio.intValue());
			for (MunicipioDTO municipio : listaDeTodosLosMunicipios) {
				if (idMunicipioString.equals(municipio.getIdMunicipio())) {
					return municipio.getNombre();
				}
			}
		}

		return "";
	}

	/**
	 * Metodo que obtiene el promedio de asistencias con base en todos los
	 * participantes del evento
	 */
	public String obtenerPromedioAsistencia(Integer idEvento) {
		if (ObjectUtils.isNotNull(idEvento)) {
			List<RelGrupoParticipanteDTO> participantes = eventoCapacitacionServiceFacade
					.obtenerParticipantesPorEvento(idEvento);

			if (ObjectUtils.isNotNull(participantes)) {
				Set<Long> cantidadAlumnosPorEvento = new HashSet<>();
				int sumaPorcentajeAsistencias = 0;
				for (RelGrupoParticipanteDTO relGrupoParticipanteDTO : participantes) {

					/*
					 * Solo se toman en cuenta los alumnos que ya tienen porcentaje de asistencia
					 */
					if (ObjectUtils.isNotNull(relGrupoParticipanteDTO.getPorcentajeAsist())) {
						if (cantidadAlumnosPorEvento.add(relGrupoParticipanteDTO.getPersona().getIdPersona())) {
							sumaPorcentajeAsistencias = sumaPorcentajeAsistencias
									+ relGrupoParticipanteDTO.getPorcentajeAsist().intValue();
						}

					}

				}
				if (cantidadAlumnosPorEvento.size() != 0) {

					int cantidadAlumnosConPorcentajeAsistencia = cantidadAlumnosPorEvento.size();
					double total = sumaPorcentajeAsistencias / cantidadAlumnosConPorcentajeAsistencia;
					return String.format("%.2f", total);
				}

			} else {
				return "0";
			}
		}
		return "0";
	}

	/**
	 * Metodo que obtiene el promedio de calificicacion final con base en todos los
	 * participantes del evento
	 */
	public String obtenerPromedioCalificacion(Integer idEvento) {
		if (ObjectUtils.isNotNull(idEvento)) {
			List<RelGrupoParticipanteDTO> participantes = eventoCapacitacionServiceFacade
					.obtenerParticipantesPorEvento(idEvento);

			if (ObjectUtils.isNotNull(participantes)) {
				Set<Long> cantidadAlumnosPorEvento = new HashSet<>();
				double sumaCalificaciones = 0;
				for (RelGrupoParticipanteDTO relGrupoParticipanteDTO : participantes) {
					if (ObjectUtils.isNotNull(relGrupoParticipanteDTO.getCalifFinal())) {
						if (cantidadAlumnosPorEvento.add(relGrupoParticipanteDTO.getPersona().getIdPersona())) {
							sumaCalificaciones = sumaCalificaciones
									+ relGrupoParticipanteDTO.getCalifFinal().doubleValue();
						}

						log.info("Participante: " + relGrupoParticipanteDTO.getPersona().getNombreCompleto());
						log.info("Calificacion: " + relGrupoParticipanteDTO.getCalifFinal().doubleValue());
					}
				}
				if (cantidadAlumnosPorEvento.size() != 0) {

					int cantidadAlumnosConCalificacionFinal = cantidadAlumnosPorEvento.size();
					double total = sumaCalificaciones / cantidadAlumnosConCalificacionFinal;
					log.info("Suma de calificaciones: " + sumaCalificaciones);
					log.info("Cantidad alumnos con calificacion final: " + cantidadAlumnosConCalificacionFinal);
					log.info("Total: " + total);
					return String.format("%.2f", total);
				}

			} else {
				return "0.0";
			}
		}
		return "0.0";
	}

	public void inicializarMalla() {
		generaEstructuraCatTpoCompetenciaPlan();
		generaCatEjesCapacitBusqueda();
	}

	/**
	 * Metodo que extrae los datos para mostrar el tipo de competencia
	 */
	private void generaEstructuraCatTpoCompetenciaPlan() {

		MallaCurricularDTO mallaSedesol = analisisDatosGestionEscolarFacade.obtenerMallaCurricular();
		if (ObjectUtils.isNotNull(mallaSedesol)) {
			List<NodoeHijosDTO> planes = new ArrayList<>();
			List<MallaCurricularDTO> mallas = new ArrayList<>();
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

			listaTiposCompetencias = new ArrayList<>();

			// Genera el Catalogo Tipo de Competencia
			estPlanSedesol = planes.get(ConstantesGestorWeb.INDICE_INICIAL);
			for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
				CatalogoComunDTO cc = new CatalogoComunDTO();
				cc.setId(nh.getIdNodo());
				cc.setNombre(nh.getNombre());
				listaTiposCompetencias.add(cc);
			}
		}

	}

	public void actualizaFechas() {
		if (tipoDatoFechas == null) {
			filtros.setFechaInicial(null);
			filtros.setFechaFinal(null);
		}

	}

	/**
	 * Metodo que sirve para extraer los ejes de capacitacion
	 */
	private void generaCatEjesCapacitBusqueda() {
		ejesCapacitacion = new ArrayList<>();
		if (ObjectUtils.isNotNull(estPlanSedesol)) {
			for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
				for (NodoeHijosDTO nint : nh.getNodosHijos()) {
					CatalogoComunDTO cc = new CatalogoComunDTO();
					cc.setId(nint.getIdNodo());
					cc.setNombre(nint.getNombre());
					ejesCapacitacion.add(cc);
				}
			}
		}

	}

	public void onChangeEventoCap(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			filtros.getFichaDescriptivaPrograma()
					.setCatTipoEventoEc(this.getValorDeCatalogo(catEventoCapacit, (Integer) e.getNewValue()));
		} else {
			filtros.getFichaDescriptivaPrograma().setCatTipoEventoEc(null);
		}
	}

	public void onChangeTpoCompetencia(ValueChangeEvent e) {

		if (ObjectUtils.isNotNull(e.getNewValue())) {

			Integer idTpoCompSel = (Integer) e.getNewValue();
			filtros.getFichaDescriptivaPrograma().setTipoCompetencia(idTpoCompSel);
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
		} else {
			listaEjesCapacitacion = new ArrayList<>();
			filtros.getFichaDescriptivaPrograma().setEjeCapacitacion(null);
		}
	}

	public String obtieneNombreTpoCompetencia(Integer idTpoComp) {

		if (ObjectUtils.isNotNull(idTpoComp)) {
			for (CatalogoComunDTO tpoCom : listaTiposCompetencias) {
				if (tpoCom.getId().equals(idTpoComp))
					return tpoCom.getNombre();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param idEjeCap
	 * @return
	 */
	public String obtieneNombreEjeCapacit(Integer idEjeCap) {

		if (ObjectUtils.isNotNull(idEjeCap)) {
			for (CatalogoComunDTO ejeCap : ejesCapacitacion) {
				if (ejeCap.getId().compareTo(idEjeCap) == 0)
					return ejeCap.getNombre();
			}
		}
		return null;
	}

	public void onEstadoChange(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idEntidad = (Integer) e.getNewValue();
			listaMunicipios = analisisDatosGestionEscolarFacade.buscarPorEntidadFederativa(idEntidad);
		} else {
			listaMunicipios = new ArrayList<>();
			filtros.setIdMunicipio(null);
		}
	}

	public void mostrarListaProgramas() {
		RequestContext.getCurrentInstance().execute("verListaProgramas()");
	}

	public void onToggle(ToggleEvent e) {
		visible.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	public TipoUsuarioEnum[] getTiposUsuarios() {
		return TipoUsuarioEnum.values();
	}

	public List<EntidadFederativaDTO> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(List<EntidadFederativaDTO> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public AnalisisDatosGestionEscolarFacade getAnalisisDatosGestionEscolarFacade() {
		return analisisDatosGestionEscolarFacade;
	}

	public void setAnalisisDatosGestionEscolarFacade(
			AnalisisDatosGestionEscolarFacade analisisDatosGestionEscolarFacade) {
		this.analisisDatosGestionEscolarFacade = analisisDatosGestionEscolarFacade;
	}

	public EventoCapacitacionDTO getFiltros() {
		return filtros;
	}

	public void setFiltros(EventoCapacitacionDTO filtros) {
		this.filtros = filtros;
	}

	public List<MunicipioDTO> getListaMunicipios() {
		return listaMunicipios;
	}

	public void setListaMunicipios(List<MunicipioDTO> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

	public List<CatalogoComunDTO> getListaTiposCompetencias() {
		return listaTiposCompetencias;
	}

	public void setListaTiposCompetencias(List<CatalogoComunDTO> listaTiposCompetencias) {
		this.listaTiposCompetencias = listaTiposCompetencias;
	}

	public NodoeHijosDTO getEstPlanSedesol() {
		return estPlanSedesol;
	}

	public void setEstPlanSedesol(NodoeHijosDTO estPlanSedesol) {
		this.estPlanSedesol = estPlanSedesol;
	}

	public List<CatalogoComunDTO> getListaEjesCapacitacion() {
		return listaEjesCapacitacion;
	}

	public void setListaEjesCapacitacion(List<CatalogoComunDTO> listaEjesCapacitacion) {
		this.listaEjesCapacitacion = listaEjesCapacitacion;
	}

	public List<CatalogoComunDTO> getEjesCapacitacion() {
		return ejesCapacitacion;
	}

	public void setEjesCapacitacion(List<CatalogoComunDTO> ejesCapacitacion) {
		this.ejesCapacitacion = ejesCapacitacion;
	}

	public List<CatalogoComunDTO> getListaModalidadesPrograma() {
		return listaModalidadesPrograma;
	}

	public void setListaModalidadesPrograma(List<CatalogoComunDTO> listaModalidadesPrograma) {
		this.listaModalidadesPrograma = listaModalidadesPrograma;
	}

	public List<CatalogoComunDTO> getCatEventoCapacit() {
		return catEventoCapacit;
	}

	public void setCatEventoCapacit(List<CatalogoComunDTO> catEventoCapacit) {
		this.catEventoCapacit = catEventoCapacit;
	}

	public String getTipoDatoFechas() {
		return tipoDatoFechas;
	}

	public void setTipoDatoFechas(String tipoDatoFechas) {
		this.tipoDatoFechas = tipoDatoFechas;
	}

	public List<EventoCapacitacionDTO> getEventosCapacitacion() {
		return eventosCapacitacion;
	}

	public void setEventosCapacitacion(List<EventoCapacitacionDTO> eventosCapacitacion) {
		this.eventosCapacitacion = eventosCapacitacion;
	}

	public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
		return eventoCapacitacionServiceFacade;
	}

	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
	}

	public List<Boolean> getVisible() {
		return visible;
	}

	public void setVisible(List<Boolean> visible) {
		this.visible = visible;
	}

	public List<EventoCapacitacionDTO> getTblEventos() {
		return tblEventos;
	}

	public void setTblEventos(List<EventoCapacitacionDTO> tblEventos) {
		this.tblEventos = tblEventos;
	}

	public ObjectMapper getJsonMapper() {
		return jsonMapper;
	}

	public void setJsonMapper(ObjectMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
	}

	public EstadisticasEventoDTO getEstadisticas() {
		return estadisticas;
	}

	public void setEstadisticas(EstadisticasEventoDTO estadisticas) {
		this.estadisticas = estadisticas;
	}

	public List<CatalogoComunDTO> getListaDestinatarios() {
		return listaDestinatarios;
	}

	public void setListaDestinatarios(List<CatalogoComunDTO> listaDestinatarios) {
		this.listaDestinatarios = listaDestinatarios;
	}

	public Boolean getRenderizarEstado() {
		return renderizarEstado;
	}

	public void setRenderizarEstado(Boolean renderizarEstado) {
		this.renderizarEstado = renderizarEstado;
	}

	public List<InfoEventoDTO> getInfoEventos() {
		return infoEventos;
	}

	public void setInfoEventos(List<InfoEventoDTO> infoEventos) {
		this.infoEventos = infoEventos;
	}

	public MunicipioService getMunicipioService() {
		return municipioService;
	}

	public void setMunicipioService(MunicipioService municipioService) {
		this.municipioService = municipioService;
	}

	public List<MunicipioDTO> getListaDeTodosLosMunicipios() {
		return listaDeTodosLosMunicipios;
	}

	public void setListaDeTodosLosMunicipios(List<MunicipioDTO> listaDeTodosLosMunicipios) {
		this.listaDeTodosLosMunicipios = listaDeTodosLosMunicipios;
	}

	public StreamedContent getExpedientePDF() {
		return expedientePDF;
	}

	public void setExpedientePDF(StreamedContent expedientePDF) {
		this.expedientePDF = expedientePDF;
	}

	public InfoEventoDTO getInfoEvento() {
		return infoEvento;
	}

	public void setInfoEvento(InfoEventoDTO infoEvento) {
		this.infoEvento = infoEvento;
	}

}
