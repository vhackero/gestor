package mx.gob.sedesol.gestorweb.beans.logisticainfraestructura;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.AreaSedeDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ConfiguracionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.CritBusquedaAreasConfigDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RecursosInfraestructuraDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaDistribucionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaRecursoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SedeDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatDistribucionArea;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatEstatusArea;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatUbicacionTerritorial;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura.LogisticaInfraServiceFacade;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.RecursosInfraestructuraService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.TipoRecursoEnum;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ManagedBean
@SessionScoped
public class AreasBean extends BaseBean {

	private static final long serialVersionUID = 1563661430221561210L;

	private static final Logger logger = Logger.getLogger(AreasBean.class);

	@ManagedProperty("#{sistema}")
	private SistemaBean textosSistema;

	@ManagedProperty("#{parametroSistemaService}")
	private ParametroSistemaService parametroSistemaService;

	@ManagedProperty("#{recursosInfraestructuraService}")
	private RecursosInfraestructuraService recursosInfraestructuraService;

	@ManagedProperty("#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty("#{orgGubernamentalService}")
	private OrgGubernamentalService orgGubernamentalService;

	private List<StreamedContent> imagenes;
	private File[] listaImagenes;
	private List<File> listaImagenesEliminar;
	private List<File> arrayImagenes;
	private File imagenElim;
	private List<File> eImagenes;
	private InputStream input;

	private List<RecursosInfraestructuraDTO> recursosTec;
	private RecursosInfraestructuraDTO recursosTecSelec;
	private List<RelAreaRecursoDTO> tblTec;
	private RelAreaRecursoDTO tecGuardar;
	private List<RelAreaRecursoDTO> recTecElim;

	private List<RecursosInfraestructuraDTO> recursosMobi;
	private RecursosInfraestructuraDTO recursosMobiSelec;
	private List<RelAreaRecursoDTO> tblMobi;
	private RelAreaRecursoDTO mobiGuardar;
	private List<RelAreaRecursoDTO> recMobiElim;

	private List<CatalogoComunDTO> distribuciones;
	private List<String> distribucionesSelec;
	private List<CatalogoComunDTO> estatusArea;
	private CatalogoComunDTO estatusAreaSelec;

	private CritBusquedaAreasConfigDTO criteriosBusq;

	@ManagedProperty(value = "#{logisticaInfraServiceFacade}")
	private LogisticaInfraServiceFacade logisticaInfraServiceFacade;

	private transient TreeNode arbolOrgGubernamental;
	private transient TreeNode nodoOrgGubernamentalSel;
	private List<CatalogoComunDTO> catUbicacionTerr;
	private List<SedeDTO> catSedesInfra;
	private List<ConfiguracionAreaDTO> areasConfigBusq;
	private ConfiguracionAreaDTO areaSeleccionada;
	private boolean verConfigArea;
	private boolean editConfigArea;

	private List<AreaSedeDTO> areasXSede;
	private boolean nuevaConfigArea;

	private String styleDist;
	private String styleDistLabel;

	private boolean fromPersonalizacion;
	private boolean verComboSede;

	private String rutaPrincipal;
	private String rutaAreas;
	private String nombreFotoAreaComun;

	public static final Integer ID_ESTATUS_AREA_ACTIVA = 1;

	public AreasBean() {
		styleDist = "blanco";
		verComboSede = false;
		styleDistLabel = "Seleccionar distribuciones";
		this.inicializaObjs();
	}

	private void inicializaObjs() {

		criteriosBusq = new CritBusquedaAreasConfigDTO();
		criteriosBusq.setIdAreaSede(0);
		criteriosBusq.setIdOrgGubernamental(0);
		criteriosBusq.setIdSede(0);
		criteriosBusq.setIdUbicacion(0);
		criteriosBusq.setOrgGubNombre("");
		imagenes = new ArrayList<>();
		arrayImagenes = new ArrayList<>();
		listaImagenesEliminar = new ArrayList<>();
		imagenElim = new File("");
		eImagenes = new ArrayList<>();
		areaSeleccionada = new ConfiguracionAreaDTO();
		recursosTecSelec = null;
		recursosMobiSelec = null;
		tblTec = new ArrayList<>();
		tecGuardar = new RelAreaRecursoDTO();
		recTecElim = new ArrayList<>();
		tblMobi = new ArrayList<>();
		mobiGuardar = new RelAreaRecursoDTO();
		recMobiElim = new ArrayList<>();
		estatusAreaSelec = new CatalogoComunDTO();
		this.areaSeleccionada.setCatEstatusArea(new CatalogoComunDTO());
		this.areaSeleccionada.getCatEstatusArea().setId(ID_ESTATUS_AREA_ACTIVA);
		setDistribucionesSelec(new ArrayList<>());

	}

	@PostConstruct
	public void init() {

		catUbicacionTerr = logisticaInfraServiceFacade.getCatUbicacionTerrService()
				.findAll(CatUbicacionTerritorial.class);
		this.generaArbolOrgGubernamental();
		recursosTec = getRecursosInfraestructuraService()
				.obtieneRecursosPorTipoDeRecurso(TipoRecursoEnum.TECNOLOGICOS.getId());
		recursosMobi = getRecursosInfraestructuraService()
				.obtieneRecursosPorTipoDeRecurso(TipoRecursoEnum.MOBILIARIOS.getId());
		distribuciones = logisticaInfraServiceFacade.getCatDistribucionAreaService().findAll(CatDistribucionArea.class);
		estatusArea = logisticaInfraServiceFacade.getCatEstatusAreaService().findAll(CatEstatusArea.class);
		styleDistChange();
		rutaPrincipal = parametroSistemaService.obtenerParametro(ConstantesGestor.PARAMETRO_RUTA_PRINCIPAL);
		rutaAreas = parametroSistemaService.obtenerParametro(ConstantesGestor.RUTA_AREAS);
		nombreFotoAreaComun = parametroSistemaService.obtenerParametro(ConstantesGestor.NOMBRE_FOTO_AREA_COMUN);
	}

	public void actualizaFormulario() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formAreaSeleccionada");
	}

