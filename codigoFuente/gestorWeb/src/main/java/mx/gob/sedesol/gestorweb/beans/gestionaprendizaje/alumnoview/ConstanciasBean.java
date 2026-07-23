package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.PlantillaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EstatusDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EventoConstanciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TiraMateriaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.FichaIntegralCasoDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoDocumentoEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.PlantillaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ExpedienteCurricularV2Facade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.TrayectoriaAcademicaContextoBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

@SessionScoped
@ManagedBean
public class ConstanciasBean extends BaseBean {

	/**
	 *
	 */
	private static final Logger log = Logger.getLogger(ConstanciasBean.class);

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{grupoParticipanteService}")
	private GrupoParticipanteService grupoParticipanteService;

	@ManagedProperty(value = "#{plantillaService}")
	private PlantillaService plantillaService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private ParametroSistemaService parametrosSistemaService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	@ManagedProperty("#{trayectoriaAcademicaContextoBean}")
	private TrayectoriaAcademicaContextoBean trayectoriaAcademicaContextoBean;

	@ManagedProperty(value = "#{asistenteInscripcionServiceImpl}")
	private AsistenteInscripcionService asistenteInscripcionService;

	@ManagedProperty(value = "#{expedienteCurricularV2Facade}")
	private ExpedienteCurricularV2Facade expedienteCurricularV2Facade;

	private List<EventoConstanciaDTO> eventos;
	private Long idPersona;
	private StreamedContent reportePDF;
	private StreamedContent constanciaPDF;
	private EventoConstanciaDTO eventoSeleccionado;
	private HistorialAcademicoDTO historialAcademico;
	private AsistenteInscripcionContextoDTO contextoAsistente;
	private FichaIntegralCasoDTO fichaIntegralV2;
	private boolean vistaGestor;
	private String nombrePersonaObjetivo;
	private String matriculaPersonaObjetivo;


	
	



	

	@PostConstruct
	public void init() {
		idPersona = trayectoriaAcademicaContextoBean.resolverIdPersonaObjetivo(getUsuarioEnSession().getIdPersona());
		vistaGestor = trayectoriaAcademicaContextoBean.isVistaGestor();
		nombrePersonaObjetivo = trayectoriaAcademicaContextoBean.resolverNombreObjetivo(getUsuarioEnSession().getUsuario());
		matriculaPersonaObjetivo = trayectoriaAcademicaContextoBean.resolverMatriculaObjetivo(getUsuarioEnSession().getUsuario());
		eventos = grupoParticipanteService.getParticipanteByActaCerradaYconstancia2(idPersona);
		historialAcademico = grupoParticipanteService.consultaDatosHistorialAcademico(idPersona.toString());
		cargarContextoAsistente();
		cargarFichaIntegralV2();

	}

	private void cargarContextoAsistente() {
		contextoAsistente = null;
		if (asistenteInscripcionService == null || idPersona == null) {
			return;
		}
		try {
			contextoAsistente = asistenteInscripcionService.obtenerContextoAsistido(idPersona);
		} catch (InscripcionException e) {
			log.warn("No fue posible obtener el contexto asistido para historial académico.", e);
		} catch (Exception e) {
			log.warn("Error inesperado al obtener el contexto asistido para historial académico.", e);
		}
	}

	private void cargarFichaIntegralV2() {
		fichaIntegralV2 = null;
		if (expedienteCurricularV2Facade == null || idPersona == null) {
			return;
		}
		try {
			fichaIntegralV2 = expedienteCurricularV2Facade.obtenerContextoExpediente(construirContextoV2());
		} catch (InscripcionException e) {
			log.warn("No fue posible cargar la ficha integral V2 para historial académico.", e);
		} catch (Exception e) {
			log.warn("Error inesperado al cargar la ficha integral V2 para historial académico.", e);
		}
	}

	private void asegurarFichaIntegralV2() {
		if (fichaIntegralV2 != null) {
			return;
		}
		cargarFichaIntegralV2();
	}

