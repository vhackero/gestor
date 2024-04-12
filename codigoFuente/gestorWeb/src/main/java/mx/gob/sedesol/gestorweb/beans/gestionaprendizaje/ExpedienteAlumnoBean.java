package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

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
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoDocumentoEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.PlantillaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;

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

	private List<EventoConstanciaDTO> eventos;
	private PersonaDTO personaDTO;

	private EventoConstanciaDTO eventoSeleccionado;

	private StreamedContent constanciaPDF;

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
		eventos = grupoParticipanteService.getParticipanteByActaCerradaYconstancia(personaDTO.getIdPersona());
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_EXP_ALM", String.valueOf(personaDTO.getIdPersona()),
				requestActual(), TipoServicioEnum.LOCAL);
		return ConstantesGestorWeb.NAVEGA_EXPEDIENTE_ALUMNO;
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
}
