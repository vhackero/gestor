package mx.gob.sedesol.gestorweb.beans.analisisdatos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.service.impl.analisisdatos.AnalisisDatosGestionEscolarFacade;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.NodoeHijosDTO;
import mx.gob.sedesol.gestorweb.commons.utils.ObjectUtils;

@ViewScoped
@ManagedBean
public class ReporteAvaBean extends BaseBean {

	private static final long serialVersionUID = -1851838647162481025L;

	@ManagedProperty("#{analisisDatosGestionEscolarFacade}")
	private AnalisisDatosGestionEscolarFacade analisisDatosGestionEscolarFacade;

	@ManagedProperty("#{eventoCapacitacionServiceFacade}")
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;

	private EventoCapacitacionDTO filtros;
	private List<CatalogoComunDTO> listaTiposCompetencias;
	private List<CatalogoComunDTO> listaEjesCapacitacion;
	private List<CatalogoComunDTO> listaModalidadesPrograma;
	private List<CatalogoComunDTO> ejesCapacitacion;
	private List<CatalogoComunDTO> listaEstatusEC;
	private List<CatalogoComunDTO> catNivelEnsenanzaProg;
	private List<EventoCapacitacionDTO> eventosCapacitacion;
	private NodoeHijosDTO estPlanSedesol;
	private List<Boolean> visible;
	private String tipoDatoFechas;

