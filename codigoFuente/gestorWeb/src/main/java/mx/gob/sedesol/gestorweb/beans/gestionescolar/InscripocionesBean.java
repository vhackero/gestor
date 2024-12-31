package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionParamNueva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesConsultaResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionesTableroResumen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoProceso;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
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
	
	private String nombreSeleccionado;

	private String convocatoriaSeleccionada2;
	
	private String nombre;
	
	private String claveProceso;
	
	private String descripcion;
	
	private String semestre;

	private String programa;
	
	private List<?> listaProgramas;
	
	public String getPrograma() {
		return programa;
	}
	
	 @PostConstruct
	    public void init() {
	        listaSemestres = new ArrayList<>();
	        for (int i = 1; i <= 24; i++) {
	            listaSemestres.add(String.valueOf(i));
	        }
	    }
	 
	 private String semestreSeleccionado;

	public String getSemestreSeleccionado() {
		return semestreSeleccionado;
	}

	public void setSemestreSeleccionado(String semestreSeleccionado) {
		this.semestreSeleccionado = semestreSeleccionado;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public List<?> getListaProgramas() {
		return listaProgramas;
	}

	public void setListaProgramas(List<?> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}

	private List<String> listaSemestres;
	
	private Date fechaInicio;
	
	private Date fechaFin;

	private String perfil;
	
	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public List<String> getListaSemestres() {
		return listaSemestres;
	}

	public void setListaSemestres(List<String> listaSemestres) {
		this.listaSemestres = listaSemestres;
	}

	public String getClaveProceso() {
		return claveProceso;
	}

	public void setClaveProceso(String claveProceso) {
		this.claveProceso = claveProceso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

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
	
	public InscripcionParamNueva getInscripcionParamNueva() {
		return inscripcionParamNueva;
	}

	public void setInscripcionParamNueva(InscripcionParamNueva inscripcionParamNueva) {
		this.inscripcionParamNueva = inscripcionParamNueva;
	}

	private InscripcionParamNueva inscripcionParamNueva = new InscripcionParamNueva();
	
	List<Convocatoria> listaConvocatoria;
	
	List<TipoProceso> listaTipoProceso;
	
	List<TblPlan> listaPlanes;
	
	List<TblFichaDescriptivaPrograma> listaPrograma;
	
	public List<TblFichaDescriptivaPrograma> getListaPrograma() {
		return listaPrograma;
	}

	public void setListaPrograma(List<TblFichaDescriptivaPrograma> listaPrograma) {
		this.listaPrograma = listaPrograma;
	}

	List<TipoProceso> listaNombres;

	List<InscripcionesTableroResumen> listaTableResumen;
	

	List<InscripcionesConsultaResumen> listaFiltrosResumen;
	
	private boolean mostrarConsultaConvocatoria = true;
	private boolean mostrarNuevaConvocatoria = false;

	public boolean isMostrarConsultaConvocatoria() {
		return mostrarConsultaConvocatoria;
	}

	public void setMostrarConsultaConvocatoria(boolean mostrarConsultaConvocatoria) {
		this.mostrarConsultaConvocatoria = mostrarConsultaConvocatoria;
	}

	public boolean isMostrarNuevaConvocatoria() {
		return mostrarNuevaConvocatoria;
	}

	public void setMostrarNuevaConvocatoria(boolean mostrarNuevaConvocatoria) {
		this.mostrarNuevaConvocatoria = mostrarNuevaConvocatoria;
	}
	
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

		inscripcionParamNueva.setListaConvocatoria(listaConvocatoria);
		
		consultarPlan();
		
		logger.info("Termina consulta lista convocatorias select");
		logger.info(listaConvocatoria);

	}
	
	
	
	public void altaInscripcion() throws Exception {
		
		logger.info("***********************Inicio Alta Inscripcion Ordinaria***********************");
		
		logger.info("nombre de la convocaria    : " + inscripcionParamNueva.getConvocatoriaSeleccionada());
		logger.info("nombre corto               : " + inscripcionParamNueva.getProcesoSeleccionada());
		
		if (listaConvocatoria == null) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion8').show()");
		}
		
		inscripcionesService.altaInscripciones(inscripcionParamNueva);
		
		if (!inscripcionParamNueva.getInscripcionExistente()) {
			inscripcionParamNueva = new InscripcionParamNueva();
			
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion9').show()");
			
			navegaNuevoConvocatoria();
		}else {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion88').show()");
		}
		
	}
	
	public void altaInscripcionExtra() throws Exception {
		
		logger.info("***********************Inicio Alta Inscripcion Extraordinaria***********************");
		
		logger.info("nombre de la convocaria    : " + inscripcionParamNueva.getConvocatoriaSeleccionada());
		logger.info("nombre corto               : " + inscripcionParamNueva.getProcesoSeleccionada());
		
		if (listaConvocatoria == null) {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion8').show()");
		}
		
		inscripcionesService.altaInscripcionesExtra(inscripcionParamNueva);
		
		if (!inscripcionParamNueva.getInscripcionOrdinaria()) {
			inscripcionParamNueva = new InscripcionParamNueva();
			
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion9').show()");
			
			navegaNuevoConvocatoria();
		}else {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion89').show()");
		}
		
	}
	
	public String limpiarCampos() throws Exception {
		inscripcionParamNueva = new InscripcionParamNueva();
		navegaNuevoConvocatoria();
		return null;
	}
	
	
	public void cancelar() throws Exception {
		inscripcionParamNueva = new InscripcionParamNueva();
		this.paginaActual = "";
	}
	
	
	
	public void consultaTipoProceso() throws Exception {

		listaTipoProceso = inscripcionesService.consultarTipoProceso();
		
		inscripcionParamNueva.setListaTipoProceso(listaTipoProceso);

		logger.info("Termina consulta listaTipoProceso select");
		logger.info(listaTipoProceso);

	}
	
	public void consultaTipoProceso2() throws Exception {

		listaTipoProceso = inscripcionesService.consultarTipoProceso();
		
		if ("2".equalsIgnoreCase(inscripcionParamNueva.getProcesoSeleccionada())) {
	        // Cargar planes asociados a la convocatoria seleccionada
			this.mostrarConsultaConvocatoria = false;
			this.mostrarNuevaConvocatoria = true;
	    }else {
	    	this.mostrarConsultaConvocatoria = true;
			this.mostrarNuevaConvocatoria = false;
	    }

		logger.info(inscripcionParamNueva.getProcesoSeleccionada());
		logger.info("Termina consulta listaTipoProceso select");
		logger.info(listaTipoProceso);
		

	}
	
	// consutlar planes

	public void consultarPlan() throws Exception {

		listaPlanes = inscripcionesService.consultarPlan(inscripcionParamNueva);

		inscripcionParamNueva.setListaPlanes(listaPlanes);
		
		consultarPrograma();
		
		logger.info("Termina consulta   listaPlanes select");
		logger.info(listaPlanes);

	}
	
	//Consultar Programa 
	public void consultarPrograma() throws Exception {

		listaPrograma = inscripcionesService.consultarPrograma(inscripcionParamNueva);
		
		logger.info("Termina consulta   listaPlanes select");
		logger.info(listaPlanes);

	}
	
	public void consultarPrograma2() throws Exception {
		
		logger.info("Termina consulta   listaPlanes select");
		logger.info(listaPrograma);

	}
	
	public void consultarTableroResumen() throws Exception {
		

		listaTableResumen = new ArrayList<InscripcionesTableroResumen>();
        tableroParamConsulta.setValueConvocatoriaEstatus(convocatoriaSeleccionada);
        tableroParamConsulta.setConsulNivelEducativo(procesoSeleccionada);
        tableroParamConsulta.setConsulNombreCorto(nombreSeleccionado);

		listaTableResumen = inscripcionesService.consultarTableroResumen(tableroParamConsulta);

		logger.info("Termina consulta lista tabla resumenn tabla");

	}
	
	
	
	public void consultarFiltros() throws Exception {

		listaFiltrosResumen = new ArrayList<InscripcionesConsultaResumen>();

		listaFiltrosResumen = inscripcionesService.consultarFiltros(tableroParamConsulta);

		logger.info("Termina consulta lista tabla resumenn tabla");

	}
	
	
    public void onSelectChange() {
        if (convocatoriaSeleccionada != null && procesoSeleccionada != null) {
            tableroParamConsulta.setValueConvocatoriaEstatus(convocatoriaSeleccionada);
            tableroParamConsulta.setConsulNivelEducativo(procesoSeleccionada);
            listaNombres = inscripcionesService.consultarNombre(tableroParamConsulta);
            
    		logger.info("Termina consulta listaNombres select");

        }
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

	public void setListaNombres(List<TipoProceso> listaNombres) {
		this.listaNombres = listaNombres;
	}	
	
	public List<TipoProceso> getListaNombres() {
		return listaNombres;
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

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}	
	
}
