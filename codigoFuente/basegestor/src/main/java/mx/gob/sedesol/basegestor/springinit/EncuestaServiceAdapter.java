package mx.gob.sedesol.basegestor.springinit;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.utils.CatEncuestasYEvaluacionesEnum;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaContexto;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaEstatus;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaObjetivo;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaTipo;
import mx.gob.sedesol.basegestor.service.encuestas.CatalogoComunEncuestaService;

@Service("encuestaServiceAdapter")
public class EncuestaServiceAdapter {

	final ApplicationContext context = null;

	public EncuestaServiceAdapter() {
	}

	@Resource(name = "catalogoComunEncuestaService")
	private CatalogoComunEncuestaService<CatEncuestaContexto, Long> encuestaContextoService;
	@Resource(name = "catalogoComunEncuestaService")
	private CatalogoComunEncuestaService<CatEncuestaEstatus, Long> encuestaEstatusService;
	@Resource(name = "catalogoComunEncuestaService")
	private CatalogoComunEncuestaService<CatEncuestaObjetivo, Long> encuestaObjetivoService;
	@Resource(name = "catalogoComunEncuestaService")
	private CatalogoComunEncuestaService<CatEncuestaTipo, Long> encuestaTipoService;

	public Object getServiceByType(Class<?> classType) {
		return context.getBean(classType);
	}

	/**
	 * Modulo de encuestas y evaluaciones.
	 * 
	 * @param catalogo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public CatalogoComunEncuestaService getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum catalogo) {
		switch (catalogo) {
		case CAT_ENCUESTAS_CONTEXTO:
			return encuestaContextoService;
		case CAT_ENCUESTAS_ESTATUS:
			return encuestaEstatusService;
		case CAT_ENCUESTAS_OBJETIVO:
			return encuestaObjetivoService;
		case CAT_ENCUESTAS_TIPO:
			return encuestaTipoService;
		default:
			break;
		}
		return null;
	}

}