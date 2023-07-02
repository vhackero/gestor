/**
 * 
 */
package mx.gob.sedesol.basegestor.service.encuestas;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.encuestas.PreguntaEncuestaDTO;
import mx.gob.sedesol.basegestor.model.entities.encuestas.TblPreguntasEncuesta;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

/**
 * @author jhcortes
 *
 */
public interface PreguntaEncuestaService extends CommonService<PreguntaEncuestaDTO, Long>{

	/**
	 * 
	 * @param idEncuesta
	 * @return
	 */
	List<TblPreguntasEncuesta> buscarPorIdEncuesta(Long idEncuesta);
}
