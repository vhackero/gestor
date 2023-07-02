package mx.gob.sedesol.gestorweb.beans.analisisdatos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import org.apache.log4j.Logger;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatGestionEscolarEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAsistenciaEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatAsistencia;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDestinatariosEc;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.AsistenciaFacadeService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.basegestor.springinit.GestionEscolarServiceAdapter;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;

@ManagedBean
@ViewScoped
public class ReporteEventosCapacitacionBean extends BaseBean {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = -4592554386167571778L;
	private static final Logger logger = Logger.getLogger(ReporteEventosCapacitacionBean.class);

	private static final String ID_DIRIGIDO_A_NO_REGISTRADO = "No registrado";
		

	/**
	 * Inyeccion de eventoCapacitacionServiceFacade
	 */
	@ManagedProperty(value = "#{eventoCapacitacionServiceFacade}")
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;
		
	/**
	 * Inyeccion de asistenciaFacadeService
	 */
	@ManagedProperty(value = "#{asistenciaFacadeService}")
	private AsistenciaFacadeService asistenciaFacadeService;

	/**
	 * Inyeccion de gestionEscolarServiceAdapter
	 */
	@ManagedProperty(value = "#{gestionEscolarServiceAdapter}")
	private GestionEscolarServiceAdapter gestionEscolarServiceAdapter;
	
	private EventoCapacitacionDTO filtro;
	private String tipoDatoFechas;
	private List<CatalogoComunDTO> modalidadEventoCapList;
	private List<CatalogoComunDTO> listaTiposCompetencias;
	private List<CatalogoComunDTO> ejesCapacitacion;
	private NodoeHijosDTO estPlanSedesol;
	private List<CatalogoComunDTO> listaEjesCapacitacion;
	private List<CatalogoComunDTO> listaEstatusEC;
	private List<EventoCapacitacionDTO> eventoCapacitacionDTOList;
	private Boolean esTablaResultadosVisible;
	private List<Boolean> visible;
	private String informePredisenadoSeleccionado;
	private Map<String, Long> eventoParticipantesMap;
	private Map<String, Map<String,String>> generoNumeroParticipantesMap;
	private Map<String, String> porcentajeAsistenciasEvtCapMap;
	private List<CatalogoComunDTO> catTipoAsistenciaList;
	private List<CatalogoComunDTO> catDestinatatiosList;

	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		logger.info("El ReporteEventosCapacitacionBean se esta inicializando");

		filtro = new EventoCapacitacionDTO();