	/**
	 * persistencia de configuracion de area
	 */
	public String guardaAreaConfig() {

		if (validarNuevaConfAreas()) {

			List<RelAreaRecursoDTO> recursosEliminar = new ArrayList<>();
			recursosEliminar.addAll(recMobiElim);
			recursosEliminar.addAll(recTecElim);

			CatalogoComunDTO ea = logisticaInfraServiceFacade.getCatEstatusAreaService()
					.buscarPorId(areaSeleccionada.getCatEstatusArea().getId(), CatEstatusArea.class);

			areaSeleccionada.setCatEstatusArea(ea);
			areaSeleccionada.setFechaRegistro(new Date());
			areaSeleccionada.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			List<RelAreaRecursoDTO> recursosGuardar = new ArrayList<>();
			recursosGuardar.addAll(tblTec);
			recursosGuardar.addAll(tblMobi);

			ResultadoDTO<ConfiguracionAreaDTO> resTx = logisticaInfraServiceFacade.guardaConfiguracionArea(
					areaSeleccionada, recursosEliminar, isNuevaConfigArea(), distribucionesSelec, recursosGuardar);

			if (ObjectUtils.isNotNull(resTx) && resTx.getResultado().getValor()) {
				try {
					eliminarImagenesDeServidor();
				} catch (FileNotFoundException e) {
					logger.info("Fallo la eliminacion de imagenes. ");
				}

				this.areaSeleccionada = resTx.getDto();
				limpiarPantalla();
				agregarFlashMessage("Se guardaron los datos correctamente.", null, FacesMessage.SEVERITY_INFO);
				generaArbolOrgGubernamental();
				return ConstantesGestorWeb.NAVEGA_BUSQ_AREAS_LOGISTICA_INFRA;
			} else {
				agregarMsgError("Ocurrio un error en el guardado de los datos", null);
				return null;
			}
		} else {
			return null;
		}

	}

	/**
	 *
	 * @return
	 */
	public String navegaConsultaAreasConfig() {
		return ConstantesGestorWeb.NAVEGA_BUSQ_AREAS_LOGISTICA_INFRA;
	}

	/**
	 * Verifica si las distribuciones estan seleccionadas. Si hay al menos una
	 * seleccionada, se pintara de verde. Si no, de blanco. Los mensajes cambian
	 * con base en la seleccion.
	 */
	public void styleDistChange() {
		if (!distribucionesSelec.isEmpty()) {
			styleDist = "verde";
			styleDistLabel = "Distribuciones seleccionadas";
		} else {
			styleDist = "blanco";
			styleDistLabel = "Seleccionar distribuciones";
		}
	}

	public void limpiarPantalla() {
		criteriosBusq = new CritBusquedaAreasConfigDTO();
		// criteriosBusq.setIdArea(0);
		criteriosBusq.setIdUbicacion(0);
		criteriosBusq.setIdSede(0);
		criteriosBusq.setIdAreaSede(0);
		criteriosBusq.setIdOrgGubernamental(0);
		criteriosBusq.setOrgGubNombre("");
		nodoOrgGubernamentalSel = null;
		generaArbolOrgGubernamental();
		areasConfigBusq = new ArrayList<>();
	}

	/**
	 *
	 */
	public void agregarTecTbl() {
		if (!ObjectUtils.isNullOrEmpty(recursosTecSelec.getNombre())
				&& !ObjectUtils.isNullOrCero(tecGuardar.getCantidad())) {
			tecGuardar.setUsuarioRegistro(getUsuarioEnSession().getIdPersona());
			tecGuardar.setIdRecurso(recursosTecSelec.getIdRecurso());
			tecGuardar.setCatRecursosInfraestructura(recursosTecSelec);
			tecGuardar.setIdAreaConfig(areaSeleccionada.getIdConfigArea());

			if (!this.containsRecurso(tecGuardar, tblTec)) {
				tblTec.add(tecGuardar);
				// recursosTecSelec = new RecursosInfraestructuraDTO();
				recursosTecSelec = null;
			} else {
				agregarMsgInfo("El recurso ya se encuentra en la lista", null);
			}
			tecGuardar = new RelAreaRecursoDTO();
		}
	}

