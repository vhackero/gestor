package mx.gob.sedesol.gestorweb.config.servlets;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatClasificacionArchivoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatElementosMultimedia;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTemaRecurso;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTipoEstructuraOa;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatEstadoEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoResponsabilidadEc;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.badges.ClasificacionBadgeDTO;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatAlcancePlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatAptitudesPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatAreasConocimiento;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatCompetenciasPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatDocumentosExpidePlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatEstatusPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatHabilidadesPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatInstitucionesCertificadora;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModalidadPlanPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatNivelConocimiento;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatNivelEnsenanzaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatObjetoCurricular;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatStatusPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoEventoEc;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoCompetencia;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTpoCargaHoraria;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.admin.RoleService;
import mx.gob.sedesol.basegestor.service.admin.TiposCorreoService;
import mx.gob.sedesol.basegestor.service.badges.ClasificacionBadgeService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstPersonalExternoService;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatCreditosPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatDivisionesPlan;
/**
 * Servlet implementation class CatalogosServlet
 */

@WebServlet(loadOnStartup = 1, value = "/CatalogosServlet", name = "/CatalogosServlet")
public class CatalogosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private transient TiposCorreoService tiposCorreoService;
	@Autowired
	private transient RoleService roleService;
	@Autowired
	private transient ClasificacionBadgeService clasificacionBadgeService;
	@Autowired
	private transient CatalogoComunService<CatEstatusPlan, Integer> catEstatusPlanService;
	@Autowired
	private transient CatalogoComunService<CatModalidadPlanPrograma, Integer> catModalidadPlanPlanService;
	@Autowired
	private transient CatalogoComunService<CatNivelEnsenanzaPrograma, Integer> catNivelEnsenanzaPlanService;
	@Autowired
	private transient CatalogoComunService<CatTipoPlan, Integer> catTipoPlanService;
	@Autowired
	private transient CatalogoComunService<CatPeriodo, Integer> catPeriodoService;
	@Autowired
	private transient CatalogoComunService<CatTipoCompetencia, Integer> catTipoCompetencia;
	@Autowired
	private transient CatalogoComunService<CatAlcancePlan, Integer> catAlcancePlanService;
	@Autowired
	private transient OrgGubernamentalService orgGubernamentalService;
	@Autowired
	private transient CatalogoComunService<CatCompetenciasPlan, Integer> catCompPlanService;
	@Autowired
	private transient CatalogoComunService<CatAreasConocimiento, Integer> catAreasConocimientoService;
	@Autowired
	private transient CatalogoComunService<CatConocimientosPlan, Integer> catConocimientoPlanService;
	@Autowired
	private transient CatalogoComunService<CatAptitudesPlan, Integer> catAptitudPlanService;
	@Autowired
	private transient CatalogoComunService<CatHabilidadesPlan, Integer> catHabilidadPlanService;
	@Autowired
	private transient CatalogoComunService<CatDocumentosExpidePlan, Integer> catDocExpidePlanService;
	@Autowired
	private transient CatalogoComunService<CatStatusPrograma, Integer> catStatusProgramaService;
	@Autowired
	private transient CatalogoComunService<CatObjetoCurricular, Integer> catObjCurricularService;
	@Autowired
	private transient CatalogoComunService<CatTipoEventoEc, Integer> catEventosCapService;
	@Autowired
	private transient CatalogoComunService<CatNivelConocimiento, Integer> catNvlConocimientoService;
	@Autowired
	private transient CatalogoComunService<CatCompetenciaEspecifica, Integer> catCompetenciaEspecificaService;
	@Autowired
	private transient CatalogoComunService<CatMaterialDidactico, Integer> catMatsDidacticosService;
	@Autowired
	private transient CatalogoComunService<CatInstitucionesCertificadora, Integer>   catInstitucionesCertificadora;
	@Autowired
	private transient CatalogoComunService<CatTpoCargaHoraria,Integer>  catTipoCargaHoraria;
	@Autowired
	private transient EstPersonalExternoService estPersonalExternoService;
	@Autowired
	private transient CatalogoComunService<CatEstadoEventoCapacitacion, Integer> catEdoEventoCap;	
	@Autowired
	private transient CatalogoComunService<CatTemaRecurso, Integer> catTemaRecurso;
	@Autowired	
	private transient CatalogoComunService<CatClasificacionArchivoOa, Integer> catClasificacionArchivoOa;
	@Autowired	
	private transient CatalogoComunService<CatTipoResponsabilidadEc, Integer> catTipoResponsabilidadEc;
	@Autowired	
	private transient CatalogoComunService<CatElementosMultimedia, Integer> catalogoElementosMultimedia;
	
	@Autowired
	private transient CatalogoComunService<CatCreditosPlan, Integer> catCreditosPlanService;
	@Autowired
	private transient CatalogoComunService<CatDivisionesPlan, Integer> catDivisionesPlanService;

	/**
	 * 
	 */
	private static Logger log = Logger.getLogger(CatalogosServlet.class);

	private ServletConfig config = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CatalogosServlet() {
		super();
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		log.info("Inicia carga de catalogos en sesion via Servlet");
		this.config = servletConfig;
		try {
			SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
			 cargarCatalogos();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void cargarCatalogos() throws Exception {
		//Administraion
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_TIPOS_CORREO, getTiposCorreo());
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_ROLES, getCatRoles());
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_CLASIFICACIONES_BADGE, getClasificacionBadgeService());
		//fin Admin
		//PLanes y programas
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_PERIODOS, catPeriodoService.findAll(CatPeriodo.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_TIPOS_COMPETENCIA, catTipoCompetencia.findAll(CatTipoCompetencia.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_TPO_PLAN, catTipoPlanService.findAll(CatTipoPlan.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_MODALIDAD_PLAN_PROG, catModalidadPlanPlanService.findAll(CatModalidadPlanPrograma.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_ESTATUS_PLAN, catEstatusPlanService.findAll(CatEstatusPlan.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_ALCANCE_PLAN, catAlcancePlanService.findAll(CatAlcancePlan.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_NIVEL_ENSE_PLAN_PROG, catNivelEnsenanzaPlanService.findAll(CatNivelEnsenanzaPrograma.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_COMPETENCIAS_PLAN, catCompPlanService.findAll(CatCompetenciasPlan.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_CONOCIMIENTOS_PLAN, catConocimientoPlanService.findAll(CatConocimientosPlan.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_APTITUDES_PLAN, catAptitudPlanService.findAll(CatAptitudesPlan.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_HABILIDADES_PLAN,catHabilidadPlanService.findAll(CatHabilidadesPlan.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_DOCS_EXPIDE_PLAN, catDocExpidePlanService.findAll(CatDocumentosExpidePlan.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_ORG_GUBERNAMENTALES, orgGubernamentalService.obtenerOrgGubsPadres());
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_ESTATUS_PROG, catStatusProgramaService.findAll(CatStatusPrograma.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_OBJ_CURRICULAR, catObjCurricularService.findAll(CatObjetoCurricular.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_EVENTO_CAPACITACION, catEventosCapService.findAll(CatTipoEventoEc.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_NIVEL_CONOCIMIENTO, catNvlConocimientoService.findAll(CatNivelConocimiento.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_COMPETENCIA_ESPECIFICA, catCompetenciaEspecificaService.findAll(CatCompetenciaEspecifica.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_INSTITUCIIONES_CERTIFICADORAS, catInstitucionesCertificadora.findAll(CatInstitucionesCertificadora.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_TIPO_CARGA_HORARIA, catTipoCargaHoraria.findAll(CatTpoCargaHoraria.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_MATERIAL_DIDACTICO, catMatsDidacticosService.findAll(CatMaterialDidactico.class));

		
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_CREDITOS_PLAN, catCreditosPlanService.findAll(CatCreditosPlan.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_DIVISIONES_PLAN, catDivisionesPlanService.findAll(CatDivisionesPlan.class));
		//config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_EST_PERSONAL_EXTERNO, estPersonalExternoService.obtenerEstPersonalExtPadres());
		//fin planes y programas
		
		//Inicio Gestion Escolar		
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_ESTADO_EVENTO_CAPACITACION, catEdoEventoCap.findAll(CatEstadoEventoCapacitacion.class));		
		//Fin Gestion Escolar
		
		//Inicia gestion aprendizaje				
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_TEMA_RECURSO, catTemaRecurso.findAll(CatTemaRecurso.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_CLASIFICACION_ARCHIVO_OA, catClasificacionArchivoOa.findAll(CatClasificacionArchivoOa.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_TIPO_RESPONSABILIDAD, catTipoResponsabilidadEc.findAll(CatTipoResponsabilidadEc.class));
		config.getServletContext().setAttribute(ConstantesGestorWeb.CAT_ELEMENTOS_MULTIMEDIA_FOA, catalogoElementosMultimedia.findAll(CatElementosMultimedia.class));

		
		//Fin gestion aprendizaje 
		
		log.info("Finaliza carga de catalogos en sesion.");
	}
	
	/**
	 * Tipos Correo
	 * @return
	 */
	private List<TipoCorreoDTO> getTiposCorreo(){
		return tiposCorreoService.findAll();
	}
	
	/**
	 * 
	 * @return
	 */
	private List<RolDTO> getCatRoles(){
		return roleService.findAll();
	}

	private List<ClasificacionBadgeDTO> getClasificacionBadgeService() {
		return clasificacionBadgeService.findAll();
	}


}