	public ReporteAvaBean() {
		filtros = new EventoCapacitacionDTO();
		FichaDescProgramaDTO programa = new FichaDescProgramaDTO();
		programa.setCatNivelEnsenanzaPrograma(new CatalogoComunDTO());
		filtros.setFichaDescriptivaPrograma(programa);
		setListaTiposCompetencias(new ArrayList<>());

	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		listaEstatusEC = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_ESTADO_EVENTO_CAPACITACION);
		setCatNivelEnsenanzaProg((List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_NIVEL_ENSE_PLAN_PROG));
		setListaModalidadesPrograma((List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG));
		generaEstructuraCatTpoCompetenciaPlan();
		generaCatEjesCapacitBusqueda();
		setVisible(Arrays.asList(true, true, true, true, true, true, true, true, false, false, false, false, false));

	}

	public void buscarEventosCapacitacion() {

		eventosCapacitacion = eventoCapacitacionServiceFacade.busquedaPorCriterios(filtros, tipoDatoFechas);
		validarFechaInicialFinal();

	}
	

	private void validarFechaInicialFinal() {
		if (!ObjectUtils.isNullOrEmpty(tipoDatoFechas)) {

			if (ObjectUtils.isNull(filtros.getFechaInicial()) || ObjectUtils.isNull(filtros.getFechaFinal())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Datos requeridos:", "La fecha inicial y final son requeridas"));

			}
		}

	}

	public void limpiarCampos() {

		filtros.getFichaDescriptivaPrograma().setTipoCompetencia(null);
		filtros.getFichaDescriptivaPrograma().setEjeCapacitacion(null);
		filtros.setCatModalidadPlanPrograma(null);
		filtros.getFichaDescriptivaPrograma().getCatNivelEnsenanzaPrograma().setId(null);;
		filtros.setCatEstadoEventoCapacitacion(null);
		tipoDatoFechas = null;
		filtros.setFechaInicial(null);
		filtros.setFechaFinal(null);
		eventosCapacitacion = new ArrayList<>();

	}
	
	public void onToggle(ToggleEvent e) {
		visible.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}
	
	
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
	 * 
	 * @param idEjeCap
	 * @return
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
	 * Metodo que extrae los datos para mostrar el tipo de competencia
	 */
	private void generaEstructuraCatTpoCompetenciaPlan() {

		List<NodoeHijosDTO> planes = new ArrayList<>();
		List<MallaCurricularDTO> mallas = new ArrayList<>();

		MallaCurricularDTO mallaSedesol = getAnalisisDatosGestionEscolarFacade().obtenerMallaCurricular();
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

		setListaTiposCompetencias(new ArrayList<>());

		// Genera el Catalogo Tipo de Competencia
		setEstPlanSedesol(planes.get(ConstantesGestorWeb.INDICE_INICIAL));
		for (NodoeHijosDTO nh : getEstPlanSedesol().getNodosHijos()) {
			CatalogoComunDTO cc = new CatalogoComunDTO();
			cc.setId(nh.getIdNodo());
			cc.setNombre(nh.getNombre());
			getListaTiposCompetencias().add(cc);
		}
	}

	/**
	 * Metodo que sirve para extraer los ejes de capacitacion
	 */
	private void generaCatEjesCapacitBusqueda() {
		setEjesCapacitacion(new ArrayList<>());

		for (NodoeHijosDTO nh : getEstPlanSedesol().getNodosHijos()) {
			for (NodoeHijosDTO nint : nh.getNodosHijos()) {
				CatalogoComunDTO cc = new CatalogoComunDTO();
				cc.setId(nint.getIdNodo());
				cc.setNombre(nint.getNombre());
				getEjesCapacitacion().add(cc);
			}
		}
	}

	public EventoCapacitacionDTO getFiltros() {
		return filtros;
	}

	public void setFiltros(EventoCapacitacionDTO filtros) {
		this.filtros = filtros;
	}

	/**
	 * Metodo que crea los ejes de capacitacion apartir del tipo de competencia
	 * seleccionado
	 */
	public void onChangeTpoCompetencia(ValueChangeEvent e) {

		if (ObjectUtils.isNotNull(e.getNewValue())) {

			Integer idTpoCompSel = (Integer) e.getNewValue();
			filtros.getFichaDescriptivaPrograma().setTipoCompetencia(idTpoCompSel);
			setListaEjesCapacitacion(new ArrayList<>());

			for (NodoeHijosDTO nh : getEstPlanSedesol().getNodosHijos()) {
				if (nh.getIdNodo().equals(idTpoCompSel)) {
					for (NodoeHijosDTO nint : nh.getNodosHijos()) {

						CatalogoComunDTO cc = new CatalogoComunDTO();
						cc.setId(nint.getIdNodo());
						cc.setNombre(nint.getNombre());
						getListaEjesCapacitacion().add(cc);
					}
				}
			}
		}

	}

	public List<CatalogoComunDTO> getListaTiposCompetencias() {
		return listaTiposCompetencias;
	}

	public void setListaTiposCompetencias(List<CatalogoComunDTO> listaTiposCompetencias) {
		this.listaTiposCompetencias = listaTiposCompetencias;
	}

	public List<CatalogoComunDTO> getListaEjesCapacitacion() {
		return listaEjesCapacitacion;
	}

	public void setListaEjesCapacitacion(List<CatalogoComunDTO> listaEjesCapacitacion) {
		this.listaEjesCapacitacion = listaEjesCapacitacion;
	}

	public List<CatalogoComunDTO> getListaModalidadesPrograma() {
		return listaModalidadesPrograma;
	}

	public void setListaModalidadesPrograma(List<CatalogoComunDTO> listaModalidadesPrograma) {
		this.listaModalidadesPrograma = listaModalidadesPrograma;
	}

	public List<CatalogoComunDTO> getEjesCapacitacion() {
		return ejesCapacitacion;
	}

	public void setEjesCapacitacion(List<CatalogoComunDTO> ejesCapacitacion) {
		this.ejesCapacitacion = ejesCapacitacion;
	}

	public NodoeHijosDTO getEstPlanSedesol() {
		return estPlanSedesol;
	}

	public void setEstPlanSedesol(NodoeHijosDTO estPlanSedesol) {
		this.estPlanSedesol = estPlanSedesol;
	}

	public List<Boolean> getVisible() {
		return visible;
	}

	public void setVisible(List<Boolean> visible) {
		this.visible = visible;
	}

	public List<CatalogoComunDTO> getListaEstatusEC() {
		return listaEstatusEC;
	}

	public void setListaEstatusEC(List<CatalogoComunDTO> listaEstatusEC) {
		this.listaEstatusEC = listaEstatusEC;
	}

	public AnalisisDatosGestionEscolarFacade getAnalisisDatosGestionEscolarFacade() {
		return analisisDatosGestionEscolarFacade;
	}

	public void setAnalisisDatosGestionEscolarFacade(
			AnalisisDatosGestionEscolarFacade analisisDatosGestionEscolarFacade) {
		this.analisisDatosGestionEscolarFacade = analisisDatosGestionEscolarFacade;
	}

	public String getTipoDatoFechas() {
		return tipoDatoFechas;
	}

	public void setTipoDatoFechas(String tipoDatoFechas) {
		this.tipoDatoFechas = tipoDatoFechas;
	}

	public List<EventoCapacitacionDTO> getEventosCapacitacion() {
		return eventosCapacitacion;
	}

	public void setEventosCapacitacion(List<EventoCapacitacionDTO> eventosCapacitacion) {
		this.eventosCapacitacion = eventosCapacitacion;
	}

	public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
		return eventoCapacitacionServiceFacade;
	}

	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
	}

	public List<CatalogoComunDTO> getCatNivelEnsenanzaProg() {
		return catNivelEnsenanzaProg;
	}

	public void setCatNivelEnsenanzaProg(List<CatalogoComunDTO> catNivelEnsenanzaProg) {
		this.catNivelEnsenanzaProg = catNivelEnsenanzaProg;
	}

}
