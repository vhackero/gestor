package mx.gob.sedesol.basegestor.planesprogramas;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanAptitudDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelPlanHabilidadDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatAptitudesPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatAreasConocimiento;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatCompetenciasPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatDocumentosExpidePlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatEstatusPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatHabilidadesPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModalidadPlanPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatNivelEnsenanzaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoPlan;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
@EnableTransactionManagement
public class PlanesTest {

	private static final Logger logger = Logger.getLogger(PlanesTest.class);
	
	@Autowired
	private PlanService planService;
	@Autowired
	private CatalogoComunService<CatTipoPlan, Integer> catTpoPlanService;
	@Autowired
	private CatalogoComunService<CatNivelEnsenanzaPrograma,Integer> catNivelEnseService;
	@Autowired
	private CatalogoComunService<CatCompetenciasPlan,Integer> catCompetenciasPlanService;
	@Autowired
	private CatalogoComunService<CatModalidadPlanPrograma,Integer> catModalidadService;
	@Autowired
	private CatalogoComunService<CatDocumentosExpidePlan,Long> catDocsExpideService;
	
	@Autowired
	private CatalogoComunService<CatAreasConocimiento,Integer> catAreasConService;
	@Autowired
	private CatalogoComunService<CatHabilidadesPlan,Integer> catHabilidadesService;
	@Autowired
	private CatalogoComunService<CatAptitudesPlan,Integer> catAptitudesService;
	
	private CatalogoComunService<CatEstatusPlan, Integer> catEstatusPlan;
	
	@Autowired
	private OrgGubernamentalService orgGubernamentalService;
	
	//@Test
	public void guardaNuevoPlan(){
		
		PlanDTO plan = new PlanDTO();
		plan.setIdentificador("PL-COM-INN-20161212-1");
		plan.setCatTipoPlan(catTpoPlanService.buscarPorId(2, CatTipoPlan.class));
		plan.setNombre("Plan Generico I");
		plan.setCatNivelEnsenanzaPrograma(catNivelEnseService.buscarPorId(2, CatNivelEnsenanzaPrograma.class));
		plan.setCatCompetenciasPlan(catCompetenciasPlanService.buscarPorId(1,CatCompetenciasPlan.class));
		plan.setCatModalidadPlanPrograma(catModalidadService.buscarPorId(5, CatModalidadPlanPrograma.class));
		plan.setFechaInicio(new Date());
		plan.setFundamentacion("Texto libre para terminos relacionados a fundamentacion");
		plan.setObjetivos("texto libre para terminos relacionados a objetivos del plan");
		plan.setPerfilIngreso("El alumno debe tener conocimientos basicos en ciencias sociales");
		plan.setPerfilEgreso("El Egresado contara con conocimientos especializados en la materia");
		plan.setPonderacionCalif(1);
		plan.setCatDocumentosExpidePlan(catDocsExpideService.buscarPorId(1L, CatDocumentosExpidePlan.class));
		
		OrgGubernamentalDTO orgGub = orgGubernamentalService.buscarPorId(13);
		plan.setTblOrganismoGubernamental(orgGub);
		
		plan.setCatEstatusPlan(catEstatusPlan.buscarPorId(1, CatEstatusPlan.class));
		
		plan.setFechaTermino(new Date());
		plan.setFechaRegistro(new Date());
		plan.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
		
		
		try{
			
			ResultadoDTO<PlanDTO> res = planService.guardar(plan);
			
			if(ObjectUtils.isNotNull(res.getDto())){
				logger.error("###RES: " + res.getDto().getIdPlan() + " nombre: " + res.getDto().getNombre());
				PlanDTO planSaved = res.getDto();
				
				
				//Aptitudes
				List<RelPlanAptitudDTO> apts = new ArrayList<>();
				List<CatalogoComunDTO> dataAps = catAptitudesService.findAll(CatAptitudesPlan.class);
				for(CatalogoComunDTO ap : dataAps){
					RelPlanAptitudDTO dto = new RelPlanAptitudDTO();
					dto.setActivo(1);
					dto.setFechaRegistro(new Date());
					dto.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
					dto.setCatAptitudesPlan(ap);
					dto.setTblPlan(planSaved);
					
					apts.add(dto);
				}
				planSaved.setRelPlanAptitudes(apts);
				//fin aptitudes
				
				
				// AREAS DE CONOCIMIENTO
				List<RelPlanConocimientoDTO> conocs = new ArrayList<>();
				List<CatalogoComunDTO> dataCns = catAreasConService.findAll(CatAreasConocimiento.class);
				for(CatalogoComunDTO dato : dataCns){
					RelPlanConocimientoDTO cn = new RelPlanConocimientoDTO();
					cn.setActivo(1);
					cn.setFechaRegistro(new Date());
					cn.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
					cn.setCatAreasConocimiento(dato);
					cn.setTblPlan(planSaved);
					
					conocs.add(cn);
				}
				planSaved.setRelPlanConocimientos(conocs);
				// FIN AREAS DE CONOCIMIENTO
				
				
				
				// HABILIDADES
				List<RelPlanHabilidadDTO> hbls = new ArrayList<>();
				List<CatalogoComunDTO> dataHbls = catHabilidadesService.findAll(CatHabilidadesPlan.class);
				for(CatalogoComunDTO hb : dataHbls){
					RelPlanHabilidadDTO rh = new RelPlanHabilidadDTO();
					rh.setActivo(1);
					rh.setCatHabilidadesPlan(hb);
					rh.setTblPlan(planSaved);
					rh.setFechaRegistro(new Date());
					rh.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
					hbls.add(rh);
				}
				planSaved.setRelPlanHabilidades(hbls);
				//FIN HABILIDADES
				
				 
				
				planSaved.setUsuarioModifico(ConstantesGestor.ID_USER_SYSTEM);
				planSaved.setFechaActualizacion(new Date());
				
				ResultadoDTO<PlanDTO> resII = planService.actualizar(planSaved);
				logger.error("###RES ACT: " + resII.getDto().getIdPlan() + " nombre: " + resII.getDto().getNombre());
				
			}//fin if
			
			
			
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}
	
	 @Test
		public void prueba() {
			assertThat(1==2, is(false));
		}
}
