package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaAplicacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PlanBajaDTO;

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

    void actualizarPersonaInactiva(Long idPersona);

    BajaMatriculacionDTO consultarMatriculacionPorEvento(String matricula, Long idEvento);

    Integer obtenerIdUsuarioMoodle(Long idPersona, Long idEvento);

    Integer obtenerIdCursoMoodle(Long idEvento);

    Integer obtenerIdPlataformaMoodle(Long idEvento);

    void insertarBaja(BajaAplicacionDTO bajaAplicacionDTO);

    BajaMatriculaDetalleDTO consultarDatosPorMatricula(String matricula);

    boolean validarPlanYProgramaPorMatricula(String matricula, Long idPlan, Long idPrograma);
}
