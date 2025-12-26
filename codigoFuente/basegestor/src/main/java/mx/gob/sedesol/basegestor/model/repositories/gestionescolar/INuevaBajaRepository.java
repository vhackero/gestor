package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaAplicacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DatosMoodlePersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PlanBajaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;

public interface INuevaBajaRepository {

    List<NodoDTO> consultarTiposBajaActivos();

    List<PlanBajaDTO> consultarPlanesActivos();

    List<NodoDTO> consultarSemestresPorPlan(Long idPlan);

    List<NodoDTO> consultarBloquesPorSemestre(Long idSemestre);

    List<NodoDTO> consultarProgramasPorEje(Long idEjeCapacitacion);

    List<String> consultarPeriodosInscripcion();

    List<NodoDTO> consultarEventosPorPeriodoYPrograma(String nombrePeriodo, Long idPrograma, String matricula);

    Long insertarMotivoBaja(Long idTipoBaja, String descripcion);

    Long obtenerIdProcesoBaja();

    Long obtenerIdPersonaPorMatricula(String matricula);

    boolean validarPlanProgramaPorPersona(Long idPersona, Long idPlan, Long idPrograma);

    void actualizarPersonaInactiva(Long idPersona);

    BajaMatriculacionDTO consultarMatriculacionPorEvento(String matricula, Long idEvento);

    Integer obtenerIdUsuarioMoodle(Long idPersona, Long idEvento);

    List<DatosMoodlePersonaDTO> obtenerDatosMoodlePorPersona(Long idPersona);

    Integer obtenerIdCursoMoodle(Long idEvento);

    Integer obtenerIdPlataformaMoodle(Long idEvento);

    void insertarBaja(BajaAplicacionDTO bajaAplicacionDTO);

    BajaMatriculaDetalleDTO consultarDatosPorMatricula(String matricula);

    ConsultaBajaDTO consultarBajaPorId(Long idBaja);

    void actualizarBaja(Long idBaja, BajaAplicacionDTO bajaAplicacionDTO);

    void eliminarBaja(Long idBaja);

    void actualizarMotivoBaja(Long idMotivoBaja, Long idTipoBaja, String descripcion);

    void reactivarPersona(Long idPersona);
}
