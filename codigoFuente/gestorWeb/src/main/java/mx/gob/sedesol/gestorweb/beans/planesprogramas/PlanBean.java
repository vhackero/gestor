package mx.gob.sedesol.gestorweb.beans.planesprogramas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanAptitudDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanHabilidadDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.PlanServiceFacade;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;

@ViewScoped
@ManagedBean
public class PlanBean extends BaseBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PlanBean.class);

	@ManagedProperty(value = "#{planServiceFacade}")
	private PlanServiceFacade planServiceFacade;

	@ManagedProperty("#{orgGubernamentalService}")
	private OrgGubernamentalService orgGubernamentalService;

	@ManagedProperty("#{bitacoraService}")
	private BitacoraService bitacoraService;

	@ManagedProperty("#{bitacoraBean}")
	private BitacoraBean bitacoraBean;

	private List<CatalogoComunDTO> catTpoPlan;
	private List<CatalogoComunDTO> catModalidadPlan;
	private List<CatalogoComunDTO> catalEstatusPlan;
	private List<CatalogoComunDTO> catAlcancePlan;
	private List<CatalogoComunDTO> catNivelEnsPlan;
	private List<CatalogoComunDTO> catCompPlan;
	private List<CatalogoComunDTO> catConocimientosPlan;
	private List<CatalogoComunDTO> catAptitutesPlan;
	private List<CatalogoComunDTO> catHabilidadesPlan;
	private List<CatalogoComunDTO> catDocsExpidePlan;

	private List<SelectItem> itemsOrgGubs;

	private List<String> conocimsPlanSelec;
	private List<String> habilidadesPlanSelec;
	private List<String> aptitudesPlanSelec;
	private List<OrgGubernamentalDTO> catOrgGubs;

	private List<PlanDTO> planesFiltrados;

	private PlanDTO filtroPlan;
	private PlanDTO planSelecBusq;
	private PlanDTO plan;
	private boolean edicionPlan;

	public PlanBean() {
		initRecursos();
		edicionPlan = Boolean.parseBoolean(getRequest().getParameter("edicion"));
	}

	@PostConstruct
	public void flujoNegocio() {
		catOrgGubs = orgGubernamentalService.obtenerOrgGubsPadres();

		this.generaItemsOrgGubs();
		// Flujo Editar plan
		if (isEdicionPlan()) {

			plan = (PlanDTO) getSession().getAttribute(ConstantesGestorWeb.OBJ_PLAN_SELEC);
			this.llenaRelacionesPlanSel(plan);
			if (ObjectUtils.isNotNull(plan)) {
				getSession().removeAttribute(ConstantesGestorWeb.OBJ_PLAN_SELEC);
			}

		} else {
			// Flujo Nuevo Plan
			plan = new PlanDTO();
			plan.setCatTipoPlan(new CatalogoComunDTO());
			plan.setCatAlcancePlan(new CatalogoComunDTO());
			plan.setCatCompetenciasPlan(new CatalogoComunDTO());
			plan.setCatDocumentosExpidePlan(new CatalogoComunDTO());
			plan.setCatEstatusPlan(new CatalogoComunDTO());
			plan.setCatModalidadPlanPrograma(new CatalogoComunDTO());
			plan.setCatNivelEnsenanzaPrograma(new CatalogoComunDTO());
			plan.setTblOrganismoGubernamental(new OrgGubernamentalDTO());
			plan.setIdentificador(planServiceFacade.generaIdentificadorPlan());

			filtroPlan = new PlanDTO();
			setEdicionPlan(Boolean.FALSE);
		}
	}

	/**
	 * Genera la lista de relaciones seleccionadas, para la edición del Plan
	 *
	 * @param plan
	 */
	private void llenaRelacionesPlanSel(PlanDTO plan) {
		if (ObjectUtils.isNotNull(plan)) {
			if (!ObjectUtils.isNullOrEmpty(plan.getRelPlanConocimientos())) {
				conocimsPlanSelec = new ArrayList<>();
				for (RelPlanConocimientoDTO conoc : plan.getRelPlanConocimientos()) {
					conocimsPlanSelec.add(conoc.getCatAreasConocimiento().getId().toString());
				}
			}

			if (!ObjectUtils.isNullOrEmpty(plan.getRelPlanAptitudes())) {
				aptitudesPlanSelec = new ArrayList<>();
				for (RelPlanAptitudDTO relApt : plan.getRelPlanAptitudes()) {
					aptitudesPlanSelec.add(relApt.getCatAptitudesPlan().getId().toString());
				}
			}

			if (!ObjectUtils.isNullOrEmpty(plan.getRelPlanHabilidades())) {
				habilidadesPlanSelec = new ArrayList<>();
				for (RelPlanHabilidadDTO relApt : plan.getRelPlanHabilidades()) {
					habilidadesPlanSelec.add(relApt.getCatHabilidadesPlan().getId().toString());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void initRecursos() {

		plan = new PlanDTO();
		logger.debug("Consultando catalogos de planes desde sesion");

		catTpoPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_TPO_PLAN);
		catModalidadPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG);
		catalEstatusPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_ESTATUS_PLAN);
		catAlcancePlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_ALCANCE_PLAN);
		catNivelEnsPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_NIVEL_ENSE_PLAN_PROG);
		catConocimientosPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_CONOCIMIENTOS_PLAN);
		catHabilidadesPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_HABILIDADES_PLAN);
		catAptitutesPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_APTITUDES_PLAN);
		catCompPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_COMPETENCIAS_PLAN);
		catDocsExpidePlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_DOCS_EXPIDE_PLAN);

	}

	/**
	 * Se genera componente que agrupa los organismos gubernamentales en Segundo
	 * nivel
	 */
	private void generaItemsOrgGubs() {

		if (ObjectUtils.isNotNull(catOrgGubs)) {

			itemsOrgGubs = new ArrayList<>();

			for (OrgGubernamentalDTO orgG : catOrgGubs) {
				logger.info(""+orgG.getDescripcion());

				SelectItemGroup group = new SelectItemGroup(orgG.getNombre());
				SelectItem[] arrayItems = null;

				if (!orgG.getLstHijosOrgGub().isEmpty()) {

					arrayItems = new SelectItem[orgG.getLstHijosOrgGub().size()];
					int i = 0;
					
					logger.info(""+orgG.getLstHijosOrgGub().size());

					for (OrgGubernamentalDTO orgGHijo : orgG.getLstHijosOrgGub()) {
						arrayItems[i] = new SelectItem(orgGHijo, orgGHijo.getNombre());
						i++;
					}
				}
				
				logger.info(arrayItems);

				group.setSelectItems(arrayItems);
				itemsOrgGubs.add(group);
			}
		}
	}

	public void onChangeModalidadPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatModalidadPlanPrograma(this.getValorDeCatalogo(catModalidadPlan, ((Integer) e.getNewValue())));
		}
	}

	public void onChangeTpoPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatTipoPlan(this.getValorDeCatalogo(catTpoPlan, ((Integer) e.getNewValue())));
		}
	}

	public void onChangeNvEnsePlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatNivelEnsenanzaPrograma(this.getValorDeCatalogo(catNivelEnsPlan, ((Integer) e.getNewValue())));
		}
	}

	public void onChangeCompetenciaPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatCompetenciasPlan(this.getValorDeCatalogo(catCompPlan, ((Integer) e.getNewValue())));
		}
	}

	public void onChangeAlcancePlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatAlcancePlan(this.getValorDeCatalogo(catAlcancePlan, ((Integer) e.getNewValue())));
		}
	}

	public void onChangeEstatusPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatEstatusPlan(this.getValorDeCatalogo(catalEstatusPlan, ((Integer) e.getNewValue())));
		}
	}

	public void onChangeDocExpide(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatDocumentosExpidePlan(this.getValorDeCatalogo(catDocsExpidePlan, ((Integer) e.getNewValue())));
		}
	}

	public void onChangeOrgGubPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			for (OrgGubernamentalDTO orgGub : catOrgGubs) {
				for (OrgGubernamentalDTO orgGubHijo : orgGub.getLstHijosOrgGub()) {
					if (orgGubHijo.getId().equals((Integer) e.getNewValue())) {
						plan.setTblOrganismoGubernamental(orgGubHijo);
					}
				}
			}
		}
	}

	/**
	 * Metodo para clonar el objeto plan
	 */
	public void clonarPlan() {
		if (ObjectUtils.isNotNull(planSelecBusq)) {
			// PlanDTO clon = planSelecBusq.cl
			PlanDTO clonPlan = SerializationUtils.clone(planSelecBusq);

			clonPlan.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
			ResultadoDTO<PlanDTO> res = getPlanServiceFacade().clonarPlan(clonPlan);
			if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CLO_PLA",
						String.valueOf(planSelecBusq.getIdPlan()), requestActual(), TipoServicioEnum.LOCAL);

				agregarMsgInfo("Se clonó correctamente el plan", null);
			} else {
				agregarMsgError("Ocurrió un error", null);
			}

		}
	}

	/**
	 *
	 * @return
	 */
	public String navegaCrearNuevoPlan() {
		plan = new PlanDTO();
		return ConstantesGestorWeb.NAVEGA_NUEVO_PLAN;
	}

	/**
	 * Navega a la pantalla de estructuras del plan
	 *
	 * @return
	 */
	public String navegaEstructuraPlan() {
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_PLA",
				String.valueOf(planSelecBusq.getIdPlan()), requestActual(), TipoServicioEnum.LOCAL);
		getSession().setAttribute(ConstantesGestorWeb.OBJ_PLAN_SELEC, planSelecBusq);
		// planProgBean.setPlanSelec(planSelecBusq);
		return ConstantesGestorWeb.NAVEGA_PLAN_PROGRAMA_MALLACURRICULAR;
	}

	/**
	 * Realiza la busqueda de planes de acuerdo al filtro
	 */
	public void buscarPlanes() {

		if (ObjectUtils.isNotNull(filtroPlan)) {
			logger.debug("Comienza busqueda de planes");
			planesFiltrados = getPlanServiceFacade().getPlanService().buscaPlanesPorCriterios(filtroPlan);

			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_PLA", "",
					requestActual(), TipoServicioEnum.LOCAL);
			/**
			 * Despues de la busqueda se limpian los criterios de busqueda de la
			 * vista*
			 */
			filtroPlan.setNombre(StringUtils.EMPTY);
			filtroPlan.setTblOrganismoGubernamental(null);
			filtroPlan.setCatModalidadPlanPrograma(new CatalogoComunDTO());
			filtroPlan.setCatTipoPlan(new CatalogoComunDTO());
			filtroPlan.setCatAlcancePlan(new CatalogoComunDTO());
			filtroPlan.setCatEstatusPlan(new CatalogoComunDTO());

		}
	}

	/**
	 * Metodo persistente para guardar los datos del Plan
	 */
	public void guardaNuevoPlan() {
		logger.info("########## PERSISTENCIA DEl PLAN ########");
		plan.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		ResultadoDTO<PlanDTO> resultado = planServiceFacade.guardaNuevoPlan(plan,
				this.obtieneListaCatalogoComun(habilidadesPlanSelec, ConstantesGestorWeb.CAT_HABILIDADES_PLAN),
				this.obtieneListaCatalogoComun(aptitudesPlanSelec, ConstantesGestorWeb.CAT_APTITUDES_PLAN),
				this.obtieneListaCatalogoComun(conocimsPlanSelec, ConstantesGestorWeb.CAT_CONOCIMIENTOS_PLAN));

		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_PLA",
					String.valueOf(resultado.getDto().getIdPlan()), requestActual(), TipoServicioEnum.LOCAL);
			agregarMsgInfo("Se guardo correctamente el plan", null);
		} else {
			agregarMsgError("Ocurrio un error", null);
		}
	}

	/**
	 *
	 */
	public void editarPlan() {
		logger.info("########## EDITANDO DEl PLAN ########");
		plan.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		ResultadoDTO<PlanDTO> resultado = planServiceFacade.editarPlan(plan,
				this.obtieneListaCatalogoComun(habilidadesPlanSelec, ConstantesGestorWeb.CAT_HABILIDADES_PLAN),
				this.obtieneListaCatalogoComun(aptitudesPlanSelec, ConstantesGestorWeb.CAT_APTITUDES_PLAN),
				this.obtieneListaCatalogoComun(conocimsPlanSelec, ConstantesGestorWeb.CAT_CONOCIMIENTOS_PLAN));
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_PLA",
					String.valueOf(resultado.getDto().getIdPlan()), requestActual(), TipoServicioEnum.LOCAL);
			agregarMsgInfo("Se edito correctamente el plan", null);
		} else {
			agregarMsgError("Ocurrio un error", null);
		}
	}

	/**
	 * Navega a la edición del plan
	 */
	public void navegaEditarPlan() {
		getSession().setAttribute(ConstantesGestorWeb.OBJ_PLAN_SELEC, planSelecBusq);
		try {
			getFacesContext().getExternalContext().redirect("nuevoPlan.jsf?edicion=true");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String regresaBusquedaPlanes() {
		this.buscarPlanes();
		return ConstantesGestorWeb.NAVEGA_BUSQUEDA_PLANES;
	}

	/**
	 *
	 * @param listIdsCat
	 * @param identificadorCat
	 * @return
	 */
	private List<CatalogoComunDTO> obtieneListaCatalogoComun(List<String> listIdsCat, String identificadorCat) {

		List<CatalogoComunDTO> cat = null;
		if (ObjectUtils.isNotNull(listIdsCat)) {
			cat = new ArrayList<>();

			if (identificadorCat.equalsIgnoreCase(ConstantesGestorWeb.CAT_CONOCIMIENTOS_PLAN)) {
				for (String idReg : listIdsCat) {
					cat.add(getValorDeCatalogo(catConocimientosPlan, Integer.parseInt(idReg)));
				}
			}

			if (identificadorCat.equalsIgnoreCase(ConstantesGestorWeb.CAT_HABILIDADES_PLAN)) {
				for (String idReg : listIdsCat) {
					cat.add(getValorDeCatalogo(catHabilidadesPlan, Integer.parseInt(idReg)));
				}
			}

			if (identificadorCat.equalsIgnoreCase(ConstantesGestorWeb.CAT_APTITUDES_PLAN)) {
				for (String idReg : listIdsCat) {
					cat.add(getValorDeCatalogo(catAptitutesPlan, Integer.parseInt(idReg)));
				}
			}

		}

		return cat;
	}

	/**
	 * @return the planServiceFacade
	 */
	public PlanServiceFacade getPlanServiceFacade() {
		if (ObjectUtils.isNull(planServiceFacade)) {
			this.planServiceFacade = new PlanServiceFacade();
		}

		return planServiceFacade;
	}

	/**
	 * @param planServiceFacade
	 *            the planServiceFacade to set
	 */
	public void setPlanServiceFacade(PlanServiceFacade planServiceFacade) {
		this.planServiceFacade = planServiceFacade;
	}

	/**
	 * @return the filtroPlan
	 */
	public PlanDTO getFiltroPlan() {
		return filtroPlan;
	}

	/**
	 * @param filtroPlan
	 *            the filtroPlan to set
	 */
	public void setFiltroPlan(PlanDTO filtroPlan) {
		this.filtroPlan = filtroPlan;
	}

	/**
	 * @return the catTpoPlan
	 */
	public List<CatalogoComunDTO> getCatTpoPlan() {
		return catTpoPlan;
	}

	/**
	 * @param catTpoPlan
	 *            the catTpoPlan to set
	 */
	public void setCatTpoPlan(List<CatalogoComunDTO> catTpoPlan) {
		this.catTpoPlan = catTpoPlan;
	}

	/**
	 * @return the catModalidadPlan
	 */
	public List<CatalogoComunDTO> getCatModalidadPlan() {
		return catModalidadPlan;
	}

	/**
	 * @param catModalidadPlan
	 *            the catModalidadPlan to set
	 */
	public void setCatModalidadPlan(List<CatalogoComunDTO> catModalidadPlan) {
		this.catModalidadPlan = catModalidadPlan;
	}

	/**
	 * @return the catAlcancePlan
	 */
	public List<CatalogoComunDTO> getCatAlcancePlan() {
		return catAlcancePlan;
	}

	/**
	 * @param catAlcancePlan
	 *            the catAlcancePlan to set
	 */
	public void setCatAlcancePlan(List<CatalogoComunDTO> catAlcancePlan) {
		this.catAlcancePlan = catAlcancePlan;
	}

	/**
	 * @return the catalEstatusPlan
	 */
	public List<CatalogoComunDTO> getCatalEstatusPlan() {
		return catalEstatusPlan;
	}

	/**
	 * @param catalEstatusPlan
	 *            the catalEstatusPlan to set
	 */
	public void setCatalEstatusPlan(List<CatalogoComunDTO> catalEstatusPlan) {
		this.catalEstatusPlan = catalEstatusPlan;
	}

	/**
	 * @return the itemsOrgGubs
	 */
	public List<SelectItem> getItemsOrgGubs() {
		return itemsOrgGubs;
	}

	/**
	 * @param itemsOrgGubs
	 *            the itemsOrgGubs to set
	 */
	public void setItemsOrgGubs(List<SelectItem> itemsOrgGubs) {
		this.itemsOrgGubs = itemsOrgGubs;
	}

	/**
	 * @return the planesFiltrados
	 */
	public List<PlanDTO> getPlanesFiltrados() {
		return planesFiltrados;
	}

	/**
	 * @param planesFiltrados
	 *            the planesFiltrados to set
	 */
	public void setPlanesFiltrados(List<PlanDTO> planesFiltrados) {
		this.planesFiltrados = planesFiltrados;
	}

	/**
	 * @return the planSelecBusq
	 */
	public PlanDTO getPlanSelecBusq() {
		return planSelecBusq;
	}

	/**
	 * @param planSelecBusq
	 *            the planSelecBusq to set
	 */
	public void setPlanSelecBusq(PlanDTO planSelecBusq) {
		this.planSelecBusq = planSelecBusq;
	}

	/**
	 * @return the plan
	 */
	public PlanDTO getPlan() {
		return plan;
	}

	/**
	 * @param plan
	 *            the plan to set
	 */
	public void setPlan(PlanDTO plan) {
		this.plan = plan;
	}

	/**
	 * @return the catNivelEnsPlan
	 */
	public List<CatalogoComunDTO> getCatNivelEnsPlan() {
		return catNivelEnsPlan;
	}

	/**
	 * @param catNivelEnsPlan
	 *            the catNivelEnsPlan to set
	 */
	public void setCatNivelEnsPlan(List<CatalogoComunDTO> catNivelEnsPlan) {
		this.catNivelEnsPlan = catNivelEnsPlan;
	}

	/**
	 * @return the catCompPlan
	 */
	public List<CatalogoComunDTO> getCatCompPlan() {
		return catCompPlan;
	}

	/**
	 * @param catCompPlan
	 *            the catCompPlan to set
	 */
	public void setCatCompPlan(List<CatalogoComunDTO> catCompPlan) {
		this.catCompPlan = catCompPlan;
	}

	/**
	 * @return the catConocimientosPlan
	 */
	public List<CatalogoComunDTO> getCatConocimientosPlan() {
		return catConocimientosPlan;
	}

	/**
	 * @param catConocimientosPlan
	 *            the catConocimientosPlan to set
	 */
	public void setCatConocimientosPlan(List<CatalogoComunDTO> catConocimientosPlan) {
		this.catConocimientosPlan = catConocimientosPlan;
	}

	/**
	 * @return the catAptitutesPlan
	 */
	public List<CatalogoComunDTO> getCatAptitutesPlan() {
		return catAptitutesPlan;
	}

	/**
	 * @param catAptitutesPlan
	 *            the catAptitutesPlan to set
	 */
	public void setCatAptitutesPlan(List<CatalogoComunDTO> catAptitutesPlan) {
		this.catAptitutesPlan = catAptitutesPlan;
	}

	/**
	 * @return the catHabilidadesPlan
	 */
	public List<CatalogoComunDTO> getCatHabilidadesPlan() {
		return catHabilidadesPlan;
	}

	/**
	 * @param catHabilidadesPlan
	 *            the catHabilidadesPlan to set
	 */
	public void setCatHabilidadesPlan(List<CatalogoComunDTO> catHabilidadesPlan) {
		this.catHabilidadesPlan = catHabilidadesPlan;
	}

	/**
	 * @return the catDocsExpidePlan
	 */
	public List<CatalogoComunDTO> getCatDocsExpidePlan() {
		return catDocsExpidePlan;
	}

	/**
	 * @param catDocsExpidePlan
	 *            the catDocsExpidePlan to set
	 */
	public void setCatDocsExpidePlan(List<CatalogoComunDTO> catDocsExpidePlan) {
		this.catDocsExpidePlan = catDocsExpidePlan;
	}

	/**
	 * @return the conocimsPlanSelec
	 */
	public List<String> getConocimsPlanSelec() {
		return conocimsPlanSelec;
	}

	/**
	 * @param conocimsPlanSelec
	 *            the conocimsPlanSelec to set
	 */
	public void setConocimsPlanSelec(List<String> conocimsPlanSelec) {
		this.conocimsPlanSelec = conocimsPlanSelec;
	}

	/**
	 * @return the habilidadesPlanSelec
	 */
	public List<String> getHabilidadesPlanSelec() {
		return habilidadesPlanSelec;
	}

	/**
	 * @param habilidadesPlanSelec
	 *            the habilidadesPlanSelec to set
	 */
	public void setHabilidadesPlanSelec(List<String> habilidadesPlanSelec) {
		this.habilidadesPlanSelec = habilidadesPlanSelec;
	}

	/**
	 * @return the aptitudesPlanSelec
	 */
	public List<String> getAptitudesPlanSelec() {
		return aptitudesPlanSelec;
	}

	/**
	 * @param aptitudesPlanSelec
	 *            the aptitudesPlanSelec to set
	 */
	public void setAptitudesPlanSelec(List<String> aptitudesPlanSelec) {
		this.aptitudesPlanSelec = aptitudesPlanSelec;
	}

	/**
	 * @return the edicionPlan
	 */
	public boolean isEdicionPlan() {
		return edicionPlan;
	}

	/**
	 * @param edicionPlan
	 *            the edicionPlan to set
	 */
	public void setEdicionPlan(boolean edicionPlan) {
		this.edicionPlan = edicionPlan;
	}

	/**
	 * @return the catOrgGubs
	 */
	public List<OrgGubernamentalDTO> getCatOrgGubs() {
		return catOrgGubs;
	}

	/**
	 * @param catOrgGubs
	 *            the catOrgGubs to set
	 */
	public void setCatOrgGubs(List<OrgGubernamentalDTO> catOrgGubs) {
		this.catOrgGubs = catOrgGubs;
	}

	public OrgGubernamentalService getOrgGubernamentalService() {
		return orgGubernamentalService;
	}

	public void setOrgGubernamentalService(OrgGubernamentalService orgGubernamentalService) {
		this.orgGubernamentalService = orgGubernamentalService;
	}

	public BitacoraBean getBitacoraBean() {
		return bitacoraBean;
	}

	public void setBitacoraBean(BitacoraBean bitacoraBean) {
		this.bitacoraBean = bitacoraBean;
	}

	public BitacoraService getBitacoraService() {
		return bitacoraService;
	}

	public void setBitacoraService(BitacoraService bitacoraService) {
		this.bitacoraService = bitacoraService;
	}

}
