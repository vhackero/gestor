package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;

public interface ConsultaBajaService {

    List<ConsultaBajaDTO> buscarBajas(String matricula, Integer idPeriodo, Integer estatus);
}
