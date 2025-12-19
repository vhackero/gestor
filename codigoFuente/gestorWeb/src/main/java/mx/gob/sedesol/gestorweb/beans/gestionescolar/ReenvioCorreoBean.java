package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstatusEnvioCorreoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.FiltroReenvioCorreoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReenvioCorreoMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.CatProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ConvocatoriaDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.PlanProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ProgramaProcesoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.ReenvioCorreoInscripcionDTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConvocatoriaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionFacade;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionesService;
import mx.gob.sedesol.basegestor.service.inscripcion.CatProcesoInscripcionService;
import mx.gob.sedesol.basegestor.service.inscripcion.EnvioCorreoService;
import mx.gob.sedesol.basegestor.service.inscripcion.PlanProcesoInscripcionService;
import mx.gob.sedesol.basegestor.service.inscripcion.ProcesoInscripcionService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class ReenvioCorreoBean extends BaseBean {
	private static final long serialVersionUID = 5368879650855319808L;
	private static final Logger logger = Logger.getLogger(ReenvioCorreoBean.class);

	@ManagedProperty(value = "#{convocatoriaService}")
	private ConvocatoriaService convocatoriaService;

	@ManagedProperty(value = "#{catProcesoInscripcionService}")
	private CatProcesoInscripcionService catProcesoInscripcionService;

	@ManagedProperty(value = "#{procesoInscripcionService}")
	private ProcesoInscripcionService procesoInscripcionService;

	@ManagedProperty(value = "#{planProcesoInscripcionService}")
	private PlanProcesoInscripcionService planProcesoInscripcionService;

	@ManagedProperty(value = "#{inscripcionesService}")
	private InscripcionesService inscripcionesService;

	@ManagedProperty(value = "#{inscripcionFacade}")
	private InscripcionFacade inscripcionFacade;

	@ManagedProperty(value = "#{envioCorreoService}")
	private EnvioCorreoService envioCorreoService;

	@ManagedProperty(value = "#{inscripcionService}")
	private InscripcionService inscripcionService;

	private FiltroReenvioCorreoInscripcionDTO filtroBusqueda;
	private List<ConvocatoriaDTO> convocatorias;
	private List<CatProcesoInscripcionDTO> tiposProcesoInscripcion;
	private List<ProcesoInscripcionDTO> procesosInscripcion;
	private List<PlanProcesoInscripcionDTO> planes;
	private List<ProgramaProcesoInscripcionDTO> programas;
	private List<EstatusEnvioCorreoInscripcionDTO> estatusEnvioCorreo;
	private LazyDataModel<ReenvioCorreoInscripcionDTO> inscripciones;
	private ReenvioCorreoInscripcionDTO inscripcionSeleccionada;
	private List<ReenvioCorreoMateriasDTO> materiasEstudianteSeleccionado;

	private String mensajeDialog;
	private Integer panelActivo;// 1 -> Busqueda, 2 -> Materias
	private String nombrePlan;

	@PostConstruct
	public void inicializarBean() {
		inicializarPanelActivo();
		limpiarFiltroBusqueda();
		cargarConvocatorias();
		cargarTiposProcesoInscripcion();
		cargarEstatusEnvio();
	}

	private void inicializarPanelActivo() {
		mostrarPanelBusquedaInscripcion();
	}

	public void mostrarPanelBusquedaInscripcion() {
		panelActivo = ConstantesGestor.PANEL_BUSQUEDA_INSCRIPCION_REENVIO_CORREO;
	}

	private void mostrarPanelMateriasEstudianteSeleccionado() {
		panelActivo = ConstantesGestor.PANEL_MATERIAS_REENVIO_CORREO;
	}

	public void limpiarFiltroBusqueda() {
		filtroBusqueda = new FiltroReenvioCorreoInscripcionDTO();
	}

	private void cargarConvocatorias() {
		this.convocatorias = convocatoriaService.obtenerConvocatoriasActivas();
	}

	private void cargarTiposProcesoInscripcion() {
		this.tiposProcesoInscripcion = catProcesoInscripcionService.obtenerTiposProcesoInscripcion();
	}

	private void cargarEstatusEnvio() {
		estatusEnvioCorreo = crearCatalogoEstatusEnvioCorreo();
	}

	private List<EstatusEnvioCorreoInscripcionDTO> crearCatalogoEstatusEnvioCorreo() {
		List<EstatusEnvioCorreoInscripcionDTO> estatusEnvioCorreo = new ArrayList<>();
		estatusEnvioCorreo.add(new EstatusEnvioCorreoInscripcionDTO(ConstantesGestor.SI_SE_ENVIO_CORREO,
				ConstantesGestor.TEXTO_CORREO_SI_ENVIADO_INSCRIPCION));
		estatusEnvioCorreo.add(new EstatusEnvioCorreoInscripcionDTO(ConstantesGestor.NO_SE_ENVIO_CORREO,
				ConstantesGestor.TEXTO_CORREO_NO_ENVIADO_INSCRIPCION));
		return estatusEnvioCorreo;
	}

	private void cargarInscripcionesPaginadas() {
		inscripciones = crearInscripcionesPaginadas();
	}

	private LazyDataModel<ReenvioCorreoInscripcionDTO> crearInscripcionesPaginadas() {
		return new LazyDataModel<ReenvioCorreoInscripcionDTO>() {
			@Override
			public List<ReenvioCorreoInscripcionDTO> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				return crearPagina(first, pageSize);
			}

			private List<ReenvioCorreoInscripcionDTO> crearPagina(int first, int pageSize) {
				List<ReenvioCorreoInscripcionDTO> pagina = buscarInscripcionesPaginadas(first, pageSize);
				long total = contarTotalInscripcionesParaPaginacion();
				this.setRowCount((int) total);
				return pagina;
			}
		};
	}

	private Long contarTotalInscripcionesParaPaginacion() {
		return inscripcionesService.contarInscripcionesParaReenvio(filtroBusqueda);
	}

	private List<ReenvioCorreoInscripcionDTO> buscarInscripcionesPaginadas(int first, int pageSize) {
		logger.info("Busqueda realizada: " + filtroBusqueda);
		return inscripcionesService.obtenerInscripcionesParaReenvioConPaginacion(filtroBusqueda, first, pageSize);
	}

	public void reenviarCorreoInscripcion() {
		if (panelActivo == ConstantesGestor.PANEL_BUSQUEDA_INSCRIPCION_REENVIO_CORREO) {
			reenviarCorreoInscripcionDesdePanelBusqueda();
		} else {
			reenviarCorreoInscripcionDesdePanelMaterias();
		}
	}

	public void reenviarCorreoInscripcionDesdePanelBusqueda() {
		try {
			boolean enviado = intentarReenviarCorreoInscripcion(inscripcionSeleccionada);
			mostrarMensajeCorrespondiente(enviado);
		} catch (Exception e) {
			gestionarErrorAlReenviarCorreo(e);
		}
	}

	public void reenviarCorreoInscripcionDesdePanelMaterias() {
		try {
			boolean enviado = intentarReenviarCorreoInscripcion(inscripcionSeleccionada,
					materiasEstudianteSeleccionado);
			mostrarMensajeCorrespondiente(enviado);
		} catch (Exception e) {
			gestionarErrorAlReenviarCorreo(e);
		}
	}

	private void gestionarErrorAlReenviarCorreo(Exception e) {
		logger.error("Ha ocurrido un error al intentar reenviar el correo: ", e);
		mostrarMensajeErrorReenvio();
	}

	private void mostrarMensajeCorrespondiente(boolean enviado) {
		if (enviado) {
			mostrarMensajeDialog("Correo enviado satisfactoriamente");
		} else {
			mostrarMensajeErrorReenvio();
		}
	}

	private boolean intentarReenviarCorreoInscripcion(ReenvioCorreoInscripcionDTO inscripcion) {
		return inscripcionFacade.intentarReenviarCorreoInscripcion(inscripcion);
	}

	private boolean intentarReenviarCorreoInscripcion(ReenvioCorreoInscripcionDTO inscripcion,
			List<ReenvioCorreoMateriasDTO> materiasEstudianteSeleccionado) {
		return inscripcionFacade.intentarReenviarCorreoInscripcion(inscripcion, materiasEstudianteSeleccionado);
	}

	private void mostrarMensajeErrorReenvio() {
		mostrarMensajeDialog("Ocurrió un error al intentar enviar el correo");
	}

	private void mostrarMensajeDialog(String mensaje) {
		this.mensajeDialog = mensaje;
		RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show()");
	}

	public void onConvocatoriaChange() {
		cargarProcesosInscripcion();
	}

	public void onTipoProcesoChange() {
		cargarProcesosInscripcion();
	}

	private void cargarProcesosInscripcion() {
		if (faltaSeleccionarConvocatoriaOTipoDeProceso()) {
			limpiarProcesosInscripcion();
			limpiarPlanes();
			limpiarProgramas();
			return;
		}
		Long idConvocatoria = obtenerIdConvocatoriaSeleccionada();
		Long idTipoProceso = obtenerIdTipoProcesoSeleccionado();
		this.procesosInscripcion = obtenerProcesosInscripcion(idConvocatoria, idTipoProceso);
	}

	private boolean faltaSeleccionarConvocatoriaOTipoDeProceso() {
		return obtenerIdConvocatoriaSeleccionada() == null || obtenerIdTipoProcesoSeleccionado() == null;
	}

	private void limpiarProcesosInscripcion() {
		this.filtroBusqueda.setIdProcesoInscripcion(null);
		this.procesosInscripcion = new ArrayList<>();
	}

	private void limpiarPlanes() {
		this.filtroBusqueda.setIdPlan(null);
		this.planes = new ArrayList<>();
	}

	private void limpiarProgramas() {
		this.filtroBusqueda.setIdPrograma(null);
		this.programas = new ArrayList<>();

	}

	private Long obtenerIdConvocatoriaSeleccionada() {
		return filtroBusqueda.getIdConvocatoria();
	}

	private Long obtenerIdTipoProcesoSeleccionado() {
		return filtroBusqueda.getIdTipoProceso();
	}

	private List<ProcesoInscripcionDTO> obtenerProcesosInscripcion(Long idConvocatoria, Long idTipoProcesoInscripcion) {
		return procesoInscripcionService.obtenerPorConvocatoriaYTipoProceso(idConvocatoria, idTipoProcesoInscripcion);
	}

	public void onProcesoInscripcionChange() {
		limpiarPlanes();
		limpiarProgramas();
		cargarPlanes();
	}

	private void cargarPlanes() {
		Long idProcesoInscripcion = obtenerIdProcesoInscripcionSeleccionado();
		this.planes = obtenerPlanesPorProcesoInscripcion(idProcesoInscripcion);
	}

	private Long obtenerIdProcesoInscripcionSeleccionado() {
		return filtroBusqueda.getIdProcesoInscripcion();
	}

	private List<PlanProcesoInscripcionDTO> obtenerPlanesPorProcesoInscripcion(Long idProcesoInscripcion) {
		return planProcesoInscripcionService.obtenerPlanesPorIdProcesoInscripcion(idProcesoInscripcion);
	}

	public void onPlanChange() {
		if (esSeleccionPlanNulo()) {
			limpiarProgramas();
			return;
		}
		cargarProgramas();
	}

	private boolean esSeleccionPlanNulo() {
		return filtroBusqueda.getIdPlan() == null;
	}

	private void cargarProgramas() {
		Long idPlan = obtenerIdPlanSeleccionado();
		Long idProcesoInscripcion = obtenerIdProcesoInscripcionSeleccionado();
		this.programas = obtenerProgramasPorPlanYProcesoInscripcion(idPlan, idProcesoInscripcion);
	}

	private List<ProgramaProcesoInscripcionDTO> obtenerProgramasPorPlanYProcesoInscripcion(Long idPlan,
			Long idProcesoInscripcion) {
		return planProcesoInscripcionService.obtenerProgramasPorPlanYProcesoInscripcion(idPlan, idProcesoInscripcion);
	}

	private Long obtenerIdPlanSeleccionado() {
		return filtroBusqueda.getIdPlan();
	}

	public void buscarUsuarios() {
		cargarInscripcionesPaginadas();
	}

	public void mostrarMateriasEstudianteSeleccionado() {
		cargarMateriasEstudianteSeleccionado(inscripcionSeleccionada);
		cargarPlanEstudianteSeleccionado(inscripcionSeleccionada);
		mostrarPanelMateriasEstudianteSeleccionado();
	}

	private void cargarPlanEstudianteSeleccionado(ReenvioCorreoInscripcionDTO inscripcionSeleccionada) {
		InscripcionPersonaDTO inscripcionPersona = inscripcionService
				.obtenerInscripcionPorPersona(String.valueOf(inscripcionSeleccionada.getIdPersona()));
		nombrePlan = inscripcionPersona.getPlan();

	}

	private void cargarMateriasEstudianteSeleccionado(ReenvioCorreoInscripcionDTO inscripcion) {
		materiasEstudianteSeleccionado = envioCorreoService.obtenerMateriasParaReenvioCorreo(inscripcion.getIdPersona(),
				inscripcion.getIdProcesoInscripcion());
	}

	public FiltroReenvioCorreoInscripcionDTO getFiltroBusqueda() {
		return filtroBusqueda;
	}

	public void setFiltroBusqueda(FiltroReenvioCorreoInscripcionDTO filtroBusqueda) {
		this.filtroBusqueda = filtroBusqueda;
	}

	public List<ConvocatoriaDTO> getConvocatorias() {
		return convocatorias;
	}

	public void setConvocatorias(List<ConvocatoriaDTO> convocatorias) {
		this.convocatorias = convocatorias;
	}

	public ConvocatoriaService getConvocatoriaService() {
		return convocatoriaService;
	}

	public void setConvocatoriaService(ConvocatoriaService convocatoriaService) {
		this.convocatoriaService = convocatoriaService;
	}

	public List<CatProcesoInscripcionDTO> getTiposProcesoInscripcion() {
		return tiposProcesoInscripcion;
	}

	public void setTiposProcesoInscripcion(List<CatProcesoInscripcionDTO> tiposProcesoInscripcion) {
		this.tiposProcesoInscripcion = tiposProcesoInscripcion;
	}

	public List<ProcesoInscripcionDTO> getProcesosInscripcion() {
		return procesosInscripcion;
	}

	public void setProcesosInscripcion(List<ProcesoInscripcionDTO> procesosInscripcion) {
		this.procesosInscripcion = procesosInscripcion;
	}

	public List<PlanProcesoInscripcionDTO> getPlanes() {
		return planes;
	}

	public void setPlanes(List<PlanProcesoInscripcionDTO> planes) {
		this.planes = planes;
	}

	public List<ProgramaProcesoInscripcionDTO> getProgramas() {
		return programas;
	}

	public void setProgramas(List<ProgramaProcesoInscripcionDTO> programas) {
		this.programas = programas;
	}

	public CatProcesoInscripcionService getCatProcesoInscripcionService() {
		return catProcesoInscripcionService;
	}

	public void setCatProcesoInscripcionService(CatProcesoInscripcionService catProcesoInscripcionService) {
		this.catProcesoInscripcionService = catProcesoInscripcionService;
	}

	public ProcesoInscripcionService getProcesoInscripcionService() {
		return procesoInscripcionService;
	}

	public void setProcesoInscripcionService(ProcesoInscripcionService procesoInscripcionService) {
		this.procesoInscripcionService = procesoInscripcionService;
	}

	public PlanProcesoInscripcionService getPlanProcesoInscripcionService() {
		return planProcesoInscripcionService;
	}

	public void setPlanProcesoInscripcionService(PlanProcesoInscripcionService planProcesoInscripcionService) {
		this.planProcesoInscripcionService = planProcesoInscripcionService;
	}

	public LazyDataModel<ReenvioCorreoInscripcionDTO> getInscripciones() {
		return inscripciones;
	}

	public void setInscripciones(LazyDataModel<ReenvioCorreoInscripcionDTO> inscripciones) {
		this.inscripciones = inscripciones;
	}

	public InscripcionesService getInscripcionesService() {
		return inscripcionesService;
	}

	public void setInscripcionesService(InscripcionesService inscripcionesService) {
		this.inscripcionesService = inscripcionesService;
	}

	public List<EstatusEnvioCorreoInscripcionDTO> getEstatusEnvioCorreo() {
		return estatusEnvioCorreo;
	}

	public void setEstatusEnvioCorreo(List<EstatusEnvioCorreoInscripcionDTO> estatusEnvioCorreo) {
		this.estatusEnvioCorreo = estatusEnvioCorreo;
	}

	public InscripcionFacade getInscripcionFacade() {
		return inscripcionFacade;
	}

	public void setInscripcionFacade(InscripcionFacade inscripcionFacade) {
		this.inscripcionFacade = inscripcionFacade;
	}

	public String getMensajeDialog() {
		return mensajeDialog;
	}

	public void setMensajeDialog(String mensajeDialog) {
		this.mensajeDialog = mensajeDialog;
	}

	public ReenvioCorreoInscripcionDTO getInscripcionSeleccionada() {
		return inscripcionSeleccionada;
	}

	public void setInscripcionSeleccionada(ReenvioCorreoInscripcionDTO inscripcionSeleccionada) {
		this.inscripcionSeleccionada = inscripcionSeleccionada;
	}

	public Integer getPanelActivo() {
		return panelActivo;
	}

	public void setPanelActivo(Integer panelActivo) {
		this.panelActivo = panelActivo;
	}

	public List<ReenvioCorreoMateriasDTO> getMateriasEstudianteSeleccionado() {
		return materiasEstudianteSeleccionado;
	}

	public void setMateriasEstudianteSeleccionado(List<ReenvioCorreoMateriasDTO> materiasEstudianteSeleccionado) {
		this.materiasEstudianteSeleccionado = materiasEstudianteSeleccionado;
	}

	public EnvioCorreoService getEnvioCorreoService() {
		return envioCorreoService;
	}

	public void setEnvioCorreoService(EnvioCorreoService envioCorreoService) {
		this.envioCorreoService = envioCorreoService;
	}

	public InscripcionService getInscripcionService() {
		return inscripcionService;
	}

	public void setInscripcionService(InscripcionService inscripcionService) {
		this.inscripcionService = inscripcionService;
	}

	public String getNombrePlan() {
		return nombrePlan;
	}

	public void setNombrePlan(String nombrePlan) {
		this.nombrePlan = nombrePlan;
	}

}
