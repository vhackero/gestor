package mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatActividadReforzamientoFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatAmbitoAplicacionFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatComposicionObjetoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatDensidadSemanticaOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatDificultadOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatElementosMultimedia;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatEstatusFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatFormatoFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatIdiomaOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatNivelGranularidadOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatRecursoDidacticoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatRecursoPredominanteFoa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatRelacionOtrosObjeto;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTipoContenidoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTipoEstructuraOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatTipoInteractividadOa;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;

import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelActividadesReforzamientoFoaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelElementosMultimediaFoaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelMaterialDidacticoFoaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelRecursoPredominanteFoaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelRelacionOtrosObjetosService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.RelUnidadOaAvaService;

@Service("fichaDescriptivaOaServiceFacade")
public class FichaDescriptivaOaServiceFacade
{
	private static final Logger logger = Logger.getLogger(FichaDescriptivaOaServiceFacade.class);
	
	@Autowired
	private RelUnidadOaAvaService relUnidadOaAvaService;
	
	@Autowired
	private RelElementosMultimediaFoaService relElementosMultimediaFoaService;
	
	@Autowired
	private RelMaterialDidacticoFoaService relMaterialDidacticoFoaService;

	@Autowired
	private RelActividadesReforzamientoFoaService relActividadesReforzamientoFoaService;

	@Autowired
	private RelRecursoPredominanteFoaService relRecursoPredominanteFoaService;
	
	@Autowired
	private RelRelacionOtrosObjetosService relRelacionOtrosObjetosFoaServices;

	@Autowired
	private CatalogoComunService<CatElementosMultimedia, Integer> catalogoElementosMultimediaService;

	@Autowired
	private CatalogoComunService<CatRecursoDidacticoOa, Integer> catalogoMaterialDidacticoFoaService;	
	
	@Autowired
	private CatalogoComunService<CatTipoEstructuraOa, Integer> catalogoTipoEstructuraService;

	@Autowired
	private CatalogoComunService<CatTipoContenidoOa, Integer> catalogoTipoContenidoService;
	
	@Autowired
	private CatalogoComunService<CatTipoInteractividadOa, Integer> catalogoTipoInteractividadService;

	@Autowired
	private CatalogoComunService<CatRecursoPredominanteFoa, Integer> catalogoTipoRecursoPredominanteService;
	
	@Autowired
	private CatalogoComunService<CatDensidadSemanticaOa, Integer> catalogoDensidadSemanticaService;

	@Autowired
	private CatalogoComunService<CatDificultadOa, Integer> catalogoNivelDificultadService;

	@Autowired
	private CatalogoComunService<CatRecursoDidacticoOa, Integer> catalogoMaterialDidacticoService;

	@Autowired
	private CatalogoComunService<CatActividadReforzamientoFoa, Integer> catalogoActividadReforzamientoService;
		
	@Autowired
	private CatalogoComunService<CatIdiomaOa, Integer> catalogoIdiomaService;
	
	@Autowired
	private CatalogoComunService<CatAmbitoAplicacionFoa, Integer> catalogoAmbitoService;
	
	@Autowired
	private CatalogoComunService<CatNivelGranularidadOa, Integer> catalogoNivelGranularidad;

	@Autowired
	private CatalogoComunService<CatComposicionObjetoOa, Integer> catalogoComposicionObjeto;
	
	@Autowired
	private CatalogoComunService<CatFormatoFoa, Integer> catalogoFormatoFoaService;

	@Autowired
	private CatalogoComunService<CatRelacionOtrosObjeto, Integer> catalogoRelacionOtrosObjetos;

	@Autowired
	private CatalogoComunService<CatRecursoPredominanteFoa, Integer> catalogoRecursoPredominante;
	
	@Autowired
	private CatalogoComunService<CatEstatusFoa, Integer> catalogoEstatusService;

	public RelElementosMultimediaFoaService getRelElementosMultimediaFoaService() {
		return relElementosMultimediaFoaService;
	}

	public void setRelElementosMultimediaFoaService(RelElementosMultimediaFoaService relElementosMultimediaFoaService) {
		this.relElementosMultimediaFoaService = relElementosMultimediaFoaService;
	}

	public RelActividadesReforzamientoFoaService getRelActividadesReforzamientoFoaService() {
		return relActividadesReforzamientoFoaService;
	}

	public void setRelActividadesReforzamientoFoaService(
			RelActividadesReforzamientoFoaService relActividadesReforzamientoFoaService) {
		this.relActividadesReforzamientoFoaService = relActividadesReforzamientoFoaService;
	}

	public CatalogoComunService<CatElementosMultimedia, Integer> getCatalogoElementosMultimediaService() {
		return catalogoElementosMultimediaService;
	}

