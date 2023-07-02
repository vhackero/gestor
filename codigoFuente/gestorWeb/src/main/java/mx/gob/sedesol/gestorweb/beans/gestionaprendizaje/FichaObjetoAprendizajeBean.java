package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.CargaArchivosOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.ElementosMultimediaFoaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.FichaDescripcionOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.FichaDescriptivaOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelActividadesReforzamientoFoaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelMaterialDidacticoFoaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelRecursoPredominanteFoaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RelRelacionOtrosObjetosFoaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgAutoreDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.SubtemasUDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ClasificacionArchivoOaEnum;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatActividadReforzamientoFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatAmbitoAplicacionFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatComposicionObjetoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatDensidadSemanticaOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatDificultadOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatElementosMultimedia;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatEstatusFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatFormatoFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatIdiomaOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatNivelGranularidadOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatRecursoDidacticoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatRecursoPredominanteFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatRelacionOtrosObjeto;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTipoContenidoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTipoEstructuraOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTipoInteractividadOa;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.CargaArchivosOaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.FichaDescriptivaOaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelUnidadOaAvaService;
import mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje.FichaDescriptivaOaServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.ReporteConfig;
import mx.gob.sedesol.gestorweb.commons.utils.ReporteUtil;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean
public class FichaObjetoAprendizajeBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(FichaObjetoAprendizajeBean.class);
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{fichaDescriptivaOaServiceFacade}")
	private FichaDescriptivaOaServiceFacade fichaDescriptivaOaServiceFacade;

	@ManagedProperty(value = "#{fichaDescriptivaOaService}")
	private FichaDescriptivaOaService fichaDescriptivaOaService;

	@ManagedProperty(value = "#{cargaArchivosOaService}")
	private CargaArchivosOaService cargaArchivosOaService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private ParametroSistemaService parametrosSistemaService;

	@ManagedProperty(value = "#{relUnidadOaAvaService}")
	private RelUnidadOaAvaService relUnidadOaAvaService;

	@ManagedProperty("#{sistema}")
	private SistemaBean textosSistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<UnidadOaAvaDTO> listaUnidades;

	private UnidadOaAvaDTO unidadOaAvaDTO;
	private FichaDescriptivaOaDTO foaEtapaDTO;
	private CargaArchivosOaDTO cargaArchivosOaDTO;

	private ElementosMultimediaFoaDTO elementosMultimediaFoaDTO;
	private RelMaterialDidacticoFoaDTO materialDidacticoFoaDTO;
	private RelActividadesReforzamientoFoaDTO actividadesReforzamientoFoaDTO;
	private RelRecursoPredominanteFoaDTO recursoPredominanteFoaDTO;
	private RelRelacionOtrosObjetosFoaDTO relacionOtrosObjetosFoaDTO;

	private List<CatalogoComunDTO> listaElementosMultimedia;
	private List<CatalogoComunDTO> listaElementosMultimediaSeleccionados;
	private List<FichaDescriptivaOaDTO> listaFOAS;
	private List<CatalogoComunDTO> listaTipoEstructura;
	private List<CatalogoComunDTO> listaTipoContenido;
	private List<CatalogoComunDTO> listaTipoInteractividad;
	private List<CatalogoComunDTO> listaTipoRecursoPredominante;
	private List<CatalogoComunDTO> listaDensidadSemantica;
	private List<CatalogoComunDTO> listaNivelDificultad;
	private List<CatalogoComunDTO> listaIdioma;
	private List<CatalogoComunDTO> listaAmbito;
	private List<CatalogoComunDTO> listaNivelGranularidad;
	private List<CatalogoComunDTO> listaNivelGranularidadSeleccionado;
	private List<CatalogoComunDTO> listaComposicionObjeto;
	private List<CatalogoComunDTO> listaComposicionObjetoSeleccionado;
	private List<CatalogoComunDTO> listaFormato;
	private List<CatalogoComunDTO> listaEstatus;
	private List<CatalogoComunDTO> listaMaterialDidactico;
	private List<CatalogoComunDTO> listaMaterialDidacticoSeleccionado;
	private List<CatalogoComunDTO> listaActivdadesReforzamiento;
	private List<CatalogoComunDTO> listaActividadesReforzamientoSeleccionado;
	private List<CatalogoComunDTO> listaRecursoPredominante;
	private List<CatalogoComunDTO> listaRecursoPredominanteSeleccionado;
	private List<CatalogoComunDTO> listaRelacionOtrosObjetos;
	private List<CatalogoComunDTO> listaRelacionOtrosObjetosSeleccionado;
	private List<CargaArchivosOaDTO> listaArchivosOA;
	private Integer indicePanel;
	private String tipoCompetencia;
	private String ejeCapacitacion;
	private String responsables;
	private String nombreOA;
	private TreeNode root;
	// Indica si es final (true) o borrador(false)
	private Boolean estatusBDFF;
	private Boolean botonFinalizar;
	private List<String> mensajes;

	private StreamedContent fileDownload;

	private StreamedContent reportePDF;

	private AmbienteVirtualAprendizajeDTO avaSeleccionado;

	private Integer idUnidadDidactica;

	private static final Logger LOG = Logger.getLogger(FichaObjetoAprendizajeBean.class);

	public FichaObjetoAprendizajeBean() {
		inicializarFoa();
		elementosMultimediaFoaDTO = new ElementosMultimediaFoaDTO();
		materialDidacticoFoaDTO = new RelMaterialDidacticoFoaDTO();
		actividadesReforzamientoFoaDTO = new RelActividadesReforzamientoFoaDTO();
		recursoPredominanteFoaDTO = new RelRecursoPredominanteFoaDTO();
		relacionOtrosObjetosFoaDTO = new RelRelacionOtrosObjetosFoaDTO();
		cargaArchivosOaDTO = new CargaArchivosOaDTO();
		listaElementosMultimediaSeleccionados = new ArrayList<>();
		listaMaterialDidacticoSeleccionado = new ArrayList<>();
		listaActividadesReforzamientoSeleccionado = new ArrayList<>();
		listaRecursoPredominanteSeleccionado = new ArrayList<>();
		listaRelacionOtrosObjetosSeleccionado = new ArrayList<>();
		listaNivelGranularidadSeleccionado = new ArrayList<>();
		listaComposicionObjetoSeleccionado = new ArrayList<>();
		mensajes = new ArrayList<>();
	}

	@PostConstruct
	public void init() {
		LOG.info("Inicializando Ficha Objeto Aprendizaje");
		listaElementosMultimedia = fichaDescriptivaOaServiceFacade.getCatalogoElementosMultimediaService()
				.findAll(CatElementosMultimedia.class);
		listaTipoEstructura = fichaDescriptivaOaServiceFacade.getCatalogoTipoEstructuraService()
				.findAll(CatTipoEstructuraOa.class);
		listaTipoContenido = fichaDescriptivaOaServiceFacade.getCatalogoTipoContenidoService()
				.findAll(CatTipoContenidoOa.class);
		listaTipoInteractividad = fichaDescriptivaOaServiceFacade.getCatalogoTipoInteractividadService()
				.findAll(CatTipoInteractividadOa.class);
		listaTipoRecursoPredominante = fichaDescriptivaOaServiceFacade.getCatalogoTipoRecursoPredominanteService()
				.findAll(CatRecursoPredominanteFoa.class);
		listaDensidadSemantica = fichaDescriptivaOaServiceFacade.getCatalogoDensidadSemanticaService()
				.findAll(CatDensidadSemanticaOa.class);
		listaNivelDificultad = fichaDescriptivaOaServiceFacade.getCatalogoNivelDificultadService()
				.findAll(CatDificultadOa.class);
		listaIdioma = fichaDescriptivaOaServiceFacade.getCatalogoIdiomaService().findAll(CatIdiomaOa.class);
		listaAmbito = fichaDescriptivaOaServiceFacade.getCatalogoAmbitoService().findAll(CatAmbitoAplicacionFoa.class);
		listaNivelGranularidad = fichaDescriptivaOaServiceFacade.getCatalogoNivelGranularidad()
				.findAll(CatNivelGranularidadOa.class);
		listaComposicionObjeto = fichaDescriptivaOaServiceFacade.getCatalogoComposicionObjeto()
				.findAll(CatComposicionObjetoOa.class);
		listaMaterialDidactico = fichaDescriptivaOaServiceFacade.getCatalogoMaterialDidacticoService()
				.findAll(CatRecursoDidacticoOa.class);
		listaActivdadesReforzamiento = fichaDescriptivaOaServiceFacade.getCatalogoActividadReforzamientoService()
				.findAll(CatActividadReforzamientoFoa.class);
		listaFormato = fichaDescriptivaOaServiceFacade.getCatalogoFormatoFoaService().findAll(CatFormatoFoa.class);
		listaRelacionOtrosObjetos = fichaDescriptivaOaServiceFacade.getCatalogoRelacionOtrosObjetos()
				.findAll(CatRelacionOtrosObjeto.class);
		listaRecursoPredominante = fichaDescriptivaOaServiceFacade.getCatalogoRecursoPredominante()
				.findAll(CatRecursoPredominanteFoa.class);
		listaEstatus = fichaDescriptivaOaServiceFacade.getCatalogoEstatusService().findAll(CatEstatusFoa.class);
	}

	public Integer obtenerEstatusFoa(UnidadOaAvaDTO unidadOa) {

		if (ObjectUtils.isNotNull(unidadOa)) {

			FichaDescriptivaOaDTO fichaDescriptiva = unidadOa.getFichaDescriptivaObjetoAprendizaje();

			if (ObjectUtils.isNotNull(fichaDescriptiva)) {

				FichaDescripcionOaDTO fichaDescripcion = fichaDescriptiva.getFichaDescripcionOa();

				if (ObjectUtils.isNotNull(fichaDescripcion)) {

					CatalogoComunDTO estatus = fichaDescripcion.getCatEstatusFoa();

					if (ObjectUtils.isNotNull(estatus.getId())) {
						return estatus.getId();
					}
				}
			}
		}
		return 0;
	}

	public void obtenerUnidades() {
		listaUnidades = getRelUnidadOaAvaService().findByIdAva(avaSeleccionado.getId());
	}

	public void cargarUnidad() {

		for (UnidadOaAvaDTO unidad : listaUnidades) {
			if (idUnidadDidactica.intValue() == unidad.getDetEstUnidadDidactica().getIdUnidadDidactica().intValue()) {
				unidadOaAvaDTO = unidad;
				generarReporte();
				break;
			}
		}
	}

	/* Generar reporte FOA */
	public void generarReporte() {
		if (ObjectUtils.isNotNull(unidadOaAvaDTO.getFichaDescriptivaObjetoAprendizaje())) {
			cargarDatosFoa(unidadOaAvaDTO.getFichaDescriptivaObjetoAprendizaje().getIdFoa());
		}

		String rutaFondoConstancia = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
				+ getParametrosSistemaService().obtenerParametro(ConstantesGestor.PARAMETRO_RUTA_UNDERTOW)
				+ getParametrosSistemaService().obtenerParametro(ConstantesGestor.PARAMETRO_RUTA_IMAGENES_DOCUMENTOS);

		reportePDF = null;

		/* DATOS GENERALES */
		String nombreOa = "";
		String areaConocimiento = "";
		String cursosReferenciaTema = "";
		String asignaturasReferencia = "";
		String modalidad = "";
		String responsablesMateria = "";
		String nivelEducativo = "";
		String objetivoGeneral = "";
		String proposito = "";
		String requisitos = "";
		String descContenido = "";
		String temasRelacionados = "";
		String elementosMultimediaSeleccionados = "";
		String tratamientoEditorial = "";
		String palabrasClave = "";
		String autores = "";

		/* ELEMENTOS INSTRUCCIONALES */
		String tiempoTipicoAprendizaje = "";
		String destinatarios = "";
		String rangoTipicoEdad = "";
		String tipoEstructura = "";
		String tipoContenido = "";
		String tipoInteractividad = "";
		String densidadSemantica = "";
		String nivelDificultad = "";
		String tipoRecursoPredominante = "";
		String materialDidacticoApoyo = "";
		String actividadesReforzamiento = "";
		String evaluacionAprendizaje = "";
		String comentarios = "";

		/* DESCRIPCION DEL OA */
		String resumen = "";
		String version = "";
		String lugarEdicion = "";
		String idioma = "";
		String ambitoAplicacion = "";
		String nivelGranularidad = "";
		String composicionObjeto = "";
		String estaDisponible = "";
		String url = "";
		String fechaLiberacion = "";
		String tiempoEstimadoReproduccion = "";
		String formato = "";
		String responsablesAcademicos = "";
		String tamanioScorm = "";
		String tamanioOa = "";

		/* CONTEXTO */
		String tipoEvento = "";
		String tieneCosto = "";
		String tieneDerechosAutor = "";
		String condicionUso = "";
		String relacionconOa = "";
		String descripcion = "";
		String anotacion = "";
		String estatus = "";

		/* DATOS GENERALES */
		try {
			nombreOa = unidadOaAvaDTO.getDetEstUnidadDidactica().getNombreUnidad();
		} catch (Exception e) {
			LOG.info("Error en nombreOa: " + e);
		}
		try {
			areaConocimiento = unidadOaAvaDTO.getDetEstUnidadDidactica().getNombreUnidad();
		} catch (Exception e) {
			LOG.info("Error en area conocimiento: " + e);
		}
		try {
			cursosReferenciaTema = unidadOaAvaDTO.getAmbienteVirtualAprendizaje().getEventoCapacitacion()
					.getFichaDescriptivaPrograma().getProgramaAntecedente().getNombreTentativo();
		} catch (Exception e) {
			LOG.info("Error en cursos referencia tema: " + e);
		}
		try {
			asignaturasReferencia = unidadOaAvaDTO.getAmbienteVirtualAprendizaje().getEventoCapacitacion()
					.getFichaDescriptivaPrograma().getProgramaAntecedente().getNombreTentativo();
		} catch (Exception e) {
			LOG.info("Error en asignaturas referencia: " + e);
		}
		try {
			modalidad = unidadOaAvaDTO.getAmbienteVirtualAprendizaje().getEventoCapacitacion()
					.getFichaDescriptivaPrograma().getCatModalidad().getNombre();
		} catch (Exception e) {
			LOG.info("Error en modalidad: " + e);
		}
		try {
			responsablesMateria = unidadOaAvaDTO.getAmbienteVirtualAprendizaje().getEventoCapacitacion()
					.getResponsableCoordinadorAcademico().getPersonaResponsabilidad().getTblPersona()
					.getNombreCompleto();
		} catch (Exception e) {
			LOG.info("Error en responsables materia: " + e);
		}
		try {
			nivelEducativo = unidadOaAvaDTO.getAmbienteVirtualAprendizaje().getEventoCapacitacion()
					.getFichaDescriptivaPrograma().getCatNivelConocimiento().getNombre();
		} catch (Exception e) {
			LOG.info("Error en nivel educativo: " + e);
		}

		try {
			objetivoGeneral = foaEtapaDTO.getObjetivoGeneral();
		} catch (Exception e) {
			LOG.info("Error en objetivo general: " + e);
		}

		try {
			proposito = foaEtapaDTO.getProposito();
		} catch (Exception e) {
			LOG.info("Error en proposito: " + e);
		}

		try {
			requisitos = foaEtapaDTO.getRequisitosConocimientosPrevios();
		} catch (Exception e) {
			LOG.info("Error en requisitos: " + e);
		}

		try {
			descContenido = foaEtapaDTO.getDescripcionContenido();
		} catch (Exception e) {
			LOG.info("Error en desc contenido: " + e);
		}

		try {
			temasRelacionados = foaEtapaDTO.getTemasRelacionados();
		} catch (Exception e) {
			LOG.info("Error en temas relacionados: " + e);
		}

		try {
			elementosMultimediaSeleccionados = obtenerElementosChk(listaElementosMultimedia);
		} catch (Exception e) {
			LOG.info("Error en elementos multimedia seleccionados: " + e);
		}

		try {
			tratamientoEditorial = foaEtapaDTO.getTratamientoEditorial();
		} catch (Exception e) {
			LOG.info("Error en tratamiento editorial: " + e);
		}

		try {
			palabrasClave = foaEtapaDTO.getPalabrasClave();
		} catch (Exception e) {
			LOG.info("Error en palabras clave: " + e);
		}

		try {
			autores = obtenerListaAutores(unidadOaAvaDTO.getAmbienteVirtualAprendizaje().getEventoCapacitacion()
					.getFichaDescriptivaPrograma().getRelProgramaAutores());
		} catch (Exception e) {
			LOG.info("Error en autores: " + e);
		}

		/* ELEMENTOS INSTRUCCIONALES */
		try {
			tiempoTipicoAprendizaje = foaEtapaDTO.getTiempoTipicoAprendizaje();
		} catch (Exception e) {
			LOG.info("Error en autores: " + e);
		}
		try {
			destinatarios = unidadOaAvaDTO.getAmbienteVirtualAprendizaje().getEventoCapacitacion().getIdDestinatario()
					.getNombre();
		} catch (Exception e) {
			LOG.info("Error en destinatarios: " + e);
		}
		try {
			rangoTipicoEdad = foaEtapaDTO.getRangoTipicoDeEdad();
		} catch (Exception e) {
			LOG.info("Error en rango tipico de edad: " + e);
		}
		try {
			tipoEstructura = obtenerNombreDeCatalogo(listaTipoEstructura,
					foaEtapaDTO.getFichaDescripcionOa().getCatTipoEstructuraOa().getId());
		} catch (Exception e) {
			LOG.info("Error en tipo estructura: " + e);
		}
		try {
			tipoContenido = obtenerNombreDeCatalogo(listaTipoContenido,
					foaEtapaDTO.getFichaDescripcionOa().getCatTipoContenidoOa().getId());
		} catch (Exception e) {
			LOG.info("Error en tipo contenido: " + e);
		}
		try {
			tipoInteractividad = obtenerNombreDeCatalogo(listaTipoInteractividad,
					foaEtapaDTO.getFichaDescripcionOa().getCatTipoInteractividadOa().getId());
		} catch (Exception e) {
			LOG.info("Error en tipo interactividad: " + e);
		}
		try {
			densidadSemantica = obtenerNombreDeCatalogo(listaDensidadSemantica,
					foaEtapaDTO.getFichaDescripcionOa().getCatDensidadSemanticaOa().getId());
		} catch (Exception e) {
			LOG.info("Error en densidad semantica: " + e);
		}
		try {
			nivelDificultad = obtenerNombreDeCatalogo(listaNivelDificultad,
					foaEtapaDTO.getFichaDescripcionOa().getCatDificultadOa().getId());
		} catch (Exception e) {
			LOG.info("Error en nivel de dificultad: " + e);
		}
		try {
			tipoRecursoPredominante = obtenerNombreDeCatalogo(listaRecursoPredominante,
					foaEtapaDTO.getFichaDescripcionOa().getCatRecursoPredominanteFoa().getId());
		} catch (Exception e) {
			LOG.info("Error en tipo de recurso predominante: " + e);
		}
		try {
			materialDidacticoApoyo = obtenerElementosChk(listaMaterialDidactico);
		} catch (Exception e) {
			LOG.info("Error en lista material didactivo: " + e);
		}
		try {
			actividadesReforzamiento = obtenerElementosChk(listaActivdadesReforzamiento);
		} catch (Exception e) {
			LOG.info("Error en actividades de reforzamiento: " + e);
		}
		try {
			evaluacionAprendizaje = foaEtapaDTO.getEvaluacionDelAprendizaje();
		} catch (Exception e) {
			LOG.info("Error en evaluacion aprendizaje: " + e);
		}
		try {
			comentarios = foaEtapaDTO.getComentarios();
		} catch (Exception e) {
			LOG.info("Error en comentarios: " + e);
		}

		/* DESCRIPCION DEL OA */
		try {
			resumen = foaEtapaDTO.getResumen();
		} catch (Exception e) {
			LOG.info("Error en el resumen: " + e);
		}
		try {
			version = foaEtapaDTO.getVersion();
		} catch (Exception e) {
			LOG.info("Error en version: " + e);
		}
		try {
			lugarEdicion = foaEtapaDTO.getLugarEdicion();
		} catch (Exception e) {
			LOG.info("Error en lugar de edicion: " + e);
		}
		try {
			idioma = obtenerNombreDeCatalogo(listaIdioma, foaEtapaDTO.getFichaDescripcionOa().getCatIdiomaOa().getId());
		} catch (Exception e) {
			LOG.info("Error en idioma: " + e);
		}
		try {
			ambitoAplicacion = foaEtapaDTO.getAmbitoAplicacion();
		} catch (Exception e) {
			LOG.info("Error en el ambito: " + e);
		}
		try {
			nivelGranularidad = obtenerNombreDeCatalogo(listaNivelGranularidad,
					foaEtapaDTO.getFichaDescripcionOa().getCatNivelGranularidadOa().getId());
		} catch (Exception e) {
			LOG.info("Error en el nivel de granularidad: " + e);
		}
		try {
			composicionObjeto = obtenerNombreDeCatalogo(listaComposicionObjeto,
					foaEtapaDTO.getFichaDescripcionOa().getCatComposicionObjetoOa().getId());
		} catch (Exception e) {
			LOG.info("Error en la composicion del objeto: " + e);
		}
		try {
			estaDisponible = obtenerCadenaSiNoDeSwitch(foaEtapaDTO.getEstaDisponible());
		} catch (Exception e) {
			LOG.info("Error en el boton de Esta disponible: " + e);
		}
		try {
			url = foaEtapaDTO.getUrl();
		} catch (Exception e) {
			LOG.info("Error en la URL: " + e);
		}
		try {
			fechaLiberacion = DateUtils.formatoFechaConstancia(foaEtapaDTO.getFechaActualizacion()).toUpperCase();
		} catch (Exception e) {
			LOG.info("Error en la fecha de liberacion: " + e);
		}
		try {
			tiempoEstimadoReproduccion = foaEtapaDTO.getTiempoEstimadoRepLectura();
		} catch (Exception e) {
			LOG.info("Error en el tiempo estimado de reproduccion: " + e);
		}
		try {
			formato = obtenerNombreDeCatalogo(listaFormato,
					foaEtapaDTO.getFichaDescripcionOa().getCatFormatoFoa().getId());
		} catch (Exception e) {
			LOG.info("Error en el select de formato: " + e);
		}
		try {
			responsablesAcademicos = foaEtapaDTO.getResponsableAcademico();
		} catch (Exception e) {
			LOG.info("Error en responsables Academicos: " + e);
		}
		try {

			tamanioScorm = foaEtapaDTO.getPesoArchivosScorm();
		} catch (Exception e) {
			LOG.info("Error en tamanio scorm: " + e);
		}
		try {
			// Falta obtener el tamanio del OA
			tamanioOa = foaEtapaDTO.getPesoArchivosScorm();
		} catch (Exception e) {
			LOG.info("Error en ");
		}

		/* CONTEXTO */
		try {
			tipoEvento = foaEtapaDTO.getNombreActividad();
		} catch (Exception e) {
			LOG.info("Error en el tipo de evento: " + e);
		}
		try {
			tieneCosto = obtenerCadenaSiNoDeSwitch(foaEtapaDTO.getTieneCosto());
		} catch (Exception e) {
			LOG.info("Error en tiene costo: " + e);
		}
		try {
			tieneDerechosAutor = obtenerCadenaSiNoDeSwitch(foaEtapaDTO.getTieneDerechosAutor());
		} catch (Exception e) {
			LOG.info("Error en tiene derechos de autor: " + e);
		}
		try {
			condicionUso = foaEtapaDTO.getCondicionesUso();
		} catch (Exception e) {
			LOG.info("Error en condiciones de uso: " + e);
		}
		try {
			relacionconOa = obtenerElementosChk(listaRelacionOtrosObjetos);
		} catch (Exception e) {
			LOG.info("Error en el check de relacion con oa: " + e);
		}
		try {
			descripcion = foaEtapaDTO.getDescripcionContenido();
		} catch (Exception e) {
			LOG.info("Error en descripcion: " + e);
		}
		try {
			anotacion = foaEtapaDTO.getAnotacion();
		} catch (Exception e) {
			LOG.info("Error en anotacion: " + e);
		}
		try {
			estatus = obtenerNombreDeCatalogo(listaEstatus,
					foaEtapaDTO.getFichaDescripcionOa().getCatEstatusFoa().getId());
		} catch (Exception e) {
			LOG.info("Error en estatus: " + e);
		}

		ReporteConfig reporteConfig = new ReporteConfig();
		reporteConfig.setDatos(null);
		reporteConfig.setNombreReporte("Plantilla_FOA");
		reporteConfig.setPathJasper("/resources/jasperReport/gestionAprendizaje/foa.jasper");
		reporteConfig.setTipoReporte(ReporteUtil.REPORTE_PDF);

		Map<String, Object> parametros = new HashMap<>();

		/* DATOS GENERALES */
		parametros.put("P_NOMBRE_OA", nombreOa);
		parametros.put("P_AREA_CONOCIMIENTO", areaConocimiento);
		parametros.put("P_CURSOS_REFERENCIA_TEMA", cursosReferenciaTema);
		parametros.put("P_ASIGNATURA_REFERENCIA", asignaturasReferencia);
		parametros.put("P_MODALIDAD", modalidad);
		parametros.put("P_RESPONSABLE_DE_MATERIA", responsablesMateria);
		parametros.put("P_NIVEL_EDUCATIVO", nivelEducativo);
		parametros.put("P_OBJETIVO_GENERAL", objetivoGeneral);
		parametros.put("P_PROPOSITO", proposito);
		parametros.put("P_REQUISITOS", requisitos);
		parametros.put("P_DESC_CONTENIDO", descContenido);
		parametros.put("P_TEMAS_RELACIONADOS", temasRelacionados);
		parametros.put("P_LISTA_ELEMENTOS_MULTIMEDIA", elementosMultimediaSeleccionados);
		parametros.put("P_TRATAMIENTO_EDITORIAL", tratamientoEditorial);
		parametros.put("P_PALABRAS_CLAVE", palabrasClave);
		parametros.put("P_AUTORES", autores);

		/* ELEMENTOS INSTRUCCIONALES */
		parametros.put("P_TIEMPO_TIPICO_APRENDIZAJE", tiempoTipicoAprendizaje);
		parametros.put("P_DESTINATARIOS", destinatarios);
		parametros.put("P_RANGO_TIPICO_EDAD", rangoTipicoEdad);
		parametros.put("P_TIPO_ESTRUCTURA", tipoEstructura);
		parametros.put("P_TIPO_CONTENIDO", tipoContenido);
		parametros.put("P_TIPO_INTERACTIVIDAD", tipoInteractividad);
		parametros.put("P_DENSIDAD_SEMANTICA", densidadSemantica);
		parametros.put("P_NIVEL_DIFICULTAD", nivelDificultad);
		parametros.put("P_TIPO_RECURSO_PREDOMINANTE", tipoRecursoPredominante);
		parametros.put("P_MATERIAL_DIDACTICO_APOYO", materialDidacticoApoyo);
		parametros.put("P_ACTIVIDADES_REFORZAMIENTO", actividadesReforzamiento);
		parametros.put("P_EVALUACION_APRENDIZAJE", evaluacionAprendizaje);
		parametros.put("P_COMENTARIOS", comentarios);

		/* DESCRIPCION DEL OA */
		parametros.put("P_RESUMEN", resumen);
		parametros.put("P_VERSION", version);
		parametros.put("P_LUGAR_EDICION", lugarEdicion);
		parametros.put("P_IDIOMA", idioma);
		parametros.put("P_AMBITO_APLICACION", ambitoAplicacion);
		parametros.put("P_NIVEL_GRANULARIDAD", nivelGranularidad);
		parametros.put("P_COMPOSICION_OBJETO", composicionObjeto);
		parametros.put("P_ESTA_DISPONIBLE", estaDisponible);
		parametros.put("P_URL", url);
		parametros.put("P_FECHA_LIBERACION", fechaLiberacion);
		parametros.put("P_TIEMPO_ESTIMADO_REPRODUCCION", tiempoEstimadoReproduccion);
		parametros.put("P_FORMATO", formato);
		parametros.put("P_RESPONSABLES_ACADEMICOS", responsablesAcademicos);
		parametros.put("P_TAMANIO_SCORM", tamanioScorm);
		parametros.put("P_TAMANIO_OA", tamanioOa);

		/* CONTEXTO */
		parametros.put("P_TIPO_EVENTO_CAPACITACION", tipoEvento);
		parametros.put("P_TIENE_COSTO", tieneCosto);
		parametros.put("P_TIENE_DERECHOS_AUTOR", tieneDerechosAutor);
		parametros.put("P_CONDICION_USO", condicionUso);
		parametros.put("P_RELACION_CON_OA", relacionconOa);
		parametros.put("P_DESCRIPCION", descripcion);
		parametros.put("P_ANOTACION", anotacion);
		parametros.put("P_ESTATUS", estatus);

		reporteConfig.setParametros(parametros);
		setReportePDF(ReporteUtil.getStreamedContentOfBytes(ReporteUtil.generar(reporteConfig), "application/pdf",
				"plantillaPrototipo"));

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_FOA_PDF", "", requestActual(), TipoServicioEnum.LOCAL);

	}

	private String obtenerCadenaSiNoDeSwitch(Boolean botonSiNo) {
		if (botonSiNo.booleanValue() == true) {
			return "Si";
		} else if (botonSiNo.booleanValue() == false) {
			return "No";
		}
		return "";
	}

	private String obtenerNombreDeCatalogo(List<CatalogoComunDTO> lista, Integer id) {
		String nombre = "";
		for (CatalogoComunDTO catalogoComunDTO : lista) {
			if (catalogoComunDTO.getId().intValue() == id.intValue()) {
				nombre = catalogoComunDTO.getNombre();
				return nombre;
			}
		}
		return "";
	}

	private String obtenerElementosChk(List<CatalogoComunDTO> lista) {
		List<String> elementosMultimediaSeleccionados = new ArrayList<>();

		for (CatalogoComunDTO catalogoComunDTO : lista) {
			LOG.info(catalogoComunDTO.getNombre());
			if (catalogoComunDTO.getActivoBoolean()) {
				elementosMultimediaSeleccionados.add(catalogoComunDTO.getNombre());
			}
		}

		String listaSeparadaPorComas = "";

		listaSeparadaPorComas = String.join(", ", elementosMultimediaSeleccionados);

		return listaSeparadaPorComas;
	}

	private String obtenerListaAutores(List<RelProgAutoreDTO> lista) {
		List<String> elementos = new ArrayList<>();

		for (RelProgAutoreDTO objeto : lista) {
			elementos.add(objeto.getAutores());
		}

		String listaSeparadaPorComas = String.join(", ", elementos);
		return listaSeparadaPorComas;
	}

	/**
	 * ****************************** Metodos
	 * ***************************************************** @param
	 * unidadOaAvaDTO
	 * 
	 * @param unidadOaAvaDTO
	 * @param foaEtapaDTO
	 */
	public void generarXML(UnidadOaAvaDTO unidadOaAvaDTO, FichaDescriptivaOaDTO foaEtapaDTO) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(System.getProperty("user.home") + ConstantesGestorWeb.NOMBRE_XML));
			ActualizarXML xml = new ActualizarXML();
			xml.agregarElemento(doc, "es", unidadOaAvaDTO.getDetEstUnidadDidactica().getNombreUnidad(), "imsmd_title",
					"imsmd_langstring"); // Titulo
			xml.agregarElemento(doc, "es", foaEtapaDTO.getResumen(), "imsmd_description", "imsmd_langstring");// 1.4
																												// Resumen
																												// (Descripción)
			xml.agregarElemento(doc, "es",
					String.valueOf(foaEtapaDTO.getFichaDescripcionOa().getCatComposicionObjetoOa().getId()),
					"imsmd_structure", "imsmd_value", "imsmd_langstring"); // 1.7
																			// Estructura
			xml.agregarElemento(doc, "es", foaEtapaDTO.getResponsableAcademico(), "imsmd_contribute", "imsmd_role",
					"imsmd_value", "imsmd_langstring"); // 2.3.1 Responsable
														// academico
			xml.agregarElemento(doc, "es",
					String.valueOf(foaEtapaDTO.getFichaDescripcionOa().getCatTipoInteractividadOa().getId()),
					"imsmd_educational", "imsmd_interactivitytype", "imsmd_value", "imsmd_langstring"); // 5.1
			xml.agregarElemento(doc, "es",
					String.valueOf(foaEtapaDTO.getFichaDescripcionOa().getCatDensidadSemanticaOa().getId()),
					"imsmd_educational", "imsmd_semanticdensity", "imsmd_value", "imsmd_langstring"); // 5.4
			xml.agregarElemento(doc, "es", foaEtapaDTO.getRangoTipicoDeEdad(), "imsmd_educational",
					"imsmd_typicalagerange", "imsmd_langstring"); // 5.7
			xml.agregarElemento(doc, "es",
					String.valueOf(foaEtapaDTO.getFichaDescripcionOa().getCatDificultadOa().getId()),
					"imsmd_educational", "imsmd_difficulty", "imsmd_value", "imsmd_langstring"); // 5.8
			xml.actualizarElemento(doc, "es", "imsmd_typicallearningtime", "imsmd_langstring",
					foaEtapaDTO.getTiempoTipicoAprendizaje());// 5.9 Tiempo de
																// aprendizaje

			xml.agregarElemento(doc, "es", foaEtapaDTO.getTieneCosto() ? "SI" : "NO", "imsmd_rights", "imsmd_cost",
					"imsmd_value", "imsmd_langstring"); // 6.1

			// Revisar que sean las posiciones y valores adecuados
			xml.actualizarElemento(doc, "es", "imsmd_coverage", "imsmd_langstring", foaEtapaDTO.getAmbitoAplicacion()); // 1.6
																														// Ámbito
																														// (Coverage)
			xml.actualizarElemento(doc, "es", "imsmd_keyword", "imsmd_langstring", foaEtapaDTO.getPalabrasClave()); // 1.5
																													// Palabras
																													// clave
			xml.actualizarElemento(doc, "es", "imsmd_identifier", "imsmd_catalog",
					foaEtapaDTO.getEstaDisponible() ? "SI" : "NO"); // 1.1
																	// Catalog
			xml.actualizarElemento(doc, "es", "imsmd_version", "imsmd_langstring", foaEtapaDTO.getVersion());// 2.1
																												// Version
			xml.actualizarElemento(doc, "es", "imsmd_date", "imsmd_datetime",
					foaEtapaDTO.getFechaLiberacion().toString());// Fecha
																	// liberacion

		} catch (IOException | ParserConfigurationException | TransformerException | SAXException ex) {
			LOG.error("Error al generar XML", ex);
		}
	}

	public void descargarXML(UnidadOaAvaDTO unidadOaAvaDTO) {
		try {
			InputStream stream = new FileInputStream(copiaPlatillaXML());

			this.unidadOaAvaDTO = unidadOaAvaDTO;
			cargarFOA();
			/*
			 * Por qué iniciallizar FOA después de cargar los datos? Provoca que
			 * al momento de generar el XML los datos del FOA estén vacíos
			 * inicializarFoa();
			 */

			foaEtapaDTO.setIdFoa(null);
			this.unidadOaAvaDTO.setFichaDescriptivaObjetoAprendizaje(new FichaDescriptivaOaDTO());
			generarXML(this.unidadOaAvaDTO, foaEtapaDTO);

			fileDownload = new DefaultStreamedContent(stream, ConstantesGestorWeb.HTTP_HEADER_CONTENTTYPE_XML,
					ConstantesGestorWeb.NOMBRE_XML);
			LOG.info("Se ha generado el archivo: " + fileDownload.getName());
		} catch (FileNotFoundException ex) {
			LOG.error("Error en la descarga del archivo", ex);
		}
	}

	private File copiaPlatillaXML() {
		InputStream platillaXML = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/xml/plantilla.xml");
		File reporteXML = new File(System.getProperty("user.home") + ConstantesGestorWeb.NOMBRE_XML);
		LOG.info("Copiando plantilla a reporte: \n" + platillaXML.toString() + "\n" + reporteXML.getAbsolutePath());
		try {
			FileUtils.copyInputStreamToFile(platillaXML, reporteXML);
		} catch (IOException ex) {
			LOG.error("Error al copiar la platilla: ", ex);
		}
		return reporteXML;
	}

	public void descargarXML() {
		try {
			LOG.info("Descargar archivos");
			File file = new File(System.getProperty("user.home") + "/plantilla.xml");

			InputStream stream = new FileInputStream(file);

			fileDownload = new DefaultStreamedContent(stream, ConstantesGestorWeb.HTTP_HEADER_CONTENTTYPE_XML,
					"plantilla.xml");
		} catch (FileNotFoundException ex) {
			LOG.error("Error en la descarga del archivo", ex);
		}
	}

	public String cargarFOA() {
		LOG.info("La unidad seleccionada es: " + unidadOaAvaDTO.getId());
		LOG.info("Navega documentar foa");
		if (ObjectUtils.isNotNull(unidadOaAvaDTO.getFichaDescriptivaObjetoAprendizaje())
				&& ObjectUtils.isNotNull(unidadOaAvaDTO.getFichaDescriptivaObjetoAprendizaje().getIdFoa())) {
			cargarDatosFoa(unidadOaAvaDTO.getFichaDescriptivaObjetoAprendizaje().getIdFoa());
			botonFinalizar = false;
			setIndicePanel(0);
		} else {
			inicializarFoa();
			foaEtapaDTO.setIdFoa(null);
			unidadOaAvaDTO.setFichaDescriptivaObjetoAprendizaje(new FichaDescriptivaOaDTO());

			List<CargaArchivosOaDTO> listaArchivos = obtenerListaArchivos(unidadOaAvaDTO);

			if (listaArchivos.size() > 0) {
				foaEtapaDTO.setPesoArchivosScorm(listaArchivos.get(0).getPesoArchivo());
				nombreOA = listaArchivos.get(0).getNombreArchivo();
			}

			mensajes = new ArrayList<>();
			botonFinalizar = false;

			armaArbol(unidadOaAvaDTO);
			setIndicePanel(0);

		}

		return ConstantesGestorWeb.NAVEGA_DOCUMENTAR_FOA;
	}

	private List<CargaArchivosOaDTO> obtenerListaArchivos(UnidadOaAvaDTO unidadOa) {
		CatalogoComunDTO catComunDTO = new CatalogoComunDTO();

		catComunDTO.setId(ClasificacionArchivoOaEnum.SCORM.getId());
		catComunDTO.setNombre(ClasificacionArchivoOaEnum.SCORM.getNombre());
		catComunDTO.setDescripcion(ClasificacionArchivoOaEnum.SCORM.getNombre());
		return cargaArchivosOaService.buscarPorIdUnidadOa(unidadOa, catComunDTO);
	}

	private void inicializarFoa() {
		foaEtapaDTO = new FichaDescriptivaOaDTO();
		foaEtapaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		foaEtapaDTO.setPesoArchivosScorm("0KB");

		establecerValorSwitchesFOA();
		limpiarListasDeSeleccionFOA();

	}

	private void limpiarListasDeSeleccionFOA() {
		listaElementosMultimediaSeleccionados = new ArrayList<>();
		listaActividadesReforzamientoSeleccionado = new ArrayList<>();
		listaMaterialDidacticoSeleccionado = new ArrayList<>();
		listaActividadesReforzamientoSeleccionado = new ArrayList<>();
		listaRecursoPredominanteSeleccionado = new ArrayList<>();
		listaRelacionOtrosObjetosSeleccionado = new ArrayList<>();
	}

	private void cargarDatosFoa(Integer idFoa) {
		CatalogoComunDTO catComunDTO = new CatalogoComunDTO();

		catComunDTO.setId(ClasificacionArchivoOaEnum.SCORM.getId());
		catComunDTO.setNombre(ClasificacionArchivoOaEnum.SCORM.getNombre());
		catComunDTO.setDescripcion(ClasificacionArchivoOaEnum.SCORM.getNombre());

		listaArchivosOA = cargaArchivosOaService.buscarPorIdUnidadOa(unidadOaAvaDTO, catComunDTO);

		foaEtapaDTO = new FichaDescriptivaOaDTO();

		foaEtapaDTO = fichaDescriptivaOaService.buscarPorId(idFoa);

		if (listaArchivosOA.size() > 0) {
			foaEtapaDTO.setPesoArchivosScorm(listaArchivosOA.get(0).getPesoArchivo());
			nombreOA = listaArchivosOA.get(0).getNombreArchivo();
		}

		establecerValorSwitchesFOA();

		// Consulta en base de datos, los elementos multimedia seleccionados
		listaElementosMultimediaSeleccionados = obtenerElementosMultimediaPorId(consultarBDElementosMultimedia(idFoa));

		// Consulta en base de de datos los materiales didacticos seleccionado
		listaMaterialDidacticoSeleccionado = obtenerMaterialDidacticoPorId(consultarBDMaterialDidactico(idFoa));

		// Consulta en base de datos las actividades de reforzamiento
		listaActividadesReforzamientoSeleccionado = obtenerActividadReforzamientoPorId(
				consultarBDActividadReforzamiento(idFoa));

		// Consulta en base de datos los recursos predominantes
		listaRecursoPredominanteSeleccionado = obtenerRecursoPredominantePorId(consultarBDRecursoPredominante(idFoa));

		// Consulta en base de datos las relaciones con otros objetos.
		listaRelacionOtrosObjetosSeleccionado = obtenerRelacionOtroObjetoPorId(consultarBDRelacionOtroObjeto(idFoa));
		armaArbol(unidadOaAvaDTO);

		estatusBDFF = (ObjectUtils.isNull(foaEtapaDTO.getEstatusDF()) ? false : foaEtapaDTO.getEstatusDF());
	}

	/**
	 * Inicializa el valor de los tres inputSwitch que estan en el FOA
	 */
	private void establecerValorSwitchesFOA() {
		if (ObjectUtils.isNull(foaEtapaDTO.getEstaDisponible())) {
			foaEtapaDTO.setEstaDisponible(Boolean.FALSE);
		}
		if (ObjectUtils.isNull(foaEtapaDTO.getTieneCosto())) {
			foaEtapaDTO.setTieneCosto(Boolean.FALSE);
		}
		if (ObjectUtils.isNull(foaEtapaDTO.getTieneDerechosAutor())) {
			foaEtapaDTO.setTieneDerechosAutor(Boolean.FALSE);
		}
	}

	// Consulta de las tablas relacionadas.
	private List<ElementosMultimediaFoaDTO> consultarBDElementosMultimedia(Integer idFoa) {
		List<ElementosMultimediaFoaDTO> emGuardados = new ArrayList<>();
		emGuardados = fichaDescriptivaOaServiceFacade.getRelElementosMultimediaFoaService()
				.buscarElementoMultimediaFoa(idFoa);
		return emGuardados;
	}

	private List<CatalogoComunDTO> obtenerElementosMultimediaPorId(List<ElementosMultimediaFoaDTO> emGuardados) {
		List<CatalogoComunDTO> listaElemMultSel = new ArrayList<CatalogoComunDTO>();
		for (ElementosMultimediaFoaDTO elementosMultimediaFoaDTO : emGuardados) {
			for (CatalogoComunDTO elementoMultimedia : listaElementosMultimedia) {
				if (elementoMultimedia.getId().equals(elementosMultimediaFoaDTO.getIdElementoMultimediaFoa())) {
					listaElemMultSel.add(elementoMultimedia);
				}
			}
		}
		return listaElemMultSel;
	}

	private List<RelMaterialDidacticoFoaDTO> consultarBDMaterialDidactico(Integer idFoa) {
		List<RelMaterialDidacticoFoaDTO> mdGuardados = new ArrayList<>();
		mdGuardados = fichaDescriptivaOaServiceFacade.getRelMaterialDidacticoFoaService()
				.buscarMaterialDidacticoFoa(idFoa);
		return mdGuardados;
	}

	private List<CatalogoComunDTO> obtenerMaterialDidacticoPorId(List<RelMaterialDidacticoFoaDTO> mdGuardados) {
		List<CatalogoComunDTO> listaMaterialDidacticoSeleccionado = new ArrayList<CatalogoComunDTO>();
		for (RelMaterialDidacticoFoaDTO materialDidacticoFoaDTO : mdGuardados) {
			for (CatalogoComunDTO materialDidactico : listaMaterialDidactico) {
				if (materialDidactico.getId().equals(materialDidacticoFoaDTO.getIdCatRecursoDidactico())) {
					listaMaterialDidacticoSeleccionado.add(materialDidactico);
				}
			}
		}
		return listaMaterialDidacticoSeleccionado;
	}

	private List<RelActividadesReforzamientoFoaDTO> consultarBDActividadReforzamiento(Integer idFoa) {
		List<RelActividadesReforzamientoFoaDTO> arGuardados = new ArrayList<>();
		arGuardados = fichaDescriptivaOaServiceFacade.getRelActividadesReforzamientoFoaService()
				.buscarActividadReforzamientoFoa(idFoa);
		return arGuardados;
	}

	private List<CatalogoComunDTO> obtenerActividadReforzamientoPorId(
			List<RelActividadesReforzamientoFoaDTO> arGuardados) {
		List<CatalogoComunDTO> listaActividadReforzamientoSeleccionado = new ArrayList<CatalogoComunDTO>();
		for (RelActividadesReforzamientoFoaDTO actividadReforzamientoFoaDTO : arGuardados) {
			for (CatalogoComunDTO actividadReforzamiento : listaActivdadesReforzamiento) {
				if (actividadReforzamiento.getId().equals(actividadReforzamientoFoaDTO.getIdActividadReforzamiento())) {
					listaActividadReforzamientoSeleccionado.add(actividadReforzamiento);
				}
			}
		}
		return listaActividadReforzamientoSeleccionado;
	}

	private List<RelRecursoPredominanteFoaDTO> consultarBDRecursoPredominante(Integer idFoa) {
		List<RelRecursoPredominanteFoaDTO> rpGuardados = new ArrayList<>();
		rpGuardados = fichaDescriptivaOaServiceFacade.getRelRecursoPredominanteFoaService()
				.buscarRecursoPredominanteFoa(idFoa);
		return rpGuardados;
	}

	private List<CatalogoComunDTO> obtenerRecursoPredominantePorId(List<RelRecursoPredominanteFoaDTO> rpGuardados) {
		List<CatalogoComunDTO> listaRecursoPredominanteSeleccionado = new ArrayList<CatalogoComunDTO>();
		for (RelRecursoPredominanteFoaDTO recursoPredominanteFoaDTO : rpGuardados) {
			for (CatalogoComunDTO recursoPredominante : listaRecursoPredominante) {
				if (recursoPredominante.getId().equals(recursoPredominanteFoaDTO.getIdCatRecursoPredominante())) {
					listaRecursoPredominanteSeleccionado.add(recursoPredominante);
				}
			}
		}
		return listaRecursoPredominanteSeleccionado;
	}

	private List<RelRelacionOtrosObjetosFoaDTO> consultarBDRelacionOtroObjeto(Integer idFoa) {
		List<RelRelacionOtrosObjetosFoaDTO> rooGuardados = new ArrayList<>();
		rooGuardados = fichaDescriptivaOaServiceFacade.getRelRelacionOtrosObjetosFoaServices()
				.buscarRelacionOtrosObjetosFoa(idFoa);
		return rooGuardados;
	}

	private List<CatalogoComunDTO> obtenerRelacionOtroObjetoPorId(List<RelRelacionOtrosObjetosFoaDTO> rooGuardados) {
		List<CatalogoComunDTO> listaRelacionOtrosObjetoSeleccionado = new ArrayList<CatalogoComunDTO>();
		for (RelRelacionOtrosObjetosFoaDTO relacionOtroObjetoFoaDTO : rooGuardados) {
			for (CatalogoComunDTO relacionOtroObjeto : listaRelacionOtrosObjetos) {
				if (relacionOtroObjeto.getId().equals(relacionOtroObjetoFoaDTO.getIdCatRelacionOtrosObjetos())) {
					listaRelacionOtrosObjetoSeleccionado.add(relacionOtroObjeto);
				}
			}
		}
		return listaRelacionOtrosObjetoSeleccionado;
	}

	// Operaciones de la página. Cancelar, Guardar y actuzalizar.
	public String cancelar() {
		return ConstantesGestorWeb.NAVEGA_MODIFICAR_SEGUIMIENTO_AVA;
	}

	public void guardarBorrador() {
		try {
			foaEtapaDTO.getFichaDescripcionOa().getCatEstatusFoa().setId(ConstantesGestor.FOA_ESTATUS_BORRADOR);
			/* Operaciona true actualiza */
			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getIdFoa())) {
				guardar();
			} else {
				actualizar();
			}
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "GUA_BOR_FOA", "", requestActual(),
					TipoServicioEnum.LOCAL);
		} catch (Exception e) {
			logger.info("Ocurrio un error al guardar el borrador del FOA" + e);
		}
	}

	public void finalizar() {
		try {
			foaEtapaDTO.setEstatusDF(true);
			foaEtapaDTO.setFechaLiberacion(getFechaActual());
			if (mensajes.size() == 0) {
				foaEtapaDTO.getFichaDescripcionOa().getCatEstatusFoa().setId(ConstantesGestor.FOA_ESTATUS_FINAL);
				if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getIdFoa())) {
					guardar();
				} else {
					actualizar();
				}
			}
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "FIN_FOA", "", requestActual(), TipoServicioEnum.LOCAL);
		} catch (Exception e) {
			logger.info("Ocurrio un error al finalizar la captura del FOA");
		}
	}

	// Método para pasar al tab 2
	public void guardarFichaDescriptiva() {
		indicePanel = 1;
	}

	// Método para pasar al tab 3
	public void guardarElementosInstruccionales() {
		indicePanel = 2;
	}

	// Método para pasar al tab 4
	public void guardarDescripcion() {
		indicePanel = 3;
	}

	public void guardar() {
		foaEtapaDTO.setFechaRegistro(getFechaActual());
		foaEtapaDTO = fichaDescriptivaOaService.guardar(foaEtapaDTO).getDto();
		if (!ObjectUtils.isNullOrEmpty(foaEtapaDTO.getIdFoa())) {
			unidadOaAvaDTO.getFichaDescriptivaObjetoAprendizaje().setIdFoa(foaEtapaDTO.getIdFoa());
			unidadOaAvaDTO.setFechaActualizacion(new Date());
			unidadOaAvaDTO.setUsuarioModifico(BigInteger.valueOf(this.getUsuarioEnSession().getIdPersona()));
			fichaDescriptivaOaServiceFacade.getRelUnidadOaAvaService().actualizar(unidadOaAvaDTO);
			guardarElementosMultimedia(foaEtapaDTO.getIdFoa());
			guardarMaterialDidactico(foaEtapaDTO.getIdFoa());
			guardarActividadesReforzamiento(foaEtapaDTO.getIdFoa());
			guardarRelacionOtrosObjetos(foaEtapaDTO.getIdFoa());
		}
	}

	public void actualizar() {
		foaEtapaDTO.setFechaActualizacion(getFechaActual());
		// GUSTAVO --BitacoraUtil.llenarBitacora(foaEtapaDTO,
		// getUsuarioEnSession(),
		// ConstantesBitacora.FICHA_DESC_OBJETO_AP_EDITAR, request);
		foaEtapaDTO = getFichaDescriptivaOaService().actualizar(foaEtapaDTO).getDto();

		if (!ObjectUtils.isNullOrEmpty(foaEtapaDTO.getIdFoa())) {
			eliminarElementosMultimedia(foaEtapaDTO.getIdFoa());
			guardarElementosMultimedia(foaEtapaDTO.getIdFoa());
			eliminarMaterialDidactico(foaEtapaDTO.getIdFoa());
			guardarMaterialDidactico(foaEtapaDTO.getIdFoa());
			eliminarActividadReforzamiento(foaEtapaDTO.getIdFoa());
			guardarActividadesReforzamiento(foaEtapaDTO.getIdFoa());
			// eliminarRecursoPredominante(foaEtapaDTO.getIdFoa());
			// guardarRecursoPredominante(foaEtapaDTO.getIdFoa());
			eliminarRelacionOtroObjeto(foaEtapaDTO.getIdFoa());
			guardarRelacionOtrosObjetos(foaEtapaDTO.getIdFoa());
		}
	}

	private void guardarElementosMultimedia(Integer idFoa) {
		if (!ObjectUtils.isNullOrEmpty(listaElementosMultimediaSeleccionados)) {
			for (CatalogoComunDTO elementoMultimedia : listaElementosMultimediaSeleccionados) {
				// Cuando se envia a guardar borrador
				elementosMultimediaFoaDTO = new ElementosMultimediaFoaDTO();
				elementosMultimediaFoaDTO.setIdFoa(idFoa);
				elementosMultimediaFoaDTO.setIdElementoMultimediaFoa(elementoMultimedia.getId());
				elementosMultimediaFoaDTO.setFechaRegistro(getFechaActual());
				elementosMultimediaFoaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
				fichaDescriptivaOaServiceFacade.getRelElementosMultimediaFoaService()
						.guardar(elementosMultimediaFoaDTO);
			}
		}
	}

	private void eliminarElementosMultimedia(Integer idFoa) {
		for (CatalogoComunDTO items : listaElementosMultimedia) {
			elementosMultimediaFoaDTO.setIdFoa(idFoa);
			elementosMultimediaFoaDTO.setIdElementoMultimediaFoa(items.getId());
			elementosMultimediaFoaDTO.setFechaRegistro(getFechaActual());
			elementosMultimediaFoaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			fichaDescriptivaOaServiceFacade.getRelElementosMultimediaFoaService().eliminar(elementosMultimediaFoaDTO);
		}
	}

	private void guardarMaterialDidactico(Integer idFoa) {
		if (!ObjectUtils.isNullOrEmpty(listaMaterialDidacticoSeleccionado)) {
			for (CatalogoComunDTO items : listaMaterialDidacticoSeleccionado) {
				materialDidacticoFoaDTO.setIdFoa(idFoa);
				materialDidacticoFoaDTO.setIdCatRecursoDidactico(items.getId());
				materialDidacticoFoaDTO.setFechaRegistro(getFechaActual());
				materialDidacticoFoaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
				fichaDescriptivaOaServiceFacade.getRelMaterialDidacticoFoaService().guardar(materialDidacticoFoaDTO);
			}
		}
	}

	private void eliminarMaterialDidactico(Integer idFoa) {
		for (CatalogoComunDTO items : listaMaterialDidactico) {
			materialDidacticoFoaDTO.setIdFoa(idFoa);
			materialDidacticoFoaDTO.setIdCatRecursoDidactico(items.getId());
			materialDidacticoFoaDTO.setFechaRegistro(getFechaActual());
			materialDidacticoFoaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			fichaDescriptivaOaServiceFacade.getRelMaterialDidacticoFoaService().eliminar(materialDidacticoFoaDTO);
		}
	}

	private void guardarActividadesReforzamiento(Integer idFoa) {
		if (!ObjectUtils.isNullOrEmpty(listaActividadesReforzamientoSeleccionado)) {
			for (CatalogoComunDTO items : listaActividadesReforzamientoSeleccionado) {
				actividadesReforzamientoFoaDTO.setIdFoa(idFoa);
				actividadesReforzamientoFoaDTO.setIdActividadReforzamiento(items.getId());
				actividadesReforzamientoFoaDTO.setFechaRegistro(getFechaActual());
				actividadesReforzamientoFoaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
				fichaDescriptivaOaServiceFacade.getRelActividadesReforzamientoFoaService()
						.guardar(actividadesReforzamientoFoaDTO);
			}
		}

	}

	private void eliminarActividadReforzamiento(Integer idFoa) {
		for (CatalogoComunDTO items : listaActivdadesReforzamiento) {
			actividadesReforzamientoFoaDTO.setIdFoa(idFoa);
			actividadesReforzamientoFoaDTO.setIdActividadReforzamiento(items.getId());
			actividadesReforzamientoFoaDTO.setFechaRegistro(getFechaActual());
			actividadesReforzamientoFoaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			// GUSTAVO
			// --BitacoraUtil.llenarBitacora(actividadesReforzamientoFoaDTO,
			// getUsuarioEnSession(), "DOCUMENTAR_FOA_ELIMINAR", request);
			fichaDescriptivaOaServiceFacade.getRelActividadesReforzamientoFoaService()
					.eliminar(actividadesReforzamientoFoaDTO);
		}
	}

	private void guardarRecursoPredominante(Integer idFoa) {
		if (!ObjectUtils.isNullOrEmpty(listaRecursoPredominanteSeleccionado)) {
			for (CatalogoComunDTO items : listaRecursoPredominanteSeleccionado) {
				recursoPredominanteFoaDTO.setIdFoa(idFoa);
				recursoPredominanteFoaDTO.setIdCatRecursoPredominante(items.getId());
				recursoPredominanteFoaDTO.setFechaRegistro(getFechaActual());
				recursoPredominanteFoaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
				// GUSTAVO
				// --BitacoraUtil.llenarBitacora(recursoPredominanteFoaDTO,
				// getUsuarioEnSession(),
				// ConstantesBitacora.DOCUMENTAR_FOA_GUARDAR, request);
				fichaDescriptivaOaServiceFacade.getRelRecursoPredominanteFoaService()
						.guardar(recursoPredominanteFoaDTO);
			}
		}

	}

	private void eliminarRecursoPredominante(Integer idFoa) {
		for (CatalogoComunDTO items : listaRecursoPredominante) {
			recursoPredominanteFoaDTO.setIdFoa(idFoa);
			recursoPredominanteFoaDTO.setIdCatRecursoPredominante(items.getId());
			recursoPredominanteFoaDTO.setFechaRegistro(getFechaActual());
			recursoPredominanteFoaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			// GUSTAVO
			// --BitacoraUtil.llenarBitacora(actividadesReforzamientoFoaDTO,
			// getUsuarioEnSession(), "DOCUMENTAR_FOA_ELIMINAR", request);
			fichaDescriptivaOaServiceFacade.getRelRecursoPredominanteFoaService().eliminar(recursoPredominanteFoaDTO);
		}
	}

	private void guardarRelacionOtrosObjetos(Integer idFoa) {
		if (!ObjectUtils.isNullOrEmpty(listaRelacionOtrosObjetosSeleccionado)) {
			for (CatalogoComunDTO items : listaRelacionOtrosObjetosSeleccionado) {
				relacionOtrosObjetosFoaDTO.setIdFoa(idFoa);
				relacionOtrosObjetosFoaDTO.setIdCatRelacionOtrosObjetos(items.getId());
				relacionOtrosObjetosFoaDTO.setFechaRegistro(getFechaActual());
				relacionOtrosObjetosFoaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
				fichaDescriptivaOaServiceFacade.getRelRelacionOtrosObjetosFoaServices()
						.guardar(relacionOtrosObjetosFoaDTO);
			}
		}
	}

	private void eliminarRelacionOtroObjeto(Integer idFoa) {
		for (CatalogoComunDTO items : listaRelacionOtrosObjetos) {
			relacionOtrosObjetosFoaDTO.setIdFoa(idFoa);
			relacionOtrosObjetosFoaDTO.setIdCatRelacionOtrosObjetos(items.getId());
			relacionOtrosObjetosFoaDTO.setFechaRegistro(getFechaActual());
			relacionOtrosObjetosFoaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			// GUSTAVO
			// --BitacoraUtil.llenarBitacora(actividadesReforzamientoFoaDTO,
			// getUsuarioEnSession(), "DOCUMENTAR_FOA_ELIMINAR", request);
			fichaDescriptivaOaServiceFacade.getRelRelacionOtrosObjetosFoaServices()
					.eliminar(relacionOtrosObjetosFoaDTO);
		}
	}

	/**
	 * Metodo que arma el arbol representativo de los subtemas del objeto de
	 * aprendizaje
	 *
	 * @param unidadOaAvaDTO
	 */
	private void armaArbol(UnidadOaAvaDTO unidadOaAvaDTO) {

		if (ObjectUtils.isNotNull(unidadOaAvaDTO.getDetEstUnidadDidactica())) {

			root = new DefaultTreeNode(unidadOaAvaDTO.getDetEstUnidadDidactica().getNombreUnidad(), null);

			List<SubtemasUDidacticaDTO> subtemasUD = unidadOaAvaDTO.getDetEstUnidadDidactica().getSubtemasUdidactica();

			for (SubtemasUDidacticaDTO subtemasUDidacticaDTO : subtemasUD) {

				TreeNode subtema = new DefaultTreeNode(subtemasUDidacticaDTO.getNombre(), root);

				root.getChildren().add(subtema);

			}
		}
	}

	public void habilitarFinalizar() {
		verificarValores();
		if (mensajes.size() == 0) {
			estatusBDFF = true;
			botonFinalizar = true;
		} else {
			estatusBDFF = false;
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg_faltan_campos').show();");
			context.scrollTo("dialogo_faltan_campos");
		}

	}

	public void verificarValores() {
		mensajes = new ArrayList<>();

		if (ObjectUtils.isNull(foaEtapaDTO)) {
			mensajes.add("Objeto invalido");
		} else {
			/* Datos generales */

			/* Elementos instruccionales */

			/* Descripción del OA */

			/* Contexto */
			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getAnotacion())) {
				mensajes.add("Anotación es un campo requerido");
			}

			// if(ObjectUtils.isNullOrEmpty(foaEtapaDTO.getComentarios()))
			// {
			// mensajes.add("Comentarios es un campo requerido");
			// }
			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getCondicionesUso())) {
				mensajes.add("Condiciones de uso es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getDescripcionContenido())) {
				mensajes.add("Descripcion del contenido es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getEstaDisponible())) {
				mensajes.add("Está disponible es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getFechaLiberacion())) {
				mensajes.add("Fecha de liberación es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getLugarEdicion())) {
				mensajes.add("Lugar es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getNombreActividad())) {
				mensajes.add("Tipo de evento de capacitación es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getObjetivoGeneral())) {
				mensajes.add("Objetivo general es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getPalabrasClave())) {
				mensajes.add("Palabras clave es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getPesoArchivosScorm())) {
				mensajes.add("Peso Archivos SCORM es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getProposito())) {
				mensajes.add("Propósito es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getRangoTipicoDeEdad())) {
				mensajes.add("Rango típico de edad es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getResumen())) {
				mensajes.add("Resumen es un campo requerido es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getRequisitosConocimientosPrevios())) {
				mensajes.add("Requisitos es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getResponsableAcademico())) {
				mensajes.add("Responsable académico es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getTemasRelacionados())) {
				mensajes.add("Temas relacionados es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getTiempoEstimadoRepLectura())) {
				mensajes.add("Tiempo estimado de resproducción o lectura es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getTiempoTipicoAprendizaje())) {
				mensajes.add("Tiempo típico aprendizaje es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getTieneCosto())) {
				mensajes.add("Tiene costo es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getTieneDerechosAutor())) {
				mensajes.add("Tiene derechos de autor es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getTratamientoEditorial())) {
				mensajes.add("Tramiento editorial es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getVersion())) {
				mensajes.add("Versión es un campo requerido");
			}

			if (ObjectUtils.isNullOrEmpty(foaEtapaDTO.getFichaDescripcionOa().getCatRecursoPredominanteFoa())) {
				mensajes.add("Recurso predominante es un campo requerido");
			}
		}
	}

	/**
	 * ******************** Getters y Setters
	 * ****************************************************
	 */
	/**
	 * @return the fichaDescriptivaOaServiceFacade
	 */
	public FichaDescriptivaOaServiceFacade getFichaDescriptivaOaServiceFacade() {
		return fichaDescriptivaOaServiceFacade;
	}

	/**
	 * @param fichaDescriptivaOaServiceFacade
	 *            the fichaDescriptivaOaServiceFacade to set
	 */
	public void setFichaDescriptivaOaServiceFacade(FichaDescriptivaOaServiceFacade fichaDescriptivaOaServiceFacade) {
		this.fichaDescriptivaOaServiceFacade = fichaDescriptivaOaServiceFacade;
	}

	public ElementosMultimediaFoaDTO getElementosMultimediaFoaDTO() {
		return elementosMultimediaFoaDTO;
	}

	public void setElementosMultimediaFoaDTO(ElementosMultimediaFoaDTO elementosMultimediaFoaDTO) {
		this.elementosMultimediaFoaDTO = elementosMultimediaFoaDTO;
	}

	public RelMaterialDidacticoFoaDTO getMaterialDidacticoFoaDTO() {
		return materialDidacticoFoaDTO;
	}

	public void setMaterialDidacticoFoaDTO(RelMaterialDidacticoFoaDTO materialDidacticoFoaDTO) {
		this.materialDidacticoFoaDTO = materialDidacticoFoaDTO;
	}

	public RelActividadesReforzamientoFoaDTO getActividadesReforzamientoFoaDTO() {
		return actividadesReforzamientoFoaDTO;
	}

	public void setActividadesReforzamientoFoaDTO(RelActividadesReforzamientoFoaDTO actividadesReforzamientoFoaDTO) {
		this.actividadesReforzamientoFoaDTO = actividadesReforzamientoFoaDTO;
	}

	public List<CatalogoComunDTO> getListaElementosMultimedia() {
		return listaElementosMultimedia;
	}

	public void setListaElementosMultimedia(List<CatalogoComunDTO> listaElementosMultimedia) {
		this.listaElementosMultimedia = listaElementosMultimedia;
	}

	public List<CatalogoComunDTO> getListaElementosMultimediaSeleccionados() {
		return listaElementosMultimediaSeleccionados;
	}

	public void setListaElementosMultimediaSeleccionados(List<CatalogoComunDTO> listaElementosMultimediaSeleccionados) {
		this.listaElementosMultimediaSeleccionados = listaElementosMultimediaSeleccionados;
	}

	public List<FichaDescriptivaOaDTO> getListaFOAS() {
		return listaFOAS;
	}

	public void setListaFOAS(List<FichaDescriptivaOaDTO> listaFOAS) {
		this.listaFOAS = listaFOAS;
	}

	public List<CatalogoComunDTO> getListaTipoEstructura() {
		return listaTipoEstructura;
	}

	public void setListaTipoEstructura(List<CatalogoComunDTO> listaTipoEstructura) {
		this.listaTipoEstructura = listaTipoEstructura;
	}

	public List<CatalogoComunDTO> getListaTipoContenido() {
		return listaTipoContenido;
	}

	public void setListaTipoContenido(List<CatalogoComunDTO> listaTipoContenido) {
		this.listaTipoContenido = listaTipoContenido;
	}

	public List<CatalogoComunDTO> getListaTipoInteractividad() {
		return listaTipoInteractividad;
	}

	public void setListaTipoInteractividad(List<CatalogoComunDTO> listaTipoInteractividad) {
		this.listaTipoInteractividad = listaTipoInteractividad;
	}

	public List<CatalogoComunDTO> getListaTipoRecursoPredominante() {
		return listaTipoRecursoPredominante;
	}

	public void setListaTipoRecursoPredominante(List<CatalogoComunDTO> listaTipoRecursoPredominante) {
		this.listaTipoRecursoPredominante = listaTipoRecursoPredominante;
	}

	public List<CatalogoComunDTO> getListaDensidadSemantica() {
		return listaDensidadSemantica;
	}

	public void setListaDensidadSemantica(List<CatalogoComunDTO> listaDensidadSemantica) {
		this.listaDensidadSemantica = listaDensidadSemantica;
	}

	public List<CatalogoComunDTO> getListaNivelDificultad() {
		return listaNivelDificultad;
	}

	public void setListaNivelDificultad(List<CatalogoComunDTO> listaNivelDificultad) {
		this.listaNivelDificultad = listaNivelDificultad;
	}

	public List<CatalogoComunDTO> getListaIdioma() {
		return listaIdioma;
	}

	public void setListaIdioma(List<CatalogoComunDTO> listaIdioma) {
		this.listaIdioma = listaIdioma;
	}

	public List<CatalogoComunDTO> getListaAmbito() {
		return listaAmbito;
	}

	public void setListaAmbito(List<CatalogoComunDTO> listaAmbito) {
		this.listaAmbito = listaAmbito;
	}

	public List<CatalogoComunDTO> getListaNivelGranularidad() {
		return listaNivelGranularidad;
	}

	public void setListaNivelGranularidad(List<CatalogoComunDTO> listaNivelGranularidad) {
		this.listaNivelGranularidad = listaNivelGranularidad;
	}

	public List<CatalogoComunDTO> getListaComposicionObjeto() {
		return listaComposicionObjeto;
	}

	public void setListaComposicionObjeto(List<CatalogoComunDTO> listaComposicionObjeto) {
		this.listaComposicionObjeto = listaComposicionObjeto;
	}

	public List<CatalogoComunDTO> getListaFormato() {
		return listaFormato;
	}

	public void setListaFormato(List<CatalogoComunDTO> listaFormato) {
		this.listaFormato = listaFormato;
	}

	public List<CatalogoComunDTO> getListaRelacionOtrosObjetos() {
		return listaRelacionOtrosObjetos;
	}

	public void setListaRelacionOtrosObjetos(List<CatalogoComunDTO> listaRelacionOtrosObjetos) {
		this.listaRelacionOtrosObjetos = listaRelacionOtrosObjetos;
	}

	public List<CatalogoComunDTO> getListaEstatus() {
		return listaEstatus;
	}

	public void setListaEstatus(List<CatalogoComunDTO> listaEstatus) {
		this.listaEstatus = listaEstatus;
	}

	public List<CatalogoComunDTO> getListaMaterialDidactico() {
		return listaMaterialDidactico;
	}

	public void setListaMaterialDidactico(List<CatalogoComunDTO> listaMaterialDidactico) {
		this.listaMaterialDidactico = listaMaterialDidactico;
	}

	public List<CatalogoComunDTO> getListaMaterialDidacticoSeleccionado() {
		return listaMaterialDidacticoSeleccionado;
	}

	public void setListaMaterialDidacticoSeleccionado(List<CatalogoComunDTO> listaMaterialDidacticoSeleccionado) {
		this.listaMaterialDidacticoSeleccionado = listaMaterialDidacticoSeleccionado;
	}

	public List<CatalogoComunDTO> getListaActivdadesReforzamiento() {
		return listaActivdadesReforzamiento;
	}

	public void setListaActivdadesReforzamiento(List<CatalogoComunDTO> listaActivdadesReforzamiento) {
		this.listaActivdadesReforzamiento = listaActivdadesReforzamiento;
	}

	public List<CatalogoComunDTO> getListaActividadesReforzamientoSeleccionado() {
		return listaActividadesReforzamientoSeleccionado;
	}

	public void setListaActividadesReforzamientoSeleccionado(
			List<CatalogoComunDTO> listaActividadesReforzamientoSeleccionado) {
		this.listaActividadesReforzamientoSeleccionado = listaActividadesReforzamientoSeleccionado;
	}

	/**
	 * @return the fichaDescriptivaOaService
	 */
	public FichaDescriptivaOaService getFichaDescriptivaOaService() {
		return fichaDescriptivaOaService;
	}

	/**
	 * @param fichaDescriptivaOaService
	 *            the fichaDescriptivaOaService to set
	 */
	public void setFichaDescriptivaOaService(FichaDescriptivaOaService fichaDescriptivaOaService) {
		this.fichaDescriptivaOaService = fichaDescriptivaOaService;
	}

	/**
	 * @return the recursoPredominanteFoaDTO
	 */
	public RelRecursoPredominanteFoaDTO getRecursoPredominanteFoaDTO() {
		return recursoPredominanteFoaDTO;
	}

	/**
	 * @param recursoPredominanteFoaDTO
	 *            the recursoPredominanteFoaDTO to set
	 */
	public void setRecursoPredominanteFoaDTO(RelRecursoPredominanteFoaDTO recursoPredominanteFoaDTO) {
		this.recursoPredominanteFoaDTO = recursoPredominanteFoaDTO;
	}

	/**
	 * @return the listaRecursoPredominante
	 */
	public List<CatalogoComunDTO> getListaRecursoPredominante() {
		return listaRecursoPredominante;
	}

	/**
	 * @param listaRecursoPredominante
	 *            the listaRecursoPredominante to set
	 */
	public void setListaRecursoPredominante(List<CatalogoComunDTO> listaRecursoPredominante) {
		this.listaRecursoPredominante = listaRecursoPredominante;
	}

	/**
	 * @return the listaRecursoPredominanteSeleccionado
	 */
	public List<CatalogoComunDTO> getListaRecursoPredominanteSeleccionado() {
		return listaRecursoPredominanteSeleccionado;
	}

	/**
	 * @param listaRecursoPredominanteSeleccionado
	 *            the listaRecursoPredominanteSeleccionado to set
	 */
	public void setListaRecursoPredominanteSeleccionado(List<CatalogoComunDTO> listaRecursoPredominanteSeleccionado) {
		this.listaRecursoPredominanteSeleccionado = listaRecursoPredominanteSeleccionado;
	}

	/**
	 * @return the relacionOtrosObjetosFoaDTO
	 */
	public RelRelacionOtrosObjetosFoaDTO getRelacionOtrosObjetosFoaDTO() {
		return relacionOtrosObjetosFoaDTO;
	}

	/**
	 * @param relacionOtrosObjetosFoaDTO
	 *            the relacionOtrosObjetosFoaDTO to set
	 */
	public void setRelacionOtrosObjetosFoaDTO(RelRelacionOtrosObjetosFoaDTO relacionOtrosObjetosFoaDTO) {
		this.relacionOtrosObjetosFoaDTO = relacionOtrosObjetosFoaDTO;
	}

	/**
	 * @return the listaRelacionOtrosObjetosSeleccionado
	 */
	public List<CatalogoComunDTO> getListaRelacionOtrosObjetosSeleccionado() {
		return listaRelacionOtrosObjetosSeleccionado;
	}

	/**
	 * @param listaRelacionOtrosObjetosSeleccionado
	 *            the listaRelacionOtrosObjetosSeleccionado to set
	 */
	public void setListaRelacionOtrosObjetosSeleccionado(List<CatalogoComunDTO> listaRelacionOtrosObjetosSeleccionado) {
		this.listaRelacionOtrosObjetosSeleccionado = listaRelacionOtrosObjetosSeleccionado;
	}

	/**
	 * @return the foaEtapaDTO
	 */
	public FichaDescriptivaOaDTO getFoaEtapaDTO() {
		return foaEtapaDTO;
	}

	/**
	 * @param foaEtapaDTO
	 *            the foaEtapaDTO to set
	 */
	public void setFoaEtapaDTO(FichaDescriptivaOaDTO foaEtapaDTO) {
		this.foaEtapaDTO = foaEtapaDTO;
	}

	/**
	 * @return the unidadOaAvaDTO
	 */
	public UnidadOaAvaDTO getUnidadOaAvaDTO() {
		return unidadOaAvaDTO;
	}

	/**
	 * @param unidadOaAvaDTO
	 *            the unidadOaAvaDTO to set
	 */
	public void setUnidadOaAvaDTO(UnidadOaAvaDTO unidadOaAvaDTO) {
		this.unidadOaAvaDTO = unidadOaAvaDTO;
	}

	/**
	 * @return the tipoCompetencia
	 */
	public String getTipoCompetencia() {
		return tipoCompetencia;
	}

	/**
	 * @param tipoCompetencia
	 *            the tipoCompetencia to set
	 */
	public void setTipoCompetencia(String tipoCompetencia) {
		this.tipoCompetencia = tipoCompetencia;
	}

	/**
	 * @return the ejeCapacitacion
	 */
	public String getEjeCapacitacion() {
		return ejeCapacitacion;
	}

	/**
	 * @param ejeCapacitacion
	 *            the ejeCapacitacion to set
	 */
	public void setEjeCapacitacion(String ejeCapacitacion) {
		this.ejeCapacitacion = ejeCapacitacion;
	}

	/**
	 * @return the responsables
	 */
	public String getResponsables() {
		return responsables;
	}

	/**
	 * @param responsables
	 *            the responsables to set
	 */
	public void setResponsables(String responsables) {
		this.responsables = responsables;
	}

	/**
	 * @return the listaNivelGranularidadSeleccionado
	 */
	public List<CatalogoComunDTO> getListaNivelGranularidadSeleccionado() {
		return listaNivelGranularidadSeleccionado;
	}

	/**
	 * @param listaNivelGranularidadSeleccionado
	 *            the listaNivelGranularidadSeleccionado to set
	 */
	public void setListaNivelGranularidadSeleccionado(List<CatalogoComunDTO> listaNivelGranularidadSeleccionado) {
		this.listaNivelGranularidadSeleccionado = listaNivelGranularidadSeleccionado;
	}

	/**
	 * @return the listaComposicionObjetoSeleccionado
	 */
	public List<CatalogoComunDTO> getListaComposicionObjetoSeleccionado() {
		return listaComposicionObjetoSeleccionado;
	}

	/**
	 * @param listaComposicionObjetoSeleccionado
	 *            the listaComposicionObjetoSeleccionado to set
	 */
	public void setListaComposicionObjetoSeleccionado(List<CatalogoComunDTO> listaComposicionObjetoSeleccionado) {
		this.listaComposicionObjetoSeleccionado = listaComposicionObjetoSeleccionado;
	}

	/**
	 * @return the indicePanel
	 */
	public Integer getIndicePanel() {
		return indicePanel;
	}

	/**
	 * @param indicePanel
	 *            the indicePanel to set
	 */
	public void setIndicePanel(Integer indicePanel) {
		this.indicePanel = indicePanel;
	}

	/**
	 * @return the mensajes
	 */
	public List<String> getMensajes() {
		return mensajes;
	}

	/**
	 * @param mensajes
	 *            the mensajes to set
	 */
	public void setMensajes(List<String> mensajes) {
		this.mensajes = mensajes;
	}

	/**
	 * @return the estatusBDFF
	 */
	public Boolean getEstatusBDFF() {
		return estatusBDFF;
	}

	/**
	 * @param estatusBDFF
	 *            the estatusBDFF to set
	 */
	public void setEstatusBDFF(Boolean estatusBDFF) {
		this.estatusBDFF = estatusBDFF;
	}

	/**
	 * @return the root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	/**
	 * @return the botonFinalizar
	 */
	public Boolean getBotonFinalizar() {
		return botonFinalizar;
	}

	/**
	 * @param botonFinalizar
	 *            the botonFinalizar to set
	 */
	public void setBotonFinalizar(Boolean botonFinalizar) {
		this.botonFinalizar = botonFinalizar;
	}

	/**
	 * @return the cargaArchivosOaService
	 */
	public CargaArchivosOaService getCargaArchivosOaService() {
		return cargaArchivosOaService;
	}

	/**
	 * @param cargaArchivosOaService
	 *            the cargaArchivosOaService to set
	 */
	public void setCargaArchivosOaService(CargaArchivosOaService cargaArchivosOaService) {
		this.cargaArchivosOaService = cargaArchivosOaService;
	}

	/**
	 * @return the cargaArchivosOaDTO
	 */
	public CargaArchivosOaDTO getCargaArchivosOaDTO() {
		return cargaArchivosOaDTO;
	}

	/**
	 * @param cargaArchivosOaDTO
	 *            the cargaArchivosOaDTO to set
	 */
	public void setCargaArchivosOaDTO(CargaArchivosOaDTO cargaArchivosOaDTO) {
		this.cargaArchivosOaDTO = cargaArchivosOaDTO;
	}

	/**
	 * @return the listaArchivosOA
	 */
	public List<CargaArchivosOaDTO> getListaArchivosOA() {
		return listaArchivosOA;
	}

	/**
	 * @param listaArchivosOA
	 *            the listaArchivosOA to set
	 */
	public void setListaArchivosOA(List<CargaArchivosOaDTO> listaArchivosOA) {
		this.listaArchivosOA = listaArchivosOA;
	}

	/**
	 * @return the nombreOA
	 */
	public String getnombreOA() {
		return nombreOA;
	}

	/**
	 * @param nombreOA
	 *            the nombreOA to set
	 */
	public void setnombreOA(String nombreOA) {
		this.nombreOA = nombreOA;
	}

	/**
	 * @return the fileDownload
	 */
	public StreamedContent getFileDownload() {
		return fileDownload;
	}

	/**
	 * @param fileDownload
	 *            the fileDownload to set
	 */
	public void setFileDownload(StreamedContent fileDownload) {
		this.fileDownload = fileDownload;
	}

	public ParametroSistemaService getParametrosSistemaService() {
		return parametrosSistemaService;
	}

	public void setParametrosSistemaService(ParametroSistemaService parametrosSistemaService) {
		this.parametrosSistemaService = parametrosSistemaService;
	}

	public StreamedContent getReportePDF() {
		return reportePDF;
	}

	public void setReportePDF(StreamedContent reportePDF) {
		this.reportePDF = reportePDF;
	}

	public AmbienteVirtualAprendizajeDTO getAvaSeleccionado() {
		return avaSeleccionado;
	}

	public void setAvaSeleccionado(AmbienteVirtualAprendizajeDTO avaSeleccionado) {
		this.avaSeleccionado = avaSeleccionado;
	}

	public List<UnidadOaAvaDTO> getListaUnidades() {
		return listaUnidades;
	}

	public void setListaUnidades(List<UnidadOaAvaDTO> listaUnidades) {
		this.listaUnidades = listaUnidades;
	}

	public RelUnidadOaAvaService getRelUnidadOaAvaService() {
		return relUnidadOaAvaService;
	}

	public void setRelUnidadOaAvaService(RelUnidadOaAvaService relUnidadOaAvaService) {
		this.relUnidadOaAvaService = relUnidadOaAvaService;
	}

	public Integer getIdUnidadDidactica() {
		return idUnidadDidactica;
	}

	public void setIdUnidadDidactica(Integer idUnidadDidactica) {
		this.idUnidadDidactica = idUnidadDidactica;
	}

	public SistemaBean getTextosSistema() {
		return textosSistema;
	}

	public void setTextosSistema(SistemaBean textosSistema) {
		this.textosSistema = textosSistema;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
