package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;

public interface UsuarioDatosLaboralesService extends CommonService<UsuarioDatosLaboralesDTO, Integer> {
	UsuarioDatosLaboralesDTO obtenerDatosLaboralesPorPersona(Long idPersona);

	List<UsuarioDatosLaboralesDTO> busquedaDatosLaboralesPorCriterios(UsuarioDatosLaboralesDTO criterios);
}
