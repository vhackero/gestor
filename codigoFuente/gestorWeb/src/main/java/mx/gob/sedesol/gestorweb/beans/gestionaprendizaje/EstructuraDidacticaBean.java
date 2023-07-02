package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.RecursosOaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.SubtemasUDidacticaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.RecursosOaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RecursosOaSerivce;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.ActividadesRecursosWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ManagedBean
@ViewScoped
public class EstructuraDidacticaBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(EstructuraDidacticaBean.class);
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Inyeccion de recursosOaSerivce
	 */
	@ManagedProperty(value = "#{recursosOaSerivce}")
	private RecursosOaSerivce recursosOaSerivce;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	/**
	 * Inyeccion del servicio de sistema
	 */
	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	private UnidadOaAvaDTO objetoAprendizajeSeleccionado;

	private AmbienteVirtualAprendizajeDTO ava;

	private RecursosOaDTO recursoSeleccionado;

	private List<RecursosOaDTO> recursosOaDTOs;

	private List<CatalogoComunDTO> catTemaRecursos;

	private String tipoTituloRecurso;

	private String origenDatos = "0";

	private TreeNode root;

	private Boolean habilitarElementosDeLaVista = Boolean.FALSE;

	private Boolean habilitarSeccionOrigenDatos = Boolean.FALSE;

	public Boolean getHabilitarSeccionOrigenDatos() {
		return habilitarSeccionOrigenDatos;
	}

	public void setHabilitarSeccionOrigenDatos(Boolean habilitarSeccionOrigenDatos) {
		this.habilitarSeccionOrigenDatos = habilitarSeccionOrigenDatos;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {

		recursoSeleccionado = new RecursosOaDTO();
		CatalogoComunDTO catTemaRecurso = new CatalogoComunDTO();

		recursoSeleccionado.setCatTemaRecurso(catTemaRecurso);

		tipoTituloRecurso = "Instrucciones";

		recursoSeleccionado.setInstruccionesContenido("Estas son las instrucciones del contenido");
		logger.info("Esta creando el bean EstructuraDidacticaBean");

		/**
		 * Se carga el catalogo CAT_TEMA_RECURSO*
		 */
		catTemaRecursos = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_TEMA_RECURSO);

	}

	/**
	 * Metodo que busca los recursos apartir de la unidadOaAvaDTO proporcionada.
	 *
	 * @param unidadOaAvaDTO
	 */
	public void crearObjetoRecursosOaDTO(UnidadOaAvaDTO unidadOaAvaDTO, AmbienteVirtualAprendizajeDTO ava) {

		/**
		 * Asigna el objeto de aprendizaje seleccionado
		 */
		objetoAprendizajeSeleccionado = unidadOaAvaDTO;
		this.ava = ava;
		this.armaArbol(unidadOaAvaDTO);

		/* Crea objeto de recursos */
		recursoSeleccionado = inicializaRecursoOaDto(objetoAprendizajeSeleccionado);
		recursosOaDTOs = recursosOaSerivce.buscarRecursosOasPorUnidadOaAva(unidadOaAvaDTO);

		logger.info("El tamaño de la lista es " + recursosOaDTOs.size());
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

	/**
	 * Metodo que borra el objeto recurso que se pasa como parametro
	 *
	 * @param recursosOaDTO
	 */
	public void borrarObjetoRecurso(RecursosOaDTO recursosOaDTO) {

		Integer idRecurso = recursosOaDTO.getId().intValue();
		logger.info("Borrara el recurso con el id " + recursosOaDTO.getIdCatTemaR() + " - "
				+ recursosOaDTO.getIdUObjetoAprendizaje());

		ResultadoDTO<RecursosOaDTO> resultado = recursosOaSerivce.eliminarRecurso(recursosOaDTO, ava);
		this.validaMensajeResultadoTransaccion(resultado.getMensajes(), resultado.getResultado());
		logger.info("El resultado de la transaccion de eliminar un recurso fue " + resultado.getResultado());

		if (resultado.getResultado().equals(ResultadoTransaccionEnum.EXITOSO)) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ELI_REC_UNI_DID", String.valueOf(idRecurso),
					requestActual(), TipoServicioEnum.LOCAL);
			this.borraRecursoDeLaLista(recursosOaDTO);

		}

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

	private RecursosOaDTO inicializaRecursoOaDto(UnidadOaAvaDTO unidadOaAvaDTO) {
		RecursosOaDTO recursosOaDTO = new RecursosOaDTO();
		recursosOaDTO.setUnidadOaAva(unidadOaAvaDTO);

		return recursosOaDTO;

	}

	/**
	 * Metodo que servirve para persistir el objeto RecursosOaDTO proporcionado
	 *
	 * @return
	 */
	public void guardaObjetoRecurso() {
		ResultadoDTO<RecursosOaDTO> resultado = null;
		logger.info("Va a guardar el recurso " + recursoSeleccionado.getNombre());

		recursoSeleccionado.setUsuarioModifico(BigInteger.valueOf(getUsuarioEnSession().getIdPersona()));
		recursoSeleccionado.setFechaActualizacion(new Date());
		recursoSeleccionado.setFechaRegistro(new Date());

		logger.info("El tipo de recurso de este objeto es " + recursoSeleccionado.getCatTemaRecurso().getNombre());

		resultado = recursosOaSerivce.guardarRecurso(recursoSeleccionado, ava);

		if (resultado.esCorrecto()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ASG_REC_UNI_DID",
					String.valueOf(resultado.getDto().getId()), requestActual(), TipoServicioEnum.LOCAL);
			this.anadeElementoPersistidoAListaRecursos(resultado.getDto());
		}

		habilitarElementosDeLaVista = Boolean.FALSE;
		habilitarSeccionOrigenDatos = Boolean.FALSE;

		recursoSeleccionado = inicializaRecursoOaDto(objetoAprendizajeSeleccionado);

	}

	public void reestablecerValores() {
		habilitarElementosDeLaVista = Boolean.FALSE;
		habilitarSeccionOrigenDatos = Boolean.FALSE;
		recursoSeleccionado = inicializaRecursoOaDto(objetoAprendizajeSeleccionado);

	}

	private Boolean borrarRecursoLms(AmbienteVirtualAprendizajeDTO ava, RecursosOaDTO recursosOaDTO) {

		Boolean resultado = Boolean.FALSE;
		ActividadesRecursosWS actividadesRecursosWs = new ActividadesRecursosWS(ava.getPlataformaMoodle());

		try {
			if (RecursosOaEnum.TEMA_DISCUCION.getValor().equals(recursosOaDTO.getNombre())) {
				resultado = actividadesRecursosWs.eliminarDiscucionForo(recursosOaDTO.getIdRecursoLms());
			} else {
				resultado = actividadesRecursosWs.eliminarRecurso(recursosOaDTO.getIdRecursoLms());

			}
		} catch (ErrorWS e) {
			e.printStackTrace();
			logger.error("Fallo al invocacion del WS borra recuso");
		}

		return resultado;
	}

	private Integer anadeRecursoLms(RecursosOaEnum recursosOaEnum, AmbienteVirtualAprendizajeDTO ava,
			RecursosOaDTO recursosOaDTO) throws ErrorWS {

		ActividadesRecursosWS actividadesRecursosWs = new ActividadesRecursosWS(ava.getPlataformaMoodle());

		Integer idCurso = ava.getIdCursoLms();
		Integer seccion = 0;
		String nombre = recursosOaDTO.getNombre();
		String descripcionHTML = recursosOaDTO.getInstruccionesContenido();
		Integer idRecursoLms = 0;

		logger.info(recursosOaEnum.getValor());

		switch (recursosOaEnum) {
		case SCORM:
			idRecursoLms = actividadesRecursosWs.crearSCORM(idCurso, seccion, nombre, descripcionHTML);
			break;

		case TEMA_DISCUCION:
			/**
			 * Como se selecciona el foro a el que se le quiere anadir el tema
			 * de discucion*
			 */
			/*
			 * List<Foro> forosMoodle =
			 * actividadesRecursosWs.obtenerForos(idCurso);
			 */
			idRecursoLms = actividadesRecursosWs.crearDiscucionForo(220, nombre, descripcionHTML);
			break;

		case FORO:
			idRecursoLms = actividadesRecursosWs.crearForo(idCurso, seccion, nombre, descripcionHTML);
			break;

		default:
			logger.info("Recurso no encontrado");
			break;
		}

		return idRecursoLms;
	}

	private void anadeElementoPersistidoAListaRecursos(RecursosOaDTO recursosOaDTO) {

		logger.info("El tamanio de la lista es " + recursosOaDTOs.size());
		recursosOaDTOs.add(recursosOaDTO);
		logger.info("El tamanio de la lista despues de anadir el objeto es" + " " + recursosOaDTOs.size());

	}

	private void borraRecursoDeLaLista(RecursosOaDTO recursosOaDTO) {

		Iterator<RecursosOaDTO> i = recursosOaDTOs.iterator();
		while (i.hasNext()) {
			RecursosOaDTO recursosOaDTOTmp = i.next();
			if (recursosOaDTOTmp.getId().equals(recursosOaDTO.getId())) {
				i.remove();
				break;
			}

		}
		logger.info("");

	}

	private void obtenerTipoRecurso(RecursosOaDTO recursosOaDTO) {

		for (CatalogoComunDTO catalogoComunDTO : catTemaRecursos) {
			if (catalogoComunDTO.getId().equals(recursosOaDTO.getIdCatTemaR())) {
				recursosOaDTO.setCatTemaRecurso(catalogoComunDTO);
				logger.info("tipo recurso encontrado " + catalogoComunDTO.getNombre());
				break;
			}
		}

	}

	public void onChangeAsignaTituloRecurso() {

		if (ObjectUtils.isNotNull(recursoSeleccionado.getIdCatTemaR())) {
			habilitarElementosDeLaVista = Boolean.TRUE;
			habilitarSeccionOrigenDatos = Boolean.FALSE;
			this.obtenerTipoRecurso(recursoSeleccionado);

			if (ObjectUtils.isNotNull(recursoSeleccionado.getCatTemaRecurso())) {
				recursoSeleccionado.setNombre(null);
				recursoSeleccionado.setInstruccionesContenido(null);

				if (RecursosOaEnum.SCORM.getValor().equals(recursoSeleccionado.getCatTemaRecurso().getNombre())) {
					tipoTituloRecurso = "Descripcion";
					habilitarSeccionOrigenDatos = Boolean.TRUE;

					this.inicializaDatosOrigenDeDatos();

				}

			}
		} else {
			habilitarElementosDeLaVista = Boolean.FALSE;
			habilitarSeccionOrigenDatos = Boolean.FALSE;
		}
	}

	public void inicializaDatosOrigenDeDatos() {

		recursoSeleccionado.setNombre(objetoAprendizajeSeleccionado.getDetEstUnidadDidactica().getNombreUnidad());
		recursoSeleccionado.setInstruccionesContenido(
				objetoAprendizajeSeleccionado.getDetEstUnidadDidactica().getObjetivosEspecificos());

		if (!"0".equals(origenDatos)) {
			recursoSeleccionado.setNombre(null);
			recursoSeleccionado.setInstruccionesContenido(null);

		}
	}

	/**
	 * Setters y getters
	 *
	 * @return
	 */
	public UnidadOaAvaDTO getObjetoAprendizajeSeleccionado() {
		return objetoAprendizajeSeleccionado;
	}

	public void setObjetoAprendizajeSeleccionado(UnidadOaAvaDTO objetoAprendizajeSeleccionado) {
		this.objetoAprendizajeSeleccionado = objetoAprendizajeSeleccionado;
	}

	public RecursosOaSerivce getRecursosOaSerivce() {
		return recursosOaSerivce;
	}

	public void setRecursosOaSerivce(RecursosOaSerivce recursosOaSerivce) {
		this.recursosOaSerivce = recursosOaSerivce;
	}

	public RecursosOaDTO getRecursoSeleccionado() {
		return recursoSeleccionado;
	}

	public void setRecursoSeleccionado(RecursosOaDTO recursoSeleccionado) {
		this.recursoSeleccionado = recursoSeleccionado;
	}

	public List<RecursosOaDTO> getRecursosOaDTOs() {
		return recursosOaDTOs;
	}

	public void setRecursosOaDTOs(List<RecursosOaDTO> recursosOaDTOs) {
		this.recursosOaDTOs = recursosOaDTOs;
	}

	public List<CatalogoComunDTO> getCatTemaRecursos() {
		return catTemaRecursos;
	}

	public void setCatTemaRecursos(List<CatalogoComunDTO> catTemaRecursos) {
		this.catTemaRecursos = catTemaRecursos;
	}

	public String getTipoTituloRecurso() {
		return tipoTituloRecurso;
	}

	public void setTipoTituloRecurso(String tipoTituloRecurso) {
		this.tipoTituloRecurso = tipoTituloRecurso;
	}

	public String getOrigenDatos() {
		return origenDatos;
	}

	public void setOrigenDatos(String origenDatos) {
		this.origenDatos = origenDatos;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Boolean getHabilitarElementosDeLaVista() {
		return habilitarElementosDeLaVista;
	}

	public void setHabilitarElementosDeLaVista(Boolean habilitarElementosDeLaVista) {
		this.habilitarElementosDeLaVista = habilitarElementosDeLaVista;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

}
