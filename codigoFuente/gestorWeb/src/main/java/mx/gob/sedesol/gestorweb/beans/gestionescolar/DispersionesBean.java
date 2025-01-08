package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EstatusDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
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
	List<TipoProceso> listaTipoProceso;
	List<ProcesosInscripcion> listaProcesosInscripcion;
	List<TipoMatriculacion> listaTipoMatriculacion;
	List<TblPlan> listaPlanes;
	List<TblFichaDescriptivaPrograma> listaPrograma;
	DispersionesParam dispercionParametros;

	// REDIRECCION OPCIONES
	private String paginaActual;
	
	private boolean mostrarPlanYPrograma = true;

	
	@PostConstruct
    public void init() {
		dispercionParametros = new DispersionesParam(); // Inicializar el objeto
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

	public String navegaNuevoConvocatoria() throws Exception {

		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/nuevaDispersion.xhtml";
		
		dispercionParametros = new DispersionesParam();
		
		consultarConvocatorias();
		consultaTipoProceso();
		//consultarProcesoInscripcion();
		consultarTipoMatriculacion();

		return null;

	}
	
	

	///////////////////////////////////

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

		 if (dispercionParametros.getIdTipoProceso() == 1 ) { 
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


}
