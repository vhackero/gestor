package mx.gob.sedesol.basegestor.service.gestionescolar.v2;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.MensajeInstitucionalContextualDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.PatronConocimientoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoCasoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;

public interface BaseConocimientoAcademicoService {

    List<PatronConocimientoDTO> obtenerPatronesActivos() throws InscripcionException;

    List<PatronConocimientoDTO> obtenerPatronesPorTipoCaso(TipoCasoAcademicoDTO tipoCaso) throws InscripcionException;

    List<MensajeInstitucionalContextualDTO> obtenerMensajesPorPerfilYPeriodo(String perfil, String periodoOperativo)
            throws InscripcionException;

    List<PatronConocimientoDTO> obtenerPatronesRelevantes(CasoAcademicoOperativoDTO caso, String perfil,
            String periodoOperativo) throws InscripcionException;

    List<MensajeInstitucionalContextualDTO> obtenerMensajesContextuales(CasoAcademicoOperativoDTO caso, String perfil,
            String periodoOperativo) throws InscripcionException;
}
