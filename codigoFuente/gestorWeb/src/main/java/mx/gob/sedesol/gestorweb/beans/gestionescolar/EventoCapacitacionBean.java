package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PlanService;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgDuracionDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.LoginWS;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.utils.EstadoEventoCapEnum;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;

@SessionScoped
@ManagedBean(name = "eventoCapacitacionBean")
public class EventoCapacitacionBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(EventoCapacitacionBean.class);

	@ManagedProperty(value = "#{eventoCapacitacionServiceFacade}")
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private ParametroSistemaService parametrosSistemaService;

	private EventoCapacitacionDTO filtros;
	private EventoCapacitacionDTO eventoReporte;
	@ManagedProperty("#{planService}")
	private PlanService planService;
	@ManagedProperty("#{mallaCurricularService}")
	private MallaCurricularService mallaCurricularService;
	@ManagedProperty(value = "#{fecServiceFacade}")
	private FECServiceFacade fecServiceFacade;

	@ManagedProperty(value = "#{registroAsistenciaBean}")
	private RegistroAsistenciaBean registroAsistenciaBean;

	private List<CatalogoComunDTO> ejesCapacitacion;

	private List<CatalogoComunDTO> listaTiposCompetencias;
	private List<CatalogoComunDTO> listaEjesCapacitacion;
	private List<CatalogoComunDTO> listaEstatusEC;
	private List<CatalogoComunDTO> listaModalidadesPrograma;

	private List<EventoCapacitacionDTO> eventosCapacitacion;

	private NodoeHijosDTO estPlanSedesol;
	private boolean visibleAuditar = Boolean.FALSE;
	private boolean visibleAcceso = Boolean.FALSE;
	private boolean visibleParticipantes = Boolean.FALSE;
	private boolean visibleAcciones = Boolean.FALSE;
	private static final Integer MOSTRAR_TODOS = -1;

	private EventoCapacitacionDTO eventoSelec;
	private String nombreEjeCapacitacion;
	private String nombreTpoCompetencia;

	private String tipoDatoFechas;
	private StreamedContent reportePDF;
	private Integer idPlan;

	private List<CatalogoComunDTO> catEstructuras;
	private List<CatalogoComunDTO> catSubEstructurasNivel1;
	private List<CatalogoComunDTO> catSubEstructurasNivel2;
	private List<CatalogoComunDTO> catSubEstructurasNivel3;
	private List<NodoeHijosDTO> nodos;
	private List<CatalogoComunDTO> planes;
	private Integer nivelMaximo = 1;
	private Integer idCapacitacion;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	public EventoCapacitacionBean() {
		eventosCapacitacion = new ArrayList<>();
		filtros = new EventoCapacitacionDTO();
		FichaDescProgramaDTO descProgramaDTO = new FichaDescProgramaDTO();
		CatalogoComunDTO catModalidad = new CatalogoComunDTO();
		descProgramaDTO.setCatModalidad(catModalidad);
		if (ObjectUtils.isNull(eventosCapacitacion)){

		}

	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void iniciaRecursos() {

		listaEstatusEC = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_ESTADO_EVENTO_CAPACITACION);
		listaModalidadesPrograma = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG);

		this.generaEstructuraCatTpoCompetenciaPlan();
		this.generaCatEjesCapacitBusqueda();
	}

	/**
	 * Accion para Descargar un Programa.
	 */
	public void descargarReporteEvento(EventoCapacitacionDTO eventoReporte) {
		List<RelProgDuracionDTO> relProgDuracion = new ArrayList<>();

		reportePDF = null;

		/* Datos del programa de capacitacion */
		String ejeCapacitacion = "";
		String tipoCompetencia = "";
		String modalidad = "";
		String nivelEnsenanza = "";
		String cargaHoraria = "";

		ejeCapacitacion = obtieneNombreEjeCapacit(eventoReporte.getFichaDescriptivaPrograma().getEjeCapacitacion());
		tipoCompetencia = obtieneNombreTpoCompetencia(eventoReporte.getFichaDescriptivaPrograma().getTipoCompetencia());
		if (ObjectUtils.isNotNull(eventoReporte.getCatModalidadPlanPrograma())) {
			modalidad = eventoReporte.getCatModalidadPlanPrograma().getNombre();
		}

		if (ObjectUtils.isNotNull(eventoReporte.getFichaDescriptivaPrograma().getCatNivelEnsenanzaPrograma())) {
			nivelEnsenanza = eventoReporte.getFichaDescriptivaPrograma().getCatNivelEnsenanzaPrograma().getNombre();
		}

		if (ObjectUtils.isNotNull(eventoReporte.getFichaDescriptivaPrograma().getRelProgramaDuracion())) {
			relProgDuracion = eventoReporte.getFichaDescriptivaPrograma().getRelProgramaDuracion();
			for (RelProgDuracionDTO relProgDuracionDTO : relProgDuracion) {
				cargaHoraria = "Tipo: " + relProgDuracionDTO.getCatTpoCargaHoraria().getNombre() + "\nHoras: "
						+ relProgDuracionDTO.getHoras() + "\nMinutos: " + relProgDuracionDTO.getMinutos() + "\n";
			}
		}

		/* Datos del evento de capacitacion */
		String nombreEvento = "";
		String tipoEvento = "";
		String objetivoGeneral = "";
		String perfilDestinatario = "";
		String requisitosTecnicos = "";

		nombreEvento = eventoReporte.getNombreEc();
		tipoEvento = eventoReporte.getPrivado() == Boolean.TRUE ? "Público" : "Privado";
		objetivoGeneral = eventoReporte.getObjetivoGeneralEc();
		perfilDestinatario = eventoReporte.getPerfilEc();
		requisitosTecnicos = eventoReporte.getRequisitosEc();

		ReporteConfig reporteConfig = new ReporteConfig();
		reporteConfig.setDatos(null);
		reporteConfig.setNombreReporte("Plantilla_Eventos");
		reporteConfig.setPathJasper("/resources/jasperReport/gestionEscolar/eventos.jasper");
		reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);

		Map<String, Object> parametros = new HashMap<>();

		/* DATOS PROGRAMA */
		parametros.put("P_EJE_CAPACITACION", ejeCapacitacion);
		parametros.put("P_TIPO_COMPETENCIA", tipoCompetencia);
		parametros.put("P_MODALIDAD", modalidad);
		parametros.put("P_NIVEL_ENSENANZA", nivelEnsenanza);
		parametros.put("P_CARGA_HORARIA", cargaHoraria);

		/* DATOS EVENTO */
		parametros.put("P_NOMBRE_EVENTO", nombreEvento);
		parametros.put("P_TIPO_EVENTO", tipoEvento);
		parametros.put("P_OBJETIVO_GENERAL", objetivoGeneral);
		parametros.put("P_PERFIL_DESTINATARIO", perfilDestinatario);
		parametros.put("P_REQUISITOS_TECNICOS", requisitosTecnicos);

		reporteConfig.setParametros(parametros);
		setReportePDF(ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig),
				ConstantesGestorWeb.HTTP_HEADER_CONTENTTYPE_PDF, eventoReporte.getNombreEc()));

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "DES_EVT", String.valueOf(eventoReporte.getIdEvento()),
				requestActual(), TipoServicioEnum.LOCAL);
	}

	public void validarComboFechas() {
		if (ObjectUtils.isNull(tipoDatoFechas)) {
			filtros.setFechaInicial(null);
			filtros.setFechaFinal(null);
			RequestContext rcontext = RequestContext.getCurrentInstance();
			rcontext.update("frmBusqueda:fc_fechaInicial");
			rcontext.update("frmBusqueda:fc_fechaFinal");

		}
	}

	public void validarFechas() {
		if (ObjectUtils.isNotNull(filtros.getFechaFinal())) {
			if (filtros.getFechaFinal().before(filtros.getFechaInicial())) {
				filtros.setFechaFinal(filtros.getFechaInicial());
			}
		}

	}

	public void limpiarCampos() {

		filtros.getFichaDescriptivaPrograma().setTipoCompetencia(null);
		filtros.getFichaDescriptivaPrograma().setEjeCapacitacion(null);
		filtros.getFichaDescriptivaPrograma().setIdPrograma(null);
		filtros.getFichaDescriptivaPrograma().setPlan(null);
		listaEjesCapacitacion = new ArrayList<>();

		filtros.setNombreEc(null);
		filtros.getFichaDescriptivaPrograma().setNombreTentativo(null);
		filtros.setCatModalidadPlanPrograma(new CatalogoComunDTO());

		tipoDatoFechas = null;
		filtros.setFechaInicial(null);
		filtros.setFechaFinal(null);
		filtros.setCatEstadoEventoCapacitacion(new CatalogoComunDTO());
		catEstructuras.clear();
		eventosCapacitacion = new ArrayList<>();

	}

	/**
	 * Validacion e impresion de mensaje de validacion desde backing bean
	 */
	public void validarEjeCapacitacion(FacesContext context, UIComponent component, Object value) {
		context = FacesContext.getCurrentInstance();
		Integer valor = (Integer) value;
		if (valor.equals(0) || valor.equals(null)) {
			context.addMessage(component.getClientId(),
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El dato es requerido.", null));
			/*
			 * Actualiza el mensaje de validacion del combo de ejes de
			 * capacitacion
			 */
			/*
			 * RequestContext rcontext = RequestContext.getCurrentInstance();
			 * rcontext.update("frmBusqueda:msjEjeCapSel");
			 */
			/* Limpia la lista de ejes de capacitacion */
			listaEjesCapacitacion = new ArrayList<>();

		}
	}

	public void validarTipoCompetencia(FacesContext context, UIComponent component, Object value) {
		context = FacesContext.getCurrentInstance();
		Integer valor = (Integer) value;
		if (valor.equals(0) || valor.equals(null)) {
			context.addMessage(component.getClientId(),
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El dato es requerido.", null));
			/*
			 * Actualiza el mensaje de validacion del combo de ejes de
			 * capacitacion
			 */
			/*
			 * RequestContext rcontext = RequestContext.getCurrentInstance();
			 * rcontext.update("frmBusqueda");
			 */

		}
	}

	public void verReporte() {

		List<RelProgDuracionDTO> relProgDuracion = new ArrayList<>();

		reportePDF = null;

		/* Datos del programa de capacitacion */
		String ejeCapacitacion = "";
		String tipoCompetencia = "";
		String modalidad = "";
		String nivelEnsenanza = "";
		String cargaHoraria = "";

		ejeCapacitacion = obtieneNombreEjeCapacit(eventoReporte.getFichaDescriptivaPrograma().getEjeCapacitacion());
		tipoCompetencia = obtieneNombreTpoCompetencia(eventoReporte.getFichaDescriptivaPrograma().getTipoCompetencia());
		if (ObjectUtils.isNotNull(eventoReporte.getCatModalidadPlanPrograma())) {
			modalidad = eventoReporte.getCatModalidadPlanPrograma().getNombre();
		}

		if (ObjectUtils.isNotNull(eventoReporte.getFichaDescriptivaPrograma().getCatNivelEnsenanzaPrograma())) {
			nivelEnsenanza = eventoReporte.getFichaDescriptivaPrograma().getCatNivelEnsenanzaPrograma().getNombre();
		}

		if (ObjectUtils.isNotNull(eventoReporte.getFichaDescriptivaPrograma().getRelProgramaDuracion())) {
			relProgDuracion = eventoReporte.getFichaDescriptivaPrograma().getRelProgramaDuracion();
			for (RelProgDuracionDTO relProgDuracionDTO : relProgDuracion) {
				cargaHoraria = "Tipo: " + relProgDuracionDTO.getCatTpoCargaHoraria().getNombre() + "\nHoras: "
						+ relProgDuracionDTO.getHoras() + "\nMinutos: " + relProgDuracionDTO.getMinutos() + "\n";
			}
		}

		/* Datos del evento de capacitacion */
		String nombreEvento = "";
		String tipoEvento = "";
		String objetivoGeneral = "";
		String perfilDestinatario = "";
		String requisitosTecnicos = "";

		nombreEvento = eventoReporte.getNombreEc();
		tipoEvento = eventoReporte.getPrivado() == Boolean.TRUE ? "Público" : "Privado";
		objetivoGeneral = eventoReporte.getObjetivoGeneralEc();
		perfilDestinatario = eventoReporte.getPerfilEc();
		requisitosTecnicos = eventoReporte.getRequisitosEc();

		ReporteConfig reporteConfig = new ReporteConfig();
		reporteConfig.setDatos(null);
		reporteConfig.setNombreReporte("Plantilla_Eventos");
		reporteConfig.setPathJasper("/resources/jasperReport/gestionEscolar/eventos.jasper");
		reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);

		Map<String, Object> parametros = new HashMap<>();

		/* DATOS PROGRAMA */
		parametros.put("P_EJE_CAPACITACION", ejeCapacitacion);
		parametros.put("P_TIPO_COMPETENCIA", tipoCompetencia);
		parametros.put("P_MODALIDAD", modalidad);
		parametros.put("P_NIVEL_ENSENANZA", nivelEnsenanza);
		parametros.put("P_CARGA_HORARIA", cargaHoraria);

		/* DATOS EVENTO */
		parametros.put("P_NOMBRE_EVENTO", nombreEvento);
		parametros.put("P_TIPO_EVENTO", tipoEvento);
		parametros.put("P_OBJETIVO_GENERAL", objetivoGeneral);
		parametros.put("P_PERFIL_DESTINATARIO", perfilDestinatario);
		parametros.put("P_REQUISITOS_TECNICOS", requisitosTecnicos);

		reporteConfig.setParametros(parametros);
		setReportePDF(ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig),
				ConstantesGestorWeb.HTTP_HEADER_CONTENTTYPE_PDF, eventoReporte.getNombreEc()));

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_EVT_PDF", String.valueOf(eventoReporte.getIdEvento()),
				requestActual(), TipoServicioEnum.LOCAL);

	}

	/**
	 * Metodo para navegar a la pantalla de creacion del evento de capacitacion
	 *
	 * @return
	 */
	public String crearEventoCapacitacion() {
		return ConstantesGestorWeb.NAVEGA_REGISTRO_NUEVO_EVENTO_CAPACITACION;
	}

	/**
	 * Metodo que extraer los datos para mostrar el tipo competencia
	 */
	private void generaEstructuraCatTpoCompetenciaPlan() {

		nodos = new ArrayList<>();
		List<MallaCurricularDTO> mallas = getFecServiceFacade().getMallaCurricularService().obtieneMallasCurricularesDisponibles();

		// List<MallaCurricularDTO> mallas =
		// getFecServiceFacade().getMallaCurricularService().obtieneMallasCurricularesDisponibles();
		// RN: Solo se presentara el plan de sedesol por el momento
		/*MallaCurricularDTO mallaSedesol = getFecServiceFacade().getMallaCurricularService()
				.obtenerMallaCurricularPorId(1);
		mallas.add(mallaSedesol);*/
		planes = new ArrayList<>();
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
			nodos.add(nodog);
			CatalogoComunDTO cat = new CatalogoComunDTO();
			cat.setId(nodog.getIdNodo());
			cat.setNombre(nodog.getNombre());
			planes.add(cat);
		}

		listaTiposCompetencias = new ArrayList<>();

		// Genera el Catalogo Tipo de Competencia
		estPlanSedesol = nodos.get(ConstantesGestorWeb.INDICE_INICIAL);
		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			listaTiposCompetencias.add(cc);
		}
	}
	public void onChangeCatPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			catEstructuras = this.generarEstructuras(nodos, Integer.parseInt(e.getNewValue().toString()));
			filtros.getFichaDescriptivaPrograma().setPlan( planService.buscarPorId( mallaCurricularService.buscarPorId(Integer.parseInt(e.getNewValue().toString())).getIdPlan()));
			//filtros.getFichaDescriptivaPrograma().setPlan(planService.buscarPorId(Integer.parseInt(e.getNewValue().toString())));
			catSubEstructurasNivel1 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel2 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
			nivelMaximo = 0;
		}
	}
	public void onChangeCatEstructura(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSubEstructura = Integer.parseInt(e.getNewValue().toString());
			filtros.getFichaDescriptivaPrograma().setEjeCapacitacion(idSubEstructura);
			catSubEstructurasNivel1 = this.generarSubEstructuras1(nodos, idSubEstructura);
			catSubEstructurasNivel2 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
			nivelMaximo = 1;
		}
	}
	public void onChangeCatSubestructura1(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSubEstructura = Integer.parseInt(e.getNewValue().toString());
			filtros.getFichaDescriptivaPrograma().setEjeCapacitacion(idSubEstructura);
			catSubEstructurasNivel2 = this.generarSubEstructuras2(nodos, idSubEstructura);
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
			nivelMaximo = 2;
		}
	}
	public void onChangeCatSubestructura2(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSubEstructura = Integer.parseInt(e.getNewValue().toString());
			filtros.getFichaDescriptivaPrograma().setEjeCapacitacion(idSubEstructura);
			catSubEstructurasNivel3 = this.generarSubEstructuras3(nodos, idSubEstructura);
			nivelMaximo = 3;
		}
	}

	public void onChangeCatSubestructura3(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			filtros.getFichaDescriptivaPrograma().setEjeCapacitacion(Integer.parseInt(e.getNewValue().toString()));
			nivelMaximo = 4;
		}
	}
	/**
	 * Metodo que crea los ejes de capacitacion apartir del tipo de competencia
	 * seleccionado
	 */
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
		}

	}

	/**
	 * Metodo que sirve para extraer los ejes de capacitacion
	 */
	private void generaCatEjesCapacitBusqueda() {
		ejesCapacitacion = new ArrayList<>();

		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			for (NodoeHijosDTO nint : nh.getNodosHijos()) {
				CatalogoComunDTO cc = new CatalogoComunDTO();
				cc.setId(nint.getIdNodo());
				cc.setNombre(nint.getNombre());
				ejesCapacitacion.add(cc);
			}
		}
	}

	/**
	 * Metodo que obtiene el nombre de un tipo de competencia apartir de su id
	 */
	public String obtieneNombreTpoCompetencia(Integer idTpoComp) {
		final String[] datos = {null};
		listaTiposCompetencias.forEach(o -> datos[0] +=" " +o.getNombre());
		if (ObjectUtils.isNotNull(idTpoComp)) {
			for (CatalogoComunDTO tpoCom : listaTiposCompetencias) {
				if (tpoCom.getId().equals(idTpoComp)) {
					return tpoCom.getNombre();
				}
			}
		}
		return null;
	}

	/**
	 * Metodo que obtienen el nombre de un eje de capacitacion apartir de su id
	 */
	public String obtieneNombreEjeCapacit(Integer idEjeCap) {
		final String[] datos = {null};
		ejesCapacitacion.forEach(o -> datos[0] +=" " +o.getNombre());
		if (ObjectUtils.isNotNull(idEjeCap)) {
			for (CatalogoComunDTO ejeCap : ejesCapacitacion) {
				if (ejeCap.getId().compareTo(idEjeCap) == 0) {
					return ejeCap.getNombre();
				}
			}
		}
		return null;
	}

	public void onChangeBusquedaEstatus(ValueChangeEvent e) {

		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer estatus = (Integer) e.getNewValue();
			eventosCapacitacion = eventoCapacitacionServiceFacade.consultaEventoPorEstatus(estatus);
			if (!eventosCapacitacion.isEmpty()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_EVT", "", requestActual(),
						TipoServicioEnum.LOCAL);
			}
			traerComponentesVisibles(estatus);
		}

	}

	public Boolean mostrarBtnEnLinea(EventoCapacitacionDTO evento) {
		if (ObjectUtils.isNotNull(evento)) {
			if (ObjectUtils.isNotNull(evento.getCatModalidadPlanPrograma())) {
				if (ObjectUtils.isNotNull(evento.getCatModalidadPlanPrograma().getId())) {
					if (evento.getCatModalidadPlanPrograma().getId().equals(ConstantesGestor.MODALIDAD_PRESENCIAL)
							|| evento.getCatEstadoEventoCapacitacion().getId()
							.intValue() != EstadoEventoCapEnum.EN_EJECUCION.getId().intValue()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Metodo que cambia de estado las banderas para mostrar los elementos
	 * visibles
	 */
	private void traerComponentesVisibles(Integer idTipoComponente) {
		visibleAcceso = Boolean.FALSE;
		visibleParticipantes = Boolean.FALSE;
		visibleAcciones = Boolean.FALSE;
		visibleAuditar = Boolean.FALSE;
		/**
		 * Calendarizado
		 */
		if (idTipoComponente.equals(EstadoEventoCapEnum.CALENDARIZADO.getId())) {
			visibleAcceso = Boolean.TRUE;
			visibleParticipantes = Boolean.TRUE;
			visibleAcciones = Boolean.TRUE;
		}

		/**
		 * En ejecucion
		 */
		if (idTipoComponente.equals(EstadoEventoCapEnum.EN_EJECUCION.getId())) {
			visibleAcceso = Boolean.TRUE;
			visibleParticipantes = Boolean.TRUE;
			visibleAcciones = Boolean.TRUE;
		}

		/**
		 * Concluidos
		 */
		if (idTipoComponente.equals(EstadoEventoCapEnum.CONCLUIDOS.getId())) {
			visibleAuditar = Boolean.TRUE;
			visibleAcceso = Boolean.TRUE;
			visibleAcciones = Boolean.TRUE;
		}

		/**
		 * Bloqueado
		 */
		if (idTipoComponente.equals(EstadoEventoCapEnum.BLOQUEADO.getId())) {
			visibleAcciones = Boolean.TRUE;
		}

		/**
		 * Cancelado
		 */
		if (idTipoComponente.equals(EstadoEventoCapEnum.CANCELADO.getId())) {
			visibleAcciones = Boolean.TRUE;
		}
		/**
		 * Borrador
		 */
		if (idTipoComponente.equals(EstadoEventoCapEnum.BORRADOR.getId())) {
			visibleAcceso = Boolean.TRUE;
			visibleParticipantes = Boolean.TRUE;
			visibleAcciones = Boolean.TRUE;
		}

		/**
		 * Muestra todos los elementos
		 */
		if (idTipoComponente.equals(MOSTRAR_TODOS)) {

			visibleAcceso = Boolean.TRUE;
			visibleParticipantes = Boolean.TRUE;
			visibleAcciones = Boolean.TRUE;
			visibleAuditar = Boolean.TRUE;
		}

	}

	/**
	 * Buscar eventos de capacitacion
	 */
	public void buscarEventosCapacitacion() {
		logger.info("***********************buscarEventosCapacitacion 2***********************");
		if (ObjectUtils.isNotNull(filtros)) {

			logger.info("tipo competencia " + filtros.getFichaDescriptivaPrograma().getTipoCompetencia());
			logger.info("eje capacitacion " + filtros.getFichaDescriptivaPrograma().getEjeCapacitacion());
			logger.info("nombre evento    " + filtros.getNombreEc());
			logger.info("nombre programa  " + filtros.getFichaDescriptivaPrograma().getNombreTentativo());
			logger.info("fecha inicial    " + filtros.getFechaInicial());
			logger.info("fecha final      " + filtros.getFechaFinal());

			eventosCapacitacion = eventoCapacitacionServiceFacade.busquedaPorCriterios(filtros, tipoDatoFechas);
			if (!eventosCapacitacion.isEmpty()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_EVT", "", requestActual(),
						TipoServicioEnum.LOCAL);
			}
			traerComponentesVisibles(MOSTRAR_TODOS);
		}

	}
	
	public String obtenerRuta(EventoCapacitacionDTO evento){
		String ruta = "";
		
		MallaCurricularDTO mallaHijo = mallaCurricularService.obtenerMallaCurricularPorId(evento.getFichaDescriptivaPrograma().getEjeCapacitacion());
		ruta += buscarPadre(mallaHijo, ruta);

		/*
		ruta += "/"+evento.getFichaDescriptivaPrograma().getNombreTentativo();
		ruta += "/"+evento.getFichaDescriptivaPrograma().getPlan().getNombre();
		*/
		
		return ruta.substring(1, ruta.length()-1);
	}
	
	public String buscarPadre(MallaCurricularDTO malla, String nombres) {
		if(ObjectUtils.isNull(malla.getMallaCurricularPadre())){
			return nombres;
		}
		
		nombres += "/"+malla.getNombre();
		String padre = buscarPadre(malla.getMallaCurricularPadre(), nombres);
		
		return padre != null ? padre : null;
	}

	public String navegaRegistrarAsistenciasEventoCap() {
		registroAsistenciaBean.init();
		return ConstantesGestorWeb.NAVEGA_REGISTRO_ASISTENCIAS;
	}

	public void especificaModeloDeCompetencias(FichaDescProgramaDTO programa) {
		nombreEjeCapacitacion = getNombreElemento(programa.getEjeCapacitacion(), this.ejesCapacitacion);
		nombreTpoCompetencia = getNombreElemento(programa.getTipoCompetencia(), this.listaTiposCompetencias);
	}

	public String obtenerRutaLMS(EventoCapacitacionDTO eventoSelec) {
		String ruta;
		if (ObjectUtils.isNotNull(eventoSelec) && ObjectUtils.isNotNull(eventoSelec.getIdPlataformaLmsBorrador())
				&& ObjectUtils.isNotNull(eventoSelec.getIdCursoLmsBorrador())) {
			ParametroWSMoodleDTO parametroWSMoodleDTO = eventoCapacitacionServiceFacade.getParametroWSMoodleService()
					.buscarPorId(eventoSelec.getIdPlataformaLmsBorrador());

			PersonaDTO persona = eventoCapacitacionServiceFacade.getPersonaService()
					.obtienePersonaPorNombreUsuario(getUsuarioEnSession().getUsuario());
			LoginWS loginWS = new LoginWS(parametroWSMoodleDTO);

			try {
				// logger.info("Id persona: " + persona.getIdPersona());
				// logger.info("idEvento: " + eventoSelec.getNombreEc());
				ruta = loginWS.generarAccesoMoodle(getUsuarioEnSession().getUsuario(), persona.getContrasenia(),
						eventoSelec.getIdCursoLmsBorrador());
			} catch (Exception e) {
				ruta = parametroWSMoodleDTO.getHost();
				// logger.error(e.getMessage(), e);
				logger.info("El usuario: " + persona.getUsuario()
						+ " no se encuentra registrado en la plataforma. No se pueden generar accesos. Favor de registrarlo.");
			}
		} else {
			ruta = null;
		}
		// logger.info("##################################" + ruta);
		return ruta;
	}

	/* INICIO DE GETS Y SETS */
	public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
		return eventoCapacitacionServiceFacade;
	}

	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
	}

	public EventoCapacitacionDTO getFiltros() {
		return filtros;
	}

	public void setFiltros(EventoCapacitacionDTO filtros) {
		this.filtros = filtros;
	}

	public boolean isVisibleAuditar() {
		return visibleAuditar;
	}

	public void setVisibleAuditar(boolean visibleAuditar) {
		this.visibleAuditar = visibleAuditar;
	}

	public boolean isVisibleAcceso() {
		return visibleAcceso;
	}

	public void setVisibleAcceso(boolean visibleAcceso) {
		this.visibleAcceso = visibleAcceso;
	}

	public boolean isVisibleParticipantes() {
		return visibleParticipantes;
	}

	public void setVisibleParticipantes(boolean visibleParticipantes) {
		this.visibleParticipantes = visibleParticipantes;
	}

	public boolean isVisibleAcciones() {
		return visibleAcciones;
	}

	public void setVisibleAcciones(boolean visibleAcciones) {
		this.visibleAcciones = visibleAcciones;
	}

	public List<CatalogoComunDTO> getListaTiposCompetencias() {
		return listaTiposCompetencias;
	}

	public void setListaTiposCompetencias(List<CatalogoComunDTO> listaTiposCompetencias) {
		this.listaTiposCompetencias = listaTiposCompetencias;
	}

	public List<CatalogoComunDTO> getListaEjesCapacitacion() {
		return listaEjesCapacitacion;
	}

	public void setListaEjesCapacitacion(List<CatalogoComunDTO> listaEjesCapacitacion) {
		this.listaEjesCapacitacion = listaEjesCapacitacion;
	}

	public List<CatalogoComunDTO> getListaEstatusEC() {
		return listaEstatusEC;
	}

	public void setListaEstatusEC(List<CatalogoComunDTO> listaEstatusEC) {
		this.listaEstatusEC = listaEstatusEC;
	}

	public List<CatalogoComunDTO> getListaModalidadesPrograma() {
		return listaModalidadesPrograma;
	}

	public void setListaModalidadesPrograma(List<CatalogoComunDTO> listaModalidadesPrograma) {
		this.listaModalidadesPrograma = listaModalidadesPrograma;
	}

	public List<EventoCapacitacionDTO> getEventosCapacitacion() {
		return eventosCapacitacion;
	}

	public void setEventosCapacitacion(List<EventoCapacitacionDTO> eventosCapacitacion) {
		this.eventosCapacitacion = eventosCapacitacion;
	}

	/**
	 * @return the eventoSelec
	 */
	public EventoCapacitacionDTO getEventoSelec() {
		return eventoSelec;
	}

	/**
	 * @param eventoSelec
	 *            the eventoSelec to set
	 */
	public void setEventoSelec(EventoCapacitacionDTO eventoSelec) {
		this.eventoSelec = eventoSelec;
	}

	/**
	 * @return the nombreTpoCompetencia
	 */
	public String getNombreTpoCompetencia() {
		return nombreTpoCompetencia;
	}

	/**
	 * @param nombreTpoCompetencia
	 *            the nombreTpoCompetencia to set
	 */
	public void setNombreTpoCompetencia(String nombreTpoCompetencia) {
		this.nombreTpoCompetencia = nombreTpoCompetencia;
	}

	/**
	 * @return the nombreEjeCapacitacion
	 */
	public String getNombreEjeCapacitacion() {
		return nombreEjeCapacitacion;
	}

	/**
	 * @param nombreEjeCapacitacion
	 *            the nombreEjeCapacitacion to set
	 */
	public void setNombreEjeCapacitacion(String nombreEjeCapacitacion) {
		this.nombreEjeCapacitacion = nombreEjeCapacitacion;
	}

	public String getTipoDatoFechas() {
		return tipoDatoFechas;
	}

	public void setTipoDatoFechas(String tipoDatoFechas) {
		this.tipoDatoFechas = tipoDatoFechas;
	}

	public EventoCapacitacionDTO getEventoReporte() {
		return eventoReporte;
	}

	public void setEventoReporte(EventoCapacitacionDTO eventoReporte) {
		this.eventoReporte = eventoReporte;
	}

	public StreamedContent getReportePDF() {
		return reportePDF;
	}

	public void setReportePDF(StreamedContent reportePDF) {
		this.reportePDF = reportePDF;
	}

	public ParametroSistemaService getParametrosSistemaService() {
		return parametrosSistemaService;
	}

	public void setParametrosSistemaService(ParametroSistemaService parametrosSistemaService) {
		this.parametrosSistemaService = parametrosSistemaService;
	}

	public FECServiceFacade getFecServiceFacade() {
		return fecServiceFacade;
	}

	public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
		this.fecServiceFacade = fecServiceFacade;
	}

	public RegistroAsistenciaBean getRegistroAsistenciaBean() {
		return registroAsistenciaBean;
	}

	public void setRegistroAsistenciaBean(RegistroAsistenciaBean registroAsistenciaBean) {
		this.registroAsistenciaBean = registroAsistenciaBean;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public Integer getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}

	public List<CatalogoComunDTO> getCatEstructuras() {
		return catEstructuras;
	}

	public void setCatEstructuras(List<CatalogoComunDTO> catEstructuras) {
		this.catEstructuras = catEstructuras;
	}

	public List<CatalogoComunDTO> getCatSubEstructurasNivel1() {
		return catSubEstructurasNivel1;
	}

	public void setCatSubEstructurasNivel1(List<CatalogoComunDTO> catSubEstructurasNivel1) {
		this.catSubEstructurasNivel1 = catSubEstructurasNivel1;
	}

	public List<CatalogoComunDTO> getCatSubEstructurasNivel2() {
		return catSubEstructurasNivel2;
	}

	public void setCatSubEstructurasNivel2(List<CatalogoComunDTO> catSubEstructurasNivel2) {
		this.catSubEstructurasNivel2 = catSubEstructurasNivel2;
	}

	public List<CatalogoComunDTO> getCatSubEstructurasNivel3() {
		return catSubEstructurasNivel3;
	}

	public void setCatSubEstructurasNivel3(List<CatalogoComunDTO> catSubEstructurasNivel3) {
		this.catSubEstructurasNivel3 = catSubEstructurasNivel3;
	}

	public List<NodoeHijosDTO> getNodos() {
		return nodos;
	}

	public void setNodos(List<NodoeHijosDTO> nodos) {
		this.nodos = nodos;
	}

	public List<CatalogoComunDTO> getPlanes() {
		return planes;
	}

	public void setPlanes(List<CatalogoComunDTO> planes) {
		this.planes = planes;
	}



	public Integer getNivelMaximo() {
		return nivelMaximo;
	}

	public void setNivelMaximo(Integer nivelMaximo) {
		this.nivelMaximo = nivelMaximo;
	}

	public Integer getIdCapacitacion() {
		return idCapacitacion;
	}

	public void setIdCapacitacion(Integer idCapacitacion) {
		this.idCapacitacion = idCapacitacion;
	}

	public PlanService getPlanService() {
		return planService;
	}

	public void setPlanService(PlanService planService) {
		this.planService = planService;
	}

	public MallaCurricularService getMallaCurricularService() {
		return mallaCurricularService;
	}

	public void setMallaCurricularService(MallaCurricularService mallaCurricularService) {
		this.mallaCurricularService = mallaCurricularService;
	}
}