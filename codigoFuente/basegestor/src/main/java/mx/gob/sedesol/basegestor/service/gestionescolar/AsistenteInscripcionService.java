package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EscenarioCargaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface AsistenteInscripcionService {

    AsistenteInscripcionContextoDTO obtenerContextoAsistido(Long idPersona) throws InscripcionException;

    List<EscenarioCargaDTO> obtenerEscenariosCarga(Long idPersona) throws InscripcionException;

    ResultadoSimulacionDTO simularInscripcion(Long idPersona, List<Long> unidadesIds) throws InscripcionException;

    ResultadoSimulacionDTO validarSeleccionFinal(Long idPersona, List<Long> unidadesIds) throws InscripcionException;

    /**
     * Ejecuta una comparación V2 en memoria. No modifica oferta, selección ni
     * validaciones vigentes; su consumidor deberá tratarla como diagnóstico.
     */
    ResultadoEvaluacionCurricularV2DTO obtenerEvaluacionCurricularSombra(Long idPersona) throws InscripcionException;
}