		/** Obtiene la modalidad de los eventos de capacitacion **/
		modalidadEventoCapList = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG);

		/** Obtiene los estatus de los eventos de capacitacion **/
		/*
		 * Nota solo dejar los estatus de en ejecucion y concluido
		 */
		listaEstatusEC = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_ESTADO_EVENTO_CAPACITACION);

		esTablaResultadosVisible = Boolean.FALSE;
		
		/**
		 * Obtiene cat tipo de asistencia
		 */
		this.obtenerTiposAsistencia();

		/**
		 * Obtiene cat tipo destinatarios
		 */
		this.obtenerTipoDestinatatios();
		
		/**
		 * Inicializa Lista objetos visibles
		 */
		
		this.inicializaListaObjetosVisibles();
		
		this.generaEstructuraCatTpoCompetenciaPlan();
		this.generaCatEjesCapacitBusqueda();
		
	}

	private void inicializaListaObjetosVisibles(){
		setVisible(Arrays.asList(true,true,true,true,true,true,true,true,true,false,
				 false,false,false,false,false,false,false,false));		
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
	 * Metodo que crea los ejes de capacitacion apartir del tipo de competencia
	 * seleccionado
	 */
	public void onChangeTpoCompetencia(ValueChangeEvent e) {

		if (ObjectUtils.isNotNull(e.getNewValue())) {

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

	public void busquedaEventosCapacitacionCriterios() {
		logger.info("busquedaEventosCapacitacionCriterios");
		esTablaResultadosVisible = Boolean.TRUE;
		List<Integer> idEventosList;

		if (ObjectUtils.isNotNull(filtro)) {

			logger.info("tipo competencia " + filtro.getFichaDescriptivaPrograma().getTipoCompetencia());
			logger.info("eje capacitacion " + filtro.getFichaDescriptivaPrograma().getEjeCapacitacion());
			logger.info("Modalidad del evento    " + filtro.getCatModalidadPlanPrograma().getNombre());
			logger.info("Id de Modalidad del evento    " + filtro.getCatModalidadPlanPrograma().getId());
			logger.info("Estatus del evt cap  " + filtro.getCatEstadoEventoCapacitacion().getNombre());
			logger.info("Id del estatus de cap  " + filtro.getCatEstadoEventoCapacitacion().getId());
			logger.info("Tipo de busqueda de fechas   " + tipoDatoFechas);
			logger.info("fecha inicial    " + filtro.getFechaInicial());
			logger.info("fecha final      " + filtro.getFechaFinal());

			eventoCapacitacionDTOList = eventoCapacitacionServiceFacade.busquedaPorCriterios(filtro, tipoDatoFechas);
			this.asignaResponsablesProduccion(eventoCapacitacionDTOList);
			idEventosList = this.obtenerIdEventosCapacitacionDeListaDeEventosCapacitacion(eventoCapacitacionDTOList);
			eventoParticipantesMap = 
						this.obtenerParticipantesPorIdEventoCapacitacion(idEventosList);
			
			generoNumeroParticipantesMap = 
						this.obtenerGeneroParticipantesPorIdEventoCapacitacion(idEventosList);
			
			porcentajeAsistenciasEvtCapMap = 		 
					obtenerPorcentajeAsistenciasPorEventoCapacitacion(idEventosList);
			

			logger.info("lista size " + eventoCapacitacionDTOList.size());

		}

	}
	
	private Map<String,String> obtenerPorcentajeAsistenciasPorEventoCapacitacion(List<Integer> idEventoCapacitacionList){
		CatalogoComunDTO tipoAsistenciaDTO = 
		this.obtenerTipoAsistencia(TipoAsistenciaEnum.ASISTENCIA.getValor());
		return 				
			asistenciaFacadeService.obtenerPorcentajeAsistenciasPorEventoCapacitacion(idEventoCapacitacionList,
						tipoAsistenciaDTO.getId());
	
		}
	
	private CatalogoComunDTO obtenerTipoAsistencia (String tipoAsistencia){
		
		CatalogoComunDTO tipoAsistenciaDTO = null;
		for (CatalogoComunDTO catalogoComunDTO : catTipoAsistenciaList) {
			if(catalogoComunDTO.getNombre().equals(tipoAsistencia)){				
				tipoAsistenciaDTO = catalogoComunDTO;
			}
		}
		return tipoAsistenciaDTO;
	}
	
	@SuppressWarnings("unchecked")
	private void  obtenerTiposAsistencia(){
		catTipoAsistenciaList = 
				gestionEscolarServiceAdapter.getCatalogoServiceByGestionEscolarEnum
				(CatGestionEscolarEnum.CAT_ASISTENCIA).findAll(CatAsistencia.class);
				
	}
	
	@SuppressWarnings("unchecked")
	private void obtenerTipoDestinatatios(){
		catDestinatatiosList = 
				gestionEscolarServiceAdapter.getCatalogoServiceByGestionEscolarEnum
				(CatGestionEscolarEnum.CAT_DESTINATARIOS_EC).findAll(CatDestinatariosEc.class);
		
	}
	
	public String obtenerTipoDestinatario(String idDestinatario){
		
		String destinatario = null;
		for (CatalogoComunDTO destinatarioDto : catDestinatatiosList) {
			destinatario = destinatarioDto.getNombre();
		}
				
		return destinatario;
	}
	
	public String obtenerValorDeMapaPorcentajeAsistencia(String idEvento){
		return 
		porcentajeAsistenciasEvtCapMap.get(idEvento);
		
	}
	
	public String obtenerValorDeMapaNumeroParticipantes(String idEvento){
		Long value = 		
				eventoParticipantesMap.get(idEvento);						
		return ObjectUtils.isNotNull(value)?value.toString():null;
	}

	public String obtenerValorDeMapaGeneroNumeroParticipantes(String idEventoCapacitacion,String genero){
		String numeroParticipantes = null;
		Map<String,String> mapaGeneroParticipantes = 
		generoNumeroParticipantesMap.get(idEventoCapacitacion);
		if(ObjectUtils.isNotNull(mapaGeneroParticipantes)){
			numeroParticipantes = 
					mapaGeneroParticipantes.get(genero);
		}
		return numeroParticipantes;
	}
	
	private void asignaResponsablesProduccion(List<EventoCapacitacionDTO> eventoCapacitacionDTOList) {

		for (EventoCapacitacionDTO eventoCapacitacionDTO : eventoCapacitacionDTOList) {

			for (ReponsableProduccionEcDTO reponsableProduccionEcDTO : eventoCapacitacionDTO
					.getReponsableProduccionEcs()) {

				if (ConstantesGestor.TIPO_RESPONSABILIDAD_COORDINADOR_ACADEMICO.equals(reponsableProduccionEcDTO
						.getPersonaResponsabilidad().getCatTipoResponsabilidadEc().getNombre())) {
					eventoCapacitacionDTO.setResponsableCoordinadorAcademico(reponsableProduccionEcDTO);
				}

			}
		}
	}

	public String obtenerDirigidoA(Integer valor) {

		String strDirigidoa = ID_DIRIGIDO_A_NO_REGISTRADO;
		if (ObjectUtils.isNotNull(valor)) {

			if (TipoUsuarioEnum.INTERNO.getValor() == valor) {
				strDirigidoa = TipoUsuarioEnum.INTERNO.getDescripcion();

			} else if (TipoUsuarioEnum.EXTERNO.getValor() == valor) {
				strDirigidoa = TipoUsuarioEnum.EXTERNO.getDescripcion();
			}
		}
		return strDirigidoa;
	}

	private Map<String, Long> obtenerParticipantesPorIdEventoCapacitacion(List<Integer> idEventosList) {
		return asistenciaFacadeService.getGrupoParticipanteService().obtenerParticipantesPorIdEventoCapacitacion(idEventosList);
	}

	
	private Map<String,Map<String,String>> obtenerGeneroParticipantesPorIdEventoCapacitacion(List<Integer> idEventosList){
				
		return asistenciaFacadeService.getGrupoParticipanteService().obtenerGeneroParticipantesPorIdEventoCapacitacion(idEventosList);		
	}
	

	private List<Integer> obtenerIdEventosCapacitacionDeListaDeEventosCapacitacion(
			List<EventoCapacitacionDTO> eventoCapacitacionDTOList) {
		List<Integer> idEventosList = new ArrayList<Integer>();
		for (EventoCapacitacionDTO eventoCapacitacionDTO : eventoCapacitacionDTOList) {
			idEventosList.add(eventoCapacitacionDTO.getIdEvento());
		}

		logger.info("El tamanio de la lista de los id de evento de capacitacion es de " + idEventosList.size());
		return idEventosList;
	}

	/**
	 * Setter y getter
	 * 
	 * @return
	 */

	public EventoCapacitacionDTO getFiltro() {
		return filtro;
	}

	public void setFiltro(EventoCapacitacionDTO filtro) {
		this.filtro = filtro;
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

	public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
		return eventoCapacitacionServiceFacade;
	}

	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
	}

	public List<CatalogoComunDTO> getListaTiposCompetencias() {
		return listaTiposCompetencias;
	}

	public void setListaTiposCompetencias(List<CatalogoComunDTO> listaTiposCompetencias) {
		this.listaTiposCompetencias = listaTiposCompetencias;
	}

	public List<CatalogoComunDTO> getEjesCapacitacion() {
		return ejesCapacitacion;
	}

	public void setEjesCapacitacion(List<CatalogoComunDTO> ejesCapacitacion) {
		this.ejesCapacitacion = ejesCapacitacion;
	}

	public List<CatalogoComunDTO> getListaEjesCapacitacion() {
		return listaEjesCapacitacion;
	}

	public void setListaEjesCapacitacion(List<CatalogoComunDTO> listaEjesCapacitacion) {
		this.listaEjesCapacitacion = listaEjesCapacitacion;
	}

	public List<CatalogoComunDTO> getListaEstatusEC() {
		return listaEstatusEC;
	}

	public void setListaEstatusEC(List<CatalogoComunDTO> listaEstatusEC) {
		this.listaEstatusEC = listaEstatusEC;
	}

	public List<EventoCapacitacionDTO> getEventoCapacitacionDTOList() {
		return eventoCapacitacionDTOList;
	}

	public void setEventoCapacitacionDTOList(List<EventoCapacitacionDTO> eventoCapacitacionDTOList) {
		this.eventoCapacitacionDTOList = eventoCapacitacionDTOList;
	}

	public Boolean getEsTablaResultadosVisible() {
		return esTablaResultadosVisible;
	}

	public void setEsTablaResultadosVisible(Boolean esTablaResultadosVisible) {
		this.esTablaResultadosVisible = esTablaResultadosVisible;
	}

	public List<Boolean> getVisible() {
		return visible;
	}

	public void setVisible(List<Boolean> visible) {
		this.visible = visible;
	}

	public String getInformePredisenadoSeleccionado() {
		return informePredisenadoSeleccionado;
	}

	public void setInformePredisenadoSeleccionado(String informePredisenadoSeleccionado) {
		this.informePredisenadoSeleccionado = informePredisenadoSeleccionado;
	}

	public Map<String, Long> getEventoParticipantesMap() {
		return eventoParticipantesMap;
	}

	public void setEventoParticipantesMap(Map<String, Long> eventoParticipantesMap) {
		this.eventoParticipantesMap = eventoParticipantesMap;
	}

	public Map<String, Map<String, String>> getGeneroNumeroParticipantesMap() {
		return generoNumeroParticipantesMap;
	}

	public void setGeneroNumeroParticipantesMap(Map<String, Map<String, String>> generoNumeroParticipantesMap) {
		this.generoNumeroParticipantesMap = generoNumeroParticipantesMap;
	}

	public AsistenciaFacadeService getAsistenciaFacadeService() {
		return asistenciaFacadeService;
	}

	public void setAsistenciaFacadeService(AsistenciaFacadeService asistenciaFacadeService) {
		this.asistenciaFacadeService = asistenciaFacadeService;
	}

	public GestionEscolarServiceAdapter getGestionEscolarServiceAdapter() {
		return gestionEscolarServiceAdapter;
	}

	public void setGestionEscolarServiceAdapter(GestionEscolarServiceAdapter gestionEscolarServiceAdapter) {
		this.gestionEscolarServiceAdapter = gestionEscolarServiceAdapter;
	}

	public Map<String, String> getPorcentajeAsistenciasEvtCapMap() {
		return porcentajeAsistenciasEvtCapMap;
	}

	public void setPorcentajeAsistenciasEvtCapMap(Map<String, String> porcentajeAsistenciasEvtCapMap) {
		this.porcentajeAsistenciasEvtCapMap = porcentajeAsistenciasEvtCapMap;
	}
	
	

}
