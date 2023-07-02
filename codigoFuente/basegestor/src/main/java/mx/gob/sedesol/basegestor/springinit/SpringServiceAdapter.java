package mx.gob.sedesol.basegestor.springinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.utils.CatPlanesProgramasEnum;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatMobiliario;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatActividadesAprendizajePrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatDocumentosExpidePlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatEstatusPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModeloPedagogicoPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatNivelEnsenanzaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatObjetoCurricular;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatStatusPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTecnicaDidacticaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatTipoPlan;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;


@Service("springServiceAdapter")
public class SpringServiceAdapter {

	//private static final String CONFIG_PATH = "classpath*:spring/applicationContext.xml";
	final ApplicationContext context = null;
	
	public SpringServiceAdapter (){
		//context = new ClassPathXmlApplicationContext(CONFIG_PATH);
	}
	
	@Autowired
	private  CatalogoComunService<CatDocumentosExpidePlan,Long> docExpidePlanService;
	@Autowired
	private  CatalogoComunService<CatTipoPlan,Integer> tipoPlanService;
	@Autowired
	private  CatalogoComunService<CatEstatusPlan,Long> estatusPlanService;
	@Autowired
	private  CatalogoComunService<CatStatusPrograma,Long> edoProgramaService;
	@Autowired
	private  CatalogoComunService<CatTecnicaDidacticaPrograma,Long> tecnicasDidacticasService;
	@Autowired
	private  CatalogoComunService<CatActividadesAprendizajePrograma,Long> actAprendizajeProgService;
	@Autowired
	private  CatalogoComunService<CatNivelEnsenanzaPrograma,Long> nivelEnsProgramaService;
	@Autowired
	private  CatalogoComunService<CatModeloPedagogicoPlan,Long> modeloPedagPlanService;
	@Autowired
	private CatalogoComunService<CatObjetoCurricular, Integer> objetoCurricularService;
	@Autowired
	private CatalogoComunService<CatMobiliario, Integer> mobiliarioService;
	

	public Object getServiceByType(Class<?> classType){
		return context.getBean(classType);
	}

	@SuppressWarnings("rawtypes")
	public CatalogoComunService getCatalogoServiceByPlanProgramaEnum(CatPlanesProgramasEnum catalogo){
		switch (catalogo) {
		case CAT_DOCUMENTOS_EXPIDE_PLAN: return docExpidePlanService; 
		case CAT_TIPO_PLAN: return tipoPlanService; 
		case CAT_ESTADO_PLAN: return estatusPlanService;
		case CAT_ESTADO_PROGRAMA: return edoProgramaService;
		case CAT_TECNICAS_DIDACTICAS_PROGRAMA: return tecnicasDidacticasService;
		case CAT_ACTIVIDADES_APRENDIZAJE_PROGRAMA: return actAprendizajeProgService;
		case CAT_NIVEL_ENSENANZA_PROGRAMA: return nivelEnsProgramaService;
		case CAT_OBJETO_CURRICULAR: return objetoCurricularService;
		}
		return null;
	}
	

}
