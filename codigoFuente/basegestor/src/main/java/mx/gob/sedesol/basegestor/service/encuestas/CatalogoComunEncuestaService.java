/**
 * 
 */
package mx.gob.sedesol.basegestor.service.encuestas;

import java.io.Serializable;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;

/**
 * @author jhcortes
 *
 */

public interface CatalogoComunEncuestaService <A, ID extends Serializable> extends CatalogoComunService<A, ID>{

	/**
	 * Metodo para buscar tipo de encuesta por contexto
	 * @param idContexto
	 * @param A
	 * @return
	 */
    List<CatalogoComunDTO> buscarPorContexto(Long idContexto, Class<A> classEntidad);

	/**
	 *
	 * @param classEntidad
	 * @return
	 */
    List<CatalogoComunDTO> buscarTipoEncuesta(Class<A> classEntidad);
}