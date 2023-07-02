package mx.gob.sedesol.basegestor.mongo.service;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;

/**
 * Servicio para el almacenamiento del ingreso al sistema de un usuario
 *
 * @author Carlos Lopez
 *
 */
public interface BitacoraEntradaSistemaService {

    /**
     * Almacena la fecha del primer ingreso al sistema, actualiza la fecha del
     * ultimo ingreso al sistema y almacena el ingreso en bitacora
     *
     * @param idPersona - id del usuario que inicia al sistema
     * @param registro - ip y navegador del cliente
     * @return
     */
    ResultadoDTO<BitacoraDTO> guardarBitacora(long idPersona, String registro);

    /**
     * Obtiene la bitacora del primer y ultimo ingreso al sistema de un usuario
     *
     * @param idPersona - id del usuario
     * @return
     */
    List<BitacoraDTO> obtenerBitacoraEntrada(long idPersona);

    /**
     * Obtiene ultimo ingreso del usuario al sistema
     *
     * @param idPersona
     * @return
     */
    BitacoraDTO obtieneBitacoraUltimaEntradaUsuario(Long idPersona);
}
