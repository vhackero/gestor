package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasInsDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasPasadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMaxMinDTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;

@ManagedBean
@ViewScoped
public class InscripcionBean extends BaseBean {
	
	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5929433407465074144L;

	private static final Logger logger = Logger.getLogger(InscripcionBean.class);
	
	@ManagedProperty(value = "#{inscripcionService}")
	private InscripcionService inscripcionService;


	private UsuarioSessionDTO usuarioEnSesion;
	
	private InscripcionDTO infoPersona;
	
	private List<InscripcionMateriasDTO> listadatosMaterias;

	private InscripcionMaxMinDTO maxMin;

	private List<InscripcionMateriasPasadasDTO> listadatosMateriasCursadas;

	private List<InscripcionMateriasInsDTO> listadatosMateriasInscritas;


	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		/**
		 * Obtiene el usuario en sesion
		 */
		usuarioEnSesion = this.getUsuarioEnSession();
		infoPersona = inscripcionService.consultaInformacionPersona(usuarioEnSesion.getIdPersona().toString());
		listadatosMaterias = inscripcionService.consultarMaterias(infoPersona.getIdPlan().toString());
		
		maxMin = inscripcionService.consultarMaxMin(infoPersona.getIdPlan().toString());
		listadatosMateriasCursadas = inscripcionService.consultarMateriasCursadas(usuarioEnSesion.getIdPersona().toString());
		listadatosMateriasInscritas = inscripcionService.consultarMateriasInscritas(infoPersona.getIdPlan().toString(), infoPersona.getIdPlan().toString());

	}
	
	
	public void cancelar() {
		logger.info("cancelar");

	}
	
	public void finalizarInscripcion() {
	    logger.info("finalizar");

	    // Filtrar materias seleccionadas
	    List<InscripcionMateriasDTO> materiasSeleccionadas = listadatosMaterias.stream()
	        .filter(inscripcion -> Boolean.TRUE.equals(inscripcion.getCheck()))
	        .collect(Collectors.toList());

	    // Lógica para procesar las materias seleccionadas
	    if (materiasSeleccionadas.isEmpty()) {
	        logger.info("No se seleccionaron materias.");
	    } else {
	        // Guardar materias seleccionadas o enviarlas al servicio
	        //inscripcionService.guardarMateriasSeleccionadas(materiasSeleccionadas);
	        logger.info("Materias seleccionadas guardadas: " + materiasSeleccionadas.size());
	    }
	}

	
	


	public InscripcionDTO getInfoPersona() {
		return infoPersona;
	}


	public void setInfoPersona(InscripcionDTO infoPersona) {
		this.infoPersona = infoPersona;
	}
	
	public UsuarioSessionDTO getUsuarioEnSesion() {
		return usuarioEnSesion;
	}

	public void setUsuarioEnSesion(UsuarioSessionDTO usuarioEnSesion) {
		this.usuarioEnSesion = usuarioEnSesion;
	}
	

	public InscripcionService getInscripcionService() {
		return inscripcionService;
	}

	public void setInscripcionService(InscripcionService inscripcionService) {
		this.inscripcionService = inscripcionService;
	}
	
	public List<InscripcionMateriasDTO> getListadatosMaterias() {
		return listadatosMaterias;
	}

	public void setListadatosMaterias(List<InscripcionMateriasDTO> listadatosMaterias) {
		this.listadatosMaterias = listadatosMaterias;
	}

	public InscripcionMaxMinDTO getMaxMin() {
		return maxMin;
	}


	public void setMaxMin(InscripcionMaxMinDTO maxMin) {
		this.maxMin = maxMin;
	}
	
	public List<InscripcionMateriasPasadasDTO> getListadatosMateriasCursadas() {
		return listadatosMateriasCursadas;
	}


	public void setListadatosMateriasCursadas(List<InscripcionMateriasPasadasDTO> listadatosMateriasCursadas) {
		this.listadatosMateriasCursadas = listadatosMateriasCursadas;
	}


	public List<InscripcionMateriasInsDTO> getListadatosMateriasInscritas() {
		return listadatosMateriasInscritas;
	}


	public void setListadatosMateriasInscritas(List<InscripcionMateriasInsDTO> listadatosMateriasInscritas) {
		this.listadatosMateriasInscritas = listadatosMateriasInscritas;
	}
}
