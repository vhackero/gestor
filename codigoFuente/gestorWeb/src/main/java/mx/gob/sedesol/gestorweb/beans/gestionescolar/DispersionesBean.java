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
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParamNuevo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersiones;
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

	private boolean mostrarPlanYPrograma = true;
	
	private boolean mostrarConsultaDispersion = true;
	private boolean mostrarNuevaDispersion = false;

	@PostConstruct
	public void init() {
		dispercionParametros = new DispersionesParam(); // Inicializar el objeto
		listaGrupoGeneral = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
        	listaGrupoGeneral.add(String.valueOf(i));
        }
        
        listaCupoGeneral = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
        	listaCupoGeneral.add(String.valueOf(i));
        }
        
        listaGrupoRestante = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
        	listaGrupoRestante.add(String.valueOf(i));
        }
        
        listaCupoRestanre = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
        	listaCupoRestanre.add(String.valueOf(i));
        }
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

		dispercionParametros = new DispersionesParam();

		consultarConvocatorias();
		consultaTipoProceso();
		// consultarProcesoInscripcion();
		consultarTipoMatriculacion();

		return null;

	}

	public String navegaConsultaDispersion() throws Exception {

		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/consultaDispersion.xhtml";

		dispercionParametros = new DispersionesParam();

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
		listaProcesosInscripcion = null;
		listaPlanes = null;
		listaPrograma = null;
		listaDispercionBusqueda = null;

	}
	
	public void actualizarDispersion () {
		
		if (editarDispersion.getCupoGeneral() == null || editarDispersion.getGrupoResto() == null ||
				editarDispersion.getCupoGeneral()== null || editarDispersion.getCupoResto() == null) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion5').show()");
		} else {
			//editarDispersion.setIdDispersion(editarDispersion.getIdDispersion());
			//List<TblDispersionesBusqueda> lista = dispersionesService.actualizarDispersion(editarDispersion);
			dispersionNuevo = new DispersionesParamNuevo();
			dispersionNuevo.setIdDispersion(editarDispersion.getIdDispersion());
			this.mostrarConsultaDispersion = false;
			this.mostrarNuevaDispersion = true;
		}
		
		
	}
	
	public void actualizaDispersionDB() {///////////////////
		
		Integer sumTotal;
		
		if (dispersionNuevo.getCupoResto() == null || dispersionNuevo.getEstudiantesGrupo() == null ||
				dispersionNuevo.getGrupoResto() == null || dispersionNuevo.getNoGrupos() == null) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion2').show()");
		} else {
			List<TblDispersionesBusqueda> lista = dispersionesService.actualizarDispersion(dispersionNuevo);
			if (lista.isEmpty()) {
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion6').show()");
			} else {
				
				sumTotal = (dispersionNuevo.getNoGrupos() * dispersionNuevo.getEstudiantesGrupo())
						+ (dispersionNuevo.getGrupoResto() * dispersionNuevo.getCupoResto());
				
				if (sumTotal == lista.get(0).getNoEstudiantes()) {
					dispersionesService.actualizarDispersionExc(dispersionNuevo);
					RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion7').show()");
					busquedaDispersion();
					this.mostrarConsultaDispersion = true;
					this.mostrarNuevaDispersion = false;
					
				} else {
					RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion8').show()");
				}
			}
		}
	}

	public void busquedaDispersion() {

		logger.info("***********************Inicio Consulta dispercion***********************");

		if (dispercionParametros.getIdConvocatoriaSeleccionada() == null || dispercionParametros.getIdTipoProceso() == null) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion2').show()");
		} else {
			listaDispercionBusqueda = dispersionesService.consultaDisperciones(dispercionParametros);
			//busquedaDis(listaDispercionBusqueda);
			if (listaDispercionBusqueda.isEmpty()) {
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion3').show()");
			}
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

		dispersionesService.borrarDispercsion(elminarDispersion);
		
		RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion4').show()");	

		logger.info(" TERMINA ELIMINAR  ");

	}

	public void altaDisperciones() {

		logger.info("***********************Inicio Alta de Dispersiones***********************");

		if (dispercionParametros.getIdTipoProceso() == 1) {

			List<TblDispersiones> validacionDispercion = dispersionesService
					.validarDispercionExistente(dispercionParametros);

			if (!validacionDispercion.isEmpty()) {
				dispersionesService.altaDisperciones(dispercionParametros);
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion1').show()");
			} else {
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion2').show()");
			}

		} else {

			List<TblDispersiones> validacionDispercionOrdinario = dispersionesService
					.validarDispercionExistenteOrdinario(dispercionParametros);

			if (dispercionParametros.getExistente() == 1) {
				dispersionesService.altaDisperciones(dispercionParametros);
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion1').show()");
			} else {
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion3').show()");
			}

		}

		logger.info("***********************Fin Alta de Dispersiones***********************");
	}

	public void consultarConvocatorias() throws Exception {

		listaConvocatoria = convocatoriaService.consultarConvocatorias();

		logger.info("Termina consulta lista convocatorias select");

	}

	public void consultarPlan() {
		listaPlanes = dispersionesService.consultarPlan(dispercionParametros);

		logger.info("Termina consulta listaPlanes select");
	}

	public void consultarPrograma() {
		listaPrograma = dispersionesService.consultarPrograma(dispercionParametros);

		logger.info("Termina consulta listaPlanes select");
	}

	public void consultaTipoProceso() throws Exception {

		listaTipoProceso = inscripcionesService.consultarTipoProceso();

		logger.info("Termina consulta listaTipoProceso select");

	}

	public void consultarProcesoInscripcion() throws Exception {

		listaProcesosInscripcion = dispersionesService.consultarProcesoInscripcion(dispercionParametros);

		if (dispercionParametros.getIdTipoProceso() == 1) {
			mostrarPlanYPrograma = false; // Ocultar "Plan" y "Programa"
		} else {
			mostrarPlanYPrograma = true; // Mostrar "Plan" y "Programa"
		}

		logger.info("Termina consulta listaProcesosInscripcion select");

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

	
}