	private ContextoAsistenteCurricularV2DTO construirContextoV2() {
		ContextoAsistenteCurricularV2DTO contextoV2 = new ContextoAsistenteCurricularV2DTO();
		contextoV2.setIdPersonaObjetivo(idPersona);
		contextoV2.setIdPersonaConsulta(idPersonaEnSesion());
		contextoV2.setPerfilConsulta(vistaGestor ? "GESTOR" : "ESTUDIANTE");
		contextoV2.setVistaGestor(Boolean.valueOf(vistaGestor));
		contextoV2.setPeriodoOperativo(esPeriodoCursamiento() ? "CURSAMIENTO" : "INSCRIPCION");
		contextoV2.setOrigenConsulta("EXPEDIENTE");
		contextoV2.setIdPlan(resolverIdPlanV2());
		return contextoV2;
	}

	private Long resolverIdPlanV2() {
		if (contextoAsistente == null) {
			return null;
		}
		InscripcionContextoDTO contextoBase = contextoAsistente.getContextoBase();
		if (contextoBase == null || contextoBase.getInscripcionPersona() == null) {
			return null;
		}
		return contextoBase.getInscripcionPersona().getIdPlan();
	}

	

	public List<EventoConstanciaDTO> getEventosAdmin( Long id){

		eventos = grupoParticipanteService.getParticipanteByActaCerradaYconstancia2(id);

		return eventos;

	}

	public HistorialAcademicoDTO getHistorialAdmin( Long id){

		historialAcademico = grupoParticipanteService.consultaDatosHistorialAcademico(id.toString());

		return historialAcademico;

	}
	

	public void generarReporte() {

		String rutaFondoConstancia = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
				+ "/recursos/documentos/fondoConstancia.png";

		reportePDF = null;

		ReporteConfig reporteConfig = new ReporteConfig();
		reporteConfig.setDatos(eventos);
		reporteConfig.setNombreReporte("Plantilla_SISI");
		reporteConfig.setPathJasper("/resources/jasperReport/gestionAprendizaje/expediente_academico.jasper");

		reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);

		HashMap<String, Object> params = new HashMap<>();
		
		
		//Parametros de Encabezado
		
		String LOGO_UNADM = "/resources/jasperReport/LOGO_EDU_UNADM.png";
		InputStream strmLOGO_UNADM = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(LOGO_UNADM);
		String LOGO_SEP = "/resources/jasperReport/LOGO_SEP.png";
		InputStream strmLOGO_SEP = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(LOGO_SEP);

		log.info("Nombre:"+historialAcademico.getNombre());
		log.info("Matri:"+historialAcademico.getMatricula());
		log.info("ClaveInst:"+historialAcademico.getClaveInst());
		log.info("Proga:"+historialAcademico.getProgramaEducativo());
		log.info("Clave:"+historialAcademico.getClave());
		log.info("Total Cred;"+historialAcademico.getTotalCreditos());
		log.info("Nivel:"+historialAcademico.getNivel());
		log.info("Promedio:"+historialAcademico.getPromedio());
		log.info("Credi:"+historialAcademico.getCreditos());
		log.info("Aprob: "+historialAcademico.getAprobadas());
		log.info("Reprob: "+historialAcademico.getReprobadas());
		log.info("NoPresen: "+historialAcademico.getNopresentadas());
		log.info("Total: "+historialAcademico.getTotal());
		log.info("Fecha: "+historialAcademico.getFechaConsulta());

