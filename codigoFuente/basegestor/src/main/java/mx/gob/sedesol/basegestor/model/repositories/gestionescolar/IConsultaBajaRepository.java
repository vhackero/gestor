package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;

public interface IConsultaBajaRepository {

    List<ConsultaBajaDTO> buscarBajas(String matricula, String nombrePeriodo, Integer estatus);

    List<String> obtenerPeriodosInscripcion();
}
