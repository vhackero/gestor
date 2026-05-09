package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EstatusDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CrearEventoDispersionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionCreacionResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionMatriculacionResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionMatriculaExistenteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MatricularDispersionDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionPreEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParamNuevo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersionesBusqueda;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConvocatoriaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.DispersionesService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionesService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

@ManagedBean
@ViewScoped
public class DispersionesBean extends BaseBean {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5929433407465074144L;
	private static final Logger logger = Logger.getLogger(DispersionesBean.class);
	
	private static final int TIPO_PROCESO_ORDINARIO = 1;
	private static final int TIPO_PROCESO_EXTRAORDINARIO = 2;
	private static final int CLASIFICACION_AVA_NUEVO = 3;
	private static final int LIMITE_ANIOS_PERIODO = 9;
	private static final int MAX_VALOR_LISTAS = 500;
	private static final String MENSAJE_SIN_PROCESOS_MATRICULACION = "No hay procesos de inscripción disponibles.";
	private static final String MENSAJE_SIN_DATOS = "No se encontraron datos";

	@ManagedProperty("#{dispersionesService}")
	private DispersionesService dispersionesService;

	@ManagedProperty("#{convocatoriaService}")
	private ConvocatoriaService convocatoriaService;

	@ManagedProperty("#{inscripcionesService}")
	private InscripcionesService inscripcionesService;
	
	@ManagedProperty("#{eventoCapacitacionServiceFacade}")
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	////////////
	private List<EstatusDTO> estatusLista;
	List<Convocatoria> listaConvocatoria;
	private List<String> listaGrupoGeneral;
	private List<String> listaCupoGeneral;
	private List<String> listaGrupoRestante;
	private List<String> listaCupoRestanre;
	private List<InscripcionPlanesProgramas> listaPlanProgramas;
	private List<InscripcionPlanesProgramas> planesProgramasSeleccionados;
	private List<InscripcionPlanesProgramas> planesProgramasBusquedaSeleccionados;
	List<TipoProceso> listaTipoProceso;
	List<ProcesosInscripcion> listaProcesosInscripcion;
	private List<ProcesosInscripcion> listaProcesosConDispersion;
	private List<ProcesosInscripcion> listaProcesosSinDispersion;
	private List<String> listaProgramasCompartidos;
	private String mensajeProcesosConDispersion;
	private String mensajeProcesosSinDispersion;
	private String mensajeProgramasCompartidos;
	List<TipoMatriculacion> listaTipoMatriculacion;
	List<TblPlan> listaPlanes;
	List<TblFichaDescriptivaPrograma> listaPrograma;
	DispersionesParam dispercionParametros;
	DispersionesParamNuevo dispersionNuevo;


	List<TblDispersionesBusqueda> listaDispercionBusqueda = new ArrayList<TblDispersionesBusqueda>();
	private List<DispersionMatriculaExistenteDTO> listaDispersionesExistentes = new ArrayList<>();
	private DispersionMatriculaExistenteDTO dispersionExistenteSeleccionada;

	TblDispersionesBusqueda elminarDispersion = new TblDispersionesBusqueda();
	
	TblDispersionesBusqueda editarDispersion = new TblDispersionesBusqueda();
	
	private String mensajeGestion;
	private String mensajeTablaDispersionExistente;
	private String mensajeTablaDispersionGruposCompartidos;

	// REDIRECCION OPCIONES
	private String paginaActual;

	private boolean mostrarPlanYPrograma = false;
	private boolean ultimaConsultaExtraordinaria = false;
	
	private boolean mostrarConsultaDispersion = true;
	private boolean mostrarNuevaDispersion = false;
	private boolean mostrarDefinicionGruposCompartidos = false;
	private Integer idProgramaPlanSeleccionado;
	
	private boolean mostrarFormularioCrearGrupos = false;
	private boolean crearGruposDesdeCompartidos = false;
	private TblDispersionesBusqueda dispersionSeleccionada;
	private DispersionPreEvento datosPreviosCrear;
	private CrearEventoGrupoForm formularioCrearEvento;
	private List<CatalogoComunDTO> listaEstatusEvento;
	private List<CatalogoComunDTO> listaModalidadesEvento;
	private List<String> listaNumeroElementos;
	private List<String> listaAnioPeriodo;
	private List<ParametroWSMoodleDTO> listaPlataformasMoodle;
	private List<CatalogoComunDTO> listaClasificacionesAva;

	@PostConstruct
	public void init() {
		dispercionParametros = new DispersionesParam(); // Inicializar el objeto
		listaProcesosInscripcion = new ArrayList<>();
		listaProcesosConDispersion = new ArrayList<>();
		listaProcesosSinDispersion = new ArrayList<>();
		listaProgramasCompartidos = new ArrayList<>();
		listaPlanes = new ArrayList<>();
		listaPrograma = new ArrayList<>();
		listaPlanProgramas = new ArrayList<>();
		planesProgramasSeleccionados = new ArrayList<>();
		planesProgramasBusquedaSeleccionados = new ArrayList<>();
		idProgramaPlanSeleccionado = null;
		inicializarListasEdicion();
		cargarCatalogosCrearGrupos();
		mensajeTablaDispersionExistente = MENSAJE_SIN_PROCESOS_MATRICULACION;
		mensajeTablaDispersionGruposCompartidos = MENSAJE_SIN_DATOS;
	}

	public DispersionesBean() {

		estatusLista = new ArrayList<>();
		// Crear los objetos EstatusDTO
		EstatusDTO activo = new EstatusDTO(1, "ACTIVO");
		EstatusDTO inactivo = new EstatusDTO(0, "INACTIVO");
		estatusLista.add(activo);
		estatusLista.add(inactivo);

	}

	//////////////////////////////////

	public String navegaNuevoDispersion() throws Exception {

		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/nuevaDispersion.xhtml";

		limpiarCampos();

		consultarConvocatorias();
		consultaTipoProceso();
		// consultarProcesoInscripcion();
		consultarTipoMatriculacion();

		return null;

	}

	public String navegaConsultaDispersion() throws Exception {

		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/consultaDispersion.xhtml";

		limpiarCampos();

		consultarConvocatorias();
		consultaTipoProceso();
		// consultarProcesoInscripcion();
		consultarTipoMatriculacion();

		return null;

	}
	
	public String navegaDispersion() throws Exception {
		
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/dispersion.xhtml";
		
		limpiarCampos();
		
		consultarConvocatorias();
		consultaTipoProceso();
		consultarTipoMatriculacion();
		
		return null;
	}
	
	public String navegaMatriculaDispersionExistente() throws Exception {
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/matriculaConDispersionExistente.xhtml";
		limpiarCampos();
		consultarConvocatorias();
		consultaTipoProceso();
		consultarTipoMatriculacion();
		cargarProcesosMatriculaExistente();
		return null;
	}

	public String navegaDispersionGruposCompartidos() throws Exception {
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/dispersionGruposCompartidos.xhtml";
		limpiarCamposDispersionGruposCompartidos();
		cargarProcesosCompartidosConDispersion();
		return null;
	}

	public void cancelar() throws Exception {
		this.paginaActual = "";

		dispercionParametros = new DispersionesParam();

		consultarConvocatorias();
		consultaTipoProceso();
		// consultarProcesoInscripcion();
		consultarTipoMatriculacion();
		this.mostrarConsultaDispersion = true;
		this.mostrarNuevaDispersion = false;
		limpiarCampos();
	}
	
	private void cargarProcesosMatriculaExistente() {
		listaProcesosConDispersion = dispersionesService.consultarProcesosConDispersion();
		listaProcesosSinDispersion = dispersionesService.consultarProcesosSinDispersion();
		mensajeProcesosConDispersion = (listaProcesosConDispersion == null || listaProcesosConDispersion.isEmpty())
				? "No se encontraron procesos de dispersión creados" : null;
		mensajeProcesosSinDispersion = (listaProcesosSinDispersion == null || listaProcesosSinDispersion.isEmpty())
				? "No se encontraron procesos de inscripción sin dispersiones" : null;
	}

	private void cargarProcesosCompartidosConDispersion() {
		listaProcesosConDispersion = dispersionesService.consultarProcesosConDispersion();
		mensajeProcesosConDispersion = (listaProcesosConDispersion == null || listaProcesosConDispersion.isEmpty())
				? "No se encontraron procesos de dispersión creados" : null;
	}

	public void limpiarCampos() {
		dispercionParametros = new DispersionesParam();
		listaProcesosInscripcion = new ArrayList<>();
		listaProcesosConDispersion = new ArrayList<>();
		listaProcesosSinDispersion = new ArrayList<>();
		listaProgramasCompartidos = new ArrayList<>();
		mensajeProcesosConDispersion = null;
		mensajeProcesosSinDispersion = null;
		mensajeProgramasCompartidos = null;
		mensajeTablaDispersionExistente = MENSAJE_SIN_PROCESOS_MATRICULACION;
		mensajeTablaDispersionGruposCompartidos = MENSAJE_SIN_DATOS;
		listaPlanes = new ArrayList<>();
		listaPrograma = new ArrayList<>();
		listaPlanProgramas = new ArrayList<>();
		planesProgramasSeleccionados = new ArrayList<>();
		planesProgramasBusquedaSeleccionados = new ArrayList<>();
		listaDispercionBusqueda = new ArrayList<>();
		listaDispersionesExistentes = new ArrayList<>();
		mostrarPlanYPrograma = false;
		idProgramaPlanSeleccionado = null;
		mostrarFormularioCrearGrupos = false;
		crearGruposDesdeCompartidos = false;
		dispersionSeleccionada = null;
		formularioCrearEvento = null;
		datosPreviosCrear = null;
		mostrarConsultaDispersion = true;
		mostrarNuevaDispersion = false;
		mostrarDefinicionGruposCompartidos = false;
		editarDispersion = new TblDispersionesBusqueda();
		dispersionNuevo = null;
		ultimaConsultaExtraordinaria = false;
		cargarProcesosMatriculaExistente();

	}