		params.put("LOGO_UNADM",strmLOGO_UNADM);
		params.put("LOGO_SEP",strmLOGO_SEP);
		params.put("NOMBRE", historialAcademico.getNombre());
		params.put("MATRICULA",historialAcademico.getMatricula());
		params.put("CLAVE_INSTITUCION",historialAcademico.getClaveInst());
		params.put("PROG_EDUCATIVO",historialAcademico.getProgramaEducativo());
		params.put("CLAVE", historialAcademico.getClave());
		params.put("NIVEL", historialAcademico.getNivel());
		params.put("PROMEDIO", historialAcademico.getPromedio().intValue());
		params.put("CREDITOS", historialAcademico.getCreditos().intValue());
		params.put("TOTAL_CREDITOS",historialAcademico.getTotalCreditos().intValue());
		params.put("APROBADAS", historialAcademico.getAprobadas().intValue());
		params.put("REPROBADAS", historialAcademico.getReprobadas().intValue());
		params.put("NO_PRESENTADAS", historialAcademico.getNopresentadas().intValue());
		params.put("TOTAL", historialAcademico.getTotal().intValue());
		params.put("FECHA_CONSULTA", historialAcademico.getFechaConsulta());
		
		
		//Parametros de datos
		JRBeanArrayDataSource dsHistorial = new JRBeanArrayDataSource(eventos.toArray());
		params.put("dsHistorial", dsHistorial);
		
		reporteConfig.setParametros(params);

