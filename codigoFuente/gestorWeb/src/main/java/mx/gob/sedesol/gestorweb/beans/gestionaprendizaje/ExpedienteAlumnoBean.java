package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PlantillaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EventoConstanciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoDocumentoEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.PlantillaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview.ConstanciasBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

@SessionScoped
@ManagedBean
public class ExpedienteAlumnoBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ExpedienteAlumnoBean.class);

	@ManagedProperty(value = "#{grupoParticipanteService}")
	private GrupoParticipanteService grupoParticipanteService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private ParametroSistemaService parametrosSistemaService;

	@ManagedProperty(value = "#{plantillaService}")
	private PlantillaService plantillaService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;
	
	@ManagedProperty("#{constanciasBean}")
	ConstanciasBean constanciasBean;
	
	public ConstanciasBean getConstanciasBean() {
		return constanciasBean;
	}

	public void setConstanciasBean(ConstanciasBean constanciasBean) {
		this.constanciasBean = constanciasBean;
	}

	private List<EventoConstanciaDTO> eventos;
	private PersonaDTO personaDTO;

	private EventoConstanciaDTO eventoSeleccionado;

	private StreamedContent constanciaPDF;
	
	// ITTIVA
	private HistorialAcademicoDTO historial;	
	private List<EventoConstanciaDTO> listaEventos;
	private StreamedContent reportePDF;

	public ExpedienteAlumnoBean() {
		personaDTO = new PersonaDTO();
		eventos = new ArrayList<>();
	}

	@PostConstruct
	public void init() {

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

			params.put("P_NOMBRE_ACREDITADO", eventoSeleccionado.getNombreAcreditado().toUpperCase());
			params.put("P_NOMBRE_PROGRAMA", eventoSeleccionado.getNombreEc().toUpperCase());
			params.put("P_PARRAFO1", parrafo1);
			params.put("P_PARRAFO2", parrafo2);
			params.put("P_PARRAFO3", parrafo3);
			params.put("P_DIRECTOR_GRAL", eventoSeleccionado.getDirectorGral());

			params.put("P_IMAGEN", rutaFondoConstancia + plantilla.getImagenFondo());

			reporteConfig.setParametros(params);
			constanciaPDF = ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig), "application/pdf",
					"Constancia");
			RequestContext.getCurrentInstance().execute("PF('modalConstancia').show()");
			RequestContext.getCurrentInstance().update("constancia");
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_CON_ALM", String.valueOf(personaDTO.getIdPersona()),
					requestActual(), TipoServicioEnum.LOCAL);
		} else {
			agregarMsgInfo("No hay una plantilla predefinida para la constancia.", null);
		}

	}

	public String navegaExpedienteAlumno() {
	
		Long id = personaDTO.getIdPersona();
		
		log.info("id persona xxx : " + id);
		
		listaEventos = new ArrayList<>();
		listaEventos = constanciasBean.getEventosAdmin(id);
		
		historial = new HistorialAcademicoDTO();		
		historial = constanciasBean.getHistorialAdmin(id);
				
		return ConstantesGestorWeb.NAVEGA_CONSTANCIAS_ADMIN;
		
		// funcionalidad anterior
//		eventos = grupoParticipanteService.getParticipanteByActaCerradaYconstancia(personaDTO.getIdPersona());
//		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_EXP_ALM", String.valueOf(personaDTO.getIdPersona()),
//				requestActual(), TipoServicioEnum.LOCAL);

		//return ConstantesGestorWeb.NAVEGA_EXPEDIENTE_ALUMNO;
		
	}

	public void generarReporte() {

		String rutaFondoConstancia = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
				+ "/recursos/documentos/fondoConstancia.png";

		reportePDF = null;

		ReporteConfig reporteConfig = new ReporteConfig();
		reporteConfig.setDatos(listaEventos);
		reporteConfig.setNombreReporte("Plantilla_SISI");
		reporteConfig.setPathJasper("/resources/jasperReport/gestionAprendizaje/expediente_academico.jasper");

		reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);

		HashMap<String, Object> params = new HashMap<>();
		
		
		//Parametros de Encabezado
		
		String LOGO_UNADM = "/resources/jasperReport/LOGO_EDU_UNADM.png";
		InputStream strmLOGO_UNADM = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(LOGO_UNADM);
		String LOGO_SEP = "/resources/jasperReport/LOGO_SEP.png";
		InputStream strmLOGO_SEP = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(LOGO_SEP);

		log.info("Nombre:"+historial.getNombre());
		log.info("Matri:"+historial.getMatricula());
		log.info("ClaveInst:"+historial.getClaveInst());
		log.info("Proga:"+historial.getProgramaEducativo());
		log.info("Clave:"+historial.getClave());
		log.info("Total Cred;"+historial.getTotalCreditos());
		log.info("Nivel:"+historial.getNivel());
		log.info("Promedio:"+historial.getPromedio());
		log.info("Credi:"+historial.getCreditos());
		log.info("Aprob: "+historial.getAprobadas());
		log.info("Reprob: "+historial.getReprobadas());
		log.info("NoPresen: "+historial.getNopresentadas());
		log.info("Total: "+historial.getTotal());
		log.info("Fecha: "+historial.getFechaConsulta());

		params.put("LOGO_UNADM",strmLOGO_UNADM);
		params.put("LOGO_SEP",strmLOGO_SEP);
		params.put("NOMBRE", historial.getNombre());
		params.put("MATRICULA",historial.getMatricula());
		params.put("CLAVE_INSTITUCION",historial.getClaveInst());
		params.put("PROG_EDUCATIVO",historial.getProgramaEducativo());
		params.put("CLAVE", historial.getClave());
		params.put("NIVEL", historial.getNivel());
		params.put("PROMEDIO", historial.getPromedio().intValue());
		params.put("CREDITOS", historial.getCreditos().intValue());
		params.put("TOTAL_CREDITOS",historial.getTotalCreditos().intValue());
		params.put("APROBADAS", historial.getAprobadas().intValue());
		params.put("REPROBADAS", historial.getReprobadas().intValue());
		params.put("NO_PRESENTADAS", historial.getNopresentadas().intValue());
		params.put("TOTAL", historial.getTotal().intValue());
		params.put("FECHA_CONSULTA", historial.getFechaConsulta());
		
		
		//Parametros de datos
		JRBeanArrayDataSource dsHistorial = new JRBeanArrayDataSource(listaEventos.toArray());
		params.put("dsHistorial", dsHistorial);
		
		reporteConfig.setParametros(params);

		setReportePDF(ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig), "application/pdf",
				historial.getMatricula()+"_Historial_academico"));
		RequestContext.getCurrentInstance().update("reporte");

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "GEN_EXP_ACA_PDF", "", requestActual(),
				TipoServicioEnum.LOCAL);

	}
	
	public List<EventoConstanciaDTO> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(List<EventoConstanciaDTO> listaEventos) {
		this.listaEventos = listaEventos;
	}

	public HistorialAcademicoDTO getHistorial() {
		return historial;
	}

	public void setHistorial(HistorialAcademicoDTO historial) {
		this.historial = historial;
	}

	public String navegaExpedienteAlumno2(PersonaDTO persona) {
		
		personaDTO = persona;
		
		eventos = grupoParticipanteService.getParticipanteByActaCerradaYconstancia(personaDTO.getIdPersona());
		
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_EXP_ALM", String.valueOf(personaDTO.getIdPersona()),
				requestActual(), TipoServicioEnum.LOCAL);
		
		return ConstantesGestorWeb.NAVEGA_EXPEDIENTE_ALUMNO;
		
	}

	public String cancelar() {
		return ConstantesGestorWeb.NAVEGA_BUSCAR_EXPEDIENTE_ALUMNO;
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

	public ParametroSistemaService getParametrosSistemaService() {
		return parametrosSistemaService;
	}

	public void setParametrosSistemaService(ParametroSistemaService parametrosSistemaService) {
		this.parametrosSistemaService = parametrosSistemaService;
	}

	public PlantillaService getPlantillaService() {
		return plantillaService;
	}

	public void setPlantillaService(PlantillaService plantillaService) {
		this.plantillaService = plantillaService;
	}

	public StreamedContent getConstanciaPDF() {
		return constanciaPDF;
	}

	public void setPlantillaPDF(StreamedContent constanciaPDF) {
		this.constanciaPDF = constanciaPDF;
	}

	/**
	 * @return the personaDTO
	 */
	public PersonaDTO getPersonaDTO() {
		return personaDTO;
	}

	/**
	 * @param personaDTO
	 *            the personaDTO to set
	 */
	public void setPersonaDTO(PersonaDTO personaDTO) {
		this.personaDTO = personaDTO;
	}

	/**
	 * @return the grupoParticipanteService
	 */
	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	/**
	 * @param grupoParticipanteService
	 *            the grupoParticipanteService to set
	 */
	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
	}

	/**
	 * @return the eventos
	 */
	public List<EventoConstanciaDTO> getEventos() {
		return eventos;
	}

	/**
	 * @param eventos
	 *            the eventos to set
	 */
	public void setEventos(List<EventoConstanciaDTO> eventos) {
		this.eventos = eventos;
	}

	public EventoConstanciaDTO getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	public void setEventoSeleccionado(EventoConstanciaDTO eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public StreamedContent getReportePDF() {
		return reportePDF;
	}

	public void setReportePDF(StreamedContent reportePDF) {
		this.reportePDF = reportePDF;
	}
}
