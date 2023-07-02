package mx.gob.sedesol.basegestor.springinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import mx.gob.sedesol.basegestor.commons.utils.CatGestionAprendizajeEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatActividadReforzamientoFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatAmbitoAplicacionFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatClasificacionArchivoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatComposicionObjetoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatDensidadSemanticaOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatDificultadOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatElementosMultimedia;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatEstadoAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatEstatusFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatFormatoFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatIdiomaOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatNivelGranularidadOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatNivelInteractividadOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatRecursoDidacticoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatRecursoPredominanteFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatRelacionOtrosObjeto;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTemaRecurso;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTipoContenidoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTipoEstructuraOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTipoInteractividadOa;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoResponsabilidadEc;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;

@Service("gestionAprendizajeServiceAdapter")
public class GestionAprendizajeServiceAdapter {


    final ApplicationContext context = null;

    public GestionAprendizajeServiceAdapter(){
    }

    
    @Autowired
    private CatalogoComunService<CatClasificacionArchivoOa,Integer> catClasificacionArchivoOaService;

    @Autowired
    private CatalogoComunService<CatEstadoAva,Integer> catEstadoAvaService;

    
    @Autowired
    private CatalogoComunService<CatTemaRecurso,Integer> catTemaRescursoService;

	@Autowired
    private CatalogoComunService<CatTipoContenidoOa, Integer> catTipoContenidoService;
        

    @Autowired
    private CatalogoComunService<CatNivelGranularidadOa, Integer> catNivelGranularidadService;
    
    @Autowired
    private CatalogoComunService<CatTipoEstructuraOa, Integer> catTipoEstructuraService;
    
    @Autowired
    private CatalogoComunService<CatComposicionObjetoOa, Integer> catComposicionObjetoService;
    
    @Autowired
    private CatalogoComunService<CatDensidadSemanticaOa, Integer> catDensidadSemanticaService;
    
    @Autowired
    private CatalogoComunService<CatNivelInteractividadOa, Integer> catNivelInteractividadService;

    @Autowired
    private CatalogoComunService<CatRecursoDidacticoOa, Integer> catRecursoDidacticoService;
    
    @Autowired
    private CatalogoComunService<CatTipoInteractividadOa, Integer> catTipoInteractividadService;
    
    @Autowired
    private CatalogoComunService<CatTipoResponsabilidadEc,Integer> catalogoTipoResponsabilidadEcService;

    @Autowired
    private CatalogoComunService<CatDificultadOa, Integer>  catDificultadOaService;
    
    @Autowired
    private CatalogoComunService<CatEstatusFoa, Integer>  catEstatusOaService;
     
    @Autowired
    private CatalogoComunService<CatFormatoFoa, Integer>  catFormatoOaService;
    
    @Autowired
    private CatalogoComunService<CatIdiomaOa, Integer> catIdiomaOaService;
                
    @Autowired
    private CatalogoComunService<CatActividadReforzamientoFoa, Integer> catActividadReforzamientoFoa;
    
    @Autowired
    private CatalogoComunService<CatAmbitoAplicacionFoa, Integer> catAmbitoAplicacionFoa;
    
    @Autowired
    private CatalogoComunService<CatElementosMultimedia, Integer> catElementosMultimedia;
    
    @Autowired
    private CatalogoComunService<CatRelacionOtrosObjeto, Integer> catRelacionOtrosObjeto;
    
    @Autowired
    private CatalogoComunService<CatRecursoPredominanteFoa, Integer> catRecursoPredominanteFoa;
    
    
        
    @SuppressWarnings("rawtypes")

    public CatalogoComunService getCatalogoServiceByGestionAprendizajeEnum(CatGestionAprendizajeEnum catalogo){
        switch(catalogo){
                                 
            case CAT_CLASIFICACION_ARCHIVO_OA:       return catClasificacionArchivoOaService;           
            case CAT_ESTADO_AVA:                     return catEstadoAvaService;            
            case CAT_TEMA_RECURSO:                   return catTemaRescursoService;
			case CAT_TIPO_CONTENIDO_OA:              return catTipoContenidoService;		    
            case CAT_NIVEL_GRANULARIDAD_OA:          return catNivelGranularidadService;
            case CAT_TIPO_ESTRUCTURA_OA:             return catTipoEstructuraService;
            case CAT_COMPOSICION_OBJETO_OA:          return catComposicionObjetoService;
            case CAT_DENSIDAD_OBJETO_OA:             return catDensidadSemanticaService;
            case CAT_NIVEL_INTERACTIVIDAD_OA:        return catNivelInteractividadService;
            case CAT_RECURSO_DIDACTICO_OA:           return catRecursoDidacticoService;
            case CAT_TIPO_INTERACTIVIDAD_OA:         return catTipoInteractividadService;
            case CAT_TIPO_RESPONSABILIDAD_EC:        return catalogoTipoResponsabilidadEcService;
            case CAT_DIFICULTAD_OA:                  return  catDificultadOaService;
            case CAT_ESTATUS_OA:                     return catEstatusOaService;
            case CAT_FORMATO_OA:                     return catFormatoOaService;
            case CAT_IDIOMA_OA:                      return catIdiomaOaService;     
            case CATALOGO_ACTIVIDAD_REFORZAMIENTO :  return catActividadReforzamientoFoa ;
            case CATALOGO_AMBITO_APLICACION :        return catAmbitoAplicacionFoa;
            case CATALOGO_ELEMENTOS_MULTIMEDIA:      return catElementosMultimedia;
            case CATALOGO_RELACION_OTROS_OBJETO :    return catRelacionOtrosObjeto;
            case CATALOGO_RECURSO_PREDOMINANTE :     return catRecursoPredominanteFoa;

        }
        return null;
    
    
	
    }
    
}
