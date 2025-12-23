package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IConsultaBajaRepository;
import mx.gob.sedesol.basegestor.service.gestionescolar.ConsultaBajaService;

@Service("consultaBajaService")
public class ConsultaBajaServiceImpl implements ConsultaBajaService {

    private static final Logger LOGGER = Logger.getLogger(ConsultaBajaServiceImpl.class);

    @Autowired
    private IConsultaBajaRepository consultaBajaRepository;

    @Override
    public List<ConsultaBajaDTO> buscarBajas(String matricula, String periodo, Integer estatus) {
        try {
            return consultaBajaRepository.buscarBajas(matricula, periodo, estatus);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar bajas de usuarios", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public List<CatalogoComunDTO> obtenerPeriodos() {
        try {
            return consultaBajaRepository.obtenerPeriodos();
        } catch (Exception ex) {
            LOGGER.error("Error al consultar periodos de inscripción", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public void eliminarBaja(ConsultaBajaDTO baja) {
        try {
            if (baja == null || baja.getIdBaja() == null) {
                throw new IllegalArgumentException("No se proporcionó la baja a eliminar");
            }

            boolean esBajaDefinitiva = baja != null && baja.getTipoBaja() != null
                    && baja.getTipoBaja().toLowerCase().contains("definitiva");
            consultaBajaRepository.eliminarBaja(baja.getIdBaja(), baja.getIdPersona(), esBajaDefinitiva);
        } catch (Exception ex) {
            LOGGER.error("Error al eliminar la baja de usuario", ex);
            throw ex;
        }
    }
}