	/**
	 *
	 */
	public void agregarMobiTbl() {
		if (!ObjectUtils.isNullOrEmpty(recursosMobiSelec.getNombre())
				&& !ObjectUtils.isNullOrCero(mobiGuardar.getCantidad())) {
			mobiGuardar.setUsuarioRegistro(getUsuarioEnSession().getIdPersona());
			mobiGuardar.setIdRecurso(recursosMobiSelec.getIdRecurso());
			mobiGuardar.setCatRecursosInfraestructura(recursosMobiSelec);
			mobiGuardar.setIdAreaConfig(areaSeleccionada.getIdConfigArea());

			if (!this.containsRecurso(mobiGuardar, tblMobi)) {
				tblMobi.add(mobiGuardar);
				// recursosMobiSelec = new RecursosInfraestructuraDTO();
				recursosMobiSelec = null;
			} else {
				agregarMsgInfo("El recurso ya se encuentra en la lista", null);
			}

			mobiGuardar = new RelAreaRecursoDTO();
		}
	}

	/**
	 *
	 * @param recAgregar
	 * @param lista
	 * @return
	 */
	private boolean containsRecurso(RelAreaRecursoDTO recAgregar, List<RelAreaRecursoDTO> lista) {
		if (ObjectUtils.isNotNull(recAgregar)) {
			for (RelAreaRecursoDTO rec : lista) {
				if (rec.getIdRecurso().equals(recAgregar.getIdRecurso())) {
					return true;
				}
			}
		}
		return false;
	}

	public void eliminarRecursoTec() {
		tblTec.remove(tecGuardar);
		recTecElim.add(tecGuardar);
		tecGuardar = new RelAreaRecursoDTO();
	}

	public void eliminarRecursoMobi() {
		tblMobi.remove(mobiGuardar);
		recMobiElim.add(mobiGuardar);
		mobiGuardar = new RelAreaRecursoDTO();
	}

