package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@ViewScoped
public class DispersionesBean extends BaseBean {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5929433407465074144L;

	private static final Logger logger = Logger.getLogger(DispersionesBean.class);
	
	
	// REDIRECCION OPCIONES
		private String paginaActual;
	
	
	public String navegaNuevoConvocatoria() {
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/nuevaInscripcion.xhtml";
		
		return null; // Mantener en la misma página
	}


	public String getPaginaActual() {
		return paginaActual;
	}


	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}
	
	
	

}
