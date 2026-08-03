package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PlantillaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EventoConstanciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.HistorialAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.BitacoraCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvidenciaCasoDTO;
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
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.BitacoraCasoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.CasoAcademicoOperativoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.EvidenciaCasoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ExpedienteCurricularV2Facade;
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
	private static final String DIRECTORIO_EVIDENCIAS_V2 = "evidencias-caso-v2";

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

	@ManagedProperty("#{trayectoriaAcademicaContextoBean}")
	private TrayectoriaAcademicaContextoBean trayectoriaAcademicaContextoBean;

	@ManagedProperty(value = "#{asistenteInscripcionServiceImpl}")
	private AsistenteInscripcionService asistenteInscripcionService;

	@ManagedProperty(value = "#{expedienteCurricularV2Facade}")
	private ExpedienteCurricularV2Facade expedienteCurricularV2Facade;

	@ManagedProperty(value = "#{casoAcademicoOperativoService}")
	private CasoAcademicoOperativoService casoAcademicoOperativoService;

	@ManagedProperty(value = "#{bitacoraCasoService}")
	private BitacoraCasoService bitacoraCasoServiceV2;

	@ManagedProperty(value = "#{evidenciaCasoService}")
	private EvidenciaCasoService evidenciaCasoService;
	
	public ConstanciasBean getConstanciasBean() {
		return constanciasBean;
	}

	public void setConstanciasBean(ConstanciasBean constanciasBean) {
		this.constanciasBean = constanciasBean;
	}

	public TrayectoriaAcademicaContextoBean getTrayectoriaAcademicaContextoBean() {
		return trayectoriaAcademicaContextoBean;
	}

	public void setTrayectoriaAcademicaContextoBean(TrayectoriaAcademicaContextoBean trayectoriaAcademicaContextoBean) {
		this.trayectoriaAcademicaContextoBean = trayectoriaAcademicaContextoBean;
	}

	private List<EventoConstanciaDTO> eventos;
	private PersonaDTO personaDTO;

	private EventoConstanciaDTO eventoSeleccionado;

	private StreamedContent constanciaPDF;
	
	// ITTIVA
	private HistorialAcademicoDTO historial;	
	private List<EventoConstanciaDTO> listaEventos;
	private StreamedContent reportePDF;

	private List<CasoAcademicoOperativoDTO> bandejaCasosV2;
	private CasoAcademicoOperativoDTO casoSeleccionadoV2;
	private FichaIntegralCasoDTO fichaIntegralCasoV2;
	private List<BitacoraCasoDTO> bitacoraCasoV2;
	private List<EvidenciaCasoDTO> evidenciasCasoV2;
	private String comentarioSeguimientoV2;
	private String tipoEvidenciaV2;
	private String descripcionEvidenciaV2;
	private String rutaEvidenciaV2;
	private transient UploadedFile archivoEvidenciaV2;
	private String nombreArchivoEvidenciaV2;
	private Long idBitacoraEdicionV2;
	private Long idEvidenciaEdicionV2;

	public ExpedienteAlumnoBean() {
		personaDTO = new PersonaDTO();
		eventos = new ArrayList<>();
		bandejaCasosV2 = new ArrayList<CasoAcademicoOperativoDTO>();
		bitacoraCasoV2 = new ArrayList<BitacoraCasoDTO>();
		evidenciasCasoV2 = new ArrayList<EvidenciaCasoDTO>();
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
		prepararContextoConsultaGestor();
		
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

	private void prepararContextoConsultaGestor() {
		trayectoriaAcademicaContextoBean.configurarConsultaGestor(personaDTO);
	}

	public void prepararHubEstudianteGestor(PersonaDTO persona) {
		if (persona == null || persona.getIdPersona() == null) {
			return;
		}
		this.personaDTO = persona;
		prepararContextoConsultaGestor();
		asegurarCasoV2ConsultaGestor(persona);
		Long id = persona.getIdPersona();
		listaEventos = constanciasBean.getEventosAdmin(id);
		historial = constanciasBean.getHistorialAdmin(id);
		cargarBandejaCasosV2();
	}

	private void asegurarCasoV2ConsultaGestor(PersonaDTO persona) {
		if (persona == null || persona.getIdPersona() == null || expedienteCurricularV2Facade == null) {
			return;
		}
		try {
			ContextoAsistenteCurricularV2DTO contextoV2 = new ContextoAsistenteCurricularV2DTO();
			contextoV2.setIdPersonaObjetivo(persona.getIdPersona());
			contextoV2.setIdPersonaConsulta(idPersonaEnSesion());
			contextoV2.setPerfilConsulta("GESTOR");
			contextoV2.setVistaGestor(Boolean.TRUE);
			contextoV2.setOrigenConsulta("EXPEDIENTE");

			AsistenteInscripcionContextoDTO contextoAsistido = asistenteInscripcionService != null
					? asistenteInscripcionService.obtenerContextoAsistido(persona.getIdPersona())
					: null;
			if (contextoAsistido != null) {
				contextoV2.setPeriodoOperativo(Boolean.TRUE.equals(contextoAsistido.getInscripcionVigente())
						? "INSCRIPCION" : "CURSAMIENTO");
				InscripcionContextoDTO contextoBase = contextoAsistido.getContextoBase();
				if (contextoBase != null && contextoBase.getInscripcionPersona() != null) {
					contextoV2.setIdPlan(contextoBase.getInscripcionPersona().getIdPlan());
				}
			}
			if (ObjectUtils.isNullOrEmpty(contextoV2.getPeriodoOperativo())) {
				contextoV2.setPeriodoOperativo("CURSAMIENTO");
			}
			expedienteCurricularV2Facade.obtenerContextoExpediente(contextoV2);
		} catch (InscripcionException e) {
			log.warn("No fue posible abrir la orientación V2 básica para la matrícula consultada.", e);
		} catch (Exception e) {
			log.warn("Error inesperado al abrir la orientación V2 básica para la matrícula consultada.", e);
		}
	}

	public void cargarBandejaCasosV2() {
		bandejaCasosV2 = new ArrayList<CasoAcademicoOperativoDTO>();
		fichaIntegralCasoV2 = null;
		casoSeleccionadoV2 = null;
		bitacoraCasoV2 = new ArrayList<BitacoraCasoDTO>();
		evidenciasCasoV2 = new ArrayList<EvidenciaCasoDTO>();
		cancelarEdicionBitacoraV2();
		cancelarEdicionEvidenciaV2();
		if (personaDTO == null || personaDTO.getIdPersona() == null || casoAcademicoOperativoService == null) {
			return;
		}
		try {
			bandejaCasosV2 = casoAcademicoOperativoService.obtenerCasosPorPersona(personaDTO.getIdPersona());
			Collections.sort(bandejaCasosV2, new Comparator<CasoAcademicoOperativoDTO>() {
				@Override
				public int compare(CasoAcademicoOperativoDTO o1, CasoAcademicoOperativoDTO o2) {
					if (o1 == null || o1.getId() == null) {
						return 1;
					}
					if (o2 == null || o2.getId() == null) {
						return -1;
					}
					return o2.getId().compareTo(o1.getId());
				}
			});
			if (!bandejaCasosV2.isEmpty()) {
				seleccionarCasoV2(bandejaCasosV2.get(0));
			}
		} catch (InscripcionException e) {
			log.warn("No fue posible cargar la bandeja V2 de orientación del estudiante consultado.", e);
		}
	}

	public void seleccionarCasoV2(CasoAcademicoOperativoDTO caso) {
		if (caso == null || caso.getId() == null) {
			return;
		}
		try {
			casoSeleccionadoV2 = casoAcademicoOperativoService.obtenerCasoPorId(caso.getId());
			ContextoAsistenteCurricularV2DTO contexto = construirContextoV2("EXPEDIENTE");
			contexto.setCasoActual(casoSeleccionadoV2);
			fichaIntegralCasoV2 = expedienteCurricularV2Facade.obtenerContextoExpediente(contexto);
			bitacoraCasoV2 = bitacoraCasoServiceV2 != null
					? bitacoraCasoServiceV2.obtenerBitacora(casoSeleccionadoV2.getId())
					: new ArrayList<BitacoraCasoDTO>();
			evidenciasCasoV2 = evidenciaCasoService != null
					? evidenciaCasoService.obtenerEvidencias(casoSeleccionadoV2.getId())
					: new ArrayList<EvidenciaCasoDTO>();
			cancelarEdicionBitacoraV2();
			cancelarEdicionEvidenciaV2();
		} catch (InscripcionException e) {
			log.warn("No fue posible cargar la ficha integral V2 de la orientación seleccionada.", e);
		}
	}

	public void registrarSeguimientoCasoV2() {
		if (casoSeleccionadoV2 == null || casoSeleccionadoV2.getId() == null) {
			agregarMsgInfo("No hay orientación V2 seleccionada.", null);
			return;
		}
		if (ObjectUtils.isNullOrEmpty(comentarioSeguimientoV2)) {
			agregarMsgInfo("Captura el comentario de acompañamiento.", null);
			return;
		}
		try {
			if (idBitacoraEdicionV2 != null) {
				BitacoraCasoDTO bitacora = new BitacoraCasoDTO();
				bitacora.setId(idBitacoraEdicionV2);
				bitacora.setDetalle(comentarioSeguimientoV2.trim());
				bitacora.setUsuario(getUsuarioEnSession().getUsuario());
				bitacoraCasoServiceV2.actualizarBitacora(casoSeleccionadoV2.getId(), bitacora);
				bitacoraCasoServiceV2.registrarEvento(casoSeleccionadoV2.getId(), "BITACORA_EDITADA",
						"Se actualizó un registro manual de acompañamiento.", getUsuarioEnSession().getUsuario());
				agregarMsgInfo("Acompañamiento actualizado.", null);
			} else {
				bitacoraCasoServiceV2.registrarEvento(casoSeleccionadoV2.getId(), "SEGUIMIENTO_GESTOR",
						comentarioSeguimientoV2.trim(), getUsuarioEnSession().getUsuario());
				agregarMsgInfo("Acompañamiento registrado.", null);
			}
			cancelarEdicionBitacoraV2();
			seleccionarCasoV2(casoSeleccionadoV2);
		} catch (InscripcionException e) {
			log.error("No fue posible registrar el acompañamiento de la orientación V2.", e);
			agregarMsgInfo("Ocurrió un problema al registrar el acompañamiento.", null);
		}
	}

	public void prepararEdicionBitacoraV2(BitacoraCasoDTO bitacora) {
		if (!isBitacoraEditable(bitacora)) {
			agregarMsgInfo("Sólo se pueden corregir registros manuales de acompañamiento.", null);
			return;
		}
		idBitacoraEdicionV2 = bitacora.getId();
		comentarioSeguimientoV2 = bitacora.getDetalle();
	}

	public void cancelarEdicionBitacoraV2() {
		idBitacoraEdicionV2 = null;
		comentarioSeguimientoV2 = null;
	}

	public void eliminarBitacoraCasoV2(BitacoraCasoDTO bitacora) {
		if (casoSeleccionadoV2 == null || casoSeleccionadoV2.getId() == null || !isBitacoraEditable(bitacora)) {
			agregarMsgInfo("La bitácora seleccionada no puede eliminarse.", null);
			return;
		}
		try {
			bitacoraCasoServiceV2.eliminarBitacora(casoSeleccionadoV2.getId(), bitacora.getId());
			bitacoraCasoServiceV2.registrarEvento(casoSeleccionadoV2.getId(), "BITACORA_ELIMINADA",
					"Se eliminó un registro manual de acompañamiento.", getUsuarioEnSession().getUsuario());
			if (idBitacoraEdicionV2 != null && idBitacoraEdicionV2.equals(bitacora.getId())) {
				cancelarEdicionBitacoraV2();
			}
			seleccionarCasoV2(casoSeleccionadoV2);
			agregarMsgInfo("Registro de acompañamiento eliminado.", null);
		} catch (InscripcionException e) {
			log.error("No fue posible eliminar el acompañamiento de la orientación V2.", e);
			agregarMsgInfo("Ocurrió un problema al eliminar el acompañamiento.", null);
		}
	}

	public void registrarEvidenciaCasoV2() {
		if (casoSeleccionadoV2 == null || casoSeleccionadoV2.getId() == null) {
			agregarMsgInfo("No hay orientación V2 seleccionada.", null);
			return;
		}
		if (ObjectUtils.isNullOrEmpty(tipoEvidenciaV2) || ObjectUtils.isNullOrEmpty(descripcionEvidenciaV2)) {
			agregarMsgInfo("Captura tipo y descripción de la evidencia.", null);
			return;
		}
		try {
			String rutaAnterior = rutaEvidenciaV2;
			if (hayArchivoSeleccionadoEvidenciaV2()) {
				guardarArchivoEvidenciaSeleccionado();
			}
			if (ObjectUtils.isNullOrEmpty(rutaEvidenciaV2)) {
				agregarMsgInfo("Selecciona un archivo de soporte antes de registrar.", null);
				return;
			}
			EvidenciaCasoDTO evidencia = new EvidenciaCasoDTO();
			evidencia.setId(idEvidenciaEdicionV2);
			evidencia.setTipoEvidencia(tipoEvidenciaV2.trim());
			evidencia.setDescripcion(descripcionEvidenciaV2.trim());
			evidencia.setRuta(rutaEvidenciaV2.trim());
			evidencia.setUsuario(getUsuarioEnSession().getUsuario());
			if (idEvidenciaEdicionV2 != null) {
				evidenciaCasoService.actualizarEvidencia(casoSeleccionadoV2.getId(), evidencia);
				if (bitacoraCasoServiceV2 != null) {
					bitacoraCasoServiceV2.registrarEvento(casoSeleccionadoV2.getId(), "EVIDENCIA_EDITADA",
							"Se actualizó evidencia: " + evidencia.getTipoEvidencia(), getUsuarioEnSession().getUsuario());
				}
				if (hayArchivoSeleccionadoEvidenciaV2() && !ObjectUtils.isNullOrEmpty(rutaAnterior)
						&& !rutaAnterior.equals(rutaEvidenciaV2)) {
					eliminarArchivoEvidenciaSilencioso(rutaAnterior);
				}
				agregarMsgInfo("Evidencia actualizada.", null);
			} else {
				evidenciaCasoService.registrarEvidencia(casoSeleccionadoV2.getId(), evidencia);
				if (bitacoraCasoServiceV2 != null) {
					bitacoraCasoServiceV2.registrarEvento(casoSeleccionadoV2.getId(), "EVIDENCIA_GESTOR",
							"Se registró evidencia: " + evidencia.getTipoEvidencia(), getUsuarioEnSession().getUsuario());
				}
				agregarMsgInfo("Evidencia registrada.", null);
			}
			cancelarEdicionEvidenciaV2();
			seleccionarCasoV2(casoSeleccionadoV2);
		} catch (IOException e) {
			log.error("No fue posible guardar el archivo de evidencia de la orientación V2.", e);
			agregarMsgInfo("Ocurrió un problema al guardar el archivo de evidencia.", null);
		} catch (InscripcionException e) {
			log.error("No fue posible registrar la evidencia de la orientación V2.", e);
			agregarMsgInfo("Ocurrió un problema al registrar la evidencia.", null);
		}
	}

	public void prepararEdicionEvidenciaV2(EvidenciaCasoDTO evidencia) {
		if (evidencia == null || evidencia.getId() == null) {
			agregarMsgInfo("No hay evidencia válida para editar.", null);
			return;
		}
		idEvidenciaEdicionV2 = evidencia.getId();
		tipoEvidenciaV2 = evidencia.getTipoEvidencia();
		descripcionEvidenciaV2 = evidencia.getDescripcion();
		rutaEvidenciaV2 = evidencia.getRuta();
		nombreArchivoEvidenciaV2 = obtenerNombreArchivoDesdeRuta(evidencia.getRuta());
		archivoEvidenciaV2 = null;
	}

	private void guardarArchivoEvidenciaSeleccionado() throws IOException {
		String rutaBase = parametrosSistemaService
				.obtenerParametro(ConstantesGestor.PARAMETRO_RUTA_RECURSOS_PUBLICOS);
		if (ObjectUtils.isNullOrEmpty(rutaBase)) {
			throw new IOException("No existe configuración de ruta pública para guardar evidencias.");
		}
		String nombreSeguro = construirNombreArchivoEvidencia(archivoEvidenciaV2.getFileName());
		File directorio = construirDirectorioEvidencias(rutaBase);
		if (!directorio.exists() && !directorio.mkdirs()) {
			throw new IOException("No fue posible crear el directorio de evidencias.");
		}
		File destino = new File(directorio, nombreSeguro);
		FileUtils.copyInputStreamToFile(archivoEvidenciaV2.getInputstream(), destino);
		rutaEvidenciaV2 = destino.getAbsolutePath();
		nombreArchivoEvidenciaV2 = archivoEvidenciaV2.getFileName();
	}

	public void cancelarEdicionEvidenciaV2() {
		idEvidenciaEdicionV2 = null;
		tipoEvidenciaV2 = null;
		descripcionEvidenciaV2 = null;
		rutaEvidenciaV2 = null;
		nombreArchivoEvidenciaV2 = null;
		archivoEvidenciaV2 = null;
	}

	public void eliminarEvidenciaCasoV2(EvidenciaCasoDTO evidencia) {
		if (casoSeleccionadoV2 == null || casoSeleccionadoV2.getId() == null || evidencia == null
				|| evidencia.getId() == null) {
			agregarMsgInfo("No hay evidencia válida para eliminar.", null);
			return;
		}
		try {
			evidenciaCasoService.eliminarEvidencia(casoSeleccionadoV2.getId(), evidencia.getId());
			eliminarArchivoEvidenciaSilencioso(evidencia.getRuta());
			if (bitacoraCasoServiceV2 != null) {
				bitacoraCasoServiceV2.registrarEvento(casoSeleccionadoV2.getId(), "EVIDENCIA_ELIMINADA",
						"Se eliminó evidencia: " + evidencia.getTipoEvidencia(), getUsuarioEnSession().getUsuario());
			}
			if (idEvidenciaEdicionV2 != null && idEvidenciaEdicionV2.equals(evidencia.getId())) {
				cancelarEdicionEvidenciaV2();
			}
			seleccionarCasoV2(casoSeleccionadoV2);
			agregarMsgInfo("Evidencia eliminada.", null);
		} catch (InscripcionException e) {
			log.error("No fue posible eliminar la evidencia del caso V2.", e);
			agregarMsgInfo("Ocurrió un problema al eliminar la evidencia.", null);
		}
	}

	public void limpiarArchivoEvidenciaV2() {
		archivoEvidenciaV2 = null;
		nombreArchivoEvidenciaV2 = null;
		if (idEvidenciaEdicionV2 == null) {
			rutaEvidenciaV2 = null;
		}
	}

	private boolean hayArchivoSeleccionadoEvidenciaV2() {
		return archivoEvidenciaV2 != null && !ObjectUtils.isNullOrEmpty(archivoEvidenciaV2.getFileName());
	}

	private void eliminarArchivoEvidenciaSilencioso(String rutaArchivo) {
		if (ObjectUtils.isNullOrEmpty(rutaArchivo)) {
			return;
		}
		try {
			File archivo = new File(rutaArchivo);
			if (archivo.exists()) {
				FileUtils.forceDelete(archivo);
			}
		} catch (IOException e) {
			log.warn("No fue posible eliminar físicamente el archivo de evidencia: " + rutaArchivo, e);
		}
	}

	private File construirDirectorioEvidencias(String rutaBase) {
		String baseNormalizada = rutaBase.endsWith(File.separator) ? rutaBase : rutaBase + File.separator;
		return new File(baseNormalizada + DIRECTORIO_EVIDENCIAS_V2);
	}

	private String construirNombreArchivoEvidencia(String nombreOriginal) {
		String nombreLimpio = ObjectUtils.isNullOrEmpty(nombreOriginal) ? "evidencia.bin" : nombreOriginal.trim();
		nombreLimpio = nombreLimpio.replaceAll("[^A-Za-z0-9._-]", "_");
		String extension = "";
		int idx = nombreLimpio.lastIndexOf('.');
		if (idx > -1 && idx < nombreLimpio.length() - 1) {
			extension = nombreLimpio.substring(idx);
			nombreLimpio = nombreLimpio.substring(0, idx);
		}
		String folio = casoSeleccionadoV2 != null && !ObjectUtils.isNullOrEmpty(casoSeleccionadoV2.getFolioExterno())
				? casoSeleccionadoV2.getFolioExterno()
				: "CASO";
		return folio + "_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8) + "_"
				+ nombreLimpio + extension;
	}

	public boolean isEditandoBitacoraV2() {
		return idBitacoraEdicionV2 != null;
	}

	public boolean isEditandoEvidenciaV2() {
		return idEvidenciaEdicionV2 != null;
	}

	public String getEtiquetaAccionSeguimientoV2() {
		return isEditandoBitacoraV2() ? "Guardar cambios" : "Registrar acompañamiento";
	}

	public String getEtiquetaAccionEvidenciaV2() {
		return isEditandoEvidenciaV2() ? "Guardar cambios" : "Registrar evidencia";
	}

	public boolean isBitacoraEditable(BitacoraCasoDTO bitacora) {
		return bitacora != null && "SEGUIMIENTO_GESTOR".equals(bitacora.getEvento());
	}

	public boolean bitacoraEditable(BitacoraCasoDTO bitacora) {
		return isBitacoraEditable(bitacora);
	}

	public String obtenerNombreArchivoDesdeRuta(String ruta) {
		if (ObjectUtils.isNullOrEmpty(ruta)) {
			return "Sin archivo";
		}
		return new File(ruta).getName();
	}

	private ContextoAsistenteCurricularV2DTO construirContextoV2(String origen) throws InscripcionException {
		ContextoAsistenteCurricularV2DTO contextoV2 = new ContextoAsistenteCurricularV2DTO();
		contextoV2.setIdPersonaObjetivo(personaDTO != null ? personaDTO.getIdPersona() : null);
		contextoV2.setIdPersonaConsulta(idPersonaEnSesion());
		contextoV2.setPerfilConsulta("GESTOR");
		contextoV2.setVistaGestor(Boolean.TRUE);
		contextoV2.setOrigenConsulta(origen);
		AsistenteInscripcionContextoDTO contextoAsistido = asistenteInscripcionService != null
				? asistenteInscripcionService.obtenerContextoAsistido(personaDTO.getIdPersona())
				: null;
		if (contextoAsistido != null) {
			contextoV2.setPeriodoOperativo(Boolean.TRUE.equals(contextoAsistido.getInscripcionVigente())
					? "INSCRIPCION" : "CURSAMIENTO");
			InscripcionContextoDTO contextoBase = contextoAsistido.getContextoBase();
			if (contextoBase != null) {
				if (contextoBase.getInscripcionPersona() != null) {
					contextoV2.setIdPlan(contextoBase.getInscripcionPersona().getIdPlan());
				}
			}
		}
		if (ObjectUtils.isNullOrEmpty(contextoV2.getPeriodoOperativo())) {
			contextoV2.setPeriodoOperativo("CURSAMIENTO");
		}
		return contextoV2;
	}

	public String navegaHubTrayectoriaEstudiante() {
		prepararHubEstudianteGestor(personaDTO);
		return ConstantesGestorWeb.NAVEGA_HUB_TRAYECTORIA_ESTUDIANTE;
	}

	public String navegaMallaCurricularAlumno() {
		trayectoriaAcademicaContextoBean.limpiarContextoAsistente();
		prepararContextoConsultaGestor();
		return ConstantesGestorWeb.NAVEGA_MALLA_CURRICULAR_ALUMNO;
	}

	public String navegaAsistenteCurricular() {
		trayectoriaAcademicaContextoBean.configurarContextoAsistenteDesdeHistorial();
		prepararContextoConsultaGestor();
		return ConstantesGestorWeb.NAVEGA_TABLA_CURRICULAR_ASISTIDA;
	}

	public void generarReporte() {

		String rutaFondoConstancia = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
				+ "/recursos/documentos/fondoConstancia.png";

		reportePDF = null;

		ReporteConfig reporteConfig = new ReporteConfig();
		reporteConfig.setDatos(null);
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
		params.put("TOTAL_CREDITOS", historial.getTotalCreditos() != null
				? historial.getTotalCreditos().toString() : "-");
		params.put("APROBADAS", historial.getAprobadas().intValue());
		params.put("REPROBADAS", historial.getReprobadas().intValue());
		params.put("NO_PRESENTADAS", historial.getNopresentadas().intValue());
		params.put("TOTAL", historial.getTotal().intValue());
		params.put("FECHA_CONSULTA", historial.getFechaConsulta());
		params.put("NOMBRE_ESTRUCTURA", historial.getNombreEstructura());
		
		
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

	public int getTotalEventosTrayectoria() {
		return listaEventos != null ? listaEventos.size() : 0;
	}

	public BigDecimal getPromedioHistorialEscalaDiez() {
		if (historial == null || historial.getPromedio() == null) {
			return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		}
		BigDecimal promedio = historial.getPromedio();
		if (promedio.compareTo(BigDecimal.TEN) > 0) {
			promedio = promedio.divide(BigDecimal.TEN, 2, RoundingMode.HALF_UP);
		} else {
			promedio = promedio.setScale(2, RoundingMode.HALF_UP);
		}
		return promedio;
	}

	public String getEstatusHistorialFormateado() {
		if (historial == null || ObjectUtils.isNullOrEmpty(historial.getEstatus())) {
			return "Sin dato";
		}
		String estatus = historial.getEstatus().trim();
		if (estatus.startsWith("Estudiante ")) {
			String resto = estatus.substring("Estudiante ".length()).trim().toLowerCase();
			return "Estudiante de " + resto;
		}
		return estatus;
	}

	public int getTotalUnidadesDidacticasTrayectoria() {
		if (historial != null && historial.getTotal() != null) {
			return historial.getTotal().intValue();
		}
		return listaEventos != null ? listaEventos.size() : 0;
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
		trayectoriaAcademicaContextoBean.configurarConsultaGestor(personaDTO);
		
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

	public CasoAcademicoOperativoService getCasoAcademicoOperativoService() {
		return casoAcademicoOperativoService;
	}

	public void setCasoAcademicoOperativoService(CasoAcademicoOperativoService casoAcademicoOperativoService) {
		this.casoAcademicoOperativoService = casoAcademicoOperativoService;
	}

	public BitacoraCasoService getBitacoraCasoServiceV2() {
		return bitacoraCasoServiceV2;
	}

	public void setBitacoraCasoServiceV2(BitacoraCasoService bitacoraCasoServiceV2) {
		this.bitacoraCasoServiceV2 = bitacoraCasoServiceV2;
	}

	public EvidenciaCasoService getEvidenciaCasoService() {
		return evidenciaCasoService;
	}

	public void setEvidenciaCasoService(EvidenciaCasoService evidenciaCasoService) {
		this.evidenciaCasoService = evidenciaCasoService;
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

	public List<CasoAcademicoOperativoDTO> getBandejaCasosV2() {
		return bandejaCasosV2;
	}

	public void setBandejaCasosV2(List<CasoAcademicoOperativoDTO> bandejaCasosV2) {
		this.bandejaCasosV2 = bandejaCasosV2;
	}

	public CasoAcademicoOperativoDTO getCasoSeleccionadoV2() {
		return casoSeleccionadoV2;
	}

	public void setCasoSeleccionadoV2(CasoAcademicoOperativoDTO casoSeleccionadoV2) {
		this.casoSeleccionadoV2 = casoSeleccionadoV2;
	}

	public FichaIntegralCasoDTO getFichaIntegralCasoV2() {
		return fichaIntegralCasoV2;
	}

	public void setFichaIntegralCasoV2(FichaIntegralCasoDTO fichaIntegralCasoV2) {
		this.fichaIntegralCasoV2 = fichaIntegralCasoV2;
	}

	public List<BitacoraCasoDTO> getBitacoraCasoV2() {
		return bitacoraCasoV2;
	}

	public void setBitacoraCasoV2(List<BitacoraCasoDTO> bitacoraCasoV2) {
		this.bitacoraCasoV2 = bitacoraCasoV2;
	}

	public List<EvidenciaCasoDTO> getEvidenciasCasoV2() {
		return evidenciasCasoV2;
	}

	public void setEvidenciasCasoV2(List<EvidenciaCasoDTO> evidenciasCasoV2) {
		this.evidenciasCasoV2 = evidenciasCasoV2;
	}

	public String getComentarioSeguimientoV2() {
		return comentarioSeguimientoV2;
	}

	public void setComentarioSeguimientoV2(String comentarioSeguimientoV2) {
		this.comentarioSeguimientoV2 = comentarioSeguimientoV2;
	}

	public String getTipoEvidenciaV2() {
		return tipoEvidenciaV2;
	}

	public void setTipoEvidenciaV2(String tipoEvidenciaV2) {
		this.tipoEvidenciaV2 = tipoEvidenciaV2;
	}

	public String getDescripcionEvidenciaV2() {
		return descripcionEvidenciaV2;
	}

	public void setDescripcionEvidenciaV2(String descripcionEvidenciaV2) {
		this.descripcionEvidenciaV2 = descripcionEvidenciaV2;
	}

	public String getRutaEvidenciaV2() {
		return rutaEvidenciaV2;
	}

	public void setRutaEvidenciaV2(String rutaEvidenciaV2) {
		this.rutaEvidenciaV2 = rutaEvidenciaV2;
	}

	public UploadedFile getArchivoEvidenciaV2() {
		return archivoEvidenciaV2;
	}

	public void setArchivoEvidenciaV2(UploadedFile archivoEvidenciaV2) {
		this.archivoEvidenciaV2 = archivoEvidenciaV2;
	}

	public String getNombreArchivoEvidenciaV2() {
		return nombreArchivoEvidenciaV2;
	}

	public void setNombreArchivoEvidenciaV2(String nombreArchivoEvidenciaV2) {
		this.nombreArchivoEvidenciaV2 = nombreArchivoEvidenciaV2;
	}
}
