package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaMatriculaDetalleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaSolicitudDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaBajaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PlanBajaDTO;

public interface NuevaBajaService {

    List<NodoDTO> obtenerTiposBaja();

    List<PlanBajaDTO> obtenerPlanes();

    List<NodoDTO> obtenerSemestresPorPlan(Long idPlan);

    List<NodoDTO> obtenerBloquesPorSemestre(Long idSemestre);

    List<NodoDTO> obtenerProgramasPorEje(Long idEjeCapacitacion);

    List<String> obtenerPeriodos();

    List<NodoDTO> obtenerEventosPorPeriodoYPrograma(String nombrePeriodo, Long idPrograma, String matricula);

    BajaMatriculaDetalleDTO obtenerDatosPorMatricula(String matricula);

    void aplicarBaja(BajaSolicitudDTO solicitud);

    void actualizarBaja(Long idBaja, BajaSolicitudDTO solicitud, Long idMotivoBaja);

    void eliminarBaja(ConsultaBajaDTO baja);
}
