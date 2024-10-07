package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesConsultaResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConvocatoriaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionesService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class InscripocionesBean extends BaseBean {
	
	private String convocatoriaSeleccionada;
	
	private String procesoSeleccionada;
	
	private String planSeleccionada;

	
	private String convocatoriaSeleccionada2;

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5929433407465074144L;

	private static final Logger logger = Logger.getLogger(InscripocionesBean.class);

	@ManagedProperty("#{convocatoriaService}")
	private ConvocatoriaService convocatoriaService;
	
	@ManagedProperty("#{inscripcionesService}")
	private InscripcionesService inscripcionesService;
	
	private ConvocatoriaParamConsulta tableroParamConsulta = new ConvocatoriaParamConsulta();
	
	List<Convocatoria> listaConvocatoria;
	
	List<TipoProceso> listaTipoProceso;
	
	List<TblPlan> listaPlanes;

	List<InscripcionesTableroResumen> listaTableResumen;
	

	List<InscripcionesConsultaResumen> listaFiltrosResumen;
	
	 
	// REDIRECCION OPCIONES
		private String paginaActual;
	
	
	public String navegaNuevoConvocatoria()  throws Exception{
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/nuevaInscripcion.xhtml";
		consultarConvocatorias();
		consultaTipoProceso();
		consultarPlan();
		return null; // Mantener en la misma página
	}


	public String navegaConsultaInscripciones()  throws Exception{
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/cosultaInscripciones.xhtml";
		consultarConvocatorias();
		consultaTipoProceso();
		consultarPlan();
		return null; // Mantener en la misma página
	}
	
	public String navegaConsultaTablero()  throws Exception{
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/cosultaTableroInscripciones.xhtml";
		consultarConvocatorias();
		consultaTipoProceso();
		consultarPlan();
		return null; // Mantener en la misma página
	}

	// consutlar tablero metodos

	public void consultarConvocatorias() throws Exception {

		listaConvocatoria = convocatoriaService.consultarConvocatorias();

		logger.info("Termina consulta lista convocatorias select");
		logger.info(listaConvocatoria);

	}
	
	public void consultaTipoProceso() throws Exception {

		listaTipoProceso = inscripcionesService.consultarTipoProceso();

		logger.info("Termina consulta listaTipoProceso select");
		logger.info(listaTipoProceso);

	}
	
	// consutlar planes

	public void consultarPlan() throws Exception {

		listaPlanes = inscripcionesService.consultarPlan();

		logger.info("Termina consulta   listaPlanes select");
		logger.info(listaPlanes);

	}
	
	public void consultarTableroResumen() throws Exception {

		listaTableResumen = new ArrayList<InscripcionesTableroResumen>();

		listaTableResumen = inscripcionesService.consultarTableroResumen(tableroParamConsulta);

		logger.info("Termina consulta lista tabla resumenn tabla");

	}
	
	
	
	public void consultarFiltros() throws Exception {

		listaFiltrosResumen = new ArrayList<InscripcionesConsultaResumen>();

		listaFiltrosResumen = inscripcionesService.consultarFiltros(tableroParamConsulta);

		logger.info("Termina consulta lista tabla resumenn tabla");

	}
	
	
	
	
	
	
	
	
	public List<InscripcionesConsultaResumen> getListaFiltrosResumen() {
		return listaFiltrosResumen;
	}


	public void setListaFiltrosResumen(List<InscripcionesConsultaResumen> listaFiltrosResumen) {
		this.listaFiltrosResumen = listaFiltrosResumen;
	}


	public String getPaginaActual() {
		return paginaActual;
	}


	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}
	
	public ConvocatoriaService getConvocatoriaService() {
		return convocatoriaService;
	}

	public void setConvocatoriaService(ConvocatoriaService convocatoriaService) {
		this.convocatoriaService = convocatoriaService;
	}

	
	public String getConvocatoriaSeleccionada() {
		return convocatoriaSeleccionada;
	}

	public void setConvocatoriaSeleccionada(String convocatoriaSeleccionada) {
		this.convocatoriaSeleccionada = convocatoriaSeleccionada;
	}




	public List<Convocatoria> getListaConvocatoria() {
		return listaConvocatoria;
	}




	public void setListaConvocatoria(List<Convocatoria> listaConvocatoria) {
		this.listaConvocatoria = listaConvocatoria;
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




	public String getProcesoSeleccionada() {
		return procesoSeleccionada;
	}




	public void setProcesoSeleccionada(String procesoSeleccionada) {
		this.procesoSeleccionada = procesoSeleccionada;
	}




	public String getPlanSeleccionada() {
		return planSeleccionada;
	}




	public void setPlanSeleccionada(String planSeleccionada) {
		this.planSeleccionada = planSeleccionada;
	}




	public List<TblPlan> getListaPlanes() {
		return listaPlanes;
	}




	public void setListaPlanes(List<TblPlan> listaPlanes) {
		this.listaPlanes = listaPlanes;
	}


	public String getConvocatoriaSeleccionada2() {
		return convocatoriaSeleccionada2;
	}


	public void setConvocatoriaSeleccionada2(String convocatoriaSeleccionada2) {
		this.convocatoriaSeleccionada2 = convocatoriaSeleccionada2;
	}


	public ConvocatoriaParamConsulta getTableroParamConsulta() {
		return tableroParamConsulta;
	}


	public void setTableroParamConsulta(ConvocatoriaParamConsulta tableroParamConsulta) {
		this.tableroParamConsulta = tableroParamConsulta;
	}


	public List<InscripcionesTableroResumen> getListaTableResumen() {
		return listaTableResumen;
	}


	public void setListaTableResumen(List<InscripcionesTableroResumen> listaTableResumen) {
		this.listaTableResumen = listaTableResumen;
	}

	
	
	
}
