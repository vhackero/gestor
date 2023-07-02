package mx.gob.sedesol.basegestor.service.planesyprogramas;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEtematicaTemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.EstructuraTematicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEstUnidadDidacticaDTO;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface EstructuraTematicaService extends CommonService<EstructuraTematicaDTO, Integer> {

	public EstructuraTematicaDTO obtenerEstructuraTematicaPorId(Integer id_est_tematica);
	
	public DetEtematicaTemaDTO obtieneDetEtematicaTema(Integer idEstTematica, String nombreTema);
	
	public EstructuraTematicaDTO obtieneEstTematicaTemaPorPrograma(Integer idPrograma);
	
	public List<RelEstUnidadDidacticaDTO> obtieneRelacionesPorEstTematica(Integer idTema); 
	
	public List<RelEstUnidadDidacticaDTO> obtieneRelEstUnidadDidPorPrograma(Integer idPrograma);
	
	public List<RelEstUnidadDidacticaDTO> obtieneEstUnidadesPorIdPrograma(Integer idPrograma);
		
	
	
	
}
