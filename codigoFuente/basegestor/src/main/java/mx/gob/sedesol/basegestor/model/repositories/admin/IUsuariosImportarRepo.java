package mx.gob.sedesol.basegestor.model.repositories.admin;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaSigeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.FuenteExternaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.SelectImportarDTO;

public interface IUsuariosImportarRepo { 

	List<SelectImportarDTO> consultaConvocatorias();

	List<SelectImportarDTO> consultaFuenteExterna();

	FuenteExternaDTO buscarFuenteExternaPorId(Integer idFuente);

	List<SelectImportarDTO> consultaPlanesActivos();

	List<SelectImportarDTO> consultaSemestresPorPlan(Integer idPlan);

	List<SelectImportarDTO> consultaBloquesPorSemestre(Integer idSemestre);

	List<SelectImportarDTO> consultaProgramasPorEje(Integer idEjeCapacitacion);

	List<SelectImportarDTO> consultaPeriodosInscripcion();

	List<SelectImportarDTO> consultaEventosPorPeriodoYPrograma(String nombrePeriodo, Integer idPrograma);

	List<SelectImportarDTO> consultaGruposPorEvento(Integer idEvento);

	List<PersonaSigeDTO> consultaPersonasImportar(String fuenteExterna, String convocatoria);
	long[] resumenFuenteImportar(Integer fuente,Integer convocatoria);
	List<PersonaSigeDTO> paginaFuenteImportar(Integer fuente,Integer convocatoria,long ultimo,long corte,int limite);
	void insertarAspirantesFuenteLote(List<mx.gob.sedesol.basegestor.commons.dto.admin.CapturaPersonaDTO> datos,Integer convocatoria);

	void insertAspirante(String idPersonaRegistrada, String programaEducativo, String idConvocatoria);

	boolean verificarRelacionConvocatoria(String planId, String convocatoriaId);

	
}
