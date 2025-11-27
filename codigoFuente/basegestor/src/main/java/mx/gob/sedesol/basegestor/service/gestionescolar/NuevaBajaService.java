package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.NodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.BajaSolicitudDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PlanBajaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelacionBajaDTO;

public interface NuevaBajaService {

    List<NodoDTO> obtenerTiposBaja();

    List<PlanBajaDTO> obtenerPlanes();

    List<NodoDTO> obtenerSemestresPorPlan(Long idPlan);

    List<NodoDTO> obtenerBloquesPorSemestre(Long idSemestre);

    List<NodoDTO> obtenerProgramasPorEje(Long idEjeCapacitacion);

    List<String> obtenerPeriodos();

    List<NodoDTO> obtenerEventosPorPeriodoYPrograma(String nombrePeriodo, Long idPrograma);

    RelacionBajaDTO obtenerRelacionPorMatricula(String matricula);

    void aplicarBaja(BajaSolicitudDTO solicitud);
}
