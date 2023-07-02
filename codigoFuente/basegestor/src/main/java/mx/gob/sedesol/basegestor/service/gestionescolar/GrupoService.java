package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;
import java.util.Map;

import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;

public interface GrupoService {
	
	public List<GrupoDTO> getGruposByEvento(Integer idEvento);

	GrupoDTO generarGrupo(Integer idEvento, Long usuarioModifico);

	ResultadoDTO<GrupoDTO> eliminarGrupo(GrupoDTO grupo, Long usuarioModifico, Integer idPlataforma);

	List<GrupoDTO> generarGrupos(EventoCapacitacionDTO evento, int numeroGrupos, 
			Long usuarioModifico, ParametroWSMoodleDTO parametroWSMoodleDTO);

	
	GrupoDTO buscarGrupoPorId(Integer idGrupo);

	ResultadoDTO<GrupoDTO> actualizarGrupo(GrupoDTO grupo, Long usuarioModifico);

	ResultadoDTO<GrupoDTO> establecerFacilitador(GrupoDTO grupo, EventoCapacitacionDTO evento,
			ParametroWSMoodleDTO parametroWSMoodleDTO);

	Map<Integer,Boolean> validaActasCerradas(List<Integer> idEventoCapacitacion);
	

}
