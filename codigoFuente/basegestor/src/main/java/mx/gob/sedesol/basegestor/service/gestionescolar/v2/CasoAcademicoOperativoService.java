package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface CasoAcademicoOperativoService {

    CasoAcademicoOperativoDTO crearCaso(CasoAcademicoOperativoDTO caso) throws InscripcionException;

    CasoAcademicoOperativoDTO obtenerCasoPorId(Long idCaso) throws InscripcionException;

    CasoAcademicoOperativoDTO obtenerCasoPorFolio(String folioExterno) throws InscripcionException;

    List<CasoAcademicoOperativoDTO> obtenerCasosPorPersona(Long idPersona) throws InscripcionException;

    CasoAcademicoOperativoDTO obtenerCasoMasRecientePorPersona(Long idPersona) throws InscripcionException;

    CasoAcademicoOperativoDTO actualizarEstatus(Long idCaso, String estatusCaso, Long usuarioModifico) throws InscripcionException;

    CasoAcademicoOperativoDTO actualizarCaso(CasoAcademicoOperativoDTO caso) throws InscripcionException;
}