	public void cargaImagen(FileUploadEvent img) {
		String path = getParametroSistemaService().obtenerParametroConRutaCompleta(ConstantesGestor.RUTA_AREAS)
				.concat(areaSeleccionada.getCatAreasSede().getCatSede().getIdSede().toString()).concat("_")
				.concat(areaSeleccionada.getCatAreasSede().getCatArea().getId().toString()).concat("/");

		eImagenes.add(new File(path.concat(img.getFile().getFileName())));

		if (ObjectUtils.isNullOrEmpty(areaSeleccionada.getRutaImgUno())) {
			areaSeleccionada.setRutaImgUno(path.concat(img.getFile().getFileName()));
		} else if (ObjectUtils.isNullOrEmpty(areaSeleccionada.getRutaImgDos())) {
			areaSeleccionada.setRutaImgDos(path.concat(img.getFile().getFileName()));
		} else if (ObjectUtils.isNullOrEmpty(areaSeleccionada.getRutaImgTres())) {
			areaSeleccionada.setRutaImgTres(path.concat(img.getFile().getFileName()));
		} else {
			carousel();
			return;
		}

		try {
			File archivoRuta = new File(path);
			if (!archivoRuta.exists()) {
				archivoRuta.mkdirs();
			}

			BufferedImage image = ImageIO.read(img.getFile().getInputstream());
			ImageIO.write(image, "jpg", new File(path.concat(img.getFile().getFileName())));
			carousel();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Elimina imagen pero solo a nivel visual, no guarda el cambio en bbdd.
	 * 
	 * @throws FileNotFoundException
	 */

	public void eliminarImagen() throws FileNotFoundException {
		if (imagenElim.exists()) {
			if (areaSeleccionada.getRutaImgUno().equals(imagenElim.getAbsolutePath())) {
				areaSeleccionada.setRutaImgUno(null);
				listaImagenesEliminar.add(imagenElim);
			} else if (areaSeleccionada.getRutaImgDos().equals(imagenElim.getAbsolutePath())) {
				areaSeleccionada.setRutaImgDos(null);
				listaImagenesEliminar.add(imagenElim);
			} else if (areaSeleccionada.getRutaImgTres().equals(imagenElim.getAbsolutePath())) {
				areaSeleccionada.setRutaImgTres(null);
				listaImagenesEliminar.add(imagenElim);
			}
			logger.info("La imagen con nombre: " + imagenElim.getName() + " se ha eliminado.");
			arrayImagenes.remove(imagenElim);
			carousel();
		} else {
			logger.error("Falló la eliminación de archivo.");
		}
	}

	/**
	 * Elimina las imagenes del servidor
	 * 
	 * @throws FileNotFoundException
	 */
	public void eliminarImagenesDeServidor() throws FileNotFoundException {
		if (!listaImagenesEliminar.isEmpty()) {
			for (File imagen : listaImagenesEliminar) {
				if (imagen.delete()) {
					logger.error("La imagen " + imagen.getName() + " se elimino.");
				} else {
					logger.error("Falló la eliminación de archivo." + imagen.getName());
				}
			}
			listaImagenesEliminar = new ArrayList<>();
		}

	}

	public String cancelaImagenes() {

		if (ObjectUtils.isNotNull(eImagenes)) {
			if (!eImagenes.isEmpty()) {
				for (File fs : eImagenes) {
					if (fs.delete()) {
						logger.info(fs.getName() + " eliminado");
					} else {
						logger.info(fs.getName() + " no eliminado");
					}
				}
			}
		}

		eImagenes = new ArrayList<>();

		areaSeleccionada = new ConfiguracionAreaDTO();
		setNuevaConfigArea(Boolean.FALSE);
		setEditConfigArea(Boolean.FALSE);
		setVerConfigArea(Boolean.FALSE);
		generaArbolOrgGubernamental();
		return ConstantesGestorWeb.NAVEGA_BUSQ_AREAS_LOGISTICA_INFRA;
	}

	public void generaArbolOrgGubernamental() {
		List<OrgGubernamentalDTO> orgGubs = orgGubernamentalService.obtenerOrgGubsPadres();
		arbolOrgGubernamental = new DefaultTreeNode();
		this.generaHijosOrgGubAreas(arbolOrgGubernamental, orgGubs);
	}

	public void onChangeBusqUbicacion(ValueChangeEvent e) {

		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idUbicacion = (Integer) e.getNewValue();
			criteriosBusq.setIdUbicacion(idUbicacion);
			catSedesInfra = logisticaInfraServiceFacade.getSedeService().consultaSedesPorUbicacionOrgGub(
					criteriosBusq.getIdUbicacion(), criteriosBusq.getIdOrgGubernamental());
			if (idUbicacion.equals(0)) {
				criteriosBusq.setIdSede(0);
				criteriosBusq.setIdAreaSede(0);
			}
		}
	}

	/**
	 *
	 * @param e
	 */
	public void onChangeSedeBusqueda(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSede = (Integer) e.getNewValue();
			criteriosBusq.setIdSede(idSede);
			areasXSede = validaUnaConfPorArea(
					logisticaInfraServiceFacade.getAreaSedeService().consultaAreasSedePorIdSede(idSede));

			if (idSede.equals(0)) {
				criteriosBusq.setIdAreaSede(0);
			}
		} else {
			logger.info("Valor nulo");
		}
	}

	/***
	 * Regresa las listas que no tienen configuracion de area
	 * 
	 * @param e
	 */
	public List<AreaSedeDTO> validaUnaConfPorArea(List<AreaSedeDTO> listaAreaSede) {
		List<AreaSedeDTO> nuevaListaAreaSede = new ArrayList<>();
		if (!listaAreaSede.isEmpty()) {
			List<ConfiguracionAreaDTO> listaConfiguracionAreas = logisticaInfraServiceFacade
					.getConfiguracionAreaService().findAll();

			for (AreaSedeDTO areaSede : listaAreaSede) {
				boolean areaYaEnConfiguracion = false;
				for (ConfiguracionAreaDTO confArea : listaConfiguracionAreas) {
					if (confArea.getCatAreasSede().getIdAreaSede().equals(areaSede.getIdAreaSede())) {
						areaYaEnConfiguracion = true;
					}
				}
				if (areaYaEnConfiguracion == false) {
					nuevaListaAreaSede.add(areaSede);
				}

			}

		}
		return nuevaListaAreaSede;

	}