		setReportePDF(ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig), "application/pdf",
				historialAcademico.getMatricula()+"_Historial_academico"));
		RequestContext.getCurrentInstance().update("reporte");

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "GEN_EXP_ACA_PDF", "", requestActual(),
				TipoServicioEnum.LOCAL);

	}

	public void generarConstancia() {

		String rutaFondoConstancia = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
				+ parametrosSistemaService.obtenerParametro(ConstantesGestor.PARAMETRO_RUTA_UNDERTOW)
				+ parametrosSistemaService.obtenerParametro(ConstantesGestor.PARAMETRO_RUTA_IMAGENES_DOCUMENTOS);

		constanciaPDF = null;
		ReporteConfig reporteConfig = new ReporteConfig();
		reporteConfig.setDatos(null);
		reporteConfig.setNombreReporte("Constancia");
		PlantillaDTO plantilla;
		if (eventoSeleccionado.getTipoConstancia().intValue() == 1) {
			plantilla = plantillaService.obtenerPlantillaPorTipoDocumento(TipoDocumentoEnum.CONSTANCIA_ACREDITACION);
			reporteConfig.setPathJasper("/resources/jasperReport/gestionAprendizaje/constanciaAcreditacion.jasper");
		} else {
			plantilla = plantillaService.obtenerPlantillaPorTipoDocumento(TipoDocumentoEnum.CONSTANCIA_PARTICIPACION);
			reporteConfig.setPathJasper("/resources/jasperReport/gestionAprendizaje/constanciaParticipacion.jasper");
		}

		if (ObjectUtils.isNotNull(plantilla)) {
			String parrafo1 = insertaVariablesEnParrafo(plantilla.getParrafo1());
			String parrafo2 = insertaVariablesEnParrafo(plantilla.getParrafo2());
			String parrafo3 = insertaVariablesEnParrafo(plantilla.getParrafo3());

			reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);
			HashMap<String, Object> params = new HashMap<>();

			/**/
			params.put("P_NOMBRE_ACREDITADO", eventoSeleccionado.getNombreAcreditado().toUpperCase());
			params.put("P_NOMBRE_PROGRAMA", eventoSeleccionado.getNombreEc());
			params.put("P_PARRAFO1", parrafo1);
			params.put("P_PARRAFO2", parrafo2);
			params.put("P_PARRAFO3", parrafo3);
			params.put("P_DIRECTOR_GRAL", eventoSeleccionado.getDirectorGral());

			params.put("P_IMAGEN", rutaFondoConstancia + plantilla.getImagenFondo());

			reporteConfig.setParametros(params);
			constanciaPDF = ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig), "application/pdf",
					"constancia");
			RequestContext.getCurrentInstance().execute("PF('modalConstancia').show()");
			RequestContext.getCurrentInstance().update("constancia");
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_CON_EXP_ACA",
					String.valueOf(idPersona), requestActual(), TipoServicioEnum.LOCAL);
		} else {
			agregarMsgInfo("No hay una plantilla predefinida para la constancia.", null);
		}

	}

	private String insertaVariablesEnParrafo(String parrafo) {
		String parrafoSalida = parrafo;
		parrafoSalida = parrafoSalida.replace("$_calificacion_", eventoSeleccionado.getCalificacion());
		parrafoSalida = parrafoSalida.replace("$_modalidad_", eventoSeleccionado.getModalidad());
		parrafoSalida = parrafoSalida.replace("$_lugar_", eventoSeleccionado.getDireccion());
		parrafoSalida = parrafoSalida.replace("$_fecha_",
				DateUtils.formatoFechaConstancia(eventoSeleccionado.getFecha()));
		parrafoSalida = parrafoSalida.replace("$_num_horas_", eventoSeleccionado.getDuracionHrs());
		return parrafoSalida;
	}
	
	public HistorialAcademicoDTO getHistorialAcademico() {
		return historialAcademico;
	}

	public boolean isVistaGestor() {
		return vistaGestor;
	}

	public String getNombrePersonaObjetivo() {
		if (historialAcademico != null && ObjectUtils.isNotNull(historialAcademico.getNombre())) {
			return historialAcademico.getNombre();
		}
		return nombrePersonaObjetivo;
	}

	public String getMatriculaPersonaObjetivo() {
		if (historialAcademico != null && ObjectUtils.isNotNull(historialAcademico.getMatricula())) {
			return historialAcademico.getMatricula();
		}
		return matriculaPersonaObjetivo;
	}

	public boolean esPeriodoCursamiento() {
		return contextoAsistente != null && Boolean.TRUE.equals(contextoAsistente.getInscripcionVigente());
	}

	public String getEtiquetaPeriodoContextual() {
		return esPeriodoCursamiento() ? "Periodo de cursamiento activo" : "Periodo de inscripción/reinscripción";
	}

	public String getEtiquetaModoContextual() {
		if (vistaGestor) {
			return esPeriodoCursamiento() ? "Seguimiento" : "Gestión";
		}
		return esPeriodoCursamiento() ? "Consulta" : "Operativo";
	}

	public String getEtiquetaEstadoPeriodo() {
		return esPeriodoCursamiento() ? "En curso" : "Activo";
	}

	public String getTituloVistaHistorial() {
		return vistaGestor ? "Historial académico estudiante | Vista gestor" : "Historial académico";
	}

	public String getMensajeContextualHistorial() {
		return esPeriodoCursamiento()
				? "Esta vista muestra la evidencia histórica del estudiante mientras cursa el periodo activo. La interpretación normativa vive en la malla y el asistente."
				: "Esta vista muestra la trayectoria consolidada del estudiante. Usa la malla y el asistente para entender cómo ese historial impacta la inscripción activa.";
	}

	public String getEtiquetaRolHistorial() {
		return "Vista de evidencia académica";
	}

	public String getMensajeTransicionAsistente() {
		return esPeriodoCursamiento()
				? "¿Quieres entender cómo este historial condiciona el seguimiento del periodo vigente? Consulta el Asistente de inscripción curricular."
				: "¿Tienes dudas sobre cómo este historial afecta tu inscripción? Consulta el Asistente de inscripción curricular.";
	}

	public String navegaMallaCurricularAlumno() {
		trayectoriaAcademicaContextoBean.limpiarContextoAsistente();
		return ConstantesGestorWeb.NAVEGA_MALLA_CURRICULAR_ALUMNO;
	}

	public String navegaAsistenteCurricular() {
		trayectoriaAcademicaContextoBean.configurarContextoAsistenteDesdeHistorial();
		return ConstantesGestorWeb.NAVEGA_TABLA_CURRICULAR_ASISTIDA;
	}

	public FichaIntegralCasoDTO getFichaIntegralV2() {
		asegurarFichaIntegralV2();
		return fichaIntegralV2;
	}

	public boolean isTieneFichaIntegralV2() {
		asegurarFichaIntegralV2();
		return fichaIntegralV2 != null;
	}

	public boolean isMostrarSeguimientoCasoEstudianteV2() {
		asegurarFichaIntegralV2();
		return !vistaGestor && fichaIntegralV2 != null;
	}

	public String getEstatusCasoEstudianteV2() {
		asegurarFichaIntegralV2();
		if (fichaIntegralV2 == null || fichaIntegralV2.getCasoAcademico() == null
				|| StringUtils.isBlank(fichaIntegralV2.getCasoAcademico().getEstatusCaso())) {
			return "Orientación general";
		}
		return fichaIntegralV2.getCasoAcademico().getEstatusCaso().trim();
	}

	public String getMensajeCasoEstudianteV2() {
		asegurarFichaIntegralV2();
		if (fichaIntegralV2 == null || StringUtils.isBlank(fichaIntegralV2.getMensajeEstudiante())) {
			return "Tu trayectoria no muestra un riesgo académico inmediato en este momento.";
		}
		return fichaIntegralV2.getMensajeEstudiante().trim();
	}

	public String getResolucionCasoEstudianteV2() {
		asegurarFichaIntegralV2();
		if (fichaIntegralV2 == null || StringUtils.isBlank(fichaIntegralV2.getAccionSugerida())) {
			return "Mantén tu avance actual y vuelve a revisar tu orientación cuando cambie el periodo.";
		}
		return fichaIntegralV2.getAccionSugerida().trim();
	}


	public void setHistorialAcademico(HistorialAcademicoDTO historialAcademico) {
		this.historialAcademico = historialAcademico;
	}

	public List<EventoConstanciaDTO> getEventos() {
		return eventos;
	}

	public void setEventos(List<EventoConstanciaDTO> eventos) {
		this.eventos = eventos;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
	}

	public StreamedContent getConstanciaPDF() {
		return constanciaPDF;
	}

	public void setPlantillaPDF(StreamedContent constanciaPDF) {
		this.constanciaPDF = constanciaPDF;
	}

	public EventoConstanciaDTO getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	public void setEventoSeleccionado(EventoConstanciaDTO eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}

	public StreamedContent getReportePDF() {
		return reportePDF;
	}

	public void setReportePDF(StreamedContent reportePDF) {
		this.reportePDF = reportePDF;
	}

	public PlantillaService getPlantillaService() {
		return plantillaService;
	}

	public void setPlantillaService(PlantillaService plantillaService) {
		this.plantillaService = plantillaService;
	}

	public ParametroSistemaService getParametrosSistemaService() {
		return parametrosSistemaService;
	}

	public void setParametrosSistemaService(ParametroSistemaService parametrosSistemaService) {
		this.parametrosSistemaService = parametrosSistemaService;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public TrayectoriaAcademicaContextoBean getTrayectoriaAcademicaContextoBean() {
		return trayectoriaAcademicaContextoBean;
	}

	public void setTrayectoriaAcademicaContextoBean(TrayectoriaAcademicaContextoBean trayectoriaAcademicaContextoBean) {
		this.trayectoriaAcademicaContextoBean = trayectoriaAcademicaContextoBean;
	}

	public AsistenteInscripcionService getAsistenteInscripcionService() {
		return asistenteInscripcionService;
	}

	public void setAsistenteInscripcionService(AsistenteInscripcionService asistenteInscripcionService) {
		this.asistenteInscripcionService = asistenteInscripcionService;
	}

	public ExpedienteCurricularV2Facade getExpedienteCurricularV2Facade() {
		return expedienteCurricularV2Facade;
	}

	public void setExpedienteCurricularV2Facade(ExpedienteCurricularV2Facade expedienteCurricularV2Facade) {
		this.expedienteCurricularV2Facade = expedienteCurricularV2Facade;
	}



	

}
