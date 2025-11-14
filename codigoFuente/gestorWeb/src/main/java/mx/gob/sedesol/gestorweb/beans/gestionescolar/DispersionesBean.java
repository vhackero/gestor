package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EstatusDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
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
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

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
	private static final int MAX_VALOR_LISTAS = 500;

	@ManagedProperty("#{dispersionesService}")
	private DispersionesService dispersionesService;

	@ManagedProperty("#{convocatoriaService}")
	private ConvocatoriaService convocatoriaService;

	@ManagedProperty("#{inscripcionesService}")
	private InscripcionesService inscripcionesService;

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
	List<TipoMatriculacion> listaTipoMatriculacion;
	List<TblPlan> listaPlanes;
	List<TblFichaDescriptivaPrograma> listaPrograma;
	DispersionesParam dispercionParametros;
	DispersionesParamNuevo dispersionNuevo;


	List<TblDispersionesBusqueda> listaDispercionBusqueda = new ArrayList<TblDispersionesBusqueda>();

	TblDispersionesBusqueda elminarDispersion = new TblDispersionesBusqueda();
	
	TblDispersionesBusqueda editarDispersion = new TblDispersionesBusqueda();

	// REDIRECCION OPCIONES
	private String paginaActual;

	private boolean mostrarPlanYPrograma = false;
	
	private boolean mostrarConsultaDispersion = true;
	private boolean mostrarNuevaDispersion = false;
	private Integer idProgramaPlanSeleccionado;

	@PostConstruct
	public void init() {
		dispercionParametros = new DispersionesParam(); // Inicializar el objeto
		listaProcesosInscripcion = new ArrayList<>();
		listaPlanes = new ArrayList<>();
		listaPrograma = new ArrayList<>();
		listaPlanProgramas = new ArrayList<>();
		planesProgramasSeleccionados = new ArrayList<>();
		planesProgramasBusquedaSeleccionados = new ArrayList<>();
		idProgramaPlanSeleccionado = null;
		inicializarListasEdicion();
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

	public void limpiarCampos() {
		dispercionParametros = new DispersionesParam();
		listaProcesosInscripcion = new ArrayList<>();
		listaPlanes = new ArrayList<>();
		listaPrograma = new ArrayList<>();
		listaPlanProgramas = new ArrayList<>();
		planesProgramasSeleccionados = new ArrayList<>();
		planesProgramasBusquedaSeleccionados = new ArrayList<>();
		listaDispercionBusqueda = new ArrayList<>();
		mostrarPlanYPrograma = false;
		idProgramaPlanSeleccionado = null;

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
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion5').show()");
			return;
		}
		
		listaDispercionBusqueda = consultarDispersionesConFiltros();
		if (listaDispercionBusqueda.isEmpty()) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion3').show()");
		}

	}
	
	public void busquedaDispersion2() {

		logger.info("***********************Inicio Consulta dispercion***********************");

		if (dispercionParametros.getIdConvocatoriaSeleccionada() == null || dispercionParametros.getIdPlan() == null
				|| dispercionParametros.getIdPrograma() == null || dispercionParametros.getIdTipoProceso() == null) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion2').show()");
		} else {
			listaDispercionBusqueda = dispersionesService.consultaDisperciones(dispercionParametros);
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
		if (!mostrarPlanYPrograma || dispercionParametros.getIdProcesoInscripcion() == null) {
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

	public List<TipoMatriculacion> getListaTipoMatriculacion() {
		return listaTipoMatriculacion;
	}

	public void setListaTipoMatriculacion(List<TipoMatriculacion> listaTipoMatriculacion) {
		this.listaTipoMatriculacion = listaTipoMatriculacion;
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
	
	
	private void inicializarListasEdicion() {
		listaGrupoGeneral = construirListaNumerica(MAX_VALOR_LISTAS);
		listaCupoGeneral = construirListaNumerica(MAX_VALOR_LISTAS);
		listaGrupoRestante = construirListaNumerica(MAX_VALOR_LISTAS);
		listaCupoRestanre = construirListaNumerica(MAX_VALOR_LISTAS);
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
		return dispercionParametros.getIdConvocatoriaSeleccionada() != null
				&& dispercionParametros.getIdTipoProceso() != null
				&& dispercionParametros.getIdProcesoInscripcion() != null;
	}
	
	private List<TblDispersionesBusqueda> consultarDispersionesConFiltros() {
		List<TblDispersionesBusqueda> resultados = new ArrayList<>();
		
		dispercionParametros.setIdPlan(null);
		dispercionParametros.setIdPrograma(null);
		
		if (esExtraordinario() && tienePlanesBusquedaSeleccionados()) {
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
	
}
