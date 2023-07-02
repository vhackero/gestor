package mx.gob.sedesol.basegestor.springinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.utils.CatGestionEscolarEnum;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatAlcanceArea;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatAsistencia;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatCategoriaEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatClasificacionAva;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatClasificacionInformacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDestinatariosEc;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDictamen;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatEstadoEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoCalificacionEc;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatTipoResponsabilidadEc;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;

/**
 * Created by jhcortes on 1/02/17.
 */
@Service("gestionEscolarServiceAdapter")
public class GestionEscolarServiceAdapter {

	final ApplicationContext context = null;

	public GestionEscolarServiceAdapter() {
	}

	@Autowired
	private CatalogoComunService<CatCategoriaEventoCapacitacion, Integer> catalogoEventoCapacitacionService;

	@Autowired
	private CatalogoComunService<CatDictamen, Integer> catalogoDictamenService;

	@Autowired
	private CatalogoComunService<CatEstadoEventoCapacitacion, Integer> catalogoEstadoEventoCapacitacionService;

	@Autowired
	private CatalogoComunService<CatClasificacionAva, Integer> catalogoClasificacionAvaService;

	@Autowired
	private CatalogoComunService<CatAlcanceArea, Integer> catalogoAlcanceAreaService;

	@Autowired
	private CatalogoComunService<CatTipoResponsabilidadEc, Integer> catalogoTipoResponsabilidadEcService;

	@Autowired
	private CatalogoComunService<CatTipoCalificacionEc, Integer> catalogoTipoCalificacionEcService;

	@Autowired
	private CatalogoComunService<CatClasificacionInformacion, Integer> catalogoClasificacionInformacionService;
	
	@Autowired
	private CatalogoComunService<CatDestinatariosEc, Integer> catalogoDestinatariosEcService;
	
	@Autowired
	private CatalogoComunService<CatAsistencia, Integer> catAsistenciaService;

	@SuppressWarnings("rawtypes")
	public CatalogoComunService getCatalogoServiceByGestionEscolarEnum(CatGestionEscolarEnum catalogo) {
		switch (catalogo) {
		case CAT_CATEGORIA_EVENTO_CAPACITACION:
			return catalogoEventoCapacitacionService;
		case CAT_DICTAMEN:
			return catalogoDictamenService;
		case CAT_ESTADO_EVENTO_CAPACITACION:
			return catalogoEstadoEventoCapacitacionService;
		case CAT_TIPO_CALIFICACION_EC:
			return catalogoTipoCalificacionEcService;
		case CAT_CLASIFICACION_INFORMACION:
			return catalogoClasificacionInformacionService;
		case CAT_CLASIFICACION_AVA:
			return catalogoClasificacionAvaService;
		case CAT_ALCANCE_AREA:
			return catalogoAlcanceAreaService;
		case CAT_TIPO_RESPONSABILIDAD_EC:
			return catalogoTipoResponsabilidadEcService;
		case CAT_DESTINATARIOS_EC:
			return catalogoDestinatariosEcService;
		case CAT_ASISTENCIA:
			return catAsistenciaService;
		}
		return null;
	}
}
