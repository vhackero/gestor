package mx.gob.sedesol.gestorweb.beans.planesprogramas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.*;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoServicioEnum;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.PlanServiceFacade;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaPlanService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.administracion.BitacoraBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;

import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;

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

	@ManagedProperty("#{mallaPlanService}")
	private MallaPlanService mallaPlanService;

	private List<CatalogoComunDTO> catPeriodo;
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

	private List<CatalogoComunDTO> catCreditosPlan;
	private List<CatalogoComunDTO> catDivisionesPlan;
	private List<CatalogoComunDTO> catTipoCompetencia;
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
	private boolean creditos;
	private boolean tipoPlan;

	private Integer elementsStruc = 1;
	private String nameStruc = "";
	private Integer subStrucLvl = 0;
	private ArrayList<String> namesSubStruc = new ArrayList<>();
	private ArrayList<String> elementsSubStruc = new ArrayList<>();

	private RelMallaPlanDTO mallaPlan;
	
	private TreeNode arbolConocimientos;
	private TreeNode[] conocimientos;
	private TreeNode arbolHabilidades;
	private TreeNode[] habilidades;
	private TreeNode arbolAptitudes;
	private TreeNode[] aptitudes;

	@ManagedProperty(value = "#{fecServiceFacade}")
	private FECServiceFacade fecServiceFacade;

	public PlanBean() {
		initRecursos();
		edicionPlan = Boolean.parseBoolean(getRequest().getParameter("edicion"));

		namesSubStruc.add("");
		elementsSubStruc.add("1");
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
				mallaPlan = mallaPlanService.findByIdPlan(plan.getIdPlan());
			}

		} else {
			// Flujo Nuevo Plan
			plan = new PlanDTO();
			  
			plan.setCatTipoPlan(getCat(catTpoPlan, "Por Periodo"));
			plan.setCatPeriodo(getCat(catPeriodo, "Semestral"));
			plan.setCatCreditosPlan(getCat(catCreditosPlan, "Obligatorio"));
			setCreditos(Boolean.TRUE);
			plan.setHorasCredito(0);
			plan.setCatCompetenciasPlan(getCat(catCompPlan, "Opcional"));
			plan.setCatDocumentosExpidePlan(getCat(catDocsExpidePlan, "Certificado"));
			
			plan.setTblOrganismoGubernamental(new OrgGubernamentalDTO());
			for(OrgGubernamentalDTO orgG : catOrgGubs) {
				if (!orgG.getLstHijosOrgGub().isEmpty()) {
					for (OrgGubernamentalDTO orgGHijo : orgG.getLstHijosOrgGub()) {
						if(orgGHijo.getNombre().equals("UnADM")){
							plan.setTblOrganismoGubernamental(orgGHijo);
							break;
						}
					}
				}
				if(orgG.getNombre().equals("UnADM")){
					plan.setTblOrganismoGubernamental(orgG);
					break;
				}
			}
			
			plan.setCatAlcancePlan(getCat(catAlcancePlan, "Publico"));
			plan.setCatEstatusPlan(new CatalogoComunDTO());
			plan.setCatModalidadPlanPrograma(new CatalogoComunDTO());
			plan.setCatNivelEnsenanzaPrograma(new CatalogoComunDTO());
			plan.setCatDivisionesPlan(new CatalogoComunDTO());
			plan.setCatTipoCompetencia(this.getValorDeCatalogo(catTipoCompetencia, 1));
			plan.setPonderacion(true);

			filtroPlan = new PlanDTO();
			setEdicionPlan(Boolean.FALSE);

			mallaPlan = new RelMallaPlanDTO();
			mallaPlan.setElementosEstructuras(1);
			mallaPlan.setElementosSubestructuras1(1);
			mallaPlan.setElementosSubestructuras2(1);
			mallaPlan.setElementosSubestructuras3(1);
			mallaPlan.setNiveles(0);
			
			mallaPlan.setNombreEstructuras("");
			mallaPlan.setNombreSubestructuras1("");
			mallaPlan.setNombreSubestructuras2("");
			mallaPlan.setNombreSubestructuras3("");
		}
	}
	
	public CatalogoComunDTO getCat(List<CatalogoComunDTO> catLista, String nombre){
		CatalogoComunDTO catEncontrado = new CatalogoComunDTO();
		
		for(CatalogoComunDTO cat : catLista) {
			if(cat.getNombre().equals(nombre)){
				catEncontrado = cat;
				break;
			}
		}
		
		return catEncontrado;
	}

	/**
	 * Genera la lista de relaciones seleccionadas, para la edición del Plan
	 *
	 * @param plan
	 */
	private void llenaRelacionesPlanSel(PlanDTO plan) {
		if (ObjectUtils.isNotNull(plan)) {

			if (!ObjectUtils.isNullOrEmpty(plan.getRelPlanConocimientos())) {
				int indexCmpsEsp = 0;
				conocimientos = new TreeNode[plan.getRelPlanConocimientos().size()];
				List<Integer> comEspecifIds = plan.getRelPlanConocimientos().stream()
						.map(c -> c.getIdAreaConocimiento()).collect(Collectors.toList());
				obtieneValorCompEspecifSelec(arbolConocimientos, comEspecifIds, conocimientos, indexCmpsEsp);
				
				conocimsPlanSelec = new ArrayList<>();
				for (RelPlanConocimientoDTO conoc : plan.getRelPlanConocimientos()) {
					conocimsPlanSelec.add(conoc.getCatAreasConocimiento().getId().toString());
				}
			}

			if (!ObjectUtils.isNullOrEmpty(plan.getRelPlanAptitudes())) {
				int indexCmpsEsp = 0;
				aptitudes = new TreeNode[plan.getRelPlanAptitudes().size()];
				List<Integer> comEspecifIds = plan.getRelPlanAptitudes().stream()
						.map(c -> c.getIdAptitud()).collect(Collectors.toList());
				obtieneValorCompEspecifSelec(arbolAptitudes, comEspecifIds, aptitudes, indexCmpsEsp);
				
				aptitudesPlanSelec = new ArrayList<>();
				for (RelPlanAptitudDTO relApt : plan.getRelPlanAptitudes()) {
					aptitudesPlanSelec.add(relApt.getCatAptitudesPlan().getId().toString());
				}
			}

			if (!ObjectUtils.isNullOrEmpty(plan.getRelPlanHabilidades())) {
				int indexCmpsEsp = 0;
				habilidades = new TreeNode[plan.getRelPlanHabilidades().size()];
				List<Integer> comEspecifIds = plan.getRelPlanHabilidades().stream()
						.map(c -> c.getIdHabilidad()).collect(Collectors.toList());
				obtieneValorCompEspecifSelec(arbolHabilidades, comEspecifIds, habilidades, indexCmpsEsp);
				
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
		catPeriodo = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_PERIODOS);
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
		catDocsExpidePlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_DOCS_EXPIDE_PLAN);

		catCreditosPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_CREDITOS_PLAN);
		catDivisionesPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_DIVISIONES_PLAN);
		catTipoCompetencia = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_TIPOS_COMPETENCIA);
		
		
		catConocimientosPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_CONOCIMIENTOS_PLAN);
		catHabilidadesPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_HABILIDADES_PLAN);
		catAptitutesPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_APTITUDES_PLAN);
		catCompPlan = (List<CatalogoComunDTO>) getSession().getServletContext()
				.getAttribute(ConstantesGestorWeb.CAT_COMPETENCIAS_PLAN);
		
		generarArbolConocimientos();
		generarArbolAptitudes();
		generarArbolHabilidades();
	}
	
	/**
	 *
	 */
	private void generarArbolConocimientos() {
		CatalogoComunDTO catCono = new CatalogoComunDTO();
		catCono.setId(-1);
		catCono.setNombre("Conocimientos");
		
		CatalogoComunDTO catBas = new CatalogoComunDTO();
		catBas.setId(-1);
		catBas.setNombre("Básicas");
		
		CatalogoComunDTO catProf = new CatalogoComunDTO();
		catProf.setId(-1);
		catProf.setNombre("Profesionales");
		
		CatalogoComunDTO catTrans = new CatalogoComunDTO();
		catTrans.setId(-1);
		catTrans.setNombre("Transversales");
		
		arbolConocimientos = new CheckboxTreeNode(catCono, null);
		arbolConocimientos.setSelectable(Boolean.FALSE);
		
		TreeNode ramaBasic = new CheckboxTreeNode(catBas, arbolConocimientos);
		ramaBasic.setSelectable(Boolean.FALSE);
		
		TreeNode ramaProf = new CheckboxTreeNode(catProf, arbolConocimientos);
		ramaProf.setSelectable(Boolean.FALSE);
		
		TreeNode ramaTrans = new CheckboxTreeNode(catTrans, arbolConocimientos);
		ramaTrans.setSelectable(Boolean.FALSE);
		
		for (CatalogoComunDTO catCon : catConocimientosPlan) {
			switch(catCon.getDescripcion()) {
			case "1":
				TreeNode nodoBasic = new CheckboxTreeNode(catCon, ramaBasic);
				nodoBasic.setExpanded(Boolean.FALSE);
			break;
			case "2":
				TreeNode nodoProf = new CheckboxTreeNode(catCon, ramaProf);
				nodoProf.setExpanded(Boolean.FALSE);
			break;
			case "3":
				TreeNode nodoTrans = new CheckboxTreeNode(catCon, ramaTrans);
				nodoTrans.setExpanded(Boolean.FALSE);
			break;
			}
			
		}
	}
	
	/**
	 *
	 */
	private void generarArbolAptitudes() {
		CatalogoComunDTO catCono = new CatalogoComunDTO();
		catCono.setId(-1);
		catCono.setNombre("Aptitudes");
		
		CatalogoComunDTO catBas = new CatalogoComunDTO();
		catBas.setId(-2);
		catBas.setNombre("Básicas");
		
		CatalogoComunDTO catProf = new CatalogoComunDTO();
		catProf.setId(-3);
		catProf.setNombre("Profesionales");
		
		CatalogoComunDTO catTrans = new CatalogoComunDTO();
		catTrans.setId(-4);
		catTrans.setNombre("Transversales");
		
		arbolAptitudes = new CheckboxTreeNode(catCono, null);
		arbolAptitudes.setSelectable(Boolean.FALSE);
		
		TreeNode ramaBasic = new CheckboxTreeNode(catBas, arbolAptitudes);
		ramaBasic.setSelectable(Boolean.FALSE);
		
		TreeNode ramaProf = new CheckboxTreeNode(catProf, arbolAptitudes);
		ramaProf.setSelectable(Boolean.FALSE);
		
		TreeNode ramaTrans = new CheckboxTreeNode(catTrans, arbolAptitudes);
		ramaTrans.setSelectable(Boolean.FALSE);
		
		for (CatalogoComunDTO catCon : catAptitutesPlan) {
			switch(catCon.getDescripcion()) {
			case "1":
				TreeNode nodoBasic = new CheckboxTreeNode(catCon, ramaBasic);
				nodoBasic.setExpanded(Boolean.FALSE);
			break;
			case "2":
				TreeNode nodoProf = new CheckboxTreeNode(catCon, ramaProf);
				nodoProf.setExpanded(Boolean.FALSE);
			break;
			case "3":
				TreeNode nodoTrans = new CheckboxTreeNode(catCon, ramaTrans);
				nodoTrans.setExpanded(Boolean.FALSE);
			break;
			}
			
		}
	}
	
	/**
	 *
	 */
	private void generarArbolHabilidades() {
		CatalogoComunDTO catCono = new CatalogoComunDTO();
		catCono.setId(-1);
		catCono.setNombre("Habilidades");
		
		CatalogoComunDTO catBas = new CatalogoComunDTO();
		catBas.setId(-2);
		catBas.setNombre("Básicas");
		
		CatalogoComunDTO catProf = new CatalogoComunDTO();
		catProf.setId(-3);
		catProf.setNombre("Profesionales");
		
		CatalogoComunDTO catTrans = new CatalogoComunDTO();
		catTrans.setId(-4);
		catTrans.setNombre("Transversales");
		
		arbolHabilidades = new CheckboxTreeNode(catCono, null);
		arbolHabilidades.setSelectable(Boolean.FALSE);
		
		TreeNode ramaBasic = new CheckboxTreeNode(catBas, arbolHabilidades);
		ramaBasic.setSelectable(Boolean.FALSE);
		
		TreeNode ramaProf = new CheckboxTreeNode(catProf, arbolHabilidades);
		ramaProf.setSelectable(Boolean.FALSE);
		
		TreeNode ramaTrans = new CheckboxTreeNode(catTrans, arbolHabilidades);
		ramaTrans.setSelectable(Boolean.FALSE);
		
		for (CatalogoComunDTO catCon : catHabilidadesPlan) {
			switch(catCon.getDescripcion()) {
			case "1":
				TreeNode nodoBasic = new CheckboxTreeNode(catCon, ramaBasic);
				nodoBasic.setExpanded(Boolean.FALSE);
			break;
			case "2":
				TreeNode nodoProf = new CheckboxTreeNode(catCon, ramaProf);
				nodoProf.setExpanded(Boolean.FALSE);
			break;
			case "3":
				TreeNode nodoTrans = new CheckboxTreeNode(catCon, ramaTrans);
				nodoTrans.setExpanded(Boolean.FALSE);
			break;
			}
			
		}
	}

	/**
	 * Se genera componente que agrupa los organismos gubernamentales en Segundo
	 * nivel
	 */
	private void generaItemsOrgGubs() {

		if (ObjectUtils.isNotNull(catOrgGubs)) {

			itemsOrgGubs = new ArrayList<>();

			for (OrgGubernamentalDTO orgG : catOrgGubs) {

				SelectItemGroup group = new SelectItemGroup(orgG.getNombre());
				SelectItem[] arrayItems = null;

				if (!orgG.getLstHijosOrgGub().isEmpty()) {

					arrayItems = new SelectItem[orgG.getLstHijosOrgGub().size()];
					int i = 0;

					for (OrgGubernamentalDTO orgGHijo : orgG.getLstHijosOrgGub()) {
						arrayItems[i] = new SelectItem(orgGHijo, orgGHijo.getNombre());
						i++;
					}
				}
				group.setSelectItems(arrayItems);
				itemsOrgGubs.add(group);
			}
		}
	}

	public void onChangeModalidadPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatModalidadPlanPrograma(this.getValorDeCatalogo(catModalidadPlan, (Integer.parseInt(e.getNewValue().toString()))));
		}
	}

	public void onChangeTpoPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatTipoPlan(this.getValorDeCatalogo(catTpoPlan, (Integer.parseInt(e.getNewValue().toString()))));
		}
	}

	public void onChangePeriodo(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatPeriodo(this.getValorDeCatalogo(catPeriodo, (Integer.parseInt(e.getNewValue().toString()))));
		}
	}

	public void onChangeNvEnsePlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatNivelEnsenanzaPrograma(this.getValorDeCatalogo(catNivelEnsPlan, (Integer.parseInt(e.getNewValue().toString()))));
		}
	}

	public void onChangeCompetenciaPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatCompetenciasPlan(this.getValorDeCatalogo(catCompPlan, (Integer.parseInt(e.getNewValue().toString()))));
		}
	}

	public void onChangeAlcancePlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatAlcancePlan(this.getValorDeCatalogo(catAlcancePlan, (Integer.parseInt(e.getNewValue().toString()))));
		}
	}

	public void onChangeCreditosPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatCreditosPlan(this.getValorDeCatalogo(catCreditosPlan, (Integer.parseInt(e.getNewValue().toString()))));
			if (Integer.parseInt(e.getNewValue().toString()) == 2) {
				setCreditos(Boolean.FALSE);
			} else {
				setCreditos(Boolean.TRUE);
			}
		} else {
			setCreditos(Boolean.TRUE);
		}
	}

	public void onChangeDivisionesPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatDivisionesPlan(this.getValorDeCatalogo(catDivisionesPlan, (Integer.parseInt(e.getNewValue().toString()))));
		}
	}

	public void onChangeTipoCompetencia(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatTipoCompetencia(this.getValorDeCatalogo(catTipoCompetencia, (Integer.parseInt(e.getNewValue().toString()))));
		}
	}

	public void onChangeEstatusPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatEstatusPlan(this.getValorDeCatalogo(catalEstatusPlan, (Integer.parseInt(e.getNewValue().toString()))));
		}
	}

	public void onChangeDocExpide(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			plan.setCatDocumentosExpidePlan(this.getValorDeCatalogo(catDocsExpidePlan, (Integer.parseInt(e.getNewValue().toString()))));
		}
	}

	public void onChangeOrgGubPlan(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			for (OrgGubernamentalDTO orgGub : catOrgGubs) {
				for (OrgGubernamentalDTO orgGubHijo : orgGub.getLstHijosOrgGub()) {
					if (orgGubHijo.getId().equals(Integer.parseInt(e.getNewValue().toString()))) {
						plan.setTblOrganismoGubernamental(orgGubHijo);
					}
				}
			}
		}
	}

	public void onChangeNameElements(ValueChangeEvent event) {
		if (ObjectUtils.isNotNull(event.getNewValue())) {
			mallaPlan.setNombreEstructuras((String) event.getNewValue());
		}
	}

	public void onChangeElementsEstruc(ValueChangeEvent event) {
		if (ObjectUtils.isNotNull(event.getNewValue())) {
			mallaPlan.setElementosEstructuras(Integer.parseInt(event.getNewValue().toString()));
		}
	}

	public void onChangeElementsSubs(ValueChangeEvent e) {
		if (ObjectUtils.isNotNull(e.getNewValue())) {
			if (Integer.parseInt(e.getNewValue().toString()) >= 3) {
				mallaPlan.setNiveles(3);
			} else {
				mallaPlan.setNiveles(Integer.parseInt(e.getNewValue().toString()));
			}
		}
	}

	public void onChangeSubsElements(ValueChangeEvent event) {
		if (ObjectUtils.isNotNull(event.getNewValue())) {
			switch (Integer.parseInt(event.getComponent().getAttributes().get("idxA").toString())) {
			case 1:
				mallaPlan.setElementosSubestructuras1(Integer.parseInt(event.getNewValue().toString()));
				break;
			case 2:
				mallaPlan.setElementosSubestructuras2(Integer.parseInt(event.getNewValue().toString()));
				break;
			case 3:
				mallaPlan.setElementosSubestructuras3(Integer.parseInt(event.getNewValue().toString()));
				break;
			}

		}
	}

	public void onChangeNameSubsElements(ValueChangeEvent event) {
		if (ObjectUtils.isNotNull(event.getNewValue())) {
			switch (Integer.parseInt(event.getComponent().getAttributes().get("idxA").toString())) {
			case 1:
				mallaPlan.setNombreSubestructuras1(event.getNewValue().toString());
				break;
			case 2:
				mallaPlan.setNombreSubestructuras2(event.getNewValue().toString());
				break;
			case 3:
				mallaPlan.setNombreSubestructuras3(event.getNewValue().toString());
				break;
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
				bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CLO_PLA", String.valueOf(planSelecBusq.getIdPlan()),
						requestActual(), TipoServicioEnum.LOCAL);

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
		bitacoraBean.guardarBitacora(idPersonaEnSesion(), "VER_PLA", String.valueOf(planSelecBusq.getIdPlan()),
				requestActual(), TipoServicioEnum.LOCAL);
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

			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CON_PLA", "", requestActual(), TipoServicioEnum.LOCAL);
			/**
			 * Despues de la busqueda se limpian los criterios de busqueda de la vista*
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
		plan.setCatPeriodo(!plan.getCatTipoPlan().getNombre().equals("Por Periodo") ? null : plan.getCatPeriodo());
		plan.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		ResultadoDTO<PlanDTO> resultado = null;
		
		try {
			conocimsPlanSelec = new ArrayList<>();
			for(TreeNode nodCon : conocimientos) {
				CatalogoComunDTO catCon = (CatalogoComunDTO) nodCon.getData();
				if(!catCon.getId().toString().contains("-")){
					conocimsPlanSelec.add( catCon.getId().toString() );
				}
			}
			
			habilidadesPlanSelec = new ArrayList<>();
			for(TreeNode nodCon : habilidades) {
				CatalogoComunDTO catCon = (CatalogoComunDTO) nodCon.getData();
				if(!catCon.getId().toString().contains("-")){
					habilidadesPlanSelec.add( catCon.getId().toString() );
				}
			}
			
			aptitudesPlanSelec = new ArrayList<>();
			for(TreeNode nodCon : aptitudes) {
				CatalogoComunDTO catCon = (CatalogoComunDTO) nodCon.getData();
				if(!catCon.getId().toString().contains("-")){
					aptitudesPlanSelec.add( catCon.getId().toString() );
				}
			}
			
			resultado = planServiceFacade.guardaNuevoPlan(plan,
					this.obtieneListaCatalogoComun(habilidadesPlanSelec, ConstantesGestorWeb.CAT_HABILIDADES_PLAN),
					this.obtieneListaCatalogoComun(aptitudesPlanSelec, ConstantesGestorWeb.CAT_APTITUDES_PLAN),
					this.obtieneListaCatalogoComun(conocimsPlanSelec, ConstantesGestorWeb.CAT_CONOCIMIENTOS_PLAN),
					mallaPlan);
		} catch (Exception e) {
			logger.error(e);
			agregarMsgError("Ocurrio un error", null);
		}

		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "CRE_PLA", String.valueOf(resultado.getDto().getIdPlan()),
					requestActual(), TipoServicioEnum.LOCAL);
			agregarMsgInfo("Se guardo correctamente el plan", null);
		} else {
			agregarMsgError("Ocurrio un error", null);
		}
	}

	/**
	 * @throws Exception
	 *
	 */
	public void editarPlan() throws Exception {
		logger.info("########## EDITANDO DEl PLAN ########");
		plan.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
		
		conocimsPlanSelec = new ArrayList<>();
		for(TreeNode nodCon : conocimientos) {
			CatalogoComunDTO catCon = (CatalogoComunDTO) nodCon.getData();
			if(!catCon.getId().toString().contains("-")){
				conocimsPlanSelec.add( catCon.getId().toString() );
			}
		}
		
		habilidadesPlanSelec = new ArrayList<>();
		for(TreeNode nodCon : habilidades) {
			CatalogoComunDTO catCon = (CatalogoComunDTO) nodCon.getData();
			if(!catCon.getId().toString().contains("-")){
				habilidadesPlanSelec.add( catCon.getId().toString() );
			}
		}
		
		aptitudesPlanSelec = new ArrayList<>();
		for(TreeNode nodCon : aptitudes) {
			CatalogoComunDTO catCon = (CatalogoComunDTO) nodCon.getData();
			if(!catCon.getId().toString().contains("-")){
				aptitudesPlanSelec.add( catCon.getId().toString() );
			}
		}
		
		ResultadoDTO<PlanDTO> resultado = planServiceFacade.editarPlan(plan,
				this.obtieneListaCatalogoComun(habilidadesPlanSelec, ConstantesGestorWeb.CAT_HABILIDADES_PLAN),
				this.obtieneListaCatalogoComun(aptitudesPlanSelec, ConstantesGestorWeb.CAT_APTITUDES_PLAN),
				this.obtieneListaCatalogoComun(conocimsPlanSelec, ConstantesGestorWeb.CAT_CONOCIMIENTOS_PLAN),
				mallaPlan);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			bitacoraBean.guardarBitacora(idPersonaEnSesion(), "EDI_PLA", String.valueOf(resultado.getDto().getIdPlan()),
					requestActual(), TipoServicioEnum.LOCAL);
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
	 *
	 * @param event
	 */
	public void onNodeSelectCompEspecif(NodeSelectEvent event) {

		TreeNode treeCmpEsp = event.getTreeNode();
		List<TreeNode> aux = Arrays.asList(conocimientos);

		if (!containsObject(aux, treeCmpEsp)) {
			int index = conocimientos.length;
			conocimientos[index + 1] = treeCmpEsp;
		}
	}

	/**
	 *
	 * @param event
	 */
	public void onNodeUnSelectCompEspecif(NodeUnselectEvent event) {
		TreeNode treeCmpEsp = event.getTreeNode();

		if (conocimientos.length > ConstantesGestorWeb.NUMERO_CERO) {
			List<TreeNode> aux = Arrays.asList(conocimientos);
			aux.remove(treeCmpEsp);
			conocimientos = (TreeNode[]) aux.toArray();
		}
	}
	
	/**
	 *
	 * @param event
	 */
	public void onNodeSelectHabilidades(NodeSelectEvent event) {

		TreeNode treeCmpEsp = event.getTreeNode();
		List<TreeNode> aux = Arrays.asList(habilidades);

		if (!containsObject(aux, treeCmpEsp)) {
			int index = habilidades.length;
			habilidades[index + 1] = treeCmpEsp;
		}
	}

	/**
	 *
	 * @param event
	 */
	public void onNodeUnSelectHabilidades(NodeUnselectEvent event) {
		TreeNode treeCmpEsp = event.getTreeNode();

		if (habilidades.length > ConstantesGestorWeb.NUMERO_CERO) {
			List<TreeNode> aux = Arrays.asList(habilidades);
			aux.remove(treeCmpEsp);
			habilidades = (TreeNode[]) aux.toArray();
		}
	}
	
	/**
	 *
	 * @param event
	 */
	public void onNodeSelectAptitudes(NodeSelectEvent event) {

		TreeNode treeCmpEsp = event.getTreeNode();
		List<TreeNode> aux = Arrays.asList(aptitudes);

		if (!containsObject(aux, treeCmpEsp)) {
			int index = aptitudes.length;
			aptitudes[index + 1] = treeCmpEsp;
		}
	}

	/**
	 *
	 * @param event
	 */
	public void onNodeUnSelectAptitudes(NodeUnselectEvent event) {
		TreeNode treeCmpEsp = event.getTreeNode();

		if (aptitudes.length > ConstantesGestorWeb.NUMERO_CERO) {
			List<TreeNode> aux = Arrays.asList(aptitudes);
			aux.remove(treeCmpEsp);
			aptitudes = (TreeNode[]) aux.toArray();
		}
	}
	
	/**
	 *
	 * @param compEspecificas
	 * @param nodo
	 * @return
	 */
	private boolean containsObject(List<TreeNode> listArbol, TreeNode nodo) {
		if (ObjectUtils.isNotNull(listArbol)) {
			if (listArbol.contains(nodo)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *
	 * @param arbol
	 * @param idData
	 */
	private void obtieneValorCompEspecifSelec(TreeNode arbol, List<Integer> valoresRegistrados, TreeNode[] nodosSelect,
			Integer indexGlobal) {

		for (TreeNode hijo : arbol.getChildren()) {
			CatalogoComunDTO data = (CatalogoComunDTO) hijo.getData();
			if (ObjectUtils.isNotNull(data) && valoresRegistrados.contains(data.getId())) {
				hijo.setSelected(Boolean.TRUE);
				nodosSelect[indexGlobal] = hijo;
				indexGlobal++;
			}
			if (!ObjectUtils.isNullOrEmpty(hijo.getChildren())) {
				obtieneValorCompEspecifSelec(hijo, valoresRegistrados, nodosSelect, indexGlobal);
			}
		}
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
	 * @param planServiceFacade the planServiceFacade to set
	 */
	public void setPlanServiceFacade(PlanServiceFacade planServiceFacade) {
		this.planServiceFacade = planServiceFacade;
	}

	public boolean isTipoPlan() {
		return tipoPlan;
	}

	public void setTipoPlan(boolean tipoPlan) {
		this.tipoPlan = tipoPlan;
	}

	public boolean isCreditos() {
		return creditos;
	}

	public void setCreditos(boolean creditos) {
		this.creditos = creditos;
	}

	/**
	 * @return the filtroPlan
	 */
	public PlanDTO getFiltroPlan() {
		return filtroPlan;
	}

	/**
	 * @param filtroPlan the filtroPlan to set
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
	 * @param catTpoPlan the catTpoPlan to set
	 */
	public void setCatTpoPlan(List<CatalogoComunDTO> catTpoPlan) {
		this.catTpoPlan = catTpoPlan;
	}

	/**
	 * @return the catPeriodo
	 */
	public List<CatalogoComunDTO> getCatPeriodo() {
		return catPeriodo;
	}

	/**
	 * @param catPeriodo the catPeriodo to set
	 */
	public void setCatPeriodo(List<CatalogoComunDTO> catPeriodo) {
		this.catPeriodo = catPeriodo;
	}

	/**
	 * @return the catModalidadPlan
	 */
	public List<CatalogoComunDTO> getCatModalidadPlan() {
		return catModalidadPlan;
	}

	/**
	 * @param catModalidadPlan the catModalidadPlan to set
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
	 * @param catAlcancePlan the catAlcancePlan to set
	 */
	public void setCatAlcancePlan(List<CatalogoComunDTO> catAlcancePlan) {
		this.catAlcancePlan = catAlcancePlan;
	}

	/**
	 * @return the catCreditosPlan
	 */
	public List<CatalogoComunDTO> getCatCreditosPlan() {
		return catCreditosPlan;
	}

	/**
	 * @param catCreditosPlan the catCreditosPlan to set
	 */
	public void setCatCreditosPlan(List<CatalogoComunDTO> catCreditosPlan) {
		this.catCreditosPlan = catCreditosPlan;
	}

	/**
	 * @return the catDivisionesPlan
	 */
	public List<CatalogoComunDTO> getCatDivisionesPlan() {
		return catDivisionesPlan;
	}

	/**
	 * @param catDivisionesPlan the catDivisionessPlan to set
	 */
	public void setCatDivisionesPlan(List<CatalogoComunDTO> catDivisionesPlan) {
		this.catDivisionesPlan = catDivisionesPlan;
	}

	/**
	 * @return the catTipoCompetencia
	 */
	public List<CatalogoComunDTO> getCatTipoCompetencia() {
		return catTipoCompetencia;
	}

	/**
	 * @param catTipoCompetencia the catTipoCompetencia to set
	 */

	public void setCatTipoCompetencia(List<CatalogoComunDTO> catTipoCompetencia) {
		this.catTipoCompetencia = catTipoCompetencia;
	}

	/**
	 * @return the catalEstatusPlan
	 */
	public List<CatalogoComunDTO> getCatalEstatusPlan() {
		return catalEstatusPlan;
	}

	/**
	 * @param catalEstatusPlan the catalEstatusPlan to set
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
	 * @param itemsOrgGubs the itemsOrgGubs to set
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
	 * @param planesFiltrados the planesFiltrados to set
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
	 * @param planSelecBusq the planSelecBusq to set
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
	 * @param plan the plan to set
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
	 * @param catNivelEnsPlan the catNivelEnsPlan to set
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
	 * @param catCompPlan the catCompPlan to set
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
	 * @param catConocimientosPlan the catConocimientosPlan to set
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
	 * @param catAptitutesPlan the catAptitutesPlan to set
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
	 * @param catHabilidadesPlan the catHabilidadesPlan to set
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
	 * @param catDocsExpidePlan the catDocsExpidePlan to set
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
	 * @param conocimsPlanSelec the conocimsPlanSelec to set
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
	 * @param habilidadesPlanSelec the habilidadesPlanSelec to set
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
	 * @param aptitudesPlanSelec the aptitudesPlanSelec to set
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
	 * @param edicionPlan the edicionPlan to set
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
	 * @param catOrgGubs the catOrgGubs to set
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

	public Integer getElementsStruc() {
		return elementsStruc;
	}

	public void setElementsStruc(Integer elementsStruc) {
		this.elementsStruc = elementsStruc;
	}

	public String getNameStruc() {
		return nameStruc;
	}

	public void setNameStruc(String nameStruc) {
		this.nameStruc = nameStruc;
	}

	public Integer getSubStrucLvl() {
		return subStrucLvl;
	}

	public void setSubStrucLvl(Integer subStrucLvl) {
		this.subStrucLvl = subStrucLvl;
	}

	public ArrayList<String> getNamesSubStruc() {
		return namesSubStruc;
	}

	public void setNamesSubStruc(ArrayList<String> namesSubStruc) {
		this.namesSubStruc = namesSubStruc;
	}

	public ArrayList<String> getElementsSubStruc() {
		return elementsSubStruc;
	}

	public void setElementsSubStruc(ArrayList<String> elementsSubStruc) {
		this.elementsSubStruc = elementsSubStruc;
	}

	public RelMallaPlanDTO getMallaPlan() {
		return mallaPlan;
	}

	public void setMallaPlan(RelMallaPlanDTO mallaPlan) {
		this.mallaPlan = mallaPlan;
	}

	public FECServiceFacade getFecServiceFacade() {
		if (ObjectUtils.isNull(fecServiceFacade)) {
			fecServiceFacade = new FECServiceFacade();
		}

		return fecServiceFacade;
	}

	public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
		this.fecServiceFacade = fecServiceFacade;
	}

	public MallaPlanService getMallaPlanService() {
		return mallaPlanService;
	}

	public void setMallaPlanService(MallaPlanService mallaPlanService) {
		this.mallaPlanService = mallaPlanService;
	}

	public TreeNode getArbolConocimientos() {
		return arbolConocimientos;
	}

	public void setArbolConocimientos(TreeNode arbolConocimientos) {
		this.arbolConocimientos = arbolConocimientos;
	}

	public TreeNode[] getConocimientos() {
		return conocimientos;
	}

	public void setConocimientos(TreeNode[] conocimientos) {
		this.conocimientos = conocimientos;
	}

	public TreeNode getArbolHabilidades() {
		return arbolHabilidades;
	}

	public void setArbolHabilidades(TreeNode arbolHabilidades) {
		this.arbolHabilidades = arbolHabilidades;
	}

	public TreeNode[] getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(TreeNode[] habilidades) {
		this.habilidades = habilidades;
	}

	public TreeNode getArbolAptitudes() {
		return arbolAptitudes;
	}

	public void setArbolAptitudes(TreeNode arbolAptitudes) {
		this.arbolAptitudes = arbolAptitudes;
	}

	public TreeNode[] getAptitudes() {
		return aptitudes;
	}

	public void setAptitudes(TreeNode[] aptitudes) {
		this.aptitudes = aptitudes;
	}
}