	public void setCatalogoElementosMultimediaService(
			CatalogoComunService<CatElementosMultimedia, Integer> catalogoElementosMultimediaService) {
		this.catalogoElementosMultimediaService = catalogoElementosMultimediaService;
	}

	public CatalogoComunService<CatRecursoDidacticoOa, Integer> getCatalogoMaterialDidacticoFoaService() {
		return catalogoMaterialDidacticoFoaService;
	}

	public void setCatalogoMaterialDidacticoFoaService(
			CatalogoComunService<CatRecursoDidacticoOa, Integer> catalogoMaterialDidacticoFoaService) {
		this.catalogoMaterialDidacticoFoaService = catalogoMaterialDidacticoFoaService;
	}

	public CatalogoComunService<CatTipoEstructuraOa, Integer> getCatalogoTipoEstructuraService() {
		return catalogoTipoEstructuraService;
	}

	public void setCatalogoTipoEstructuraService(
			CatalogoComunService<CatTipoEstructuraOa, Integer> catalogoTipoEstructuraService) {
		this.catalogoTipoEstructuraService = catalogoTipoEstructuraService;
	}

	public CatalogoComunService<CatTipoContenidoOa, Integer> getCatalogoTipoContenidoService() {
		return catalogoTipoContenidoService;
	}

	public void setCatalogoTipoContenidoService(
			CatalogoComunService<CatTipoContenidoOa, Integer> catalogoTipoContenidoService) {
		this.catalogoTipoContenidoService = catalogoTipoContenidoService;
	}

	public CatalogoComunService<CatTipoInteractividadOa, Integer> getCatalogoTipoInteractividadService() {
		return catalogoTipoInteractividadService;
	}

	public void setCatalogoTipoInteractividadService(
			CatalogoComunService<CatTipoInteractividadOa, Integer> catalogoTipoInteractividadService) {
		this.catalogoTipoInteractividadService = catalogoTipoInteractividadService;
	}

	public CatalogoComunService<CatRecursoPredominanteFoa, Integer> getCatalogoTipoRecursoPredominanteService() {
		return catalogoTipoRecursoPredominanteService;
	}

	public void setCatalogoTipoRecursoPredominanteService(
			CatalogoComunService<CatRecursoPredominanteFoa, Integer> catalogoTipoRecursoPredominanteService) {
		this.catalogoTipoRecursoPredominanteService = catalogoTipoRecursoPredominanteService;
	}

	public CatalogoComunService<CatDensidadSemanticaOa, Integer> getCatalogoDensidadSemanticaService() {
		return catalogoDensidadSemanticaService;
	}

	public void setCatalogoDensidadSemanticaService(
			CatalogoComunService<CatDensidadSemanticaOa, Integer> catalogoDensidadSemanticaService) {
		this.catalogoDensidadSemanticaService = catalogoDensidadSemanticaService;
	}

	public CatalogoComunService<CatDificultadOa, Integer> getCatalogoNivelDificultadService() {
		return catalogoNivelDificultadService;
	}

	public void setCatalogoNivelDificultadService(
			CatalogoComunService<CatDificultadOa, Integer> catalogoNivelDificultadService) {
		this.catalogoNivelDificultadService = catalogoNivelDificultadService;
	}

	public CatalogoComunService<CatRecursoDidacticoOa, Integer> getCatalogoMaterialDidacticoService() {
		return catalogoMaterialDidacticoService;
	}

	public void setCatalogoMaterialDidacticoService(
			CatalogoComunService<CatRecursoDidacticoOa, Integer> catalogoMaterialDidacticoService) {
		this.catalogoMaterialDidacticoService = catalogoMaterialDidacticoService;
	}

	public CatalogoComunService<CatActividadReforzamientoFoa, Integer> getCatalogoActividadReforzamientoService() {
		return catalogoActividadReforzamientoService;
	}

	public void setCatalogoActividadReforzamientoService(
			CatalogoComunService<CatActividadReforzamientoFoa, Integer> catalogoActividadReforzamientoService) {
		this.catalogoActividadReforzamientoService = catalogoActividadReforzamientoService;
	}

	public CatalogoComunService<CatIdiomaOa, Integer> getCatalogoIdiomaService() {
		return catalogoIdiomaService;
	}

	public void setCatalogoIdiomaService(CatalogoComunService<CatIdiomaOa, Integer> catalogoIdiomaService) {
		this.catalogoIdiomaService = catalogoIdiomaService;
	}

	public CatalogoComunService<CatAmbitoAplicacionFoa, Integer> getCatalogoAmbitoService() {
		return catalogoAmbitoService;
	}

	public void setCatalogoAmbitoService(CatalogoComunService<CatAmbitoAplicacionFoa, Integer> catalogoAmbitoService) {
		this.catalogoAmbitoService = catalogoAmbitoService;
	}

