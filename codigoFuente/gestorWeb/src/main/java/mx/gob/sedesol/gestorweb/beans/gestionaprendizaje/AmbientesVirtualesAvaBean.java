package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.UnidadOaAvaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatGestionAprendizajeEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.SemaforoEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoResponsabilidadEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatEstadoAva;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionSistemaService;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.admin.MensajeOperacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.AmbienteVirtualApService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelUnidadOaAvaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelReponsableProduccionEcService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.basegestor.springinit.GestionAprendizajeServiceAdapter;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CursoWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.beans.administracion.CorreoNotificacionBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.commons.utils.EstadoEventoCapEnum;
import mx.gob.sedesol.gestorweb.commons.utils.EstatusAmbienteVirtualAprendizajeEnum;
import mx.gob.sedesol.gestorweb.commons.utils.ModalidadEnum;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ManagedBean
@ViewScoped
public class AmbientesVirtualesAvaBean extends BaseBean {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5929433407465074144L;

	private static final Logger logger = Logger.getLogger(AmbientesVirtualesAvaBean.class);

	private TreeNode root;

	/**
	 * Inyeccion del ambientevirtuaApService
	 */
	@ManagedProperty(value = "#{ambienteVirtualApService}")
	private AmbienteVirtualApService ambienteVirtualApService;

	/**
	 * Inyeccion de gestion aprendizaje Service Adapter
	 */
	@ManagedProperty(value = "#{gestionAprendizajeServiceAdapter}")
	private GestionAprendizajeServiceAdapter gestionAprendizajeServiceAdapter;

	/**
	 * Inyeccion de eventoCapacitacionServiceFacade
	 */
	@ManagedProperty(value = "#{eventoCapacitacionServiceFacade}")
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	/**
	 * Inyeccion del servicio de sistema
	 */
	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	/**
	 * Inyeccion de relUnidadOaAvaService
	 */
	@ManagedProperty(value = "#{relUnidadOaAvaService}")
	private RelUnidadOaAvaService relUnidadOaAvaService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;
	/**
	 * Inyeccion de GrupoService
	 */
	@ManagedProperty(value = "#{grupoService}")
	private GrupoService grupoService;

	@ManagedProperty(value = "#{correoElectronicoService}")
	private CorreoElectronicoService correoElectronicoService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private ParametroSistemaService parametroSistemaService;

	@ManagedProperty(value = "#{notificacionSistemaService}")
	private NotificacionSistemaService notificacionSistemaService;

	@ManagedProperty("#{personaCorreoService}")
	private PersonaCorreoService personaCorreoService;

	@ManagedProperty("#{mensajeOperacionService}")
	private MensajeOperacionService mensajeOperacionService;

	@ManagedProperty("#{relReponsableProduccionEcService}")
	private RelReponsableProduccionEcService relReponsableProduccionEcService;

	@ManagedProperty("#{correoNotificacionBean}")
	private CorreoNotificacionBean correoNotificacionBean;

	private List<CatalogoComunDTO> estadoEventoCapList;

	private List<CatalogoComunDTO> modalidadEventoCapList;

	private List<CatalogoComunDTO> estadoAvaList;

	private List<AmbienteVirtualAprendizajeDTO> ambienteVirtualAprendizajeDTOList;

	private List<AmbienteVirtualAprendizajeDTO> avaFiltroBusquedaList;

	private CatalogoComunDTO estatusSeleccionado;

	private Integer idEstatusAva;

	private List<Integer> idModalidades;

	private Integer idEstatusEntCap;

	private List<CatalogoComunDTO> listaEjesCapacitacion;

	private List<CatalogoComunDTO> ejesCapacitacion;

	private EventoCapacitacionDTO filtro = new EventoCapacitacionDTO();

	private NodoeHijosDTO estPlanSedesol;

	private List<CatalogoComunDTO> listaTiposCompetencias;

	private String tipoDatoFechas;

	private Boolean esColumnaSemaroforoVisible = Boolean.FALSE;

	private Boolean esPanelSemaforoVisible = Boolean.FALSE;

	private Boolean esColumnaUnidadDidacticaVisible = Boolean.FALSE;

	private Boolean esColumnaCerrarActasVisible = Boolean.FALSE;

	private Boolean esFechaInicialYFinalHabilitada = Boolean.FALSE;

	private List<UnidadOaAvaDTO> unidadOaAvaDTOs;

	private Boolean esEjeDeCapacitacionEditable = Boolean.FALSE;

	private Boolean esNombreProgramaEditable = Boolean.FALSE;

	private Boolean esModalidadEditable = Boolean.FALSE;

	private Boolean esAvanceVisible = Boolean.FALSE;

	private Boolean esEstatusAvaVisible = Boolean.FALSE;

	private Map<Integer, Boolean> actasCerradasPorIdEvtCapMap;

	private UsuarioSessionDTO usuarioSessionDTO;
	
	
	//Subestructuras
		private List<CatalogoComunDTO> catPlanes;
		private List<CatalogoComunDTO> catEstructuras;
		private List<CatalogoComunDTO> catSubEstructurasNivel1;
		private List<CatalogoComunDTO> catSubEstructurasNivel2;
		private List<CatalogoComunDTO> catSubEstructurasNivel3;
		
		private Integer idCatPlan;
		private Integer idCatEstructura;
		private Integer idCatSubEstructuraNivel1;
		private Integer idCatSubEstructuraNivel2;
		private Integer idCatSubEstructuraNivel3;
		private Integer ejeCapacitacion;
		private List<NodoeHijosDTO> nodos;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		logger.info("Inicializando recursos de AmbientesVirtualesAvaBean");

		usuarioSessionDTO = this.getUsuarioEnSession();