	/**
	 *
	 * @param e
	 */
	public void onChangeAreaSede(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idAreaSede = (Integer) e.getNewValue();
			if (!idAreaSede.equals(0)) {
				areaSeleccionada
						.setCatAreasSede(logisticaInfraServiceFacade.getAreaSedeService().buscarPorId(idAreaSede));
			}

		}
	}

	/**
	 *
	 */
	public void buscarAreasConfiguradas() {

		if (validarBusquedaConfAreas()) {
			areasConfigBusq = logisticaInfraServiceFacade.getConfiguracionAreaService()
					.busquedaAreasConfiguradasCriterios(criteriosBusq);
		}

	}

	private boolean validarBusquedaConfAreas() {
		boolean validacionCorrecta = true;
		if (ObjectUtils.isNotNull(criteriosBusq.getIdUbicacion()) && criteriosBusq.getIdUbicacion().equals(0)) {
			FacesContext.getCurrentInstance().addMessage("formBusConf:selUbicacion", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, sistema.obtenerTexto("gw.global.msg.dato.req"), null));
			validacionCorrecta = false;
		}
		if (ObjectUtils.isNotNull(criteriosBusq.getIdSede()) && criteriosBusq.getIdSede().equals(0)) {
			FacesContext.getCurrentInstance().addMessage("formBusConf:cmbSede", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, sistema.obtenerTexto("gw.global.msg.dato.req"), null));
			validacionCorrecta = false;
		}
		if (ObjectUtils.isNull(nodoOrgGubernamentalSel)) {
			FacesContext.getCurrentInstance().addMessage("formBusConf:treeTbl", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, sistema.obtenerTexto("gw.global.msg.dato.req"), null));
			validacionCorrecta = false;
		}
		return validacionCorrecta;
	}

	private boolean validarNuevaConfAreas() {
		boolean validacionCorrecta = true;
		if (ObjectUtils.isNull(nodoOrgGubernamentalSel)) {
			FacesContext.getCurrentInstance().addMessage("formAreaSeleccionada:treeTbl", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, sistema.obtenerTexto("gw.global.msg.dato.req"), null));
			validacionCorrecta = false;
		}
		if (ObjectUtils.isNotNull(criteriosBusq.getIdUbicacion()) && criteriosBusq.getIdUbicacion().equals(0)) {
			FacesContext.getCurrentInstance().addMessage("formAreaSeleccionada:selUbicacion", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, sistema.obtenerTexto("gw.global.msg.dato.req"), null));
			validacionCorrecta = false;
		}
		if (ObjectUtils.isNotNull(criteriosBusq.getIdSede()) && criteriosBusq.getIdSede().equals(0)) {
			FacesContext.getCurrentInstance().addMessage("formAreaSeleccionada:cmbSede", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, sistema.obtenerTexto("gw.global.msg.dato.req"), null));
			validacionCorrecta = false;
		}
		if (ObjectUtils.isNotNull(criteriosBusq.getIdAreaSede()) && criteriosBusq.getIdAreaSede().equals(0)) {
			FacesContext.getCurrentInstance().addMessage("formAreaSeleccionada:cmbArea", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, sistema.obtenerTexto("gw.global.msg.dato.req"), null));
			validacionCorrecta = false;
		}
		if (ObjectUtils.isNull(areaSeleccionada.getRutaImgUno()) && ObjectUtils.isNull(areaSeleccionada.getRutaImgDos())
				&& ObjectUtils.isNull(areaSeleccionada.getRutaImgTres())) {
			FacesContext.getCurrentInstance().addMessage("formAreaSeleccionada:btnExaminar", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, sistema.obtenerTexto("gw.global.msg.dato.req"), null));
			validacionCorrecta = false;
		}

		return validacionCorrecta;
	}

	/**
	 *
	 * @return
	 */
	public String navegaConfiguracionArea() {
		if (ObjectUtils.isNotNull(areaSeleccionada)) {

			criteriosBusq.setIdAreaSede(null);
			setNuevaConfigArea(Boolean.FALSE);
			setEditConfigArea(Boolean.TRUE);
			setVerConfigArea(Boolean.FALSE);
			tblTec = new ArrayList<>();
			tblMobi = new ArrayList<>();
			distribucionesSelec = new ArrayList<>();
			recursosTecSelec = null;
			recursosMobiSelec = null;
			listaImagenesEliminar = new ArrayList<>();
			eImagenes = new ArrayList<>();

			for (RelAreaRecursoDTO r : areaSeleccionada.getRelAreaRecursos()) {
				if (r.getCatRecursosInfraestructura().getCatTipoRecurso().getId()
						.equals(TipoRecursoEnum.TECNOLOGICOS.getId())) {
					tblTec.add(r);
				} else if (r.getCatRecursosInfraestructura().getCatTipoRecurso().getId()
						.equals(TipoRecursoEnum.MOBILIARIOS.getId())) {
					tblMobi.add(r);
				}
			}

			for (RelAreaDistribucionDTO d : areaSeleccionada.getRelAreaDistribucion()) {
				distribucionesSelec.add(d.getCatDistribucionArea().getId().toString());
			}

			styleDistChange();

			carousel();
			generaArbolOrgGubernamental();
			return ConstantesGestorWeb.NAVEGA_CONF_AREAS_LOGISTICA_INFRA;
		}

		return null;

	}

	/**
	 *
	 */
	public String navegaVisualizacionArea() {
		if (ObjectUtils.isNotNull(areaSeleccionada)) {

			setNuevaConfigArea(Boolean.FALSE);
			setEditConfigArea(Boolean.FALSE);
			setVerConfigArea(Boolean.TRUE);
			tblTec = new ArrayList<>();
			tblMobi = new ArrayList<>();
			recursosTecSelec = null;
			recursosMobiSelec = null;
			distribucionesSelec = new ArrayList<>();
			listaImagenesEliminar = new ArrayList<>();
			eImagenes = new ArrayList<>();

			for (RelAreaRecursoDTO r : areaSeleccionada.getRelAreaRecursos()) {
				if (r.getCatRecursosInfraestructura().getCatTipoRecurso().getId()
						.equals(TipoRecursoEnum.TECNOLOGICOS.getId())) {
					tblTec.add(r);
				} else if (r.getCatRecursosInfraestructura().getCatTipoRecurso().getId()
						.equals(TipoRecursoEnum.MOBILIARIOS.getId())) {
					tblMobi.add(r);
				}
			}

			for (RelAreaDistribucionDTO d : areaSeleccionada.getRelAreaDistribucion()) {
				distribucionesSelec.add(d.getCatDistribucionArea().getId().toString());
			}
			styleDistChange();
			carousel();

			return ConstantesGestorWeb.NAVEGA_CONF_AREAS_LOGISTICA_INFRA;
		}

		return null;
	}

	/**
	 * Metodo que regresa las distribuciones concatenadas separadas por comas y
	 * con un punto al final
	 */
	public String obtenerDistribuciones() {

		if (ObjectUtils.isNotNull(areaSeleccionada)) {
			if (ObjectUtils.isNotNull(areaSeleccionada.getRelAreaDistribucion())) {
				if (!areaSeleccionada.getRelAreaDistribucion().isEmpty()) {
					String distribuciones = "";
					for (RelAreaDistribucionDTO d : areaSeleccionada.getRelAreaDistribucion()) {
						distribuciones += d.getCatDistribucionArea().getNombre() + ", ";

					}
					int tamanio = distribuciones.length();
					String cadenaSinComa = distribuciones.substring(0, tamanio - 2);
					return cadenaSinComa += ".";

				}
			}
		}
		return "";
	}

	/**
	 *
	 * @param event
	 */
	public void onNodeSelectDependencia(NodeSelectEvent event) {
		NodoDTO n = (NodoDTO) event.getTreeNode().getData();
		criteriosBusq.setIdOrgGubernamental(n.getId());
		criteriosBusq.setOrgGubNombre(n.getNombre());
		// actualiza output de dependencia

		criteriosBusq.setIdSede(0);
		criteriosBusq.setIdUbicacion(0);
		criteriosBusq.setIdAreaSede(0);

	}

	public void carousel() {

		imagenes = new ArrayList<>();
		arrayImagenes = new ArrayList<>();
		if (!ObjectUtils.isNullOrEmpty(areaSeleccionada.getRutaImgUno())) {
			arrayImagenes.add(new File(areaSeleccionada.getRutaImgUno()));
		}
		if (!ObjectUtils.isNullOrEmpty(areaSeleccionada.getRutaImgDos())) {
			arrayImagenes.add(new File(areaSeleccionada.getRutaImgDos()));
		}
		if (!ObjectUtils.isNullOrEmpty(areaSeleccionada.getRutaImgTres())) {
			arrayImagenes.add(new File(areaSeleccionada.getRutaImgTres()));
		}
		try {
			for (File f : arrayImagenes) {
				if (f.exists()) {
					input = new FileInputStream(f);
					StreamedContent sc = new DefaultStreamedContent(input,
							getFacesContext().getExternalContext().getMimeType(f.getName()), f.getName());
					imagenes.add(sc);
				} else {
					input = new FileInputStream(rutaPrincipal + rutaAreas + nombreFotoAreaComun);
					StreamedContent sc = new DefaultStreamedContent(input,
							getFacesContext().getExternalContext().getMimeType(f.getName()), f.getName());
					imagenes.add(sc);
				}

			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}

	}

	public void cancelarPersonalizacionArea() {
		logger.info("Cancelar Personalizacion");
	}

	public void guardaPersonalizacionArea() {
		logger.info("Guardar Personalizacion");
	}

	/**
	 *
	 * @return
	 */
	public String crearConfiguracionArea() {
		this.inicializaObjs();
		this.init();

		setNuevaConfigArea(Boolean.TRUE);
		setEditConfigArea(Boolean.FALSE);
		setVerConfigArea(Boolean.FALSE);
		generaArbolOrgGubernamental();
		return ConstantesGestorWeb.NAVEGA_CONF_AREAS_LOGISTICA_INFRA;
	}

	public File[] getListaImagenes() {
		return listaImagenes;
	}

	public List<StreamedContent> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<StreamedContent> imagenes) {
		this.imagenes = imagenes;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public List<File> getArrayImagenes() {
		return arrayImagenes;
	}

	public void setArrayImagenes(List<File> arrayImagenes) {
		this.arrayImagenes = arrayImagenes;
	}

	public File getImagenElim() {
		return imagenElim;
	}

	public void setImagenElim(File imagenElim) {
		this.imagenElim = imagenElim;
	}

	public void setListaImagenes(File[] listaImagenes) {
		this.listaImagenes = listaImagenes;
	}

	/**
	 * @return the criteriosBusq
	 */
	public CritBusquedaAreasConfigDTO getCriteriosBusq() {
		return criteriosBusq;
	}

	/**
	 * @param criteriosBusq
	 *            the criteriosBusq to set
	 */
	public void setCriteriosBusq(CritBusquedaAreasConfigDTO criteriosBusq) {
		this.criteriosBusq = criteriosBusq;
	}

	/**
	 * @return the arbolOrgGubernamental
	 */
	public TreeNode getArbolOrgGubernamental() {
		return arbolOrgGubernamental;
	}

	/**
	 * @param arbolOrgGubernamental
	 *            the arbolOrgGubernamental to set
	 */
	public void setArbolOrgGubernamental(TreeNode arbolOrgGubernamental) {
		this.arbolOrgGubernamental = arbolOrgGubernamental;
	}

	/**
	 * @return the nodoOrgGubernamentalSel
	 */
	public TreeNode getNodoOrgGubernamentalSel() {
		return nodoOrgGubernamentalSel;
	}

	/**
	 * @param nodoOrgGubernamentalSel
	 *            the nodoOrgGubernamentalSel to set
	 */
	public void setNodoOrgGubernamentalSel(TreeNode nodoOrgGubernamentalSel) {
		this.nodoOrgGubernamentalSel = nodoOrgGubernamentalSel;
	}

	/**
	 * @param catUbicacionTerr
	 *            the catUbicacionTerr to set
	 */
	public void setCatUbicacionTerr(List<CatalogoComunDTO> catUbicacionTerr) {
		this.catUbicacionTerr = catUbicacionTerr;
	}

	/**
	 * @return the catUbicacionTerr
	 */
	public List<CatalogoComunDTO> getCatUbicacionTerr() {
		return catUbicacionTerr;
	}

	/**
	 * @return the logisticaInfraServiceFacade
	 */
	public LogisticaInfraServiceFacade getLogisticaInfraServiceFacade() {
		return logisticaInfraServiceFacade;
	}

	/**
	 * @param logisticaInfraServiceFacade
	 *            the logisticaInfraServiceFacade to set
	 */
	public void setLogisticaInfraServiceFacade(LogisticaInfraServiceFacade logisticaInfraServiceFacade) {
		this.logisticaInfraServiceFacade = logisticaInfraServiceFacade;
	}

	/**
	 * @return the catSedesInfra
	 */
	public List<SedeDTO> getCatSedesInfra() {
		return catSedesInfra;
	}

	/**
	 * @param catSedesInfra
	 *            the catSedesInfra to set
	 */
	public void setCatSedesInfra(List<SedeDTO> catSedesInfra) {
		this.catSedesInfra = catSedesInfra;
	}

	/**
	 * @return the areasConfigBusq
	 */
	public List<ConfiguracionAreaDTO> getAreasConfigBusq() {
		return areasConfigBusq;
	}

	/**
	 * @param areasConfigBusq
	 *            the areasConfigBusq to set
	 */
	public void setAreasConfigBusq(List<ConfiguracionAreaDTO> areasConfigBusq) {
		this.areasConfigBusq = areasConfigBusq;
	}

	/**
	 * @return the areaSeleccionada
	 */
	public ConfiguracionAreaDTO getAreaSeleccionada() {
		return areaSeleccionada;
	}

	/**
	 * @param areaSeleccionada
	 *            the areaSeleccionada to set
	 */
	public void setAreaSeleccionada(ConfiguracionAreaDTO areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	/**
	 * @return the verConfigArea
	 */
	public boolean isVerConfigArea() {
		return verConfigArea;
	}

	/**
	 * @param verConfigArea
	 *            the verConfigArea to set
	 */
	public void setVerConfigArea(boolean verConfigArea) {
		this.verConfigArea = verConfigArea;
	}

	public RecursosInfraestructuraService getRecursosInfraestructuraService() {
		return recursosInfraestructuraService;
	}

	public void setRecursosInfraestructuraService(RecursosInfraestructuraService recursosInfraestructuraService) {
		this.recursosInfraestructuraService = recursosInfraestructuraService;
	}

	public List<RecursosInfraestructuraDTO> getRecursosTec() {
		return recursosTec;
	}

	public void setRecursosTec(List<RecursosInfraestructuraDTO> recursosTec) {
		this.recursosTec = recursosTec;
	}

	public RecursosInfraestructuraDTO getRecursosTecSelec() {
		return recursosTecSelec;
	}

	public void setRecursosTecSelec(RecursosInfraestructuraDTO recursosTecSelec) {
		this.recursosTecSelec = recursosTecSelec;
	}

	public List<RelAreaRecursoDTO> getTblTec() {
		return tblTec;
	}

	public void setTblTec(List<RelAreaRecursoDTO> tblTec) {
		this.tblTec = tblTec;
	}

	public RelAreaRecursoDTO getTecGuardar() {
		return tecGuardar;
	}

	public void setTecGuardar(RelAreaRecursoDTO tecGuardar) {
		this.tecGuardar = tecGuardar;
	}

	public List<RecursosInfraestructuraDTO> getRecursosMobi() {
		return recursosMobi;
	}

	public void setRecursosMobi(List<RecursosInfraestructuraDTO> recursosMobi) {
		this.recursosMobi = recursosMobi;
	}

	public RecursosInfraestructuraDTO getRecursosMobiSelec() {
		return recursosMobiSelec;
	}

	public void setRecursosMobiSelec(RecursosInfraestructuraDTO recursosMobiSelec) {
		this.recursosMobiSelec = recursosMobiSelec;
	}

	public List<RelAreaRecursoDTO> getTblMobi() {
		return tblMobi;
	}

	public void setTblMobi(List<RelAreaRecursoDTO> tblMobi) {
		this.tblMobi = tblMobi;
	}

	public RelAreaRecursoDTO getMobiGuardar() {
		return mobiGuardar;
	}

	public void setMobiGuardar(RelAreaRecursoDTO mobiGuardar) {
		this.mobiGuardar = mobiGuardar;
	}

	public List<CatalogoComunDTO> getDistribuciones() {
		return distribuciones;
	}

	public void setDistribuciones(List<CatalogoComunDTO> distribuciones) {
		this.distribuciones = distribuciones;
	}

	public List<CatalogoComunDTO> getEstatusArea() {
		return estatusArea;
	}

	public void setEstatusArea(List<CatalogoComunDTO> estatusArea) {
		this.estatusArea = estatusArea;
	}

	public CatalogoComunDTO getEstatusAreaSelec() {
		return estatusAreaSelec;
	}

	public void setEstatusAreaSelec(CatalogoComunDTO estatusAreaSelec) {
		this.estatusAreaSelec = estatusAreaSelec;
	}

	/**
	 * @return the fromPersonalizacion
	 */
	public boolean isFromPersonalizacion() {
		return fromPersonalizacion;
	}

	public List<File> geteImagenes() {
		return eImagenes;
	}

	public void seteImagenes(List<File> eImagenes) {
		this.eImagenes = eImagenes;
	}

	public List<String> getDistribucionesSelec() {
		return distribucionesSelec;
	}

	public void setDistribucionesSelec(List<String> distribucionesSelec) {
		this.distribucionesSelec = distribucionesSelec;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	/**
	 * @return the areasXSede
	 */
	public List<AreaSedeDTO> getAreasXSede() {
		return areasXSede;
	}

	/**
	 * @param areasXSede
	 *            the areasXSede to set
	 */
	public void setAreasXSede(List<AreaSedeDTO> areasXSede) {
		this.areasXSede = areasXSede;
	}

	/**
	 * @return the nuevaConfigArea
	 */
	public boolean isNuevaConfigArea() {
		return nuevaConfigArea;
	}

	/**
	 * @param nuevaConfigArea
	 *            the nuevaConfigArea to set
	 */
	public void setNuevaConfigArea(boolean nuevaConfigArea) {
		this.nuevaConfigArea = nuevaConfigArea;
	}

	public String getStyleDist() {
		return styleDist;
	}

	public void setStyleDist(String styleDist) {
		this.styleDist = styleDist;
	}

	public String getStyleDistLabel() {
		return styleDistLabel;
	}

	public void setStyleDistLabel(String styleDistLabel) {
		this.styleDistLabel = styleDistLabel;
	}

	public boolean isVerComboSede() {
		return verComboSede;
	}

	public void setVerComboSede(boolean verComboSede) {
		this.verComboSede = verComboSede;
	}

	public SistemaBean getTextosSistema() {
		return textosSistema;
	}

	public void setTextosSistema(SistemaBean textosSistema) {
		this.textosSistema = textosSistema;
	}

	public boolean isEditConfigArea() {
		return editConfigArea;
	}

	public void setEditConfigArea(boolean editConfigArea) {
		this.editConfigArea = editConfigArea;
	}

	public String getRutaPrincipal() {
		return rutaPrincipal;
	}

	public void setRutaPrincipal(String rutaPrincipal) {
		this.rutaPrincipal = rutaPrincipal;
	}

	public String getRutaAreas() {
		return rutaAreas;
	}

	public void setRutaAreas(String rutaAreas) {
		this.rutaAreas = rutaAreas;
	}

	public String getNombreFotoAreaComun() {
		return nombreFotoAreaComun;
	}

	public void setNombreFotoAreaComun(String nombreFotoAreaComun) {
		this.nombreFotoAreaComun = nombreFotoAreaComun;
	}

	public List<File> getListaImagenesEliminar() {
		return listaImagenesEliminar;
	}

	public void setListaImagenesEliminar(List<File> listaImagenesEliminar) {
		this.listaImagenesEliminar = listaImagenesEliminar;
	}

	public OrgGubernamentalService getOrgGubernamentalService() {
		return orgGubernamentalService;
	}

	public void setOrgGubernamentalService(OrgGubernamentalService orgGubernamentalService) {
		this.orgGubernamentalService = orgGubernamentalService;
	}

}
