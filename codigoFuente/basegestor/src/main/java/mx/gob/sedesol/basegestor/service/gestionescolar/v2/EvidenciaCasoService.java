package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EvidenciaCasoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface EvidenciaCasoService {

    EvidenciaCasoDTO registrarEvidencia(Long idCaso, EvidenciaCasoDTO evidencia) throws InscripcionException;

    List<EvidenciaCasoDTO> obtenerEvidencias(Long idCaso) throws InscripcionException;

    EvidenciaCasoDTO actualizarEvidencia(Long idCaso, EvidenciaCasoDTO evidencia) throws InscripcionException;

    void eliminarEvidencia(Long idCaso, Long idEvidencia) throws InscripcionException;
}
