package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public interface ConsultaBajaService {

    List<ConsultaBajaDTO> buscarBajas(String matricula, String nombrePeriodo, Boolean estatus);

    List<CatalogoComunDTO> obtenerPeriodos();
}
