package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EstatusDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConvocatoriaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.DispersionesService;
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

	////////////
	private List<EstatusDTO> estatusLista;
	List<Convocatoria> listaConvocatoria;

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

		return null;

	}

	///////////////////////////////////

	public void consultarConvocatorias() throws Exception {

		listaConvocatoria = convocatoriaService.consultarConvocatorias();

		logger.info("Termina consulta lista convocatorias select");

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

}
