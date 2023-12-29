package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PlanService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.primefaces.component.tabview.Tab;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CapturaEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProgramaSocialDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ConfiguracionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.CritBusquedaAreasConfigDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.PersonalizacionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaDistribucionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaRecursoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ReservacionEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ReservacionEventoGeneralDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SolicitudReservacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgDuracionDTO;
import mx.gob.sedesol.basegestor.commons.utils.EstadoEventoCapEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoCalificacionECEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoConstanciaEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatEstatusReservacion;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionSistemaService;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.admin.MensajeOperacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;
import mx.gob.sedesol.basegestor.service.encuestas.EncuestaService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura.LogisticaInfraServiceFacade;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PlanService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Curso;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CursoWS;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.beans.administracion.CorreoNotificacionBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.dto.SolicitudReservAreaDTO;
import mx.gob.sedesol.gestorweb.commons.utils.EstatusProgramaEnum;
import mx.gob.sedesol.gestorweb.commons.utils.EstatusReservacionEnum;
import mx.gob.sedesol.gestorweb.commons.utils.GestorArchivos;
import mx.gob.sedesol.gestorweb.commons.utils.TipoRecursoEnum;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean
public class CapturaEventoCapacitacion extends BaseBean {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(CapturaEventoCapacitacion.class);

	@ManagedProperty(value = "#{eventoCapacitacionServiceFacade}")
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	@ManagedProperty("#{grupoService}")
	private GrupoService grupoService;

	@ManagedProperty("#{grupoParticipanteService}")
	private GrupoParticipanteService grupoParticipanteService;

	@ManagedProperty(value = "#{correoElectronicoService}")
	private CorreoElectronicoService correoElectronicoService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private ParametroSistemaService parametroSistemaService;

	@ManagedProperty(value = "#{notificacionSistemaService}")
	private NotificacionSistemaService notificacionSistemaService;

	@ManagedProperty("#{personaCorreoService}")
	private PersonaCorreoService personaCorreoService;

	@ManagedProperty(value = "#{logisticaInfraServiceFacade}")
	private LogisticaInfraServiceFacade logisticaInfraServiceFacade;

	@ManagedProperty("#{planService}")
	private PlanService planService;

	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;
	@ManagedProperty("#{mallaCurricularService}")
	private MallaCurricularService mallaCurricularService;
	@ManagedProperty(value = "#{eventoCapacitacionBean}")
	private EventoCapacitacionBean eventoCapacitacionBean;

	@ManagedProperty("#{mensajeOperacionService}")
	private MensajeOperacionService mensajeOperacionService;

	@ManagedProperty("#{encuestaService}")
	private EncuestaService encuestaService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	@ManagedProperty("#{correoNotificacionBean}")
	private CorreoNotificacionBean correoNotificacionBean;

	@ManagedProperty("#{relEncuestaEventoCapacitacionService}")
	private RelEncuestaEventoCapacitacionService relEncuestaEventoCapacitacionService;

	private Integer idPlan;
	private List<CatalogoComunDTO> catEstructuras;
	private List<CatalogoComunDTO> catSubEstructurasNivel1;
	private List<CatalogoComunDTO> catSubEstructurasNivel2;
	private List<CatalogoComunDTO> catSubEstructurasNivel3;
	private List<NodoeHijosDTO> nodos;
	private List<CatalogoComunDTO> catTpoComp;
	private List<CatalogoComunDTO> planes;
	private Integer nivelMaximo = 1;
	private List<CatalogoComunDTO> listaTiposCompetencias;
	private List<CatalogoComunDTO> listaEjesCapacitacion;
	private List<CatalogoComunDTO> listaModalidades;
	private List<CatalogoComunDTO> listaNivelesEnsenanza;

	private NodoeHijosDTO estPlanSedesol;

	private List<CatalogoComunDTO> listaDestinatarios;
	private List<ProgramaSocialDTO> listaProgramasSociales;
	private List<EntidadFederativaDTO> listaEntidadesFederativas;
	private List<MunicipioDTO> listaMunicipios;
	private List<CatalogoComunDTO> listaCategoriasEC;

	private List<ParametroWSMoodleDTO> listaPlataformasMoodle;
	private List<CatalogoComunDTO> listaClasificacionesAVA;

	private List<SedeDTO> sedes;
	private Map<Integer, List<AreaSedeDTO>> areasSede;

	private FichaDescProgramaDTO filtrosPrograma = new FichaDescProgramaDTO();

	private List<FichaDescProgramaDTO> programas;
	private List<Integer> idsEstructura;

	private CapturaEventoCapacitacionDTO datos;
	private EventoCapacitacionDTO evento;

	private Integer indicePanel;

	private List<EventoCapacitacionDTO> eventosAnteriores = new ArrayList<>();

	private List<Curso> cursosMoodle = new ArrayList<>();

	private List<PersonaResponsabilidadesDTO> coordinadores;
	private List<PersonaResponsabilidadesDTO> facilitadores;
	private List<PersonaResponsabilidadesDTO> productores;

	private PersonaResponsabilidadesDTO coordinador;
	private PersonaResponsabilidadesDTO facilitador;
	private PersonaResponsabilidadesDTO productor;

	private String rutaImagenes;
	private String rutaUndertow;
	private String nombreImagenComun;

	private String nombreImagen;

	private List<EncuestaDTO> listaEncuestas;
	private String mensajeEncuestas;

	private Integer tipoAreaSeleccionado;
	/* INICIO VARIABLES INFRAESTRUCTURA */
	private SolicitudReservacionDTO solRes;
	private List<SolicitudReservAreaDTO> listaReservaciones;
	private Date fechaReservacion;

	private List<RelAreaRecursoDTO> selTec;
	private List<RelAreaRecursoDTO> selMobi;
	private ConfiguracionAreaDTO configuracionAreaReservacion;
	private SolicitudReservAreaDTO solicitudSeleccionada;
	private SolicitudReservAreaDTO solicitudSeleccionadaEdit;
	private List<StreamedContent> imagenes;
	private List<File> arrayImagenes;
	private Integer idCapacitacion;
	private String idCatEstructura;
	private String idCatSubEstructuraNivel1;
	private String idCatSubEstructuraNivel2;
	private String idCatSubEstructuraNivel3;
	private ConfiguracionAreaDTO areaSeleccionada;
	private List<CatalogoComunDTO> catAreasInfra;
	/* FIN VARIABLES INFRAESTRUCTURA */
	
	private Boolean autonomo;
	
	private ModelMapper modelMapper = new ModelMapper();

	public static final int PRIMER_CORREO_DE_LA_PERSONA = 0;

