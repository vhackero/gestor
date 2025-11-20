package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;
import java.util.Optional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CatalogoOpcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.NuevaBajaDTO;

public interface IBajaUsuarioRepository {

    List<CatalogoOpcionDTO> obtenerTiposBaja();

    Optional<Long> buscarPersonaPorMatricula(String matricula);

    List<CatalogoOpcionDTO> obtenerPlanesPorPersona(Long idPersona);

    List<CatalogoOpcionDTO> obtenerSemestres(Long idPersona, Integer idPlan);

    List<CatalogoOpcionDTO> obtenerBloques(Long idPersona, Integer idPlan, Integer semestre);

    List<CatalogoOpcionDTO> obtenerProgramas(Long idPersona, Integer idPlan, Integer semestre, String bloque);

    List<CatalogoOpcionDTO> obtenerPeriodos(Integer idPlan);

    List<CatalogoOpcionDTO> obtenerEventos(Integer idPlan, Integer idPrograma);

    Integer obtenerProcesoBajas();

    BajaDetalleDTO obtenerDetalleBaja(Long idPersona, Integer idPlan, Integer idPrograma, Integer idEvento);

    Integer registrarMotivo(NuevaBajaDTO nuevaBaja);

    void registrarBaja(NuevaBajaDTO nuevaBaja, Long usuarioSesion);
}
