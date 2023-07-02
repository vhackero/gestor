package mx.gob.sedesol.basegestor.mongo.service;

import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;

/**
 * Servicio para la consulta de la bitacora
 *
 * @author Carlos Lopez
 *
 */
public interface BitacoraService {

    /**
     * Obtiene la bitacora con los siguientes filtros
     *
     * @param bitacoraDTO - filtros: rango de fechas, usuario, operacion
     * @return
     */
    List<BitacoraDTO> obtenerBitacora(BitacoraDTO bitacoraDTO);

    /**
     * Guarda un regstro de bitácora
     *
     * @param dto Datos a guardar en la bitácora
     * @return ResultadoDTO
     */
    void guardarBitacora(final BitacoraDTO dto);

}