	public void limpiarCamposDispersionGruposCompartidos() {
		dispercionParametros = new DispersionesParam();
		listaProgramasCompartidos = new ArrayList<>();
		listaDispercionBusqueda = new ArrayList<>();
		mensajeProgramasCompartidos = null;
		mensajeTablaDispersionGruposCompartidos = MENSAJE_SIN_DATOS;
		mostrarDefinicionGruposCompartidos = false;
		editarDispersion = new TblDispersionesBusqueda();
		dispersionNuevo = null;
	}
	
	public void onConvocatoriaChange() {
		dispercionParametros.setIdTipoProceso(null);
		dispercionParametros.setIdProcesoInscripcion(null);
		listaProcesosInscripcion = new ArrayList<>();
		ocultarPlanPrograma();
	}
	
	public void actualizarDispersion () {
		
		if (editarDispersion == null || editarDispersion.getIdDispersion() == null) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion5').show()");
			return;
		}
		
		dispersionNuevo = new DispersionesParamNuevo();
		dispersionNuevo.setIdDispersion(editarDispersion.getIdDispersion());
		dispersionNuevo.setNoGrupos(editarDispersion.getGruposGenerales());
		dispersionNuevo.setEstudiantesGrupo(editarDispersion.getCupoGeneral());
		dispersionNuevo.setGrupoResto(editarDispersion.getGrupoResto());
		dispersionNuevo.setCupoResto(editarDispersion.getCupoResto());
		
			asegurarValorEnLista(listaGrupoGeneral, dispersionNuevo.getNoGrupos());
			asegurarValorEnLista(listaCupoGeneral, dispersionNuevo.getEstudiantesGrupo());
			asegurarValorEnLista(listaGrupoRestante, dispersionNuevo.getGrupoResto());
			asegurarValorEnLista(listaCupoRestanre, dispersionNuevo.getCupoResto());
			idProgramaPlanSeleccionado = null;
			
