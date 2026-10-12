package mx.gob.sedesol.basegestor.service.admin;

import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaExternaCargaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoCargaExternaDTO;

public interface PersonaExternaCargaService {
    ResultadoCargaExternaDTO importar(List<PersonaExternaCargaDTO> filas);
    List<Object[]> catalogosPlantilla();
}
