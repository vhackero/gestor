package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.NotificacionSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.CargaArchivosOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RecursosOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.ReponsableProduccionOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatGestionAprendizajeEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatEstadoAva;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionSistemaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.AmbienteVirtualApService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.CargaArchivosOaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelUnidadOaAvaService;
import mx.gob.sedesol.basegestor.springinit.GestionAprendizajeServiceAdapter;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CursoWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.commons.utils.EstadoEventoCapEnum;
import mx.gob.sedesol.gestorweb.commons.utils.EstatusAmbienteVirtualAprendizajeEnum;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ManagedBean
@SessionScoped
public class ModificarSeguimientoAVABean extends BaseBean {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ModificarSeguimientoAVABean.class);

	/**
	 * Inyeccion de ambienteVirtualApService
	 */
	@ManagedProperty(value = "#{ambienteVirtualApService}")
	private AmbienteVirtualApService ambienteVirtualApService;

	/**
	 * Inyeccion de relUnidadOaAvaService
	 */
	@ManagedProperty(value = "#{relUnidadOaAvaService}")
	private RelUnidadOaAvaService relUnidadOaAvaService;

	/**
	 * Inyeccion de cargaArchivosOaService
	 */
	@ManagedProperty(value = "#{cargaArchivosOaService}")
	private CargaArchivosOaService cargaArchivosOaService;

	/**
	 * Inyeccion del servicio de notificaciones
	 */
	@ManagedProperty(value = "#{notificacionSistemaService}")
	private NotificacionSistemaService notificacionSistemaService;

	/**
	 * Inyeccion del servicio de sistema
	 */
	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	/**
	 * Inyeccion de gestion aprendizaje Service Adapter
	 */
	@ManagedProperty(value = "#{gestionAprendizajeServiceAdapter}")
	private GestionAprendizajeServiceAdapter gestionAprendizajeServiceAdapter;

	private AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO;

	private List<UnidadOaAvaDTO> unidadOaAvaDTOs;

	private List<ReponsableProduccionOaDTO> reponsableProduccionOaDTOs;

	private ReponsableProduccionEcDTO coordinadorAcademico;

	private ReponsableProduccionEcDTO responsableProduccion;

	private UnidadOaAvaDTO objetoAprendizajeSeleccionado;

	private CatalogoComunDTO catTemaRecurso;

	private RecursosOaDTO recursosOaDTO;

	private List<RecursosOaDTO> recursosOaDTOs;

	private String tituloMdlCargaArchivo = "Guion Instruccional";

	private List<CargaArchivosOaDTO> archivosCargados;

	private UploadedFile fileUpload;

	private StreamedContent fileDownload;

	private List<CatalogoComunDTO> catClasificacionArchivos;

	private List<CatalogoComunDTO> catEstadoEventoCapacitacion;

	private CatalogoComunDTO clasificacionArchivoSeleccionado;

	private UsuarioSessionDTO usuarioSessionDTO;

	private Boolean botonCargaEsActivo = Boolean.FALSE;

	private Boolean botonDescargaEsActivo = Boolean.FALSE;

	private Boolean isAvaConfirmable = Boolean.FALSE;

	private Map<String, String> mapaPermisos;

	private List<CatalogoComunDTO> estadoAvaList;

	private String tipoCompetencia;

	private String ejeCapacitacion;

	private String pesoMaximoArchivo;

	private String extensionValida;

	private String mensajeExtensionInvalida;

	private String mensajePesoIncorrecto;

	private static final String RESPONSABILIDAD_COORDINADORACADEMICO = "Coordinador académico";

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		pesoMaximoArchivo = "300000000";
		logger.info("Inicializando recursos de ModificaSeguimientoAVABean");

		logger.info("Obteniendo parametros");

		logger.info("Obtiene permisos");

		mapaPermisos = getUsuarioEnSession().getListaPermisos();

		usuarioSessionDTO = this.getUsuarioEnSession();

		recursosOaDTO = new RecursosOaDTO();

		/** Se carga el catalogo CAT_CLASIFICACION_ARCHIVO_OA **/
		catClasificacionArchivos = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_CLASIFICACION_ARCHIVO_OA);

		/** Se carga el catalogo CAT_ESTADO_EVENTO_CAPACITACION **/
		catEstadoEventoCapacitacion = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_ESTADO_EVENTO_CAPACITACION);

		logger.info("El valor del objeto AVA es " + ambienteVirtualAprendizajeDTO);

		/** Obtiene los estatus del AVA **/
		estadoAvaList = gestionAprendizajeServiceAdapter
				.getCatalogoServiceByGestionAprendizajeEnum(CatGestionAprendizajeEnum.CAT_ESTADO_AVA)
				.findAll(CatEstadoAva.class);
	}

	private void obtenerResponsablesProdXUnidades(List<UnidadOaAvaDTO> unidadOaAvaDTOs) {

		for (UnidadOaAvaDTO unidadOaAvaDTO : unidadOaAvaDTOs) {
			obtenerResponsablesProdXUnidad(unidadOaAvaDTO);
		}

	}

	private void obtenerResponsablesProdXUnidad(UnidadOaAvaDTO unidadOaAvaDTO) {
		for (ReponsableProduccionOaDTO reponsableProduccionOaDTO : unidadOaAvaDTO.getReponsableProduccionOas()) {

			obtnerResponsableProduccionXtipoResponsabilidad(unidadOaAvaDTO, reponsableProduccionOaDTO);
		}
	}

	private void obtnerResponsableProduccionXtipoResponsabilidad(UnidadOaAvaDTO unidadOaAvaDTO,
			ReponsableProduccionOaDTO reponsableProduccionOaDTO) {

		String tipoResponsabilidad = null;

		if (ObjectUtils
				.isNotNull(reponsableProduccionOaDTO.getPersonaResponsabilidade().getCatTipoResponsabilidadEc())) {

			tipoResponsabilidad = reponsableProduccionOaDTO.getPersonaResponsabilidade().getCatTipoResponsabilidadEc()
					.getNombre();

			switch (tipoResponsabilidad) {
			case "Experto en contenido":

				unidadOaAvaDTO.setExpertoEnContenido(reponsableProduccionOaDTO);

				break;

			case "Diseñador instruccional":

				unidadOaAvaDTO.setDisenadorInstruccional(reponsableProduccionOaDTO);
				break;

			case "Diseñador elearning":
				unidadOaAvaDTO.setDisenadorElearning(reponsableProduccionOaDTO);
				break;

			case "Desarrollador elearning":
				unidadOaAvaDTO.setDesarrolladorElearning(reponsableProduccionOaDTO);
				break;

			default:
				logger.info("AsignacionRespProduccionGABean:" + "obtnerResponsableProduccionXtipoResponsabilidad:Tipo "
						+ "	de responsable no encontrado el tipo de responsabilidad es " + "	"
						+ tipoResponsabilidad);
				break;
			}

		} else {
			logger.info("AsignacionRespProduccionGABean:" + "obtnerResponsableProduccionXtipoResponsabilidad:Tipo "
					+ "	de responsable nulo");
		}
	}

	private void validaElementosConfirmables(List<UnidadOaAvaDTO> unidadOaAvaDTOs) {
		for (UnidadOaAvaDTO unidadOaAvaDTO : unidadOaAvaDTOs) {
			inicializaNulos(unidadOaAvaDTO, ambienteVirtualAprendizajeDTO);
			this.validaElementosConfirmables(unidadOaAvaDTO);
			this.validaElementoFuncionalidadConfirmable(unidadOaAvaDTO, ambienteVirtualAprendizajeDTO);
		}
		this.validaAVAConfirmable(unidadOaAvaDTOs);
	}

	/**
	 * Cuando se suba a produccion quitar este metodo ya que en teoria las
	 * unidades no se insertaran en nulo
	 * 
	 * @param unidadOaAvaDTO
	 */
	private void inicializaNulos(UnidadOaAvaDTO unidadOaAvaDTO,
			AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		if (ObjectUtils.isNull(unidadOaAvaDTO.getValidacionDesarrolloOa())) {
			unidadOaAvaDTO.setValidacionDesarrolloOa(Boolean.FALSE);
		}

		if (ObjectUtils.isNull(unidadOaAvaDTO.getValidacionGuionInst())) {
			unidadOaAvaDTO.setValidacionGuionInst(Boolean.FALSE);
		}

		if (ObjectUtils.isNull(unidadOaAvaDTO.getValidacionScorm())) {
			unidadOaAvaDTO.setValidacionScorm(Boolean.FALSE);
		}

		if (ObjectUtils.isNull(unidadOaAvaDTO.getFuncionalidad())) {
			unidadOaAvaDTO.setFuncionalidad(Boolean.FALSE);
		}

		if (ObjectUtils.isNull(unidadOaAvaDTO.getFuncionalidad())) {
			unidadOaAvaDTO.setFuncionalidad(Boolean.FALSE);
		}

		if (ObjectUtils.isNull(ambienteVirtualAprendizajeDTO.getValidacionAva())) {
			ambienteVirtualAprendizajeDTO.setValidacionAva(Boolean.FALSE);
		}

	}

	/**
	 * Metodo que valida si el Guion Instruccional el desarrollo Oa y el Scorm
	 * pueden habilitarse y confirmarse
	 * 
	 * @param unidadOaAvaDTO
	 */
	private void validaElementosConfirmables(UnidadOaAvaDTO unidadOaAvaDTO) {
		if (!ObjectUtils.isNullOrEmpty(unidadOaAvaDTO.getCargaArchivosOas())) {
			for (CargaArchivosOaDTO cargaArchivosOaDTO : unidadOaAvaDTO.getCargaArchivosOas()) {

				String nombreTipoArchivo = cargaArchivosOaDTO.getCatClasificacionArchivoOa().getNombre();

				switch (nombreTipoArchivo) {

				case "Guión instruccional":
					/**
					 * Si el guion esta confirmado(checado) ya no se puede
					 * desmarcar
					 **/
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VAL_GUI_INS", "", requestActual(),
							TipoServicioEnum.LOCAL);
					if (!unidadOaAvaDTO.getValidacionGuionInst()) {
						/**
						 * Valida si se tienen permisos para habiliar el check y
						 * que se puede confirmar el guion
						 **/
						if (validarConfirmacionGuionInstitucional()) {
							unidadOaAvaDTO.setIsGuionIConfirmable(Boolean.TRUE);
						}
					} else {
						unidadOaAvaDTO.setIsGuionIConfirmable(Boolean.FALSE);
					}
					break;
				case "Desarrollo OA":
					/**
					 * Si el desarrollo esta confirmado(checado) ya no se puede
					 * desmarcar
					 **/
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VAL_DES_OAS", "", requestActual(),
							TipoServicioEnum.LOCAL);
					if (!unidadOaAvaDTO.getValidacionDesarrolloOa()) {
						/**
						 * Valida si se tienen permisos para habilitar el check
						 * y que se pueda confirmar el Desarrollo del oa
						 **/
						if (validarConfirmacionDesarrolloOa()) {
							unidadOaAvaDTO.setIsDesarrolloOaConfirmable(Boolean.TRUE);
						}
					} else {
						unidadOaAvaDTO.setIsDesarrolloOaConfirmable(Boolean.FALSE);
					}

					break;

				case "SCORM":
					/**
					 * Si el SCORM esta confirmado(checado) ya no se puede
					 * desmarcar
					 **/
					bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VAL_SCO", "", requestActual(),
							TipoServicioEnum.LOCAL);
					if (!unidadOaAvaDTO.getValidacionScorm()) {
						/**
						 * Valida si se tienen permisos para habilitar el check
						 * y que se pueda confirmar el Scorm
						 **/
						if (validarConfirmacionScorm()) {
							unidadOaAvaDTO.setIsScormConfirmable(Boolean.TRUE);
						}
					} else {
						unidadOaAvaDTO.setIsScormConfirmable(Boolean.FALSE);
					}

					break;

				default:
					logger.error(
							"validaElementosConfirmables:Tipo archivo no encontrado archivo es " + nombreTipoArchivo);
				}
			}
		}

	}

	public String navegaModificarSeguimientoAVA() {

		logger.info("El tipo de competencia es " + this.tipoCompetencia);
		/** Obtiene AVA **/

		/** Obtiene a el Coordinarodor academico **/

		coordinadorAcademico = ambienteVirtualAprendizajeDTO.getEventoCapacitacion()
				.getResponsableCoordinadorAcademico();

		responsableProduccion = ambienteVirtualAprendizajeDTO.getEventoCapacitacion().getResponsableProduccion();

		/** Obtiene las unidades **/

		logger.info("recuperando unidades por id AVA " + ambienteVirtualAprendizajeDTO.getId());

		unidadOaAvaDTOs = relUnidadOaAvaService.findByIdAva(ambienteVirtualAprendizajeDTO.getId());

		/** Asigna a los responsables de produccion por unidad **/
		this.obtenerResponsablesProdXUnidades(unidadOaAvaDTOs);

		/** Settear coordinador academico a OA **/

		for (UnidadOaAvaDTO unidadOaAvaDTO : unidadOaAvaDTOs) {
			unidadOaAvaDTO.getAmbienteVirtualAprendizaje().getEventoCapacitacion()
					.setResponsableCoordinadorAcademico(coordinadorAcademico);
		}

		logger.info("unidades recuperadas " + unidadOaAvaDTOs.size());

		/**
		 * Valida los elementos confirmables (guion,desarrollo del oa, Scorm)
		 **/
		this.validaElementosConfirmables(unidadOaAvaDTOs);

		/** Calcula el porcentaje de avance por unidad **/

		this.calculaAvanceXListaUnidades(unidadOaAvaDTOs);

		/** Calcula el porcentaje de avance por AVA **/
		this.calculaAvanceAva(ambienteVirtualAprendizajeDTO, unidadOaAvaDTOs);

		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_AVA",
				String.valueOf(ambienteVirtualAprendizajeDTO.getId()), requestActual(), TipoServicioEnum.LOCAL);

		return ConstantesGestorWeb.NAVEGA_MODIFICAR_SEGUIMIENTO_AVA;
	}

	/**
	 * Metodo que valida si el usuario tiene permisos para validar el guion
	 * instruccional
	 * 
	 * @return
	 */
	private Boolean validarConfirmacionGuionInstitucional() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_VALIDAR_GUION_INST);
	}

	/**
	 * Metodo que valida si el usuario tiene permisos para validar el desarrollo
	 * del oa
	 * 
	 * @return
	 */
	private Boolean validarConfirmacionDesarrolloOa() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_VALIDAR_DESARROLLO_OA);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para validar el Scorm
	 * 
	 * @return
	 */
	private Boolean validarConfirmacionScorm() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_VALIDAR_SCORM);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para validar la
	 * funcionalidad del objeto de aprendizaje
	 * 
	 * @return
	 */
	private Boolean validarConfirmacionUnidadOA() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_VALIDAR_FUNCIONALIDAD_OA);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para validar la
	 * funcionalidad del AVA
	 * 
	 * @return
	 */
	private Boolean validarConfirmacionAVA() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_VALIDAR_AVA);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para cargar archivos a al
	 * modulo de guion institucional
	 * 
	 * @return
	 */
	private Boolean validarCargaArchivosGuionInst() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_AJUNTAR_GUION_INST);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para cargar archivos aun
	 * despues de validado al modulo de guion institucional
	 * 
	 * @return
	 */
	private Boolean validarCargaArchivosGuionInstExt() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_AJUNTAR_GUION_INST_EXT);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para cargar archivos a al
	 * modulo de desarrollo oa
	 * 
	 * @return
	 */
	private Boolean validarCargaArchivosDesarrolloOa() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_AJUNTAR_DESARROLLO_OA);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para cargar archivos
	 * despues de validado al modulo de desarrollo oa
	 * 
	 * @return
	 */
	private Boolean validarCargaArchivosDesarrolloOaExt() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_AJUNTAR_DESARROLLO_OA_EXT);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para cargar archivos a al
	 * modulo de SCORM
	 * 
	 * @return
	 */
	private Boolean validarCargaArchivosScorm() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_AJUNTAR_SCORM);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para cargar archivos a al
	 * modulo de SCORM despues de validado
	 * 
	 * @return
	 */
	private Boolean validarCargaArchivosScormExt() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_AJUNTAR_SCORM_EXT);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para descargar archivos
	 * del modulo de guion instruccional
	 * 
	 * @return
	 */
	private Boolean validarDescargaArchivosGuionInst() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_DESCARGAR_GUION_INST);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para descargar archivos
	 * del modulo de desarrollo del oa
	 * 
	 * @return
	 */
	private Boolean validarDescargaArchivosDesarrolloOa() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_DESCARGAR_DESARROLLO_OA);
	}

	/**
	 * Metodo que sirve para validar si tiene permisos para descargar archivos
	 * del modulo de Scorm
	 * 
	 * @return
	 */
	private Boolean validarDescargaArchivosScorm() {
		return mapaPermisos.containsKey(ConstantesGestorWeb.CLAVE_SEGURIDAD_DESCARGAR_SCORM);
	}

	/**
	 * Metodo que sirve para validar si el elemento funcionalidad sera
	 * confirmable en funcion de si tiene permisos para hacerlo y si
	 * anteriormente ya se validaron el guion, el objeto de aprendizaje y el
	 * scorm
	 * 
	 * @param unidadOaAvaDTO
	 * @param ambienteVirtualAprendizajeDTO
	 */
	private void validaElementoFuncionalidadConfirmable(UnidadOaAvaDTO unidadOaAvaDTO,
			AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {
		logger.info(unidadOaAvaDTO.getValidacionDesarrolloOa());
		logger.info(unidadOaAvaDTO.getValidacionGuionInst());
		logger.info(unidadOaAvaDTO.getValidacionScorm());

		if (unidadOaAvaDTO.getValidacionDesarrolloOa() && unidadOaAvaDTO.getValidacionGuionInst()
				&& unidadOaAvaDTO.getValidacionScorm()) {

			unidadOaAvaDTO.setIsFuncionalidadConfirmable(Boolean.FALSE);

			if (!unidadOaAvaDTO.getFuncionalidad() && this.validarConfirmacionUnidadOA()) {
				unidadOaAvaDTO.setIsFuncionalidadConfirmable(Boolean.TRUE);
			}
		} else {
			/**
			 * Si llega a este bloque de codigo es que falta validar el guion o
			 * el desarrollo del oa o el scorm
			 **/

			/**
			 * No es correcto que se tenga la unidad validada si no cumple con
			 * la valiacion anterior se actualiza el estado de la unidad
			 **/

			if (unidadOaAvaDTO.getFuncionalidad()) {
				unidadOaAvaDTO.setFuncionalidad(Boolean.FALSE);

			}

			/** Quita la confirmacion de funcionalidad de la unidad **/
			unidadOaAvaDTO.setIsFuncionalidadConfirmable(Boolean.FALSE);
			unidadOaAvaDTO.setFuncionalidad(Boolean.FALSE);

			if (ambienteVirtualAprendizajeDTO.getValidacionAva()) {
				ambienteVirtualAprendizajeDTO.setValidacionAva(Boolean.FALSE);
				ambienteVirtualAprendizajeDTO.setActivo(Boolean.FALSE);

			}

			/** Quita la confirmacion del AVA y lo desactiva **/
			isAvaConfirmable = Boolean.FALSE;
			ambienteVirtualAprendizajeDTO.setValidacionAva(Boolean.FALSE);
			ambienteVirtualAprendizajeDTO.setActivo(Boolean.FALSE);

		}

		logger.info("la unidad " + unidadOaAvaDTO.getId() + " Es confirmable ? "
				+ unidadOaAvaDTO.getIsFuncionalidadConfirmable());

	}

	private void validaAVAConfirmable(List<UnidadOaAvaDTO> unidadOaAvaDTOs) {
		isAvaConfirmable = Boolean.FALSE;
		if (this.validarConfirmacionAVA() && !ambienteVirtualAprendizajeDTO.getValidacionAva()) {

			for (UnidadOaAvaDTO unidadOaAvaDTO : unidadOaAvaDTOs) {
				if (!unidadOaAvaDTO.getFuncionalidad()) {
					isAvaConfirmable = Boolean.TRUE;
					/** Quita la confirmacion del AVA y lo desactiva **/
					/**
					 * Si a alguna unidad no esta validada retira la validacion
					 * del AVA y lo desactiva
					 **/
					ambienteVirtualAprendizajeDTO.setValidacionAva(Boolean.FALSE);
					ambienteVirtualAprendizajeDTO.setActivo(Boolean.FALSE);

					break;

				}
			}
		} else {
			isAvaConfirmable = Boolean.TRUE;
			logger.info("validaAVAConfirmable:Sin  permisos para habilitar  ava");
		}

	}

	public ReponsableProduccionEcDTO obtenerCoordinadorAcademico(
			List<ReponsableProduccionEcDTO> reponsableProduccionEcDTO) {

		String nombreResponsabilidad = null;

		ReponsableProduccionEcDTO coordinadorAcademico = new ReponsableProduccionEcDTO();

		for (ReponsableProduccionEcDTO reponsableProduccionEcDTO2 : reponsableProduccionEcDTO) {

			if (ObjectUtils
					.isNotNull(reponsableProduccionEcDTO2.getPersonaResponsabilidad().getCatTipoResponsabilidadEc())) {
				nombreResponsabilidad = reponsableProduccionEcDTO2.getPersonaResponsabilidad()
						.getCatTipoResponsabilidadEc().getNombre();

				if (RESPONSABILIDAD_COORDINADORACADEMICO.equals(nombreResponsabilidad)) {
					coordinadorAcademico = reponsableProduccionEcDTO2;
				}
			} else {
				logger.info("No se econtraron datos de coordianador academico");
			}
		}

		return coordinadorAcademico;
	}

	public void procesaModalGestionArchivos(UnidadOaAvaDTO unidadOaAvaDTO, String tipoCargaAchivo,
			Boolean botonCargaEsActivo) {

		clasificacionArchivoSeleccionado = this.obtieneTipoCargaArchivo(tipoCargaAchivo);

		objetoAprendizajeSeleccionado = unidadOaAvaDTO;

		this.botonCargaEsActivo = (!this.validarCargaDescargaArchivo(clasificacionArchivoSeleccionado.getNombre(), 1,
				objetoAprendizajeSeleccionado));

		this.botonDescargaEsActivo = (!this.validarCargaDescargaArchivo(clasificacionArchivoSeleccionado.getNombre(), 2,
				objetoAprendizajeSeleccionado));

		this.obtenerArchivosCargados(objetoAprendizajeSeleccionado, clasificacionArchivoSeleccionado);

		this.obtenerPesoMaximoArchivoValida(clasificacionArchivoSeleccionado.getNombre());
		this.obtenerExtensionArchivoValida(clasificacionArchivoSeleccionado.getNombre());
		this.obtenerMensajePesoIncorrecto(clasificacionArchivoSeleccionado.getNombre());
		this.obtenerMensajeExtensionIncorrecta(clasificacionArchivoSeleccionado.getNombre());

	}

	/**
	 * Metodo que valida los permisos para la carga y descarga de los archivos
	 * dependiendo tipo de archivo y si es carga o descarga 1 = Carga 2 =
	 * Descarga
	 * 
	 * @param nombreTipoArchivo
	 * @param tipoCargaDescarga
	 * @return
	 */
	public Boolean validarCargaDescargaArchivo(String nombreTipoArchivo, Integer tipoCargaDescarga,
			UnidadOaAvaDTO unidadOaAvaDTO) {

		Boolean esConfirmacionValida = Boolean.FALSE;

		switch (nombreTipoArchivo) {

		case "Guión instruccional":
			if (tipoCargaDescarga.equals(1)) {
				/**
				 * Si el guion esta validado (checado) ya no se pueden cargar
				 * archivos
				 **/
				if (!unidadOaAvaDTO.getValidacionGuionInst()) {
					/**
					 * Valida que se tenga permisos para cargar el guion
					 * instruccional
					 **/
					if (this.validarCargaArchivosGuionInst()) {
						esConfirmacionValida = Boolean.TRUE;
					}
				} else {
					if (this.validarCargaArchivosGuionInstExt()) {
						esConfirmacionValida = Boolean.TRUE;
					}
				}
			}
			/** Valida que si tiene permisos para descargar guion **/
			else if (tipoCargaDescarga.equals(2)) {
				/**
				 * Si el guion esta validado (checado) ya no hace la validacion
				 * y todos los roles pueden decargar el guion
				 **/
				if (!unidadOaAvaDTO.getValidacionGuionInst()) {
					if (this.validarDescargaArchivosGuionInst()) {
						esConfirmacionValida = Boolean.TRUE;
					}
				} else {
					esConfirmacionValida = Boolean.TRUE;
				}
			}
			break;
		case "Desarrollo OA":
			if (tipoCargaDescarga.equals(1)) {

				if (!unidadOaAvaDTO.getValidacionDesarrolloOa()) {
					/**
					 * Valida que se tenga permisos para cargar el desarrollo oa
					 **/
					if (this.validarCargaArchivosDesarrolloOa()) {
						esConfirmacionValida = Boolean.TRUE;
					}
				} else {
					if (this.validarCargaArchivosDesarrolloOaExt()) {
						esConfirmacionValida = Boolean.TRUE;
					}
				}
			} /**
				 * Valida que si tiene permisos para descargar el desarrollo oa
				 **/
			else if (tipoCargaDescarga.equals(2)) {
				/**
				 * Si el desarrollo oa esta validado (checado) ya no hace la
				 * validacion y todos los roles pueden decargar el guion
				 **/
				if (!unidadOaAvaDTO.getValidacionDesarrolloOa()) {
					if (this.validarDescargaArchivosDesarrolloOa()) {
						esConfirmacionValida = Boolean.TRUE;
					}
				} else {
					esConfirmacionValida = Boolean.TRUE;
				}
			}

			break;

		case "SCORM":
			if (tipoCargaDescarga.equals(1)) {

				if (!unidadOaAvaDTO.getValidacionScorm()) {
					/** Valida que se tenga permisos para cargar el Scorm **/
					if (this.validarCargaArchivosScorm()) {
						esConfirmacionValida = Boolean.TRUE;
					}
				} else {
					if (this.validarCargaArchivosScormExt()) {
						esConfirmacionValida = Boolean.TRUE;
					}
				}
			} /** Valida que si tiene permisos para descargar el Scorm **/
			else if (tipoCargaDescarga.equals(2)) {
				/**
				 * Si el desarrollo oa esta validado (checado) ya no hace la
				 * validacion y todos los roles pueden decargar el Scorm
				 **/
				if (!unidadOaAvaDTO.getValidacionScorm()) {
					if (this.validarDescargaArchivosScorm()) {
						esConfirmacionValida = Boolean.TRUE;
					}
				} else {
					esConfirmacionValida = Boolean.TRUE;
				}
			}

			break;

		default:
			logger.error("validarCargaDescargaArchivo:Tipo archivo no encontrado archivo es " + nombreTipoArchivo);
		}

		return esConfirmacionValida;
	}

	private CatalogoComunDTO obtieneTipoCargaArchivo(String tipoCargaAchivo) {
		CatalogoComunDTO clasificacionCargaArchivoDTO = new CatalogoComunDTO();
		for (CatalogoComunDTO catalogoComunDTO : catClasificacionArchivos) {
			if (tipoCargaAchivo.equals(catalogoComunDTO.getNombre())) {
				clasificacionCargaArchivoDTO = catalogoComunDTO;
			}
		}

		if (ObjectUtils.isNull(clasificacionCargaArchivoDTO.getId())) {
			logger.error("No se encontro el tipo de archivo");
		}

		logger.info("La clasificacion de archivo es " + clasificacionCargaArchivoDTO.getNombre());
		return clasificacionCargaArchivoDTO;
	}

	private void obtenerArchivosCargados(UnidadOaAvaDTO unidadOaAvaDTO, CatalogoComunDTO catalogoComunDTO) {

		logger.info("Buscar los archivos por la unidad ava " + unidadOaAvaDTO);

		archivosCargados = cargaArchivosOaService.buscarPorIdUnidadOa(unidadOaAvaDTO, catalogoComunDTO);

		logger.info("El numero de archivos recuperados es de " + archivosCargados.size());

	}

	private String obtenerPesoMaximoArchivoValida(String clasificacionArchivoSeleccionado) {

		if (ObjectUtils.isNotNull(clasificacionArchivoSeleccionado)) {
			String nombreArchivo = clasificacionArchivoSeleccionado;

			switch (nombreArchivo) {

			case "Guión instruccional":

				break;
			case "Desarrollo OA":

				break;

			case "SCORM":

				break;

			default:
				logger.error("obtenerPesoMaximoArchivoValida:Tipo archivo no encontrado archivo es" + " "
						+ clasificacionArchivoSeleccionado);
			}
		}
		return pesoMaximoArchivo;
	}

	private String obtenerMensajeExtensionIncorrecta(String clasificacionArchivoSeleccionado) {

		mensajeExtensionInvalida = "Peso invalido";
		if (ObjectUtils.isNotNull(clasificacionArchivoSeleccionado)) {
			String nombreArchivo = clasificacionArchivoSeleccionado;
			switch (nombreArchivo) {

			case "Guión instruccional":
				mensajeExtensionInvalida = "gw.ga.mensaje.tipoarchivo";
				break;
			case "Desarrollo OA":
				mensajeExtensionInvalida = "gw.ga.mensaje.tipoarchivoZip";

			case "SCORM":
				mensajeExtensionInvalida = "gw.ga.mensaje.tipoarchivoZip";
				break;

			default:
				logger.error("obtenerExtensionArchivoValida archivo no encontrado archivo es" + " "
						+ clasificacionArchivoSeleccionado);
			}
		}

		return extensionValida;
	}

	private void obtenerMensajePesoIncorrecto(String clasificacionArchivoSeleccionado) {

		mensajePesoIncorrecto = "Peso invalido";
		if (ObjectUtils.isNotNull(clasificacionArchivoSeleccionado)) {
			String nombreArchivo = clasificacionArchivoSeleccionado;
			switch (nombreArchivo) {

			case "Guión instruccional":
				mensajePesoIncorrecto = "gw.ga.mensaje.pesoarchivo";
				break;
			case "Desarrollo OA":
				mensajePesoIncorrecto = "gw.ga.mensaje.pesoarchivoOaScorm";

			case "SCORM":
				mensajePesoIncorrecto = "gw.ga.mensaje.pesoarchivoOaScorm";
				break;

			default:
				logger.error("obtenerExtensionArchivoValida archivo no encontrado archivo es" + " "
						+ clasificacionArchivoSeleccionado);
			}
		}

	}

	private void obtenerExtensionArchivoValida(String clasificacionArchivoSeleccionado) {
		extensionValida = "/(\\.|\\/)(doc|docx|pdf|ppt|pptx)$/";
		if (ObjectUtils.isNotNull(clasificacionArchivoSeleccionado)) {
			String nombreArchivo = clasificacionArchivoSeleccionado;
			switch (nombreArchivo) {

			case "Guión instruccional":
				extensionValida = "/(\\.|\\/)(doc|docx|pdf|ppt|pptx)$/";
				break;
			case "Desarrollo OA":
				extensionValida = "/(\\.|\\/)(zip)$/";
				break;

			case "SCORM":
				extensionValida = "/(\\.|\\/)(zip)$/";
				break;

			default:
				logger.error("obtenerExtensionArchivoValida archivo no encontrado archivo es" + " "
						+ clasificacionArchivoSeleccionado);
			}
		}

	}

	/**
	 * Metodo que realiza la carga de un archivo almacendolo en el servidor
	 * fisicamente, persistiendo un registro en la base de datos con el estado
	 * del archivo y el envio de un mensaje a el perfil validara el archivo
	 * 
	 * @param fileUploadEvent
	 */
	public void cargaArchivo(FileUploadEvent fileUploadEvent) {

		if (this.validarCargaDescargaArchivo(clasificacionArchivoSeleccionado.getNombre(), 1,
				objetoAprendizajeSeleccionado)) {
			String directorio = null;
			String idArchivo = null;
			Boolean ocurrioUnError = Boolean.FALSE;
			ResultadoDTO<CargaArchivosOaDTO> resultadoGuardarDetallesArchivo = null;
			this.fileUpload = fileUploadEvent.getFile();
			logger.info("Se cargo el archivo correctamente");
			logger.info("El nombre del archivo es " + fileUpload.getFileName());

			idArchivo = this.generaIdArchivo();

			directorio = this.obtenerDirectorioParaEscrituraArchivo();

			resultadoGuardarDetallesArchivo = this.guardaDetallesCargaArchivo(fileUpload, directorio, idArchivo);

			logger.info("El resultado de la transaccion de carga de archivo es "
					+ resultadoGuardarDetallesArchivo.getResultado() + " "
					+ (resultadoGuardarDetallesArchivo.getResultado().equals(ResultadoTransaccionEnum.EXITOSO)));

			if (resultadoGuardarDetallesArchivo.getResultado().equals(ResultadoTransaccionEnum.EXITOSO)) {
				ocurrioUnError = this.escribeArchivoEnDisco(directorio, idArchivo);
				if (ocurrioUnError) {
					borrarArchivo(resultadoGuardarDetallesArchivo.getDto());
				} else {
					this.anadeArchivoAUnidadAva(objetoAprendizajeSeleccionado,
							resultadoGuardarDetallesArchivo.getDto());
					this.validaElementosConfirmables(objetoAprendizajeSeleccionado);
					/** Envio de mensaje de archivo cargado **/
					this.enviaMensajeArchivoCargado(clasificacionArchivoSeleccionado.getNombre(),
							fileUpload.getFileName());
				}

			}

			this.obtenerArchivosCargados(objetoAprendizajeSeleccionado, clasificacionArchivoSeleccionado);
		} else {
			logger.error("Sin permisos suficientes para cargar el archivo");

		}
	}

	private Double obtenerPesoArchivo(Long pesoArchivo) {
		Double kilobytes = 0.0;
		if (ObjectUtils.isNotNull(pesoArchivo) && 0 != pesoArchivo) {
			kilobytes = (pesoArchivo.doubleValue() / 1024);
		}
		logger.info("El peso del archivo en kb es " + redondearADosDecimales(kilobytes));
		return redondearADosDecimales(kilobytes);

	}

	private Double redondearADosDecimales(Double valor) {
		return valor = Math.round(valor * 100.0) / 100.0;
	}

	/**
	 * Metodo que envia mensajes
	 */
	private void enviaMensajeArchivoCargado(String tipoArchivoSeleccionado, String nombreArchivo) {

		String nombreCompleto = null;

		Long idPersona = null;

		List<VariableMensajeOperacionDTO> variables = new ArrayList<>();

		if (ObjectUtils.isNotNull(responsableProduccion) && ObjectUtils.isNotNull(coordinadorAcademico)) {

			switch (tipoArchivoSeleccionado) {

			case "Guión instruccional":

				nombreCompleto = coordinadorAcademico.getPersonaResponsabilidad().getTblPersona().getNombreCompleto();

				idPersona = coordinadorAcademico.getPersonaResponsabilidad().getTblPersona().getIdPersona();

				break;
			case "Desarrollo OA":

				nombreCompleto = responsableProduccion.getPersonaResponsabilidad().getTblPersona().getNombreCompleto();

				idPersona = responsableProduccion.getPersonaResponsabilidad().getTblPersona().getIdPersona();

				break;

			case "SCORM":
				nombreCompleto = responsableProduccion.getPersonaResponsabilidad().getTblPersona().getNombreCompleto();

				idPersona = responsableProduccion.getPersonaResponsabilidad().getTblPersona().getIdPersona();

				break;

			default:
				logger.error(
						"validarCargaDescargaArchivo:Tipo archivo no encontrado archivo es " + tipoArchivoSeleccionado);
			}

			/** Nombre a quien va dirigido el mensjae **/
			variables.add(new VariableMensajeOperacionDTO("${nombre_usuario}", nombreCompleto));
			/** Nombre del evento de capacitacion **/
			variables.add(new VariableMensajeOperacionDTO("${nombre_evt_cap}",
					ambienteVirtualAprendizajeDTO.getEventoCapacitacion().getNombreEc()));
			/** Nombre del tipo de archivo **/
			variables.add(new VariableMensajeOperacionDTO("${tipo_archivo}", tipoArchivoSeleccionado));
			/** nombre del objeto de aprendizaje **/
			variables.add(new VariableMensajeOperacionDTO("${nombre_oa}",
					objetoAprendizajeSeleccionado.getDetEstUnidadDidactica().getNombreUnidad()));
			/** nombre del archivo cargado **/
			variables.add(new VariableMensajeOperacionDTO("${nombre_archivo_subido}", nombreArchivo));

			this.enviaMensaje(ConstantesBitacora.ARCHIVO_CARGADO_MOD_SEG_AVA, idPersona, variables);
		} else {
			logger.info("No se envio mensaje por que el coordinador academico o el responsable de produccion es nulo");
		}

	}

	/**
	 * Envia un mensaje a los usuarios registrados en la lista de que el archivo
	 * ha sido validado
	 * 
	 * @param tipoArchivo
	 * @param idPersonasList
	 * @param nombreUnidadOaAva
	 * @param nombreEventoCapacitacion
	 */
	private void enviaMensajeArchivoValidado(String tipoArchivo, Set<Long> idPersonasList, String nombreUnidadOaAva,
			String nombreEventoCapacitacion) {

		List<VariableMensajeOperacionDTO> variables = this.armaMensajeValidacionArchivos(tipoArchivo, nombreUnidadOaAva,
				nombreEventoCapacitacion);

		/** Envia mensajes **/
		this.enviaMensajeListaUsuarios(idPersonasList, variables, ConstantesBitacora.ARCHIVO_VALIDADO_MOD_SEG_AVA);

	}

	/**
	 * Envia un mensaje a los usuarios registrados en la lista de que el OA ha
	 * sido validado
	 * 
	 * @param idPersonasList
	 * @param nombreEventoCapacitacion
	 * @param nombreUnidadOaAva
	 */
	private void enviarMensajeFuncionalidadValidada(Set<Long> idPersonasList, String nombreEventoCapacitacion,
			String nombreUnidadOaAva) {

		List<VariableMensajeOperacionDTO> variables = this.armaMensajeValidacionFuncionalidad(nombreUnidadOaAva,
				nombreEventoCapacitacion);

		/** Envia mensajes **/
		this.enviaMensajeListaUsuarios(idPersonasList, variables,
				ConstantesBitacora.FUNCIONALIDAD_VALIDADA_MOD_SEG_AVA);

	}

	/**
	 * Envia un mensaje a los usuarios registrados en la lista de que el AVA se
	 * valido
	 * 
	 * @param idPersonasList
	 * @param nombreEventoCapacitacion
	 */
	private void enviarMensajeAvaValidado(Set<Long> idPersonasList, String nombreEventoCapacitacion) {

		List<VariableMensajeOperacionDTO> variables = this.armaMensajeValidacionAVA(nombreEventoCapacitacion);

		/** Envia Mensaje **/
		this.enviaMensajeListaUsuarios(idPersonasList, variables, ConstantesBitacora.AVA_VALIDADO_MOD_SEG_AVA);

	}

	/**
	 * Metodo que estrae los id del equipo de produccion y valida si se le
	 * enviara el mensaje a el responsable de produccion y al CA
	 * 
	 * @param unidadOaAvaDTO
	 * @return
	 */
	private Set<Long> obtenerIdPersonasDestinatarios(UnidadOaAvaDTO unidadOaAvaDTO, Boolean enviarMensajeACAYRepProd) {

		Set<Long> idPersonasList = new HashSet<Long>();
		Long idPersona = null;

		/**
		 * Valida si se le enviara mensaje al CA y al Responsable produccion
		 **/
		if (enviarMensajeACAYRepProd) {

			/** responsable produccion **/
			idPersona = responsableProduccion.getPersonaResponsabilidad().getTblPersona().getIdPersona();
			idPersonasList.add(idPersona);

			/** Coordinador academico **/

			idPersona = coordinadorAcademico.getPersonaResponsabilidad().getTblPersona().getIdPersona();
			idPersonasList.add(idPersona);
		}

		/** Experto en contenido **/

		if (ObjectUtils.isNotNull(unidadOaAvaDTO.getExpertoEnContenido().getPersonaResponsabilidade())) {
			idPersona = unidadOaAvaDTO.getExpertoEnContenido().getPersonaResponsabilidade().getTblPersona()
					.getIdPersona();

			idPersonasList.add(idPersona);

		}

		/** Disenador Instruccional **/

		if (ObjectUtils.isNotNull(unidadOaAvaDTO.getDisenadorInstruccional().getPersonaResponsabilidade())) {
			idPersona = unidadOaAvaDTO.getDisenadorInstruccional().getPersonaResponsabilidade().getTblPersona()
					.getIdPersona();

			idPersonasList.add(idPersona);
		}

		/** Disenador Elearning **/
		if (ObjectUtils.isNotNull(unidadOaAvaDTO.getDisenadorElearning().getPersonaResponsabilidade())) {

			idPersona = unidadOaAvaDTO.getDisenadorElearning().getPersonaResponsabilidade().getTblPersona()
					.getIdPersona();
			idPersonasList.add(idPersona);
		}

		/** Programador Elearning **/

		if (ObjectUtils.isNotNull(unidadOaAvaDTO.getDesarrolladorElearning().getPersonaResponsabilidade())) {
			idPersona = unidadOaAvaDTO.getDesarrolladorElearning().getPersonaResponsabilidade().getTblPersona()
					.getIdPersona();
			idPersonasList.add(idPersona);

		}

		return idPersonasList;
	}

	private List<VariableMensajeOperacionDTO> armaMensajeValidacionAVA(String nombreEventoCapacitacion) {

		List<VariableMensajeOperacionDTO> variables = new ArrayList<>();
		variables.add(new VariableMensajeOperacionDTO("${nombre_evento_cap}", nombreEventoCapacitacion));
		return variables;
	}

	private List<VariableMensajeOperacionDTO> armaMensajeValidacionFuncionalidad(String nombreOA,
			String nombreEventoCapacitacion) {

		List<VariableMensajeOperacionDTO> variables = new ArrayList<>();

		variables.add(new VariableMensajeOperacionDTO("${nombre_oa}", nombreOA));
		variables.add(new VariableMensajeOperacionDTO("${nombre_evento_cap}", nombreEventoCapacitacion));

		return variables;
	}

	private List<VariableMensajeOperacionDTO> armaMensajeValidacionArchivos(String tipoArchivo, String nombreUnidadOa,
			String nombreEventoCap) {

		List<VariableMensajeOperacionDTO> variables = new ArrayList<>();

		variables.add(new VariableMensajeOperacionDTO("${tipo_archivo}", tipoArchivo));
		variables.add(new VariableMensajeOperacionDTO("${nombre_unidad_oa}", nombreUnidadOa));
		variables.add(new VariableMensajeOperacionDTO("${nombre_evento_capacitacion}", nombreEventoCap));

		return variables;
	}

	/**
	 * Metodo que manda mensajes a partir de una lista de usuarios para una
	 * misma plantilla
	 * 
	 * @param idsPersonasList
	 * @param variables
	 * @param operacion
	 */
	private void enviaMensajeListaUsuarios(Set<Long> idsPersonasList, List<VariableMensajeOperacionDTO> variables,
			String operacion) {
		Integer iteracion = 0;
		for (Long long1 : idsPersonasList) {
			logger.info("Enviando mensaje numero " + iteracion++);
			logger.info("A la persona que se enviara el mensaje sera " + long1);
			this.enviaMensaje(operacion, long1, variables);
		}

	}

	/**
	 * Metodo que envia un mensaje pasando como parametro el nombre de la
	 * operacion parao btener la plantilla, el id de el usuario a quien se le
	 * enviara el mensaje y el listado de variables correspondientes para cada
	 * plantilla
	 * 
	 * @param operacion
	 * @param idPersona
	 * @param variables
	 */
	private void enviaMensaje(String operacion, Long idPersona, List<VariableMensajeOperacionDTO> variables) {

		if (ObjectUtils.isNotNull(idPersona) && !this.validaCamposNulosParaEnvioMensaje(variables)) {
			ResultadoDTO<NotificacionSistemaDTO> resultado = notificacionSistemaService.enviarNotificacion(operacion,
					idPersona, variables);
			logger.info("El resultado del envio del mensaje a la persona " + "con el id " + idPersona + " fue "
					+ resultado.getResultado());
		} else {
			logger.info("No se pudo enviar el mensaje el id de la persona a enviar el mensaje es " + idPersona);
		}

	}

	/**
	 * Metodo que valida los parametros que se envian como mensaje
	 */
	private Boolean validaCamposNulosParaEnvioMensaje(List<VariableMensajeOperacionDTO> variables) {
		Boolean existenCamposNulos = Boolean.FALSE;

		for (VariableMensajeOperacionDTO variableMensajeOperacionDTO : variables) {
			if (ObjectUtils.isNull(variableMensajeOperacionDTO)) {
				existenCamposNulos = Boolean.TRUE;
			}
		}

		return existenCamposNulos;
	}

	private void anadeArchivoAUnidadAva(UnidadOaAvaDTO unidadOaAvaDTO, CargaArchivosOaDTO cargaArchivosOaDTO) {

		if (ObjectUtils.isNullOrEmpty(unidadOaAvaDTO.getCargaArchivosOas())) {

			List<CargaArchivosOaDTO> cargaArchivosOaDTOs = new ArrayList<CargaArchivosOaDTO>();

			cargaArchivosOaDTOs.add(cargaArchivosOaDTO);

			unidadOaAvaDTO.setCargaArchivosOas(cargaArchivosOaDTOs);

		} else {
			unidadOaAvaDTO.getCargaArchivosOas().add(cargaArchivosOaDTO);
		}

	}

	private Integer obtieneVersionDelAchivo() {
		Integer versionArchivo = null;
		CargaArchivosOaDTO ultimoArchivoCargado = null;
		if (archivosCargados.size() == 0) {
			versionArchivo = 0;
		} else {
			if (archivosCargados.size() > 0) {
				logger.info("El tamaño de la lista de archivos es " + archivosCargados.size());
				ultimoArchivoCargado = archivosCargados.get(0);
				versionArchivo = ultimoArchivoCargado.getVersionArchivo();
				versionArchivo = versionArchivo + 1;
			}
		}
		logger.info("La version del archivo es " + versionArchivo);

		return versionArchivo;
	}

	private String obtenerDirectorioParaEscrituraArchivo() {
		String directorio = null;
		CargaArchivosOaDTO ultimoArchivoCargado = null;
		if (archivosCargados.size() == 0) {
			directorio = generaDirectorioArchivo();
		} else {
			if (archivosCargados.size() > 0) {
				logger.info("El tamaño de la lista de archivos es " + archivosCargados.size());
				ultimoArchivoCargado = archivosCargados.get(0);
				directorio = ultimoArchivoCargado.getDirectorio();
				logger.info("La cadena resultante es " + directorio);
			}
		}
		return directorio;
	}

	private String generaDirectorioArchivo() {
		StringBuilder directorioArchivo = new StringBuilder(System.getProperty("user.home") + "/upload/");
		return directorioArchivo.toString();
	}

	private String generaIdArchivo() {
		return UUID.randomUUID().toString();
	}

	private ResultadoDTO<CargaArchivosOaDTO> guardaDetallesCargaArchivo(UploadedFile fileUpload, String directorio,
			String idArchivo) {

		logger.info("Va a guardar elestado del archivo ");

		String pesoArchivo = null;

		pesoArchivo = obtenerPesoArchivo(fileUpload.getSize()).toString();

		if (ObjectUtils.isNull(pesoArchivo)) {
			logger.info("El peso del archivo fue nulo se setea a cero");
			pesoArchivo = "0";
		}

		CargaArchivosOaDTO cargaArchivosOaDTO = new CargaArchivosOaDTO();

		cargaArchivosOaDTO.setUsuarioModifico(BigInteger.valueOf((usuarioSessionDTO.getIdPersona())));
		cargaArchivosOaDTO.setFechaRegistro(new Date());
		cargaArchivosOaDTO.setDirectorio(directorio);
		cargaArchivosOaDTO
				.setIdLms(1);/** Checar que se va a hacer con el LMS **/
		cargaArchivosOaDTO.setNombreArchivo(fileUpload.getFileName());
		cargaArchivosOaDTO.setCatClasificacionArchivoOa(clasificacionArchivoSeleccionado);
		cargaArchivosOaDTO.setUnidadOaAva(objetoAprendizajeSeleccionado);
		cargaArchivosOaDTO.setVersionArchivo(obtieneVersionDelAchivo());
		cargaArchivosOaDTO.setIdArchivo(idArchivo);
		cargaArchivosOaDTO.setPesoArchivo(pesoArchivo.concat(" KB"));

		/** Se requiere a la unidad a la que pertenece este archivo **/
		// GUSTAVO --BitacoraUtil.llenarBitacora(cargaArchivosOaDTO,
		// getUsuarioEnSession(), ConstantesBitacora.CARGA_ARCHIVO_OA_AGREGAR,
		// request);
		ResultadoDTO<CargaArchivosOaDTO> cargaArchivosOaDTOs = cargaArchivosOaService.guardar(cargaArchivosOaDTO);

		this.validaMensajeResultadoTransaccion(cargaArchivosOaDTOs.getMensajes(), cargaArchivosOaDTOs.getResultado());

		logger.info("Resultado de la transaccion de guardar archivo  " + cargaArchivosOaDTOs.getResultado());

		return cargaArchivosOaDTOs;
	}

	private void validaMensajeResultadoTransaccion(List<String> mensajes,
			ResultadoTransaccionEnum resultadoTransaccionEnum) {

		if (resultadoTransaccionEnum == ResultadoTransaccionEnum.EXITOSO) {
			if (ObjectUtils.isNotNull(mensajes)) {
				agregarMsgInfo(mensajes.get(0), null, sistema);
			}
		} else {
			if (ObjectUtils.isNotNull(mensajes)) {
				agregarMsgError(mensajes, null, sistema);
			}
		}
	}

	private Boolean escribeArchivoEnDisco(String directorio, String idArchivo) {

		Boolean ocurrioUnError = Boolean.FALSE;

		try {

			logger.info("La ruta donde quedara el archivo es en " + directorio.concat(idArchivo));
			/** Validar de donde se obtiene esta ruta **/
			// File rutaArchivo = new
			// File("/home/gramirez/upload/"+file.getFileName());
			File rutaArchivo = new File(directorio.concat(idArchivo));

			FileUtils.copyInputStreamToFile(fileUpload.getInputstream(), rutaArchivo);
			logger.info("Carga del archivo correcta!" + fileUpload.getFileName());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("Ocurrio un error en la escritura del archivo en disco ");
			ocurrioUnError = Boolean.TRUE;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Ocurrio un error en la carga del archivo ");
			ocurrioUnError = Boolean.TRUE;
		}
		return ocurrioUnError;
	}

	private void borrarArchivo(CargaArchivosOaDTO cargaArchivosOaDTO) {

		logger.info("El archivo a eliminar es " + cargaArchivosOaDTO.getId());
		// GUSTAVO --BitacoraUtil.llenarBitacora(cargaArchivosOaDTO,
		// getUsuarioEnSession(), ConstantesBitacora.CARGA_ARCHIVO_OA_ELIMINAR,
		// request);
		ResultadoDTO<CargaArchivosOaDTO> resultadoDto = cargaArchivosOaService.eliminar(cargaArchivosOaDTO);
		logger.info("La transaccion para borrar archivo fue " + resultadoDto.getResultado());

	}

	private void procesarUnidadOaAva(UnidadOaAvaDTO unidadOaAvaDTO, String nombreTipoConfirmacion) {

		if (this.validarTipoConfirmacion(nombreTipoConfirmacion, unidadOaAvaDTO)) {

			this.validaElementoFuncionalidadConfirmable(unidadOaAvaDTO, ambienteVirtualAprendizajeDTO);
			this.validaAVAConfirmable(unidadOaAvaDTOs);
			this.calculaAvanceXUnidad(unidadOaAvaDTO);
			this.calculaAvanceAva(ambienteVirtualAprendizajeDTO, unidadOaAvaDTOs);

			ResultadoDTO<UnidadOaAvaDTO> resultadoActualizacion = this
					.actualizarUnidadYAmbientevirtualAprendizaje(unidadOaAvaDTO, ambienteVirtualAprendizajeDTO);

			if (resultadoActualizacion.getResultado().equals(ResultadoTransaccionEnum.EXITOSO)) {
				this.validaElementosConfirmables(unidadOaAvaDTO);
				this.enviaMensajeValidacion(nombreTipoConfirmacion, unidadOaAvaDTO, Boolean.TRUE);

			} else {
				this.limpiarcheckBox(nombreTipoConfirmacion, unidadOaAvaDTO);
				this.calculaAvanceXUnidad(unidadOaAvaDTO);
				this.calculaAvanceAva(ambienteVirtualAprendizajeDTO, unidadOaAvaDTOs);

			}

		} else {
			logger.error("La confirmacion es invalida ya que no se tienen permisos suficientes");
		}

	}

	private void limpiarcheckBox(String nombreTipoConfirmacion, UnidadOaAvaDTO unidadOaAvaDTO) {

		unidadOaAvaDTO.setIsFuncionalidadConfirmable(Boolean.FALSE);
		switch (nombreTipoConfirmacion) {

		case "Guión instruccional":
			unidadOaAvaDTO.setValidacionGuionInst(Boolean.FALSE);

			break;
		case "Desarrollo OA":
			unidadOaAvaDTO.setValidacionDesarrolloOa(Boolean.FALSE);

			break;

		case "SCORM":
			unidadOaAvaDTO.setValidacionScorm(Boolean.FALSE);

			break;

		case "Valida Funcionalidad":
			unidadOaAvaDTO.setFuncionalidad(Boolean.FALSE);
			isAvaConfirmable = Boolean.TRUE;
			unidadOaAvaDTO.setIsFuncionalidadConfirmable(Boolean.TRUE);
			break;

		default:
			logger.error("limpiarcheckBox:Tipo archivo no encontrado archivo es " + nombreTipoConfirmacion);
		}

	}

	private void enviaMensajeValidacion(String nombreTipoConfirmacion, UnidadOaAvaDTO unidadOaAvaDTO,
			Boolean enviarMensajeACAYRepProd) {

		Set<Long> idPersonasList = null;

		String nombreEventoCapacitacion = ambienteVirtualAprendizajeDTO.getEventoCapacitacion().getNombreEc();
		String nombreUnidadOaAva = unidadOaAvaDTO.getDetEstUnidadDidactica().getNombreUnidad();

		idPersonasList = this.obtenerIdPersonasDestinatarios(unidadOaAvaDTO, enviarMensajeACAYRepProd);

		switch (nombreTipoConfirmacion) {
		case "Valida Funcionalidad":
			logger.info("Se enviara mensaje por la validacion de la funcionalidad del OA");
			this.enviarMensajeFuncionalidadValidada(idPersonasList, nombreEventoCapacitacion, nombreUnidadOaAva);
			break;

		case "Valida AVA":
			logger.info("Se enviara mensaje por la validacion del AVA");
			this.enviarMensajeAvaValidado(idPersonasList, nombreEventoCapacitacion);
			break;
		default:
			logger.info("Se enviara mensaje por la validacion del archivo " + nombreTipoConfirmacion);
			this.enviaMensajeArchivoValidado(nombreTipoConfirmacion, idPersonasList, nombreUnidadOaAva,
					nombreEventoCapacitacion);
			break;
		}

	}

	public Boolean validarTipoConfirmacion(String nombreTipoConfirmacion, UnidadOaAvaDTO unidadOaAvaDTO) {

		Boolean esConfirmacionValida = Boolean.FALSE;

		switch (nombreTipoConfirmacion) {

		case "Guión instruccional":
			/**
			 * Si el guion instruccional ya esta validado(marcado en el check
			 * box) ya no valida los permisos y no deja que se retire la
			 * validacion
			 **/
			if (unidadOaAvaDTO.getValidacionGuionInst()) {
				/** Valida que se tengan permisos para validar el checkbox **/
				if (validarConfirmacionGuionInstitucional()) {
					esConfirmacionValida = Boolean.TRUE;
				}
			} else {
				unidadOaAvaDTO.setValidacionGuionInst(Boolean.TRUE);
			}
			break;
		case "Desarrollo OA":
			/**
			 * Si el desarrollo OA ya esta validado(marcado en el check box) ya
			 * no valida los permisos y no deja que se retire la validacion
			 **/
			if (unidadOaAvaDTO.getValidacionDesarrolloOa()) {
				/**
				 * Valida que se tengan permisos para validar el checkbox de la
				 * validacion del scorm
				 **/
				if (validarConfirmacionDesarrolloOa()) {
					esConfirmacionValida = Boolean.TRUE;
				}
			} else {
				unidadOaAvaDTO.setValidacionDesarrolloOa(Boolean.TRUE);
			}
			break;

		case "SCORM":
			/**
			 * Si el scorm ya esta validado(marcado en el check box) ya no
			 * valida los permisos y no deja que se retire la validacion
			 **/
			if (unidadOaAvaDTO.getValidacionScorm()) {
				/**
				 * Valida que se tengan permisos para validar el checkbox de la
				 * validacion del Scorm
				 **/
				if (validarConfirmacionScorm()) {
					esConfirmacionValida = Boolean.TRUE;
				}
			} else {
				unidadOaAvaDTO.setValidacionScorm(Boolean.TRUE);
			}
			break;

		case "Valida Funcionalidad":
			/**
			 * Si el scorm ya esta validado(marcado en el check box) ya no
			 * valida los permisos y no deja que se retire la validacion
			 **/
			if (unidadOaAvaDTO.getFuncionalidad()) {
				/**
				 * Valida que se tengan permisos para validar el checkbox de la
				 * validacion del Scorm
				 **/
				if (validarConfirmacionUnidadOA()) {
					esConfirmacionValida = Boolean.TRUE;
				}
			} else {
				unidadOaAvaDTO.setFuncionalidad(Boolean.TRUE);
			}
			break;

		default:
			logger.error(
					"validarTipoArchivoConfirmacion:Tipo archivo no encontrado archivo es " + nombreTipoConfirmacion);
		}

		return esConfirmacionValida;
	}

	private void procesarAVA() {
		ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultadoActualizacionAva = null;
		this.calculaAvanceAva(ambienteVirtualAprendizajeDTO, unidadOaAvaDTOs);
		resultadoActualizacionAva = this.actualizarAva(ambienteVirtualAprendizajeDTO);

		if (ResultadoTransaccionEnum.EXITOSO.equals(resultadoActualizacionAva.getResultado())) {
			this.validaAVAConfirmable(unidadOaAvaDTOs);

			/** Envia Mensaje **/
			this.enviaMensajeValidacionAva(ambienteVirtualAprendizajeDTO, unidadOaAvaDTOs);
		} else {
			isAvaConfirmable = Boolean.FALSE;
			ambienteVirtualAprendizajeDTO.setValidacionAva(Boolean.FALSE);

		}

	}

	private void enviaMensajeValidacionAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO,
			List<UnidadOaAvaDTO> unidadOaAvaDTOs) {
		Boolean enviarMensajeACAYRepProd = Boolean.TRUE;

		for (UnidadOaAvaDTO unidadOaAvaDTO : unidadOaAvaDTOs) {
			this.enviaMensajeValidacion("Valida AVA", unidadOaAvaDTO, enviarMensajeACAYRepProd);
			enviarMensajeACAYRepProd = Boolean.FALSE;
		}

	}

	private ResultadoDTO<UnidadOaAvaDTO> actualizarUnidadYAmbientevirtualAprendizaje(UnidadOaAvaDTO unidadOaAvaDTO,
			AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		ResultadoDTO<UnidadOaAvaDTO> resultadoActualizacion = new ResultadoDTO<UnidadOaAvaDTO>();
		;
		try {

			unidadOaAvaDTO.setFechaActualizacion(new Date());
			unidadOaAvaDTO.setUsuarioModifico(BigInteger.valueOf(usuarioSessionDTO.getIdPersona()));

			ambienteVirtualAprendizajeDTO.setFechaActualizacion(new Date());
			ambienteVirtualAprendizajeDTO.setUsuarioModifico(usuarioSessionDTO.getIdPersona());

			resultadoActualizacion = relUnidadOaAvaService.actualizarUnidadYAmbientevirtualAprendizaje(unidadOaAvaDTO,
					ambienteVirtualAprendizajeDTO);
		} catch (Exception e) {
			resultadoActualizacion.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
			resultadoActualizacion.setResultado(ResultadoTransaccionEnum.FALLIDO);

		}
		this.validaMensajeResultadoTransaccion(resultadoActualizacion.getMensajes(),
				resultadoActualizacion.getResultado());

		logger.info("El resultado de la actualizacion fue " + resultadoActualizacion.getResultado());

		return resultadoActualizacion;

	}

	private ResultadoDTO<AmbienteVirtualAprendizajeDTO> actualizarAva(
			AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		logger.info("Actualizara el AVA");

		ambienteVirtualAprendizajeDTO.setFechaActualizacion(new Date());
		ambienteVirtualAprendizajeDTO.setUsuarioModifico(usuarioSessionDTO.getIdPersona());
		// GUSTAVO --BitacoraUtil.llenarBitacora(ambienteVirtualAprendizajeDTO,
		// getUsuarioEnSession(), ConstantesBitacora.AVA_EDITAR, request);
		ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado =

				ambienteVirtualApService.actualizar(ambienteVirtualAprendizajeDTO);

		this.validaMensajeResultadoTransaccion(resultado.getMensajes(), resultado.getResultado());

		logger.info("El resultado de la actualizacion del AVA fue " + resultado.getResultado());
		return resultado;
	}

	public void descargaArchivos(CargaArchivosOaDTO cargaArchivosOaDTO) {
		try {
			logger.info("checj 1");
			if (this.validarCargaDescargaArchivo(clasificacionArchivoSeleccionado.getNombre(), 2,
					objetoAprendizajeSeleccionado)) {
				logger.info("El archivo a descarga es :"
						+ cargaArchivosOaDTO.getDirectorio().concat(cargaArchivosOaDTO.getIdArchivo()));

				/*
				 * InputStream stream =
				 * FacesContext.getCurrentInstance().getExternalContext().
				 * getResourceAsStream(cargaArchivosOaDTO.getDirectorio().concat
				 * (cargaArchivosOaDTO.getIdArchivo())); fileDownload = new
				 * DefaultStreamedContent(stream, "image/jpg",
				 * "downloaded_optimus.jpg");
				 */
				File file = new File(cargaArchivosOaDTO.getDirectorio().concat(cargaArchivosOaDTO.getIdArchivo()));

				InputStream stream = new FileInputStream(file);

				fileDownload = new DefaultStreamedContent(stream, ConstantesGestorWeb.HTTP_HEADER_CONTENTTYPE_ZIP,
						cargaArchivosOaDTO.getNombreArchivo());
			} else {
				logger.error("Sin permisos suficientes para descargar el archivo");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("Error en la descarga del archivo ");
		}

	}

	private void calculaAvanceXListaUnidades(List<UnidadOaAvaDTO> unidadOaAvaDTOs) {
		for (UnidadOaAvaDTO unidadOaAvaDTO : unidadOaAvaDTOs) {
			calculaAvanceXUnidad(unidadOaAvaDTO);
		}
	}

	public void actualizaUnidadSeleccionada(UnidadOaAvaDTO unidadOaAvaDTO, String tipoConfirmacion) {
		this.procesarUnidadOaAva(unidadOaAvaDTO, tipoConfirmacion);
	}

	public void actualizarAVASeleccionado() {

		if (this.validarConfirmacionAva(ambienteVirtualAprendizajeDTO)) {
			this.procesarAVA();
		}

	}

	private Boolean validarConfirmacionAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		Boolean esConfirmacionValida = Boolean.FALSE;
		/**
		 * Si el AVA ya esta validado(marcado en el check box) ya no valida los
		 * permisos y no deja que se retire la validacion
		 **/
		if (ambienteVirtualAprendizajeDTO.getValidacionAva()) {
			/**
			 * Valida que se tengan permisos para validar el checkbox de la
			 * validacion del Scorm
			 **/
			if (this.validarConfirmacionAVA()) {
				esConfirmacionValida = Boolean.TRUE;
			}
		} else {
			ambienteVirtualAprendizajeDTO.setValidacionAva(Boolean.TRUE);
		}
		return esConfirmacionValida;
	}

	/**
	 * Metodo que calcula el porcentaje de avance de un unidadOaAva dependiendo
	 * de los elementos que ya se hayan validado
	 * 
	 * @param unidadOaAvaDTO
	 */
	private void calculaAvanceXUnidad(UnidadOaAvaDTO unidadOaAvaDTO) {

		Integer porcentajeAvance = 0;
		Integer veinticinco = 25;

		if (ObjectUtils.isNotNull(unidadOaAvaDTO.getValidacionDesarrolloOa())
				&& unidadOaAvaDTO.getValidacionDesarrolloOa()) {
			porcentajeAvance = porcentajeAvance + veinticinco;
		}

		if (ObjectUtils.isNotNull(unidadOaAvaDTO.getValidacionGuionInst()) && unidadOaAvaDTO.getValidacionGuionInst()) {
			porcentajeAvance = porcentajeAvance + veinticinco;
		}

		if (ObjectUtils.isNotNull(unidadOaAvaDTO.getValidacionScorm()) && unidadOaAvaDTO.getValidacionScorm()) {
			porcentajeAvance = porcentajeAvance + veinticinco;
		}

		if (ObjectUtils.isNotNull(unidadOaAvaDTO.getFuncionalidad()) && unidadOaAvaDTO.getFuncionalidad()) {
			porcentajeAvance = porcentajeAvance + veinticinco;
		}

		unidadOaAvaDTO.setPorcentajeAvanceOa(porcentajeAvance.byteValue());
		porcentajeAvance = 0;
	}

	private void calculaAvanceAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO,
			List<UnidadOaAvaDTO> unidadOaAvaDTOs) {

		Integer numeroUnidades = unidadOaAvaDTOs.size();
		/* Se realiza la suma de mas uno por la validacion del AVA */
		numeroUnidades++;
		Integer totalAvanceUnidades = 0;
		Integer numeroCien = 100;
		Integer resultado;
		numeroUnidades = numeroUnidades * numeroCien;

		for (UnidadOaAvaDTO unidadOaAvaDTO : unidadOaAvaDTOs) {
			totalAvanceUnidades = unidadOaAvaDTO.getPorcentajeAvanceOa() + totalAvanceUnidades;
		}

		if (ambienteVirtualAprendizajeDTO.getValidacionAva()) {

			totalAvanceUnidades = totalAvanceUnidades + numeroCien;

		}

		resultado =

				(totalAvanceUnidades * numeroCien) / numeroUnidades;

		if (resultado > numeroCien) {
			logger.info("Ocurrio un error logico, no puede ser mas del 100 %");
		}

		ambienteVirtualAprendizajeDTO.setPorcentajeAvance(resultado.byteValue());
		numeroUnidades = 0;
	}
	
	public String activacionAva(){
		return activarAva(ambienteVirtualAprendizajeDTO);
	}
	
	public String activarAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {
		logger.info(("Activara el AVA " + ambienteVirtualAprendizajeDTO.getId()));

		CatalogoComunDTO estatusAvaActivo = obtenerEstatusAva(EstatusAmbienteVirtualAprendizajeEnum.ACTIVO.getId(),
				estadoAvaList);

		CatalogoComunDTO edoEvtCapEnEjecucion = obtenerEdoEventoCapacitacionPorEnum(EstadoEventoCapEnum.EN_EJECUCION);

		ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado = ambienteVirtualApService
				.activaAvaEjecutaEventoCapacitacion(ambienteVirtualAprendizajeDTO, usuarioSessionDTO.getIdPersona(),
						estatusAvaActivo, edoEvtCapEnEjecucion);

		if (resultado.esCorrecto()) {
			try {
				this.mostrarCursoLms(ambienteVirtualAprendizajeDTO);
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ACT_AVA", String.valueOf(resultado.getDto().getId()),
						requestActual(), TipoServicioEnum.LOCAL);
			} catch (ErrorWS e) {
				e.printStackTrace();
				logger.error("Ocurrio un error al mostrar el curso en LMS");
				resultado.agregaMensaje("Ocurrio un error al mostrar el curso en el LMS");
			}

		}

		this.validaMensajeResultadoTransaccion(resultado.getMensajes(), resultado.getResultado());
		logger.info("El resultado de la actualizacion del AVA fue " + resultado.getResultado());
		return ConstantesGestorWeb.NAVEGA_AMBIENTES_VIRTUALES_APRENDIZAJE;
	}

	private CatalogoComunDTO obtenerEdoEventoCapacitacionPorEnum(EstadoEventoCapEnum estadoEventoCapEnum) {

		CatalogoComunDTO eventoCapacitacionEstadoEjecucion = null;

		for (CatalogoComunDTO edoEventoCap : catEstadoEventoCapacitacion) {
			if (estadoEventoCapEnum.getId().equals(edoEventoCap.getId())) {
				eventoCapacitacionEstadoEjecucion = edoEventoCap;

			}
		}

		return eventoCapacitacionEstadoEjecucion;
	}

	private CatalogoComunDTO obtenerEstatusAva(Integer idEstatus, List<CatalogoComunDTO> estadoAvaList) {
		CatalogoComunDTO estatusAva = null;

		for (CatalogoComunDTO estadoAva : estadoAvaList) {
			if (estadoAva.getId().equals(idEstatus)) {
				estatusAva = estadoAva;
				logger.info("Es estatus " + idEstatus + " tiene el id " + estatusAva.getId());
				break;
			}
		}
		return estatusAva;
	}

	public String navegarPantallaprincipal() {

		return ConstantesGestorWeb.NAVEGA_AMBIENTES_VIRTUALES_APRENDIZAJE;
	}

	public String navegarAUrlExterna() throws IOException {

		logger.info("El id del evento de capacitacion es "
				+ ambienteVirtualAprendizajeDTO.getEventoCapacitacion().getIdEvento());

		String urlLms =

				ambienteVirtualApService.obtenerUrlCursoLms(usuarioSessionDTO.getIdPersona(),
						ambienteVirtualAprendizajeDTO.getIdCursoLms(),
						ambienteVirtualAprendizajeDTO.getPlataformaMoodle());

		logger.info("La url obtenida es :" + urlLms);

		logger.info("Redireccionara a la url " + urlLms);

		return urlLms;
	}

	public String pintarFechaInicial(AmbienteVirtualAprendizajeDTO ava) {
		String colorFechaInicial = null;

		if (this.mostrarTextoFechaInicialProxima(ava)) {
			colorFechaInicial = "textoRojo";

		}
		return colorFechaInicial;
	}

	/**
	 * Metodo que establese si la leyenda fecha inicial del evento de
	 * capacitacion esta proxima debe aparecer o no
	 */

	public Boolean mostrarTextoFechaInicialProxima(AmbienteVirtualAprendizajeDTO ava) {

		Long diferencia = null;
		Boolean esFechaProxima = Boolean.FALSE;
		Date fechaActual = new Date();
		Date fechaInicialEventoCap = ava.getEventoCapacitacion().getFechaInicial();
		diferencia = obtenerDiferenciaDeDias(fechaInicialEventoCap, fechaActual);

		if (diferencia < 5) {
			esFechaProxima = Boolean.TRUE;
		}

		return esFechaProxima;
	}

	private Long obtenerDiferenciaDeDias(Date fechaInicial, Date fechaFinal) {
		Long diferencia = null;

		diferencia = (fechaInicial.getTime() - fechaFinal.getTime()) / ConstantesGestorWeb.MILISEGUNDOS_POR_DIA;

		return diferencia;
	}

	public String asignaColorSemaro(AmbienteVirtualAprendizajeDTO ava) {
		return ambienteVirtualApService.asignaColorSemaro(ava);
	}

	public void mostrarCursoLms(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) throws ErrorWS {
		CursoWS cursoWS = new CursoWS(ambienteVirtualAprendizajeDTO.getPlataformaMoodle());
		cursoWS.mostrarCurso(ambienteVirtualAprendizajeDTO.getIdCursoLms());

	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * 
	 * Setter y getter
	 */

	public AmbienteVirtualApService getAmbienteVirtualApService() {
		return ambienteVirtualApService;
	}

	public void setAmbienteVirtualApService(AmbienteVirtualApService ambienteVirtualApService) {
		this.ambienteVirtualApService = ambienteVirtualApService;
	}

	public AmbienteVirtualAprendizajeDTO getAmbienteVirtualAprendizajeDTO() {
		return ambienteVirtualAprendizajeDTO;
	}

	public void setAmbienteVirtualAprendizajeDTO(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {
		this.ambienteVirtualAprendizajeDTO = ambienteVirtualAprendizajeDTO;
	}

	public RelUnidadOaAvaService getRelUnidadOaAvaService() {
		return relUnidadOaAvaService;
	}

	public void setRelUnidadOaAvaService(RelUnidadOaAvaService relUnidadOaAvaService) {
		this.relUnidadOaAvaService = relUnidadOaAvaService;
	}

	public List<UnidadOaAvaDTO> getUnidadOaAvaDTOs() {
		return unidadOaAvaDTOs;
	}

	public void setUnidadOaAvaDTOs(List<UnidadOaAvaDTO> unidadOaAvaDTOs) {
		this.unidadOaAvaDTOs = unidadOaAvaDTOs;
	}

	public List<ReponsableProduccionOaDTO> getReponsableProduccionOaDTOs() {
		return reponsableProduccionOaDTOs;
	}

	public void setReponsableProduccionOaDTOs(List<ReponsableProduccionOaDTO> reponsableProduccionOaDTOs) {
		this.reponsableProduccionOaDTOs = reponsableProduccionOaDTOs;
	}

	public ReponsableProduccionEcDTO getCoordinadorAcademico() {
		return coordinadorAcademico;
	}

	public void setCoordinadorAcademico(ReponsableProduccionEcDTO coordinadorAcademico) {
		this.coordinadorAcademico = coordinadorAcademico;
	}

	public UnidadOaAvaDTO getObjetoAprendizajeSeleccionado() {
		return objetoAprendizajeSeleccionado;
	}

	public void setObjetoAprendizajeSeleccionado(UnidadOaAvaDTO objetoAprendizajeSeleccionado) {
		this.objetoAprendizajeSeleccionado = objetoAprendizajeSeleccionado;
	}

	public RecursosOaDTO getRecursosOaDTO() {
		return recursosOaDTO;
	}

	public void setRecursosOaDTO(RecursosOaDTO recursosOaDTO) {
		this.recursosOaDTO = recursosOaDTO;
	}

	public List<RecursosOaDTO> getRecursosOaDTOs() {
		return recursosOaDTOs;
	}

	public void setRecursosOaDTOs(List<RecursosOaDTO> recursosOaDTOs) {
		this.recursosOaDTOs = recursosOaDTOs;
	}

	public CatalogoComunDTO getCatTemaRecurso() {
		return catTemaRecurso;
	}

	public void setCatTemaRecurso(CatalogoComunDTO catTemaRecurso) {
		this.catTemaRecurso = catTemaRecurso;
	}

	public String getTituloMdlCargaArchivo() {
		return tituloMdlCargaArchivo;
	}

	public void setTituloMdlCargaArchivo(String tituloMdlCargaArchivo) {
		this.tituloMdlCargaArchivo = tituloMdlCargaArchivo;
	}

	public CargaArchivosOaService getCargaArchivosOaService() {
		return cargaArchivosOaService;
	}

	public void setCargaArchivosOaService(CargaArchivosOaService cargaArchivosOaService) {
		this.cargaArchivosOaService = cargaArchivosOaService;
	}

	public List<CargaArchivosOaDTO> getArchivosCargados() {
		return archivosCargados;
	}

	public void setArchivosCargados(List<CargaArchivosOaDTO> archivosCargados) {
		this.archivosCargados = archivosCargados;
	}

	public StreamedContent getFileDownload() {
		return fileDownload;
	}

	public void setFileDownload(StreamedContent fileDownload) {
		this.fileDownload = fileDownload;
	}

	public UploadedFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(UploadedFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	public List<CatalogoComunDTO> getCatClasificacionArchivos() {
		return catClasificacionArchivos;
	}

	public void setCatClasificacionArchivos(List<CatalogoComunDTO> catClasificacionArchivos) {
		this.catClasificacionArchivos = catClasificacionArchivos;
	}

	public CatalogoComunDTO getClasificacionArchivoSeleccionado() {
		return clasificacionArchivoSeleccionado;
	}

	public void setClasificacionArchivoSeleccionado(CatalogoComunDTO clasificacionArchivoSeleccionado) {
		this.clasificacionArchivoSeleccionado = clasificacionArchivoSeleccionado;
	}

	public Boolean getIsAvaConfirmable() {
		return isAvaConfirmable;
	}

	public void setIsAvaConfirmable(Boolean isAvaConfirmable) {
		this.isAvaConfirmable = isAvaConfirmable;
	}

	public Boolean getBotonCargaEsActivo() {
		return botonCargaEsActivo;
	}

	public void setBotonCargaEsActivo(Boolean botonCargaEsActivo) {
		this.botonCargaEsActivo = botonCargaEsActivo;
	}

	public Boolean getBotonDescargaEsActivo() {
		return botonDescargaEsActivo;
	}

	public void setBotonDescargaEsActivo(Boolean botonDescargaEsActivo) {
		this.botonDescargaEsActivo = botonDescargaEsActivo;
	}

	public NotificacionSistemaService getNotificacionSistemaService() {
		return notificacionSistemaService;
	}

	public void setNotificacionSistemaService(NotificacionSistemaService notificacionSistemaService) {
		this.notificacionSistemaService = notificacionSistemaService;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public GestionAprendizajeServiceAdapter getGestionAprendizajeServiceAdapter() {
		return gestionAprendizajeServiceAdapter;
	}

	public void setGestionAprendizajeServiceAdapter(GestionAprendizajeServiceAdapter gestionAprendizajeServiceAdapter) {
		this.gestionAprendizajeServiceAdapter = gestionAprendizajeServiceAdapter;
	}

	public String getTipoCompetencia() {
		return tipoCompetencia;
	}

	public void setTipoCompetencia(String tipoCompetencia) {
		this.tipoCompetencia = tipoCompetencia;
	}

	public String getEjeCapacitacion() {
		return ejeCapacitacion;
	}

	public void setEjeCapacitacion(String ejeCapacitacion) {
		this.ejeCapacitacion = ejeCapacitacion;
	}

	public String getPesoMaximoArchivo() {
		return pesoMaximoArchivo;
	}

	public void setPesoMaximoArchivo(String pesoMaximoArchivo) {
		this.pesoMaximoArchivo = pesoMaximoArchivo;
	}

	public String getExtensionValida() {
		return extensionValida;
	}

	public void setExtensionValida(String extensionValida) {
		this.extensionValida = extensionValida;
	}

	public String getMensajeExtensionInvalida() {
		return mensajeExtensionInvalida;
	}

	public void setMensajeExtensionInvalida(String mensajeExtensionInvalida) {
		this.mensajeExtensionInvalida = mensajeExtensionInvalida;
	}

	public String getMensajePesoIncorrecto() {
		return mensajePesoIncorrecto;
	}

	public void setMensajePesoIncorrecto(String mensajePesoIncorrecto) {
		this.mensajePesoIncorrecto = mensajePesoIncorrecto;
	}

	public ReponsableProduccionEcDTO getResponsableProduccion() {
		return responsableProduccion;
	}

	public void setResponsableProduccion(ReponsableProduccionEcDTO responsableProduccion) {
		this.responsableProduccion = responsableProduccion;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}
}
