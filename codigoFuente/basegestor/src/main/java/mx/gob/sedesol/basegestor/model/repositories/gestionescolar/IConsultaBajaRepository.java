package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public interface IConsultaBajaRepository {

    List<ConsultaBajaDTO> buscarBajas(String matricula, String nombrePeriodo, Boolean estatus);

    List<CatalogoComunDTO> obtenerPeriodos();
}
