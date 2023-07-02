/**
 * 
 */
package mx.gob.sedesol.basegestor.service.encuestas;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.model.entities.encuestas.TblEncuesta;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

/**
 * @author jhcortes
 *
 */
public interface EncuestaService  extends CommonService<EncuestaDTO, Integer>{

	EncuestaDTO buscarEncuestaNomClv(String nombre, String clave);
	
	List<EncuestaDTO> buscarPorCriterios(EncuestaDTO dto);

	List<EncuestaDTO> buscarTodosOrdenEstatus();
	
	List<EncuestaDTO> buscarTipoEncuesta(Integer clTipoEncuesta);
	
	List<EncuestaDTO> buscarEncuestaContexto(Integer clContexto);
	
	Double calificacionPrograma(Integer idPrograma);
	
	List<EncuestaDTO> buscarEncsPorKirkReaccionAlumnoPublicar();
	List<EncuestaDTO> consultarEncuestasPorDefecto();
}

