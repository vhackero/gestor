package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.BitacoraCasoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface BitacoraCasoService {

    void registrarEvento(Long idCaso, String evento, String detalle, String usuario) throws InscripcionException;

    List<BitacoraCasoDTO> obtenerBitacora(Long idCaso) throws InscripcionException;

    BitacoraCasoDTO actualizarBitacora(Long idCaso, BitacoraCasoDTO bitacora) throws InscripcionException;

    void eliminarBitacora(Long idCaso, Long idBitacora) throws InscripcionException;
}
