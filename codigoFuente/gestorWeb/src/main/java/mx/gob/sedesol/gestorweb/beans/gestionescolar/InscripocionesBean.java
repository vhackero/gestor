package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConvocatoriaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionesService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class InscripocionesBean extends BaseBean {
	
	private String convocatoriaSeleccionada;
	
	private String procesoSeleccionada;


	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5929433407465074144L;

	private static final Logger logger = Logger.getLogger(InscripocionesBean.class);

	@ManagedProperty("#{convocatoriaService}")
	private ConvocatoriaService convocatoriaService;
	
	@ManagedProperty("#{inscripcionesService}")
	private InscripcionesService inscripcionesService;
	
	
	
	List<Convocatoria> listaConvocatoria;
	
	List<TipoProceso> listaTipoProceso;
	
	
	// REDIRECCION OPCIONES
		private String paginaActual;
	
	
	public String navegaNuevoConvocatoria()  throws Exception{
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/nuevaInscripcion.xhtml";
		consultarConvocatorias();
		consultaTipoProceso();
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

	
	
	
}
