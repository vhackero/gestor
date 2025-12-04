package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;

public interface IConsultaBajaRepository {

    List<ConsultaBajaDTO> buscarBajas(String matricula, String periodo, Integer estatus);

    List<CatalogoComunDTO> obtenerPeriodos();

    boolean eliminarBaja(Integer idBaja);
}
