package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IConsultaBajaRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConsultaBajaService;

@Service("consultaBajaService")
public class ConsultaBajaServiceImpl implements ConsultaBajaService {

    private static final Logger LOGGER = Logger.getLogger(ConsultaBajaServiceImpl.class);

    @Autowired
    private IConsultaBajaRepository consultaBajaRepository;

    @Override
    public List<ConsultaBajaDTO> buscarBajas(String matricula, Integer idPeriodo, Integer estatus) {
        try {
            return consultaBajaRepository.buscarBajas(matricula, idPeriodo, estatus);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar bajas de usuarios", ex);
            return new ArrayList<>();
        }
    }
}
