package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface PersonaResponsabilidadesService extends CommonService<PersonaResponsabilidadesDTO, Integer> {

	public List<PersonaResponsabilidadesDTO> obtienePersonasPorResponsabilidad(Integer idTipoResponsabilidad);

	public List<PersonaResponsabilidadesDTO> obtienePersonasPorResponsabilidadList(List<Integer> idResponsabilidades) ;
}