	public CatalogoComunService<CatNivelGranularidadOa, Integer> getCatalogoNivelGranularidad() {
		return catalogoNivelGranularidad;
	}

	public void setCatalogoNivelGranularidad(
			CatalogoComunService<CatNivelGranularidadOa, Integer> catalogoNivelGranularidad) {
		this.catalogoNivelGranularidad = catalogoNivelGranularidad;
	}

	public CatalogoComunService<CatComposicionObjetoOa, Integer> getCatalogoComposicionObjeto() {
		return catalogoComposicionObjeto;
	}

	public void setCatalogoComposicionObjeto(
			CatalogoComunService<CatComposicionObjetoOa, Integer> catalogoComposicionObjeto) {
		this.catalogoComposicionObjeto = catalogoComposicionObjeto;
	}

	public CatalogoComunService<CatFormatoFoa, Integer> getCatalogoFormatoFoaService() {
		return catalogoFormatoFoaService;
	}

	public void setCatalogoFormatoFoaService(CatalogoComunService<CatFormatoFoa, Integer> catalogoFormatoFoaService) {
		this.catalogoFormatoFoaService = catalogoFormatoFoaService;
	}

	public CatalogoComunService<CatRelacionOtrosObjeto, Integer> getCatalogoRelacionOtrosObjetos() {
		return catalogoRelacionOtrosObjetos;
	}

	public void setCatalogoRelacionOtrosObjetos(
			CatalogoComunService<CatRelacionOtrosObjeto, Integer> catalogoRelacionOtrosObjetos) {
		this.catalogoRelacionOtrosObjetos = catalogoRelacionOtrosObjetos;
	}

	public CatalogoComunService<CatEstatusFoa, Integer> getCatalogoEstatusService() {
		return catalogoEstatusService;
	}

	public void setCatalogoEstatusService(CatalogoComunService<CatEstatusFoa, Integer> catalogoEstatusService) {
		this.catalogoEstatusService = catalogoEstatusService;
	}

	/**
	 * @return the relMaterialDidacticoFoaService
	 */
	public RelMaterialDidacticoFoaService getRelMaterialDidacticoFoaService() {
		return relMaterialDidacticoFoaService;
	}

	/**
	 * @param relMaterialDidacticoFoaService the relMaterialDidacticoFoaService to set
	 */
	public void setRelMaterialDidacticoFoaService(RelMaterialDidacticoFoaService relMaterialDidacticoFoaService) {
		this.relMaterialDidacticoFoaService = relMaterialDidacticoFoaService;
	}

	/**
	 * @return the relRecursoPredominanteFoaService
	 */
	public RelRecursoPredominanteFoaService getRelRecursoPredominanteFoaService() {
		return relRecursoPredominanteFoaService;
	}

	/**
	 * @param relRecursoPredominanteFoaService the relRecursoPredominanteFoaService to set
	 */
	public void setRelRecursoPredominanteFoaService(RelRecursoPredominanteFoaService relRecursoPredominanteFoaService) {
		this.relRecursoPredominanteFoaService = relRecursoPredominanteFoaService;
	}

	/**
	 * @return the relRelacionOtrosObjetosFoaServices
	 */
	public RelRelacionOtrosObjetosService getRelRelacionOtrosObjetosFoaServices() {
		return relRelacionOtrosObjetosFoaServices;
	}

	/**
	 * @param relRelacionOtrosObjetosFoaServices the relRelacionOtrosObjetosFoaServices to set
	 */
	public void setRelRelacionOtrosObjetosFoaServices(RelRelacionOtrosObjetosService relRelacionOtrosObjetosFoaServices) {
		this.relRelacionOtrosObjetosFoaServices = relRelacionOtrosObjetosFoaServices;
	}

	/**
	 * @return the catalogoRecursoPredominante
	 */
	public CatalogoComunService<CatRecursoPredominanteFoa, Integer> getCatalogoRecursoPredominante() {
		return catalogoRecursoPredominante;
	}

	/**
	 * @param catalogoRecursoPredominante the catalogoRecursoPredominante to set
	 */
	public void setCatalogoRecursoPredominante(CatalogoComunService<CatRecursoPredominanteFoa, Integer> catalogoRecursoPredominante) {
		this.catalogoRecursoPredominante = catalogoRecursoPredominante;
	}

	/**
	 * @return the relUnidadOaAvaService
	 */
	public RelUnidadOaAvaService getRelUnidadOaAvaService() {
		return relUnidadOaAvaService;
	}

	/**
	 * @param relUnidadOaAvaService the relUnidadOaAvaService to set
	 */
	public void setRelUnidadOaAvaService(RelUnidadOaAvaService relUnidadOaAvaService) {
		this.relUnidadOaAvaService = relUnidadOaAvaService;
	}


}
