package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import java.io.Serializable;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodleEventoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodlePlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActualizacionCursoMoodleProgramaDTO;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.admin.RelPersonaPlataformaMoodleService;
import mx.gob.sedesol.basegestor.service.gestionescolar.ActualizacionCursoMoodleService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CursoWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.utils.ActualizacionCursoMoodleRutaUtil;
import mx.gob.sedesol.gestorweb.commons.utils.GestorArchivos;

@ManagedBean(name = "actualizacionCursoMoodleBean")
@ViewScoped
public class ActualizacionCursoMoodleBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ActualizacionCursoMoodleBean.class);
	private static final String MENSAJE_ARCHIVO_INVALIDO = "El formato de curso cargado no es el correcto";
	private static final String PREFIJO_ARCHIVO_RESPALDO = "moodle_respaldo_";

	private Long idPlanSeleccionado;
	private Long idProgramaSeleccionado;
	private String anioPeriodoSeleccionado;
	private String numeroElementosSeleccionado;
	private transient UploadedFile archivoCurso;
	private String nombreArchivoSeleccionado;
	private String nombreArchivoRespaldo;
	private String urlArchivoRespaldo;
	private List<SelectItem> planes;
	private List<SelectItem> programas;
	private List<SelectItem> aniosPeriodo;
	private List<SelectItem> numerosElementos;
	private List<ActualizacionCursoMoodleFila> resultados;

	@ManagedProperty(value = "#{actualizacionCursoMoodleService}")
	private ActualizacionCursoMoodleService actualizacionCursoMoodleService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private ParametroSistemaService parametroSistemaService;

	@ManagedProperty(value = "#{parametroWSMoodleService}")
	private ParametroWSMoodleService parametroWSMoodleService;

	@ManagedProperty(value = "#{relPersonaPlataformaMoodleService}")
	private RelPersonaPlataformaMoodleService relPersonaPlataformaMoodleService;

	@ManagedProperty(value = "#{personaService}")
	private PersonaService personaService;

	@PostConstruct
	public void init() {
		cargarCatalogosBase();
		limpiar();
	}

	public void onPlanChange() {
		idProgramaSeleccionado = null;
		resultados = new ArrayList<>();
		if (idPlanSeleccionado == null) {
			programas = Collections.emptyList();
			return;
		}
		programas = convertirProgramas(actualizacionCursoMoodleService.obtenerProgramasPorPlan(idPlanSeleccionado));
	}

	public void limpiar() {
		idPlanSeleccionado = null;
		idProgramaSeleccionado = null;
		anioPeriodoSeleccionado = null;
		numeroElementosSeleccionado = null;
		archivoCurso = null;
		nombreArchivoSeleccionado = null;
		nombreArchivoRespaldo = null;
		urlArchivoRespaldo = null;
		programas = Collections.emptyList();
		resultados = new ArrayList<>();
	}

	public void buscar() {
		if (idPlanSeleccionado == null || idProgramaSeleccionado == null || StringUtils.isBlank(anioPeriodoSeleccionado)
				|| StringUtils.isBlank(numeroElementosSeleccionado)) {
			addMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar todos los criterios de búsqueda.");
			return;
		}
		String periodo = anioPeriodoSeleccionado + "-" + numeroElementosSeleccionado;
		resultados = convertirResultados(actualizacionCursoMoodleService.buscarEventos(idPlanSeleccionado,
				idProgramaSeleccionado, periodo));
		if (resultados.isEmpty()) {
			addMessage(FacesMessage.SEVERITY_INFO, "No se encontraron resultados.");
		}
	}

	public void seleccionarArchivo() {
		if (archivoCurso == null) {
			logger.warn("No se recibio archivo en la carga de respaldo Moodle.");
		} else {
			logger.info("Archivo recibido para respaldo Moodle: " + archivoCurso.getFileName());
		}
		if (!archivoValido()) {
			addMessage(FacesMessage.SEVERITY_WARN, MENSAJE_ARCHIVO_INVALIDO);
			archivoCurso = null;
			nombreArchivoSeleccionado = null;
			nombreArchivoRespaldo = null;
			urlArchivoRespaldo = null;
			return;
		}
		nombreArchivoSeleccionado = archivoCurso.getFileName();
		if (!almacenarArchivoRespaldo()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "No fue posible almacenar el archivo de respaldo.");
			return;
		}
		addMessage(FacesMessage.SEVERITY_INFO, "Archivo seleccionado: " + nombreArchivoSeleccionado);
	}

	public void fusionar() {
		ejecutarRestauracion("merge");
	}

	public void reemplazar() {
		ejecutarRestauracion("replace");
	}

	public boolean isAccionHabilitada() {
		return StringUtils.isNotBlank(urlArchivoRespaldo)
				&& resultados.stream().anyMatch(ActualizacionCursoMoodleFila::isSeleccionado);
	}

	private boolean validarAccionSobreSeleccion() {
		if (StringUtils.isBlank(nombreArchivoSeleccionado) || StringUtils.isBlank(urlArchivoRespaldo)
				|| StringUtils.isBlank(nombreArchivoRespaldo)) {
			addMessage(FacesMessage.SEVERITY_WARN, MENSAJE_ARCHIVO_INVALIDO);
			return false;
		}
		if (resultados.stream().noneMatch(ActualizacionCursoMoodleFila::isSeleccionado)) {
			addMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar al menos un curso.");
			return false;
		}
		return true;
	}

	private void ejecutarRestauracion(String mode) {
		if (!validarAccionSobreSeleccion()) {
			return;
		}

		List<ActualizacionCursoMoodleFila> filasSeleccionadas = resultados.stream()
				.filter(ActualizacionCursoMoodleFila::isSeleccionado).collect(Collectors.toList());
		List<Integer> courseIds = filasSeleccionadas.stream()
				.map(ActualizacionCursoMoodleFila::getIdCursoMoodle).filter(id -> id != null && id > 0)
				.collect(Collectors.toList());
		int seleccionadosSinCursoMoodle = filasSeleccionadas.size() - courseIds.size();
		if (courseIds.isEmpty()) {
			addMessage(FacesMessage.SEVERITY_WARN,
					"Los cursos seleccionados no cuentan con id de curso Moodle para realizar la acción.");
			return;
		}
		if (seleccionadosSinCursoMoodle > 0) {
			addMessage(FacesMessage.SEVERITY_WARN,
					"Se omitieron " + seleccionadosSinCursoMoodle
							+ " cursos seleccionados porque no cuentan con id de curso Moodle.");
		}

		ParametroWSMoodleDTO plataforma = parametroWSMoodleService.obtenerMoodleActivo();
		if (plataforma == null || plataforma.getIdParametroWSMoodle() == null) {
			addMessage(FacesMessage.SEVERITY_ERROR, "No existe una plataforma Moodle activa configurada.");
			return;
		}

		PersonaDTO persona = personaService.buscarPorId(getUsuarioEnSession().getIdPersona());
		if (persona == null) {
			addMessage(FacesMessage.SEVERITY_ERROR, "No fue posible obtener la persona en sesión.");
			return;
		}

		Integer userIdMoodle = relPersonaPlataformaMoodleService.obtenerIdMoodle(persona, plataforma, idPersonaEnSesion());
		if (userIdMoodle == null || userIdMoodle <= 0) {
			addMessage(FacesMessage.SEVERITY_ERROR, "No fue posible obtener el usuario Moodle del usuario en sesión.");
			return;
		}

		try {
			CursoWS cursoWS = new CursoWS(plataforma);
			logger.info("Cursos enviados a restauracion Moodle: " + courseIds);
			String respuesta = cursoWS.restauracionMasivaCurso(nombreArchivoRespaldo, urlArchivoRespaldo,
					userIdMoodle, mode, courseIds);
			logger.info("Respuesta restauracion masiva Moodle: " + respuesta);
			addMessage(FacesMessage.SEVERITY_INFO,
					"Se ejecutó la solicitud de " + ("merge".equals(mode) ? "fusión" : "reemplazo")
							+ " hacia Moodle.");
		} catch (ErrorWS e) {
			logger.error("Error al ejecutar restauracion masiva Moodle", e);
			addMessage(FacesMessage.SEVERITY_ERROR,
					StringUtils.isNotBlank(e.getMessage()) ? e.getMessage()
							: "Ocurrió un error al invocar el servicio de Moodle.");
		} catch (Exception e) {
			logger.error("Error inesperado al ejecutar restauracion masiva Moodle", e);
			addMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al invocar el servicio de Moodle.");
		}
	}

	private boolean archivoValido() {
		return archivoCurso != null && StringUtils.isNotBlank(archivoCurso.getFileName())
				&& archivoCurso.getFileName().toLowerCase().endsWith(".mbz");
	}

	private boolean almacenarArchivoRespaldo() {
		File directorioRespaldos = ActualizacionCursoMoodleRutaUtil.obtenerDirectorioRespaldos(parametroSistemaService);
		String rutaDirectorio = directorioRespaldos.getAbsolutePath() + File.separator;
		String nombreGenerado = PREFIJO_ARCHIVO_RESPALDO + UUID.randomUUID().toString() + ".mbz";
		String rutaArchivo = rutaDirectorio + nombreGenerado;
		if (!directorioRespaldos.exists() || !directorioRespaldos.isDirectory() || !directorioRespaldos.canWrite()) {
			logger.error("No fue posible crear o utilizar la carpeta de respaldos Moodle: " + rutaDirectorio);
			return false;
		}

		if (!GestorArchivos.almacenarArchivo(rutaArchivo, archivoCurso.getContents()).getResultado().getValor()) {
			logger.error("No fue posible almacenar el respaldo Moodle en: " + rutaArchivo);
			return false;
		}

		nombreArchivoRespaldo = nombreGenerado;
		urlArchivoRespaldo = obtenerUrlPublicaRespaldos() + nombreGenerado;
		archivoCurso = null;
		logger.info("Respaldo Moodle almacenado en: " + rutaArchivo);
		logger.info("URL publica generada para respaldo Moodle: " + urlArchivoRespaldo);
		return true;
	}

	private String obtenerUrlPublicaRespaldos() {
		String rutaPublicaConfigurada = parametroSistemaService
				.obtenerParametro(ConstantesGestor.PARAMETRO_URL_PUBLICA_RESPALDOS_MOODLE);
		if (StringUtils.isNotBlank(rutaPublicaConfigurada)) {
			return StringUtils.appendIfMissing(rutaPublicaConfigurada.trim(), "/");
		}
		StringBuilder ruta = new StringBuilder();
		ruta.append(FacesContext.getCurrentInstance().getExternalContext().getRequestScheme()).append("://");
		ruta.append(FacesContext.getCurrentInstance().getExternalContext().getRequestServerName()).append(":");
		ruta.append(FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort());
		ruta.append(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
		ruta.append("/ws/public/respaldos-moodle/");
		return ruta.toString();
	}

	private void cargarCatalogosBase() {
		planes = convertirPlanes(actualizacionCursoMoodleService.obtenerPlanes());
		aniosPeriodo = construirAniosPeriodo();
		numerosElementos = construirNumerosElementos();
	}

	private List<SelectItem> convertirPlanes(List<ActualizacionCursoMoodlePlanDTO> planesDisponibles) {
		List<SelectItem> items = new ArrayList<>();
		for (ActualizacionCursoMoodlePlanDTO plan : planesDisponibles) {
			items.add(new SelectItem(plan.getIdPlan(), plan.getNombre()));
		}
		return items;
	}

	private List<SelectItem> convertirProgramas(List<ActualizacionCursoMoodleProgramaDTO> programasDisponibles) {
		List<SelectItem> items = new ArrayList<>();
		for (ActualizacionCursoMoodleProgramaDTO programa : programasDisponibles) {
			StringBuilder label = new StringBuilder();
			if (StringUtils.isNotBlank(programa.getClavePrograma())) {
				label.append(programa.getClavePrograma()).append(" - ");
			}
			label.append(programa.getNombreTentativo());
			items.add(new SelectItem(programa.getIdPrograma(), label.toString()));
		}
		return items;
	}

	private List<SelectItem> construirAniosPeriodo() {
		List<SelectItem> items = new ArrayList<>();
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		for (int anio = anioActual + 5; anio >= anioActual - 10; anio--) {
			items.add(new SelectItem(String.valueOf(anio), String.valueOf(anio)));
		}
		return items;
	}

	private List<SelectItem> construirNumerosElementos() {
		List<SelectItem> items = new ArrayList<>();
		for (int numero = 1; numero <= 12; numero++) {
			items.add(new SelectItem(String.valueOf(numero), String.valueOf(numero)));
		}
		return items;
	}

	private List<ActualizacionCursoMoodleFila> convertirResultados(List<ActualizacionCursoMoodleEventoDTO> eventos) {
		List<ActualizacionCursoMoodleFila> filas = new ArrayList<>();
		for (ActualizacionCursoMoodleEventoDTO evento : eventos) {
			filas.add(new ActualizacionCursoMoodleFila(evento));
		}
		return filas;
	}

	private void addMessage(FacesMessage.Severity severity, String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));
	}

	public Long getIdPlanSeleccionado() {
		return idPlanSeleccionado;
	}

	public void setIdPlanSeleccionado(Long idPlanSeleccionado) {
		this.idPlanSeleccionado = idPlanSeleccionado;
	}

	public Long getIdProgramaSeleccionado() {
		return idProgramaSeleccionado;
	}

	public void setIdProgramaSeleccionado(Long idProgramaSeleccionado) {
		this.idProgramaSeleccionado = idProgramaSeleccionado;
	}

	public String getAnioPeriodoSeleccionado() {
		return anioPeriodoSeleccionado;
	}

	public void setAnioPeriodoSeleccionado(String anioPeriodoSeleccionado) {
		this.anioPeriodoSeleccionado = anioPeriodoSeleccionado;
	}

	public String getNumeroElementosSeleccionado() {
		return numeroElementosSeleccionado;
	}

	public void setNumeroElementosSeleccionado(String numeroElementosSeleccionado) {
		this.numeroElementosSeleccionado = numeroElementosSeleccionado;
	}

	public UploadedFile getArchivoCurso() {
		return archivoCurso;
	}

	public void setArchivoCurso(UploadedFile archivoCurso) {
		this.archivoCurso = archivoCurso;
	}

	public String getNombreArchivoSeleccionado() {
		return nombreArchivoSeleccionado;
	}

	public void setNombreArchivoSeleccionado(String nombreArchivoSeleccionado) {
		this.nombreArchivoSeleccionado = nombreArchivoSeleccionado;
	}

	public String getNombreArchivoRespaldo() {
		return nombreArchivoRespaldo;
	}

	public void setNombreArchivoRespaldo(String nombreArchivoRespaldo) {
		this.nombreArchivoRespaldo = nombreArchivoRespaldo;
	}

	public String getUrlArchivoRespaldo() {
		return urlArchivoRespaldo;
	}

	public void setUrlArchivoRespaldo(String urlArchivoRespaldo) {
		this.urlArchivoRespaldo = urlArchivoRespaldo;
	}

	public List<SelectItem> getPlanes() {
		return planes;
	}

	public void setPlanes(List<SelectItem> planes) {
		this.planes = planes;
	}

	public List<SelectItem> getProgramas() {
		return programas;
	}

	public void setProgramas(List<SelectItem> programas) {
		this.programas = programas;
	}

	public List<SelectItem> getAniosPeriodo() {
		return aniosPeriodo;
	}

	public void setAniosPeriodo(List<SelectItem> aniosPeriodo) {
		this.aniosPeriodo = aniosPeriodo;
	}

	public List<SelectItem> getNumerosElementos() {
		return numerosElementos;
	}

	public void setNumerosElementos(List<SelectItem> numerosElementos) {
		this.numerosElementos = numerosElementos;
	}

	public List<ActualizacionCursoMoodleFila> getResultados() {
		return resultados;
	}

	public void setResultados(List<ActualizacionCursoMoodleFila> resultados) {
		this.resultados = resultados;
	}

	public ActualizacionCursoMoodleService getActualizacionCursoMoodleService() {
		return actualizacionCursoMoodleService;
	}

	public void setActualizacionCursoMoodleService(ActualizacionCursoMoodleService actualizacionCursoMoodleService) {
		this.actualizacionCursoMoodleService = actualizacionCursoMoodleService;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public ParametroWSMoodleService getParametroWSMoodleService() {
		return parametroWSMoodleService;
	}

	public void setParametroWSMoodleService(ParametroWSMoodleService parametroWSMoodleService) {
		this.parametroWSMoodleService = parametroWSMoodleService;
	}

	public RelPersonaPlataformaMoodleService getRelPersonaPlataformaMoodleService() {
		return relPersonaPlataformaMoodleService;
	}

	public void setRelPersonaPlataformaMoodleService(
			RelPersonaPlataformaMoodleService relPersonaPlataformaMoodleService) {
		this.relPersonaPlataformaMoodleService = relPersonaPlataformaMoodleService;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public static class ActualizacionCursoMoodleFila implements Serializable {

		private static final long serialVersionUID = 1L;

		private Integer idEvento;
		private Integer idCursoMoodle;
		private Long idPrograma;
		private String eventoCurso;
		private String tipoAsignatura;
		private String semestre;
		private String bloque;
		private boolean seleccionado;

		public ActualizacionCursoMoodleFila(ActualizacionCursoMoodleEventoDTO evento) {
			this.idEvento = evento.getIdEvento();
			this.idCursoMoodle = evento.getIdCursoMoodle();
			this.idPrograma = evento.getIdPrograma();
			this.eventoCurso = evento.getEventoCurso();
			this.tipoAsignatura = evento.getTipoAsignatura();
			this.semestre = evento.getSemestre();
			this.bloque = evento.getBloque();
		}

		public Integer getIdEvento() {
			return idEvento;
		}

		public Integer getIdCursoMoodle() {
			return idCursoMoodle;
		}

		public Long getIdPrograma() {
			return idPrograma;
		}

		public String getEventoCurso() {
			return eventoCurso;
		}

		public String getTipoAsignatura() {
			return tipoAsignatura;
		}

		public String getSemestre() {
			return semestre;
		}

		public String getBloque() {
			return bloque;
		}

		public boolean isSeleccionado() {
			return seleccionado;
		}

		public void setSeleccionado(boolean seleccionado) {
			this.seleccionado = seleccionado;
		}

		public boolean isTieneCursoMoodle() {
			return idCursoMoodle != null && idCursoMoodle > 0;
		}
	}
}