			this.mostrarConsultaDispersion = false;
			this.mostrarNuevaDispersion = true;
		}
	
	public void actualizaDispersionDB() {///////////////////
		
		Integer sumTotal;
		
		if (dispersionNuevo == null || dispersionNuevo.getCupoResto() == null || dispersionNuevo.getEstudiantesGrupo() == null ||
				dispersionNuevo.getGrupoResto() == null || dispersionNuevo.getNoGrupos() == null) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion8').show()");
		} else {
			List<TblDispersionesBusqueda> lista = dispersionesService.actualizarDispersion(dispersionNuevo);
			if (lista.isEmpty()) {
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion6').show()");
			} else {
				
				sumTotal = (dispersionNuevo.getNoGrupos() * dispersionNuevo.getEstudiantesGrupo())
						+ (dispersionNuevo.getGrupoResto() * dispersionNuevo.getCupoResto());
				Integer totalRegistrado = lista.get(0).getNoEstudiantes();
				
				logger.info(String.format(
						"Validando dispersión (id=%d) con noGrupos=%d, cupoGeneral=%d, gruposResto=%d, cupoResto=%d, sumaCalculada=%d, estudiantesRegistrados=%d",
						dispersionNuevo.getIdDispersion(),
						dispersionNuevo.getNoGrupos(),
						dispersionNuevo.getEstudiantesGrupo(),
						dispersionNuevo.getGrupoResto(),
						dispersionNuevo.getCupoResto(),
						sumTotal,
						totalRegistrado));
				
				if (sumTotal != null && sumTotal.equals(totalRegistrado)) {
					boolean actualizada = dispersionesService.actualizarDispersionExc(dispersionNuevo);
					if (actualizada) {
						RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion7').show()");
						busquedaDispersion();
						this.mostrarConsultaDispersion = true;
						this.mostrarNuevaDispersion = false;
					} else {
						logger.warn(String.format("No se pudo actualizar la dispersión con id=%d en base de datos.",
								dispersionNuevo.getIdDispersion()));
						RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccionErrorUpdate').show()");
					}
				} else {
					RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccionSuma').show()");
				}
			}
		}
	}

	public void busquedaDispersion() {

		logger.info("***********************Inicio Consulta dispercion***********************");

		if (!filtrosBusquedaCompletos()) {
			List<String> faltantes = obtenerCamposFaltantes();
			String descripcion = faltantes.isEmpty() ? "Complete los filtros obligatorios."
					: "Faltan por seleccionar: " + String.join(", ", faltantes);
			mostrarMensajeDispersion(true, descripcion);
			return;
		}
		
		ultimaConsultaExtraordinaria = esProcesoExtraordinarioSeleccionado();
		listaDispercionBusqueda = consultarDispersionesConFiltros();
		actualizarAccionesTablero();
		if (listaDispercionBusqueda.isEmpty()) {
			mostrarMensajeDispersion(false, "No se encontraron dispersiones");
		}

	}
	
	public void busquedaDispersionExistente() {
		listaDispersionesExistentes = new ArrayList<>();
		mensajeTablaDispersionExistente = MENSAJE_SIN_PROCESOS_MATRICULACION;
		if (dispercionParametros.getIdProcesoInscripcionConDispersion() == null
				|| dispercionParametros.getIdProcesoInscripcionMatricular() == null) {
			mostrarMensajeDispersion(true, "Seleccione ambos procesos de inscripción para continuar.");
			return;
		}
		listaDispersionesExistentes = dispersionesService
				.consultarDispersionesExistentes(dispercionParametros.getIdProcesoInscripcionConDispersion(),
						dispercionParametros.getIdProcesoInscripcionMatricular());
		if (listaDispersionesExistentes == null) {
			listaDispersionesExistentes = new ArrayList<>();
		}
		if (listaDispersionesExistentes.isEmpty()) {
			return;
		} else {
			mensajeTablaDispersionExistente = MENSAJE_SIN_PROCESOS_MATRICULACION;
			mostrarMensajeDispersion(false, null);
		}
	}

	public void onProcesoCompartidoSeleccionado() {
		dispercionParametros.setNombreProgramaCompartidoSeleccionado(null);
		listaProgramasCompartidos = new ArrayList<>();
		listaDispercionBusqueda = new ArrayList<>();
		mensajeTablaDispersionGruposCompartidos = MENSAJE_SIN_DATOS;
		mensajeProgramasCompartidos = null;
		if (dispercionParametros.getIdProcesoInscripcionConDispersion() == null) {
			return;
		}
		listaProgramasCompartidos = dispersionesService
				.consultarProgramasCompartidos(dispercionParametros.getIdProcesoInscripcionConDispersion());
		mensajeProgramasCompartidos = (listaProgramasCompartidos == null || listaProgramasCompartidos.isEmpty())
				? "No se encontraron programas compartidos"
				: null;
	}

	public void busquedaDispersionGruposCompartidos() {
		listaDispercionBusqueda = new ArrayList<>();
		mensajeTablaDispersionGruposCompartidos = MENSAJE_SIN_DATOS;
		mostrarDefinicionGruposCompartidos = false;
		if (dispercionParametros.getIdProcesoInscripcionConDispersion() == null
				|| dispercionParametros.getNombreProgramaCompartidoSeleccionado() == null
				|| dispercionParametros.getNombreProgramaCompartidoSeleccionado().trim().isEmpty()) {
			mostrarMensajeDispersion(true,
					"Seleccione el proceso de inscripción con dispersión y el programa compartido para continuar.");
			return;
		}
		listaDispercionBusqueda = dispersionesService.consultarDispersionesGruposCompartidos(
				dispercionParametros.getIdProcesoInscripcionConDispersion(),
				dispercionParametros.getNombreProgramaCompartidoSeleccionado());
		actualizarAccionesGruposCompartidos();
	}

	public void definirGruposCompartidos(TblDispersionesBusqueda dispersion) {
		if (dispersion == null || dispersion.getIdDispersion() == null) {
			mostrarMensajeDispersion(true, "Seleccione un registro válido para definir los grupos.");
			return;
		}
		editarDispersion = dispersion;
		dispersionNuevo = new DispersionesParamNuevo();
		dispersionNuevo.setIdDispersion(dispersion.getIdDispersion());
		dispersionNuevo.setNoGrupos(dispersion.getGruposGenerales());
		dispersionNuevo.setEstudiantesGrupo(dispersion.getCupoGeneral());
		dispersionNuevo.setGrupoResto(dispersion.getGrupoResto());
		dispersionNuevo.setCupoResto(dispersion.getCupoResto());
		asegurarValorEnLista(listaGrupoGeneral, dispersionNuevo.getNoGrupos());
		asegurarValorEnLista(listaCupoGeneral, dispersionNuevo.getEstudiantesGrupo());
		asegurarValorEnLista(listaGrupoRestante, dispersionNuevo.getGrupoResto());
		asegurarValorEnLista(listaCupoRestanre, dispersionNuevo.getCupoResto());
		mostrarDefinicionGruposCompartidos = true;
	}

	public void guardarDefinicionGruposCompartidos() {
		Integer sumTotal;
		if (dispersionNuevo == null || dispersionNuevo.getCupoResto() == null
				|| dispersionNuevo.getEstudiantesGrupo() == null || dispersionNuevo.getGrupoResto() == null
				|| dispersionNuevo.getNoGrupos() == null) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion8').show()");
			return;
		}
		List<TblDispersionesBusqueda> lista = dispersionesService.actualizarDispersion(dispersionNuevo);
		if (lista.isEmpty()) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion6').show()");
			return;
		}
		sumTotal = (dispersionNuevo.getNoGrupos() * dispersionNuevo.getEstudiantesGrupo())
				+ (dispersionNuevo.getGrupoResto() * dispersionNuevo.getCupoResto());
		Integer totalRegistrado = editarDispersion != null ? editarDispersion.getNoEstudiantes() : null;
		if (totalRegistrado == null) {
			totalRegistrado = lista.get(0).getNoEstudiantes();
		}
		if (sumTotal != null && totalRegistrado != null && sumTotal > 0 && sumTotal <= totalRegistrado) {
			boolean actualizada = dispersionesService.actualizarDispersionExc(dispersionNuevo);
			if (actualizada) {
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion7').show()");
				mostrarDefinicionGruposCompartidos = false;
				busquedaDispersionGruposCompartidos();
			} else {
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccionErrorUpdate').show()");
			}
		} else {
			mostrarMensajeDispersion(true,
					"La capacidad definida debe ser mayor a cero y menor o igual al número de usuarios con inscripción.");
		}
	}

	public void cancelarDefinicionGruposCompartidos() {
		mostrarDefinicionGruposCompartidos = false;
		dispersionNuevo = null;
		editarDispersion = new TblDispersionesBusqueda();
	}

	public void limpiarDefinicionGruposCompartidos() {
		if (editarDispersion != null && editarDispersion.getIdDispersion() != null) {
			definirGruposCompartidos(editarDispersion);
		}
	}
	
	public void cancelarDispersionGruposCompartidos() throws Exception {
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/dispersion.xhtml";
		limpiarCampos();
		consultarConvocatorias();
		consultaTipoProceso();
		consultarTipoMatriculacion();
	}

	public void crearGruposCompartidos(TblDispersionesBusqueda dispersion) {
		if (dispersion == null) {
			mostrarMensajeDispersion(true, "Seleccione un registro para continuar.");
			return;
		}
		if (!dispersion.isCrearGruposHabilitado()) {
			mostrarMensajeDispersion(true,
					"No es posible crear grupos hasta completar la definición de grupos para la dispersión compartida.");
			return;
		}
		crearGruposDesdeCompartidos = true;
		prepararFormularioCrearGrupos(dispersion);
	}

	public void matricularUsuariosCompartidos(TblDispersionesBusqueda dispersion) {
		if (dispersion == null) {
			mostrarMensajeDispersion(true, "Seleccione un registro para continuar.");
			return;
		}
		if (!dispersion.isMatricularHabilitado()) {
			mostrarMensajeDispersion(true,
					"No es posible matricular usuarios hasta completar la generación de grupos.");
			return;
		}
		MatricularDispersionDTO solicitud = construirSolicitudMatriculacionCompartidos(dispersion);
		if (solicitud == null) {
			return;
		}
		try {
			ResultadoDTO<DispersionMatriculacionResultadoDTO> respuesta = dispersionesService
					.matricularUsuariosDispersion(solicitud);
			if (respuesta != null && respuesta.esCorrecto()) {
				dispersion.setMatricularHabilitado(false);
				String mensaje = obtenerMensajeRespuesta(respuesta,
						"Usuarios matriculados correctamente en los grupos de la dispersión compartida.");
				busquedaDispersionGruposCompartidos();
				mostrarMensajeDispersion(false, mensaje);
			} else {
				String mensaje = obtenerMensajeRespuesta(respuesta,
						"No fue posible completar la matriculación de usuarios.");
				mostrarMensajeDispersion(true, mensaje);
			}
		} catch (Exception ex) {
			logger.error("Error al matricular usuarios de la dispersión compartida", ex);
			mostrarMensajeDispersion(true, "Ocurrió un error al matricular los usuarios de la dispersión compartida.");
		}
	}
	
	public void crearGruposDispersion(TblDispersionesBusqueda dispersion) {
		if (dispersion == null) {
			mostrarMensajeDispersion(true, "Seleccione una dispersión para continuar.");
			return;
		}
		if (!dispersion.isCrearGruposHabilitado()) {
			mostrarMensajeDispersion(true,
					"Los grupos ya fueron generados o no se requiere este paso para la dispersión seleccionada.");
			return;
		}
		crearGruposDesdeCompartidos = false;
		prepararFormularioCrearGrupos(dispersion);
	}
	
	public void matricularUsuariosDispersion(TblDispersionesBusqueda dispersion) {
		if (dispersion == null) {
			mostrarMensajeDispersion(true, "Seleccione una dispersión para continuar.");
			return;
		}
		if (!dispersion.isMatricularHabilitado()) {
			mostrarMensajeDispersion(true,
					"No es posible matricular usuarios hasta completar la generación de grupos.");
			return;
		}
		MatricularDispersionDTO solicitud = construirSolicitudMatriculacion(dispersion);
		if (solicitud == null) {
			return;
		}
		try {
			ResultadoDTO<DispersionMatriculacionResultadoDTO> respuesta = dispersionesService
					.matricularUsuariosDispersion(solicitud);
			if (respuesta != null && respuesta.esCorrecto()) {
				dispersion.setMatricularHabilitado(false);
				String mensaje = obtenerMensajeRespuesta(respuesta,
						"Usuarios matriculados correctamente en los grupos de la dispersión.");
				busquedaDispersion();
				mostrarMensajeDispersion(false, mensaje);
			} else {
				String mensaje = obtenerMensajeRespuesta(respuesta,
						"No fue posible completar la matriculación de usuarios.");
				mostrarMensajeDispersion(true, mensaje);
			}
		} catch (Exception ex) {
			logger.error("Error al matricular usuarios de la dispersión", ex);
			mostrarMensajeDispersion(true, "Ocurrió un error al matricular los usuarios de la dispersión.");
		}
	}
	
	private void prepararFormularioCrearGrupos(TblDispersionesBusqueda dispersion) {
		if (dispersion.getIdPrograma() == null) {
			mostrarMensajeDispersion(true, "No fue posible identificar el programa asociado a la dispersión.");
			return;
		}
		datosPreviosCrear = dispersionesService.obtenerDatosPreviosEvento(dispersion.getIdPrograma());
		if (datosPreviosCrear == null) {
			mostrarMensajeDispersion(true, "No se encontraron datos del programa para crear eventos.");
			return;
		}
		this.dispersionSeleccionada = dispersion;
		inicializarFormularioCrearGrupos();
		mostrarFormularioCrearGrupos = true;
	}
	
	private void inicializarFormularioCrearGrupos() {
		formularioCrearEvento = new CrearEventoGrupoForm();
		formularioCrearEvento.setPrivado(Boolean.FALSE);
		formularioCrearEvento.setGenerarPorClave(Boolean.TRUE);
		String nombrePrograma = datosPreviosCrear != null ? datosPreviosCrear.getNombrePrograma()
				: (dispersionSeleccionada != null ? dispersionSeleccionada.getPrograma() : null);
		formularioCrearEvento.setNombreEvento(nombrePrograma);
		formularioCrearEvento.setNombreGrupoBase(datosPreviosCrear != null ? datosPreviosCrear.getClaveParaGrupo() : null);
		formularioCrearEvento.setFechaInicial(new Date());
		formularioCrearEvento.setFechaFinal(new Date());
		if (dispersionSeleccionada != null) {
			int total = 0;
			if (dispersionSeleccionada.getGruposGenerales() != null) {
				total += dispersionSeleccionada.getGruposGenerales();
			}
			if (dispersionSeleccionada.getGrupoResto() != null) {
				total += dispersionSeleccionada.getGrupoResto();
			}
			if (total > 0) {
				int maxElementoPermitido = listaNumeroElementos.isEmpty() ? total
						: Integer.parseInt(listaNumeroElementos.get(listaNumeroElementos.size() - 1));
				int numeroAsignado = Math.min(total, maxElementoPermitido);
				formularioCrearEvento.setNumeroElementos(String.valueOf(numeroAsignado));
			} else {
				formularioCrearEvento.setNumeroElementos(null);
			}
		}
		formularioCrearEvento.setAnioPeriodo(listaAnioPeriodo != null && !listaAnioPeriodo.isEmpty()
				? listaAnioPeriodo.get(0)
				: null);
		formularioCrearEvento.setIdClasificacionAva(!listaClasificacionesAva.isEmpty()
				? listaClasificacionesAva.get(0).getId()
				: CLASIFICACION_AVA_NUEVO);
		formularioCrearEvento.setEventoAutonomo(Boolean.TRUE);
		formularioCrearEvento.setIdPlataformaLms(null);
		formularioCrearEvento.setVincularAva(Boolean.FALSE);
	}
	
	public void limpiarFormularioCrearGrupos() {
		inicializarFormularioCrearGrupos();
	}
	
	public void cancelarFormularioCrearGrupos() {
		mostrarFormularioCrearGrupos = false;
		crearGruposDesdeCompartidos = false;
		dispersionSeleccionada = null;
		formularioCrearEvento = null;
		datosPreviosCrear = null;
	}
	
	private boolean formularioCrearGruposValido() {
		if (formularioCrearEvento != null) {
			formularioCrearEvento.setVincularAva(formularioCrearEvento.getIdPlataformaLms() != null);
		}
		if (formularioCrearEvento == null || dispersionSeleccionada == null || datosPreviosCrear == null) {
			mostrarMensajeDispersion(true, "Seleccione una dispersión para crear los eventos.");
			return false;
		}
		if (formularioCrearEvento.getIdEstatusEvento() == null || formularioCrearEvento.getIdModalidad() == null
				|| formularioCrearEvento.getFechaInicial() == null || formularioCrearEvento.getFechaFinal() == null
				|| formularioCrearEvento.getAnioPeriodo() == null
				|| formularioCrearEvento.getNumeroElementos() == null) {
			mostrarMensajeDispersion(true, "Complete los campos obligatorios del formulario de creación.");
			return false;
		}
		if (formularioCrearEvento.getFechaFinal().before(formularioCrearEvento.getFechaInicial())) {
			mostrarMensajeDispersion(true, "La fecha final no puede ser menor a la fecha inicial.");
			return false;
		}
		if (formularioCrearEvento.getIdPlataformaLms() == null) {
			mostrarMensajeDispersion(true, "Seleccione la plataforma Moodle a vincular antes de continuar.");
			return false;
		}
		if (formularioCrearEvento.getIdClasificacionAva() == null) {
			mostrarMensajeDispersion(true, "Seleccione la clasificación del AVA a crear.");
			return false;
		}
		return true;
	}
	
	private CrearEventoDispersionDTO construirSolicitudCrearEventos() {
		CrearEventoDispersionDTO solicitud = new CrearEventoDispersionDTO();
		solicitud.setIdDispersion(dispersionSeleccionada.getIdDispersion());
		solicitud.setIdPrograma(dispersionSeleccionada.getIdPrograma());
		solicitud.setIdPlan(dispersionSeleccionada.getIdPlan());
		solicitud.setTipoMatriculacion(dispersionSeleccionada.getTipoMatriculacion());
		solicitud.setGruposGenerales(dispersionSeleccionada.getGruposGenerales());
		solicitud.setGruposResto(dispersionSeleccionada.getGrupoResto());
		solicitud.setCupoGeneral(dispersionSeleccionada.getCupoGeneral());
		solicitud.setCupoResto(dispersionSeleccionada.getCupoResto());
		solicitud.setNoEstudiantes(dispersionSeleccionada.getNoEstudiantes());
		solicitud.setIdEstatusEvento(formularioCrearEvento.getIdEstatusEvento());
		solicitud.setNombreEventoBase(formularioCrearEvento.getNombreEvento());
		solicitud.setPrivado(formularioCrearEvento.getPrivado());
		solicitud.setFechaInicial(formularioCrearEvento.getFechaInicial());
		solicitud.setFechaFinal(formularioCrearEvento.getFechaFinal());
		solicitud.setIdModalidad(formularioCrearEvento.getIdModalidad());
		solicitud.setAnioPeriodo(formularioCrearEvento.getAnioPeriodo());
		solicitud.setNumeroElementos(formularioCrearEvento.getNumeroElementos());
		solicitud.setGenerarPorClave(formularioCrearEvento.getGenerarPorClave());
		solicitud.setNombreGrupoBase(formularioCrearEvento.getNombreGrupoBase());
		solicitud.setIdPlataformaLms(formularioCrearEvento.getIdPlataformaLms());
		solicitud.setIdClasificacionAva(formularioCrearEvento.getIdClasificacionAva());
		solicitud.setEventoAutonomo(formularioCrearEvento.getEventoAutonomo());
		solicitud.setVincularAva(formularioCrearEvento.getIdPlataformaLms() != null);
		solicitud.setClaveParaEvento(datosPreviosCrear != null ? datosPreviosCrear.getClaveParaEvento() : null);
		solicitud.setClaveParaGrupo(datosPreviosCrear != null ? datosPreviosCrear.getClaveParaGrupo() : null);
		solicitud.setBloquePrograma(datosPreviosCrear != null ? datosPreviosCrear.getBloque() : null);
		solicitud.setNombrePrograma(datosPreviosCrear != null ? datosPreviosCrear.getNombrePrograma() : null);
		solicitud.setObjetivosGenerales(datosPreviosCrear != null ? datosPreviosCrear.getObjetivosGenerales() : null);
		solicitud.setPerfilEgreso(datosPreviosCrear != null ? datosPreviosCrear.getPerfilEgreso() : null);
		solicitud.setRequisitosIngreso(datosPreviosCrear != null ? datosPreviosCrear.getRequisitosIngreso() : null);
		solicitud.setCalificacionMinAprobatoria(
				datosPreviosCrear != null ? datosPreviosCrear.getCalificacionMinAprobatoria() : null);
		solicitud.setIdUsuario(getUsuarioEnSession() != null ? getUsuarioEnSession().getIdPersona() : null);
		return solicitud;
	}
	
	private MatricularDispersionDTO construirSolicitudMatriculacion(TblDispersionesBusqueda dispersion) {
		if (dispercionParametros == null || dispercionParametros.getIdConvocatoriaSeleccionada() == null
				|| dispercionParametros.getIdTipoProceso() == null
				|| dispercionParametros.getIdProcesoInscripcion() == null) {
			mostrarMensajeDispersion(true,
					"Seleccione la convocatoria, el tipo y el proceso de inscripción antes de matricular usuarios.");
			return null;
		}
		Long usuario = getUsuarioEnSession() != null ? getUsuarioEnSession().getIdPersona() : null;
		if (usuario == null) {
			mostrarMensajeDispersion(true, "No se pudo identificar al usuario en sesión.");
			return null;
		}
		MatricularDispersionDTO solicitud = new MatricularDispersionDTO();
		solicitud.setIdDispersion(dispersion.getIdDispersion());
		solicitud.setIdConvocatoria(dispercionParametros.getIdConvocatoriaSeleccionada());
		solicitud.setIdTipoProceso(dispercionParametros.getIdTipoProceso());
		solicitud.setIdProcesoInscripcion(dispercionParametros.getIdProcesoInscripcion());
		solicitud.setIdPrograma(dispersion.getIdPrograma());
		solicitud.setIdPlan(dispersion.getIdPlan());
		solicitud.setGruposGenerales(dispersion.getGruposGenerales());
		solicitud.setGrupoResto(dispersion.getGrupoResto());
		solicitud.setIdUsuario(usuario);
		return solicitud;
	}

	private MatricularDispersionDTO construirSolicitudMatriculacionCompartidos(TblDispersionesBusqueda dispersion) {
		ProcesosInscripcion procesoSeleccionado = obtenerProcesoConDispersionSeleccionado();
		if (procesoSeleccionado == null || procesoSeleccionado.getIdConvocatoria() == null
				|| procesoSeleccionado.getIdTipoProceso() == null
				|| procesoSeleccionado.getIdProcesoInscripcion() == null) {
			mostrarMensajeDispersion(true,
					"Seleccione un proceso de inscripción con dispersión válido antes de matricular usuarios.");
			return null;
		}
		Long usuario = getUsuarioEnSession() != null ? getUsuarioEnSession().getIdPersona() : null;
		if (usuario == null) {
			mostrarMensajeDispersion(true, "No se pudo identificar al usuario en sesión.");
			return null;
		}
		MatricularDispersionDTO solicitud = new MatricularDispersionDTO();
		solicitud.setIdDispersion(dispersion.getIdDispersion());
		solicitud.setIdConvocatoria(procesoSeleccionado.getIdConvocatoria());
		solicitud.setIdTipoProceso(procesoSeleccionado.getIdTipoProceso());
		solicitud.setIdProcesoInscripcion(procesoSeleccionado.getIdProcesoInscripcion());
		solicitud.setIdPrograma(dispersion.getIdPrograma());
		solicitud.setIdPlan(dispersion.getIdPlan());
		solicitud.setGruposGenerales(dispersion.getGruposGenerales());
		solicitud.setGrupoResto(dispersion.getGrupoResto());
		solicitud.setMatriculacionCompartidos(Boolean.TRUE);
		solicitud.setNombreProgramaSeleccionado(dispersion.getPrograma());
		solicitud.setIdUsuario(usuario);
		return solicitud;
	}

	private ProcesosInscripcion obtenerProcesoConDispersionSeleccionado() {
		if (dispercionParametros == null || dispercionParametros.getIdProcesoInscripcionConDispersion() == null
				|| listaProcesosConDispersion == null || listaProcesosConDispersion.isEmpty()) {
			return null;
		}
		for (ProcesosInscripcion proceso : listaProcesosConDispersion) {
			if (proceso != null && dispercionParametros.getIdProcesoInscripcionConDispersion()
					.equals(proceso.getIdProcesoInscripcion())) {
				return proceso;
			}
		}
		return null;
	}
	
	public void aceptarFormularioCrearGrupos() {
		if (!formularioCrearGruposValido()) {
			return;
		}
		try {
			boolean contextoCompartidos = crearGruposDesdeCompartidos;
			CrearEventoDispersionDTO solicitud = construirSolicitudCrearEventos();
			ResultadoDTO<DispersionCreacionResultadoDTO> respuesta = dispersionesService
					.crearEventosDispersion(solicitud);
			if (respuesta != null && respuesta.esCorrecto()) {
				String mensaje = !respuesta.getMensajes().isEmpty() ? respuesta.getMensajes().get(0)
						: "Eventos y grupos generados correctamente.";
				cancelarFormularioCrearGrupos();
				refrescarBusquedaPosteriorCrearGrupos(contextoCompartidos);
				mostrarMensajeDispersion(false, mensaje);
			} else {
				String mensaje = (respuesta != null && !respuesta.getMensajes().isEmpty())
						? respuesta.getMensajes().get(0)
						: "No fue posible completar la creación de eventos.";
				mostrarMensajeDispersion(true, mensaje);
			}
		} catch (Exception ex) {
			logger.error("Error al generar eventos y grupos de dispersión", ex);
			mostrarMensajeDispersion(true, "Ocurrió un error al crear los eventos de dispersión.");
		}
	}
	
	public void busquedaDispersion2() {

		logger.info("***********************Inicio Consulta dispercion***********************");

		if (dispercionParametros.getIdConvocatoriaSeleccionada() == null || dispercionParametros.getIdPlan() == null
				|| dispercionParametros.getIdPrograma() == null || dispercionParametros.getIdTipoProceso() == null) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion2').show()");
		} else {
			listaDispercionBusqueda = dispersionesService.consultaDisperciones(dispercionParametros);
			actualizarAccionesTablero();
			busquedaDis2(listaDispercionBusqueda);
			if (listaDispercionBusqueda.isEmpty()) {
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion3').show()");
			}
		}

	}
	
	public void busquedaDis(List<TblDispersionesBusqueda> listaDispercionBusqueda) {
		TblDispersionesBusqueda tblDispersion = new TblDispersionesBusqueda();
		tblDispersion.setIdDispersion(10);
		tblDispersion.setPlan("Contaduría y Finanzas Públicas");
		tblDispersion.setPrograma("Administración estratégica");
		tblDispersion.setClave("CAE");
		tblDispersion.setSemestre("Semestre 2");
		tblDispersion.setBloque("Bloque 2");
		tblDispersion.setNoEstudiantes(483);
		tblDispersion.setGruposGenerales(0);
		tblDispersion.setCupoGeneral(0);
		tblDispersion.setGrupoResto(0);
		tblDispersion.setCupoResto(0);
		listaDispercionBusqueda.add(tblDispersion);
	}

	public void busquedaDis2(List<TblDispersionesBusqueda> listaDispercionBusqueda) {
		TblDispersionesBusqueda tblDispersion = new TblDispersionesBusqueda();
		tblDispersion.setIdDispersion(10);
		tblDispersion.setPlan("Contaduría y Finanzas Públicas");
		tblDispersion.setPrograma("Administración estratégica");
		tblDispersion.setClave("CAE");
		tblDispersion.setSemestre("Semestre 2");
		tblDispersion.setBloque("Bloque 2");
		tblDispersion.setNoEstudiantes(483);
		tblDispersion.setGruposGenerales(10);
		tblDispersion.setCupoGeneral(11);
		tblDispersion.setGrupoResto(32);
		tblDispersion.setCupoResto(43);
		listaDispercionBusqueda.add(tblDispersion);
	}

	///////////////////////////////////

	public void eliminar() throws Exception {

		logger.info(" INICIA ELIMINAR  ");

		if (elminarDispersion == null || elminarDispersion.getIdDispersion() == null) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion5').show()");
			return;
		}
		
		boolean tieneRelaciones = dispersionesService.existeRelDispersionGrupo(elminarDispersion.getIdDispersion());
		if (tieneRelaciones) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccionDispersionRelacion').show()");
			logger.warn(String.format("No se puede eliminar la dispersión %d porque tiene relaciones en rel_dispersiones_grupo.",
				elminarDispersion.getIdDispersion()));
			return;
		}

		dispersionesService.borrarDispercsion(elminarDispersion);
		
		if (filtrosBusquedaCompletos()) {
			busquedaDispersion();
		} else {
			listaDispercionBusqueda = new ArrayList<>();
		}
		
		RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion4').show()");	

		logger.info(" TERMINA ELIMINAR  ");

	}

	public void altaDisperciones() {

		logger.info("***********************Inicio Alta de Dispersiones***********************");

		if (!camposRequeridosCompletos()) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion8').show()");
			return;
		}

		Long usuarioId = getUsuarioEnSession() != null ? getUsuarioEnSession().getIdPersona() : null;
		dispercionParametros.setIdUsuarioAccion(usuarioId);

		boolean existeDispersion = dispersionesService.validarDispercionExistente(dispercionParametros);
		if (existeDispersion) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion2').show()");
			return;
		}

		boolean existeOrdinaria = dispersionesService.validarDispercionExistenteOrdinario(dispercionParametros);

		if (esOrdinario() && existeOrdinaria) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion9').show()");
			return;
		}

		if (esExtraordinario() && !existeOrdinaria) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion3').show()");
			return;
		}

		registrarNuevaDispersion();

		logger.info("***********************Fin Alta de Dispersiones***********************");
	}
	
	private void registrarNuevaDispersion() {
		if (esExtraordinario()) {
			for (InscripcionPlanesProgramas planPrograma : planesProgramasSeleccionados) {
				if (planPrograma == null) {
					continue;
				}
				dispercionParametros.setIdPlan(planPrograma.getIdPlan());
				dispercionParametros.setIdPrograma(planPrograma.getIdPrograma());
				dispersionesService.altaDisperciones(dispercionParametros);
			}
			dispercionParametros.setIdPlan(null);
			dispercionParametros.setIdPrograma(null);
		} else {
			dispersionesService.altaDisperciones(dispercionParametros);
		}
		limpiarCampos();
		RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion1').show()");
	}

	private boolean camposRequeridosCompletos() {
		if (dispercionParametros.getIdConvocatoriaSeleccionada() == null
				|| dispercionParametros.getIdTipoProceso() == null
				|| dispercionParametros.getIdProcesoInscripcion() == null
				|| dispercionParametros.getIdTipoMatriculacion() == null) {
			return false;
		}
		if (esExtraordinario() && !tienePlanesProgramasSeleccionados()) {
			return false;
		}
		return true;
	}
	
	private boolean esOrdinario() {
		return dispercionParametros.getIdTipoProceso() != null
				&& dispercionParametros.getIdTipoProceso().intValue() == TIPO_PROCESO_ORDINARIO;
	}
	
	private boolean esExtraordinario() {
		return dispercionParametros.getIdTipoProceso() != null
				&& dispercionParametros.getIdTipoProceso().intValue() == TIPO_PROCESO_EXTRAORDINARIO;
	}
	
	private boolean tienePlanesProgramasSeleccionados() {
		return planesProgramasSeleccionados != null && !planesProgramasSeleccionados.isEmpty();
	}
	
	private boolean tienePlanesBusquedaSeleccionados() {
		return planesProgramasBusquedaSeleccionados != null && !planesProgramasBusquedaSeleccionados.isEmpty();
	}
	
	private void ocultarPlanPrograma() {
		mostrarPlanYPrograma = false;
		dispercionParametros.setIdPlan(null);
		dispercionParametros.setIdPrograma(null);
		listaPlanes = new ArrayList<>();
		listaPrograma = new ArrayList<>();
		listaPlanProgramas = new ArrayList<>();
		planesProgramasSeleccionados = new ArrayList<>();
		idProgramaPlanSeleccionado = null;
	}

	public void consultarConvocatorias() throws Exception {

		listaConvocatoria = convocatoriaService.consultarConvocatorias();

		logger.info("Termina consulta lista convocatorias select");

	}

	public void consultarPlan() {
		if (dispercionParametros.getIdConvocatoriaSeleccionada() == null) {
			listaPlanes = new ArrayList<>();
			return;
		}
		listaPlanes = dispersionesService.consultarPlan(dispercionParametros);

		logger.info("Termina consulta listaPlanes select");
	}

	public void consultarPrograma() {
		if (dispercionParametros.getIdPlan() == null) {
			listaPrograma = new ArrayList<>();
			return;
		}
		listaPrograma = dispersionesService.consultarPrograma(dispercionParametros);

		logger.info("Termina consulta listaPlanes select");
	}

	public void consultaTipoProceso() throws Exception {

		listaTipoProceso = inscripcionesService.consultarTipoProceso();

		logger.info("Termina consulta listaTipoProceso select");

	}

	public void consultarProcesoInscripcion() throws Exception {

		if (dispercionParametros.getIdConvocatoriaSeleccionada() == null
				|| dispercionParametros.getIdTipoProceso() == null) {
			listaProcesosInscripcion = new ArrayList<>();
			dispercionParametros.setIdProcesoInscripcion(null);
			ocultarPlanPrograma();
			return;
		}

		listaProcesosInscripcion = dispersionesService.consultarProcesoInscripcion(dispercionParametros);

		if (esExtraordinario()) {
			mostrarPlanYPrograma = true;
			consultarPlan();
			if (dispercionParametros.getIdProcesoInscripcion() != null) {
				consultarPlanesProgramas();
			} else {
				listaPlanProgramas = new ArrayList<>();
			}
		} else {
			ocultarPlanPrograma();
		}

		logger.info("Termina consulta listaProcesosInscripcion select");

	}
	
	public void onProcesoInscripcionSeleccionado() {
		if (dispercionParametros.getIdProcesoInscripcion() == null) {
			listaPlanProgramas = new ArrayList<>();
			planesProgramasSeleccionados = new ArrayList<>();
			planesProgramasBusquedaSeleccionados = new ArrayList<>();
			return;
		}
		consultarPlanesProgramas();
	}

	public void consultarTipoMatriculacion() throws Exception {

		listaTipoMatriculacion = dispersionesService.consultarTipoMatriculacion();

		logger.info("Termina consulta listaTipoMatriculacion select");

	}

	////////////////////////////////////

	public String getPaginaActual() {
		return paginaActual;
	}

	public DispersionesService getDispersionesService() {
		return dispersionesService;
	}

	public void setDispersionesService(DispersionesService dispersionesService) {
		this.dispersionesService = dispersionesService;
	}

	public ConvocatoriaService getConvocatoriaService() {
		return convocatoriaService;
	}

	public void setConvocatoriaService(ConvocatoriaService convocatoriaService) {
		this.convocatoriaService = convocatoriaService;
	}

	public List<Convocatoria> getListaConvocatoria() {
		return listaConvocatoria;
	}

	public void setListaConvocatoria(List<Convocatoria> listaConvocatoria) {
		this.listaConvocatoria = listaConvocatoria;
	}

	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}

	public InscripcionesService getInscripcionesService() {
		return inscripcionesService;
	}

	public void setInscripcionesService(InscripcionesService inscripcionesService) {
		this.inscripcionesService = inscripcionesService;
	}
	
	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
	}

	public List<TipoProceso> getListaTipoProceso() {
		return listaTipoProceso;
	}

	public void setListaTipoProceso(List<TipoProceso> listaTipoProceso) {
		this.listaTipoProceso = listaTipoProceso;
	}

	public List<ProcesosInscripcion> getListaProcesosInscripcion() {
		return listaProcesosInscripcion;
	}

	public void setListaProcesosInscripcion(List<ProcesosInscripcion> listaProcesosInscripcion) {
		this.listaProcesosInscripcion = listaProcesosInscripcion;
	}
	
	public List<ProcesosInscripcion> getListaProcesosConDispersion() {
		return listaProcesosConDispersion;
	}
	
	public void setListaProcesosConDispersion(List<ProcesosInscripcion> listaProcesosConDispersion) {
		this.listaProcesosConDispersion = listaProcesosConDispersion;
	}
	
	public List<ProcesosInscripcion> getListaProcesosSinDispersion() {
		return listaProcesosSinDispersion;
	}
	
	public void setListaProcesosSinDispersion(List<ProcesosInscripcion> listaProcesosSinDispersion) {
		this.listaProcesosSinDispersion = listaProcesosSinDispersion;
	}
	
	public String getMensajeProcesosConDispersion() {
		return mensajeProcesosConDispersion;
	}
	
	public void setMensajeProcesosConDispersion(String mensajeProcesosConDispersion) {
		this.mensajeProcesosConDispersion = mensajeProcesosConDispersion;
	}
	
	public String getMensajeProcesosSinDispersion() {
		return mensajeProcesosSinDispersion;
	}
	
	public void setMensajeProcesosSinDispersion(String mensajeProcesosSinDispersion) {
		this.mensajeProcesosSinDispersion = mensajeProcesosSinDispersion;
	}

	public List<String> getListaProgramasCompartidos() {
		return listaProgramasCompartidos;
	}

	public void setListaProgramasCompartidos(List<String> listaProgramasCompartidos) {
		this.listaProgramasCompartidos = listaProgramasCompartidos;
	}

	public String getMensajeProgramasCompartidos() {
		return mensajeProgramasCompartidos;
	}

	public void setMensajeProgramasCompartidos(String mensajeProgramasCompartidos) {
		this.mensajeProgramasCompartidos = mensajeProgramasCompartidos;
	}

	public List<TipoMatriculacion> getListaTipoMatriculacion() {
		return listaTipoMatriculacion;
	}

	public void setListaTipoMatriculacion(List<TipoMatriculacion> listaTipoMatriculacion) {
		this.listaTipoMatriculacion = listaTipoMatriculacion;
	}
	
	public boolean isMostrarFormularioCrearGrupos() {
		return mostrarFormularioCrearGrupos;
	}
	
	public DispersionPreEvento getDatosPreviosCrear() {
		return datosPreviosCrear;
	}
	
	public CrearEventoGrupoForm getFormularioCrearEvento() {
		return formularioCrearEvento;
	}
	
	public List<CatalogoComunDTO> getListaEstatusEvento() {
		return listaEstatusEvento;
	}
	
	public List<CatalogoComunDTO> getListaModalidadesEvento() {
		return listaModalidadesEvento;
	}
	
	public List<String> getListaNumeroElementos() {
		return listaNumeroElementos;
	}
	
	public List<String> getListaAnioPeriodo() {
		return listaAnioPeriodo;
	}
	
	public List<ParametroWSMoodleDTO> getListaPlataformasMoodle() {
		return listaPlataformasMoodle;
	}
	
	public List<CatalogoComunDTO> getListaClasificacionesAva() {
		return listaClasificacionesAva;
	}
	
	public List<DispersionMatriculaExistenteDTO> getListaDispersionesExistentes() {
		return listaDispersionesExistentes;
	}
	
	public void setListaDispersionesExistentes(List<DispersionMatriculaExistenteDTO> listaDispersionesExistentes) {
		this.listaDispersionesExistentes = listaDispersionesExistentes;
	}

	public DispersionesParam getDispercionParametros() {
		return dispercionParametros;
	}

	public void setDispercionParametros(DispersionesParam dispercionParametros) {
		this.dispercionParametros = dispercionParametros;
	}

	public boolean isMostrarPlanYPrograma() {
		return mostrarPlanYPrograma;
	}

	public void setMostrarPlanYPrograma(boolean mostrarPlanYPrograma) {
		this.mostrarPlanYPrograma = mostrarPlanYPrograma;
	}

	public List<TblDispersionesBusqueda> getListaDispercionBusqueda() {
		return listaDispercionBusqueda;
	}

	public void setListaDispercionBusqueda(List<TblDispersionesBusqueda> listaDispercionBusqueda) {
		this.listaDispercionBusqueda = listaDispercionBusqueda;
	}

	public List<TblPlan> getListaPlanes() {
		return listaPlanes;
	}

	public void setListaPlanes(List<TblPlan> listaPlanes) {
		this.listaPlanes = listaPlanes;
	}

	public List<TblFichaDescriptivaPrograma> getListaPrograma() {
		return listaPrograma;
	}

	public void setListaPrograma(List<TblFichaDescriptivaPrograma> listaPrograma) {
		this.listaPrograma = listaPrograma;
	}

	public TblDispersionesBusqueda getElminarDispersion() {
		return elminarDispersion;
	}

	public void setElminarDispersion(TblDispersionesBusqueda elminarDispersion) {
		this.elminarDispersion = elminarDispersion;
	}
	
	public TblDispersionesBusqueda getEditarDispersion() {
		return editarDispersion;
	}

	public void setEditarDispersion(TblDispersionesBusqueda editarDispersion) {
		this.editarDispersion = editarDispersion;
	}

	public boolean getMostrarConsultaDispersion() {
		return mostrarConsultaDispersion;
	}

	public void setMostrarConsultaDispersion(boolean mostrarConsultaDispersion) {
		this.mostrarConsultaDispersion = mostrarConsultaDispersion;
	}

	public boolean getMostrarNuevaDispersion() {
		return mostrarNuevaDispersion;
	}

	public void setMostrarNuevaDispersion(boolean mostrarNuevaDispersion) {
		this.mostrarNuevaDispersion = mostrarNuevaDispersion;
	}

	public boolean isMostrarDefinicionGruposCompartidos() {
		return mostrarDefinicionGruposCompartidos;
	}

	public void setMostrarDefinicionGruposCompartidos(boolean mostrarDefinicionGruposCompartidos) {
		this.mostrarDefinicionGruposCompartidos = mostrarDefinicionGruposCompartidos;
	}
	
	public DispersionesParamNuevo getDispersionNuevo() {
		return dispersionNuevo;
	}

	public void setDispersionNuevo(DispersionesParamNuevo dispersionNuevo) {
		this.dispersionNuevo = dispersionNuevo;
	}

	public List<String> getListaGrupoGeneral() {
		return listaGrupoGeneral;
	}

	public void setListaGrupoGeneral(List<String> listaGrupoGeneral) {
		this.listaGrupoGeneral = listaGrupoGeneral;
	}

	public List<String> getListaCupoGeneral() {
		return listaCupoGeneral;
	}

	public void setListaCupoGeneral(List<String> listaCupoGeneral) {
		this.listaCupoGeneral = listaCupoGeneral;
	}

	public List<String> getListaGrupoRestante() {
		return listaGrupoRestante;
	}

	public void setListaGrupoRestante(List<String> listaGrupoRestante) {
		this.listaGrupoRestante = listaGrupoRestante;
	}

	public List<String> getListaCupoRestanre() {
		return listaCupoRestanre;
	}

	public void setListaCupoRestanre(List<String> listaCupoRestanre) {
		this.listaCupoRestanre = listaCupoRestanre;
	}

	public List<InscripcionPlanesProgramas> getListaPlanProgramas() {
		return listaPlanProgramas;
	}

	public void setListaPlanProgramas(List<InscripcionPlanesProgramas> listaPlanProgramas) {
		this.listaPlanProgramas = listaPlanProgramas;
	}

	public List<InscripcionPlanesProgramas> getPlanesProgramasSeleccionados() {
		return planesProgramasSeleccionados;
	}

	public void setPlanesProgramasSeleccionados(List<InscripcionPlanesProgramas> planesProgramasSeleccionados) {
		this.planesProgramasSeleccionados = planesProgramasSeleccionados;
	}

	public List<InscripcionPlanesProgramas> getPlanesProgramasBusquedaSeleccionados() {
		return planesProgramasBusquedaSeleccionados;
	}

	public void setPlanesProgramasBusquedaSeleccionados(
			List<InscripcionPlanesProgramas> planesProgramasBusquedaSeleccionados) {
		this.planesProgramasBusquedaSeleccionados = planesProgramasBusquedaSeleccionados;
	}

	public Integer getIdProgramaPlanSeleccionado() {
		return idProgramaPlanSeleccionado;
	}

	public void setIdProgramaPlanSeleccionado(Integer idProgramaPlanSeleccionado) {
		this.idProgramaPlanSeleccionado = idProgramaPlanSeleccionado;
	}
	
	public String getMensajeGestion() {
		return mensajeGestion;
	}
	
	public void setMensajeGestion(String mensajeGestion) {
		this.mensajeGestion = mensajeGestion;
	}
	
	public String getMensajeTablaDispersionExistente() {
		return mensajeTablaDispersionExistente;
	}
	
	public void setMensajeTablaDispersionExistente(String mensajeTablaDispersionExistente) {
		this.mensajeTablaDispersionExistente = mensajeTablaDispersionExistente;
	}

	public String getMensajeTablaDispersionGruposCompartidos() {
		return mensajeTablaDispersionGruposCompartidos;
	}

	public void setMensajeTablaDispersionGruposCompartidos(String mensajeTablaDispersionGruposCompartidos) {
		this.mensajeTablaDispersionGruposCompartidos = mensajeTablaDispersionGruposCompartidos;
	}
	
	public TblDispersionesBusqueda getDispersionSeleccionada() {
		return dispersionSeleccionada;
	}
	
	public void consultarPlanesProgramas() {
		if (dispercionParametros.getIdConvocatoriaSeleccionada() == null) {
			listaPlanProgramas = new ArrayList<>();
			planesProgramasSeleccionados = new ArrayList<>();
			return;
		}
		listaPlanProgramas = dispersionesService.consultarPlanesProgramas(dispercionParametros);
		planesProgramasSeleccionados = new ArrayList<>();
		planesProgramasBusquedaSeleccionados = new ArrayList<>();
		idProgramaPlanSeleccionado = null;
		dispercionParametros.setIdPlan(null);
		dispercionParametros.setIdPrograma(null);
	}
	
	public void onPlanProgramaSeleccionado() {
		if (idProgramaPlanSeleccionado == null || listaPlanProgramas == null) {
			dispercionParametros.setIdPlan(null);
			dispercionParametros.setIdPrograma(null);
			return;
		}
		
		for (InscripcionPlanesProgramas planPrograma : listaPlanProgramas) {
			if (planPrograma.getIdPrograma() != null
					&& planPrograma.getIdPrograma().equals(idProgramaPlanSeleccionado)) {
				dispercionParametros.setIdPlan(planPrograma.getIdPlan());
				dispercionParametros.setIdPrograma(planPrograma.getIdPrograma());
				return;
			}
		}
	}
	
	public void matricularUsuariosDispersionExistente(DispersionMatriculaExistenteDTO dispersion) {
		this.dispersionExistenteSeleccionada = dispersion;
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmMatriculaExistente').show()");
	}
	
	public void confirmarMatriculaUsuariosDispersionExistente() {
		if (dispersionExistenteSeleccionada == null) {
			mostrarMensajeDispersion(true, "No se seleccionó una dispersión.");
			return;
		}
		Long usuario = getUsuarioEnSession() != null ? getUsuarioEnSession().getIdPersona() : null;
		if (usuario == null) {
			mostrarMensajeDispersion(true, "No se pudo identificar al usuario en sesión.");
			return;
		}
		ResultadoDTO<DispersionMatriculacionResultadoDTO> respuesta = dispersionesService
				.matricularDispersionExistente(dispersionExistenteSeleccionada, usuario);
		String mensaje = obtenerMensajeRespuesta(respuesta, "No fue posible completar la matriculación.");
		if (respuesta != null && respuesta.esCorrecto()) {
			mostrarMensajeDispersion(false, mensaje);
			busquedaDispersionExistente();
		} else {
			mostrarMensajeDispersion(true, mensaje);
		}
		dispersionExistenteSeleccionada = null;
	}
	
	
	private void inicializarListasEdicion() {
		listaGrupoGeneral = construirListaNumerica(MAX_VALOR_LISTAS);
		listaCupoGeneral = construirListaNumerica(MAX_VALOR_LISTAS);
		listaGrupoRestante = construirListaNumerica(MAX_VALOR_LISTAS);
		listaCupoRestanre = construirListaNumerica(MAX_VALOR_LISTAS);
	}
	
	private void cargarCatalogosCrearGrupos() {
		listaEstatusEvento = obtenerCatalogoDesdeContexto(ConstantesGestorWeb.CAT_ESTADO_EVENTO_CAPACITACION);
		listaModalidadesEvento = obtenerCatalogoDesdeContexto(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG);
		listaNumeroElementos = construirListaNumeroElementos();
		listaAnioPeriodo = construirListaAniosPeriodo();
		listaPlataformasMoodle = cargarPlataformasMoodle();
		listaClasificacionesAva = cargarClasificacionesAva();
	}
	
	@SuppressWarnings("unchecked")
	private List<CatalogoComunDTO> obtenerCatalogoDesdeContexto(String atributo) {
		Object atributoContexto = getSession().getServletContext().getAttribute(atributo);
		if (atributoContexto instanceof List<?>) {
			return new ArrayList<>((List<CatalogoComunDTO>) atributoContexto);
		}
		return new ArrayList<>();
	}
	
	private List<String> construirListaNumeroElementos() {
		List<String> elementos = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			elementos.add(String.valueOf(i));
		}
		return elementos;
	}
	
	private List<String> construirListaAniosPeriodo() {
		List<String> anios = new ArrayList<>();
		LocalDate fechaActual = LocalDate.now();
		int anio = fechaActual.getYear();
		for (int i = 0; i < LIMITE_ANIOS_PERIODO; i++) {
			anios.add(String.valueOf(anio + i));
		}
		return anios;
	}
	
	private List<ParametroWSMoodleDTO> cargarPlataformasMoodle() {
		try {
			return eventoCapacitacionServiceFacade != null
					? eventoCapacitacionServiceFacade.obtenerPlataformasMoodle()
					: new ArrayList<ParametroWSMoodleDTO>();
		} catch (Exception e) {
			logger.error("Error al cargar plataformas Moodle", e);
			return new ArrayList<>();
		}
	}
	
	private List<CatalogoComunDTO> cargarClasificacionesAva() {
		List<CatalogoComunDTO> clasificaciones = new ArrayList<>();
		try {
			if (eventoCapacitacionServiceFacade != null) {
				clasificaciones = eventoCapacitacionServiceFacade.obtenerClasificacionesAVA();
			}
		} catch (Exception e) {
			logger.error("Error al cargar clasificaciones AVA", e);
		}
		return filtrarClasificacionNueva(clasificaciones);
	}
	
	private List<CatalogoComunDTO> filtrarClasificacionNueva(List<CatalogoComunDTO> catalogo) {
		if (catalogo == null || catalogo.isEmpty()) {
			return new ArrayList<>();
		}
		List<CatalogoComunDTO> resultado = new ArrayList<>();
		for (CatalogoComunDTO item : catalogo) {
			if (item != null && item.getId() != null && item.getId().intValue() == CLASIFICACION_AVA_NUEVO) {
				resultado.add(item);
				break;
			}
		}
		return resultado;
	}
	
	private List<String> construirListaNumerica(int limite) {
		List<String> lista = new ArrayList<>();
		for (int i = 1; i <= limite; i++) {
			lista.add(String.valueOf(i));
		}
		return lista;
	}
	
	private void asegurarValorEnLista(List<String> lista, Integer valor) {
		if (lista == null || valor == null) {
			return;
		}
		String valorComoTexto = valor.toString();
		if (!lista.contains(valorComoTexto)) {
			lista.add(valorComoTexto);
		}
	}
	
	private boolean filtrosBusquedaCompletos() {
		return obtenerCamposFaltantes().isEmpty();
	}
	
	private List<String> obtenerCamposFaltantes() {
		List<String> faltantes = new ArrayList<>();
		if (dispercionParametros.getIdConvocatoriaSeleccionada() == null) {
			faltantes.add("convocatoria");
		}
		if (dispercionParametros.getIdTipoProceso() == null) {
			faltantes.add("tipo de proceso de inscripción");
		}
		if (dispercionParametros.getIdProcesoInscripcion() == null) {
			faltantes.add("proceso de inscripción");
		}
		return faltantes;
	}
	
	private List<TblDispersionesBusqueda> consultarDispersionesConFiltros() {
		List<TblDispersionesBusqueda> resultados = new ArrayList<>();
		
		dispercionParametros.setIdPlan(null);
		dispercionParametros.setIdPrograma(null);
		
		if (tienePlanesBusquedaSeleccionados()) {
			List<Integer> idsPlan = new ArrayList<>();
			List<Integer> idsPrograma = new ArrayList<>();
			for (InscripcionPlanesProgramas planPrograma : planesProgramasBusquedaSeleccionados) {
				if (planPrograma != null) {
					idsPlan.add(planPrograma.getIdPlan());
					idsPrograma.add(planPrograma.getIdPrograma());
				}
			}
			dispercionParametros.setListaIdPlan(idsPlan);
			dispercionParametros.setListaIdPrograma(idsPrograma);
		} else {
			dispercionParametros.setListaIdPlan(null);
			dispercionParametros.setListaIdPrograma(null);
		}
		
		List<TblDispersionesBusqueda> parciales = dispersionesService.consultaDisperciones(dispercionParametros);
		if (parciales != null && !parciales.isEmpty()) {
			resultados.addAll(parciales);
		}
		
		dispercionParametros.setListaIdPlan(null);
		dispercionParametros.setListaIdPrograma(null);
		dispercionParametros.setIdPlan(null);
		dispercionParametros.setIdPrograma(null);
		
		return resultados;
	}
	
	private void actualizarAccionesTablero() {
		if (listaDispercionBusqueda == null) {
			return;
		}
		for (TblDispersionesBusqueda dispersion : listaDispercionBusqueda) {
			if (dispersion == null) {
				continue;
			}
			int gruposGenerales = dispersion.getGruposGenerales() != null ? dispersion.getGruposGenerales() : 0;
			int gruposResto = dispersion.getGrupoResto() != null ? dispersion.getGrupoResto() : 0;
			int totalConfigurado = Math.max(0, gruposGenerales) + Math.max(0, gruposResto);
			boolean existenGruposGenerados = dispersion.getGruposCreados() != null && dispersion.getGruposCreados() > 0;
			int estudiantesInscritos = dispersion.getNoEstudiantes() != null ? dispersion.getNoEstudiantes() : 0;
			boolean matriculacionCompleta = dispersion.getUsuariosMatriculados() != null
					&& dispersion.getUsuariosMatriculados() >= estudiantesInscritos && estudiantesInscritos > 0;
			
			boolean habilitarCrear = totalConfigurado > 0 && !existenGruposGenerados;
			boolean habilitarMatricular = (existenGruposGenerados
					|| habilitarMatriculaExtraordinaria(dispersion, totalConfigurado)) && !matriculacionCompleta;
			
			dispersion.setCrearGruposHabilitado(habilitarCrear);
			dispersion.setMatricularHabilitado(habilitarMatricular);
		}
	}

	private void actualizarAccionesGruposCompartidos() {
		if (listaDispercionBusqueda == null) {
			return;
		}
		for (TblDispersionesBusqueda dispersion : listaDispercionBusqueda) {
			if (dispersion == null) {
				continue;
			}
			int gruposGenerales = dispersion.getGruposGenerales() != null ? dispersion.getGruposGenerales() : 0;
			int gruposResto = dispersion.getGrupoResto() != null ? dispersion.getGrupoResto() : 0;
			int totalConfigurado = Math.max(0, gruposGenerales) + Math.max(0, gruposResto);
			boolean definicionCompleta = totalConfigurado > 0;
			boolean existenGruposGenerados = dispersion.getGruposCreados() != null && dispersion.getGruposCreados() > 0;
			int estudiantesInscritos = dispersion.getNoEstudiantes() != null ? dispersion.getNoEstudiantes() : 0;
			boolean existeMatriculacionRegistrada = dispersion.getUsuariosMatriculados() != null
					&& dispersion.getUsuariosMatriculados() > 0;
			boolean matriculacionCompleta = dispersion.getUsuariosMatriculados() != null
					&& dispersion.getUsuariosMatriculados() >= estudiantesInscritos && estudiantesInscritos > 0;
			boolean habilitarDefinir = !existenGruposGenerados && !existeMatriculacionRegistrada
					&& !matriculacionCompleta;
			boolean habilitarCrear = definicionCompleta && !existenGruposGenerados && !existeMatriculacionRegistrada
					&& !matriculacionCompleta;
			boolean habilitarMatricular = existenGruposGenerados && !existeMatriculacionRegistrada
					&& !matriculacionCompleta;
			dispersion.setDefinirGruposHabilitado(habilitarDefinir);
			dispersion.setCrearGruposHabilitado(habilitarCrear);
			dispersion.setMatricularHabilitado(habilitarMatricular);
		}
	}
	
	private void mostrarMensajeDispersion(boolean error, String mensaje) {
		mensajeGestion = mensaje;
		if (mensaje == null || mensaje.isEmpty()) {
			return;
		}
		RequestContext.getCurrentInstance().update(":formBusquedaDispersiones");
		String dialogo = error ? "PF('dlgDispersionError').show()" : "PF('dlgDispersionInfo').show()";
		RequestContext.getCurrentInstance().execute(dialogo);
	}
	
	private String obtenerMensajeRespuesta(ResultadoDTO<?> respuesta, String mensajeDefault) {
		if (respuesta != null) {
			Object detalle = respuesta.getDto();
			if (detalle instanceof DispersionMatriculacionResultadoDTO) {
				DispersionMatriculacionResultadoDTO resultadoMatriculacion = (DispersionMatriculacionResultadoDTO) detalle;
				int alumnos = resultadoMatriculacion.getParticipantesMatriculados();
				int grupos = resultadoMatriculacion.getGruposProcesados();
				if (alumnos > 0 && grupos > 0) {
					return String.format("Se han matriculado %d alumno(s) en %d grupo(s).", alumnos, grupos);
				}
			}
			if (respuesta.getMensajes() != null && !respuesta.getMensajes().isEmpty()) {
				return String.join(" ", respuesta.getMensajes());
			}
		}
		return mensajeDefault;
	}
	
	private boolean habilitarMatriculaExtraordinaria(TblDispersionesBusqueda dispersion, int totalConfigurado) {
		boolean sinMatriculacionRegistrada = dispersion != null
				&& (dispersion.getUsuariosMatriculados() == null || dispersion.getUsuariosMatriculados() == 0);
		return dispersion != null && ultimaConsultaExtraordinaria && totalConfigurado == 0 && sinMatriculacionRegistrada;
	}

	private void refrescarBusquedaPosteriorCrearGrupos(boolean contextoCompartidos) {
		if (contextoCompartidos) {
			busquedaDispersionGruposCompartidos();
		} else {
			busquedaDispersion();
		}
	}
	
	private boolean esProcesoExtraordinarioSeleccionado() {
		return dispercionParametros != null && dispercionParametros.getIdTipoProceso() != null
				&& dispercionParametros.getIdTipoProceso().intValue() == TIPO_PROCESO_EXTRAORDINARIO;
	}
	
	public static class CrearEventoGrupoForm implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		private Integer idEstatusEvento;
		private String nombreEvento;
		private Boolean privado;
		private Date fechaInicial;
		private Date fechaFinal;
		private Integer idModalidad;
		private String anioPeriodo;
		private String numeroElementos;
		private Boolean generarPorClave;
		private String nombreGrupoBase;
		private Boolean vincularAva;
		private Integer idPlataformaLms;
		private Integer idClasificacionAva;
		private Boolean eventoAutonomo;

		public Integer getIdEstatusEvento() {
			return idEstatusEvento;
		}

		public void setIdEstatusEvento(Integer idEstatusEvento) {
			this.idEstatusEvento = idEstatusEvento;
		}

		public String getNombreEvento() {
			return nombreEvento;
		}

		public void setNombreEvento(String nombreEvento) {
			this.nombreEvento = nombreEvento;
		}

		public Boolean getPrivado() {
			return privado;
		}

		public void setPrivado(Boolean privado) {
			this.privado = privado;
		}

		public Date getFechaInicial() {
			return fechaInicial;
		}

		public void setFechaInicial(Date fechaInicial) {
			this.fechaInicial = fechaInicial;
		}

		public Date getFechaFinal() {
			return fechaFinal;
		}

		public void setFechaFinal(Date fechaFinal) {
			this.fechaFinal = fechaFinal;
		}

		public Integer getIdModalidad() {
			return idModalidad;
		}

		public void setIdModalidad(Integer idModalidad) {
			this.idModalidad = idModalidad;
		}

		public String getAnioPeriodo() {
			return anioPeriodo;
		}

		public void setAnioPeriodo(String anioPeriodo) {
			this.anioPeriodo = anioPeriodo;
		}

		public String getNumeroElementos() {
			return numeroElementos;
		}

		public void setNumeroElementos(String numeroElementos) {
			this.numeroElementos = numeroElementos;
		}

		public Boolean getGenerarPorClave() {
			return generarPorClave;
		}

		public void setGenerarPorClave(Boolean generarPorClave) {
			this.generarPorClave = generarPorClave;
		}

		public String getNombreGrupoBase() {
			return nombreGrupoBase;
		}

		public void setNombreGrupoBase(String nombreGrupoBase) {
			this.nombreGrupoBase = nombreGrupoBase;
		}

		public Boolean getVincularAva() {
			return vincularAva;
		}

		public void setVincularAva(Boolean vincularAva) {
			this.vincularAva = vincularAva;
		}
		
		public Integer getIdPlataformaLms() {
			return idPlataformaLms;
		}
		
		public void setIdPlataformaLms(Integer idPlataformaLms) {
			this.idPlataformaLms = idPlataformaLms;
		}
		
		public Integer getIdClasificacionAva() {
			return idClasificacionAva;
		}
		
		public void setIdClasificacionAva(Integer idClasificacionAva) {
			this.idClasificacionAva = idClasificacionAva;
		}
		
		public Boolean getEventoAutonomo() {
			return eventoAutonomo;
		}
		
		public void setEventoAutonomo(Boolean eventoAutonomo) {
			this.eventoAutonomo = eventoAutonomo;
		}
	}
	
}
