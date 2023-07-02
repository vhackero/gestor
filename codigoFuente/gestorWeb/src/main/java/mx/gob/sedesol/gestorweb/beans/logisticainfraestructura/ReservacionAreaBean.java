package mx.gob.sedesol.gestorweb.beans.logisticainfraestructura;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.component.schedule.Schedule;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ConfiguracionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.CritBusquedaAreasConfigDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.EventoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.PersonalizacionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RecursosInfraestructuraDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaDistribucionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaRecursoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelPersonalizacionRecursoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ReservacionEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ReservacionEventoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SolicitudReservacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatAreaInfraestructura;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatEstatusReservacion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatUbicacionTerritorial;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura.LogisticaInfraServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesReportesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.dto.SolicitudReservAreaDTO;
import mx.gob.sedesol.gestorweb.commons.utils.EstadoEventoCapEnum;
import mx.gob.sedesol.gestorweb.commons.utils.EstatusReservacionEnum;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;
import mx.gob.sedesol.gestorweb.commons.utils.TipoRecursoEnum;
import mx.gob.sedesol.gestorweb.commons.utils.UtileriasLogistica;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ManagedBean
@ViewScoped
public class ReservacionAreaBean extends BaseBean {

	private static final long serialVersionUID = 3931434376985585429L;

	private static final Logger logger = Logger.getLogger(ReservacionAreaBean.class);

	private transient ModelMapper mapper = new ModelMapper();

	@ManagedProperty("#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private ParametroSistemaService parametroSistemaService;

	@ManagedProperty("#{sistema}")
	private SistemaBean textosSistema;

	@ManagedProperty(value = "#{correoElectronicoService}")
	private CorreoElectronicoService correoElectronicoService;

	@ManagedProperty(value = "#{logisticaInfraServiceFacade}")
	private transient LogisticaInfraServiceFacade facade;

	@ManagedProperty("#{orgGubernamentalService}")
	private OrgGubernamentalService orgGubernamentalService;

	private static final String DIALOGRESERVACIONSHOW = "PF('dialogReservacion').show();";
	private static final String FORMATODIAMESANO = "dd/MM/yyyy";
	private static final String FORMATOHORAMINUTO = "HH:mm";
	private static final String SCHEDULEVIEW = "agendaDay";
	private static final String SCHEDULEVIEWMONTH = "month";

	private transient ScheduleModel scheduleModel;
	private DefaultScheduleEvent eventoReservacion;
	private Integer tipoEvento;
	private List<EventoCapacitacionDTO> eventosCapacitacion;
	private EventoCapacitacionDTO eventoCapacitacion;
	private EventoCapacitacionDTO evtCapacitacionSelect;
	private Boolean eventoCapacit;
	private Boolean eventoGenerico;
	private boolean detReservEventoCap;

	private transient TreeNode arbolOrgGubernamental;
	private transient TreeNode nodoOrgGubernamentalSel;
	private List<CatalogoComunDTO> catUbicacionTerr;
	private List<SedeDTO> catSedesInfra;
	private List<ConfiguracionAreaDTO> areasConfigBusq;
	private CritBusquedaAreasConfigDTO criteriosBusq;
	private List<CatalogoComunDTO> catAreasInfra;
	private boolean eventDateOn;
	private boolean opcionesEventos;
	private boolean muestraEventosCap;

	private Integer eventoPrivado;

	private List<CatalogoComunDTO> catTpoCompSedesol;
	private List<CatalogoComunDTO> catEjesCapacitSedesol;
	private List<SolicitudReservAreaDTO> solicitudesReserv;
	private EventoGeneralDTO eventoGeneral;
	private boolean nuevaReservXArea;

	// reservacion area y evento
	private ReservacionEventoCapacitacionDTO rEC;
	private RelSolicitudEventoCapacitacionDTO sEC;
	private ReservacionEventoGeneralDTO rEG;
	private RelSolicitudEventoGeneralDTO sEG;
	private Date fechaReservacion;
	private SolicitudReservacionDTO solRes;
	private ConfiguracionAreaDTO configuracionAreaReservacion;

	// aprobacion de reservaciones
	private List<RelSolicitudEventoCapacitacionDTO> reservacionesCapPorAprobar;
	private List<RelSolicitudEventoGeneralDTO> reservacionesGenPorAprobar;
	private boolean bolPanelAprobarCap;
	private String iconoAprovadoGen;
	private String iconoAprovadoCap;
	private boolean bolInfoReserv;
	private RelSolicitudEventoGeneralDTO infoReservacionGen;
	private RelSolicitudEventoCapacitacionDTO infoReservacionCap;
	private PersonalizacionAreaDTO persoCap;
	private PersonalizacionAreaDTO persoGen;
	private AreaSedeDTO areaSedeInfoCap;
	private AreaSedeDTO areaSedeInfoGen;

	// PERSONALIZACION AREA
	private transient InputStream input;
	private SolicitudReservAreaDTO solicitudSeleccionada;
	private SolicitudReservAreaDTO solicitudSeleccionadaEdit;
	private transient List<StreamedContent> imagenes;
	private PersonalizacionAreaDTO personalizacionArea;
	private List<CatalogoComunDTO> catDistribucionConfigArea;
	private List<RecursosInfraestructuraDTO> recursosTecnologicos;
	private List<RecursosInfraestructuraDTO> recursosMobiliario;
	private ConfiguracionAreaDTO areaSeleccionada;
	private List<File> arrayImagenes;
	private List<RelAreaRecursoDTO> selTec;
	private List<RelAreaRecursoDTO> selTecGuardar;
	private RelAreaRecursoDTO recursoTecGuardar;
	private List<RelAreaRecursoDTO> selMobi;
	private List<RelAreaRecursoDTO> selMobiGuardar;
	private RelAreaRecursoDTO recursoMobiGuardar;

	private List<Integer> selTecCant;
	private Integer cantTecSeleccionada;
	private List<Integer> selMobiCant;
	private Integer cantMobiSeleccionada;

	@ManagedProperty(value = "#{areasBean}")
	private AreasBean areasBean;

	private transient Schedule scheduleCmp;
	private transient StreamedContent pdfReservacion;

	public ReservacionAreaBean() {

		scheduleModel = new DefaultScheduleModel();

		criteriosBusq = new CritBusquedaAreasConfigDTO();
		// criteriosBusq.setIdAreaSede(0);
		criteriosBusq.setIdOrgGubernamental(0);
		criteriosBusq.setIdSede(0);
		criteriosBusq.setIdUbicacion(0);
		criteriosBusq.setIdArea(0);
		criteriosBusq.setOrgGubNombre("");

		setOpcionesEventos(Boolean.TRUE);
		solicitudesReserv = new ArrayList<>();
		setNuevaReservXArea(Boolean.TRUE);

		solicitudSeleccionada = new SolicitudReservAreaDTO();
		solicitudSeleccionadaEdit = new SolicitudReservAreaDTO();
		personalizacionArea = new PersonalizacionAreaDTO();
		recursoTecGuardar = new RelAreaRecursoDTO();
		recursoMobiGuardar = new RelAreaRecursoDTO();
		selMobi = new ArrayList<>();
		selTec = new ArrayList<>();
		selTecGuardar = new ArrayList<>();
		selMobiGuardar = new ArrayList<>();
		selTecCant = new ArrayList<>();
		selMobiCant = new ArrayList<>();

		eventoGeneral = new EventoGeneralDTO();
		setrEC(new ReservacionEventoCapacitacionDTO());
		setsEC(new RelSolicitudEventoCapacitacionDTO());
		rEG = new ReservacionEventoGeneralDTO();
		sEG = new RelSolicitudEventoGeneralDTO();
		setFechaReservacion(new Date());
		solRes = new SolicitudReservacionDTO();
		configuracionAreaReservacion = new ConfiguracionAreaDTO();

		reservacionesCapPorAprobar = new ArrayList<>();
		reservacionesGenPorAprobar = new ArrayList<>();
		infoReservacionGen = new RelSolicitudEventoGeneralDTO();
		infoReservacionCap = new RelSolicitudEventoCapacitacionDTO();
		persoCap = new PersonalizacionAreaDTO();
		persoGen = new PersonalizacionAreaDTO();
		areaSedeInfoGen = new AreaSedeDTO();
		areaSedeInfoCap = new AreaSedeDTO();

		eventoCapacit = false;
		eventoGenerico = false;
	}

	@PostConstruct
	public void iniciaRecursos() {

		eventosCapacitacion = getFacade().getEventoCapacitacionService()
				.consultaEventoPorEstatus(EstadoEventoCapEnum.CALENDARIZADO.getId());
		catUbicacionTerr = getFacade().getCatUbicacionTerrService().findAll(CatUbicacionTerritorial.class);

		this.generaArbolOrgGubernamental();
		this.generaCatalogosTpoCompYEjesCap();

	}

	/**
	 *
	 */
	private void inicializaReservacionesArea(CritBusquedaAreasConfigDTO criterios) {

		scheduleModel = new DefaultScheduleModel();

		AreaSedeDTO area = getFacade().getAreaSedeService().consultaAreaSedePorIdSedeyIdArea(criterios.getIdSede(),
				criterios.getIdArea());

		List<ReservacionEventoCapacitacionDTO> reservacionesCapXArea = getFacade()
				.getReservacionEventoCapacitacionService().buscaReservacionesPorIdAreaSede(area.getIdAreaSede());

		List<ReservacionEventoGeneralDTO> reservacionesGenXArea = getFacade().getReservacionEventoGeneralService()
				.buscaReservacionesPorIdAreaSede(area.getIdAreaSede());

		// agrega reservaciones de eventos de capacitacion al schedule
		if (!ObjectUtils.isNullOrEmpty(reservacionesCapXArea)) {

			for (ReservacionEventoCapacitacionDTO reserv : reservacionesCapXArea) {

				AreaSedeDTO nombreArea = getFacade().getAreaSedeService().buscarPorId(reserv.getIdAreaSede());

				DefaultScheduleEvent eventoArea = new DefaultScheduleEvent(nombreArea.getCatArea().getNombre(),
						(Date) reserv.getFechaInicialReservacion(), (Date) reserv.getFechaFinalReservacion(), reserv);

				eventoArea.setStyleClass(EstatusReservacionEnum
						.obtieneEstatusReservacionById(reserv.getCatEstatusReservacion().getId()).getStyleSchedule());

				this.scheduleModel.addEvent(eventoArea);
			}
		}

		// agrega reservaciones de eventos generales al schedule
		if (!ObjectUtils.isNullOrEmpty(reservacionesGenXArea)) {

			for (ReservacionEventoGeneralDTO reserv : reservacionesGenXArea) {

				AreaSedeDTO nombreArea = getFacade().getAreaSedeService().buscarPorId(reserv.getIdAreaSede());

				DefaultScheduleEvent eventoArea = new DefaultScheduleEvent(nombreArea.getCatArea().getNombre(),
						(Date) reserv.getFechaInicialReservacion(), (Date) reserv.getFechaFinalReservacion(), reserv);

				eventoArea.setStyleClass(EstatusReservacionEnum
						.obtieneEstatusReservacionById(reserv.getCatEstatusReservacion().getId()).getStyleSchedule());

				this.scheduleModel.addEvent(eventoArea);
			}

		}

	}

	public void validarPersonalizacion() {
		if (ObjectUtils.isNullOrEmpty(solicitudSeleccionadaEdit.getPerArea().getEmailResponsable())) {
			RequestContext.getCurrentInstance().execute("PF('dialogPersonalizacion').update();");
			RequestContext.getCurrentInstance().execute("PF('dialogPersonalizacion').show();");
		} else {
			RequestContext.getCurrentInstance().execute("PF('modalReserv').show();");
		}
	}

	@SuppressWarnings("unchecked")
	private void inicializaDatosSolReservcion(CritBusquedaAreasConfigDTO criteriosBusq,
			DefaultScheduleEvent scheduleEvetSel) {

		if (ObjectUtils.isNotNull(criteriosBusq)) {

			SolicitudReservAreaDTO solicitudInicial = new SolicitudReservAreaDTO();

			Map<String, Object> sedesInfo = facade.obtieneSedesInfo(criteriosBusq.getIdOrgGubernamental(),
					criteriosBusq.getIdSede());

			List<NodoDTO> ubiSedes = (List<NodoDTO>) sedesInfo.get("ubiSedes");

			catAreasInfra = (List<CatalogoComunDTO>) sedesInfo.get("catAreasInfra");

			solicitudInicial.setCatAreas(catAreasInfra);

			solicitudInicial.setCatUbicacionSede(ubiSedes);

			if (nuevaReservXArea == Boolean.TRUE) {
				nuevaReservXArea = false;
				solicitudInicial.setPerArea(new PersonalizacionAreaDTO());
				Calendar c = Calendar.getInstance();
				c.setTime(scheduleEvetSel.getStartDate());
				Integer cs = c.get(Calendar.HOUR_OF_DAY);
				Integer m = c.get(Calendar.MINUTE);
				String h = cs.toString() + ":" + m;
				if (cs.toString().length() == 1) {
					h = "0" + h;
				}
				if (m.toString().length() == 1) {
					h = h + "0";
				}
				solicitudInicial.setHoraInicial(h);

				Integer idAreaSede = getFacade().getAreaSedeService()
						.consultaAreaSedePorIdSedeyIdArea(criteriosBusq.getIdSede(), criteriosBusq.getIdArea())
						.getIdAreaSede();
				solicitudInicial.setIdAreaSede(idAreaSede);

				solicitudInicial.setIdArea(criteriosBusq.getIdArea());
				solicitudInicial.setIdSede(criteriosBusq.getIdSede());

				scheduleEvetSel.setData(solicitudInicial);
				solicitudInicial.setEventoSchedule(scheduleEvetSel);

				generaHorasLibres(solicitudInicial);

				asignaHorasB(solicitudInicial);

				solicitudesReserv.add(solicitudInicial);

			}
		}

	}

	private void generaCatalogosTpoCompYEjesCap() {

		List<NodoeHijosDTO> planes = new ArrayList<>();
		List<MallaCurricularDTO> planesAux = new ArrayList<>();
		catTpoCompSedesol = new ArrayList<>();
		catEjesCapacitSedesol = new ArrayList<>();

		MallaCurricularDTO planSedesol = getFacade().getMallaCurricularService().obtenerMallaCurricularPorId(1);
		planesAux.add(planSedesol);

		for (MallaCurricularDTO m : planesAux) {

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

		for (NodoeHijosDTO nh : planes.get(0).getNodosHijos()) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			catTpoCompSedesol.add(cc);
		}

		// Segundo Nivel: Ejes de capacitacion
		for (NodoeHijosDTO nh : planes.get(0).getNodosHijos()) {
			for (NodoeHijosDTO nint : nh.getNodosHijos()) {
				CatalogoComunDTO cc = new CatalogoComunDTO();
				cc.setId(nint.getIdNodo());
				cc.setNombre(nint.getNombre());
				catEjesCapacitSedesol.add(cc);
			}
		}

	}

	public void onNodeSelectDependencia(NodeSelectEvent event) {
		NodoDTO n = (NodoDTO) event.getTreeNode().getData();
		criteriosBusq.setIdOrgGubernamental(n.getId());
		criteriosBusq.setOrgGubNombre(n.getNombre());
		// actualiza output de dependencia
		criteriosBusq.setIdSede(0);
		criteriosBusq.setIdUbicacion(0);
		criteriosBusq.setIdArea(0);

	}

	public void generaArbolOrgGubernamental() {
		List<OrgGubernamentalDTO> orgGubs = orgGubernamentalService.obtenerOrgGubsPadres();
		arbolOrgGubernamental = new DefaultTreeNode();
		this.generaHijosOrgGubAreas(arbolOrgGubernamental, orgGubs);
	}

	public void onChangeBusqUbicacion(ValueChangeEvent e) {

		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idUbicacion = (Integer) e.getNewValue();
			criteriosBusq.setIdUbicacion(idUbicacion);
			catSedesInfra = getFacade().getSedeService().consultaSedesPorUbicacionOrgGub(criteriosBusq.getIdUbicacion(),
					criteriosBusq.getIdOrgGubernamental());
			if (idUbicacion.equals(0)) {
				criteriosBusq.setIdSede(0);
				criteriosBusq.setIdArea(0);
			}

		}
	}

