package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
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

    List<NodoDTO> consultarEventosPorPeriodoYPrograma(String nombrePeriodo, Long idPrograma);

    Long insertarMotivoBaja(Long idTipoBaja, String descripcion);

    Long obtenerIdProcesoBaja();

    Long obtenerIdPersonaPorMatricula(String matricula);

    BajaMatriculacionDTO consultarMatriculacionPorEvento(String matricula, Long idEvento);

    Integer obtenerIdUsuarioMoodle(String matricula);

    void insertarBaja(BajaAplicacionDTO bajaAplicacionDTO);
}