	public static final int DE_EVENTO_CAP_ANTERIOR = 1;
	public static final int DE_CURSO_MOODLE = 2;
	public static final int NUEVO_AVA = 3;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void iniciaRecursos() {
		datos = new CapturaEventoCapacitacionDTO();
		evento = new EventoCapacitacionDTO();
		datos.setEvento(evento);
		filtrosPrograma.setCatStatusPrograma(new CatalogoComunDTO());
		filtrosPrograma.getCatStatusPrograma().setId(EstatusProgramaEnum.FINAL.getId());
		this.generaEstructuraCatTpoCompetenciaPlan();

		listaModalidades = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG);
		listaNivelesEnsenanza = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_NIVEL_ENSE_PLAN_PROG);

		listaDestinatarios = eventoCapacitacionServiceFacade.obtenerDestinatariosEc();
		listaEntidadesFederativas = eventoCapacitacionServiceFacade.obtenerEntidadesFederativas();
		listaMunicipios = new ArrayList<>();
		listaProgramasSociales = eventoCapacitacionServiceFacade.obtenerProgramasSociales();
		listaCategoriasEC = eventoCapacitacionServiceFacade.obtenerCatelogriasEC();
		listaPlataformasMoodle = eventoCapacitacionServiceFacade.obtenerPlataformasMoodle();
		listaClasificacionesAVA = eventoCapacitacionServiceFacade.obtenerClasificacionesAVA();

		removerOpcionesDeAvaNoDisponibles();

		sedes = logisticaInfraServiceFacade.getSedeService()
				.consultaSedesPorOrgGubDepedencia(ConstantesGestorWeb.DGGPB);
		inicializarAreasSede();
		fechaReservacion = null;
		//obtenerEstructuras();
		rutaImagenes = eventoCapacitacionServiceFacade.obtenerRutaAlmacenamientoImagenEvento();
		nombreImagenComun = eventoCapacitacionServiceFacade.obtenerNombreImagenComun();
		rutaUndertow = eventoCapacitacionServiceFacade.obtenerRutaUndertow();
		
		autonomo = true;
	}

	private void removerOpcionesDeAvaNoDisponibles() {
		for (CatalogoComunDTO catalogo : listaClasificacionesAVA) {
			if (catalogo.getId().equals(NUEVO_AVA)) {
				listaClasificacionesAVA = new ArrayList<>();
				listaClasificacionesAVA.add(catalogo);
				break;
			}
		}
	}

	public void concluirEvento(EventoCapacitacionDTO evento) {
		List<GrupoDTO> gruposDelEvento = grupoService.getGruposByEvento(evento.getIdEvento());
		if (gruposDelEvento.isEmpty()) {
			agregarMsgInfo("El evento " + evento.getNombreEc() + " no tiene grupos asignados.", null);
		} else {
			boolean todasLasActasCerradas = true;
			for (GrupoDTO grupoDTO : gruposDelEvento) {
				if (!grupoDTO.isActaCerrada()) {
					agregarMsgInfo("Aún no se ha realizado el cierre de acta para el grupo: " + grupoDTO.getNombre(),
							null);
					todasLasActasCerradas = false;
					break;
				}
			}
			if (todasLasActasCerradas) {
				eventoCapacitacionServiceFacade.getEventoCapacitacionService()
						.modificarEstatusEvento(EstadoEventoCapEnum.CONCLUIDOS.getId(), evento.getIdEvento());
				RequestContext.getCurrentInstance().execute("PF('wConcluirEvento').hide()");
				eventoCapacitacionBean.setEventosCapacitacion(new ArrayList<>());
				RequestContext.getCurrentInstance().update("frmtabla");
				agregarMsgInfo("El evento " + evento.getNombreEc() + " ha finalizado.", null);
			}
		}
	}

	public void bloquearEvento() {
		eventoCapacitacionServiceFacade.getEventoCapacitacionService()
				.modificarEstatusEvento(EstadoEventoCapEnum.BLOQUEADO.getId(), evento.getIdEvento());
		eventoCapacitacionBean.setEventosCapacitacion(new ArrayList<>());
		RequestContext.getCurrentInstance().update("frmtabla");
		agregarMsgInfo("El evento " + evento.getNombreEc() + " ha sido bloqueado.", null);
	}
	
	public void desbloquearEvento() {
		eventoCapacitacionServiceFacade.getEventoCapacitacionService()
				.modificarEstatusEvento(EstadoEventoCapEnum.BORRADOR.getId(), evento.getIdEvento());
		eventoCapacitacionBean.setEventosCapacitacion(new ArrayList<>());
		RequestContext.getCurrentInstance().update("frmtabla");
		agregarMsgInfo("El evento " + evento.getNombreEc() + " ha sido desbloqueado y pasó a estatus en Borrador.", null);
	}
	
	public void cancelarEvento() {
		eventoCapacitacionServiceFacade.getEventoCapacitacionService()
				.modificarEstatusEvento(EstadoEventoCapEnum.CANCELADO.getId(), evento.getIdEvento());
		eventoCapacitacionBean.setEventosCapacitacion(new ArrayList<>());
		RequestContext.getCurrentInstance().update("frmtabla");
		agregarMsgInfo("El evento " + evento.getNombreEc() + " ha sido cancelado.", null);
	}

	public void inicializarAreasSede() {
		areasSede = new HashMap<>();
		for (SedeDTO sede : sedes) {
			areasSede.put(sede.getIdSede(),
					logisticaInfraServiceFacade.getAreaSedeService().consultaAreasSedePorIdSede(sede.getIdSede()));
		}
	}
	public void onChangeCatPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer planSel = Integer.parseInt(e.getNewValue().toString());
			
			filtrosPrograma.setPlan( planService.buscarPorId( mallaCurricularService.buscarPorId(planSel).getIdPlan() ) );
			catEstructuras = this.generarEstructuras(nodos, Integer.parseInt(e.getNewValue().toString()));
			catSubEstructurasNivel1 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel2 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
			nivelMaximo = 0;
		}
	}
	public void onChangeCatEstructura(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSubEstructura = Integer.parseInt(e.getNewValue().toString());
			idCatEstructura = mallaCurricularService.obtenerMallaCurricularPorId(idSubEstructura).getNombre();
			filtrosPrograma.setEjeCapacitacion(idSubEstructura);
			catSubEstructurasNivel1 = this.generarSubEstructuras1(nodos, idSubEstructura);
			catSubEstructurasNivel2 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
			nivelMaximo = 1;
		}
	}
	public void onChangeCatSubestructura1(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSubEstructura = Integer.parseInt(e.getNewValue().toString());
			filtrosPrograma.setEjeCapacitacion(idSubEstructura);
			idCatSubEstructuraNivel1 = mallaCurricularService.obtenerMallaCurricularPorId(idSubEstructura).getNombre();
			catSubEstructurasNivel2 = this.generarSubEstructuras2(nodos, idSubEstructura);
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
			nivelMaximo = 2;
		}
	}
	public void onChangeCatSubestructura2(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSubEstructura = Integer.parseInt(e.getNewValue().toString());
			idCatSubEstructuraNivel2 = mallaCurricularService.obtenerMallaCurricularPorId(idSubEstructura).getNombre();
			filtrosPrograma.setEjeCapacitacion(idSubEstructura);
			catSubEstructurasNivel3 = this.generarSubEstructuras3(nodos, idSubEstructura);
			nivelMaximo = 3;
		}
	}

	public void onChangeCatSubestructura3(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			idCatSubEstructuraNivel3 = mallaCurricularService.obtenerMallaCurricularPorId(Integer.parseInt(e.getNewValue().toString())).getNombre();
			filtrosPrograma.setEjeCapacitacion(Integer.parseInt(e.getNewValue().toString()));
			nivelMaximo = 4;
		}
	}
	public void onChangeAplicaEncuesta() {

		listaEncuestas = encuestaService.buscarEncsPorKirkReaccionAlumnoPublicar();

		if (!listaEncuestas.isEmpty()) {
			String idEncuestaFacilitador = parametroSistemaService
					.obtenerParametro(ConstantesGestor.ENCUESTA_FACILITADOR);
			String idEncuestaCurso = parametroSistemaService.obtenerParametro(ConstantesGestor.ENCUESTA_CURSO);

			List<EncuestaDTO> encuestasEliminar = new ArrayList<>();
			if (ObjectUtils.isNotNull(idEncuestaFacilitador) && ObjectUtils.isNotNull(idEncuestaCurso)) {
				for (int i = 0; i < listaEncuestas.size(); i++) {
					if (listaEncuestas.get(i).getId().equals(Integer.valueOf(idEncuestaFacilitador))
							|| listaEncuestas.get(i).getId().equals(Integer.valueOf(idEncuestaCurso))) {
						encuestasEliminar.add(listaEncuestas.get(i));
					}
				}
				listaEncuestas.removeAll(encuestasEliminar);
			} else {
				logger.info("No se han creado los parametros de sistema idEncuestaFacilitador y idEncuestaCurso");
			}
		}

		if (listaEncuestas.isEmpty()) {
			mensajeEncuestas = "No se han creado encuestas.";
		} else {
			mensajeEncuestas = "";

		}

	}

	public void validarFechas() {
		if (ObjectUtils.isNotNull(datos.getEvento().getFechaFinal())) {
			if (datos.getEvento().getFechaFinal().before(datos.getEvento().getFechaInicial())) {
				datos.getEvento().setFechaFinal(datos.getEvento().getFechaInicial());
			}
		}

		if (ObjectUtils.isNotNull(fechaReservacion)) {
			fechaReservacion = null;
		}

	}

	public TipoUsuarioEnum[] getTiposUsuarios() {
		return TipoUsuarioEnum.values();
	}

	public TipoCalificacionECEnum[] getTiposCalificacion() {
		return TipoCalificacionECEnum.values();
	}

	public TipoConstanciaEnum[] getTiposConstancias() {
		return TipoConstanciaEnum.values();
	}

	public void onChangeEntidadFederativa(ValueChangeEvent e) {
		if (ObjectUtils.isNull(e.getNewValue())) {
			listaMunicipios = new ArrayList<>();
		} else {
			Integer valor = (Integer) e.getNewValue();
			listaMunicipios = eventoCapacitacionServiceFacade.obtenerMunicipiosPorEntidad(valor);
		}
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

		catTpoComp = new ArrayList<>();

		// Genera el Catalogo Tipo de Competencia
		estPlanSedesol = nodos.get(ConstantesGestorWeb.INDICE_INICIAL);
		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			catTpoComp.add(cc);
		}
	}

	/**
	 * Metodo que crea los ejes de capacitacion apartir del tipo de competencia
	 * seleccionado
	 */
	public void onChangeTpoCompetencia(ValueChangeEvent e) {

		if (ObjectUtils.isNotNull(e.getNewValue())) {

			Integer idTpoCompSel = (Integer) e.getNewValue();
			filtrosPrograma.setTipoCompetencia(idTpoCompSel);
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

	public void buscarProgramasCapacitacion() {
		if (ObjectUtils.isNotNull(filtrosPrograma)) {
			programas = eventoCapacitacionServiceFacade.buscaProgramasPorCriterios(filtrosPrograma);
		}
	}

	public void seleccionarPrograma(ValueChangeEvent e) {

		if (ObjectUtils.isNotNull(e.getNewValue())) {
			for (FichaDescProgramaDTO programaDTO : programas) {
				if (programaDTO.getIdPrograma().equals(e.getNewValue())) {
					datos.setPrograma(programaDTO);
					datos.setIdPrograma((int) e.getNewValue());

				}
			}
		}
	}

	public void seleccionarCoordinador(ValueChangeEvent e) {
		logger.info("#_" + e.getNewValue());
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			for (PersonaResponsabilidadesDTO coordinador : datos.getCoordinadores()) {
				if (coordinador.getId().equals(e.getNewValue())) {
					datos.setIdCoordinador((int) e.getNewValue());
					coordinador.setEsResponsablePrincipal(true);
				} else {
					coordinador.setEsResponsablePrincipal(false);
				}
			}
		}
	}

	public void seleccionarProductor(ValueChangeEvent e) {
		logger.info("#_" + e.getNewValue());
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			for (PersonaResponsabilidadesDTO productor : datos.getProductores()) {
				if (productor.getId().equals(e.getNewValue())) {
					datos.setIdProductor((int) e.getNewValue());
					productor.setEsResponsablePrincipal(true);
				} else {
					productor.setEsResponsablePrincipal(false);
				}
			}
		}
	}

	public String llenarDatosPrograma() {
		datos.getEvento().setIdPrograma(datos.getPrograma().getIdPrograma());
		datos.getEvento().setFichaDescriptivaPrograma(datos.getPrograma());
		Integer modalidadPrograma = datos.getEvento().getFichaDescriptivaPrograma().getCatModalidad().getId();
		CatalogoComunDTO modalidadEvento = setModEvtPorProg(modalidadPrograma);
		datos.getEvento().setCatModalidadPlanPrograma(modalidadEvento);
		RequestContext.getCurrentInstance().update("tabEventoCapacitacion:tabDos");

		if (ObjectUtils.isNullOrEmpty(datos.getPrograma().getCalificacionMinAprobatoria())) {
			datos.getEvento()
					.setCalificacionMinAprobatoria(String.valueOf(ConstantesGestor.VALOR_INICIAL_CALIFICACION_MINIMA));
		} else {
			datos.getEvento().setCalificacionMinAprobatoria(datos.getPrograma().getCalificacionMinAprobatoria());
		}
		datos.getEvento().setNombreEc(datos.getPrograma().getNombreTentativo());
		datos.getEvento().setObjetivoGeneralEc(datos.getPrograma().getObjetivosGenerales());
		datos.getEvento().setPerfilEc(datos.getPrograma().getPerfilIngreso());
		datos.getEvento().setRequisitosEc(datos.getPrograma().getConocimietosPrevios());
		// Se establece el tipo de calificacion y el tipo de dictamen en
		// automatico.
		datos.getEvento().setTpoCalificacion(ConstantesGestor.TIPO_CALIFICACION_PROMEDIO);
		datos.getEvento().setTpoDictamen(ConstantesGestor.TIPO_DICTAMEN_PROMEDIO);
		calcularCargaHoraria();
		indicePanel = 1;
		return null;
	}

	/**
	 * Verifica la modalidad del programa y dependiendo de esta establece una
	 * modalidad para el evento
	 */
	public CatalogoComunDTO setModEvtPorProg(Integer modalidadPrograma) {
		for (CatalogoComunDTO modalidad : listaModalidades) {

			if (modalidad.getId().equals(modalidadPrograma)) {

				return modalidad;
			}
		}
		return null;
	}

	public void fileUploadListener(FileUploadEvent e) {
		UploadedFile archivoCargar = e.getFile();
		if (ObjectUtils.isNotNull(archivoCargar) && !ObjectUtils.isNullOrEmpty(archivoCargar.getFileName())) {
			datos.getEvento().setUrlImagen(UUID.randomUUID().toString());
			String rutaArchivo = rutaImagenes + datos.getEvento().getUrlImagen();
			if (GestorArchivos.crearCarpeta(rutaImagenes).getResultado().getValor() && GestorArchivos
					.almacenarArchivo(rutaArchivo, archivoCargar.getContents()).getResultado().getValor()) {
				datos.setRutaCompletaImagen(rutaUndertow + datos.getEvento().getUrlImagen());
			}
			nombreImagen = e.getFile().getFileName();
		}
	}

	public String cargarDatosAgenda() {
		datos.getEvento().setFechaInicial(datos.getPrograma().getFechaVigInicial());
		datos.getEvento().setFechaFinal(datos.getPrograma().getFechaVigFinal());
		datos.getEvento().setCategoriaEC(datos.getPrograma().getCatTipoEventoEc());
		indicePanel = 2;
		return null;
	}

	public String cargarResponsables() {
		indicePanel = 3;
		return null;
	}

	private void calcularCargaHoraria() {
		int numHoras = 0;
		int numMinutos = 0;

		PlanDTO plan = planService.buscarPorId(datos.getPrograma().getPlan().getIdPlan());
		if (plan.getCatCreditosPlan().getNombre().equals("Obligatorio")){
			numHoras = datos.getPrograma().getCreditos() * plan.getHorasCredito();
		}else {
			for (RelProgDuracionDTO duracion : datos.getPrograma().getRelProgramaDuracion()) {
				if (!ObjectUtils.isNullOrEmpty(duracion.getHoras())) {
					numHoras += Integer.parseInt(duracion.getHoras());
				}
				if (!ObjectUtils.isNullOrEmpty(duracion.getMinutos())) {
					numMinutos += Integer.parseInt(duracion.getMinutos());
				}
			}
		}
		int minutosReales = numMinutos % 60;
		int horasRestantes = (int) numMinutos / 60;
		int horasReales = numHoras + horasRestantes;
		datos.setNumeroHoras(String.valueOf(horasReales));
		datos.setNumeroMinutos(String.valueOf(minutosReales));
	}

	public void onChangePlataforma(ValueChangeEvent e) {
		logger.info("##### changePlataforma");
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idPlataforma = (Integer) e.getNewValue();
			logger.info(datos.getPrograma().getIdPrograma() + " " + idPlataforma);
			eventosAnteriores = eventoCapacitacionServiceFacade.getEventoCapacitacionService()
					.obtenerEventosConcluidos(datos.getPrograma().getIdPrograma(), idPlataforma);

			// ExisteCurso existeCurso = new
			// ExisteCurso(modelMapper.map(listaPlataformasMoodle.get(0),
			// platafor));
			CursoWS cursoWS = new CursoWS(listaPlataformasMoodle.get(0));
			try {
				cursosMoodle = cursoWS.listarCursosSinIdNumber();
			} catch (Exception e2) {
				logger.error(e2.getMessage(), e2);
			}
		} else {
			eventosAnteriores = new ArrayList<>();
		}
	}

	public void agregarCoordinador() {
		if (ObjectUtils.isNotNull(coordinador)) {
			logger.info(coordinador.getTblPersona());
			if (datos.getCoordinadores().isEmpty()) {
				coordinador.setEsResponsablePrincipal(true);
				datos.setIdCoordinador(coordinador.getId());
			} else {
				coordinador.setEsResponsablePrincipal(false);
			}
			datos.getCoordinadores().add(coordinador);
			coordinadores.remove(coordinador);
			coordinador = null;
		}
	}

	public void eliminarCoordinador() {
		coordinadores.add(coordinador);
		datos.getCoordinadores().remove(coordinador);
		coordinador = null;
	}

	public void agregarFacilitador() {
		if (ObjectUtils.isNotNull(facilitador)) {
			datos.getFacilitadores().add(facilitador);
			facilitadores.remove(facilitador);
			facilitador = null;
		}
	}

	public void eliminarFacilitador() {
		facilitadores.add(facilitador);
		datos.getFacilitadores().remove(facilitador);
		facilitador = null;
	}

	public void agregarProductor() {
		if (ObjectUtils.isNotNull(productor)) {
			if (datos.getProductores().isEmpty()) {
				productor.setEsResponsablePrincipal(true);
				datos.setIdProductor(productor.getId());
			} else {
				productor.setEsResponsablePrincipal(false);
			}
			datos.getProductores().add(productor);
			productores.remove(productor);
			productor = null;
		}
	}

	public void eliminarProductores() {
		productores.add(productor);
		datos.getProductores().remove(productor);
		productor = null;
	}

	public String cargarEventoCapacitacion() {
		indicePanel = 1;
		datos = eventoCapacitacionServiceFacade.obtenerEvento(evento);
		coordinadores = eventoCapacitacionServiceFacade.obtienePersonasPorResponsabilidad(2);
		facilitadores = eventoCapacitacionServiceFacade.obtienePersonasPorResponsabilidad(1);
		productores = eventoCapacitacionServiceFacade.obtienePersonasPorResponsabilidad(3);
		if (ObjectUtils.isNull(datos.getEvento().getIdEntidadFederativa())) {
			listaMunicipios = new ArrayList<>();
		} else {
			listaMunicipios = eventoCapacitacionServiceFacade
					.obtenerMunicipiosPorEntidad(datos.getEvento().getIdEntidadFederativa());
		}

		String rutaAbsolutaFoto = rutaImagenes + datos.getEvento().getUrlImagen();

		File archivo = new File(rutaAbsolutaFoto);

		if (archivo.exists()) {
			datos.setRutaCompletaImagen(rutaUndertow + datos.getEvento().getUrlImagen());
		} else {
			datos.setRutaCompletaImagen(rutaUndertow + nombreImagenComun);
		}

		if (ObjectUtils.isNotNull(datos.getSolicitud())) {

		}

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_EVT", String.valueOf(evento.getIdEvento()),
				requestActual(), TipoServicioEnum.LOCAL);

		return ConstantesGestorWeb.NAVEGA_REGISTRO_NUEVO_EVENTO_CAPACITACION;
	}

	public void abrirModal() {
		RequestContext.getCurrentInstance().execute("PF('modalReserv').show()");
	}

	/**
	 * Cuando la modalidad es presencial se tiene que cambiar el estatus a
	 * ejecucion
	 * 
	 * @param evento
	 */
	public void establecerEstatus(EventoCapacitacionDTO evento) {
		if (ObjectUtils.isNotNull(evento)) {
			if (ObjectUtils.isNotNull(evento.getCatModalidadPlanPrograma())) {
				if (ObjectUtils.isNotNull(evento.getCatModalidadPlanPrograma().getId())) {
					if (evento.getCatModalidadPlanPrograma().getId().equals(ConstantesGestor.MODALIDAD_PRESENCIAL)) {
						evento.getCatEstadoEventoCapacitacion().setId(ConstantesGestor.EVEN_CAP_ESTATUS_EJECUCION);
					}
				}
			}
		}
	}

	public String guardarEventoCapacitacion() {
		logger.info("guardar evento bean");
		List<String> resultadoInfraestructura = procesarInfraestructura();
		if (resultadoInfraestructura.isEmpty()) {
			datos.getEvento().setValorCalificacionDictamen(100 - datos.getEvento().getValorAsistenciaDictamen());
			datos.getEvento().getCatEstadoEventoCapacitacion().setId(ConstantesGestor.EVEN_CAP_ESTATUS_CALENDARIZADO);
			establecerEstatus(datos.getEvento());
			ResultadoDTO<EventoCapacitacionDTO> resultado = eventoCapacitacionServiceFacade
					.guardarEventoCapacitacion(datos, autonomo);
			if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "FIN_CRE_EVT",
						String.valueOf(resultado.getDto().getIdEvento()), requestActual(), TipoServicioEnum.LOCAL);
				if (datos.getEvento().getCatModalidadPlanPrograma().getId()
						.equals(ConstantesGestor.MODALIDAD_PRESENCIAL)) {
					agregarFlashMessage("El evento de capacitación se guardó en estatus En ejecución.", null,
							FacesMessage.SEVERITY_INFO);
				} else {
					agregarFlashMessage("El evento de capacitación se guardó en estatus Calendarizado.", null,
							FacesMessage.SEVERITY_INFO);
				}

				// TODO Enviar notificaciones y correos a los responsables
				if (ObjectUtils.isNotNull(resultado.getDto())) {
					EventoCapacitacionDTO evento = resultado.getDto();
					String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_AL_CREAR_EVENTO;
					String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_AL_CREAR_EVENTO;
					correoNotificacionBean.notificarResponsablesEvento(claveNotificacion, claveCorreo, evento);
				} else {
					logger.info("No fue posible enviar la notificacion a los responsables.");
				}

				/*
				 * Se limpia la lista de eventos de capacitacion y se resetea la
				 * modalidad
				 */
				eventoCapacitacionBean.setEventosCapacitacion(new ArrayList<>());
				eventoCapacitacionBean.getFiltros().setCatEstadoEventoCapacitacion(new CatalogoComunDTO());
				return ConstantesGestorWeb.NAVEGA_BUSQUEDA_EVENTO_CAP;
			} else {
				if (ObjectUtils.isNotNull(resultado.getMensajes())) {
					agregarMsgError(resultado.getMensajes(), null, sistema);
				}
			}
		} else {
			agregarMsgError(resultadoInfraestructura, null, sistema);
		}

		return null;
	}

	public String nuevoEventoCapacitacion() {
		/* Limpiando el filtro de seleccion de programas */
		filtrosPrograma = new FichaDescProgramaDTO();
		filtrosPrograma.setCatStatusPrograma(new CatalogoComunDTO());
		filtrosPrograma.getCatStatusPrograma().setId(EstatusProgramaEnum.FINAL.getId());
		listaEjesCapacitacion = new ArrayList<CatalogoComunDTO>();
		programas = new ArrayList<FichaDescProgramaDTO>();

		/* Limpiando los datos del evento */
		datos = new CapturaEventoCapacitacionDTO();
		evento = new EventoCapacitacionDTO();
		evento.setCatModalidadPlanPrograma(new CatalogoComunDTO());
		evento.setIdDestinatario(new CatalogoComunDTO());
		datos.setEvento(evento);
		datos.getEvento().setOportunidadesEvaluacion(2);
		datos.getEvento().setConstancia(true);
		datos.getEvento().setIdDirigido(TipoUsuarioEnum.INTERNO.getValor());
		datos.getEvento().setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		datos.setTipoAreaSeleccionado(0);

		datos.setRutaCompletaImagen(rutaUndertow + nombreImagenComun);
		nombreImagen = null;
		indicePanel = 0;

		coordinadores = eventoCapacitacionServiceFacade.obtienePersonasPorResponsabilidad(2);
		facilitadores = eventoCapacitacionServiceFacade.obtienePersonasPorResponsabilidad(1);
		productores = eventoCapacitacionServiceFacade.obtienePersonasPorResponsabilidad(3);

		// INFRAESTRUCTURA
		listaReservaciones = new ArrayList<>();
		agregarRegistroReservacion();
		configuracionAreaReservacion = new ConfiguracionAreaDTO();
		solRes = new SolicitudReservacionDTO();
		fechaReservacion = null;

		return ConstantesGestorWeb.NAVEGA_REGISTRO_NUEVO_EVENTO_CAPACITACION;
	}

	private List<String> procesarInfraestructura() {
		List<String> resultado = new ArrayList<String>();
		if (datos.getEvento().getCatModalidadPlanPrograma().getId().equals(ConstantesGestor.MODALIDAD_PRESENCIAL)
				|| datos.getEvento().getCatModalidadPlanPrograma().getId().equals(ConstantesGestor.MODALIDAD_MIXTO)) {

			if (!datos.getTipoAreaSeleccionado().equals(0)) {
				if (datos.getTipoAreaSeleccionado().equals(ConstantesGestor.TIPO_AREA_INTERNA)) {

					solRes.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
					solRes.setFechaRegistro(new Date());
					datos.setSolicitud(solRes);
					datos.setReservaciones(new ArrayList<>());

					for (SolicitudReservAreaDTO r : listaReservaciones) {
						AreaSedeDTO area = logisticaInfraServiceFacade.getAreaSedeService()
								.consultaAreaSedePorIdSedeyIdArea(r.getIdSede(), r.getIdArea());

						ReservacionEventoCapacitacionDTO rEC = new ReservacionEventoCapacitacionDTO();
						rEC.setPersonalizacionAreaDTO(r.getPerArea());
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

						rEC.setIdPersonalizacionArea(r.getPerArea().getIdPersonalizacion());
						rEC.setFechaRegistro(new Date());
						rEC.setUsuarioRegistro(getUsuarioEnSession().getIdPersona());

						CatalogoComunDTO estatus = logisticaInfraServiceFacade.getEstatusReservacionService()
								.buscarPorId(EstatusReservacionEnum.RESERVADO_SIN_APROBAR.getId(),
										CatEstatusReservacion.class);

						rEC.setCatEstatusReservacion(estatus);

						datos.getReservaciones().add(rEC);

					}

				}
			} else {
				resultado.add(MensajesSistemaEnum.EC_TIPO_AREA_REQ.getId());
			}

		}
		return resultado;
	}

	public String guardarBorrador() {
		logger.info("guardar borrador bean " + datos.getEvento().getIdClasificacionAva());
		datos.getEvento().setValorCalificacionDictamen(100 - datos.getEvento().getValorAsistenciaDictamen());
		datos.getEvento().getCatEstadoEventoCapacitacion().setId(ConstantesGestor.EVEN_CAP_ESTATUS_BORRADOR);
		ResultadoDTO<EventoCapacitacionDTO> resultado = eventoCapacitacionServiceFacade.guardarBorrador(datos, autonomo);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "GUA_BOR_EVT",
					String.valueOf(resultado.getDto().getIdEvento()), requestActual(), TipoServicioEnum.LOCAL);
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarFlashMessage("El evento de capacitación se guardó en estatus Borrador.", null,
						FacesMessage.SEVERITY_INFO);
			}
			return ConstantesGestorWeb.NAVEGA_BUSQUEDA_EVENTO_CAP;
		} else {
			if (ObjectUtils.isNotNull(resultado.getMensajes())) {
				agregarMsgError(resultado.getMensajes(), null, sistema);
			}
		}
		return null;
	}

	/* INICIA LA INPLEMENTACION DE INFRAESTRUCTURA */
	public void actualizaCatAreas(SolicitudReservAreaDTO e) {
		logger.info("Actualizar area " + e.getIdSede());
		SolicitudReservAreaDTO solicitudS = (SolicitudReservAreaDTO) e;
		solicitudS.setIdArea(null);
		solicitudS.setCatAreas(new ArrayList<>());

		// Genera el Catalogo de areas asociadas a la sede
		CritBusquedaAreasConfigDTO criteriosConf = new CritBusquedaAreasConfigDTO();
		criteriosConf.setIdSede(solicitudS.getIdSede());
		catAreasInfra = new ArrayList<>();
		List<ConfiguracionAreaDTO> areasConfiguradas = logisticaInfraServiceFacade.getConfiguracionAreaService()
				.busquedaAreasConfiguradasCriterios(criteriosConf);

		if (ObjectUtils.isNotNull(areasConfiguradas)) {
			for (ConfiguracionAreaDTO s : areasConfiguradas) {
				catAreasInfra.add(s.getCatAreasSede().getCatArea());
			}
		}
		solicitudS.setCatAreas(catAreasInfra);
	}

	public void agregarRegistroReservacion() {

		SolicitudReservAreaDTO solicitudAdicional = new SolicitudReservAreaDTO();

		List<SedeDTO> sedes = logisticaInfraServiceFacade.getSedeService()
				.consultaSedesPorOrgGubDepedencia(ConstantesGestorWeb.DGGPB);
		HashMap<Integer, String> sedesPorUbi = new HashMap<>();

		if (!ObjectUtils.isNullOrEmpty(sedes)) {
			for (SedeDTO sede : sedes) {
				sedesPorUbi.put(sede.getIdSede(),
						sede.getCatUbicacionTerritorial().getNombre().concat("-").concat(sede.getNombre()));
			}

			List<NodoDTO> ubiSedes = new ArrayList<>();
			for (Map.Entry<Integer, String> entry : sedesPorUbi.entrySet()) {
				NodoDTO nodo = new NodoDTO(entry.getKey(), entry.getValue());
				ubiSedes.add(nodo);

				CritBusquedaAreasConfigDTO criteriosConf = new CritBusquedaAreasConfigDTO();
				catAreasInfra = new ArrayList<>();
				List<ConfiguracionAreaDTO> areasConfiguradas = logisticaInfraServiceFacade.getConfiguracionAreaService()
						.busquedaAreasConfiguradasCriterios(criteriosConf);

				if (ObjectUtils.isNotNull(areasConfiguradas)) {
					for (ConfiguracionAreaDTO s : areasConfiguradas) {
						catAreasInfra.add(s.getCatAreasSede().getCatArea());
					}
				}
				solicitudAdicional.setCatAreas(catAreasInfra);
			}

			solicitudAdicional.setCatUbicacionSede(ubiSedes);
		}

		solicitudAdicional.setPerArea(new PersonalizacionAreaDTO());

		listaReservaciones.add(solicitudAdicional);
	}

	public void generaHorasLibres(SolicitudReservAreaDTO e) {
		SolicitudReservAreaDTO solicitud = e;

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fechaRes = formatter.format(fechaReservacion).toString();

		List<String> horas = new ArrayList<>();

		Integer idAreaSede = logisticaInfraServiceFacade.getAreaSedeService()
				.consultaAreaSedePorIdSedeyIdArea(e.getIdSede(), e.getIdArea()).getIdAreaSede();

		solicitud.setIdAreaSede(idAreaSede);

		List<ReservacionEventoCapacitacionDTO> reservacionesC = logisticaInfraServiceFacade
				.getReservacionEventoCapacitacionService().buscaReservacionesPorIdAreaSede(solicitud.getIdAreaSede());
		List<ReservacionEventoGeneralDTO> reservacionesG = logisticaInfraServiceFacade
				.getReservacionEventoGeneralService().buscaReservacionesPorIdAreaSede(solicitud.getIdAreaSede());

		if (!ObjectUtils.isNullOrEmpty(reservacionesC)) {
			for (ReservacionEventoCapacitacionDTO res : reservacionesC) {
				if (fechaRes.equals(formatter.format(res.getFechaInicialReservacion()))) {
					horas.addAll(horasCap(res));
				}
			}
		}

		if (!ObjectUtils.isNullOrEmpty(reservacionesG)) {
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

	private List<String> horasGen(ReservacionEventoGeneralDTO reservacionG) {
		int indexInicial = 0;
		int indexFinal = 0;
		List<String> horasOcupadas = new ArrayList<>();
		List<String> array24Horas = obtieneArray24Horas();

		Calendar cI = Calendar.getInstance();
		cI.setTime(reservacionG.getFechaInicialReservacion());

		Calendar cF = Calendar.getInstance();
		cF.setTime(reservacionG.getFechaFinalReservacion());

		DateFormat dateFormat = new SimpleDateFormat("HH:mm");

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

	private List<String> horasCap(ReservacionEventoCapacitacionDTO reservacionC) {

		int indexInicial = 0;
		int indexFinal = 0;
		List<String> horasOcupadas = new ArrayList<>();
		List<String> array24Horas = obtieneArray24Horas();

		Calendar cI = Calendar.getInstance();
		cI.setTime(reservacionC.getFechaInicialReservacion());

		Calendar cF = Calendar.getInstance();
		cF.setTime(reservacionC.getFechaFinalReservacion());

		DateFormat dateFormat = new SimpleDateFormat("HH:mm");

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

	private List<String> obtieneArray24Horas() {
		List<String> horas = new ArrayList<>();

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

	public void navegaPersonalizacionArea() {

		selTec = new ArrayList<>();
		selMobi = new ArrayList<>();

		CritBusquedaAreasConfigDTO critArea = new CritBusquedaAreasConfigDTO();

		critArea.setIdOrgGubernamental(ConstantesGestorWeb.DGGPB);
		// TODO revisar si se tiene que mandar la ubicacion
		critArea.setIdSede(solicitudSeleccionada.getIdSede());
		critArea.setIdArea(solicitudSeleccionada.getIdArea());

		configuracionAreaReservacion = logisticaInfraServiceFacade.getConfiguracionAreaService()
				.busquedaAreasConfiguradasCriterios(critArea).get(ConstantesGestorWeb.INDICE_INICIAL);

		solicitudSeleccionadaEdit = new SolicitudReservAreaDTO();

		if (solicitudSeleccionada.getPersonalizacionBoolean() != null
				&& solicitudSeleccionada.getPersonalizacionBoolean()) {
			modelMapper.map(solicitudSeleccionada, solicitudSeleccionadaEdit);
			// solicitudSeleccionadaEdit.setPerArea(solicitudSeleccionada.getPerArea());

			selMobi = solicitudSeleccionadaEdit.getPerArea().getRecMob();
			selTec = solicitudSeleccionadaEdit.getPerArea().getRecTec();
			llenaPersonalizarDistribucion();

		} else {

			modelMapper.map(solicitudSeleccionada, solicitudSeleccionadaEdit);

			solicitudSeleccionadaEdit.setPerArea(new PersonalizacionAreaDTO());
			// solicitudSeleccionadaEdit.getPerArea()
			// .setEmailResponsable(configuracionAreaReservacion.getEmailResponsable());
			// solicitudSeleccionadaEdit.getPerArea().setEmailCc(configuracionAreaReservacion.getEmailCc());
			llenaRecursos();
		}
		llenaImagenes();
	}

	public void llenaPersonalizarDistribucion() {

		for (RelAreaDistribucionDTO d : configuracionAreaReservacion.getRelAreaDistribucion()) {
			if (solicitudSeleccionadaEdit.getPerArea().getDistribucionArea().getId()
					.equals(d.getCatDistribucionArea().getId())) {
				solicitudSeleccionadaEdit.getPerArea().setDistribucionArea(d.getCatDistribucionArea());
			}
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
		if (!arrayImagenes.isEmpty()) {
			try {
				for (File f : arrayImagenes) {
					InputStream input = new FileInputStream(f);
					StreamedContent sc = new DefaultStreamedContent(input,
							getFacesContext().getExternalContext().getMimeType(f.getName()), f.getName());
					imagenes.add(sc);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

	public void onTabChange(TabChangeEvent event) {
		Tab activeTab = event.getTab();
		cargarDatosAgenda();
		switch (activeTab.getId()) {
		case "tabCero":
			indicePanel = 0;
			break;
		case "tabUno":
			indicePanel = 1;
			break;
		case "tabDos":
			indicePanel = 2;
			break;
		case "tabTres":
			indicePanel = 3;
			break;
		}

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
		if (!arrayImagenes.isEmpty()) {
			try {
				for (File f : arrayImagenes) {
					InputStream input = new FileInputStream(f);
					StreamedContent sc = new DefaultStreamedContent(input,
							getFacesContext().getExternalContext().getMimeType(f.getName()), f.getName());
					imgs.add(sc);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return imgs;
	}

	public void limpiaSS() {
		solicitudSeleccionada = new SolicitudReservAreaDTO();
	}

	// guarda datos de DTO de personalziacion
	public void guardaPersonalizacionArea() {

		solicitudSeleccionadaEdit.getPerArea().setFechaRegistro(new Date());
		solicitudSeleccionadaEdit.getPerArea().setFechaActualizacion(new Date());
		solicitudSeleccionadaEdit.getPerArea().setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		solicitudSeleccionadaEdit.getPerArea().setCatAreasSede(configuracionAreaReservacion.getCatAreasSede());
		solicitudSeleccionadaEdit.getPerArea().setRecTec(selTec);
		solicitudSeleccionadaEdit.getPerArea().setRecMob(selMobi);

		solicitudSeleccionadaEdit.setPersonalizacionBoolean(Boolean.TRUE);

		listaReservaciones.remove(solicitudSeleccionada);
		listaReservaciones.add(solicitudSeleccionadaEdit);

		solicitudSeleccionada = new SolicitudReservAreaDTO();
		solicitudSeleccionadaEdit = new SolicitudReservAreaDTO();

		agregarMsgInfo("Personalización guardada", null);

	}

	public void validarReservacion() {

		// validación de campos vacios
		boolean validacionTabla = true;
		for (SolicitudReservAreaDTO s : listaReservaciones) {
			if (s.getIdSede() == null) {
				validacionTabla = false;
			} else if (s.getIdArea() == null) {
				validacionTabla = false;
			} else if (s.getPersonalizacionBoolean() == null) {
				validacionTabla = false;
			} else if (s.getHoraInicial() == null) {
				validacionTabla = false;
			} else if (s.getHoraFinal() == null) {
				validacionTabla = false;
			}
		}
		if (!validacionTabla) {
			agregarMsgError("Todos los campos de la tabla son requeridos.", null, sistema);
		} else {

			// Validacion de sala y horas
			Boolean aprobado = true;
			for (SolicitudReservAreaDTO sA : listaReservaciones) {
				for (SolicitudReservAreaDTO sB : listaReservaciones) {

					if (sA != sB) {
						Integer inHIsA = 0;
						Integer inHFsA = 0;
						Integer inHIsB = 0;
						Integer inHFsB = 0;
						for (String h : sA.getHorasA()) {
							// Saca indexes de sA
							if (h.equals(sA.getHoraInicial())) {
								inHIsA = sA.getHorasA().indexOf(h);
							} else if (h.equals(sA.getHoraFinal())) {
								inHFsA = sA.getHorasA().indexOf(h);
							}
							// Saca indexes de sB
							if (h.equals(sB.getHoraInicial())) {
								inHIsB = sB.getHorasA().indexOf(h);
							} else if (h.equals(sB.getHoraFinal())) {
								inHFsB = sB.getHorasA().indexOf(h);
							}
						}

						if ((inHIsA > inHIsB) & (inHIsA <= inHFsB)) {
							if (inHIsA == inHFsB) {
								aprobado = true;
							} else {
								aprobado = validaAreas(sA, sB);
							}
						} else if ((inHFsA > inHIsB) & (inHFsA <= inHFsB)) {
							aprobado = validaAreas(sA, sB);
						}
					}
				}
			}
			if (aprobado) {
				RequestContext.getCurrentInstance().execute("PF('dialogReservacion').hide();");
				agregarMsgInfo("Solicitud de reservación de área creada correctamente.", null);
			} else {
				agregarMsgError("Los horarios se empalman.", null, sistema);
			}
		}

	}

	private boolean validaAreas(SolicitudReservAreaDTO areaA, SolicitudReservAreaDTO areaB) {
		if (areaA.getIdArea() == areaB.getIdArea()) {
			return false;
		} else {
			return true;
		}
	}

	public void onChangeTipoArea(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			tipoAreaSeleccionado = (Integer) e.getNewValue();
			datos.setTipoAreaSeleccionado(tipoAreaSeleccionado);

			if (datos.getTipoAreaSeleccionado() != 1) {
				// Reseteamos todo lo que tenga que ver con infraestructura
				listaReservaciones = new ArrayList<>();
				agregarRegistroReservacion();
				configuracionAreaReservacion = new ConfiguracionAreaDTO();
				solRes = new SolicitudReservacionDTO();
				fechaReservacion = null;

			}
		}
	}

	/* INICIO DE GETS Y SETS */
	public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
		return eventoCapacitacionServiceFacade;
	}

	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
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

	public FichaDescProgramaDTO getFiltrosPrograma() {
		return filtrosPrograma;
	}

	public void setFiltrosPrograma(FichaDescProgramaDTO filtrosPrograma) {
		this.filtrosPrograma = filtrosPrograma;
	}

	public List<FichaDescProgramaDTO> getProgramas() {
		return programas;
	}

	public void setProgramas(List<FichaDescProgramaDTO> programas) {
		this.programas = programas;
	}

	public CapturaEventoCapacitacionDTO getDatos() {
		return datos;
	}

	public void setDatos(CapturaEventoCapacitacionDTO datos) {
		this.datos = datos;
	}

	public Integer getIndicePanel() {
		return indicePanel;
	}

	public void setIndicePanel(Integer indicePanel) {
		this.indicePanel = indicePanel;
	}

	public List<CatalogoComunDTO> getListaCategoriasEC() {
		return listaCategoriasEC;
	}

	public void setListaCategoriasEC(List<CatalogoComunDTO> listaCategoriasEC) {
		this.listaCategoriasEC = listaCategoriasEC;
	}

	public List<ParametroWSMoodleDTO> getListaPlataformasMoodle() {
		return listaPlataformasMoodle;
	}

	public void setListaPlataformasMoodle(List<ParametroWSMoodleDTO> listaPlataformasMoodle) {
		this.listaPlataformasMoodle = listaPlataformasMoodle;
	}

	public List<ProgramaSocialDTO> getListaProgramasSociales() {
		return listaProgramasSociales;
	}

	public void setListaProgramasSociales(List<ProgramaSocialDTO> listaProgramasSociales) {
		this.listaProgramasSociales = listaProgramasSociales;
	}

	public List<CatalogoComunDTO> getListaClasificacionesAVA() {
		return listaClasificacionesAVA;
	}

	public void setListaClasificacionesAVA(List<CatalogoComunDTO> listaClasificacionesAVA) {
		this.listaClasificacionesAVA = listaClasificacionesAVA;
	}

	public List<CatalogoComunDTO> getListaModalidades() {
		return listaModalidades;
	}

	public void setListaModalidades(List<CatalogoComunDTO> listaModalidades) {
		this.listaModalidades = listaModalidades;
	}

	public List<CatalogoComunDTO> getListaNivelesEnsenanza() {
		return listaNivelesEnsenanza;
	}

	public void setListaNivelesEnsenanza(List<CatalogoComunDTO> listaNivelesEnsenanza) {
		this.listaNivelesEnsenanza = listaNivelesEnsenanza;
	}

	public List<CatalogoComunDTO> getListaDestinatarios() {
		return listaDestinatarios;
	}

	public void setListaDestinatarios(List<CatalogoComunDTO> listaDestinatarios) {
		this.listaDestinatarios = listaDestinatarios;
	}

	public List<EntidadFederativaDTO> getListaEntidadesFederativas() {
		return listaEntidadesFederativas;
	}

	public void setListaEntidadesFederativas(List<EntidadFederativaDTO> listaEntidadesFederativas) {
		this.listaEntidadesFederativas = listaEntidadesFederativas;
	}

	public List<MunicipioDTO> getListaMunicipios() {
		return listaMunicipios;
	}

	public void setListaMunicipios(List<MunicipioDTO> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

	public LogisticaInfraServiceFacade getLogisticaInfraServiceFacade() {
		return logisticaInfraServiceFacade;
	}

	public void setLogisticaInfraServiceFacade(LogisticaInfraServiceFacade logisticaInfraServiceFacade) {
		this.logisticaInfraServiceFacade = logisticaInfraServiceFacade;
	}

	public PlanService getPlanService() {
		return planService;
	}

	public void setPlanService(PlanService planService) {
		this.planService = planService;
	}

	public List<SolicitudReservAreaDTO> getListaReservaciones() {
		return listaReservaciones;
	}

	public void setListaReservaciones(List<SolicitudReservAreaDTO> listaReservaciones) {
		this.listaReservaciones = listaReservaciones;
	}

	public List<SedeDTO> getSedes() {
		return sedes;
	}

	public void setSedes(List<SedeDTO> sedes) {
		this.sedes = sedes;
	}

	public List<PersonaResponsabilidadesDTO> getCoordinadores() {
		return coordinadores;
	}

	public void setCoordinadores(List<PersonaResponsabilidadesDTO> coordinadores) {
		this.coordinadores = coordinadores;
	}

	public List<PersonaResponsabilidadesDTO> getFacilitadores() {
		return facilitadores;
	}

	public void setFacilitadores(List<PersonaResponsabilidadesDTO> facilitadores) {
		this.facilitadores = facilitadores;
	}

	public List<PersonaResponsabilidadesDTO> getProductores() {
		return productores;
	}

	public void setProductores(List<PersonaResponsabilidadesDTO> productores) {
		this.productores = productores;
	}

	public PersonaResponsabilidadesDTO getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(PersonaResponsabilidadesDTO coordinador) {
		this.coordinador = coordinador;
	}

	public PersonaResponsabilidadesDTO getFacilitador() {
		return facilitador;
	}

	public void setFacilitador(PersonaResponsabilidadesDTO facilitador) {
		this.facilitador = facilitador;
	}

	public PersonaResponsabilidadesDTO getProductor() {
		return productor;
	}

	public void setProductor(PersonaResponsabilidadesDTO productor) {
		this.productor = productor;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public EventoCapacitacionDTO getEvento() {
		return evento;
	}

	public void setEvento(EventoCapacitacionDTO evento) {
		this.evento = evento;
	}

	public List<EventoCapacitacionDTO> getEventosAnteriores() {
		return eventosAnteriores;
	}

	public void setEventosAnteriores(List<EventoCapacitacionDTO> eventosAnteriores) {
		this.eventosAnteriores = eventosAnteriores;
	}

	public List<Curso> getCursosMoodle() {
		return cursosMoodle;
	}

	public void setCursosMoodle(List<Curso> cursosMoodle) {
		this.cursosMoodle = cursosMoodle;
	}

	/* INICIA GETTERS Y SETTERS INFRAESTRUCTURA */

	public SolicitudReservacionDTO getSolRes() {
		return solRes;
	}

	public void setSolRes(SolicitudReservacionDTO solRes) {
		this.solRes = solRes;
	}

	public SolicitudReservAreaDTO getSolicitudSeleccionada() {
		return solicitudSeleccionada;
	}

	public void setSolicitudSeleccionada(SolicitudReservAreaDTO solicitudSeleccionada) {
		this.solicitudSeleccionada = solicitudSeleccionada;
	}

	public List<RelAreaRecursoDTO> getSelTec() {
		return selTec;
	}

	public List<RelAreaRecursoDTO> getSelMobi() {
		return selMobi;
	}

	public void setSelTec(List<RelAreaRecursoDTO> selTec) {
		this.selTec = selTec;
	}

	public void setSelMobi(List<RelAreaRecursoDTO> selMobi) {
		this.selMobi = selMobi;
	}

	public SolicitudReservAreaDTO getSolicitudSeleccionadaEdit() {
		return solicitudSeleccionadaEdit;
	}

	public void setSolicitudSeleccionadaEdit(SolicitudReservAreaDTO solicitudSeleccionadaEdit) {
		this.solicitudSeleccionadaEdit = solicitudSeleccionadaEdit;
	}

	public ConfiguracionAreaDTO getConfiguracionAreaReservacion() {
		return configuracionAreaReservacion;
	}

	public void setConfiguracionAreaReservacion(ConfiguracionAreaDTO configuracionAreaReservacion) {
		this.configuracionAreaReservacion = configuracionAreaReservacion;
	}

	public ConfiguracionAreaDTO getAreaSeleccionada() {
		return areaSeleccionada;
	}

	public void setAreaSeleccionada(ConfiguracionAreaDTO areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	public Date getFechaReservacion() {
		return fechaReservacion;
	}

	public void setFechaReservacion(Date fechaReservacion) {
		this.fechaReservacion = fechaReservacion;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public Integer getTipoAreaSeleccionado() {
		return tipoAreaSeleccionado;
	}

	public void setTipoAreaSeleccionado(Integer tipoAreaSeleccionado) {
		this.tipoAreaSeleccionado = tipoAreaSeleccionado;
	}

	public EventoCapacitacionBean getEventoCapacitacionBean() {
		return eventoCapacitacionBean;
	}

	public void setEventoCapacitacionBean(EventoCapacitacionBean eventoCapacitacionBean) {
		this.eventoCapacitacionBean = eventoCapacitacionBean;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public CorreoElectronicoService getCorreoElectronicoService() {
		return correoElectronicoService;
	}

	public void setCorreoElectronicoService(CorreoElectronicoService correoElectronicoService) {
		this.correoElectronicoService = correoElectronicoService;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public NotificacionSistemaService getNotificacionSistemaService() {
		return notificacionSistemaService;
	}

	public void setNotificacionSistemaService(NotificacionSistemaService notificacionSistemaService) {
		this.notificacionSistemaService = notificacionSistemaService;
	}

	public MensajeOperacionService getMensajeOperacionService() {
		return mensajeOperacionService;
	}

	public void setMensajeOperacionService(MensajeOperacionService mensajeOperacionService) {
		this.mensajeOperacionService = mensajeOperacionService;
	}

	public PersonaCorreoService getPersonaCorreoService() {
		return personaCorreoService;
	}

	public void setPersonaCorreoService(PersonaCorreoService personaCorreoService) {
		this.personaCorreoService = personaCorreoService;
	}

	public CorreoNotificacionBean getCorreoNotificacionBean() {
		return correoNotificacionBean;
	}

	public void setCorreoNotificacionBean(CorreoNotificacionBean correoNotificacionBean) {
		this.correoNotificacionBean = correoNotificacionBean;
	}

	public EncuestaService getEncuestaService() {
		return encuestaService;
	}

	public void setEncuestaService(EncuestaService encuestaService) {
		this.encuestaService = encuestaService;
	}

	public List<EncuestaDTO> getListaEncuestas() {
		return listaEncuestas;
	}

	public void setListaEncuestas(List<EncuestaDTO> listaEncuestas) {
		this.listaEncuestas = listaEncuestas;
	}

	public String getMensajeEncuestas() {
		return mensajeEncuestas;
	}

	public void setMensajeEncuestas(String mensajeEncuestas) {
		this.mensajeEncuestas = mensajeEncuestas;
	}

	public RelEncuestaEventoCapacitacionService getRelEncuestaEventoCapacitacionService() {
		return relEncuestaEventoCapacitacionService;
	}

	public void setRelEncuestaEventoCapacitacionService(
			RelEncuestaEventoCapacitacionService relEncuestaEventoCapacitacionService) {
		this.relEncuestaEventoCapacitacionService = relEncuestaEventoCapacitacionService;
	}

	public GrupoService getGrupoService() {
		return grupoService;
	}

	public void setGrupoService(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
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

	public List<CatalogoComunDTO> getCatTpoComp() {
		return catTpoComp;
	}

	public void setCatTpoComp(List<CatalogoComunDTO> catTpoComp) {
		this.catTpoComp = catTpoComp;
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
	/* FIN GETTERS Y SETTERS INFRAESTRUCTURA */

	public Integer getIdCapacitacion() {
		return idCapacitacion;
	}

	public void setIdCapacitacion(Integer idCapacitacion) {
		this.idCapacitacion = idCapacitacion;
	}

	public Boolean getAutonomo() {
		return autonomo;
	}

	public void setAutonomo(Boolean autonomo) {
		this.autonomo = autonomo;
	}
	public MallaCurricularService getMallaCurricularService() {
		return mallaCurricularService;
	}

	public void setMallaCurricularService(MallaCurricularService mallaCurricularService) {
		this.mallaCurricularService = mallaCurricularService;
	}

	public List<Integer> getIdsEstructura() {
		return idsEstructura;
	}

	public void setIdsEstructura(List<Integer> idsEstructura) {
		this.idsEstructura = idsEstructura;
	}

	public String getIdCatEstructura() {
		return idCatEstructura;
	}

	public void setIdCatEstructura(String idCatEstructura) {
		this.idCatEstructura = idCatEstructura;
	}

	public String getIdCatSubEstructuraNivel1() {
		return idCatSubEstructuraNivel1;
	}

	public void setIdCatSubEstructuraNivel1(String idCatSubEstructuraNivel1) {
		this.idCatSubEstructuraNivel1 = idCatSubEstructuraNivel1;
	}

	public String getIdCatSubEstructuraNivel2() {
		return idCatSubEstructuraNivel2;
	}

	public void setIdCatSubEstructuraNivel2(String idCatSubEstructuraNivel2) {
		this.idCatSubEstructuraNivel2 = idCatSubEstructuraNivel2;
	}

	public String getIdCatSubEstructuraNivel3() {
		return idCatSubEstructuraNivel3;
	}

	public void setIdCatSubEstructuraNivel3(String idCatSubEstructuraNivel3) {
		this.idCatSubEstructuraNivel3 = idCatSubEstructuraNivel3;
	}
}
