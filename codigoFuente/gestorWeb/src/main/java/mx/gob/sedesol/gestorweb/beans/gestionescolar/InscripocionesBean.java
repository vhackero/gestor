package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Convocatoria;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ConvocatoriaParamConsulta;
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
	
	private InscripcionesConsultaResumen registroParaEliminar;

	private String convocatoriaSeleccionada;

	private String procesoSeleccionada;

	private String planSeleccionada;

	private String nombreSeleccionado;

	private String convocatoriaSeleccionada2;

	private String procesoSeleccionada2;

	private Date fechaInicio;

	private Date fechaFin;

	private Date fechaInicioEdit;

	private Date fechaFinEdit;

	private String filtroEstatus;
	private List<String> listaEstatus;

	private InscripcionesConsultaResumen registroSeleccionado;
	private boolean mostrarFormularioEdicion = false;

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

	private ConvocatoriaParamConsulta consultaParamConsulta = new ConvocatoriaParamConsulta();

	List<Convocatoria> listaConvocatoria;

	List<TipoProceso> listaTipoProceso;

	List<TblPlan> listaPlanes;

	List<TipoProceso> listaNombres;

	List<InscripcionesTableroResumen> listaTableResumen;

	List<InscripcionesConsultaResumen> listaFiltrosResumen;

	List<InscripcionesConsultaResumen> listaFiltrosResumenOG;

	// REDIRECCION OPCIONES
	private String paginaActual;

	@PostConstruct
	public void initConsultaProceso() {
		// Reiniciar variables de consulta
		convocatoriaSeleccionada = null;
		procesoSeleccionada = null;
		planSeleccionada = null;
		nombreSeleccionado = null;

		convocatoriaSeleccionada2 = null;
		procesoSeleccionada2 = null;
		fechaInicio = null;
		fechaFin = null;
		filtroEstatus = null;

		// Reiniciar listas
		listaEstatus = Arrays.asList("Activo", "Inactivo"); // Si no es dinámico
		listaNombres = new ArrayList<>();
		listaTableResumen = new ArrayList<>();
		listaFiltrosResumen = new ArrayList<>();
		listaFiltrosResumenOG = new ArrayList<>();

		// Reiniciar objetos
		registroSeleccionado = null;
		tableroParamConsulta = new ConvocatoriaParamConsulta();
		consultaParamConsulta = new ConvocatoriaParamConsulta();

		// Ocultar formulario de edición
		mostrarFormularioEdicion = false;

		logger.info("El bean InscripocionesBean ha sido inicializado.");
	}

	public String navegaNuevoConvocatoria() throws Exception {
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/nuevaInscripcion.xhtml";
		consultarConvocatorias();
		consultaTipoProceso();
		consultarPlan();
		return null; // Mantener en la misma página
	}

	public String navegaConsultaInscripciones() throws Exception {
		this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/cosultaInscripciones.xhtml";
		consultarConvocatorias();
		consultaTipoProceso();
		consultarPlan();
		initConsultaProceso();
		return null; // Mantener en la misma página
	}

	public String navegaConsultaTablero() throws Exception {
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
		
		if(convocatoriaSeleccionada == null || convocatoriaSeleccionada == "" 
			|| procesoSeleccionada == null || procesoSeleccionada == ""
			|| nombreSeleccionado == null || nombreSeleccionado == ""){
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion2').show()");
			logger.info("DIAlog de obligacotrios");
			return;
		}


		listaTableResumen = new ArrayList<InscripcionesTableroResumen>();
		tableroParamConsulta.setValueConvocatoriaEstatus(convocatoriaSeleccionada);
		tableroParamConsulta.setConsulNivelEducativo(procesoSeleccionada);
		tableroParamConsulta.setConsulNombreCorto(nombreSeleccionado);

		listaTableResumen = inscripcionesService.consultarTableroResumen(tableroParamConsulta);

		logger.info("Termina consulta lista tabla resumenn tabla");

	}

	public void consultarFiltros() throws Exception {
		
		if(convocatoriaSeleccionada2 == null || convocatoriaSeleccionada2 == "") {
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion2').show()");
			logger.info("DIAlog de obligacotrios");
			return;
		}

		consultaParamConsulta.setValueConvocatoriaEstatus(convocatoriaSeleccionada2);
		consultaParamConsulta.setConsulNivelEducativo(procesoSeleccionada2);
		consultaParamConsulta.setConsulFechaApertura(fechaInicio);
		consultaParamConsulta.setConsulFechaCierre(fechaFin);

		listaFiltrosResumen = new ArrayList<InscripcionesConsultaResumen>();

		listaFiltrosResumen = inscripcionesService.consultarFiltros(consultaParamConsulta);
		listaFiltrosResumenOG = listaFiltrosResumen;
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

	public void onSelectChangeEstatus() {

		if (filtroEstatus == null) {
			listaFiltrosResumen = listaFiltrosResumenOG;
		} else {
			listaFiltrosResumen = listaFiltrosResumenOG.stream()
					.filter(item -> filtroEstatus.equalsIgnoreCase(item.getEstatus())).collect(Collectors.toList());
		}

	}

	public void limpiarCampos() {
		consultaParamConsulta = new ConvocatoriaParamConsulta();
		convocatoriaSeleccionada2 = null;
		procesoSeleccionada2 = null;
		fechaInicio = null;
		fechaFin = null;
		listaFiltrosResumen = null;
		listaFiltrosResumenOG = null;
	}

	public void habilitarEdicion(InscripcionesConsultaResumen registro) throws Exception {
	    // 1) Parseamos las fechas de inicio y fin desde el registro
	    SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	    try {
	        this.fechaInicioEdit = formatoEntrada.parse(registro.getFecIni());
	        this.fechaFinEdit = formatoEntrada.parse(registro.getFecFin());
	    } catch (ParseException e) {
	        e.printStackTrace();
	        // Manejo de error en caso de no poder parsear
	    }

	    // 2) Validamos si se puede o no editar
	    //    - No se puede editar si la fecha de inicio es hoy
	    //    - O si ya finalizó el periodo activo (la fecha actual es posterior a fechaFinEdit)
	    Date hoy = new Date();

	    // Para comparar únicamente la parte de fecha (día, mes y año), sin considerar horas/minutos
	    SimpleDateFormat formatoDia = new SimpleDateFormat("yyyyMMdd");
	    boolean fechaInicioEsHoy = formatoDia.format(fechaInicioEdit).equals(formatoDia.format(hoy));
	    boolean periodoActivoTerminado = hoy.after(fechaFinEdit);

	    if (fechaInicioEsHoy || periodoActivoTerminado) {
	        // 3) Si NO se puede editar, mostramos el diálogo y salimos del método
	        RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccionEditarBorrar').show()");
	        return;
	    }

	    // 4) Si sí se puede editar, continuamos con la ejecución normal
	    this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/actualizaInscripciones.xhtml";
	    consultarConvocatorias();
	    consultaTipoProceso();
	    consultarPlan();

	    registroSeleccionado = registro;
	    this.mostrarFormularioEdicion = true;
	}

//	public void elminarConvo(InscripcionesConsultaResumen registro) {
//		registroSeleccionado = registro;
//        Long procesoInscripcionId = Long.parseLong(registroSeleccionado.getProcesoInscripcionId());
//		eliminarRegistro(procesoInscripcionId);
//	}

	public void guardarCambios() throws Exception {
	    try {
	        Long procesoInscripcionId = Long.parseLong(registroSeleccionado.getProcesoInscripcionId());
	        String nombre = registroSeleccionado.getNombre();
	        LocalDateTime fechaInicio = fechaInicioEdit.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	        LocalDateTime fechaFin = fechaFinEdit.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	        int estatus = registroSeleccionado.getEstatus().equalsIgnoreCase("Activo") ? 1 : 0;
	        Long idTipoProceso = Long.parseLong(registroSeleccionado.getIdTipoProceso());
	        Long convocatoriaId = Long.parseLong(registroSeleccionado.getIdConvocatoria());

	        inscripcionesService.updateProcesoInscripcion(procesoInscripcionId, nombre, fechaInicio, fechaFin, estatus, idTipoProceso, convocatoriaId);
	        
	        // Cambiar a la página de consulta
	        this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/cosultaInscripciones.xhtml";
	        
	        logger.info("Los cambios se guardaron correctamente.");
	        consultarFiltros();
			RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccion3').show()");

	    } catch (Exception e) {
	        logger.error("Error al guardar los cambios.", e);
	        throw e;
	    }
	}

	public void cancelarEdicion() throws Exception {
	    try {
	        // Cambiar a la página de consulta
			this.paginaActual = "/views/private/gestionAprendizaje/alumnoView/cosultaInscripciones.xhtml";
			consultarFiltros();
	    } catch (Exception e) {
	        logger.error("Error al cancelar la edición.", e);
	        throw e;
	    }
	}
	
	public void prepararEliminar(InscripcionesConsultaResumen registro) {
	    this.registroParaEliminar = registro;
	    SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	    try {
	        this.fechaInicioEdit = formatoEntrada.parse(registro.getFecIni());
	        this.fechaFinEdit = formatoEntrada.parse(registro.getFecFin());
	    } catch (ParseException e) {
	        e.printStackTrace();
	        // Manejo de error en caso de no poder parsear
	    }

	    // 2) Validamos si se puede o no editar
	    //    - No se puede editar si la fecha de inicio es hoy
	    //    - O si ya finalizó el periodo activo (la fecha actual es posterior a fechaFinEdit)
	    Date hoy = new Date();

	    // Para comparar únicamente la parte de fecha (día, mes y año), sin considerar horas/minutos
	    SimpleDateFormat formatoDia = new SimpleDateFormat("yyyyMMdd");
	    boolean fechaInicioEsHoy = formatoDia.format(fechaInicioEdit).equals(formatoDia.format(hoy));
	    boolean periodoActivoTerminado = hoy.after(fechaFinEdit);

	    if (fechaInicioEsHoy || periodoActivoTerminado) {
	        // 3) Si NO se puede editar, mostramos el diálogo y salimos del método
	        RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccionEditarBorrar').show()");
	        return;
	    }
	    
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarEliminar').show()");

	    logger.info("Preparando eliminación para: " + registro.getNombre());
	}
	
	public void eliminarRegistroConfirmado() {
	    try {
	        if (registroParaEliminar != null) {
	            Long procesoInscripcionId = Long.parseLong(registroParaEliminar.getProcesoInscripcionId());
	            inscripcionesService.deleteProcesoInscripcion(procesoInscripcionId);
	            logger.info("Registro eliminado correctamente con ID: " + procesoInscripcionId);
				RequestContext.getCurrentInstance().execute("PF('dlgValidarSeleccionDelete').show()");
	            consultarFiltros(); // Refresca la lista después de eliminar
	        }
	    } catch (Exception e) {
	        logger.error("Error al eliminar el registro.", e);
	    }
	}
	
	public InscripcionesConsultaResumen getRegistroParaEliminar() {
	    return registroParaEliminar;
	}

	public void setRegistroParaEliminar(InscripcionesConsultaResumen registroParaEliminar) {
	    this.registroParaEliminar = registroParaEliminar;
	}



	// Getters y setters para las nuevas variables
	public InscripcionesConsultaResumen getRegistroSeleccionado() {
		return registroSeleccionado;
	}

	public void setRegistroSeleccionado(InscripcionesConsultaResumen registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}

	public boolean isMostrarFormularioEdicion() {
		return mostrarFormularioEdicion;
	}

	public void setMostrarFormularioEdicion(boolean mostrarFormularioEdicion) {
		this.mostrarFormularioEdicion = mostrarFormularioEdicion;
	}

	public List<InscripcionesConsultaResumen> getListaFiltrosResumenOG() {
		return listaFiltrosResumenOG;
	}

	public void setListaFiltrosResumenOG(List<InscripcionesConsultaResumen> listaFiltrosResumenOG) {
		this.listaFiltrosResumenOG = listaFiltrosResumenOG;
	}

	public InscripocionesBean() {
		// Simulación de datos de estatus
		listaEstatus = Arrays.asList("Activo", "Inactivo");
	}

	public String getFiltroEstatus() {
		return filtroEstatus;
	}

	public void setFiltroEstatus(String filtroEstatus) {
		this.filtroEstatus = filtroEstatus;
	}

	public List<String> getListaEstatus() {
		return listaEstatus;
	}

	public void setListaEstatus(List<String> listaEstatus) {
		this.listaEstatus = listaEstatus;
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

	public String getProcesoSeleccionada2() {
		return procesoSeleccionada2;
	}

	public void setProcesoSeleccionada2(String procesoSeleccionada2) {
		this.procesoSeleccionada2 = procesoSeleccionada2;
	}

	public ConvocatoriaParamConsulta getConsultaParamConsulta() {
		return consultaParamConsulta;
	}

	public void setConsultaParamConsulta(ConvocatoriaParamConsulta consultaParamConsulta) {
		this.consultaParamConsulta = consultaParamConsulta;
	}

	public Date getFechaInicioEdit() {
		return fechaInicioEdit;
	}

	public void setFechaInicioEdit(Date fechaInicioEdit) {
		this.fechaInicioEdit = fechaInicioEdit;
	}

	public Date getFechaFinEdit() {
		return fechaFinEdit;
	}

	public void setFechaFinEdit(Date fechaFinEdit) {
		this.fechaFinEdit = fechaFinEdit;
	}

}
