package mx.gob.sedesol.basegestor.service.planesyprogramas;

import java.util.List;


import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface FichaDescProgramaService extends CommonService<FichaDescProgramaDTO, Integer> {

	public List<FichaDescProgramaDTO>buscaProgramasPorCriterios(FichaDescProgramaDTO filtro);
	
	List<FichaDescProgramaDTO> buscaProgramasPorCriteriosDatosBasicos(FichaDescProgramaDTO filtro);
	
	public List<FichaDescProgramaDTO> consultaUltimosProgramas();
	
	public List<FichaDescProgramaDTO> consultaProgramasPorEstatus(Integer idEstatus);
	
	public List<FichaDescProgramaDTO> consultarProgramasPorTCompYEjeCap(
			Integer tipoCompetencia,
			Integer ejeCapacitacion, 
			Integer idEstatusPrograma);
	
	public void eliminaRelProgPersonalExtPorIdPrograma(Integer idPrograma);
	
	public void eliminaRelProgResponsablesIdPrograma(Integer idPrograma);
	
	public void eliminaRelProgAutorIdPrograma(Integer idPrograma);
	
	public void eliminaRelProgCompEspecificasIdPrograma(Integer idPrograma);
	
	public void eliminaRelProgDuracionIdPrograma(Integer idPrograma);
	
	public Integer totalProgramasActivosByEstatus(Integer idEstatus);
	
	public Integer totalProgramas();
	
	public Integer totalProgramasByTipo(Integer tipo);
	
	public Integer totalModalidadPrograma(Integer idModalidad);
	
	public Integer totalNivelEnsenanzaById(Integer idNivelEnsenanza);
	
	public Integer totalNivelConocimientoById(Integer idNivelConocimiento);
	
	public Integer totalTipoEventoById(Integer idEvento);
	
	public TblFichaDescriptivaPrograma consultaProgramasPorIdNombre(Integer idPrograma,String nombreTentativo);
	
}
