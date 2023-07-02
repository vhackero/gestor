package mx.gob.sedesol.basegestor.springinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.utils.CatLogisticaInfraestructuraEnum;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatAreaInfraestructura;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatDistribucionArea;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatEstatusArea;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatTipoRecurso;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatUbicacionTerritorial;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;

@Service("logisticaInfraServiceAdapter")
public class LogisticaInfraServiceAdapter {

	@Autowired
	private  CatalogoComunService<CatUbicacionTerritorial,Integer> catUbicacionTerritorialService;
	@Autowired
	private  CatalogoComunService<CatEstatusArea,Integer> catEstatusAreaService;
	@Autowired
	private CatalogoComunService<CatTipoRecurso, Integer> catTiposRecursoService;
	@Autowired
	private CatalogoComunService<CatDistribucionArea, Integer> catDistribucionAreaService;
	@Autowired
	private CatalogoComunService<CatAreaInfraestructura, Integer> catAreaInfraEstructuraService;
	
	/**
	 * 
	 * @param catalogo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public CatalogoComunService getCatalogoComunServiceByLogisticaInfraEnum(CatLogisticaInfraestructuraEnum catalogo){
		
		switch (catalogo) {
		
		case CAT_DISTRIBUCION_AREA: return catDistribucionAreaService;
			
		case CAT_TIPOS_RECURSO: return catTiposRecursoService;
			
		case CAT_ESTATUS_AREA: return catEstatusAreaService;
			
		case CAT_UBICACION_TERRITORIAL: return catUbicacionTerritorialService;
		
		case CAT_AREAS_INFRAESTRUCTURA: return catAreaInfraEstructuraService;
		
		}
		return null;
	}


}