		/** Obtiene el estatus del evento de capacitacion **/
		/** Se carga el catalogo CAT_CLASIFICACION_ARCHIVO_OA **/
		estadoEventoCapList = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_ESTADO_EVENTO_CAPACITACION);
		/** Obtiene la modalidad de los eventos de capacitacion **/
		modalidadEventoCapList = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG);
		/** Obtiene los estatus del AVA **/
		estadoAvaList = gestionAprendizajeServiceAdapter
				.getCatalogoServiceByGestionAprendizajeEnum(CatGestionAprendizajeEnum.CAT_ESTADO_AVA)
				.findAll(CatEstadoAva.class);

		idEstatusAva = this.obtenerObjEstatusAva(EstatusAmbienteVirtualAprendizajeEnum.EN_SOLICITUD, estadoAvaList)
				.getId();

		modalidadEventoCapList = eliminarModalidadPresencial(modalidadEventoCapList);
		idModalidades = this.obteneridModalidadEvCap(modalidadEventoCapList);

		idEstatusEntCap = this.obtenerEstatusEvtCap(EstadoEventoCapEnum.CALENDARIZADO, estadoEventoCapList);

		/** Obtiene los ambientes virtuales de aprendizaje **/

		ambienteVirtualAprendizajeDTOList = this.obtenerAmbientesvirtualAprendizaje(idEstatusAva, idModalidades,
				idEstatusEntCap);

		this.asignaResponsablesProduccion(ambienteVirtualAprendizajeDTOList);

		estatusSeleccionado = new CatalogoComunDTO();
		estatusSeleccionado.setId(idEstatusAva);

		esEstatusAvaVisible = Boolean.FALSE;

		this.generaEstructuraCatTpoCompetenciaPlan();
		this.generaCatEjesCapacitBusqueda();
		
		this.generaEstructuraCatPlanes();
	}

	private List<AmbienteVirtualAprendizajeDTO> obtenerAmbientesvirtualAprendizaje(Integer idEstatusAva,
			List<Integer> idModalidades, Integer idEstatusEntCap) {
		List<AmbienteVirtualAprendizajeDTO> ambienteVirtualAprendizajeDTOList = null;

		ambienteVirtualAprendizajeDTOList = ambienteVirtualApService
				.consultarAvaPorEstatusYEventoCapacitacion(idEstatusAva, idModalidades, idEstatusEntCap);
		return ambienteVirtualAprendizajeDTOList;

	}

	private void asignaResponsablesProduccion(List<AmbienteVirtualAprendizajeDTO> ambienteVirtualAprendizajeDTOList) {

		for (AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO : ambienteVirtualAprendizajeDTOList) {

			for (ReponsableProduccionEcDTO reponsableProduccionEcDTO : ambienteVirtualAprendizajeDTO
					.getEventoCapacitacion().getReponsableProduccionEcs()) {

				if (TipoResponsabilidadEnum.COORDINADOR_ACADEMICO.getId().equals(
						reponsableProduccionEcDTO.getPersonaResponsabilidad().getCatTipoResponsabilidadEc().getId())
						&& (reponsableProduccionEcDTO.getEsResponsablePrincipal())) {

					ambienteVirtualAprendizajeDTO.getEventoCapacitacion()
							.setResponsableCoordinadorAcademico(reponsableProduccionEcDTO);

				} else if (TipoResponsabilidadEnum.RESPONSABLE_DE_PRODUCCION.getId().equals(
						reponsableProduccionEcDTO.getPersonaResponsabilidad().getCatTipoResponsabilidadEc().getId())
						&& (reponsableProduccionEcDTO.getEsResponsablePrincipal())) {
					ambienteVirtualAprendizajeDTO.getEventoCapacitacion()
							.setResponsableProduccion(reponsableProduccionEcDTO);
				}

			}
		}
	}

	private Integer obtenerEstatusEvtCap(EstadoEventoCapEnum estatus, List<CatalogoComunDTO> estadoEventoCapList) {
		Integer idEstatusEvtCap = null;

		for (CatalogoComunDTO catalogoComunDTO : estadoEventoCapList) {
			if (estatus.getId().equals(catalogoComunDTO.getId())) {
				idEstatusEvtCap = catalogoComunDTO.getId();
				break;
			}
		}
		return idEstatusEvtCap;
	}

	private List<CatalogoComunDTO> eliminarModalidadPresencial(List<CatalogoComunDTO> modalidadEventoCapList) {

		List<CatalogoComunDTO> modalidadEventoCapListTemp = new ArrayList<CatalogoComunDTO>();

		modalidadEventoCapList.forEach(modalidadEc -> {
			if (!ModalidadEnum.PRESENCIAL.getId().equals(modalidadEc.getId())) {
				modalidadEventoCapListTemp.add(modalidadEc);
			}
		});

		return modalidadEventoCapListTemp;

	}

	private List<Integer> obteneridModalidadEvCap(List<CatalogoComunDTO> modalidadEventoCapList) {
		List<Integer> idsModalidadEvtCap = new ArrayList<Integer>();
		modalidadEventoCapList.forEach(modalidadEc -> {
			idsModalidadEvtCap.add(modalidadEc.getId());
		});
		return idsModalidadEvtCap;
	}

	public void porcesarAvaCambioEstatusConstruccion(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {
		Boolean avaTieneRespConstruccion = Boolean.FALSE;
		avaTieneRespConstruccion = this.validaSiAvaTieneRespProduccion(ambienteVirtualAprendizajeDTO);

		if (avaTieneRespConstruccion) {
			this.actualizaAvaEstatusConstruccion(ambienteVirtualAprendizajeDTO);
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EMP_CON_AVA",
					String.valueOf(ambienteVirtualAprendizajeDTO.getId()), requestActual(), TipoServicioEnum.LOCAL);
		} else {
			agregarMsgError("El AVA no tiene responsable produccion asignado", null, sistema);
		}
	}

	private void actualizaAvaEstatusConstruccion(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {
		Integer idEstatusAvaConstruccion = this
				.obtenerObjEstatusAva(EstatusAmbienteVirtualAprendizajeEnum.EN_CONSTRUCCION, estadoAvaList).getId();

		ambienteVirtualAprendizajeDTO.getCatEstadoAva().setId(idEstatusAvaConstruccion);
		ambienteVirtualAprendizajeDTO.setUsuarioModifico(usuarioSessionDTO.getIdPersona());
		ambienteVirtualAprendizajeDTO.setFechaActualizacion(new Date());

		ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultadoDTO = ambienteVirtualApService
				.actualizar(ambienteVirtualAprendizajeDTO);

		logger.info("El resultado de la transaccion de actualizacion de ava" + " a estatus construccion fue "
				+ resultadoDTO.getResultado());

		if (resultadoDTO.getResultado().equals(ResultadoTransaccionEnum.EXITOSO)) {
			// TODO Envio de correos y notificaciones a los responsables
			if (ObjectUtils.isNotNull(resultadoDTO.getDto().getEventoCapacitacion())) {
				EventoCapacitacionDTO evento = resultadoDTO.getDto().getEventoCapacitacion();
				String claveNotificacion = ConstantesGestorWeb.CLAVE_NOTIFICACION_AL_CONSTRUIR_AVA;
				String claveCorreo = ConstantesGestorWeb.CLAVE_CORREO_AL_CONSTRUIR_AVA;
				correoNotificacionBean.notificarResponsablesEvento(claveNotificacion, claveCorreo, evento);
			} else {
				logger.info("No fue posible enviar la notificacion a los responsables.");
			}
			this.borraAvaDeLaLista(resultadoDTO.getDto().getId(), ambienteVirtualAprendizajeDTOList);
		}

		this.validaMensajeResultadoTransaccion(resultadoDTO.getMensajes(), resultadoDTO.getResultado());

	}

	private void borraAvaDeLaLista(Integer idAva,
			List<AmbienteVirtualAprendizajeDTO> ambienteVirtualAprendizajeDTOList) {

		Iterator<AmbienteVirtualAprendizajeDTO> i = ambienteVirtualAprendizajeDTOList.iterator();
		while (i.hasNext()) {
			AmbienteVirtualAprendizajeDTO avaTemp = i.next();
			if (avaTemp.getId().equals(idAva)) {
				i.remove();
				break;
			}

		}
	}

	private Boolean validaSiAvaTieneRespProduccion(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {
		Boolean avaTieneRespConstruccion = Boolean.FALSE;

		if (ObjectUtils.isNotNull(ambienteVirtualAprendizajeDTO.getEventoCapacitacion().getResponsableProduccion())) {
			avaTieneRespConstruccion = Boolean.TRUE;
		}

		return avaTieneRespConstruccion;
	}

	private CatalogoComunDTO obtenerObjEstatusAva(EstatusAmbienteVirtualAprendizajeEnum estatus,
			List<CatalogoComunDTO> estadoAvaList) {
		CatalogoComunDTO estatusAva = null;

		for (CatalogoComunDTO estadoAva : estadoAvaList) {
			if (estatus.getId().equals(estadoAva.getId())) {
				estatusAva = estadoAva;
				break;
			}
		}
		return estatusAva;
	}

	public void onChangeEstatusAva(ValueChangeEvent e) {

		if (ObjectUtils.isNotNull(e.getNewValue())) {
			esEstatusAvaVisible = Boolean.FALSE;
			estatusSeleccionado.setId((Integer) e.getNewValue());
			Integer idEstatusEntCap = null;

			/**
			 * Si la busqueda del AVA es en estatus SOLICITUD o CALENDARIZADO y
			 * que el evt de capacitacion con el que este ligado este en estatus
			 * CALENDARIZADO
			 **/
			if (EstatusAmbienteVirtualAprendizajeEnum.EN_SOLICITUD.getId().equals(estatusSeleccionado.getId())
					|| EstatusAmbienteVirtualAprendizajeEnum.EN_CONSTRUCCION.getId()
							.equals(estatusSeleccionado.getId())) {

				idEstatusEntCap = EstadoEventoCapEnum.CALENDARIZADO.getId();
			}

			/**
			 * Si la busqueda del AVA es en estatus Activo el evt de
			 * capacitacion con el que este ligado este en estatus En Ejecucion
			 **/
			if (EstatusAmbienteVirtualAprendizajeEnum.ACTIVO.getId().equals(estatusSeleccionado.getId())) {
				idEstatusEntCap = EstadoEventoCapEnum.EN_EJECUCION.getId();
			}

			/**
			 * Si la busqueda del AVA es en estatus Inactivo el evt de
			 * capacitacion con el que este ligado este en estatus Concluido
			 **/
			if (EstatusAmbienteVirtualAprendizajeEnum.INACTIVO.getId().equals(estatusSeleccionado.getId())) {
				idEstatusEntCap = EstadoEventoCapEnum.CONCLUIDOS.getId();

			}

			ambienteVirtualAprendizajeDTOList = this.obtenerAmbientesvirtualAprendizaje(estatusSeleccionado.getId(),
					idModalidades, idEstatusEntCap);

			if (!ambienteVirtualAprendizajeDTOList.isEmpty()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_AVA", "", requestActual(),
						TipoServicioEnum.LOCAL);
			}

			if (EstatusAmbienteVirtualAprendizajeEnum.ACTIVO.getId().equals(estatusSeleccionado.getId())) {

				List<Integer> idEventosCapacitacion = obtenerIdEvtCapacitacion(ambienteVirtualAprendizajeDTOList);
				this.obtenerEventosSinCerrarActas(idEventosCapacitacion);
			}

			this.asignaResponsablesProduccion(ambienteVirtualAprendizajeDTOList);
			this.validaColumnasPresentadas(ambienteVirtualAprendizajeDTOList);
		} else {
			esPanelSemaforoVisible = Boolean.FALSE;
			ambienteVirtualAprendizajeDTOList = null;
		}
	}

	public List<Integer> obtenerIdEvtCapacitacion(List<AmbienteVirtualAprendizajeDTO> avaList) {
		List<Integer> idEvtList = new ArrayList<Integer>();

		avaList.forEach(ava -> {
			idEvtList.add(ava.getEventoCapacitacion().getIdEvento());
		});

		return idEvtList;

	}

	public void validaColumnasPresentadas(List<AmbienteVirtualAprendizajeDTO> ambienteVirtualAprendizajeDTOList) {

		esColumnaSemaroforoVisible = Boolean.FALSE;
		esColumnaUnidadDidacticaVisible = Boolean.FALSE;
		esPanelSemaforoVisible = Boolean.FALSE;
		esColumnaCerrarActasVisible = Boolean.FALSE;
		esAvanceVisible = Boolean.FALSE;
		for (AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO : ambienteVirtualAprendizajeDTOList) {
			String estadoAva = ambienteVirtualAprendizajeDTO.getCatEstadoAva().getNombre();

			if (EstatusAmbienteVirtualAprendizajeEnum.EN_CONSTRUCCION.getEtiqueta().equals(estadoAva)) {
				esColumnaSemaroforoVisible = Boolean.TRUE;
				esPanelSemaforoVisible = Boolean.TRUE;
				esAvanceVisible = Boolean.TRUE;
				continue;
			}

			if (EstatusAmbienteVirtualAprendizajeEnum.INACTIVO.getEtiqueta().equals(estadoAva)) {
				esColumnaUnidadDidacticaVisible = Boolean.TRUE;
				continue;
			}

			if (EstatusAmbienteVirtualAprendizajeEnum.ACTIVO.getEtiqueta().equals(estadoAva)) {
				esColumnaCerrarActasVisible = Boolean.TRUE;
				continue;
			}
		}

	}

	public void busquedaAvasPorCriteriosBusqueda() {
		List<AmbienteVirtualAprendizajeDTO> avaActivos = null;
		esEstatusAvaVisible = Boolean.TRUE;
		estatusSeleccionado.setId(null);

		ambienteVirtualAprendizajeDTOList = this.ambienteVirtualApService.busquedaDeAvasConCriterios(filtro,
				tipoDatoFechas);

		if (!ambienteVirtualAprendizajeDTOList.isEmpty()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_AVA", "", requestActual(), TipoServicioEnum.LOCAL);
		}

		avaActivos = obtenerAvasPorEstatus(ambienteVirtualAprendizajeDTOList,
				EstatusAmbienteVirtualAprendizajeEnum.ACTIVO);

		if (!ObjectUtils.isNullOrEmpty(avaActivos)) {
			List<Integer> idEventosCapacitacion = obtenerIdEvtCapacitacion(avaActivos);
			this.obtenerEventosSinCerrarActas(idEventosCapacitacion);
		}

		this.asignaResponsablesProduccion(ambienteVirtualAprendizajeDTOList);
		this.validaColumnasPresentadas(ambienteVirtualAprendizajeDTOList);
		this.limpiaCriteriosBusqueda();
		this.reiniciaEstadoDeshabilitadoCriteriosBusqueda();
		logger.info("Termino la busqueda por criterios");

	}

	private List<AmbienteVirtualAprendizajeDTO> obtenerAvasPorEstatus(List<AmbienteVirtualAprendizajeDTO> avaList,
			EstatusAmbienteVirtualAprendizajeEnum estatusAvaEnum) {
		List<AmbienteVirtualAprendizajeDTO> avaActivos = new ArrayList<AmbienteVirtualAprendizajeDTO>();

		avaList.forEach(ava -> {
			if (ava.getCatEstadoAva().getId()
					.equals(this.obtenerObjEstatusAva(estatusAvaEnum, estadoAvaList).getId())) {
				avaActivos.add(ava);
			}
		});

		return avaActivos;
	}

	private void reiniciaEstadoDeshabilitadoCriteriosBusqueda() {
		esEjeDeCapacitacionEditable = Boolean.FALSE;

		esNombreProgramaEditable = Boolean.FALSE;

		esModalidadEditable = Boolean.FALSE;
		
		idCatPlan = 0;
		catEstructuras = new ArrayList<CatalogoComunDTO>();
		catSubEstructurasNivel1 = new ArrayList<CatalogoComunDTO>();
		catSubEstructurasNivel2 = new ArrayList<CatalogoComunDTO>();
		catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
	}

	private void limpiaCriteriosBusqueda() {
		filtro.getFichaDescriptivaPrograma().setTipoCompetencia(null);
		filtro.getFichaDescriptivaPrograma().setEjeCapacitacion(null);
		filtro.getFichaDescriptivaPrograma().setNombreTentativo(null);
		;
		filtro.setNombreEc(null);
		filtro.setCatModalidadPlanPrograma(new CatalogoComunDTO());
		filtro.setFechaInicial(null);
		filtro.setFechaFinal(null);
		tipoDatoFechas = null;

	}

	/**
	 * Metodo que crea los ejes de capacitacion apartir del tipo de competencia
	 * seleccionado
	 */
	public void onChangeTpoCompetencia(ValueChangeEvent e) {
		esEjeDeCapacitacionEditable = Boolean.FALSE;
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			esEjeDeCapacitacionEditable = Boolean.TRUE;
			Integer idTpoCompSel = (Integer) e.getNewValue();
			filtro.getFichaDescriptivaPrograma().setTipoCompetencia(idTpoCompSel);
			listaEjesCapacitacion = new ArrayList<>();

			for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
				if (nh.getIdNodo().equals(idTpoCompSel)) {
					for (NodoeHijosDTO nint : nh.getNodosHijos()) {

						CatalogoComunDTO cc = new CatalogoComunDTO();
						cc.setId(nint.getIdNodo());
						cc.setNombre(nint.getNombre());
						listaEjesCapacitacion.add(cc);
					}
				}
			}
		}
	}

	public void onChangeEventoCapacitacion(ValueChangeEvent e) {
		this.esNombreProgramaEditable = Boolean.FALSE;
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			this.esNombreProgramaEditable = Boolean.TRUE;

			Integer idEeCap = Integer.parseInt(e.getNewValue().toString());
			filtro.getFichaDescriptivaPrograma().setEjeCapacitacion(idEeCap);

			avaFiltroBusquedaList = ambienteVirtualApService.consultarAvasPorEjeCapacitacion(
					idModalidades, this.obtenerListaIdEstatusEventos(),
					filtro.getFichaDescriptivaPrograma().getEjeCapacitacion());
			logger.info("La busqeda de ava por tpo competencia y eje de capacitacion " + "		retorno"
					+ avaFiltroBusquedaList.size() + " Elementos");

		}

	}

	private List<Integer> obtenerListaIdEstatusEventos() {

		List<Integer> idEstatusEventoCapacitacion = new ArrayList<Integer>();

		idEstatusEventoCapacitacion.add(EstadoEventoCapEnum.CALENDARIZADO.getId());
		idEstatusEventoCapacitacion.add(EstadoEventoCapEnum.EN_EJECUCION.getId());
		idEstatusEventoCapacitacion.add(EstadoEventoCapEnum.CONCLUIDOS.getId());

		return idEstatusEventoCapacitacion;

	}

	public void onChangeNombreProgramaCapacitacion(ValueChangeEvent e) {
		this.esModalidadEditable = Boolean.FALSE;
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			this.esModalidadEditable = Boolean.TRUE;
		}

	}

	/**
	 * Metodo que sirve para extraer los ejes de capacitacion
	 */
	private void generaCatEjesCapacitBusqueda() {
		ejesCapacitacion = new ArrayList<>();

		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			for (NodoeHijosDTO nint : nh.getNodosHijos()) {
				CatalogoComunDTO cc = new CatalogoComunDTO();
				cc.setId(nint.getIdNodo());
				cc.setNombre(nint.getNombre());
				ejesCapacitacion.add(cc);
			}
		}
	}

	/**
	 * Metodo que extraer los datos para mostrar el tipo competencia
	 */
	private void generaEstructuraCatTpoCompetenciaPlan() {

		List<NodoeHijosDTO> planes = new ArrayList<>();
		List<MallaCurricularDTO> mallas = new ArrayList<>();

		MallaCurricularDTO mallaSedesol = eventoCapacitacionServiceFacade.obtenerMallaCurricular();
		mallas.add(mallaSedesol);

		for (MallaCurricularDTO m : mallas) {
			NodoeHijosDTO nodog = new NodoeHijosDTO();
			nodog.setNombre(m.getNombre());
			nodog.setIdNodo(m.getId());
			nodog.setIdPadre(m.getMallaCurricularPadre() != null ? m.getMallaCurricularPadre().getId() : null);
			nodog.setIdObjCurr(m.getObjetoCurricular().getId());
			nodog.setNivel(0);

			if (!m.getLstHijosMallaCurr().isEmpty()) {
				this.generaCatxNivel(m.getLstHijosMallaCurr(), nodog, nodog.getNivel());
			}

			planes.add(nodog);
		}

		listaTiposCompetencias = new ArrayList<>();

		// Genera el Catalogo Tipo de Competencia
		estPlanSedesol = planes.get(ConstantesGestorWeb.INDICE_INICIAL);
		for (NodoeHijosDTO nh : estPlanSedesol.getNodosHijos()) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			listaTiposCompetencias.add(cc);
		}
	}

	/**
	 * Metodo que obtiene el nombre de un tipo de competencia apartir de su id
	 */
	public String obtieneNombreTpoCompetencia(Integer idTpoComp) {

		if (ObjectUtils.isNotNull(idTpoComp)) {
			for (CatalogoComunDTO tpoCom : listaTiposCompetencias) {
				if (tpoCom.getId().equals(idTpoComp))
					return tpoCom.getNombre();
			}
		}
		return null;
	}

	/**
	 * Metodo que obtienen el nombre de un eje de capacitacion apartir de su id
	 */
	public String obtieneNombreEjeCapacit(Integer idEjeCap) {
		if (ObjectUtils.isNotNull(idEjeCap)) {
			for (CatalogoComunDTO ejeCap : ejesCapacitacion) {
				if (ejeCap.getId().compareTo(idEjeCap) == 0)
					return ejeCap.getNombre();
			}
		}
		return null;
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

	public String irModificarseguimientoAva() {

		return ConstantesGestorWeb.NAVEGA_MODIFICAR_SEGUIMIENTO_AVA;
	}

	public void onChangeActivaBusquedaFechas(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			String tipoDatoFechas = (String) e.getNewValue();
			esFechaInicialYFinalHabilitada = Boolean.FALSE;
			if (ObjectUtils.isNotNull(tipoDatoFechas) && (!"".equals(tipoDatoFechas))) {
				esFechaInicialYFinalHabilitada = Boolean.TRUE;
			}
		}
	}

	public Boolean validaAccionUrlAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		Boolean esVisible = Boolean.FALSE;
		if (ObjectUtils.isNotNull(ambienteVirtualAprendizajeDTO)) {
			String estadoAVA = ambienteVirtualAprendizajeDTO.getCatEstadoAva().getNombre();

			if (!EstatusAmbienteVirtualAprendizajeEnum.EN_SOLICITUD.getEtiqueta().equals(estadoAVA)) {
				esVisible = Boolean.TRUE;
			}

		}

		return esVisible;
	}

	public Boolean validaAccionDesactivarAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		Boolean esVisible = Boolean.FALSE;
		if (ObjectUtils.isNotNull(ambienteVirtualAprendizajeDTO)) {
			String estadoAVA = ambienteVirtualAprendizajeDTO.getCatEstadoAva().getNombre();
			if (EstatusAmbienteVirtualAprendizajeEnum.ACTIVO.getEtiqueta().equals(estadoAVA)) {
				esVisible = Boolean.TRUE;
			}

		}

		return esVisible;
	}

	public Boolean validaAccionUnidadDidacticaAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		Boolean esVisible = Boolean.FALSE;
		if (ObjectUtils.isNotNull(ambienteVirtualAprendizajeDTO)) {
			String estadoAVA = ambienteVirtualAprendizajeDTO.getCatEstadoAva().getNombre();
			if (EstatusAmbienteVirtualAprendizajeEnum.INACTIVO.getEtiqueta().equals(estadoAVA)) {
				esVisible = Boolean.TRUE;
			}

		}

		return esVisible;
	}

	public Boolean validaAccionModificarSeguimientoAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		Boolean esVisible = Boolean.FALSE;
		if (ObjectUtils.isNotNull(ambienteVirtualAprendizajeDTO)) {
			String estadoAVA = ambienteVirtualAprendizajeDTO.getCatEstadoAva().getNombre();
			if (EstatusAmbienteVirtualAprendizajeEnum.EN_CONSTRUCCION.getEtiqueta().equals(estadoAVA)) {
				esVisible = Boolean.TRUE;
			}

		}

		return esVisible;
	}

	public Boolean validaAccionCerrarActasAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		Boolean esVisible = Boolean.FALSE;
		if (ObjectUtils.isNotNull(ambienteVirtualAprendizajeDTO)) {
			String estadoAVA = ambienteVirtualAprendizajeDTO.getCatEstadoAva().getNombre();
			if (EstatusAmbienteVirtualAprendizajeEnum.ACTIVO.getEtiqueta().equals(estadoAVA)) {
				esVisible = Boolean.TRUE;
			}

		}

		return esVisible;
	}

	public Boolean validaAccionSemaforoAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		Boolean esVisible = Boolean.FALSE;
		if (ObjectUtils.isNotNull(ambienteVirtualAprendizajeDTO)) {
			String estadoAVA = ambienteVirtualAprendizajeDTO.getCatEstadoAva().getNombre();
			if (EstatusAmbienteVirtualAprendizajeEnum.EN_CONSTRUCCION.getEtiqueta().equals(estadoAVA)) {
				esVisible = Boolean.TRUE;
			}

		}

		return esVisible;
	}

	public Boolean validaAccionArchivarAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		Boolean esVisible = Boolean.FALSE;
		if (ObjectUtils.isNotNull(ambienteVirtualAprendizajeDTO)) {
			Integer estadoAVA = ambienteVirtualAprendizajeDTO.getCatEstadoAva().getId();
			if (EstatusAmbienteVirtualAprendizajeEnum.INACTIVO.getId().equals(estadoAVA)) {
				esVisible = Boolean.TRUE;
			}

		}

		return esVisible;
	}

	/**
	 * Valida si el boton de archivar AVA esta habilitado o no, esto depende de
	 * lo que exista en la variable AmbienteVirtualAprendizajeDTO.esAvaArchivado
	 * 
	 * @param ambienteVirtualAprendizajeDTO
	 *//*
		 * private Boolean
		 * validaBtnArchivaAvaActivo(AmbienteVirtualAprendizajeDTO ava){
		 * 
		 * 
		 * if(ObjectUtils.isNotNull(ava.getEsAvaArchivado())&&
		 * ava.getEsAvaArchivado()){
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * return null;
		 * 
		 * }
		 */

	public Boolean validaAccionAsignarRespYPuestaProduccionAva(
			AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		Boolean esVisible = Boolean.FALSE;
		if (ObjectUtils.isNotNull(ambienteVirtualAprendizajeDTO)) {
			String estadoAVA = ambienteVirtualAprendizajeDTO.getCatEstadoAva().getNombre();
			if (EstatusAmbienteVirtualAprendizajeEnum.EN_SOLICITUD.getEtiqueta().equals(estadoAVA)) {
				esVisible = Boolean.TRUE;
			}

		}

		return esVisible;
	}

	public Boolean validaAccionarAvaAsignaResponsablePermitida(
			AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		String estadoAVA = ambienteVirtualAprendizajeDTO.getCatEstadoAva().getNombre();
		Boolean esVisible = Boolean.FALSE;
		if (EstatusAmbienteVirtualAprendizajeEnum.EN_SOLICITUD.getEtiqueta().equals(estadoAVA)) {
			esVisible = Boolean.TRUE;
		}
		return esVisible;
	}

	public Boolean validaCerrarActasPermitida(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		String estadoAVA = ambienteVirtualAprendizajeDTO.getCatEstadoAva().getNombre();
		Boolean esVisible = Boolean.FALSE;
		if (EstatusAmbienteVirtualAprendizajeEnum.EN_SOLICITUD.getEtiqueta().equals(estadoAVA)) {
			esVisible = Boolean.TRUE;
		}
		return esVisible;

	}

	public Boolean validaSiExisteResponsableProduccion(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {
		Boolean existeResponsable = Boolean.FALSE;
		if (ObjectUtils.isNotNull(ambienteVirtualAprendizajeDTO.getEventoCapacitacion().getResponsableProduccion())) {
			existeResponsable = Boolean.TRUE;
		}

		return !existeResponsable;
	}

	public void desactivarAVA(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {
		logger.info(("Desactivara el AVA " + ambienteVirtualAprendizajeDTO.getId()));
		ambienteVirtualAprendizajeDTO.setActivo(Boolean.FALSE);
		ambienteVirtualAprendizajeDTO
				.setCatEstadoAva(obtenerObjEstatusAva(EstatusAmbienteVirtualAprendizajeEnum.INACTIVO, estadoAvaList));

		ambienteVirtualAprendizajeDTO.setUsuarioModifico(usuarioSessionDTO.getIdPersona());
		ambienteVirtualAprendizajeDTO.setFechaActualizacion(new Date());
		ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado = ambienteVirtualApService
				.actualizar(ambienteVirtualAprendizajeDTO);

		if (resultado.esCorrecto()) {
			this.borraAvaDeLaLista(resultado.getDto().getId(), ambienteVirtualAprendizajeDTOList);
			try {
				Integer idAva = ambienteVirtualAprendizajeDTO.getId();
				this.ocultarCursoLms(ambienteVirtualAprendizajeDTO);
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "DCT_AVA", String.valueOf(idAva), requestActual(),
						TipoServicioEnum.LOCAL);
			} catch (ErrorWS e) {
				e.printStackTrace();
				logger.error("Ocurrio un error al ocultar el curso LMS");
				resultado.agregaMensaje("Ocurrio un error al ocultar el curso LMS");
			}
		}
		this.validaMensajeResultadoTransaccion(resultado.getMensajes(), resultado.getResultado());
		logger.info("El resultado de la actualizacion del AVA fue " + resultado.getResultado());

	}

	/**
	 * Metodo que envia una peticion Ws para ocultar el curso lms
	 * 
	 * @param ava
	 * @throws ErrorWS
	 */
	private void ocultarCursoLms(AmbienteVirtualAprendizajeDTO ava) throws ErrorWS {
		CursoWS cursoWS = new CursoWS(ava.getPlataformaMoodle());
		cursoWS.ocultarCurso(ava.getIdCursoLms());
	}

	public String asignaColorSemaro(AmbienteVirtualAprendizajeDTO ava) {
		return ambienteVirtualApService.asignaColorSemaro(ava);
	}

	/**
	 * Metodo que arma el arbol de las unidades apartir de un ambiente virtual
	 * de aprendizaje
	 * 
	 * @param ambienteVirtualAprendizajeDTO
	 */
	public void armaArbol(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {
		unidadOaAvaDTOs = this.buscaUnidadesPorAva(ambienteVirtualAprendizajeDTO);
		logger.info("El numero de unidades retornadas es de " + unidadOaAvaDTOs.size());

		root = new DefaultTreeNode(ambienteVirtualAprendizajeDTO.getEventoCapacitacion().getNombreEc(), null);

		for (UnidadOaAvaDTO unidadOaAvaDTO : unidadOaAvaDTOs) {

			TreeNode subtema = new DefaultTreeNode(unidadOaAvaDTO.getDetEstUnidadDidactica().getNombreUnidad(), root);

			root.getChildren().add(subtema);

		}
	}

	/**
	 * Metodo que busca las unidades ligadas a un ava
	 * 
	 * @param ambienteVirtualAprendizajeDTO
	 * @return
	 */
	private List<UnidadOaAvaDTO> buscaUnidadesPorAva(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {

		return relUnidadOaAvaService.findByIdAva(ambienteVirtualAprendizajeDTO.getId());

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
		CatalogoComunDTO estatusEnConstruccion = null;

		diferencia = obtenerDiferenciaDeDias(fechaInicialEventoCap, fechaActual);

		estatusEnConstruccion = this.obtenerObjEstatusAva(EstatusAmbienteVirtualAprendizajeEnum.EN_CONSTRUCCION,
				estadoAvaList);

		if (diferencia < 5 && estatusEnConstruccion.getId().equals(ava.getCatEstadoAva().getId())) {
			esFechaProxima = Boolean.TRUE;
		}

		return esFechaProxima;
	}

	private Long obtenerDiferenciaDeDias(Date fechaInicial, Date fechaFinal) {
		Long diferencia = null;

		diferencia = (fechaInicial.getTime() - fechaFinal.getTime()) / ConstantesGestorWeb.MILISEGUNDOS_POR_DIA;

		return diferencia;
	}

	public void deshabilita() {
		if (ObjectUtils.isNull(filtro.getFichaDescriptivaPrograma().getEjeCapacitacion())) {
			esEjeDeCapacitacionEditable = Boolean.FALSE;
			esNombreProgramaEditable = Boolean.FALSE;
			esModalidadEditable = Boolean.FALSE;

		}

	}

	public void obtenerEventosSinCerrarActas(List<Integer> idEventosCapacitacion) {

		actasCerradasPorIdEvtCapMap = grupoService.validaActasCerradas(idEventosCapacitacion);

	}

	/**
	 * true si las el evento tiene actas sin cerrar
	 * 
	 * @param idEventoCapacitacion
	 * @return
	 */
	public Boolean validaActasCerradas(Integer idEventoCapacitacion) {

		Boolean resultado = actasCerradasPorIdEvtCapMap.get(idEventoCapacitacion);

		if (ObjectUtils.isNull(resultado)) {
			resultado = Boolean.FALSE;
		}

		return resultado;
	}

	public String validaColorTextoCerrarActas(AmbienteVirtualAprendizajeDTO ava) {

		String colorTexto = null;

		Boolean resultado = validaActasCerradas(ava.getEventoCapacitacion().getIdEvento());

		if (resultado) {
			colorTexto = SemaforoEnum.SEMAFORO_ROJO.getColorSemaforo();
		}

		return colorTexto;
	}

	public String validaTextoCerrarActas(AmbienteVirtualAprendizajeDTO ava) {

		String texto = "gw.ga.label.realizado";

		Boolean resultado = validaActasCerradas(ava.getEventoCapacitacion().getIdEvento());

		if (resultado) {

			texto = "gw.ga.label.pendiente";
		}

		return texto;
	}

	public void guardarBitacoraAva(AmbienteVirtualAprendizajeDTO ava) {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "IR_AVA_MDL", String.valueOf(ava.getId()), requestActual(),
				TipoServicioEnum.LOCAL);
	}

	public String navegarAUrlExterna(AmbienteVirtualAprendizajeDTO ava) throws IOException {

		logger.info("El id del evento de capacitacion es " + ava.getEventoCapacitacion().getIdEvento());

		String urlLms =

				ambienteVirtualApService.obtenerUrlCursoLms(usuarioSessionDTO.getIdPersona(), ava.getIdCursoLms(),
						ava.getPlataformaMoodle());

		logger.info("La url obtenida es :" + urlLms);

		logger.info("Redireccionara a la url " + urlLms);

		return urlLms;

	}

	/**
	 * Metodo que manda una peticion ws para archivar(clonar) el ava
	 * 
	 * @param ava
	 */
	public void archivarAva(AmbienteVirtualAprendizajeDTO ava) {
		logger.info("Va a respaldar el curso ");

		Boolean resultadoOperacionArchivarAva = ambienteVirtualApService.respaldarCurso(ava,
				this.usuarioSessionDTO.getIdPersona());

		if (resultadoOperacionArchivarAva) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "ARC_AVA", String.valueOf(ava.getId()), requestActual(),
					TipoServicioEnum.LOCAL);
			agregarMsgInfo("Se realizo el archivo del curso exitosamente", null, sistema);
		} else {
			agregarMsgInfo("Ocurrio un problema al archivar el curso", null, sistema);
		}

	}
	
	
	/**
	 * Genera el catalogo de planes
	 *
	 * @param e
	 */
	private void generaEstructuraCatPlanes() {
		nodos = this.generarPlanes();
		catPlanes = new ArrayList<>();
		
		for (NodoeHijosDTO nh : nodos) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			
			catPlanes.add(cc);
		}
	}
	
	/**
	 * Genera el catalogo de estructuras
	 *
	 * @param e
	 */
	public void onChangeCatPlan(ValueChangeEvent e) {
		this.esNombreProgramaEditable = Boolean.FALSE;
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idPlanSel = Integer.parseInt(e.getNewValue().toString());
			
			catEstructuras = this.generarEstructuras(nodos, idPlanSel);
			
			catSubEstructurasNivel1 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel2 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
			
			ejeCapacitacion = idPlanSel;
			
			if(catEstructuras.size() == 0){
				buscarPlanes();
			}
		}
	}
	
	/**
	 * Genera el catalogo de subestructuras nivel 1
	 *
	 * @param e
	 */
	public void onChangeCatEstructura(ValueChangeEvent e) {
		this.esNombreProgramaEditable = Boolean.FALSE;
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSubEstructura = Integer.parseInt(e.getNewValue().toString());
			
			catSubEstructurasNivel1 = this.generarSubEstructuras1(nodos, idSubEstructura);
			
			catSubEstructurasNivel2 = new ArrayList<CatalogoComunDTO>();
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
			ejeCapacitacion = idSubEstructura;
			idCatEstructura = idSubEstructura;
			
			if(catSubEstructurasNivel1.size() == 0){
				buscarPlanes();
			}
			
		}
	}
	
	/**
	 * Genera el catalogo de subestructuras nivel 2
	 *
	 * @param e
	 */
	public void onChangeCatSubestructura1(ValueChangeEvent e) {
		this.esNombreProgramaEditable = Boolean.FALSE;
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSubEstructura = Integer.parseInt(e.getNewValue().toString());
			catSubEstructurasNivel2 = this.generarSubEstructuras2(nodos, idSubEstructura);
			
			catSubEstructurasNivel3 = new ArrayList<CatalogoComunDTO>();
			ejeCapacitacion =  Integer.parseInt(e.getNewValue().toString());
			idCatSubEstructuraNivel1 = idSubEstructura;
			
			if(catSubEstructurasNivel2.size() == 0){
				buscarPlanes();
			}
			
		}
	}
	
	/**
	 * Genera el catalogo de subestructuras nivel 3
	 *
	 * @param e
	 */
	public void onChangeCatSubestructura2(ValueChangeEvent e) {
		this.esNombreProgramaEditable = Boolean.FALSE;
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			Integer idSubEstructura = Integer.parseInt(e.getNewValue().toString());
			catSubEstructurasNivel3 = this.generarSubEstructuras3(nodos, idSubEstructura);
			
			ejeCapacitacion = Integer.parseInt(e.getNewValue().toString());
			idCatSubEstructuraNivel2 = idSubEstructura;
			
			if(catSubEstructurasNivel3.size() == 0){
				buscarPlanes();
			}
		}
	}
	
	public void onChangeCatSubestructura3(ValueChangeEvent e) {
		this.esNombreProgramaEditable = Boolean.FALSE;
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			ejeCapacitacion = Integer.parseInt(e.getNewValue().toString());

			idCatSubEstructuraNivel3 = Integer.parseInt(e.getNewValue().toString());
			
			buscarPlanes();
		}
	}
	
	public void buscarPlanes() {
		this.esNombreProgramaEditable = Boolean.TRUE;
		filtro.getFichaDescriptivaPrograma().setEjeCapacitacion(ejeCapacitacion);

		avaFiltroBusquedaList = ambienteVirtualApService.consultarAvasPorEjeCapacitacion(
				idModalidades, this.obtenerListaIdEstatusEventos(),
				filtro.getFichaDescriptivaPrograma().getEjeCapacitacion());
		logger.info("La busqueda de ava por tpo competencia y eje de capacitacion " + "		retorno"
				+ avaFiltroBusquedaList.size() + " Elementos");
	}

	/**
	 * getter y setter
	 * 
	 * @return
	 */

	public AmbienteVirtualApService getAmbienteVirtualApService() {
		return ambienteVirtualApService;
	}

	public void setAmbienteVirtualApService(AmbienteVirtualApService ambienteVirtualApService) {
		this.ambienteVirtualApService = ambienteVirtualApService;
	}

	public GestionAprendizajeServiceAdapter getGestionAprendizajeServiceAdapter() {
		return gestionAprendizajeServiceAdapter;
	}

	public EventoCapacitacionDTO getFiltro() {
		return filtro;
	}

	public void setFiltro(EventoCapacitacionDTO filtro) {
		this.filtro = filtro;
	}

	public void setGestionAprendizajeServiceAdapter(GestionAprendizajeServiceAdapter gestionAprendizajeServiceAdapter) {
		this.gestionAprendizajeServiceAdapter = gestionAprendizajeServiceAdapter;
	}

	public List<AmbienteVirtualAprendizajeDTO> getAmbienteVirtualAprendizajeDTOList() {
		return ambienteVirtualAprendizajeDTOList;
	}

	public void setAmbienteVirtualAprendizajeDTOList(
			List<AmbienteVirtualAprendizajeDTO> ambienteVirtualAprendizajeDTOList) {
		this.ambienteVirtualAprendizajeDTOList = ambienteVirtualAprendizajeDTOList;
	}

	public List<CatalogoComunDTO> getEstadoAvaList() {
		return estadoAvaList;
	}

	public void setEstadoAvaList(List<CatalogoComunDTO> estadoAvaList) {
		this.estadoAvaList = estadoAvaList;
	}

	public CatalogoComunDTO getEstatusSeleccionado() {
		return estatusSeleccionado;
	}

	public void setEstatusSeleccionado(CatalogoComunDTO estatusSeleccionado) {
		this.estatusSeleccionado = estatusSeleccionado;
	}

	public List<CatalogoComunDTO> getListaEjesCapacitacion() {
		return listaEjesCapacitacion;
	}

	public void setListaEjesCapacitacion(List<CatalogoComunDTO> listaEjesCapacitacion) {
		this.listaEjesCapacitacion = listaEjesCapacitacion;
	}

	public List<CatalogoComunDTO> getEjesCapacitacion() {
		return ejesCapacitacion;
	}

	public void setEjesCapacitacion(List<CatalogoComunDTO> ejesCapacitacion) {
		this.ejesCapacitacion = ejesCapacitacion;
	}

	public List<CatalogoComunDTO> getListaTiposCompetencias() {
		return listaTiposCompetencias;
	}

	public void setListaTiposCompetencias(List<CatalogoComunDTO> listaTiposCompetencias) {
		this.listaTiposCompetencias = listaTiposCompetencias;
	}

	public void setEstPlanSedesol(NodoeHijosDTO estPlanSedesol) {
		this.estPlanSedesol = estPlanSedesol;
	}

	public NodoeHijosDTO getEstPlanSedesol() {
		return estPlanSedesol;
	}

	public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
		return eventoCapacitacionServiceFacade;
	}

	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
	}

	public String getTipoDatoFechas() {
		return tipoDatoFechas;
	}

	public void setTipoDatoFechas(String tipoDatoFechas) {
		this.tipoDatoFechas = tipoDatoFechas;
	}

	public List<CatalogoComunDTO> getModalidadEventoCapList() {
		return modalidadEventoCapList;
	}

	public void setModalidadEventoCapList(List<CatalogoComunDTO> modalidadEventoCapList) {
		this.modalidadEventoCapList = modalidadEventoCapList;
	}

	public List<AmbienteVirtualAprendizajeDTO> getAvaFiltroBusquedaList() {
		return avaFiltroBusquedaList;
	}

	public void setAvaFiltroBusquedaList(List<AmbienteVirtualAprendizajeDTO> avaFiltroBusquedaList) {
		this.avaFiltroBusquedaList = avaFiltroBusquedaList;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public Boolean getEsColumnaSemaroforoVisible() {
		return esColumnaSemaroforoVisible;
	}

	public void setEsColumnaSemaroforoVisible(Boolean esColumnaSemaroforoVisible) {
		this.esColumnaSemaroforoVisible = esColumnaSemaroforoVisible;
	}

	public Boolean getEsColumnaUnidadDidacticaVisible() {
		return esColumnaUnidadDidacticaVisible;
	}

	public void setEsColumnaUnidadDidacticaVisible(Boolean esColumnaUnidadDidacticaVisible) {
		this.esColumnaUnidadDidacticaVisible = esColumnaUnidadDidacticaVisible;
	}

	public Boolean getEsFechaInicialYFinalHabilitada() {
		return esFechaInicialYFinalHabilitada;
	}

	public void setEsFechaInicialYFinalHabilitada(Boolean esFechaInicialYFinalHabilitada) {
		this.esFechaInicialYFinalHabilitada = esFechaInicialYFinalHabilitada;
	}

	public Boolean getEsColumnaCerrarActasVisible() {
		return esColumnaCerrarActasVisible;
	}

	public void setEsColumnaCerrarActasVisible(Boolean esColumnaCerrarActasVisible) {
		this.esColumnaCerrarActasVisible = esColumnaCerrarActasVisible;
	}

	public Boolean getEsPanelSemaforoVisible() {
		return esPanelSemaforoVisible;
	}

	public void setEsPanelSemaforoVisible(Boolean esPanelSemaforoVisible) {
		this.esPanelSemaforoVisible = esPanelSemaforoVisible;
	}

	public RelUnidadOaAvaService getRelUnidadOaAvaService() {
		return relUnidadOaAvaService;
	}

	public void setRelUnidadOaAvaService(RelUnidadOaAvaService relUnidadOaAvaService) {
		this.relUnidadOaAvaService = relUnidadOaAvaService;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Boolean getEsEjeDeCapacitacionEditable() {
		return esEjeDeCapacitacionEditable;
	}

	public void setEsEjeDeCapacitacionEditable(Boolean esEjeDeCapacitacionEditable) {
		this.esEjeDeCapacitacionEditable = esEjeDeCapacitacionEditable;
	}

	public Boolean getEsNombreProgramaEditable() {
		return esNombreProgramaEditable;
	}

	public void setEsNombreProgramaEditable(Boolean esNombreProgramaEditable) {
		this.esNombreProgramaEditable = esNombreProgramaEditable;
	}

	public Boolean getEsModalidadEditable() {
		return esModalidadEditable;
	}

	public void setEsModalidadEditable(Boolean esModalidadEditable) {
		this.esModalidadEditable = esModalidadEditable;
	}

	public Boolean getEsAvanceVisible() {
		return esAvanceVisible;
	}

	public void setEsAvanceVisible(Boolean esAvanceVisible) {
		this.esAvanceVisible = esAvanceVisible;
	}

	public GrupoService getGrupoService() {
		return grupoService;
	}

	public void setGrupoService(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	public Boolean getEsEstatusAvaVisible() {
		return esEstatusAvaVisible;
	}

	public void setEsEstatusAvaVisible(Boolean esEstatusAvaVisible) {
		this.esEstatusAvaVisible = esEstatusAvaVisible;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public CorreoElectronicoService getCorreoElectronicoService() {
		return correoElectronicoService;
	}

	public void setCorreoElectronicoService(CorreoElectronicoService correoElectronicoService) {
		this.correoElectronicoService = correoElectronicoService;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public NotificacionSistemaService getNotificacionSistemaService() {
		return notificacionSistemaService;
	}

	public void setNotificacionSistemaService(NotificacionSistemaService notificacionSistemaService) {
		this.notificacionSistemaService = notificacionSistemaService;
	}

	public PersonaCorreoService getPersonaCorreoService() {
		return personaCorreoService;
	}

	public void setPersonaCorreoService(PersonaCorreoService personaCorreoService) {
		this.personaCorreoService = personaCorreoService;
	}

	public MensajeOperacionService getMensajeOperacionService() {
		return mensajeOperacionService;
	}

	public void setMensajeOperacionService(MensajeOperacionService mensajeOperacionService) {
		this.mensajeOperacionService = mensajeOperacionService;
	}

	public RelReponsableProduccionEcService getRelReponsableProduccionEcService() {
		return relReponsableProduccionEcService;
	}

	public void setRelReponsableProduccionEcService(RelReponsableProduccionEcService relReponsableProduccionEcService) {
		this.relReponsableProduccionEcService = relReponsableProduccionEcService;
	}

	public CorreoNotificacionBean getCorreoNotificacionBean() {
		return correoNotificacionBean;
	}

	public void setCorreoNotificacionBean(CorreoNotificacionBean correoNotificacionBean) {
		this.correoNotificacionBean = correoNotificacionBean;
	}
	
	
	public List<CatalogoComunDTO> getCatPlanes() {
		return catPlanes;
	}

	public void setCatPlanes(List<CatalogoComunDTO> catPlanes) {
		this.catPlanes = catPlanes;
	}

	public List<CatalogoComunDTO> getCatEstructuras() {
		return catEstructuras;
	}

	public void setCatEstructuras(List<CatalogoComunDTO> catEstructuras) {
		this.catEstructuras = catEstructuras;
	}

	public List<CatalogoComunDTO> getCatSubEstructurasNivel1() {
		return catSubEstructurasNivel1;
	}

	public void setCatSubEstructurasNivel1(List<CatalogoComunDTO> catSubEstructurasNivel1) {
		this.catSubEstructurasNivel1 = catSubEstructurasNivel1;
	}

	public List<CatalogoComunDTO> getCatSubEstructurasNivel2() {
		return catSubEstructurasNivel2;
	}

	public void setCatSubEstructurasNivel2(List<CatalogoComunDTO> catSubEstructurasNivel2) {
		this.catSubEstructurasNivel2 = catSubEstructurasNivel2;
	}

	public List<CatalogoComunDTO> getCatSubEstructurasNivel3() {
		return catSubEstructurasNivel3;
	}

	public void setCatSubEstructurasNivel3(List<CatalogoComunDTO> catSubEstructurasNivel3) {
		this.catSubEstructurasNivel3 = catSubEstructurasNivel3;
	}

	public Integer getIdCatPlan() {
		return idCatPlan;
	}

	public void setIdCatPlan(Integer idCatPlan) {
		this.idCatPlan = idCatPlan;
	}

	public Integer getIdCatEstructura() {
		return idCatEstructura;
	}

	public void setIdCatEstructura(Integer idCatEstructura) {
		this.idCatEstructura = idCatEstructura;
	}

	public Integer getIdCatSubEstructuraNivel1() {
		return idCatSubEstructuraNivel1;
	}

	public void setIdCatSubEstructuraNivel1(Integer idCatSubEstructuraNivel1) {
		this.idCatSubEstructuraNivel1 = idCatSubEstructuraNivel1;
	}

	public Integer getIdCatSubEstructuraNivel2() {
		return idCatSubEstructuraNivel2;
	}

	public void setIdCatSubEstructuraNivel2(Integer idCatSubEstructuraNivel2) {
		this.idCatSubEstructuraNivel2 = idCatSubEstructuraNivel2;
	}

	public Integer getIdCatSubEstructuraNivel3() {
		return idCatSubEstructuraNivel3;
	}

	public void setIdCatSubEstructuraNivel3(Integer idCatSubEstructuraNivel3) {
		this.idCatSubEstructuraNivel3 = idCatSubEstructuraNivel3;
	}

	public Integer getEjeCapacitacion() {
		return ejeCapacitacion;
	}

	public void setEjeCapacitacion(Integer ejeCapacitacion) {
		this.ejeCapacitacion = ejeCapacitacion;
	}

	public List<NodoeHijosDTO> getNodos() {
		return nodos;
	}

	public void setNodos(List<NodoeHijosDTO> nodos) {
		this.nodos = nodos;
	}
	
	
}
