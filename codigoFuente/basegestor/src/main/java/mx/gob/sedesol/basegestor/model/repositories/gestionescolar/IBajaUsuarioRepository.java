package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;
import java.util.Optional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CatalogoOpcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.NuevaBajaDTO;

public interface IBajaUsuarioRepository {

    List<CatalogoOpcionDTO> obtenerTiposBaja();

    Optional<Long> buscarPersonaPorMatricula(String matricula);

    List<CatalogoOpcionDTO> obtenerPlanes();

    List<CatalogoOpcionDTO> obtenerSemestres(Integer idPlan);

    List<CatalogoOpcionDTO> obtenerBloques(Integer idSemestre);

    List<CatalogoOpcionDTO> obtenerProgramas(Integer idEjeCapacitacion);

    List<CatalogoOpcionDTO> obtenerPeriodos();

    List<CatalogoOpcionDTO> obtenerEventos(String nombrePeriodo, Integer idPrograma);

    Integer obtenerProcesoBajas();

    BajaDetalleDTO obtenerDetalleBaja(Long idPersona, Integer idPlan, Integer idPrograma, Integer idEvento);

    Integer registrarMotivo(NuevaBajaDTO nuevaBaja);

    void registrarBaja(NuevaBajaDTO nuevaBaja, Long usuarioSesion);
}
