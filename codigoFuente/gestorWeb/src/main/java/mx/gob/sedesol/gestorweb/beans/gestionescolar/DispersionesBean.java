package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EstatusDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
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
	
	// REDIRECCION OPCIONES
	private String paginaActual;

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
		
		consultarConvocatorias();
		consultaTipoProceso();
		consultarProcesoInscripcion();
		consultarTipoMatriculacion();

		return null;

	}
	
	

	///////////////////////////////////

	public void consultarConvocatorias() throws Exception {

		listaConvocatoria = convocatoriaService.consultarConvocatorias();

		logger.info("Termina consulta lista convocatorias select");

	}
	
	
	public void consultaTipoProceso() throws Exception {

		listaTipoProceso = inscripcionesService.consultarTipoProceso();

		logger.info("Termina consulta listaTipoProceso select");

	}
	
	public void consultarProcesoInscripcion() throws Exception {

		listaProcesosInscripcion = dispersionesService.consultarProcesoInscripcion();

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
	
	

}
