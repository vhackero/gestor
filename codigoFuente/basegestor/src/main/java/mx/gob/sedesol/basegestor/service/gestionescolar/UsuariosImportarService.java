package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.SelectImportarDTO;



public interface UsuariosImportarService {
	

	List<SelectImportarDTO> consultaConvocatorias();

	List<SelectImportarDTO> consultaFuenteExterna();

	List<SelectImportarDTO> consultaPlanesActivos();

	List<SelectImportarDTO> consultaSemestresPorPlan(Integer idPlan);

	List<SelectImportarDTO> consultaBloquesPorSemestre(Integer idSemestre);

	List<SelectImportarDTO> consultaProgramasPorEje(Integer idEjeCapacitacion);

	List<SelectImportarDTO> consultaPeriodosInscripcion();

	List<SelectImportarDTO> consultaEventosPorPeriodoYPrograma(String nombrePeriodo, Integer idPrograma);

	List<SelectImportarDTO> consultaGruposPorEvento(Integer idEvento);
	
	List<PersonaSigeDTO> consultaPersonasImportar(String fuenteExterna, String convocatoria);


}