	/**
	 * Busca las areas ya configuradas
	 *
	 * @param e
	 */
	public void onChangeSede(ValueChangeEvent e) {

		// Genera el Catalogo de areas asociadas a la sede
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSede = (Integer) e.getNewValue();
			criteriosBusq.setIdSede(idSede);

			/// consultar configuraciones por Area
			CritBusquedaAreasConfigDTO criteriosConf = new CritBusquedaAreasConfigDTO();
			criteriosConf.setIdSede(idSede);
			catAreasInfra = new ArrayList<>();
			List<ConfiguracionAreaDTO> areasConfiguradas = getFacade().getConfiguracionAreaService()
					.busquedaAreasConfiguradasCriterios(criteriosConf);

			if (ObjectUtils.isNotNull(areasConfiguradas)) {
				for (ConfiguracionAreaDTO s : areasConfiguradas) {
					catAreasInfra.add(s.getCatAreasSede().getCatArea());
					logger.info("AREA: " + s.getCatAreasSede().getCatArea().getNombre());
				}
			}

			if (idSede.equals(0)) {
				criteriosBusq.setIdArea(0);
			}
		}
	}

	/**
	 *
	 * @param e
	 */
	public void onChangeArea(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idArea = (Integer) e.getNewValue();
			if (!idArea.equals(0)) {
				criteriosBusq.setIdArea(idArea);
				setNuevaReservXArea(Boolean.TRUE);

				this.inicializaReservacionesArea(criteriosBusq);
				buscaAreaCriterios();
				llenaImagenesAreaSeleccionada();

			}
		}

	}

	public void limpiarPantalla() {
		criteriosBusq = new CritBusquedaAreasConfigDTO();
		criteriosBusq.setIdArea(0);
		criteriosBusq.setIdUbicacion(0);
		criteriosBusq.setIdSede(0);
		// criteriosBusq.setIdAreaSede(0);
		criteriosBusq.setIdOrgGubernamental(0);
		criteriosBusq.setOrgGubNombre("");
		nodoOrgGubernamentalSel = null;
		generaArbolOrgGubernamental();
		areasConfigBusq = new ArrayList<>();
		scheduleModel = new DefaultScheduleModel();
	}

	/**
	 *
	 * @param selectEvent
	 *            Entra al seleccionar un evento en el schedule
	 */
	public void onEventSelect(SelectEvent selectEvent) {
		eventoReservacion = (DefaultScheduleEvent) selectEvent.getObject();

		if (scheduleCmp.getView().equalsIgnoreCase(ConstantesGestorWeb.SCHEDULE_VIEW_MONTH)) {
			// cambia a vista por dia
			scheduleCmp.setView(SCHEDULEVIEW);
			scheduleCmp.setInitialDate(eventoReservacion.getStartDate());
		} else if (scheduleCmp.getView().equalsIgnoreCase(ConstantesGestorWeb.SCHEDULE_VIEW_AGENDADAY)) {

			if (getEventoReservacion().getData() instanceof ReservacionEventoCapacitacionDTO) {
				// reservacion tipo evento evento capacitacion
				ReservacionEventoCapacitacionDTO reservacionPorAprobar = (ReservacionEventoCapacitacionDTO) eventoReservacion
						.getData();

				RelSolicitudEventoCapacitacionDTO solicitud = getFacade().getSolicitudEventoCapacitacionService()
						.consultaSolicitudesPorIdReservacion(reservacionPorAprobar.getIdReservacionEc())
						.get(ConstantesGestorWeb.INDICE_INICIAL);

				reservacionesCapPorAprobar = facade.getSolicitudEventoCapacitacionService()
						.consultaReservacionesPorIdSolicitud(solicitud.getIdSolicitud());

				reservacionesCapPorAprobar = facade.asignarSolicitudesCapAprobadas(reservacionesCapPorAprobar);

				bolPanelAprobarCap = true;
				RequestContext.getCurrentInstance().execute("PF('dialogWvAprobar').show();");

			} else if (getEventoReservacion().getData() instanceof ReservacionEventoGeneralDTO) {
				// reservacion tipo evento evento general

				ReservacionEventoGeneralDTO reservacionPorAprobar = (ReservacionEventoGeneralDTO) eventoReservacion
						.getData();

				RelSolicitudEventoGeneralDTO solicitud = getFacade().getSolicitudEventoGeneralService()
						.consultaSolicitudesPorIdReservacion(reservacionPorAprobar.getIdReservacionEg())
						.get(ConstantesGestorWeb.INDICE_INICIAL);

				reservacionesGenPorAprobar = getFacade().getSolicitudEventoGeneralService()
						.consultaReservacionesPorIdSolicitud(solicitud.getIdSolicitud());

				reservacionesGenPorAprobar = facade.asignarSolicitudesGenAprobadas(reservacionesGenPorAprobar);

				bolPanelAprobarCap = false;
				RequestContext.getCurrentInstance().execute("PF('dialogWvAprobar').show();");

			}
		}
	}

	/**
	 *
	 * @param selectEvent
	 *            Entra al seleccionar un día en vista de mes o de dia,
	 *            calculando la hora para la vista de dia.
	 */
	public void onDateSelect(SelectEvent se) {

		fechaReservacion = (Date) se.getObject();

		if (scheduleCmp.getView().equalsIgnoreCase(ConstantesGestorWeb.SCHEDULE_VIEW_MONTH)) {
			// cambia a vista por dia
			scheduleCmp.setView(SCHEDULEVIEW);
			scheduleCmp.setInitialDate(fechaReservacion);

		} else {
			if (!criteriosBusq.getIdArea().equals(0)) {
				String nombreEvento = getFacade().getCatAreaInfraService()
						.buscarPorId(criteriosBusq.getIdArea(), CatAreaInfraestructura.class).getNombre();

				eventoReservacion = new DefaultScheduleEvent(nombreEvento, (Date) se.getObject(),
						(Date) se.getObject());
				eventoReservacion.setStyleClass("evnt_no_aprobado");
				this.inicializaDatosSolReservcion(criteriosBusq, getEventoReservacion());
				setNuevaReservXArea(Boolean.FALSE);
				RequestContext.getCurrentInstance().execute(DIALOGRESERVACIONSHOW);
			} else {
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion').show()");
			}

		}
	}

	/**
	 *
	 * @param se
	 */
	public void onViewChange(SelectEvent se) {
		if (se.getObject().toString().equals(SCHEDULEVIEW)) {
			setEventDateOn(Boolean.TRUE);
		} else if (se.getObject().toString().equals(SCHEDULEVIEWMONTH)) {
			setEventDateOn(Boolean.FALSE);
		}

	}

	private void enviarCorreoCap(SolicitudReservAreaDTO reservacion, EventoCapacitacionDTO eventoCapacitacionCorreo) {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		StringBuilder responsable = new StringBuilder(reservacion.getPerArea().getEmailResponsable());
		StringBuilder area = new StringBuilder(reservacion.getPerArea().getCatAreasSede().getCatArea().getNombre());
		StringBuilder fechaInicio = new StringBuilder(formatter.format(reservacion.getEventoSchedule().getStartDate()));
		StringBuilder fechaFinal = new StringBuilder(formatter.format(reservacion.getEventoSchedule().getEndDate()));
		StringBuilder nombreEvento = new StringBuilder(eventoCapacitacionCorreo.getNombreEc());

		CorreoDTO correoDto = getCorreoElectronicoService().asignaParametrosConfigCorreo();
		correoDto.setTitulo("Reservación de áreas");
		correoDto.setAsunto("Nueva solicitud de reservación");
		correoDto
				.setDestinatarios(new ArrayList<String>(Arrays.asList(reservacion.getPerArea().getEmailResponsable())));
		correoDto.setRemitente(parametroSistemaService
				.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
		correoDto.setContenido(MessageFormat.format(
				"<table>" + "<tr>"
						+ "<th colspan='2' style='border: 1px solid black;' bgcolor='#99cc00'>Solicitud de Salones o Salas</th>"
						+ "</tr><tr><td bgcolor='#99cc00'>Responsable:</td><td>{0}</td></tr>"
						+ "<tr><td bgcolor='#99cc00'>Área:</td><td>{1}</td></tr><tr>"
						+ "<td bgcolor='#99cc00'>Fecha inicial:</td><td>{2}</td></tr><tr>"
						+ "<td bgcolor='#99cc00'>Fecha final:</td><td>{3}</td></tr><tr>"
						+ "<td bgcolor='#99cc00'>Nombre del evento:</td><td>{4}</td></tr><tr>",
				responsable, area, fechaInicio, fechaFinal, nombreEvento));

		correoElectronicoService.enviaCorreoElectronico(correoDto);

	}

	private void enviarCorreoGen(SolicitudReservAreaDTO reservacion, EventoGeneralDTO eventoGeneralCorreo) {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		StringBuilder responsable = new StringBuilder(reservacion.getPerArea().getEmailResponsable());
		StringBuilder area = new StringBuilder(reservacion.getPerArea().getCatAreasSede().getCatArea().getNombre());
		StringBuilder fechaInicio = new StringBuilder(formatter.format(reservacion.getEventoSchedule().getStartDate()));
		StringBuilder fechaFinal = new StringBuilder(formatter.format(reservacion.getEventoSchedule().getEndDate()));
		StringBuilder nombreEvento = new StringBuilder(eventoGeneralCorreo.getNombre());

		CorreoDTO correoDto = getCorreoElectronicoService().asignaParametrosConfigCorreo();
		correoDto.setTitulo("Reservación de áreas");
		correoDto.setAsunto("Nueva solicitud de reservación");
		correoDto
				.setDestinatarios(new ArrayList<String>(Arrays.asList(reservacion.getPerArea().getEmailResponsable())));
		correoDto.setRemitente(parametroSistemaService
				.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
		correoDto.setContenido(MessageFormat.format(
				"<table><tr>"
						+ "<th colspan='2' style='border: 1px solid black;' bgcolor='#99cc00'>Solicitud de Salones o Salas</th>"
						+ "</tr><tr><td bgcolor='#99cc00'>Responsable:</td><td>{0}</td></tr>"
						+ "<tr><td bgcolor='#99cc00'>Área:</td><td>{1}</td></tr><tr>"
						+ "<td bgcolor='#99cc00'>Fecha inicial:</td><td>{2}</td></tr><tr>"
						+ "<td bgcolor='#99cc00'>Fecha final:</td><td>{3}</td></tr><tr>"
						+ "<td bgcolor='#99cc00'>Nombre del evento:</td><td>{4}</td></tr><tr>"
						+ "<td bgcolor='#99cc00'>No. de personas:</td><td></td></tr></table>",
				responsable, area, fechaInicio, fechaFinal, nombreEvento));

		correoElectronicoService.enviaCorreoElectronico(correoDto);

	}

	/**
	 * Metodo que realizara la persistencia de los datos
	 *
	 * @param actionEvent
	 */
	public void agregaEventoReservacion() {

		Boolean bolCapPersi = false;
		Boolean bolGenPersi = false;

		boolean validacionTabla = UtileriasLogistica.validaCamposVacios(solicitudesReserv);

		if (!validacionTabla) {
			agregarMsgError("Favor de llenar todos los campos.", null, sistema);
			RequestContext.getCurrentInstance().execute(DIALOGRESERVACIONSHOW);

		} else {

			Boolean aprobado = UtileriasLogistica.validarHorasAreas(solicitudesReserv);

			if (aprobado) {

				Date fechaActual = getFechaActual();

				for (SolicitudReservAreaDTO sol : solicitudesReserv) {

					sol = UtileriasLogistica.asignaHoraInicialFinalSchedule(sol);

					if (tipoEvento.equals(ConstantesGestorWeb.RESERV_EVENTO_GENERAL)) {

						eventoGeneral.setCatAreasSede(getFacade().getAreaSedeService()
								.consultaAreaSedePorIdSedeyIdArea(sol.getIdSede(), sol.getIdArea()));
						eventoGeneral.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
						eventoGeneral.setFechaRegistro(fechaActual);
					}

				}

				persistePersonalizacionArea();

				// Persistencia tblSolicitudReservacion
				solRes.setFechaRegistro(new Date());
				solRes.setUsuarioModifico(getUsuarioEnSession().getIdPersona());

				if (!ObjectUtils.isNullOrEmpty(solRes)) {
					// GUSTAVO --BitacoraUtil.llenarBitacora(solRes,
					// getUsuarioEnSession(),
					// ConstantesBitacora.SOL_RESERVACION_AGREGAR, request);
					solRes = getFacade().getSolicitudReservacionService().guardar(solRes).getDto();

					bolCapPersi = eventoCapacit;
					bolGenPersi = eventoGenerico;

					eventoReservacion = new DefaultScheduleEvent();
					tipoEvento = null;
					eventoCapacit = Boolean.FALSE;
					detReservEventoCap = Boolean.FALSE;
					opcionesEventos = true;

				} else {
					agregarMsgError("Ocurrio un error en el guardado de datos", null);
					scheduleModel = new DefaultScheduleModel();
				}

				if (bolGenPersi) {
					// GUSTAVO --BitacoraUtil.llenarBitacora(eventoGeneral,
					// getUsuarioEnSession(),
					// ConstantesBitacora.EVENTO_GENERAL_AGREGAR, request);
					// persistencia de evento general
					eventoGeneral = getFacade().getEventoGeneralService().guardar(eventoGeneral).getDto();

					for (SolicitudReservAreaDTO r : solicitudesReserv) {
						// Persistencia tblReservacionEvento general

						AreaSedeDTO area = getFacade().getAreaSedeService()
								.consultaAreaSedePorIdSedeyIdArea(r.getIdSede(), r.getIdArea());

						rEG = new ReservacionEventoGeneralDTO();
						rEG.setIdAreaSede(area.getIdAreaSede());

						// set hora inicial y final
						String hi = Character.toString(r.getHoraInicial().charAt(0))
								+ Character.toString(r.getHoraInicial().charAt(1));
						String mi = Character.toString(r.getHoraInicial().charAt(3))
								+ Character.toString(r.getHoraInicial().charAt(4));

						String hf = Character.toString(r.getHoraFinal().charAt(0))
								+ Character.toString(r.getHoraFinal().charAt(1));
						String mf = Character.toString(r.getHoraFinal().charAt(3))
								+ Character.toString(r.getHoraFinal().charAt(4));

						// set fecha y hora inicial
						Calendar c = Calendar.getInstance();
						c.setTime(fechaReservacion);
						c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hi));
						c.set(Calendar.MINUTE, Integer.parseInt(mi));
						setFechaReservacion(c.getTime());
						rEG.setFechaInicialReservacion(fechaReservacion);

						// set fecha y hora final
						Calendar cb = Calendar.getInstance();
						cb.setTime(fechaReservacion);
						cb.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hf));
						cb.set(Calendar.MINUTE, Integer.parseInt(mf));
						setFechaReservacion(cb.getTime());
						rEG.setFechaFinalReservacion(fechaReservacion);

						rEG.setTblEventoGeneral(eventoGeneral);
						rEG.setIdPersonalizacionArea(r.getPerArea().getIdPersonalizacion());
						rEG.setFechaRegistro(new Date());
						rEG.setUsuarioRegistro(getUsuarioEnSession().getIdPersona());

						CatalogoComunDTO estatus = getFacade().getEstatusReservacionService().buscarPorId(
								EstatusReservacionEnum.RESERVADO_SIN_APROBAR.getId(), CatEstatusReservacion.class);

						rEG.setCatEstatusReservacion(estatus);
						// GUSTAVO --BitacoraUtil.llenarBitacora(rEC,
						// getUsuarioEnSession(),
						// ConstantesBitacora.RESERVACION_EVENTO_GENERAL_AGREGAR,
						// request);
						rEG = getFacade().getReservacionEventoGeneralService().guardar(rEG).getDto();

						// Genera los eventos en la agenda
						DefaultScheduleEvent evento = new DefaultScheduleEvent(r.getEventoSchedule().getTitle(),
								r.getEventoSchedule().getStartDate(), r.getEventoSchedule().getEndDate(), rEG);
						evento.setStyleClass(EstatusReservacionEnum
								.obtieneEstatusReservacionById(rEG.getCatEstatusReservacion().getId())
								.getStyleSchedule());

						scheduleModel.addEvent(evento);
						this.inicializaReservacionesArea(criteriosBusq);

						enviarCorreoGen(r, eventoGeneral);

						// Persistencia relSolicitudEvento general
						sEG = new RelSolicitudEventoGeneralDTO();
						sEG.setFechaRegistro(new Date());
						sEG.setIdReservacionEG(rEG.getIdReservacionEg());
						sEG.setIdSolicitud(solRes.getIdSolicitud());
						sEG.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
						// GUSTAVO --BitacoraUtil.llenarBitacora(sEG,
						// getUsuarioEnSession(),
						// ConstantesBitacora.SOLICITUD_EVENTO_GENERAL_AGREGAR,
						// request);
						sEG = getFacade().getSolicitudEventoGeneralService().guardar(sEG).getDto();
					}

				} else if (bolCapPersi) {
					for (SolicitudReservAreaDTO r : solicitudesReserv) {
						// Persistencia tblReservacionEvento capacitacion

						AreaSedeDTO area = getFacade().getAreaSedeService()
								.consultaAreaSedePorIdSedeyIdArea(r.getIdSede(), r.getIdArea());

						rEC = new ReservacionEventoCapacitacionDTO();
						rEC.setIdAreaSede(area.getIdAreaSede());

						// set hora inicial y final
						String hi = Character.toString(r.getHoraInicial().charAt(0))
								+ Character.toString(r.getHoraInicial().charAt(1));
						String mi = Character.toString(r.getHoraInicial().charAt(3))
								+ Character.toString(r.getHoraInicial().charAt(4));

						String hf = Character.toString(r.getHoraFinal().charAt(0))
								+ Character.toString(r.getHoraFinal().charAt(1));
						String mf = Character.toString(r.getHoraFinal().charAt(3))
								+ Character.toString(r.getHoraFinal().charAt(4));

						// set fecha y hora inicial
						Calendar c = Calendar.getInstance();
						c.setTime(fechaReservacion);
						c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hi));
						c.set(Calendar.MINUTE, Integer.parseInt(mi));
						fechaReservacion = c.getTime();
						rEC.setFechaInicialReservacion(fechaReservacion);

						// set fecha y hora final
						Calendar cb = Calendar.getInstance();
						cb.setTime(fechaReservacion);
						cb.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hf));
						cb.set(Calendar.MINUTE, Integer.parseInt(mf));
						fechaReservacion = cb.getTime();
						rEC.setFechaFinalReservacion(fechaReservacion);

						rEC.setTblEvento(evtCapacitacionSelect);

						rEC.setIdPersonalizacionArea(r.getPerArea().getIdPersonalizacion());
						rEC.setFechaRegistro(new Date());
						rEC.setUsuarioRegistro(getUsuarioEnSession().getIdPersona());

						CatalogoComunDTO estatus = getFacade().getEstatusReservacionService().buscarPorId(
								EstatusReservacionEnum.RESERVADO_SIN_APROBAR.getId(), CatEstatusReservacion.class);

						rEC.setCatEstatusReservacion(estatus);
						// GUSTAVO --BitacoraUtil.llenarBitacora(getrEC(),
						// getUsuarioEnSession(),
						// ConstantesBitacora.RESERVACION_EVENTO_CAPACITACION_AGREGAR,
						// request);
						rEC = getFacade().getReservacionEventoCapacitacionService().guardar(getrEC()).getDto();

						// Genera los eventos en la agenda
						DefaultScheduleEvent evento = new DefaultScheduleEvent(r.getEventoSchedule().getTitle(),
								r.getEventoSchedule().getStartDate(), r.getEventoSchedule().getEndDate(), rEC);
						evento.setStyleClass(EstatusReservacionEnum
								.obtieneEstatusReservacionById(rEC.getCatEstatusReservacion().getId())
								.getStyleSchedule());

						scheduleModel.addEvent(evento);
						this.inicializaReservacionesArea(criteriosBusq);

						enviarCorreoCap(r, evtCapacitacionSelect);

						// Persistencia relSolicitudEvento capacitacion
						sEC = new RelSolicitudEventoCapacitacionDTO();
						sEC.setFechaRegistro(new Date());
						sEC.setIdReservacionEC(getrEC().getIdReservacionEc());
						sEC.setIdSolicitud(getSolRes().getIdSolicitud());
						sEC.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
						// GUSTAVO --BitacoraUtil.llenarBitacora(getrEC(),
						// getUsuarioEnSession(),
						// ConstantesBitacora.SOLICITUD_EVENTO_CAPACITACION_AGREGAR,
						// request);
						sEC = getFacade().getSolicitudEventoCapacitacionService().guardar(sEC).getDto();
					}
				}

				if (ObjectUtils.isNotNull(sEC)) {

					solicitudesReserv = new ArrayList<>();
					nuevaReservXArea = true;
					solRes = new SolicitudReservacionDTO();

					RequestContext.getCurrentInstance()
							.execute("PF('dialogReservacion').hide();PF('scheduleReserv').update();");

					agregarMsgInfo("Se guardo correctamente la reservación", null);
				} else {
					agregarMsgError("Falló el guardado de los datos", null);
				}

			} else {
				agregarMsgError("Horarios encimados, modifica el horario o las areas", null, sistema);
				RequestContext.getCurrentInstance().execute(DIALOGRESERVACIONSHOW);
			}

		}
	}

	/**
	 *
	 */
	public void tipoEventoSel() {
		if (tipoEvento.equals(ConstantesGestorWeb.RESERV_EVENTO_CAPACITACION)) {
			setEventoCapacit(Boolean.TRUE);
			setEventoGenerico(Boolean.FALSE);
			setMuestraEventosCap(Boolean.TRUE);

		} else if (tipoEvento.equals(ConstantesGestorWeb.RESERV_EVENTO_GENERAL)) {
			setEventoCapacit(Boolean.FALSE);
			setOpcionesEventos(Boolean.FALSE);
			setDetReservEventoCap(Boolean.TRUE);
			setMuestraEventosCap(Boolean.FALSE);
			eventoGenerico = true;
		}
	}

	/**
	 *
	 * @param e
	 */
	public void changeEstatusEventoCap(ValueChangeEvent e) {
		Integer idEstEvnt = Integer.parseInt(e.getNewValue().toString());
		eventosCapacitacion = getFacade().getEventoCapacitacionService().consultaEventoPorEstatus(idEstEvnt);
	}

	/**
	 *
	 * @param event
	 */
	public void onRowSelect(SelectEvent event) {
		eventoCapacitacion = (EventoCapacitacionDTO) event.getObject();
		setDetReservEventoCap(Boolean.TRUE);
		setEventoCapacit(Boolean.TRUE);
		setOpcionesEventos(Boolean.FALSE);
		setMuestraEventosCap(Boolean.FALSE);
		evtCapacitacionSelect = eventoCapacitacion;
	}

	/**
	 * Cancelar Reservacion
	 */
	public void cancelaReservacion() {
		logger.info("Cancelar Reservacion");

	}

	/**
	 * Cancelar fecha en schedule
	 */
	public void cancelaFechaReservacion() {
		logger.info("Cancelar Reservacion");

		nuevaReservXArea = true;
		setEventoCapacit(Boolean.FALSE);
		setOpcionesEventos(Boolean.TRUE);
		setDetReservEventoCap(Boolean.FALSE);
		setEventoGenerico(Boolean.FALSE);
		setMuestraEventosCap(Boolean.FALSE);
		solicitudesReserv = new ArrayList<>();
		tipoEvento = null;

		eventoCapacitacion = new EventoCapacitacionDTO();
		eventoGeneral = new EventoGeneralDTO();
		solRes = new SolicitudReservacionDTO();

		if (ObjectUtils.isNotNull(getEventoReservacion())) {
			scheduleModel.deleteEvent(getEventoReservacion());
		}
	}

	public void buscarReservacionesArea() {
		logger.info("Buscar Reservaciones de area");
	}

	/**
	 *
	 * @param idTpoComp
	 * @return
	 */
	public String obtieneNombreTpoCompetencia(Integer idTpoComp) {

		if (ObjectUtils.isNotNull(idTpoComp)) {
			for (CatalogoComunDTO tpoCom : catTpoCompSedesol) {
				if (tpoCom.getId().equals(idTpoComp)) {
					return tpoCom.getNombre();
				}
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
			for (CatalogoComunDTO ejeCap : catEjesCapacitSedesol) {
				if (ejeCap.getId().compareTo(idEjeCap) == 0) {
					return ejeCap.getNombre();
				}
			}
		}
		return null;
	}

	/**
	 * Agrega un registro adicional: sala Adicional
	 */
	@SuppressWarnings("unchecked")
	public void agregaSalaAdicional() {

		if (!ObjectUtils.isNullOrEmpty(solicitudesReserv)) {

			SolicitudReservAreaDTO solicitudAdicional = new SolicitudReservAreaDTO();

			Map<String, Object> sedesInfo = facade.obtieneSedesInfo(criteriosBusq.getIdOrgGubernamental(),
					criteriosBusq.getIdSede());

			CatalogoComunDTO area = getFacade().getCatAreaInfraService().buscarPorId(criteriosBusq.getIdArea(),
					CatAreaInfraestructura.class);

			List<NodoDTO> ubiSedes = (List<NodoDTO>) sedesInfo.get("ubiSedes");

			catAreasInfra = (List<CatalogoComunDTO>) sedesInfo.get("catAreasInfra");

			solicitudAdicional.setCatAreas(catAreasInfra);

			solicitudAdicional.setCatUbicacionSede(ubiSedes);

			solicitudAdicional.setIdSede(criteriosBusq.getIdSede());

			solicitudAdicional.setPerArea(new PersonalizacionAreaDTO());

			Integer idAreaSede = getFacade().getAreaSedeService()
					.consultaAreaSedePorIdSedeyIdArea(criteriosBusq.getIdSede(), criteriosBusq.getIdArea())
					.getIdAreaSede();
			solicitudAdicional.setIdAreaSede(idAreaSede);

			solicitudAdicional.setEventoSchedule(new DefaultScheduleEvent(area.getNombre().concat("- Adicional"),
					new Date(), (Date) new Date(), solicitudAdicional));
			solicitudAdicional.getEventoSchedule().setStyleClass("evnt_no_aprobado");

			solicitudesReserv.add(solicitudAdicional);
		}
	}

	public void limpiaSS() {
		solicitudSeleccionada = new SolicitudReservAreaDTO();
	}

	public void navegaPersonalizacionArea() {

		selTec = new ArrayList<>();
		selMobi = new ArrayList<>();

		CritBusquedaAreasConfigDTO critArea = new CritBusquedaAreasConfigDTO();
		critArea.setOrgGubNombre(criteriosBusq.getOrgGubNombre());
		critArea.setIdOrgGubernamental(criteriosBusq.getIdOrgGubernamental());
		critArea.setIdUbicacion(criteriosBusq.getIdUbicacion());
		critArea.setIdSede(solicitudSeleccionada.getIdSede());
		critArea.setIdArea(solicitudSeleccionada.getIdArea());

		configuracionAreaReservacion = getFacade().getConfiguracionAreaService()
				.busquedaAreasConfiguradasCriterios(critArea).get(ConstantesGestorWeb.INDICE_INICIAL);

		solicitudSeleccionadaEdit = new SolicitudReservAreaDTO();

		if (solicitudSeleccionada.getPersonalizacionBoolean() != null
				&& solicitudSeleccionada.getPersonalizacionBoolean()) {
			mapper.map(solicitudSeleccionada, solicitudSeleccionadaEdit);

			selMobi = solicitudSeleccionadaEdit.getPerArea().getRecMob();
			selTec = solicitudSeleccionadaEdit.getPerArea().getRecTec();
			llenaPersonalizarDistribucion();

		} else {

			mapper.map(solicitudSeleccionada, solicitudSeleccionadaEdit);

			solicitudSeleccionadaEdit.setPerArea(new PersonalizacionAreaDTO());
			solicitudSeleccionadaEdit.getPerArea()
					.setEmailResponsable(configuracionAreaReservacion.getEmailResponsable());
			solicitudSeleccionadaEdit.getPerArea().setEmailCc(configuracionAreaReservacion.getEmailCc());
			llenaRecursos();
		}
		llenaImagenes();
	}

	public void actualizaEstatusReservCap() {
		for (RelSolicitudEventoCapacitacionDTO r : reservacionesCapPorAprobar) {
			// GUSTAVO
			// --BitacoraUtil.llenarBitacora(r.getTblReservacionEventoCapacitacion(),
			// getUsuarioEnSession(),
			// ConstantesBitacora.RESERVACION_EVENTO_CAPACITACION_EDITAR,
			// request);
			r.setTblReservacionEventoCapacitacion(getFacade().getReservacionEventoCapacitacionService()
					.actualizar(r.getTblReservacionEventoCapacitacion()).getDto());
		}

		this.inicializaReservacionesArea(criteriosBusq);

		bolPanelAprobarCap = false;
	}

	public void actualizaEstatusReservGen() {
		for (RelSolicitudEventoGeneralDTO r : reservacionesGenPorAprobar) {
			// GUSTAVO
			// --BitacoraUtil.llenarBitacora(r.getTblReservacionEventoGeneral(),
			// getUsuarioEnSession(),
			// ConstantesBitacora.RESERVACION_EVENTO_GENERAL_EDITAR, request);
			r.setTblReservacionEventoGeneral(getFacade().getReservacionEventoGeneralService()
					.actualizar(r.getTblReservacionEventoGeneral()).getDto());
		}
		this.inicializaReservacionesArea(criteriosBusq);

		bolPanelAprobarCap = true;
	}

	public void asignaInfoReservSeleccionadaGen(RelSolicitudEventoGeneralDTO reservGen) {
		infoReservacionGen = new RelSolicitudEventoGeneralDTO();
		infoReservacionGen = reservGen;

		persoGen = getFacade().getPersonalizacionAreaService()
				.buscarPorId(reservGen.getTblReservacionEventoGeneral().getIdPersonalizacionArea());

		areaSedeInfoGen = getFacade().getAreaSedeService()
				.buscarPorId(reservGen.getTblReservacionEventoGeneral().getIdAreaSede());

		bolInfoReserv = false;
	}

	public String fechaInicialInfoGen() {

		Calendar c = Calendar.getInstance();
		c.setTime(infoReservacionGen.getTblReservacionEventoGeneral().getFechaInicialReservacion());

		DateFormat dateFormat = new SimpleDateFormat(FORMATODIAMESANO);
		return dateFormat.format(c.getTime());

	}

	public String fechaFinalInfoGen() {
		Calendar c = Calendar.getInstance();
		c.setTime(infoReservacionGen.getTblReservacionEventoGeneral().getFechaFinalReservacion());

		DateFormat dateFormat = new SimpleDateFormat(FORMATODIAMESANO);
		return dateFormat.format(c.getTime());

	}

	public String horarioInfoGen() {
		Calendar c = Calendar.getInstance();
		c.setTime(infoReservacionGen.getTblReservacionEventoGeneral().getFechaInicialReservacion());

		Calendar c2 = Calendar.getInstance();
		c2.setTime(infoReservacionGen.getTblReservacionEventoGeneral().getFechaFinalReservacion());

		DateFormat dateFormat = new SimpleDateFormat(FORMATOHORAMINUTO);
		return dateFormat.format(c.getTime()) + " - " + dateFormat.format(c2.getTime());
	}

	public String fechaInicialInfoCap() {
		Calendar c = Calendar.getInstance();
		c.setTime(infoReservacionCap.getTblReservacionEventoCapacitacion().getFechaInicialReservacion());

		DateFormat dateFormat = new SimpleDateFormat(FORMATODIAMESANO);
		return dateFormat.format(c.getTime());
	}

	public String fechaFinalInfoCap() {
		Calendar c = Calendar.getInstance();
		c.setTime(infoReservacionCap.getTblReservacionEventoCapacitacion().getFechaFinalReservacion());

		DateFormat dateFormat = new SimpleDateFormat(FORMATODIAMESANO);
		return dateFormat.format(c.getTime());
	}

	public String horarioInfoCap() {
		Calendar c = Calendar.getInstance();
		c.setTime(infoReservacionCap.getTblReservacionEventoCapacitacion().getFechaInicialReservacion());

		Calendar c2 = Calendar.getInstance();
		c2.setTime(infoReservacionCap.getTblReservacionEventoCapacitacion().getFechaFinalReservacion());

		DateFormat dateFormat = new SimpleDateFormat(FORMATOHORAMINUTO);
		return dateFormat.format(c.getTime()) + " - " + dateFormat.format(c2.getTime());
	}

	public void asignaInfoReservSeleccionadaCap(RelSolicitudEventoCapacitacionDTO reservCap) {
		infoReservacionCap = new RelSolicitudEventoCapacitacionDTO();
		infoReservacionCap = reservCap;

		persoCap = getFacade().getPersonalizacionAreaService()
				.buscarPorId(reservCap.getTblReservacionEventoCapacitacion().getIdPersonalizacionArea());

		areaSedeInfoCap = getFacade().getAreaSedeService()
				.buscarPorId(reservCap.getTblReservacionEventoCapacitacion().getIdAreaSede());

		bolInfoReserv = true;
	}

	public void actualizaIconoGen(RelSolicitudEventoGeneralDTO reservGen) {
		RelSolicitudEventoGeneralDTO reservacion = reservGen;
		Integer estatus = reservacion.getTblReservacionEventoGeneral().getCatEstatusReservacion().getId();

		if (estatus == EstatusReservacionEnum.RESERVADO_SIN_APROBAR.getId()) {
			reservacion.setAprobado(true);

			CatalogoComunDTO catEstatus = getFacade().getCatEstatusReservacionService()
					.buscarPorId(EstatusReservacionEnum.RESERVADO_APROBADO.getId(), CatEstatusReservacion.class);
			reservacion.getTblReservacionEventoGeneral().setCatEstatusReservacion(catEstatus);
		} else if (estatus == EstatusReservacionEnum.RESERVADO_APROBADO.getId()) {
			reservacion.setAprobado(false);

			CatalogoComunDTO catEstatus = getFacade().getCatEstatusReservacionService()
					.buscarPorId(EstatusReservacionEnum.RESERVADO_SIN_APROBAR.getId(), CatEstatusReservacion.class);
			reservacion.getTblReservacionEventoGeneral().setCatEstatusReservacion(catEstatus);
		}

	}

	public void actualizaIconoCap(RelSolicitudEventoCapacitacionDTO reservCap) {
		RelSolicitudEventoCapacitacionDTO reservacion = reservCap;
		Integer estatus = reservacion.getTblReservacionEventoCapacitacion().getCatEstatusReservacion().getId();

		if (estatus == EstatusReservacionEnum.RESERVADO_SIN_APROBAR.getId()) {
			reservacion.setAprobado(true);

			CatalogoComunDTO catEstatus = getFacade().getCatEstatusReservacionService()
					.buscarPorId(EstatusReservacionEnum.RESERVADO_APROBADO.getId(), CatEstatusReservacion.class);
			reservacion.getTblReservacionEventoCapacitacion().setCatEstatusReservacion(catEstatus);
		} else if (estatus.equals(EstatusReservacionEnum.RESERVADO_APROBADO.getId())) {
			reservacion.setAprobado(false);

			CatalogoComunDTO catEstatus = getFacade().getCatEstatusReservacionService()
					.buscarPorId(EstatusReservacionEnum.RESERVADO_SIN_APROBAR.getId(), CatEstatusReservacion.class);
			reservacion.getTblReservacionEventoCapacitacion().setCatEstatusReservacion(catEstatus);
		}
	}

	private List<String> obtieneArray24Horas() {
		List<String> horas = new ArrayList<>();

		horas.add("00:00");
		horas.add("00:30");
		horas.add("01:00");
		horas.add("01:30");
		horas.add("02:00");
		horas.add("02:30");
		horas.add("03:00");
		horas.add("03:30");
		horas.add("04:00");
		horas.add("04:30");
		horas.add("05:00");
		horas.add("05:30");
		horas.add("06:00");
		horas.add("06:30");
		horas.add("07:00");
		horas.add("07:30");
		horas.add("08:00");
		horas.add("08:30");
		horas.add("09:00");
		horas.add("09:30");
		horas.add("10:00");
		horas.add("10:30");
		horas.add("11:00");
		horas.add("11:30");
		horas.add("12:00");
		horas.add("12:30");
		horas.add("13:00");
		horas.add("13:30");
		horas.add("14:00");
		horas.add("14:30");
		horas.add("15:00");
		horas.add("15:30");
		horas.add("16:00");
		horas.add("16:30");
		horas.add("17:00");
		horas.add("17:30");
		horas.add("18:00");
		horas.add("18:30");
		horas.add("19:00");
		horas.add("19:30");
		horas.add("20:00");
		horas.add("20:30");
		horas.add("21:00");
		horas.add("21:30");
		horas.add("22:00");
		horas.add("22:30");
		horas.add("23:00");
		horas.add("23:30");

		return horas;
	}

	private List<String> horasCap(ReservacionEventoCapacitacionDTO reservacionC) {

		int indexInicial = 0;
		int indexFinal = 0;
		List<String> horasOcupadas = new ArrayList<>();
		List<String> array24Horas = obtieneArray24Horas();

		Calendar cI = Calendar.getInstance();
		cI.setTime(reservacionC.getFechaInicialReservacion());

		Calendar cF = Calendar.getInstance();
		cF.setTime(reservacionC.getFechaFinalReservacion());

		DateFormat dateFormat = new SimpleDateFormat(FORMATOHORAMINUTO);

		String horaInicial = dateFormat.format(cI.getTime());
		String horaFinal = dateFormat.format(cF.getTime());

		for (String h : array24Horas) {
			if (h.equals(horaInicial)) {
				indexInicial = array24Horas.indexOf(h);
			}
			if (h.equals(horaFinal)) {
				indexFinal = array24Horas.indexOf(h);
			}
		}

		for (int i = indexInicial; i < indexFinal; i++) {
			horasOcupadas.add(array24Horas.get(i));
		}

		return horasOcupadas;

	}

	private List<String> horasGen(ReservacionEventoGeneralDTO reservacionG) {
		int indexInicial = 0;
		int indexFinal = 0;
		List<String> horasOcupadas = new ArrayList<>();
		List<String> array24Horas = obtieneArray24Horas();

		Calendar cI = Calendar.getInstance();
		cI.setTime(reservacionG.getFechaInicialReservacion());

		Calendar cF = Calendar.getInstance();
		cF.setTime(reservacionG.getFechaFinalReservacion());

		DateFormat dateFormat = new SimpleDateFormat(FORMATOHORAMINUTO);

		String horaInicial = dateFormat.format(cI.getTime());
		String horaFinal = dateFormat.format(cF.getTime());

		for (String h : array24Horas) {
			if (h.equals(horaInicial)) {
				indexInicial = array24Horas.indexOf(h);
			}
			if (h.equals(horaFinal)) {
				indexFinal = array24Horas.indexOf(h);
			}
		}

		for (int i = indexInicial; i < indexFinal; i++) {
			horasOcupadas.add(array24Horas.get(i));
		}

		return horasOcupadas;
	}

	public void asignaHorasB(SolicitudReservAreaDTO e) {
		SolicitudReservAreaDTO reservacion = e;

		List<String> horasOcupadas = new ArrayList<>();

		DateFormat formatter = new SimpleDateFormat(FORMATODIAMESANO);
		String fechaRes = formatter.format(fechaReservacion);

		List<ReservacionEventoCapacitacionDTO> reservacionesC = getFacade().getReservacionEventoCapacitacionService()
				.buscaReservacionesPorIdAreaSede(reservacion.getIdAreaSede());
		List<ReservacionEventoGeneralDTO> reservacionesG = getFacade().getReservacionEventoGeneralService()
				.buscaReservacionesPorIdAreaSede(reservacion.getIdAreaSede());

		if (!ObjectUtils.isNullOrEmpty(reservacionesC)) {
			// lista de strings
			for (ReservacionEventoCapacitacionDTO res : reservacionesC) {
				if (fechaRes.equals(formatter.format(res.getFechaInicialReservacion()))) {
					horasOcupadas.addAll(horasCap(res));
				}
			}
		}

		if (!ObjectUtils.isNullOrEmpty(reservacionesG)) {
			// lista de strings
			for (ReservacionEventoGeneralDTO res : reservacionesG) {
				if (fechaRes.equals(formatter.format(res.getFechaInicialReservacion()))) {
					horasOcupadas.addAll(horasGen(res));
				}
			}
		}

		List<String> array24Horas = obtieneArray24Horas();
		List<String> horasB = new ArrayList<>();

		// index de hora inicial
		Integer indexHI = 0;
		for (int i = 0; i < array24Horas.size(); i++) {
			if (array24Horas.get(i).equals(reservacion.getHoraInicial())) {
				indexHI = i;
			}
		}

		// llena las horas finales
		boolean stop = false;
		for (int i = indexHI + 1; i < array24Horas.size(); i++) {
			if (stop) {
				break;
			} else {
				horasB.add(array24Horas.get(i));
				for (String h : horasOcupadas) {

					if (stop) {
						break;
					} else if (i != array24Horas.size() - 1) {

						if (array24Horas.get(i + 1).equals(h)) {
							horasB.add(h);
							stop = true;
						}
					}
				}
			}
		}

		if (horasB.size() > 1 && !horasOcupadas.isEmpty()) {
			if (horasB.get(horasB.size() - 1).equals(horasOcupadas.get(1))) {
				horasB.remove(horasOcupadas.get(1));
			}
		}

		reservacion.setHorasB(horasB);
	}

	public void generaHorasLibres(SolicitudReservAreaDTO e) {
		SolicitudReservAreaDTO solicitud = e;

		DateFormat formatter = new SimpleDateFormat(FORMATODIAMESANO);
		String fechaRes = formatter.format(fechaReservacion);

		List<String> horas = new ArrayList<>();

		Integer idAreaSede = getFacade().getAreaSedeService()
				.consultaAreaSedePorIdSedeyIdArea(e.getIdSede(), e.getIdArea()).getIdAreaSede();

		solicitud.setIdAreaSede(idAreaSede);

		List<ReservacionEventoCapacitacionDTO> reservacionesC = getFacade().getReservacionEventoCapacitacionService()
				.buscaReservacionesPorIdAreaSede(solicitud.getIdAreaSede());
		List<ReservacionEventoGeneralDTO> reservacionesG = getFacade().getReservacionEventoGeneralService()
				.buscaReservacionesPorIdAreaSede(solicitud.getIdAreaSede());

		if (!ObjectUtils.isNullOrEmpty(reservacionesC)) {
			// lista de strings

			for (ReservacionEventoCapacitacionDTO res : reservacionesC) {
				if (fechaRes.equals(formatter.format(res.getFechaInicialReservacion()))) {
					horas.addAll(horasCap(res));
				}
			}
		}

		if (!ObjectUtils.isNullOrEmpty(reservacionesG)) {
			// lista de strings

			for (ReservacionEventoGeneralDTO res : reservacionesG) {
				if (fechaRes.equals(formatter.format(res.getFechaInicialReservacion()))) {
					horas.addAll(horasGen(res));
				}
			}
		}

		List<String> array24Horas = obtieneArray24Horas();

		if (!ObjectUtils.isNullOrEmpty(horas)) {
			for (String h : horas) {
				array24Horas.remove(h);
			}
		}

		solicitud.setHorasA(array24Horas);

	}

	public void actualizaCatAreas(SolicitudReservAreaDTO e) {

		SolicitudReservAreaDTO solicitudS = e;
		solicitudS.setIdArea(null);
		solicitudS.setCatAreas(new ArrayList<>());

		// Genera el Catalogo de areas asociadas a la sede
		CritBusquedaAreasConfigDTO criteriosConf = new CritBusquedaAreasConfigDTO();
		criteriosConf.setIdSede(solicitudS.getIdSede());
		catAreasInfra = new ArrayList<>();
		List<ConfiguracionAreaDTO> areasConfiguradas = getFacade().getConfiguracionAreaService()
				.busquedaAreasConfiguradasCriterios(criteriosConf);

		if (ObjectUtils.isNotNull(areasConfiguradas)) {
			for (ConfiguracionAreaDTO s : areasConfiguradas) {
				catAreasInfra.add(s.getCatAreasSede().getCatArea());
			}
		}
		solicitudS.setCatAreas(catAreasInfra);
	}

	public List<StreamedContent> obtieneImagenes() {
		// llena carousel imagenes
		List<StreamedContent> imgs = new ArrayList<>();
		arrayImagenes = new ArrayList<>();
		if (!ObjectUtils.isNullOrEmpty(configuracionAreaReservacion.getRutaImgUno())) {
			arrayImagenes.add(new File(configuracionAreaReservacion.getRutaImgUno()));
		}
		if (!ObjectUtils.isNullOrEmpty(configuracionAreaReservacion.getRutaImgDos())) {
			arrayImagenes.add(new File(configuracionAreaReservacion.getRutaImgDos()));
		}
		if (!ObjectUtils.isNullOrEmpty(configuracionAreaReservacion.getRutaImgTres())) {
			arrayImagenes.add(new File(configuracionAreaReservacion.getRutaImgTres()));
		}
		try {
			for (File f : arrayImagenes) {
				if (ObjectUtils.isNotNull(f)) {
					input = new FileInputStream(f);
					StreamedContent sc = new DefaultStreamedContent(input,
							getFacesContext().getExternalContext().getMimeType(f.getName()), f.getName());
					imgs.add(sc);
				}
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return imgs;
	}

	public void llenaImagenes() {
		// llena carousel imagenes
		imagenes = new ArrayList<>();
		arrayImagenes = new ArrayList<>();
		if (!ObjectUtils.isNullOrEmpty(configuracionAreaReservacion.getRutaImgUno())) {
			arrayImagenes.add(new File(configuracionAreaReservacion.getRutaImgUno()));
		}
		if (!ObjectUtils.isNullOrEmpty(configuracionAreaReservacion.getRutaImgDos())) {
			arrayImagenes.add(new File(configuracionAreaReservacion.getRutaImgDos()));
		}
		if (!ObjectUtils.isNullOrEmpty(configuracionAreaReservacion.getRutaImgTres())) {
			arrayImagenes.add(new File(configuracionAreaReservacion.getRutaImgTres()));
		}

		try {
			for (File f : getArrayImagenes()) {
				input = new FileInputStream(f);
				StreamedContent sc = new DefaultStreamedContent(input,
						getFacesContext().getExternalContext().getMimeType(f.getName()), f.getName());
				getImagenes().add(sc);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void buscaAreaCriterios() {

		areaSeleccionada = new ConfiguracionAreaDTO();
		areasConfigBusq = getFacade().getConfiguracionAreaService().busquedaAreasConfiguradasCriterios(criteriosBusq);

		if (ObjectUtils.isNotNull(areasConfigBusq)) {
			areaSeleccionada = areasConfigBusq.get(ConstantesGestorWeb.INDICE_INICIAL);
			areasBean.setAreaSeleccionada(areaSeleccionada);
		}

	}

	public void llenaImagenesAreaSeleccionada() {
		// llena carousel imagenes
		imagenes = new ArrayList<>();
		arrayImagenes = new ArrayList<>();
		if (!ObjectUtils.isNullOrEmpty(areaSeleccionada.getRutaImgUno())) {
			arrayImagenes.add(new File(areaSeleccionada.getRutaImgUno()));
		}
		if (!ObjectUtils.isNullOrEmpty(areaSeleccionada.getRutaImgDos())) {
			arrayImagenes.add(new File(areaSeleccionada.getRutaImgDos()));
		}
		if (!ObjectUtils.isNullOrEmpty(areaSeleccionada.getRutaImgTres())) {
			arrayImagenes.add(new File(areaSeleccionada.getRutaImgTres()));
		}
		try {
			for (File f : arrayImagenes) {
				input = new FileInputStream(f);
				StreamedContent sc = new DefaultStreamedContent(input,
						getFacesContext().getExternalContext().getMimeType(f.getName()), f.getName());
				imagenes.add(sc);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void llenaRecursos() {
		for (RelAreaRecursoDTO r : configuracionAreaReservacion.getRelAreaRecursos()) {
			if (r.getCatRecursosInfraestructura().getCatTipoRecurso().getId()
					.equals(TipoRecursoEnum.TECNOLOGICOS.getId())) {
				selTec.add(r);
			} else if (r.getCatRecursosInfraestructura().getCatTipoRecurso().getId()
					.equals(TipoRecursoEnum.MOBILIARIOS.getId())) {
				selMobi.add(r);
			}
		}
	}

	public void llenaPersonalizarDistribucion() {

		for (RelAreaDistribucionDTO d : configuracionAreaReservacion.getRelAreaDistribucion()) {
			if (solicitudSeleccionadaEdit.getPerArea().getDistribucionArea().getId()
					.equals(d.getCatDistribucionArea().getId())) {
				solicitudSeleccionadaEdit.getPerArea().setDistribucionArea(d.getCatDistribucionArea());
			}
		}

	}

	public void cancelarPersonalizacionArea() {
		logger.info("Cancelar Personalizacion##############################");
	}

	// persistencia de personalizacion area y de los recursos del area
	public void persistePersonalizacionArea() {

		for (SolicitudReservAreaDTO perSol : solicitudesReserv) {
			if (perSol.getPersonalizacionBoolean() != null && perSol.getPersonalizacionBoolean() == Boolean.TRUE) {

				List<RelAreaRecursoDTO> recTodo = new ArrayList<>();
				recTodo.addAll(perSol.getPerArea().getRecTec());
				recTodo.addAll(perSol.getPerArea().getRecMob());
				// GUSTAVO --BitacoraUtil.llenarBitacora(perSol.getPerArea(),
				// getUsuarioEnSession(), ConstantesBitacora.PERS_AREA_AGREGAR,
				// request);
				ResultadoDTO<PersonalizacionAreaDTO> pa = getFacade().getPersonalizacionAreaService()
						.guardar(perSol.getPerArea());
				perSol.setPerArea(pa.getDto());

				for (RelAreaRecursoDTO recurso : recTodo) {
					RelPersonalizacionRecursoDTO pr = new RelPersonalizacionRecursoDTO();
					pr.setActivo(true);
					pr.setCantidad(recurso.getCantidadSeleccionada());
					pr.setFechaRegistro(new Date());
					pr.setIdPersonalizacionArea(perSol.getPerArea().getIdPersonalizacion());
					pr.setIdRecurso(recurso.getCatRecursosInfraestructura().getIdRecurso());
					pr.setUsuarioRegistro(getUsuarioEnSession().getIdPersona());
					// GUSTAVO --BitacoraUtil.llenarBitacora(pr,
					// getUsuarioEnSession(),
					// ConstantesBitacora.AREA_RECURSOS_AGREGAR, request);
					getFacade().getPersonalizacionRecursoService().guardar(pr);
				}
			}
		}

	}

	// guarda datos de DTO de personalziacion
	public void guardaPersonalizacionArea() {

		solicitudSeleccionadaEdit.getPerArea().setFechaRegistro(new Date());
		solicitudSeleccionadaEdit.getPerArea().setFechaActualizacion(new Date());
		solicitudSeleccionadaEdit.getPerArea().setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		solicitudSeleccionadaEdit.getPerArea().setCatAreasSede(areaSeleccionada.getCatAreasSede());
		solicitudSeleccionadaEdit.getPerArea().setRecTec(selTec);
		solicitudSeleccionadaEdit.getPerArea().setRecMob(selMobi);

		solicitudSeleccionadaEdit.setPersonalizacionBoolean(Boolean.TRUE);

		solicitudesReserv.remove(solicitudSeleccionada);
		solicitudesReserv.add(solicitudSeleccionadaEdit);

		solicitudSeleccionada = new SolicitudReservAreaDTO();
		solicitudSeleccionadaEdit = new SolicitudReservAreaDTO();

		agregarMsgInfo("Personalización guardada", null);

	}

	/**
	 *
	 * @param solEvento
	 */
	public void descargarPDFReservacionEG(RelSolicitudEventoGeneralDTO solEvento) {
		if (ObjectUtils.isNotNull(solEvento)) {

			pdfReservacion = new DefaultStreamedContent();
			List<ReservacionEventoGeneralDTO> dsEventoGral = new ArrayList<>();
			dsEventoGral.add(solEvento.getTblReservacionEventoGeneral());

			ReporteConfig reporteConfig = new ReporteConfig();
			reporteConfig
					.setNombreReporte(solEvento.getTblReservacionEventoGeneral().getTblEventoGeneral().getNombre());
			reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);
			reporteConfig.setPathJasper(ConstantesReportesGestorWeb.PATH_JASPER_RESERVACION_AREA_EG);
			reporteConfig.setDatos(dsEventoGral);

			HashMap<String, Object> parametros = new HashMap<>();
			List<RelPersonalizacionRecursoDTO> persAreaDS = getFacade().getPersonalizacionRecursoService()
					.consultaRelPersonalizacionArea(
							solEvento.getTblReservacionEventoGeneral().getIdPersonalizacionArea());

			if (!ObjectUtils.isNullOrEmpty(persAreaDS)) {
				parametros.put("DS_REL_PERS_AREA", persAreaDS);
			}

			reporteConfig.setParametros(parametros);
			pdfReservacion = ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig),
					ConstantesGestorWeb.HTTP_HEADER_CONTENTTYPE_PDF,
					solEvento.getTblReservacionEventoGeneral().getTblEventoGeneral().getNombre());

		}
	}

	/**
	 *
	 * @param solEvento
	 */
	public void descargarPDFReservacionEC(RelSolicitudEventoCapacitacionDTO solEvento) {
		if (ObjectUtils.isNotNull(solEvento)) {

			pdfReservacion = new DefaultStreamedContent();
			List<ReservacionEventoCapacitacionDTO> dsEventoGral = new ArrayList<>();
			dsEventoGral.add(solEvento.getTblReservacionEventoCapacitacion());

			ReporteConfig reporteConfig = new ReporteConfig();
			reporteConfig
					.setNombreReporte(solEvento.getTblReservacionEventoCapacitacion().getTblEvento().getNombreEc());
			reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);
			reporteConfig.setPathJasper(ConstantesReportesGestorWeb.PATH_JASPER_RESERVACION_AREA_EC);
			reporteConfig.setDatos(dsEventoGral);

			HashMap<String, Object> parametros = new HashMap<>();
			List<RelPersonalizacionRecursoDTO> persAreaDS = getFacade().getPersonalizacionRecursoService()
					.consultaRelPersonalizacionArea(
							solEvento.getTblReservacionEventoCapacitacion().getIdPersonalizacionArea());

			parametros.put("RESP_EC", solEvento.getTblSolicitudReservacion().getEmailSolicitante());
			if (!ObjectUtils.isNullOrEmpty(persAreaDS)) {
				parametros.put("DS_REL_PERS_AREA", persAreaDS);
			}

			reporteConfig.setParametros(parametros);
			pdfReservacion = ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig),
					ConstantesGestorWeb.HTTP_HEADER_CONTENTTYPE_PDF,
					solEvento.getTblReservacionEventoCapacitacion().getTblEvento().getNombreEc());

		}
	}

	/**
	 * @return the scheduleModel
	 */
	public ScheduleModel getScheduleModel() {
		return scheduleModel;
	}

	/**
	 * @param scheduleModel
	 *            the scheduleModel to set
	 */
	public void setScheduleModel(ScheduleModel scheduleModel) {
		this.scheduleModel = scheduleModel;
	}

	/**
	 * @return the tipoEvento
	 */
	public Integer getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * @param tipoEvento
	 *            the tipoEvento to set
	 */
	public void setTipoEvento(Integer tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	/**
	 * @return the eventosCapacitacion
	 */
	public List<EventoCapacitacionDTO> getEventosCapacitacion() {
		return eventosCapacitacion;
	}

	/**
	 * @param eventosCapacitacion
	 *            the eventosCapacitacion to set
	 */
	public void setEventosCapacitacion(List<EventoCapacitacionDTO> eventosCapacitacion) {
		this.eventosCapacitacion = eventosCapacitacion;
	}

	/**
	 * @return the eventoCapacit
	 */
	public boolean isEventoCapacit() {
		return eventoCapacit;
	}

	/**
	 * @param eventoCapacit
	 *            the eventoCapacit to set
	 */
	public void setEventoCapacit(boolean eventoCapacit) {
		this.eventoCapacit = eventoCapacit;
	}

	/**
	 * @return the eventoCapacitacion
	 */
	public EventoCapacitacionDTO getEventoCapacitacion() {
		return eventoCapacitacion;
	}

	/**
	 * @param eventoCapacitacion
	 *            the eventoCapacitacion to set
	 */
	public void setEventoCapacitacion(EventoCapacitacionDTO eventoCapacitacion) {
		this.eventoCapacitacion = eventoCapacitacion;
	}

	/**
	 * @return the eventoGenerico
	 */
	public boolean isEventoGenerico() {
		return eventoGenerico;
	}

	/**
	 * @param eventoGenerico
	 *            the eventoGenerico to set
	 */
	public void setEventoGenerico(boolean eventoGenerico) {
		this.eventoGenerico = eventoGenerico;
	}

	/**
	 * @return the detReservEventoCap
	 */
	public boolean isDetReservEventoCap() {
		return detReservEventoCap;
	}

	/**
	 * @param detReservEventoCap
	 *            the detReservEventoCap to set
	 */
	public void setDetReservEventoCap(boolean detReservEventoCap) {
		this.detReservEventoCap = detReservEventoCap;
	}

	/**
	 * @return the arbolOrgGubernamental
	 */
	public TreeNode getArbolOrgGubernamental() {
		return arbolOrgGubernamental;
	}

	/**
	 * @param arbolOrgGubernamental
	 *            the arbolOrgGubernamental to set
	 */
	public void setArbolOrgGubernamental(TreeNode arbolOrgGubernamental) {
		this.arbolOrgGubernamental = arbolOrgGubernamental;
	}

	/**
	 * @return the nodoOrgGubernamentalSel
	 */
	public TreeNode getNodoOrgGubernamentalSel() {
		return nodoOrgGubernamentalSel;
	}

	/**
	 * @param nodoOrgGubernamentalSel
	 *            the nodoOrgGubernamentalSel to set
	 */
	public void setNodoOrgGubernamentalSel(TreeNode nodoOrgGubernamentalSel) {
		this.nodoOrgGubernamentalSel = nodoOrgGubernamentalSel;
	}

	/**
	 * @return the catUbicacionTerr
	 */
	public List<CatalogoComunDTO> getCatUbicacionTerr() {
		return catUbicacionTerr;
	}

	/**
	 * @param catUbicacionTerr
	 *            the catUbicacionTerr to set
	 */
	public void setCatUbicacionTerr(List<CatalogoComunDTO> catUbicacionTerr) {
		this.catUbicacionTerr = catUbicacionTerr;
	}

	/**
	 * @return the catSedesInfra
	 */
	public List<SedeDTO> getCatSedesInfra() {
		return catSedesInfra;
	}

	/**
	 * @param catSedesInfra
	 *            the catSedesInfra to set
	 */
	public void setCatSedesInfra(List<SedeDTO> catSedesInfra) {
		this.catSedesInfra = catSedesInfra;
	}

	/**
	 * @return the areasConfigBusq
	 */
	public List<ConfiguracionAreaDTO> getAreasConfigBusq() {
		return areasConfigBusq;
	}

	/**
	 * @param areasConfigBusq
	 *            the areasConfigBusq to set
	 */
	public void setAreasConfigBusq(List<ConfiguracionAreaDTO> areasConfigBusq) {
		this.areasConfigBusq = areasConfigBusq;
	}

	/**
	 * @return the criteriosBusq
	 */
	public CritBusquedaAreasConfigDTO getCriteriosBusq() {
		return criteriosBusq;
	}

	/**
	 * @param criteriosBusq
	 *            the criteriosBusq to set
	 */
	public void setCriteriosBusq(CritBusquedaAreasConfigDTO criteriosBusq) {
		this.criteriosBusq = criteriosBusq;
	}

	/**
	 * @return the catAreasInfra
	 */
	public List<CatalogoComunDTO> getCatAreasInfra() {
		return catAreasInfra;
	}

	/**
	 * @param catAreasInfra
	 *            the catAreasInfra to set
	 */
	public void setCatAreasInfra(List<CatalogoComunDTO> catAreasInfra) {
		this.catAreasInfra = catAreasInfra;
	}

	/**
	 * @return the eventDateOn
	 */
	public boolean isEventDateOn() {
		return eventDateOn;
	}

	/**
	 * @param eventDateOn
	 *            the eventDateOn to set
	 */
	public void setEventDateOn(boolean eventDateOn) {
		this.eventDateOn = eventDateOn;
	}

	/**
	 * @return the eventoPrivado
	 */
	public Integer getEventoPrivado() {
		return eventoPrivado;
	}

	/**
	 * @param eventoPrivado
	 *            the eventoPrivado to set
	 */
	public void setEventoPrivado(Integer eventoPrivado) {
		this.eventoPrivado = eventoPrivado;
	}

	/**
	 * @return the opcionesEventos
	 */
	public boolean isOpcionesEventos() {
		return opcionesEventos;
	}

	/**
	 * @param opcionesEventos
	 *            the opcionesEventos to set
	 */
	public void setOpcionesEventos(boolean opcionesEventos) {
		this.opcionesEventos = opcionesEventos;
	}

	/**
	 * @return the solicitudesReserv
	 */
	public List<SolicitudReservAreaDTO> getSolicitudesReserv() {
		return solicitudesReserv;
	}

	/**
	 * @param solicitudesReserv
	 *            the solicitudesReserv to set
	 */
	public void setSolicitudesReserv(List<SolicitudReservAreaDTO> solicitudesReserv) {
		this.solicitudesReserv = solicitudesReserv;
	}

	/**
	 * @return the eventoGeneral
	 */
	public EventoGeneralDTO getEventoGeneral() {
		return eventoGeneral;
	}

	/**
	 * @param eventoGeneral
	 *            the eventoGeneral to set
	 */
	public void setEventoGeneral(EventoGeneralDTO eventoGeneral) {
		this.eventoGeneral = eventoGeneral;
	}

	/**
	 * @return the nuevaReservXArea
	 */
	public boolean isNuevaReservXArea() {
		return nuevaReservXArea;
	}

	/**
	 * @param nuevaReservXArea
	 *            the nuevaReservXArea to set
	 */
	public void setNuevaReservXArea(boolean nuevaReservXArea) {
		this.nuevaReservXArea = nuevaReservXArea;
	}

	/**
	 * @return the personalizacionArea
	 */
	public PersonalizacionAreaDTO getPersonalizacionArea() {
		return personalizacionArea;
	}

	/**
	 * @param personalizacionArea
	 *            the personalizacionArea to set
	 */
	public void setPersonalizacionArea(PersonalizacionAreaDTO personalizacionArea) {
		this.personalizacionArea = personalizacionArea;
	}

	/**
	 * @return the catDistribucionConfigArea
	 */
	public List<CatalogoComunDTO> getCatDistribucionConfigArea() {
		return catDistribucionConfigArea;
	}

	/**
	 * @param catDistribucionConfigArea
	 *            the catDistribucionConfigArea to set
	 */
	public void setCatDistribucionConfigArea(List<CatalogoComunDTO> catDistribucionConfigArea) {
		this.catDistribucionConfigArea = catDistribucionConfigArea;
	}

	/**
	 * @return the recursosTecnologicos
	 */
	public List<RecursosInfraestructuraDTO> getRecursosTecnologicos() {
		return recursosTecnologicos;
	}

	/**
	 * @param recursosTecnologicos
	 *            the recursosTecnologicos to set
	 */
	public void setRecursosTecnologicos(List<RecursosInfraestructuraDTO> recursosTecnologicos) {
		this.recursosTecnologicos = recursosTecnologicos;
	}

	/**
	 * @return the recursosMobiliario
	 */
	public List<RecursosInfraestructuraDTO> getRecursosMobiliario() {
		return recursosMobiliario;
	}

	/**
	 * @param recursosMobiliario
	 *            the recursosMobiliario to set
	 */
	public void setRecursosMobiliario(List<RecursosInfraestructuraDTO> recursosMobiliario) {
		this.recursosMobiliario = recursosMobiliario;
	}

	/**
	 * @return the areaSeleccionada
	 */
	public ConfiguracionAreaDTO getAreaSeleccionada() {
		return areaSeleccionada;
	}

	/**
	 * @param areaSeleccionada
	 *            the areaSeleccionada to set
	 */
	public void setAreaSeleccionada(ConfiguracionAreaDTO areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	/**
	 * @return the areasBean
	 */
	public AreasBean getAreasBean() {
		return areasBean;
	}

	/**
	 * @param areasBean
	 *            the areasBean to set
	 */
	public void setAreasBean(AreasBean areasBean) {
		this.areasBean = areasBean;
	}

	/**
	 * @return the muestraEventosCap
	 */
	public boolean isMuestraEventosCap() {
		return muestraEventosCap;
	}

	/**
	 * @param muestraEventosCap
	 *            the muestraEventosCap to set
	 */
	public void setMuestraEventosCap(boolean muestraEventosCap) {
		this.muestraEventosCap = muestraEventosCap;
	}

	/**
	 * @return the scheduleCmp
	 */
	public Schedule getScheduleCmp() {
		return scheduleCmp;
	}

	/**
	 * @param scheduleCmp
	 *            the scheduleCmp to set
	 */
	public void setScheduleCmp(Schedule scheduleCmp) {
		this.scheduleCmp = scheduleCmp;
	}

	public List<File> getArrayImagenes() {
		return arrayImagenes;
	}

	public void setArrayImagenes(List<File> arrayImagenes) {
		this.arrayImagenes = arrayImagenes;
	}

	public List<StreamedContent> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<StreamedContent> imagenes) {
		this.imagenes = imagenes;
	}

	public List<RelAreaRecursoDTO> getSelTec() {
		return selTec;
	}

	public void setSelTec(List<RelAreaRecursoDTO> selTec) {
		this.selTec = selTec;
	}

	public List<RelAreaRecursoDTO> getSelMobi() {
		return selMobi;
	}

	public void setSelMobi(List<RelAreaRecursoDTO> selMobi) {
		this.selMobi = selMobi;
	}

	public RelAreaRecursoDTO getRecursoTecGuardar() {
		return recursoTecGuardar;
	}

	public void setRecursoTecGuardar(RelAreaRecursoDTO recursoTecGuardar) {
		this.recursoTecGuardar = recursoTecGuardar;
	}

	public RelAreaRecursoDTO getRecursoMobiGuardar() {
		return recursoMobiGuardar;
	}

	public void setRecursoMobiGuardar(RelAreaRecursoDTO recursoMobiGuardar) {
		this.recursoMobiGuardar = recursoMobiGuardar;
	}

	public List<Integer> getSelTecCant() {
		return selTecCant;
	}

	public void setSelTecCant(List<Integer> selTecCant) {
		this.selTecCant = selTecCant;
	}

	public List<Integer> getSelMobiCant() {
		return selMobiCant;
	}

	public void setSelMobiCant(List<Integer> selMobiCant) {
		this.selMobiCant = selMobiCant;
	}

	public Integer getCantTecSeleccionada() {
		return cantTecSeleccionada;
	}

	public void setCantTecSeleccionada(Integer cantTecSeleccionada) {
		this.cantTecSeleccionada = cantTecSeleccionada;
	}

	public Integer getCantMobiSeleccionada() {
		return cantMobiSeleccionada;
	}

	public void setCantMobiSeleccionada(Integer cantMobiSeleccionada) {
		this.cantMobiSeleccionada = cantMobiSeleccionada;
	}

	public List<RelAreaRecursoDTO> getSelMobiGuardar() {
		return selMobiGuardar;
	}

	public void setSelMobiGuardar(List<RelAreaRecursoDTO> selMobiGuardar) {
		this.selMobiGuardar = selMobiGuardar;
	}

	public List<RelAreaRecursoDTO> getSelTecGuardar() {
		return selTecGuardar;
	}

	public void setSelTecGuardar(List<RelAreaRecursoDTO> selTecGuardar) {
		this.selTecGuardar = selTecGuardar;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	/**
	 * @return the solRes
	 */
	public SolicitudReservacionDTO getSolRes() {
		return solRes;
	}

	/**
	 * @param solRes
	 *            the solRes to set
	 */
	public void setSolRes(SolicitudReservacionDTO solRes) {
		this.solRes = solRes;
	}

	/**
	 * @return the rEC
	 */
	public ReservacionEventoCapacitacionDTO getrEC() {
		return rEC;
	}

	/**
	 * @param rEC
	 *            the rEC to set
	 */
	public void setrEC(ReservacionEventoCapacitacionDTO rEC) {
		this.rEC = rEC;
	}

	/**
	 * @return the sEC
	 */
	public RelSolicitudEventoCapacitacionDTO getsEC() {
		return sEC;
	}

	/**
	 * @param sEC
	 *            the sEC to set
	 */
	public void setsEC(RelSolicitudEventoCapacitacionDTO sEC) {
		this.sEC = sEC;
	}

	/**
	 * @return the fechaReservacion
	 */
	public Date getFechaReservacion() {
		return fechaReservacion;
	}

	/**
	 * @param fechaReservacion
	 *            the fechaReservacion to set
	 */
	public void setFechaReservacion(Date fechaReservacion) {
		this.fechaReservacion = fechaReservacion;
	}

	/**
	 * @return the solicitudSeleccionada
	 */
	public SolicitudReservAreaDTO getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}

	/**
	 * @param solicitudSeleccionada
	 *            the solicitudSeleccionada to set
	 */
	public void setSolicitudSeleccionada(SolicitudReservAreaDTO solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}

	/**
	 * @return the solicitudSeleccionadaEdit
	 */
	public SolicitudReservAreaDTO getSolicitudSeleccionadaEdit() {
		return solicitudSeleccionadaEdit;
	}

	/**
	 * @param solicitudSeleccionadaEdit
	 *            the solicitudSeleccionadaEdit to set
	 */
	public void setSolicitudSeleccionadaEdit(SolicitudReservAreaDTO solicitudSeleccionadaEdit) {
		this.solicitudSeleccionadaEdit = solicitudSeleccionadaEdit;
	}

	/**
	 * @return the configuracionAreaReservacion
	 */
	public ConfiguracionAreaDTO getConfiguracionAreaReservacion() {
		return configuracionAreaReservacion;
	}

	/**
	 * @param configuracionAreaReservacion
	 *            the configuracionAreaReservacion to set
	 */
	public void setConfiguracionAreaReservacion(ConfiguracionAreaDTO configuracionAreaReservacion) {
		this.configuracionAreaReservacion = configuracionAreaReservacion;
	}

	public DefaultScheduleEvent getEventoReservacion() {
		return eventoReservacion;
	}

	public void setEventoReservacion(DefaultScheduleEvent eventoReservacion) {
		this.eventoReservacion = eventoReservacion;
	}

	public boolean isBolPanelAprobarCap() {
		return bolPanelAprobarCap;
	}

	public void setBolPanelAprobarCap(boolean bolPanelAprobarCap) {
		this.bolPanelAprobarCap = bolPanelAprobarCap;
	}

	public List<RelSolicitudEventoCapacitacionDTO> getReservacionesCapPorAprobar() {
		return reservacionesCapPorAprobar;
	}

	public void setReservacionesCapPorAprobar(List<RelSolicitudEventoCapacitacionDTO> reservacionesCapPorAprobar) {
		this.reservacionesCapPorAprobar = reservacionesCapPorAprobar;
	}

	public List<RelSolicitudEventoGeneralDTO> getReservacionesGenPorAprobar() {
		return reservacionesGenPorAprobar;
	}

	public void setReservacionesGenPorAprobar(List<RelSolicitudEventoGeneralDTO> reservacionesGenPorAprobar) {
		this.reservacionesGenPorAprobar = reservacionesGenPorAprobar;
	}

	public String getIconoAprovadoGen() {
		return iconoAprovadoGen;
	}

	public void setIconoAprovadoGen(String iconoAprovadoGen) {
		this.iconoAprovadoGen = iconoAprovadoGen;
	}

	public String getIconoAprovadoCap() {
		return iconoAprovadoCap;
	}

	public void setIconoAprovadoCap(String iconoAprovadoCap) {
		this.iconoAprovadoCap = iconoAprovadoCap;
	}

	public RelSolicitudEventoGeneralDTO getInfoReservacionGen() {
		return infoReservacionGen;
	}

	public void setInfoReservacionGen(RelSolicitudEventoGeneralDTO infoReservacionGen) {
		this.infoReservacionGen = infoReservacionGen;
	}

	public RelSolicitudEventoCapacitacionDTO getInfoReservacionCap() {
		return infoReservacionCap;
	}

	public void setInfoReservacionCap(RelSolicitudEventoCapacitacionDTO infoReservacionCap) {
		this.infoReservacionCap = infoReservacionCap;
	}

	public boolean isBolInfoReserv() {
		return bolInfoReserv;
	}

	public void setBolInfoReserv(boolean bolInfoReserv) {
		this.bolInfoReserv = bolInfoReserv;
	}

	public PersonalizacionAreaDTO getPersoCap() {
		return persoCap;
	}

	public void setPersoCap(PersonalizacionAreaDTO persoCap) {
		this.persoCap = persoCap;
	}

	public PersonalizacionAreaDTO getPersoGen() {
		return persoGen;
	}

	public void setPersoGen(PersonalizacionAreaDTO persoGen) {
		this.persoGen = persoGen;
	}

	public AreaSedeDTO getAreaSedeInfoCap() {
		return areaSedeInfoCap;
	}

	public void setAreaSedeInfoCap(AreaSedeDTO areaSedeInfoCap) {
		this.areaSedeInfoCap = areaSedeInfoCap;
	}

	public AreaSedeDTO getAreaSedeInfoGen() {
		return areaSedeInfoGen;
	}

	public void setAreaSedeInfoGen(AreaSedeDTO areaSedeInfoGen) {
		this.areaSedeInfoGen = areaSedeInfoGen;
	}

	/**
	 * @return the pdfReservacion
	 */
	public StreamedContent getPdfReservacion() {
		return pdfReservacion;
	}

	/**
	 * @param pdfReservacion
	 *            the pdfReservacion to set
	 */
	public void setPdfReservacion(StreamedContent pdfReservacion) {
		this.pdfReservacion = pdfReservacion;
	}

	public CorreoElectronicoService getCorreoElectronicoService() {
		return correoElectronicoService;
	}

	public void setCorreoElectronicoService(CorreoElectronicoService correoElectronicoService) {
		this.correoElectronicoService = correoElectronicoService;
	}

	public SistemaBean getTextosSistema() {
		return textosSistema;
	}

	public void setTextosSistema(SistemaBean textosSistema) {
		this.textosSistema = textosSistema;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public LogisticaInfraServiceFacade getFacade() {
		return facade;
	}

	public void setFacade(LogisticaInfraServiceFacade facade) {
		this.facade = facade;
	}

	public OrgGubernamentalService getOrgGubernamentalService() {
		return orgGubernamentalService;
	}

	public void setOrgGubernamentalService(OrgGubernamentalService orgGubernamentalService) {
		this.orgGubernamentalService = orgGubernamentalService;
	}

}
